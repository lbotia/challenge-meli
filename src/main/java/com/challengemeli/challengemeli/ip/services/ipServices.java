package com.challengemeli.challengemeli.ip.services;

import com.challengemeli.challengemeli.ip.entity.IpInfoEntity;
import com.challengemeli.challengemeli.ip.models.CountryResponse;
import com.challengemeli.challengemeli.ip.models.GenericResponse;
import com.challengemeli.challengemeli.ip.models.IpResponse;
import com.challengemeli.challengemeli.ip.models.currency.CurrencyData;
import com.challengemeli.challengemeli.ip.repositories.IpInfoRepository;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class ipServices implements ipInterface{

    @Autowired
    private IpInfoRepository ipInfoRepository;
    @Autowired
    private RestTemplate restTemplate;
    private Logger LOG = LoggerFactory.getLogger(ipServices.class);
    private static final Pattern PATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    private final String URL_API_COUNTRY_CURRENCY = "https://restcountries.com/v3.1/alpha/";

    private final String ACCESSKEY_API = "?access_key=7dd01e18f27140259353176e5160de52";

    private final String URL_API_COUNTRY_IP = "http://api.ipapi.com/";
    public boolean validateIp(String ip){
            return PATTERN.matcher(ip).matches();
    }

    @Override
    public ResponseEntity consultarIp(String ipConsultada) {


        Optional<IpInfoEntity> optionalIpInfoEntity = ipInfoRepository.findById(ipConsultada);

        if (optionalIpInfoEntity.isPresent()) {
            return new ResponseEntity<>(
                    new GenericResponse(HttpStatus.CONFLICT.name(),"ip ya existe en la bd."), HttpStatus.CONFLICT);


        }
            Optional<CountryResponse> optionalCountryResponse = getCountryIpData(ipConsultada);

        if (!optionalCountryResponse.isPresent()){
            return new ResponseEntity<>(
                    new GenericResponse(HttpStatus.CONFLICT.name(),"No fue posible consultar la IP."), HttpStatus.CONFLICT);

        }

        IpInfoEntity ipInfoEntity = new IpInfoEntity();
        ipInfoEntity.setIp(ipConsultada);

        CountryResponse countryResponse = optionalCountryResponse.get();
        ipInfoEntity.setCountryName(countryResponse.getCountryName());
        ipInfoEntity.setCodeIso(countryResponse.getCountryCode());

        ipInfoRepository.save(ipInfoEntity);

        return null;
    }

    @Override
    public Optional<CountryResponse> getCountryIpData(String ip) {
        String uriWithIP = URL_API_COUNTRY_IP+ip+ACCESSKEY_API;
        ResponseEntity<CountryResponse> response = restTemplate.getForEntity(uriWithIP, CountryResponse.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null){
            return Optional.of(response.getBody());
        }

        return Optional.empty();

    }
    @Override
    public Optional<String> getCurrencyData(String isoCountryCode){

        String uri = URL_API_COUNTRY_CURRENCY + isoCountryCode;
        ResponseEntity<CurrencyData[]> response = restTemplate.getForEntity(uri,CurrencyData[].class );

        List<CurrencyData> currencyDataList = Arrays.asList(response.getBody());


        Optional<CurrencyData> optResponse = currencyDataList.stream().findFirst();

        if (!optResponse.isPresent()){
            return Optional.empty();
        }

        String currenciesJson = new Gson().toJson(optResponse.get().getCurrencies());
        LOG.info("json response ->  {} ", currenciesJson);

        Type mapTokenType = new TypeToken<Map<String, Map>>(){}.getType();

        Map<String,String[]> currencyMap = new Gson().fromJson(currenciesJson,  mapTokenType);

        return currencyMap.keySet().stream().findFirst();

    }

}
