package com.nodelab.accademiaVillaDeiRomani.service;


import java.io.File;

import com.nodelab.accademiaVillaDeiRomani.model.Utente;

public interface MailService {

	public void sendSimpleMessage(String to, String subject, String text);
	

	void sendRegistrationMail(String to, String subject, String Matricola,String token) throws Exception;


	public void sendResetPasswordMail(String to, String subject,String token, Utente utente) throws Exception;


	public void sendBackup(File file) throws Exception;
	
	
}
