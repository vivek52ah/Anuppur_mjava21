package com.anuppur.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.multipart.MultipartFile;

import com.anuppur.constants.DMSConstants;
import com.anuppur.entity.DocumentUpload;
import com.anuppur.entity.DocumentUploadWorkProgress;
import com.anuppur.exception.DMSBusinessException;
import com.anuppur.repository.WorkStatusRepository;
import com.anuppur.security.SecureFileUploadPolicy;

@SuppressWarnings("all")
public class DMSUtil {

	public static final Logger logger = LoggerFactory.getLogger(DMSUtil.class);

	public static String[] acceptedContentTypes = { "application/pdf", "image/jpg", "image/jpeg", "image/png",
			"image/gif" };

	public static String[] pdfOnly = { "application/pdf" };
		
	public static String getImageString(byte[] profileImage) {
		return "data:image/jpg;base64," + Base64.getEncoder().encodeToString(profileImage);
	}

	@Autowired
	private static WorkStatusRepository workStatusRepository;

	public static DocumentUpload uploadCCDocument(String documentPath, String workid, MultipartFile document,
			String documentDesc, String documentType) throws DMSBusinessException {

		String fileName = saveCCFile(documentPath, String.valueOf(workid), document);

		DocumentUpload documentUpload = new DocumentUpload();
		documentUpload.setDocumentName(fileName);
		documentUpload.setDocumentDesc(documentDesc);
//		documentUpload.setDocumentType(new MasterLegalDocumentType(documentType));
//		legalDocumentUpload.setCreatedOnAndCreatedBy();
		return documentUpload;
	}

	public static DocumentUpload uploadTsWorkDocument(String documentPath, String workid, MultipartFile document,
			String documentDesc, String documentType) throws DMSBusinessException {

		String fileName = saveTechnicalSanctionFile(documentPath, String.valueOf(workid), document);

		DocumentUpload documentUpload = new DocumentUpload();
		documentUpload.setDocumentName(fileName);
		documentUpload.setDocumentDesc(documentDesc);
//		documentUpload.setDocumentType(new MasterLegalDocumentType(documentType));
//		legalDocumentUpload.setCreatedOnAndCreatedBy();
		return documentUpload;
	}
	
	public static DocumentUpload uploadDMAttachment(String documentPath, String workid, MultipartFile document,
			String documentDesc, String documentType) throws DMSBusinessException {

		String fileName = saveDMAttachmentFile(documentPath, String.valueOf(workid), document);

		DocumentUpload documentUpload = new DocumentUpload();
		documentUpload.setDocumentName(fileName);
		documentUpload.setDocumentDesc(documentDesc);
//		documentUpload.setDocumentType(new MasterLegalDocumentType(documentType));
//		legalDocumentUpload.setCreatedOnAndCreatedBy();
		return documentUpload;
	}

	public static DocumentUpload uploadTsRevisedWorkDocument(String documentPath, String workid, MultipartFile document,
			String documentDesc, String documentType) throws DMSBusinessException {

		String fileName = saveTechnicalSanctionRevisedFile(documentPath, String.valueOf(workid), document);

		DocumentUpload documentUpload = new DocumentUpload();
		documentUpload.setDocumentName(fileName);
		documentUpload.setDocumentDesc(documentDesc);
//		documentUpload.setDocumentType(new MasterLegalDocumentType(documentType));
//		legalDocumentUpload.setCreatedOnAndCreatedBy();
		return documentUpload;
	}

	/*
	 * public static DocumentUpload uploadWorkProgressDocument(String
	 * documentPath,String workid, MultipartFile document, String documentDesc,
	 * String documentType) throws DMSBusinessException {
	 * 
	 * String fileName = saveWorkProgressFile(documentPath, String.valueOf(workid),
	 * document);
	 * 
	 * DocumentUpload documentUpload = new DocumentUpload();
	 * documentUpload.setDocumentName(fileName);
	 * documentUpload.setDocumentDesc(documentDesc); //
	 * documentUpload.setDocumentType(new MasterLegalDocumentType(documentType)); //
	 * legalDocumentUpload.setCreatedOnAndCreatedBy(); return documentUpload; }
	 */

