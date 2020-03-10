package com.cenkc.hrs.beassignment.model;

import lombok.Data;

/**
 * created by cenkc on 3/9/2020
 */
@Data
public abstract class BaseServiceResponseBean {
    private ServiceResponseErrorBean error;
}
