package com.challengemeli.challengemeli.ip.services;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ipServices implements ipInterface{

    private static final Pattern PATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");


    public boolean validateIp(String ip){
            return PATTERN.matcher(ip).matches();
    }


}
