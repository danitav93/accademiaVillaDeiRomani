package com.nodelab.accademiaVillaDeiRomani.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nodelab.accademiaVillaDeiRomani.constant.Test;
import com.nodelab.accademiaVillaDeiRomani.constant.Urls;
import com.nodelab.accademiaVillaDeiRomani.constant.View;
import com.nodelab.accademiaVillaDeiRomani.model.Utente;
import com.nodelab.accademiaVillaDeiRomani.service.MailService;
import com.nodelab.accademiaVillaDeiRomani.service.MessageService;
import com.nodelab.accademiaVillaDeiRomani.service.UtenteService;

/**
 * controller che gestisce la registrazione
 * @author User
 *
 */
@Controller
public class RegistrationController {

	@Autowired
	private UtenteService utenteService;


	@Autowired
	private MailService mailService;

	@Autowired
	private MessageService messageService;

	/**
	 * Quando richiedo la pagina di registrazione
	 * @return
	 */
	@RequestMapping(value= {Urls.registrationPath}, method = RequestMethod.GET)
	public ModelAndView registration(){

		ModelAndView modelAndView = new ModelAndView();

		Utente utente = new Utente();

		modelAndView.addObject("utente", utente);

		modelAndView.setViewName(View.registrationView);

		return modelAndView;

	}

	/**
	 * Quando richiedo l attivazione di un utente tramite link email
	 * @return
	 */
	@RequestMapping(value= {Urls.abilitaUtentePath}, method = RequestMethod.GET)
	public ModelAndView abilitaUtente(@RequestParam(value = "matricola", required = true) String matricola){

		ModelAndView modelAndView = new ModelAndView();

		utenteService.abilitaUtenteByMatricola(matricola);

		modelAndView.addObject("activated", 1);

		modelAndView.setViewName(View.loginView);

		return modelAndView;

	}



	/**
	 * Quando provo ad effettuare la registrazione
	 * @param user
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = { Urls.submitRegistrationPath}, method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid Utente utente, BindingResult bindingResult, HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();

		if (Test.EMAIL_CHECK) {
			//controllo che non ci siano due utenti con la stessa mail
			Utente utenteExists = utenteService.findUtenteByEmail(utente.getEmail());

			if (utenteExists != null) {
				bindingResult
				.rejectValue("email", "error.user",
						"There is already a user registered with the email provided");
			}
		}


		//se c'è solo un errore so che è il ruolo (lo setto automaticamente dopo)
		if (bindingResult.hasErrors() && bindingResult.getAllErrors().size()>1) {

			modelAndView.setViewName(View.registrationView);

		} else {

			utente=utenteService.saveNewRegisteredUser(utente);
			//mandiamo una mail con il link di conferma
			mailService.sendRegistrationMail(utente.getEmail(), messageService.getMessage("subjectRegistrationMail") ,new Integer(utente.getMatricola()).toString());

			modelAndView.addObject("utente", utente);

			modelAndView.setViewName(View.registrationSucceded);

		}

		return modelAndView;

	}





}
