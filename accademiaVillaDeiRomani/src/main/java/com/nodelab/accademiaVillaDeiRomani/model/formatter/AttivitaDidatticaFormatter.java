package com.nodelab.accademiaVillaDeiRomani.model.formatter;

import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.nodelab.accademiaVillaDeiRomani.model.AttivitaDidattica;
import com.nodelab.accademiaVillaDeiRomani.repository.AttivitaDidatticaRepository;
import com.nodelab.accademiaVillaDeiRomani.service.CorsoService;
import com.nodelab.accademiaVillaDeiRomani.service.UtenteService;

@Component
public class AttivitaDidatticaFormatter implements Formatter<AttivitaDidattica> {

	@Autowired
	CorsoService corsoService;

	@Autowired
	UtenteService utenteService;

	@Autowired
	AttivitaDidatticaRepository attivitaDidatticaRepository;

	@Override
	public String print(AttivitaDidattica arg0, Locale arg1) {
		return arg0.getNome();
	}

	@Override
	public AttivitaDidattica parse(String arg0, Locale arg1) throws ParseException {
		Optional<AttivitaDidattica> attivitaDidattica = attivitaDidatticaRepository.findById(Integer.parseInt(arg0));
		if (attivitaDidattica.isPresent()) {
			return attivitaDidattica.get();
		} else {
			return null;
		}
		
	}

}
