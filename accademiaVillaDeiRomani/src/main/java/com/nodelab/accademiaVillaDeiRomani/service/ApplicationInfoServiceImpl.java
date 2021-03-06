package com.nodelab.accademiaVillaDeiRomani.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nodelab.accademiaVillaDeiRomani.constant.Application;

@Service("applicationInfoService")
public class ApplicationInfoServiceImpl implements ApplicationInfoService {
	

	
	@Autowired
	private EncryptedPropertyService encryptedPropertyService;
	
	
	@Override
	public String getDbUrl() {
		switch (encryptedPropertyService.getEncryptedPropery("ambiente")) {
		case Application.AMBIENTE_LOCALE:
			return encryptedPropertyService.getEncryptedPropery("db.url.locale");
		case Application.AMBIENTE_SVILUPPO:
			return encryptedPropertyService.getEncryptedPropery("db.url.sviluppo");
		case Application.AMBIENTE_PRODUZIONE:
			return encryptedPropertyService.getEncryptedPropery("db.url.produzione");
		}
		return null;
	}

	@Override
	public String getDbUsername() {
		switch (encryptedPropertyService.getEncryptedPropery("ambiente")) {
		case Application.AMBIENTE_LOCALE:
			return encryptedPropertyService.getEncryptedPropery("db.username.locale");
		case Application.AMBIENTE_SVILUPPO:
			return encryptedPropertyService.getEncryptedPropery("db.username.sviluppo");
		case Application.AMBIENTE_PRODUZIONE:
			return encryptedPropertyService.getEncryptedPropery("db.username.produzione");
		}
		return null;
	}

	@Override
	public String getDbPassword() {
		switch (encryptedPropertyService.getEncryptedPropery("ambiente")) {
		case Application.AMBIENTE_LOCALE:
			return encryptedPropertyService.getEncryptedPropery("db.password.locale");
		case Application.AMBIENTE_SVILUPPO:
			return encryptedPropertyService.getEncryptedPropery("db.password.sviluppo");
		case Application.AMBIENTE_PRODUZIONE:
			return encryptedPropertyService.getEncryptedPropery("db.password.produzione");
		}
		return null;
	}

	@Override
	public String getDbDriver() {
		return encryptedPropertyService.getEncryptedPropery("db.driver");
	}

	@Override
	public String getDbName() {
		switch (encryptedPropertyService.getEncryptedPropery("ambiente")) {
		case Application.AMBIENTE_LOCALE:
			return encryptedPropertyService.getEncryptedPropery("db.name.locale");
		case Application.AMBIENTE_SVILUPPO:
			return encryptedPropertyService.getEncryptedPropery("db.name.sviluppo");
		case Application.AMBIENTE_PRODUZIONE:
			return encryptedPropertyService.getEncryptedPropery("db.name.produzione");
		}
		return null;
	}

	@Override
	public String getEmailHost() {
		switch (encryptedPropertyService.getEncryptedPropery("ambiente")) {
		case Application.AMBIENTE_LOCALE:
			return encryptedPropertyService.getEncryptedPropery("mail.host.locale");
		case Application.AMBIENTE_SVILUPPO:
			return encryptedPropertyService.getEncryptedPropery("mail.host.sviluppo");
		case Application.AMBIENTE_PRODUZIONE:
			return encryptedPropertyService.getEncryptedPropery("mail.host.produzione");
		}
		return null;
	}

	@Override
	public String getEmailPort() {
		switch (encryptedPropertyService.getEncryptedPropery("ambiente")) {
		case Application.AMBIENTE_LOCALE:
			return encryptedPropertyService.getEncryptedPropery("mail.port.locale");
		case Application.AMBIENTE_SVILUPPO:
			return encryptedPropertyService.getEncryptedPropery("mail.port.sviluppo");
		case Application.AMBIENTE_PRODUZIONE:
			return encryptedPropertyService.getEncryptedPropery("mail.port.produzione");
		}
		return null;
	}

