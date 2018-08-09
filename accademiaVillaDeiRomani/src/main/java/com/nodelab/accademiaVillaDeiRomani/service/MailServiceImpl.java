package com.nodelab.accademiaVillaDeiRomani.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
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
	
	@Autowired 
	private OAuthMailServiceImpl oAuthMailService;
	
	@Autowired 
	private ApplicationInfoService applicationInfoService;
	
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
	public void sendRegistrationMail( String to, String subject, String matricola,String token) throws  Exception {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
	        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true,"utf-8");
	        messageHelper.setTo(to);
	        messageHelper.setSubject(subject);
	        String imageResourceName = logoImageResource.getFilename();
	        String content = mailContentBuilder.buildRegistrationTemplate(matricola,imageResourceName,token);
	        messageHelper.setText(content, true);
	        messageHelper.addInline(imageResourceName, logoImageResource);
	        
	       
	    };
	   
	    String accessToken=oAuthMailService.getMailAccessToken();
	    if (accessToken==null) {
	    	throw new Exception();
	    }
	    ((JavaMailSenderImpl)emailSender).setPassword(accessToken);
	    emailSender.send(messagePreparator);
	    
	}


	@Override
	public void sendResetPasswordMail(String to, String subject,String token, Utente utente) throws Exception  {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
	        
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true,"utf-8");
	        
			messageHelper.setTo(to);
	        
			messageHelper.setSubject(subject);
	        
			String imageResourceName = logoImageResource.getFilename();
	        String content = mailContentBuilder.buildResetPasswordTemplate(token,utente,imageResourceName);
	        messageHelper.setText(content, true);
	        messageHelper.addInline(imageResourceName, logoImageResource);
	       
	    };
	    String accessToken=oAuthMailService.getMailAccessToken();
	    if (accessToken==null) {
	    	throw new Exception();
	    }
	    ((JavaMailSenderImpl)emailSender).setPassword(accessToken);
	    emailSender.send(messagePreparator);
		
	}


	@Override
	public void sendBackup(File file) throws Exception {
MimeMessagePreparator messagePreparator = mimeMessage -> {
	        
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true,"utf-8");
	        
			messageHelper.setTo(applicationInfoService.getEmailUsername());
	        
			messageHelper.setSubject("BACK_UP");
			
			messageHelper.setText("");
	        
	        messageHelper.addAttachment(file.getName(), file);
	       
	    };
	    String accessToken=oAuthMailService.getMailAccessToken();
	    if (accessToken==null) {
	    	throw new Exception();
	    }
	    ((JavaMailSenderImpl)emailSender).setPassword(accessToken);
	    emailSender.send(messagePreparator);
		
	}
	
	
	

}
