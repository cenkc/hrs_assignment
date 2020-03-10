package com.cenkc.hrs.beassignment.controller;

import com.cenkc.hrs.beassignment.model.VatValidatorServiceRequestBean;
import com.cenkc.hrs.beassignment.model.VatValidatorServiceResponseBean;
import com.cenkc.hrs.beassignment.service.vat.VatValidatorService;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    private String uri;
    private ObjectMapper objectMapper;
    private VatValidatorServiceResponseBean responseBean;
    private VatValidatorServiceRequestBean requestBean;

    @BeforeEach
    void setUp() {
        uri = "/api/vat/lookup";
        objectMapper = new ObjectMapper();
        responseBean = new VatValidatorServiceResponseBean("LU");
        requestBean = new VatValidatorServiceRequestBean("LU20260743");
    }

    @Test
    public void vatLookup() throws Exception {

        when(vatValidatorService.vatLookup(any(VatValidatorServiceRequestBean.class))).thenReturn(responseBean);

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

        verify(vatValidatorService, times(1)).vatLookup(any(VatValidatorServiceRequestBean.class));
    }
}