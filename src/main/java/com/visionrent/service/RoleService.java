package com.visionrent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visionrent.domain.Role;
import com.visionrent.domain.enums.RoleType;
import com.visionrent.exception.ResourceNotFoundException;
import com.visionrent.exception.message.ErrorMessage;
import com.visionrent.repository.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	public Role findByType(RoleType roleType) {
		Role role =  roleRepository.findByType(roleType).orElseThrow(()->
		       new ResourceNotFoundException(String.format(
		    		   ErrorMessage.ROLE_NOT_FOUND_MESSAGE, roleType.name())));
		
		return role ;
		
	}

}
