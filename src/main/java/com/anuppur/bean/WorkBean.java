package com.anuppur.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

public class WorkBean {

	private Integer index;

	private Long id;
	private Long workId;

	private String workName;

	private String fileStatus;

	private Long drawingId;

	private String drawCreatedBy;

	private String secureAmtStatus;

	private String startDate;
    private Date dmAproveRejectDate;
       
    private Long userAssignee;
    
    private String userAssigneeName;
    
    
    private Boolean isGeoTagged;
    
    
       
	public Boolean getIsGeoTagged() {
		return isGeoTagged;
	}

	public void setIsGeoTagged(Boolean isGeoTagged) {
		this.isGeoTagged = isGeoTagged;
	}

	public String getUserAssigneeName() {
		return userAssigneeName;
	}

	public void setUserAssigneeName(String userAssigneeName) {
		this.userAssigneeName = userAssigneeName;
	}

	public Long getUserAssignee() {
		return userAssignee;
	}

	public void setUserAssignee(Long userAssignee) {
		this.userAssignee = userAssignee;
	}

	public Date getDmAproveRejectDate() {
		return dmAproveRejectDate;
	}

	public void setDmAproveRejectDate(Date dmAproveRejectDate) {
		this.dmAproveRejectDate = dmAproveRejectDate;
	}

	
	private String DmRemarks;
	
	
	
	public String getDmRemarks() {
		return DmRemarks;
	}

	public void setDmRemarks(String dmRemarks) {
		DmRemarks = dmRemarks;
	}


	private String tsNo;
	private String tsDate;
	private BigDecimal tsAmt;
	private MultipartFile tsDocumentUpload;
	private String tsRemarks;
	private String asNo;
	private String asDate;
	private MultipartFile asDocumentUpload;
	private String asRemarks;
	private Long tsFileId;
	private Long asFileId;
	private String fileName;
	private Boolean isTenders;
	private String gramPanchayatCode;
	private Long gramPanchayatId;

	private Long dmStatus;
	
	
	private Boolean isDisabled = false;
	
	
	
	
	private String DmRemakrs;
	
	private Boolean isDisabledDep = false;
	
	private Long financialHeadId;
	
	private Long vidhanSabhaId;
	
	
	private String financialHeadName;
	
	private String vidhanSabhaName;
	
	
	private BigDecimal pac;
	
	private BigDecimal totalExpensess;
	
	
	private BigDecimal lastExpenditure;
	
	public Boolean getIsDisabledDep() {
		return isDisabledDep;
	}

	public void setIsDisabledDep(Boolean isDisabledDep) {
		this.isDisabledDep = isDisabledDep;
	}

	public String getDmRemakrs() {
		return DmRemakrs;
	}

	public void setDmRemakrs(String dmRemakrs) {
		DmRemakrs = dmRemakrs;
	}

	public Long getDmStatus() {
		return dmStatus;
	}

