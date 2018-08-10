package com.nodelab.accademiaVillaDeiRomani.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the google_access_token database table.
 * 
 */
@Entity
@Table(name="google_access_token")
@NamedQuery(name="GoogleAccessToken.findAll", query="SELECT g FROM GoogleAccessToken g")
public class GoogleAccessToken implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_google_access_token")
	private int idGoogleAccessToken;

	@Column(name="access_token")
	private String accessToken;

	@Column(name="expire")
	private Long expire;

	public GoogleAccessToken() {
	}

	public int getIdGoogleAccessToken() {
		return this.idGoogleAccessToken;
	}

	public void setIdGoogleAccessToken(int idGoogleAccessToken) {
		this.idGoogleAccessToken = idGoogleAccessToken;
	}

	public String getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public  Long getExpire() {
		return this.expire;
	}

	public void setExpire( Long expire) {
		this.expire = expire;
	}

}