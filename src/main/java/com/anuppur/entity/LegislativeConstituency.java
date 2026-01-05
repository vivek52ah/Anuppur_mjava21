/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.validation.constraints.NotNull;

@Entity 
@Table(name = "mst_legislative_constituency")
public class LegislativeConstituency implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    @Id
	@Basic(optional = false)
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "constituency_name")
	private String constituencyName;
	
	@Column(name = "constituency_code")
	private String constituencyCode;

	@Column(name = "enabled")
	private Short enabled;
	
	/*
	 * @Column(name = "DISTRICT_CODE") private String districtCode;
	 */
	
	
	@JoinColumn(name = "district_id", referencedColumnName = "id")
	@ManyToOne
	private District district;

	public LegislativeConstituency() {
	}

	public LegislativeConstituency(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConstituencyName() {
		return constituencyName;
	}

	public void setConstituencyName(String constituencyName) {
		this.constituencyName = constituencyName;
	}

	public Short getEnabled() {
		return enabled;
	}

	public void setEnabled(Short enabled) {
		this.enabled = enabled;
	}
	
	public String getConstituencyCode() {
		return constituencyCode;
	}

	public void setConstituencyCode(String constituencyCode) {
		this.constituencyCode = constituencyCode;
	}

	/*
	 * public String getDistrictCode() { return districtCode; }
	 * 
	 * public void setDistrictCode(String districtCode) { this.districtCode =
	 * districtCode; }
	 */
	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}


}
