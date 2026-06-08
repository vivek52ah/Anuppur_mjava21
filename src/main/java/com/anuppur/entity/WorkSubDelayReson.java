package com.anuppur.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="mst_work_sub_delay_reason")
public class WorkSubDelayReson implements Serializable{

    private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "work_sub_delay_reason_id")
   private Long workSubDelayReasonId;
   private String subDelayReason;
   private Short enabled;
	
    
    @JoinColumn(name = "work_sub_status_id", referencedColumnName = "work_sub_status_id")
    @ManyToOne
    private WorkSubStatus workSubStatusId;
	
	 private String createdDate;
	    
	 private String createdBy;


    public WorkSubDelayReson(Long workSubDelayReasonId) {
	}

	public WorkSubDelayReson() {
	}

	public String getSubDelayReason() {
        return this.subDelayReason;
    }

    public void setSubDelayReason(String subDelayReason) {
        this.subDelayReason = subDelayReason;
    }

    public Short getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Short enabled) {
        this.enabled = enabled;
    }



    public WorkSubStatus getWorkSubStatusId() {
        return this.workSubStatusId;
    }

    public void setWorkSubStatusId(WorkSubStatus workSubStatusId) {
        this.workSubStatusId = workSubStatusId;
    }

    public String getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    public Long getWorkSubDelayReasonId() {
    	return workSubDelayReasonId;
    }

    public void setWorkSubDelayReasonId(Long workSubDelayReasonId) {
    	this.workSubDelayReasonId = workSubDelayReasonId;
    }

     
}
