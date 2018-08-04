package com.nodelab.accademiaVillaDeiRomani.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nodelab.accademiaVillaDeiRomani.model.AttivitaDidattica;
import com.nodelab.accademiaVillaDeiRomani.model.Corso;
import com.nodelab.accademiaVillaDeiRomani.model.CorsoHasAttivitaDidattica;

public interface CorsoHasAttivitaDidatticaRepository extends JpaRepository<CorsoHasAttivitaDidattica, Integer> {

	CorsoHasAttivitaDidattica findByAttivitaDidatticaAndCorso(AttivitaDidattica attivitaDidattica, Corso corso);

}
