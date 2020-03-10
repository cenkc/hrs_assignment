package com.cenkc.hrs.beassignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * created by cenkc on 3/9/2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CurrencyConverterDTO {
    private BigDecimal amount;
    private String srcCurr;
    private String trgCurr;
}
