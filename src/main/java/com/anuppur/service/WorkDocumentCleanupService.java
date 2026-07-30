package com.anuppur.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anuppur.constants.DMSConstants;
import com.anuppur.entity.DocumentUpload;
import com.anuppur.entity.DocumentUploadDrawingDetail;
import com.anuppur.entity.DocumentUploadWorkProgress;
import com.anuppur.entity.DepartmentRemarks;
import com.anuppur.entity.DmRemarks;
import com.anuppur.entity.WorkDocument;
import com.anuppur.repository.DepartmentRemarksRepository;
import com.anuppur.repository.DmRemarksRepository;
import com.anuppur.repository.DocumentRepository;
import com.anuppur.repository.DocumentUploadDrawingDetailRepository;
import com.anuppur.repository.DocumentUploadWorkProgressRepository;
import com.anuppur.repository.WorkDocumentRepository;
import com.anuppur.repository.WorkRepository;

@Service
public class WorkDocumentCleanupService {

    private static final Logger logger = LoggerFactory.getLogger(WorkDocumentCleanupService.class);

    private final DocumentRepository documentRepository;
    private final DocumentUploadWorkProgressRepository progressRepository;
    private final DocumentUploadDrawingDetailRepository drawingRepository;
    private final WorkDocumentRepository workDocumentRepository;
    private final WorkRepository workRepository;
    private final DmRemarksRepository dmRemarksRepository;
    private final DepartmentRemarksRepository departmentRemarksRepository;
    private final Path documentRoot;
    private final Path workDocumentRoot;
    private final List<Path> documentDirectories;

    public WorkDocumentCleanupService(
            DocumentRepository documentRepository,
            DocumentUploadWorkProgressRepository progressRepository,
            DocumentUploadDrawingDetailRepository drawingRepository,
            WorkDocumentRepository workDocumentRepository,
            WorkRepository workRepository,
            DmRemarksRepository dmRemarksRepository,
            DepartmentRemarksRepository departmentRemarksRepository,
            @Value("${document.root}") String root,
            @Value("${document.work}") String work,
            @Value("${document.technical}") String technical,
            @Value("${document.administrator}") String administrator,
            @Value("${document.tsRevised}") String tsRevised,
            @Value("${document.asRevised}") String asRevised,
            @Value("${document.tender}") String tender,
            @Value("${document.workprogress}") String workProgress,
            @Value("${document.drawingfile}") String drawing,
            @Value("${document.cc}") String cc,
            @Value("${document.dmAttachment}") String dmAttachment) {
        this.documentRepository = documentRepository;
        this.progressRepository = progressRepository;
        this.drawingRepository = drawingRepository;
        this.workDocumentRepository = workDocumentRepository;
        this.workRepository = workRepository;
        this.dmRemarksRepository = dmRemarksRepository;
        this.departmentRemarksRepository = departmentRemarksRepository;
        this.documentRoot = Paths.get(root).toAbsolutePath().normalize();
        this.workDocumentRoot = resolveUnderRoot(work);
        this.documentDirectories = List.of(
                resolveUnderRoot(technical), resolveUnderRoot(administrator), resolveUnderRoot(tsRevised),
                resolveUnderRoot(asRevised), resolveUnderRoot(tender), resolveUnderRoot(workProgress),
                resolveUnderRoot(drawing), resolveUnderRoot(cc), resolveUnderRoot(dmAttachment));
    }

