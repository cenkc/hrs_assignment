package com.cenkc.hrs.beassignment.service.vat;

import com.cenkc.hrs.beassignment.model.ServiceResponseErrorBean;
import com.cenkc.hrs.beassignment.model.VatValidatorServiceRequestBean;
import com.cenkc.hrs.beassignment.model.VatValidatorServiceResponseBean;
import com.cenkc.hrs.beassignment.model.remoteVatApi.VatLookupRequestBean;
import com.cenkc.hrs.beassignment.model.remoteVatApi.VatLookupResponseBean;
import com.cenkc.hrs.beassignment.util.SystemConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * created by cenkc on 3/9/2020
 */
@Service
@CacheConfig(cacheNames =  {"validator"})
public class VatValidatorServiceImpl implements VatValidatorService {


    @Autowired
    private RestTemplate restTemplate;

    @Value("${vat.validator.api.key}")//a7184b56-c8e2-4abf-abc4-d30cfa17c63f
    private String apiKey;

    @Value("${vat.validator.api.base-url}")//https://api.cloudmersive.com
    private String apiBaseUrl;

    @Value("${vat.validator.api.endpoint-lookup}")///validate/vat/lookup
    private String apiEndpointLookup;

    private static final String APIKEY = "Apikey";

    private static final int RESPONSE_ERROR = 1201;  //responseEntity is null
    private static final int NULL_RESPONSE = 1202;   //responseBean is null
    private static final int INVALID_VAT = 1203;     //responseBean is not valid

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    @Cacheable
    public VatValidatorServiceResponseBean vatLookup(VatValidatorServiceRequestBean requestBean) {

        VatValidatorServiceResponseBean serviceResponseBean = new VatValidatorServiceResponseBean();

        try {
            log.info("Trying to validate VAT number: " + requestBean.getVatNumber());
            HttpHeaders headers = setHeaders();

            HttpEntity<VatLookupRequestBean> entity = new HttpEntity<>(new VatLookupRequestBean(requestBean.getVatNumber()), headers);

            String url = apiBaseUrl + apiEndpointLookup;
            ResponseEntity<VatLookupResponseBean> responseEntity = restTemplate.postForEntity(url, entity, VatLookupResponseBean.class);


            if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
                VatLookupResponseBean responseBean = responseEntity.getBody();
                if (responseBean != null) {
                    if (responseBean.isValid) {
                        serviceResponseBean.setCountryCode(responseBean.getCountryCode());
                    } else {
                        serviceResponseBean.setError(
                                new ServiceResponseErrorBean(
                                        INVALID_VAT, SystemConstants.VAT_LOOKUP_INVALID_VAT_NUMBER));
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
            log.error("Exception occurred while vaildating VAT, message is: " + e.getMessage());
            serviceResponseBean.setError(
                    new ServiceResponseErrorBean(
                            500,
                            "Internal Server Error occurred and logged.."));
        }
        return serviceResponseBean;
    }

    private HttpHeaders setHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(APIKEY, apiKey);
        return headers;
    }

}
