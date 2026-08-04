package com.anuppur.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class SecureDownloadHeadersFilterTest {

    @Test
    void forcesUploadedContentToDownloadAndDisablesSniffing() throws Exception {
        SecureDownloadHeadersFilter filter = new SecureDownloadHeadersFilter();
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/anuppur/downloadDocumentWSPro/10");
        MockHttpServletResponse response = new MockHttpServletResponse();

        filter.doFilter(request, response, (req, res) ->
                ((jakarta.servlet.http.HttpServletResponse) res).setHeader(
                        "Content-Disposition", "inline; filename=photo.jpg"));

        assertEquals("attachment; filename=photo.jpg", response.getHeader("Content-Disposition"));
        assertEquals("nosniff", response.getHeader("X-Content-Type-Options"));
    }

    @Test
    void disablesInlineRemarkPreviewOnApplicationOrigin() throws Exception {
        SecureDownloadHeadersFilter filter = new SecureDownloadHeadersFilter();
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/anuppur/previewDocumentRemarks/10");
        MockHttpServletResponse response = new MockHttpServletResponse();

        filter.doFilter(request, response, (req, res) -> {
            jakarta.servlet.http.HttpServletResponse httpResponse =
                    (jakarta.servlet.http.HttpServletResponse) res;
            httpResponse.addHeader("Content-Disposition", "inline; filename=evidence.pdf");
        });

        assertEquals("attachment; filename=evidence.pdf", response.getHeader("Content-Disposition"));
        Collection<String> dispositions = response.getHeaders("Content-Disposition");
        assertEquals(1, dispositions.size());
    }
}
