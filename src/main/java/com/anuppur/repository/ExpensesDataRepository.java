package com.anuppur.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anuppur.bean.ExpensesDataBean;
import com.anuppur.entity.ExpensesData;

public interface ExpensesDataRepository extends JpaRepository<ExpensesData, Long>{

	List<ExpensesData> findByStatus(Long status);
	
	@Query("SELECT sum(e.expensessCurrentFy) from ExpensesData e where e.workId IN:workId")
	BigDecimal sumExpensesAmount(@Param("workId") Long workId);

	List<ExpensesData> findByWorkId(Long workTypeId);

	List<ExpensesData> findByYearContainingAndWorkId(Long newYear, Long workTypeId);

	List<ExpensesData> findByYearAndWorkIdIn(Long newYear, Long workId);

	List<ExpensesData> findByWorkIdAndStatusIn(Long workId, Long status);

	 List<ExpensesData> findByWorkIdAndYearIn(Long workId, Long year);

	@Query("SELECT c From ExpensesData c ")
	List<ExpensesData> getAllWork();

	List<ExpensesData> getAllByWorkId(Long workTypeId);



	//BigDecimal sumExpensesAmount(Long workId);
	
	
	
	/*
	 * @Query("SELECT e.expId ,e.workId, e.year,e.expensessUptoMarch, e.expensessCurrentFy,e.totalExpensess,e.createdDate from ExpensesData e WHERE e.year IN :year AND e.workId IN :workId"
	 * ) List<ExpensesData> findByYearContainingAndWorkId(@Param("year") Long year,
	 * 
	 * @Param("workId") Long workId);
	 */

}
