package com.cenkc.hrs.beassignment.service.vat;

import com.cenkc.hrs.beassignment.model.remoteVatApi.VatLookupRequestBean;
import com.cenkc.hrs.beassignment.model.remoteVatApi.VatLookupResponseBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;

/**
 * created by cenkc on 10.03.2020
 */
@SpringBootTest
class VatValidatorServiceImplTest {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${vat.validator.api.key}")//a7184b56-c8e2-4abf-abc4-d30cfa17c63f
    private String apiKey;

    @Value("${vat.validator.api.base-url}")//https://api.cloudmersive.com
    private String apiBaseUrl;

    @Value("${vat.validator.api.endpoint-lookup}")///validate/vat/lookup
    private String apiEndpointLookup;

    private String uriVatValidator;
    private HttpEntity<VatLookupRequestBean> entity;
    private static final String APIKEY = "Apikey";

    @BeforeEach
    void setUp() {
        uriVatValidator = prepateVatValidatorUrl();
        entity = prepareEntity();
    }

    @Test
    public void givenRestTemplate_whenReques_withValidVatNumber_thenSuccess() {
        ResponseEntity<VatLookupResponseBean> responseEntity = restTemplate.postForEntity(uriVatValidator, entity, VatLookupResponseBean.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        VatLookupResponseBean responseBean = responseEntity.getBody();
        assertEquals(true, responseBean.isValid);
        assertEquals("LU", responseBean.getCountryCode());
    }

    @Test
    public void givenRestTemplate_whenRequest_withInvalidVatNumber_thenFail() {
        ResponseEntity<VatLookupResponseBean> responseEntity = restTemplate
                .postForEntity(
                        uriVatValidator,
                        new HttpEntity<>(new VatLookupRequestBean("DE11111111"), getHeaders()),
                        VatLookupResponseBean.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        VatLookupResponseBean responseBean = responseEntity.getBody();
        assertEquals(false, responseBean.isValid);
    }

    private String prepateVatValidatorUrl() {
        return apiBaseUrl + apiEndpointLookup;
    }

    private HttpEntity<VatLookupRequestBean> prepareEntity() {
        return new HttpEntity<>(new VatLookupRequestBean("LU20260743"), getHeaders());
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(APIKEY, apiKey);
        return headers;
    }
}