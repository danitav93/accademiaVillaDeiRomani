package com.nodelab.accademiaVillaDeiRomani.service;


import org.springframework.mail.MailException;

public interface MailService {

	public void sendSimpleMessage(String to, String subject, String text);
	

	void sendRegistrationMail(String to, String subject, String Matricola) throws MailException;	
}
