package com.cenkc.hrs.beassignment.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * created by cenkc on 3/9/2020
 */
@Configuration
public class RestTemplateConfig {

    @Value("${connect.timeout:3000}")
    private int connTimeOutMillis;

    @Value("${read.timeout:3000}")
    private int readTimeOutMillis;

    @Value("${pool.timeout:1000}")
    private int poolTimeOutMillis;

    @Value("${pool.size:1}")
    private int poolSize;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(getRequestFactory());
        restTemplate.setMessageConverters(getMessageConverters());
        return restTemplate;
    }

    private BufferingClientHttpRequestFactory getRequestFactory() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connTimeOutMillis)
                .setConnectionRequestTimeout(poolTimeOutMillis)
                .setSocketTimeout(readTimeOutMillis)
                .build();

        CloseableHttpClient client = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setMaxConnPerRoute(poolSize)
                .setMaxConnTotal(poolSize)
                .build();

        return new BufferingClientHttpRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
    }

    private List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        return messageConverters;
    }
}
