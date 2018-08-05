package com.nodelab.accademiaVillaDeiRomani.service;

import java.util.List;

import javax.validation.Valid;

import com.nodelab.accademiaVillaDeiRomani.model.Contributo;
import com.nodelab.accademiaVillaDeiRomani.model.Utente;

public interface ContributoService {

	List<Contributo> getAllContributi();

	List<Contributo> getContributiNotPayedByUtente(Utente utente);

	List<Contributo> getContributiPayedByUtente(Utente utente);

	Contributo save(@Valid Contributo contributo);

	void removeContributo(Contributo contributo);

	
}
