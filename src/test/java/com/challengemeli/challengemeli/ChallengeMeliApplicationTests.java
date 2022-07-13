package com.challengemeli.challengemeli;

import com.challengemeli.challengemeli.ip.entity.IpInfoEntity;
import com.challengemeli.challengemeli.ip.models.CountryResponse;
import com.challengemeli.challengemeli.ip.models.IpResponse;
import com.challengemeli.challengemeli.ip.repositories.IpInfoRepository;
import com.challengemeli.challengemeli.ip.services.ipServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.util.Optional;

@SpringBootTest
class ChallengeMeliApplicationTests {


	@Autowired
	private ipServices ipServices;

	@Autowired
	private IpInfoRepository ipInfoRepository;
	@Test
	void validateIp() {

		boolean validate = ipServices.validateIp("12.12.12.12");

		Assert.isTrue(validate,"ip validada");
	}

	@Test
	void testConexionDb(){

		String dateInDb = ipInfoRepository.dateInDb();
		Assert.isTrue( dateInDb != null && !dateInDb.isEmpty(), "conexion no exitosa");
	}

	@Test
	void testResponseServiceApiCountry(){

		String ip = "186.31.180.195";
		Optional<CountryResponse> optionalCountryResponse = ipServices.getCountryIpData(ip);
		Assert.isTrue(optionalCountryResponse.isPresent(), "Api ip exitosa");

	}


	@Test
	void requestIp(){
		String ip = "186.31.180.195";
		ResponseEntity<IpResponse> ipResponseResponseEntity = ipServices.consultarIp(ip);

		Assert.isTrue(ipResponseResponseEntity.getBody() != null, "consulta e insercion exitosa");



	}

	@Test
	void consultarIpBD(){

		String ip = "186.84.88.223";

		Optional<IpInfoEntity> optionalIpInfoEntity =  ipInfoRepository.findById(ip);

		Assert.isTrue(optionalIpInfoEntity.isPresent(), "Consulta en bd");
	}

	@Test
	void testGetApiCurrency(){
		String codeIso = "col";
		String currencyCodeCol = "COP";
		Optional<String> optionalCurrencyResponse = ipServices.getCurrencyData(codeIso);
		Assert.isTrue(optionalCurrencyResponse.isPresent(),"Currency Present");
		Assert.isTrue(optionalCurrencyResponse.get().equals(currencyCodeCol), "Match currency");

	}

}
