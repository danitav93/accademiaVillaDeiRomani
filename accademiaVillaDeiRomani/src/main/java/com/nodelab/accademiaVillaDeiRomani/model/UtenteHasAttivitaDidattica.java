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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

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
	
	@Temporal(TemporalType.DATE)
	@Column(name="data_esame")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataEsame;
	
	@Column(name="voto_Esame")
	private Integer votoEsame;
	
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

	

	public Date getDataEsame() {
		return dataEsame;
	}

	public void setDataEsame(Date dataEsame) {
		this.dataEsame = dataEsame;
	}

	public Integer getVotoEsame() {
		return votoEsame;
	}

	public void setVotoEsame(Integer votoEsame) {
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
