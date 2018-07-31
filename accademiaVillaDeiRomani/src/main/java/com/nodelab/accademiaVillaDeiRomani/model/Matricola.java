package com.nodelab.accademiaVillaDeiRomani.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the matricola database table.
 * 
 */
@Entity
@NamedQuery(name="Matricola.findAll", query="SELECT m FROM Matricola m")
public class Matricola implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_matricola")
	private int idMatricola;

	private int value;

	public Matricola() {
	}

	public int getIdMatricola() {
		return this.idMatricola;
	}

	public void setIdMatricola(int idMatricola) {
		this.idMatricola = idMatricola;
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}