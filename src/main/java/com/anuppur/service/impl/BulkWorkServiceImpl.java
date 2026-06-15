package com.anuppur.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.anuppur.bean.BulkUploadResultBean;
import com.anuppur.entity.Block;
import com.anuppur.entity.District;
import com.anuppur.entity.FinancialHead;
import com.anuppur.entity.FinancialYear;
import com.anuppur.entity.GramPanchayat;
import com.anuppur.entity.ImplementationAgency;
import com.anuppur.entity.Schemes;
import com.anuppur.entity.VidhanSabha;
import com.anuppur.entity.Work;
import com.anuppur.entity.WorkCategory;
import com.anuppur.entity.WorkHead;
import com.anuppur.entity.WorkPriority;
import com.anuppur.entity.WorkStatus;
import com.anuppur.entity.WorkSubType;
import com.anuppur.entity.WorkType;
import com.anuppur.entity.Division;
import com.anuppur.repository.BlockRepository;
import com.anuppur.repository.DivisionRepository;
import com.anuppur.repository.DistrictRepository;
import com.anuppur.repository.FinancialHeadRepository;
import com.anuppur.repository.FinancialYearRepository;
import com.anuppur.repository.GramPanchayatRepository;
import com.anuppur.repository.ImplAgencyRepository;
import com.anuppur.repository.SchemeRepository;
import com.anuppur.repository.VidhanSabhaRepository;
import com.anuppur.repository.WorkCategoryRepository;
import com.anuppur.repository.WorkHeadRepository;
import com.anuppur.repository.WorkPriorityRepository;
import com.anuppur.repository.WorkRepository;
import com.anuppur.repository.WorkStatusRepository;
import com.anuppur.repository.WorkSubTypeRepository;
import com.anuppur.repository.WorkTypeRepository;
import com.anuppur.bean.BulkWorkRowBean;
import com.anuppur.bean.RowErrorBean;
import com.anuppur.service.BulkWorkService;
import com.anuppur.service.ExcelParser;
import com.anuppur.service.ExcelTemplateGenerator;
import com.anuppur.service.MasterDataCache;
import com.anuppur.service.RowValidator;
import com.anuppur.service.ValidationReportGenerator;
import com.anuppur.service.WorkMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional
public class BulkWorkServiceImpl implements BulkWorkService {

    private static final Logger logger = LoggerFactory.getLogger(BulkWorkServiceImpl.class);

    private static final long MAX_FILE_SIZE = 5L * 1024 * 1024;
    private static final int MAX_ROWS = 500;
    private static final Set<String> UPLOADS_IN_PROGRESS = ConcurrentHashMap.newKeySet();

    @Autowired
    private FinancialYearRepository financialYearRepository;

    @Autowired
    private SchemeRepository schemeRepository;

    @Autowired
    private WorkTypeRepository workTypeRepository;

    @Autowired
    private WorkCategoryRepository workCategoryRepository;

    @Autowired
    private WorkSubTypeRepository workSubTypeRepository;

    @Autowired
    private WorkStatusRepository workStatusRepository;

    @Autowired
    private ImplAgencyRepository implAgencyRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private DivisionRepository divisionRepository;

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private GramPanchayatRepository gramPanchayatRepository;

    @Autowired
    private WorkHeadRepository workHeadRepository;

    @Autowired
    private WorkPriorityRepository workPriorityRepository;

    @Autowired
    private FinancialHeadRepository financialHeadRepository;

    @Autowired
    private VidhanSabhaRepository vidhanSabhaRepository;

    @Autowired
    private WorkRepository workRepository;

    @Override
    public byte[] generateTemplate() {
        MasterDataCache cache = buildCache();
        try {
            return new ExcelTemplateGenerator().generate(cache);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Excel template", e);
        }
    }

