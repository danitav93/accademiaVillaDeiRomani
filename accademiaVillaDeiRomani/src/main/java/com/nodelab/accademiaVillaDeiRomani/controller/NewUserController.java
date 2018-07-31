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
import com.nodelab.accademiaVillaDeiRomani.service.RoleService;
import com.nodelab.accademiaVillaDeiRomani.service.UtenteService;

/**
 * Questo controller gestisce la pagina di quando creo un utente
 * @author User
 *
 */
@Controller
public class NewUserController {
	
	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * Quando admin crea utente
	 * @param user
	 * @param bindingResult
	 * @return
	 */
	
	
}
