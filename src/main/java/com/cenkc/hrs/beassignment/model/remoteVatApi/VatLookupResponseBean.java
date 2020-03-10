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
public class VatLookupResponseBean {

    @JsonProperty("CountryCode")
    public String countryCode;

    @JsonProperty("VatNumber")
    public String vatNumber;

    @JsonProperty("IsValid")
    public Boolean isValid;

    @JsonProperty("BusinessName")
    public String businessName;

    @JsonProperty("BusinessAddress")
    public String businessAddress;

    @JsonProperty("BusinessBuilding")
    public String businessBuilding;

    @JsonProperty("BusinessStreetNumber")
    public String businessStreetNumber;

    @JsonProperty("BusinessStreet")
    public String businessStreet;

    @JsonProperty("BusinessCity")
    public String businessCity;

    @JsonProperty("BusinessStateOrProvince")
    public String businessStateOrProvince;

    @JsonProperty("BusinessPostalCode")
    public String businessPostalCode;

    @JsonProperty("BusinessCountry")
    public String businessCountry;
}
