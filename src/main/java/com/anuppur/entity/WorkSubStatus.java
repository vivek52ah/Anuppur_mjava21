package com.anuppur.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="mst_work_sub_status")
public class WorkSubStatus implements Serializable {

	private static final long serialVersionUID = 1L;


	
	@Id
	 @Basic(optional = false)
	@Column(name="work_sub_status_id")
	private Long workSubStatusId;
	
	@Column(name="work_sub_status_name_e")
	private String workSubStatusNameE;
	
	@Column(name="work_sub_status_name_h")
	private String workSubStatusNameH;
	
	@Column(name="enabled")
	private short enabled;
	
	@Column(name="work_status_id")
	private Integer workStatusId;

	
	@Column(name = "work_sub_type_id")
	private Long workSubTypeId;
	
	
	
	
	public Long getWorkSubTypeId() {
		return workSubTypeId;
	}

	public void setWorkSubTypeId(Long workSubTypeId) {
		this.workSubTypeId = workSubTypeId;
	}

	public WorkSubStatus(Long workSubStatusId){
		this.workSubStatusId = workSubStatusId;
	}

	public WorkSubStatus() {
        //TODO Auto-generated constructor stub
    }

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

	

	
	public Integer getWorkStatusId() {
		return workStatusId;
	}

	public void setWorkStatusId(Integer workStatusId) {
		this.workStatusId = workStatusId;
	}

	public short getEnabled() {
		return enabled;
	}

	public void setEnabled(short enabled) {
		this.enabled = enabled;
	}

}
