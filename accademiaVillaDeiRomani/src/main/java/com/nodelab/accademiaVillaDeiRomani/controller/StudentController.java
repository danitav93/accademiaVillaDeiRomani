package com.nodelab.accademiaVillaDeiRomani.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nodelab.accademiaVillaDeiRomani.constant.Urls;
import com.nodelab.accademiaVillaDeiRomani.constant.View;
import com.nodelab.accademiaVillaDeiRomani.formBean.AggiungiTasseBean;
import com.nodelab.accademiaVillaDeiRomani.formBean.PercorsoFormativoBean;
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
	public ModelAndView homeAdmin(ModelMap model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("matricola", auth.getName());
		return new ModelAndView(Urls.redirect+Urls.adminPath,model);
	}

	/**
	 * Quando richiedo di aprire il pannello per aggiungere lo studente
	 * @return
	 */
	@RequestMapping(value= {Urls.openPanelAggiungiPercorsoFormativoPath}, method = RequestMethod.GET)
	public String openReportStudentiPanel(@RequestParam(value = "matricola", required = true) String matricola, Model model){

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato
		Utente utenteLoggato = utenteService.findUtenteByMatricola(auth.getName());
		model.addAttribute("utenteLogged", utenteLoggato);

		Utente utente= utenteService.findUtenteByMatricola(matricola);
		model.addAttribute("utente", utente);

		PercorsoFormativoBean percorsoFormativoBean = new PercorsoFormativoBean();

		model.addAttribute("aggiungiPercorsoFormativo", true);
		model.addAttribute("percorsoFormativoBean", percorsoFormativoBean);
		model.addAttribute("corsi",corsoService.getListOfCorsi());
		model.addAttribute("attivitaDidattiche",attivitaDidatticaService.getListOfAttivitaDidattiche());

		return View.studentView;

	}

	/**
	 * Quando faccio il submit di un nuovo percorso formativo
	 * @return
	 */
	@RequestMapping(value = { Urls.submitNewPercorsoFormativoPath}, method = RequestMethod.POST)
	public ModelAndView submitNewUser(@Valid @ModelAttribute("percorsoFormativoBean") PercorsoFormativoBean percorsoFormativoBean, BindingResult bindingResult,@RequestParam(value = "matricola", required = true) String matricola,ModelMap model) {

		Utente utente= utenteService.findUtenteByMatricola(matricola);


		percorsoFormativoBean.setUtente(utente);

		//salvo il nuovo percorso formativo
		utenteService.updatePercorsoFormativo(percorsoFormativoBean);

		model.addAttribute("matricola",matricola);
		model.addAttribute("key","percorsoFormativoAggiornatoSuccessfully");
		model.addAttribute("value",true);

		//reindirizzo alla student view
		return new ModelAndView(Urls.redirect+Urls.studentPath, model);

	}

	/**
	 * Matodo che reindirizza alla student view, può mettere una variabile opzionale (key,value) per mostrare eventuali snack bar 
	 * @param matricola
	 * @param key
	 * @param value
	 * @return
	 */
	@RequestMapping(value= {Urls.studentPath}, method = RequestMethod.GET)
	public ModelAndView submitLogin(@RequestParam(value = "matricola", required = true) String matricola,@RequestParam(value = "key", required = false) String key, @RequestParam(value = "value", required = false) String value){

		ModelAndView modelAndView = new ModelAndView();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato e l'utente della student view
		Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
		Utente utente= utenteService.findUtenteByMatricola(matricola);


		//li aggiungo al model
		modelAndView.addObject("utente",utente);
		modelAndView.addObject("utenteLogged",utenteLogged);

		//aggiungo la variabile opzionale
		if (key!=null && value!=null) {
			modelAndView.addObject(key,value);
		}

		modelAndView.setViewName(View.studentView);


		return modelAndView;

	}

	/**
	 * Quando richiedo di aprire il pannello inserisci tasse
	 * @return
	 */
	@RequestMapping(value= {Urls.insertTaxPath}, method = RequestMethod.GET)
	public String openInsertTax(@RequestParam(value="matricola", required=true) String matricola, Model model){

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato
		Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
		model.addAttribute("utenteLogged", utenteLogged);
		Utente utente = utenteService.findUtenteByMatricola(matricola);
		model.addAttribute("utente", utente);
		model.addAttribute("gestioneTasse", true);
		model.addAttribute("contributi", contributoService.getAllContributi());
		model.addAttribute("aggiungiTasseBean", new AggiungiTasseBean());
		return View.studentView;


	}



	/**
	 * Quando chiedo di generare un nuovo pagamento tasse
	 * @return
	 */
	@RequestMapping(value = { Urls.submitNewTaxPath}, method = RequestMethod.POST)
	public ModelAndView submitStudentiReport(@Valid @ModelAttribute("aggiungiTasseBean") AggiungiTasseBean aggiungiTasseBean,@RequestParam(value = "matricola", required = true) String matricola,BindingResult bindingResult,ModelMap model) {
		
		
		Utente utente = utenteService.findUtenteByMatricola(matricola);
		aggiungiTasseBean.setUtente(utente);
		utenteService.addTax(aggiungiTasseBean);
		
		
		model.addAttribute("matricola",matricola);
		model.addAttribute("matricola",matricola);
		model.addAttribute("key","taxCreatedSuccessfully");
		model.addAttribute("value",true);
		return new ModelAndView(Urls.redirect+Urls.studentPath,model);

	}

}
