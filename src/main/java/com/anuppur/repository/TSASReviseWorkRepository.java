package com.anuppur.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anuppur.entity.TSASReviseWork;
import com.anuppur.entity.Work;
import com.anuppur.entity.WorkProgress;




public interface TSASReviseWorkRepository extends JpaRepository<TSASReviseWork, Long>{
	
	
	@Query(value = "SELECT * FROM t_work_ts_revised WHERE work_status != :status", nativeQuery = true)
	Page<TSASReviseWork>  findByWorkStatusNotIn(Pageable pageable, @Param("status") String status);
	
	//TSASReviseWork  findByWorkId(Long id);
	TSASReviseWork  findByTsAsId(Long id);
	
	@Query(value = "select * from ("
			+ "select vx.id,IFNULL(vx.work_id,0)work_id,IFNULL(vx.status,'NA')status,ifnull(vx.doc_type,'NA')doc_type,ifnull(vx.num,0)num,ifnull(vx.date,'NA')date\r\n"
			+ ",ifnull(vx.amt,0)amt,ifnull(vx.remarks,'NA')remarks,ifnull(vx.document_upload,0)document_upload ,ifnull(vx.ts_as_status,'NA')ts_as_status\r\n"
			+ " ,(case when status = 'Original' and vx.ts_as_status = 'Cancel' then vx.id else 0 end)flag "
			+ " from (\r\n"
			+ " SELECT t.id,t.work_id as work_id,'Original' as status,'AS' as doc_type , t.as_no as num ,t.as_date as date,t.as_amt as amt ,t.as_remarks as remarks,t.as_document_upload as document_upload,t.ts_as_status as ts_as_status FROM t_work_ts t  \r\n"
			+ " union all \r\n"
			+ " SELECT twt.id,twt.work_id as work_id ,'Original' as status,'TS' as doc_type, twt.ts_no as num,twt.ts_date as date,twt.ts_amt as amt ,twt.ts_remarks as remarks,twt.ts_document_upload as document_upload,twt.ts_as_status as ts_as_status FROM t_work_ts twt \r\n"
			+ " union all SELECT tr.id,tr.work_id as work_id ,'Revised' status,tr.type_doc as doc_type,tr.rv_order_no as num,tr.rv_order_date as date,tr.rv_amt as amt ,tr.rv_remarks as remarks,tr.rv_document_upload as document_upload,tr.ts_as_status as ts_as_status FROM t_work_ts_revised tr  \r\n"
			+ " )vx  where vx.work_id =:id and vx.doc_type !='TS' )t order by status desc , ts_as_status asc ;\r\n"
			+ "\r\n"
			+ "", nativeQuery = true)
	List<Object[]> findByWorkId(@Param("id") Long id);

	Page<TSASReviseWork> findByWorkId(Pageable pageable, Long workTypeId);

	@Query(value = "SELECT count(*) FROM t_work_ts_revised WHERE status != :statusDeleted", nativeQuery = true)
	long countByStatusNotIn(@Param("statusDeleted") String statusDeleted);
	
	@Query("SELECT w FROM TSASReviseWork w WHERE w.work = :workId")
    List<TSASReviseWork> findAllByWorkId(@Param("workId") Long workId);
	
	@Query(value = "select CONCAT(IFNULL(vx.date, 'NA'), ' , ', IFNULL(vx.amt, 0)) AS date_amt\r\n"
			+ "\r\n"
			+ "from (\r\n"
			+ "SELECT t.id,t.work_id as work_id,'Original' as status,'AS' as doc_type , t.as_no as num ,t.as_date as date,t.as_amt as amt ,t.as_remarks as remarks,t.as_document_upload as document_upload,t.ts_as_status as ts_as_status FROM t_work_ts t  \r\n"
			+ "union all \r\n"
			+ "SELECT twt.id,twt.work_id as work_id ,'Original' as status,'TS' as doc_type, twt.ts_no as num,twt.ts_date as date,twt.ts_amt as amt ,twt.ts_remarks as remarks,twt.ts_document_upload as document_upload,twt.ts_as_status as ts_as_status FROM t_work_ts twt \r\n"
			+ "union all SELECT tr.id,tr.work_id as work_id ,'Revised' status,tr.type_doc as doc_type,tr.rv_order_no as num,tr.rv_order_date as date,tr.rv_amt as amt ,tr.rv_remarks as remarks,tr.rv_document_upload as document_upload,tr.ts_as_status as ts_as_status FROM t_work_ts_revised tr  \r\n"
			+ ")vx \r\n"
			+ " where vx.work_id =:id \r\n"
			+ "order by vx.id asc ,vx.doc_type desc, vx.status asc\r\n"
			+ "\r\n"
			+ ";" , nativeQuery = true)
	String getWorks(@Param("id") Long id);

	@Query("SELECT w From TSASReviseWork w WHERE w.status = 'Active' ")
	List<TSASReviseWork> getAllWork();
	
	
	
}