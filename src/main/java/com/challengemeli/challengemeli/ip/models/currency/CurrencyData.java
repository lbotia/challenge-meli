package com.challengemeli.challengemeli.ip.models.currency;

import com.google.gson.Gson;

public class CurrencyData {

    private Object currencies;
    private String cioc;

    public String getCioc() {
        return cioc;
    }

    public void setCioc(String cioc) {
        this.cioc = cioc;
    }

    public Object getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Object currencies) {
        this.currencies = currencies;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
