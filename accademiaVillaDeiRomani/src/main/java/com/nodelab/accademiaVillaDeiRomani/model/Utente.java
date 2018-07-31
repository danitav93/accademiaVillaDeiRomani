package com.nodelab.accademiaVillaDeiRomani.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nodelab.accademiaVillaDeiRomani.annotation.PasswordAnnotation;




//pinooooooorere

@Entity
@Indexed
@Table(name="utente")
public class Utente implements Serializable {
	
	private static final long serialVersionUID = 1L;


	@Id
	@Column(name="id_utente")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idUtente;
	
	@Transient
	@PasswordAnnotation(message="*Your password must have at least 5 characters")
	private String password;

	
	@Column(name = "email")
	@Email(message = "*Please provide a valid Email")
	@NotEmpty(message = "*Please provide an email")
	private String email;
	
	@Column(name = "telefono")
	@NotEmpty(message = "*Please provide phone number")
	private String telefono;
	
	@Column(name = "nome")
	@Field
	@NotEmpty(message = "*Please provide name")
	private String nome;

	@Column(name = "cognome")
	@Field
	@NotEmpty(message = "*Please provide last name")
	private String cognome;
	
	@Column(name = "matricola")
	@Field
	private String matricola;
	
	
	@Column(name="borse_di_studio")
	private String borseDiStudio;

	@Column(name="codice_fiscale")
	@NotEmpty(message = "*Please provide fiscal code")
	private String codiceFiscale;

	
	@Temporal(TemporalType.DATE)
	@Column(name="data_iscrizione")
	private Date dataIscrizione;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name="data_nascita")
	@NotNull(message = "*Please provide birthday.")
	private Date dataNascita;

	@Column(name = "indirizzo")
	@NotEmpty(message = "*Please provide address")
	private String indirizzo;
	
	@Column(name = "sex")
	@NotNull(message = "*Please provide sex")
	private Integer sex;
	
	@Column(name = "nazione")
	@NotEmpty(message = "*Please provide nation of birth")
	private String nazione;

	@Column(name = "progetti")
	private String progetti;

	@Column(name = "stage")
	private String stage;

	@Column(name="tirocini")
	private String tirocini;
	
	@Column(name = "active")
	private int active;
	
	@ManyToOne
	@JoinColumn(name = "id_role")
	@NotNull(message = "*Select a role")
	@JsonManagedReference
	private Role role;

	@OneToMany(mappedBy = "utente")
	@JsonManagedReference
	private Set<UtenteHasContributo> utenteHasContributiSet;
	
	@OneToMany(mappedBy = "utente")
	@JsonManagedReference
	private Set<UtenteHasAttivitaDidattica> utenteHasAttivitaDidatticaSet;
	
	@OneToOne(mappedBy = "utente")
	@JsonManagedReference
	private UtenteHasCorso utenteHasCorso;
	
	
	public Set<UtenteHasContributo> getUtenteHasContributiSet() {
		return utenteHasContributiSet;
	}
	
	public void setUtenteHasContributiSet(Set<UtenteHasContributo> utenteHasContributiSet) {
		this.utenteHasContributiSet = utenteHasContributiSet;
	}

	public Utente() {
	}


	public int getIdUtente() {
		return this.idUtente;
	}
	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
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

	public String getMatricola() {
		return this.matricola;
	}

	public void setMatricola(String matricola) {
		this.matricola = matricola;
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

	public Set<UtenteHasAttivitaDidattica> getUtenteHasAttivitaDidatticaSet() {
		return utenteHasAttivitaDidatticaSet;
	}

	public void setUtenteHasAttivitaDidatticaSet(Set<UtenteHasAttivitaDidattica> utenteHasAttivitaDidatticaSet) {
		this.utenteHasAttivitaDidatticaSet = utenteHasAttivitaDidatticaSet;
	}

	public UtenteHasCorso getUtenteHasCorso() {
		return utenteHasCorso;
	}

	public void setUtenteHasCorso(UtenteHasCorso utenteHasCorso) {
		this.utenteHasCorso = utenteHasCorso;
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

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
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
	
	
}
