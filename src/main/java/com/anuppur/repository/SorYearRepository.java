package com.anuppur.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anuppur.entity.Schemes;
import com.anuppur.entity.SorYear;

public interface SorYearRepository  extends JpaRepository<SorYear, Long> {
	
    List<SorYear> findByEnabled(Short isEnabled);
	
	
	
	SorYear findBySorYearAndEnabled(String schemeName, Short isEnabled);

	List<SorYear> findByEnabledOrderByIdDesc(Short enabled);

	Page<SorYear> findBySorYearContainingAndEnabled(Pageable pageable, String searchParameter, short s);

	Page<SorYear> findByEnabled(short s, Pageable pageable);



	List<SorYear> findByEnabledOrderByIdAsc(Short enabled);

	
	//added by Sumit
	List<SorYear> findByEnabledOrderBySorYearAsc(Short enabled);
	
	List<SorYear> findByEnabledOrderBySorYearDesc(Short enabled);

}