	public static DocumentUploadWorkProgress uploadWorkProgressDocument(String documentPath, Long workid,
			MultipartFile document, String documentDesc, String documentType, Long workSubStatusId, Long workStatusId)
			throws DMSBusinessException {

		String fileName = saveWorkProgressFile(documentPath, String.valueOf(workid), document);

		DocumentUploadWorkProgress documentUpload = new DocumentUploadWorkProgress();
		documentUpload.setDocumentName(fileName);
		documentUpload.setWorkId(workid);
		documentUpload.setEnabled((short) 1);
		documentUpload.setCreatedDate(new Date());
		documentUpload.setWorkSubStatusId(workSubStatusId);

		return documentUpload;
	}

	public static DocumentUpload uploadTenderWorkDocument(String documentPath, String workid, MultipartFile document,
			String documentDesc, String documentType) throws DMSBusinessException {

		String fileName = saveTenderSanctionFile(documentPath, String.valueOf(workid), document);

		DocumentUpload documentUpload = new DocumentUpload();
		documentUpload.setDocumentName(fileName);
		documentUpload.setDocumentDesc(documentDesc);
//		documentUpload.setDocumentType(new MasterLegalDocumentType(documentType));
//		legalDocumentUpload.setCreatedOnAndCreatedBy();
		return documentUpload;
	}

	public static String saveTenderSanctionFile(String documentsPath, String requestId, MultipartFile mpresFile)
	        throws DMSBusinessException {

	    Date date = new Date();
	    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
	    String strDate = formatter.format(date);

	    File serverFile = null;
	    String createdFileName = null;

	    File dir = new File(documentsPath);

	    String fileExtension = "pdf";
	    if (null != mpresFile.getOriginalFilename()) {
	        String[] fileArr = mpresFile.getOriginalFilename().split("\\.");
	        int length = fileArr.length;
	        fileExtension = fileArr[length - 1];
	    }

	    if (!dir.exists()) {
	        dir.mkdirs();
	        createdFileName = SecureFileUploadPolicy.createDocumentStorageName(mpresFile);
	        serverFile = new File(dir.getAbsolutePath() + File.separator + createdFileName);
	    } else {
	        createdFileName = SecureFileUploadPolicy.createDocumentStorageName(mpresFile);
	        serverFile = new File(dir.getAbsolutePath() + File.separator + createdFileName);
	    }

	    BufferedOutputStream stream = null;
	    DMSBusinessException businessException = null;  // To capture any exception
	    try {
	        stream = new BufferedOutputStream(new FileOutputStream(serverFile));
	        stream.write(mpresFile.getBytes());
	    } catch (Exception e) {
	        logger.error("Failed to write file to server. Request ID: {}. Error: {}", requestId, e.getMessage(), e);
	        businessException = new DMSBusinessException("System is unable to process.");
	    } finally {
	        // Ensure the stream is closed properly
	        try {
	            if (stream != null) {
	                stream.close();
	            }
	        } catch (IOException e) {
	            // Log the exception while closing the stream
	            logger.error("Failed to close the file output stream. Error: {}", e.getMessage(), e);
	            if (businessException == null) {
	                // Only create a new exception if none was already caught
	                businessException = new DMSBusinessException("System is unable to process.");
	            }
	        }
	    }

	    // Throw any captured exception after the finally block
	    if (businessException != null) {
	        throw businessException;
	    }

	    return createdFileName;
	}

	public static DocumentUpload uploadTenderWorkDocumentUloi(String documentPath, String workid,
			MultipartFile document, String documentDesc, String documentType) throws DMSBusinessException {

		String fileName = saveTenderSanctionFileUloi(documentPath, String.valueOf(workid), document);

		DocumentUpload documentUpload = new DocumentUpload();
		documentUpload.setDocumentName(fileName);
		documentUpload.setDocumentDesc(documentDesc);
//		documentUpload.setDocumentType(new MasterLegalDocumentType(documentType));
//		legalDocumentUpload.setCreatedOnAndCreatedBy();
		return documentUpload;
	}

