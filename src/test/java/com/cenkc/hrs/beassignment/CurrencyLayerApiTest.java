package com.cenkc.hrs.beassignment;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * created by cenkc on 3/9/2020
 */
public class CurrencyLayerApiTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String url = "http://api.currencylayer.com/convert";

    @Test
    public void testCurrencyLayerApi() {
        final ResponseEntity<String> forEntity = restTemplate.getForEntity(prepareConverterUrl(), String.class);
        System.out.println(forEntity.toString());
    }

    private String prepareConverterUrl() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("access_key", "073bc1d58a483dbb6882b5ecf7d1976d")
                .queryParam("from", "USD")
                .queryParam("to", "EUR")
                .queryParam("amount", 25)
                .queryParam("format", 1);
        return builder.build().toUri().toASCIIString();
    }
}
