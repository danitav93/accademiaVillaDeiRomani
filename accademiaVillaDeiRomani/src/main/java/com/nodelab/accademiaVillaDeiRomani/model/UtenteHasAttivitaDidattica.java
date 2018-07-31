package com.nodelab.accademiaVillaDeiRomani.model;

import java.io.Serializable;
import java.util.Date;

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
@Table(name="utente_has_attivita_didattica")
public class UtenteHasAttivitaDidattica implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id_utente_has_attivita_didattica")
	private int idUtenteHasAttivitaDidattica;
	
	@Column(name="anno_scolastico")
	private String annoScolastico;
	
	@Column(name="data_esame")
	private Date dataEsame;
	
	@Column(name="voto_Esame")
	private int votoEsame;
	
	@ManyToOne
    @JoinColumn(name = "id_utente")
	@JsonBackReference
	private Utente utente;
	
	@ManyToOne
	@JoinColumn(name = "id_attivita_didattica")
	@JsonManagedReference
	private AttivitaDidattica attivitaDidattica;

	public int getIdUtenteHasAttivitaDidattica() {
		return idUtenteHasAttivitaDidattica;
	}

	public void setIdUtenteHasAttivitaDidattica(int idUtenteHasAttivitaDidattica) {
		this.idUtenteHasAttivitaDidattica = idUtenteHasAttivitaDidattica;
	}

	public String getAnnoScolastico() {
		return annoScolastico;
	}

	public void setAnnoScolastico(String annoScolastico) {
		this.annoScolastico = annoScolastico;
	}

	public Date getDataEsame() {
		return dataEsame;
	}

	public void setDataEsame(Date dataEsame) {
		this.dataEsame = dataEsame;
	}

	public int getVotoEsame() {
		return votoEsame;
	}

	public void setVotoEsame(int votoEsame) {
		this.votoEsame = votoEsame;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public AttivitaDidattica getAttivitaDidattica() {
		return attivitaDidattica;
	}

	public void setAttivitaDidattica(AttivitaDidattica attivitaDidattica) {
		this.attivitaDidattica = attivitaDidattica;
	}

	
	
	
}
