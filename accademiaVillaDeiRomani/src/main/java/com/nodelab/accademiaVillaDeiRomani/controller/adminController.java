package com.nodelab.accademiaVillaDeiRomani.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.nodelab.accademiaVillaDeiRomani.constant.Urls;
import com.nodelab.accademiaVillaDeiRomani.constant.View;
import com.nodelab.accademiaVillaDeiRomani.formBean.ReportStudenteBean;
import com.nodelab.accademiaVillaDeiRomani.formBean.UtenteBean;
import com.nodelab.accademiaVillaDeiRomani.hibernate.search.UtenteSearch;
import com.nodelab.accademiaVillaDeiRomani.model.AttivitaDidattica;
import com.nodelab.accademiaVillaDeiRomani.model.Contributo;
import com.nodelab.accademiaVillaDeiRomani.model.Corso;
import com.nodelab.accademiaVillaDeiRomani.model.CorsoHasAttivitaDidattica;
import com.nodelab.accademiaVillaDeiRomani.model.Role;
import com.nodelab.accademiaVillaDeiRomani.model.Utente;
import com.nodelab.accademiaVillaDeiRomani.service.AttivitaDidatticaService;
import com.nodelab.accademiaVillaDeiRomani.service.BackupService;
import com.nodelab.accademiaVillaDeiRomani.service.ContributoService;
import com.nodelab.accademiaVillaDeiRomani.service.CorsoService;
import com.nodelab.accademiaVillaDeiRomani.service.MailService;
import com.nodelab.accademiaVillaDeiRomani.service.MessageService;
import com.nodelab.accademiaVillaDeiRomani.service.NazioneService;
import com.nodelab.accademiaVillaDeiRomani.service.ReportService;
import com.nodelab.accademiaVillaDeiRomani.service.RoleService;
import com.nodelab.accademiaVillaDeiRomani.service.UtenteService;
import com.nodelab.accademiaVillaDeiRomani.service.VariabileAmbienteService;
import com.nodelab.accademiaVillaDeiRomani.utility.DataConverter;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * Questo controller gestisce la adminView
 * @author Daniele Tavernelli
 *
 */
@Controller
public class adminController {

    private Logger logger = LoggerFactory.getLogger(adminController.class);

	
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
	private BackupService backupService;

	@Autowired
	MailService mailService;

	@Autowired
	MessageService messageService;
	
	@Autowired
	DataConverter dataConverter;
	
	@Autowired
	private NazioneService nazioneService;
	
	@Autowired
	private VariabileAmbienteService variabileAmbienteService;
	

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

		logger.info("UTENTE MATRICOLA: "+utente.getMatricola()+" ESEGUE RICERCA: "+query);

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

		logger.info("UTENTE MATRICOLA: "+utenteLogged.getMatricola()+" APRE PAGINA UTENTE MATRICOLA: "+utente.getMatricola());

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
	model.addAttribute("nazioni", nazioneService.getAllNazione() );


		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato
		Utente utente = utenteService.findUtenteByMatricola(auth.getName());
		model.addAttribute("utente", utente);

