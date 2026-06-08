package com.anuppur.repository;



import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anuppur.bean.WorkExcelProjection;
import com.anuppur.entity.ImplementationAgency;
import com.anuppur.entity.Work;


public interface WorkRepository extends JpaRepository<Work, Long>{
	
	
	@Query(value = "SELECT * FROM t_work WHERE work_status != :status", nativeQuery = true)
	Page<Work>  findByWorkStatusNotIn(Pageable pageable, @Param("status") String status);
	
	@Query(value = "SELECT * FROM t_work WHERE work_head LIKE CONCAT('%', :headName, '%') AND status != :disabled LIMIT 1", nativeQuery = true)
	Work findByWorkHeadContainingAndStatusNotIn(@Param("headName") String headName, @Param("disabled") String disabled);
	
	@Query(value = "SELECT * FROM t_work WHERE status != :status", nativeQuery = true)
	Page<Work>  findByStatusNotIn(Pageable pageable, @Param("status") String status);
	
	@Query(value = "SELECT * FROM t_work WHERE work_name LIKE CONCAT('%', :workName, '%') AND status != :status", nativeQuery = true)
	Page<Work> findByWorkNameContainingAndStatusNotIn(Pageable pageable, @Param("workName") String workName, @Param("status") String status);
	
	@Query(value = "SELECT * FROM t_work WHERE work_type LIKE CONCAT('%', :workType, '%') AND status != :status", nativeQuery = true)
	Page<Work> findByWorkTypeContainingAndStatusNotIn(Pageable pageable, @Param("workType") String workType, @Param("status") String status);
	
	@Query(value = "SELECT * FROM t_work WHERE division_code = :divisionCode AND status != :status", nativeQuery = true)
	Page<Work> findByDivisionCodeAndStatusNotIn(Pageable pageable, @Param("divisionCode") Long divisionCode, @Param("status") String status);
	
	@Query(value = "SELECT * FROM t_work WHERE district_code = :districtCode AND status != :status", nativeQuery = true)
	Page<Work> findByDistrictCodeAndStatusNotIn(Pageable pageable, @Param("districtCode") String districtCode, @Param("status") String status);
	
	@Query(value = "SELECT * FROM t_work WHERE financial_year LIKE CONCAT('%', :financialYear, '%') AND status != :status", nativeQuery = true)
	Page<Work> findByFinancialYearContainingAndStatusNotIn(Pageable pageable, @Param("financialYear") String financialYear, @Param("status") String status);
	
	@Query(value = "SELECT count(*) FROM t_work WHERE work_status != :count", nativeQuery = true)
	long countByWorkStatusNotIn(@Param("count") String count);
	
	@Query(value = "SELECT count(*) FROM t_work WHERE status != :count", nativeQuery = true)
	long countByStatusNotIn(@Param("count") String count);

	@Query(value = "SELECT * FROM t_work WHERE work_name LIKE CONCAT('%', :workName, '%') AND division_code = :divisionCode AND status != :statusDeleted", nativeQuery = true)
	Page<Work> findByWorkNameContainingAndDivisionCodeAndStatusNotIn(Pageable pageable, @Param("workName") String workName,
			@Param("divisionCode") Long divisionCode, @Param("statusDeleted") String statusDeleted);

	@Query(value = "SELECT * FROM t_work WHERE work_type LIKE CONCAT('%', :workType, '%') AND division_code = :divisionCode AND status != :statusDeleted", nativeQuery = true)
	Page<Work> findByWorkTypeContainingAndDivisionCodeAndStatusNotIn(Pageable pageable, @Param("workType") String workType,
			@Param("divisionCode") Long divisionCode, @Param("statusDeleted") String statusDeleted);

	@Query(value = "SELECT * FROM t_work WHERE financial_year LIKE CONCAT('%', :financialYear, '%') AND division_code = :divisionCode AND status != :statusDeleted", nativeQuery = true)
	Page<Work> findByFinancialYearContainingAndDivisionCodeAndStatusNotIn(Pageable pageable, @Param("financialYear") String financialYear,
			@Param("divisionCode") Long divisionCode, @Param("statusDeleted") String statusDeleted);

	@Query(value = "SELECT * FROM t_work WHERE work_name LIKE CONCAT('%', :workName, '%') AND district_code = :districtCode AND status != :statusDeleted", nativeQuery = true)
	Page<Work> findByWorkNameContainingAndDistrictCodeAndStatusNotIn(Pageable pageable, @Param("workName") String workName,
			@Param("districtCode") String districtCode, @Param("statusDeleted") String statusDeleted);

	@Query(value = "SELECT * FROM t_work WHERE work_type LIKE CONCAT('%', :workType, '%') AND district_code = :districtCode AND status != :statusDeleted", nativeQuery = true)
	Page<Work> findByWorkTypeContainingAndDistrictCodeAndStatusNotIn(Pageable pageable, @Param("workType") String workType,
			@Param("districtCode") String districtCode, @Param("statusDeleted") String statusDeleted);

	@Query(value = "SELECT * FROM t_work WHERE financial_year LIKE CONCAT('%', :financialYear, '%') AND district_code = :districtCode AND status != :statusDeleted", nativeQuery = true)
	Page<Work> findByFinancialYearContainingAndDistrictCodeAndStatusNotIn(Pageable pageable, @Param("financialYear") String financialYear,
			@Param("districtCode") String districtCode, @Param("statusDeleted") String statusDeleted);

	@Query(value = "SELECT * FROM t_work WHERE work_name LIKE CONCAT('%', :workName, '%') AND work_type = :workType AND financial_year = :financialYear AND status != :statusDeleted", nativeQuery = true)
	Page<Work> findByWorkNameContainingAndWorkTypeAndFinancialYearAndStatusNotIn(Pageable pageable, @Param("workName") String workName,
			@Param("workType") String workType, @Param("financialYear") String financialYear, @Param("statusDeleted") String statusDeleted);

	@Query(value = "SELECT * FROM t_work WHERE scheme LIKE CONCAT('%', :schemeName, '%') AND status != :statusDeleted LIMIT 1", nativeQuery = true)
	Work findBySchemeContainingAndStatusNotIn(@Param("schemeName") String schemeName, @Param("statusDeleted") String statusDeleted);

	@Query(value = "SELECT * FROM t_work WHERE work_name LIKE CONCAT('%', :workName, '%') AND division_code = :divisionCode AND status != :statusDeleted AND work_status != :workStatusHandover", nativeQuery = true)
	Page<Work> findByWorkNameContainingAndDivisionCodeAndStatusNotInAndWorkStatusNotIn(Pageable pageable,
			@Param("workName") String workName, @Param("divisionCode") Long divisionCode, @Param("statusDeleted") String statusDeleted, @Param("workStatusHandover") String workStatusHandover);

	@Query(value = "SELECT * FROM t_work WHERE work_type LIKE CONCAT('%', :workType, '%') AND division_code = :divisionCode AND status != :statusDeleted AND work_status != :workStatusHandover", nativeQuery = true)
	Page<Work> findByWorkTypeContainingAndDivisionCodeAndStatusNotInAndWorkStatusNotIn(Pageable pageable,
			@Param("workType") String workType, @Param("divisionCode") Long divisionCode, @Param("statusDeleted") String statusDeleted, @Param("workStatusHandover") String workStatusHandover);

	@Query(value = "SELECT * FROM t_work WHERE financial_year LIKE CONCAT('%', :financialYear, '%') AND division_code = :divisionCode AND status != :statusDeleted AND work_status != :workStatusHandover", nativeQuery = true)
	Page<Work> findByFinancialYearContainingAndDivisionCodeAndStatusNotInAndWorkStatusNotIn(Pageable pageable,
			@Param("financialYear") String financialYear, @Param("divisionCode") Long divisionCode, @Param("statusDeleted") String statusDeleted, @Param("workStatusHandover") String workStatusHandover);

	@Query(value = "SELECT * FROM t_work WHERE division_code = :divisionCode AND status != :statusDeleted AND work_status != :workStatusHandover", nativeQuery = true)
	Page<Work> findByDivisionCodeAndStatusNotInAndWorkStatusNotIn(Pageable pageable, @Param("divisionCode") Long divisionCode,
			@Param("statusDeleted") String statusDeleted, @Param("workStatusHandover") String workStatusHandover);

	@Query(value = "SELECT * FROM t_work WHERE work_name LIKE CONCAT('%', :workName, '%') AND district_code = :districtCode AND status != :statusDeleted AND work_status != :workStatusHandover", nativeQuery = true)
	Page<Work> findByWorkNameContainingAndDistrictCodeAndStatusNotInAndWorkStatusNotIn(Pageable pageable,
			@Param("workName") String workName, @Param("districtCode") String districtCode, @Param("statusDeleted") String statusDeleted, @Param("workStatusHandover") String workStatusHandover);

	@Query(value = "SELECT * FROM t_work WHERE work_type LIKE CONCAT('%', :workType, '%') AND district_code = :districtCode AND status != :statusDeleted AND work_status != :workStatusHandover", nativeQuery = true)
	Page<Work> findByWorkTypeContainingAndDistrictCodeAndStatusNotInAndWorkStatusNotIn(Pageable pageable,
			@Param("workType") String workType, @Param("districtCode") String districtCode, @Param("statusDeleted") String statusDeleted, @Param("workStatusHandover") String workStatusHandover);

	@Query(value = "SELECT * FROM t_work WHERE financial_year LIKE CONCAT('%', :financialYear, '%') AND district_code = :districtCode AND status != :statusDeleted AND work_status != :workStatusHandover", nativeQuery = true)
	Page<Work> findByFinancialYearContainingAndDistrictCodeAndStatusNotInAndWorkStatusNotIn(Pageable pageable,
			@Param("financialYear") String financialYear, @Param("districtCode") String districtCode, @Param("statusDeleted") String statusDeleted, @Param("workStatusHandover") String workStatusHandover);

	@Query(value = "SELECT * FROM t_work WHERE district_code = :districtCode AND status != :statusDeleted AND work_status != :workStatusHandover", nativeQuery = true)
	Page<Work> findByDistrictCodeAndStatusNotInAndWorkStatusNotIn(Pageable pageable, @Param("districtCode") String districtCode,
			@Param("statusDeleted") String statusDeleted, @Param("workStatusHandover") String workStatusHandover);

	@Query(value = "SELECT * FROM t_work WHERE work_name LIKE CONCAT('%', :workName, '%') AND status != :statusDeleted AND work_status != :workStatusHandover", nativeQuery = true)
	Page<Work> findByWorkNameContainingAndStatusNotInAndWorkStatusNotIn(Pageable pageable, @Param("workName") String workName,
			@Param("statusDeleted") String statusDeleted, @Param("workStatusHandover") String workStatusHandover);

	@Query(value = "SELECT * FROM t_work WHERE work_type LIKE CONCAT('%', :workType, '%') AND status != :statusDeleted AND work_status != :workStatusHandover", nativeQuery = true)
	Page<Work> findByWorkTypeContainingAndStatusNotInAndWorkStatusNotIn(Pageable pageable, @Param("workType") String workType,
			@Param("statusDeleted") String statusDeleted, @Param("workStatusHandover") String workStatusHandover);

	@Query(value = "SELECT * FROM t_work WHERE financial_year LIKE CONCAT('%', :financialYear, '%') AND status != :statusDeleted AND work_status != :workStatusHandover", nativeQuery = true)
	Page<Work> findByFinancialYearContainingAndStatusNotInAndWorkStatusNotIn(Pageable pageable, @Param("financialYear") String financialYear,
			@Param("statusDeleted") String statusDeleted, @Param("workStatusHandover") String workStatusHandover);

	@Query(value = "SELECT * FROM t_work WHERE status != :statusDeleted AND work_status != :workStatusHandover", nativeQuery = true)
	Page<Work> findByStatusNotInAndWorkStatusNotIn(Pageable pageable, @Param("statusDeleted") String statusDeleted, @Param("workStatusHandover") String workStatusHandover);

	@Query(value = "SELECT * FROM t_work WHERE work_name LIKE CONCAT('%', :workName, '%') AND division_code = :divisionCode AND status != :statusDeleted AND work_status = :workStatusHandover", nativeQuery = true)
	Page<Work> findByWorkNameContainingAndDivisionCodeAndStatusNotInAndWorkStatusIn(Pageable pageable, @Param("workName") String workName,
			@Param("divisionCode") Long divisionCode, @Param("statusDeleted") String statusDeleted, @Param("workStatusHandover") Long workStatusHandover);

	@Query(value = "SELECT * FROM t_work WHERE work_type LIKE CONCAT('%', :workType, '%') AND division_code = :divisionCode AND status != :statusDeleted AND work_status = :workStatusHandover", nativeQuery = true)
	Page<Work> findByWorkTypeContainingAndDivisionCodeAndStatusNotInAndWorkStatusIn(Pageable pageable, @Param("workType") String workType,
			@Param("divisionCode") Long divisionCode, @Param("statusDeleted") String statusDeleted, @Param("workStatusHandover") String workStatusHandover);

	@Query(value = "SELECT * FROM t_work WHERE financial_year LIKE CONCAT('%', :financialYear, '%') AND division_code = :divisionCode AND status != :statusDeleted AND work_status = :workStatusHandover", nativeQuery = true)
	Page<Work> findByFinancialYearContainingAndDivisionCodeAndStatusNotInAndWorkStatusIn(Pageable pageable,
			@Param("financialYear") Long financialYear, @Param("divisionCode") Long divisionCode, @Param("statusDeleted") String statusDeleted, @Param("workStatusHandover") String workStatusHandover);

	@Query(value = "SELECT * FROM t_work a WHERE 1=1 AND (a.work_name = COALESCE(:workName, a.work_name)) AND (a.division_code = COALESCE(:divisionCode, a.division_code)) AND (a.division_id = COALESCE(:divisionId, a.division_id)) AND (a.district_id = COALESCE(:districtId, a.district_id)) AND (a.work_subtype_id = COALESCE(:workSubtypeId, a.work_subtype_id)) AND (a.work_status = COALESCE(:workStatus, a.work_status)) AND (a.financial_year = COALESCE(:financialYear, a.financial_year)) AND (a.work_type = COALESCE(:workType, a.work_type)) AND (a.implementation_agency = COALESCE(:agency, a.implementation_agency)) AND a.work_status IN ('12') AND a.status NOT IN ('Deleted')", nativeQuery = true)
	Page<Work> findByDivisionCode(Pageable pageable, @Param("workName") String workName, @Param("divisionCode") Long divisionCode,
			@Param("divisionId") Long divisionId, @Param("districtId") Long districtId, @Param("workSubtypeId") Integer workSubTypeIdInt, @Param("workStatus") String workStatusId, @Param("financialYear") Long financialYearId, @Param("workType") Long workTypeId,  @Param("agency") Long agency);
	
	@Query(value = "SELECT * FROM t_work WHERE division_code = :divisionCode AND status != :statusDeleted AND work_status = :workStatusHandover", nativeQuery = true)
	Page<Work> findByDivisionCodeAndStatusNotInAndWorkStatusIn(Pageable pageable, @Param("divisionCode") Long divisionCode,
			@Param("statusDeleted") String statusDeleted, @Param("workStatusHandover") Long workStatusHandover);

	@Query(value = "SELECT * FROM t_work WHERE work_name LIKE CONCAT('%', :workName, '%') AND district_code = :districtCode AND status != :statusDeleted AND work_status = :workStatusHandover", nativeQuery = true)
	Page<Work> findByWorkNameContainingAndDistrictCodeAndStatusNotInAndWorkStatusIn(Pageable pageable, @Param("workName") String workName,
			@Param("districtCode") String districtCode, @Param("statusDeleted") String statusDeleted, @Param("workStatusHandover") Long workStatusHandover);

	@Query(value = "SELECT * FROM t_work WHERE work_type LIKE CONCAT('%', :workType, '%') AND district_code = :districtCode AND status != :statusDeleted AND work_status = :workStatusHandover", nativeQuery = true)
	Page<Work> findByWorkTypeContainingAndDistrictCodeAndStatusNotInAndWorkStatusIn(Pageable pageable, @Param("workType") String workType,
			@Param("districtCode") String districtCode, @Param("statusDeleted") String statusDeleted, @Param("workStatusHandover") String workStatusHandover);

	@Query(value = "SELECT * FROM t_work a WHERE 1=1 AND (a.work_name = COALESCE(:workName, a.work_name)) AND (a.district_code = COALESCE(:districtCode, a.district_code)) AND (a.division_id = COALESCE(:divisionId, a.division_id)) AND (a.district_id = COALESCE(:districtId, a.district_id)) AND (a.work_subtype_id = COALESCE(:workSubtypeId, a.work_subtype_id)) AND (a.work_status = COALESCE(:workStatus, a.work_status)) AND (a.financial_year = COALESCE(:financialYear, a.financial_year)) AND (a.work_type = COALESCE(:workType, a.work_type)) AND (a.implementation_agency = COALESCE(:agency, a.implementation_agency)) AND a.work_status IN ('12') AND a.status NOT IN ('Deleted')", nativeQuery = true)
	Page<Work> findByDistrictCodeContainingAndDistrictCodeAndStatusNotInAndWorkStatusIn(Pageable pageable,@Param("workName") String workName, @Param("districtCode") String districtCode,
			@Param("divisionId") Long divisionId, @Param("districtId") Long districtId, @Param("workSubtypeId") Integer workSubTypeIdInt, @Param("workStatus") String workStatusId, @Param("financialYear") Long financialYearId, @Param("workType") Long workTypeId, @Param("agency") Long agency);
	
	
	@Query(value = "SELECT * FROM t_work WHERE financial_year LIKE CONCAT('%', :financialYear, '%') AND district_code = :districtCode AND status != :statusDeleted AND work_status = :workStatusHandover", nativeQuery = true)
	Page<Work> findByFinancialYearContainingAndDistrictCodeAndStatusNotInAndWorkStatusIn(Pageable pageable,
			@Param("financialYear") Long financialYear, @Param("districtCode") String districtCode, @Param("statusDeleted") String statusDeleted, @Param("workStatusHandover") String workStatusHandover);

	@Query(value = "SELECT * FROM t_work WHERE district_code = :districtCode AND status != :statusDeleted AND work_status = :workStatusHandover", nativeQuery = true)
	Page<Work> findByDistrictCodeAndStatusNotInAndWorkStatusIn(Pageable pageable, @Param("districtCode") String districtCode,
			@Param("statusDeleted") String statusDeleted, @Param("workStatusHandover") Long workStatusHandover);

	@Query(value = "SELECT * FROM t_work WHERE work_name LIKE CONCAT('%', :workName, '%') AND status != :statusDeleted AND work_status = :workStatusHandover", nativeQuery = true)
	Page<Work> findByWorkNameContainingAndStatusNotInAndWorkStatusIn(Pageable pageable, @Param("workName") String workName,
			@Param("statusDeleted") String statusDeleted, @Param("workStatusHandover") Long workStatusHandover);

	@Query(value = "SELECT * FROM t_work WHERE work_type LIKE CONCAT('%', :workType, '%') AND status != :statusDeleted AND work_status = :workStatusHandover", nativeQuery = true)
	Page<Work> findByWorkTypeContainingAndStatusNotInAndWorkStatusIn(Pageable pageable, @Param("workType") String workType,
			@Param("statusDeleted") String statusDeleted, @Param("workStatusHandover") String workStatusHandover);
	
	
	/*
	 * @Query(" from Work a where 1=1 and (a.financialYear=COALESCE(:financialYear,a.financialYear)) and a.workStatus in ('12') and a.status not in ('Deleted')"
	 * ) Page<Work> findByFinancialYearIdAndStatusNotInAndWorkStatusIn(Pageable
	 * pageable,
	 * 
	 * @Param("financialYear") Long financialYearId);
	 */


