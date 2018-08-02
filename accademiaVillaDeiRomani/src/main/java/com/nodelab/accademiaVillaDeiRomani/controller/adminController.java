package com.nodelab.accademiaVillaDeiRomani.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
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

import com.nodelab.accademiaVillaDeiRomani.constant.Report;
import com.nodelab.accademiaVillaDeiRomani.constant.Ruoli;
import com.nodelab.accademiaVillaDeiRomani.constant.Test;
import com.nodelab.accademiaVillaDeiRomani.constant.Urls;
import com.nodelab.accademiaVillaDeiRomani.constant.View;
import com.nodelab.accademiaVillaDeiRomani.formBean.ReportStudenteBean;
import com.nodelab.accademiaVillaDeiRomani.hibernate.search.UtenteSearch;
import com.nodelab.accademiaVillaDeiRomani.model.Role;
import com.nodelab.accademiaVillaDeiRomani.model.Utente;
import com.nodelab.accademiaVillaDeiRomani.service.AttivitaDidatticaService;
import com.nodelab.accademiaVillaDeiRomani.service.ContributoService;
import com.nodelab.accademiaVillaDeiRomani.service.CorsoService;
import com.nodelab.accademiaVillaDeiRomani.service.MailService;
import com.nodelab.accademiaVillaDeiRomani.service.MessageService;
import com.nodelab.accademiaVillaDeiRomani.service.ReportService;
import com.nodelab.accademiaVillaDeiRomani.service.RoleService;
import com.nodelab.accademiaVillaDeiRomani.service.UtenteService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * Questo controller gestisce la adminView
 * @author Daniele Tavernelli
 *
 */
@Controller
public class adminController {

	@Autowired
	private UtenteSearch utenteSearch; 

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private CorsoService corsoService;

	@Autowired
	private AttivitaDidatticaService attivitaDidatticaService;

	@Autowired
	private ContributoService contributoService;

	@Autowired
	private ReportService reportService;



	@Autowired
	MailService mailService;

	@Autowired
	MessageService messageService;

	/**
	 * method that retrieve users according to a query, it uses hibernate search (lucene) 
	 * @param query
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {Urls.submitSearchPath}, method = RequestMethod.GET)
	public String search(@RequestParam(value = "search", required = false) String query, Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato
		Utente utente = utenteService.findUtenteByMatricola(auth.getName());
		model.addAttribute("utente", utente);

		List<Utente> searchResults = null;
		try {
			searchResults = utenteSearch.search(query);
		} catch (Exception ex) {
		}
		if (searchResults==null) {
			searchResults=new ArrayList<>();
		}
		model.addAttribute("users", searchResults);
		return View.adminView;

	}

	/**
	 * method that open user view when admin click on user card 
	 * @param query
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {Urls.userSelectedPath}, method = RequestMethod.GET)
	public String getUserSelectedFromList(@RequestParam(value = "matricola", required = true) String matricola, Model model) {

		Utente utente = utenteService.findUtenteByMatricola(matricola);

		model.addAttribute("utente", utente);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato
		Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
		model.addAttribute("utenteLogged", utenteLogged);


		switch (utente.getRole().getName()) {

		//a seconda del ruolo faccio vedere una vista
		case Ruoli.ruolo_studente:
			return View.studentView;
		}

		return View.studentView;

	}

	/**
	 * Quando richiedo di aprire il pannello per creare un nuovo utente
	 * @return
	 */
	@RequestMapping(value= {Urls.openNewUserPanelPath}, method = RequestMethod.GET)
	public String openNewUserPanel( Model model){



		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato
		Utente utente = utenteService.findUtenteByMatricola(auth.getName());
		model.addAttribute("utente", utente);

		//aggiungo il modello per il nuovo utente
		Utente newUser = new Utente();
		model.addAttribute("newUser", newUser);

		//aggiungo i ruoli
		List<Role> ruoli= roleService.getListOfRoles();
		model.addAttribute("ruoli", ruoli);

		return View.adminView;

	}

