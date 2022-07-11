package com.challengemeli.challengemeli;

import com.challengemeli.challengemeli.ip.entity.IpInfoEntity;
import com.challengemeli.challengemeli.ip.repositories.IpInfoRepository;
import com.challengemeli.challengemeli.ip.services.ipServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

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



}
