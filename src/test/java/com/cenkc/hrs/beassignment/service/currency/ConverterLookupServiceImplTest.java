package com.cenkc.hrs.beassignment.service.currency;

import com.cenkc.hrs.beassignment.model.remoteCurrencyApi.ConvertResponseBean;
import com.cenkc.hrs.beassignment.model.remoteCurrencyApi.LiveResponseBean;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * created by cenkc on 10.03.2020
 */
@SpringBootTest
class ConverterLookupServiceImplTest {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${currency.converter.api.accesskey}")
    private String apiAccessKey;

    private String uriConvert;
    private String uriLive;

    private final String apiBaseUrl = "http://api.currencylayer.com";
    private final String endpointLive = "/live";
    private final String endpointConvert = "/convert";

    private static final String SRC_CURRENCY = "USD";
    private static final String TRG_CURRENCY = "EUR";
    private static final int FORMAT = 1;
    private static final int ACCESS_RESTRICTED_ERR_CODE = 105;

    @BeforeEach
    void setUp() {
        uriConvert = prepareConverterUrl();
        uriLive = prepareLiveEndpointUrl();
    }

    @Test
    public void givenRestTemplate_whenRequestedLiveApi_thenResponse() {
        final ResponseEntity<LiveResponseBean> responseEntity = restTemplate.getForEntity(uriLive, LiveResponseBean.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        final LiveResponseBean responseBean = responseEntity.getBody();
        Assert.assertEquals(true, responseBean.isSuccess());
        Assert.assertEquals(SRC_CURRENCY, responseBean.getSource());
    }

    @Test
    void givenRestTemplate_whenRequestedConverterApi_thenFail() {
        // Since FREE Subscription Plan does not support '/convert' endpoint, api call should fail with error code 105
        ResponseEntity<ConvertResponseBean> responseEntity = restTemplate.getForEntity(uriConvert, ConvertResponseBean.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        ConvertResponseBean responseBean = responseEntity.getBody();
        Assert.assertEquals(ACCESS_RESTRICTED_ERR_CODE, responseBean.getError().getCode());
    }

    private String prepareLiveEndpointUrl() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiBaseUrl + endpointLive)
                .queryParam("access_key", apiAccessKey);
        return builder.build().toUri().toASCIIString();
    }

    private String prepareConverterUrl() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiBaseUrl + endpointConvert)
                .queryParam("access_key", apiAccessKey)
                .queryParam("from", SRC_CURRENCY)
                .queryParam("to", TRG_CURRENCY)
                .queryParam("amount", 25)
                .queryParam("format", FORMAT);
        return builder.build().toUri().toASCIIString();
    }
}