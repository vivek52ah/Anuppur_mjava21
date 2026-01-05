/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anuppur.bean;

public class SchemeBean {

	private Long schemeId;

	private String schemeNameE;

	private String schemeNameH;

	private Short enabled;

	private String schemeCode;

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

	public SchemeBean() {
	}

	public Short getEnabled() {
		return enabled;
	}

	public void setEnabled(Short enabled) {
		this.enabled = enabled;
	}

	public String getSchemeNameE() {
		return schemeNameE;
	}

	public void setSchemeNameE(String schemeNameE) {
		this.schemeNameE = schemeNameE;
	}

	public String getSchemeNameH() {
		return schemeNameH;
	}

	public void setSchemeNameH(String schemeNameH) {
		this.schemeNameH = schemeNameH;
	}

	public String getSchemeCode() {
		return schemeCode;
	}

	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Long getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}
}