	public static String saveTenderSanctionFileUloi(String documentsPath, String requestId, MultipartFile mpresFile)
	        throws DMSBusinessException {

	    Date date = new Date();
	    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
	    String strDate = formatter.format(date);

	    File serverFile = null;
	    String createdFileName = null;

	    File dir = new File(documentsPath);

	    String fileExtension = "pdf";
	    if (null != mpresFile.getOriginalFilename()) {
	        String[] fileArr = mpresFile.getOriginalFilename().split("\\.");
	        int length = fileArr.length;
	        fileExtension = fileArr[length - 1];
	    }

	    if (!dir.exists()) {
	        dir.mkdirs();
	        createdFileName = SecureFileUploadPolicy.createDocumentStorageName(mpresFile);
	        serverFile = new File(dir.getAbsolutePath() + File.separator + createdFileName);
	    } else {
	        createdFileName = SecureFileUploadPolicy.createDocumentStorageName(mpresFile);
	        serverFile = new File(dir.getAbsolutePath() + File.separator + createdFileName);
	    }

	    BufferedOutputStream stream = null;
	    DMSBusinessException businessException = null;  // Capture exception here

	    try {
	        stream = new BufferedOutputStream(new FileOutputStream(serverFile));
	        stream.write(mpresFile.getBytes());
	    } catch (Exception e) {
	        logger.error("Failed to write file to server. Request ID: {}. Error: {}", requestId, e.getMessage(), e);
	        businessException = new DMSBusinessException("System is unable to process.");
	    } finally {
	        try {
	            if (stream != null) {
	                stream.close();
	            }
	        } catch (IOException e) {
	            logger.error("Failed to close file output stream. Error: {}", e.getMessage(), e);
	            if (businessException == null) {
	                // Only create the exception if one hasn't been created yet
	                businessException = new DMSBusinessException("System is unable to process.");
	            }
	        }
	    }

	    // Throw the exception outside the finally block if one was captured
	    if (businessException != null) {
	        throw businessException;
	    }

	    return createdFileName;
	}

	// uploadTenderWorkDocumentUAgreemnet
	public static DocumentUpload uploadTenderWorkDocumentUploadAgreement(String documentPath, String workid,
			MultipartFile document, String documentDesc, String documentType) throws DMSBusinessException {

		String fileName = saveTenderSanctionFileUploadAgreement(documentPath, String.valueOf(workid), document);

		DocumentUpload documentUpload = new DocumentUpload();
		documentUpload.setDocumentName(fileName);
		documentUpload.setDocumentDesc(documentDesc);
//		documentUpload.setDocumentType(new MasterLegalDocumentType(documentType));
//		legalDocumentUpload.setCreatedOnAndCreatedBy();
		return documentUpload;
	}

	public static String saveTenderSanctionFileUploadAgreement(String documentsPath, String requestId, MultipartFile mpresFile)
	        throws DMSBusinessException {

	    Date date = new Date();
	    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
	    String strDate = formatter.format(date);

	    File serverFile = null;
	    String createdFileName = null;

	    File dir = new File(documentsPath);

	    String fileExtension = "pdf";
	    if (null != mpresFile.getOriginalFilename()) {
	        String[] fileArr = mpresFile.getOriginalFilename().split("\\.");
	        int length = fileArr.length;
	        fileExtension = fileArr[length - 1];
	    }

	    if (!dir.exists()) {
	        dir.mkdirs();
	        createdFileName = SecureFileUploadPolicy.createDocumentStorageName(mpresFile);
	        serverFile = new File(dir.getAbsolutePath() + File.separator + createdFileName);
	    } else {
	        createdFileName = SecureFileUploadPolicy.createDocumentStorageName(mpresFile);
	        serverFile = new File(dir.getAbsolutePath() + File.separator + createdFileName);
	    }

	    BufferedOutputStream stream = null;
	    DMSBusinessException businessException = null;  // To capture the exception

	    try {
	        stream = new BufferedOutputStream(new FileOutputStream(serverFile));
	        stream.write(mpresFile.getBytes());
	    } catch (Exception e) {
	        logger.error("Failed to write file to server. Request ID: {}. Error: {}", requestId, e.getMessage(), e);
	        businessException = new DMSBusinessException("System is unable to process.");
	    } finally {
	        try {
	            if (stream != null) {
	                stream.close();
	            }
	        } catch (IOException e) {
	            logger.error("Failed to close file output stream. Error: {}", e.getMessage(), e);
	            if (businessException == null) {
	                // Only create the exception if one hasn't been created yet
	                businessException = new DMSBusinessException("System is unable to process.");
	            }
	        }
	    }

	    // Throw the exception outside the finally block if one was captured
	    if (businessException != null) {
	        throw businessException;
	    }

	    return createdFileName;
	}


