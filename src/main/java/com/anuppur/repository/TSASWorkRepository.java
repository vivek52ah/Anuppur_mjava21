package com.anuppur.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anuppur.entity.TSASReviseWork;
import com.anuppur.entity.TSASWork;


public interface TSASWorkRepository extends JpaRepository<TSASWork, Long>{
	
	
	@Query(value = "SELECT * FROM t_work_ts WHERE work_status != :status", nativeQuery = true)
	Page<TSASWork>  findByWorkStatusNotIn(Pageable pageable, @Param("status") String status);
	
	
	TSASWork  findByWorkId(Long id);
	
	
	@Query(value = "SELECT * FROM (\r\n"
			+ "    SELECT \r\n"
			+ "        t.rv_order_no AS order_no,\r\n"
			+ "        t.rv_order_date AS order_date,\r\n"
			+ "        t.rv_amt AS amt,\r\n"
			+ "        t.rv_document_upload AS document_upload,\r\n"
			+ "        t.rv_remarks AS remarks,\r\n"
			+ "        t.ts_as_status AS status,\r\n"
			+ "        t.id AS id\r\n"
			+ "    FROM \r\n"
			+ "        t_work_ts_revised t\r\n"
			+ "    WHERE \r\n"
			+ "        t.ts_as_status = 'Active' \r\n"
			+ "        AND t.work_id =:id"
			+ "        AND t.type_doc = 'AS'\r\n"
			+ "    \r\n"
			+ "    UNION ALL\r\n"
			+ "    \r\n"
			+ "    SELECT \r\n"
			+ "        ts.as_no AS order_no,\r\n"
			+ "        ts.as_date AS order_date,\r\n"
			+ "        ts.as_amt AS amt,\r\n"
			+ "        ts.as_document_upload AS document_upload,\r\n"
			+ "        ts.as_remarks AS remarks,\r\n"
			+ "        ts.ts_as_status AS status,\r\n"
			+ "        ts.id AS id\r\n"
			+ "    FROM \r\n"
			+ "        t_work_ts ts\r\n"
			+ "    WHERE \r\n"
			+ "        ts.ts_as_status = 'Active' \r\n"
			+ "        AND ts.work_id =:id"
			+ ") tr\r\n"
			+ "", nativeQuery = true)
	TSASWork  getWork(@Param("id") Long id);
	
	@Query(value="SELECT COUNT(*)\r\n"
			+ "FROM t_work_ts ts\r\n"
			+ "INNER JOIN t_work w ON ts.work_id = w.id\r\n"
			+ "WHERE ts.status IN ('Active') AND ts.work_status = 'AA Issued' and (w.scheme != 'null' or w.scheme != '') and (w.financial_year != 'null' or w.financial_year != '') and (w.work_head != 'null' or w.work_head != '')", nativeQuery=true)
	BigDecimal countastsWork();
	
	// aman 17-07-2024
		@Query(value=" SELECT twt.as_date as dateOfAdministrativeApproval FROM t_work_ts twt where work_id =:id ",nativeQuery=true)
		String getDateOfAdministrativeApproval(@Param("id") Long id  );
		
		@Query(value=" SELECT twt.as_amt as amountOfAdministrativeApproval FROM t_work_ts twt where work_id =:id ",nativeQuery=true)
		BigDecimal getAmountOfAdministrativeApproval(@Param("id") Long id  );
		
		@Query(value=" SELECT  CONCAT(SUBSTRING(as_date, 7, 4) ,'-', SUBSTRING(as_date, 9, 2)+1) as yearOfAdministrativeApproval FROM t_work_ts twt where work_id =:id ",nativeQuery=true)
		String getYearOfAdministrativeApproval(@Param("id") Long id  );
		
		@Query("SELECT w From TSASWork w WHERE w.status = 'Active' ")
		List<TSASWork> getAllWork();
		
		@Query(value = "select count(ifnull(vv.id,0))cancel_id from (\r\n"
				+ "SELECT (twt.id) as id  FROM t_work_ts twt where work_id =:workId  and ts_as_status ='Cancel' \r\n"
				+ "union all\r\n"
				+ "SELECT (tr.id) as id  FROM t_work_ts_revised tr  where work_id =:workId  and ts_as_status ='Cancel' )vv", nativeQuery = true)
		Long getCountId(@Param("workId") Long workId);
}
