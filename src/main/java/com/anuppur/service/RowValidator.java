package com.anuppur.service;

import com.anuppur.bean.BulkWorkRowBean;
import com.anuppur.bean.RowErrorBean;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RowValidator {

    public List<RowErrorBean> validate(BulkWorkRowBean row, MasterDataCache cache) {
        List<RowErrorBean> errors = new ArrayList<>();

        // --- Mandatory blank checks (mirrors addWorkDetail required fields) ---
        checkMandatory(errors, row, row.getWorkName(),                 "Work Name");
        checkMandatory(errors, row, row.getWorkTypeName(),             "Type of Work");
        checkMandatory(errors, row, row.getFinancialYearName(),        "FY of Sanction");
        checkMandatory(errors, row, row.getWorkSubTypeName(),          "Sub Work Type");
        checkMandatory(errors, row, row.getIsTender(),                 "Is Tender");
        checkMandatory(errors, row, row.getImplementationAgencyName(), "Executive Agency");
        checkMandatory(errors, row, row.getDistrictName(),             "District");
        checkMandatory(errors, row, row.getBlockName(),                "Block");
        checkMandatory(errors, row, row.getWorkStatusName(),           "Work Status");
        checkMandatory(errors, row, row.getEstimatedAmount(),          "Estimated Amount");

        // --- Lookup existence checks ---
        if (!isBlank(row.getWorkTypeName()) && !cache.workTypeMap.containsKey(row.getWorkTypeName())) {
            errors.add(error(row, "Type of Work",
                "Work Type '" + row.getWorkTypeName() + "' not found"));
        }
        if (!isBlank(row.getFinancialYearName()) && !cache.financialYearMap.containsKey(row.getFinancialYearName())) {
            errors.add(error(row, "FY of Sanction",
                "Financial Year '" + row.getFinancialYearName() + "' not found"));
        }
        if (!isBlank(row.getWorkSubTypeName()) && !cache.workSubTypeMap.containsKey(row.getWorkSubTypeName())) {
            errors.add(error(row, "Sub Work Type",
                "Sub Work Type '" + row.getWorkSubTypeName() + "' not found"));
        }
        if (!isBlank(row.getImplementationAgencyName()) && !cache.implAgencyMap.containsKey(row.getImplementationAgencyName())) {
            errors.add(error(row, "Executive Agency",
                "Implementation Agency '" + row.getImplementationAgencyName() + "' not found"));
        }
        if (!isBlank(row.getDistrictName()) && !cache.districtMap.containsKey(row.getDistrictName())) {
            errors.add(error(row, "District",
                "District '" + row.getDistrictName() + "' not found"));
        }
        if (!isBlank(row.getBlockName()) && !cache.blockMap.containsKey(row.getBlockName())) {
            errors.add(error(row, "Block",
                "Block '" + row.getBlockName() + "' not found"));
        }
        if (!isBlank(row.getGramPanchayatName()) && !cache.gramPanchayatMap.containsKey(row.getGramPanchayatName())) {
            errors.add(error(row, "Gram Panchayat",
                "Gram Panchayat '" + row.getGramPanchayatName() + "' not found"));
        }
        if (!isBlank(row.getWorkStatusName()) && !cache.workStatusMap.containsKey(row.getWorkStatusName())) {
            errors.add(error(row, "Work Status",
                "Work Status '" + row.getWorkStatusName() + "' not found"));
        }
        if (!isBlank(row.getWorkCategoryName()) && !cache.workCategoryMap.containsKey(row.getWorkCategoryName())) {
            errors.add(error(row, "Work Category",
                "Work Category '" + row.getWorkCategoryName() + "' not found"));
        }
        if (!isBlank(row.getFinancialHeadName()) && !cache.financialHeadMap.containsKey(row.getFinancialHeadName())) {
            errors.add(error(row, "Financial Head",
                "Financial Head '" + row.getFinancialHeadName() + "' not found"));
        }

        // --- Is Tender must be 1 or 0 ---
        if (!isBlank(row.getIsTender())) {
            String t = row.getIsTender().trim();
            if (!t.equals("1") && !t.equals("0")) {
                errors.add(error(row, "Is Tender", "Is Tender must be 1 (Tender Work) or 0 (Non-Tender Work)"));
            }
        }

        // --- Estimated Amount must be positive numeric ---
        if (!isBlank(row.getEstimatedAmount())) {
            try {
                BigDecimal amt = new BigDecimal(row.getEstimatedAmount().trim());
                if (amt.compareTo(BigDecimal.ZERO) <= 0) {
                    errors.add(error(row, "Estimated Amount", "Estimated Amount must be a positive numeric value"));
                }
            } catch (NumberFormatException e) {
                errors.add(error(row, "Estimated Amount", "Estimated Amount must be a positive numeric value"));
            }
        }

        // --- Date format checks (DD/MM/YYYY) ---
        validateDateFormat(errors, row, row.getTsDate(), "TS Order Date");
        validateDateFormat(errors, row, row.getAsDate(), "AS Order Date");

        // --- Work No duplicate check ---
        if (!isBlank(row.getWorkNo()) && cache.existingWorkNos.contains(row.getWorkNo())) {
            errors.add(error(row, "Work No", "Work No '" + row.getWorkNo() + "' already exists"));
        }

        return errors;
    }

    private void checkMandatory(List<RowErrorBean> errors, BulkWorkRowBean row,
                                  String value, String columnName) {
        if (isBlank(value)) {
            errors.add(error(row, columnName, "Column '" + columnName + "' is mandatory"));
        }
    }

    private void validateDateFormat(List<RowErrorBean> errors, BulkWorkRowBean row,
                                     String value, String columnName) {
        if (!isBlank(value)) {
            String normalized = normalizeDate(value.trim());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            try {
                sdf.parse(normalized);
            } catch (ParseException e) {
                errors.add(error(row, columnName, columnName + " must be in DD/MM/YYYY format"));
            }
        }
    }

    /**
     * Normalizes date separators and 2-digit years so DD-MM-YY or DD-MM-YYYY
     * both become DD/MM/YYYY before validation.
     */
    private String normalizeDate(String raw) {
        // Replace hyphens and dots with slashes
        String s = raw.replace('-', '/').replace('.', '/');
        // Handle 2-digit year: DD/MM/YY -> DD/MM/20YY
        String[] parts = s.split("/");
        if (parts.length == 3 && parts[2].length() == 2) {
            s = parts[0] + "/" + parts[1] + "/20" + parts[2];
        }
        return s;
    }

    private RowErrorBean error(BulkWorkRowBean row, String col, String msg) {
        RowErrorBean e = new RowErrorBean();
        e.setRowNumber(row.getRowNumber());
        e.setColumnName(col);
        e.setErrorMessage(msg);
        return e;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
