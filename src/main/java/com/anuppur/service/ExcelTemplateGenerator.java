package com.anuppur.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates the BulkWorkCreationTemplate.xlsx.
 * Column layout mirrors the addWorkDetail page exactly.
 *
 * Data Entry sheet columns (0-based):
 *  0  Work Name *
 *  1  Type of Work *          (dropdown -> RefData col 0)
 *  2  FY of Sanction *        (dropdown -> RefData col 1)
 *  3  Sub Work Type *         (dropdown -> RefData col 2)
 *  4  Is Tender *             (dropdown: 1=Tender Work, 0=Non-Tender Work)
 *  5  Executive Agency *      (dropdown -> RefData col 3)
 *  6  District *              (dropdown -> RefData col 4)
 *  7  Block *                 (dropdown -> RefData col 5)
 *  8  Gram Panchayat          (dropdown -> RefData col 6)
 *  9  Vidhan Sabha            (dropdown -> RefData col 7)
 * 10  Work Status *           (dropdown -> RefData col 8)
 * 11  Work Category           (dropdown -> RefData col 9)
 * 12  Work No
 * 13  Estimated Amount *
 * 14  Amount Released Till Date
 * 15  Allocated Amount
 * 16  Financial Head (Financing Agency) (dropdown -> RefData col 10)
 * 17  TS Order No
 * 18  TS Order Date (DD/MM/YYYY)
 * 19  TS Amount (In Lacs)
 * 20  AS Order No
 * 21  AS Order Date (DD/MM/YYYY)
 * 22  AS Amount (In Lacs)
 * 23  TS Document Path
 * 24  AS Document Path
 */
public class ExcelTemplateGenerator {

    // Column headers — * = mandatory
    private static final String[] HEADERS = {
        "Work Name *",                        // 0
        "Type of Work *",                     // 1  dropdown
        "FY of Sanction *",                   // 2  dropdown
        "Sub Work Type *",                    // 3  dropdown
        "Is Tender * (1=Tender/0=Non-Tender)",// 4  dropdown
        "Executive Agency *",                 // 5  dropdown
        "District *",                         // 6  dropdown
        "Block *",                            // 7  dropdown
        "Gram Panchayat",                     // 8  dropdown
        "Vidhan Sabha",                       // 9  dropdown
        "Work Status *",                      // 10 dropdown
        "Work Category",                      // 11 dropdown
        "Work No",                            // 12
        "Estimated Amount *",                 // 13
        "Amount Released Till Date",          // 14
        "Allocated Amount",                   // 15
        "Financial Head (Financing Agency)",  // 16 dropdown
        "TS Order No",                        // 17
        "TS Order Date (DD/MM/YYYY)",         // 18
        "TS Amount (In Lacs)",                // 19
        "AS Order No",                        // 20
        "AS Order Date (DD/MM/YYYY)",         // 21
        "AS Amount (In Lacs)",                // 22
        "TS Document Path",                   // 23
        "AS Document Path"                    // 24
    };

    private static final int TOTAL_COLS = HEADERS.length;

    // Mandatory column indices
    private static final int[] MANDATORY_COLS = {0, 1, 2, 3, 4, 5, 6, 7, 10, 13};

    // Reference Data sheet column headers (one per lookup)
    private static final String[] REF_HEADERS = {
        "Type of Work",       // 0
        "FY of Sanction",     // 1
        "Sub Work Type",      // 2
        "Executive Agency",   // 3
        "District",           // 4
        "Block",              // 5
        "Gram Panchayat",     // 6
        "Vidhan Sabha",       // 7
        "Work Status",        // 8
        "Work Category",      // 9
        "Financial Head"      // 10
    };

    // [dataCol, refDataCol] — which Data Entry column gets a dropdown from which RefData column
    private static final int[][] DROPDOWN_MAPPINGS = {
        {1,  0},  // Type of Work       -> RefData col 0
        {2,  1},  // FY of Sanction     -> RefData col 1
        {3,  2},  // Sub Work Type      -> RefData col 2
        {5,  3},  // Executive Agency   -> RefData col 3
        {6,  4},  // District           -> RefData col 4
        {7,  5},  // Block              -> RefData col 5
        {8,  6},  // Gram Panchayat     -> RefData col 6
        {9,  7},  // Vidhan Sabha       -> RefData col 7
        {10, 8},  // Work Status        -> RefData col 8
        {11, 9},  // Work Category      -> RefData col 9
        {16, 10}  // Financial Head     -> RefData col 10
    };

