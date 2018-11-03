package com.nodelab.accademiaVillaDeiRomani.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nodelab.accademiaVillaDeiRomani.model.AttivitaDidattica;

public interface AttivitaDidatticaRepository extends JpaRepository<AttivitaDidattica, Integer> {

	public List<AttivitaDidattica> findAllByOrderByNomeAsc();

	
}
