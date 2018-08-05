package com.nodelab.accademiaVillaDeiRomani.formBean;

import com.nodelab.accademiaVillaDeiRomani.annotation.PasswordAnnotation;

public class PasswordBean {
	@PasswordAnnotation(message="*Your password must have at least 5 characters")
    private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
