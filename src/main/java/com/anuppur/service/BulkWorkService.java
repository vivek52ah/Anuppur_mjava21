package com.anuppur.service;

import org.springframework.web.multipart.MultipartFile;
import com.anuppur.bean.BulkUploadResultBean;

public interface BulkWorkService {
    byte[] generateTemplate();
    BulkUploadResultBean processUpload(MultipartFile file, String username);
}
