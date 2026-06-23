package com.anuppur.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import com.anuppur.bean.GeoTaggingBean;
import com.anuppur.bean.TSASWorkBean;
import com.anuppur.bean.UserBean;
import com.anuppur.bean.WorkBean;
import com.anuppur.bean.WorkProgressBean;
import com.anuppur.bean.WorkProgressDataMoibleBean;
import com.anuppur.bean.WorkStatusBean;
import com.anuppur.bean.WorkSubStatusBean;
import com.anuppur.bean.WorkTenderBean;
import com.anuppur.constants.DMSConstants;
import com.anuppur.entity.Work;
import com.anuppur.json.WorkProgressImagesJson;
import com.anuppur.response.ResponseObject;
import com.anuppur.service.CommonService;
import com.anuppur.service.UserService;
import com.anuppur.util.DMSUtil;
import com.anuppur.util.JwtUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;

@RestController
@RequestMapping("/mobile")
public class MobileApiController extends BaseController {

	@Autowired
	private CommonService commonService;
	public static final Logger logger = LoggerFactory.getLogger(MobileController.class);
	private User user;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@GetMapping("/secure-data")
	public String getSecureData() {
		return "This is secured mobile data!";
	}

	@RequestMapping(value = "fetchWorkDetails/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> fetchWorkDetails(@PathVariable Long id, HttpServletRequest request) {
		logger.info(request.getRequestURI());
		try {
			// Fetch logged-in user

			// Fetch Work details
			WorkBean workBean = commonService.fetchWorkDetails(id);

			if (workBean == null) {

				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Work details not found.");
			}

			// Role-based security logic
			String roleCode = fetchLoggedInUser(request).getRole().getRoleCode();

			if ("ROLE_SYSTEM_ADMIN".equals(roleCode)) {
				// if (workBean.getDmStatus() != null) {
				workBean.setIsDisabled(true);

			} else if ("ROLE_DEPARTMENT".equals(roleCode)) {
				if (workBean.getDmStatus() != null) {
					if (workBean.getDmStatus() == 2L) {
						workBean.setIsDisabledDep(true);
					} else if (workBean.getDmStatus() == 1L) {
						workBean.setIsDisabledDep(false);
					}
				} else {
					workBean.setIsDisabledDep(true);
				}
			} else {
				// If the user role is not authorized
				// return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have the
				// required permissions.");
			}

			return ResponseEntity.ok(workBean);

		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access.");
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("Invalid ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while processing the request.");
		}
	}

	@RequestMapping(value = "/fetchWorks/{agencyId}", method = RequestMethod.GET)
	public List<Work> getWorksByAgencyId(@PathVariable Long agencyId) {

		try {
			return commonService.getWorksByAgencyId(agencyId);
		} catch (Exception e) {

			logger.error("Some Error At Server", e);
			return null;
		}

	}

	@RequestMapping(value = "fetchLoggedInUser", method = RequestMethod.GET)
	public UserBean fetchLoggedInUser(HttpServletRequest request) {

		try {
			return commonService.fetchLoggedInUser();
		} catch (Exception e) {
			logger.error("Cannot fetch logged in userdetails", e);
			return null;
		}

	}

	@RequestMapping(value = "/fetchWorkTenderAgreement/{Id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public WorkTenderBean getWorkTenderAgreement(@PathVariable("Id") Long workId) {
		// user = DMSUtil.getUserDetail();
		// logger.info("User - {}, Role - {} - Fetching Work Tender Agreement",
		// user.getUsername(), user.getAuthorities());
		WorkTenderBean bean = new WorkTenderBean();
		try {
			bean = commonService.fetchWorkTenderAgreement(workId);
			if (bean != null) {
				// logger.info("User - {}, Role - {} - Fetching Work Tender Agreement",
				// user.getUsername(),
				// user.getAuthorities());
			} else {
				String fetchMsg = DMSConstants.ERROR_FETCHING_DATA;
				// logger.error("User - {}, Role - {} - {}", user.getUsername(),
				// user.getAuthorities(), fetchMsg);
			}
		} catch (Exception ex) {
			String fetchMsg = DMSConstants.ERROR_FETCHING_DATA;
			logger.error("error fetching data", fetchMsg, ex);
			// logger.error("")
		}
		return bean;
	}

	@RequestMapping(value = "/downloadDocumentAS/{documentId}", method = RequestMethod.GET)
	public void downloadDocumentAS(@PathVariable Long documentId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" downloadDocument =");

