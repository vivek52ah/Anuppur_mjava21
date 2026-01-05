package com.anuppur.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;

import lombok.Data;

//@Audited
@Entity
@Table(name = "t_work")

public class Work extends Auditable implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@OneToMany(mappedBy = "work", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TSASReviseWork> tsasReviseWorks = new ArrayList<>();

	@Column(name = "work_name")
	private String workName;

	@Column(name = "work_no")
	private String workNo;

	@Column(name = "work_type")
	private Long workType;

	@Column(name = "financial_year")
	private Long financialYear;

	private Long scheme;

	@Column(name = "work_status")
	private Long workStatus;

	@Column(name = "work_priority")
	private String workPriority;

	@Column(name = "work_head")
	private Long workHead;

	@Column(name = "estimated_amt")
	private BigDecimal estimatedAmt;

	@Column(name = "amt_released_till_date")
	private BigDecimal amtReleasedTillDate;

	@Column(name = "division_code")
	private Long divisionCode;

	@Column(name = "division_id")
	private Long divisionId;

	@Column(name = "implementation_agency")
	private Long implementationAgency;

	@Column(name = "district_code")
	private String districtCode;

	@Column(name = "district_id")
	private Long districtId;

	@Column(name = "block_code")
	private String blockCode;

	@Column(name = "block_id")
	private Long blockId;

	@Column(name = "dm_a_r_date")
	private Date dmApproveRejctDate;

	public Date getDmApproveRejctDate() {
		return dmApproveRejctDate;
	}

	public void setDmApproveRejctDate(Date dmApproveRejctDate) {
		this.dmApproveRejctDate = dmApproveRejctDate;
	}

	@Column(name = "legislative_constituency_code")
	private String legislativeConstituencyCode;

	@Column(name = "legislative_constituency_id")
	private Long legislativeConstituencyId;

	@Column(name = "work_type_id")
	private Long worTypeId;

	@Column(name = "work_category_id")
	private Long workCategoryId;

	@Column(name = "work_subtype_id")
	private Long workSubtypeId;

	@Column(name = "multifunded_status")
	private String multifundedStatus;

	@Column(name = "fund_by_state")
	private BigDecimal fundByState;

	@Column(name = "fund_by_nhm")
	private BigDecimal fundByNhm;

	@Column(name = "fund_by_ecrp2")
	private BigDecimal fundByEcrp2;

	@Column(name = "fund_by_others")
	private BigDecimal fundByOthers;

	@Column(name = "category_subtype_id")
	private Long categorySubtypeId;

	@Column(name = "work_request_status_id")
	private Long workRequestStatusId;

	private String status;

	@Column(name = "scheme_state")
	private Long schemeState;

	@Column(name = "scheme_nhm")
	private Long schemeNhm;

	@Column(name = "scheme_ecrp2")
	private Long schemeEcrp2;

	@Column(name = "scheme_others")
	private Long schemeOthers;

	@Column(name = "created_by", updatable = false)
	@CreatedBy
	private String createdBy;

	@Column(name = "head_state")
	private Long headState;

	@Column(name = "head_nhm")
	private Long headNhm;

	@Column(name = "head_ecrp2")
	private Long headEcrp2;

	@Column(name = "head_others")
	private Long headOthers;

	@Column(name = "drawing_created_by")
	private String drawCreatedBy;

	@Column(name = "security_amt_status")
	private String secureAmtStatus;

	@Column(name = "start_date")
	private String startDate;

	@Column(name = "allocated_amount")
	private BigDecimal allocatedAmount;

	@Column(name = "ts_no")
	private String tsNo;

	@Column(name = "ts_date")
	private String tsDate;

	@Column(name = "ts_amt")
	private BigDecimal tsAmt;

	@Column(name = "dm_status")
	private Long dmStatus;

	@Column(name = "is_tender")
	private Boolean isTenders;
	/*
	 * @Column(name = "ts_document_upload") private String tsDocumentUpload;
	 */

	@JoinColumn(name = "ts_document_upload", referencedColumnName = "id")
	@OneToOne
	private DocumentUpload documentUploadTechnical;

	@Column(name = "ts_remarks")
	private String tsRemarks;

	@Column(name = "as_no")
	private String asNo;

	@Column(name = "as_date")
	private String asDate;

	@Column(name = "as_amt")
	private BigDecimal asAmt;

	@JoinColumn(name = "as_document_upload", referencedColumnName = "id")
	@OneToOne
	private DocumentUpload documentUploadAdministration;

	@Column(name = "as_remarks")
	private String asRemarks;

	@Column(name = "gram_code")
	private String gramPanchayatCode;

	@Column(name = "gram_id")
	private Long gramPanchayatId;

	@Column(name = "user_id")
	private Long userAssignee;

	@Column(name = "dm_remarks")
	private String dmRemakrs;

	@Column(name = "user_assignee_date")
	private Date userAssigneeDate;
	
	@Column(name = "work_priority_id")
	private Long workPriorityId;
	
	
	@Column(name = "financial_head_id")
	private Long financialHeadId;
	
	@Column(name = "vidhan_sabha_id")
	private Long vidhanSabhaId;

	public List<TSASReviseWork> getTsasReviseWorks() {
		return tsasReviseWorks;
	}

	public void setTsasReviseWorks(List<TSASReviseWork> tsasReviseWorks) {
		this.tsasReviseWorks = tsasReviseWorks;
	}

	public Long getUserAssignee() {
		return userAssignee;
	}

	public void setUserAssignee(Long userAssignee) {
		this.userAssignee = userAssignee;
	}

	public String getDmRemakrs() {
		return dmRemakrs;
	}

	public void setDmRemakrs(String dmRemakrs) {
		this.dmRemakrs = dmRemakrs;
	}

	public BigDecimal getAllocatedAmount() {
		return this.allocatedAmount;
	}

	public void setAllocatedAmount(BigDecimal allocatedAmount) {
		this.allocatedAmount = allocatedAmount;
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

	public Long getDmStatus() {
		return dmStatus;
	}

	public void setDmStatus(Long dmStatus) {
		this.dmStatus = dmStatus;
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

	public Long getSchemeState() {
		return schemeState;
	}

	public void setSchemeState(Long schemeState) {
		this.schemeState = schemeState;
	}

	public Long getSchemeNhm() {
		return schemeNhm;
	}

	public void setSchemeNhm(Long schemeNhm) {
		this.schemeNhm = schemeNhm;
	}

	public Long getSchemeEcrp2() {
		return schemeEcrp2;
	}

	public void setSchemeEcrp2(Long schemeEcrp2) {
		this.schemeEcrp2 = schemeEcrp2;
	}

	public Long getSchemeOthers() {
		return schemeOthers;
	}

	public void setSchemeOthers(Long schemeOthers) {
		this.schemeOthers = schemeOthers;
	}

	public Long getHeadState() {
		return headState;
	}

	public void setHeadState(Long headState) {
		this.headState = headState;
	}

	public Long getHeadNhm() {
		return headNhm;
	}

	public void setHeadNhm(Long headNhm) {
		this.headNhm = headNhm;
	}

	public Long getHeadEcrp2() {
		return headEcrp2;
	}

	public void setHeadEcrp2(Long headEcrp2) {
		this.headEcrp2 = headEcrp2;
	}

	public Long getHeadOthers() {
		return headOthers;
	}

	public void setHeadOthers(Long headOthers) {
		this.headOthers = headOthers;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getWorkRequestStatusId() {
		return workRequestStatusId;
	}

	public void setWorkRequestStatusId(Long workRequestStatusId) {
		this.workRequestStatusId = workRequestStatusId;
	}

	public Work() {
		super();
	}

	public Work(Long id) {
		this.id = id;
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

	public String getWorkNo() {
		return workNo;
	}

	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}

	public Long getWorkType() {
		return workType;
	}

	public void setWorkType(Long workType) {
		this.workType = workType;
	}

	public Long getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(Long financialYear) {
		this.financialYear = financialYear;
	}

	public Long getScheme() {
		return scheme;
	}

	public void setScheme(Long scheme) {
		this.scheme = scheme;
	}

	public Long getImplementationAgency() {
		return implementationAgency;
	}

	public void setImplementationAgency(Long implementationAgency) {
		this.implementationAgency = implementationAgency;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public String getBlockCode() {
		return blockCode;
	}

	public void setBlockCode(String blockCode) {
		this.blockCode = blockCode;
	}

	public Long getBlockId() {
		return blockId;
	}

	public void setBlockId(Long blockId) {
		this.blockId = blockId;
	}

	public String getLegislativeConstituencyCode() {
		return legislativeConstituencyCode;
	}

	public void setLegislativeConstituencyCode(String legislativeConstituencyCode) {
		this.legislativeConstituencyCode = legislativeConstituencyCode;
	}

	public Long getLegislativeConstituencyId() {
		return legislativeConstituencyId;
	}

	public void setLegislativeConstituencyId(Long legislativeConstituencyId) {
		this.legislativeConstituencyId = legislativeConstituencyId;
	}

	public Long getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(Long workStatus) {
		this.workStatus = workStatus;
	}

	public String getWorkPriority() {
		return workPriority;
	}

	public void setWorkPriority(String workPriority) {
		this.workPriority = workPriority;
	}

	public Long getWorkHead() {
		return workHead;
	}

	public void setWorkHead(Long workHead) {
		this.workHead = workHead;
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

	public Long getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(Long divisionId) {
		this.divisionId = divisionId;
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

	public Long getCategorySubtypeId() {
		return categorySubtypeId;
	}

	public void setCategorySubtypeId(Long categorySubtypeId) {
		this.categorySubtypeId = categorySubtypeId;
	}

	public Long getDivisionCode() {
		return divisionCode;
	}

	public void setDivisionCode(Long divisionCode) {
		this.divisionCode = divisionCode;
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

	public DocumentUpload getDocumentUploadTechnical() {
		return documentUploadTechnical;
	}

	public void setDocumentUploadTechnical(DocumentUpload documentUploadTechnical) {
		this.documentUploadTechnical = documentUploadTechnical;
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

	public BigDecimal getAsAmt() {
		return asAmt;
	}

	public void setAsAmt(BigDecimal asAmt) {
		this.asAmt = asAmt;
	}

	public DocumentUpload getDocumentUploadAdministration() {
		return documentUploadAdministration;
	}

	public void setDocumentUploadAdministration(DocumentUpload documentUploadAdministration) {
		this.documentUploadAdministration = documentUploadAdministration;
	}

	public String getAsRemarks() {
		return asRemarks;
	}

	public void setAsRemarks(String asRemarks) {
		this.asRemarks = asRemarks;
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

	public Date getUserAssigneeDate() {
		return userAssigneeDate;
	}

	public void setUserAssigneeDate(Date userAssigneeDate) {
		this.userAssigneeDate = userAssigneeDate;
	}

	public Long getWorkPriorityId() {
		return workPriorityId;
	}

	public void setWorkPriorityId(Long workPriorityId) {
		this.workPriorityId = workPriorityId;
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

	
	
}