	public static DocumentUpload uploadAsWorkDocument(String documentPath, String workid, MultipartFile document,
			String documentDesc, String documentType) throws DMSBusinessException {

		String fileName = saveTsSanctionFile(documentPath, String.valueOf(workid), document);

		DocumentUpload documentUpload = new DocumentUpload();
		documentUpload.setDocumentName(fileName);
		documentUpload.setDocumentDesc(documentDesc);
//		documentUpload.setDocumentType(new MasterLegalDocumentType(documentType));
//		legalDocumentUpload.setCreatedOnAndCreatedBy();
		return documentUpload;
	}

	public static DocumentUpload uploadAsRevisedWorkDocument(String documentPath, String workid, MultipartFile document,
			String documentDesc, String documentType) throws DMSBusinessException {

		String fileName = saveASRevisedSanctionFile(documentPath, String.valueOf(workid), document);

		DocumentUpload documentUpload = new DocumentUpload();
		documentUpload.setDocumentName(fileName);
		documentUpload.setDocumentDesc(documentDesc);
//		documentUpload.setDocumentType(new MasterLegalDocumentType(documentType));
//		legalDocumentUpload.setCreatedOnAndCreatedBy();
		return documentUpload;
	}

	public static String saveCCFile(String documentsPath, String requestId, MultipartFile mpresFile)
			throws DMSBusinessException {

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
		String strDate = formatter.format(date);

		File serverFile = null;
		String createdFileName = null;

		File dir = new File(documentsPath);

		String fileExtension = "pdf";
		if (null != mpresFile.getOriginalFilename()) {
			String[] fileArr = mpresFile.getOriginalFilename().split("\\.");
			int length = fileArr.length;
			fileExtension = fileArr[length - 1];
		}

		if (!dir.exists()) {
			dir.mkdirs();

			createdFileName = SecureFileUploadPolicy.createDocumentStorageName(mpresFile);
			serverFile = new File(dir.getAbsolutePath() + File.separator + createdFileName);
		} else {

			createdFileName = SecureFileUploadPolicy.createDocumentStorageName(mpresFile);
			serverFile = new File(dir.getAbsolutePath() + File.separator + createdFileName);
		}

		BufferedOutputStream stream = null;
		try {
			stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(mpresFile.getBytes());
		} catch (Exception e) {
			throw new DMSBusinessException("System is unable to process.");
		} finally {
			try {
				if (null != stream) {
					stream.close();
				}
			} catch (IOException e) {
				logger.error("Failed to close output stream: {}", e.getMessage(), e);
			}
		}
		return createdFileName;
	}

	public static String saveTsSanctionFile(String documentsPath, String requestId, MultipartFile mpresFile)
			throws DMSBusinessException {

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
		String strDate = formatter.format(date);

		File serverFile = null;
		String createdFileName = null;

		File dir = new File(documentsPath);

		String fileExtension = "pdf";
		if (null != mpresFile.getOriginalFilename()) {
			String[] fileArr = mpresFile.getOriginalFilename().split("\\.");
			int length = fileArr.length;
			fileExtension = fileArr[length - 1];
		}

		if (!dir.exists()) {
			dir.mkdirs();

			createdFileName = SecureFileUploadPolicy.createDocumentStorageName(mpresFile);
			serverFile = new File(dir.getAbsolutePath() + File.separator + createdFileName);
		} else {

			createdFileName = SecureFileUploadPolicy.createDocumentStorageName(mpresFile);
			serverFile = new File(dir.getAbsolutePath() + File.separator + createdFileName);
		}

		BufferedOutputStream stream = null;
		try {
			stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(mpresFile.getBytes());
		} catch (Exception e) {
			throw new DMSBusinessException("System is unable to process.");
		} finally {
			try {
				if (null != stream) {
					stream.close();
				}
			} catch (IOException e) {
			logger.error("Failed to close output stream: {}", e.getMessage(), e);
			}
		}
		return createdFileName;
	}

