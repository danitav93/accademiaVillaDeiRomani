package com.nodelab.accademiaVillaDeiRomani.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailContentBuilder {

	 private TemplateEngine templateEngine;
	 
	    @Autowired
	    public MailContentBuilder(TemplateEngine templateEngine) {
	        this.templateEngine = templateEngine;
	    }
	 
	    public String buildRegistrationTemplate(String message) {
	    
	        Context context = new Context();
	        context.setVariable("message", message);
	        return templateEngine.process("genericUser/registrationMail", context);
	    }
	 
	
	
}
