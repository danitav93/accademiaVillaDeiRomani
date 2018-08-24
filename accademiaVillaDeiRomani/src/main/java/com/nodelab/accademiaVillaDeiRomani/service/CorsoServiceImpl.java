package com.nodelab.accademiaVillaDeiRomani.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nodelab.accademiaVillaDeiRomani.model.Corso;
import com.nodelab.accademiaVillaDeiRomani.model.CorsoHasAttivitaDidattica;
import com.nodelab.accademiaVillaDeiRomani.model.Utente;
import com.nodelab.accademiaVillaDeiRomani.model.UtenteHasCorso;
import com.nodelab.accademiaVillaDeiRomani.repository.CorsoHasAttivitaDidatticaRepository;
import com.nodelab.accademiaVillaDeiRomani.repository.CorsoRepository;

@Service("corsoService")
public class CorsoServiceImpl implements CorsoService {

	@Autowired
	private CorsoRepository corsoRepository;

	@Autowired
	private CorsoHasAttivitaDidatticaRepository corsoHasAttivitaDidatticaRepository;
	
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

	@Override
	public Corso save(@Valid Corso corso) {
		return corsoRepository.save(corso);
		
	}

	@Override
	public void removeCorso(Corso corso) {
		corsoRepository.delete(corso);
	}

	@Override
	public CorsoHasAttivitaDidattica saveCorsoHasAttivitaDidattica(@Valid CorsoHasAttivitaDidattica corsoHasAttivitaDidattica) {
		return corsoHasAttivitaDidatticaRepository.save(corsoHasAttivitaDidattica);
		
	}

	@Override
	public void removeCorsoHasAttivitaDidattica(CorsoHasAttivitaDidattica corsoHasAttivitaDidattica) {
		CorsoHasAttivitaDidattica corsoHasAttivitaDidatticaToDelete=corsoHasAttivitaDidatticaRepository.findByAttivitaDidatticaAndCorso(corsoHasAttivitaDidattica.getAttivitaDidattica(),corsoHasAttivitaDidattica.getCorso());
		
		corsoHasAttivitaDidatticaRepository.delete(corsoHasAttivitaDidatticaToDelete);	
	}

	@Override
	public void updateCorsoHasAttivitaDidattica(@Valid CorsoHasAttivitaDidattica corsoHasAttivitaDidattica) {
		CorsoHasAttivitaDidattica corsoHasAttivitaDidatticaToSave=corsoHasAttivitaDidatticaRepository.findByAttivitaDidatticaAndCorso(corsoHasAttivitaDidattica.getAttivitaDidattica(),corsoHasAttivitaDidattica.getCorso());
		corsoHasAttivitaDidatticaToSave.setAnno(corsoHasAttivitaDidattica.getAnno());
		corsoHasAttivitaDidatticaToSave.setObbligatorio(corsoHasAttivitaDidattica.isObbligatorio());
		corsoHasAttivitaDidatticaToSave.setCf(corsoHasAttivitaDidattica.getCf());
		corsoHasAttivitaDidatticaRepository.save(corsoHasAttivitaDidatticaToSave);
	}
	
	
	
}