    @Override
    public BulkUploadResultBean processUpload(MultipartFile file, String username) {
        // Guard 1: file size
        if (file.getSize() > MAX_FILE_SIZE) {
            return errorResult("File size must not exceed 5 MB");
        }

        byte[] uploadBytes;
        String uploadKey;
        try {
            uploadBytes = file.getBytes();
            uploadKey = username + ":" + sha256(uploadBytes);
        } catch (Exception e) {
            logger.warn("Failed to read uploaded file: {}", e.getMessage());
            return errorResult("The uploaded file could not be read");
        }

        if (!UPLOADS_IN_PROGRESS.add(uploadKey)) {
            return errorResult("This Excel file is already being processed. Please wait for the current upload to finish.");
        }

        try {
            // Guard 2 + parse
            List<BulkWorkRowBean> rows;
            try {
                rows = new ExcelParser().parse(new ByteArrayInputStream(uploadBytes));
            } catch (Exception e) {
                logger.warn("Failed to parse uploaded file: {}", e.getMessage());
                return errorResult("The uploaded file is not a valid Excel (.xlsx) file");
            }

            // Guard 3: no data rows
            if (rows.isEmpty()) {
                return errorResult("The uploaded file contains no data rows");
            }

            // Guard 4: too many rows
            if (rows.size() > MAX_ROWS) {
                return errorResult("A maximum of 500 rows are allowed per upload");
            }

            // 8.1 Load MasterDataCache
            MasterDataCache cache = buildCache();

            // 8.2 Validate each row
            RowValidator validator = new RowValidator();
            List<RowErrorBean> allErrors = new ArrayList<>();
            List<BulkWorkRowBean> validRows = new ArrayList<>();

            for (BulkWorkRowBean row : rows) {
                List<RowErrorBean> rowErrors = validator.validate(row, cache);
                if (rowErrors.isEmpty()) {
                    validRows.add(row);
                } else {
                    allErrors.addAll(rowErrors);
                }
            }

            // 8.3 Map and persist valid rows
            WorkMapper mapper = new WorkMapper();
            List<Work> worksToSave = new ArrayList<>();
            for (BulkWorkRowBean row : validRows) {
                worksToSave.add(mapper.map(row, cache, username));
            }
            if (!worksToSave.isEmpty()) {
                workRepository.saveAll(worksToSave);
            }

            // 8.4 Assemble result
            int total = rows.size();
            int successCount = worksToSave.size();
            int failureCount = total - successCount;

            BulkUploadResultBean result = new BulkUploadResultBean();
            result.setTotalRows(total);
            result.setSuccessCount(successCount);
            result.setFailureCount(failureCount);
            result.setErrors(allErrors);

            if (failureCount == 0) {
                result.setMessage("All " + successCount + " work records created successfully");
            } else if (successCount == 0) {
                result.setMessage("No records were created. Please review the validation report");
            } else {
                result.setMessage(successCount + " work records created successfully. " + failureCount + " rows had errors.");
            }

            // 8.4 Generate validation report if any errors
            if (!allErrors.isEmpty()) {
                try {
                    result.setValidationReportBase64(new ValidationReportGenerator().generateBase64Report(allErrors));
                } catch (Exception e) {
                    logger.error("Failed to generate validation report", e);
                }
            }

            // 8.5 Log
            logger.info("Bulk upload by user={}, timestamp={}, totalRows={}, successCount={}, failureCount={}",
                    username, java.time.LocalDateTime.now(), total, successCount, failureCount);

            return result;
        } finally {
            UPLOADS_IN_PROGRESS.remove(uploadKey);
        }
    }

