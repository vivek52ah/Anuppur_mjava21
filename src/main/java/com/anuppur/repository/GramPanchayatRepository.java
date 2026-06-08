package com.anuppur.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.anuppur.entity.GramPanchayat;

@Repository
public interface GramPanchayatRepository extends JpaRepository<GramPanchayat, Long> {

	List<GramPanchayat> findByBlockCodeAndEnabled(String blockCode, short s);

	GramPanchayat findBygramPanchayatId(Long gpId);

	List<GramPanchayat> findBygramPanchayatCode(String gramPanchayatCode);

	List<GramPanchayat> findByGramPanchayatCode(String string);

	@Query("SELECT g FROM GramPanchayat g WHERE g.gramPanchayatCode = :gramPanchayatCode")
    GramPanchayat findByGramPanchayat(@Param("gramPanchayatCode") String gramPanchayatCode);
	
	/*
	 * List<GramPanchayat>
	 * findBYDistrictCodeAndBlockCodeAndEnabledOrderByGramPanchayatName(String
	 * districtCode, String blockCode, Short isEnabled);
	 */

	@Query("SELECT g FROM GramPanchayat g WHERE " + "(:districtCode IS NULL OR g.districtCode = :districtCode) AND "
			+ "(:blockCode IS NULL OR g.blockCode = :blockCode) AND "
			+ "g.enabled = :enabled ORDER BY g.gramPanchayatName")
	List<GramPanchayat> findByOptionalDistrictAndBlockCode(@Param("districtCode") String districtCode,
			@Param("blockCode") String blockCode, @Param("enabled") Short enabled);

	Page<GramPanchayat> findByDistrictCodeAndBlockCodeAndEnabled(Pageable page,String districtid, String blockId, Short s);

	Page<GramPanchayat> findByGramPanchayatNameAndDistrictCodeAndBlockCodeAndEnabled(Pageable pageable,
			String searchParameter, String districtid, String blockId, Short s);

	Page<GramPanchayat> findByGramPanchayatNameContainingAndEnabled(Pageable pageable, String searchParameter,
			Short enabled);

	GramPanchayat findByGramPanchayatName(String gramPanchayatName);

	Page<GramPanchayat> findAllByEnabled(Pageable pageable, short s);

	Page<GramPanchayat> findByDistrictCodeAndEnabled(Pageable pageable, String districtCode, Short s);

	Page<GramPanchayat> findByGramPanchayatNameContainingAndDistrictCodeAndEnabled(Pageable pageable,
			String districtCode, String searchParameter, Short enabled);

}
