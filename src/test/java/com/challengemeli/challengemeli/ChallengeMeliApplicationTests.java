package com.challengemeli.challengemeli;

import com.challengemeli.challengemeli.ip.entity.IpInfoEntity;
import com.challengemeli.challengemeli.ip.models.CountryResponse;
import com.challengemeli.challengemeli.ip.repositories.IpInfoRepository;
import com.challengemeli.challengemeli.ip.services.ipServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
		Optional<CountryResponse> optionalFixerResponse = ipServices.getCountryIpData(ip);
		Assert.isTrue(optionalFixerResponse.isPresent(), "Api Fixer exitosa");

	}


}
