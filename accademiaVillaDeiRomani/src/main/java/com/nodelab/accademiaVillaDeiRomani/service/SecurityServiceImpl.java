package com.nodelab.accademiaVillaDeiRomani.service;

import java.util.Arrays;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nodelab.accademiaVillaDeiRomani.constant.Privileges;
import com.nodelab.accademiaVillaDeiRomani.model.PasswordResetToken;
import com.nodelab.accademiaVillaDeiRomani.model.RegistrationVerificationToken;
import com.nodelab.accademiaVillaDeiRomani.model.Utente;
import com.nodelab.accademiaVillaDeiRomani.repository.PasswordResetTokenRepository;

@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	PasswordResetTokenRepository passwordResetTokenRepository;
	
	@Override
	public String validatePasswordResetToken(Integer idUtente, String token) {
		PasswordResetToken passToken = passwordResetTokenRepository.findByToken(token);
		
		if ((passToken == null) || (passToken.getUtente().getIdUtente() != idUtente)) {
			return "erroreLinkNonValido";
		}

		Calendar cal = Calendar.getInstance();
		if ((passToken.getExpiryDate()
				.getTime() - cal.getTime()
				.getTime()) <= 0) {
			return "expired";
		}

		Utente utente = passToken.getUtente();
		Authentication auth = new UsernamePasswordAuthenticationToken(
				utente, null, Arrays.asList(
						new SimpleGrantedAuthority(Privileges.CHANGE_PASSWORD_PRIVILEGE)));
		SecurityContextHolder.getContext().setAuthentication(auth);
		return null;
	}

	@Override
	public boolean validateRegistrationVerificationToken(RegistrationVerificationToken verificationToken) {
		Calendar cal = Calendar.getInstance();
		
		return ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) >= 0);
			
		
	}

}
