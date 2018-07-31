package com.nodelab.accademiaVillaDeiRomani.service;

import javax.validation.Valid;

import com.nodelab.accademiaVillaDeiRomani.model.Utente;

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

	
	
	
}
