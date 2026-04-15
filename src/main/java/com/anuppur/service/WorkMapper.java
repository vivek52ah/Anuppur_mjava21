package com.anuppur.service;

import com.anuppur.bean.BulkWorkRowBean;
import com.anuppur.entity.Work;

import java.math.BigDecimal;

public class WorkMapper {

    public Work map(BulkWorkRowBean row, MasterDataCache cache, String username) {
        Work work = new Work();

        // Work Name & No
        work.setWorkName(row.getWorkName());
        work.setWorkNo(row.getWorkNo());

        // Type of Work -> workType + worTypeId
        Long workTypeId = cache.workTypeMap.get(row.getWorkTypeName());
        work.setWorkType(workTypeId);
        work.setWorTypeId(workTypeId);

        // FY of Sanction
        work.setFinancialYear(cache.financialYearMap.get(row.getFinancialYearName()));

        // Sub Work Type
        work.setWorkSubtypeId(cache.workSubTypeMap.get(row.getWorkSubTypeName()));

        // Is Tender (1 = true, 0 = false)
        if (!isBlank(row.getIsTender())) {
            work.setIsTenders("1".equals(row.getIsTender().trim()));
        }

        // Executive Agency
        work.setImplementationAgency(cache.implAgencyMap.get(row.getImplementationAgencyName()));

        // District — always division 3, district code 461, district ID 49
        work.setDivisionId(cache.divisionId);
        work.setDistrictId(cache.districtMap.get(row.getDistrictName()));
        work.setDistrictCode(cache.districtCodeMap.get(row.getDistrictName()));

        // Block
        work.setBlockId(cache.blockMap.get(row.getBlockName()));
        work.setBlockCode(cache.blockCodeMap.get(row.getBlockName()));

        // Gram Panchayat (optional)
        if (!isBlank(row.getGramPanchayatName())) {
            work.setGramPanchayatId(cache.gramPanchayatMap.get(row.getGramPanchayatName()));
            work.setGramPanchayatCode(cache.gramPanchayatCodeMap.get(row.getGramPanchayatName()));
        }

        // Vidhan Sabha (optional)
        if (!isBlank(row.getVidhanSabhaName())) {
            work.setVidhanSabhaId(cache.vidhanSabhaMap.get(row.getVidhanSabhaName()));
        }

        // Work Status
        work.setWorkStatus(cache.workStatusMap.get(row.getWorkStatusName()));

        // Work Category (optional)
        if (!isBlank(row.getWorkCategoryName())) {
            work.setWorkCategoryId(cache.workCategoryMap.get(row.getWorkCategoryName()));
        }

        // Financial amounts
        work.setEstimatedAmt(new BigDecimal(row.getEstimatedAmount().trim()));
        work.setAmtReleasedTillDate(parseBigDecimal(row.getAmtReleasedTillDate()));
        work.setAllocatedAmount(parseBigDecimal(row.getAllocatedAmount()));

        // Financial Head (Financing Agency)
        if (!isBlank(row.getFinancialHeadName())) {
            work.setFinancialHeadId(cache.financialHeadMap.get(row.getFinancialHeadName()));
        }

        // TS Details
        work.setTsNo(row.getTsNo());
        work.setTsDate(normalizeDate(row.getTsDate()));
        work.setTsAmt(parseBigDecimal(row.getTsAmt()));

        // AS Details
        work.setAsNo(row.getAsNo());
        work.setAsDate(normalizeDate(row.getAsDate()));
        work.setAsAmt(parseBigDecimal(row.getAsAmt()));

        // Programmatic fields
        work.setStatus("A");
        work.setCreatedBy(username);

        return work;
    }

    private BigDecimal parseBigDecimal(String s) {
        if (isBlank(s)) return null;
        try { return new BigDecimal(s.trim()); }
        catch (NumberFormatException e) { return null; }
    }

    /** Normalize DD-MM-YY or DD-MM-YYYY to DD/MM/YYYY */
    private String normalizeDate(String raw) {
        if (isBlank(raw)) return raw;
        String s = raw.trim().replace('-', '/').replace('.', '/');
        String[] parts = s.split("/");
        if (parts.length == 3 && parts[2].length() == 2) {
            s = parts[0] + "/" + parts[1] + "/20" + parts[2];
        }
        return s;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
