package com.anuppur.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anuppur.entity.TemplateEntity;




@Repository
public interface TemplateRepository extends JpaRepository<TemplateEntity, Long>{
	
	TemplateEntity findByKey(String key);

}
