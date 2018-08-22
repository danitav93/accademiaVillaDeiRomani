package com.nodelab.accademiaVillaDeiRomani.controller;

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

import com.nodelab.accademiaVillaDeiRomani.constant.Urls;
import com.nodelab.accademiaVillaDeiRomani.constant.View;
import com.nodelab.accademiaVillaDeiRomani.formBean.AggiungiEsameBean;
import com.nodelab.accademiaVillaDeiRomani.formBean.AggiungiTasseBean;
import com.nodelab.accademiaVillaDeiRomani.formBean.PercorsoFormativoBean;
import com.nodelab.accademiaVillaDeiRomani.model.Contributo;
import com.nodelab.accademiaVillaDeiRomani.model.Utente;
import com.nodelab.accademiaVillaDeiRomani.service.AttivitaDidatticaService;
import com.nodelab.accademiaVillaDeiRomani.service.ContributoService;
import com.nodelab.accademiaVillaDeiRomani.service.CorsoService;
import com.nodelab.accademiaVillaDeiRomani.service.NazioneService;
import com.nodelab.accademiaVillaDeiRomani.service.RoleService;
import com.nodelab.accademiaVillaDeiRomani.service.UtenteService;
import com.nodelab.accademiaVillaDeiRomani.service.VariabileAmbienteService;

/**
 * controller che gestisce la view dello studente
 * @author User
 *
 */
@Controller
public class StudentController {

	Logger logger = LoggerFactory.getLogger(StudentController.class);

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
	
	@Autowired
	private NazioneService nazioneService;
	
