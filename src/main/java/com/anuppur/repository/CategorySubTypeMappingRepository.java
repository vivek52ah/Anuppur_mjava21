package com.anuppur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anuppur.entity.CategorySubTypeMapping;
import com.anuppur.entity.SubCategory;
import com.anuppur.entity.WorkCategory;


public interface CategorySubTypeMappingRepository extends JpaRepository<CategorySubTypeMapping, Long>{
	
	
	  List<CategorySubTypeMapping>findBySubCategory(SubCategory subCategory);
	  
	  List<CategorySubTypeMapping> findByWorkCategoryAndEnabled(WorkCategory workCategory, Short isEnabled);
	  
	   List<CategorySubTypeMapping> findByWorkCategoryAndSubCategoryAndEnabled(WorkCategory workCategory,SubCategory subCategory, Short isEnabled);

}
