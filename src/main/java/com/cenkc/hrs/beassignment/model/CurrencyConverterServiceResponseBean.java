package com.cenkc.hrs.beassignment.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * created by cenkc on 3/9/2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CurrencyConverterServiceResponseBean extends BaseServiceResponseBean {
    private BigDecimal amount;
    private String sourceCurrency;
    private String targetCurrency;
}