	/*
	 * Page<Work>
	 * findByFinancialYearContainingAndStatusNotInAndWorkStatusIn(Pageable pageable,
	 * Long financialYearId, String statusDeleted, Long workStatusHandover);
	 */
	
	@Query(value = "SELECT * FROM t_work a WHERE 1=1 AND (a.work_name = COALESCE(:workName, a.work_name)) AND (a.division_id = COALESCE(:divisionId, a.division_id)) AND (a.district_id = COALESCE(:districtId, a.district_id)) AND (a.work_subtype_id = COALESCE(:workSubtypeId, a.work_subtype_id)) AND (a.work_status = COALESCE(:workStatus, a.work_status)) AND (a.financial_year = COALESCE(:financialYear, a.financial_year)) AND (a.work_type = COALESCE(:workType, a.work_type)) AND (a.implementation_agency = COALESCE(:agency, a.implementation_agency)) AND a.work_status IN ('12') AND a.status NOT IN ('Deleted')", nativeQuery = true)
	Page<Work> findByDivisionNameContainingAndStatusNotInAndWorkStatusIn(Pageable pageable, @Param("workName") String workName,
			@Param("divisionId") Long divisionId, @Param("districtId") Long districtId,
			@Param("workSubtypeId") Integer workSubTypeIdInt, @Param("workStatus") String workStatusName, @Param("financialYear") Long financialYearId,  @Param("workType") Long workTypeId, @Param("agency") Long agency);
	
	@Query(value = "SELECT * FROM t_work WHERE status != :statusDeleted AND work_status = :workStatusHandover", nativeQuery = true)
	Page<Work> findByStatusNotInAndWorkStatusIn(Pageable pageable, @Param("statusDeleted") String statusDeleted, @Param("workStatusHandover") Long workStatusHandover);
     
	
	@Query(value="SELECT count(*) from t_work where status in ('Active') and (scheme is not null) and (financial_year is not null) and (work_head is not null)", nativeQuery=true)
	BigDecimal countWork();
	
	
	
    @Query(value="SELECT mia.impl_agency_name AS Name_of_Agency, COUNT(a.id) AS Number_of_Work, SUM(b.as_amt) AS Amount_of_AA, "
    		+ "SUM(c.expensess_upto_march) AS Total_Expenditure_up_to_March_23, sum(c.expensess_current_fy) AS Expenditure_in_Yr_2023_24, sum(c.total_expensess) AS "
    		+ "Up_to_Date_Expenditure, "
    		+ "SUM(CASE WHEN a.work_status = 'Handed Over' THEN  1 else 0 end) as No_of_Work_Handed_Over_in_Yr_2023_24, "
    		+ "SUM(CASE WHEN a.work_status = 'Completed' THEN  1 else 0 end) as No_of_Work_Completed_in_Yr_2023_24, "
    		+ "SUM(CASE WHEN a.work_status = 'In-Progress' THEN  1 else 0 end) as Work_Started, "
    		+ "SUM(CASE WHEN a.work_status = 'Not Started' THEN  1 else 0 end) as Not_Started, "
    		+ "SUM(dud.Finishing_Level) as Finishing_Level, SUM(dud.Roof_Level) as Roof_Level, SUM(dud.Lintel_Level) as Lintel_Level, "
    		+ " SUM(dud.Plinth_Level) as Plinth_Level, SUM(dud.Foundation_level) as Foundation_level, "
    		+ "SUM(dud.Work_is_not_started_by_the_contractor) as Work_is_not_started_by_the_contractor, "
    		+ "SUM(CASE WHEN a.work_status = 'Work Order Issued' THEN  1 else 0 end) as Tender_Issued, "
    		+ "SUM(CASE WHEN a.work_status = 'Tender Called' or a.work_status = 'Tender Received' or "
    		+ "a.work_status = 'Tender Approval in Process' or a.work_status = 'Re-Tender' or "
    		+ "a.work_status = 'LoA Issued'  THEN  1 else 0 end) as Tender_Awarded "
    		+ "from t_work a  "
    		+ "LEFT JOIN "
    		+ "t_work_ts b on a.id = b.work_id "
    		+ "LEFT JOIN "
    		+ "(SELECT work_id,SUM(expensess_upto_march) AS expensess_upto_march ,SUM(expensess_current_fy) AS expensess_current_fy, "
    		+ "SUM(total_expensess) AS total_expensess FROM expenses_cost GROUP BY 1 ) AS c ON a.id = c.work_id "
    		+ "left join "
    		+ "("
    		+ "select work_id, "
    		+ "SUM(CASE WHEN du.work_sub_status_id = 9 THEN 1 else 0 end) as Finishing_Level, "
    		+ "SUM(CASE WHEN du.work_sub_status_id = 8 THEN 1 else 0 end) as Roof_Level, "
    		+ "SUM(CASE WHEN du.work_sub_status_id = 7 THEN 1 else 0 end) as Lintel_Level, "
    		+ "SUM(CASE WHEN du.work_sub_status_id = 6 THEN 1 else 0 end) as Plinth_Level, "
    		+ "SUM(CASE WHEN du.work_sub_status_id = 5 THEN 1 else 0 end) as Foundation_level, "
    		+ "SUM(CASE WHEN du.work_sub_status_id = 2 THEN 1 else 0 end) as Work_is_not_started_by_the_contractor "
    		+ "from document_upload_workprogress_details du group by work_id) as dud on a.id = dud.work_id"
    		+ " left join mst_implementation_agency mia on mia.id = a.implementation_agency WHERE a.status != 'Deleted' and (a.financial_year != 'null' or a.financial_year != '') and (a.scheme != 'null' or a.scheme != '') and (a.work_head != 'null' or a.work_head != '')" 
    		+ "group by a.implementation_agency", nativeQuery=true)
	List<Object[]> fetchAgencyWiseWork();
	
	
	
	 @Query(value="SELECT s.scheme_name AS scheme, COUNT(a.id) AS Number_of_Work, SUM(b.as_amt) AS Amount_of_AA, "
	    		+ "SUM(c.expensess_upto_march) AS Total_Expenditure_up_to_March_23, sum(c.expensess_current_fy) AS Expenditure_in_Yr_2023_24, sum(c.total_expensess) AS "
	    		+ "Up_to_Date_Expenditure, "
	    		+ "SUM(CASE WHEN a.work_status = 'Handed Over' THEN  1 else 0 end) as No_of_Work_Handed_Over_in_Yr_2023_24, "
	    		+ "SUM(CASE WHEN a.work_status = 'Completed' THEN  1 else 0 end) as No_of_Work_Completed_in_Yr_2023_24, "
	    		+ "SUM(CASE WHEN a.work_status = 'In-Progress' THEN  1 else 0 end) as Work_Started, "
	    		+ "SUM(CASE WHEN a.work_status = 'Not Started' THEN  1 else 0 end) as Not_Started, "
	    		+ "SUM(dud.Finishing_Level) as Finishing_Level, SUM(dud.Roof_Level) as Roof_Level, SUM(dud.Lintel_Level) as Lintel_Level, "
	    		+ " SUM(dud.Plinth_Level) as Plinth_Level, SUM(dud.Foundation_level) as Foundation_level, "
	    		+ "SUM(dud.Work_is_not_started_by_the_contractor) as Work_is_not_started_by_the_contractor, "
	    		+ "SUM(CASE WHEN a.work_status = 'Work Order Issued' THEN  1 else 0 end) as Tender_Issued, "
	    		+ "SUM(CASE WHEN a.work_status = 'Tender Called' or a.work_status = 'Tender Received' or "
	    		+ "a.work_status = 'Tender Approval in Process' or a.work_status = 'Re-Tender' or "
	    		+ "a.work_status = 'LoA Issued'  THEN  1 else 0 end) as Tender_Awarded "
	    		+ "from t_work a  "
	    		+ "LEFT JOIN "
	    		+ "t_work_ts b on a.id = b.work_id "
	    		+ "LEFT JOIN "
	    		+ "(SELECT work_id,SUM(expensess_upto_march) AS expensess_upto_march ,SUM(expensess_current_fy) AS expensess_current_fy, "
	    		+ "SUM(total_expensess) AS total_expensess FROM expenses_cost GROUP BY 1 ) AS c ON a.id = c.work_id "
	    		+ "left join "
	    		+ "("
	    		+ "select work_id, "
	    		+ "SUM(CASE WHEN du.work_sub_status_id = 9 THEN 1 else 0 end) as Finishing_Level, "
	    		+ "SUM(CASE WHEN du.work_sub_status_id = 8 THEN 1 else 0 end) as Roof_Level, "
	    		+ "SUM(CASE WHEN du.work_sub_status_id = 7 THEN 1 else 0 end) as Lintel_Level, "
	    		+ "SUM(CASE WHEN du.work_sub_status_id = 6 THEN 1 else 0 end) as Plinth_Level, "
	    		+ "SUM(CASE WHEN du.work_sub_status_id = 5 THEN 1 else 0 end) as Foundation_level, "
	    		+ "SUM(CASE WHEN du.work_sub_status_id = 2 THEN 1 else 0 end) as Work_is_not_started_by_the_contractor "
	    		+ "from document_upload_workprogress_details du group by work_id) as dud on a.id = dud.work_id "
	    		+ "left join mst_schemes s on s.id = a.scheme WHERE a.status != 'Deleted'"
	    		+ " and (a.financial_year != 'null' or a.financial_year != '') and (a.scheme != 'null' or a.scheme != '') and (a.work_head != 'null' or a.work_head != '')  group by 1, a.scheme_state, a.scheme_nhm, a.scheme_ecrp2, a.scheme_others", nativeQuery=true)
		List<Object[]> fetchSchemeWiseWork();
		
		
		
		 @Query(value="SELECT fy.financial_year AS year, COUNT(a.id) AS Number_of_Work, SUM(b.as_amt) AS Amount_of_AA, "
		    		+ "SUM(c.expensess_upto_march) AS Total_Expenditure_up_to_March_23, sum(c.expensess_current_fy) AS Expenditure_in_Yr_2023_24, sum(c.total_expensess) AS "
		    		+ "Up_to_Date_Expenditure, "
		    		+ "SUM(CASE WHEN a.work_status = 'Handed Over' THEN  1 else 0 end) as No_of_Work_Handed_Over_in_Yr_2023_24, "
		    		+ "SUM(CASE WHEN a.work_status = 'Completed' THEN  1 else 0 end) as No_of_Work_Completed_in_Yr_2023_24, "
		    		+ "SUM(CASE WHEN a.work_status = 'In-Progress' THEN  1 else 0 end) as Work_Started, "
		    		+ "SUM(CASE WHEN a.work_status = 'Not Started' THEN  1 else 0 end) as Not_Started, "
		    		+ "SUM(dud.Finishing_Level) as Finishing_Level, SUM(dud.Roof_Level) as Roof_Level, SUM(dud.Lintel_Level) as Lintel_Level, "
		    		+ " SUM(dud.Plinth_Level) as Plinth_Level, SUM(dud.Foundation_level) as Foundation_level, "
		    		+ "SUM(dud.Work_is_not_started_by_the_contractor) as Work_is_not_started_by_the_contractor, "
		    		+ "SUM(CASE WHEN a.work_status = 'Work Order Issued' THEN  1 else 0 end) as Tender_Issued, "
		    		+ "SUM(CASE WHEN a.work_status = 'Tender Called' or a.work_status = 'Tender Received' or "
		    		+ "a.work_status = 'Tender Approval in Process' or a.work_status = 'Re-Tender' or "
		    		+ "a.work_status = 'LoA Issued'  THEN  1 else 0 end) as Tender_Awarded "
		    		+ "from t_work a  "
		    		+ "LEFT JOIN "
		    		+ "t_work_ts b on a.id = b.work_id AND a.status != 'Deleted' "
		    		+ "LEFT JOIN "
		    		+ "(SELECT work_id,SUM(expensess_upto_march) AS expensess_upto_march ,SUM(expensess_current_fy) AS expensess_current_fy, "
		    		+ "SUM(total_expensess) AS total_expensess FROM expenses_cost GROUP BY 1 ) AS c ON a.id = c.work_id "
		    		+ "left join "
		    		+ "("
		    		+ "select work_id, "
		    		+ "SUM(CASE WHEN du.work_sub_status_id = 9 THEN 1 else 0 end) as Finishing_Level, "
		    		+ "SUM(CASE WHEN du.work_sub_status_id = 8 THEN 1 else 0 end) as Roof_Level, "
		    		+ "SUM(CASE WHEN du.work_sub_status_id = 7 THEN 1 else 0 end) as Lintel_Level, "
		    		+ "SUM(CASE WHEN du.work_sub_status_id = 6 THEN 1 else 0 end) as Plinth_Level, "
		    		+ "SUM(CASE WHEN du.work_sub_status_id = 5 THEN 1 else 0 end) as Foundation_level, "
		    		+ "SUM(CASE WHEN du.work_sub_status_id = 2 THEN 1 else 0 end) as Work_is_not_started_by_the_contractor "
		    		+ "from document_upload_workprogress_details du group by work_id) as dud on a.id = dud.work_id "
		    		+ "left join mst_financial_year fy on fy.id = a.financial_year where  a.status != 'Deleted' and (a.financial_year != 'null' or a.financial_year != '') and (a.scheme != 'null' or a.scheme != '') and (a.work_head != 'null' or a.work_head != '')"
		    		+ "group by 1", nativeQuery=true)
			List<Object[]> fetchYearWiseWork();
			
			
			 @Query(value="SELECT wh.head_name AS segment, COUNT(a.id) AS Number_of_Work, SUM(b.as_amt) AS Amount_of_AA, "
			    		+ "SUM(c.expensess_upto_march) AS Total_Expenditure_up_to_March_23, sum(c.expensess_current_fy) AS Expenditure_in_Yr_2023_24, sum(c.total_expensess) AS "
			    		+ "Up_to_Date_Expenditure, "
			    		+ "SUM(CASE WHEN a.work_status = 'Handed Over' THEN  1 else 0 end) as No_of_Work_Handed_Over_in_Yr_2023_24, "
			    		+ "SUM(CASE WHEN a.work_status = 'Completed' THEN  1 else 0 end) as No_of_Work_Completed_in_Yr_2023_24, "
			    		+ "SUM(CASE WHEN a.work_status = 'In-Progress' THEN  1 else 0 end) as Work_Started, "
			    		+ "SUM(CASE WHEN a.work_status = 'Not Started' THEN  1 else 0 end) as Not_Started, "
			    		+ "SUM(dud.Finishing_Level) as Finishing_Level, SUM(dud.Roof_Level) as Roof_Level, SUM(dud.Lintel_Level) as Lintel_Level, "
			    		+ " SUM(dud.Plinth_Level) as Plinth_Level, SUM(dud.Foundation_level) as Foundation_level, "
			    		+ "SUM(dud.Work_is_not_started_by_the_contractor) as Work_is_not_started_by_the_contractor, "
			    		+ "SUM(CASE WHEN a.work_status = 'Work Order Issued' THEN  1 else 0 end) as Tender_Issued, "
			    		+ "SUM(CASE WHEN a.work_status = 'Tender Called' or a.work_status = 'Tender Received' or "
			    		+ "a.work_status = 'Tender Approval in Process' or a.work_status = 'Re-Tender' or "
			    		+ "a.work_status = 'LoA Issued'  THEN  1 else 0 end) as Tender_Awarded "
			    		+ "from t_work a  "
			    		+ "LEFT JOIN "
			    		+ "t_work_ts b on a.id = b.work_id "
			    		+ "LEFT JOIN "
			    		+ "(SELECT work_id,SUM(expensess_upto_march) AS expensess_upto_march ,SUM(expensess_current_fy) AS expensess_current_fy, "
			    		+ "SUM(total_expensess) AS total_expensess FROM expenses_cost GROUP BY 1 ) AS c ON a.id = c.work_id "
			    		+ "left join "
			    		+ "("
			    		+ "select work_id, "
			    		+ "SUM(CASE WHEN du.work_sub_status_id = 9 THEN 1 else 0 end) as Finishing_Level, "
			    		+ "SUM(CASE WHEN du.work_sub_status_id = 8 THEN 1 else 0 end) as Roof_Level, "
			    		+ "SUM(CASE WHEN du.work_sub_status_id = 7 THEN 1 else 0 end) as Lintel_Level, "
			    		+ "SUM(CASE WHEN du.work_sub_status_id = 6 THEN 1 else 0 end) as Plinth_Level, "
			    		+ "SUM(CASE WHEN du.work_sub_status_id = 5 THEN 1 else 0 end) as Foundation_level, "
			    		+ "SUM(CASE WHEN du.work_sub_status_id = 2 THEN 1 else 0 end) as Work_is_not_started_by_the_contractor "
			    		+ "from document_upload_workprogress_details du group by work_id) as dud on a.id = dud.work_id "
			    		+ "left join mst_work_head wh on wh.id = a.work_head WHERE a.status != 'Deleted' and (a.financial_year != 'null' or a.financial_year != '') and (a.scheme != 'null' or a.scheme != '') and (a.work_head != 'null' or a.work_head != '')"
			    		+ "group by 1,a.head_state, a.head_nhm, a.head_ecrp2, a.head_others", nativeQuery=true)
				List<Object[]> fetchSegmentWiseWork();
				
				
				
