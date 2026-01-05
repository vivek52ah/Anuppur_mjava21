package com.anuppur.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.anuppur.entity.CC;

public interface CCRepository extends JpaRepository<CC, Long> {
	
	CC findByWorkId(Long workId);
	
	
	@Query(value="SELECT COUNT(*)\r\n"
			+ "FROM t_work_cc wc\r\n"
			+ "INNER JOIN t_work w ON wc.work_id = w.id\r\n"
			+ "WHERE wc.status IN ('Active') AND wc.work_status = 'CC Uploaded' and (w.scheme != 'null' or w.scheme != '') and (w.financial_year != 'null' or w.financial_year != '') and (w.work_head != 'null' or w.work_head != '');\r\n"
			+ "", nativeQuery=true)
	BigDecimal countCCCount();
	
	@Query(value="SELECT COUNT(*)\r\n"
			+ "FROM t_work_cc wc\r\n"
			+ "INNER JOIN t_work w ON wc.work_id = w.id\r\n"
			+ "WHERE wc.status IN ('Active') AND wc.work_status = 'Handed Over' and (w.scheme != 'null' or w.scheme != '') and (w.financial_year != 'null' or w.financial_year != '') and (w.work_head != 'null' or w.work_head != '');\r\n"
			+ "", nativeQuery=true)
	BigDecimal countHandOverCount();

	@Query("SELECT w From CC w WHERE w.status = 'Active' ")
	List<CC> getAllWork();

}