		String fileName = commonService.fetchDownloadFileName(documentId);
		// String fileName = commonService.fetchDownloadFileName(documentId);

		if (fileName != null) {
			File file = new File(fileName);

			// Check if the file exists
			if (!file.exists()) {
				logger.error("File not found: {}", fileName);
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
				return;
			}

			// Try-with-resources for InputStream and OutputStream
			try (InputStream is = new FileInputStream(file); OutputStream os = response.getOutputStream()) {

				// MIME type of the file (set as generic binary)
				response.setContentType("application/octet-stream");
				// Response header for file download
				response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

				byte[] buffer = new byte[1024];
				int len;

				// Read from the file and write into the response
				while ((len = is.read(buffer)) != -1) {
					os.write(buffer, 0, len);
				}

				os.flush(); // Ensure everything is written to the output stream
			} catch (IOException e) {
				logger.error("Error while processing file: {}", fileName, e);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while processing file");
			}
		} else {
			logger.error("Invalid file name for documentId: {}", documentId);
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid file name");
		}
	}

	@RequestMapping(value = "/downloadDocumentTS/{documentId}", method = RequestMethod.GET)
	public void downloadDocumentTS(@PathVariable Long documentId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" downloadDocument =");

		String fileName = commonService.fetchDownloadFileName(documentId);
		// String fileName = commonService.fetchDownloadFileName(documentId);

		if (fileName != null) {
			File file = new File(fileName);

			// Check if the file exists
			if (!file.exists()) {
				logger.error("File not found: {}", fileName);
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
				return;
			}

			// Try-with-resources for InputStream and OutputStream
			try (InputStream is = new FileInputStream(file); OutputStream os = response.getOutputStream()) {

				// MIME type of the file (set as generic binary)
				response.setContentType("application/octet-stream");
				// Response header for file download
				response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

				byte[] buffer = new byte[1024];
				int len;

				// Read from the file and write into the response
				while ((len = is.read(buffer)) != -1) {
					os.write(buffer, 0, len);
				}

				os.flush(); // Ensure everything is written to the output stream
			} catch (IOException e) {
				logger.error("Error while processing file: {}", fileName, e);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while processing file");
			}
		} else {
			logger.error("Invalid file name for documentId: {}", documentId);
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid file name");
		}

	}

	@RequestMapping(value = "fetchTSASDetails/{id}", method = RequestMethod.GET)
	public TSASWorkBean fetchTSASWorkDetails(@PathVariable Long id, HttpServletRequest request) throws ParseException {
		// user = DMSUtil.getUserDetail();
		// String role = DMSUtil.getUserRole(user);
		// logger.info("User - {}, Role - {} - Fetching TSAS Work data",
		// user.getUsername(), user.getAuthorities());

		TSASWorkBean tsasWorkBean = new TSASWorkBean();

		try {
			tsasWorkBean = commonService.fetchTSASWorkDetails(id);
		} catch (Exception e) {
			// TODO: handle exception
			// e.printStackTrace();
			logger.error("Error Fetching Data", e);

		}

		return tsasWorkBean;

	}

	@RequestMapping(value = "fetchWorkStatusByFlag/{flag}", method = RequestMethod.GET)
	public List<WorkStatusBean> fetchWorkStatusByFlag(@PathVariable Long flag, HttpServletRequest request) {

		List<WorkStatusBean> fetchWorkStatusByFlag = new ArrayList<>();
		try {
			fetchWorkStatusByFlag

					= commonService.fetchWorkStatusByFlag(flag);

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error Fetching Data", e);

		}
		return fetchWorkStatusByFlag;

	}

	@RequestMapping(value = "fetchWorkSubStatusByWorkStatus/{workStatusId}/{worksubstatusId}", method = RequestMethod.GET)
	public List<WorkSubStatusBean> fetchWorkSubStatusByWorkStatus(@PathVariable Integer workStatusId,
			@PathVariable Long worksubstatusId, HttpServletRequest request) {

		// List<WorkSubStatusBean> fetchWorkSubStatusByWorkStatus =
		// commonService.fetchWorkSubStatusByWorkStatus(workStatusId,worksubstatusId);
		try {
			return commonService.fetchWorkSubStatusByWorkStatus(worksubstatusId, workStatusId);
		} catch (Exception e) {
			// TODO: handle exception
			// e.printStackTrace();
			logger.error("Error fetching Data ", e);
			return null;

		}

	}

	@RequestMapping(value = "fetchWorkProgress/{id}", method = RequestMethod.GET)
	public WorkProgressBean fetchWorkProgress(@PathVariable Long id, HttpServletRequest request) throws ParseException {

		// user = DMSUtil.getUserDetail();
		// String role = DMSUtil.getUserRole(user);
		// logger.info("User - {}, Role - {} - Fetching TSAS Work data",
		// user.getUsername(), user.getAuthorities());

		try {
			return commonService.fetchWorkProgress(id);
		} catch (Exception e) {
			// TODO: handle exception

			// workProgressBean = commonService.fetchWorkProgress(id);
			logger.error("Error Fetching Data", e);
			return null;
		}

	}

	@RequestMapping(value = "/fetchWorksByAreaOfficer/{userId}", method = RequestMethod.GET)
	public List<WorkBean> getWorksByAreaOffice(@PathVariable Long userId) {
		logger.info("MYAPI HITTED");
		// List<Work> worksByAreaOfficer = commonService.getWorksByAreaOfficer(userId);
		try {
			return commonService.getWorksByAreaOfficer(userId);

		} catch (Exception e) {
			// TODO: handle exception
			// e.printStackTrace();
			logger.error("Error getting data", e);
			return null;
		}
	}

	@RequestMapping(value = "saveGeoTaging", method = RequestMethod.POST)
	public ResponseObject saveGeoTagingData(@RequestBody GeoTaggingBean bean, HttpServletRequest request) {

		ResponseObject response = new ResponseObject();

		try {

			// String remoteIpAddr = request.getHeader("X-Forwarded-For");

			response = commonService.addGeoTaggingData(bean);
			if (response != null) {
				response.setSuccessMessage("GeoTagging Data saved successfully!");
				// logger.info("User - {}, Role - {} - Work saved successfully!",
				// user.getUsername(),
				// user.getAuthorities());
			} else {
				response = new ResponseObject();
				String errorMsg = DMSConstants.ERROR_SAVING_DATA;
				response.setErrorMessage(errorMsg);
				// logger.error("User - {}, Role - {} - {}", user.getUsername(),
				// user.getAuthorities(), errorMsg);
			}
		} catch (Exception e) {
			String errorMsg = e.getMessage();
			response = new ResponseObject();
			response.setErrorMessage(errorMsg);
			// logger.error("User - " + user.getUsername() + ", Role - " +
			// user.getAuthorities() + " - " + errorMsg);
		}
		return response;

	}

	@RequestMapping(value = "getGeoTaggingForWork/{WorkId}", method = RequestMethod.GET)
	public GeoTaggingBean getGeoTaggingForWork(@PathVariable Long WorkId, HttpServletRequest request) {

		try {
			return commonService.getGeoTaggingForWork(WorkId);

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error Getting Data", e);
			return null;
		}
	}

	@RequestMapping(value = "/addWorkProgress", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	@ResponseBody
	public ResponseObject addWorkProgressMobileData(WorkProgressBean workProgressBean, HttpServletRequest request)
			throws Exception {

		ResponseObject response = null;

		try {
			user = DMSUtil.getUserDetail();

			String remoteIpAddr = request.getHeader("X-Forwarded-For");

			logger.info("Header...." + remoteIpAddr);
			response = commonService.addWorkProgress(workProgressBean);
			if (response != null) {
				response.setSuccessMessage("WorkProgress saved successfully!");
				if (user != null) {
					logger.info("User - {}, Role - {} - WorkProgress saved successfully!", user.getUsername(),
							user.getAuthorities());
				}
			} else {
				response = new ResponseObject();
				String errorMsg = DMSConstants.ERROR_SAVING_DATA;
				response.setErrorMessage(errorMsg);
				logger.error("Mobile WorkProgress save failed - {}", errorMsg);
			}
		} catch (Exception e) {
			String errorMsg = e.getMessage();
			response = new ResponseObject();
			response.setErrorMessage(errorMsg);
			logger.error("Mobile WorkProgress save failed", e);
		}
		return response;
	}

	@RequestMapping(value = "/addWorkProgressMoibleData", method = RequestMethod.POST, consumes = {
			"multipart/form-data" })
	@ResponseBody
	public ResponseObject addWorkProgressMoibleData(WorkProgressDataMoibleBean uploadWorkProgressBean,
			HttpServletRequest request) throws Exception {

		ResponseObject response = null;

		try {
			user = DMSUtil.getUserDetail();
			if (user != null) {
				logger.info("User - {}, Role - {} - Adding mobile work progress data", user.getUsername(),
						user.getAuthorities());
			}

			String remoteIpAddr = request.getHeader("X-Forwarded-For");

			logger.info("Header...." + remoteIpAddr);

			response = commonService.addWorkProgressDataMobile(uploadWorkProgressBean);
			if (response != null) {
				response.setSuccessMessage("Work saved successfully!");
				if (user != null) {
					logger.info("User - {}, Role - {} - Work saved successfully!", user.getUsername(),
							user.getAuthorities());
				}
			} else {
				response = new ResponseObject();
				String errorMsg = DMSConstants.ERROR_SAVING_DATA;
				response.setErrorMessage(errorMsg);
				logger.error("Mobile work progress upload failed - {}", errorMsg);
			}
		} catch (Exception e) {
			String errorMsg = e.getMessage();
			response = new ResponseObject();
			response.setErrorMessage(errorMsg);
			logger.error("Mobile work progress upload failed", e);
		}
		return response;
	}

	@RequestMapping(value = "/fetchProgressImagesList/{workId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String fetchProgressImagesList(HttpServletRequest request, @PathVariable Long workId) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching Progress Images List", user.getUsername(), user.getAuthorities());

		String workSubStatusName = request.getParameter("workSubStatusNameE");
		String reasonDelay = request.getParameter("reasonDelay");

		String workStatusName = request.getParameter("workStatusNameE");

		String actionTakenDelay = request.getParameter("actionTakenDelay");

		String createdDate = request.getParameter("createdDate");

		String documentId = request.getParameter("documentId");

		String sSortCol = request.getParameter("iSortCol_0");
		String sSortDir = request.getParameter("sSortDir_0");
		String sColName = request.getParameter("mDataProp_" + sSortCol);

		String subDelayReason = request.getParameter("subDelayReason");

		// Fetch the page number from client
		Integer pageNumber = 0;

		// Integer pageDisplayLength =
		// Integer.valueOf(request.getParameter("iDisplayLength"));

		Sort sort = null;

		Pageable pageable = PageRequest.of(pageNumber, 2, sort);

		WorkProgressImagesJson workProgressImagesJson = commonService.fetchProgressImagesList(pageable,
				!StringUtils.isEmpty(workStatusName) ? workStatusName : null,
				!StringUtils.isEmpty(workSubStatusName) ? workSubStatusName : null,
				!StringUtils.isEmpty(reasonDelay) ? reasonDelay : null,

				!StringUtils.isEmpty(actionTakenDelay) ? actionTakenDelay : null,
				!StringUtils.isEmpty(createdDate) ? createdDate : null,

				!StringUtils.isEmpty(createdDate) ? createdDate : null,

				!StringUtils.isEmpty(subDelayReason) ? subDelayReason : null, workId);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(workProgressImagesJson);

		return json;
	}

	// Method to handle document download for work sample based on document ID.
	@RequestMapping(value = "/downloadDocumentWSPro/{documentId}", method = RequestMethod.GET)
	public void downloadDocumentWSPro(@PathVariable Long documentId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" downloadDocument =");

		String fileName = commonService.fetchDownloadDocumentWSPro(documentId);
		// System.err.println("fileName>>"+fileName);
		// String fileName = commonService.fetchDownloadFileName(documentId);

		if (fileName != null) {
			File file = new File(fileName);

			// Check if the file exists
			if (!file.exists()) {
				logger.error("File not found: {}", fileName);
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
				return;
			}

			// Use try-with-resources for safe handling of InputStream and OutputStream
			try (InputStream is = new FileInputStream(file); OutputStream os = response.getOutputStream()) {

				// MIME type of the file (set as generic binary)
				response.setContentType("application/octet-stream");
				// Response header for file download
				response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

				// Read from the file and write into the response
				byte[] buffer = new byte[1024];
				int len;
				while ((len = is.read(buffer)) != -1) {
					os.write(buffer, 0, len);
				}

				os.flush(); // Ensure everything is written to the output stream
			} catch (IOException e) {
				logger.error("Error while processing file: {}", fileName, e);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while processing file");
			}
		} else {
			logger.error("Invalid file name for documentId: {}", documentId);
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid file name");
		}

	}
	
	
	
	@RequestMapping(value = "fetchWorkStatus", method = RequestMethod.GET)
	public List<WorkStatusBean> fetchWorkStatus(HttpServletRequest request) {
		
	return	commonService.fetchWorkStatus();
		
	}
	
	@RequestMapping(value = "fetchWorkSubStatus", method = RequestMethod.GET)
	public List<WorkSubStatusBean> fetchWorkSubStatus(HttpServletRequest request) {
		
	return	commonService.getWorkSubStatus();
		
	}
	
}
