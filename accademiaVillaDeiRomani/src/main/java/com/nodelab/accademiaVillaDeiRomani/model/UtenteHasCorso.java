package com.nodelab.accademiaVillaDeiRomani.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="utente_has_corso")
public class UtenteHasCorso implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_utente_has_corso")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idUtenteHasCorso;

	@OneToOne
	@JoinColumn(name = "id_utente")
	@JsonBackReference
	private Utente utente;

	@ManyToOne
	@JoinColumn(name = "id_corso")
	@JsonManagedReference
	private Corso corso;

	public Integer getIdUtenteHasCorso() {
		return idUtenteHasCorso;
	}

	public void setIdUtenteHasCorso(Integer idUtenteHasCorso) {
		this.idUtenteHasCorso = idUtenteHasCorso;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public Corso getCorso() {
		return corso;
	}

	public void setCorso(Corso corso) {
		this.corso = corso;
	}

	@Override
	public boolean equals(Object o) {

		if (o == this) return true;
		if (!(o instanceof UtenteHasCorso)) {
			return false;
		}
		
		UtenteHasCorso utenteHasCorso = (UtenteHasCorso) o;
		if (utenteHasCorso.corso==null || utenteHasCorso.utente==null || corso==null || utente==null) {
			return false;
		}

		return utenteHasCorso.corso.getIdCorso()==(corso.getIdCorso()) &&
				utenteHasCorso.utente.getIdUtente() == utente.getIdUtente();
	}

	//Idea from effective Java : Item 9
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + corso.getIdCorso();
		result = 31 * result + utente.getIdUtente();
		return result;
	}





}
