package com.nodelab.accademiaVillaDeiRomani.formBean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.nodelab.accademiaVillaDeiRomani.annotation.PasswordAnnotation;
import com.nodelab.accademiaVillaDeiRomani.model.Corso;
import com.nodelab.accademiaVillaDeiRomani.model.Role;







public class UtenteBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	
	@PasswordAnnotation
	private String password;

	
	@Email(message = "*Please provide a valid Email")
	@NotEmpty(message = "*Please provide an email")
	private String email;
	
	//@NotEmpty(message = "*Please provide phone number")
	private String telefono;
	
	@NotEmpty(message = "*Please provide name")
	private String nome;

	@NotEmpty(message = "*Please provide last name")
	private String cognome;
	
	private String borseDiStudio;

	private String codiceFiscale;

	@Temporal(TemporalType.DATE)
	private Date dataIscrizione;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@NotNull(message = "*Please provide birthday.")
	private Date dataNascita;

	@NotEmpty(message = "*Please provide address")
	private String indirizzo;
	
	@NotEmpty(message = "*Please provide city")
	private String citta;
	
	private String cap;
	
	@NotNull(message = "*Please provide sex")
	private Integer sex;
	
	@NotEmpty(message = "*Please provide nation of birth")
	private String nazione;

	private String progetti;

	private String stage;

	private String tirocini;
		
	@NotNull(message = "*Select a role")
	private Role role;

	private Corso corso;
	
	
	public UtenteBean() {
	}


	
	public String getBorseDiStudio() {
		return this.borseDiStudio;
	}

	public void setBorseDiStudio(String borseDiStudio) {
		this.borseDiStudio = borseDiStudio;
	}

	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Date getDataIscrizione() {
		return this.dataIscrizione;
	}

	public void setDataIscrizione(Date dataIscrizione) {
		this.dataIscrizione = dataIscrizione;
	}

	public Date getDataNascita() {
		return this.dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getProgetti() {
		return this.progetti;
	}

	public void setProgetti(String progetti) {
		this.progetti = progetti;
	}

	public String getStage() {
		return this.stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTirocini() {
		return tirocini;
	}

	public void setTirocini(String tirocini) {
		this.tirocini = tirocini;
	}

	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getNazione() {
		return nazione;
	}

	public void setNazione(String nazione) {
		this.nazione = nazione;
	}

	public String getCitta() {
		return citta;
	}



	public void setCitta(String citta) {
		this.citta = citta;
	}



	public String getCap() {
		return cap;
	}



	public void setCap(String cap) {
		this.cap = cap;
	}



	public Corso getCorso() {
		return corso;
	}



	public void setCorso(Corso corso) {
		this.corso = corso;
	}
	
	
}