				 @Query(value="SELECT concat(s.scheme_name,'(',fy.financial_year,')') as schemeYear, COUNT(a.id) AS Number_of_Work, SUM(b.as_amt) AS Amount_of_AA, "
				    		+ "SUM(c.expensess_upto_march) AS Total_Expenditure_up_to_March_23, sum(c.expensess_current_fy) AS Expenditure_in_Yr_2023_24, sum(c.total_expensess) AS "
				    		+ "Up_to_Date_Expenditure, "
				    		+ "SUM(CASE WHEN a.work_status = 'Handed Over' THEN  1 else 0 end) as No_of_Work_Handed_Over_in_Yr_2023_24, "
				    		+ "SUM(CASE WHEN a.work_status = 'Completed' THEN  1 else 0 end) as No_of_Work_Completed_in_Yr_2023_24, "
				    		+ "SUM(CASE WHEN a.work_status = 'In-Progress' THEN  1 else 0 end) as Work_Started, "
				    		+ "SUM(CASE WHEN a.work_status = 'Not Started' THEN  1 else 0 end) as Not_Started, "
				    		+ "SUM(dud.Finishing_Level) as Finishing_Level, SUM(dud.Roof_Level) as Roof_Level, SUM(dud.Lintel_Level) as Lintel_Level, "
				    		+ " SUM(dud.Plinth_Level) as Plinth_Level, SUM(dud.Foundation_level) as Foundation_level, "
				    		+ "SUM(dud.Work_is_not_started_by_the_contractor) as Work_is_not_started_by_the_contractor, "
				    		+ "SUM(CASE WHEN a.work_status = 'Work Order Issued' THEN  1 else 0 end) as Tender_Issued, "
				    		+ "SUM(CASE WHEN a.work_status = 'Tender Called' or a.work_status = 'Tender Received' or "
				    		+ "a.work_status = 'Tender Approval in Process' or a.work_status = 'Re-Tender' or "
				    		+ "a.work_status = 'LoA Issued'  THEN  1 else 0 end) as Tender_Awarded "
				    		+ "from t_work a  "
				    		+ "LEFT JOIN "
				    		+ "t_work_ts b on a.id = b.work_id "
				    		+ "LEFT JOIN "
				    		+ "(SELECT work_id,SUM(expensess_upto_march) AS expensess_upto_march ,SUM(expensess_current_fy) AS expensess_current_fy, "
				    		+ "SUM(total_expensess) AS total_expensess FROM expenses_cost GROUP BY 1 ) AS c ON a.id = c.work_id "
				    		+ "left join "
				    		+ "("
				    		+ "select work_id, "
				    		+ "SUM(CASE WHEN du.work_sub_status_id = 9 THEN 1 else 0 end) as Finishing_Level, "
				    		+ "SUM(CASE WHEN du.work_sub_status_id = 8 THEN 1 else 0 end) as Roof_Level, "
				    		+ "SUM(CASE WHEN du.work_sub_status_id = 7 THEN 1 else 0 end) as Lintel_Level, "
				    		+ "SUM(CASE WHEN du.work_sub_status_id = 6 THEN 1 else 0 end) as Plinth_Level, "
				    		+ "SUM(CASE WHEN du.work_sub_status_id = 5 THEN 1 else 0 end) as Foundation_level, "
				    		+ "SUM(CASE WHEN du.work_sub_status_id = 2 THEN 1 else 0 end) as Work_is_not_started_by_the_contractor "
				    		+ "from document_upload_workprogress_details du group by work_id) as dud on a.id = dud.work_id "
				    		+ "left join mst_schemes s on s.id = a.scheme left join mst_financial_year fy on fy.id = a.financial_year WHERE a.status != 'Deleted' and (a.financial_year != 'null' or a.financial_year != '') and (a.scheme != 'null' or a.scheme != '') and (a.work_head != 'null' or a.work_head != '')"
				    		+ "group by 1, a.scheme_state, a.scheme_nhm, a.scheme_ecrp2, a.scheme_others", nativeQuery=true)
					List<Object[]> fetchSchemeYearWiseWork();
					
					
					
					

					 @Query(value="SELECT a.division_name as division,a.district_name as district, COUNT(a.id) AS Number_of_Work, SUM(b.as_amt) AS Amount_of_AA, "
					    		+ "SUM(c.expensess_upto_march) AS Total_Expenditure_up_to_March_23, sum(c.expensess_current_fy) AS Expenditure_in_Yr_2023_24, sum(c.total_expensess) AS "
					    		+ "Up_to_Date_Expenditure, "
					    		+ "SUM(CASE WHEN a.work_status = 'Handed Over' THEN  1 else 0 end) as No_of_Work_Handed_Over_in_Yr_2023_24, "
					    		+ "SUM(CASE WHEN a.work_status = 'Completed' THEN  1 else 0 end) as No_of_Work_Completed_in_Yr_2023_24, "
					    		+ "SUM(CASE WHEN a.work_status = 'In-Progress' THEN  1 else 0 end) as Work_Started, "
					    		+ "SUM(CASE WHEN a.work_status = 'Not Started' THEN  1 else 0 end) as Not_Started, "
					    		+ "SUM(dud.Finishing_Level) as Finishing_Level, SUM(dud.Roof_Level) as Roof_Level, SUM(dud.Lintel_Level) as Lintel_Level, "
					    		+ " SUM(dud.Plinth_Level) as Plinth_Level, SUM(dud.Foundation_level) as Foundation_level, "
					    		+ "SUM(dud.Work_is_not_started_by_the_contractor) as Work_is_not_started_by_the_contractor, "
					    		+ "SUM(CASE WHEN a.work_status = 'Work Order Issued' THEN  1 else 0 end) as Tender_Issued, "
					    		+ "SUM(CASE WHEN a.work_status = 'Tender Called' or a.work_status = 'Tender Received' or "
					    		+ "a.work_status = 'Tender Approval in Process' or a.work_status = 'Re-Tender' or "
					    		+ "a.work_status = 'LoA Issued'  THEN  1 else 0 end) as Tender_Awarded "
					    		+ "from t_work a  "
					    		+ "LEFT JOIN "
					    		+ "t_work_ts b on a.id = b.work_id "
					    		+ "LEFT JOIN "
					    		+ "(SELECT work_id,SUM(expensess_upto_march) AS expensess_upto_march ,SUM(expensess_current_fy) AS expensess_current_fy, "
					    		+ "SUM(total_expensess) AS total_expensess FROM expenses_cost GROUP BY 1 ) AS c ON a.id = c.work_id "
					    		+ "left join "
					    		+ "("
					    		+ "select work_id, "
					    		+ "SUM(CASE WHEN du.work_sub_status_id = 9 THEN 1 else 0 end) as Finishing_Level, "
					    		+ "SUM(CASE WHEN du.work_sub_status_id = 8 THEN 1 else 0 end) as Roof_Level, "
					    		+ "SUM(CASE WHEN du.work_sub_status_id = 7 THEN 1 else 0 end) as Lintel_Level, "
					    		+ "SUM(CASE WHEN du.work_sub_status_id = 6 THEN 1 else 0 end) as Plinth_Level, "
					    		+ "SUM(CASE WHEN du.work_sub_status_id = 5 THEN 1 else 0 end) as Foundation_level, "
					    		+ "SUM(CASE WHEN du.work_sub_status_id = 2 THEN 1 else 0 end) as Work_is_not_started_by_the_contractor "
					    		+ "from document_upload_workprogress_details du group by work_id) as dud on a.id = dud.work_id WHERE a.status != 'Deleted' and (a.financial_year != 'null' or a.financial_year != '') and (a.scheme != 'null' or a.scheme != '') and (a.work_head != 'null' or a.work_head != '')"
					    		+ "group by 1, a.scheme_state, a.scheme_nhm, a.scheme_ecrp2, a.scheme_others", nativeQuery=true)
						List<Object[]> fetchDivisionWiseWork();
				
			
		
	
	
	
	
	
	
	
						 @Query(value="select a.work_name, b.work_subtype_name_e as sub_type_of_work, c.Status as design_drawing_status, a.drawing_created_by as created_by "
						 		+ "from t_work as a join mst_work_subtype as b on a.work_subtype_id = b.work_subtype_id "
						 		+ "join document_upload_work_details as c on a.id = c.work_id where a.status != 'Deleted' group by a.id", nativeQuery=true)
							List<Object[]> fetchDrawingWork();
							
							
							 @Query(value="select a.work_name, b.work_subtype_name_e as sub_type_of_work, a.work_status as AS_status, 'pending' as tender_status "
							 		+ "from t_work as a join mst_work_subtype as b on a.work_subtype_id = b.work_subtype_id "
							 		+ "where a.work_status = 'AS Issued' and a.status != 'Deleted'", nativeQuery=true)
									List<Object[]> fetchAsApprovalWork();
									
									
									@Query(value="select w.work_name, wss.work_sub_status_name_e, duw.physical_perc, sum(ec.expensess_upto_march) "
											+ "from t_work as w inner join document_upload_workprogress_details as duw on w.id = duw.work_id "
											+ "inner join mst_work_sub_status as wss on duw.work_sub_status_id = wss.work_sub_status_id "
											+ "inner join expenses_cost as ec on w.id = ec.work_id and duw.work_id = ec.work_id "
											+ "inner join ( "
											+ "select a.work_sub_status_id, a.work_id from document_upload_workprogress_details as a inner join "
											+ "(select work_id, max(created_date) as max_date from document_upload_workprogress_details group by work_id)as b "
											+ "on a.work_id = b.work_id and a.created_date = b.max_date) as a on a.work_id = w.id and a.work_sub_status_id = duw.work_sub_status_id where w.status != 'Deleted'"
											+ "group by ec.work_id", nativeQuery=true)
									List<Object[]> fetchPhysicalWiseWork();
                            
							
							@Query(value="select work_name,status from t_work", nativeQuery = true)
							List<Object[]> findAllWork();
                            
							
							
							
							@Query(value="SELECT a.implementation_agency,\r\n"
									+ "sum(case when a.work_status = 'In-Progress' then 1 else 0 end) AS Remaining_work_count,  \r\n"
									+ "sum(case when a.work_status = 'In-Progress' then round((b.as_amt/100000),2) else 0 end) AS AS_amount_remain,\r\n"
									+ "sum(case when c.work_order_date between fin_year_start() and fin_year_end() then 1 else 0 end) AS Issued_work_count,  \r\n"
									+ "sum(case when c.work_order_date between fin_year_start() and fin_year_end() then round((b.as_amt/100000),2) else 0 end) AS AS_amount_new,\r\n"
									+ "(sum(case when a.work_status = 'In-Progress' then 1 else 0 end) + sum(case when c.work_order_date between fin_year_start() and fin_year_end() then 1 else 0 end)) as total_work_count,\r\n"
									+ "(sum(case when a.work_status = 'In-Progress' then round((b.as_amt/100000),2) else 0 end) + sum(case when c.work_order_date between fin_year_start() and fin_year_end() then round((b.as_amt/100000),2) else 0 end)) as total_amount\r\n"
									+ "FROM t_work a\r\n"
									+ "left join t_work_ts b on a.id = b.work_id \r\n"
									+ "left join t_work_tender c on a.id = c.work_id\r\n WHERE a.status != 'Deleted'"
									+ "group by a.implementation_agency order by 1 desc;",  nativeQuery = true)
							List<Object[]> fetchDataReportOne();
							
							
							
							@Query(value="SELECT a.implementation_agency as Agency,\r\n"
									+ "(sum(case when a.work_status = 'In-Progress' then 1 else 0 end) + sum(case when c.work_order_date between fin_year_start() and fin_year_end() then 1 else 0 end)) as total_work_count,\r\n"
									+ "(sum(case when a.work_status = 'In-Progress' then round((d.as_amt/100000),2) else 0 end) + sum(case when c.work_order_date between fin_year_start() and fin_year_end() then round((d.as_amt/100000),2) else 0 end)) as total_amount,\r\n"
									+ "sum(case when a.work_status = 'In-Progress' and (STR_TO_DATE(c.work_completion_date,\"%d/%m/%Y\")>=curdate() or STR_TO_DATE(f.likely_date_completion,\"%d/%m/%Y\")>=curdate() )\r\n"
									+ "then 1 else 0 end) as work_count_stipulated,\r\n"
									+ "sum(case when a.work_status = 'In-Progress' and (STR_TO_DATE(c.work_completion_date,\"%d/%m/%Y\")>=curdate() or STR_TO_DATE(f.likely_date_completion,\"%d/%m/%Y\")>=curdate() )\r\n"
									+ " then round((b.expensess_current_fy/100000),2) else 0 end) as Fin_target_Amount_stipulated,\r\n"
									+ "sum(case when a.work_status = 'In-Progress' and e.work_order_date <= now() then 1 else 0 end) as work_count_quarter,\r\n"
									+ "sum(case when a.work_status = 'In-Progress' and e.work_order_date <= now() then round((b.expensess_current_fy/100000),2) else 0 end) as Fin_target_Amount_quarter,\r\n"
									+ "sum(case when a.work_status = 'Completed' and e.work_order_date <= now() then 1 else 0 end) as work_count_complition_quarter,\r\n"
									+ "sum(case when a.work_status = 'Completed' and e.work_order_date <= now() then round((b.expensess_current_fy/100000),2) else 0 end) as Fin_target_Amount_complition_quarter\r\n"
									+ "FROM t_work a left join \r\n"
									+ "(select work_id, sum(expensess_current_fy) as expensess_current_fy from expenses_cost group by work_id) as b\r\n"
									+ " on a.id = b.work_id left join t_work_tender c on a.id = c.work_id\r\n"
									+ "left join t_work_ts d on a.id = d.work_id\r\n"
									+ "left join t_work_tender as e on a.id = e.work_id left join t_work_progress f on a.id = f.work_id\r\n WHERE a.status != 'Deleted'"
									+ "group by a.implementation_agency order by 1 desc;\r\n"
									+ "",  nativeQuery = true)
							List<Object[]> fetchDataReportTwo();
                            
							
							
							@Query(value="SELECT a.implementation_agency as Agency, count(a.id) as Total_Work,\r\n"
									+ "sum(round((d.as_amt/100000),2)) as Total_Amount\r\n"
									+ "FROM t_work a left join t_work_ts d on a.id = d.work_id group by a.implementation_agency order by 1 desc;",  nativeQuery = true)
							List<Object[]> fetchDataReportThree();
                            
							
							
							@Query(value="SELECT a.implementation_agency as Agency, count(a.id) AS Total_work_count,\r\n"
									+ "sum(case when a.work_status = 'Handed Over' then 1 else 0 end) AS Handed_Over,  \r\n"
									+ "sum(case when a.work_status = 'Completed' and b.date_completion between fin_year_start() and fin_year_end() then 1 else 0 end) AS Completed_in_Yr_2023_24,\r\n"
									+ "sum(case when a.work_status = 'In-Progress' then 1 else 0 end) AS Work_Started,\r\n"
									+ "sum(case when a.work_status = 'LoA Issued' then 1 else 0 end) AS Tender_Awarded,\r\n"
									+ "sum(case when a.work_status = 'Tender Called' then 1 else 0 end) AS Tender_Issued,\r\n"
									+ "sum(case when e.work_sub_status_id = 1 then 1 else 0 end) AS Site_Not_Selected_out_of_total_work_alloted\r\n"
									+ "FROM t_work a \r\n"
									+ "left join t_work_progress as b on a.id = b.work_id \r\n"
									+ "left join \r\n"
									+ "(select b.created_date, a.work_sub_status_id, a.work_id from document_upload_workprogress_details a inner join \r\n"
									+ "(select max(created_date) as created_date, work_id from document_upload_workprogress_details \r\n"
									+ "group by work_id) as b on a.work_id = b.work_id and a.created_date = b.created_date) as e on a.id = e.work_id\r\n WHERE a.status != 'Deleted'"
									+ "group by a.implementation_agency order by 1 desc;",  nativeQuery = true)
							List<Object[]> fetchDataReportFour();
                            

							
							@Query(value="SELECT a.implementation_agency as Agency, count(a.id) as Total_Work,\r\n"
									+ "sum(round((c.as_amt/100000),2)) as Total_Amount,\r\n"
									+ "sum(case when a.created_date between fin_year_start() and fin_year_end() then IFNULL(round((b.expensess_current_fy/100000),2),0) else 0 end) as Fin_target_Amount,\r\n"
									+ "sum(IFNULL(round((d.total_expensess/100000),2),0)) as Total_Target_Amount\r\n"
									+ "FROM t_work a left join\r\n"
									+ "(select work_id, sum(expensess_current_fy) as expensess_current_fy, created_date from expenses_cost group by work_id) as b\r\n"
									+ "on a.id = b.work_id  left join t_work_ts as c on a.id = c.work_id \r\n"
									+ "left join (select work_id, max(total_expensess) as total_expensess, max(year) from expenses_cost group by work_id) as d on a.id = d.work_id\r\n WHERE a.status != 'Deleted'"
									+ "group by a.implementation_agency order by 1 desc;",  nativeQuery = true)
							List<Object[]> fetchDataReportFive();
                            
							
							
							@Query(value="select a.scheme as Scheme, count(a.id) as Total_Work, sum(IFNULL((b.as_amt), 0)) as Total_Amount, \r\n"
									+ "sum(IFNULL((c.expensess_current_fy), 0)) as Total_Expenditure,\r\n"
									+ "sum(case when c.created_date between fin_year_start() and fin_year_end() then c.expensess_current_fy else 0 end) as Total_Expenditure_amount_2023_24,\r\n"
									+ "sum(case when a.work_status = 'Handed Over' and d.date_hand_over between fin_year_start() and fin_year_end() then 1 else 0 end) Handed_Over_2023_24,\r\n"
									+ "sum(case when a.work_status = 'Completed' and e.created_date between fin_year_start() and fin_year_end() then 1 else 0 end) Completed_2023_24,\r\n"
									+ "sum(case when a.work_status = 'In-Progress' then 1 else 0 end) In_Progress_2023_24,\r\n"
									+ "sum(case when a.work_status = 'LoA Issued' then 1 else 0 end) Tender_Awarded,\r\n"
									+ "sum(case when a.work_status = 'Tender Called' then 1 else 0 end) Tender_Issued,\r\n"
									+ "sum(case when f.work_sub_status_id = 1 then 1 else 0 end) AS Site_Not_Selected_out_of_total_work_alloted\r\n"
									+ "from t_work as a left join t_work_ts as b on a.id = b.work_id\r\n"
									+ "left join expenses_cost as c on a.id = c.work_id \r\n"
									+ "left join t_work_cc as d on a.id = d.work_id\r\n"
									+ "left join t_work_progress e on a.id = e.work_id\r\n"
									+ "left join \r\n"
									+ "(select b.created_date, a.work_sub_status_id, a.work_id from document_upload_workprogress_details a inner join \r\n"
									+ "(select max(created_date) as created_date, work_id from document_upload_workprogress_details \r\n"
									+ "group by work_id) as b on a.work_id = b.work_id and a.created_date = b.created_date) as f on a.id = f.work_id\r\n"
									+ "where a.multifunded_status=0 and a.implementation_agency = 'MPBDC'\r\n WHERE a.status != 'Deleted'"
									+ "group by 1;",  nativeQuery = true)
							List<Object[]> fetchDataReportSix();
                            
							
							
