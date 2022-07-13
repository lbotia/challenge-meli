package com.challengemeli.challengemeli.ip.models;

public class BlackListResponse {
    private  String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BlackListResponse{" +
                "status='" + status + '\'' +
                '}';
    }
}
