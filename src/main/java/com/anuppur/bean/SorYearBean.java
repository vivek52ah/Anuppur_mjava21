package com.anuppur.bean;

public class SorYearBean {
	
	private Long sorYearId;

	private String sorYearName;

	private Short enabled;
	
	private Integer index;
    
	
	
	 private String createdDate;
	    
	 private String createdBy;
	 
	 

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Long getSorYearId() {
		return sorYearId;
	}

	public void setSorYearId(Long sorYearId) {
		this.sorYearId = sorYearId;
	}

	public String getSorYearName() {
		return sorYearName;
	}

	public void setSorYearName(String sorYearName) {
		this.sorYearName = sorYearName;
	}

	public Short getEnabled() {
		return enabled;
	}

	public void setEnabled(Short enabled) {
		this.enabled = enabled;
	}
	
	

}
