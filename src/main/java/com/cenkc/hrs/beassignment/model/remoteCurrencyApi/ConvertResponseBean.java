package com.cenkc.hrs.beassignment.model.remoteCurrencyApi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * created by cenkc on 3/9/2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConvertResponseBean extends CurrencyApiBaseResponseBean {
    private ConvertQueryBean query;
    private ConvertInfoBean info;
    private Double result;
}
