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




}