							@Query(value="select a.scheme as Scheme, a.financial_year as Year, count(a.id) as Total_Work, sum(IFNULL((b.as_amt), 0)) as Total_Amount, \r\n"
									+ "sum(IFNULL((c.expensess_current_fy), 0)) as Total_Expenditure,\r\n"
									+ "sum(case when c.created_date between fin_year_start() and fin_year_end() then c.expensess_current_fy else 0 end) as Total_Expenditure_amount_2023_24,\r\n"
									+ "sum(case when a.work_status = 'Handed Over' and d.date_hand_over between fin_year_start() and fin_year_end() then 1 else 0 end) Handed_Over_2023_24,\r\n"
									+ "sum(case when a.work_status = 'Completed' and e.created_date between fin_year_start() and fin_year_end() then 1 else 0 end) Completed_2023_24,\r\n"
									+ "sum(case when a.work_status = 'In-Progress' then 1 else 0 end) In_Progress_2023_24,\r\n"
									+ "sum(case when a.work_status = 'LoA Issued' then 1 else 0 end) Tender_Awarded,\r\n"
									+ "sum(case when a.work_status = 'Tender Called' then 1 else 0 end) Tender_Issued,\r\n"
									+ "sum(case when f.work_sub_status_id = 1 then 1 else 0 end) AS Site_Not_Selected_out_of_total_work_alloted\r\n"
									+ "from t_work as a left join t_work_ts as b on a.id = b.work_id\r\n"
									+ "left join expenses_cost as c on a.id = c.work_id \r\n"
									+ "left join t_work_cc as d on a.id = d.work_id\r\n"
									+ "left join t_work_progress e on a.id = e.work_id\r\n"
									+ "left join \r\n"
									+ "(select b.created_date, a.work_sub_status_id, a.work_id from document_upload_workprogress_details a inner join \r\n"
									+ "(select max(created_date) as created_date, work_id from document_upload_workprogress_details \r\n"
									+ "group by work_id) as b on a.work_id = b.work_id and a.created_date = b.created_date) as f on a.id = f.work_id\r\n"
									+ "where a.multifunded_status=0 and a.implementation_agency = 'MPBDC'\r\n WHERE a.status != 'Deleted'"
									+ "group by 1, 2;",  nativeQuery = true)
							List<Object[]> fetchDataReportSeven();
                            
							
							@Query(value="select a.scheme as Scheme, count(a.id) as Total_Work,  sum(IFNULL((b.as_amt), 0)) as Total_Amount, \r\n"
									+ "sum(IFNULL((c.expensess_current_fy), 0)) as Total_Expenditure,\r\n"
									+ "sum(case when c.created_date between fin_year_start() and fin_year_end() then c.expensess_current_fy else 0 end) as Total_Expenditure_amount_2023_24,\r\n"
									+ "sum(case when a.work_status = 'Handed Over' and d.date_hand_over between fin_year_start() and fin_year_end() then 1 else 0 end) Handed_Over_2023_24,\r\n"
									+ "sum(case when a.work_status = 'Completed' and e.created_date between fin_year_start() and fin_year_end() then 1 else 0 end) Completed_2023_24,\r\n"
									+ "sum(case when a.work_status = 'In-Progress' then 1 else 0 end) In_Progress_2023_24,\r\n"
									+ "sum(case when a.work_status = 'LoA Issued' then 1 else 0 end) Tender_Awarded,\r\n"
									+ "sum(case when a.work_status = 'Tender Called' then 1 else 0 end) Tender_Issued,\r\n"
									+ "sum(case when f.work_sub_status_id = 1 then 1 else 0 end) AS Site_Not_Selected_out_of_total_work_alloted\r\n"
									+ "from t_work as a left join t_work_ts as b on a.id = b.work_id\r\n"
									+ "left join expenses_cost as c on a.id = c.work_id \r\n"
									+ "left join t_work_cc as d on a.id = d.work_id\r\n"
									+ "left join t_work_progress e on a.id = e.work_id\r\n"
									+ "left join \r\n"
									+ "(select b.created_date, a.work_sub_status_id, a.work_id from document_upload_workprogress_details a inner join \r\n"
									+ "(select max(created_date) as created_date, work_id from document_upload_workprogress_details \r\n"
									+ "group by work_id) as b on a.work_id = b.work_id and a.created_date = b.created_date) as f on a.id = f.work_id\r\n"
									+ "where  a.multifunded_status=0 and  a.implementation_agency = 'PIU'\r\n WHERE a.status != 'Deleted'"
									+ "group by 1;",  nativeQuery = true)
							List<Object[]> fetchDataReportEight();
                            
							
							@Query(value="select a.scheme as Scheme, a.financial_year as Year, count(a.id) as Total_Work, sum(IFNULL((b.as_amt), 0)) as Total_Amount, \r\n"
									+ "sum(IFNULL((c.expensess_current_fy), 0)) as Total_Expenditure,\r\n"
									+ "sum(case when c.created_date between fin_year_start() and fin_year_end() then c.expensess_current_fy else 0 end) as Total_Expenditure_amount_2023_24,\r\n"
									+ "sum(case when a.work_status = 'Handed Over' and d.date_hand_over between fin_year_start() and fin_year_end() then 1 else 0 end) Handed_Over_2023_24,\r\n"
									+ "sum(case when a.work_status = 'Completed' and e.created_date between fin_year_start() and fin_year_end() then 1 else 0 end) Completed_2023_24,\r\n"
									+ "sum(case when a.work_status = 'In-Progress' then 1 else 0 end) In_Progress_2023_24,\r\n"
									+ "sum(case when a.work_status = 'LoA Issued' then 1 else 0 end) Tender_Awarded,\r\n"
									+ "sum(case when a.work_status = 'Tender Called' then 1 else 0 end) Tender_Issued,\r\n"
									+ "sum(case when f.work_sub_status_id = 1 then 1 else 0 end) AS Site_Not_Selected_out_of_total_work_alloted\r\n"
									+ "from t_work as a left join t_work_ts as b on a.id = b.work_id\r\n"
									+ "left join expenses_cost as c on a.id = c.work_id \r\n"
									+ "left join t_work_cc as d on a.id = d.work_id\r\n"
									+ "left join t_work_progress e on a.id = e.work_id\r\n"
									+ "left join \r\n"
									+ "(select b.created_date, a.work_sub_status_id, a.work_id from document_upload_workprogress_details a inner join \r\n"
									+ "(select max(created_date) as created_date, work_id from document_upload_workprogress_details \r\n"
									+ "group by work_id) as b on a.work_id = b.work_id and a.created_date = b.created_date) as f on a.id = f.work_id\r\n"
									+ "where  a.multifunded_status=0 and  a.implementation_agency = 'PIU'\r\n WHERE a.status != 'Deleted'"
									+ "group by 1, 2;",  nativeQuery = true)
							List<Object[]> fetchDataReportNine();
                            
							
							
							
							@Query(value="select a.scheme as Scheme, count(a.id) as Total_Work,  sum(IFNULL((b.as_amt), 0)) as Total_Amount, \r\n"
									+ "sum(IFNULL((c.expensess_current_fy), 0)) as Total_Expenditure,\r\n"
									+ "sum(case when c.created_date between fin_year_start() and fin_year_end() then c.expensess_current_fy else 0 end) as Total_Expenditure_amount_2023_24,\r\n"
									+ "sum(case when a.work_status = 'Handed Over' and d.date_hand_over between fin_year_start() and fin_year_end() then 1 else 0 end) Handed_Over_2023_24,\r\n"
									+ "sum(case when a.work_status = 'Completed' and e.created_date between fin_year_start() and fin_year_end() then 1 else 0 end) Completed_2023_24,\r\n"
									+ "sum(case when a.work_status = 'In-Progress' then 1 else 0 end) In_Progress_2023_24,\r\n"
									+ "sum(case when a.work_status = 'LoA Issued' then 1 else 0 end) Tender_Awarded,\r\n"
									+ "sum(case when a.work_status = 'Tender Called' then 1 else 0 end) Tender_Issued,\r\n"
									+ "sum(case when f.work_sub_status_id = 1 then 1 else 0 end) AS Site_Not_Selected_out_of_total_work_alloted\r\n"
									+ "from t_work as a left join t_work_ts as b on a.id = b.work_id\r\n"
									+ "left join expenses_cost as c on a.id = c.work_id \r\n"
									+ "left join t_work_cc as d on a.id = d.work_id\r\n"
									+ "left join t_work_progress e on a.id = e.work_id\r\n"
									+ "left join \r\n"
									+ "(select b.created_date, a.work_sub_status_id, a.work_id from document_upload_workprogress_details a inner join \r\n"
									+ "(select max(created_date) as created_date, work_id from document_upload_workprogress_details \r\n"
									+ "group by work_id) as b on a.work_id = b.work_id and a.created_date = b.created_date) as f on a.id = f.work_id\r\n"
									+ "where  a.multifunded_status=0 and  a.implementation_agency = 'PWD'\r\n WHERE a.status != 'Deleted'"
									+ "group by 1;",  nativeQuery = true)
							List<Object[]> fetchDataReportTen();
                            
							
							
							@Query(value="select a.scheme as Scheme, a.financial_year as Year, count(a.id) as Total_Work, sum(IFNULL((b.as_amt), 0)) as Total_Amount, \r\n"
									+ "sum(IFNULL((c.expensess_current_fy), 0)) as Total_Expenditure,\r\n"
									+ "sum(case when c.created_date between fin_year_start() and fin_year_end() then c.expensess_current_fy else 0 end) as Total_Expenditure_amount_2023_24,\r\n"
									+ "sum(case when a.work_status = 'Handed Over' and d.date_hand_over between fin_year_start() and fin_year_end() then 1 else 0 end) Handed_Over_2023_24,\r\n"
									+ "sum(case when a.work_status = 'Completed' and e.created_date between fin_year_start() and fin_year_end() then 1 else 0 end) Completed_2023_24,\r\n"
									+ "sum(case when a.work_status = 'In-Progress' then 1 else 0 end) In_Progress_2023_24,\r\n"
									+ "sum(case when a.work_status = 'LoA Issued' then 1 else 0 end) Tender_Awarded,\r\n"
									+ "sum(case when a.work_status = 'Tender Called' then 1 else 0 end) Tender_Issued,\r\n"
									+ "sum(case when f.work_sub_status_id = 1 then 1 else 0 end) AS Site_Not_Selected_out_of_total_work_alloted\r\n"
									+ "from t_work as a left join t_work_ts as b on a.id = b.work_id\r\n"
									+ "left join expenses_cost as c on a.id = c.work_id \r\n"
									+ "left join t_work_cc as d on a.id = d.work_id\r\n"
									+ "left join t_work_progress e on a.id = e.work_id\r\n"
									+ "left join \r\n"
									+ "(select b.created_date, a.work_sub_status_id, a.work_id from document_upload_workprogress_details a inner join \r\n"
									+ "(select max(created_date) as created_date, work_id from document_upload_workprogress_details \r\n"
									+ "group by work_id) as b on a.work_id = b.work_id and a.created_date = b.created_date) as f on a.id = f.work_id\r\n"
									+ "where  a.multifunded_status=0 and  a.implementation_agency = 'PWD'\r\n WHERE a.status != 'Deleted'"
									+ "group by 1, 2;",  nativeQuery = true)
							List<Object[]> fetchDataReportEleven();
                            
							
							
							@Query(value="select a.scheme as Scheme, count(a.id) as Total_Work, sum(IFNULL((b.as_amt), 0)) as Total_Amount, \r\n"
									+ "sum(IFNULL((c.expensess_current_fy), 0)) as Total_Expenditure,\r\n"
									+ "sum(case when c.created_date between fin_year_start() and fin_year_end() then c.expensess_current_fy else 0 end) as Total_Expenditure_amount_2023_24,\r\n"
									+ "sum(case when a.work_status = 'Handed Over' and d.date_hand_over between fin_year_start() and fin_year_end() then 1 else 0 end) Handed_Over_2023_24,\r\n"
									+ "sum(case when a.work_status = 'Completed' and e.created_date between fin_year_start() and fin_year_end() then 1 else 0 end) Completed_2023_24,\r\n"
									+ "sum(case when a.work_status = 'In-Progress' then 1 else 0 end) In_Progress_2023_24,\r\n"
									+ "sum(case when a.work_status = 'LoA Issued' then 1 else 0 end) Tender_Awarded,\r\n"
									+ "sum(case when a.work_status = 'Tender Called' then 1 else 0 end) Tender_Issued,\r\n"
									+ "sum(case when f.work_sub_status_id = 1 then 1 else 0 end) AS Site_Not_Selected_out_of_total_work_alloted\r\n"
									+ "from t_work as a left join t_work_ts as b on a.id = b.work_id\r\n"
									+ "left join expenses_cost as c on a.id = c.work_id \r\n"
									+ "left join t_work_cc as d on a.id = d.work_id\r\n"
									+ "left join t_work_progress e on a.id = e.work_id\r\n"
									+ "left join \r\n"
									+ "(select b.created_date, a.work_sub_status_id, a.work_id from document_upload_workprogress_details a inner join \r\n"
									+ "(select max(created_date) as created_date, work_id from document_upload_workprogress_details \r\n"
									+ "group by work_id) as b on a.work_id = b.work_id and a.created_date = b.created_date) as f on a.id = f.work_id\r\n"
									+ "where  a.multifunded_status=0 and  a.implementation_agency = 'MP Housing Board'\r\n WHERE a.status != 'Deleted'"
									+ "group by 1;",  nativeQuery = true)
							List<Object[]> fetchDataReportTwelve();
                           
							
							
							@Query(value="select a.scheme as Scheme, a.financial_year as Year, count(a.id) as Total_Work, sum(IFNULL((b.as_amt), 0)) as Total_Amount, \r\n"
									+ "sum(IFNULL((c.expensess_current_fy), 0)) as Total_Expenditure,\r\n"
									+ "sum(case when c.created_date between fin_year_start() and fin_year_end() then c.expensess_current_fy else 0 end) as Total_Expenditure_amount_2023_24,\r\n"
									+ "sum(case when a.work_status = 'Handed Over' and d.date_hand_over between fin_year_start() and fin_year_end() then 1 else 0 end) Handed_Over_2023_24,\r\n"
									+ "sum(case when a.work_status = 'Completed' and e.created_date between fin_year_start() and fin_year_end() then 1 else 0 end) Completed_2023_24,\r\n"
									+ "sum(case when a.work_status = 'In-Progress' then 1 else 0 end) In_Progress_2023_24,\r\n"
									+ "sum(case when a.work_status = 'LoA Issued' then 1 else 0 end) Tender_Awarded,\r\n"
									+ "sum(case when a.work_status = 'Tender Called' then 1 else 0 end) Tender_Issued,\r\n"
									+ "sum(case when f.work_sub_status_id = 1 then 1 else 0 end) AS Site_Not_Selected_out_of_total_work_alloted\r\n"
									+ "from t_work as a left join t_work_ts as b on a.id = b.work_id\r\n"
									+ "left join expenses_cost as c on a.id = c.work_id \r\n"
									+ "left join t_work_cc as d on a.id = d.work_id\r\n"
									+ "left join t_work_progress e on a.id = e.work_id\r\n"
									+ "left join \r\n"
									+ "(select b.created_date, a.work_sub_status_id, a.work_id from document_upload_workprogress_details a inner join \r\n"
									+ "(select max(created_date) as created_date, work_id from document_upload_workprogress_details \r\n"
									+ "group by work_id) as b on a.work_id = b.work_id and a.created_date = b.created_date) as f on a.id = f.work_id\r\n"
									+ "where  a.multifunded_status=0 and  a.implementation_agency = 'MP Housing Board'\r\n WHERE a.status != 'Deleted'"
									+ "group by 1, 2;",  nativeQuery = true)
							List<Object[]> fetchDataReportThirteen();
                            
							
							
							@Query(value="select a.scheme as Scheme, count(a.id) as Total_Work, sum(IFNULL((b.as_amt), 0)) as Total_Amount, \r\n"
									+ "sum(IFNULL((c.expensess_current_fy), 0)) as Total_Expenditure,\r\n"
									+ "sum(case when c.created_date between fin_year_start() and fin_year_end() then c.expensess_current_fy else 0 end) as Total_Expenditure_amount_2023_24,\r\n"
									+ "sum(case when a.work_status = 'Handed Over' and d.date_hand_over between fin_year_start() and fin_year_end() then 1 else 0 end) Handed_Over_2023_24,\r\n"
									+ "sum(case when a.work_status = 'Completed' and e.created_date between fin_year_start() and fin_year_end() then 1 else 0 end) Completed_2023_24,\r\n"
									+ "sum(case when a.work_status = 'In-Progress' then 1 else 0 end) In_Progress_2023_24,\r\n"
									+ "sum(case when a.work_status = 'LoA Issued' then 1 else 0 end) Tender_Awarded,\r\n"
									+ "sum(case when a.work_status = 'Tender Called' then 1 else 0 end) Tender_Issued,\r\n"
									+ "sum(case when f.work_sub_status_id = 1 then 1 else 0 end) AS Site_Not_Selected_out_of_total_work_alloted\r\n"
									+ "from t_work as a left join t_work_ts as b on a.id = b.work_id\r\n"
									+ "left join expenses_cost as c on a.id = c.work_id \r\n"
									+ "left join t_work_cc as d on a.id = d.work_id\r\n"
									+ "left join t_work_progress e on a.id = e.work_id\r\n"
									+ "left join \r\n"
									+ "(select b.created_date, a.work_sub_status_id, a.work_id from document_upload_workprogress_details a inner join \r\n"
									+ "(select max(created_date) as created_date, work_id from document_upload_workprogress_details \r\n"
									+ "group by work_id) as b on a.work_id = b.work_id and a.created_date = b.created_date) as f on a.id = f.work_id\r\n"
									+ "where  a.multifunded_status=0 and  a.implementation_agency = 'Police Housing'\r\n WHERE a.status != 'Deleted'"
									+ "group by 1;",  nativeQuery = true)
							List<Object[]> fetchDataReportForteen();
                            
							
							@Query(value="select a.scheme as Scheme, a.financial_year as Year, count(a.id) as Total_Work, sum(IFNULL((b.as_amt), 0)) as Total_Amount, \r\n"
									+ "sum(IFNULL((c.expensess_current_fy), 0)) as Total_Expenditure,\r\n"
									+ "sum(case when c.created_date between fin_year_start() and fin_year_end() then c.expensess_current_fy else 0 end) as Total_Expenditure_amount_2023_24,\r\n"
									+ "sum(case when a.work_status = 'Handed Over' and d.date_hand_over between fin_year_start() and fin_year_end() then 1 else 0 end) Handed_Over_2023_24,\r\n"
									+ "sum(case when a.work_status = 'Completed' and e.created_date between fin_year_start() and fin_year_end() then 1 else 0 end) Completed_2023_24,\r\n"
									+ "sum(case when a.work_status = 'In-Progress' then 1 else 0 end) In_Progress_2023_24,\r\n"
									+ "sum(case when a.work_status = 'LoA Issued' then 1 else 0 end) Tender_Awarded,\r\n"
									+ "sum(case when a.work_status = 'Tender Called' then 1 else 0 end) Tender_Issued,\r\n"
									+ "sum(case when f.work_sub_status_id = 1 then 1 else 0 end) AS Site_Not_Selected_out_of_total_work_alloted\r\n"
									+ "from t_work as a left join t_work_ts as b on a.id = b.work_id\r\n"
									+ "left join expenses_cost as c on a.id = c.work_id \r\n"
									+ "left join t_work_cc as d on a.id = d.work_id\r\n"
									+ "left join t_work_progress e on a.id = e.work_id\r\n"
									+ "left join \r\n"
									+ "(select b.created_date, a.work_sub_status_id, a.work_id from document_upload_workprogress_details a inner join \r\n"
									+ "(select max(created_date) as created_date, work_id from document_upload_workprogress_details \r\n"
									+ "group by work_id) as b on a.work_id = b.work_id and a.created_date = b.created_date) as f on a.id = f.work_id\r\n"
									+ "where  a.multifunded_status=0 and a.implementation_agency = 'Police Housing'\r\n WHERE a.status != 'Deleted'"
									+ "group by 1, 2;",  nativeQuery = true)
							List<Object[]> fetchDataReportFifteen();
                           
							
							
							@Query(value="select a.scheme as Scheme, count(a.id) as Total_Work, sum(IFNULL((b.as_amt), 0)) as Total_Amount, \r\n"
									+ "sum(IFNULL((c.expensess_current_fy), 0)) as Total_Expenditure,\r\n"
									+ "sum(case when c.created_date between fin_year_start() and fin_year_end() then c.expensess_current_fy else 0 end) as Total_Expenditure_amount_2023_24,\r\n"
									+ "sum(case when a.work_status = 'Handed Over' and d.date_hand_over between fin_year_start() and fin_year_end() then 1 else 0 end) Handed_Over_2023_24,\r\n"
									+ "sum(case when a.work_status = 'Completed' and e.created_date between fin_year_start() and fin_year_end() then 1 else 0 end) Completed_2023_24,\r\n"
									+ "sum(case when a.work_status = 'In-Progress' then 1 else 0 end) In_Progress_2023_24,\r\n"
									+ "sum(case when a.work_status = 'LoA Issued' then 1 else 0 end) Tender_Awarded,\r\n"
									+ "sum(case when a.work_status = 'Tender Called' then 1 else 0 end) Tender_Issued,\r\n"
									+ "sum(case when f.work_sub_status_id = 1 then 1 else 0 end) AS Site_Not_Selected_out_of_total_work_alloted\r\n"
									+ "from t_work as a left join t_work_ts as b on a.id = b.work_id\r\n"
									+ "left join expenses_cost as c on a.id = c.work_id \r\n"
									+ "left join t_work_cc as d on a.id = d.work_id\r\n"
									+ "left join t_work_progress e on a.id = e.work_id\r\n"
									+ "left join \r\n"
									+ "(select b.created_date, a.work_sub_status_id, a.work_id from document_upload_workprogress_details a inner join \r\n"
									+ "(select max(created_date) as created_date, work_id from document_upload_workprogress_details \r\n"
									+ "group by work_id) as b on a.work_id = b.work_id and a.created_date = b.created_date) as f on a.id = f.work_id\r\n"
									+ "where  a.multifunded_status=0 and a.implementation_agency = 'DHS'\r\n WHERE a.status != 'Deleted'"
									+ "group by 1;",  nativeQuery = true)
							List<Object[]> fetchDataReportSixTeen();
                            
							
							
