package com.anuppur.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


/**
 * The persistent class for the mst_work_status database table.
 * 
 */
@Entity
@Table(name="mst_work_status")
public class WorkStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private short enabled;

	@Column(name="work_status_name_e")
	private String workStatusNameE;

	@Column(name="work_status_name_h")
	private String workStatusNameH;
	
	@JoinColumn(name = "work_type_id", referencedColumnName = "work_type_id")
	@ManyToOne
	private WorkType workType;
	
	@JoinColumn(name = "scheme_id", referencedColumnName = "id")
	@ManyToOne
	private Schemes schemes;
	
	@Column(name="color")
    private String color;



	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	private Long flag;

	public Long getFlag() {
		return flag;
	}

	public void setFlag(Long flag) {
		this.flag = flag;
	}

	public WorkStatus() {
	}
	
	public WorkStatus(Long id) {
		this.id=id;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public short getEnabled() {
		return enabled;
	}

	public void setEnabled(short enabled) {
		this.enabled = enabled;
	}

	public String getWorkStatusNameE() {
		return this.workStatusNameE;
	}

	public void setWorkStatusNameE(String workStatusNameE) {
		this.workStatusNameE = workStatusNameE;
	}

	public String getWorkStatusNameH() {
		return this.workStatusNameH;
	}

	public void setWorkStatusNameH(String workStatusNameH) {
		this.workStatusNameH = workStatusNameH;
	}

	public WorkType getWorkType() {
		return workType;
	}

	public void setWorkType(WorkType workType) {
		this.workType = workType;
	}

	public Schemes getSchemes() {
		return schemes;
	}

	public void setSchemes(Schemes schemes) {
		this.schemes = schemes;
	}

}