		//aggiungo il modello per il nuovo utente
		UtenteBean newUser = new UtenteBean();
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
	public ModelAndView submitNewUser(@Valid @ModelAttribute("newUser") UtenteBean newUserBean, BindingResult bindingResult, ModelMap model) {

		ModelAndView modelAndView;

		//mi prendo l'utente loggato
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utente utenteLoggato = utenteService.findUtenteByMatricola(auth.getName());

		if (variabileAmbienteService.emailCheck()) {
			//controllo che non ci siano due utenti con la stessa mail
			Utente utenteExists = utenteService.findUtenteByEmail(newUserBean.getEmail());

			if (utenteExists != null) {
				bindingResult
				.rejectValue("email", "error.user",
						"There is already a user registered with the email provided");
			}
		}


		if (bindingResult.hasErrors()) {
			modelAndView = new ModelAndView();
			modelAndView.addObject("newUser",newUserBean);
			modelAndView.addObject("utente",utenteLoggato);
			modelAndView.addObject("ruoli",roleService.getListOfRoles());
			modelAndView.addObject("nazioni", nazioneService.getAllNazione() );
			modelAndView.setViewName(View.adminView);

		} else {
			
			Utente newUser=dataConverter.getModelUtenteByUtenteFormBean(newUserBean);
			newUser= utenteService.saveNewUserFromAdmin(newUser);

			//mandiamo una mail con il link di conferma
			String token = UUID.randomUUID().toString();
			utenteService.createRegistrationverificationToken(token,newUser);
			try  {
				mailService.sendRegistrationMail(newUser.getEmail(), messageService.getMessage("subjectRegistrationMail") ,new Integer(newUser.getMatricola()).toString(),token);
			} catch (Exception e) {
				
				utenteService.deleteUtente(newUser);
				model.addAttribute("matricola", utenteLoggato.getMatricola());

				model.addAttribute("key","error");

				model.addAttribute("value",true);

				logger.warn("UTENTE MATRICOLA: "+utenteLoggato.getMatricola()+" CREA NUOVO UTENTE ERRORE ");
				
				modelAndView = new ModelAndView(Urls.redirect+Urls.adminPath,model);
			}

			model.addAttribute("matricola", utenteLoggato.getMatricola());

			model.addAttribute("key","userCreatedSuccessfully");

			model.addAttribute("value",true);

			logger.warn("UTENTE MATRICOLA: "+utenteLoggato.getMatricola()+" CREA NUOVO UTENTE MATRICOLA:"+newUser.getMatricola()+" ROLE: "+newUser.getRole().getName());
			
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

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			//mi prendo l'utente loggato
			Utente utente = utenteService.findUtenteByMatricola(auth.getName());
			logger.info("UTENTE MATRICOLA: "+utente.getMatricola()+" GENERA REPORT STUDENTI");

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
	public ModelAndView openAdminView(@RequestParam(value = "matricola", required = true) String matricola,@RequestParam(value = "key", required = false) String key, @RequestParam(value = "value", required = false) String value){

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



	//---------------------------------
	//---------------------------------
	//inizio gestione corsi
	//---------------------------------
	//---------------------------------
	/**
	 * Quando apro pannello per creare corso
	 * @return
	 */
	@RequestMapping(value= {Urls.openCreateCoursePanelPath}, method = RequestMethod.GET)
	public String openCreateCourse(Model model){

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato
		Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
		model.addAttribute("utente", utenteLogged);
		model.addAttribute("corso", new Corso());
		model.addAttribute("createCourse", true);
		return View.adminView;

	}



	/**
	 * Quando submit nuovo corso
	 * @return
	 */
	@RequestMapping(value = { Urls.submitCreateCoursePath}, method = RequestMethod.POST)
	public ModelAndView submitCreateCourse(@Valid @ModelAttribute("corso") Corso corso, BindingResult bindingResult,ModelMap model) {

		ModelAndView modelAndView;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (bindingResult.hasErrors()) {

			Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
			modelAndView = new ModelAndView();
			modelAndView.addObject("utente", utenteLogged);
			model.addAttribute("corso", new Corso());
			model.addAttribute("createCourse", true);
			modelAndView.setViewName(View.adminView);
		} else {
			corso=corsoService.save(corso);
			model.addAttribute("matricola",auth.getName());
			model.addAttribute("key","operationCompletedSuccessfully");
			model.addAttribute("value",true);
			modelAndView =  new ModelAndView(Urls.redirect+Urls.adminPath, model);
			
			logger.warn("UTENTE MATRICOLA: "+auth.getName()+" CREA CORSO: ID="+corso.getIdCorso()+" NOME="+corso.getNome());

		}

		return modelAndView;

	}
	/**
	 * Quando richiedo di aprire il pannello elimina corso
	 * @return
	 */
	@RequestMapping(value= {Urls.openDeleteCoursePanelPath}, method = RequestMethod.GET)
	public String openDeletetCorso(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato
		Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
		model.addAttribute("utente", utenteLogged);
		model.addAttribute("corsoToDelete", new Corso());
		model.addAttribute("corsi", corsoService.getListOfCorsi());
		model.addAttribute("eliminateCourse", true);
		return View.adminView;

	}
	/**
	 * Quando chiedo di eliminare una corso
	 * @return
	 */
	@RequestMapping(value = { Urls.submitDeleteCoursePath}, method = RequestMethod.POST)
	public ModelAndView submitDeleteCourse(Corso corso,ModelMap model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();


		corsoService.removeCorso(corso);

		model.addAttribute("matricola",auth.getName());
		model.addAttribute("key","operationCompletedSuccessfully");
		model.addAttribute("value",true);
		
		logger.warn("UTENTE MATRICOLA: "+auth.getName()+" ELIMINA CORSO: ID="+corso.getIdCorso()+" NOME="+corso.getNome());

		return  new ModelAndView(Urls.redirect+Urls.adminPath, model);


	}
	/**
	 * Quando richiedo di aprire il pannello modifica corso
	 * @return
	 */
	@RequestMapping(value= {Urls.openEditCoursePanelPath}, method = RequestMethod.GET)
	public String openEditCorso(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato
		Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
		model.addAttribute("utente", utenteLogged);
		model.addAttribute("corsi", corsoService.getListOfCorsi());
		model.addAttribute("corso", new Corso());
		model.addAttribute("editCourse", true);
		return View.adminView;

	}
	/**
	 * Quando chiedo di modificare una attivita didattica
	 * @return
	 */
	@RequestMapping(value = { Urls.submitEditCoursePath}, method = RequestMethod.POST)
	public ModelAndView submitEditCourse(@Valid @ModelAttribute("corso") Corso corso, BindingResult bindingResult,ModelMap model) {

		ModelAndView modelAndView;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (bindingResult.hasErrors()) {
			modelAndView=new ModelAndView();
			Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
			modelAndView.addObject("utente", utenteLogged);
			modelAndView.addObject("corsi", corsoService.getListOfCorsi());
			modelAndView.addObject("corso", corso);
			modelAndView.addObject("editCourse", true);
			modelAndView.setViewName(View.adminView);
			return modelAndView;
		} else {
			corsoService.save(corso);
			model.addAttribute("matricola",auth.getName());
			model.addAttribute("key","operationCompletedSuccessfully");
			model.addAttribute("value",true);
			modelAndView =  new ModelAndView(Urls.redirect+Urls.adminPath, model);
			logger.warn("UTENTE MATRICOLA: "+auth.getName()+" MODIFICA CORSO: ID="+corso.getIdCorso()+" NOME="+corso.getNome());

		}
		return modelAndView;
	}
	/**
	 * Quando richiedo di visualizzare i corsi
	 * @return
	 */
	@RequestMapping(value= {Urls.openVisualizzaCoursePanelPath}, method = RequestMethod.GET)
	public String openVisualizzazioneCorsi(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato
		Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
		model.addAttribute("utente", utenteLogged);
		model.addAttribute("corsi", corsoService.getListOfCorsi());
		model.addAttribute("visualizeCourse", true);
		return View.adminView;

	}
	//---------------------------------
	//---------------------------------
	//fine gestione corsi
	//---------------------------------
	//---------------------------------




	//---------------------------------
	//---------------------------------
	//inizio gestione attivita didattiche
	//---------------------------------
	//---------------------------------
	/**
	 * Quando apro pannello per creare attivitaDidattica
	 * @return
	 */
	@RequestMapping(value= {Urls.openCreateAttivitaDidatticaPanelPath}, method = RequestMethod.GET)
	public String openCreateAttivitaDidattica(Model model){

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato
		Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
		model.addAttribute("utente", utenteLogged);
		model.addAttribute("corsi", corsoService.getListOfCorsi());
		model.addAttribute("attivitaDidattica", new AttivitaDidattica());
		model.addAttribute("createAttivitaDidattica", true);
		return View.adminView;

	}



	/**
	 * Quando submit nuova attivita didattica
	 * @return
	 */
	@RequestMapping(value = { Urls.submitCreateAttivitaDidatticaPath}, method = RequestMethod.POST)
	public ModelAndView submitCreateAttivitaDidattica(@Valid @ModelAttribute("attivitaDidattica") AttivitaDidattica attivitaDidattica, BindingResult bindingResult,ModelMap model) {

		ModelAndView modelAndView;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (bindingResult.hasErrors()) {
			modelAndView=new ModelAndView();
			Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
			modelAndView.addObject("utente", utenteLogged);
			modelAndView.addObject("corsi", corsoService.getListOfCorsi());
			modelAndView.addObject("attivitaDidattica", new AttivitaDidattica());
			modelAndView.addObject("createAttivitaDidattica", true);
			modelAndView.setViewName(View.adminView);
			return modelAndView;
		} else {
			attivitaDidattica=attivitaDidatticaService.save(attivitaDidattica);
			model.addAttribute("matricola",auth.getName());
			model.addAttribute("key","operationCompletedSuccessfully");
			model.addAttribute("value",true);
			modelAndView =  new ModelAndView(Urls.redirect+Urls.adminPath, model);
			
			logger.warn("UTENTE MATRICOLA: "+auth.getName()+" CREA ATTIVITA DIDATTICA: ID="+attivitaDidattica.getIdAttivitaDidattica()+" NOME="+attivitaDidattica.getNome());

		}

		return modelAndView;

	}
	/**
	 * Quando richiedo di aprire il pannello elimina attivita didattica
	 * @return
	 */
	@RequestMapping(value= {Urls.openDeleteAttivitaDidatticaPanelPath}, method = RequestMethod.GET)
	public String openDeleteAttivitaDidattica(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato
		Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
		model.addAttribute("utente", utenteLogged);
		model.addAttribute("attivitaDidatticaToDelete", new AttivitaDidattica());
		model.addAttribute("attivitaDidattiche", attivitaDidatticaService.getListOfAttivitaDidattiche());
		model.addAttribute("eliminateAttivitaDidattica", true);
		return View.adminView;

	}
	/**
	 * Quando chiedo di eliminare una attivita didattica
	 * @return
	 */
	@RequestMapping(value = { Urls.submitDeleteAttivitaDidatticaPath}, method = RequestMethod.POST)
	public ModelAndView submitDeleteAttivitaDidattica(AttivitaDidattica attivitaDidattica,ModelMap model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();


		attivitaDidatticaService.removeAttivitaDidattica(attivitaDidattica);

		model.addAttribute("matricola",auth.getName());
		model.addAttribute("key","operationCompletedSuccessfully");
		model.addAttribute("value",true);
		
		logger.warn("UTENTE MATRICOLA: "+auth.getName()+" ELIMINA ATTIVITA DIDATTICA: ID="+attivitaDidattica.getIdAttivitaDidattica()+" NOME="+attivitaDidattica.getNome());

		
		return  new ModelAndView(Urls.redirect+Urls.adminPath, model);


	}
	/**
	 * Quando richiedo di aprire il pannello modifica attivitaDidattica
	 * @return
	 */
	@RequestMapping(value= {Urls.openEditAttivitaDidatticaPanelPath}, method = RequestMethod.GET)
	public String openEditAttivitaDidattica(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato
		Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
		model.addAttribute("utente", utenteLogged);
		model.addAttribute("attivitaDidattiche", attivitaDidatticaService.getListOfAttivitaDidattiche());
		model.addAttribute("attivitaDidattica", new AttivitaDidattica());
		model.addAttribute("editAttivitaDidattica", true);
		return View.adminView;

	}
	/**
	 * Quando chiedo di modificare una attivita didattica
	 * @return
	 */
	@RequestMapping(value = { Urls.submitEditAttivitaDidatticaPath}, method = RequestMethod.POST)
	public ModelAndView submitEditAttivitaDidattica(@Valid @ModelAttribute("attivitaDidattica") AttivitaDidattica attivitaDidattica, BindingResult bindingResult,ModelMap model) {

		ModelAndView modelAndView;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (bindingResult.hasErrors()) {
			modelAndView=new ModelAndView();
			Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
			modelAndView.addObject("utente", utenteLogged);
			modelAndView.addObject("attivitaDidattiche", attivitaDidatticaService.getListOfAttivitaDidattiche());
			modelAndView.addObject("attivitaDidattica", attivitaDidattica);
			modelAndView.addObject("editAttivitaDidattica", true);
			modelAndView.setViewName(View.adminView);
			return modelAndView;
		} else {
			attivitaDidatticaService.save(attivitaDidattica);
			model.addAttribute("matricola",auth.getName());
			model.addAttribute("key","operationCompletedSuccessfully");
			model.addAttribute("value",true);
			modelAndView =  new ModelAndView(Urls.redirect+Urls.adminPath, model);
			
			logger.warn("UTENTE MATRICOLA: "+auth.getName()+" MODIFICA ATTIVITA DIDATTICA: ID="+attivitaDidattica.getIdAttivitaDidattica()+" NOME="+attivitaDidattica.getNome());

		}
		return modelAndView;
	}
	/**
	 * Quando richiedo di aprire il pannello per la visualizzazione delle attivitaDidattiche
	 * @return
	 */
	@RequestMapping(value= {Urls.openVisualizzaAttivitaDidatticaPanelPath}, method = RequestMethod.GET)
	public String openVisualizzaAttivitaDidattica(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato
		Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
		model.addAttribute("utente", utenteLogged);
		model.addAttribute("attivitaDidattiche", attivitaDidatticaService.getListOfAttivitaDidattiche());
		model.addAttribute("visualizeAttivitaDidattica", true);
		return View.adminView;

	}
	//---------------------------------
	//---------------------------------
	//fine gestione attivita didattiche
	//---------------------------------
	//---------------------------------


	//---------------------------------
	//---------------------------------
	//inizio gestione rel attivita-corsi
	//---------------------------------
	//---------------------------------
	/**
	 * Quando apro pannello per creare rel attivita-corsi
	 * @return
	 */
	@RequestMapping(value= {Urls.openAddRelAttivitaCorsoPanelPath}, method = RequestMethod.GET)
	public String openAddRelAttivitaCorso(Model model){

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato
		Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
		model.addAttribute("utente", utenteLogged);
		model.addAttribute("corsi", corsoService.getListOfCorsi());
		model.addAttribute("attivitaDidattiche", attivitaDidatticaService.getListOfAttivitaDidattiche());
		model.addAttribute("relCorsoAttivita", new CorsoHasAttivitaDidattica());
		model.addAttribute("createRelAttivitaCorso", true);
		return View.adminView;

	}



	/**
	 * Quando submit nuova rel attivita-corsi
	 * @return
	 */
	@RequestMapping(value = { Urls.submitAddRelAttivitaCorsoPath}, method = RequestMethod.POST)
	public ModelAndView submitAddRelAttivitaCorso(@Valid @ModelAttribute("corsoHasAttivitaDidattica") CorsoHasAttivitaDidattica corsoHasAttivitaDidattica, BindingResult bindingResult,ModelMap model) {

		ModelAndView modelAndView;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (bindingResult.hasErrors()) {
			modelAndView=new ModelAndView();
			Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
			modelAndView.addObject("utente", utenteLogged);
			modelAndView.addObject("corsi", corsoService.getListOfCorsi());
			modelAndView.addObject("attivitaDidattiche", attivitaDidatticaService.getListOfAttivitaDidattiche());
			modelAndView.addObject("relCorsoAttivita", new CorsoHasAttivitaDidattica());
			modelAndView.addObject("createRelAttivitaCors", true);
			modelAndView.setViewName(View.adminView);
			return modelAndView;
		} else {
			corsoHasAttivitaDidattica=corsoService.saveCorsoHasAttivitaDidattica(corsoHasAttivitaDidattica);
			model.addAttribute("matricola",auth.getName());
			model.addAttribute("key","operationCompletedSuccessfully");
			model.addAttribute("value",true);
			modelAndView =  new ModelAndView(Urls.redirect+Urls.adminPath, model);
			
			logger.warn("UTENTE MATRICOLA: "+auth.getName()+" CREA UTENTE-HAS-ATTIVITA-DIDATTICA: NOME-CORSO="+corsoHasAttivitaDidattica.getCorso().getNome()+" NOME ATTIVITà="+corsoHasAttivitaDidattica.getAttivitaDidattica().getNome());

			
		}

		return modelAndView;

	}
	/**
	 * Quando richiedo di aprire il pannello elimina rel attivita-corsi
	 * @return
	 */
	@RequestMapping(value= {Urls.openDeleteRelAttivitaCorsoPanelPath}, method = RequestMethod.GET)
	public String openDeleteRelAttivitaCorsoPanel(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato
		Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
		model.addAttribute("utente", utenteLogged);
		model.addAttribute("corsi", corsoService.getListOfCorsi());
		model.addAttribute("relCorsoAttivitaToDelete", new CorsoHasAttivitaDidattica());
		model.addAttribute("deleteRelAttivitaCorso", true);
		return View.adminView;

	}
	/**
	 * Quando chiedo di eliminare una rel attivita-corsi
	 * @return
	 */
	@RequestMapping(value = { Urls.submitDeleteRelAttivitaCorsoPath}, method = RequestMethod.POST)
	public ModelAndView submitRelAttivitaCorso(CorsoHasAttivitaDidattica corsoHasAttivitaDidattica,ModelMap model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();


		corsoService.removeCorsoHasAttivitaDidattica(corsoHasAttivitaDidattica);

		model.addAttribute("matricola",auth.getName());
		model.addAttribute("key","operationCompletedSuccessfully");
		model.addAttribute("value",true);
		
		logger.warn("UTENTE MATRICOLA: "+auth.getName()+" ELIMINA UTENTE-HAS-ATTIVITA-DIDATTICA: NOME-CORSO="+corsoHasAttivitaDidattica.getCorso().getNome()+" NOME ATTIVITà="+corsoHasAttivitaDidattica.getAttivitaDidattica().getNome());
		
		return  new ModelAndView(Urls.redirect+Urls.adminPath, model);


	}
	/**
	 * Quando richiedo di aprire il pannello modifica rel attivita-corsi
	 * @return
	 */
	@RequestMapping(value= {Urls.openEditRelAttivitaCorsoPanelPath}, method = RequestMethod.GET)
	public String openEditRelAttivitaCorsoPanel(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato
		Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
		model.addAttribute("utente", utenteLogged);
		model.addAttribute("corsi", corsoService.getListOfCorsi());
		model.addAttribute("relCorsoAttivita", new CorsoHasAttivitaDidattica());
		model.addAttribute("editRelAttivitaCorso", true);
		return View.adminView;

	}
	/**
	 * Quando chiedo di modificare una rel attivita-corsi
	 * @return
	 */
	@RequestMapping(value = { Urls.submitEditRelAttivitaCorsoPath}, method = RequestMethod.POST)
	public ModelAndView submitEditRelAttivitaCorso(@Valid @ModelAttribute("corsoHasAttivitaDidattica") CorsoHasAttivitaDidattica corsoHasAttivitaDidattica, BindingResult bindingResult,ModelMap model) {

		ModelAndView modelAndView;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (bindingResult.hasErrors()) {
			modelAndView=new ModelAndView();
			Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
			modelAndView.addObject("utente", utenteLogged);
			modelAndView.addObject("corsi", corsoService.getListOfCorsi());
			modelAndView.addObject("relCorsoAttivita", new CorsoHasAttivitaDidattica());
			modelAndView.addObject("editRelAttivitaCorso", true);
			modelAndView.setViewName(View.adminView);
			return modelAndView;
		} else {
			corsoService.updateCorsoHasAttivitaDidattica(corsoHasAttivitaDidattica);
			model.addAttribute("matricola",auth.getName());
			model.addAttribute("key","operationCompletedSuccessfully");
			model.addAttribute("value",true);
			
			logger.warn("UTENTE MATRICOLA: "+auth.getName()+" MODIFICA UTENTE-HAS-ATTIVITA-DIDATTICA: NOME-CORSO="+corsoHasAttivitaDidattica.getCorso().getNome()+" NOME ATTIVITà="+corsoHasAttivitaDidattica.getAttivitaDidattica().getNome());
			
			modelAndView =  new ModelAndView(Urls.redirect+Urls.adminPath, model);
		}
		return modelAndView;
	}
	/**
	 * Quando richiedo di aprire il pannello per la visualizzazione rel attivita-corsi
	 * @return
	 */
	@RequestMapping(value= {Urls.openVisualizeRelAttivitaCorsoPanelPath}, method = RequestMethod.GET)
	public String openVisualizeRelAttivitaCorso(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato
		Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
		model.addAttribute("utente", utenteLogged);
		model.addAttribute("corsi", corsoService.getListOfCorsi());
		model.addAttribute("visualizeRelAttivitaCorso", true);
		return View.adminView;

	}
	//---------------------------------
	//---------------------------------
	//fine gestione rel attivita-corsi
	//---------------------------------
	//---------------------------------


	//---------------------------------
	//---------------------------------
	//inizio gestione tasse
	//---------------------------------
	//---------------------------------
	/**
	 * Quando apro pannello per creare tassa
	 * @return
	 */
	@RequestMapping(value= {Urls.openCreateTassaPanelPath}, method = RequestMethod.GET)
	public String openCreateTassa(Model model){

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato
		Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
		model.addAttribute("utente", utenteLogged);
		model.addAttribute("contributo", new Contributo());
		model.addAttribute("createTassa", true);
		return View.adminView;

	}



	/**
	 * Quando submit nuovo tassa
	 * @return
	 */
	@RequestMapping(value = { Urls.submitCreateTassaPath}, method = RequestMethod.POST)
	public ModelAndView submitCreateTassae(@Valid @ModelAttribute("contributo") Contributo contributo, BindingResult bindingResult,ModelMap model) {

		ModelAndView modelAndView;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (bindingResult.hasErrors()) {

			Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
			modelAndView = new ModelAndView();
			modelAndView.addObject("utente", utenteLogged);
			model.addAttribute("contributo", new Contributo());
			model.addAttribute("createTassa", true);
			modelAndView.setViewName(View.adminView);
		} else {
			contributo=contributoService.save(contributo);
			model.addAttribute("matricola",auth.getName());
			model.addAttribute("key","operationCompletedSuccessfully");
			model.addAttribute("value",true);
			modelAndView =  new ModelAndView(Urls.redirect+Urls.adminPath, model);
			
			logger.warn("UTENTE MATRICOLA: "+auth.getName()+" CREA CONTRIBUTO: ID="+contributo.getIdContributo()+" NOME= "+contributo.getNome());

			
		}

		return modelAndView;

	}
	/**
	 * Quando richiedo di aprire il pannello elimina tassa
	 * @return
	 */
	@RequestMapping(value= {Urls.openDeleteTassaPanelPath}, method = RequestMethod.GET)
	public String openDeletetTax(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato
		Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
		model.addAttribute("utente", utenteLogged);
		model.addAttribute("contributoToDelete", new Contributo());
		model.addAttribute("contributi", contributoService.getAllContributi());
		model.addAttribute("deleteTassa", true);
		return View.adminView;

	}
	/**
	 * Quando chiedo di eliminare una tassa
	 * @return
	 */
	@RequestMapping(value = { Urls.submitDeleteTassaPath}, method = RequestMethod.POST)
	public ModelAndView submitDeleteTassa(Contributo contributo,ModelMap model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();


		contributoService.removeContributo(contributo);

		model.addAttribute("matricola",auth.getName());
		model.addAttribute("key","operationCompletedSuccessfully");
		model.addAttribute("value",true);
		
		logger.warn("UTENTE MATRICOLA: "+auth.getName()+" ELIMINA CONTRIBUTO: ID="+contributo.getIdContributo()+" NOME= "+contributo.getNome());

		return  new ModelAndView(Urls.redirect+Urls.adminPath, model);


	}
	/**
	 * Quando richiedo di aprire il pannello modifica tassa
	 * @return
	 */
	@RequestMapping(value= {Urls.openEditTassaPanelPath}, method = RequestMethod.GET)
	public String openEditTassa(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato
		Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
		model.addAttribute("utente", utenteLogged);
		model.addAttribute("contributi", contributoService.getAllContributi());
		model.addAttribute("contributoToUpdate", new Contributo());
		model.addAttribute("editTassa", true);
		return View.adminView;

	}
	/**
	 * Quando chiedo di modificare una tassa
	 * @return
	 */
	@RequestMapping(value = { Urls.submitEditTassaPath}, method = RequestMethod.POST)
	public ModelAndView submitEditTassa(@Valid @ModelAttribute("contributo") Contributo contributo, BindingResult bindingResult,ModelMap model) {

		ModelAndView modelAndView;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (bindingResult.hasErrors()) {
			modelAndView=new ModelAndView();
			Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
			modelAndView.addObject("utente", utenteLogged);
			modelAndView.addObject("contributi", contributoService.getAllContributi());
			modelAndView.addObject("contributo", new Contributo());
			modelAndView.addObject("editTassa", true);
			modelAndView.setViewName(View.adminView);
			return modelAndView;
		} else {
			contributoService.save(contributo);
			model.addAttribute("matricola",auth.getName());
			model.addAttribute("key","operationCompletedSuccessfully");
			model.addAttribute("value",true);
			
			logger.warn("UTENTE MATRICOLA: "+auth.getName()+" MODIFICA CONTRIBUTO: ID="+contributo.getIdContributo()+" NOME= "+contributo.getNome());

			modelAndView =  new ModelAndView(Urls.redirect+Urls.adminPath, model);
		}
		return modelAndView;
	}
	/**
	 * Quando richiedo di aprire il pannello per la visualizzazione delle tasse
	 * @return
	 */
	@RequestMapping(value= {Urls.openVisualizaTassaPanelPath}, method = RequestMethod.GET)
	public String openVisualizaTassa(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato
		Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
		model.addAttribute("utente", utenteLogged);
		model.addAttribute("contributi", contributoService.getAllContributi());
		model.addAttribute("visualizeTassa", true);
		return View.adminView;

	}
	//---------------------------------
	//---------------------------------
	//fine gestione tasse
	//---------------------------------
	//---------------------------------

	/**
	 * backup
	 * @return
	 */
	@RequestMapping(value= {Urls.submitBackupPath}, method = RequestMethod.GET)
	public ModelAndView backup(ModelMap model){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		try {
			backupService.generateBackUp();
			
			model.addAttribute("matricola",auth.getName());
			model.addAttribute("key","operationCompletedSuccessfully");
			model.addAttribute("value",true);
			
			logger.warn("UTENTE MATRICOLA: "+auth.getName()+" HA GENERATO BACKUP CORRETTAMENTE");

		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("UTENTE MATRICOLA: "+auth.getName()+" ERRORE NEL GENERARE IL BACKUP");

			model.addAttribute("matricola",auth.getName());
			model.addAttribute("key","error");
			model.addAttribute("value",true);
		}
		
		return new ModelAndView(Urls.redirect+Urls.adminPath, model);

	}
	
	
}
