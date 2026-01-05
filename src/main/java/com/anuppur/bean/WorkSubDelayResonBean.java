package com.anuppur.bean;

public class WorkSubDelayResonBean {



   private long workSubDelayReasonId;
   private String subDelayReason;
   private Short enabled;
	
	private Integer index;
    
    private long workSubStatusId;
	
	 private String createdDate;
	    
	 private String createdBy;


    public long getWorkSubDelayReasonId() {
        return this.workSubDelayReasonId;
    }

    public void setWorkSubDelayReasonId(long workSubDelayReasonId) {
        this.workSubDelayReasonId = workSubDelayReasonId;
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

    public Integer getIndex() {
        return this.index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public long getWorkSubStatusId() {
        return this.workSubStatusId;
    }

    public void setWorkSubStatusId(long workSubStatusId) {
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


}
