package com.cenkc.hrs.beassignment.model.remoteCurrencyApi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * created by cenkc on 3/9/2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConvertQueryBean {
    private String from;
    private String to;
    private Double amount;
}
