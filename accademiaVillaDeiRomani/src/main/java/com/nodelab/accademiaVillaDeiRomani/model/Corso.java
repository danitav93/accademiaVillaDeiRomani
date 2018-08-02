package com.nodelab.accademiaVillaDeiRomani.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@NamedQuery(name="Corso.findAll", query="SELECT co FROM Corso co")
public class Corso implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id_corso")
	private int idCorso;
	
	@Column(name="nome")
	private String nome; 
	
	@OneToMany(mappedBy = "corso")
	@JsonBackReference
	private Set<UtenteHasCorso> utenteHasCorsoSet;
	
	@OneToMany(mappedBy = "corso")
	@JsonManagedReference
	private Set<CorsoHasAttivitaDidattica> corsoHasAttivitaDidatticaSet;

	public int getIdCorso() {
		return idCorso;
	}

	public void setIdCorso(int idCorso) {
		this.idCorso = idCorso;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<UtenteHasCorso> getUtenteHasCorsoSet() {
		return utenteHasCorsoSet;
	}

	public void setUtenteHasCorsoSet(Set<UtenteHasCorso> utenteHasCorsoSet) {
		this.utenteHasCorsoSet = utenteHasCorsoSet;
	}

	public Set<CorsoHasAttivitaDidattica> getCorsoHasAttivitaDidatticaSet() {
		return corsoHasAttivitaDidatticaSet;
	}

	public void setCorsoHasAttivitaDidatticaSet(Set<CorsoHasAttivitaDidattica> corsoHasAttivitaDidatticaSet) {
		this.corsoHasAttivitaDidatticaSet = corsoHasAttivitaDidatticaSet;
	}
	
	
}
