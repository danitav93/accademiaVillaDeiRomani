package com.nodelab.accademiaVillaDeiRomani.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nodelab.accademiaVillaDeiRomani.model.AttivitaDidattica;
import com.nodelab.accademiaVillaDeiRomani.model.Utente;
import com.nodelab.accademiaVillaDeiRomani.model.UtenteHasAttivitaDidattica;
import com.nodelab.accademiaVillaDeiRomani.repository.AttivitaDidatticaRepository;


@Service("attivitaDidatticaService")
public class AttivitaDidatticaServiceImpl implements AttivitaDidatticaService{

	@Autowired
	private AttivitaDidatticaRepository attivitaDidatticaRepository;

	@Override
	public List<AttivitaDidattica> getListOfAttivitaDidattiche() {
		return attivitaDidatticaRepository.findAll();
	}

	@Override
	public List<AttivitaDidattica> getAllAttivitaDidatticheByUtente(Utente utente) {
		List<AttivitaDidattica> toReturn= new ArrayList<>();
		for (UtenteHasAttivitaDidattica utenteHasAttivitaDidattica:utente.getUtenteHasAttivitaDidatticaSet()) {
			toReturn.add(utenteHasAttivitaDidattica.getAttivitaDidattica());
		}
		return toReturn;
	}
	
}
