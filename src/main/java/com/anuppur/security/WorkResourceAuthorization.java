package com.anuppur.security;

import org.springframework.stereotype.Component;

import com.anuppur.entity.DocumentUpload;
import com.anuppur.entity.DocumentUploadDrawingDetail;
import com.anuppur.entity.DocumentUploadWorkProgress;
import com.anuppur.entity.WorkDocument;
import com.anuppur.repository.DocumentRepository;
import com.anuppur.repository.DocumentUploadDrawingDetailRepository;
import com.anuppur.repository.DocumentUploadWorkProgressRepository;
import com.anuppur.repository.WorkDocumentRepository;

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

    public WorkResourceAuthorization(WorkAuthorization workAuthorization,
            WorkDocumentRepository workDocumentRepository,
            DocumentRepository documentRepository,
            DocumentUploadDrawingDetailRepository drawingRepository,
            DocumentUploadWorkProgressRepository progressDocumentRepository) {
        this.workAuthorization = workAuthorization;
        this.workDocumentRepository = workDocumentRepository;
        this.documentRepository = documentRepository;
        this.drawingRepository = drawingRepository;
        this.progressDocumentRepository = progressDocumentRepository;
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
        return document != null && canAccess(document.getWorkId());
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

    private boolean canAccess(Long workId) {
        return workId != null && workAuthorization.canAccessWork(workId);
    }
}
