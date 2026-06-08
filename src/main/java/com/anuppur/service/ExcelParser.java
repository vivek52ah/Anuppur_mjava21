package com.anuppur.service;

import com.anuppur.bean.BulkWorkRowBean;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses the uploaded Excel file into BulkWorkRowBean list.
 * Column order must match ExcelTemplateGenerator exactly.
 */
public class ExcelParser {

    private static final int TOTAL_COLUMNS = 25;

    public List<BulkWorkRowBean> parse(InputStream is) throws Exception {
        List<BulkWorkRowBean> rows = new ArrayList<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) continue;

                String[] v = new String[TOTAL_COLUMNS];
                for (int col = 0; col < TOTAL_COLUMNS; col++) {
                    v[col] = getCellStringValue(row.getCell(col));
                }

                // Skip completely empty rows
                boolean allBlank = true;
                for (String s : v) {
                    if (s != null && !s.isEmpty()) { allBlank = false; break; }
                }
                if (allBlank) continue;

                BulkWorkRowBean bean = new BulkWorkRowBean();
                bean.setRowNumber(rowIndex);
                bean.setWorkName(v[0]);
                bean.setWorkTypeName(v[1]);
                bean.setFinancialYearName(v[2]);
                bean.setWorkSubTypeName(v[3]);
                bean.setIsTender(v[4]);
                bean.setImplementationAgencyName(v[5]);
                bean.setDistrictName(v[6]);
                bean.setBlockName(v[7]);
                bean.setGramPanchayatName(v[8]);
                bean.setVidhanSabhaName(v[9]);
                bean.setWorkStatusName(v[10]);
                bean.setWorkCategoryName(v[11]);
                bean.setWorkNo(v[12]);
                bean.setEstimatedAmount(v[13]);
                bean.setAmtReleasedTillDate(v[14]);
                bean.setAllocatedAmount(v[15]);
                bean.setFinancialHeadName(v[16]);
                bean.setTsNo(v[17]);
                bean.setTsDate(v[18]);
                bean.setTsAmt(v[19]);
                bean.setAsNo(v[20]);
                bean.setAsDate(v[21]);
                bean.setAsAmt(v[22]);
                bean.setTsDocumentPath(v[23]);
                bean.setAsDocumentPath(v[24]);

                rows.add(bean);
            }
        }

        return rows;
    }

    private String getCellStringValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case NUMERIC:
                return new DataFormatter().formatCellValue(cell).trim();
            case STRING:
                return cell.getStringCellValue().trim();
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try { return cell.getStringCellValue().trim(); }
                catch (Exception e) {
                    try { return String.valueOf(cell.getNumericCellValue()); }
                    catch (Exception ex) { return ""; }
                }
            default:
                return "";
        }
    }
}
