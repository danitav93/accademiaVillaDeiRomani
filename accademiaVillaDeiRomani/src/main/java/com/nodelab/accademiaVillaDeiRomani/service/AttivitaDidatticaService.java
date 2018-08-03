package com.nodelab.accademiaVillaDeiRomani.service;

import java.util.List;

import com.nodelab.accademiaVillaDeiRomani.model.AttivitaDidattica;
import com.nodelab.accademiaVillaDeiRomani.model.Utente;

public interface AttivitaDidatticaService {

	List<AttivitaDidattica> getListOfAttivitaDidattiche();

	List<AttivitaDidattica> getAllAttivitaDidatticheByUtente(Utente utente);




	
}
