package com.nodelab.accademiaVillaDeiRomani.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="password_reset_token")
public class PasswordResetToken {

	private static final int EXPIRATION = 60 * 24;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id_password_reset_token")
	private Integer idPasswordResetToken;

	private String token;

	@OneToOne(targetEntity = Utente.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "idUtente")
	private Utente utente;

	@Column(name="expiry_date")
	private Date expiryDate;



	public Integer getIdPasswordResetToken() {
		return idPasswordResetToken;
	}

	public void setIdPasswordResetToken(Integer idPasswordResetToken) {
		this.idPasswordResetToken = idPasswordResetToken;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public static int getExpiration() {
		return EXPIRATION;
	}

	


}
