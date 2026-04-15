package com.anuppur.bean;

public class DepartmentWiseReportRowBean {

    private Long departmentId;
    private String departmentName;
    private Long implementationAgencyId;
    private String implementationAgencyName;
    private Long financialYearId;
    private String financialYearName;
    private long totalWorks;
    private long completedWorks;
    private long ongoingWorks;
    private long notStartedWorks;

    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }

    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    public String getImplementationAgencyName() { return implementationAgencyName; }
    public void setImplementationAgencyName(String implementationAgencyName) { this.implementationAgencyName = implementationAgencyName; }

    public Long getImplementationAgencyId() { return implementationAgencyId; }
    public void setImplementationAgencyId(Long implementationAgencyId) { this.implementationAgencyId = implementationAgencyId; }

    public Long getFinancialYearId() { return financialYearId; }
    public void setFinancialYearId(Long financialYearId) { this.financialYearId = financialYearId; }

    public String getFinancialYearName() { return financialYearName; }
    public void setFinancialYearName(String financialYearName) { this.financialYearName = financialYearName; }

    public long getTotalWorks() { return totalWorks; }
    public void setTotalWorks(long totalWorks) { this.totalWorks = totalWorks; }

    public long getCompletedWorks() { return completedWorks; }
    public void setCompletedWorks(long completedWorks) { this.completedWorks = completedWorks; }

    public long getOngoingWorks() { return ongoingWorks; }
    public void setOngoingWorks(long ongoingWorks) { this.ongoingWorks = ongoingWorks; }

    public long getNotStartedWorks() { return notStartedWorks; }
    public void setNotStartedWorks(long notStartedWorks) { this.notStartedWorks = notStartedWorks; }
}
