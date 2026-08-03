package com.anuppur.dto;

/**
 * Dedicated DTO for the sensitive implementing-agency field.
 */
public class WorkImplementationAgencyUpdateRequest {

    private Long workId;
    private Long implementationAgencyId;

    public Long getWorkId() {
        return workId;
    }

    public void setWorkId(Long workId) {
        this.workId = workId;
    }

    public Long getImplementationAgencyId() {
        return implementationAgencyId;
    }

    public void setImplementationAgencyId(Long implementationAgencyId) {
        this.implementationAgencyId = implementationAgencyId;
    }
}
