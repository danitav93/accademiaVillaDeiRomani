package com.nodelab.accademiaVillaDeiRomani.service;

import java.util.List;

import javax.validation.Valid;

import com.nodelab.accademiaVillaDeiRomani.formBean.AggiungiEsameBean;
import com.nodelab.accademiaVillaDeiRomani.formBean.AggiungiTasseBean;
import com.nodelab.accademiaVillaDeiRomani.formBean.PercorsoFormativoBean;
import com.nodelab.accademiaVillaDeiRomani.model.Contributo;
import com.nodelab.accademiaVillaDeiRomani.model.RegistrationVerificationToken;
import com.nodelab.accademiaVillaDeiRomani.model.Utente;
import com.nodelab.accademiaVillaDeiRomani.report.bean.ReportStudenteBeanDataSource;

public interface UtenteService {

	public Utente findUtenteByEmail(String email);
	
	public Utente findUtenteById(int id);

	public void saveUser(Utente user); 
	
	public Utente saveNewRegisteredUser(Utente user);
	
	public String getNewMatricola();

	public void update(@Valid Utente utente);

	public @Valid Utente saveNewUserFromAdmin(@Valid Utente utente);

	public Utente findUtenteByMatricola(String matricola);

	public void abilitaUtenteByMatricola(String matricola);

	public List<ReportStudenteBeanDataSource> executeStudentReportRowQuery(String query);

	public void updatePercorsoFormativo(PercorsoFormativoBean percorsoFormativoBean);

	public void addTax(@Valid AggiungiTasseBean aggiungiTasseBean);

	public void addExam(@Valid AggiungiEsameBean aggiungiEsameBean);

	public @Valid Utente saveUpdatedUser(Utente oldUtente, @Valid Utente newUtente);

	public void removeTax(Utente utente, Contributo contributo);

	public void createPasswordResetTokenForUser(Utente utente, String token);

	public void changeUserPassword(Utente user, String password);

	public void createRegistrationverificationToken(String token, @Valid Utente utente);

	public RegistrationVerificationToken getRegistrationVerificationToken(String token);

	public void abilitaUtente(Utente utente);

	public void deleteUtente( Utente utente);

	

	

	
	
	
}
