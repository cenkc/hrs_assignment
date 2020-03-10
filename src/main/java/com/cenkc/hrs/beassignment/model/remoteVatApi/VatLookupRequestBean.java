package com.cenkc.hrs.beassignment.model.remoteVatApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * created by cenkc on 3/9/2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VatLookupRequestBean {

    @JsonProperty("VatCode")
    private String vatCode;
}
