package com.cenkc.hrs.beassignment.controller;

import com.cenkc.hrs.beassignment.model.VatValidatorServiceRequestBean;
import com.cenkc.hrs.beassignment.model.VatValidatorServiceResponseBean;
import com.cenkc.hrs.beassignment.service.vat.VatValidatorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * created by cenkc on 3/9/2020
 */
@RestController
@Api(value = "/api/vat", produces = "application/json", consumes = "application/json")
@RequestMapping("/api/vat")
public class VatLookupController {

    @Autowired
    private VatValidatorService vatValidatorService;

    @PostMapping("/lookup")
    @ApiOperation(value = "Returns country (code) for VAT number",
            notes = "Sample request body is : \n\n{\n" +
                    "    \t\"vatNumber\": \"LU20260743\"\n" +
                    "}\n\n\n" +
                    "Sample cURL command is (watch out for single quotes) : \n\n" +
                    "curl -X POST http://localhost:8081/api/vat/lookup " +
                    "-H 'Content-Type: application/json' " +
                    "-d '{\"vatNumber\": \"LU20260743\"}'",
            response = VatValidatorServiceResponseBean.class,
            consumes = "application/json")
    @ApiResponses(value =  {
            @ApiResponse(code = 200, message = "VAT Number lookup completed", response = VatValidatorServiceResponseBean.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<VatValidatorServiceResponseBean> vatLookup(@Valid @RequestBody VatValidatorServiceRequestBean requestBean) {
        VatValidatorServiceResponseBean result = vatValidatorService.vatLookup(requestBean);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
