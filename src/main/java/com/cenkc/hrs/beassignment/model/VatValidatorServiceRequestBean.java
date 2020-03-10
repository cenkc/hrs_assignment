package com.cenkc.hrs.beassignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * created by cenkc on 3/9/2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VatValidatorServiceRequestBean {

    @NotEmpty
    private String vatNumber;
}
