package com.nodelab.accademiaVillaDeiRomani.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nodelab.accademiaVillaDeiRomani.model.Contributo;

public interface ContributoRepository  extends JpaRepository<Contributo, Integer> {

	List<Contributo> findByIdContributoNotIn(List<Integer> idsPayed);
 
}
