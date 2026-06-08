package com.anuppur.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anuppur.entity.WorkFinancialAgency;

public interface FinancialAgencyRepository extends JpaRepository<WorkFinancialAgency, Long> {

	List<WorkFinancialAgency> findByWorkId(Long workId);

	Optional<WorkFinancialAgency> findByWorkIdAndFinancialHeadId(Long id, Long financialHeadId);

	WorkFinancialAgency findByIdAndWorkId(Long id, Long id2);

	Double findCostByWorkId(Long workId);

	boolean existsById(Long id);

	void deleteById(Long id);

	List<WorkFinancialAgency> findByWorkIdAndExpenditureIsNotNull(Long workId);

	@Query("SELECT COALESCE(SUM(wfa.expenditure), 0) FROM WorkFinancialAgency wfa WHERE wfa.workId = :workId")
	Double sumExpenditureByWorkId(@Param("workId") Long workId);

}
