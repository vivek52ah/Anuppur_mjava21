package com.anuppur.security;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;

class SecureFileUploadPolicyTest {

    @TempDir
    Path tempDir;

    @Test
    void acceptsAllowedExtensionMimeAndMagicBytes() {
        assertDoesNotThrow(() -> SecureFileUploadPolicy.validateDocument(file(
                "report.pdf", "application/pdf", "%PDF-1.7\ncontent".getBytes(StandardCharsets.US_ASCII))));
        assertDoesNotThrow(() -> SecureFileUploadPolicy.validateDocument(file(
                "photo.jpeg", "image/jpeg", bytes(0xFF, 0xD8, 0xFF, 0xE0))));
        assertDoesNotThrow(() -> SecureFileUploadPolicy.validateDocument(file(
                "image.png", "image/png", bytes(0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A))));
    }

    @Test
    void rejectsActiveContentRenamedFilesAndMimeMismatch() {
        assertThrows(InvalidFileUploadException.class, () -> SecureFileUploadPolicy.validateDocument(file(
                "attack.html", "text/html", "<script>alert(1)</script>".getBytes(StandardCharsets.UTF_8))));
        assertThrows(InvalidFileUploadException.class, () -> SecureFileUploadPolicy.validateDocument(file(
                "attack.pdf", "application/pdf", "<html>not a pdf</html>".getBytes(StandardCharsets.UTF_8))));
        assertThrows(InvalidFileUploadException.class, () -> SecureFileUploadPolicy.validateDocument(file(
                "image.png", "image/jpeg", bytes(0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A))));
        assertThrows(InvalidFileUploadException.class, () -> SecureFileUploadPolicy.validateDocument(file(
                "vector.svg", "image/svg+xml", "<svg/>".getBytes(StandardCharsets.UTF_8))));
    }

    @Test
    void storesWithUuidNameInsteadOfOriginalName() throws Exception {
        MockMultipartFile upload = file("confidential report.pdf", "application/pdf",
                "%PDF-1.7\ncontent".getBytes(StandardCharsets.US_ASCII));
        String storedName = SecureFileUploadPolicy.storeDocument(upload, tempDir);

        assertTrue(storedName.matches("[0-9a-f-]{36}\\.pdf"));
        assertFalse(storedName.contains("confidential"));
        assertTrue(Files.exists(tempDir.resolve(storedName)));
    }

    @Test
    void enforcesDocumentSizeLimit() {
        byte[] oversized = new byte[(int) SecureFileUploadPolicy.MAX_DOCUMENT_SIZE + 1];
        oversized[0] = '%';
        oversized[1] = 'P';
        oversized[2] = 'D';
        oversized[3] = 'F';
        oversized[4] = '-';
        assertThrows(InvalidFileUploadException.class, () -> SecureFileUploadPolicy.validateDocument(
                file("large.pdf", "application/pdf", oversized)));
    }

    @Test
    void xlsxMustHaveRequiredStructureAndNoMacroContent() throws Exception {
        MockMultipartFile valid = file("bulk.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                xlsx(false));
        assertDoesNotThrow(() -> SecureFileUploadPolicy.validateBulkSpreadsheet(valid));

        MockMultipartFile macro = file("bulk.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                xlsx(true));
        assertThrows(InvalidFileUploadException.class,
                () -> SecureFileUploadPolicy.validateBulkSpreadsheet(macro));
    }

    private static MockMultipartFile file(String name, String mime, byte[] content) {
        return new MockMultipartFile("file", name, mime, content);
    }

    private static byte[] bytes(int... values) {
        byte[] result = new byte[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = (byte) values[i];
        }
        return result;
    }

    private static byte[] xlsx(boolean macro) throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (ZipOutputStream zip = new ZipOutputStream(output)) {
            add(zip, "[Content_Types].xml", "<Types/>");
            add(zip, "xl/workbook.xml", "<workbook/>");
            if (macro) {
                add(zip, "xl/vbaProject.bin", "macro");
            }
        }
        return output.toByteArray();
    }

    private static void add(ZipOutputStream zip, String name, String value) throws Exception {
        zip.putNextEntry(new ZipEntry(name));
        zip.write(value.getBytes(StandardCharsets.UTF_8));
        zip.closeEntry();
    }
}
