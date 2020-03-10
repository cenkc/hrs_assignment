package com.cenkc.hrs.beassignment.controller;

import com.cenkc.hrs.beassignment.model.CurrencyConverterServiceResponseBean;
import com.cenkc.hrs.beassignment.model.CurrencyConverterServiceRequestBean;
import com.cenkc.hrs.beassignment.service.currency.ConverterLookupService;
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
@Api(value = "/api/currency", produces = "application/json", consumes = "application/json")
@RequestMapping("/api/currency")
public class CurrencyConverterController {

    @Autowired
    private ConverterLookupService converterLookupService;

    @PostMapping("/convert")
    @ApiOperation(value = "Returns amount in target-currency",
            notes = "Sample request body is : \n\n{\n" +
                    "    \t\"sourceCurrency\": \"USD\",\n" +
                    "    \t\"targetCurrency\": \"EUR\",\n" +
                    "    \t\"amount\": 5\n" +
                    "}\n\n\n" +
                    "Sample cURL command is (watch out for single quotes) : \n\n" +
                    "curl -X POST http://localhost:8081/api/currency/convert " +
                    "-H 'Content-Type: application/json' " +
                    "-d '{\"sourceCurrency\": \"USD\", \"targetCurrency\": \"EUR\", \"amount\": 5 }'",
            response = CurrencyConverterServiceResponseBean.class,
            consumes = "application/json")
    @ApiResponses(value =  {
            @ApiResponse(code = 200, message = "Currency conversion completed", response = CurrencyConverterServiceResponseBean.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<CurrencyConverterServiceResponseBean> currencyConvert(@Valid @RequestBody CurrencyConverterServiceRequestBean converterBean) {
        final CurrencyConverterServiceResponseBean result = converterLookupService.convert(converterBean);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
