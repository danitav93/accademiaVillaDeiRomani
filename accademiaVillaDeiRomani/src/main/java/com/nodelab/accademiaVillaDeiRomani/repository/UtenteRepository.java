package com.nodelab.accademiaVillaDeiRomani.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nodelab.accademiaVillaDeiRomani.model.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Integer> {
	
	Utente findByEmail(String email);

	Utente findByMatricola(String matricola);

	@Modifying
	@Query("UPDATE utente u SET u.hasPercorsoFormativo = :hasPercorsoFormativo WHERE u.idUtente = :idUtente")
	void setUtenteHasPercorsoFormativoById(@Param("idUtente")int  idUtente,@Param("hasPercorsoFormativo") boolean hasPercorsoFormativo);

	List<Utente> findByNomeIgnoreCaseAndCognomeIgnoreCase(String nome,String cognome);

	List<Utente> findByCognomeIgnoreCase(String cognome);
	
}
