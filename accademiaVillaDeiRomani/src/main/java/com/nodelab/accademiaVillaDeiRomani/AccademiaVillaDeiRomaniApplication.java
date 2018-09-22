package com.nodelab.accademiaVillaDeiRomani;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
/*@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class AccademiaVillaDeiRomaniApplication  {

	public static void main(String[] args) {
		
		
		SpringApplication.run(AccademiaVillaDeiRomaniApplication.class, args);
		
		ginoooooooooooooooooooooooooooooooooo		prova merge
		
	}
}*/


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class AccademiaVillaDeiRomaniApplication extends SpringBootServletInitializer {

  

    public static void main(String[] args) throws Exception {
    	
        SpringApplication.run(AccademiaVillaDeiRomaniApplication.class, args);
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    	
        return application.sources(AccademiaVillaDeiRomaniApplication.class);
    }

}
