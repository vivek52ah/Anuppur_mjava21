package com.anuppur.security;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.web.server.ResponseStatusException;

class SecureUploadInterceptorTest {

    private final SecureUploadInterceptor interceptor = new SecureUploadInterceptor();

    @Test
    void centrallyRejectsActiveContentBeforeController() {
        MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
        request.setRequestURI("/anuppur/addWorkProgress");
        request.addFile(new MockMultipartFile("file", "payload.js", "application/javascript",
                "alert(1)".getBytes(StandardCharsets.UTF_8)));

        assertThrows(ResponseStatusException.class,
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
}