	public void setDmStatus(Long dmStatus) {
		this.dmStatus = dmStatus;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getSecureAmtStatus() {
		return secureAmtStatus;
	}

	public void setSecureAmtStatus(String secureAmtStatus) {
		this.secureAmtStatus = secureAmtStatus;
	}

	public String getDrawCreatedBy() {
		return drawCreatedBy;
	}
    
	public void setDrawCreatedBy(String drawCreatedBy) {
		this.drawCreatedBy = drawCreatedBy;
	}

	
	
	public Boolean getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(Boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	public String getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	public Long getDrawingId() {
		return drawingId;
	}

	public void setDrawingId(Long drawingId) {
		this.drawingId = drawingId;
	}

	private String drawingStatus;

	private String workNo;

	private Long workTypeId;

	private String workType;

	private Long financialYear;

	private Long schemeId;
	private String scheme;

	private Long schemeStateId;
	private String schemeState;

	private Long schemeNhmId;
	private String schemeNhm;

	private Long schemeEcpr2Id;
	private String schemeEcpr2;

	private Long schemeOthersId;
	private String schemeOthers;

	private String workTypeName;
	// aman
	private String dateOfAdministrativeApproval;
	private String yearOfAdministrativeApproval;
	private BigDecimal amountOfAdministrativeApproval;
	private String workOrderDate;
	private BigDecimal timeLineInMonths;
	private BigDecimal totalExpeditureTillDate;
	private String levelOfCompletion;
	private Date assignDate;
	private String modifiedDate;

	public String getDateOfAdministrativeApproval() {
		return dateOfAdministrativeApproval;
	}

	public void setDateOfAdministrativeApproval(String dateOfAdministrativeApproval) {
		this.dateOfAdministrativeApproval = dateOfAdministrativeApproval;
	}

	public String getYearOfAdministrativeApproval() {
		return yearOfAdministrativeApproval;
	}

	public void setYearOfAdministrativeApproval(String yearOfAdministrativeApproval) {
		this.yearOfAdministrativeApproval = yearOfAdministrativeApproval;
	}

	public BigDecimal getAmountOfAdministrativeApproval() {
		return amountOfAdministrativeApproval;
	}

	public void setAmountOfAdministrativeApproval(BigDecimal amountOfAdministrativeApproval) {
		this.amountOfAdministrativeApproval = amountOfAdministrativeApproval;
	}

	public String getWorkOrderDate() {
		return workOrderDate;
	}

	public void setWorkOrderDate(String workOrderDate) {
		this.workOrderDate = workOrderDate;
	}

	public BigDecimal getTimeLineInMonths() {
		return timeLineInMonths;
	}

	public void setTimeLineInMonths(BigDecimal timeLineInMonths) {
		this.timeLineInMonths = timeLineInMonths;
	}

	public BigDecimal getTotalExpeditureTillDate() {
		return totalExpeditureTillDate;
	}

	public void setTotalExpeditureTillDate(BigDecimal totalExpeditureTillDate) {
		this.totalExpeditureTillDate = totalExpeditureTillDate;
	}

	public String getLevelOfCompletion() {
		return levelOfCompletion;
	}

	public void setLevelOfCompletion(String levelOfCompletion) {
		this.levelOfCompletion = levelOfCompletion;
	}

	public String getWorkTypeName() {
		return workTypeName;
	}

	public void setWorkTypeName(String workTypeName) {
		this.workTypeName = workTypeName;
	}

	public String getDrawingStatus() {
		return drawingStatus;
	}

	public void setDrawingStatus(String drawingStatus) {
		this.drawingStatus = drawingStatus;
	}

	private List<String> files;

	public List<String> getFiles() {
		return files;
	}

	public void setFiles(List<String> files) {
		this.files = files;
	}

	public Long getSchemeStateId() {
		return schemeStateId;
	}

	public void setSchemeStateId(Long schemeStateId) {
		this.schemeStateId = schemeStateId;
	}

	public String getSchemeState() {
		return schemeState;
	}

	public void setSchemeState(String schemeState) {
		this.schemeState = schemeState;
	}

	public Long getSchemeNhmId() {
		return schemeNhmId;
	}

	public void setSchemeNhmId(Long schemeNhmId) {
		this.schemeNhmId = schemeNhmId;
	}

	public String getSchemeNhm() {
		return schemeNhm;
	}

	public void setSchemeNhm(String schemeNhm) {
		this.schemeNhm = schemeNhm;
	}

	public Long getSchemeEcpr2Id() {
		return schemeEcpr2Id;
	}

	public void setSchemeEcpr2Id(Long schemeEcpr2Id) {
		this.schemeEcpr2Id = schemeEcpr2Id;
	}

	public String getSchemeEcpr2() {
		return schemeEcpr2;
	}

	public void setSchemeEcpr2(String schemeEcpr2) {
		this.schemeEcpr2 = schemeEcpr2;
	}

	public Long getSchemeOthersId() {
		return schemeOthersId;
	}

	public void setSchemeOthersId(Long schemeOthersId) {
		this.schemeOthersId = schemeOthersId;
	}

	public String getSchemeOthers() {
		return schemeOthers;
	}

	public void setSchemeOthers(String schemeOthers) {
		this.schemeOthers = schemeOthers;
	}

	public Long getHeadStateId() {
		return HeadStateId;
	}

	public void setHeadStateId(Long headStateId) {
		HeadStateId = headStateId;
	}

	public String getHeadState() {
		return HeadState;
	}

	public void setHeadState(String headState) {
		HeadState = headState;
	}

	public Long getHeadNhmId() {
		return HeadNhmId;
	}

	public void setHeadNhmId(Long headNhmId) {
		HeadNhmId = headNhmId;
	}

	public String getHeadNhm() {
		return HeadNhm;
	}

	public void setHeadNhm(String headNhm) {
		HeadNhm = headNhm;
	}

	public Long getHeadEcpr2Id() {
		return HeadEcpr2Id;
	}

	public void setHeadEcpr2Id(Long headEcpr2Id) {
		HeadEcpr2Id = headEcpr2Id;
	}

	public String getHeadEcpr2() {
		return HeadEcpr2;
	}

	public void setHeadEcpr2(String headEcpr2) {
		HeadEcpr2 = headEcpr2;
	}

	public Long getHeadOthersId() {
		return HeadOthersId;
	}

	public void setHeadOthersId(Long headOthersId) {
		HeadOthersId = headOthersId;
	}

	public String getHeadOthers() {
		return HeadOthers;
	}

	public void setHeadOthers(String headOthers) {
		HeadOthers = headOthers;
	}

	private Long workStatusId;
	private Long workStatus;

	private Long workPriorityId;
	private String workPriority;

	private Long HeadId;
	private Long Head;

	private Long HeadStateId;
	private String HeadState;

	private Long HeadNhmId;
	private String HeadNhm;

	private Long HeadEcpr2Id;
	private String HeadEcpr2;

	private Long HeadOthersId;
	private String HeadOthers;

	private BigDecimal estimatedAmt;

	private BigDecimal amtReleasedTillDate;

	private Long implementationAgencyId;

	private Long implementationAgency;

	private Long districtId;
	private String districtCode;
	private Long workRequestStatusId;
	
	

	public Long getWorkRequestStatusId() {
		return workRequestStatusId;
	}

	public void setWorkRequestStatusId(Long workRequestStatusId) {
		this.workRequestStatusId = workRequestStatusId;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	private String districtName;
	private String districtNameH;

	private Long blockId;

	
	
	private String gramPanchayatName;
	
	
	private String blockCode;

	private String blockName;

	private BigDecimal asAmt;

	private String workCategoryName;

	private String remarks;

	private String status;

	private BigDecimal estAmt;

	private Long divisionId;

	private String divisionName;

	private String ConstituencyCode;

	private String constituencyName;

	private Long workCategoryId;

	private String workSubTypeName;

	public String getWorkSubTypeName() {
		return workSubTypeName;
	}

	public void setWorkSubTypeName(String workSubTypeName) {
		this.workSubTypeName = workSubTypeName;
	}

	private Long workSubTypeId;

	private String multifundedStatus;

	private BigDecimal fundByState;

	private BigDecimal fundByNhm;

	private BigDecimal fundByEcrp2;

	private BigDecimal fundByOthers;

	private Long categorySubTypeId;

	private String categorySubTypeName;

	private List<WorkProgressImageListBean> workProgressData;
	private List<WorkProgressImageListBean> workProgressImagesDataList;

	private BigDecimal allocatedAmount;

	private String role;

	public BigDecimal getAllocatedAmount() {
		return this.allocatedAmount;
	}

	public void setAllocatedAmount(BigDecimal allocatedAmount) {
		this.allocatedAmount = allocatedAmount;
	}

	public List<WorkProgressImageListBean> getWorkProgressData() {
		return workProgressData;
	}

	public void setWorkProgressData(List<WorkProgressImageListBean> workProgressData) {
		this.workProgressData = workProgressData;
	}

	public List<WorkProgressImageListBean> getWorkProgressImagesDataList() {
		return workProgressImagesDataList;
	}

	public void setWorkProgressImagesDataList(List<WorkProgressImageListBean> workProgressImagesDataList) {
		this.workProgressImagesDataList = workProgressImagesDataList;
	}

	public String getCategorySubTypeName() {
		return categorySubTypeName;
	}

	public void setCategorySubTypeName(String categorySubTypeName) {
		this.categorySubTypeName = categorySubTypeName;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public Long getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(Long financialYear) {
		this.financialYear = financialYear;
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

	public BigDecimal getEstimatedAmt() {
		return estimatedAmt;
	}

	public void setEstimatedAmt(BigDecimal estimatedAmt) {
		this.estimatedAmt = estimatedAmt;
	}

	public BigDecimal getAmtReleasedTillDate() {
		return amtReleasedTillDate;
	}

	
	
	public String getGramPanchayatName() {
		return gramPanchayatName;
	}

	public void setGramPanchayatName(String gramPanchayatName) {
		this.gramPanchayatName = gramPanchayatName;
	}

	public Long getHeadId() {
		return HeadId;
	}

	public void setHeadId(Long headId) {
		HeadId = headId;
	}

	public Long getHead() {
		return Head;
	}

	public void setHead(Long head) {
		Head = head;
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

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public Long getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(Long divisionId) {
		this.divisionId = divisionId;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getWorkNo() {
		return workNo;
	}

	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}

	public Long getWorkTypeId() {
		return workTypeId;
	}

	public void setWorkTypeId(Long workTypeId) {
		this.workTypeId = workTypeId;
	}

	public Long getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}

	public Long getWorkStatusId() {
		return workStatusId;
	}

	public void setWorkStatusId(Long workStatusId) {
		this.workStatusId = workStatusId;
	}

	public Long getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(Long workStatus) {
		this.workStatus = workStatus;
	}

	public Long getImplementationAgencyId() {
		return implementationAgencyId;
	}

	public void setImplementationAgencyId(Long implementationAgencyId) {
		this.implementationAgencyId = implementationAgencyId;
	}

	public Long getBlockId() {
		return blockId;
	}

	public void setBlockId(Long blockId) {
		this.blockId = blockId;
	}

	public void setWorkPriorityId(Long workPriorityId) {
		this.workPriorityId = workPriorityId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public String getDistrictNameH() {
		return districtNameH;
	}

	public void setDistrictNameH(String districtNameH) {
		this.districtNameH = districtNameH;
	}

	public BigDecimal getEstAmt() {
		return estAmt;
	}

	public void setEstAmt(BigDecimal estAmt) {
		this.estAmt = estAmt;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public String getConstituencyCode() {
		return ConstituencyCode;
	}

	public void setConstituencyCode(String constituencyCode) {
		ConstituencyCode = constituencyCode;
	}

	public String getConstituencyName() {
		return constituencyName;
	}

	public void setConstituencyName(String constituencyName) {
		this.constituencyName = constituencyName;
	}

	public Long getWorkCategoryId() {
		return workCategoryId;
	}

	public void setWorkCategoryId(Long workCategoryId) {
		this.workCategoryId = workCategoryId;
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

	public Long getCategorySubTypeId() {
		return categorySubTypeId;
	}

	public void setCategorySubTypeId(Long categorySubTypeId) {
		this.categorySubTypeId = categorySubTypeId;
	}

	public Long getWorkPriorityId() {
		return workPriorityId;
	}

	public String getMultifundedStatus() {
		return multifundedStatus;
	}

	public void setMultifundedStatus(String multifundedStatus) {
		this.multifundedStatus = multifundedStatus;
	}

	public Long getWorkSubTypeId() {
		return workSubTypeId;
	}

	public void setWorkSubTypeId(Long workSubTypeId) {
		this.workSubTypeId = workSubTypeId;
	}

	public String getWorkCategoryName() {
		return workCategoryName;
	}

	public void setWorkCategoryName(String workCategoryName) {
		this.workCategoryName = workCategoryName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	private Long workIdCount;

	public Long getWorkIdCount() {
		return workIdCount;
	}

	public void setWorkIdCount(Long workIdCount) {
		this.workIdCount = workIdCount;
	}

	private String financialYearName;

	public String getFinancialYearName() {
		return financialYearName;
	}

	public void setFinancialYearName(String financialYearName) {
		this.financialYearName = financialYearName;
	}

	private String implementationAgencyName;

	public String getImplementationAgencyName() {
		return implementationAgencyName;
	}

	public void setImplementationAgencyName(String implementationAgencyName) {
		this.implementationAgencyName = implementationAgencyName;
	}

	private String workStatusName;

	public String getWorkStatusName() {
		return workStatusName;
	}

	public void setWorkStatusName(String workStatusName) {
		this.workStatusName = workStatusName;
	}

	private String headName;

	public String getHeadName() {
		return headName;
	}

	public void setHeadName(String headName) {
		this.headName = headName;
	}

	private String contractorName;

	private String sor;

	private String aboveBelow;

	private BigDecimal tenderParcentage;

	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public String getSor() {
		return sor;
	}

	public void setSor(String sor) {
		this.sor = sor;
	}

	public String getAboveBelow() {
		return aboveBelow;
	}

	public void setAboveBelow(String aboveBelow) {
		this.aboveBelow = aboveBelow;
	}

	public BigDecimal getTenderParcentage() {
		return tenderParcentage;
	}

	public void setTenderParcentage(BigDecimal bigDecimal) {
		this.tenderParcentage = bigDecimal;
	}

	public String getTsNo() {
		return tsNo;
	}

	public void setTsNo(String tsNo) {
		this.tsNo = tsNo;
	}

	public String getTsDate() {
		return tsDate;
	}

	public void setTsDate(String tsDate) {
		this.tsDate = tsDate;
	}

	public BigDecimal getTsAmt() {
		return tsAmt;
	}

	public void setTsAmt(BigDecimal tsAmt) {
		this.tsAmt = tsAmt;
	}

	public MultipartFile getTsDocumentUpload() {
		return tsDocumentUpload;
	}

	public void setTsDocumentUpload(MultipartFile tsDocumentUpload) {
		this.tsDocumentUpload = tsDocumentUpload;
	}

	public String getTsRemarks() {
		return tsRemarks;
	}

	public void setTsRemarks(String tsRemarks) {
		this.tsRemarks = tsRemarks;
	}

	public String getAsNo() {
		return asNo;
	}

	public void setAsNo(String asNo) {
		this.asNo = asNo;
	}

	public String getAsDate() {
		return asDate;
	}

	public void setAsDate(String asDate) {
		this.asDate = asDate;
	}

	public MultipartFile getAsDocumentUpload() {
		return asDocumentUpload;
	}

	public void setAsDocumentUpload(MultipartFile asDocumentUpload) {
		this.asDocumentUpload = asDocumentUpload;
	}

	public String getAsRemarks() {
		return asRemarks;
	}

	public void setAsRemarks(String asRemarks) {
		this.asRemarks = asRemarks;
	}

	public BigDecimal getAsAmt() {
		return asAmt;
	}

	public void setAsAmt(BigDecimal asAmt) {
		this.asAmt = asAmt;
	}

	public Long getTsFileId() {
		return tsFileId;
	}

	public void setTsFileId(Long tsFileId) {
		this.tsFileId = tsFileId;
	}

	public Long getAsFileId() {
		return asFileId;
	}

	public void setAsFileId(Long asFileId) {
		this.asFileId = asFileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public Boolean getIsTenders() {
		return isTenders;
	}

	public void setIsTenders(Boolean isTenders) {
		this.isTenders = isTenders;
	}

	public String getGramPanchayatCode() {
		return gramPanchayatCode;
	}

	public void setGramPanchayatCode(String gramPanchayatCode) {
		this.gramPanchayatCode = gramPanchayatCode;
	}

	public Long getGramPanchayatId() {
		return gramPanchayatId;
	}

	public void setGramPanchayatId(Long gramPanchayatId) {
		this.gramPanchayatId = gramPanchayatId;
	}

	

	private List<DocumentUploadWorkProgressBean> progressDocuments;

	public List<DocumentUploadWorkProgressBean> getProgressDocuments() {
	    return progressDocuments;
	}

	public void setProgressDocuments(List<DocumentUploadWorkProgressBean> progressDocuments) {
	    this.progressDocuments = progressDocuments;
	}

	public Date getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}

	public Long getFinancialHeadId() {
		return financialHeadId;
	}

	public void setFinancialHeadId(Long financialHeadId) {
		this.financialHeadId = financialHeadId;
	}

	public Long getVidhanSabhaId() {
		return vidhanSabhaId;
	}

	public void setVidhanSabhaId(Long vidhanSabhaId) {
		this.vidhanSabhaId = vidhanSabhaId;
	}

	public final String getFinancialHeadName() {
		return financialHeadName;
	}

	public final void setFinancialHeadName(String financialHeadName) {
		this.financialHeadName = financialHeadName;
	}

	public final String getVidhanSabhaName() {
		return vidhanSabhaName;
	}

	public final void setVidhanSabhaName(String vidhanSabhaName) {
		this.vidhanSabhaName = vidhanSabhaName;
	}



	public final String getModifiedDate() {
		return modifiedDate;
	}

	public final void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public final BigDecimal getPac() {
		return pac;
	}

	public final void setPac(BigDecimal pac) {
		this.pac = pac;
	}

	public final BigDecimal getTotalExpensess() {
		return totalExpensess;
	}

	public final void setTotalExpensess(BigDecimal totalExpensess) {
		this.totalExpensess = totalExpensess;
	}

	public final BigDecimal getLastExpenditure() {
		return lastExpenditure;
	}

	public final void setLastExpenditure(BigDecimal lastExpenditure) {
		this.lastExpenditure = lastExpenditure;
	}




	
	
	
	
	
}
