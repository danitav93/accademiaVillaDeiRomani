package com.nodelab.accademiaVillaDeiRomani.utility;

import org.jasypt.util.text.BasicTextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptPreperties {
	
	static Logger logger = LoggerFactory.getLogger(EncryptPreperties.class);
	
	public static void main(String[] args) {
		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		
		String privateData = "";
		
		textEncryptor.setPasswordCharArray("".toCharArray());
		
		String myEncryptedText = textEncryptor.encrypt(privateData);
		
		logger.info(myEncryptedText);
		
		String plainText = textEncryptor.decrypt(myEncryptedText);
		
		logger.info(plainText);
		
		
	}
	
}
