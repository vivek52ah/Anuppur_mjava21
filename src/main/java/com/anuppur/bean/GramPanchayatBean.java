package com.anuppur.bean;

public class GramPanchayatBean {

	private Long gramPanchayatId;

	private String districtCode;
	
	private String tehsilCode;

	private String blockCode;
	
	private String gramPanchayatCode;

	private String gramPanchayatName;

	private String gramPanchayatkNameH;
    private String districtName;
    
    private String blockName;
    
	private Short enabled;
    private Integer Index;
    
    
	public Integer getIndex() {
		return Index;
	}

	public void setIndex(Integer index) {
		Index = index;
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

	public String getTehsilCode() {
		return tehsilCode;
	}

	public void setTehsilCode(String tehsilCode) {
		this.tehsilCode = tehsilCode;
	}

	public String getBlockCode() {
		return blockCode;
	}

	public void setBlockCode(String blockCode) {
		this.blockCode = blockCode;
	}
	
	

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
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

	public String getGramPanchayatkNameH() {
		return gramPanchayatkNameH;
	}

	public void setGramPanchayatkNameH(String gramPanchayatkNameH) {
		this.gramPanchayatkNameH = gramPanchayatkNameH;
	}

	public Short getEnabled() {
		return enabled;
	}

	public void setEnabled(Short enabled) {
		this.enabled = enabled;
	}


}
