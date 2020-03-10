package com.cenkc.hrs.beassignment.controller;

import com.cenkc.hrs.beassignment.model.CurrencyConverterServiceRequestBean;
import com.cenkc.hrs.beassignment.model.CurrencyConverterServiceResponseBean;
import com.cenkc.hrs.beassignment.service.currency.ConverterLookupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

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

    @Test
    public void currencyConvert() throws Exception {
        String uri = "/api/currency/convert";

        CurrencyConverterServiceResponseBean response = new CurrencyConverterServiceResponseBean();
        response.setSourceCurrency("USD");
        response.setTargetCurrency("EUR");
        response.setAmount(new BigDecimal("4.4576574"));

        when(converterLookupService.convert(any(CurrencyConverterServiceRequestBean.class))).thenReturn(response);

        CurrencyConverterServiceRequestBean request = new CurrencyConverterServiceRequestBean();
        request.setAmount(new BigDecimal("5.11"));
        request.setSourceCurrency("USD");
        request.setTargetCurrency("EUR");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJSON = objectMapper.writeValueAsString(request);

        mvc.perform(
                post(uri)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(converterLookupService, times(1)).convert(any(CurrencyConverterServiceRequestBean.class));
    }



}