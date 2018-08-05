package com.nodelab.accademiaVillaDeiRomani.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.nodelab.accademiaVillaDeiRomani.model.RegistrationVerificationToken;
import com.nodelab.accademiaVillaDeiRomani.model.Utente;

public interface RegistrationVerificationTokenRepository extends JpaRepository<RegistrationVerificationToken, Integer> {

	void deleteByUtente(Utente utente);

	RegistrationVerificationToken findByToken(String token);

}