    private String sha256(byte[] bytes) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(bytes);
            StringBuilder hex = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (Exception e) {
            throw new IllegalStateException("SHA-256 algorithm is not available", e);
        }
    }

    private MasterDataCache buildCache() {
        MasterDataCache cache = new MasterDataCache();

        // Division ID = 3 (hardcoded — same as addWorkDetail page: loadDivisions sets divisionId='3')
        final long DIVISION_ID = 3L;
        // District code = 461, district ID = 49 (hardcoded — same as loadDistrictsWork)
        final String DISTRICT_CODE = "461";

        for (FinancialYear fy : financialYearRepository.findAll()) {
            if (fy.getFinancialYear() != null) {
                cache.financialYearMap.put(fy.getFinancialYear(), fy.getId());
            }
        }
        for (Schemes s : schemeRepository.findAll()) {
            if (s.getSchemeName() != null) {
                cache.schemeMap.put(s.getSchemeName(), s.getId());
            }
        }
        for (WorkType wt : workTypeRepository.findAll()) {
            if (wt.getWorkTypeNameE() != null) {
                cache.workTypeMap.put(wt.getWorkTypeNameE(), wt.getWorkTypeId());
            }
        }
        for (WorkCategory wc : workCategoryRepository.findAll()) {
            if (wc.getWorkCategoryNameE() != null) {
                cache.workCategoryMap.put(wc.getWorkCategoryNameE(), wc.getWorkCategoryId());
            }
        }
        for (WorkSubType wst : workSubTypeRepository.findAll()) {
            if (wst.getWorkSubTypeNameE() != null) {
                cache.workSubTypeMap.put(wst.getWorkSubTypeNameE(), wst.getWorkSubtypeId());
            }
        }
        for (WorkStatus ws : workStatusRepository.findAll()) {
            if (ws.getWorkStatusNameE() != null) {
                cache.workStatusMap.put(ws.getWorkStatusNameE(), ws.getId());
            }
        }
        // Executive Agency — only enabled=1 (mirrors fetchConstructionAgency which filters enabled)
        for (ImplementationAgency ia : implAgencyRepository.findByEnabled((short) 1)) {
            if (ia.getImplAgencyname() != null) {
                cache.implAgencyMap.put(ia.getImplAgencyname(), ia.getImplementationAgencyId());
            }
        }

        // Districts filtered by Division ID = 3 (mirrors fetchDistrictByDivision/3)
        Division division = divisionRepository.findById(DIVISION_ID).orElse(null);
        if (division != null) {
            for (District d : districtRepository.findByDivisionAndEnabled(division, (short) 1)) {
                if (d.getDistrictName() != null) {
                    cache.districtMap.put(d.getDistrictName(), d.getDistrictId());
                    if (d.getDistrictCode() != null) {
                        cache.districtCodeMap.put(d.getDistrictName(), d.getDistrictCode());
                    }
                }
            }
        }

        // Blocks filtered by district code 461 (mirrors fetchBlocksByDistrict/461)
        District anuppurDistrict = districtRepository.findByDistrictCodeAndEnabled(DISTRICT_CODE, (short) 1);
        if (anuppurDistrict != null) {
            for (Block b : blockRepository.findByDistrictAndEnabledOrderByBlockName(anuppurDistrict, (short) 1)) {
                if (b.getBlockName() != null) {
                    cache.blockMap.put(b.getBlockName(), b.getBlockId());
                    if (b.getBlockCode() != null) {
                        cache.blockCodeMap.put(b.getBlockName(), b.getBlockCode());
                    }
                }
            }
        }

        // GPs filtered by district code 461 (mirrors fetchGramPanchayatByBlockCode/461/{blockCode})
        for (GramPanchayat gp : gramPanchayatRepository.findByOptionalDistrictAndBlockCode(DISTRICT_CODE, null, (short) 1)) {
            if (gp.getGramPanchayatName() != null) {
                cache.gramPanchayatMap.put(gp.getGramPanchayatName(), gp.getGramPanchayatId());
                if (gp.getGramPanchayatCode() != null) {
                    cache.gramPanchayatCodeMap.put(gp.getGramPanchayatName(), gp.getGramPanchayatCode());
                }
            }
        }

        for (WorkHead wh : workHeadRepository.findAll()) {
            if (wh.getHeadName() != null) {
                cache.workHeadMap.put(wh.getHeadName(), wh.getId());
            }
        }
        for (WorkPriority wp : workPriorityRepository.findAll()) {
            if (wp.getWorkPriorityName() != null) {
                cache.workPriorityMap.put(wp.getWorkPriorityName(), wp.getId());
            }
        }
        for (FinancialHead fh : financialHeadRepository.findAll()) {
            if (fh.getFinancialHeadName() != null) {
                cache.financialHeadMap.put(fh.getFinancialHeadName(), fh.getId());
            }
        }
        for (VidhanSabha vs : vidhanSabhaRepository.findAll()) {
            if (vs.getVidhanSabhaName() != null) {
                cache.vidhanSabhaMap.put(vs.getVidhanSabhaName(), vs.getId());
            }
        }
        for (Work w : workRepository.findAll()) {
            if (w.getWorkNo() != null) {
                cache.existingWorkNos.add(w.getWorkNo());
            }
        }

        // Store division/district constants for WorkMapper
        cache.divisionId = DIVISION_ID;
        cache.districtCode = DISTRICT_CODE;

        return cache;
    }

    private BulkUploadResultBean errorResult(String message) {
        BulkUploadResultBean result = new BulkUploadResultBean();
        result.setMessage(message);
        result.setTotalRows(0);
        result.setSuccessCount(0);
        result.setFailureCount(0);
        return result;
    }
}