	public static String saveASRevisedSanctionFile(String documentsPath, String requestId, MultipartFile mpresFile)
			throws DMSBusinessException {

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
		String strDate = formatter.format(date);

		File serverFile = null;
		String createdFileName = null;

		File dir = new File(documentsPath);

		String fileExtension = "pdf";
		if (null != mpresFile.getOriginalFilename()) {
			String[] fileArr = mpresFile.getOriginalFilename().split("\\.");
			int length = fileArr.length;
			fileExtension = fileArr[length - 1];
		}

		if (!dir.exists()) {
			dir.mkdirs();

			createdFileName = SecureFileUploadPolicy.createDocumentStorageName(mpresFile);
			serverFile = new File(dir.getAbsolutePath() + File.separator + createdFileName);
		} else {

			createdFileName = SecureFileUploadPolicy.createDocumentStorageName(mpresFile);
			serverFile = new File(dir.getAbsolutePath() + File.separator + createdFileName);
		}

		BufferedOutputStream stream = null;
		try {
			stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(mpresFile.getBytes());
		} catch (Exception e) {
			throw new DMSBusinessException("System is unable to process.");
		} finally {
			try {
				if (null != stream) {
					stream.close();
				}
			} catch (IOException e) {
				logger.error("Failed to close output stream: {}", e.getMessage(), e);
			}
		}
		return createdFileName;
	}

	public static String saveWorkProgressFile(String documentsPath, String requestId, MultipartFile mpresFile)
			throws DMSBusinessException {

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
		String strDate = formatter.format(date);

		File serverFile = null;
		String createdFileName = null;

		File dir = new File(documentsPath);

		String fileExtension = "pdf";
		if (null != mpresFile.getOriginalFilename()) {
			String[] fileArr = mpresFile.getOriginalFilename().split("\\.");
			int length = fileArr.length;
			fileExtension = fileArr[length - 1];
		}

		if (!dir.exists()) {
			dir.mkdirs();

			createdFileName = SecureFileUploadPolicy.createDocumentStorageName(mpresFile);
			serverFile = new File(dir.getAbsolutePath() + File.separator + createdFileName);
		} else {

			createdFileName = SecureFileUploadPolicy.createDocumentStorageName(mpresFile);
			serverFile = new File(dir.getAbsolutePath() + File.separator + createdFileName);
		}

		BufferedOutputStream stream = null;
		try {
			stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(mpresFile.getBytes());
		} catch (Exception e) {
			throw new DMSBusinessException("System is unable to process.");
		} finally {
			try {
				if (null != stream) {
					stream.close();
				}
			} catch (IOException e) {
				logger.error("Failed to close output stream: {}", e.getMessage(), e);
			}
		}
		return createdFileName;
	}

