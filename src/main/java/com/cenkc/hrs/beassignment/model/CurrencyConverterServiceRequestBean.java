package com.cenkc.hrs.beassignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * created by cenkc on 3/9/2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyConverterServiceRequestBean {

    @Positive
    private BigDecimal amount;

    @NotEmpty
    private String sourceCurrency;

    @NotEmpty
    private String targetCurrency;
}
