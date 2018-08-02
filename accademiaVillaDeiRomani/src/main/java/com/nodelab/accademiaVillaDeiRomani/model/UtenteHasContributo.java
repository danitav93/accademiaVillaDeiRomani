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
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="utente_has_contributo")
public class UtenteHasContributo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_utente_has_contributo")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idUtenteHasContributo;
	
	@ManyToOne
    @JoinColumn(name = "id_utente")
	@JsonBackReference
	private Utente utente;
	
	@ManyToOne
	@JoinColumn(name = "id_contributo")
	@JsonManagedReference
	private Contributo contributo;
	
	@Column(name="importo")
	private Double importo;
	 
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name="data")
	@NotNull(message = "*Please provide date.")
	private Date data;
	
	public UtenteHasContributo() {
		super();
	}
	
	
	
	
	public Utente getUtente() {
		return utente;
	}
	
	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public int getIdUtenteHasContributo() {
		return idUtenteHasContributo;
	}



	public void setIdUtenteHasContributo(int idUtenteHasContributo) {
		this.idUtenteHasContributo = idUtenteHasContributo;
	}



	public Contributo getContributo() {
		return contributo;
	}



	public void setContributo(Contributo contributo) {
		this.contributo = contributo;
	}



	public Double getImporto() {
		return importo;
	}

	public void setImporto(Double importo) {
		this.importo = importo;
	}




	public Date getData() {
		return data;
	}




	public void setData(Date data) {
		this.data = data;
	}

	
	
	
}
