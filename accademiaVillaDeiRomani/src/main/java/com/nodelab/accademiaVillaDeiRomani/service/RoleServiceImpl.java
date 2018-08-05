package com.nodelab.accademiaVillaDeiRomani.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nodelab.accademiaVillaDeiRomani.constant.Ruoli;
import com.nodelab.accademiaVillaDeiRomani.model.Role;
import com.nodelab.accademiaVillaDeiRomani.repository.RoleRepository;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public List<Role> getListOfRoles() {
		List<Role> roles=roleRepository.findAll();
		Role toRemove=new Role();
		for (Role role:roles) {
			if (role.getName().equals(Ruoli.ruolo_super_amministratore)) {
				toRemove=role;
			}
		}
		roles.remove(toRemove);
		return roles;
	}

}
