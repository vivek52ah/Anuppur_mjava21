package com.anuppur.bean;

public class HeadBean {

	private Long headId;

	private String headNameE;

	private String headNameH;

	private Short enabled;

	private Integer index;
	
	
    private String createdDate;
    
    private String createdBy;
	
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

	


	public HeadBean() {
	}

	public Long getHeadId() {
		return headId;
	}

	public void setHeadId(Long headId) {
		this.headId = headId;
	}

	public String getHeadNameE() {
		return headNameE;
	}

	public void setHeadNameE(String headNameE) {
		this.headNameE = headNameE;
	}

	public String getHeadNameH() {
		return headNameH;
	}

	public void setHeadNameH(String headNameH) {
		this.headNameH = headNameH;
	}

	public Short getEnabled() {
		return enabled;
	}

	public void setEnabled(Short enabled) {
		this.enabled = enabled;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

}
