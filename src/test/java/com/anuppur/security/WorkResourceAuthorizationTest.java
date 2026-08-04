package com.anuppur.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.anuppur.entity.DocumentUpload;
import com.anuppur.entity.DocumentUploadDrawingDetail;
import com.anuppur.entity.DocumentUploadWorkProgress;
import com.anuppur.entity.DepartmentRemarks;
import com.anuppur.entity.Work;
import com.anuppur.entity.WorkDocument;
import com.anuppur.entity.WorkFinancialAgency;
import com.anuppur.repository.DocumentRepository;
import com.anuppur.repository.DocumentUploadDrawingDetailRepository;
import com.anuppur.repository.DocumentUploadWorkProgressRepository;
import com.anuppur.repository.DepartmentRemarksRepository;
import com.anuppur.repository.DmRemarksRepository;
import com.anuppur.repository.WorkDocumentRepository;
import com.anuppur.repository.FinancialAgencyRepository;

class WorkResourceAuthorizationTest {

    private WorkAuthorization workAuthorization;
    private WorkDocumentRepository workDocuments;
    private DocumentRepository documents;
    private DocumentUploadDrawingDetailRepository drawings;
    private DocumentUploadWorkProgressRepository progressDocuments;
    private WorkResourceAuthorization authorization;
    private FinancialAgencyRepository financialAgencies;
    private DepartmentRemarksRepository departmentRemarks;
    private DmRemarksRepository dmRemarks;

    @BeforeEach
    void setUp() {
        workAuthorization = mock(WorkAuthorization.class);
        workDocuments = mock(WorkDocumentRepository.class);
        documents = mock(DocumentRepository.class);
        drawings = mock(DocumentUploadDrawingDetailRepository.class);
        progressDocuments = mock(DocumentUploadWorkProgressRepository.class);
        financialAgencies = mock(FinancialAgencyRepository.class);
        departmentRemarks = mock(DepartmentRemarksRepository.class);
        dmRemarks = mock(DmRemarksRepository.class);
        authorization = new WorkResourceAuthorization(workAuthorization, workDocuments,
                documents, drawings, progressDocuments, financialAgencies,
                departmentRemarks, dmRemarks);
    }

    @Test
    void resolvesEveryChildResourceToItsOwningWork() {
        Work work = new Work();
        work.setId(42L);
        when(workAuthorization.canAccessWork(42L)).thenReturn(true);

        WorkDocument workDocument = new WorkDocument();
        workDocument.setWork(work);
        when(workDocuments.findById(1L)).thenReturn(Optional.of(workDocument));

        DocumentUpload uploaded = new DocumentUpload();
        uploaded.setWorkId(42L);
        when(documents.findById(2L)).thenReturn(Optional.of(uploaded));

        DocumentUploadDrawingDetail drawing = new DocumentUploadDrawingDetail();
        drawing.setWorkId(42L);
        when(drawings.findById(3L)).thenReturn(Optional.of(drawing));

        DocumentUploadWorkProgress progress = new DocumentUploadWorkProgress();
        progress.setWorkId(42L);
        when(progressDocuments.findById(4L)).thenReturn(Optional.of(progress));

        WorkFinancialAgency financialAgency = new WorkFinancialAgency();
        financialAgency.setWorkId(42L);
        when(financialAgencies.findById(5L)).thenReturn(Optional.of(financialAgency));

        assertThat(authorization.canAccessWorkDocument(1L)).isTrue();
        assertThat(authorization.canAccessUploadedDocument(2L)).isTrue();
        assertThat(authorization.canAccessDrawingDocument(3L)).isTrue();
        assertThat(authorization.canAccessProgressDocument(4L)).isTrue();
        assertThat(authorization.canAccessFinancialAgency(5L)).isTrue();
    }

    @Test
    void deniesMissingResourcesAndResourcesOutsideScope() {
        DocumentUpload uploaded = new DocumentUpload();
        uploaded.setWorkId(99L);
        when(documents.findById(2L)).thenReturn(Optional.of(uploaded));
        when(workAuthorization.canAccessWork(99L)).thenReturn(false);

        assertThat(authorization.canAccessUploadedDocument(2L)).isFalse();
        assertThat(authorization.canAccessUploadedDocument(404L)).isFalse();
        assertThat(authorization.canAccessProgressDocument(null)).isFalse();
    }

    @Test
    void resolvesLegacyRemarkDocumentsWithoutAStoredWorkId() {
        DocumentUpload uploaded = new DocumentUpload();
        uploaded.setDocumentId(6L);
        when(documents.findById(6L)).thenReturn(Optional.of(uploaded));

        DepartmentRemarks remark = new DepartmentRemarks();
        remark.setWorkId(42L);
        when(departmentRemarks.findEnabledByDocumentId(6L)).thenReturn(Optional.of(remark));
        when(workAuthorization.canAccessWork(42L)).thenReturn(true);

        assertThat(authorization.canAccessUploadedDocument(6L)).isTrue();
    }

    @Test
    void workDocumentDeleteRequiresOwnershipInAdditionToWorkScope() {
        Work work = new Work();
        work.setId(42L);
        WorkDocument document = new WorkDocument();
        document.setWork(work);
        document.setCreatedBy("department");
        when(workDocuments.findById(1L)).thenReturn(Optional.of(document));
        when(workAuthorization.canModifyOwnedResource(42L, document)).thenReturn(false, true);

        assertThat(authorization.canDeleteWorkDocument(1L)).isFalse();
        assertThat(authorization.canDeleteWorkDocument(1L)).isTrue();
    }
}
