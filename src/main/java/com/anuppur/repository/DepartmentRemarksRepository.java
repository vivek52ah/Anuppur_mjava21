package com.anuppur.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.anuppur.entity.DepartmentRemarks;

@Repository
public interface DepartmentRemarksRepository extends JpaRepository<DepartmentRemarks, Long>{

	List<DepartmentRemarks> findByworkIdAndEnabled(Long long1, short s);

	List<DepartmentRemarks> findByWorkId(Long id);

	@Query(value = "SELECT * FROM department_remarks WHERE document_id = :documentId AND enabled = 1 LIMIT 1", nativeQuery = true)
	Optional<DepartmentRemarks> findEnabledByDocumentId(@Param("documentId") Long documentId);
	
	@Query(value = "SELECT created_time FROM department_remarks WHERE id = :id", nativeQuery = true)
	String findCreatedDateByWorkId(@Param("id") Long id);

	// Department-wise report: with agency and financial year filter
	@Query(value =
		"SELECT mia.id AS implementationAgencyId, mia.impl_agency_name AS implementationAgencyName, " +
		"fy.id AS financialYearId, fy.financial_year AS financialYearName, " +
		"COUNT(DISTINCT w.id) AS totalWorks, " +
		"COUNT(DISTINCT CASE WHEN w.work_status = 11 THEN w.id END) AS completedWorks, " +
		"COUNT(DISTINCT CASE WHEN w.work_status = 10 THEN w.id END) AS ongoingWorks, " +
		"COUNT(DISTINCT CASE WHEN w.work_status = 9 THEN w.id END) AS notStartedWorks " +
		"FROM t_work w " +
		"JOIN mst_financial_year fy ON fy.id = w.financial_year " +
		"JOIN mst_implementation_agency mia ON mia.id = w.implementation_agency AND mia.enabled = 1 " +
		"WHERE w.status = 'Active' " +
		"AND mia.id IN (:agencyIds) " +
		"AND fy.id IN (:financialYearIds) " +
		"GROUP BY mia.id, mia.impl_agency_name, fy.id, fy.financial_year " +
		"ORDER BY mia.impl_agency_name, fy.financial_year DESC",
		nativeQuery = true)
	List<Object[]> fetchDepartmentWiseReportByDeptAndFy(
		@Param("agencyIds") List<Long> agencyIds,
		@Param("financialYearIds") List<Long> financialYearIds);

	// Department-wise report: with financial year filter only (all agencies)
	@Query(value =
		"SELECT mia.id AS implementationAgencyId, mia.impl_agency_name AS implementationAgencyName, " +
		"fy.id AS financialYearId, fy.financial_year AS financialYearName, " +
		"COUNT(DISTINCT w.id) AS totalWorks, " +
		"COUNT(DISTINCT CASE WHEN w.work_status = 11 THEN w.id END) AS completedWorks, " +
		"COUNT(DISTINCT CASE WHEN w.work_status = 10 THEN w.id END) AS ongoingWorks, " +
		"COUNT(DISTINCT CASE WHEN w.work_status = 9 THEN w.id END) AS notStartedWorks " +
		"FROM t_work w " +
		"JOIN mst_financial_year fy ON fy.id = w.financial_year " +
		"JOIN mst_implementation_agency mia ON mia.id = w.implementation_agency AND mia.enabled = 1 " +
		"WHERE w.status = 'Active' " +
		"AND fy.id IN (:financialYearIds) " +
		"GROUP BY mia.id, mia.impl_agency_name, fy.id, fy.financial_year " +
		"ORDER BY mia.impl_agency_name, fy.financial_year DESC",
		nativeQuery = true)
	List<Object[]> fetchDepartmentWiseReportByFy(
		@Param("financialYearIds") List<Long> financialYearIds);

	// Department-wise report: with agency filter only (all financial years)
	@Query(value =
		"SELECT mia.id AS implementationAgencyId, mia.impl_agency_name AS implementationAgencyName, " +
		"fy.id AS financialYearId, fy.financial_year AS financialYearName, " +
		"COUNT(DISTINCT w.id) AS totalWorks, " +
		"COUNT(DISTINCT CASE WHEN w.work_status = 11 THEN w.id END) AS completedWorks, " +
		"COUNT(DISTINCT CASE WHEN w.work_status = 10 THEN w.id END) AS ongoingWorks, " +
		"COUNT(DISTINCT CASE WHEN w.work_status = 9 THEN w.id END) AS notStartedWorks " +
		"FROM t_work w " +
		"JOIN mst_financial_year fy ON fy.id = w.financial_year " +
		"JOIN mst_implementation_agency mia ON mia.id = w.implementation_agency AND mia.enabled = 1 " +
		"WHERE w.status = 'Active' " +
		"AND mia.id IN (:agencyIds) " +
		"GROUP BY mia.id, mia.impl_agency_name, fy.id, fy.financial_year " +
		"ORDER BY mia.impl_agency_name, fy.financial_year DESC",
		nativeQuery = true)
	List<Object[]> fetchDepartmentWiseReportByDept(
		@Param("agencyIds") List<Long> agencyIds);

