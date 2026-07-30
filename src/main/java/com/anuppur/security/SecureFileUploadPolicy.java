package com.anuppur.security;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.web.multipart.MultipartFile;

public final class SecureFileUploadPolicy {

    public static final long MAX_DOCUMENT_SIZE = 10L * 1024L * 1024L;
    public static final long MAX_SPREADSHEET_SIZE = 5L * 1024L * 1024L;

    private static final Map<String, Set<String>> DOCUMENT_MIME_TYPES = Map.of(
            "pdf", Set.of("application/pdf"),
            "jpg", Set.of("image/jpeg"),
            "jpeg", Set.of("image/jpeg"),
            "png", Set.of("image/png"));

    private static final String XLSX_MIME =
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    private SecureFileUploadPolicy() {
    }

    public static void validateDocument(MultipartFile file) {
        validateBasic(file, MAX_DOCUMENT_SIZE);
        String extension = extension(file);
        Set<String> allowedMimeTypes = DOCUMENT_MIME_TYPES.get(extension);
        if (allowedMimeTypes == null) {
            throw invalid("Only PDF, JPG/JPEG and PNG documents are allowed.");
        }

        String mimeType = normalizedMime(file.getContentType());
        if (!allowedMimeTypes.contains(mimeType)) {
            throw invalid("File extension and MIME type do not match an allowed document type.");
        }

        try (InputStream input = new BufferedInputStream(file.getInputStream())) {
            byte[] signature = input.readNBytes(8);
            boolean valid = switch (extension) {
                case "pdf" -> startsWith(signature, new byte[] { '%', 'P', 'D', 'F', '-' });
                case "jpg", "jpeg" -> startsWith(signature,
                        new byte[] { (byte) 0xFF, (byte) 0xD8, (byte) 0xFF });
                case "png" -> startsWith(signature,
                        new byte[] { (byte) 0x89, 'P', 'N', 'G', 0x0D, 0x0A, 0x1A, 0x0A });
                default -> false;
            };
            if (!valid) {
                throw invalid("File content does not match its extension and MIME type.");
            }
        } catch (IOException ex) {
            throw new InvalidFileUploadException("Unable to inspect uploaded file.", ex);
        }
    }

    public static void validateBulkSpreadsheet(MultipartFile file) {
        validateBasic(file, MAX_SPREADSHEET_SIZE);
        if (!"xlsx".equals(extension(file)) || !XLSX_MIME.equals(normalizedMime(file.getContentType()))) {
            throw invalid("Bulk import accepts only a valid XLSX file.");
        }

        boolean contentTypesFound = false;
        boolean workbookFound = false;
        int entryCount = 0;
        try (ZipInputStream zip = new ZipInputStream(new BufferedInputStream(file.getInputStream()))) {
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                if (++entryCount > 10000) {
                    throw invalid("XLSX archive contains too many entries.");
                }
                String name = entry.getName().replace('\\', '/').toLowerCase(Locale.ROOT);
                if (name.startsWith("/") || name.contains("../")) {
                    throw invalid("Unsafe XLSX archive path detected.");
                }
                if (name.endsWith("vbaproject.bin") || name.endsWith(".js") || name.endsWith(".html")
                        || name.endsWith(".htm") || name.endsWith(".svg")) {
                    throw invalid("Active or macro content is not allowed in XLSX uploads.");
                }
                contentTypesFound |= "[content_types].xml".equals(name);
                workbookFound |= "xl/workbook.xml".equals(name);
            }
        } catch (IOException ex) {
            throw new InvalidFileUploadException("Unable to inspect XLSX upload.", ex);
        }
        if (!contentTypesFound || !workbookFound) {
            throw invalid("File signature or XLSX structure is invalid.");
        }
    }

    public static String createDocumentStorageName(MultipartFile file) {
        validateDocument(file);
        return UUID.randomUUID() + "." + extension(file);
    }

    public static String storeDocument(MultipartFile file, Path directory) throws IOException {
        String storedName = createDocumentStorageName(file);
        Path safeDirectory = directory.toAbsolutePath().normalize();
        Files.createDirectories(safeDirectory);
        Path destination = safeDirectory.resolve(storedName).normalize();
        if (!destination.getParent().equals(safeDirectory)) {
            throw invalid("Unsafe upload destination.");
        }
        try (InputStream input = file.getInputStream()) {
            Files.copy(input, destination);
        }
        return storedName;
    }

    public static String validateImageBytesAndCreateName(byte[] bytes) {
        if (bytes == null || bytes.length == 0 || bytes.length > MAX_DOCUMENT_SIZE) {
            throw invalid("Image is empty or exceeds the 10 MB limit.");
        }
        if (startsWith(bytes, new byte[] { (byte) 0xFF, (byte) 0xD8, (byte) 0xFF })) {
            return UUID.randomUUID() + ".jpg";
        }
        if (startsWith(bytes, new byte[] { (byte) 0x89, 'P', 'N', 'G', 0x0D, 0x0A, 0x1A, 0x0A })) {
            return UUID.randomUUID() + ".png";
        }
        throw invalid("Only valid JPEG or PNG image content is allowed.");
    }

    private static void validateBasic(MultipartFile file, long maxSize) {
        if (file == null || file.isEmpty()) {
            throw invalid("Uploaded file is empty.");
        }
        if (file.getSize() > maxSize) {
            throw invalid("Uploaded file exceeds the permitted size limit.");
        }
        String originalName = file.getOriginalFilename();
        if (originalName == null || originalName.isBlank() || originalName.indexOf('\0') >= 0) {
            throw invalid("Uploaded file name is invalid.");
        }
        String normalized = originalName.replace('\\', '/');
        if (normalized.contains("../") || normalized.startsWith("/")) {
            throw invalid("Unsafe uploaded file name.");
        }
    }

    private static String extension(MultipartFile file) {
        String name = file.getOriginalFilename();
        int index = name == null ? -1 : name.lastIndexOf('.');
        if (index <= 0 || index == name.length() - 1) {
            throw invalid("Uploaded file must have an allowed extension.");
        }
        return name.substring(index + 1).toLowerCase(Locale.ROOT);
    }

    private static String normalizedMime(String mime) {
        if (mime == null) {
            return "";
        }
        int parameters = mime.indexOf(';');
        return (parameters >= 0 ? mime.substring(0, parameters) : mime).trim().toLowerCase(Locale.ROOT);
    }

    private static boolean startsWith(byte[] value, byte[] prefix) {
        if (value.length < prefix.length) {
            return false;
        }
        for (int i = 0; i < prefix.length; i++) {
            if (value[i] != prefix[i]) {
                return false;
            }
        }
        return true;
    }

    private static InvalidFileUploadException invalid(String message) {
        return new InvalidFileUploadException(message);
    }
}