							@Query(value="select a.scheme as Scheme, a.financial_year as Year, count(a.id) as Total_Work, sum(IFNULL((b.as_amt), 0)) as Total_Amount, \r\n"
									+ "sum(IFNULL((c.expensess_current_fy), 0)) as Total_Expenditure,\r\n"
									+ "sum(case when c.created_date between fin_year_start() and fin_year_end() then c.expensess_current_fy else 0 end) as Total_Expenditure_amount_2023_24,\r\n"
									+ "sum(case when a.work_status = 'Handed Over' and d.date_hand_over between fin_year_start() and fin_year_end() then 1 else 0 end) Handed_Over_2023_24,\r\n"
									+ "sum(case when a.work_status = 'Completed' and e.created_date between fin_year_start() and fin_year_end() then 1 else 0 end) Completed_2023_24,\r\n"
									+ "sum(case when a.work_status = 'In-Progress' then 1 else 0 end) In_Progress_2023_24,\r\n"
									+ "sum(case when a.work_status = 'LoA Issued' then 1 else 0 end) Tender_Awarded,\r\n"
									+ "sum(case when a.work_status = 'Tender Called' then 1 else 0 end) Tender_Issued,\r\n"
									+ "sum(case when f.work_sub_status_id = 1 then 1 else 0 end) AS Site_Not_Selected_out_of_total_work_alloted\r\n"
									+ "from t_work as a left join t_work_ts as b on a.id = b.work_id\r\n"
									+ "left join expenses_cost as c on a.id = c.work_id \r\n"
									+ "left join t_work_cc as d on a.id = d.work_id\r\n"
									+ "left join t_work_progress e on a.id = e.work_id\r\n"
									+ "left join \r\n"
									+ "(select b.created_date, a.work_sub_status_id, a.work_id from document_upload_workprogress_details a inner join \r\n"
									+ "(select max(created_date) as created_date, work_id from document_upload_workprogress_details \r\n"
									+ "group by work_id) as b on a.work_id = b.work_id and a.created_date = b.created_date) as f on a.id = f.work_id\r\n"
									+ "where  a.multifunded_status=0 and  a.implementation_agency = 'DHS'\r\n WHERE a.status != 'Deleted'"
									+ "group by 1, 2;",  nativeQuery = true)
							List<Object[]> fetchDataReportSevenTeen();
                            
							
							
							@Query(value="select distinct(implementation_agency) from t_work;",  nativeQuery = true)
							List<Integer> fetchWorkAgencyData();
							
							
							
							@Query(value="select a.scheme as Scheme, count(a.id) as Total_Work, sum(IFNULL(round((b.as_amt/100000),2), 0)) as Total_Amount,\r\n"
									+ "sum(IFNULL(round((g.total_expensess/100000),2),0)) as Total_Target_Amount,\r\n"
									+ "sum(case when c.created_date between fin_year_start() and fin_year_end() then  round((c.expensess_current_fy/100000),2) else 0 end) as Total_Expenditure_amount_2023_24,\r\n"
									+ "sum(case when a.work_status = 'Handed Over' and d.date_hand_over between fin_year_start() and fin_year_end() then 1 else 0 end) Handed_Over_2023_24,\r\n"
									+ "sum(case when a.work_status = 'Completed' and e.created_date between fin_year_start() and fin_year_end() then 1 else 0 end) Completed_2023_24,\r\n"
									+ "sum(case when a.work_status = 'In-Progress' then 1 else 0 end) In_Progress_2023_24,\r\n"
									+ "sum(case when a.work_status = 'LoA Issued' then 1 else 0 end) Tender_Awarded,\r\n"
									+ "sum(case when a.work_status = 'Tender Called' then 1 else 0 end) Tender_Issued,\r\n"
									+ "sum(case when f.work_sub_status_id = 1 then 1 else 0 end) AS Site_Not_Selected_out_of_total_work_alloted\r\n"
									+ "from t_work as a left join t_work_ts as b on a.id = b.work_id\r\n"
									+ "left join expenses_cost as c on a.id = c.work_id \r\n"
									+ "left join t_work_cc as d on a.id = d.work_id\r\n"
									+ "left join t_work_progress e on a.id = e.work_id\r\n"
									+ "left join \r\n"
									+ "(select b.created_date, a.work_sub_status_id, a.work_id from document_upload_workprogress_details a inner join \r\n"
									+ "(select max(created_date) as created_date, work_id from document_upload_workprogress_details \r\n"
									+ "group by work_id) as b on a.work_id = b.work_id and a.created_date = b.created_date) as f on a.id = f.work_id\r\n"
									+ "left join (select work_id, max(total_expensess) as total_expensess, max(year) from expenses_cost group by work_id) as g on a.id = g.work_id\r\n"
									+ "where  a.multifunded_status=0 and a.implementation_agency  =  :agency \r\n"
									+ "group by 1;",  nativeQuery = true)
							List<Object[]> fetchDataReportNew(@Param("agency") Integer agency);
                            
							
							
							@Query(value="select a.scheme as Scheme,a.financial_year as Year, count(a.id) as Total_Work, sum(IFNULL(round((b.as_amt/100000),2), 0)) as Total_Amount,\r\n"
									+ "sum(IFNULL(round((g.total_expensess/100000),2),0)) as Total_Target_Amount,\r\n"
									+ "sum(case when c.created_date between fin_year_start() and fin_year_end() then  round((c.expensess_current_fy/100000),2) else 0 end) as Total_Expenditure_amount_2023_24,\r\n"
									+ "sum(case when a.work_status = 'Handed Over' and d.date_hand_over between fin_year_start() and fin_year_end() then 1 else 0 end) Handed_Over_2023_24,\r\n"
									+ "sum(case when a.work_status = 'Completed' and e.created_date between fin_year_start() and fin_year_end() then 1 else 0 end) Completed_2023_24,\r\n"
									+ "sum(case when a.work_status = 'In-Progress' then 1 else 0 end) In_Progress_2023_24,\r\n"
									+ "sum(case when a.work_status = 'LoA Issued' then 1 else 0 end) Tender_Awarded,\r\n"
									+ "sum(case when a.work_status = 'Tender Called' then 1 else 0 end) Tender_Issued,\r\n"
									+ "sum(case when f.work_sub_status_id = 1 then 1 else 0 end) AS Site_Not_Selected_out_of_total_work_alloted\r\n"
									+ "from t_work as a left join t_work_ts as b on a.id = b.work_id\r\n"
									+ "left join expenses_cost as c on a.id = c.work_id \r\n"
									+ "left join t_work_cc as d on a.id = d.work_id\r\n"
									+ "left join t_work_progress e on a.id = e.work_id\r\n"
									+ "left join \r\n"
									+ "(select b.created_date, a.work_sub_status_id, a.work_id from document_upload_workprogress_details a inner join \r\n"
									+ "(select max(created_date) as created_date, work_id from document_upload_workprogress_details \r\n"
									+ "group by work_id) as b on a.work_id = b.work_id and a.created_date = b.created_date) as f on a.id = f.work_id\r\n"
									+ "left join (select work_id, max(total_expensess) as total_expensess, max(year) from expenses_cost group by work_id) as g on a.id = g.work_id\r\n"
									+ "where  a.multifunded_status=0 and a.implementation_agency  =  :agency \r\n"
									+ "group by 1,2;",  nativeQuery = true)
							List<Object[]> fetchDataReportFinancialNew(@Param("agency") Integer agency);
							
							
							
							@Query(value="select cs.scheme_name as Scheme, count(a.id) as Total_Work,\r\n"
									+ "sum(IFNULL(round((cs.fund/100000),2),0)) as Total_Amount, \r\n"
									+ "sum(IFNULL(round((g.total_expensess/100000),2),0)) as Total_Target_Amount,\r\n"
									+ "sum(case when c.created_date between fin_year_start() and fin_year_end() then round((c.expensess_current_fy/100000),2) else 0 end) as Total_Expenditure_amount_2023_24,\r\n"
									+ "sum(case when a.work_status = 'Handed Over' and d.date_hand_over between fin_year_start() and fin_year_end() then 1 else 0 end) Handed_Over_2023_24,\r\n"
									+ "sum(case when a.work_status = 'Completed' and e.created_date between fin_year_start() and fin_year_end() then 1 else 0 end) Completed_2023_24,\r\n"
									+ "sum(case when a.work_status = 'In-Progress' then 1 else 0 end) In_Progress_2023_24,\r\n"
									+ "sum(case when a.work_status = 'LoA Issued' then 1 else 0 end) Tender_Awarded,\r\n"
									+ "sum(case when a.work_status = 'Tender Called' then 1 else 0 end) Tender_Issued,\r\n"
									+ "sum(case when f.work_sub_status_id = 1 then 1 else 0 end) AS Site_Not_Selected_out_of_total_work_alloted\r\n"
									+ "from (select s.id as workid,m.scheme_name,s.w_scheme,substring_index(ws_fund,m.scheme_name,1),\r\n"
									+ "replace(substring_index(substring_index(ws_fund,m.scheme_name,1),',',-1),'|','') as fund\r\n"
									+ "from (\r\n"
									+ "(select id,concat(ifnull(scheme_state,0),',',ifnull(scheme_nhm,0),',',ifnull(scheme_ecrp2,0),',',ifnull(scheme_others,0)) as w_scheme,\r\n"
									+ "concat(concat(ifnull(fund_by_state,0),'|',ifnull(scheme_state,0)),',',concat(ifnull(fund_by_nhm,0),'|',ifnull(scheme_nhm,0)),',',concat(ifnull(fund_by_ecrp2,0),'|',ifnull(scheme_ecrp2,0)),',',concat(ifnull(fund_by_others,0),'|',ifnull(scheme_others,0))) as ws_fund\r\n"
									+ "#concat(ifnull(fund_by_state,0),'|',ifnull(scheme_state,0)) as ws_scheme,concat(ifnull(fund_by_nhm,0),'|',ifnull(scheme_nhm,0)),concat(ifnull(fund_by_ecrp2,0),'|',ifnull(scheme_ecrp2,0)),concat(ifnull(fund_by_others,0),'|',ifnull(scheme_others,0))\r\n"
									+ "#sum(ifnull(fund_by_state,0)) as fund1, sum(ifnull(fund_by_nhm,0)) as fund2, sum(ifnull(fund_by_ecrp2,0)) as fund3, sum(ifnull(fund_by_others,0)) as fund4 \r\n"
									+ "from t_work where multifunded_status = 1 group by id) as s\r\n"
									+ "left join\r\n"
									+ " mst_schemes as m on find_in_set(m.scheme_name,s.w_scheme)\r\n"
									+ ") ) as cs inner join t_work as a on a.id=cs.workid \r\n"
									+ "left join t_work_ts as b on a.id = b.work_id \r\n"
									+ "left join \r\n"
									+ "(select work_id,sum(expensess_current_fy) as expensess_current_fy, created_date from expenses_cost group by work_id) as c on cs.workid = c.work_id\r\n"
									+ "left join t_work_cc as d on a.id = d.work_id\r\n"
									+ "left join t_work_progress e on a.id = e.work_id \r\n"
									+ "left join (select b.created_date, a.work_sub_status_id, a.work_id from document_upload_workprogress_details a left join \r\n"
									+ "(select max(created_date) as created_date, work_id from document_upload_workprogress_details \r\n"
									+ "group by work_id) as b on a.work_id = b.work_id and a.created_date = b.created_date) as f on a.id = f.work_id\r\n"
									+ "left join (select work_id, max(total_expensess) as total_expensess, max(year) from expenses_cost group by work_id) as g on a.id = g.work_id\r\n"
									+ "where a.multifunded_status=1 and a.implementation_agency = :agency  group by 1;",  nativeQuery = true)
							List<Object[]> fetchDataReportSchemeMultifunded(@Param("agency") Integer agency);
							
							
							
							
							@Query(value="select cs.scheme_name as Scheme, a.financial_year as Year, count(a.id) as Total_Work,\r\n"
									+ "sum(IFNULL(round((cs.fund/100000),2),0)) as Total_Amount, \r\n"
									+ "sum(IFNULL(round((g.total_expensess/100000),2),0)) as Total_Target_Amount,\r\n"
									+ "sum(case when c.created_date between fin_year_start() and fin_year_end() then round((c.expensess_current_fy/100000),2) else 0 end) as Total_Expenditure_amount_2023_24,\r\n"
									+ "sum(case when a.work_status = 'Handed Over' and d.date_hand_over between fin_year_start() and fin_year_end() then 1 else 0 end) Handed_Over_2023_24,\r\n"
									+ "sum(case when a.work_status = 'Completed' and e.created_date between fin_year_start() and fin_year_end() then 1 else 0 end) Completed_2023_24,\r\n"
									+ "sum(case when a.work_status = 'In-Progress' then 1 else 0 end) In_Progress_2023_24,\r\n"
									+ "sum(case when a.work_status = 'LoA Issued' then 1 else 0 end) Tender_Awarded,\r\n"
									+ "sum(case when a.work_status = 'Tender Called' then 1 else 0 end) Tender_Issued,\r\n"
									+ "sum(case when f.work_sub_status_id = 1 then 1 else 0 end) AS Site_Not_Selected_out_of_total_work_alloted\r\n"
									+ "from (select s.id as workid,m.scheme_name,s.w_scheme,substring_index(ws_fund,m.scheme_name,1),\r\n"
									+ "replace(substring_index(substring_index(ws_fund,m.scheme_name,1),',',-1),'|','') as fund\r\n"
									+ "from (\r\n"
									+ "(select id,concat(ifnull(scheme_state,0),',',ifnull(scheme_nhm,0),',',ifnull(scheme_ecrp2,0),',',ifnull(scheme_others,0)) as w_scheme,\r\n"
									+ "concat(concat(ifnull(fund_by_state,0),'|',ifnull(scheme_state,0)),',',concat(ifnull(fund_by_nhm,0),'|',ifnull(scheme_nhm,0)),',',concat(ifnull(fund_by_ecrp2,0),'|',ifnull(scheme_ecrp2,0)),',',concat(ifnull(fund_by_others,0),'|',ifnull(scheme_others,0))) as ws_fund\r\n"
									+ "#concat(ifnull(fund_by_state,0),'|',ifnull(scheme_state,0)) as ws_scheme,concat(ifnull(fund_by_nhm,0),'|',ifnull(scheme_nhm,0)),concat(ifnull(fund_by_ecrp2,0),'|',ifnull(scheme_ecrp2,0)),concat(ifnull(fund_by_others,0),'|',ifnull(scheme_others,0))\r\n"
									+ "#sum(ifnull(fund_by_state,0)) as fund1, sum(ifnull(fund_by_nhm,0)) as fund2, sum(ifnull(fund_by_ecrp2,0)) as fund3, sum(ifnull(fund_by_others,0)) as fund4 \r\n"
									+ "from t_work where multifunded_status = 1 group by id) as s\r\n"
									+ "left join\r\n"
									+ " mst_schemes as m on find_in_set(m.scheme_name,s.w_scheme)\r\n"
									+ ") ) as cs inner join t_work as a on a.id=cs.workid \r\n"
									+ "left join t_work_ts as b on a.id = b.work_id \r\n"
									+ "left join \r\n"
									+ "(select work_id,sum(expensess_current_fy) as expensess_current_fy, created_date from expenses_cost group by work_id) as c on cs.workid = c.work_id\r\n"
									+ "left join t_work_cc as d on a.id = d.work_id\r\n"
									+ "left join t_work_progress e on a.id = e.work_id \r\n"
									+ "left join (select b.created_date, a.work_sub_status_id, a.work_id from document_upload_workprogress_details a left join \r\n"
									+ "(select max(created_date) as created_date, work_id from document_upload_workprogress_details \r\n"
									+ "group by work_id) as b on a.work_id = b.work_id and a.created_date = b.created_date) as f on a.id = f.work_id\r\n"
									+ "left join (select work_id, max(total_expensess) as total_expensess, max(year) from expenses_cost group by work_id) as g on a.id = g.work_id\r\n"
									+ "where a.multifunded_status=1 and a.implementation_agency = :agency  group by 1, 2;",  nativeQuery = true)
							List<Object[]> fetchDataReportFinancialMultifunded(@Param("agency") Integer agency);

						

							@Query(value = "SELECT * FROM t_work WHERE id = :workId AND status != :statusDeleted LIMIT 1", nativeQuery = true)
							Work findByIdAndStatusNotIn(@Param("workId") Long workId, @Param("statusDeleted") String statusDeleted);

							@Query(value = "SELECT * FROM t_work WHERE work_name LIKE CONCAT('%', :workName, '%') AND implementation_agency = :implAgency AND status != :statusDeleted AND work_status != :workStatusHandover", nativeQuery = true)
							Page<Work> findByWorkNameContainingAndImplementationAgencyAndStatusNotInAndWorkStatusNotIn(
									Pageable pageable, @Param("workName") String workName, @Param("implAgency") ImplementationAgency implAgency,
									@Param("statusDeleted") String statusDeleted, @Param("workStatusHandover") String workStatusHandover);

							@Query(value = "SELECT * FROM t_work WHERE work_type LIKE CONCAT('%', :workType, '%') AND implementation_agency = :implAgency AND status != :statusDeleted AND work_status != :workStatusHandover", nativeQuery = true)
							Page<Work> findByWorkTypeContainingAndImplementationAgencyAndStatusNotInAndWorkStatusNotIn(
									Pageable pageable, @Param("workType") String workType, @Param("implAgency") ImplementationAgency implAgency,
									@Param("statusDeleted") String statusDeleted, @Param("workStatusHandover") String workStatusHandover);

							@Query(value = "SELECT * FROM t_work WHERE financial_year LIKE CONCAT('%', :financialYear, '%') AND implementation_agency = :implAgency AND status != :statusDeleted AND work_status != :workStatusHandover", nativeQuery = true)
							Page<Work> findByFinancialYearContainingAndImplementationAgencyAndStatusNotInAndWorkStatusNotIn(
									Pageable pageable, @Param("financialYear") String financialYear, @Param("implAgency") ImplementationAgency implAgency,
									@Param("statusDeleted") String statusDeleted, @Param("workStatusHandover") String workStatusHandover);

							@Query(value = "SELECT * FROM t_work WHERE implementation_agency = :implAgency AND status != :statusDeleted AND work_status != :workStatusHandover", nativeQuery = true)
							Page<Work> findByImplementationAgencyAndStatusNotInAndWorkStatusNotIn(Pageable pageable,
									@Param("implAgency") ImplementationAgency implAgency, @Param("statusDeleted") String statusDeleted, @Param("workStatusHandover") String workStatusHandover);


