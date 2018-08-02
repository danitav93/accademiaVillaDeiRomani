package com.nodelab.accademiaVillaDeiRomani.formBean;

import java.util.List;

import com.nodelab.accademiaVillaDeiRomani.model.AttivitaDidattica;
import com.nodelab.accademiaVillaDeiRomani.model.Corso;
import com.nodelab.accademiaVillaDeiRomani.model.Utente;

public class PercorsoFormativoBean {

	private Corso corso;
	private List<AttivitaDidattica> attivitaDidatticaFacoltativaList;
	private Utente utente;
	
	
	public Corso getCorso() {
		return corso;
	}
	public void setCorso(Corso corso) {
		this.corso = corso;
	}
	public List<AttivitaDidattica> getAttivitaDidatticaFacoltativaList() {
		return attivitaDidatticaFacoltativaList;
	}
	public void setAttivitaDidatticaFacoltativaList(List<AttivitaDidattica> attivitaDidatticaList) {
		this.attivitaDidatticaFacoltativaList = attivitaDidatticaList;
	}
	public Utente getUtente() {
		return utente;
	}
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	
	
	
	
}
