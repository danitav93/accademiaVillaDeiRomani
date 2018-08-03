package com.nodelab.accademiaVillaDeiRomani.service;

import java.util.List;

import com.nodelab.accademiaVillaDeiRomani.model.Contributo;
import com.nodelab.accademiaVillaDeiRomani.model.Utente;

public interface ContributoService {

	List<Contributo> getAllContributi();

	List<Contributo> getContributiNotPayedByUtente(Utente utente);

	List<Contributo> getContributiPayedByUtente(Utente utente);

	
}
