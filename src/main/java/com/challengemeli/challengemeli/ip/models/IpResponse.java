package com.challengemeli.challengemeli.ip.models;

public class IpResponse {

    private String nameCountry;
    private String codeISO;
    private String localMoney;
    private double trm;

    public String getNameCountry() {
        return nameCountry;
    }

    public void setNameCountry(String nameCountry) {
        this.nameCountry = nameCountry;
    }

    public String getCodeISO() {
        return codeISO;
    }

    public void setCodeISO(String codeISO) {
        this.codeISO = codeISO;
    }

    public String getLocalMoney() {
        return localMoney;
    }

    public void setLocalMoney(String localMoney) {
        this.localMoney = localMoney;
    }

    public double getTrm() {
        return trm;
    }

    public void setTrm(double trm) {
        this.trm = trm;
    }
}
