package com.nodelab.accademiaVillaDeiRomani.service;


import org.springframework.mail.MailException;

import com.nodelab.accademiaVillaDeiRomani.model.Utente;

public interface MailService {

	public void sendSimpleMessage(String to, String subject, String text);
	

	void sendRegistrationMail(String to, String subject, String Matricola,String token) throws MailException;


	public void sendResetPasswordMail(String to, String subject,String token, Utente utente);
	
	
}
