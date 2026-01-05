package com.anuppur.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "mst_gram_panchayat")
public class GramPanchayat {
	
	

	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long gramPanchayatId;
	
	@Column(name = "district_code")
	@JoinColumn(name = "district_code", referencedColumnName = "district_code")
	private String districtCode;
	
	@Column(name = "block_code")
	@JoinColumn(name = "block_code", referencedColumnName = "block_code")
	private String blockCode;
	
	@Column(name = "tehsil_code")
	private String tehsil_code;
	
	@Column(name = "gp_code")
	private String gramPanchayatCode;
	
	@Column(name = "gp_name" )
	private String gramPanchayatName;
	@Column(name = "gp_name_h")
	private String gramPanchayatNameH;
	
	
	@Column(name = "ENABLED")
	private Short enabled;

	public GramPanchayat() {
		
	}

	public GramPanchayat(Long gpId) {
		this.gramPanchayatId = gpId;
	}


	public Long getGramPanchayatId() {
		return gramPanchayatId;
	}


	public void setGramPanchayatId(Long gramPanchayatId) {
		this.gramPanchayatId = gramPanchayatId;
	}


	public String getDistrictCode() {
		return districtCode;
	}


	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}


	public String getBlockCode() {
		return blockCode;
	}


	public void setBlockCode(String blockCode) {
		this.blockCode = blockCode;
	}


	public String getTehsil_code() {
		return tehsil_code;
	}


	public void setTehsil_code(String tehsil_code) {
		this.tehsil_code = tehsil_code;
	}


	public String getGramPanchayatCode() {
		return gramPanchayatCode;
	}


	public void setGramPanchayatCode(String gramPanchayatCode) {
		this.gramPanchayatCode = gramPanchayatCode;
	}


	public String getGramPanchayatName() {
		return gramPanchayatName;
	}


	public void setGramPanchayatName(String gramPanchayatName) {
		this.gramPanchayatName = gramPanchayatName;
	}


	public String getGramPanchayatNameH() {
		return gramPanchayatNameH;
	}


	public void setGramPanchayatNameH(String gramPanchayatNameH) {
		this.gramPanchayatNameH = gramPanchayatNameH;
	}


	public Short getEnabled() {
		return enabled;
	}


	public void setEnabled(Short enabled) {
		this.enabled = enabled;
	}
}