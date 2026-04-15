package com.anuppur.bean;

/**
 * Mirrors the fields on the addWorkDetail page.
 * Column order matches the Excel template exactly.
 */
public class BulkWorkRowBean {

    private int rowNumber;

    // --- Work Details (matches addWorkDetail form) ---
    private String workName;            // col 0  - Work Name *
    private String workTypeName;        // col 1  - Type of Work * (dropdown)
    private String financialYearName;   // col 2  - FY of Sanction * (dropdown)
    private String workSubTypeName;     // col 3  - Sub Work Type * (dropdown)
    private String isTender;            // col 4  - Is Tender * (1=Tender, 0=Non-Tender, dropdown)
    private String implementationAgencyName; // col 5 - Executive Agency * (dropdown)
    private String districtName;        // col 6  - District * (dropdown)
    private String blockName;           // col 7  - Block * (dropdown)
    private String gramPanchayatName;   // col 8  - Gram Panchayat (dropdown)
    private String vidhanSabhaName;     // col 9  - Vidhan Sabha (dropdown)

    // --- Work Status & Category ---
    private String workStatusName;      // col 10 - Work Status * (dropdown)
    private String workCategoryName;    // col 11 - Work Category (dropdown)
    private String workNo;              // col 12 - Work No (optional)

    // --- Financial ---
    private String estimatedAmount;     // col 13 - Estimated Amount *
    private String amtReleasedTillDate; // col 14 - Amount Released Till Date
    private String allocatedAmount;     // col 15 - Allocated Amount
    private String financialHeadName;   // col 16 - Financial Head (Financing Agency) (dropdown)

    // --- TS Details ---
    private String tsNo;                // col 17 - TS Order No
    private String tsDate;              // col 18 - TS Order Date (DD/MM/YYYY)
    private String tsAmt;               // col 19 - TS Amount (In Lacs)

    // --- AS Details ---
    private String asNo;                // col 20 - AS Order No
    private String asDate;              // col 21 - AS Order Date (DD/MM/YYYY)
    private String asAmt;               // col 22 - AS Amount (In Lacs)

    // --- Documents ---
    private String tsDocumentPath;      // col 23 - TS Document filename (pre-uploaded)
    private String asDocumentPath;      // col 24 - AS Document filename (pre-uploaded)

    // getters/setters
    public int getRowNumber() { return rowNumber; }
    public void setRowNumber(int rowNumber) { this.rowNumber = rowNumber; }

    public String getWorkName() { return workName; }
    public void setWorkName(String workName) { this.workName = workName; }

    public String getWorkTypeName() { return workTypeName; }
    public void setWorkTypeName(String workTypeName) { this.workTypeName = workTypeName; }

    public String getFinancialYearName() { return financialYearName; }
    public void setFinancialYearName(String financialYearName) { this.financialYearName = financialYearName; }

    public String getWorkSubTypeName() { return workSubTypeName; }
    public void setWorkSubTypeName(String workSubTypeName) { this.workSubTypeName = workSubTypeName; }

    public String getIsTender() { return isTender; }
    public void setIsTender(String isTender) { this.isTender = isTender; }

    public String getImplementationAgencyName() { return implementationAgencyName; }
    public void setImplementationAgencyName(String implementationAgencyName) { this.implementationAgencyName = implementationAgencyName; }

    public String getDistrictName() { return districtName; }
    public void setDistrictName(String districtName) { this.districtName = districtName; }

    public String getBlockName() { return blockName; }
    public void setBlockName(String blockName) { this.blockName = blockName; }

    public String getGramPanchayatName() { return gramPanchayatName; }
    public void setGramPanchayatName(String gramPanchayatName) { this.gramPanchayatName = gramPanchayatName; }

    public String getVidhanSabhaName() { return vidhanSabhaName; }
    public void setVidhanSabhaName(String vidhanSabhaName) { this.vidhanSabhaName = vidhanSabhaName; }

    public String getWorkStatusName() { return workStatusName; }
    public void setWorkStatusName(String workStatusName) { this.workStatusName = workStatusName; }

    public String getWorkCategoryName() { return workCategoryName; }
    public void setWorkCategoryName(String workCategoryName) { this.workCategoryName = workCategoryName; }

    public String getWorkNo() { return workNo; }
    public void setWorkNo(String workNo) { this.workNo = workNo; }

    public String getEstimatedAmount() { return estimatedAmount; }
    public void setEstimatedAmount(String estimatedAmount) { this.estimatedAmount = estimatedAmount; }

    public String getAmtReleasedTillDate() { return amtReleasedTillDate; }
    public void setAmtReleasedTillDate(String amtReleasedTillDate) { this.amtReleasedTillDate = amtReleasedTillDate; }

    public String getAllocatedAmount() { return allocatedAmount; }
    public void setAllocatedAmount(String allocatedAmount) { this.allocatedAmount = allocatedAmount; }

    public String getFinancialHeadName() { return financialHeadName; }
    public void setFinancialHeadName(String financialHeadName) { this.financialHeadName = financialHeadName; }

    public String getTsNo() { return tsNo; }
    public void setTsNo(String tsNo) { this.tsNo = tsNo; }

    public String getTsDate() { return tsDate; }
    public void setTsDate(String tsDate) { this.tsDate = tsDate; }

    public String getTsAmt() { return tsAmt; }
    public void setTsAmt(String tsAmt) { this.tsAmt = tsAmt; }

    public String getAsNo() { return asNo; }
    public void setAsNo(String asNo) { this.asNo = asNo; }

    public String getAsDate() { return asDate; }
    public void setAsDate(String asDate) { this.asDate = asDate; }

    public String getAsAmt() { return asAmt; }
    public void setAsAmt(String asAmt) { this.asAmt = asAmt; }

    public String getTsDocumentPath() { return tsDocumentPath; }
    public void setTsDocumentPath(String tsDocumentPath) { this.tsDocumentPath = tsDocumentPath; }

    public String getAsDocumentPath() { return asDocumentPath; }
    public void setAsDocumentPath(String asDocumentPath) { this.asDocumentPath = asDocumentPath; }
}
