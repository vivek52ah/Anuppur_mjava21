package com.anuppur.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.anuppur.entity.Role;

public interface RoleRepository  extends JpaRepository<Role, String> {
	
	
    
}
