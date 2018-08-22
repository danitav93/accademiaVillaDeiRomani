 package com.nodelab.accademiaVillaDeiRomani.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "nazione")
public class Nazione implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id_nazione")
	private Integer idNazione;
	
	

	@Column(name="nazione")
	private String nazione;

	
	public String getNazione() {
		return nazione;
	}
	public void setNazione(String nazione) {
		this.nazione = nazione;
	}


	

	public Integer getIdnazione() {
		return idNazione;
	}

	public void setIdNazione(Integer idNazione) {
		this.idNazione = idNazione;
	}



}
