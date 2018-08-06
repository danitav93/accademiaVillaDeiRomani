package com.nodelab.accademiaVillaDeiRomani;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class AccademiaVillaDeiRomaniApplication  {

	public static void main(String[] args) {
		
		System.setProperty("jasypt.encryptor.password", "");
		SpringApplication.run(AccademiaVillaDeiRomaniApplication.class, args);
		
		
		
		
		
	}
}


