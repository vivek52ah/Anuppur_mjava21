package com.anuppur.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anuppur.bean.WorkSubDelayResonBean;
import com.anuppur.entity.WorkSubDelayReson;
import com.anuppur.entity.WorkSubStatus;
@Repository
public interface WorkSubDelayResonRepository extends JpaRepository<WorkSubDelayReson,Long> {

    WorkSubDelayReson findBySubDelayReasonAndEnabled(String subDelayReason, short enabled);
    Page<WorkSubDelayReson> findBySubDelayReasonContainingAndEnabled(Pageable pageable, String searchParameter, short enabled);
    Page<WorkSubDelayReson> findByEnabled(short s,Pageable pageable);
    // WorkSubDelayReson findByWorkSubDelayResonAndEnabled(String subDelayReason, short enabled);
    List<WorkSubDelayReson> findByEnabledAndWorkSubStatusId(Short enabled, WorkSubStatus workSubStatusId);

    
}