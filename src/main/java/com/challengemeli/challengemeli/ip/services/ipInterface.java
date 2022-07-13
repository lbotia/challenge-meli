package com.challengemeli.challengemeli.ip.services;

import com.challengemeli.challengemeli.ip.entity.IpInfoEntity;
import com.challengemeli.challengemeli.ip.models.CountryResponse;
import com.challengemeli.challengemeli.ip.models.FixerResponse;
import com.challengemeli.challengemeli.ip.models.IpResponse;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.Optional;

public interface ipInterface {

    boolean validateIp(String ip);

    ResponseEntity<IpResponse> consultarIp(String ipConsultada);

    Optional<CountryResponse> getCountryIpData(String ip);

    Optional<String> getCurrencyData(String codeIso);

    Optional<Double> getTRMByCurrencyCode(String currencyCode);

    Optional<FixerResponse> getFixerData();

    Optional<IpResponse> parseIPInfoToIPResponse(IpInfoEntity ipInfoEntity);

    Boolean validateDate(Date dateBd );
}
