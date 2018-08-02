package com.nodelab.accademiaVillaDeiRomani.service;

import java.util.List;

import javax.validation.Valid;

import com.nodelab.accademiaVillaDeiRomani.formBean.AggiungiTasseBean;
import com.nodelab.accademiaVillaDeiRomani.formBean.PercorsoFormativoBean;
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

	
	
	
}