	public static String saveTechnicalSanctionFile(String documentsPath, String requestId, MultipartFile mpresFile)
			throws DMSBusinessException {

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
		String strDate = formatter.format(date);

		File serverFile = null;
		String createdFileName = null;

		File dir = new File(documentsPath);

		String fileExtension = "pdf";
		if (null != mpresFile.getOriginalFilename()) {
			String[] fileArr = mpresFile.getOriginalFilename().split("\\.");
			int length = fileArr.length;
			fileExtension = fileArr[length - 1];
		}

		if (!dir.exists()) {
			dir.mkdirs();

			createdFileName = SecureFileUploadPolicy.createDocumentStorageName(mpresFile);
			serverFile = new File(dir.getAbsolutePath() + File.separator + createdFileName);
		} else {

			createdFileName = SecureFileUploadPolicy.createDocumentStorageName(mpresFile);
			serverFile = new File(dir.getAbsolutePath() + File.separator + createdFileName);
		}

		BufferedOutputStream stream = null;
		try {
			stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(mpresFile.getBytes());
		} catch (Exception e) {
			throw new DMSBusinessException("System is unable to process.");
		} finally {
			try {
				if (null != stream) {
					stream.close();
				}
			} catch (IOException e) {
				logger.error("Failed to close output stream: {}", e.getMessage(), e);
			}
		}
		return createdFileName;
	}
	public static String saveDMAttachmentFile(String documentsPath, String requestId, MultipartFile mpresFile)
			throws DMSBusinessException {

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
		String strDate = formatter.format(date);

		File serverFile = null;
		String createdFileName = null;

		File dir = new File(documentsPath);

		String fileExtension = "pdf";
		if (null != mpresFile.getOriginalFilename()) {
			String[] fileArr = mpresFile.getOriginalFilename().split("\\.");
			int length = fileArr.length;
			fileExtension = fileArr[length - 1];
		}

		if (!dir.exists()) {
			dir.mkdirs();

			createdFileName = SecureFileUploadPolicy.createDocumentStorageName(mpresFile);
			serverFile = new File(dir.getAbsolutePath() + File.separator + createdFileName);
		} else {

			createdFileName = SecureFileUploadPolicy.createDocumentStorageName(mpresFile);
			serverFile = new File(dir.getAbsolutePath() + File.separator + createdFileName);
		}

		BufferedOutputStream stream = null;
		try {
			stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(mpresFile.getBytes());
		} catch (Exception e) {
			throw new DMSBusinessException("System is unable to process.");
		} finally {
			try {
				if (null != stream) {
					stream.close();
				}
			} catch (IOException e) {
				logger.error("Failed to close output stream: {}", e.getMessage(), e);
			}
		}
		return createdFileName;
	}

	public static String saveTechnicalSanctionRevisedFile(String documentsPath, String requestId,
			MultipartFile mpresFile) throws DMSBusinessException {

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
		String strDate = formatter.format(date);

		File serverFile = null;
		String createdFileName = null;

		File dir = new File(documentsPath);

		String fileExtension = "pdf";
		if (null != mpresFile.getOriginalFilename()) {
			String[] fileArr = mpresFile.getOriginalFilename().split("\\.");
			int length = fileArr.length;
			fileExtension = fileArr[length - 1];
		}

		if (!dir.exists()) {
			dir.mkdirs();

			createdFileName = SecureFileUploadPolicy.createDocumentStorageName(mpresFile);
			serverFile = new File(dir.getAbsolutePath() + File.separator + createdFileName);
		} else {

			createdFileName = SecureFileUploadPolicy.createDocumentStorageName(mpresFile);
			serverFile = new File(dir.getAbsolutePath() + File.separator + createdFileName);
		}

		BufferedOutputStream stream = null;
		try {
			stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(mpresFile.getBytes());
		} catch (Exception e) {
			throw new DMSBusinessException("System is unable to process.");
		} finally {
			try {
				if (null != stream) {
					stream.close();
				}
			} catch (IOException e) {
				logger.error("Failed to close output stream: {}", e.getMessage(), e);
			}
		}
		return createdFileName;
	}
	static SecureRandom rnd = new SecureRandom(); 
	
	public static String generateCaptchaText(int captchaLength) {

		// String saltChars = "ABCDEFGHIJKLMNPQRSTUVWXYZ123456789";
		String saltChars = "1234567890";
		StringBuffer captchaStrBuffer = new StringBuffer();
		

		// build a random captchaLength chars salt
		while (captchaStrBuffer.length() < captchaLength) {
			int index = (int) (rnd.nextFloat() * saltChars.length());
			captchaStrBuffer.append(saltChars.substring(index, index + 1));
		}

		return captchaStrBuffer.toString();

		// return "1";
	}

	public static String getMessage(String propertyFile, String key, Object[] params) {

		Locale locale = LocaleContextHolder.getLocale();

		ResourceBundle bundle = ResourceBundle.getBundle(propertyFile, locale);

		return MessageFormat.format(bundle.getString(key), params);
	}