							@Query(value = "SELECT * FROM t_work a WHERE 1=1 AND (a.work_name = COALESCE(:workName, a.work_name)) AND (a.work_type = COALESCE(:workType, a.work_type)) AND (a.financial_year = COALESCE(:financialYear, a.financial_year)) AND (a.district_id = COALESCE(:districtId, a.district_id)) AND (a.implementation_agency = COALESCE(:agency, a.implementation_agency)) AND (a.work_status = COALESCE(:workStatus, a.work_status)) AND a.status NOT IN ('Deleted', 'Handed Over')", nativeQuery = true)
							Page<Work> fetchAllWorksByDivision(Pageable pageable, @Param("workName") String workName, @Param("workType") Long workType,
									 @Param("financialYear")  String financialYear,  @Param("districtId") Long districtId,  @Param("workStatus") String workStatusName,@Param("agency") Long agency  );
							
							
							@Query(value = "SELECT * FROM t_work a WHERE 1=1 AND (a.work_name = COALESCE(:workName, a.work_name)) AND (a.work_type = COALESCE(:workType, a.work_type)) AND (a.financial_year = COALESCE(:financialYear, a.financial_year)) AND (a.district_code = COALESCE(:districtCode, a.district_code)) AND (a.division_id = COALESCE(:divisionId, a.division_id)) AND (a.district_id = COALESCE(:districtId, a.district_id)) AND (a.implementation_agency = COALESCE(:agency, a.implementation_agency)) AND (:workPriorityId IS NULL OR a.work_priority_id = :workPriorityId) AND (:financialHeadId IS NULL OR a.financial_head_id = :financialHeadId) AND (:vidhanSabhaId IS NULL OR a.vidhan_sabha_id = :vidhanSabhaId) AND (a.work_subtype_id = COALESCE(:workSubtypeId, a.work_subtype_id)) AND (a.work_status = COALESCE(:workStatus, a.work_status)) AND a.status NOT IN ('Deleted', 'Handed Over')", nativeQuery = true)
							Page<Work> fetchAllWorksByDistrict(Pageable pageable, @Param("workName") String workName, @Param("workType") Long workType,
									 @Param("financialYear")  String financialYear, @Param("districtCode") String districtCode,@Param("agency") Long agency, @Param("divisionId") Long divisionId, @Param("districtId") Long districtId, @Param("workSubtypeId") Integer workSubTypeIdInt, @Param("workStatus") String workStatusName, @Param("workPriorityId") Long workPriorityId, @Param("financialHeadId") Long financialHeadId, @Param("vidhanSabhaId") Long vidhanSabhaId);
	
							@Query(value = "SELECT * FROM t_work a WHERE 1=1 AND (a.work_name = COALESCE(:workName, a.work_name)) AND (a.work_type = COALESCE(:workType, a.work_type)) AND (a.financial_year = COALESCE(:financialYear, a.financial_year)) AND (a.division_id = COALESCE(:divisionId, a.division_id)) AND (a.district_id = COALESCE(:districtId, a.district_id)) AND (a.implementation_agency = COALESCE(:agency, a.implementation_agency)) AND (a.work_subtype_id = COALESCE(:workSubtypeId, a.work_subtype_id)) AND (:workPriorityId IS NULL OR a.work_priority_id = :workPriorityId) AND (:financialHeadId IS NULL OR a.financial_head_id = :financialHeadId) AND (:vidhanSabhaId IS NULL OR a.vidhan_sabha_id = :vidhanSabhaId) AND (a.work_status = COALESCE(:workStatus, a.work_status)) AND a.status NOT IN ('Deleted', 'Handed Over')", nativeQuery = true)
							Page<Work> fetchAllWorksByAgency(Pageable pageable, @Param("workName") String workName, @Param("workType") Long workType,
									 @Param("financialYear")  String financialYear, @Param("agency") Long agency, @Param("divisionId") Long divisionId, @Param("districtId") Long districtId, @Param("workSubtypeId") Integer workSubTypeIdInt, @Param("workStatus") String workStatusName, @Param("workPriorityId") Long workPriorityId, @Param("financialHeadId") Long financialHeadId, @Param("vidhanSabhaId") Long vidhanSabhaId);
							
							
							@Query(value = "SELECT * FROM t_work a WHERE 1=1 AND (a.work_name = COALESCE(:workName, a.work_name)) AND (a.work_type = COALESCE(:workType, a.work_type)) AND (a.financial_year = COALESCE(:financialYear, a.financial_year)) AND (a.division_id = COALESCE(:divisionId, a.division_id)) AND (a.district_id = COALESCE(:districtId, a.district_id)) AND (a.district_code = COALESCE(:districtCode, a.district_code)) AND (a.work_subtype_id = COALESCE(:workSubtypeId, a.work_subtype_id)) AND (a.implementation_agency = COALESCE(:agency, a.implementation_agency)) AND (:workPriorityId IS NULL OR a.work_priority_id = :workPriorityId) AND (:financialHeadId IS NULL OR a.financial_head_id = :financialHeadId) AND (:vidhanSabhaId IS NULL OR a.vidhan_sabha_id = :vidhanSabhaId) AND (a.work_status = COALESCE(:workStatus, a.work_status)) AND a.status NOT IN ('Deleted', 'Handed Over')", nativeQuery = true)
							Page<Work> fetchAllDeptDistrict(Pageable pageable, @Param("workName") String workName, @Param("workType") Long workType,
									 @Param("financialYear")  String financialYear, @Param("districtCode") String districtCode, @Param("divisionId") Long divisionId, @Param("districtId") Long districtId, @Param("workSubtypeId") Integer workSubTypeIdInt, @Param("workStatus") String workStatusName,@Param("agency") Long agency, @Param("workPriorityId") Long workPriorityId, @Param("financialHeadId") Long financialHeadId, @Param("vidhanSabhaId") Long vidhanSabhaId);
							
							
							
							@Query(value = "SELECT * FROM t_work a WHERE 1=1 AND (LOWER(a.work_name) LIKE LOWER(CONCAT('%', COALESCE(:workName, ''), '%'))) AND (a.work_type = COALESCE(:workType, a.work_type)) AND (a.financial_year = COALESCE(:financialYear, a.financial_year)) AND (a.district_id = COALESCE(:districtId, a.district_id)) AND (a.implementation_agency = COALESCE(:agency, a.implementation_agency)) AND (:workPriorityId IS NULL OR a.work_priority_id = :workPriorityId) AND (:financialHeadId IS NULL OR a.financial_head_id = :financialHeadId) AND (:vidhanSabhaId IS NULL OR a.vidhan_sabha_id = :vidhanSabhaId) AND (a.work_status = COALESCE(:workStatus, a.work_status)) AND a.status NOT IN ('Deleted')", nativeQuery = true)
						    Page<Work> findAllByStatusNotDeleted(Pageable pageable, @Param("workName") String workName, @Param("workType") Long workType,
									 @Param("financialYear") String financialYear,  @Param("districtId") Long districtId,  @Param("workStatus") String workStatusName, @Param("agency") Long agency, @Param("workPriorityId") Long workPriorityId, @Param("financialHeadId") Long financialHeadId, @Param("vidhanSabhaId") Long vidhanSabhaId);
							
							
		//DIVISION wise reports
						    @Query(value="SELECT mia.impl_agency_name AS Name_of_Agency, COUNT(a.id) AS Number_of_Work, SUM(b.as_amt) AS Amount_of_AA, "
						    		+ "SUM(c.expensess_upto_march) AS Total_Expenditure_up_to_March_23, sum(c.expensess_current_fy) AS Expenditure_in_Yr_2023_24, sum(c.total_expensess) AS "
						    		+ "Up_to_Date_Expenditure, "
						    		+ "SUM(CASE WHEN a.work_status = 'Handed Over' THEN  1 else 0 end) as No_of_Work_Handed_Over_in_Yr_2023_24, "
						    		+ "SUM(CASE WHEN a.work_status = 'Completed' THEN  1 else 0 end) as No_of_Work_Completed_in_Yr_2023_24, "
						    		+ "SUM(CASE WHEN a.work_status = 'In-Progress' THEN  1 else 0 end) as Work_Started, "
						    		+ "SUM(CASE WHEN a.work_status = 'Not Started' THEN  1 else 0 end) as Not_Started, "
						    		+ "SUM(dud.Finishing_Level) as Finishing_Level, SUM(dud.Roof_Level) as Roof_Level, SUM(dud.Lintel_Level) as Lintel_Level, "
						    		+ " SUM(dud.Plinth_Level) as Plinth_Level, SUM(dud.Foundation_level) as Foundation_level, "
						    		+ "SUM(dud.Work_is_not_started_by_the_contractor) as Work_is_not_started_by_the_contractor, "
						    		+ "SUM(CASE WHEN a.work_status = 'Work Order Issued' THEN  1 else 0 end) as Tender_Issued, "
						    		+ "SUM(CASE WHEN a.work_status = 'Tender Called' or a.work_status = 'Tender Received' or "
						    		+ "a.work_status = 'Tender Approval in Process' or a.work_status = 'Re-Tender' or "
						    		+ "a.work_status = 'LoA Issued'  THEN  1 else 0 end) as Tender_Awarded "
						    		+ "from t_work a  "
						    		+ "LEFT JOIN "
						    		+ "t_work_ts b on a.id = b.work_id "
						    		+ "LEFT JOIN "
						    		+ "(SELECT work_id,SUM(expensess_upto_march) AS expensess_upto_march ,SUM(expensess_current_fy) AS expensess_current_fy, "
						    		+ "SUM(total_expensess) AS total_expensess FROM expenses_cost GROUP BY 1 ) AS c ON a.id = c.work_id "
						    		+ "left join "
						    		+ "("
						    		+ "select work_id, "
						    		+ "SUM(CASE WHEN du.work_sub_status_id = 9 THEN 1 else 0 end) as Finishing_Level, "
						    		+ "SUM(CASE WHEN du.work_sub_status_id = 8 THEN 1 else 0 end) as Roof_Level, "
						    		+ "SUM(CASE WHEN du.work_sub_status_id = 7 THEN 1 else 0 end) as Lintel_Level, "
						    		+ "SUM(CASE WHEN du.work_sub_status_id = 6 THEN 1 else 0 end) as Plinth_Level, "
						    		+ "SUM(CASE WHEN du.work_sub_status_id = 5 THEN 1 else 0 end) as Foundation_level, "
						    		+ "SUM(CASE WHEN du.work_sub_status_id = 2 THEN 1 else 0 end) as Work_is_not_started_by_the_contractor "
						    		+ "from document_upload_workprogress_details du group by work_id) as dud on a.id = dud.work_id "
						    		+ "left join mst_implementation_agency mia on mia.id = a.implementation_agency WHERE a.status != 'Deleted' AND a.division_code = :division_code and (a.financial_year != 'null' or a.financial_year != '') and (a.scheme != 'null' or a.scheme != '') and (a.work_head != 'null' or a.work_head != '')" 
						    		+ "group by a.implementation_agency", nativeQuery=true)
							List<Object[]> fetchAgencyWiseWorkByDivision(@Param("division_code") Long divisionCode);							
							
							 @Query(value="SELECT s.scheme_name AS scheme, COUNT(a.id) AS Number_of_Work, SUM(b.as_amt) AS Amount_of_AA, "
							    		+ "SUM(c.expensess_upto_march) AS Total_Expenditure_up_to_March_23, sum(c.expensess_current_fy) AS Expenditure_in_Yr_2023_24, sum(c.total_expensess) AS "
							    		+ "Up_to_Date_Expenditure, "
							    		+ "SUM(CASE WHEN a.work_status = 'Handed Over' THEN  1 else 0 end) as No_of_Work_Handed_Over_in_Yr_2023_24, "
							    		+ "SUM(CASE WHEN a.work_status = 'Completed' THEN  1 else 0 end) as No_of_Work_Completed_in_Yr_2023_24, "
							    		+ "SUM(CASE WHEN a.work_status = 'In-Progress' THEN  1 else 0 end) as Work_Started, "
							    		+ "SUM(CASE WHEN a.work_status = 'Not Started' THEN  1 else 0 end) as Not_Started, "
							    		+ "SUM(dud.Finishing_Level) as Finishing_Level, SUM(dud.Roof_Level) as Roof_Level, SUM(dud.Lintel_Level) as Lintel_Level, "
							    		+ " SUM(dud.Plinth_Level) as Plinth_Level, SUM(dud.Foundation_level) as Foundation_level, "
							    		+ "SUM(dud.Work_is_not_started_by_the_contractor) as Work_is_not_started_by_the_contractor, "
							    		+ "SUM(CASE WHEN a.work_status = 'Work Order Issued' THEN  1 else 0 end) as Tender_Issued, "
							    		+ "SUM(CASE WHEN a.work_status = 'Tender Called' or a.work_status = 'Tender Received' or "
							    		+ "a.work_status = 'Tender Approval in Process' or a.work_status = 'Re-Tender' or "
							    		+ "a.work_status = 'LoA Issued'  THEN  1 else 0 end) as Tender_Awarded "
							    		+ "from t_work a  "
							    		+ "LEFT JOIN "
							    		+ "t_work_ts b on a.id = b.work_id "
							    		+ "LEFT JOIN "
							    		+ "(SELECT work_id,SUM(expensess_upto_march) AS expensess_upto_march ,SUM(expensess_current_fy) AS expensess_current_fy, "
							    		+ "SUM(total_expensess) AS total_expensess FROM expenses_cost GROUP BY 1 ) AS c ON a.id = c.work_id "
							    		+ "left join "
							    		+ "("
							    		+ "select work_id, "
							    		+ "SUM(CASE WHEN du.work_sub_status_id = 9 THEN 1 else 0 end) as Finishing_Level, "
							    		+ "SUM(CASE WHEN du.work_sub_status_id = 8 THEN 1 else 0 end) as Roof_Level, "
							    		+ "SUM(CASE WHEN du.work_sub_status_id = 7 THEN 1 else 0 end) as Lintel_Level, "
							    		+ "SUM(CASE WHEN du.work_sub_status_id = 6 THEN 1 else 0 end) as Plinth_Level, "
							    		+ "SUM(CASE WHEN du.work_sub_status_id = 5 THEN 1 else 0 end) as Foundation_level, "
							    		+ "SUM(CASE WHEN du.work_sub_status_id = 2 THEN 1 else 0 end) as Work_is_not_started_by_the_contractor "
							    		+ "from document_upload_workprogress_details du group by work_id) as dud on a.id = dud.work_id "
							    		+ " left join mst_schemes s on s.id = a.scheme WHERE a.status != 'Deleted' AND a.division_code = :division_code  and (a.financial_year != 'null' or a.financial_year != '') and (a.scheme != 'null' or a.scheme != '') and (a.work_head != 'null' or a.work_head != '')"
							    		+ "group by 1, a.scheme_state, a.scheme_nhm, a.scheme_ecrp2, a.scheme_others", nativeQuery=true)
								List<Object[]> fetchSchemeWiseWorkByDivision(@Param("division_code") Long divisionCode);
	
								@Query(value="SELECT fy.financial_year AS year, COUNT(a.id) AS Number_of_Work, SUM(b.as_amt) AS Amount_of_AA, "
							    		+ "SUM(c.expensess_upto_march) AS Total_Expenditure_up_to_March_23, sum(c.expensess_current_fy) AS Expenditure_in_Yr_2023_24, sum(c.total_expensess) AS "
							    		+ "Up_to_Date_Expenditure, "
							    		+ "SUM(CASE WHEN a.work_status = 'Handed Over' THEN  1 else 0 end) as No_of_Work_Handed_Over_in_Yr_2023_24, "
							    		+ "SUM(CASE WHEN a.work_status = 'Completed' THEN  1 else 0 end) as No_of_Work_Completed_in_Yr_2023_24, "
							    		+ "SUM(CASE WHEN a.work_status = 'In-Progress' THEN  1 else 0 end) as Work_Started, "
							    		+ "SUM(CASE WHEN a.work_status = 'Not Started' THEN  1 else 0 end) as Not_Started, "
							    		+ "SUM(dud.Finishing_Level) as Finishing_Level, SUM(dud.Roof_Level) as Roof_Level, SUM(dud.Lintel_Level) as Lintel_Level, "
							    		+ " SUM(dud.Plinth_Level) as Plinth_Level, SUM(dud.Foundation_level) as Foundation_level, "
							    		+ "SUM(dud.Work_is_not_started_by_the_contractor) as Work_is_not_started_by_the_contractor, "
							    		+ "SUM(CASE WHEN a.work_status = 'Work Order Issued' THEN  1 else 0 end) as Tender_Issued, "
							    		+ "SUM(CASE WHEN a.work_status = 'Tender Called' or a.work_status = 'Tender Received' or "
							    		+ "a.work_status = 'Tender Approval in Process' or a.work_status = 'Re-Tender' or "
							    		+ "a.work_status = 'LoA Issued'  THEN  1 else 0 end) as Tender_Awarded "
							    		+ "from t_work a  "
							    		+ "LEFT JOIN "
							    		+ "t_work_ts b on a.id = b.work_id AND a.status != 'Deleted' "
							    		+ "LEFT JOIN "
							    		+ "(SELECT work_id,SUM(expensess_upto_march) AS expensess_upto_march ,SUM(expensess_current_fy) AS expensess_current_fy, "
							    		+ "SUM(total_expensess) AS total_expensess FROM expenses_cost GROUP BY 1 ) AS c ON a.id = c.work_id "
							    		+ "left join "
							    		+ "("
							    		+ "select work_id, "
							    		+ "SUM(CASE WHEN du.work_sub_status_id = 9 THEN 1 else 0 end) as Finishing_Level, "
							    		+ "SUM(CASE WHEN du.work_sub_status_id = 8 THEN 1 else 0 end) as Roof_Level, "
							    		+ "SUM(CASE WHEN du.work_sub_status_id = 7 THEN 1 else 0 end) as Lintel_Level, "
							    		+ "SUM(CASE WHEN du.work_sub_status_id = 6 THEN 1 else 0 end) as Plinth_Level, "
							    		+ "SUM(CASE WHEN du.work_sub_status_id = 5 THEN 1 else 0 end) as Foundation_level, "
							    		+ "SUM(CASE WHEN du.work_sub_status_id = 2 THEN 1 else 0 end) as Work_is_not_started_by_the_contractor "
							    		+ "from document_upload_workprogress_details du group by work_id) as dud on a.id = dud.work_id "
							    		+ "left join mst_financial_year fy on fy.id = a.financial_year where  a.status != 'Deleted' AND a.division_code = :division_code and (a.financial_year != 'null' or a.financial_year != '') and (a.scheme != 'null' or a.scheme != '') and (a.work_head != 'null' or a.work_head != '')"
							    		+ "group by 1", nativeQuery=true)
								List<Object[]> fetchYearWiseWorkByDivision(@Param("division_code") Long divisionCode);

