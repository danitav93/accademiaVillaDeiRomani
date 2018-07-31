package com.nodelab.accademiaVillaDeiRomani.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.nodelab.accademiaVillaDeiRomani.constant.Urls;
import com.nodelab.accademiaVillaDeiRomani.constant.Ruoli;
import com.nodelab.accademiaVillaDeiRomani.constant.View;
import com.nodelab.accademiaVillaDeiRomani.model.Utente;
import com.nodelab.accademiaVillaDeiRomani.service.AttivitaDidatticaService;
import com.nodelab.accademiaVillaDeiRomani.service.ContributoService;
import com.nodelab.accademiaVillaDeiRomani.service.CorsoService;
import com.nodelab.accademiaVillaDeiRomani.service.RoleService;
import com.nodelab.accademiaVillaDeiRomani.service.UtenteService;

/**
 * Questo controller gestisce la view di update di un utente
 * @author User
 *
 */
@Controller
public class UpdateStudenteController {



	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	CorsoService corsoService;
	
	@Autowired
	AttivitaDidatticaService attivitaDidatticaService;
	
	@Autowired
	ContributoService contributoService;

	

	/**
	 * Quando provo ad aggiornare un utente
	 * @param user
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = { Urls.submitEditStudentePath}, method = RequestMethod.POST)
	public ModelAndView updateUser(@Valid Utente utente, BindingResult bindingResult) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		//mi prendo l'utente loggato
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utente utenteLoggato = utenteService.findUtenteByMatricola(auth.getName());
		
		
		//controllo che se ho cambiato username od email queste non coincidano con alcune già esistenti
		Utente utenteExists = utenteService.findUtenteByMatricola(utente.getMatricola());
		if (utenteExists != null && utenteExists.getIdUtente()!=utente.getIdUtente()) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		
		//controllo che non ci siano due utenti con lo stesso username
		utenteExists = utenteService.findUtenteByMatricola(utente.getMatricola());

		if (utenteExists != null && utenteExists.getIdUtente()!=utente.getIdUtente()) {
			bindingResult
					.rejectValue("username", "error.user",
							"There is already a user registered with the username provided");
		}

		//se c'è solo un errore allora è la password e quindi posso andare avanti
		if (bindingResult.hasErrors() && bindingResult.getAllErrors().size()>1) {
			
			modelAndView.addObject("ruoli", roleService.getListOfRoles());
			
			modelAndView.addObject("attivitaDidattiche",attivitaDidatticaService.getListOfAttivitaDidattiche());
			
			modelAndView.addObject("utenteHasCorsiList",corsoService.getListOfUtenteHasCorso(utente));
			
			modelAndView.addObject("contributi",contributoService.getAllContributi());
			
			modelAndView.setViewName(View.studentViewEdit);
			
		} else {

			utenteService.update(utente);

			switch (utente.getRole().getName()) {
        	
			//se ho aggiornato uno studente vado a vedere la sua pagina
	        case Ruoli.ruolo_studente:
	        	modelAndView.addObject("utente", utente);
	        	modelAndView.setViewName(View.studentView);
	        	break;
	        	
	        //di default ritorno alla pagina di amministratore
	        default: 
	        	modelAndView.addObject("utente", utenteLoggato);
	        	modelAndView.setViewName(View.adminView);
	        }
	        
			

		}

		return modelAndView;

	}
	

}
