package com.nodelab.accademiaVillaDeiRomani.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nodelab.accademiaVillaDeiRomani.model.Nazione;
import com.nodelab.accademiaVillaDeiRomani.repository.NazioneRepository;

@Service("nazionalitàService")
public class NazioneServiceImpl implements NazioneService{

	@Autowired
	private NazioneRepository nazionalitàRepository;



	@Override
	public List<Nazione> getAllNazione() {
		return nazionalitàRepository.findAll();
	
	}

}