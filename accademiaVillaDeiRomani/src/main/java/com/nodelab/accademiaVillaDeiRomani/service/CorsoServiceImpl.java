package com.nodelab.accademiaVillaDeiRomani.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nodelab.accademiaVillaDeiRomani.model.Corso;
import com.nodelab.accademiaVillaDeiRomani.model.Utente;
import com.nodelab.accademiaVillaDeiRomani.model.UtenteHasCorso;
import com.nodelab.accademiaVillaDeiRomani.repository.CorsoRepository;

@Service("corsoService")
public class CorsoServiceImpl implements CorsoService {

	@Autowired
	private CorsoRepository corsoRepository;

	@Override
	public List<Corso> getListOfCorsi() {
		return corsoRepository.findAll();
	}

	@Override
	public List<UtenteHasCorso> getListOfUtenteHasCorso(Utente utente) {
		
		List<UtenteHasCorso> toReturn = new ArrayList<>();
		
		List<Corso> corsi = getListOfCorsi();
		
	
		for (Corso corso : corsi) {
			UtenteHasCorso utenteHasCorso = new UtenteHasCorso();
			utenteHasCorso.setCorso(corso);
			utenteHasCorso.setUtente(utente);
			toReturn.add(utenteHasCorso);
		}
				
		return toReturn;
	}

	@Override
	public Corso getCorsoByName(String nome) {
		return corsoRepository.findByNome(nome);
	}

	@Override
	public Corso getCorsoById(int id) {
		return corsoRepository.findById(id).get();
	}
	
	
	
}
