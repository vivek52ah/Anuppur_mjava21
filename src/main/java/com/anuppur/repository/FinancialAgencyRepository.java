package com.anuppur.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anuppur.entity.WorkFinancialAgency;

public interface FinancialAgencyRepository extends JpaRepository<WorkFinancialAgency, Long> {

	List<WorkFinancialAgency> findByWorkId(Long workId);

	Optional<WorkFinancialAgency> findByWorkIdAndFinancialHeadId(Long id, Long financialHeadId);

	WorkFinancialAgency findByIdAndWorkId(Long id, Long id2);

	Double findCostByWorkId(Long workId);

	boolean existsById(Long id);

	void deleteById(Long id);

	List<WorkFinancialAgency> findByWorkIdAndExpenditureIsNotNull(Long workId);


}