    public byte[] generate(MasterDataCache cache) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet dataSheet = workbook.createSheet("Data Entry");
            XSSFSheet refSheet  = workbook.createSheet("Reference Data");

            XSSFCellStyle headerStyle    = createHeaderStyle(workbook, false);
            XSSFCellStyle mandatoryStyle = createHeaderStyle(workbook, true);

            // Text cell style — forces Excel to treat cell as plain text (prevents date auto-conversion)
            XSSFCellStyle textStyle = workbook.createCellStyle();
            DataFormat fmt = workbook.createDataFormat();
            textStyle.setDataFormat(fmt.getFormat("@"));

            // Header row
            XSSFRow headerRow = dataSheet.createRow(0);
            XSSFCreationHelper factory = workbook.getCreationHelper();
            Drawing<?> drawing = dataSheet.createDrawingPatriarch();

            for (int i = 0; i < TOTAL_COLS; i++) {
                XSSFCell cell = headerRow.createCell(i);
                cell.setCellValue(HEADERS[i]);
                boolean isMandatory = isMandatory(i);
                cell.setCellStyle(isMandatory ? mandatoryStyle : headerStyle);
                dataSheet.setColumnWidth(i, 6500);
            }

            // Apply text format to date columns (18=TS Date, 21=AS Date) for all data rows
            // This prevents Excel from auto-converting DD/MM/YYYY to a date serial
            int[] dateCols = {18, 21};
            for (int dateCol : dateCols) {
                for (int r = 1; r <= 500; r++) {
                    XSSFRow row = dataSheet.getRow(r);
                    if (row == null) row = dataSheet.createRow(r);
                    XSSFCell cell = row.getCell(dateCol);
                    if (cell == null) cell = row.createCell(dateCol);
                    cell.setCellStyle(textStyle);
                }
                // Also set column-level default style
                dataSheet.setDefaultColumnStyle(dateCol, textStyle);
            }

            // Comments for document columns
            addCellComment(drawing, factory, headerRow, 23,
                "Enter the filename returned after pre-uploading the TS document, or leave blank");
            addCellComment(drawing, factory, headerRow, 24,
                "Enter the filename returned after pre-uploading the AS document, or leave blank");

            // Freeze header row
            dataSheet.createFreezePane(0, 1);

            // Populate Reference Data sheet
            populateReferenceData(refSheet, cache);

            // Add formula-based dropdown validations from Reference Data sheet
            addDropdownValidations(dataSheet, cache);

