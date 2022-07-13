package com.challengemeli.challengemeli.ip.services;

import com.challengemeli.challengemeli.ip.models.CountryResponse;
import com.challengemeli.challengemeli.ip.models.IpResponse;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface ipInterface {

    boolean validateIp(String ip);

    ResponseEntity<IpResponse> consultarIp(String ipConsultada);

    Optional<CountryResponse> getCountryIpData(String ip);

    Optional<String> getCurrencyData(String codeIso);
}
