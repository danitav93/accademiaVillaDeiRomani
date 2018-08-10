package com.nodelab.accademiaVillaDeiRomani.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.nodelab.accademiaVillaDeiRomani.model.GoogleAccessToken;
import com.nodelab.accademiaVillaDeiRomani.repository.GoogleAccessTokenRepository;

@Service("oAuthMailServiceImpl")
public class OAuthMailServiceImpl implements OAuthMailService{

	@Autowired
	GoogleAccessTokenRepository googleAccessTokenRepository;
	
	@Autowired
	ApplicationInfoService applicationInfoService;
	
	@Override
	public String getMailAccessToken() {
	
		List<GoogleAccessToken> googleAccessTokenList= googleAccessTokenRepository.findAll();
		GoogleAccessToken googleAccessToken;
		if (googleAccessTokenList==null || googleAccessTokenList.isEmpty()) {
			googleAccessToken=new GoogleAccessToken();
			googleAccessToken.setExpire(0L);
		} else {
			googleAccessToken=googleAccessTokenList.get(0);
		}
		
		if (System.currentTimeMillis()>googleAccessToken.getExpire()) {
			
			
			try {
				GoogleTokenResponse response =
				        new GoogleRefreshTokenRequest(new NetHttpTransport(), new JacksonFactory(), applicationInfoService.getGoogleRefreshToken(), applicationInfoService.getGoogleClientId(), applicationInfoService.getGoogleClientSecret()).execute();
				googleAccessToken.setAccessToken(response.getAccessToken());
				googleAccessToken.setExpire(System.currentTimeMillis()+(((Number)response.getExpiresInSeconds()).intValue()*1000));
				
				googleAccessToken=googleAccessTokenRepository.save(googleAccessToken);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			
		}
		return googleAccessToken.getAccessToken();
	}

}
