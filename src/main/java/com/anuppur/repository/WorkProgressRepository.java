package com.anuppur.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anuppur.entity.WorkProgress;
import com.anuppur.entity.WorkSubDelayReson;


public interface WorkProgressRepository  extends JpaRepository<WorkProgress, Long> {
	
	WorkProgress  findByWorkId(Long id);
	
	@Query(value="SELECT COUNT(*)\r\n"
			+ "FROM t_work_progress wp\r\n"
			+ "INNER JOIN t_work w ON wp.work_id = w.id\r\n"
			+ "WHERE wp.status IN ('Active') AND wp.work_status = 'Not Started' and (w.scheme != 'null' or w.scheme != '') and (w.financial_year != 'null' or w.financial_year != '') and (w.work_head != 'null' or w.work_head != '');", nativeQuery=true)
	BigDecimal countNotStartedCount();
	
	@Query(value="SELECT COUNT(*)\r\n"
			+ "FROM t_work_progress wp\r\n"
			+ "INNER JOIN t_work w ON wp.work_id = w.id\r\n"
			+ "WHERE wp.status IN ('Active') AND wp.work_status = 'In-Progress' and (w.scheme != 'null' or w.scheme != '') and (w.financial_year != 'null' or w.financial_year != '') and (w.work_head != 'null' or w.work_head != '');\r\n"
			+ "", nativeQuery=true)
	BigDecimal countProgressCount();
	
	@Query(value="SELECT COUNT(*)\r\n"
			+ "FROM t_work_progress wp\r\n"
			+ "INNER JOIN t_work w ON wp.work_id = w.id\r\n"
			+ "WHERE wp.status IN ('Active') AND wp.work_status = 'Completed' and (w.scheme != 'null' or w.scheme != '') and (w.financial_year != 'null' or w.financial_year != '') and (w.work_head != 'null' or w.work_head != '');", nativeQuery=true)
	BigDecimal countCompletedCount();

	WorkProgress findByWorkSubDelayReasonId(Long workSubDelayReasonId);

//	@Query(value="select * from t_work_progress where status not in (:status) and work_sub_delay_reason_id =:wsdrId ", nativeQuery=true)
//	WorkProgress fetchWorkSubDelayReasonIdAndStatus(@Param("wsdrId") WorkSubDelayReson workSubDelayReasonId, @Param("status") String status);
//	
	

	
	@Query("SELECT COUNT(wp) FROM WorkProgress wp WHERE wp.work.id = :workId AND wp.totalExpensess IS NOT NULL")
    Long countByWorkIdAndTotalExpensessIsNotNull(@Param("workId") Long workId);
	
	// aman 17-07-2024
	
		@Query(value = "SELECT twp.total_expensess as totalExpeditureTillDate FROM t_work_progress twp where twp.work_id =:id ", nativeQuery = true)
		BigDecimal getTotalExpeditureTillDate(@Param("id") Long id);
		
		@Query(value=" select ms.work_sub_status_name_e from t_work_progress twp left join mst_work_sub_status ms on ms.work_sub_status_id = twp.work_sub_status_id where twp.work_id =:id and twp.work_status_id = 10",nativeQuery=true)
		String getLevelOfCompletion(@Param("id") Long id);
		
		@Query(value=" SELECT twp.date_completion as dateOfCommpletion FROM t_work_progress twp where twp.work_id =:id",nativeQuery = true)
		String getDateOfCompletion(@Param("id") Long id);

		@Query("SELECT w From WorkProgress w WHERE w.status = 'Active' ")
		List<WorkProgress> getAllWork();

}
