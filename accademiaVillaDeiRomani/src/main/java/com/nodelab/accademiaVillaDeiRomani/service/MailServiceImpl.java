package com.nodelab.accademiaVillaDeiRomani.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service("mailService")
public class MailServiceImpl implements MailService {

	@Autowired
    private JavaMailSender emailSender;
 
	@Autowired
	private MailContentBuilder mailContentBuilder;
	
    public void sendSimpleMessage(String to, String subject, String text) {
        
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        emailSender.send(message);
        
    }

    
	@Override
	public void sendRegistrationMail( String to, String subject, String matricola) throws  MailException {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
	        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
	        //messageHelper.setFrom("sample@dolszewski.com");
	        messageHelper.setTo(to);
	        messageHelper.setSubject(subject);
	        String content = mailContentBuilder.buildRegistrationTemplate(matricola);
	        messageHelper.setText(content, true);
	    };
	    
	    emailSender.send(messagePreparator);
	    
		
	}

}
