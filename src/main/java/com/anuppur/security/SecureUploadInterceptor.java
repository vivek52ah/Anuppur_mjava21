package com.anuppur.security;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecureUploadInterceptor implements HandlerInterceptor {

    private static final String BULK_UPLOAD_PATH = "/systemAdmin/bulkWork/upload";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(request instanceof MultipartHttpServletRequest multipartRequest)) {
            return true;
        }

        boolean bulkSpreadsheet = request.getRequestURI().endsWith(BULK_UPLOAD_PATH);
        for (List<MultipartFile> files : multipartRequest.getMultiFileMap().values()) {
            for (MultipartFile file : files) {
                // Empty optional file inputs are ignored; mandatory inputs are still checked by controllers.
                if (file == null || file.isEmpty()) {
                    continue;
                }
                if (bulkSpreadsheet) {
                    SecureFileUploadPolicy.validateBulkSpreadsheet(file);
                } else {
                    SecureFileUploadPolicy.validateDocument(file);
                }
            }
        }
        return true;
    }
}
