package com.nodelab.accademiaVillaDeiRomani.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.nodelab.accademiaVillaDeiRomani.constant.Urls;
import com.nodelab.accademiaVillaDeiRomani.constant.View;
import com.nodelab.accademiaVillaDeiRomani.formBean.PasswordBean;
import com.nodelab.accademiaVillaDeiRomani.formBean.UtenteBean;
import com.nodelab.accademiaVillaDeiRomani.model.RegistrationVerificationToken;
import com.nodelab.accademiaVillaDeiRomani.model.Utente;
import com.nodelab.accademiaVillaDeiRomani.service.MailService;
import com.nodelab.accademiaVillaDeiRomani.service.MessageService;
import com.nodelab.accademiaVillaDeiRomani.service.NazioneService;
import com.nodelab.accademiaVillaDeiRomani.service.SecurityService;
import com.nodelab.accademiaVillaDeiRomani.service.UtenteService;
import com.nodelab.accademiaVillaDeiRomani.service.VariabileAmbienteService;
import com.nodelab.accademiaVillaDeiRomani.utility.DataConverter;

/**
 * controller che gestisce la registrazione
 * @author User
 *
 */
@Controller
public class RegistrationController {

	private Logger logger = LoggerFactory.getLogger(RegistrationController.class);


	@Autowired
	private UtenteService utenteService;


	@Autowired
	private MailService mailService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private NazioneService nazioneService;
	
	@Autowired
	private DataConverter dataConverter;
	
	@Autowired
	private VariabileAmbienteService variabileAmbienteService;

	/**
	 * Quando richiedo la pagina di registrazione
	 * @return
	 */
	@RequestMapping(value= {Urls.registrationPath}, method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		UtenteBean utenteBean = new UtenteBean();
		modelAndView.addObject("utenteBean", utenteBean);
		modelAndView.setViewName(View.registrationView);
		modelAndView.addObject("nazioni", nazioneService.getAllNazione() );
		
		return modelAndView;
	}

	/**
	 * Quando richiedo l attivazione di un utente tramite link email
	 * @return
	 */
	@RequestMapping(value= {Urls.abilitaUtentePath}, method = RequestMethod.GET)
	public ModelAndView abilitaUtente(@RequestParam(value = "token", required = true) String token,ModelMap model){
		RegistrationVerificationToken verificationToken = utenteService.getRegistrationVerificationToken(token);
		if (verificationToken==null) {
			model.addAttribute("parameter",messageService.getMessage("erroreLinkNonValido"));
			return new ModelAndView(Urls.redirect+Urls.loginPath,model);
		}
		if (!securityService.validateRegistrationVerificationToken(verificationToken)) {
			model.addAttribute("parameter",messageService.getMessage("expired"));
			return new ModelAndView(Urls.redirect+Urls.loginPath,model);
		}
		Utente utente = verificationToken.getUtente();
		utenteService.abilitaUtente(utente);
		model.addAttribute("parameter",messageService.getMessage("utenteAbilitatoConSuccesso"));

		logger.info("UTENTE ABILITATO TRAMITE MAIL: MATRICOLA= "+utente.getMatricola());


		return new ModelAndView(Urls.redirect+Urls.loginPath,model);
	}



	/**
	 * Quando provo ad effettuare la registrazione
	 * @param user
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = { Urls.submitRegistrationPath}, method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid @ModelAttribute("utenteBean") UtenteBean utenteBean, BindingResult bindingResult, HttpServletRequest request,ModelMap model) {
		ModelAndView modelAndView = new ModelAndView();
		if (variabileAmbienteService.emailCheck()) {
			//controllo che non ci siano due utenti con la stessa mail
			Utente utenteExists = utenteService.findUtenteByEmail(utenteBean.getEmail());
			if (utenteExists != null) {
				bindingResult
				.rejectValue("email", "error.user",
						"There is already a user registered with the email provided");
			}
		}
		//se c'è solo un errore so che è il ruolo (lo setto automaticamente dopo)
		if (bindingResult.hasErrors() && bindingResult.getAllErrors().size()>1) {
			modelAndView.setViewName(View.registrationView);
			modelAndView.addObject("nazioni", nazioneService.getAllNazione() );
			modelAndView.addObject("focus", ((FieldError)(bindingResult.getAllErrors().get(0))).getField());
		} else {
			Utente utente = dataConverter.getModelUtenteByUtenteFormBean(utenteBean); 
			utente=utenteService.saveNewRegisteredUser(utente);
			//mandiamo una mail con il link di conferma
			String token = UUID.randomUUID().toString();
			utenteService.createRegistrationverificationToken(token,utente);
			try {
				mailService.sendRegistrationMail(utente.getEmail(), messageService.getMessage("subjectRegistrationMail") ,new Integer(utente.getMatricola()).toString(),token);
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("parameter",messageService.getMessage("erroreDuranteLaRegistrazione"));
				return new ModelAndView(Urls.redirect+Urls.loginPath,model);

			}
			model.addAttribute("matricola", utente.getMatricola());
			modelAndView=new ModelAndView(Urls.redirect+Urls.registrazioneAvvenutaConSuccessoPath,model);

			logger.info("UTENTE REGISTRATO: MATRICOLA= "+utente.getMatricola());

		}
		return modelAndView;
	}

	/**
	 * Quando richiedo la pagina di registrazione avvenuta con successo
	 * @return
	 */
	@RequestMapping(value= {Urls.registrazioneAvvenutaConSuccessoPath}, method = RequestMethod.GET)
	public ModelAndView openRegistrationSucceded(@RequestParam(value = "matricola", required = true) String matricola){
		ModelAndView modelAndView = new ModelAndView();
		//mi prendo l'utente loggato e l'utente della student view
		Utente utente = utenteService.findUtenteByMatricola(matricola);
		modelAndView.addObject("utente",utente);
		modelAndView.setViewName(View.registrationSucceded);
		return modelAndView;
	}

