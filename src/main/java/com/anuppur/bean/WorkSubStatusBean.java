package com.anuppur.bean;

public class WorkSubStatusBean {

	private Long workSubStatusId;
	private String workSubStatusNameE;
	private String workSubStatusNameH;
	private Short enabled;
	private Integer workStatusId;

	public Long getWorkSubStatusId() {
		return workSubStatusId;
	}

	public void setWorkSubStatusId(Long workSubStatusId) {
		this.workSubStatusId = workSubStatusId;
	}

	public String getWorkSubStatusNameE() {
		return workSubStatusNameE;
	}

	public void setWorkSubStatusNameE(String workSubStatusNameE) {
		this.workSubStatusNameE = workSubStatusNameE;
	}

	public String getWorkSubStatusNameH() {
		return workSubStatusNameH;
	}

	public void setWorkSubStatusNameH(String workSubStatusNameH) {
		this.workSubStatusNameH = workSubStatusNameH;
	}

	public Short getEnabled() {
		return enabled;
	}

	public void setEnabled(Short enabled) {
		this.enabled = enabled;
	}

	public Integer getWorkStatusId() {
		return workStatusId;
	}

	public void setWorkStatusId(Integer workStatusId) {
		this.workStatusId = workStatusId;
	}

}
