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
        javaMailSender.setPort( Integer.parseInt(applicationInfoService.getEmailPort()));
        javaMailSender.setUsername(applicationInfoService.getEmailUsername());
     //   javaMailSender.setPassword(applicationInfoService.getEmailPassword());
        
        javaMailSender.setJavaMailProperties(getMailProperties());
        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.auth.mechanisms", "XOAUTH2");
        properties.setProperty("mail.debug", "false");

        return properties;
    }
}