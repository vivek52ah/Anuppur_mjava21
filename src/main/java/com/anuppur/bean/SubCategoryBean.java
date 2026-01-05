package com.anuppur.bean;

import com.anuppur.entity.WorkCategory;
import com.anuppur.entity.WorkType;

public class SubCategoryBean {

	public SubCategoryBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	private Long categorySubTypeId;

	private String categorySubTypeNameE;

	private String categorySubTypeNameH;

	private short enabled;

	private WorkType workType;

	private WorkCategory workCategory;

	public SubCategoryBean(Long id) {
		this.categorySubTypeId = id;
	}

	public Long getCategorySubTypeId() {
		return categorySubTypeId;
	}

	public void setCategorySubTypeId(Long categorySubTypeId) {
		this.categorySubTypeId = categorySubTypeId;
	}

	public String getCategorySubTypeNameE() {
		return categorySubTypeNameE;
	}

	public void setCategorySubTypeNameE(String categorySubTypeNameE) {
		this.categorySubTypeNameE = categorySubTypeNameE;
	}

	public String getCategorySubTypeNameH() {
		return categorySubTypeNameH;
	}

	public void setCategorySubTypeNameH(String categorySubTypeNameH) {
		this.categorySubTypeNameH = categorySubTypeNameH;
	}

	public short getEnabled() {
		return enabled;
	}

	public void setEnabled(short enabled) {
		this.enabled = enabled;
	}

	public WorkType getWorkType() {
		return workType;
	}

	public void setWorkType(WorkType workType) {
		this.workType = workType;
	}

	public WorkCategory getWorkCategory() {
		return workCategory;
	}

	public void setWorkCategory(WorkCategory workCategory) {
		this.workCategory = workCategory;
	}

}
