package com.nodelab.accademiaVillaDeiRomani.service;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

@Service("messageService")
public class MessageServiceImpl implements MessageService {
	 
	private final MessageSourceAccessor accessor;



	    public MessageServiceImpl(MessageSource messageSource) {

	        this.accessor = new MessageSourceAccessor(messageSource, LocaleContextHolder.getLocale());

	    }




	@Override
	public String getMessage(String key) {
		
		return accessor.getMessage(key);
	}




	@Override
	public String getMessageByLocale(String string,Locale locale) {
		return accessor.getMessage(string, locale);
	}

}
