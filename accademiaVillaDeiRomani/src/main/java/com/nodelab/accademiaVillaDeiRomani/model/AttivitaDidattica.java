package com.nodelab.accademiaVillaDeiRomani.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="attivita_didattica")
public class AttivitaDidattica implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id_attivita_didattica")
	private int idAttivitaDidattica;
	
	private String nome; 
	
	private int cf;
	
	@OneToMany(mappedBy = "attivitaDidattica")
	@JsonBackReference
	private Set<UtenteHasAttivitaDidattica> utenteHasAttivitaDidatticaSet;
	
	@OneToMany(mappedBy = "attivitaDidattica")
	@JsonBackReference
	private Set<CorsoHasAttivitaDidattica> corsoHasAttivitaDidatticaSet;

	public int getIdAttivitaDidattica() {
		return idAttivitaDidattica;
	}

	public void setIdAttivitaDidattica(int idAttivitaDidattica) {
		this.idAttivitaDidattica = idAttivitaDidattica;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getCf() {
		return cf;
	}

	public void setCf(int cf) {
		this.cf = cf;
	}

	public Set<UtenteHasAttivitaDidattica> getUtenteHasAttivitaDidatticaSet() {
		return utenteHasAttivitaDidatticaSet;
	}

	public void setUtenteHasAttivitaDidatticaSet(Set<UtenteHasAttivitaDidattica> utenteHasAttivitaDidatticaSet) {
		this.utenteHasAttivitaDidatticaSet = utenteHasAttivitaDidatticaSet;
	}

	public Set<CorsoHasAttivitaDidattica> getCorsoHasAttivitaDidatticaSet() {
		return corsoHasAttivitaDidatticaSet;
	}

	public void setCorsoHasAttivitaDidatticaSet(Set<CorsoHasAttivitaDidattica> corsoHasAttivitaDidatticaSet) {
		this.corsoHasAttivitaDidatticaSet = corsoHasAttivitaDidatticaSet;
	}
	
	
}
