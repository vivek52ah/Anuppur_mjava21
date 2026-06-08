package com.anuppur.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anuppur.entity.ExpensesData;

public interface ExpensesDataRepository extends JpaRepository<ExpensesData, Long> {

    List<ExpensesData> findByStatus(Long status);

    
    // FIXED
    @Query("SELECT SUM(e.expensessCurrentFy) FROM ExpensesData e WHERE e.workId IN :workIds")
    BigDecimal sumExpensesAmount(@Param("workIds") List<Long> workIds);

    
    List<ExpensesData> findByWorkId(Long workTypeId);

    
    // REMOVE Containing because Long field me Containing valid nahi hota
    List<ExpensesData> findByYearAndWorkId(Long year, Long workTypeId);

    
    // FIXED
    List<ExpensesData> findByYearAndWorkIdIn(Long year, List<Long> workIds);

    
    // statusIn => List required
    List<ExpensesData> findByWorkIdAndStatusIn(Long workId, List<Long> statusList);

    
    // yearIn => List required
    List<ExpensesData> findByWorkIdAndYearIn(Long workId, List<Long> years);

    
    @Query("SELECT c FROM ExpensesData c")
    List<ExpensesData> getAllWork();

    
    List<ExpensesData> getAllByWorkId(Long workTypeId);

}