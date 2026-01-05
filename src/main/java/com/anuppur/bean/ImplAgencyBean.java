/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anuppur.bean;

public class ImplAgencyBean {

	private Integer index;

	private Long implementationAgencyId;

	private String implementationAgencyNameE;

	private String implementationAgencyNameH;

	private Short enabled;

	private Long implAgencyTypeId;

	private String implAgencyType;

	private String createdDate;
	
	private String createdBy;

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	

	public ImplAgencyBean() {
	}

	public Short getEnabled() {
		return enabled;
	}

	public void setEnabled(Short enabled) {
		this.enabled = enabled;
	}

	public Long getImplementationAgencyId() {
		return implementationAgencyId;
	}

	public void setImplementationAgencyId(Long implementationAgencyId) {
		this.implementationAgencyId = implementationAgencyId;
	}

	public String getImplementationAgencyNameE() {
		return implementationAgencyNameE;
	}

	public void setImplementationAgencyNameE(String implementationAgencyNameE) {
		this.implementationAgencyNameE = implementationAgencyNameE;
	}

	public String getImplementationAgencyNameH() {
		return implementationAgencyNameH;
	}

	public void setImplementationAgencyNameH(String implementationAgencyNameH) {
		this.implementationAgencyNameH = implementationAgencyNameH;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Long getImplAgencyTypeId() {
		return implAgencyTypeId;
	}

	public void setImplAgencyTypeId(Long implAgencyTypeId) {
		this.implAgencyTypeId = implAgencyTypeId;
	}

	public String getImplAgencyType() {
		return implAgencyType;
	}

	public void setImplAgencyType(String implAgencyType) {
		this.implAgencyType = implAgencyType;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

}
