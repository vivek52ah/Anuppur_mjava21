package com.anuppur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anuppur.entity.AreaOfficerRecord;

@Repository
public interface areaofficerrecordRepository extends JpaRepository<AreaOfficerRecord, Long> {

	List<AreaOfficerRecord> findAllByWorkId(Long workid);

	AreaOfficerRecord findByUserid(Long userid);

	boolean existsByUseridAndWorkId(Long userid, Long workId);

	
	
}
