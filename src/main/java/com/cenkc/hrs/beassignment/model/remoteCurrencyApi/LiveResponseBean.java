package com.cenkc.hrs.beassignment.model.remoteCurrencyApi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * created by cenkc on 3/9/2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LiveResponseBean extends CurrencyApiBaseResponseBean{
    private long timestamp;
    private String source;
    private Map<String, Double> quotes;
}
