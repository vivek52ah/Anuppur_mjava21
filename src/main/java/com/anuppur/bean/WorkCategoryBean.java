package com.anuppur.bean;

public class WorkCategoryBean {

	private Long workCategoryId;

	private short enabled;

	private String workCategoryNameE;

	private String workCategoryNameH;

	private Integer index;

	private DistrictBean districtBean;

	private WorkTypeBean workTypeBean;

	public WorkCategoryBean() {
	}

	public WorkCategoryBean(Long id) {
		this.workCategoryId = id;
	}

	public short getEnabled() {
		return enabled;
	}

	public void setEnabled(short enabled) {
		this.enabled = enabled;
	}

	public String getWorkCategoryNameE() {
		return workCategoryNameE;
	}

	public void setWorkCategoryNameE(String workCategoryNameE) {
		this.workCategoryNameE = workCategoryNameE;
	}

	public String getWorkCategoryNameH() {
		return workCategoryNameH;
	}

	public void setWorkCategoryNameH(String workCategoryNameH) {
		this.workCategoryNameH = workCategoryNameH;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public DistrictBean getDistrictBean() {
		return districtBean;
	}

	public void setDistrictBean(DistrictBean districtBean) {
		this.districtBean = districtBean;
	}

	public WorkTypeBean getWorkTypeBean() {
		return workTypeBean;
	}

	public void setWorkTypeBean(WorkTypeBean workTypeBean) {
		this.workTypeBean = workTypeBean;
	}

	public Long getWorkCategoryId() {
		return workCategoryId;
	}

	public void setWorkCategoryId(Long workCategoryId) {
		this.workCategoryId = workCategoryId;
	}
}