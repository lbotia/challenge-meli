package com.challengemeli.challengemeli.ip.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "IP_INFO")
public class IpInfoEntity {
    @Id
    @Column(name = "ip")
    private String ip;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "code_iso")
    private String codeIso;

    @Column(name = "local_money")
    private String localMoney;

    @Column(name = "trm")
    private Double trm;

    @Column(name = "trm_date")
    private Date trmDate;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCodeIso() {
        return codeIso;
    }

    public void setCodeIso(String codeIso) {
        this.codeIso = codeIso;
    }

    public String getLocalMoney() {
        return localMoney;
    }

    public void setLocalMoney(String localMoney) {
        this.localMoney = localMoney;
    }

    public Double getTrm() {
        return trm;
    }

    public void setTrm(Double trm) {
        this.trm = trm;
    }

    public Date getTrmDate() {
        return trmDate;
    }

    public void setTrmDate(Date trmDate) {
        this.trmDate = trmDate;
    }

    @Override
    public String toString() {
        return "IpInfoEntity{" +
                "ip='" + ip + '\'' +
                ", countryName='" + countryName + '\'' +
                ", codeIso='" + codeIso + '\'' +
                ", localMoney='" + localMoney + '\'' +
                ", trm=" + trm +
                ", trmDate=" + trmDate +
                '}';
    }
}

