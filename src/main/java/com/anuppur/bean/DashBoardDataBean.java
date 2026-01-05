package com.anuppur.bean;

import java.math.BigDecimal;

public class DashBoardDataBean {
	
	private int workId;
	private BigDecimal  workCount;
	private BigDecimal workCreate;
	private BigDecimal asIsuuesCount;
	private BigDecimal tenderCalledCount;
	private BigDecimal tenderRcvCount;
	private BigDecimal tenderApprovalInprocessCount;
	private BigDecimal reTenderCount;
	private BigDecimal loaIssuesCount;
	private BigDecimal woIssuedCount;
	private BigDecimal contractorCount;
	private BigDecimal notStartedCount;
	private BigDecimal inProgressCount;
	private BigDecimal completedCount;
	private BigDecimal ccCount;
	private BigDecimal handOverCount;
	public int getWorkId() {
		return workId;
	}
	public void setWorkId(int workId) {
		this.workId = workId;
	}
	public BigDecimal getWorkCount() {
		return workCount;
	}
	public void setWorkCount(BigDecimal workCount) {
		this.workCount = workCount;
	}
	public BigDecimal getAsIsuuesCount() {
		return asIsuuesCount;
	}
	public void setAsIsuuesCount(BigDecimal asIsuuesCount) {
		this.asIsuuesCount = asIsuuesCount;
	}
	public BigDecimal getTenderCalledCount() {
		return tenderCalledCount;
	}
	public void setTenderCalledCount(BigDecimal tenderCalledCount) {
		this.tenderCalledCount = tenderCalledCount;
	}
	public BigDecimal getTenderRcvCount() {
		return tenderRcvCount;
	}
	public void setTenderRcvCount(BigDecimal tenderRcvCount) {
		this.tenderRcvCount = tenderRcvCount;
	}
	public BigDecimal getTenderApprovalInprocessCount() {
		return tenderApprovalInprocessCount;
	}
	public void setTenderApprovalInprocessCount(BigDecimal tenderApprovalInprocessCount) {
		this.tenderApprovalInprocessCount = tenderApprovalInprocessCount;
	}
	public BigDecimal getReTenderCount() {
		return reTenderCount;
	}
	public void setReTenderCount(BigDecimal reTenderCount) {
		this.reTenderCount = reTenderCount;
	}
	public BigDecimal getLoaIssuesCount() {
		return loaIssuesCount;
	}
	public void setLoaIssuesCount(BigDecimal loaIssuesCount) {
		this.loaIssuesCount = loaIssuesCount;
	}
	public BigDecimal getWoIssuedCount() {
		return woIssuedCount;
	}
	public void setWoIssuedCount(BigDecimal woIssuedCount) {
		this.woIssuedCount = woIssuedCount;
	}
	public BigDecimal getContractorCount() {
		return contractorCount;
	}
	public void setContractorCount(BigDecimal contractorCount) {
		this.contractorCount = contractorCount;
	}
	public BigDecimal getNotStartedCount() {
		return notStartedCount;
	}
	public void setNotStartedCount(BigDecimal notStartedCount) {
		this.notStartedCount = notStartedCount;
	}
	public BigDecimal getInProgressCount() {
		return inProgressCount;
	}
	public void setInProgressCount(BigDecimal inProgressCount) {
		this.inProgressCount = inProgressCount;
	}
	public BigDecimal getCompletedCount() {
		return completedCount;
	}
	public void setCompletedCount(BigDecimal completedCount) {
		this.completedCount = completedCount;
	}
	public BigDecimal getCcCount() {
		return ccCount;
	}
	public void setCcCount(BigDecimal ccCount) {
		this.ccCount = ccCount;
	}
	public BigDecimal getHandOverCount() {
		return handOverCount;
	}
	public void setHandOverCount(BigDecimal handOverCount) {
		this.handOverCount = handOverCount;
	}
	
	
	public BigDecimal getWorkCreate() {
		return workCreate;
	}
	public void setWorkCreate(BigDecimal workCreate) {
		this.workCreate = workCreate;
	}
	@Override
	public String toString() {
		return "DashBoardDataBean [workId=" + workId + ", workCount=" + workCount + ", asIsuuesCount=" + asIsuuesCount
				+ ", tenderCalledCount=" + tenderCalledCount + ", tenderRcvCount=" + tenderRcvCount
				+ ", tenderApprovalInprocessCount=" + tenderApprovalInprocessCount + ", reTenderCount=" + reTenderCount
				+ ", loaIssuesCount=" + loaIssuesCount + ", woIssuedCount=" + woIssuedCount + ", contractorCount="
				+ contractorCount + ", notStartedCount=" + notStartedCount + ", inProgressCount=" + inProgressCount
				+ ", completedCount=" + completedCount + ", ccCount=" + ccCount + ", handOverCount=" + handOverCount
				+ "]";
	}
	
	
	
	

}
