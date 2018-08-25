package com.nodelab.accademiaVillaDeiRomani.utility;

import org.springframework.stereotype.Component;

import com.nodelab.accademiaVillaDeiRomani.formBean.UtenteBean;
import com.nodelab.accademiaVillaDeiRomani.model.Utente;
import com.nodelab.accademiaVillaDeiRomani.model.UtenteHasCorso;

@Component
public class DataConverter {

	public Utente getModelUtenteByUtenteFormBean(UtenteBean utenteBean) {
		
		Utente utente= new Utente();
		
		utente.setBorseDiStudio(utenteBean.getBorseDiStudio());
		utente.setCodiceFiscale(utenteBean.getCodiceFiscale());
		utente.setCognome(utenteBean.getCognome());
		utente.setDataIscrizione(utenteBean.getDataIscrizione());
		utente.setDataNascita(utenteBean.getDataNascita());
		utente.setEmail(utenteBean.getEmail());
		utente.setIndirizzo(utenteBean.getIndirizzo());
		utente.setNazione(utenteBean.getNazione());
		utente.setNome(utenteBean.getNome());
		utente.setPassword(utenteBean.getPassword());
		utente.setProgetti(utenteBean.getProgetti());
		utente.setRole(utenteBean.getRole());
		utente.setSex(utenteBean.getSex());
		utente.setStage(utenteBean.getStage());
		utente.setTelefono(utenteBean.getTelefono());
		utente.setTirocini(utenteBean.getTirocini());
		utente.setCap(utenteBean.getCap());
		utente.setCitta(utenteBean.getCitta());
		
		// dovrebbero essere finiti
		
		
		return utente;
		
	}
	
	
	
	
}