	// Department-wise report: no filters (all agencies, all financial years)
	@Query(value =
		"SELECT mia.id AS implementationAgencyId, mia.impl_agency_name AS implementationAgencyName, " +
		"fy.id AS financialYearId, fy.financial_year AS financialYearName, " +
		"COUNT(DISTINCT w.id) AS totalWorks, " +
		"COUNT(DISTINCT CASE WHEN w.work_status = 11 THEN w.id END) AS completedWorks, " +
		"COUNT(DISTINCT CASE WHEN w.work_status = 10 THEN w.id END) AS ongoingWorks, " +
		"COUNT(DISTINCT CASE WHEN w.work_status = 9 THEN w.id END) AS notStartedWorks " +
		"FROM t_work w " +
		"JOIN mst_financial_year fy ON fy.id = w.financial_year " +
		"JOIN mst_implementation_agency mia ON mia.id = w.implementation_agency AND mia.enabled = 1 " +
		"WHERE w.status = 'Active' " +
		"GROUP BY mia.id, mia.impl_agency_name, fy.id, fy.financial_year " +
		"ORDER BY mia.impl_agency_name, fy.financial_year DESC",
		nativeQuery = true)
	List<Object[]> fetchDepartmentWiseReportAll();

	// DM Remark-wise report: no filters
	@Query(value =
		"SELECT dm.id AS dm_id, dm.name AS dm_name, " +
		"GROUP_CONCAT(DISTINCT mia.impl_agency_name ORDER BY mia.impl_agency_name SEPARATOR ', ') AS dept_names, " +
		"COUNT(DISTINCT dr.work_id) AS total_works " +
		"FROM department_remarks dr " +
		"JOIN mst_department dm ON dm.id = dr.department_master_id " +
		"JOIN t_work w ON w.id = dr.work_id AND w.status = 'Active' " +
		"JOIN mst_implementation_agency mia ON mia.id = w.implementation_agency " +
		"WHERE dr.enabled = 1 " +
		"GROUP BY dr.department_master_id, dm.name " +
		"ORDER BY dm.name",
		nativeQuery = true)
	List<Object[]> fetchDmRemarkWiseReportAll();

	// DM Remark-wise report: Issue Type (deptMasterIds) filter only
	@Query(value =
		"SELECT dm.id AS dm_id, dm.name AS dm_name, " +
		"GROUP_CONCAT(DISTINCT mia.impl_agency_name ORDER BY mia.impl_agency_name SEPARATOR ', ') AS dept_names, " +
		"COUNT(DISTINCT dr.work_id) AS total_works " +
		"FROM department_remarks dr " +
		"JOIN mst_department dm ON dm.id = dr.department_master_id " +
		"JOIN t_work w ON w.id = dr.work_id AND w.status = 'Active' " +
		"JOIN mst_implementation_agency mia ON mia.id = w.implementation_agency " +
		"WHERE dr.enabled = 1 " +
		"AND dr.department_master_id IN (:deptMasterIds) " +
		"GROUP BY dr.department_master_id, dm.name " +
		"ORDER BY dm.name",
		nativeQuery = true)
	List<Object[]> fetchDmRemarkWiseReportByDept(@Param("deptMasterIds") List<Long> deptMasterIds);

	// DM Remark-wise report: Department Name (implAgencyIds) filter only
	@Query(value =
		"SELECT dm.id AS dm_id, dm.name AS dm_name, " +
		"GROUP_CONCAT(DISTINCT mia.impl_agency_name ORDER BY mia.impl_agency_name SEPARATOR ', ') AS dept_names, " +
		"COUNT(DISTINCT dr.work_id) AS total_works " +
		"FROM department_remarks dr " +
		"JOIN mst_department dm ON dm.id = dr.department_master_id " +
		"JOIN t_work w ON w.id = dr.work_id AND w.status = 'Active' " +
		"JOIN mst_implementation_agency mia ON mia.id = w.implementation_agency " +
		"WHERE dr.enabled = 1 " +
		"AND mia.id IN (:implAgencyIds) " +
		"GROUP BY dr.department_master_id, dm.name " +
		"ORDER BY dm.name",
		nativeQuery = true)
	List<Object[]> fetchDmRemarkWiseReportByAgency(@Param("implAgencyIds") List<Long> implAgencyIds);

	// DM Remark-wise report: both Issue Type and Department Name filters
	@Query(value =
		"SELECT dm.id AS dm_id, dm.name AS dm_name, " +
		"GROUP_CONCAT(DISTINCT mia.impl_agency_name ORDER BY mia.impl_agency_name SEPARATOR ', ') AS dept_names, " +
		"COUNT(DISTINCT dr.work_id) AS total_works " +
		"FROM department_remarks dr " +
		"JOIN mst_department dm ON dm.id = dr.department_master_id " +
		"JOIN t_work w ON w.id = dr.work_id AND w.status = 'Active' " +
		"JOIN mst_implementation_agency mia ON mia.id = w.implementation_agency " +
		"WHERE dr.enabled = 1 " +
		"AND dr.department_master_id IN (:deptMasterIds) " +
		"AND mia.id IN (:implAgencyIds) " +
		"GROUP BY dr.department_master_id, dm.name " +
		"ORDER BY dm.name",
		nativeQuery = true)
	List<Object[]> fetchDmRemarkWiseReportByDeptAndAgency(
		@Param("deptMasterIds") List<Long> deptMasterIds,
		@Param("implAgencyIds") List<Long> implAgencyIds);

}