            // Add explicit dropdown for Is Tender (col 4)
            addExplicitDropdown(dataSheet, 4, new String[]{"1", "0"});

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out.toByteArray();
        }
    }

    private boolean isMandatory(int col) {
        for (int m : MANDATORY_COLS) {
            if (m == col) return true;
        }
        return false;
    }

    private XSSFCellStyle createHeaderStyle(XSSFWorkbook wb, boolean mandatory) {
        XSSFCellStyle style = wb.createCellStyle();
        // Mandatory = dark blue, optional = medium blue
        byte[] color = mandatory
            ? new byte[]{(byte)0x1F, (byte)0x49, (byte)0x7D}   // dark navy
            : new byte[]{(byte)0x44, (byte)0x72, (byte)0xC4};   // standard blue
        style.setFillForegroundColor(new XSSFColor(color, null));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFFont font = wb.createFont();
        font.setBold(true);
        font.setColor(new XSSFColor(new byte[]{(byte)0xFF, (byte)0xFF, (byte)0xFF}, null));
        style.setFont(font);
        style.setWrapText(true);
        return style;
    }

    private void addCellComment(Drawing<?> drawing, XSSFCreationHelper factory,
                                 XSSFRow row, int col, String text) {
        XSSFClientAnchor anchor = factory.createClientAnchor();
        anchor.setCol1(col); anchor.setRow1(0);
        anchor.setCol2(col + 3); anchor.setRow2(4);
        Comment comment = drawing.createCellComment(anchor);
        comment.setString(factory.createRichTextString(text));
        row.getCell(col).setCellComment(comment);
    }

    private void populateReferenceData(XSSFSheet refSheet, MasterDataCache cache) {
        XSSFRow hdr = refSheet.createRow(0);
        for (int i = 0; i < REF_HEADERS.length; i++) {
            hdr.createCell(i).setCellValue(REF_HEADERS[i]);
        }

        List<List<String>> columns = new ArrayList<>();
        columns.add(new ArrayList<>(cache.workTypeMap.keySet()));        // 0
        columns.add(new ArrayList<>(cache.financialYearMap.keySet()));   // 1
        columns.add(new ArrayList<>(cache.workSubTypeMap.keySet()));     // 2
        columns.add(new ArrayList<>(cache.implAgencyMap.keySet()));      // 3
        columns.add(new ArrayList<>(cache.districtMap.keySet()));        // 4
        columns.add(new ArrayList<>(cache.blockMap.keySet()));           // 5
        columns.add(new ArrayList<>(cache.gramPanchayatMap.keySet()));   // 6
        columns.add(new ArrayList<>(cache.vidhanSabhaMap.keySet()));     // 7
        columns.add(new ArrayList<>(cache.workStatusMap.keySet()));      // 8
        columns.add(new ArrayList<>(cache.workCategoryMap.keySet()));    // 9
        columns.add(new ArrayList<>(cache.financialHeadMap.keySet()));   // 10

        int maxRows = 0;
        for (List<String> col : columns) {
            if (col.size() > maxRows) maxRows = col.size();
        }

        for (int r = 0; r < maxRows; r++) {
            XSSFRow row = refSheet.createRow(r + 1);
            for (int c = 0; c < columns.size(); c++) {
                List<String> colData = columns.get(c);
                if (r < colData.size()) {
                    row.createCell(c).setCellValue(colData.get(r));
                }
            }
        }
    }

    private void addDropdownValidations(XSSFSheet dataSheet, MasterDataCache cache) {
        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(dataSheet);

        for (int[] mapping : DROPDOWN_MAPPINGS) {
            int dataCol = mapping[0];
            int refCol  = mapping[1];

            int refRowCount = getRefRowCount(cache, refCol);
            int endRow = Math.max(refRowCount, 1);
            String colLetter = columnLetter(refCol);
            String formula = "'Reference Data'!$" + colLetter + "$2:$" + colLetter + "$" + (endRow + 1);

            XSSFDataValidationConstraint constraint =
                (XSSFDataValidationConstraint) dvHelper.createFormulaListConstraint(formula);

            CellRangeAddressList range = new CellRangeAddressList(1, 500, dataCol, dataCol);
            XSSFDataValidation dv = (XSSFDataValidation) dvHelper.createValidation(constraint, range);
            dv.setShowErrorBox(true);
            dv.createErrorBox("Invalid Value", "Please select a value from the dropdown list");
            dataSheet.addValidationData(dv);
        }
    }

    private void addExplicitDropdown(XSSFSheet dataSheet, int col, String[] values) {
        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(dataSheet);
        XSSFDataValidationConstraint constraint =
            (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(values);
        CellRangeAddressList range = new CellRangeAddressList(1, 500, col, col);
        XSSFDataValidation dv = (XSSFDataValidation) dvHelper.createValidation(constraint, range);
        dv.setShowErrorBox(true);
        dv.createErrorBox("Invalid Value", "Please select 1 (Tender Work) or 0 (Non-Tender Work)");
        dataSheet.addValidationData(dv);
    }

    private int getRefRowCount(MasterDataCache cache, int refCol) {
        switch (refCol) {
            case 0:  return cache.workTypeMap.size();
            case 1:  return cache.financialYearMap.size();
            case 2:  return cache.workSubTypeMap.size();
            case 3:  return cache.implAgencyMap.size();
            case 4:  return cache.districtMap.size();
            case 5:  return cache.blockMap.size();
            case 6:  return cache.gramPanchayatMap.size();
            case 7:  return cache.vidhanSabhaMap.size();
            case 8:  return cache.workStatusMap.size();
            case 9:  return cache.workCategoryMap.size();
            case 10: return cache.financialHeadMap.size();
            default: return 500;
        }
    }

    private String columnLetter(int colIndex) {
        StringBuilder sb = new StringBuilder();
        colIndex++;
        while (colIndex > 0) {
            int rem = (colIndex - 1) % 26;
            sb.insert(0, (char)('A' + rem));
            colIndex = (colIndex - 1) / 26;
        }
        return sb.toString();
    }
}
