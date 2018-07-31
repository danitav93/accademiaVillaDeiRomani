package com.nodelab.accademiaVillaDeiRomani.report.bean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.nodelab.accademiaVillaDeiRomani.model.AttivitaDidattica;
import com.nodelab.accademiaVillaDeiRomani.model.Contributo;
import com.nodelab.accademiaVillaDeiRomani.model.Corso;

/*
 * Questo bean è utilizzato per i di filtri sul report degli studenti
 */
public class ReportStudenteBean {

	private Corso corso;
	private AttivitaDidattica attivitaDidattica;
	private boolean esameSostenuto=false;
	private Contributo contributo;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date aImmatricolazioneDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date daImmatricolazioneDate;
	
	private Integer  daAnni;
	private Integer  aAnni;
	private Integer  sex;
	
	public Corso getCorso() {
		return corso;
	}
	public void setCorso(Corso corso) {
		this.corso = corso;
	}
	public AttivitaDidattica getAttivitaDidattica() {
		return attivitaDidattica;
	}
	public void setAttivitaDidattica(AttivitaDidattica attivitaDidattica) {
		this.attivitaDidattica = attivitaDidattica;
	}
	public boolean isEsameSostenuto() {
		return esameSostenuto;
	}
	public void setEsameSostenuto(boolean esameSostenuto) {
		this.esameSostenuto = esameSostenuto;
	}
	public Contributo getContributo() {
		return contributo;
	}
	public void setContributo(Contributo contributo) {
		this.contributo = contributo;
	}
	public Date getaImmatricolazioneDate() {
		return aImmatricolazioneDate;
	}
	public void setaImmatricolazioneDate(Date aImmatricolazioneDate) {
		this.aImmatricolazioneDate = aImmatricolazioneDate;
	}
	public Date getDaImmatricolazioneDate() {
		return daImmatricolazioneDate;
	}
	public void setDaImmatricolazioneDate(Date daImmatricolazioneDate) {
		this.daImmatricolazioneDate = daImmatricolazioneDate;
	}
	public Integer getDaAnni() {
		return daAnni;
	}
	public void setDaAnni(Integer daAnni) {
		this.daAnni = daAnni;
	}
	public Integer getaAnni() {
		return aAnni;
	}
	public void setaAnni(Integer aAnni) {
		this.aAnni = aAnni;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
	
	
	
	
	
}
