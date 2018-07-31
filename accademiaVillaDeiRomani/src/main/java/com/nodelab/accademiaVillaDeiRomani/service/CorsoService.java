package com.nodelab.accademiaVillaDeiRomani.service;

import java.util.List;

import com.nodelab.accademiaVillaDeiRomani.model.Corso;
import com.nodelab.accademiaVillaDeiRomani.model.Utente;
import com.nodelab.accademiaVillaDeiRomani.model.UtenteHasCorso;

public interface CorsoService {

	List<Corso> getListOfCorsi();

	List<UtenteHasCorso> getListOfUtenteHasCorso(Utente utente);

	Corso getCorsoByName(String string);

	Corso getCorsoById(int id);

	
	
	
}
