package com.anuppur.bean;

public class DmRemarkWiseReportRowBean {

    private Long departmentMasterId;
    private String issueType;
    private String departmentNames; // comma-separated impl agency names
    private long totalWorks;

    public Long getDepartmentMasterId() { return departmentMasterId; }
    public void setDepartmentMasterId(Long departmentMasterId) { this.departmentMasterId = departmentMasterId; }

    public String getIssueType() { return issueType; }
    public void setIssueType(String issueType) { this.issueType = issueType; }

    public String getDepartmentNames() { return departmentNames; }
    public void setDepartmentNames(String departmentNames) { this.departmentNames = departmentNames; }

    public long getTotalWorks() { return totalWorks; }
    public void setTotalWorks(long totalWorks) { this.totalWorks = totalWorks; }
}
