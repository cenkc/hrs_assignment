package com.cenkc.hrs.beassignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * created by cenkc on 3/9/2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponseErrorBean {

    private int code;
    private String info;
}
