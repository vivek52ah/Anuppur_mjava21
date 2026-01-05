package com.anuppur.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.anuppur.bean.WorkBean;
import com.anuppur.entity.WorkTender;

@Repository
public interface WorkTenderRepository extends JpaRepository<WorkTender, Long> {
	
	@Query("SELECT w From WorkTender w WHERE w.work.id =:id ")
    WorkTender findByWorkId(@Param("id") Long id);

	WorkTender findBySorYearContainingAndStatusNotIn(String sorYear, String statusDeleted);

	@Query(value = "SELECT COUNT(*)\r\n"
			+ "FROM t_work_tender wt\r\n"
			+ "INNER JOIN t_work w ON wt.work_id = w.id\r\n"
			+ "WHERE wt.status IN ('Active') AND wt.work_status = 'Tender Called date' and (w.scheme != 'null' or w.scheme != '') and (w.financial_year != 'null' or w.financial_year != '') and (w.work_head != 'null' or w.work_head != '');", nativeQuery = true)
	BigDecimal counTenderCalledCount();

	
	@Query(value = "SELECT COUNT(*)\r\n"
			+ "FROM t_work_tender wt\r\n"
			+ "INNER JOIN t_work w ON wt.work_id = w.id\r\n"
			+ "WHERE wt.status IN ('Active') AND wt.work_status = 'Tender Received' and (w.scheme != 'null' or w.scheme != '') and (w.financial_year != 'null' or w.financial_year != '') and (w.work_head != 'null' or w.work_head != '');", nativeQuery = true)
	//@Query(value = "select count(*) from t_work_tender where status in ('Active') and work_status='Tender Received' ", nativeQuery = true)
	BigDecimal counTenderRcvCount();

	@Query(value = "SELECT COUNT(*)\r\n"
			+ "FROM t_work_tender wt\r\n"
			+ "INNER JOIN t_work w ON wt.work_id = w.id\r\n"
			+ "WHERE wt.status IN ('Active') AND wt.work_status = 'Tender Approval in Process' and (w.scheme != 'null' or w.scheme != '') and (w.financial_year != 'null' or w.financial_year != '') and (w.work_head != 'null' or w.work_head != '');", nativeQuery = true)
	BigDecimal counTenderApprovalCount();

	@Query(value = "SELECT COUNT(*)\r\n"
			+ "FROM t_work_tender wt\r\n"
			+ "INNER JOIN t_work w ON wt.work_id = w.id\r\n"
			+ "WHERE wt.status IN ('Active') AND wt.work_status = 'Re-Tender' and (w.scheme != 'null' or w.scheme != '') and (w.financial_year != 'null' or w.financial_year != '') and (w.work_head != 'null' or w.work_head != '');", nativeQuery = true)
	BigDecimal counReTenderCount();

	@Query(value = "SELECT COUNT(*)\r\n"
			+ "FROM t_work_tender wt\r\n"
			+ "INNER JOIN t_work w ON wt.work_id = w.id\r\n"
			+ "WHERE wt.status IN ('Active') AND wt.work_status = 'LoA Issued' and (w.scheme != 'null' or w.scheme != '') and (w.financial_year != 'null' or w.financial_year != '') and (w.work_head != 'null' or w.work_head != '');", nativeQuery = true)
	BigDecimal counLoACount();

	@Query(value = "SELECT COUNT(*)\r\n"
			+ "FROM t_work_tender wt\r\n"
			+ "INNER JOIN t_work w ON wt.work_id = w.id\r\n"
			+ "WHERE wt.status IN ('Active') AND wt.work_status = 'Work Order Issued' and (w.scheme != 'null' or w.scheme != '') and (w.financial_year != 'null' or w.financial_year != '') and (w.work_head != 'null' or w.work_head != '');", nativeQuery = true)
	BigDecimal counWOIssuedCount();

	/*
	 * @Query(value = "SELECT * FROM t_work_tender\r\n" +
	 * "WHERE STR_TO_DATE(end_date, '%d/%m/%Y') BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 30 DAY)"
	 * , nativeQuery = true)
	 */
	@Query(value = "SELECT * FROM t_work_tender\r\n"
			+ "WHERE STR_TO_DATE(end_date, '%d/%m/%Y') BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 30 DAY)", nativeQuery = true)
	List<WorkTender> findUpcomingWorkTenders();
	
	// aman 17-07-2024
		@Query(value=" SELECT twt.work_order_date as WorkOrderDate FROM t_work_tender twt where work_id =:id ",nativeQuery=true)
		String getWorkOrderDate(@Param("id") Long id  );
		
		@Query(value = "SELECT twt.contract_tenure as timeLineInMonths FROM t_work_tender twt where work_id =:id ", nativeQuery = true)
		BigDecimal getTimeLineInMonths(@Param("id") Long id  );

		@Query("SELECT w From WorkTender w WHERE w.status = 'Active' ")
		List<WorkTender> getAllWork();
		
		@Query("From WorkTender w WHERE w.work.id =:id ")
	    WorkTender getWorkTenderData(@Param("id") Long id);

		WorkBean findByWorkOrderDate(Long id);

}
