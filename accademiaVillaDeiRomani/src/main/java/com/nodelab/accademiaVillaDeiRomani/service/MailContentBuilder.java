package com.nodelab.accademiaVillaDeiRomani.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.nodelab.accademiaVillaDeiRomani.constant.Urls;
import com.nodelab.accademiaVillaDeiRomani.model.Utente;

@Service
public class MailContentBuilder {

	@Autowired
	MessageService messageService;
	
	@Autowired
	ApplicationInfoService applicationInfoService;
	
	 private TemplateEngine templateEngine;
	 
	    @Autowired
	    public MailContentBuilder(TemplateEngine templateEngine) {
	        this.templateEngine = templateEngine;
	    }
	 
	    public String buildRegistrationTemplate(String matricola, String imageResourceName,String token) {
	    
	        Context context = new Context();
	        context.setVariable("matricola", matricola);
	        context.setVariable("link",applicationInfoService.getDominio()+Urls.abilita+"?token="+token);
	        context.setVariable("benvenuto", messageService.getMessage("benvenuto"));
	        context.setVariable("messaggioMail1", messageService.getMessage("messaggioRegistrationMail1"));
	        context.setVariable("messaggioMail2", messageService.getMessage("messaggioRegistrationMail12"));
	        context.setVariable("messaggioMail3",messageService.getMessage("messaggioRegistrationMail13"));
	        context.setVariable("messaggioMail4",messageService.getMessage("messaggioRegistrationMail14"));

	        context.setVariable("registrazioneAvvenutaConSuccesso",messageService.getMessage("registrazioneAvvenutaConSuccesso"));
	        context.setVariable("imageResourceName",imageResourceName);
	        return templateEngine.process("genericUser/registrationMail", context);
	    }

		public String buildResetPasswordTemplate(String token, Utente utente,String imageResourceName) {
			Context context = new Context();
	        context.setVariable("link",applicationInfoService.getDominio()+Urls.resetPasswordMailConfirmation+"?idUtente="+utente.getIdUtente()+"&token="+token);
	        context.setVariable("messaggioMail1", messageService.getMessage("messaggioResetPasswordMail1"));
	        context.setVariable("messaggioMail2",messageService.getMessage("messaggioResetPasswordMail2"));
	        context.setVariable("messaggioMail4",messageService.getMessage("messaggioRegistrationMail14"));

	        context.setVariable("imageResourceName",imageResourceName);
	        return templateEngine.process("genericUser/resetPasswordMail", context);
		}
	 
	
	
}