	public static User getUserDetail() {

		SecurityContext securityContext = SecurityContextHolder.getContext();
		User user = null;
		if (null != securityContext) {
			Authentication authentication = securityContext.getAuthentication();
			if (null != authentication) {
				if (authentication.getPrincipal() instanceof String) {
					user = null;
				} else {
					user = (User) authentication.getPrincipal();
				}
			}
		}
		return user;
	}

	public static String getUserRole(User user) {

		Collection<GrantedAuthority> authorities = user.getAuthorities();

		String role = null;
		for (GrantedAuthority ga : authorities) {

			role = ga.getAuthority();
		}

		return role;
	}

	public static String generatePassword() {
		return generateSessionKey(8);
	}

	private static String generateSessionKey(int length) {
		String alphabet = new String("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"); // 9
		int n = alphabet.length(); // 10

		String DMFult = new String();
		

		for (int i = 0; i < length; i++)
			// 12
			DMFult = DMFult + alphabet.charAt(rnd.nextInt(n)); // 13

		return DMFult;
	}

	public static Date convertStringToDate(String inputDate) {

		SimpleDateFormat dateFormat = new SimpleDateFormat(DMSConstants.DATE_FORMAT);

		try {
			return dateFormat.parse(inputDate.trim());
		} catch (ParseException e) {
			logger.info("failed to convert String to date, Input Date - {}", inputDate);
			logger.error("An exception occurred.", e);
		}
		return null;
	}

	public static String convertDateToString(Date inputDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DMSConstants.DATE_FORMAT);

