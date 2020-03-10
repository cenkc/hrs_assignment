package com.cenkc.hrs.beassignment.service.currency;

import com.cenkc.hrs.beassignment.model.CurrencyConverterServiceResponseBean;
import com.cenkc.hrs.beassignment.model.CurrencyConverterDTO;
import com.cenkc.hrs.beassignment.model.CurrencyConverterServiceRequestBean;
import com.cenkc.hrs.beassignment.model.ServiceResponseErrorBean;
import com.cenkc.hrs.beassignment.model.remoteCurrencyApi.LiveResponseBean;
import com.cenkc.hrs.beassignment.util.SystemConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;

/**
 * created by cenkc on 3/9/2020
 */
@Service
@CacheConfig(cacheNames = {"converter"})
public class ConverterLookupServiceImpl implements ConverterLookupService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${currency.converter.api.accesskey}")
    private String apiAccessKey;

    @Value("${currency.converter.api.base-url}")
    private String apiBaseUrl;

    @Value("${currency.converter.api.endpoint-live}")
    private String endpointLive;

    @Value("${currency.converter.api.format}")
    private String apiFormat;

    private static final String API_ACCESS_KEY = "access_key";
    private static final String SRC_CURRENCY = "USD";
    private static final String TRG_CURRENCY = "EUR";

    private static final int RESPONSE_ERROR = 1101;     //responseEntity is null
    private static final int NULL_RESPONSE = 1102;      //responseBean is null
    private static final int INVALID_RESPONSE = 1103;   //response is not valid

    private Logger log = LoggerFactory.getLogger(ConverterLookupServiceImpl.class);

    @Override
    @Cacheable
    public CurrencyConverterServiceResponseBean convert(CurrencyConverterServiceRequestBean requestBean) {


        CurrencyConverterServiceResponseBean serviceResponseBean = new CurrencyConverterServiceResponseBean();

        try {
            log.info("Trying to convert " + requestBean.getAmount().toString() + " " + SRC_CURRENCY + " to " + requestBean.getTargetCurrency());
            CurrencyConverterDTO converterDTO = new CurrencyConverterDTO(
                    requestBean.getAmount(),
                    SRC_CURRENCY,
                    requestBean.getTargetCurrency());

            ResponseEntity<LiveResponseBean> responseEntity = restTemplate.getForEntity(generateApiUrl(converterDTO), LiveResponseBean.class);


            if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
                LiveResponseBean responseBean = responseEntity.getBody();
                if (responseBean != null) {
                    if (responseBean.isSuccess()) {
                        serviceResponseBean.setAmount(calculateAmount(converterDTO, responseBean));
                        serviceResponseBean.setSourceCurrency(SRC_CURRENCY);
                        serviceResponseBean.setTargetCurrency(converterDTO.getTrgCurr());
                    } else {
                        serviceResponseBean.setError(
                                new ServiceResponseErrorBean(
                                        INVALID_RESPONSE, SystemConstants.CURRENCY_CONVERTER_INVALID_API_RESPONSE));
                    }
                } else {
                    serviceResponseBean.setError(
                            new ServiceResponseErrorBean(
                                    NULL_RESPONSE, SystemConstants.NULL_API_RESPONSE));
                }

            } else {
                serviceResponseBean.setError(
                        new ServiceResponseErrorBean(
                                RESPONSE_ERROR,
                                SystemConstants.API_RESPONSE_ERROR + responseEntity.getStatusCode()));
            }
        } catch (RestClientException e) {
            log.error("Exception occurred while currenct conversion , message is: " + e.getMessage());
            serviceResponseBean.setError(
                    new ServiceResponseErrorBean(
                            500,
                            "Internal Server Error occurred and logged.."));
        }
        return serviceResponseBean;
    }

    private BigDecimal calculateAmount(CurrencyConverterDTO converterDTO, LiveResponseBean responseBean) {
        Double quote = responseBean.getQuotes().get(SRC_CURRENCY + converterDTO.getTrgCurr());
        return BigDecimal.valueOf(quote).multiply(converterDTO.getAmount());
    }

    private String generateApiUrl(CurrencyConverterDTO converterDTO) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiBaseUrl + endpointLive)
                .queryParam(API_ACCESS_KEY, apiAccessKey)
                //since currencylayer's /live api uses USD for the only Currency Source
                .queryParam("from", SRC_CURRENCY)
                .queryParam("to", converterDTO.getTrgCurr())
                .queryParam("amount", converterDTO.getAmount())
                .queryParam("format", apiFormat);

        return builder.build().toUri().toASCIIString();
    }
}
