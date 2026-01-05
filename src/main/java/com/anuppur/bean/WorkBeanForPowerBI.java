package com.anuppur.bean;

import java.math.BigDecimal;
import java.util.Date;

public class WorkBeanForPowerBI {

	private Long id;
	private Long workId;

	private String workNo;
	private String workName;
	
	private Long financialYear;
	
	private String workType;
	private String scheme;
	private String workPriority;
	private String Head;
	private BigDecimal estimatedAmt;
	private BigDecimal amtReleasedTillDate;
	private Long implementationAgency;
	private Long divisionId;
	private String divisionName;
	private String districtCode;
	private String districtName;
	private String blockCode;
	private String blockName;
	private String legislativeConstituencyCode;
	private String legislativeConstituencyName;
	private Long worTypeId;
	private Long workCategoryId;
	private Long workSubtypeId;
	private String multifundedStatus;
	private BigDecimal fundByState;
	private BigDecimal fundByNhm;
	private BigDecimal fundByEcrp2;
	private BigDecimal fundByOthers;
	private Long workStatus;
	private Long categorySubtypeId;
	 private String createdBy;
	 private Date createdDate;
	 private Date modifiedDate;
	 private String modifiedBy;
	 private Long workRequestStatusId;
//	Deleted_status 
	 private String schemeState;
	 private String schemeNhm;
	 private String schemeEcrp2;
	 private String schemeOthers;
	 private String headState;
	 private String headNhm;
	 private String headEcrp2;
	 private String headOthers;
	 private String drawCreatedBy;
	 private String secureAmtStatus;
	 private String startDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getWorkId() {
		return workId;
	}
	public void setWorkId(Long workId) {
		this.workId = workId;
	}
	public String getWorkName() {
		return workName;
	}
	public void setWorkName(String workName) {
		this.workName = workName;
	}
	public Long getFinancialYear() {
		return financialYear;
	}
	public void setFinancialYear(Long financialYear) {
		this.financialYear = financialYear;
	}
	public String getWorkType() {
		return workType;
	}
	public void setWorkType(String workType) {
		this.workType = workType;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public String getWorkPriority() {
		return workPriority;
	}
	public void setWorkPriority(String workPriority) {
		this.workPriority = workPriority;
	}
	public String getHead() {
		return Head;
	}
	public void setHead(String head) {
		Head = head;
	}
	public BigDecimal getEstimatedAmt() {
		return estimatedAmt;
	}
	public void setEstimatedAmt(BigDecimal estimatedAmt) {
		this.estimatedAmt = estimatedAmt;
	}
	public BigDecimal getAmtReleasedTillDate() {
		return amtReleasedTillDate;
	}
	public void setAmtReleasedTillDate(BigDecimal amtReleasedTillDate) {
		this.amtReleasedTillDate = amtReleasedTillDate;
	}
	public Long getImplementationAgency() {
		return implementationAgency;
	}
	public void setImplementationAgency(Long implementationAgency) {
		this.implementationAgency = implementationAgency;
	}
	public Long getDivisionId() {
		return divisionId;
	}
	public void setDivisionId(Long divisionId) {
		this.divisionId = divisionId;
	}
	public String getDivisionName() {
		return divisionName;
	}
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getBlockCode() {
		return blockCode;
	}
	public void setBlockCode(String blockCode) {
		this.blockCode = blockCode;
	}
	public String getBlockName() {
		return blockName;
	}
	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}
	public String getLegislativeConstituencyCode() {
		return legislativeConstituencyCode;
	}
	public void setLegislativeConstituencyCode(String legislativeConstituencyCode) {
		this.legislativeConstituencyCode = legislativeConstituencyCode;
	}
	public String getLegislativeConstituencyName() {
		return legislativeConstituencyName;
	}
	public void setLegislativeConstituencyName(String legislativeConstituencyName) {
		this.legislativeConstituencyName = legislativeConstituencyName;
	}
	public Long getWorTypeId() {
		return worTypeId;
	}
	public void setWorTypeId(Long worTypeId) {
		this.worTypeId = worTypeId;
	}
	public Long getWorkCategoryId() {
		return workCategoryId;
	}
	public void setWorkCategoryId(Long workCategoryId) {
		this.workCategoryId = workCategoryId;
	}
	public Long getWorkSubtypeId() {
		return workSubtypeId;
	}
	public void setWorkSubtypeId(Long workSubtypeId) {
		this.workSubtypeId = workSubtypeId;
	}
	public String getMultifundedStatus() {
		return multifundedStatus;
	}
	public void setMultifundedStatus(String multifundedStatus) {
		this.multifundedStatus = multifundedStatus;
	}
	public BigDecimal getFundByState() {
		return fundByState;
	}
	public void setFundByState(BigDecimal fundByState) {
		this.fundByState = fundByState;
	}
	public BigDecimal getFundByNhm() {
		return fundByNhm;
	}
	public void setFundByNhm(BigDecimal fundByNhm) {
		this.fundByNhm = fundByNhm;
	}
	public BigDecimal getFundByEcrp2() {
		return fundByEcrp2;
	}
	public void setFundByEcrp2(BigDecimal fundByEcrp2) {
		this.fundByEcrp2 = fundByEcrp2;
	}
	public BigDecimal getFundByOthers() {
		return fundByOthers;
	}
	public void setFundByOthers(BigDecimal fundByOthers) {
		this.fundByOthers = fundByOthers;
	}
	public Long getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(Long workStatus) {
		this.workStatus = workStatus;
	}
	public Long getCategorySubtypeId() {
		return categorySubtypeId;
	}
	public void setCategorySubtypeId(Long categorySubtypeId) {
		this.categorySubtypeId = categorySubtypeId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Long getWorkRequestStatusId() {
		return workRequestStatusId;
	}
	public void setWorkRequestStatusId(Long workRequestStatusId) {
		this.workRequestStatusId = workRequestStatusId;
	}
	public String getSchemeState() {
		return schemeState;
	}
	public void setSchemeState(String schemeState) {
		this.schemeState = schemeState;
	}
	public String getSchemeNhm() {
		return schemeNhm;
	}
	public void setSchemeNhm(String schemeNhm) {
		this.schemeNhm = schemeNhm;
	}
	public String getSchemeEcrp2() {
		return schemeEcrp2;
	}
	public void setSchemeEcrp2(String schemeEcrp2) {
		this.schemeEcrp2 = schemeEcrp2;
	}
	public String getSchemeOthers() {
		return schemeOthers;
	}
	public void setSchemeOthers(String schemeOthers) {
		this.schemeOthers = schemeOthers;
	}
	public String getHeadState() {
		return headState;
	}
	public void setHeadState(String headState) {
		this.headState = headState;
	}
	public String getHeadNhm() {
		return headNhm;
	}
	public void setHeadNhm(String headNhm) {
		this.headNhm = headNhm;
	}
	public String getHeadEcrp2() {
		return headEcrp2;
	}
	public void setHeadEcrp2(String headEcrp2) {
		this.headEcrp2 = headEcrp2;
	}
	public String getHeadOthers() {
		return headOthers;
	}
	public void setHeadOthers(String headOthers) {
		this.headOthers = headOthers;
	}
	public String getDrawCreatedBy() {
		return drawCreatedBy;
	}
	public void setDrawCreatedBy(String drawCreatedBy) {
		this.drawCreatedBy = drawCreatedBy;
	}
	public String getSecureAmtStatus() {
		return secureAmtStatus;
	}
	public void setSecureAmtStatus(String secureAmtStatus) {
		this.secureAmtStatus = secureAmtStatus;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getWorkNo() {
		return workNo;
	}
	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}
	 
	 
	 


}
