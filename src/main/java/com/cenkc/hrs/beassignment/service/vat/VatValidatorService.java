package com.cenkc.hrs.beassignment.service.vat;

import com.cenkc.hrs.beassignment.model.VatValidatorServiceRequestBean;
import com.cenkc.hrs.beassignment.model.VatValidatorServiceResponseBean;

/**
 * created by cenkc on 3/9/2020
 */
public interface VatValidatorService {

    VatValidatorServiceResponseBean vatLookup(VatValidatorServiceRequestBean requestBean);
}
