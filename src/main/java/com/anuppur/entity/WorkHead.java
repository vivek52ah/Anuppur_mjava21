/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anuppur.entity;

import java.io.Serializable;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity 
@Table(name = "mst_work_head")
public class WorkHead implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    @Id
	@Basic(optional = false)
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "head_name")
	private String headName;
	
	@Column(name = "priority_type")
	private Integer priorityType;

	@Column(name = "enabled")
	private Short enabled;
	
	
	@Column(name = "created_date")
	private String  createdDate;
	
	@Column(name = "created_by")
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

	public WorkHead() {
	}

	public WorkHead(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHeadName() {
		return headName;
	}

	public void setHeadName(String headName) {
		this.headName = headName;
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
