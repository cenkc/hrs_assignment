package com.cenkc.hrs.beassignment.service.time;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * created by cenkc on 10.03.2020
 */
@SpringBootTest
class CurrentTimeServiceImplTest {

    @Autowired
    private CurrentTimeServiceImpl currentTimeService;

    @Test
    void givenService_whenRequest_thenResponseNotNull() {
        assertNotNull(currentTimeService.getCurrentTime());
    }
}