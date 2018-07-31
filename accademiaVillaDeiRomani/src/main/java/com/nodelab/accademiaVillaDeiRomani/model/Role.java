package com.nodelab.accademiaVillaDeiRomani.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;


/**
 * The persistent class for the role database table.
 * 
 */
@Entity
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_role")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idRole;

	@Column(name="name")
	private String name;
	
	@OneToMany(mappedBy = "role")
	@JsonBackReference
	private Set<Utente> utentiSet;

	public Role() {
	}

	public int getIdRole() {
		return this.idRole;
	}

	public void setIdRole(int idRole) {
		this.idRole = idRole;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Utente> getUtentiSet() {
		return utentiSet;
	}

	public void setUtentiSet(Set<Utente> utentiSet) {
		this.utentiSet = utentiSet;
	}

	
	
}