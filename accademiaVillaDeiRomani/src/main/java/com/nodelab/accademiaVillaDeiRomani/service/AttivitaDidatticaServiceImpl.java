package com.nodelab.accademiaVillaDeiRomani.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nodelab.accademiaVillaDeiRomani.formBean.ModificaAttivitaDidatticaBean;
import com.nodelab.accademiaVillaDeiRomani.model.AttivitaDidattica;
import com.nodelab.accademiaVillaDeiRomani.model.Corso;
import com.nodelab.accademiaVillaDeiRomani.model.CorsoHasAttivitaDidattica;
import com.nodelab.accademiaVillaDeiRomani.model.Utente;
import com.nodelab.accademiaVillaDeiRomani.model.UtenteHasAttivitaDidattica;
import com.nodelab.accademiaVillaDeiRomani.repository.AttivitaDidatticaRepository;
import com.nodelab.accademiaVillaDeiRomani.repository.CorsoHasAttivitaDidatticaRepository;


@Service("attivitaDidatticaService")
public class AttivitaDidatticaServiceImpl implements AttivitaDidatticaService{

	@Autowired
	private AttivitaDidatticaRepository attivitaDidatticaRepository;
	
	@Autowired
	private CorsoHasAttivitaDidatticaRepository corsoHasAttivitaDidatticaRepository;


	@Override
	public List<AttivitaDidattica> getListOfAttivitaDidattiche() {
		return attivitaDidatticaRepository.findAll();
	}

	@Override
	public List<AttivitaDidattica> getAllAttivitaDidatticheByUtente(Utente utente) {
		List<AttivitaDidattica> toReturn= new ArrayList<>();
		for (UtenteHasAttivitaDidattica utenteHasAttivitaDidattica:utente.getUtenteHasAttivitaDidatticaSet()) {
			toReturn.add(utenteHasAttivitaDidattica.getAttivitaDidattica());
		}
		return toReturn;
	}

	@Override
	public void save(@Valid AttivitaDidattica attivitaDidattica) {
		attivitaDidatticaRepository.save(attivitaDidattica);
	}

	@Override
	public void removeAttivitaDidattica(AttivitaDidattica attivitaDidattica) {
		
		attivitaDidatticaRepository.delete(attivitaDidattica);
		
	}

	@Override
	public void updateAttivitaDidattica(ModificaAttivitaDidatticaBean modificaAttivitaDidatticaBean) {
		//poiche attivita didattica è del bean per le jackson back reference i suoi corsoSet potrebbe essere null quindi lo riprendo dal database
		modificaAttivitaDidatticaBean.setAttivitaDidattica(attivitaDidatticaRepository.findById(modificaAttivitaDidatticaBean.getAttivitaDidattica().getIdAttivitaDidattica()).get());
		//cancello le vecchie associazioni corso-attivita didattica
		corsoHasAttivitaDidatticaRepository.deleteAll(modificaAttivitaDidatticaBean.getAttivitaDidattica().getCorsoHasAttivitaDidatticaSet());
		corsoHasAttivitaDidatticaRepository.flush();
		
		//salvo le nuove associazioni 
		List<Integer> idsPrimoAnno = new ArrayList<>();
		for (Corso corso: modificaAttivitaDidatticaBean.getCorsiPrimoAnno()) {
			idsPrimoAnno.add(corso.getIdCorso());
		}
		List<Integer> idsSecondoAnno = new ArrayList<>();
		for (Corso corso: modificaAttivitaDidatticaBean.getCorsiSecondoAnno()) {
			idsSecondoAnno.add(corso.getIdCorso());
		}
		List<Integer> idsTerzoAnno = new ArrayList<>();
		for (Corso corso: modificaAttivitaDidatticaBean.getCorsiTerzoAnno()) {
			idsTerzoAnno.add(corso.getIdCorso());
		}
		List<Integer> idsObbligatori = new ArrayList<>();
		for (Corso corso: modificaAttivitaDidatticaBean.getCorsiAssocObbligatori()) {
			idsObbligatori.add(corso.getIdCorso());
		}
		
		List<CorsoHasAttivitaDidattica> assocToSave= new ArrayList<>();
		for (Corso corso:modificaAttivitaDidatticaBean.getCorsiToAssoc()) {
			CorsoHasAttivitaDidattica corsoHasAttivitaDidattica = new CorsoHasAttivitaDidattica();
			corsoHasAttivitaDidattica.setCorso(corso);
			corsoHasAttivitaDidattica.setObbligatorio(idsObbligatori.contains(corso.getIdCorso()));
			if (idsTerzoAnno.contains(corso.getIdCorso())) {
				corsoHasAttivitaDidattica.setAnno(3);
			} else if (idsSecondoAnno.contains(corso.getIdCorso())) {
				corsoHasAttivitaDidattica.setAnno(2);
			} else {
				corsoHasAttivitaDidattica.setAnno(1);
			}
			corsoHasAttivitaDidattica.setAttivitaDidattica(modificaAttivitaDidatticaBean.getAttivitaDidattica());
			assocToSave.add(corsoHasAttivitaDidattica);
		}
		assocToSave=corsoHasAttivitaDidatticaRepository.saveAll(assocToSave);
		modificaAttivitaDidatticaBean.getAttivitaDidattica().setCorsoHasAttivitaDidatticaSet(new HashSet<CorsoHasAttivitaDidattica>(assocToSave));
		
		//aggiorno i dati dell'attività didattica
		modificaAttivitaDidatticaBean.getAttivitaDidattica().setCf(modificaAttivitaDidatticaBean.getCf());
		modificaAttivitaDidatticaBean.getAttivitaDidattica().setNome(modificaAttivitaDidatticaBean.getNome());

		attivitaDidatticaRepository.save(modificaAttivitaDidatticaBean.getAttivitaDidattica());
		
	}
	
}
