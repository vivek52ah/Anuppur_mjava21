package com.anuppur.dto;

import java.math.BigDecimal;

/**
 * Narrow request DTO for adding expenditure to one financial-head row.
 */
public class FinancialExpenditureRequest {

    private Long id;
    private Long workId;
    private BigDecimal expenditure;

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

    public BigDecimal getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(BigDecimal expenditure) {
        this.expenditure = expenditure;
    }
}
