package com.cenkc.hrs.beassignment.controller;

import com.cenkc.hrs.beassignment.model.VatValidatorServiceRequestBean;
import com.cenkc.hrs.beassignment.model.VatValidatorServiceResponseBean;
import com.cenkc.hrs.beassignment.service.vat.VatValidatorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * created by cenkc on 10.03.2020
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = VatLookupController.class)
class VatLookupControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private VatValidatorService vatValidatorService;

    @Test
    public void vatLookup() throws Exception {

        String uri = "/api/vat/lookup";

        VatValidatorServiceResponseBean response = new VatValidatorServiceResponseBean("LU");

        when(vatValidatorService.vatLookup(any(VatValidatorServiceRequestBean.class))).thenReturn(response);

        VatValidatorServiceRequestBean request = new VatValidatorServiceRequestBean("LU20260743");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJSON = objectMapper.writeValueAsString(request);

        mvc.perform(
                post(uri)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(vatValidatorService, times(1)).vatLookup(any(VatValidatorServiceRequestBean.class));
    }
}