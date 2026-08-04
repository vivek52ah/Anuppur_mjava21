package com.anuppur.security;

import org.springframework.stereotype.Component;

import com.anuppur.entity.DocumentUpload;
import com.anuppur.entity.DocumentUploadDrawingDetail;
import com.anuppur.entity.DocumentUploadWorkProgress;
import com.anuppur.entity.DepartmentRemarks;
import com.anuppur.entity.DmRemarks;
import com.anuppur.entity.WorkDocument;
import com.anuppur.entity.WorkFinancialAgency;
import com.anuppur.repository.DepartmentRemarksRepository;
import com.anuppur.repository.DocumentRepository;
import com.anuppur.repository.DocumentUploadDrawingDetailRepository;
import com.anuppur.repository.DocumentUploadWorkProgressRepository;
import com.anuppur.repository.DmRemarksRepository;
import com.anuppur.repository.WorkDocumentRepository;
import com.anuppur.repository.FinancialAgencyRepository;

/**
 * Resolves child-resource identifiers to their owning work before delegating
 * the final district/department decision to {@link WorkAuthorization}.
 * Missing resources and missing ownership data are denied.
 */
@Component("workResourceAuthorization")
public class WorkResourceAuthorization {

    private final WorkAuthorization workAuthorization;
    private final WorkDocumentRepository workDocumentRepository;
    private final DocumentRepository documentRepository;
    private final DocumentUploadDrawingDetailRepository drawingRepository;
    private final DocumentUploadWorkProgressRepository progressDocumentRepository;
    private final FinancialAgencyRepository financialAgencyRepository;
    private final DepartmentRemarksRepository departmentRemarksRepository;
    private final DmRemarksRepository dmRemarksRepository;

    public WorkResourceAuthorization(WorkAuthorization workAuthorization,
            WorkDocumentRepository workDocumentRepository,
            DocumentRepository documentRepository,
            DocumentUploadDrawingDetailRepository drawingRepository,
            DocumentUploadWorkProgressRepository progressDocumentRepository,
            FinancialAgencyRepository financialAgencyRepository,
            DepartmentRemarksRepository departmentRemarksRepository,
            DmRemarksRepository dmRemarksRepository) {
        this.workAuthorization = workAuthorization;
        this.workDocumentRepository = workDocumentRepository;
        this.documentRepository = documentRepository;
        this.drawingRepository = drawingRepository;
        this.progressDocumentRepository = progressDocumentRepository;
        this.financialAgencyRepository = financialAgencyRepository;
        this.departmentRemarksRepository = departmentRemarksRepository;
        this.dmRemarksRepository = dmRemarksRepository;
    }

    public boolean canAccessWorkDocument(Long documentId) {
        WorkDocument document = documentId == null
                ? null
                : workDocumentRepository.findById(documentId).orElse(null);
        return document != null && document.getWork() != null
                && canAccess(document.getWork().getId());
    }

    public boolean canAccessUploadedDocument(Long documentId) {
        DocumentUpload document = documentId == null
                ? null
                : documentRepository.findById(documentId).orElse(null);
        if (document == null) {
            return false;
        }
        if (document.getWorkId() != null) {
            return canAccess(document.getWorkId());
        }

        DepartmentRemarks departmentRemark = departmentRemarksRepository
                .findEnabledByDocumentId(documentId).orElse(null);
        if (departmentRemark != null) {
            return canAccess(departmentRemark.getWorkId());
        }

        DmRemarks dmRemark = dmRemarksRepository.findEnabledByDocumentId(documentId).orElse(null);
        return dmRemark != null && canAccess(dmRemark.getWorkId());
    }

    public boolean canAccessDrawingDocument(Long documentId) {
        DocumentUploadDrawingDetail document = documentId == null
                ? null
                : drawingRepository.findById(documentId).orElse(null);
        return document != null && canAccess(document.getWorkId());
    }

    public boolean canAccessProgressDocument(Long documentId) {
        DocumentUploadWorkProgress document = documentId == null
                ? null
                : progressDocumentRepository.findById(documentId).orElse(null);
        return document != null && canAccess(document.getWorkId());
    }

    public boolean canDeleteWorkDocument(Long documentId) {
        WorkDocument document = documentId == null
                ? null
                : workDocumentRepository.findById(documentId).orElse(null);
        return document != null && document.getWork() != null
                && workAuthorization.canModifyOwnedResource(document.getWork().getId(), document);
    }

    public boolean canAccessFinancialAgency(Long financialAgencyId) {
        WorkFinancialAgency agency = financialAgencyId == null
                ? null
                : financialAgencyRepository.findById(financialAgencyId).orElse(null);
        return agency != null && canAccess(agency.getWorkId());
    }

    private boolean canAccess(Long workId) {
        return workId != null && workAuthorization.canAccessWork(workId);
    }
}
