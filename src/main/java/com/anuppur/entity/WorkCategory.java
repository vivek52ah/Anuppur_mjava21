package com.anuppur.entity;

import java.io.Serializable;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name="mst_work_category")
public class WorkCategory  implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	   @Basic(optional = false)
	   
	    @Column(name = "work_category_id")
	    private Long workCategoryId;
	    
	    
	public Long getWorkCategoryId() {
			return workCategoryId;
		}

		public void setWorkCategoryId(Long workCategoryId) {
			this.workCategoryId = workCategoryId;
		}

	private short enabled;

	@Column(name="work_category_name_e")
	private String workCategoryNameE;

	@Column(name="work_category_name_h")
	private String workCategoryNameH;
	
	@JoinColumn(name = "district_id", referencedColumnName = "id")
	@ManyToOne
	private District district;
	
	@JoinColumn(name = "work_type_id", referencedColumnName = "work_type_id")
	@ManyToOne
	private WorkType workType;
	
	private Long ordering;

	public WorkCategory() {
	}
	
	public WorkCategory(Long id) {
		this.workCategoryId=id;
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

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public WorkType getWorkType() {
		return workType;
	}

	public void setWorkType(WorkType workType) {
		this.workType = workType;
	}

	public Long getOrdering() {
		return ordering;
	}

	public void setOrdering(Long ordering) {
		this.ordering = ordering;
	}
}