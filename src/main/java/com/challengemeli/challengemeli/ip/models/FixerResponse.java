package com.challengemeli.challengemeli.ip.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FixerResponse {

    private Map<String, Double> rates;




    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }



    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
