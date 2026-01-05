package com.anuppur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anuppur.entity.OfficeType;
import com.anuppur.entity.UserOfficeTypeMapping;
import com.anuppur.entity.UserType;





public interface UserOfficeTypeMappingRepository extends JpaRepository<UserOfficeTypeMapping, Long>{
	
  List<UserOfficeTypeMapping>findByOfficeType(OfficeType officeType);
  
  List<UserOfficeTypeMapping> findByUserTypeAndEnabled(UserType userType, Short isEnabled);
  
   List<UserOfficeTypeMapping> findByUserTypeAndOfficeTypeAndEnabled(UserType userType,OfficeType officeType, Short isEnabled);
  
  

}
