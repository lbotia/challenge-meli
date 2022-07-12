package com.challengemeli.challengemeli.ip.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryResponse {

    private String country_code;


    public String getCountryCode() { return country_code; }
    public void setCountryCode(String value) { this.country_code = value; }


}
