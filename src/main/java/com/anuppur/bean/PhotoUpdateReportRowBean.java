package com.anuppur.bean;

public class PhotoUpdateReportRowBean {

    private Long departmentId;
    private String departmentName;
    private String workName;
    private long totalWorks;
    private String areaOfficerName;
    private int days;

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public long getTotalWorks() {
        return totalWorks;
    }

    public void setTotalWorks(long totalWorks) {
        this.totalWorks = totalWorks;
    }

    public String getAreaOfficerName() {
        return areaOfficerName;
    }

    public void setAreaOfficerName(String areaOfficerName) {
        this.areaOfficerName = areaOfficerName;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
