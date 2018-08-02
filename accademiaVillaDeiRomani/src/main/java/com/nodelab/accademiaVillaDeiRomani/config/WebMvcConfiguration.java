package com.nodelab.accademiaVillaDeiRomani.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer ;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.nodelab.accademiaVillaDeiRomani.model.formatter.AttivitaDidatticaFormatter;
import com.nodelab.accademiaVillaDeiRomani.model.formatter.UtenteHasCorsoFormatter;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer  {

	@Autowired
	UtenteHasCorsoFormatter utenteHasCorsoFormatter;
	
	@Autowired
	AttivitaDidatticaFormatter attivitaDidatticaFormatter;
	
	//security
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	//multilanguage
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(localeChangeInterceptor());
	}
	
	@Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18n/messages");
        messageSource.setDefaultEncoding("ISO-8859-1");
        return messageSource;
    }
	
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource)
	{
	    return new JdbcTemplate(dataSource);
	}

	@Override
    public void addFormatters(FormatterRegistry formatterRegistry) {

        formatterRegistry.addFormatter(utenteHasCorsoFormatter);
        formatterRegistry.addFormatter(attivitaDidatticaFormatter);

    }

}
