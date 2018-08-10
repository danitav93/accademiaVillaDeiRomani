package com.nodelab.accademiaVillaDeiRomani.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nodelab.accademiaVillaDeiRomani.model.GoogleAccessToken;

public interface GoogleAccessTokenRepository extends JpaRepository<GoogleAccessToken, Integer>{

}
