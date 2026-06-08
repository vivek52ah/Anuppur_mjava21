package com.anuppur.entity;

import java.io.Serializable;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;


@Entity  
@Table(name="mst_year_status")
public class YearStatus  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@NotNull
	@Column(name = "id")
	private Long yearId;

	private Long status;
	
	 @Column(name = "enabled")
	private Short enabled;
	
	
	public Short getEnabled() {
		return enabled;
	}


	public void setEnabled(Short enabled) {
		this.enabled = enabled;
	}


	private Long year;


	public Long getYearId() {
		return yearId;
	}


	public void setYearId(Long yearId) {
		this.yearId = yearId;
	}


	public Long getStatus() {
		return status;
	}


	public void setStatus(Long status) {
		this.status = status;
	}


	public Long getYear() {
		return year;
	}


	public void setYear(Long year) {
		this.year = year;
	}
	


}
