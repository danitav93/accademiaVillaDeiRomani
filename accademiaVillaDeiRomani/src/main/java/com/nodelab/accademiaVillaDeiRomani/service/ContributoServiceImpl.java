package com.nodelab.accademiaVillaDeiRomani.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nodelab.accademiaVillaDeiRomani.model.Contributo;
import com.nodelab.accademiaVillaDeiRomani.repository.ContributoRepository;

@Service("contributoService")
public class ContributoServiceImpl implements ContributoService{

	@Autowired
	private ContributoRepository contributoRepository;

	@Override
	public List<Contributo> getAllContributi() {
		return contributoRepository.findAll();
	}
	
}
