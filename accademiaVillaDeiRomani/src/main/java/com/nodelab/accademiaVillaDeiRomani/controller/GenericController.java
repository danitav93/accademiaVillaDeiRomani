package com.nodelab.accademiaVillaDeiRomani.controller;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nodelab.accademiaVillaDeiRomani.constant.Urls;

@RestController
public class GenericController {
 
    Logger logger = LoggerFactory.getLogger(GenericController.class);
    
    @Autowired
    ServletContext servletContext;
    
 
    @RequestMapping("/logTest")
    public String index() {
        logger.trace("A TRACE Message");
        logger.debug("A DEBUG Message");
        logger.info("An INFO Message");
        logger.warn("A WARN Message");
        logger.error("An ERROR Message");
 
        return "Howdy! Check out the Logs to see the output...";
    }
    
    @RequestMapping(value = {Urls.currentFlag}, method = RequestMethod.GET)
    public void getCurrentFlag(HttpServletResponse response) throws IOException {
    	
    	Locale locale = LocaleContextHolder.getLocale();
    	String pathImg="static/img/";
    	
    	if (locale.getLanguage().equals(Locale.ITALY.getLanguage())) {
    		pathImg=pathImg+"it.png";
    	} else if (locale.getLanguage().equals(Locale.CHINA.getLanguage())) {
    		pathImg=pathImg+"china.png";
    	} else {
    		pathImg=pathImg+"uk.png";
    	}
    	
    	
    	
    	
    	File file = new ClassPathResource(pathImg).getFile();
        InputStream in = new FileInputStream(file);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }
    
    
}
