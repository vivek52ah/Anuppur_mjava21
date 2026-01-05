package com.anuppur.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anuppur.entity.Block;
import com.anuppur.entity.District;
import com.anuppur.entity.Division;
import com.anuppur.entity.WorkCategory;

public interface BlockRepository  extends JpaRepository<Block, Long> {

	

	Block findByBlockCode(String blockCode);
	
	List<Block> findByDistrictAndEnabledOrderByBlockName(District d , Short isEnabled );
	
	List<Block> findByEnabled(Short isEnabled);

Block findByBlockCodeAndEnabled(String blockCode, Short enabled);
Page<Block> findByBlockNameContainingAndDistrictAndEnabled(Pageable pageable, String searchboxval,
			District d,Short s);

	Page<Block> findByDistrictAndEnabled(Pageable pageable, District district,Short s);

	Page<Block> findByBlockNameContainingAndEnabled(Pageable pageable, String searchboxval1, Short s);

	Page<Block> findAllByEnabled(Pageable pageable, short s);

	Page<Block> findByBlockNameContainingIgnoreCaseAndEnabled(Pageable pageable, String searchboxval1, Short s);
	
	

}
