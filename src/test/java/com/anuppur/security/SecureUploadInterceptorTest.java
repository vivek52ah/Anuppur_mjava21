package com.anuppur.security;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;

class SecureUploadInterceptorTest {

    private final SecureUploadInterceptor interceptor = new SecureUploadInterceptor();

    @Test
    void centrallyRejectsActiveContentBeforeController() {
        MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
        request.setRequestURI("/anuppur/addWorkProgress");
        request.addFile(new MockMultipartFile("file", "payload.js", "application/javascript",
                "alert(1)".getBytes(StandardCharsets.UTF_8)));

        assertThrows(InvalidFileUploadException.class,
                () -> interceptor.preHandle(request, new MockHttpServletResponse(), new Object()));
    }

    @Test
    void acceptsValidDocumentForNormalUploadEndpoint() {
        MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
        request.setRequestURI("/anuppur/addWorkProgress");
        request.addFile(new MockMultipartFile("file", "evidence.pdf", "application/pdf",
                "%PDF-1.7\ncontent".getBytes(StandardCharsets.US_ASCII)));

        assertDoesNotThrow(() -> interceptor.preHandle(request, new MockHttpServletResponse(), new Object()));
    }

    @Test
    void rejectsNonPdfContentRenamedWithPdfExtensionWithActionableMessage() {
        MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
        request.setRequestURI("/systemAdmin/saveOrUpdateDepartment");
        request.addFile(new MockMultipartFile("dmattachment", "attachment.pdf", "application/pdf",
                new byte[] { (byte) 0xAC, (byte) 0xED, 0x00, 0x05 }));

        InvalidFileUploadException exception = assertThrows(InvalidFileUploadException.class,
                () -> interceptor.preHandle(request, new MockHttpServletResponse(), new Object()));

        assertEquals("File content does not match its extension and MIME type.", exception.getMessage());
    }
}
