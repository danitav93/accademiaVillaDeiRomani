package com.nodelab.accademiaVillaDeiRomani.service;

import java.util.List;

import javax.validation.Valid;

import com.nodelab.accademiaVillaDeiRomani.model.Corso;
import com.nodelab.accademiaVillaDeiRomani.model.CorsoHasAttivitaDidattica;
import com.nodelab.accademiaVillaDeiRomani.model.Utente;
import com.nodelab.accademiaVillaDeiRomani.model.UtenteHasCorso;

public interface CorsoService {

	List<Corso> getListOfCorsi();

	List<UtenteHasCorso> getListOfUtenteHasCorso(Utente utente);

	Corso getCorsoByName(String string);

	Corso getCorsoById(int id);

	Corso save(@Valid Corso corso);

	void removeCorso(Corso corso);

	CorsoHasAttivitaDidattica saveCorsoHasAttivitaDidattica(@Valid CorsoHasAttivitaDidattica corsoHasAttivitaDidattica);

	void removeCorsoHasAttivitaDidattica(CorsoHasAttivitaDidattica corsoHasAttivitaDidattica);

	void updateCorsoHasAttivitaDidattica(@Valid CorsoHasAttivitaDidattica corsoHasAttivitaDidattica);

	
	
	
}
