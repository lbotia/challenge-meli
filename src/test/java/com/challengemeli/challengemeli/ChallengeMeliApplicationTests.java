package com.challengemeli.challengemeli;

import com.challengemeli.challengemeli.ip.services.ipServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class ChallengeMeliApplicationTests {


	@Autowired
	private ipServices ipServices;


	@Test
	void validateIp() {

		boolean validate = ipServices.validateIp("12.12.12.12");

		Assert.isTrue(validate,"ip validada");
	}



}
