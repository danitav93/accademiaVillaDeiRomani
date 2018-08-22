package com.nodelab.accademiaVillaDeiRomani.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nodelab.accademiaVillaDeiRomani.constant.VariabiliAmbiente;

@Service("variabileAmbienteService")
public class VariabileAmbienteServiceImpl implements VariabileAmbienteService {

	@Value(VariabiliAmbiente.DUPLICATE_MAIL_CHECK)
	private String emailCheck;
	
	@Override
	public boolean emailCheck() {
		
		return emailCheck.equalsIgnoreCase("true");
		
	}

}
