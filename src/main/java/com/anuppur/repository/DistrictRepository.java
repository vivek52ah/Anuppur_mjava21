package com.anuppur.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anuppur.entity.District;
import com.anuppur.entity.Division;

public interface DistrictRepository extends JpaRepository<District, Long> {

	List<District> findByEnabled(Short isEnabled);
	
	District findByDistrictId(Long id);
	
	District findByDistrictCode(String districtCode);
	
	District findByDistrictNameAndEnabled(String districtName, Short isEnabled);

	District findByDistrictCodeAndEnabled(String districtCode, Short enabled);

	District findByDistrictIdAndEnabled(Long districtId, Short enabled);

	List<District> findByDivision(Division division);
	
	List<District> findByDivisionAndEnabled(Division division, Short isEnabled );

	List<District> findByEnabledAndDivisionDivisionId(short s, Long divisionId);
	
	Page<District>   getAllByEnabled(Pageable p , Short s);
	Page<District>   getByDistrictNameAndEnabled(Pageable p ,String sa, Short s);

	Page<District> getAllByEnabledAndDivisionBetween(Pageable pageable, Short enabled, Division setDivisionId,
			Division setDivisionId2);

	Page<District> getByDistrictNameContainingAndEnabledAndDivisionBetween(Pageable pageable, String searchParameter,
			Short enabled, Division division, Division division2);

	List<District> getAllByDivisionBetween(Division division, Division division2);

	District findByDistrictCodeOrDivisionOrDistrictNameOrDistrictNameH(String districtCode, Division division,
			String districtName, String districtNameH);

	District findByDistrictName(String districtName);

	District findByDistrictNameH(String districtNameH);

	List<District> getAllByDivisionBetweenAndEnabled(Division division, Division division2, Short enabled);

	//District findByDistrictNameAndEnabled(String districtName,Short L);

	District findByDistrictNameHAndEnabled(String districtNameH, Short l);

	
}