								@Query(value="SELECT wh.head_name AS segment, COUNT(a.id) AS Number_of_Work, SUM(b.as_amt) AS Amount_of_AA, "
							    		+ "SUM(c.expensess_upto_march) AS Total_Expenditure_up_to_March_23, sum(c.expensess_current_fy) AS Expenditure_in_Yr_2023_24, sum(c.total_expensess) AS "
							    		+ "Up_to_Date_Expenditure, "
							    		+ "SUM(CASE WHEN a.work_status = 'Handed Over' THEN  1 else 0 end) as No_of_Work_Handed_Over_in_Yr_2023_24, "
							    		+ "SUM(CASE WHEN a.work_status = 'Completed' THEN  1 else 0 end) as No_of_Work_Completed_in_Yr_2023_24, "
							    		+ "SUM(CASE WHEN a.work_status = 'In-Progress' THEN  1 else 0 end) as Work_Started, "
							    		+ "SUM(CASE WHEN a.work_status = 'Not Started' THEN  1 else 0 end) as Not_Started, "
							    		+ "SUM(dud.Finishing_Level) as Finishing_Level, SUM(dud.Roof_Level) as Roof_Level, SUM(dud.Lintel_Level) as Lintel_Level, "
							    		+ " SUM(dud.Plinth_Level) as Plinth_Level, SUM(dud.Foundation_level) as Foundation_level, "
							    		+ "SUM(dud.Work_is_not_started_by_the_contractor) as Work_is_not_started_by_the_contractor, "
							    		+ "SUM(CASE WHEN a.work_status = 'Work Order Issued' THEN  1 else 0 end) as Tender_Issued, "
							    		+ "SUM(CASE WHEN a.work_status = 'Tender Called' or a.work_status = 'Tender Received' or "
							    		+ "a.work_status = 'Tender Approval in Process' or a.work_status = 'Re-Tender' or "
							    		+ "a.work_status = 'LoA Issued'  THEN  1 else 0 end) as Tender_Awarded "
							    		+ "from t_work a  "
							    		+ "LEFT JOIN "
							    		+ "t_work_ts b on a.id = b.work_id "
							    		+ "LEFT JOIN "
							    		+ "(SELECT work_id,SUM(expensess_upto_march) AS expensess_upto_march ,SUM(expensess_current_fy) AS expensess_current_fy, "
							    		+ "SUM(total_expensess) AS total_expensess FROM expenses_cost GROUP BY 1 ) AS c ON a.id = c.work_id "
							    		+ "left join "
							    		+ "("
							    		+ "select work_id, "
							    		+ "SUM(CASE WHEN du.work_sub_status_id = 9 THEN 1 else 0 end) as Finishing_Level, "
							    		+ "SUM(CASE WHEN du.work_sub_status_id = 8 THEN 1 else 0 end) as Roof_Level, "
							    		+ "SUM(CASE WHEN du.work_sub_status_id = 7 THEN 1 else 0 end) as Lintel_Level, "
							    		+ "SUM(CASE WHEN du.work_sub_status_id = 6 THEN 1 else 0 end) as Plinth_Level, "
							    		+ "SUM(CASE WHEN du.work_sub_status_id = 5 THEN 1 else 0 end) as Foundation_level, "
							    		+ "SUM(CASE WHEN du.work_sub_status_id = 2 THEN 1 else 0 end) as Work_is_not_started_by_the_contractor "
							    		+ "from document_upload_workprogress_details du group by work_id) as dud on a.id = dud.work_id "
							    		+ "left join mst_work_head wh on wh.id = a.work_head WHERE a.status != 'Deleted' AND a.division_code = :division_code and (a.financial_year != 'null' or a.financial_year != '') and (a.scheme != 'null' or a.scheme != '') and (a.work_head != 'null' or a.work_head != '')"
							    		+ "group by 1,a.head_state, a.head_nhm, a.head_ecrp2, a.head_others", nativeQuery=true)
								List<Object[]> fetchSegmentWiseWorkByDivision(@Param("division_code") Long divisionCode);


								@Query(value="SELECT concat(s.scheme_name,'(',fy.financial_year,')') as schemeYear, COUNT(a.id) AS Number_of_Work, SUM(b.as_amt) AS Amount_of_AA, "
							    		+ "SUM(c.expensess_upto_march) AS Total_Expenditure_up_to_March_23, sum(c.expensess_current_fy) AS Expenditure_in_Yr_2023_24, sum(c.total_expensess) AS "
							    		+ "Up_to_Date_Expenditure, "
							    		+ "SUM(CASE WHEN a.work_status = 'Handed Over' THEN  1 else 0 end) as No_of_Work_Handed_Over_in_Yr_2023_24, "
							    		+ "SUM(CASE WHEN a.work_status = 'Completed' THEN  1 else 0 end) as No_of_Work_Completed_in_Yr_2023_24, "
							    		+ "SUM(CASE WHEN a.work_status = 'In-Progress' THEN  1 else 0 end) as Work_Started, "
							    		+ "SUM(CASE WHEN a.work_status = 'Not Started' THEN  1 else 0 end) as Not_Started, "
							    		+ "SUM(dud.Finishing_Level) as Finishing_Level, SUM(dud.Roof_Level) as Roof_Level, SUM(dud.Lintel_Level) as Lintel_Level, "
							    		+ " SUM(dud.Plinth_Level) as Plinth_Level, SUM(dud.Foundation_level) as Foundation_level, "
							    		+ "SUM(dud.Work_is_not_started_by_the_contractor) as Work_is_not_started_by_the_contractor, "
							    		+ "SUM(CASE WHEN a.work_status = 'Work Order Issued' THEN  1 else 0 end) as Tender_Issued, "
							    		+ "SUM(CASE WHEN a.work_status = 'Tender Called' or a.work_status = 'Tender Received' or "
							    		+ "a.work_status = 'Tender Approval in Process' or a.work_status = 'Re-Tender' or "
							    		+ "a.work_status = 'LoA Issued'  THEN  1 else 0 end) as Tender_Awarded "
							    		+ "from t_work a  "
							    		+ "LEFT JOIN "
							    		+ "t_work_ts b on a.id = b.work_id "
							    		+ "LEFT JOIN "
							    		+ "(SELECT work_id,SUM(expensess_upto_march) AS expensess_upto_march ,SUM(expensess_current_fy) AS expensess_current_fy, "
							    		+ "SUM(total_expensess) AS total_expensess FROM expenses_cost GROUP BY 1 ) AS c ON a.id = c.work_id "
							    		+ "left join "
							    		+ "("
							    		+ "select work_id, "
							    		+ "SUM(CASE WHEN du.work_sub_status_id = 9 THEN 1 else 0 end) as Finishing_Level, "
							    		+ "SUM(CASE WHEN du.work_sub_status_id = 8 THEN 1 else 0 end) as Roof_Level, "
							    		+ "SUM(CASE WHEN du.work_sub_status_id = 7 THEN 1 else 0 end) as Lintel_Level, "
							    		+ "SUM(CASE WHEN du.work_sub_status_id = 6 THEN 1 else 0 end) as Plinth_Level, "
							    		+ "SUM(CASE WHEN du.work_sub_status_id = 5 THEN 1 else 0 end) as Foundation_level, "
							    		+ "SUM(CASE WHEN du.work_sub_status_id = 2 THEN 1 else 0 end) as Work_is_not_started_by_the_contractor "
							    		+ "from document_upload_workprogress_details du group by work_id) as dud on a.id = dud.work_id "
							    		+ "left join mst_schemes s on s.id = a.scheme left join mst_financial_year fy on fy.id = a.financial_year WHERE a.status != 'Deleted' AND a.division_code = :division_code AND (a.financial_year != 'null' or a.financial_year != '') and (a.scheme != 'null' or a.scheme != '') and (a.work_head != 'null' or a.work_head != '')"
							    		+ "group by 1, a.scheme_state, a.scheme_nhm, a.scheme_ecrp2, a.scheme_others", nativeQuery=true)
								List<Object[]> fetchSchemeYearWiseWorkByDivision(@Param("division_code") Long divisionCode);
								
				// Reports fetch by district and agency
								
								@Query(value="SELECT mia.impl_agency_name AS Name_of_Agency, COUNT(a.id) AS Number_of_Work, SUM(b.as_amt) AS Amount_of_AA, "
							    		+ "SUM(c.expensess_upto_march) AS Total_Expenditure_up_to_March_23, sum(c.expensess_current_fy) AS Expenditure_in_Yr_2023_24, sum(c.total_expensess) AS "
							    		+ "Up_to_Date_Expenditure, "
							    		+ "SUM(CASE WHEN a.work_status = 'Handed Over' THEN  1 else 0 end) as No_of_Work_Handed_Over_in_Yr_2023_24, "
							    		+ "SUM(CASE WHEN a.work_status = 'Completed' THEN  1 else 0 end) as No_of_Work_Completed_in_Yr_2023_24, "
							    		+ "SUM(CASE WHEN a.work_status = 'In-Progress' THEN  1 else 0 end) as Work_Started, "
							    		+ "SUM(CASE WHEN a.work_status = 'Not Started' THEN  1 else 0 end) as Not_Started, "
							    		+ "SUM(dud.Finishing_Level) as Finishing_Level, SUM(dud.Roof_Level) as Roof_Level, SUM(dud.Lintel_Level) as Lintel_Level, "
							    		+ " SUM(dud.Plinth_Level) as Plinth_Level, SUM(dud.Foundation_level) as Foundation_level, "
							    		+ "SUM(dud.Work_is_not_started_by_the_contractor) as Work_is_not_started_by_the_contractor, "
							    		+ "SUM(CASE WHEN a.work_status = 'Work Order Issued' THEN  1 else 0 end) as Tender_Issued, "
							    		+ "SUM(CASE WHEN a.work_status = 'Tender Called' or a.work_status = 'Tender Received' or "
							    		+ "a.work_status = 'Tender Approval in Process' or a.work_status = 'Re-Tender' or "
							    		+ "a.work_status = 'LoA Issued'  THEN  1 else 0 end) as Tender_Awarded "
							    		+ "from t_work a  "
							    		+ "LEFT JOIN "
							    		+ "t_work_ts b on a.id = b.work_id "
							    		+ "LEFT JOIN "
							    		+ "(SELECT work_id,SUM(expensess_upto_march) AS expensess_upto_march ,SUM(expensess_current_fy) AS expensess_current_fy, "
							    		+ "SUM(total_expensess) AS total_expensess FROM expenses_cost GROUP BY 1 ) AS c ON a.id = c.work_id "
							    		+ "left join "
							    		+ "("
							    		+ "select work_id, "
							    		+ "SUM(CASE WHEN du.work_sub_status_id = 9 THEN 1 else 0 end) as Finishing_Level, "
							    		+ "SUM(CASE WHEN du.work_sub_status_id = 8 THEN 1 else 0 end) as Roof_Level, "
							    		+ "SUM(CASE WHEN du.work_sub_status_id = 7 THEN 1 else 0 end) as Lintel_Level, "
							    		+ "SUM(CASE WHEN du.work_sub_status_id = 6 THEN 1 else 0 end) as Plinth_Level, "
							    		+ "SUM(CASE WHEN du.work_sub_status_id = 5 THEN 1 else 0 end) as Foundation_level, "
							    		+ "SUM(CASE WHEN du.work_sub_status_id = 2 THEN 1 else 0 end) as Work_is_not_started_by_the_contractor "
							    		+ "from document_upload_workprogress_details du group by work_id) as dud on a.id = dud.work_id "
							    		+ " left join mst_implementation_agency mia on mia.id = a.implementation_agency WHERE a.status != 'Deleted' AND (:district_code IS NULL OR a.district_code = :district_code) AND (:implementation_agency IS NULL OR a.implementation_agency = :implementation_agency) and (a.financial_year != 'null' or a.financial_year != '') and (a.scheme != 'null' or a.scheme != '') and (a.work_head != 'null' or a.work_head != '')" 
							    		+ "group by a.implementation_agency", nativeQuery=true)
								List<Object[]> fetchAgencyWiseWorkByDistrict(@Param("district_code") String districtCode, @Param("implementation_agency") ImplementationAgency implementationAgency);							

								 @Query(value="SELECT s.scheme_name AS scheme, COUNT(a.id) AS Number_of_Work, SUM(b.as_amt) AS Amount_of_AA, "
								    		+ "SUM(c.expensess_upto_march) AS Total_Expenditure_up_to_March_23, sum(c.expensess_current_fy) AS Expenditure_in_Yr_2023_24, sum(c.total_expensess) AS "
								    		+ "Up_to_Date_Expenditure, "
								    		+ "SUM(CASE WHEN a.work_status = 'Handed Over' THEN  1 else 0 end) as No_of_Work_Handed_Over_in_Yr_2023_24, "
								    		+ "SUM(CASE WHEN a.work_status = 'Completed' THEN  1 else 0 end) as No_of_Work_Completed_in_Yr_2023_24, "
								    		+ "SUM(CASE WHEN a.work_status = 'In-Progress' THEN  1 else 0 end) as Work_Started, "
								    		+ "SUM(CASE WHEN a.work_status = 'Not Started' THEN  1 else 0 end) as Not_Started, "
								    		+ "SUM(dud.Finishing_Level) as Finishing_Level, SUM(dud.Roof_Level) as Roof_Level, SUM(dud.Lintel_Level) as Lintel_Level, "
								    		+ " SUM(dud.Plinth_Level) as Plinth_Level, SUM(dud.Foundation_level) as Foundation_level, "
								    		+ "SUM(dud.Work_is_not_started_by_the_contractor) as Work_is_not_started_by_the_contractor, "
								    		+ "SUM(CASE WHEN a.work_status = 'Work Order Issued' THEN  1 else 0 end) as Tender_Issued, "
								    		+ "SUM(CASE WHEN a.work_status = 'Tender Called' or a.work_status = 'Tender Received' or "
								    		+ "a.work_status = 'Tender Approval in Process' or a.work_status = 'Re-Tender' or "
								    		+ "a.work_status = 'LoA Issued'  THEN  1 else 0 end) as Tender_Awarded "
								    		+ "from t_work a  "
								    		+ "LEFT JOIN "
								    		+ "t_work_ts b on a.id = b.work_id "
								    		+ "LEFT JOIN "
								    		+ "(SELECT work_id,SUM(expensess_upto_march) AS expensess_upto_march ,SUM(expensess_current_fy) AS expensess_current_fy, "
								    		+ "SUM(total_expensess) AS total_expensess FROM expenses_cost GROUP BY 1 ) AS c ON a.id = c.work_id "
								    		+ "left join "
								    		+ "("
								    		+ "select work_id, "
								    		+ "SUM(CASE WHEN du.work_sub_status_id = 9 THEN 1 else 0 end) as Finishing_Level, "
								    		+ "SUM(CASE WHEN du.work_sub_status_id = 8 THEN 1 else 0 end) as Roof_Level, "
								    		+ "SUM(CASE WHEN du.work_sub_status_id = 7 THEN 1 else 0 end) as Lintel_Level, "
								    		+ "SUM(CASE WHEN du.work_sub_status_id = 6 THEN 1 else 0 end) as Plinth_Level, "
								    		+ "SUM(CASE WHEN du.work_sub_status_id = 5 THEN 1 else 0 end) as Foundation_level, "
								    		+ "SUM(CASE WHEN du.work_sub_status_id = 2 THEN 1 else 0 end) as Work_is_not_started_by_the_contractor "
								    		+ "from document_upload_workprogress_details du group by work_id) as dud on a.id = dud.work_id "
								    		+ "left join mst_schemes s on s.id = a.scheme WHERE a.status != 'Deleted' AND (:district_code IS NULL OR a.district_code = :district_code) AND (:implementation_agency IS NULL OR a.implementation_agency = :implementation_agency) and (a.financial_year != 'null' or a.financial_year != '') and (a.scheme != 'null' or a.scheme != '') and (a.work_head != 'null' or a.work_head != '') "
								    		+ "group by 1, a.scheme_state, a.scheme_nhm, a.scheme_ecrp2, a.scheme_others", nativeQuery=true)
									List<Object[]> fetchSchemeWiseWorkByDistrict(@Param("district_code") String districtCode, @Param("implementation_agency") ImplementationAgency implementationAgency);

									
									@Query(value="SELECT fy.financial_year AS year, COUNT(a.id) AS Number_of_Work, SUM(b.as_amt) AS Amount_of_AA, "
								    		+ "SUM(c.expensess_upto_march) AS Total_Expenditure_up_to_March_23, sum(c.expensess_current_fy) AS Expenditure_in_Yr_2023_24, sum(c.total_expensess) AS "
								    		+ "Up_to_Date_Expenditure, "
								    		+ "SUM(CASE WHEN a.work_status = 'Handed Over' THEN  1 else 0 end) as No_of_Work_Handed_Over_in_Yr_2023_24, "
								    		+ "SUM(CASE WHEN a.work_status = 'Completed' THEN  1 else 0 end) as No_of_Work_Completed_in_Yr_2023_24, "
								    		+ "SUM(CASE WHEN a.work_status = 'In-Progress' THEN  1 else 0 end) as Work_Started, "
								    		+ "SUM(CASE WHEN a.work_status = 'Not Started' THEN  1 else 0 end) as Not_Started, "
								    		+ "SUM(dud.Finishing_Level) as Finishing_Level, SUM(dud.Roof_Level) as Roof_Level, SUM(dud.Lintel_Level) as Lintel_Level, "
								    		+ " SUM(dud.Plinth_Level) as Plinth_Level, SUM(dud.Foundation_level) as Foundation_level, "
								    		+ "SUM(dud.Work_is_not_started_by_the_contractor) as Work_is_not_started_by_the_contractor, "
								    		+ "SUM(CASE WHEN a.work_status = 'Work Order Issued' THEN  1 else 0 end) as Tender_Issued, "
								    		+ "SUM(CASE WHEN a.work_status = 'Tender Called' or a.work_status = 'Tender Received' or "
								    		+ "a.work_status = 'Tender Approval in Process' or a.work_status = 'Re-Tender' or "
								    		+ "a.work_status = 'LoA Issued'  THEN  1 else 0 end) as Tender_Awarded "
								    		+ "from t_work a  "
								    		+ "LEFT JOIN "
								    		+ "t_work_ts b on a.id = b.work_id AND a.status != 'Deleted' "
								    		+ "LEFT JOIN "
								    		+ "(SELECT work_id,SUM(expensess_upto_march) AS expensess_upto_march ,SUM(expensess_current_fy) AS expensess_current_fy, "
								    		+ "SUM(total_expensess) AS total_expensess FROM expenses_cost GROUP BY 1 ) AS c ON a.id = c.work_id "
								    		+ "left join "
								    		+ "("
								    		+ "select work_id, "
								    		+ "SUM(CASE WHEN du.work_sub_status_id = 9 THEN 1 else 0 end) as Finishing_Level, "
								    		+ "SUM(CASE WHEN du.work_sub_status_id = 8 THEN 1 else 0 end) as Roof_Level, "
								    		+ "SUM(CASE WHEN du.work_sub_status_id = 7 THEN 1 else 0 end) as Lintel_Level, "
								    		+ "SUM(CASE WHEN du.work_sub_status_id = 6 THEN 1 else 0 end) as Plinth_Level, "
								    		+ "SUM(CASE WHEN du.work_sub_status_id = 5 THEN 1 else 0 end) as Foundation_level, "
								    		+ "SUM(CASE WHEN du.work_sub_status_id = 2 THEN 1 else 0 end) as Work_is_not_started_by_the_contractor "
								    		+ "from document_upload_workprogress_details du group by work_id) as dud on a.id = dud.work_id "
								    		+ "left join mst_financial_year fy on fy.id = a.financial_year where  a.status != 'Deleted' AND (:district_code IS NULL OR a.district_code = :district_code) AND (:implementation_agency IS NULL OR a.implementation_agency = :implementation_agency) and (a.financial_year != 'null' or a.financial_year != '') and (a.scheme != 'null' or a.scheme != '') and (a.work_head != 'null' or a.work_head != '')"
								    		+ "group by 1", nativeQuery=true)
									List<Object[]> fetchYearWiseWorkByDistrict(@Param("district_code") String districtCode, @Param("implementation_agency") ImplementationAgency implementationAgency);

