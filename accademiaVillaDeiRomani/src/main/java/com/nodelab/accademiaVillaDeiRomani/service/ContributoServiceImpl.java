package com.nodelab.accademiaVillaDeiRomani.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nodelab.accademiaVillaDeiRomani.model.Contributo;
import com.nodelab.accademiaVillaDeiRomani.model.Utente;
import com.nodelab.accademiaVillaDeiRomani.model.UtenteHasContributo;
import com.nodelab.accademiaVillaDeiRomani.repository.ContributoRepository;

@Service("contributoService")
public class ContributoServiceImpl implements ContributoService{

	@Autowired
	private ContributoRepository contributoRepository;



	@Override
	public List<Contributo> getAllContributi() {
		return contributoRepository.findAll();
	}

	@Override
	public List<Contributo> getContributiNotPayedByUtente(Utente utente) {
		List<Integer> idsPayed = new ArrayList<>();

		for (UtenteHasContributo utenteHasContributo:utente.getUtenteHasContributiSet()) {
			idsPayed.add(utenteHasContributo.getContributo().getIdContributo());
		}
		if (!idsPayed.isEmpty()) {
			return contributoRepository.findByIdContributoNotIn(idsPayed);
		} else {
			return contributoRepository.findAll();
		}

	}

	@Override
	public List<Contributo> getContributiPayedByUtente(Utente utente) {
		List<Contributo> toReturn = new ArrayList<>();
		for (UtenteHasContributo utenteHasContributo:utente.getUtenteHasContributiSet()) {
			toReturn.add(utenteHasContributo.getContributo());
		}
		return toReturn;
	}

	@Override
	public void save(@Valid Contributo contributo) {
		contributoRepository.save(contributo);		
	}

	@Override
	public void removeContributo(Contributo contributo) {
		contributoRepository.delete(contributo);
		
	}

}
