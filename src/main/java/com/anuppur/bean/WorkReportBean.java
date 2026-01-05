package com.anuppur.bean;


import java.math.BigDecimal;
import java.math.BigInteger;

public class WorkReportBean {
	
	private int index;
	private String nameOfAgency;
	private String scheme;
	private String workName;
	private String subTypeName;
	private String drawingStatus;
	private String pendingStatus;
	private String asStatus;
	private String tenderStatus;
	private Integer phyPerc;
	private BigDecimal expAmt;
	private String financialYear;
	
	
	public Integer getPhyPerc() {
		return phyPerc;
	}
	public void setPhyPerc(Integer phyPerc) {
		this.phyPerc = phyPerc;
	}
	public BigDecimal getExpAmt() {
		return expAmt;
	}
	public void setExpAmt(BigDecimal expAmt) {
		this.expAmt = expAmt;
	}
	public String getAsStatus() {
		return asStatus;
	}
	public void setAsStatus(String asStatus) {
		this.asStatus = asStatus;
	}
	public String getTenderStatus() {
		return tenderStatus;
	}
	public void setTenderStatus(String tenderStatus) {
		this.tenderStatus = tenderStatus;
	}
	private String year;
	private String segment;
	private String schemeYear;
	private  String division;
	private String district;
	private BigInteger workCount;

	private BigDecimal workContractrAmt = new BigDecimal(0);
	
	private BigDecimal totalExpUptoMarch = new BigDecimal(0);

	private BigDecimal totalExp = new BigDecimal(0);
	
	private BigDecimal uptoDateAmt = new BigDecimal(0);
	private BigDecimal handedOverStatus  = new BigDecimal(0);;
	private BigDecimal completedStatus= new BigDecimal(0);
	private BigDecimal workStarted= new BigDecimal(0);
	private BigDecimal notStarted= new BigDecimal(0);
	private BigDecimal finishLevelCount= new BigDecimal(0);
	private BigDecimal roofLevel= new BigDecimal(0);
	private BigDecimal lintelLevel= new BigDecimal(0);
	private BigDecimal plinthLevel= new BigDecimal(0);
	private BigDecimal foundationLevel= new BigDecimal(0);
	private BigDecimal siteNotSelected= new BigDecimal(0);
	private BigDecimal tenderIssue= new BigDecimal(0);
	private BigDecimal tenderAwarded= new BigDecimal(0);
	
	
	
	public String getWorkName() {
		return workName;
	}
	public void setWorkName(String workName) {
		this.workName = workName;
	}
	public String getSubTypeName() {
		return subTypeName;
	}
	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}
	public String getDrawingStatus() {
		return drawingStatus;
	}
	public void setDrawingStatus(String drawingStatus) {
		this.drawingStatus = drawingStatus;
	}
	public String getPendingStatus() {
		return pendingStatus;
	}
	public void setPendingStatus(String pendingStatus) {
		this.pendingStatus = pendingStatus;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	
	
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getSchemeYear() {
		return schemeYear;
	}
	public void setSchemeYear(String schemeYear) {
		this.schemeYear = schemeYear;
	}
	public String getSegment() {
		return segment;
	}
	public void setSegment(String segment) {
		this.segment = segment;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public String getNameOfAgency() {
		return nameOfAgency;
	}
	public void setNameOfAgency(String nameOfAgency) {
		this.nameOfAgency = nameOfAgency;
	}
	public BigInteger getWorkCount() {
		return workCount;
	}
	public void setWorkCount(BigInteger workCount) {
		this.workCount = workCount;
	}
	
	
	public BigDecimal getWorkContractrAmt() {
		return workContractrAmt;
	}
	public void setWorkContractrAmt(BigDecimal workContractrAmt) {
		this.workContractrAmt = workContractrAmt;
	}
	
	public BigDecimal getTotalExpUptoMarch() {
		return totalExpUptoMarch;
	}
	public void setTotalExpUptoMarch(BigDecimal totalExpUptoMarch) {
		this.totalExpUptoMarch = totalExpUptoMarch;
	}
	public BigDecimal getTotalExp() {
		return totalExp;
	}
	public void setTotalExp(BigDecimal totalExp) {
		this.totalExp = totalExp;
	}
	public BigDecimal getUptoDateAmt() {
		return uptoDateAmt;
	}
	public void setUptoDateAmt(BigDecimal uptoDateAmt) {
		this.uptoDateAmt = uptoDateAmt;
	}
	
	public BigDecimal getHandedOverStatus() {
		return handedOverStatus;
	}
	public void setHandedOverStatus(BigDecimal handedOverStatus) {
		this.handedOverStatus = handedOverStatus;
	}
	public BigDecimal getCompletedStatus() {
		return completedStatus;
	}
	public void setCompletedStatus(BigDecimal completedStatus) {
		this.completedStatus = completedStatus;
	}
	public BigDecimal getWorkStarted() {
		return workStarted;
	}
	public void setWorkStarted(BigDecimal workStarted) {
		this.workStarted = workStarted;
	}
	public BigDecimal getNotStarted() {
		return notStarted;
	}
	public void setNotStarted(BigDecimal notStarted) {
		this.notStarted = notStarted;
	}
	public BigDecimal getFinishLevelCount() {
		return finishLevelCount;
	}
	public void setFinishLevelCount(BigDecimal finishLevelCount) {
		this.finishLevelCount = finishLevelCount;
	}
	public BigDecimal getRoofLevel() {
		return roofLevel;
	}
	public void setRoofLevel(BigDecimal roofLevel) {
		this.roofLevel = roofLevel;
	}
	public BigDecimal getLintelLevel() {
		return lintelLevel;
	}
	public void setLintelLevel(BigDecimal lintelLevel) {
		this.lintelLevel = lintelLevel;
	}
	public BigDecimal getPlinthLevel() {
		return plinthLevel;
	}
	public void setPlinthLevel(BigDecimal plinthLevel) {
		this.plinthLevel = plinthLevel;
	}
	public BigDecimal getFoundationLevel() {
		return foundationLevel;
	}
	public void setFoundationLevel(BigDecimal foundationLevel) {
		this.foundationLevel = foundationLevel;
	}
	public BigDecimal getSiteNotSelected() {
		return siteNotSelected;
	}
	public void setSiteNotSelected(BigDecimal siteNotSelected) {
		this.siteNotSelected = siteNotSelected;
	}
	public BigDecimal getTenderIssue() {
		return tenderIssue;
	}
	public void setTenderIssue(BigDecimal tenderIssue) {
		this.tenderIssue = tenderIssue;
	}
	public BigDecimal getTenderAwarded() {
		return tenderAwarded;
	}
	public void setTenderAwarded(BigDecimal tenderAwarded) {
		this.tenderAwarded = tenderAwarded;
	}
	public String getFinancialYear() {
		return financialYear;
	}
	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}
	
	
}