	@Autowired
	private VariabileAmbienteService variabileAmbienteService;
	


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
	public String openPanelAggiungiPercorsoFormativoPanel(@RequestParam(value = "matricola", required = true) String matricola, Model model){

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
	public ModelAndView submitPercorsoFormativo(@Valid @ModelAttribute("percorsoFormativoBean") PercorsoFormativoBean percorsoFormativoBean, BindingResult bindingResult,@RequestParam(value = "matricola", required = true) String matricola,ModelMap model) {

		Utente utente= utenteService.findUtenteByMatricola(matricola);
		percorsoFormativoBean.setUtente(utente);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato
		Utente utenteLoggato = utenteService.findUtenteByMatricola(auth.getName());

		//salvo il nuovo percorso formativo
		utenteService.updatePercorsoFormativo(percorsoFormativoBean);

		model.addAttribute("matricola",matricola);
		model.addAttribute("key","percorsoFormativoAggiornatoSuccessfully");
		model.addAttribute("value",true);

		logger.warn("UTENTE LOGGATO: MATRICOLA= "+utenteLoggato.getMatricola()+" HA AGGIUNTO PERCORSO FORMATIVO ALL'UTENTE MATRICOLA: "+utente.getMatricola());
		
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
	public ModelAndView homeStudent(@RequestParam(value = "matricola", required = true) String matricola,@RequestParam(value = "key", required = false) String key, @RequestParam(value = "value", required = false) String value){

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
	 * Quando richiedo di abilitare uno studente
	 * @return
	 */
	@RequestMapping(value= {Urls.abilitazioneStudenteDirettaPath}, method = RequestMethod.GET)
	public ModelAndView abilitaStudente(@RequestParam(value="matricola", required=true) String matricola, ModelMap model){

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato e l'utente della student view
		Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
		Utente utente = utenteService.findUtenteByMatricola(matricola);
		utenteService.abilitaUtente(utente);
		model.addAttribute("matricola",matricola);
		model.addAttribute("key","operationCompletedSuccessfully");
		model.addAttribute("value",true);
		
		logger.warn("UTENTE LOGGATO: MATRICOLA= "+utenteLogged.getMatricola()+" HA AGGIUNTO ABILITATO L'UTENTE MATRICOLA: "+utente.getMatricola());

		
		return new ModelAndView(Urls.redirect+Urls.studentPath,model);

	}







	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------
	//gestione tasse
	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------
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
		model.addAttribute("aggiungiTasse", true);
		model.addAttribute("contributi", contributoService.getContributiNotPayedByUtente(utente));
		model.addAttribute("aggiungiTasseBean", new AggiungiTasseBean());
		return View.studentView;
	}


	/**
	 * Quando chiedo di generare un nuovo pagamento tasse
	 * @return
	 */
	@RequestMapping(value = { Urls.submitNewTaxPath}, method = RequestMethod.POST)
	public ModelAndView submitNewTax(@Valid @ModelAttribute("aggiungiTasseBean") AggiungiTasseBean aggiungiTasseBean,BindingResult bindingResult,@RequestParam(value = "matricola", required = true) String matricola,ModelMap model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato
		Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());

		Utente utente = utenteService.findUtenteByMatricola(matricola);
		aggiungiTasseBean.setUtente(utente);
		utenteService.addTax(aggiungiTasseBean);


		model.addAttribute("matricola",matricola);
		model.addAttribute("key","taxCreatedSuccessfully");
		model.addAttribute("value",true);
		
		logger.warn("UTENTE LOGGATO: MATRICOLA= "+utenteLogged.getMatricola()+" HA AGGIUNTO TASSA ALL'UTENTE MATRICOLA: "+utente.getMatricola());
		
		return new ModelAndView(Urls.redirect+Urls.studentPath,model);

	}
	
	
	
	/**
	 * Quando richiedo di aprire il pannello elimina tasse
	 * @return
	 */
	@RequestMapping(value= {Urls.deleteTaxPath}, method = RequestMethod.GET)
	public String openDeletetTax(@RequestParam(value="matricola", required=true) String matricola, Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato
		Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
		model.addAttribute("utenteLogged", utenteLogged);
		Utente utente = utenteService.findUtenteByMatricola(matricola);
		model.addAttribute("utente", utente);
		model.addAttribute("eliminaTasse", true);
		model.addAttribute("contributi", contributoService.getContributiPayedByUtente(utente));
		model.addAttribute("contributoToDelete", new Contributo());

		return View.studentView;
	}
	/**
	 * Quando chiedo di eliminare una tassa
	 * @return
	 */
	@RequestMapping(value = { Urls.submitDeleteTaxPath}, method = RequestMethod.POST)
	public ModelAndView submitDeleteTax(Contributo contributo, @RequestParam(value = "matricola", required = true) String matricola,ModelMap model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato
		Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());

		Utente utente = utenteService.findUtenteByMatricola(matricola);

		utenteService.removeTax(utente,contributo);

		model.addAttribute("matricola",matricola);
		model.addAttribute("key","operationCompletedSuccessfully");
		model.addAttribute("value",true);
		
		logger.warn("UTENTE LOGGATO: MATRICOLA= "+utenteLogged.getMatricola()+" HA ELIMINATO LA TASSA ALL'UTENTE MATRICOLA: "+utente.getMatricola());
		
		return new ModelAndView(Urls.redirect+Urls.studentPath,model);

	}
	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------
	//fine gestione tasse
	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------




	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------
	//gestione esami
	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------
	/**
	 * Quando richiedo di aprire il pannello che inserisce un esame in carriera
	 * @return
	 */
	@RequestMapping(value= {Urls.insertExamPath}, method = RequestMethod.GET)
	public String openInsertExam(@RequestParam(value="matricola", required=true) String matricola, Model model){

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato
		Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
		model.addAttribute("utenteLogged", utenteLogged);
		Utente utente = utenteService.findUtenteByMatricola(matricola);
		model.addAttribute("utente", utente);
		model.addAttribute("aggiungiEsame", true);
		model.addAttribute("attivitaDidatticheAssociate", attivitaDidatticaService.getAllAttivitaDidatticheByUtente(utente));
		model.addAttribute("aggiungiEsameBean", new AggiungiEsameBean());
		return View.studentView;


	}



