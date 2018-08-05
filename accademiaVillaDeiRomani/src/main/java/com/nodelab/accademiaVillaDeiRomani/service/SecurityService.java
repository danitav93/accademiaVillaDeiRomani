package com.nodelab.accademiaVillaDeiRomani.service;

import com.nodelab.accademiaVillaDeiRomani.model.RegistrationVerificationToken;

public interface SecurityService {

	String validatePasswordResetToken(Integer idUtente, String token);

	boolean validateRegistrationVerificationToken(RegistrationVerificationToken verificationToken);

}
