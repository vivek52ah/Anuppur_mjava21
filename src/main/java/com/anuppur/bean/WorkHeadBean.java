/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anuppur.bean;

public class WorkHeadBean {

	private Long workHeadId;

	private String workHeadName;

	private String workHeadNameH;

	private Integer priorityType;

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

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public WorkHeadBean() {

	}

	public Long getWorkHeadId() {
		return workHeadId;
	}

	public void setWorkHeadId(Long workHeadId) {
		this.workHeadId = workHeadId;
	}

	public String getWorkHeadName() {
		return workHeadName;
	}

	public void setWorkHeadName(String workHeadName) {
		this.workHeadName = workHeadName;
	}

	public String getWorkHeadNameH() {
		return workHeadNameH;
	}

	public void setWorkHeadNameH(String workHeadNameH) {
		this.workHeadNameH = workHeadNameH;
	}

	public Integer getPriorityType() {
		return priorityType;
	}

	public void setPriorityType(Integer priorityType) {
		this.priorityType = priorityType;
	}

	public Short getEnabled() {
		return enabled;
	}

	public void setEnabled(Short enabled) {
		this.enabled = enabled;
	}

}