	/**
	 * Quando chiedo di generare un nuovo esame
	 * @return
	 */
	@RequestMapping(value = { Urls.submitNewExamPath}, method = RequestMethod.POST)
	public ModelAndView submitNewExam(@Valid @ModelAttribute("aggiungiEsameBean") AggiungiEsameBean aggiungiEsameBean,@RequestParam(value = "matricola", required = true) String matricola,BindingResult bindingResult,ModelMap model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato
		Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
		
		Utente utente = utenteService.findUtenteByMatricola(matricola);
		aggiungiEsameBean.setUtente(utente);

		utenteService.addExam(aggiungiEsameBean);


		model.addAttribute("matricola",matricola);
		model.addAttribute("matricola",matricola);
		model.addAttribute("key","esameAddedSuccessfully");
		model.addAttribute("value",true);
		
		logger.warn("UTENTE LOGGATO: MATRICOLA= "+utenteLogged.getMatricola()+" HA AGGIUNTO L'ESAME ALL'UTENTE MATRICOLA: "+utente.getMatricola());
		
		return new ModelAndView(Urls.redirect+Urls.studentPath,model);

	}
	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------
	//fine gestione esami
	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------






	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------
	// gestione dati personali
	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------
	/**
	 * Quando richiedo di aprire il pannello che modifica i dati personali dell utente
	 * @return
	 */
	@RequestMapping(value= {Urls.editPersonalDataStudentPath}, method = RequestMethod.GET)
	public String editPersonalData(@RequestParam(value="matricola", required=true) String matricola, Model model){

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//mi prendo l'utente loggato
		Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
		model.addAttribute("utenteLogged", utenteLogged);
		Utente utente = utenteService.findUtenteByMatricola(matricola);
		model.addAttribute("newUtente", utente);
		model.addAttribute("utente", utente);
		model.addAttribute("editDatiPersonali", true);
		model.addAttribute("nazioni", nazioneService.getAllNazione() );

		return View.studentView;

	}



	/**
	 * Quando chiedo di editare i dati personali dell'utente
	 * @return
	 */
	@RequestMapping(value = { Urls.submitEditStudentePath}, method = RequestMethod.POST)
	public ModelAndView submitEditPersonalData(@Valid @ModelAttribute("newUtente") Utente newUtente, BindingResult bindingResult,@RequestParam(value="matricola", required=true) String matricola,ModelMap model) {

		ModelAndView modelAndView;
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utente utenteLogged = utenteService.findUtenteByMatricola(auth.getName());
		
		Utente oldUtente= utenteService.findUtenteByMatricola(matricola);
		if (variabileAmbienteService.emailCheck()) {
			//controllo che non ci siano due utenti con la stessa mail
			Utente utenteExists = utenteService.findUtenteByEmail(newUtente.getEmail());

			if (utenteExists != null) {
				bindingResult
				.rejectValue("email", "error.user",
						"There is already a user registered with the email provided");
			}
		}

		if (bindingResult.getErrorCount()>1) {
			
			modelAndView = new ModelAndView();
			modelAndView.addObject("utenteLogged", utenteLogged);
			modelAndView.addObject("utente",oldUtente);
			model.addAttribute("newUtente", newUtente);
			model.addAttribute("editDatiPersonali", true);
			model.addAttribute("nazioni", nazioneService.getAllNazione() );
			modelAndView.setViewName(View.studentView);
	
		} else {
			newUtente=utenteService.saveUpdatedUser(oldUtente,newUtente);
			model.addAttribute("matricola",newUtente.getMatricola());
			model.addAttribute("key","editDatiPersonaliSuccessfully");
			model.addAttribute("value",true);
			
			logger.warn("UTENTE LOGGATO: MATRICOLA= "+utenteLogged.getMatricola()+" HA AGGIORNATO I DATI DELL'UTENTE MATRICOLA: "+newUtente.getMatricola());
			
			modelAndView= new ModelAndView(Urls.redirect+Urls.studentPath,model);
		}

		return modelAndView;

	}
	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------
	// fine gestione dati personali
	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------

}
