package com.nodelab.accademiaVillaDeiRomani.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nodelab.accademiaVillaDeiRomani.model.PasswordResetToken;
import com.nodelab.accademiaVillaDeiRomani.model.Utente;


public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {

	PasswordResetToken findByToken(String token);

	void deleteByUtente(Utente utente);

}
