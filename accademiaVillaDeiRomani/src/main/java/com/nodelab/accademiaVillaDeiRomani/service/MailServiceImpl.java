package com.nodelab.accademiaVillaDeiRomani.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.nodelab.accademiaVillaDeiRomani.model.Utente;

@Service("mailService")
public class MailServiceImpl implements MailService {

	@Autowired
    private JavaMailSender emailSender;
 
	@Autowired
	private MailContentBuilder mailContentBuilder;
	
	@Value(value = "classpath:static/img/logo_villa.png")
	private Resource logoImageResource;
	
    public void sendSimpleMessage(String to, String subject, String text) {
        
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        emailSender.send(message);
        
    }

    
	@Override
	public void sendRegistrationMail( String to, String subject, String matricola,String token) throws  MailException {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
	        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true,"utf-8");
	        messageHelper.setTo(to);
	        messageHelper.setSubject(subject);
	        String imageResourceName = logoImageResource.getFilename();
	        String content = mailContentBuilder.buildRegistrationTemplate(matricola,imageResourceName,token);
	        messageHelper.setText(content, true);
	        messageHelper.addInline(imageResourceName, logoImageResource);
	       
	    };
	    emailSender.send(messagePreparator);
	}


	@Override
	public void sendResetPasswordMail(String to, String subject,String token, Utente utente) throws  MailException {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
	        
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true,"utf-8");
	        
			messageHelper.setTo(to);
	        
			messageHelper.setSubject(subject);
	        
			String imageResourceName = logoImageResource.getFilename();
	        String content = mailContentBuilder.buildResetPasswordTemplate(token,utente,imageResourceName);
	        messageHelper.setText(content, true);
	        messageHelper.addInline(imageResourceName, logoImageResource);
	       
	    };
	    emailSender.send(messagePreparator);
		
	}
	
	
	

}
