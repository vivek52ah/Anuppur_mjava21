package com.anuppur.bean;

import java.util.List;

public class BulkUploadResultBean {

    private int totalRows;
    private int successCount;
    private int failureCount;
    private String message;
    private List<RowErrorBean> errors;
    private String validationReportBase64;

    public int getTotalRows() { return totalRows; }
    public void setTotalRows(int totalRows) { this.totalRows = totalRows; }

    public int getSuccessCount() { return successCount; }
    public void setSuccessCount(int successCount) { this.successCount = successCount; }

    public int getFailureCount() { return failureCount; }
    public void setFailureCount(int failureCount) { this.failureCount = failureCount; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public List<RowErrorBean> getErrors() { return errors; }
    public void setErrors(List<RowErrorBean> errors) { this.errors = errors; }

    public String getValidationReportBase64() { return validationReportBase64; }
    public void setValidationReportBase64(String validationReportBase64) { this.validationReportBase64 = validationReportBase64; }
}
