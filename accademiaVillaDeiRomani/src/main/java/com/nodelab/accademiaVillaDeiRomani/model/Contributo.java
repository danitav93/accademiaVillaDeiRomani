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
@Table(name = "contributo")
public class Contributo implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id_contributo")
	private Integer idContributo;
	
	private String nome;

	@OneToMany(mappedBy = "contributo")
	@Column(name="utente_has_contributi")
	@JsonBackReference
	private Set<UtenteHasContributo> utenteHasContributoSet;

	public Contributo() {
		super();
	}

	public Contributo(Integer idContributo, String nome) {
		super();
		this.idContributo = idContributo;
		this.nome = nome;
	}

	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}


	public Set<UtenteHasContributo> getUtente_has_contributi() {
		return utenteHasContributoSet;
	}

	public Integer getIdContributo() {
		return idContributo;
	}

	public void setIdContributo(Integer idContributo) {
		this.idContributo = idContributo;
	}

	public Set<UtenteHasContributo> getUtenteHasContributoSet() {
		return utenteHasContributoSet;
	}

	public void setUtenteHasContributoSet(Set<UtenteHasContributo> utenteHasContributoSet) {
		this.utenteHasContributoSet = utenteHasContributoSet;
	}
	



}
