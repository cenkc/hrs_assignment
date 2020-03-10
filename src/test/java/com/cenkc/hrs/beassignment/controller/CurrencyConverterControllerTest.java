package com.cenkc.hrs.beassignment.controller;

import com.cenkc.hrs.beassignment.model.CurrencyConverterServiceRequestBean;
import com.cenkc.hrs.beassignment.model.CurrencyConverterServiceResponseBean;
import com.cenkc.hrs.beassignment.service.currency.ConverterLookupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * created by cenkc on 10.03.2020
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CurrencyConverterController.class)
class CurrencyConverterControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ConverterLookupService converterLookupService;

    private String uri;
    private ObjectMapper objectMapper;
    private CurrencyConverterServiceResponseBean responseBean;
    private CurrencyConverterServiceRequestBean requestBean;

    @BeforeEach
    void setUp() {
        uri = "/api/currency/convert";
        objectMapper = new ObjectMapper();

        fillResponseBean();

        fillRequestBean();
    }

    @Test
    public void currencyConvert() throws Exception {

        when(converterLookupService.convert(any(CurrencyConverterServiceRequestBean.class))).thenReturn(responseBean);

        MvcResult result = mvc.perform(
                post(uri)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBean)))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        MockHttpServletResponse response = result.getResponse();
        String responseBody = response.getContentAsString();

        //either will work
        assertEquals(objectMapper.writeValueAsString(responseBean), responseBody);
        assertThat(responseBody).isEqualTo(objectMapper.writeValueAsString(responseBean));

        verify(converterLookupService, times(1)).convert(any(CurrencyConverterServiceRequestBean.class));
    }

    private void fillRequestBean() {
        requestBean = new CurrencyConverterServiceRequestBean();
        requestBean.setAmount(new BigDecimal("5.11"));
        requestBean.setSourceCurrency("USD");
        requestBean.setTargetCurrency("EUR");
    }

    private void fillResponseBean() {
        responseBean = new CurrencyConverterServiceResponseBean();
        responseBean.setSourceCurrency("USD");
        responseBean.setTargetCurrency("EUR");
        responseBean.setAmount(new BigDecimal("4.4576574"));
    }

}