package com.nodelab.accademiaVillaDeiRomani.formBean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.nodelab.accademiaVillaDeiRomani.model.Contributo;
import com.nodelab.accademiaVillaDeiRomani.model.Utente;

public class AggiungiTasseBean {
	
	private Contributo contributo;
	private Double importo;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date data;
	
	private Utente utente;
	
	
	public Contributo getContributo() {
		return contributo;
	}
	public void setContributo(Contributo contributo) {
		this.contributo = contributo;
	}
	public Double getImporto() {
		return importo;
	}
	public void setImporto(Double importo) {
		this.importo = importo;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Utente getUtente() {
		return utente;
	}
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	
	

}
