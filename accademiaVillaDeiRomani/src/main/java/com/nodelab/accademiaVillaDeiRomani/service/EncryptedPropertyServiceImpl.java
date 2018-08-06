package com.nodelab.accademiaVillaDeiRomani.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service("encryptedPropertyService")
public class EncryptedPropertyServiceImpl implements EncryptedPropertyService {

	@Autowired
	Environment environment;
	
	@Override
	public String getEncryptedPropery(String key) {
		return environment.getProperty(key);
	}
	
	
	
}
