package com.nodelab.accademiaVillaDeiRomani.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nodelab.accademiaVillaDeiRomani.model.Corso;

public interface CorsoRepository extends JpaRepository<Corso, Integer> {

	Corso findByNome(String nome);

}
