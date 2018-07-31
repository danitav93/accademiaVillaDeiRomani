package com.nodelab.accademiaVillaDeiRomani.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nodelab.accademiaVillaDeiRomani.constant.Ruoli;
import com.nodelab.accademiaVillaDeiRomani.model.Matricola;
import com.nodelab.accademiaVillaDeiRomani.model.Utente;
import com.nodelab.accademiaVillaDeiRomani.repository.MatricolaRepository;
import com.nodelab.accademiaVillaDeiRomani.repository.RoleRepository;
import com.nodelab.accademiaVillaDeiRomani.repository.UtenteHasCorsoRepository;
import com.nodelab.accademiaVillaDeiRomani.repository.UtenteRepository;
import com.nodelab.accademiaVillaDeiRomani.utility.TimeAndDate;

@Service("utenteService")
public class UtenteServiceImpl implements UtenteService  {

	@Autowired
	private UtenteRepository utenteRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UtenteHasCorsoRepository utenteHasCorsoRepository;
	
	@Autowired
	private MatricolaRepository matricolaRepository;

	@Override
	public Utente findUtenteByEmail(String email) {
		return utenteRepository.findByEmail(email);
	}

	@Override
	public Utente findUtenteById(int id) {
		return utenteRepository.findById(id).get();
	}

	@Override
	public void saveUser(Utente utente) {
		utenteRepository.save(utente);
	}

	/**
	 * prendo il valore dal record che sarà la matricola dell utente che si registra. Poi aumento il valore
	 */
	@Override
	public String getNewMatricola() {
		Matricola matricola= matricolaRepository.findById(1).get();
		Integer matricolaToReturn=matricola.getValue();
		matricola.setValue(matricola.getValue()+1);
		return matricolaToReturn.toString();
	}

	

	@Override
	public Utente saveNewRegisteredUser(Utente utente) {
		utente.setPassword(bCryptPasswordEncoder.encode(utente.getPassword()));
		utente.setMatricola(getNewMatricola());
		utente.setDataIscrizione(TimeAndDate.getCurrentDate());
		utente.setRole(roleRepository.findByName(Ruoli.ruolo_studente));
		utente.setActive(0);
		return utenteRepository.save(utente);
	}

	@Override
	public void update(@Valid Utente utente) {
		
		Utente oldUtente = findUtenteById(utente.getIdUtente());
		utente.setPassword(oldUtente.getPassword());
		utente.setDataIscrizione(oldUtente.getDataIscrizione());
		utente.setMatricola(oldUtente.getMatricola());
		utente.setActive(oldUtente.getActive());

		
		//cancello le relazioni vecchie
		if (oldUtente.getUtenteHasCorso()!=null) {
			utenteHasCorsoRepository.delete(oldUtente.getUtenteHasCorso());
		}
		
		//salvo quelle nuove
		utenteHasCorsoRepository.save(utente.getUtenteHasCorso());
		
		//salvo l'utente 
		utenteRepository.save(utente);
		
	}

	@Override
	public Utente saveNewUserFromAdmin(@Valid Utente utente) {
		utente.setPassword(bCryptPasswordEncoder.encode(utente.getPassword()));
		utente.setMatricola(getNewMatricola());
		utente.setDataIscrizione(TimeAndDate.getCurrentDate());
		utente.setActive(0);
		return utenteRepository.save(utente);
		
	}

	@Override
	public Utente findUtenteByMatricola(String matricola) {
		return utenteRepository.findByMatricola(matricola);
	}

	@Override
	public void abilitaUtenteByMatricola(String matricola) {
		Utente utente=utenteRepository.findByMatricola(matricola);
		
		utente.setActive(1);
		
		utenteRepository.save(utente);
		
		return;
	}

	





}
