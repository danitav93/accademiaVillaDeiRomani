package com.nodelab.accademiaVillaDeiRomani.service;


import java.util.Locale;

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
	 
	    public String buildRegistrationTemplate(String matricola, String imageResourceName,String token, Locale locale) {
	    
	        Context context = new Context();
	        context.setVariable("matricola", matricola);
	        context.setVariable("link",applicationInfoService.getDominio()+Urls.abilita+"?token="+token);
	        context.setVariable("benvenuto", messageService.getMessage("benvenuto"));
	        context.setVariable("messaggioMail1", messageService.getMessageByLocale("messaggioRegistrationMail1",locale));
	        context.setVariable("messaggioMail2", messageService.getMessageByLocale("messaggioRegistrationMail12",locale));
	        context.setVariable("messaggioMail3",messageService.getMessageByLocale("messaggioRegistrationMail13",locale));
	        context.setVariable("messaggioMail4",messageService.getMessageByLocale("messaggioRegistrationMail14",locale));

	        context.setVariable("registrazioneAvvenutaConSuccesso",messageService.getMessageByLocale("registrazioneAvvenutaConSuccesso",locale));
	        context.setVariable("imageResourceName",imageResourceName);
	        return templateEngine.process("genericUser/registrationMail", context);
	    }

		public String buildResetPasswordTemplate(String token, Utente utente,String imageResourceName, Locale locale) {
			Context context = new Context();
	        context.setVariable("link",applicationInfoService.getDominio()+Urls.resetPasswordMailConfirmation+"?idUtente="+utente.getIdUtente()+"&token="+token);
	        context.setVariable("messaggioMail1", messageService.getMessageByLocale("messaggioResetPasswordMail1",locale));
	        context.setVariable("messaggioMail2",messageService.getMessageByLocale("messaggioResetPasswordMail2",locale));
	        context.setVariable("messaggioMail4",messageService.getMessageByLocale("messaggioRegistrationMail14",locale));

	        context.setVariable("imageResourceName",imageResourceName);
	        return templateEngine.process("genericUser/resetPasswordMail", context);
		}
	 
	
	
}
