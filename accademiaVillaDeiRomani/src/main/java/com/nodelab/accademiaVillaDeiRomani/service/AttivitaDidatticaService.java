package com.nodelab.accademiaVillaDeiRomani.service;

import java.util.List;

import javax.validation.Valid;

import com.nodelab.accademiaVillaDeiRomani.formBean.ModificaAttivitaDidatticaBean;
import com.nodelab.accademiaVillaDeiRomani.model.AttivitaDidattica;
import com.nodelab.accademiaVillaDeiRomani.model.Utente;

public interface AttivitaDidatticaService {

	List<AttivitaDidattica> getListOfAttivitaDidattiche();

	List<AttivitaDidattica> getAllAttivitaDidatticheByUtente(Utente utente);

	void save(@Valid AttivitaDidattica attivitaDidattica);

	void removeAttivitaDidattica(AttivitaDidattica attivitaDidattica);

	void updateAttivitaDidattica(ModificaAttivitaDidatticaBean modificaAttivitaDidatticaBean);




	
}
