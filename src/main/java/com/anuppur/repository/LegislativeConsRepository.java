package com.anuppur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anuppur.entity.District;
import com.anuppur.entity.LegislativeConstituency;

public interface LegislativeConsRepository extends JpaRepository<LegislativeConstituency, Long> {

	
	@Query("select distinct constituencyName from LegislativeConstituency w where w.enabled = (:isEnabled)")
	List<String> findDistinctLegislativeConstituencyByEnabled(@Param("isEnabled")Short isEnabled);

	LegislativeConstituency findByConstituencyNameAndDistrict(String legislativeConstituencyName, District district);

	
	LegislativeConstituency findByConstituencyCode(String constituencyCode);

	List<LegislativeConstituency> findByIdIn(List<Long> longlcId);
	
	LegislativeConstituency findByConstituencyName(String legislativeConstituencyName);
	
	List<LegislativeConstituency> findByDistrictAndEnabled(District district, Short isEnabled );
	List<LegislativeConstituency> findByEnabled(Short isEnabled);

	LegislativeConstituency findByIdAndDistrict(Long legislativeConstituencyId, District district);
	
	

}
