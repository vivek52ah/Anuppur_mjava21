/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anuppur.bean;

public class WorkStatusBean {
		private Integer index;
	private Long workStatusId;

	private String workStatusNameE;

	private String workStatusNameH;

	private Short enabled;
    private String StatusDate;
	private Long flag;

	private String color;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Long getFlag() {
		return flag;
	}

	public void setFlag(Long flag) {
		this.flag = flag;
	}

	public WorkStatusBean() {
	}

	public String getWorkStatusNameE() {
		return workStatusNameE;
	}

	public void setWorkStatusNameE(String workStatusNameE) {
		this.workStatusNameE = workStatusNameE;
	}

	public String getWorkStatusNameH() {
		return workStatusNameH;
	}

	public void setWorkStatusNameH(String workStatusNameH) {
		this.workStatusNameH = workStatusNameH;
	}

	public Short getEnabled() {
		return enabled;
	}

	public void setEnabled(Short enabled) {
		this.enabled = enabled;
	}

	public Long getWorkStatusId() {
		return workStatusId;
	}

	public void setWorkStatusId(Long workStatusId) {
		this.workStatusId = workStatusId;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getStatusDate() {
		return StatusDate;
	}

	public void setStatusDate(String statusDate) {
		StatusDate = statusDate;
	}
	
	
	
}