	@Override
	public String getEmailUsername() {
		switch (encryptedPropertyService.getEncryptedPropery("ambiente")) {
		case Application.AMBIENTE_LOCALE:
			return encryptedPropertyService.getEncryptedPropery("mail.username.locale");
		case Application.AMBIENTE_SVILUPPO:
			return encryptedPropertyService.getEncryptedPropery("mail.username.sviluppo");
		case Application.AMBIENTE_PRODUZIONE:
			return encryptedPropertyService.getEncryptedPropery("mail.username.produzione");
		}
		return null;
	}

	@Override
	public String getEmailPassword() {
		switch (encryptedPropertyService.getEncryptedPropery("ambiente")) {
		case Application.AMBIENTE_LOCALE:
			return encryptedPropertyService.getEncryptedPropery("mail.password.locale");
		case Application.AMBIENTE_SVILUPPO:
			return encryptedPropertyService.getEncryptedPropery("mail.password.sviluppo");
		case Application.AMBIENTE_PRODUZIONE:
			return encryptedPropertyService.getEncryptedPropery("mail.password.produzione");
		}
		return null;
	}

	@Override
	public String getDominio() {
		switch (encryptedPropertyService.getEncryptedPropery("ambiente")) {
		case Application.AMBIENTE_LOCALE:
			return encryptedPropertyService.getEncryptedPropery("dominio.locale");
		case Application.AMBIENTE_SVILUPPO:
			return encryptedPropertyService.getEncryptedPropery("dominio.sviluppo");
		case Application.AMBIENTE_PRODUZIONE:
			return encryptedPropertyService.getEncryptedPropery("dominio.produzione");
		}
		return null;
	}

	@Override
	public String getGoogleClientId() {
		switch (encryptedPropertyService.getEncryptedPropery("ambiente")) {
		case Application.AMBIENTE_LOCALE:
			return encryptedPropertyService.getEncryptedPropery("google.clientId.locale");
		case Application.AMBIENTE_SVILUPPO:
			return encryptedPropertyService.getEncryptedPropery("google.clientId.sviluppo");
		case Application.AMBIENTE_PRODUZIONE:
			return encryptedPropertyService.getEncryptedPropery("google.clientId.produzione");
		}
		return null;
	}

	@Override
	public String getGoogleClientSecret() {
		switch (encryptedPropertyService.getEncryptedPropery("ambiente")) {
		case Application.AMBIENTE_LOCALE:
			return encryptedPropertyService.getEncryptedPropery("google.clientSecret.locale");
		case Application.AMBIENTE_SVILUPPO:
			return encryptedPropertyService.getEncryptedPropery("google.clientSecret.sviluppo");
		case Application.AMBIENTE_PRODUZIONE:
			return encryptedPropertyService.getEncryptedPropery("google.clientSecret.produzione");
		}
		return null;
	}

	@Override
	public String getGoogleRefreshToken() {
		switch (encryptedPropertyService.getEncryptedPropery("ambiente")) {
		case Application.AMBIENTE_LOCALE:
			return encryptedPropertyService.getEncryptedPropery("google.refreshToken.locale");
		case Application.AMBIENTE_SVILUPPO:
			return encryptedPropertyService.getEncryptedPropery("google.refreshToken.sviluppo");
		case Application.AMBIENTE_PRODUZIONE:
			return encryptedPropertyService.getEncryptedPropery("google.refreshToken.produzione");
		}
		return null;
	}

	@Override
	public String getGoogleYTokenUrl() {
		return encryptedPropertyService.getEncryptedPropery("google.token.url");
	}

	@Override
	public String getAmazonEmailHost() {
		return encryptedPropertyService.getEncryptedPropery("amazon.smtp.host");
	}

	@Override
	public String getAmazonEmailPort() {
		return encryptedPropertyService.getEncryptedPropery("amazon.smtp.port");
	}

	@Override
	public String getAmazonEmailUsername() {
		return encryptedPropertyService.getEncryptedPropery("amazon.smtp.username");
	}

	@Override
	public String getAmazonEmailPassword() {
		return encryptedPropertyService.getEncryptedPropery("amazon.smtp.password");
	}




}
