package com.anuppur.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.test.util.ReflectionTestUtils;

import com.anuppur.service.impl.CommonServiceImpl;

class CommonServiceDocumentLookupTest {

    @TempDir
    Path tempDirectory;

    @Test
    void locatesFilesWrittenByLegacyConcatenatedWindowsPathConfiguration() throws Exception {
        String configuredRoot = tempDirectory.resolve("documents").toString();
        String relativeDirectory = "WORK/DMAttachement/";
        Path legacyDirectory = Path.of(configuredRoot + relativeDirectory);
        Files.createDirectories(legacyDirectory);
        Path storedFile = Files.writeString(legacyDirectory.resolve("evidence.pdf"), "%PDF-test");

        CommonServiceImpl service = new CommonServiceImpl();
        ReflectionTestUtils.setField(service, "documentRootPath", configuredRoot);

        String resolved = ReflectionTestUtils.invokeMethod(service, "locateStoredDocument",
                "evidence.pdf", null, List.of(relativeDirectory));

        assertThat(Path.of(resolved)).isEqualTo(storedFile.toAbsolutePath().normalize());
    }
}
