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
@Table(name = "mst_district")
public class District implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long districtId;
	
	@Column(name = "district_code")
	private String districtCode;

	@Column(name = "district_name")
	private String districtName;

	@Column(name = "district_name_h")
	private String districtNameH;
	
	@Column(name = "enabled")
	private Short enabled;

	@JoinColumn(name = "division_id", referencedColumnName = "id")
	@ManyToOne
	private Division division;

	public District() {
	}

	public District(Long districtId) {
		this.districtId = districtId;
	}

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getDistrictNameH() {
		return districtNameH;
	}

	public void setDistrictNameH(String districtNameH) {
		this.districtNameH = districtNameH;
	}

	public Short getEnabled() {
		return enabled;
	}

	public void setEnabled(Short enabled) {
		this.enabled = enabled;
	}

	

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}
	
}