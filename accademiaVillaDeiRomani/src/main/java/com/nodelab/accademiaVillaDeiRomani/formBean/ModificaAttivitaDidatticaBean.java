package com.nodelab.accademiaVillaDeiRomani.formBean;

import java.util.List;

import com.nodelab.accademiaVillaDeiRomani.model.AttivitaDidattica;
import com.nodelab.accademiaVillaDeiRomani.model.Corso;

public class ModificaAttivitaDidatticaBean {
	
	private AttivitaDidattica attivitaDidattica;

	private List<Corso> corsiToAssoc;
	
	private List<Corso> corsiAssocObbligatori;
	
	private List<Corso> corsiPrimoAnno;
	
	private List<Corso> corsiSecondoAnno;

	private List<Corso> corsiTerzoAnno;

	
	private String nome;
	
	
	private Integer anno;

	public List<Corso> getCorsiToAssoc() {
		return corsiToAssoc;
	}

	public void setCorsiToAssoc(List<Corso> corsiToAssoc) {
		this.corsiToAssoc = corsiToAssoc;
	}

	public List<Corso> getCorsiAssocObbligatori() {
		return corsiAssocObbligatori;
	}

	public void setCorsiAssocObbligatori(List<Corso> corsiAssocObbligatori) {
		this.corsiAssocObbligatori = corsiAssocObbligatori;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getAnno() {
		return anno;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}

	public AttivitaDidattica getAttivitaDidattica() {
		return attivitaDidattica;
	}

	public void setAttivitaDidattica(AttivitaDidattica attivitaDidattica) {
		this.attivitaDidattica = attivitaDidattica;
	}

	public List<Corso> getCorsiPrimoAnno() {
		return corsiPrimoAnno;
	}

	public void setCorsiPrimoAnno(List<Corso> corsiPrimoAnno) {
		this.corsiPrimoAnno = corsiPrimoAnno;
	}

	public List<Corso> getCorsiSecondoAnno() {
		return corsiSecondoAnno;
	}

	public void setCorsiSecondoAnno(List<Corso> corsiSecondoAnno) {
		this.corsiSecondoAnno = corsiSecondoAnno;
	}

	public List<Corso> getCorsiTerzoAnno() {
		return corsiTerzoAnno;
	}

	public void setCorsiTerzoAnno(List<Corso> corsiTerzoAnno) {
		this.corsiTerzoAnno = corsiTerzoAnno;
	}
	
	
	
	
	
}