	/**
	 * Quando richiedo la pagina di reset password
	 * @return
	 */
	@RequestMapping(value= {Urls.openResetPasswordView}, method = RequestMethod.GET)
	public ModelAndView openResetPassword(@RequestParam(value = "success", required = false) Boolean success,@RequestParam(value = "error", required = false) String error){
		ModelAndView modelAndView = new ModelAndView();

		if (success!=null) {
			modelAndView.addObject("success",success);
		}
		if (error!=null) {
			modelAndView.addObject("error",error);
		}
		modelAndView.setViewName(View.forgotPassword);
		return modelAndView;
	}

	/**
	 * Quando inserisco la mail per farmi mandare il link per resettare
	 * @param userEmail
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {Urls.resetPassword}, method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView resetPassword(@RequestParam(value="email",required=true) String userEmail, ModelMap model) {



		Utente utente = utenteService.findUtenteByEmail(userEmail);
		if (utente == null) {
			model.addAttribute("error",messageService.getMessage("notEmailFound"));
		} else {

			String token = UUID.randomUUID().toString();
			utenteService.createPasswordResetTokenForUser(utente, token);
			try {
				mailService.sendResetPasswordMail(userEmail,messageService.getMessage("resetPasswordEmailSubject"),token, utente);
			} catch (Exception e) {
				model.addAttribute("error",messageService.getMessage("erroreDuranteOperazione"));
				logger.info("UTENTE HA RICHIESTO RESET PASSWORD: EMAIL= "+userEmail);
				return new ModelAndView(Urls.redirect+Urls.openResetPasswordView,model);	
			}
			model.addAttribute("success",true);

			logger.info("UTENTE HA RICHIESTO RESET PASSWORD: EMAIL= "+userEmail);
		}
		return new ModelAndView(Urls.redirect+Urls.openResetPasswordView,model);	

	}

	/**
	 * Quando click il link della mail per resettare la password
	 * @param idUtente
	 * @param token
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {Urls.resetPasswordMailConfirmationPath}, method = RequestMethod.GET)
	public ModelAndView showChangePasswordPage(@RequestParam(value="idUtente",required=true) Integer idUtente, @RequestParam(value="token",required=true) String token, ModelMap model) {
		String result = securityService.validatePasswordResetToken(idUtente, token);
		if (result != null) {
			model.addAttribute("error",messageService.getMessage(result));
			return new ModelAndView(Urls.redirect+Urls.openResetPasswordView,model);
		}
		return new ModelAndView(Urls.redirect+Urls.openUpdatePasswordView);
	}


	/**
	 * Quando salvo la nuova password
	 * @param passwordBean
	 * @return
	 */
	@RequestMapping(value = {Urls.updatePassword}, method = RequestMethod.POST)
	public ModelAndView savePassword(@Valid @ModelAttribute("passwordBean") PasswordBean passwordBean,BindingResult bindingResult,ModelMap model) {
		if (bindingResult.hasErrors()) {
			ModelAndView modelAndView=new ModelAndView();
			modelAndView.addObject("passwordBean",passwordBean);
			modelAndView.setViewName(View.updatePassword);
			return modelAndView;
		}
		Utente user = (Utente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		utenteService.changeUserPassword(user, passwordBean.getPassword());
		model.addAttribute("parameter","Password aggiornata con successo");

		logger.info("UTENTE HA RESETTATO LA PASSWORD: MATRICOLA= "+user.getMatricola());


		return new ModelAndView(Urls.redirect+Urls.loginPath,model);
	}

	/**
	 * Quando richiedo la pagina di update password
	 * @return
	 */
	@RequestMapping(value= {Urls.openUpdatePasswordView}, method = RequestMethod.GET)
	public ModelAndView openUpdatePasswordView(){
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.addObject("passwordBean",new PasswordBean());
		modelAndView.setViewName(View.updatePassword);
		return modelAndView;
	}

}
