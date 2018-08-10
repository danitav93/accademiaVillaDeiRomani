package com.nodelab.accademiaVillaDeiRomani.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.nodelab.accademiaVillaDeiRomani.service.ApplicationInfoService;

@Configuration 
public class MailConfiguration {

  @Autowired
  ApplicationInfoService applicationInfoService;

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(applicationInfoService.getEmailHost());
        //javaMailSender.setHost(applicationInfoService.getAmazonEmailHost());
        javaMailSender.setPort( Integer.parseInt(applicationInfoService.getEmailPort()));
        //javaMailSender.setPort( Integer.parseInt(applicationInfoService.getAmazonEmailPort()));
        javaMailSender.setUsername(applicationInfoService.getEmailUsername());
        //javaMailSender.setUsername(applicationInfoService.getAmazonEmailUsername());
        javaMailSender.setPassword(applicationInfoService.getEmailPassword());
        //javaMailSender.setPassword(applicationInfoService.getAmazonEmailPassword());
        
        
        javaMailSender.setJavaMailProperties(getMailProperties());
        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        //properties.setProperty("mail.smtp.auth.mechanisms", "XOAUTH2");
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.debug", "false");
        properties.put("mail.smtps.ssl.checkserveridentity", "true");
        properties.put("mail.smtps.ssl.trust", "*");
        return properties;
    }
}