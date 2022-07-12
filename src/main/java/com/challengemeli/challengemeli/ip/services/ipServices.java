package com.challengemeli.challengemeli.ip.services;

import com.challengemeli.challengemeli.ip.models.CountryResponse;
import com.challengemeli.challengemeli.ip.models.IpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class ipServices implements ipInterface{

    @Autowired
    private RestTemplate restTemplate;
    private Logger LOG = LoggerFactory.getLogger(ipServices.class);
    private static final Pattern PATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");



    private final String ACCESKEY_API = "?access_key="+"7dd01e18f27140259353176e5160de52";

    private final String URL_API_COUNTRY_IP = "http://api.ipapi.com/";
    public boolean validateIp(String ip){
            return PATTERN.matcher(ip).matches();
    }

    @Override
    public ResponseEntity<IpResponse> consultarIp(String ipConsultada) {
        return null;
    }

    @Override
    public Optional<CountryResponse> getCountryIpData(String ip) {
        ResponseEntity<CountryResponse> response = restTemplate.getForEntity(URL_API_COUNTRY_IP+ip+ACCESKEY_API, CountryResponse.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null){
            return Optional.of(response.getBody());
        }

        return Optional.empty();

    }


}
