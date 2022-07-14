package com.challengemeli.challengemeli;

import com.challengemeli.challengemeli.ip.entity.IpInfoEntity;
import com.challengemeli.challengemeli.ip.models.BlackListResponse;
import com.challengemeli.challengemeli.ip.models.CountryResponse;
import com.challengemeli.challengemeli.ip.models.FixerResponse;
import com.challengemeli.challengemeli.ip.models.IpResponse;
import com.challengemeli.challengemeli.ip.repositories.IpInfoRepository;
import com.challengemeli.challengemeli.ip.services.ipInterface;
import com.challengemeli.challengemeli.ip.services.ipServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@SpringBootTest
class ChallengeMeliApplicationTests {

	@Autowired
	private ipInterface countryIpInterface;

	@Autowired
	private IpInfoRepository ipInfoRepository;

	@Test
	void contextLoads() {
	}
	@Test
	void validateIp() {

		boolean validate = countryIpInterface.validateIp("12.12.12.12");

		Assert.isTrue(validate,"ip validada");
	}
	@Test
	void testGetApiIPData(){
		String ip = "186.31.180.195";
		Optional<CountryResponse> optionalCountryResponse =  countryIpInterface.getCountryIpData(ip);
		Assert.isTrue(optionalCountryResponse.isPresent(),"Api exitosa");

	}

	@Test
	void testGetFixerData(){
		Optional<FixerResponse> optionalFixerResponse = countryIpInterface.getFixerData();
		Assert.isTrue(optionalFixerResponse.isPresent(), "Api Fixer exitosa");
	}

	@Test
	void testGetTRMByCurrencyCode(){
		String currencyCode = "COP";

		Optional<Double> optTRMValue = countryIpInterface.getTRMByCurrencyCode(currencyCode);

		Assert.isTrue(optTRMValue.isPresent(), "Error no se encuentra el valor para el TRM");

	}

	@Test
	void testGetApiCurrency(){
		String codeIso = "col";
		String currencyCodeCol = "COP";
		Optional<String> optionalCurrencyResponse = countryIpInterface.getCurrencyData(codeIso);
		Assert.isTrue(optionalCurrencyResponse.isPresent(),"Currency Present");
		Assert.isTrue(optionalCurrencyResponse.get().equals(currencyCodeCol), "Match currency");

	}

	@Test
	void testConexionDb(){

		String dateInDb = ipInfoRepository.dateInDb();
		Assert.isTrue( dateInDb != null && !dateInDb.isEmpty(), "conexion no exitosa");
	}

	@Test
	void testConsultBlackList(){

		BlackListResponse markedBlackList = countryIpInterface.cosultIpBlackList("186.84.88.225");

		Assert.isTrue(markedBlackList != null, "Error consultando blackList");

	}

	@Test
	void testConsulterEIInsert(){

		ResponseEntity<IpResponse> ipResponseResponseEntity = countryIpInterface.consultarIp("186.84.88.223");

		Assert.isTrue(ipResponseResponseEntity.getBody() != null, "consulta e insercion exitosa");

	}

	@Test
	void testValidateDate() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		String  fechaStr = "26/02/2022";
		Date date = sdf.parse(fechaStr);

		Boolean resp = countryIpInterface.validateDate(date);

		Assert.isTrue(resp != null,"Error Fecha");

	}

}