		try {
			if (inputDate != null)
				return dateFormat.format(inputDate);
		} catch (Exception e) {
			logger.info("failed to convert date to String, Input Date - {}", inputDate);
			logger.error("An exception occurred.", e);
		}
		return null;
	}

	public static String convertDateToStringWithFormat(Date inputDate, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		try {
			if (inputDate != null)
				return dateFormat.format(inputDate);
		} catch (Exception e) {
			logger.info("failed to convert date to String, Input Date - {}, format - {}", inputDate, format);
			logger.error("An exception occurred.", e);
		}
		return null;
	}

	public static boolean isThisDateBeforeTheGivenDate(String dateToValidateStr, String givenDateStr) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		try {
			// if not valid, it will throw ParseException
			Date dateToValidate = sdf.parse(dateToValidateStr);
			Date givenDate = sdf.parse(givenDateStr);

			if (dateToValidate.compareTo(givenDate) < 1) {
				return true;
			} else
				return false;

		} catch (ParseException e) {
			logger.info("isThisDateBeforeTheGivenDate(), Input Dates - {}, {}", dateToValidateStr, givenDateStr);
			logger.error("An exception occurred.", e);
			return false;
		}
	}

	public static String minusOneMonth(String dateToMinus) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		try {
			// if not valid, it will throw ParseException
			Date date = sdf.parse(dateToMinus);

			Calendar cal = Calendar.getInstance();
			cal.setTime(date);

			cal.add(Calendar.MONTH, -1);

			int month = cal.get(Calendar.MONTH);
			int year = cal.get(Calendar.YEAR);

			month++;// calender's month indexing starts from 0
			if (String.valueOf(month).length() < 2) {
				return "0" + month + "/" + year;
			}
			return month + "/" + year;
		} catch (ParseException e) {
			logger.info("minusOneMonth(), Input Date - {}", dateToMinus);
			logger.error("An exception occurred.", e);
			return "";
		}
	}

	public static String getFinancialYear() {

		int year = Calendar.getInstance().get(Calendar.YEAR);

		int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
		if (month < 4) {
			return (year - 1) % 100 + "-" + year % 100;
		} else {
			return year % 100 + "-" + (year + 1) % 100;
		}
	}

	public static String decryptParam(String param) {

		if (param == null || param.trim().isEmpty()) {
			return "0";
		}
		try {
			byte[] decoded = Base64.getUrlDecoder().decode(param);
			return new String(decoded, StandardCharsets.UTF_8);
		} catch (IllegalArgumentException ex) {
			return "0";
		}
	}

	public static String saveFile(String documentsPath, String id, MultipartFile file) throws DMSBusinessException {
		File serverFile;
	    String createdFileName;

	    // Create directory if it doesn't exist
	    File dir = new File(documentsPath + File.separator + id);
	    if (!dir.exists() && !dir.mkdirs()) {
	        throw new DMSBusinessException("Unable to create directory for storing files.");
	    }

	    // Generate file name and server file
	    createdFileName = SecureFileUploadPolicy.createDocumentStorageName(file);
	    serverFile = new File(dir.getAbsolutePath() + File.separator + createdFileName);

	    if (serverFile.exists()) {
	        // Generate unique file name
	        createdFileName = SecureFileUploadPolicy.createDocumentStorageName(file);
	        serverFile = new File(dir.getAbsolutePath() + File.separator + createdFileName);
	    }

	    // Write file to the server using try-with-resources
	    try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
	        stream.write(file.getBytes());
	    } catch (IOException e) {
	        logger.error("Error writing file to server: {}", e.getMessage(), e);
	        throw new DMSBusinessException("System is unable to process.");
	    }

	    return createdFileName;
	}

	public static String savebase64EncodedImage(String base, String documentsPath, String requestId, String encodedImg,
			int count) throws DMSBusinessException {

		byte[] decodedImg = Base64.getDecoder().decode(encodedImg.getBytes(StandardCharsets.UTF_8));

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
		String strDate = formatter.format(date);

		File serverFile = null;
		String createdFileName = null;
		File dir = new File(documentsPath);
		if (!dir.exists()) {
			dir.mkdirs();

			createdFileName = SecureFileUploadPolicy.validateImageBytesAndCreateName(decodedImg);
			serverFile = new File(dir.getAbsolutePath() + File.separator + createdFileName);
		} else {

			createdFileName = SecureFileUploadPolicy.validateImageBytesAndCreateName(decodedImg);
			serverFile = new File(dir.getAbsolutePath() + File.separator + createdFileName);
		}

		BufferedOutputStream stream = null;
		try {
			stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(decodedImg);
		} catch (Exception e) {
		
		} finally {
			try {
				if (null != stream) {
					stream.close();
				}
			} catch (IOException e) {
				logger.error("Failed to close output stream: {}", e.getMessage(), e);
			}
		}

		return createdFileName;
	}

	public static Timestamp getTimestampFromDate(String inputDate) throws DMSBusinessException {

		SimpleDateFormat dateFormat = new SimpleDateFormat(DMSConstants.DATE_FORMAT);

		try {
			if (inputDate != null) {
				Date date = dateFormat.parse(inputDate.trim());
				return new Timestamp(date.getTime());
			} else {
				return null;
			}
		} catch (ParseException e) {
			throw new DMSBusinessException("System is not able to process.");
		}

	}

	public static long getDifferenceDays(Date d1, Date d2) {
		long diff = d2.getTime() - d1.getTime();
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}

	public static String saveDrawingFile(String documentsPath, String requestId, MultipartFile mpresFile)
			throws DMSBusinessException {

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
		String strDate = formatter.format(date);

		File serverFile = null;
		String createdFileName = null;

		File dir = new File(documentsPath);

		String fileExtension = "pdf";
		if (null != mpresFile.getOriginalFilename()) {
			String[] fileArr = mpresFile.getOriginalFilename().split("\\.");
			int length = fileArr.length;
			fileExtension = fileArr[length - 1];
		}

		if (!dir.exists()) {
			dir.mkdirs();

			createdFileName = SecureFileUploadPolicy.createDocumentStorageName(mpresFile);
			serverFile = new File(dir.getAbsolutePath() + File.separator + createdFileName);
		} else {

			createdFileName = SecureFileUploadPolicy.createDocumentStorageName(mpresFile);
			serverFile = new File(dir.getAbsolutePath() + File.separator + createdFileName);
		}

		BufferedOutputStream stream = null;
		try {
			stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(mpresFile.getBytes());
		} catch (Exception e) {
logger.error("Error",e);
		} finally {
			try {
				if (null != stream) {
					stream.close();
				}
			} catch (IOException e) {
				logger.error("Failed to close output stream: {}", e.getMessage(), e);
			}
		}
		return createdFileName;
	}

}
