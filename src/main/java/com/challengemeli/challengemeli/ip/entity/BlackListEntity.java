package com.challengemeli.challengemeli.ip.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "BLACK_LIST")
public class BlackListEntity {
    @Id
    @Column(name = "ip")
    private String ip;

    @Column(name = "ban_date")
    private Date banDate;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getBanDate() {
        return banDate;
    }

    public void setBanDate(Date banDate) {
        this.banDate = banDate;
    }

    @Override
    public String toString() {
        return "BlackListEntity{" +
                "ip='" + ip + '\'' +
                ", banDate=" + banDate +
                '}';
    }
}

