package com.anuppur.service;

import java.util.Objects;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anuppur.dto.WorkImplementationAgencyUpdateRequest;
import com.anuppur.entity.ImplementationAgency;
import com.anuppur.entity.Work;
import com.anuppur.exception.FinancialValidationException;
import com.anuppur.repository.ImplAgencyRepository;
import com.anuppur.repository.WorkRepository;

@Service
public class WorkSensitiveFieldService {

    private final WorkRepository workRepository;
    private final ImplAgencyRepository implementationAgencyRepository;

    public WorkSensitiveFieldService(WorkRepository workRepository,
            ImplAgencyRepository implementationAgencyRepository) {
        this.workRepository = workRepository;
        this.implementationAgencyRepository = implementationAgencyRepository;
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
    public void updateImplementationAgency(WorkImplementationAgencyUpdateRequest request) {
        if (request == null || request.getWorkId() == null || request.getImplementationAgencyId() == null) {
            throw new FinancialValidationException("Work ID and implementing agency ID are required.");
        }
        Work work = workRepository.findByIdForFinancialUpdate(request.getWorkId())
                .orElseThrow(() -> new FinancialValidationException(
                        "Work not found for ID " + request.getWorkId() + "."));
        ImplementationAgency agency = implementationAgencyRepository
                .findById(request.getImplementationAgencyId())
                .orElseThrow(() -> new FinancialValidationException(
                        "Implementing agency not found for ID " + request.getImplementationAgencyId() + "."));
        work.setImplementationAgency(agency.getImplementationAgencyId());
        workRepository.save(work);
    }

    public void validateNormalEditImplementationAgency(Work work, Long requestedAgencyId) {
        if (work != null && work.getId() != null && requestedAgencyId != null
                && !Objects.equals(work.getImplementationAgency(), requestedAgencyId)) {
            throw new FinancialValidationException(
                    "Implementing agency cannot be changed through the normal work edit request.");
        }
    }
}
