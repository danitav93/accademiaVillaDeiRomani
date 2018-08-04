package com.nodelab.accademiaVillaDeiRomani.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="corso_has_attivita_didattica")
public class CorsoHasAttivitaDidattica implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_corso_has_attivita_didattica")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idCorsoHasAttivitaDidattica;
	
	@ManyToOne
	@JoinColumn(name = "id_corso")
	@JsonBackReference
	private Corso corso;
	
	@ManyToOne
	@JoinColumn(name = "id_attivita_didattica")
	@JsonManagedReference
	private AttivitaDidattica attivitaDidattica;
	
	@Column(name="obbligatorio")
	private boolean obbligatorio;
	
	@Column(name="anno")
	private Integer anno;

	public Integer getAnno() {
		return anno;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}

	public Integer getIdCorsoHasAttivitaDidattica() {
		return idCorsoHasAttivitaDidattica;
	}

	public void setIdCorsoHasAttivitaDidattica(Integer idCorsoHasAttivitaDidattica) {
		this.idCorsoHasAttivitaDidattica = idCorsoHasAttivitaDidattica;
	}

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

	public boolean isObbligatorio() {
		return obbligatorio;
	}

	public void setObbligatorio(boolean obbligatorio) {
		this.obbligatorio = obbligatorio;
	}
	
	
}
