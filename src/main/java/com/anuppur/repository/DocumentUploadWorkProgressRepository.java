package com.anuppur.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anuppur.entity.DocumentUploadWorkProgress;



public interface DocumentUploadWorkProgressRepository extends JpaRepository<DocumentUploadWorkProgress, Long> {
	
	DocumentUploadWorkProgress findByDocumentName(String string);
	
	List<DocumentUploadWorkProgress> findByDocumentId(Long id);
	
	DocumentUploadWorkProgress findByWorkId(Long id, short s);

	List<DocumentUploadWorkProgress> findByWorkId(Long workId);

	long countByEnabled(short s);

	List<DocumentUploadWorkProgress> findByWorkIdAndCreatedDate(Long workId, Date date);

	@Query(value =
		"SELECT " +
		"    mia.id AS departmentId, " +
		"    mia.impl_agency_name AS departmentName, " +
		"    GROUP_CONCAT(DISTINCT w.work_name ORDER BY w.work_name SEPARATOR ', ') AS workName, " +
		"    COUNT(DISTINCT w.id) AS totalWorks, " +
		"    MAX(CONCAT(IFNULL(u.first_name,''), ' ', IFNULL(u.last_name,''))) AS areaOfficerName, " +
		"    DATEDIFF(CURRENT_DATE, MAX(DATE(p.created_date))) AS days " +
		"FROM t_work w " +
		"JOIN mst_implementation_agency mia ON mia.id = w.implementation_agency AND mia.enabled = 1 " +
		"JOIN document_upload_workprogress_details p ON p.work_id = w.id " +
		"    AND DATE(p.created_date) >= DATE_SUB(CURRENT_DATE, INTERVAL 15 DAY) " +
		"LEFT JOIN ( " +
		"    SELECT workid, MAX(userid) AS userid FROM area_officer_record GROUP BY workid " +
		") aor ON aor.workid = w.id " +
		"LEFT JOIN users u ON u.id = COALESCE(aor.userid, w.user_id) " +
		"WHERE w.status = 'Active' " +
		"GROUP BY mia.id, mia.impl_agency_name " +
		"ORDER BY mia.impl_agency_name ASC",
		nativeQuery = true)
	List<Object[]> fetchPhotoUpdateReportAll();

	@Query(value =
		"SELECT " +
		"    mia.id AS departmentId, " +
		"    mia.impl_agency_name AS departmentName, " +
		"    GROUP_CONCAT(DISTINCT w.work_name ORDER BY w.work_name SEPARATOR ', ') AS workName, " +
		"    COUNT(DISTINCT w.id) AS totalWorks, " +
		"    MAX(CONCAT(IFNULL(u.first_name,''), ' ', IFNULL(u.last_name,''))) AS areaOfficerName, " +
		"    DATEDIFF(CURRENT_DATE, MAX(DATE(p.created_date))) AS days " +
		"FROM t_work w " +
		"JOIN mst_implementation_agency mia ON mia.id = w.implementation_agency AND mia.enabled = 1 " +
		"JOIN document_upload_workprogress_details p ON p.work_id = w.id " +
		"    AND DATE(p.created_date) >= DATE_SUB(CURRENT_DATE, INTERVAL 15 DAY) " +
		"LEFT JOIN ( " +
		"    SELECT workid, MAX(userid) AS userid FROM area_officer_record GROUP BY workid " +
		") aor ON aor.workid = w.id " +
		"LEFT JOIN users u ON u.id = COALESCE(aor.userid, w.user_id) " +
		"WHERE w.status = 'Active' " +
		"  AND mia.id IN (:departmentIds) " +
		"GROUP BY mia.id, mia.impl_agency_name " +
		"ORDER BY mia.impl_agency_name ASC",
		nativeQuery = true)
	List<Object[]> fetchPhotoUpdateReportByDept(@Param("departmentIds") List<Long> departmentIds);

}
