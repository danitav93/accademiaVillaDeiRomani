package com.nodelab.accademiaVillaDeiRomani;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
/*@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class AccademiaVillaDeiRomaniApplication  {

	public static void main(String[] args) {
		
		System.setProperty("jasypt.encryptor.password", "Guit.1993!");
		SpringApplication.run(AccademiaVillaDeiRomaniApplication.class, args);
		
		
		vcbvcbcvbcvbcv pino
		
		
	}
}*/


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class AccademiaVillaDeiRomaniApplication extends SpringBootServletInitializer {

  

    public static void main(String[] args) throws Exception {
    	System.setProperty("jasypt.encryptor.password", "Guit.1993!");
        SpringApplication.run(AccademiaVillaDeiRomaniApplication.class, args);
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    	System.setProperty("jasypt.encryptor.password", "Guit.1993!");
        return application.sources(AccademiaVillaDeiRomaniApplication.class);
    }

}
