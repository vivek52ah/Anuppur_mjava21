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
@Table(name = "mst_work_type")
public class WorkType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "work_type_id")
    private Long workTypeId;

    @Column(name = "work_type_name_e")
    private String workTypeNameE;

    @Column(name = "work_type_name_h")
    private String workTypeNameH;
    
    @Column(name = "enabled")
    private Short enabled;
    
    public WorkType() {
    }

    public WorkType(Long workTypeId) {
        this.workTypeId = workTypeId;
    }

	public Long getWorkTypeId() {
		return workTypeId;
	}

	public void setWorkTypeId(Long workTypeId) {
		this.workTypeId = workTypeId;
	}

	public String getWorkTypeNameE() {
		return workTypeNameE;
	}

	public void setWorkTypeNameE(String workTypeNameE) {
		this.workTypeNameE = workTypeNameE;
	}

	public String getWorkTypeNameH() {
		return workTypeNameH;
	}

	public void setWorkTypeNameH(String workTypeNameH) {
		this.workTypeNameH = workTypeNameH;
	}

	public Short getEnabled() {
		return enabled;
	}

	public void setEnabled(Short enabled) {
		this.enabled = enabled;
	}
    
    
    
}
