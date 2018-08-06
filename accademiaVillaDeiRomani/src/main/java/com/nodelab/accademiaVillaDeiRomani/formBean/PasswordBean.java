package com.nodelab.accademiaVillaDeiRomani.formBean;

import com.nodelab.accademiaVillaDeiRomani.annotation.PasswordAnnotation;

public class PasswordBean {
	@PasswordAnnotation
    private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
