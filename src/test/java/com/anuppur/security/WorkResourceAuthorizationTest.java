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
import com.anuppur.entity.Work;
import com.anuppur.entity.WorkDocument;
import com.anuppur.repository.DocumentRepository;
import com.anuppur.repository.DocumentUploadDrawingDetailRepository;
import com.anuppur.repository.DocumentUploadWorkProgressRepository;
import com.anuppur.repository.WorkDocumentRepository;

class WorkResourceAuthorizationTest {

    private WorkAuthorization workAuthorization;
    private WorkDocumentRepository workDocuments;
    private DocumentRepository documents;
    private DocumentUploadDrawingDetailRepository drawings;
    private DocumentUploadWorkProgressRepository progressDocuments;
    private WorkResourceAuthorization authorization;

    @BeforeEach
    void setUp() {
        workAuthorization = mock(WorkAuthorization.class);
        workDocuments = mock(WorkDocumentRepository.class);
        documents = mock(DocumentRepository.class);
        drawings = mock(DocumentUploadDrawingDetailRepository.class);
        progressDocuments = mock(DocumentUploadWorkProgressRepository.class);
        authorization = new WorkResourceAuthorization(workAuthorization, workDocuments,
                documents, drawings, progressDocuments);
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

        assertThat(authorization.canAccessWorkDocument(1L)).isTrue();
        assertThat(authorization.canAccessUploadedDocument(2L)).isTrue();
        assertThat(authorization.canAccessDrawingDocument(3L)).isTrue();
        assertThat(authorization.canAccessProgressDocument(4L)).isTrue();
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
}
