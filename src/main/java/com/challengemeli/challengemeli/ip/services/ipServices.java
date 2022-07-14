package com.challengemeli.challengemeli.ip.services;

import com.challengemeli.challengemeli.ip.entity.BlackListEntity;
import com.challengemeli.challengemeli.ip.entity.IpInfoEntity;
import com.challengemeli.challengemeli.ip.models.*;
import com.challengemeli.challengemeli.ip.models.currency.CurrencyData;
import com.challengemeli.challengemeli.ip.repositories.BlackListRepository;
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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class ipServices implements ipInterface{

    @Autowired
    private IpInfoRepository ipInfoRepository;

    @Autowired
    private BlackListRepository blackListRepository;
    @Autowired
    private RestTemplate restTemplate;
    private Logger LOG = LoggerFactory.getLogger(ipServices.class);
    private static final Pattern PATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    private final String URL_API_COUNTRY_CURRENCY = "https://restcountries.com/v3.1/alpha/";

    private final String ACCESSKEY_API = "?access_key=7dd01e18f27140259353176e5160de52";

    private final String URL_API_COUNTRY_IP = "http://api.ipapi.com/";

    private final String ACCESSKEY_FIXER = "f8d3fb0cf6095cf93388560effefaa27";
    private final String URL_API_COUNTRY_FIXER = "http://data.fixer.io/api/latest?access_key="+ACCESSKEY_FIXER;
    /**
     * metodo que valida apartir de un string si es una ip
     * esto con una expresion regular
     * @param ip
     * @return
     */
    public boolean validateIp(String ip){
            return PATTERN.matcher(ip).matches();
    }
    /**
     * metodo general consulta si se encuentra en lista negra la ip
     * consulta si la ip se encuentra en la tabla IP_INF
     * si la encuentra trae la informacion de la bd
     * si no consulta he inserta
     * @param ipConsultada
     * @return
     */
    @Override
    public ResponseEntity consultarIp(String ipConsultada) {

        boolean validateIp = validateIp(ipConsultada);

        if(!validateIp) {
            return new ResponseEntity<>(
                    new GenericResponse(HttpStatus.CONFLICT.name(),"IIP CONSULTADA NO VALIDA."), HttpStatus.CONFLICT);
        }

        Optional<BlackListEntity> optionalBlackListEntity = blackListRepository.findById(ipConsultada);

        if(optionalBlackListEntity.isPresent()){

            return new ResponseEntity<>(
                    new GenericResponse(HttpStatus.CONFLICT.name(),"IP se encuentra en lista negra."), HttpStatus.CONFLICT);

        }

        Optional<IpInfoEntity> optionalIpInfoEntity = ipInfoRepository.findById(ipConsultada);

        if (optionalIpInfoEntity.isPresent()) {
            //actualizar si la trm si el dia de consulta es diferente a el dia actual

            IpInfoEntity ipInfoEntity = optionalIpInfoEntity.get();

            boolean dateDBisToday = validateDate(ipInfoEntity.getTrmDate());

            if(!dateDBisToday){

                Optional<Double> optionalTrm = getTRMByCurrencyCode(ipInfoEntity.getLocalMoney());

                if(!optionalTrm.isPresent()){
                    return new ResponseEntity<>(
                            new GenericResponse(HttpStatus.CONFLICT.name(),"Error consultado TRM."), HttpStatus.CONFLICT);

                }

                ipInfoEntity.setTrm(optionalTrm.get());
                ipInfoEntity.setTrmDate(new Date());
                ipInfoRepository.save(ipInfoEntity);

            }

            Optional<IpResponse> optionalIPResponse = parseIPInfoToIPResponse(optionalIpInfoEntity.get());

            if(optionalIPResponse.isPresent()){
                return new ResponseEntity<>(optionalIPResponse.get(),HttpStatus.OK);
            }

        }
        Optional<CountryResponse> optionalCountryResponse = getCountryIpData(ipConsultada);

        if (!optionalCountryResponse.isPresent()){
            return new ResponseEntity<>(
                    new GenericResponse(HttpStatus.CONFLICT.name(),"No fue posible consultar la IP."), HttpStatus.CONFLICT);

        }


        IpInfoEntity ipInfoEntity = new IpInfoEntity();
        ipInfoEntity.setIp(ipConsultada);
/*
        optionalCountryResponse.ifPresent( countryResponse -> {
            ipInfoEntity.setCountryName(countryResponse.getCountryName());
        } );
*/

        CountryResponse countryResponse = optionalCountryResponse.get();
        ipInfoEntity.setCountryName(countryResponse.getCountryName());
        ipInfoEntity.setCodeIso(countryResponse.getCountryCode());

        Optional<String> optionalLocalMoney = getCurrencyData(countryResponse.getCountryCode());

        if(!optionalLocalMoney.isPresent()){

            return new ResponseEntity<>(
                    new GenericResponse(HttpStatus.CONFLICT.name(),"Error consultado moneda local."), HttpStatus.CONFLICT);

        }

        ipInfoEntity.setLocalMoney(optionalLocalMoney.get());

        Optional<Double> optionalTrm = getTRMByCurrencyCode(optionalLocalMoney.get());

        if(!optionalTrm.isPresent()){
            return new ResponseEntity<>(
                    new GenericResponse(HttpStatus.CONFLICT.name(),"Error consultado TRM."), HttpStatus.CONFLICT);

        }

        ipInfoEntity.setTrm(optionalTrm.get());

        ipInfoRepository.save(ipInfoEntity);

        Optional<IpResponse> optionalIPResponse = parseIPInfoToIPResponse(ipInfoEntity);

        if(optionalIPResponse.isPresent()){

            return new ResponseEntity<>(optionalIPResponse.get(),HttpStatus.OK);

        }

        return new ResponseEntity<>(
                new GenericResponse(HttpStatus.CONFLICT.name(),"No fue posible consultar la IP."), HttpStatus.CONFLICT);

    }
    /**
     * Consulta la ip para traer informacion del pais por ip
     * @param ip
     * @return respuesta del servicio
     */
    @Override
    public Optional<CountryResponse> getCountryIpData(String ip) {
        String uriWithIP = URL_API_COUNTRY_IP+ip+ACCESSKEY_API;
        ResponseEntity<CountryResponse> response = restTemplate.getForEntity(uriWithIP, CountryResponse.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null){
            return Optional.of(response.getBody());
        }

        return Optional.empty();

    }
    /**
     * trae el tipo de moneda de la url consumida
     * @param isoCountryCode
     * @return tipo Moneda
     */
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
    /**
     * se consulta la lista de todas las trm y se filtra por el codigo de pais
     * @param currencyCode
     * @return valor de la trm
     */
    @Override
    public Optional<Double> getTRMByCurrencyCode(String currencyCode) {

        if (currencyCode == null || currencyCode.isEmpty()){
            return Optional.empty();
        }

        Optional<FixerResponse> optionalFixerResponse = getFixerData();

        if (!optionalFixerResponse.isPresent()){
            return Optional.empty();
        }

        FixerResponse fixerResponse = optionalFixerResponse.get();

        Optional<Map.Entry<String, Double>> optCurrencyCode = fixerResponse
                .getRates()
                .entrySet()
                .stream()
                .filter( rate -> rate.getKey().equals(currencyCode) )
                .findFirst();

        if (!optCurrencyCode.isPresent()){
            return Optional.empty();
        }

        return Optional.of(optCurrencyCode.get().getValue());
    }

    /**
     * consulta el servicio para traer la trm del dia
     * @return la trm del dia
     */
    @Override
    public Optional<FixerResponse> getFixerData(){

        ResponseEntity<FixerResponse> response = restTemplate.getForEntity(URL_API_COUNTRY_FIXER, FixerResponse.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null){
            return Optional.of(response.getBody());
        }

        return Optional.empty();
    }
    /**
     * llena response que se va a devolver en el servicio
     * @param ipInfoEntity
     * @return reponse
     */

    @Override
    public Optional<IpResponse> parseIPInfoToIPResponse(IpInfoEntity ipInfoEntity){

        if(ipInfoEntity == null){
            return Optional.empty();
        }

        IpResponse ipResponse = new IpResponse();

        ipResponse.setNameCountry(ipInfoEntity.getCountryName());
        ipResponse.setCodeISO(ipInfoEntity.getCodeIso());
        ipResponse.setLocalMoney(ipInfoEntity.getLocalMoney());
        ipResponse.setTrm(ipInfoEntity.getTrm());

        return Optional.of(ipResponse);

    }
    /**
     * valida que la fecha en la bd de la trm sea diferente a la fecha de consulta
     * @param dateBd
     * @return true o false
     */
    @Override
    public Boolean validateDate(Date dateBd ){

        Date dateToday = new Date();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        String dtBd = formatter.format(dateBd);
        String dtHoy = formatter.format(dateToday);

        return dtBd.equals(dtHoy);
    }
    /**
     * consulta en la bd BLACK_LIST si existe el registro
     * si no existe lo inserta
     * @param ip
     * @return
     */
    @Override
    public BlackListResponse cosultIpBlackList(String ip) {

        BlackListResponse blackListResponse = new BlackListResponse();

        Optional<BlackListEntity> optionalBlackListEntity = blackListRepository.findById(ip);

        if(optionalBlackListEntity.isPresent()){

            blackListResponse.setStatus("YA SE ENCUENTRA MARCADO.");
            return blackListResponse;
        }

        BlackListEntity blackListEntity = new BlackListEntity();

        blackListEntity.setIp(ip);
        blackListEntity.setBanDate(new Date());

        blackListRepository.save(blackListEntity);

        blackListResponse.setStatus("MARCADO CON EXITO.");
        return blackListResponse;

    }



}
