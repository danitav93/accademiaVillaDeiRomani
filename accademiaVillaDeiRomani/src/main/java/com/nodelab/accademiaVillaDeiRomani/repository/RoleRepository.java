package com.nodelab.accademiaVillaDeiRomani.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nodelab.accademiaVillaDeiRomani.model.Role;


public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	Role findByName(String name);

}
