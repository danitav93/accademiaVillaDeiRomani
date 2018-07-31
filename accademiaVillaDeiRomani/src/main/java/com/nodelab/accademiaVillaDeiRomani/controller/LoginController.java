package com.nodelab.accademiaVillaDeiRomani.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.nodelab.accademiaVillaDeiRomani.constant.Urls;
import com.nodelab.accademiaVillaDeiRomani.constant.Ruoli;
import com.nodelab.accademiaVillaDeiRomani.constant.View;
import com.nodelab.accademiaVillaDeiRomani.model.Utente;
import com.nodelab.accademiaVillaDeiRomani.service.ContributoService;
import com.nodelab.accademiaVillaDeiRomani.service.UtenteService;



/*
 * QUESTA CLASSE IMPLEMENTA IL CONTROLLER RELATIVO ALLA VIEW DELLA LOGIN
 */
@Controller
public class LoginController {

	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	private ContributoService contributoService;
	
	
	/**
	 * Metodo che indirizza alla loginView
	 * @param model
	 * @return the name of the view to be shown
	 */
	@RequestMapping(value={Urls.loginPath}, method = RequestMethod.GET)
    public ModelAndView login( ) {
		
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName(View.loginView);

		return modelAndView;
        
    }
	
	
	/**
	 * Metodo che risponde quando un utente si logga, controllo se è un admin allora lo indirizzo alla admin view,
	 * altrimenti alla user view
	 * @param model
	 * @return
	 */
	@RequestMapping(value= {Urls.submitLoginPath}, method = RequestMethod.GET)
	public ModelAndView submitLogin(){

		ModelAndView modelAndView = new ModelAndView();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		//mi prendo l'utente loggato
		Utente utente = utenteService.findUtenteByMatricola(auth.getName());

		//lo aggiungo al model
		modelAndView.addObject("utente",utente);

		switch (utente.getRole().getName()) {

			case Ruoli.ruolo_amministratore: //se è un amministratore vado alla view dell'amministratore
				
				modelAndView.setViewName(View.adminView);
				break;

			case Ruoli.ruolo_studente: //altrimenti alla view dell'utente
				modelAndView.addObject("contributi",contributoService.getAllContributi());
				modelAndView.setViewName(View.studentView);
				break;

		}


		return modelAndView;

	}

    
    
    
}