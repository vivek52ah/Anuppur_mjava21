package com.anuppur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anuppur.entity.WorkCategory;
import com.anuppur.entity.WorkCategoryTypeMapping;
import com.anuppur.entity.WorkType;

public interface WorkCategoryTypeMappingRepository  extends JpaRepository<WorkCategoryTypeMapping, Long>{

	
	
	List<WorkCategoryTypeMapping>findByWorkCategory(WorkCategory workCategory);
	  
	  List<WorkCategoryTypeMapping> findByWorkTypeAndEnabled(WorkType workType, Short isEnabled);
	  
	   List<WorkCategoryTypeMapping> findByWorkTypeAndWorkCategoryAndEnabled(WorkType workType,WorkCategory workCategory, Short isEnabled);
	  
}