									@Query(value="SELECT wh.head_name AS segment, COUNT(a.id) AS Number_of_Work, SUM(b.as_amt) AS Amount_of_AA, "
								    		+ "SUM(c.expensess_upto_march) AS Total_Expenditure_up_to_March_23, sum(c.expensess_current_fy) AS Expenditure_in_Yr_2023_24, sum(c.total_expensess) AS "
								    		+ "Up_to_Date_Expenditure, "
								    		+ "SUM(CASE WHEN a.work_status = 'Handed Over' THEN  1 else 0 end) as No_of_Work_Handed_Over_in_Yr_2023_24, "
								    		+ "SUM(CASE WHEN a.work_status = 'Completed' THEN  1 else 0 end) as No_of_Work_Completed_in_Yr_2023_24, "
								    		+ "SUM(CASE WHEN a.work_status = 'In-Progress' THEN  1 else 0 end) as Work_Started, "
								    		+ "SUM(CASE WHEN a.work_status = 'Not Started' THEN  1 else 0 end) as Not_Started, "
								    		+ "SUM(dud.Finishing_Level) as Finishing_Level, SUM(dud.Roof_Level) as Roof_Level, SUM(dud.Lintel_Level) as Lintel_Level, "
								    		+ " SUM(dud.Plinth_Level) as Plinth_Level, SUM(dud.Foundation_level) as Foundation_level, "
								    		+ "SUM(dud.Work_is_not_started_by_the_contractor) as Work_is_not_started_by_the_contractor, "
								    		+ "SUM(CASE WHEN a.work_status = 'Work Order Issued' THEN  1 else 0 end) as Tender_Issued, "
								    		+ "SUM(CASE WHEN a.work_status = 'Tender Called' or a.work_status = 'Tender Received' or "
								    		+ "a.work_status = 'Tender Approval in Process' or a.work_status = 'Re-Tender' or "
								    		+ "a.work_status = 'LoA Issued'  THEN  1 else 0 end) as Tender_Awarded "
								    		+ "from t_work a  "
								    		+ "LEFT JOIN "
								    		+ "t_work_ts b on a.id = b.work_id "
								    		+ "LEFT JOIN "
								    		+ "(SELECT work_id,SUM(expensess_upto_march) AS expensess_upto_march ,SUM(expensess_current_fy) AS expensess_current_fy, "
								    		+ "SUM(total_expensess) AS total_expensess FROM expenses_cost GROUP BY 1 ) AS c ON a.id = c.work_id "
								    		+ "left join "
								    		+ "("
								    		+ "select work_id, "
								    		+ "SUM(CASE WHEN du.work_sub_status_id = 9 THEN 1 else 0 end) as Finishing_Level, "
								    		+ "SUM(CASE WHEN du.work_sub_status_id = 8 THEN 1 else 0 end) as Roof_Level, "
								    		+ "SUM(CASE WHEN du.work_sub_status_id = 7 THEN 1 else 0 end) as Lintel_Level, "
								    		+ "SUM(CASE WHEN du.work_sub_status_id = 6 THEN 1 else 0 end) as Plinth_Level, "
								    		+ "SUM(CASE WHEN du.work_sub_status_id = 5 THEN 1 else 0 end) as Foundation_level, "
								    		+ "SUM(CASE WHEN du.work_sub_status_id = 2 THEN 1 else 0 end) as Work_is_not_started_by_the_contractor "
								    		+ "from document_upload_workprogress_details du group by work_id) as dud on a.id = dud.work_id "
								    		+ "left join mst_work_head wh on wh.id = a.work_head WHERE a.status != 'Deleted' AND (:district_code IS NULL OR a.district_code = :district_code) AND (:implementation_agency IS NULL OR a.implementation_agency = :implementation_agency) AND (a.work_head != '' OR a.work_head!='NULL') and (a.financial_year != 'null' or a.financial_year != '') and (a.scheme != 'null' or a.scheme != '') and (a.work_head != 'null' or a.work_head != '')"
								    		+ "group by 1,a.head_state, a.head_nhm, a.head_ecrp2, a.head_others", nativeQuery=true)
									List<Object[]> fetchSegmentWiseWorkByDistrict(@Param("district_code") String districtCode, @Param("implementation_agency") ImplementationAgency implementationAgency);

									@Query(value="SELECT concat(s.scheme_name,'(',fy.financial_year,')') as schemeYear, COUNT(a.id) AS Number_of_Work, SUM(b.as_amt) AS Amount_of_AA, "
								    		+ "SUM(c.expensess_upto_march) AS Total_Expenditure_up_to_March_23, sum(c.expensess_current_fy) AS Expenditure_in_Yr_2023_24, sum(c.total_expensess) AS "
								    		+ "Up_to_Date_Expenditure, "
								    		+ "SUM(CASE WHEN a.work_status = 'Handed Over' THEN  1 else 0 end) as No_of_Work_Handed_Over_in_Yr_2023_24, "
								    		+ "SUM(CASE WHEN a.work_status = 'Completed' THEN  1 else 0 end) as No_of_Work_Completed_in_Yr_2023_24, "
								    		+ "SUM(CASE WHEN a.work_status = 'In-Progress' THEN  1 else 0 end) as Work_Started, "
								    		+ "SUM(CASE WHEN a.work_status = 'Not Started' THEN  1 else 0 end) as Not_Started, "
								    		+ "SUM(dud.Finishing_Level) as Finishing_Level, SUM(dud.Roof_Level) as Roof_Level, SUM(dud.Lintel_Level) as Lintel_Level, "
								    		+ " SUM(dud.Plinth_Level) as Plinth_Level, SUM(dud.Foundation_level) as Foundation_level, "
								    		+ "SUM(dud.Work_is_not_started_by_the_contractor) as Work_is_not_started_by_the_contractor, "
								    		+ "SUM(CASE WHEN a.work_status = 'Work Order Issued' THEN  1 else 0 end) as Tender_Issued, "
								    		+ "SUM(CASE WHEN a.work_status = 'Tender Called' or a.work_status = 'Tender Received' or "
								    		+ "a.work_status = 'Tender Approval in Process' or a.work_status = 'Re-Tender' or "
								    		+ "a.work_status = 'LoA Issued'  THEN  1 else 0 end) as Tender_Awarded "
								    		+ "from t_work a  "
								    		+ "LEFT JOIN "
								    		+ "t_work_ts b on a.id = b.work_id "
								    		+ "LEFT JOIN "
								    		+ "(SELECT work_id,SUM(expensess_upto_march) AS expensess_upto_march ,SUM(expensess_current_fy) AS expensess_current_fy, "
								    		+ "SUM(total_expensess) AS total_expensess FROM expenses_cost GROUP BY 1 ) AS c ON a.id = c.work_id "
								    		+ "left join "
								    		+ "("
								    		+ "select work_id, "
								    		+ "SUM(CASE WHEN du.work_sub_status_id = 9 THEN 1 else 0 end) as Finishing_Level, "
								    		+ "SUM(CASE WHEN du.work_sub_status_id = 8 THEN 1 else 0 end) as Roof_Level, "
								    		+ "SUM(CASE WHEN du.work_sub_status_id = 7 THEN 1 else 0 end) as Lintel_Level, "
								    		+ "SUM(CASE WHEN du.work_sub_status_id = 6 THEN 1 else 0 end) as Plinth_Level, "
								    		+ "SUM(CASE WHEN du.work_sub_status_id = 5 THEN 1 else 0 end) as Foundation_level, "
								    		+ "SUM(CASE WHEN du.work_sub_status_id = 2 THEN 1 else 0 end) as Work_is_not_started_by_the_contractor "
								    		+ "from document_upload_workprogress_details du group by work_id) as dud on a.id = dud.work_id "
								    		+ "left join mst_schemes s on s.id = a.scheme left join mst_financial_year fy on fy.id = a.financial_year WHERE a.status != 'Deleted' AND (:district_code IS NULL OR a.district_code = :district_code) AND (:implementation_agency IS NULL OR a.implementation_agency = :implementation_agency) and (a.financial_year != 'null' or a.financial_year != '') and (a.scheme != 'null' or a.scheme != '') and (a.work_head != 'null' or a.work_head != '')"
								    		+ "group by 1, a.scheme_state, a.scheme_nhm, a.scheme_ecrp2, a.scheme_others", nativeQuery=true)
									List<Object[]> fetchSchemeYearWiseWorkByDistrict(@Param("district_code") String districtCode, @Param("implementation_agency") ImplementationAgency implementationAgency);
										
									@Query(value = "SELECT * FROM t_work w " +
									           "WHERE w.status = 'Active' " +
									           "AND w.work_status = 'AA Issued' " +
									           "AND (w.scheme IS NOT NULL AND w.scheme != '') " +
									           "AND (w.financial_year IS NOT NULL AND w.financial_year != '') " +
									           "AND (w.work_head IS NOT NULL AND w.work_head != '')", nativeQuery = true)
									    Page<Work> findActiveWorks(Pageable pageable);

									//List<Work> findByWorkStatusIn(String[] workStatusVerification);
									//@Query(value = "select * from t_work where work_status in (SELECT work_status_name_e FROM mst_work_status)", nativeQuery=true)
									//@Query(value = "select * from t_work", nativeQuery=true)
									@Query("SELECT w From Work w WHERE w.status = 'Active' ")
									List<Work> getAllWork();
									
									@Query(value = "select count(*) FROM t_work w where w.work_status = :workStatus and status = 'Active' and (scheme is not null) and (financial_year is not null) and (work_head is not null)"  , nativeQuery = true)
									BigDecimal getDashboardCountByStatus(@Param("workStatus") String workStatus);
									@Query(value="select work_name, implementation_agency, financial_year,work_status  from t_work",nativeQuery = true)													
									List<Work> findByYear(String year);
                                    
									
									@Query("SELECT w FROM Work w WHERE " +
										       "(:workName IS NULL OR LOWER(w.workName) LIKE CONCAT('%', LOWER(:workName), '%')) " +
										       "AND (:department IS NULL OR w.createdBy = :department) " +
										       "AND (:year IS NULL OR w.financialYear = :year)")
									Page<Work> findByWorkNameORYearOrDepartment(Pageable pageable,@Param("department") String department,
											@Param("year") Long year, @Param("workName") String workName);

									Page<Work> findByWorkNameIgnoreCase(Pageable pageable, String workName);

									Page<Work> findByWorkNameContainingIgnoreCase(Pageable pageable, String workName);

									Page<Work> findByWorkNameStartingWith(Pageable pageable, String workName);

									Page<Work> findByFinancialYear(Pageable pageable, Long long1);

									Page<Work> findByFinancialYearAndWorkNameContainingIgnoreCase(Pageable pageable,
											Long long1, String workName);

									Page<Work> findByWorkNameContaining(Pageable pageable, String workName);

									Page<Work> findByFinancialYearAndWorkNameContaining(Pageable pageable, Long long1,
											String workName);

									Page<Work> findByCreatedBy(Pageable pageable, String username);

									Page<Work> findByCreatedByAndFinancialYearAndWorkNameContaining(Pageable pageable,
											String username, Long long1, String workName);

									Page<Work> findByCreatedByAndFinancialYear(Pageable pageable, String username,
											Long long1);

									Page<Work> findByCreatedByAndWorkNameContaining(Pageable pageable, String username,
											String workName);

									Page<Work> findByWorkStatus(Pageable pageable, Long long1);

									Page<Work> findByWorkStatusAndWorkNameContaining(Pageable pageable, Long long1,
											String workName);

									Page<Work> findByWorkStatusAndWorkNameContainingAndCreatedBy(Pageable pageable,
											Long long1, String workName, String username);

									Page<Work> findByWorkStatusAndWorkNameContainingAndFinancialYear(Pageable pageable,
											Long long1, String workName, Long long2);

									Page<Work> findByWorkStatusAndWorkNameContainingAndFinancialYearAndCreatedBy(
											Pageable pageable, Long long1, String workName, Long long2,
											String username);
									@Query(value = "SELECT * FROM t_work a WHERE 1=1 AND (a.work_name = COALESCE(:workName, a.work_name)) AND (a.work_type = COALESCE(:workType, a.work_type)) AND (a.financial_year = COALESCE(:financialYear, a.financial_year)) AND (a.district_id = COALESCE(:districtId, a.district_id)) AND (a.implementation_agency = COALESCE(:agency, a.implementation_agency)) AND (:workPriorityId IS NULL OR a.work_priority_id = :workPriorityId) AND (:financialHeadId IS NULL OR a.financial_head_id = :financialHeadId) AND (:vidhanSabhaId IS NULL OR a.vidhan_sabha_id = :vidhanSabhaId) AND (a.work_status = COALESCE(:workStatus, a.work_status)) AND a.status NOT IN ('Deleted', 'Handed Over') AND a.created_by = :username", nativeQuery = true)
									
									Page<Work> fetchAllWorksByDivision(Pageable pageable, @Param("workName") String workName, @Param("workType") Long workType,
											 @Param("financialYear")  String financialYear,  @Param("districtId") Long districtId,  @Param("workStatus") String workStatusName,@Param("agency") Long agency ,@Param("username") String username, @Param("workPriorityId") Long workPriorityId, @Param("financialHeadId") Long financialHeadId, @Param("vidhanSabhaId") Long vidhanSabhaId);
									
									
									List<Work> findByImplementationAgency(Long agencyId);

									List<Work> findByUserAssignee(Long userId);
									

									@Query("SELECT w FROM Work w WHERE w.status = 'Active'")
									List<Work> findAllActiveWorks();

									
									//Mobile API by Sumit
									@Query("SELECT w FROM Work w WHERE w.id = :id AND w.status = 'Active'")
									Work findActiveStatusById(@Param("id") Long id);
									
									@Query("SELECT w FROM Work w WHERE w.userAssignee = :userId AND w.status = 'Active'")
									List<Work> findActiveWorksByUser(@Param("userId") Long userId);
									
									
									
									
									//inspection Report Query
									@Query(value = "SELECT w.work_no, w.work_name, w.implementation_agency, w.user_id, w.work_status, wp.work_sub_status_id, wp.modified_date " +
								               "FROM t_work w " +
								               "LEFT JOIN t_work_progress wp ON w.id = wp.work_id " +
								               "WHERE w.user_id IS NOT NULL " +  // ✅ user_id null nahi hona chahiye
								               "AND (w.work_status = COALESCE(:workStatus, w.work_status)) " +
								               "AND (w.user_id = COALESCE(:userId, w.user_id)) " +
								               "AND (w.implementation_agency = COALESCE(:agencyId, w.implementation_agency)) " +
								               "AND (:workSubStatus IS NULL OR wp.work_sub_status_id = :workSubStatus) " +
								               "AND (LOWER(w.work_name) LIKE LOWER(CONCAT('%', :workName, '%'))) " +
										       "ORDER BY w.id desc",
								       nativeQuery = true)
								List<Object[]> fetchWorkWithProgressFilters(
								       @Param("workStatus") Long workStatus,
								       @Param("userId") Long userId,
								       @Param("agencyId") Long agencyId,
								       @Param("workSubStatus") Long workSubStatus,
								       @Param("workName") String workName);
								
								
								
								//Expenditure Report Query
								@Query(value = "SELECT w.work_no, w.work_name, w.implementation_agency, w.user_id, wt.pac, wp.total_expensess, " +
								        "MAX(ec.expensess_upto_march) AS total_expensess_upto_march, w.work_status, MAX(ec.created_date) AS created_date " +
								        "FROM t_work w " +
								        "LEFT JOIN t_work_progress wp ON w.id = wp.work_id " +
								        "LEFT JOIN t_work_tender wt ON w.id = wt.work_id " +
								        "LEFT JOIN expenses_cost ec ON w.id = ec.work_id " +
								        "WHERE w.status = 'Active' " +
								        "AND (:workStatus IS NULL OR w.work_status = :workStatus) " +
								        "AND (:userId IS NULL OR w.user_id = :userId) " +
								        "AND (:agencyId IS NULL OR w.implementation_agency = :agencyId) " +
								        "AND (:workName IS NULL OR LOWER(w.work_name) LIKE LOWER(CONCAT('%', :workName, '%'))) " +
								        "GROUP BY w.work_no, w.work_name, w.implementation_agency, w.user_id, wt.pac, wp.total_expensess, w.work_status " +
								        "ORDER BY MAX(w.id) desc",
								        nativeQuery = true)
								List<Object[]> fetchWorkWithLatestExpensesFilters(
								        @Param("workStatus") Long workStatus,
								        @Param("userId") Long userId,
								        @Param("agencyId") Long agencyId,
								        @Param("workName") String workName);



								@Query(value = "SELECT user_id FROM t_work WHERE implementation_agency = :agencyId AND user_id IS NOT NULL", nativeQuery = true)
							    List<Long> findUserIdsByImplementationAgency(@Param("agencyId") Long agencyId);

								
								@Query("SELECT a.workNo FROM Work a " +
									       "WHERE a.status <> 'Deleted' AND LOWER(a.workNo) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
									       "ORDER BY a.workNo ASC")
									List<String> searchWorkNoSuggestions(@Param("keyword") String keyword);

								
								
								
								
								@Query(value =
										"SELECT " +
										" w.id, w.work_no, w.work_name, " +

										/* ===== Fund 1 ===== */
										" (SELECT fa.cost FROM t_work_financial_agency fa " +
										"   WHERE fa.work_id = w.id ORDER BY fa.id DESC LIMIT 1) AS Fund1, " +

										" (SELECT fa.expenditure FROM t_work_financial_agency fa " +
										"   WHERE fa.work_id = w.id ORDER BY fa.id DESC LIMIT 1) AS Fund1_Exp, " +

										/* ===== Fund 2 ===== */
										" (SELECT fa.cost FROM t_work_financial_agency fa " +
										"   WHERE fa.work_id = w.id ORDER BY fa.id DESC LIMIT 1 OFFSET 1) AS Fund2, " +

										" (SELECT fa.expenditure FROM t_work_financial_agency fa " +
										"   WHERE fa.work_id = w.id ORDER BY fa.id DESC LIMIT 1 OFFSET 1) AS Fund2_Exp, " +

										/* ===== Fund 3 ===== */
										" (SELECT fa.cost FROM t_work_financial_agency fa " +
										"   WHERE fa.work_id = w.id ORDER BY fa.id DESC LIMIT 1 OFFSET 2) AS Fund3, " +

										" (SELECT fa.expenditure FROM t_work_financial_agency fa " +
										"   WHERE fa.work_id = w.id ORDER BY fa.id DESC LIMIT 1 OFFSET 2) AS Fund3_Exp, " +

										/* ===== Last Photo ===== */
										" (SELECT dp.id FROM document_upload_workprogress_details dp " +
										"   WHERE dp.work_id = w.id ORDER BY dp.created_date DESC LIMIT 1) AS last_photo_id, " +

										/* ===== Second Last Photo ===== */
										" (SELECT dp.id FROM document_upload_workprogress_details dp " +
										"   WHERE dp.work_id = w.id ORDER BY dp.created_date DESC LIMIT 1 OFFSET 1) AS second_last_photo_id " +

										" FROM t_work w " +
										" WHERE w.id = :workId",
										nativeQuery = true)
										Object findWorkWithFundsAndPhotos(@Param("workId") Long workId);






								@Query("SELECT w FROM Work w WHERE w.id = :workId")
								List<Work> findAllByWorkId(@Param("workId") Long workId);

								List<Work> findByStatus(String statusActive);

								








								
								
}
