package com.nodelab.accademiaVillaDeiRomani.service;

public interface ApplicationInfoService {

	public String getDbUrl();
	
	public String getDbUsername();
	
	public String getDbPassword();
	
	public String getDbDriver();

	public String getDbName();

	public String getEmailHost();

	public String getEmailPort();

	public String getEmailUsername();

	public String getEmailPassword();

	public String getDominio();

	public String getGoogleClientId();

	public String getGoogleClientSecret();

	public String getGoogleRefreshToken();

	public String getGoogleYTokenUrl();

	public String getAmazonEmailHost();

	public String getAmazonEmailPort();

	public String getAmazonEmailUsername();

	public String getAmazonEmailPassword();
	
}
