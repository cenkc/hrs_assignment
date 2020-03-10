package com.cenkc.hrs.beassignment.service.currency;

import com.cenkc.hrs.beassignment.model.CurrencyConverterServiceResponseBean;
import com.cenkc.hrs.beassignment.model.CurrencyConverterServiceRequestBean;

/**
 * created by cenkc on 3/9/2020
 */
public interface ConverterLookupService {

    CurrencyConverterServiceResponseBean convert(CurrencyConverterServiceRequestBean converterBean);
}
