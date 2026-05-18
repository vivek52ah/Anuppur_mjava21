# Download Error - Diagnostic Guide

## Common Download Issues in the Application

### 1. Excel Export Errors
**Features affected**:
- Export works list to Excel
- Export reports to Excel
- Export user list to Excel

**Common errors**:
- "Failed to download"
- "File not found"
- "Internal server error"
- Empty/corrupted Excel file

### 2. PDF Export Errors
**Features affected**:
- Work reports PDF
- Inspection reports PDF
- Certificate downloads

**Common errors**:
- "PDF generation failed"
- "Template not found"
- Blank PDF file

### 3. Document Download Errors
**Features affected**:
- Work progress images
- Technical documents
- Administrative documents
- Tender documents

**Common errors**:
- "File not found"
- "Access denied"
- "Path not configured"

## How to Diagnose

### Step 1: Check Browser Console
1. Press **F12** to open Developer Tools
2. Go to **Console** tab
3. Try the download again
4. Look for error messages (red text)
5. Share the error with me

### Step 2: Check Network Tab
1. Press **F12** to open Developer Tools
2. Go to **Network** tab
3. Try the download again
4. Find the download request (usually red if failed)
5. Click on it and check:
   - **Status Code** (200 = OK, 404 = Not Found, 500 = Server Error)
   - **Response** tab (shows error message)
6. Share the status code and response

### Step 3: Check Terminal/Eclipse Console
1. Look at your application console in Eclipse
2. Try the download again
3. Look for error messages or stack traces
4. Common errors to look for:
   ```
   FileNotFoundException
   IOException
   NullPointerException
   IllegalArgumentException
   ```
5. Share the complete error stack trace

## Common Causes and Solutions

### Issue 1: Document Root Path Not Configured

**Error messages**:
- "File not found"
- "Path does not exist"
- "Cannot find document"

**Cause**: The `document.root` property in `application-local.properties` is not set correctly.

**Check current configuration**:
```properties
# In application-local.properties
document.root=C:\Users\sumit\Desktop\WMS\DHS 24-04-2024
```

**Solution**:
1. Create the directory if it doesn't exist
2. Or update the path to an existing directory
3. Ensure the path has proper permissions

**Example fix**:
```properties
# Change to your actual path
document.root=C:\Users\JHON\Desktop\Anuppur_Documents\
```

### Issue 2: Apache POI Library Issues (Excel Export)

**Error messages**:
- "NoClassDefFoundError: org/apache/poi"
- "ClassNotFoundException: XSSFWorkbook"
- Excel file is corrupted

**Cause**: Apache POI library version conflict or missing dependency

**Solution**: Check `pom.xml` has correct POI dependencies:
```xml
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi</artifactId>
    <version>5.2.3</version>
</dependency>
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>5.2.3</version>
</dependency>
```

### Issue 3: File Size Limit Exceeded

**Error messages**:
- "Maximum upload size exceeded"
- "File too large"

**Cause**: Spring Boot file size limits

**Solution**: Update `application-local.properties`:
```properties
spring.servlet.multipart.max-file-size=25MB
spring.servlet.multipart.max-request-size=25MB
server.tomcat.max-http-form-post-size=6291456
```

### Issue 4: Missing Template Files (PDF Generation)

**Error messages**:
- "Template not found"
- "Cannot load template"

**Cause**: PDF template files missing from resources

**Solution**: Ensure template files exist in:
```
src/main/resources/templates/reports/
```

### Issue 5: Permission Issues

**Error messages**:
- "Access denied"
- "Permission denied"
- "Cannot write to file"

**Cause**: Application doesn't have write permissions to document directory

**Solution**:
1. Check folder permissions
2. Run Eclipse as Administrator (if on Windows)
3. Or change document.root to a folder with write permissions

### Issue 6: Content Type Issues

**Error messages**:
- Browser shows garbled text instead of downloading
- "Cannot display file"

**Cause**: Incorrect Content-Type header in response

**Solution**: Check controller sets correct headers:
```java
response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
response.setHeader("Content-Disposition", "attachment; filename=report.xlsx");
```

## Quick Tests

### Test 1: Check Document Root Directory
```cmd
dir "C:\Users\sumit\Desktop\WMS\DHS 24-04-2024"
```

If directory doesn't exist, create it:
```cmd
mkdir "C:\Users\JHON\Desktop\Anuppur_Documents"
```

### Test 2: Check Write Permissions
Try creating a test file in the document root:
```cmd
echo test > "C:\Users\sumit\Desktop\WMS\DHS 24-04-2024\test.txt"
```

If this fails, you have permission issues.

### Test 3: Test Simple Download
Try downloading a simple file first (like a small Excel export) to see if the issue is specific to certain file types.

## Specific Download Features

### Excel Export (Works List)
**URL pattern**: `/systemAdmin/manageOngoingWorks/downloadAllWorksExcel`

**Common issues**:
- Empty data (no works to export)
- POI library issues
- Memory issues with large datasets

**Check**:
1. Verify works exist in database
2. Check POI dependencies
3. Check heap memory settings

### PDF Export (Reports)
**URL pattern**: `/systemAdmin/manageOngoingWorks/downloadAllWorksPdf`

**Common issues**:
- Template not found
- Font issues
- Image loading failures

**Check**:
1. Verify template files exist
2. Check font files in resources
3. Check image paths

### Document Downloads (Work Progress Images)
**URL pattern**: `/systemAdmin/downloadDocument/{id}`

**Common issues**:
- File path not found
- Incorrect document root
- File deleted from disk

**Check**:
1. Verify file exists in document.root
2. Check file path in database
3. Verify document.root configuration

## What Information to Share

To help you fix the issue, please provide:

### 1. Exact Error Message
- Screenshot of error
- Or copy the exact error text

### 2. Browser Console Errors
```
Press F12 → Console tab → Copy all red errors
```

### 3. Network Tab Information
```
Press F12 → Network tab → Find failed request → Share:
- Request URL
- Status Code
- Response
```

### 4. Terminal/Eclipse Console Errors
```
Copy the complete error stack trace from Eclipse console
```

### 5. What You're Trying to Download
- Excel report?
- PDF report?
- Work document?
- Image?

### 6. Which Page/Feature
- Manage Ongoing Works → Export Excel?
- Reports → Download PDF?
- Work Details → Download Document?

## Temporary Workarounds

While we fix the issue, you can:

### For Excel Exports:
- Try exporting smaller datasets (use filters)
- Try copying data from screen instead

### For PDF Reports:
- Try printing to PDF from browser (Ctrl+P → Save as PDF)

### For Documents:
- Check if files exist in the document root directory
- Access files directly from file system

## Configuration to Check

### application-local.properties
```properties
# Document paths
document.root=C:\Users\sumit\Desktop\WMS\DHS 24-04-2024
document.work=WORK_DOCS/
document.technical=WORK/TECH_DOC/
document.administrator=WORK/ADMS_DOC/
document.workprogress=WORK/WORK_PROGRESS/

# File upload limits
spring.servlet.multipart.max-file-size=25MB
spring.servlet.multipart.max-request-size=25MB
server.tomcat.max-http-form-post-size=6291456
```

## Next Steps

1. **Provide the specific error details** (see "What Information to Share" above)
2. **Try the Quick Tests** to identify the issue
3. **Share the results** with me
4. **I'll provide the exact fix** based on your findings

Once you share the specific error details, I can provide a precise solution!
