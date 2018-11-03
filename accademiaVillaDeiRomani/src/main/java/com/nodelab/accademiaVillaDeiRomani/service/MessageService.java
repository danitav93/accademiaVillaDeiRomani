package com.nodelab.accademiaVillaDeiRomani.service;

import java.util.Locale;

public interface MessageService {
	public String getMessage(String key);

	public String getMessageByLocale(String string, Locale locale);
}
