package com.nodelab.accademiaVillaDeiRomani.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nodelab.accademiaVillaDeiRomani.constant.Urls;
import com.nodelab.accademiaVillaDeiRomani.constant.View;
import com.nodelab.accademiaVillaDeiRomani.model.Utente;
import com.nodelab.accademiaVillaDeiRomani.service.AttivitaDidatticaService;
import com.nodelab.accademiaVillaDeiRomani.service.ContributoService;
import com.nodelab.accademiaVillaDeiRomani.service.CorsoService;
import com.nodelab.accademiaVillaDeiRomani.service.RoleService;
import com.nodelab.accademiaVillaDeiRomani.service.UtenteService;

/**
 * controller che gestisce la view dello studente
 * @author User
 *
 */
@Controller
public class StudentController {
	
	@Autowired
	UtenteService utenteService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	CorsoService corsoService;
	
	@Autowired
	AttivitaDidatticaService attivitaDidatticaService;
	
	@Autowired
	ContributoService contributoService;
	
	/**
	 * Quando si vuole editare la pagina di uno studente
	 * @return
	 */
	@RequestMapping(value= {Urls.editStudentPath}, method = RequestMethod.GET)
	public ModelAndView openEditStudenteView(@RequestParam(value = "matricola", required = true) String matricola){
		
		ModelAndView modelAndView = new ModelAndView();
		
		Utente utente = utenteService.findUtenteByMatricola(matricola);
		
		modelAndView.addObject("utente", utente );
		
		modelAndView.addObject("ruoli", roleService.getListOfRoles());
		
		modelAndView.addObject("attivitaDidattiche",attivitaDidatticaService.getListOfAttivitaDidattiche());
		
		modelAndView.addObject("utenteHasCorsiList",corsoService.getListOfUtenteHasCorso(utente));
		
		modelAndView.addObject("contributi",contributoService.getAllContributi());

		modelAndView.setViewName(View.studentViewEdit);

		return modelAndView;

	}
	
	/**
	 * Quando si vuole tornare alla home dell'admin
	 * @return
	 */
	@RequestMapping(value= {Urls.homeAdminPath}, method = RequestMethod.GET)
	public ModelAndView homeAdmin(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato
		Utente utente = utenteService.findUtenteByMatricola(auth.getName());
		modelAndView.addObject("utente", utente);
		modelAndView.setViewName(View.adminView);
		return modelAndView;
	}

}
