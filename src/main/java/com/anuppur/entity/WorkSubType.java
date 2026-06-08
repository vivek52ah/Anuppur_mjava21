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
@Table(name = "mst_work_subtype")
public class WorkSubType implements Serializable {
    private static final long serialVersionUID = 1L;
	
	@Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	   @Basic(optional = false)
    @Column(name = "work_subtype_id")
    private Long workSubtypeId;
    
    @Column(name = "work_subtype_name_e")
    private String workSubTypeNameE;
    
    @Column(name = "work_subtype_name_h")
    private String workSubTypeNameH;
    
    @Column(name = "enabled")
    private Short enabled;

	public Long getWorkSubtypeId() {
		return workSubtypeId;
	}

	public void setWorkSubtypeId(Long workSubtypeId) {
		this.workSubtypeId = workSubtypeId;
	}

	public String getWorkSubTypeNameE() {
		return workSubTypeNameE;
	}

	public void setWorkSubTypeNameE(String workSubTypeNameE) {
		this.workSubTypeNameE = workSubTypeNameE;
	}

	public String getWorkSubTypeNameH() {
		return workSubTypeNameH;
	}

	public void setWorkSubTypeNameH(String workSubTypeNameH) {
		this.workSubTypeNameH = workSubTypeNameH;
	}

	public Short getEnabled() {
		return enabled;
	}

	public void setEnabled(Short enabled) {
		this.enabled = enabled;
	}


}
