package com.anuppur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anuppur.entity.Contractor;

public interface ContractorRepository extends JpaRepository<Contractor, Long> {
	Contractor findByWork_Id(Long workId);

	@Query("SELECT COUNT(c) FROM Contractor c WHERE c.work.id = :workId")
	Long countByWorkId(@Param("workId") Long workId);

	@Query("SELECT COALESCE(MAX(c.id), 0) FROM Contractor c")
	Long findMaxId();

	@Query("SELECT c From Contractor c WHERE c.status = 'Active' ")
	List<Contractor> getAllWork();
}
