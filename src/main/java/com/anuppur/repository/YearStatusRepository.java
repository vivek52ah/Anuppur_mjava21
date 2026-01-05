package com.anuppur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anuppur.entity.YearStatus;

public interface YearStatusRepository  extends JpaRepository<YearStatus, Long> {

	YearStatus findByYear(Long year);

	List<YearStatus> findByEnabledOrderByYearIdAsc(Short enabled);

}
