package com.anuppur.service;

import com.anuppur.bean.RowErrorBean;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

public class ValidationReportGenerator {

    public String generateBase64Report(List<RowErrorBean> errors) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Validation Errors");

            // Header row
            XSSFRow header = sheet.createRow(0);
            header.createCell(0).setCellValue("Row Number");
            header.createCell(1).setCellValue("Column Name");
            header.createCell(2).setCellValue("Error Message");

            // Data rows
            int rowIndex = 1;
            for (RowErrorBean error : errors) {
                XSSFRow row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(error.getRowNumber());
                row.createCell(1).setCellValue(error.getColumnName());
                row.createCell(2).setCellValue(error.getErrorMessage());
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return Base64.getEncoder().encodeToString(out.toByteArray());
        }
    }
}
