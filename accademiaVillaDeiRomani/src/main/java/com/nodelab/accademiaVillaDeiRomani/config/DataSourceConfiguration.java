package com.nodelab.accademiaVillaDeiRomani.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.nodelab.accademiaVillaDeiRomani.service.ApplicationInfoService;

@Configuration
public class DataSourceConfiguration {

	@Autowired
	ApplicationInfoService applicationInfoService;
	
	@Bean
	@Primary
	public DataSource dataSource() {
		
	    return DataSourceBuilder
	        .create()
	        .username(applicationInfoService.getDbUsername())
	        .password(applicationInfoService.getDbPassword())
	        .url(applicationInfoService.getDbUrl())
	        .driverClassName(applicationInfoService.getDbDriver())
	        .build();
	}
	
	
	
}
