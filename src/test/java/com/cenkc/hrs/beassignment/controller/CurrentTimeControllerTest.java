package com.cenkc.hrs.beassignment.controller;

/**
 * created by cenkc on 10.03.2020
 */

import com.cenkc.hrs.beassignment.model.CurrentTimeServiceResponseBean;
import com.cenkc.hrs.beassignment.service.time.CurrentTimeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CurrentTimeController.class)
public class CurrentTimeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CurrentTimeService currentTimeService;

    @Test
    public void getCurrentTime() throws Exception {

        String uri = "/api/time/current";
        ObjectMapper objectMapper = new ObjectMapper();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS zzz EEE");
        Date date = sdf.parse("2020-03-10 10:42:08.742 UTC Tue");
        CurrentTimeServiceResponseBean responseBean = new CurrentTimeServiceResponseBean(date);

        when(currentTimeService.getCurrentTime()).thenReturn(responseBean);

        MvcResult result = mvc.perform(
                get(uri)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        String responseBody = response.getContentAsString();

        //either will work
        assertEquals(objectMapper.writeValueAsString(responseBean), responseBody);
        assertThat(responseBody).isEqualTo(objectMapper.writeValueAsString(responseBean));

        verify(currentTimeService, times(1)).getCurrentTime();
    }
}