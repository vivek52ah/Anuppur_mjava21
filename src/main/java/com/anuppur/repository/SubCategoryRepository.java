package com.anuppur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anuppur.entity.Block;
import com.anuppur.entity.SubCategory;
import com.anuppur.entity.WorkCategory;
import com.anuppur.entity.WorkType;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long>{
	
	List<SubCategory> findByCategorySubTypeIdIn(List<Long> ids);
	
	List<SubCategory> findByEnabled(Short isEnabled);
	
	
    List<SubCategory> findByEnabledOrderByCategorySubTypeNameE(Short isEnabled);
    
    List<SubCategory> findByWorkCategoryAndEnabledOrderByCategorySubTypeNameE(WorkCategory workCategory, Short isEnabled);
	
	

}
