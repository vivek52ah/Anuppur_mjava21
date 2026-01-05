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
@Table(name = "mst_block")
public class Block implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long blockId;

	
	/*
	 * @Column(name = "DISTRICT_CODE") private String districtCode;
	 */
	 
	
	@Column(name = "block_code")
	private String blockCode;
	
	
	@Column(name = "block_name")
	private String blockName;

	
	@Column(name = "block_name_h")
	private String blockNameH;
	
	@Column(name = "enabled")
	private Short enabled;


	  @JoinColumn(name = "district_id", referencedColumnName = "id")
	  
	  @ManyToOne private District district;
	 
	
	  public District getDistrict() { return district; }
	 
	  public void setDistrict(District district) { this.district = district; }
	 

	public Block() {
	}

	public Block(Long blockId) {
		this.blockId = blockId;
	}

	public Long getBlockId() {
		return blockId;
	}

	public void setBlockId(Long blockId) {
		this.blockId = blockId;
	}

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public String getBlockNameH() {
		return blockNameH;
	}

	public void setBlockNameH(String blockNameH) {
		this.blockNameH = blockNameH;
	}

	public Short getEnabled() {
		return enabled;
	}

	public void setEnabled(Short enabled) {
		this.enabled = enabled;
	}

	
	/*
	 * public String getDistrictCode() { return districtCode; }
	 * 
	 * public void setDistrictCode(String districtCode) { this.districtCode =
	 * districtCode; }
	 */
	 
	public String getBlockCode() {
		return blockCode;
	}

	public void setBlockCode(String blockCode) {
		this.blockCode = blockCode;
	}

}