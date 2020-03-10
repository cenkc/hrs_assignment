package com.cenkc.hrs.beassignment.controller;

import com.cenkc.hrs.beassignment.model.CurrentTimeServiceResponseBean;
import com.cenkc.hrs.beassignment.service.time.CurrentTimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by cenkc on 3/9/2020
 */
@RestController
@Api(value = "/api/time", produces = "application/json", consumes = "application/json")
@RequestMapping("/api/time")
public class CurrentTimeController {

    @Autowired
    private CurrentTimeService currentTimeService;

    @GetMapping("/current")
    @ApiOperation(value = "Returns current time as valid JSON",
            notes = "Sample cURL command is : \n\ncurl -X GET http://localhost:8081/api/time/current",
            response = CurrentTimeServiceResponseBean.class,
            consumes = "application/json")
    @ApiResponses(value =  {
            @ApiResponse(code = 200, message = "Current time returned", response = CurrentTimeServiceResponseBean.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<CurrentTimeServiceResponseBean> getCurrentTime() {
        CurrentTimeServiceResponseBean responseBean = currentTimeService.getCurrentTime();
        return new ResponseEntity<>(responseBean, HttpStatus.OK);
    }
}
