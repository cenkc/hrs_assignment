package com.cenkc.hrs.beassignment.model.remoteCurrencyApi;

import lombok.Data;

/**
 * created by cenkc on 3/9/2020
 */
@Data
public abstract class CurrencyApiBaseResponseBean {
    private boolean success;
    private String terms;
    private String privacy;
    private ErrorResponseBean error;
}
