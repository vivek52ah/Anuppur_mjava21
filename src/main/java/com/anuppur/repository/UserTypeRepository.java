package com.anuppur.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.anuppur.entity.UserType;


public interface UserTypeRepository extends JpaRepository<UserType,String> {
	
	UserType findById(Long userTypeId);



	

}
