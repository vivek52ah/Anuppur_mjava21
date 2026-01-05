package com.anuppur.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.anuppur.entity.OfficeType;
import com.anuppur.entity.WorkCategory;
import com.anuppur.entity.WorkType;

public interface WorkCategoryRepository  extends JpaRepository<WorkCategory, Long> {
	
	List<WorkCategory> findByEnabledOrderByWorkCategoryNameE(Short isEnabled);
	
	List<WorkCategory> findByWorkTypeAndEnabledOrderByWorkCategoryNameE(WorkType workType, Short isEnabled);


	Page<WorkCategory> findByWorkCategoryNameEContainingAndEnabled(Pageable pageable, String searchParameter, short s);

	Page<WorkCategory> findByEnabled(Pageable pageable, short s);

	long countByEnabled(short s);

	WorkCategory findByworkCategoryNameEAndWorkTypeAndEnabled(String workCategoryNameE, WorkType workType, short s);

	WorkCategory findByworkCategoryNameEAndEnabled(String workCategoryNameE, short s);


	
	List<WorkCategory> findByEnabledOrderByOrdering(Short isEnabled);
	
	List<WorkCategory> findByEnabledOrderByOrderingAsc(Short isEnabled);
	
	
	List<WorkCategory> findByWorkCategoryIdIn(List<Long> ids);

	
	@Query(value="select work_category_name_e,work_type_id,('Hello') as status from mst_work_category" , nativeQuery = true)
	List<Object[]> findAllWorkCategory();

	Page<WorkCategory> findByworkCategoryNameEContainingAndEnabled(Pageable pageable, String searchParameter,
			Short enabled);


	boolean existsByWorkCategoryNameE(String workCategoryNameE);

	

	

	
}
