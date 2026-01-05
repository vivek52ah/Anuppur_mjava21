package com.anuppur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anuppur.entity.Block;
import com.anuppur.entity.DistBlockLegislativeMapping;
import com.anuppur.entity.District;
import com.anuppur.entity.Division;
import com.anuppur.entity.LegislativeConstituency;


public interface DistBlockLegislativeRepository extends JpaRepository<DistBlockLegislativeMapping, Long> {
	
	
     List<DistBlockLegislativeMapping>findByDistrict(District district);
     
     List<DistBlockLegislativeMapping>findByBlock(Block block);
     
     List<DistBlockLegislativeMapping>findByLegislativeConstituency(LegislativeConstituency legislativeConstituency);
	  
	  List<DistBlockLegislativeMapping> findByDistrictAndEnabled(District district, Short isEnabled);
	  
	  List<DistBlockLegislativeMapping> findByBlockAndEnabled(Block block, Short isEnabled);
	  
	  List<DistBlockLegislativeMapping> findByLegislativeConstituencyAndEnabled(LegislativeConstituency legislativeConstituency, Short isEnabled);
	  
	   List<DistBlockLegislativeMapping> findByDivisionAndDistrictAndEnabled(Division division,District district, Short isEnabled);
	   
	   List<DistBlockLegislativeMapping> findByDistrictAndBlockAndEnabled(District district,Block block, Short isEnabled);
	   
	   List<DistBlockLegislativeMapping> findByDistrictAndLegislativeConstituencyAndEnabled(District district,LegislativeConstituency legislativeConstituency, Short isEnabled);

}
