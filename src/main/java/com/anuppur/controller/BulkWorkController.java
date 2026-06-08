package com.anuppur.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.anuppur.bean.BulkUploadResultBean;
import com.anuppur.service.BulkWorkService;
import com.anuppur.util.DMSUtil;

@RestController
@RequestMapping("/systemAdmin/bulkWork")
@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
public class BulkWorkController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BulkWorkController.class);

    @Autowired
    private BulkWorkService bulkWorkService;

    @Value("${document.root}")
    private String documentRoot;

    @Value("${document.technical}")
    private String documentTechnical;

    @Value("${document.administrator}")
    private String documentAdministrator;

    @GetMapping("/uploadPage")
    public ModelAndView uploadPage(HttpServletRequest request) {
        return new ModelAndView("systemAdmin/bulkWorkUpload");
    }

    @GetMapping("/downloadTemplate")
    public ResponseEntity<byte[]> downloadTemplate() {
        try {
            byte[] templateBytes = bulkWorkService.generateTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("attachment", "BulkWorkCreationTemplate.xlsx");
            return new ResponseEntity<>(templateBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to generate template", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<BulkUploadResultBean> upload(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request) {
        String username = DMSUtil.getUserDetail() != null
                ? DMSUtil.getUserDetail().getUsername()
                : "unknown";
        BulkUploadResultBean result = bulkWorkService.processUpload(file, username);
        return ResponseEntity.ok(result);
    }

    /** Upload a Technical Sanction (TS) document */
    @PostMapping("/uploadTsDocument")
    public ResponseEntity<Map<String, String>> uploadTsDocument(
            @RequestParam("file") MultipartFile file) {
        return saveDocument(file, documentRoot + documentTechnical);
    }

    /** Upload an Administrative Sanction (AS) document */
    @PostMapping("/uploadAsDocument")
    public ResponseEntity<Map<String, String>> uploadAsDocument(
            @RequestParam("file") MultipartFile file) {
        return saveDocument(file, documentRoot + documentAdministrator);
    }

    private ResponseEntity<Map<String, String>> saveDocument(MultipartFile file, String dirPath) {
        try {
            String originalName = file.getOriginalFilename();
            String ext = "";
            if (originalName != null && originalName.contains(".")) {
                ext = originalName.substring(originalName.lastIndexOf('.'));
            }
            String storedName = UUID.randomUUID().toString() + ext;

            Path dir = Paths.get(dirPath);
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }

            Path dest = dir.resolve(storedName);
            Files.copy(file.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);

            Map<String, String> response = new HashMap<>();
            response.put("fileName", storedName);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            logger.error("Failed to upload document to {}: {}", dirPath, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
