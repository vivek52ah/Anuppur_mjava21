package com.anuppur.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.anuppur.entity.WorkTender;

@Repository
public interface WorkTenderRepository extends JpaRepository<WorkTender, Long> {

    @Query("SELECT w FROM WorkTender w WHERE w.work.id = :id")
    WorkTender findByWorkId(@Param("id") Long id);

    @Query(value = """
            SELECT *
            FROM t_work_tender
            WHERE sor_year LIKE CONCAT('%', :sorYear, '%')
            AND status != :statusDeleted
            LIMIT 1
            """, nativeQuery = true)
    WorkTender findBySorYearContainingAndStatusNotIn(
            @Param("sorYear") String sorYear,
            @Param("statusDeleted") String statusDeleted);

    @Query(value = """
            SELECT COUNT(*)
            FROM t_work_tender wt
            INNER JOIN t_work w ON wt.work_id = w.id
            WHERE wt.status = 'Active'
            AND wt.work_status = 'Tender Called date'
            AND (w.scheme IS NOT NULL AND w.scheme != '')
            AND (w.financial_year IS NOT NULL AND w.financial_year != '')
            AND (w.work_head IS NOT NULL AND w.work_head != '')
            """, nativeQuery = true)
    BigDecimal counTenderCalledCount();

    @Query(value = """
            SELECT COUNT(*)
            FROM t_work_tender wt
            INNER JOIN t_work w ON wt.work_id = w.id
            WHERE wt.status = 'Active'
            AND wt.work_status = 'Tender Received'
            AND (w.scheme IS NOT NULL AND w.scheme != '')
            AND (w.financial_year IS NOT NULL AND w.financial_year != '')
            AND (w.work_head IS NOT NULL AND w.work_head != '')
            """, nativeQuery = true)
    BigDecimal counTenderRcvCount();

    @Query(value = """
            SELECT COUNT(*)
            FROM t_work_tender wt
            INNER JOIN t_work w ON wt.work_id = w.id
            WHERE wt.status = 'Active'
            AND wt.work_status = 'Tender Approval in Process'
            AND (w.scheme IS NOT NULL AND w.scheme != '')
            AND (w.financial_year IS NOT NULL AND w.financial_year != '')
            AND (w.work_head IS NOT NULL AND w.work_head != '')
            """, nativeQuery = true)
    BigDecimal counTenderApprovalCount();

    @Query(value = """
            SELECT COUNT(*)
            FROM t_work_tender wt
            INNER JOIN t_work w ON wt.work_id = w.id
            WHERE wt.status = 'Active'
            AND wt.work_status = 'Re-Tender'
            AND (w.scheme IS NOT NULL AND w.scheme != '')
            AND (w.financial_year IS NOT NULL AND w.financial_year != '')
            AND (w.work_head IS NOT NULL AND w.work_head != '')
            """, nativeQuery = true)
    BigDecimal counReTenderCount();

    @Query(value = """
            SELECT COUNT(*)
            FROM t_work_tender wt
            INNER JOIN t_work w ON wt.work_id = w.id
            WHERE wt.status = 'Active'
            AND wt.work_status = 'LoA Issued'
            AND (w.scheme IS NOT NULL AND w.scheme != '')
            AND (w.financial_year IS NOT NULL AND w.financial_year != '')
            AND (w.work_head IS NOT NULL AND w.work_head != '')
            """, nativeQuery = true)
    BigDecimal counLoACount();

    @Query(value = """
            SELECT COUNT(*)
            FROM t_work_tender wt
            INNER JOIN t_work w ON wt.work_id = w.id
            WHERE wt.status = 'Active'
            AND wt.work_status = 'Work Order Issued'
            AND (w.scheme IS NOT NULL AND w.scheme != '')
            AND (w.financial_year IS NOT NULL AND w.financial_year != '')
            AND (w.work_head IS NOT NULL AND w.work_head != '')
            """, nativeQuery = true)
    BigDecimal counWOIssuedCount();

    @Query(value = """
            SELECT *
            FROM t_work_tender
            WHERE STR_TO_DATE(end_date, '%d/%m/%Y')
            BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 30 DAY)
            """, nativeQuery = true)
    List<WorkTender> findUpcomingWorkTenders();

    // aman 17-07-2024

    @Query(value = """
            SELECT twt.work_order_date
            FROM t_work_tender twt
            WHERE twt.work_id = :id
            """, nativeQuery = true)
    String getWorkOrderDate(@Param("id") Long id);

    @Query(value = """
            SELECT twt.contract_tenure
            FROM t_work_tender twt
            WHERE twt.work_id = :id
            """, nativeQuery = true)
    BigDecimal getTimeLineInMonths(@Param("id") Long id);

    @Query("SELECT w FROM WorkTender w WHERE w.status = 'Active'")
    List<WorkTender> getAllWork();

    @Query("SELECT w FROM WorkTender w WHERE w.work.id = :id")
    WorkTender getWorkTenderData(@Param("id") Long id);

    // FIXED METHOD
    WorkTender findByWorkOrderDate(String workOrderDate);

}