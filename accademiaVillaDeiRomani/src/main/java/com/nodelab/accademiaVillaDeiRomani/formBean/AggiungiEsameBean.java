package com.nodelab.accademiaVillaDeiRomani.formBean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.nodelab.accademiaVillaDeiRomani.model.AttivitaDidattica;
import com.nodelab.accademiaVillaDeiRomani.model.Utente;

public class AggiungiEsameBean {

	private AttivitaDidattica attivitaDidattica;
	
	private Utente utente;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataEsame;
	
	private Integer votoEsame;

	public AttivitaDidattica getAttivitaDidattica() {
		return attivitaDidattica;
	}

	public void setAttivitaDidattica(AttivitaDidattica attivitaDidattica) {
		this.attivitaDidattica = attivitaDidattica;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public Date getDataEsame() {
		return dataEsame;
	}

	public void setDataEsame(Date dataEsame) {
		this.dataEsame = dataEsame;
	}

	public Integer getVotoEsame() {
		return votoEsame;
	}

	public void setVotoEsame(Integer votoEsame) {
		this.votoEsame = votoEsame;
	}

	
	
	
	
}
