package com.nodelab.accademiaVillaDeiRomani.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nodelab.accademiaVillaDeiRomani.model.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Integer> {
	
	Utente findByEmail(String email);

	Utente findByMatricola(String matricola);
	
}
