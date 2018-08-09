package com.nodelab.accademiaVillaDeiRomani.utility;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;

public class GMailOAuth {

	static Logger logger = LoggerFactory.getLogger(EncryptPreperties.class);

	
	public static void main(String[] args) {
		
		
		String url= new GoogleAuthorizationCodeRequestUrl("CLIENT-ID", "http://localhost:8080/", Collections.singleton("https://mail.google.com/")).setAccessType("offline").build();
		
		logger.info(url);

	}

}
