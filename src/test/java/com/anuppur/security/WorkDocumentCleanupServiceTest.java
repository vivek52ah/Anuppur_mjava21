package com.anuppur.security;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.anuppur.entity.DocumentUpload;
import com.anuppur.repository.DocumentRepository;
import com.anuppur.repository.DepartmentRemarksRepository;
import com.anuppur.repository.DmRemarksRepository;
import com.anuppur.repository.DocumentUploadDrawingDetailRepository;
import com.anuppur.repository.DocumentUploadWorkProgressRepository;
import com.anuppur.repository.WorkDocumentRepository;
import com.anuppur.repository.WorkRepository;
import com.anuppur.service.WorkDocumentCleanupService;

class WorkDocumentCleanupServiceTest {

    @TempDir
    Path root;

    @Test
    void deletesPhysicalFilesAndMetadataForDeletedWork() throws Exception {
        DocumentRepository documents = mock(DocumentRepository.class);
        DocumentUploadWorkProgressRepository progress = mock(DocumentUploadWorkProgressRepository.class);
        DocumentUploadDrawingDetailRepository drawings = mock(DocumentUploadDrawingDetailRepository.class);
        WorkDocumentRepository workDocuments = mock(WorkDocumentRepository.class);
        WorkRepository works = mock(WorkRepository.class);
        DmRemarksRepository dmRemarks = mock(DmRemarksRepository.class);
        DepartmentRemarksRepository departmentRemarks = mock(DepartmentRemarksRepository.class);

        DocumentUpload document = new DocumentUpload();
        document.setWorkId(42L);
        document.setDocumentName("550e8400-e29b-41d4-a716-446655440000.pdf");
        when(documents.findAllByWorkId(42L)).thenReturn(List.of(document));
        when(progress.findAllByWorkId(42L)).thenReturn(List.of());
        when(drawings.findAllByWorkId(42L)).thenReturn(List.of());
        when(workDocuments.findByWorkId(42L)).thenReturn(List.of());
        when(dmRemarks.findByWorkId(42L)).thenReturn(List.of());
        when(departmentRemarks.findByWorkId(42L)).thenReturn(List.of());

        Path technical = root.resolve("WORK/TECH_DOC");
        Files.createDirectories(technical);
        Path storedFile = technical.resolve(document.getDocumentName());
        Files.writeString(storedFile, "%PDF-test");

        WorkDocumentCleanupService cleanup = new WorkDocumentCleanupService(
                documents, progress, drawings, workDocuments, works, dmRemarks, departmentRemarks,
                root.toString(), "WORK_DOCS/",
                "WORK/TECH_DOC/", "WORK/ADMS_DOC/", "WORK/TECH_DOC/REVISED_TS/",
                "WORK/ADMS_DOC/REVISED_AS/", "WORK/TENDER_DOC/", "WORK/WORK_PROGRESS/",
                "WORK/WORK_DRAWING/", "WORK/CC_DOC/", "WORK/DMAttachement/");

        cleanup.deleteForWork(42L);

        assertFalse(Files.exists(storedFile));
        verify(documents).deleteAll(List.of(document));
    }
}