	/**
	 * Quando faccio il submit di un nuovo utente
	 * @return
	 */
	@RequestMapping(value = { Urls.submitNewUserPath}, method = RequestMethod.POST)
	public ModelAndView submitNewUser(@Valid @ModelAttribute("newUser") Utente newUser, BindingResult bindingResult, ModelMap model) {

		ModelAndView modelAndView;

		//mi prendo l'utente loggato
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utente utenteLoggato = utenteService.findUtenteByMatricola(auth.getName());

		if (Test.EMAIL_CHECK) {
			//controllo che non ci siano due utenti con la stessa mail
			Utente utenteExists = utenteService.findUtenteByEmail(newUser.getEmail());

			if (utenteExists != null) {
				bindingResult
				.rejectValue("email", "error.user",
						"There is already a user registered with the email provided");
			}
		}


		if (bindingResult.hasErrors()) {
			modelAndView = new ModelAndView();
			modelAndView.addObject("newUser",newUser);
			modelAndView.addObject("utente",utenteLoggato);
			modelAndView.addObject("ruoli",roleService.getListOfRoles());
			modelAndView.setViewName(View.adminView);

		} else {

			newUser= utenteService.saveNewUserFromAdmin(newUser);

			//mandiamo una mail con il link di conferma
			mailService.sendRegistrationMail(newUser.getEmail(), messageService.getMessage("subjectRegistrationMail") ,new Integer(newUser.getMatricola()).toString());


			model.addAttribute("matricola", utenteLoggato.getMatricola());

			model.addAttribute("key","userCreatedSuccessfully");
			
			model.addAttribute("value",true);


			modelAndView = new ModelAndView(Urls.redirect+Urls.adminPath,model);



		}
		return modelAndView;

	}

	/**
	 * Quando richiedo di aprire il pannello report studenti
	 * @return
	 */
	@RequestMapping(value= {Urls.openReportStudentiPanelPath}, method = RequestMethod.GET)
	public String openReportStudentiPanel( Model model){

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato
		Utente utente = utenteService.findUtenteByMatricola(auth.getName());
		model.addAttribute("utente", utente);

		ReportStudenteBean reportStudenteBean = new ReportStudenteBean();
		model.addAttribute("reportStudenteBean", reportStudenteBean);

		model.addAttribute("corsi",corsoService.getListOfCorsi());
		model.addAttribute("attivitaDidattiche",attivitaDidatticaService.getListOfAttivitaDidattiche());
		model.addAttribute("contributi",contributoService.getAllContributi());

		return View.adminView;

	}

	/**
	 * Quando chiedo di generare il report studenti
	 * @return
	 */
	@RequestMapping(value = { Urls.submitStudentiReportPath}, method = RequestMethod.POST)
	public void submitStudentiReport( @ModelAttribute("reportStudenteBean") ReportStudenteBean reportStudenteBean,BindingResult bindingResult,HttpServletResponse response) {

		try {
			//genero il report
			response.setContentType("application/x-download");
			response.setHeader("Content-disposition", "attachment;filename="+Report.studentiReportName);
			JasperPrint report=reportService.generateStudentiReport(reportStudenteBean);
			OutputStream outStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(report, outStream);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}



	/**
	 * Matodo che reindirizza alla admin view, può mettere una variabile opzionale (key,value) per mostrare eventuali snack bar 
	 * @param matricola
	 * @param key
	 * @param value
	 * @return
	 */
	@RequestMapping(value= {Urls.adminPath}, method = RequestMethod.GET)
	public ModelAndView submitLogin(@RequestParam(value = "matricola", required = true) String matricola,@RequestParam(value = "key", required = false) String key, @RequestParam(value = "value", required = false) String value){

		ModelAndView modelAndView = new ModelAndView();

		//mi prendo l'utente loggato e l'utente della student view
		Utente utente = utenteService.findUtenteByMatricola(matricola);


		//li aggiungo al model
		modelAndView.addObject("utente",utente);

		//aggiungo la variabile opzionale
		if (key!=null && value!=null) {
			modelAndView.addObject(key,value);
		}

		modelAndView.setViewName(View.adminView);


		return modelAndView;

	}






}
