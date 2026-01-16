package com.anuppur.bean;

public class BlockBean {

	
	private Integer index;
	private Long blockId;

	private String districtCode;

	private String blockCode;

	private String blockName;

	private String blockNameH;

	private Short enabled;

	private DistrictBean district;

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

	public DistrictBean getDistrict() {
		return district;
	}

	public void setDistrict(DistrictBean district) {
		this.district = district;
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

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
    
}