    @Transactional
    public void deleteForWork(Long workId) {
        if (workId == null) {
            return;
        }

        Set<DocumentUpload> documentSet = new LinkedHashSet<>(documentRepository.findAllByWorkId(workId));
        List<DocumentUploadWorkProgress> progressDocuments = progressRepository.findAllByWorkId(workId);
        List<DocumentUploadDrawingDetail> drawingDocuments = drawingRepository.findAllByWorkId(workId);
        List<WorkDocument> workDocuments = workDocumentRepository.findByWorkId(workId);
        List<DmRemarks> dmRemarks = dmRemarksRepository.findByWorkId(workId);
        List<DepartmentRemarks> departmentRemarks = departmentRemarksRepository.findByWorkId(workId);

        dmRemarks.forEach(remark -> addDocument(documentSet, remark.getDocumentUpload()));
        departmentRemarks.forEach(remark -> addDocument(documentSet, remark.getDocumentUpload()));
        List<DocumentUpload> documents = List.copyOf(documentSet);

        documents.forEach(document -> deleteFromKnownDirectories(document.getDocumentName(), workId));
        progressDocuments.forEach(document -> deleteFromKnownDirectories(document.getDocumentName(), workId));
        drawingDocuments.forEach(document -> deleteFromKnownDirectories(document.getDocumentName(), workId));
        workDocuments.forEach(document -> deleteSafely(workDocumentRoot.resolve(String.valueOf(workId)),
                document.getFileName()));

        dmRemarks.forEach(remark -> remark.setDocumentUpload(null));
        departmentRemarks.forEach(remark -> remark.setDocumentUpload(null));
        dmRemarksRepository.saveAll(dmRemarks);
        departmentRemarksRepository.saveAll(departmentRemarks);
        documentRepository.deleteAll(documents);
        progressRepository.deleteAll(progressDocuments);
        drawingRepository.deleteAll(drawingDocuments);
        workDocumentRepository.deleteAll(workDocuments);
    }

    /** Cleans document records/files whose work is missing or already soft-deleted. */
    @Scheduled(cron = "${app.upload.orphan-cleanup-cron:0 30 2 * * *}")
    @Transactional
    public void cleanupOrphanDocuments() {
        Set<Long> referencedWorkIds = new LinkedHashSet<>();
        documentRepository.findAll().forEach(d -> add(referencedWorkIds, d.getWorkId()));
        progressRepository.findAll().forEach(d -> add(referencedWorkIds, d.getWorkId()));
        drawingRepository.findAll().forEach(d -> add(referencedWorkIds, d.getWorkId()));
        workDocumentRepository.findAll().forEach(d -> {
            if (d.getWork() != null) {
                add(referencedWorkIds, d.getWork().getId());
            }
        });
        dmRemarksRepository.findAll().forEach(d -> add(referencedWorkIds, d.getWorkId()));
        departmentRemarksRepository.findAll().forEach(d -> add(referencedWorkIds, d.getWorkId()));

        referencedWorkIds.stream().filter(this::isOrphan).forEach(workId -> {
            logger.info("Cleaning orphan documents for work {}", workId);
            deleteForWork(workId);
        });
    }

    private boolean isOrphan(Long workId) {
        return workRepository.findById(workId)
                .map(work -> DMSConstants.STATUS_DELETED.equalsIgnoreCase(work.getStatus()))
                .orElse(true);
    }

    private void deleteFromKnownDirectories(String storedName, Long workId) {
        documentDirectories.forEach(directory -> {
            deleteSafely(directory, storedName);
            deleteSafely(directory.resolve(String.valueOf(workId)), storedName);
        });
    }

    private void deleteSafely(Path allowedDirectory, String storedName) {
        if (storedName == null || storedName.isBlank() || storedName.contains("/") || storedName.contains("\\")) {
            logger.warn("Skipped unsafe stored document name: {}", storedName);
            return;
        }
        Path safeDirectory = allowedDirectory.toAbsolutePath().normalize();
        Path target = safeDirectory.resolve(storedName).normalize();
        if (!target.startsWith(documentRoot) || !target.getParent().equals(safeDirectory)) {
            logger.warn("Skipped document path outside configured root: {}", target);
            return;
        }
        try {
            Files.deleteIfExists(target);
        } catch (IOException ex) {
            logger.error("Unable to delete document file {}", target, ex);
        }
    }

    private Path resolveUnderRoot(String child) {
        Path resolved = documentRoot.resolve(child).normalize();
        if (!resolved.startsWith(documentRoot)) {
            throw new IllegalArgumentException("Configured document path escapes document.root: " + child);
        }
        return resolved;
    }

    private static void add(Set<Long> values, Long value) {
        if (value != null) {
            values.add(value);
        }
    }

    private static void addDocument(Set<DocumentUpload> documents, DocumentUpload document) {
        if (document != null) {
            documents.add(document);
        }
    }
}
