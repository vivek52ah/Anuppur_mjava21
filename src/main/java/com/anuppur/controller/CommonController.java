package com.anuppur.controller;

import java.beans.PropertyEditorSupport;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import com.anuppur.bean.BlockBean;
import com.anuppur.bean.DepartmentWiseReportRowBean;
import com.anuppur.bean.DmRemarkWiseReportRowBean;
import com.anuppur.bean.PhotoUpdateReportRowBean;
import com.anuppur.bean.CCBean;
import com.anuppur.bean.ChangePasswordBean;
import com.anuppur.bean.ContractorBean;
import com.anuppur.bean.DepartmentMasterBean;
import com.anuppur.bean.DepartmentRemarksBean;
import com.anuppur.bean.DistrictBean;
import com.anuppur.bean.DivisionBean;
import com.anuppur.bean.DmRemarksBean;
import com.anuppur.bean.DocumentUploadDrawingDetailBean;
import com.anuppur.bean.DocumentUploadWorkProgressBean;
import com.anuppur.bean.ExpensesDataBean;
import com.anuppur.bean.FinancialAgencyBean;
import com.anuppur.bean.FinancialHeadBean;
import com.anuppur.bean.FinancialYearBean;
import com.anuppur.bean.GeoTaggingBean;
import com.anuppur.bean.GramPanchayatBean;
import com.anuppur.bean.HeadBean;
import com.anuppur.bean.ImplAgencyBean;
import com.anuppur.bean.ImplAgencyTypeBean;
import com.anuppur.bean.LCBean;
import com.anuppur.bean.LegislativeConstituencyBean;
import com.anuppur.bean.MonthBean;
import com.anuppur.bean.OtherDocListBean;
import com.anuppur.bean.PhysicalPercentageBean;
import com.anuppur.bean.PhysicalPercentageUpgradationBean;
import com.anuppur.bean.SchemeBean;
import com.anuppur.bean.SorYearBean;
import com.anuppur.bean.SubCategoryBean;
import com.anuppur.bean.TSASReviseWorkBean;
import com.anuppur.bean.TSASWorkBean;
import com.anuppur.bean.UserBean;
import com.anuppur.bean.VidhanSabhaBean;
import com.anuppur.bean.WorkBean;
import com.anuppur.bean.WorkCategoryBean;
import com.anuppur.bean.WorkHeadBean;
import com.anuppur.bean.WorkPriorityBean;
import com.anuppur.bean.WorkProgressBean;
import com.anuppur.bean.WorkProgressImageListBean;
import com.anuppur.bean.WorkReportBean;
import com.anuppur.bean.WorkStatusBean;
import com.anuppur.bean.WorkSubDelayResonBean;
import com.anuppur.bean.WorkSubStatusBean;
import com.anuppur.bean.WorkSubTypeBean;
import com.anuppur.bean.WorkTenderBean;
import com.anuppur.bean.WorkTypeBean;
import com.anuppur.bean.YearStatusBean;
import com.anuppur.bean.departmentbean;
import com.anuppur.constants.DMSConstants;
import com.anuppur.entity.DepartmentMaster;
import com.anuppur.entity.Users;
import com.anuppur.entity.Work;
import com.anuppur.json.BlockJson;
import com.anuppur.json.ExpensesDataJson;
import com.anuppur.json.HeadJson;
import com.anuppur.json.ImplAgencyJson;
import com.anuppur.json.MergeWorkJson;
import com.anuppur.json.SchemeJson;
import com.anuppur.json.SdrJson;
import com.anuppur.json.SorJson;
import com.anuppur.json.TASAReviseJson;
import com.anuppur.json.UserJson;
import com.anuppur.json.WorkJson;
import com.anuppur.json.WorkProgressImagesJson;
import com.anuppur.json.WorkStatusJson;
import com.anuppur.repository.FinancialYearRepository;
import com.anuppur.repository.UserRepository;
import com.anuppur.repository.WorkCategoryRepository;
import com.anuppur.repository.WorkRepository;
import com.anuppur.response.ResponseObject;
import com.anuppur.service.CommonService;
import com.anuppur.service.SuperAdminService;
import com.anuppur.service.UserService;
import com.anuppur.service.impl.CommonServiceImpl;
import com.anuppur.util.DMSUtil;
import com.anuppur.util.SHAHashingUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

@RestController
@RequestMapping(value = { "/", "/admin/*", "/dpo/*", "/district/*", "/systemAdmin/*", "/hq/*", "division/*",
		"/agencyAdmin/*", "/ceo/*" })
@PreAuthorize("isAuthenticated()")
public class CommonController extends BaseController {

	public static final Logger logger = LoggerFactory.getLogger(CommonController.class);

	private User user;

	@Autowired
	private UserService userService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WorkCategoryRepository workCategoryRepository;

	@Autowired
	private WorkRepository workRepository;

	@Autowired
	private FinancialYearRepository financialYearRepository;

	@Autowired
	private SuperAdminService superAdminService;

//	@RequestMapping(value = "/changepassword", method = RequestMethod.GET)
//	public ModelAndView viewChangePasswordForm(HttpServletRequest request) {
//
//		user = DMSUtil.getUserDetail();
//		logger.info("User - {}, Role - {} - Displaying Change password page", user.getUsername(),
//				user.getAuthorities());
//
//		ModelAndView modelAndView = new ModelAndView("common/changepassword");
//		return modelAndView;
//
//	}

	@RequestMapping(value = "/changepassword", method = RequestMethod.GET)
	public ModelAndView viewChangePasswordForm(HttpServletRequest request) {

	    User user = DMSUtil.getUserDetail();

	    logger.info("User - {}, Role - {} - Displaying Change password page",
	            user.getUsername(),
	            user.getAuthorities());

	    return new ModelAndView("common/changepassword");
	}
	
	// Method to handle user password change functionality.
//	@RequestMapping(value = "/dochangepassword", method = RequestMethod.POST)
//	public ResponseObject changePassword(@RequestBody ChangePasswordBean changePassword, HttpServletRequest request)
//			throws Exception {
//
//		user = DMSUtil.getUserDetail();
//		logger.info("User - {}, Role - {} - Changing password", user.getUsername(), user.getAuthorities());
//
//		ResponseObject response = new ResponseObject();
//
//		User user = DMSUtil.getUserDetail();
//
//		Users userEntity = userService.findByUserName(user.getUsername());
//
//		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//		if (!StringUtils.isEmpty(changePassword.getCurrentPassword())
//				&& !passwordEncoder.matches(changePassword.getCurrentPassword(), userEntity.getPassword())) {
//
//			response.setErrorMessage("Current password is not valid.");
//			logger.error("User - {}, Role - {} - Current password is not valid.", user.getUsername(),
//					user.getAuthorities());
//			return response;
//		}
//
//		if ((!StringUtils.isEmpty(changePassword.getPassword())
//				&& !StringUtils.isEmpty(changePassword.getConfirmPassword()))
//				&& (!changePassword.getPassword().equals(changePassword.getConfirmPassword()))) {
//
//			response.setErrorMessage("New password and confirm password not matched.");
//			logger.error("User - {}, Role - {} - New password and confirm password not matched.", user.getUsername(),
//					user.getAuthorities());
//			return response;
//		}
//
//		changePassword.setPassword(passwordEncoder.encode(changePassword.getPassword()));
//		userService.changePassword(changePassword, user.getUsername());
//		response.setSuccessMessage("You have successfully changed the password.");
//		logger.info("User - {}, Role - {} - You have successfully changed the password.", user.getUsername(),
//				user.getAuthorities());
//		return response;
//
//	}
	
	@RequestMapping(value = "/dochangepassword", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject changePassword(
	        @RequestBody ChangePasswordBean changePassword,
	        HttpServletRequest request) throws Exception {

	    User user = DMSUtil.getUserDetail();

	    logger.info("User - {}, Role - {} - Changing password",
	            user.getUsername(),
	            user.getAuthorities());

	    ResponseObject response = new ResponseObject();

	    Users userEntity = userService.findByUserName(user.getUsername());

	    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	    // Validate current password
	    String currentPasswordHash = !StringUtils.isEmpty(changePassword.getCurrentPassword())
	            ? SHAHashingUtil.encryptPassword(changePassword.getCurrentPassword()).toString()
	            : null;
	    if (!StringUtils.isEmpty(changePassword.getCurrentPassword())
	            && !passwordEncoder.matches(currentPasswordHash, userEntity.getPassword())) {

	        response.setErrorMessage("Current password is not valid.");

	        logger.error("User - {}, Role - {} - Current password is not valid.",
	                user.getUsername(),
	                user.getAuthorities());

	        return response;
	    }

	    // Validate new password and confirm password
	    if (!StringUtils.isEmpty(changePassword.getPassword())
	            && !StringUtils.isEmpty(changePassword.getConfirmPassword())
	            && !changePassword.getPassword()
	                    .equals(changePassword.getConfirmPassword())) {

	        response.setErrorMessage(
	                "New password and confirm password not matched.");

	        logger.error("User - {}, Role - {} - Password mismatch.",
	                user.getUsername(),
	                user.getAuthorities());

	        return response;
	    }

	    // Encode password
	    changePassword.setPassword(
	            passwordEncoder.encode(SHAHashingUtil.encryptPassword(changePassword.getPassword()).toString()));

	    // Save password
	    userService.changePassword(changePassword, user.getUsername());

	    response.setSuccessMessage(
	            "You have successfully changed the password.");

	    logger.info("User - {}, Role - {} - Password changed successfully.",
	            user.getUsername(),
	            user.getAuthorities());

	    return response;
	}

//	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_SU','ROLE_DEPARTMENT', 'ROLE_CEO')")
	@RequestMapping(value = "/addNewWork", method = RequestMethod.GET)
	public ModelAndView addNewWork(HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - " + user.getUsername() + ", Role - " + user.getAuthorities()
				+ " - Displaying add New Work Form");

		ModelAndView modelAndView = new ModelAndView("common/addNewWorkForm");
		return modelAndView;
	}

	@RequestMapping(value = "printForm/{id}", method = RequestMethod.GET)
	public ModelAndView printForm(@PathVariable Long id, HttpServletRequest request) {
		user = DMSUtil.getUserDetail();

		ModelAndView modelAndView = new ModelAndView("common/printForm");
		return modelAndView;
	}

	// Method to fetch the list of financial years.
	@RequestMapping(value = "fetchFinancialYear", method = RequestMethod.GET)
	public List<FinancialYearBean> fetchFinancialYear(HttpServletRequest request) {
		return commonService.fetchFinancialYear();
	}

	// Method to fetch the list of SOR years.
	@RequestMapping(value = "fetchSorYear", method = RequestMethod.GET)
	public List<SorYearBean> fetchSorYear(HttpServletRequest request) {
		return commonService.fetchSorYear();
	}

	// Method to fetch the list of year statuses.
	@RequestMapping(value = "fetchYear", method = RequestMethod.GET)
	public List<YearStatusBean> fetchYear(HttpServletRequest request) {
		return commonService.fetchYear();
	}

	// Method to fetch the list of distinct work statuses.
	@RequestMapping(value = "fetchWorkStatus", method = RequestMethod.GET)
	public List<String> fetchWorkStatus(HttpServletRequest request) {
		return commonService.fetchDistinctWorkStatus();
	}

	// Method to fetch work status based on the work type ID.
	@RequestMapping(value = "fetchWorkStatusByWorkType/{workTypeId}", method = RequestMethod.GET)
	public List<WorkStatusBean> fetchWorkStatusByWorkType(@PathVariable Long workTypeId, HttpServletRequest request) {
		return commonService.fetchWorkStatusByWorkType(workTypeId);
	}

	// Method to fetch the list of work categories.
	@RequestMapping(value = "fetchWorkCategory", method = RequestMethod.GET)
	public List<WorkCategoryBean> fetchWorkCategory(HttpServletRequest request) {
		return commonService.fetchWorkCategory();
	}

	// Method to fetch the list of blocks.
	@RequestMapping(value = "fetchBlock", method = RequestMethod.GET)
	public List<BlockBean> fetchBlock(HttpServletRequest request) {
		return commonService.fetchBlock();
	}

	// Method to fetch the list of legislative constituencies.
	@RequestMapping(value = "fetchlegislativeCont", method = RequestMethod.GET)
	public List<LegislativeConstituencyBean> fetchlegislativeCont(HttpServletRequest request) {
		return commonService.fetchlegislativeCont();
	}

	// Method to fetch the list of subcategories.
	@RequestMapping(value = "fetchSubCategories", method = RequestMethod.GET)
	public List<SubCategoryBean> fetchSubCategories(HttpServletRequest request) {
		return commonService.fetchSubCategories();
	}

	// Method to fetch work categories based on the work type ID.
	@RequestMapping(value = "fetchWorkCategoryByWorkType/{workTypeId}", method = RequestMethod.GET)
	public List<WorkCategoryBean> fetchWorkCategoryByWorkType(@PathVariable Long workTypeId,
			HttpServletRequest request) {
		return commonService.fetchWorkCategoryByWorkType(workTypeId);
	}

	// Method to fetch the list of districts by division ID.
	@RequestMapping(value = "fetchDistrictByDivision/{divisionId}", method = RequestMethod.GET)
	public List<DistrictBean> fetchDistrictByDivision(@PathVariable Long divisionId, HttpServletRequest request) {
		return commonService.fetchDistrictByDivision(divisionId);
	}

	// Method to fetch legislative constituencies by district code.
	@RequestMapping(value = "fetchLegislativeByDistrict/{districtCode}", method = RequestMethod.GET)
	public List<LegislativeConstituencyBean> fetchLegislativeByDistrict(@PathVariable String districtCode,
			HttpServletRequest request) {
		return commonService.fetchLegislativeByDistrict(districtCode);
	}

	// Method to fetch the subcategories based on the provided category ID.
	@RequestMapping(value = "fetchSubCategoryByCategory/{categoryId}", method = RequestMethod.GET)
	public List<SubCategoryBean> fetchSubCategoryByCategory(@PathVariable Long categoryId, HttpServletRequest request) {
		return commonService.fetchSubCategoryByCategory(categoryId);
	}

	// Method to fetch the list of work types.
	@RequestMapping(value = "fetchWorkTypes", method = RequestMethod.GET)
	public List<WorkTypeBean> fetchWorkTypes(HttpServletRequest request) {
		return commonService.fetchWorkTypes();
	}

	// Method to fetch the list of work subtypes.
	@RequestMapping(value = "fetchWorkSubTypesList", method = RequestMethod.GET)
	public List<WorkSubTypeBean> fetchWorkSubTypes(HttpServletRequest request) {
		return commonService.fetchWorkSubTypes();
	}

	// Method to fetch the list of implementation agencies.
	@RequestMapping(value = "fetchImplAgency", method = RequestMethod.GET)
	public List<ImplAgencyBean> fetchImplAgency(HttpServletRequest request) {
		return commonService.fetchImplAgency();
	}

	// Method to fetch the list of implementation agency types.
	@RequestMapping(value = "fetchImplAgencyType", method = RequestMethod.GET)
	public List<ImplAgencyTypeBean> fetchImplAgencyType(HttpServletRequest request) {
		return commonService.fetchImplAgencyType();
	}

	// Method to fetch the list of schemes.
	@RequestMapping(value = "fetchSchemes", method = RequestMethod.GET)
	public List<SchemeBean> fetchSchemes(HttpServletRequest request) {
		return commonService.fetchSchemes();
	}

	// Method to fetch the list of heads.
	@RequestMapping(value = "fetchHeads", method = RequestMethod.GET)
	public List<HeadBean> fetchHeads(HttpServletRequest request) {
		return commonService.fetchHeads();
	}

	// Method to fetch the list of sub-work statuses.
	@RequestMapping(value = "fetchSubWorkStatus", method = RequestMethod.GET)
	public List<WorkSubStatusBean> fetchSubWorkStatus(HttpServletRequest request) {
		return commonService.fetchSubWorkStatus();
	}

	// Method to fetch the list of distinct legislative constituencies.
	@RequestMapping(value = "fetchLegislativeConstituency", method = RequestMethod.GET)
	public List<String> fetchLegislativeConstituency(HttpServletRequest request) {
		return commonService.fetchDistinctLegislativeConstituency();
	}

	// Method to fetch the list of LCs based on the district name.
	@RequestMapping(value = "fetchLCsByDistrictName/{districtName}", method = RequestMethod.GET)
	public List<LCBean> fetchLCsByDistrictName(@PathVariable String districtName, HttpServletRequest request) {
		return commonService.fetchLCsByDistrictName(districtName);
	}

	// Method to fetch work status based on the provided flag.
	@RequestMapping(value = "fetchWorkStatusByFlag/{flag}", method = RequestMethod.GET)
	public List<WorkStatusBean> fetchWorkStatusByFlag(@PathVariable Long flag, HttpServletRequest request) {
		return commonService.fetchWorkStatusByFlag(flag);
	}

	// Method to fetch work sub-statuses based on the given work status ID.
	@RequestMapping(value = "fetchWorkSubStatusByWorkStatus/{worksubstatusId}/{workStatusId}", method = RequestMethod.GET)
	public List<WorkSubStatusBean> fetchWorkSubStatusByWorkStatus(@PathVariable Integer workStatusId,
			@PathVariable Long worksubstatusId, HttpServletRequest request) {
		return commonService.fetchWorkSubStatusByWorkStatus(worksubstatusId, workStatusId);
	}

	// Method to fetch the physical percentage of work based on the work sub-status
	// ID.
	@RequestMapping(value = "fetchPercByWorkSubStatus/{workSubStatusId}", method = RequestMethod.GET)
	public PhysicalPercentageBean fetchPercByWorkSubStatus(@PathVariable Long workSubStatusId,
			HttpServletRequest request) {
		return commonService.fetchPercByWorkSubStatus(workSubStatusId);
	}

	// Method to fetch physical percentage upgradation based on work sub-status ID.
	@RequestMapping(value = "fetchPercByWorkSubStatusUpgrad/{workSubStatusId}", method = RequestMethod.GET)
	public PhysicalPercentageUpgradationBean fetchPercByWorkSubStatusUpgrad(@PathVariable Long workSubStatusId,
			HttpServletRequest request) {
		return commonService.fetchPercByWorkSubStatusUpgrad(workSubStatusId);
	}

	// Method to fetch the list of blocks by district code.
	@RequestMapping(value = "fetchBlocksByDistrict/{districtCode}", method = RequestMethod.GET)
	public List<BlockBean> fetchBlocksByDistrict(@PathVariable String districtCode, HttpServletRequest request) {
		return commonService.fetchBlocksByDistrict(districtCode);
	}

	// Method to fetch Gram Panchayat data by district and block code.
	@RequestMapping(value = "fetchGramPanchayatByBlockCode/{districtCode}/{blockCode}", method = RequestMethod.GET)
	public List<GramPanchayatBean> fetchGramPanchayatByBlockCode(@PathVariable String districtCode,
			@PathVariable String blockCode, HttpServletRequest request) {
		return commonService.fetchGramPanchayatByBlockCode(districtCode, blockCode);
	}

	// Method to fetch blocks based on the district name.
	@RequestMapping(value = "fetchBlocksByDistrictName/{districtName}", method = RequestMethod.GET)
	public List<BlockBean> fetchBlocksByDistrictName(@PathVariable String districtName, HttpServletRequest request) {
		return commonService.fetchBlocksByDistrictName(districtName);
	}

	// Method to fetch work categories based on the provided work type ID.
	@RequestMapping(value = "fetchCategoryByWorkType/{workTypeId}", method = RequestMethod.GET)
	@ResponseBody
	public List<WorkCategoryBean> fetchCategoryByWorkType(HttpServletRequest request, @PathVariable String workTypeId) {
		return commonService.fetchCategoryByWorkType(Long.parseLong(workTypeId));
	}

	// Method to fetch the list of work progress images.
	@RequestMapping(value = "fetchWorkProgressImageDataList", method = RequestMethod.GET)
	public List<WorkProgressImageListBean> fetchWorkProgressImageDataList(HttpServletRequest request) {
		return commonService.fetchWorkProgressImageList();

	}

	@RequestMapping(value = "/manageOngoingWorks", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView manageOngoingWorks(HttpServletRequest request,
			@RequestParam(required = false) String departmentId,
			@RequestParam(required = false) String financialYearId,
			@RequestParam(required = false) String workStatus,
			@RequestParam(required = false) String departmentRemark) {

		user = DMSUtil.getUserDetail();

		ModelAndView modelAndView = null;

		logger.info("User - " + user.getUsername() + ", Role - " + user.getAuthorities()
				+ " - Displaying manage Ongoing Works");
		modelAndView = new ModelAndView("common/manageOngoingWorks");
		String role = DMSUtil.getUserRole(user);
		modelAndView.addObject("role", role);
		UserBean userBean = fetchLoggedInUserDetails(request);
		logger.info(userBean.getLoggedInUserRole() + "role");
		modelAndView.addObject("roleName", userBean.getLoggedInUserRole());
		modelAndView.addObject("division", userBean.getDivisionId());
		modelAndView.addObject("departmentId", departmentId);
		modelAndView.addObject("financialYearId", financialYearId);
		modelAndView.addObject("workStatus", workStatus);
		modelAndView.addObject("departmentRemark", departmentRemark);
		// Store URL params in session for use by fetchWorksList
		if (workStatus != null && !workStatus.isEmpty()) {
			request.getSession().setAttribute("urlWorkStatus", workStatus);
		} else {
			request.getSession().removeAttribute("urlWorkStatus");
		}
		if (departmentId != null && !departmentId.isEmpty()) {
			request.getSession().setAttribute("urlDepartmentId", departmentId);
		} else {
			request.getSession().removeAttribute("urlDepartmentId");
		}
		if (financialYearId != null && !financialYearId.isEmpty()) {
			request.getSession().setAttribute("urlFinancialYearId", financialYearId);
		} else {
			request.getSession().removeAttribute("urlFinancialYearId");
		}
		if (departmentRemark != null && !departmentRemark.isEmpty()) {
			request.getSession().setAttribute("urlDepartmentRemark", departmentRemark);
		} else {
			request.getSession().removeAttribute("urlDepartmentRemark");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/viewCompletedWork", method = RequestMethod.GET)
	public ModelAndView viewCompletedWork(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();

		ModelAndView modelAndView = null;

		logger.info("User - " + user.getUsername() + ", Role - " + user.getAuthorities()
				+ " - Displaying manage Ongoing Works");
		modelAndView = new ModelAndView("common/viewCompletedWork");
		String role = DMSUtil.getUserRole(user);
		modelAndView.addObject("role", role);
		UserBean userBean = fetchLoggedInUserDetails(request);
		modelAndView.addObject("roleName", userBean.getLoggedInUserRole());
		modelAndView.addObject("division", userBean.getDivisionId());
		return modelAndView;
	}

	@RequestMapping(value = "/agencyWiseReport", method = RequestMethod.GET)
	public ModelAndView agencyWiseReportView(HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Manage WorkCategory page", user.getUsername(),
				user.getAuthorities());

		return new ModelAndView("common/agencyWiseReport");
	}

	@RequestMapping(value = "/schemeWiseReport", method = RequestMethod.GET)
	public ModelAndView schemeWiseReportView(HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Manage WorkCategory page", user.getUsername(),
				user.getAuthorities());

		return new ModelAndView("common/schemeWiseReport");
	}

	@RequestMapping(value = "/yearWiseReport", method = RequestMethod.GET)
	public ModelAndView yearWiseReportView(HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Manage WorkCategory page", user.getUsername(),
				user.getAuthorities());

		return new ModelAndView("common/yearWiseReport");
	}

//	@RequestMapping(value = "/reports", method = RequestMethod.GET)
//	public ModelAndView reportsView(HttpServletRequest request) {
//		user = DMSUtil.getUserDetail();
//		logger.info("User - {}, Role - {} - Displaying Manage WorkCategory page", user.getUsername(),
//				user.getAuthorities());
//
//		return new ModelAndView("common/reports");
//	}
	
	
	@RequestMapping(value = "/inspectionReport", method = RequestMethod.GET)
	public ModelAndView inspectionReportsView(HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Manage WorkCategory page", user.getUsername(),
				user.getAuthorities());

		return new ModelAndView("common/inspectionReport");
	}
	
	@RequestMapping(value = "/workExpenditureReport", method = RequestMethod.GET)
	public ModelAndView workExpenditureReportView(HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Manage WorkCategory page", user.getUsername(),
				user.getAuthorities());

		return new ModelAndView("common/workExpenditureReport");
	}

	@RequestMapping(value = "/segmentWiseRport", method = RequestMethod.GET)
	public ModelAndView segmentWiseRportView(HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Manage WorkCategory page", user.getUsername(),
				user.getAuthorities());

		return new ModelAndView("common/segmentWiseRport");
	}

	@RequestMapping(value = "/schemeYearWiseReport", method = RequestMethod.GET)
	public ModelAndView schemeYearWiseReportView(HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Manage WorkCategory page", user.getUsername(),
				user.getAuthorities());

		return new ModelAndView("common/schemeYearWiseReport");
	}

	@RequestMapping(value = "/divisionReport", method = RequestMethod.GET)
	public ModelAndView divisionReportReportView(HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Manage WorkCategory page", user.getUsername(),
				user.getAuthorities());

		return new ModelAndView("common/divisionReport");
	}

	@RequestMapping(value = "/drawingStatusReport", method = RequestMethod.GET)
	public ModelAndView drawingStatusReportView(HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Manage WorkCategory page", user.getUsername(),
				user.getAuthorities());

		return new ModelAndView("common/drawingStatusReport");
	}

	@RequestMapping(value = "/asIssuedReport", method = RequestMethod.GET)
	public ModelAndView asIssuedReportView(HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Manage WorkCategory page", user.getUsername(),
				user.getAuthorities());

		return new ModelAndView("common/asIssuedReport");
	}

	@RequestMapping(value = "/physicalPercentageWiseReport", method = RequestMethod.GET)
	public ModelAndView physicalPercentageWiseReportView(HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Manage WorkCategory page", user.getUsername(),
				user.getAuthorities());

		return new ModelAndView("common/physicalPercentageWiseReport");
	}

	@RequestMapping(value = "/generatePptReports", method = RequestMethod.GET)
	public ModelAndView generatePPTView(HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Manage WorkCategory page", user.getUsername(),
				user.getAuthorities());

		return new ModelAndView("common/generatePptReports");
	}

	@RequestMapping(value = "/departmentWiseWorksReport", method = RequestMethod.GET)
	public ModelAndView departmentWiseWorksReportView(HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Department Wise Works Report page", user.getUsername(),
				user.getAuthorities());
		return new ModelAndView("common/departmentWiseWorksReport");
	}

	@RequestMapping(value = "/reports", method = RequestMethod.GET)
	public ModelAndView reportsView(HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		UserBean userBean = fetchLoggedInUserDetails(request);
		ModelAndView mav = new ModelAndView("common/reports");
		mav.addObject("roleName", userBean.getLoggedInUserRole());
		return mav;
	}

	@RequestMapping(value = "/fetchDepartmentWiseReport", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResponseEntity<?> fetchDepartmentWiseReport(
			@RequestParam(required = false) String agencyIds,
			@RequestParam(required = false) String financialYearIds,
			HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching Department Wise Report", user.getUsername(),
				user.getAuthorities());
		try {
			List<Long> agencyIdList = convertToList(agencyIds);
			List<Long> fyIdList = convertToList(financialYearIds);
			List<DepartmentWiseReportRowBean> result = commonService.getDepartmentWiseReport(agencyIdList, fyIdList);
			return ResponseEntity.ok(result);
		} catch (NumberFormatException e) {
			logger.error("Invalid filter parameter: {}", e.getMessage());
			return ResponseEntity.badRequest().body("{\"errorMessage\": \"Invalid filter parameter: " + e.getMessage() + "\"}");
		} catch (Exception e) {
			logger.error("Error fetching department wise report: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{\"errorMessage\": \"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping(value = "/photoUpdateReport", method = RequestMethod.GET)
	public ModelAndView photoUpdateReportView(HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Photo Update Report page", user.getUsername(),
				user.getAuthorities());
		return new ModelAndView("common/photoUpdateReport");
	}

	@RequestMapping(value = "/fetchPhotoUpdateReport", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResponseEntity<?> fetchPhotoUpdateReport(
			@RequestParam(required = false) String departmentIds,
			HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching Photo Update Report", user.getUsername(),
				user.getAuthorities());
		try {
			List<Long> deptIdList = convertToList(departmentIds);
			List<PhotoUpdateReportRowBean> result = commonService.getPhotoUpdateReport(deptIdList);
			return ResponseEntity.ok(result);
		} catch (NumberFormatException e) {
			logger.error("Invalid filter parameter: {}", e.getMessage());
			return ResponseEntity.badRequest().body("{\"errorMessage\": \"Invalid filter parameter: " + e.getMessage() + "\"}");
		} catch (Exception e) {
			logger.error("Error fetching photo update report: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{\"errorMessage\": \"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping(value = "/dmRemarkWiseReport", method = RequestMethod.GET)
	public ModelAndView dmRemarkWiseReportView(HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying DM Remark Wise Report page", user.getUsername(),
				user.getAuthorities());
		return new ModelAndView("common/dmRemarkWiseReport");
	}

	@RequestMapping(value = "/fetchDmRemarkWiseReport", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResponseEntity<?> fetchDmRemarkWiseReport(
			@RequestParam(required = false) String deptMasterIds,
			@RequestParam(required = false) String implAgencyIds,
			HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching DM Remark Wise Report", user.getUsername(),
				user.getAuthorities());
		try {
			List<Long> deptMasterIdList = convertToList(deptMasterIds);
			List<Long> implAgencyIdList = convertToList(implAgencyIds);
			List<DmRemarkWiseReportRowBean> result = commonService.getDmRemarkWiseReport(deptMasterIdList, implAgencyIdList);
			return ResponseEntity.ok(result);
		} catch (NumberFormatException e) {
			logger.error("Invalid filter parameter: {}", e.getMessage());
			return ResponseEntity.badRequest().body("{\"errorMessage\": \"Invalid filter parameter: " + e.getMessage() + "\"}");
		} catch (Exception e) {
			logger.error("Error fetching DM remark wise report: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{\"errorMessage\": \"" + e.getMessage() + "\"}");
		}
	}

	// Method to handle uploading of work progress inspection images.
	@RequestMapping(value = "/uploadWorkProgressImages", method = RequestMethod.POST, consumes = {
			"multipart/form-data" })
	public ResponseObject uploadInspectionImages(WorkProgressImageListBean workProgressImageListBean,
			HttpServletRequest request) throws Exception {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Adding Inspection Images", user.getUsername(), user.getAuthorities());
		ResponseObject response = null;
		try {
			response = commonService.uploadWorkProgressImages(workProgressImageListBean);
			if (response != null) {
				response.setSuccessMessage("Work saved successfully!");
				logger.info("User - {}, Role - {} - Inspection Images saved successfully!", user.getUsername(),
						user.getAuthorities());
			} else {
				response = new ResponseObject();
				String errorMsg = DMSConstants.ERROR_SAVING_DATA;
				response.setErrorMessage(errorMsg);
				logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
			}
		} catch (Exception e) {
			String errorMsg = e.getMessage();
			response = new ResponseObject();
			response.setErrorMessage(errorMsg);
			logger.error("User - " + user.getUsername() + ", Role - " + user.getAuthorities() + " - " + errorMsg);
		}
		return response;
	}

	
	//convert multiple ids by sumit 
	public List<Long> convertToList(String input) {
	    if (input == null || input.trim().isEmpty()) {
	    return Collections.emptyList();
	    }

	    return Arrays.stream(input.split(","))
	                 .map(Long::parseLong)
	                 .collect(Collectors.toList());
	}

	
	
	// Method to fetch the list of ongoing works with filters and pagination.
	@RequestMapping(value = "/fetchWorksList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String fetchWorksList(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching OngoingWorks List", user.getUsername(), user.getAuthorities());

		String scheme = request.getParameter("scheme");
		String workType = request.getParameter("workType1");
	//	financialYear1
		String financialYear = request.getParameter("financialYear1");
		String implementationAgency = request.getParameter("implementationAgency");
		String blockId = request.getParameter("blockId");
		String divisionId = request.getParameter("divisionId");
		String districtId = request.getParameter("districtId");
		String workStatus = request.getParameter("workStatus");
		String workStatusId = request.getParameter("workStatusId");
		String workSubTypeId = request.getParameter("workSubTypeId");
		String workPriorityId = request.getParameter("workPriorityId");
		String financialHeadId = request.getParameter("financialHeadId1");
		String vidhanSabhaId = request.getParameter("vidhanSabhaId1");
		String workNameFilter = request.getParameter("workNameFilter");
		// Also check for DataTables global search parameter (keyword)
		if ((workNameFilter == null || workNameFilter.isEmpty()) && request.getParameter("keyword") != null) {
			workNameFilter = request.getParameter("keyword");
		}
		String departmentRemark = request.getParameter("departmentRemark");
		String department = request.getParameter("department");
		// Use session-stored URL params (set by manageOngoingWorks when navigating from dept-wise report)
		// Session takes priority over localStorage-restored workStatusId on every call
		Object sessionWorkStatus = request.getSession().getAttribute("urlWorkStatus");
		if (!StringUtils.isEmpty(workStatusId)) {
			request.getSession().removeAttribute("urlWorkStatus");
			sessionWorkStatus = null;
		}
		if (sessionWorkStatus != null) {
			workStatus = sessionWorkStatus.toString();
			workStatusId = null;
		}
		// Also: if workStatus string param is directly passed (from DataTables data function), it takes priority
		if (workStatus != null && !workStatus.isEmpty()) {
			workStatusId = null; // clear localStorage-restored IDs
		}
		Object sessionDeptId = request.getSession().getAttribute("urlDepartmentId");
		if (sessionDeptId != null && (department == null || department.isEmpty())) {
			department = sessionDeptId.toString();
		}
		Object sessionFyId = request.getSession().getAttribute("urlFinancialYearId");
		if (sessionFyId != null && (financialYear == null || financialYear.isEmpty())) {
			financialYear = sessionFyId.toString();
		}
		String sSortCol = request.getParameter("iSortCol_0");
		String sSortDir = request.getParameter("sSortDir_0");
		String sColName = request.getParameter("mDataProp_" + sSortCol);
		
		
		
		List<Long> fyList = convertToList(financialYear);
		List<Long> workTypeList = convertToList(workType);
		List<Long> agencyList = convertToList(implementationAgency);
		List<Long> statusList = convertToList(workStatusId);
		List<Long> priorityList = convertToList(workPriorityId);
		List<Long> headList = convertToList(financialHeadId);
		List<Long> vsList = convertToList(vidhanSabhaId);
		List<Long> departmentList = convertToList(department);
	//	List<Integer> districtList = convertToList(districtId);
	//	List<Integer> divisionList = convertToList(divisionId);
	//	List<Integer> workSubTypeList = convertToList(workSubTypeId);


		// Fetch the page number from client
		Integer pageNumber = 0;

		// Fetch search parameter
		//String searchParameterWorkName = request.getParameter("searchBoxVal");
		// String searchParameterAsNo = request.getParameter("searchBoxValAsNo");
		String searchParameterWorkNo = request.getParameter("searchBoxVal");

		// String searchByDivision= request.getParameter("searchByDivision");

		String searchByDivision = "0";
		// Fetch Page display length. DataTables may send legacy iDisplayLength or 1.10 length.
		String displayLengthParam = request.getParameter("iDisplayLength");
		if (StringUtils.isEmpty(displayLengthParam)) {
			displayLengthParam = request.getParameter("length");
		}
		Integer pageDisplayLength = !StringUtils.isEmpty(displayLengthParam) ? Integer.valueOf(displayLengthParam) : 10;

		String displayStartParam = request.getParameter("iDisplayStart");
		if (StringUtils.isEmpty(displayStartParam)) {
			displayStartParam = request.getParameter("start");
		}
		if (!StringUtils.isEmpty(displayStartParam)) {
			pageNumber = (Integer.valueOf(displayStartParam) / pageDisplayLength);
		}

		Sort sort = null;
		if (sColName != null) {
			if (StringUtils.equals("asc", sSortDir)) {
				sort = Sort.by(Direction.ASC, sColName);
			} else {
				sort = Sort.by(Direction.DESC, sColName);
			}
		} else {
			sort = Sort.by(Direction.DESC, "id");// default sorting
		}

		Pageable pageable = PageRequest.of(pageNumber, pageDisplayLength, sort);

		WorkJson workJson = commonService.fetchWorksList(pageable,
				!StringUtils.isEmpty(searchParameterWorkNo) ? searchParameterWorkNo : null,
				null,  // workName parameter (not used, use workNameFilter instead)
				!StringUtils.isEmpty(scheme) ? scheme : null, 
				workTypeList,
				fyList,
				agencyList,
				!StringUtils.isEmpty(blockId) ? blockId : null, 
				!StringUtils.isEmpty(workStatus) ? workStatus : null,
				!StringUtils.isEmpty(districtId) ? districtId : null,
				!StringUtils.isEmpty(divisionId) ? divisionId : null,
				!StringUtils.isEmpty(searchByDivision) ? searchByDivision : null,
				!StringUtils.isEmpty(workSubTypeId) ? workSubTypeId : null,
				statusList,
				priorityList,
				headList,
				vsList,
				!StringUtils.isEmpty(workNameFilter) ? workNameFilter : null,
				!StringUtils.isEmpty(departmentRemark) ? departmentRemark : null,
				(departmentList != null && !departmentList.isEmpty()) ? departmentList : null
				);


		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(workJson);

		return json;
	}

	// Method to fetch the list of ongoing works by agency or division based on the
	// mode.
	@RequestMapping(value = "/fetchWorksListByAgency/{mode}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public List<WorkReportBean> fetchWorksListByAgency(@PathVariable Long mode, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching OngoingWorks List", user.getUsername(), user.getAuthorities());
		String genType = "";
		// String genType = request.getParameter("financialYear");
		List<WorkReportBean> workReportBean = new ArrayList<>();
		if (mode == 6) {
			workReportBean = commonService.fetchWorksListByDivision(mode);
		} else {
			workReportBean = commonService.fetchWorksListByAgency(mode);
		}

		return workReportBean;
	}

	// Method to fetch the list of ongoing works based on drawing details and mode.
	@RequestMapping(value = "/fetchWorksListByDrawing/{mode}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public List<WorkReportBean> fetchWorksListByDrawing(@PathVariable Long mode, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching OngoingWorks List", user.getUsername(), user.getAuthorities());
		String genType = "";
		// String genType = request.getParameter("financialYear");
		List<WorkReportBean> workReportBean = new ArrayList<>();
		workReportBean = commonService.fetchWorksListByDrawing(mode);
		return workReportBean;
	}

	// Method to export agency-wise report data to an Excel file based on the
	// provided ID.
	@RequestMapping(value = "/exportAgencyWiseReportDataLatest/{id}", method = RequestMethod.GET)
	public void exportAgencyWiseReportDataLatest(@PathVariable Long id, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {

		List<Object[]> agencyWiseEntityList = commonService.fetchexportDrawingReportData(id);

		try {
			String fileName = "";
			if (id == 1) {
				fileName = "PendingReport.xlsx";
			} else if (id == 2) {
				fileName = "AsIssuedReport.xlsx";
			} else if (id == 3) {
				fileName = "PhysicalPercentageWiseReport.xlsx";
			}

			ClassPathResource pdfFile = new ClassPathResource(fileName);
			InputStream excelFile = pdfFile.getInputStream();
			XSSFWorkbook workbook = new XSSFWorkbook(excelFile);

			XSSFSheet sheet1 = workbook.getSheetAt(0);

			int snoCounter = 1, rowCounter = 2;

			XSSFRow row = null;
			for (Object[] objArr : agencyWiseEntityList) {

				row = sheet1.createRow(rowCounter);
				row.createCell(0).setCellValue(snoCounter++);
				if (objArr[0] != null) {
					row.createCell(1).setCellValue((String) objArr[0]);
				}

				if (objArr[1] != null) {
					row.createCell(2).setCellValue((String) objArr[1]);
				}

				if (id == 1 || id == 2) {
					if (objArr[2] != null) {
						row.createCell(3).setCellValue((String) objArr[2]);

					}

					if (objArr[3] != null) {
						row.createCell(4).setCellValue((String) objArr[3]);

					}
				} else if (id == 3) {
					if (objArr[2] != null) {
						row.createCell(3).setCellValue((Integer) objArr[2]);

					}

					if (objArr[3] != null) {
						if (objArr[3] instanceof BigDecimal) {
							row.createCell(4).setCellValue(((BigDecimal) objArr[3]).longValue());
						} else if (objArr[3] instanceof Long) {
							row.createCell(4).setCellValue((Long) objArr[3]);
						} else {
							// Handle other cases or throw an exception if needed
						}
					}
				}

				rowCounter++;

			}

			row = sheet1.createRow(rowCounter);

			ByteArrayOutputStream outStream = new ByteArrayOutputStream();

			workbook.write(outStream);

			byte[] outArray = outStream.toByteArray();

			response.setContentLength(outArray.length);

			response.setContentType("application/octet-stream");

			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", fileName);
			response.setHeader(headerKey, headerValue);
			OutputStream outStream1 = response.getOutputStream();
			outStream1.write(outArray);
			outStream1.flush();
			workbook.close();

		} catch (FileNotFoundException e) {
			logger.error("FILE NOT FOUND", e);
		} catch (IOException e) {
			logger.error("FILE NOT FOUND", e);
		}
	}

	// Method to fetch the list of handover works with filters and pagination.
	@RequestMapping(value = "/fetchHandoverWorksList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String fetchHandoverWorksList(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching OngoingWorks List", user.getUsername(), user.getAuthorities());

		String scheme = request.getParameter("scheme");
		String workType = request.getParameter("workType");
		String financialYear = request.getParameter("financialYear");
		String implementationAgency = request.getParameter("implementationAgency");
		logger.info("implementationAgency.....1111..." + implementationAgency);
		String blockId = request.getParameter("blockId");
		String divisionId = request.getParameter("divisionId");
		logger.info("divisionId====== " + divisionId);
		String districtId = request.getParameter("districtId");
		logger.info("districtId====== " + districtId);
		String workStatus = request.getParameter("workStatus");
		String workStatusId = request.getParameter("workStatusId");
		logger.info("workStatusId====== " + workStatusId);
		String workSubTypeId = request.getParameter("workSubTypeId");
		logger.info("workSubTypeId====== " + workSubTypeId);
		String sSortCol = request.getParameter("iSortCol_0");
		String sSortDir = request.getParameter("sSortDir_0");
		String sColName = request.getParameter("mDataProp_" + sSortCol);

		// String districtName = request.getParameter("districtName");

		// Fetch the page number from client
		Integer pageNumber = 0;

		// Fetch search parameter
		String searchParameterWorkName = request.getParameter("searchBoxVal");
		// String searchParameterAsNo = request.getParameter("searchBoxValAsNo");
		String searchParameterWorkNo = request.getParameter("searchBoxValWorkNo");

		// String searchByDivision= request.getParameter("searchByDivision");

		String searchByDivision = "0";
		// Fetch Page display length. DataTables may send legacy iDisplayLength or 1.10 length.
		String displayLengthParam = request.getParameter("iDisplayLength");
		if (StringUtils.isEmpty(displayLengthParam)) {
			displayLengthParam = request.getParameter("length");
		}
		Integer pageDisplayLength = !StringUtils.isEmpty(displayLengthParam) ? Integer.valueOf(displayLengthParam) : 10;

		String displayStartParam = request.getParameter("iDisplayStart");
		if (StringUtils.isEmpty(displayStartParam)) {
			displayStartParam = request.getParameter("start");
		}
		if (!StringUtils.isEmpty(displayStartParam)) {
			pageNumber = (Integer.valueOf(displayStartParam) / pageDisplayLength);
		}

		Sort sort = null;
		if (sColName != null) {
			if (StringUtils.equals("asc", sSortDir)) {
				sort = Sort.by(Direction.ASC, sColName);
			} else {
				sort = Sort.by(Direction.DESC, sColName);
			}
		} else {
			sort = Sort.by(Direction.DESC, "id");// default sorting
		}

		Pageable pageable = PageRequest.of(pageNumber, pageDisplayLength, sort);

		WorkJson workJson = commonService.fetchHandoverWorksList(pageable,
				!StringUtils.isEmpty(searchParameterWorkNo) ? searchParameterWorkNo : null,
				!StringUtils.isEmpty(searchParameterWorkName) ? searchParameterWorkName : null,
				!StringUtils.isEmpty(scheme) ? scheme : null, !StringUtils.isEmpty(workType) ? workType : null,
				!StringUtils.isEmpty(financialYear) ? financialYear : null,
				!StringUtils.isEmpty(implementationAgency) ? implementationAgency : null,
				!StringUtils.isEmpty(blockId) ? blockId : null, !StringUtils.isEmpty(workStatus) ? workStatus : null,
				!StringUtils.isEmpty(divisionId) ? divisionId : null,
				!StringUtils.isEmpty(districtId) ? districtId : null,
				!StringUtils.isEmpty(searchByDivision) ? searchByDivision : null,
				!StringUtils.isEmpty(workSubTypeId) ? workSubTypeId : null,
				!StringUtils.isEmpty(workStatusId) ? workStatusId : null);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(workJson);

		return json;
	}

	// Method to fetch the revised TSAS list with filters and pagination based on
	// work type ID.
	@RequestMapping(value = "/fetchTSASRevisedList/{workTypeId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String fetchTSASRevisedList(HttpServletRequest request, @PathVariable Long workTypeId) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching TSASRevised List", user.getUsername(), user.getAuthorities());

		String typeDoc = request.getParameter("typeDoc");
		String rvOrderNo = request.getParameter("rvOrderNo");

		String rvOrderDate = request.getParameter("rvOrderDate");
		String rvAmt = request.getParameter("rvAmt");
		String rvFileId = request.getParameter("rvFileId");
		String rvRemarks = request.getParameter("rvRemarks");

		String sSortCol = request.getParameter("iSortCol_0");
		String sSortDir = request.getParameter("sSortDir_0");
		String sColName = request.getParameter("mDataProp_" + sSortCol);

		// Fetch the page number from client
		Integer pageNumber = 0;

		// Fetch search parameter
		String searchParameterWorkName = request.getParameter("searchBoxVal");

		// String searchParameterWorkNo = request.getParameter("searchBoxValWorkNo");

		String searchByDivision = "0";
		// Fetch Page display length. DataTables may send legacy iDisplayLength or 1.10 length.
		String progressDisplayLengthParam = request.getParameter("iDisplayLength");
		if (StringUtils.isEmpty(progressDisplayLengthParam)) {
			progressDisplayLengthParam = request.getParameter("length");
		}
		Integer pageDisplayLength = !StringUtils.isEmpty(progressDisplayLengthParam)
				? Integer.valueOf(progressDisplayLengthParam)
				: 10;

		String progressDisplayStartParam = request.getParameter("iDisplayStart");
		if (StringUtils.isEmpty(progressDisplayStartParam)) {
			progressDisplayStartParam = request.getParameter("start");
		}
		if (!StringUtils.isEmpty(progressDisplayStartParam)) {
			pageNumber = (Integer.valueOf(progressDisplayStartParam) / pageDisplayLength);
		}

		Sort sort = null;
		if (sColName != null) {
			if (StringUtils.equals("asc", sSortDir)) {
				sort = Sort.by(Direction.ASC, sColName);
			} else {
				sort = Sort.by(Direction.DESC, sColName);
			}
		} else {
			sort = Sort.by(Direction.DESC, "id");// default sorting
		}

		Pageable pageable = PageRequest.of(pageNumber, pageDisplayLength, sort);

		TASAReviseJson tsReviseJson = commonService.fetchTSASRevisedList(pageable,
				!StringUtils.isEmpty(typeDoc) ? typeDoc : null, !StringUtils.isEmpty(rvOrderNo) ? rvOrderNo : null,
				!StringUtils.isEmpty(rvOrderDate) ? rvOrderDate : null, !StringUtils.isEmpty(rvAmt) ? rvAmt : null,
				!StringUtils.isEmpty(searchParameterWorkName) ? searchParameterWorkName : null,
				!StringUtils.isEmpty(rvFileId) ? rvFileId : null, !StringUtils.isEmpty(rvRemarks) ? rvRemarks : null,
				workTypeId);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(tsReviseJson);
		return json;
	}

	// Method to fetch the list of progress images based on work type and various
	// filters.
	@RequestMapping(value = "/fetchProgressImagesList/{workTypeId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String fetchProgressImagesList(HttpServletRequest request, @PathVariable Long workTypeId) {

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

		// Fetch search parameter
		String searchParameterWorkName = request.getParameter("searchBoxVal");

		// String searchParameterWorkNo = request.getParameter("searchBoxValWorkNo");

		String searchByDivision = "0";
		// Fetch Page display length. DataTables may send legacy iDisplayLength or 1.10 length.
		String expensesDisplayLengthParam = request.getParameter("iDisplayLength");
		if (StringUtils.isEmpty(expensesDisplayLengthParam)) {
			expensesDisplayLengthParam = request.getParameter("length");
		}
		Integer pageDisplayLength = !StringUtils.isEmpty(expensesDisplayLengthParam)
				? Integer.valueOf(expensesDisplayLengthParam)
				: 10;

		String expensesDisplayStartParam = request.getParameter("iDisplayStart");
		if (StringUtils.isEmpty(expensesDisplayStartParam)) {
			expensesDisplayStartParam = request.getParameter("start");
		}
		if (!StringUtils.isEmpty(expensesDisplayStartParam)) {
			pageNumber = (Integer.valueOf(expensesDisplayStartParam) / pageDisplayLength);
		}

		Sort sort = null;
		if (sColName != null) {
			if (StringUtils.equals("asc", sSortDir)) {
				sort = Sort.by(Direction.ASC, sColName);
			} else {
				sort = Sort.by(Direction.DESC, sColName);
			}
		} else {
			sort = Sort.by(Direction.DESC, "id");// default sorting
		}

		Pageable pageable = PageRequest.of(pageNumber, pageDisplayLength, sort);

		WorkProgressImagesJson workProgressImagesJson = commonService.fetchProgressImagesList(pageable,
				!StringUtils.isEmpty(workStatusName) ? workStatusName : null,
				!StringUtils.isEmpty(workSubStatusName) ? workSubStatusName : null,
				!StringUtils.isEmpty(reasonDelay) ? reasonDelay : null,

				!StringUtils.isEmpty(actionTakenDelay) ? actionTakenDelay : null,
				!StringUtils.isEmpty(createdDate) ? createdDate : null,

				!StringUtils.isEmpty(createdDate) ? createdDate : null,

				!StringUtils.isEmpty(subDelayReason) ? subDelayReason : null, workTypeId);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(workProgressImagesJson);

		return json;
	}

	// Method to fetch the list of expenses data based on work type and various
	// filters.
	@RequestMapping(value = "/fetchExpensesDataList/{workTypeId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String fetchExpensesDataList(HttpServletRequest request, @PathVariable Long workTypeId) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching Progress Images List", user.getUsername(), user.getAuthorities());

		String totalExpensess = request.getParameter("totalExpensess");

		String expensessUptoMarch = request.getParameter("expensessUptoMarch");
		String expensessCurrentFy = request.getParameter("expensessCurrentFy");

		String year = request.getParameter("year");

		String createdDate = request.getParameter("createdDate");
//		Long yearNew =Long.parseLong(String.valueOf(year));

		String sSortCol = request.getParameter("iSortCol_0");
		String sSortDir = request.getParameter("sSortDir_0");
		String sColName = request.getParameter("mDataProp_" + sSortCol);

		// Fetch the page number from client
		Integer pageNumber = 0;

		// Fetch search parameter
		String searchParameterWorkName = request.getParameter("searchBoxVal");

		// String searchParameterWorkNo = request.getParameter("searchBoxValWorkNo");

		String searchByDivision = "0";
		// Fetch Page display length. DataTables may send legacy iDisplayLength or 1.10 length.
		String expensesDisplayLengthParam = request.getParameter("iDisplayLength");
		if (StringUtils.isEmpty(expensesDisplayLengthParam)) {
			expensesDisplayLengthParam = request.getParameter("length");
		}
		Integer pageDisplayLength = !StringUtils.isEmpty(expensesDisplayLengthParam)
				? Integer.valueOf(expensesDisplayLengthParam)
				: 10;

		String expensesDisplayStartParam = request.getParameter("iDisplayStart");
		if (StringUtils.isEmpty(expensesDisplayStartParam)) {
			expensesDisplayStartParam = request.getParameter("start");
		}
		if (!StringUtils.isEmpty(expensesDisplayStartParam)) {
			pageNumber = (Integer.valueOf(expensesDisplayStartParam) / pageDisplayLength);
		}

		Sort sort = null;
		if (sColName != null) {
			if (StringUtils.equals("asc", sSortDir)) {
				sort = Sort.by(Direction.ASC, sColName);
			} else {
				sort = Sort.by(Direction.DESC, sColName);
			}
		} else {
			sort = Sort.by(Direction.DESC, "year");// default sorting
		}

		Pageable pageable = PageRequest.of(pageNumber, pageDisplayLength, sort);

		ExpensesDataJson expensesDataJson = commonService.fetchExpensesDataList(pageable,
				!StringUtils.isEmpty(totalExpensess) ? totalExpensess : null,

				!StringUtils.isEmpty(expensessUptoMarch) ? expensessUptoMarch : null,
				!StringUtils.isEmpty(expensessCurrentFy) ? expensessCurrentFy : null,

				!StringUtils.isEmpty(year) ? year : null, !StringUtils.isEmpty(createdDate) ? createdDate : null,
				workTypeId);

		if (expensesDataJson == null) {
			expensesDataJson = new ExpensesDataJson();
			expensesDataJson.setAaData(new ArrayList<>());
			expensesDataJson.setiTotalRecords(0);
			expensesDataJson.setiTotalDisplayRecords(0);
		}

		String sEcho = request.getParameter("sEcho");
		if (!StringUtils.isEmpty(sEcho)) {
			expensesDataJson.setsEcho(sEcho);
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(expensesDataJson);

		return json;
	}

	// Method to add work data and handle the response for successful or failed
	// save.
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM','ROLE_DEPARTMENT') and @workAuthorization.canEditWork(#p0)")
	@RequestMapping(value = "/addWorkData", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	@ResponseBody
	public ResponseObject addWorkData(WorkBean workBean, HttpServletRequest request) throws Exception {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Adding Work data", user.getUsername(), user.getAuthorities());
		ResponseObject response = null;
		try {
			response = commonService.addWork(workBean);
			if (response != null) {
				response.setSuccessMessage("Work saved successfully!");
				logger.info("User - {}, Role - {} - Work saved successfully!", user.getUsername(),
						user.getAuthorities());
			} else {
				response = new ResponseObject();
				String errorMsg = DMSConstants.ERROR_SAVING_DATA;
				response.setErrorMessage(errorMsg);
				logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
			}
		} catch (Exception e) {
			String errorMsg = e.getMessage();
			response = new ResponseObject();
			response.setErrorMessage(errorMsg);
			logger.error("User - " + user.getUsername() + ", Role - " + user.getAuthorities() + " - " + errorMsg);
		}
		return response;
	}

	@RequestMapping(value = "/addTSASWorkData", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	@ResponseBody
	public ResponseObject addTSASWorkData(TSASWorkBean tsasWorkBean, HttpServletRequest request) throws Exception {

		logger.info("##############" + tsasWorkBean.getTsAsSataus());

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Adding Work data", user.getUsername(), user.getAuthorities());
		ResponseObject response = null;

		try {

			String remoteIpAddr = request.getHeader("X-Forwarded-For");

			logger.info("Header...." + remoteIpAddr);

			response = commonService.addTSASWorkData(tsasWorkBean);
			if (response != null) {
				response.setSuccessMessage("Work saved successfully!");
				logger.info("User - {}, Role - {} - Work saved successfully!", user.getUsername(),
						user.getAuthorities());
			} else {
				response = new ResponseObject();
				String errorMsg = DMSConstants.ERROR_SAVING_DATA;
				response.setErrorMessage(errorMsg);
				logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
			}
		} catch (Exception e) {
			String errorMsg = e.getMessage();
			response = new ResponseObject();
			response.setErrorMessage(errorMsg);
			logger.error("User - " + user.getUsername() + ", Role - " + user.getAuthorities() + " - " + errorMsg);
		}
		return response;
	}

	// Method to add TSAS work data and handle success or error responses.
	@RequestMapping(value = "/addTSReviseWorkData", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	@ResponseBody
	public ResponseObject addTSReviseWorkData(TSASReviseWorkBean tsasReviseWorkBean, HttpServletRequest request)
			throws Exception {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Adding Work data", user.getUsername(), user.getAuthorities());
		ResponseObject response = null;

		try {

			String remoteIpAddr = request.getHeader("X-Forwarded-For");

			logger.info("Header...." + remoteIpAddr);

			response = commonService.addTSReviseWorkData(tsasReviseWorkBean);
			if (response != null) {
				response.setSuccessMessage("Revised Data saved successfully!");
				logger.info("User - {}, Role - {} - Work saved successfully!", user.getUsername(),
						user.getAuthorities());
			} else {
				response = new ResponseObject();
				String errorMsg = DMSConstants.ERROR_SAVING_DATA;
				response.setErrorMessage(errorMsg);
				logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
			}
		} catch (Exception e) {
			String errorMsg = e.getMessage();
			response = new ResponseObject();
			response.setErrorMessage(errorMsg);
			logger.error("User - " + user.getUsername() + ", Role - " + user.getAuthorities() + " - " + errorMsg);
		}
		return response;
	}

	// Method to add or revise work data based on the provided input.
	@RequestMapping(value = "/addASReviseWorkData", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	@ResponseBody
	public ResponseObject addASReviseWorkData(TSASReviseWorkBean tsasReviseWorkBean, HttpServletRequest request)
			throws Exception {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Adding Work data", user.getUsername(), user.getAuthorities());
		ResponseObject response = null;

		try {

			String remoteIpAddr = request.getHeader("X-Forwarded-For");

			logger.info("Header...." + remoteIpAddr);

			response = commonService.addASReviseWorkData(tsasReviseWorkBean);
			if (response != null) {
				response.setSuccessMessage("Revised Data saved successfully!");
				logger.info("User - {}, Role - {} - Work saved successfully!", user.getUsername(),
						user.getAuthorities());
			} else {
				response = new ResponseObject();
				String errorMsg = DMSConstants.ERROR_SAVING_DATA;
				response.setErrorMessage(errorMsg);
				logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
			}
		} catch (Exception e) {
			String errorMsg = e.getMessage();
			response = new ResponseObject();
			response.setErrorMessage(errorMsg);
			logger.error("User - " + user.getUsername() + ", Role - " + user.getAuthorities() + " - " + errorMsg);
		}
		return response;
	}

	// Method to add work progress data and handle associated response.
	@RequestMapping(value = "/addWorkProgress", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	@ResponseBody
	public ResponseObject addWorkProgress(WorkProgressBean workProgressBean, HttpServletRequest request)
			throws Exception {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Adding Work data", user.getUsername(), user.getAuthorities());
		ResponseObject response = null;

		try {

			String remoteIpAddr = request.getHeader("X-Forwarded-For");

			logger.info("Header...." + remoteIpAddr);
			response = commonService.addWorkProgress(workProgressBean);
			if (response != null) {
				response.setSuccessMessage("Work saved successfully!");
				logger.info("User - {}, Role - {} - Work saved successfully!", user.getUsername(),
						user.getAuthorities());
			} else {
				response = new ResponseObject();
				String errorMsg = DMSConstants.ERROR_SAVING_DATA;
				response.setErrorMessage(errorMsg);
				logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
			}
		} catch (Exception e) {
			String errorMsg = e.getMessage();
			response = new ResponseObject();
			response.setErrorMessage(errorMsg);
			logger.error("User - " + user.getUsername() + ", Role - " + user.getAuthorities() + " - " + errorMsg);
		}
		return response;
	}

	// Method to handle uploading and saving work progress sub-status data.
	@RequestMapping(value = "/addWorkProSubStatusUploading", method = RequestMethod.POST, consumes = {
			"multipart/form-data" })
	@ResponseBody
	public ResponseObject addWorkProSubStatusUploading(DocumentUploadWorkProgressBean uploadWorkProgressBean,
			HttpServletRequest request) throws Exception {
		
		

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Adding Work data", user.getUsername(), user.getAuthorities());
		ResponseObject response = null;

		try {

			   MultipartFile file = uploadWorkProgressBean.getFile();

		        if (file == null || file.isEmpty()) {
		            logger.info("No file uploaded");
		        }
			
			String remoteIpAddr = request.getHeader("X-Forwarded-For");

			logger.info("Header...." + remoteIpAddr);

			response = commonService.addWorkProSubStatusUploading(uploadWorkProgressBean);
			if (response != null) {
				response.setSuccessMessage("Work saved successfully!");
				logger.info("User - {}, Role - {} - Work saved successfully!", user.getUsername(),
						user.getAuthorities());
			} else {
				response = new ResponseObject();
				String errorMsg = DMSConstants.ERROR_SAVING_DATA;
				response.setErrorMessage(errorMsg);
				logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
			}
		} catch (Exception e) {
			String errorMsg = e.getMessage();
			response = new ResponseObject();
			response.setErrorMessage(errorMsg);
			logger.error("User - " + user.getUsername() + ", Role - " + user.getAuthorities() + " - " + errorMsg);
		}
		return response;
	}

	// Method to handle the uploading of drawing files for work data.
	@RequestMapping(value = "/uploadedDrawingFiles", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	@ResponseBody
	public ResponseObject uploadedDrawingFiles(DocumentUploadDrawingDetailBean uploadDrawingDetailBean,
			HttpServletRequest request) throws Exception {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Adding Work data", user.getUsername(), user.getAuthorities());
		ResponseObject response = null;

		try {

			String remoteIpAddr = request.getHeader("X-Forwarded-For");

			logger.info("Header...." + uploadDrawingDetailBean.getDrawingFile() + "cbcbf "
					+ uploadDrawingDetailBean.getWorkId() + " status " + uploadDrawingDetailBean.getDrawingStatus());

			response = commonService.uploadedDrawingFiles(uploadDrawingDetailBean);
			if (response != null) {
				response.setSuccessMessage("Work saved successfully!");
				logger.info("User - {}, Role - {} - Work saved successfully!", user.getUsername(),
						user.getAuthorities());
			} else {
				response = new ResponseObject();
				String errorMsg = DMSConstants.ERROR_SAVING_DATA;
				response.setErrorMessage(errorMsg);
				logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
			}
		} catch (Exception e) {
			String errorMsg = e.getMessage();
			response = new ResponseObject();
			response.setErrorMessage(errorMsg);
			logger.error("User - " + user.getUsername() + ", Role - " + user.getAuthorities() + " - " + errorMsg);
		}
		return response;
	}

	// Method to add work progress expenses data with error handling and logging.
	@RequestMapping(value = "/addWorkProExpensesData", method = RequestMethod.POST, consumes = {
			"multipart/form-data" })
	@ResponseBody
	public ResponseObject addWorkProExpensesData(ExpensesDataBean expensesDataBean, HttpServletRequest request)
			throws Exception {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Adding Work data", user.getUsername(), user.getAuthorities());
		ResponseObject response = null;

		try {

			String remoteIpAddr = request.getHeader("X-Forwarded-For");

			logger.info("Header...." + remoteIpAddr);

			response = commonService.addWorkProExpensesData(expensesDataBean);
			if (response != null) {
				response.setSuccessMessage("Work saved successfully!");
				logger.info("User - {}, Role - {} - Work saved successfully!", user.getUsername(),
						user.getAuthorities());
			} else {
				response = new ResponseObject();
				String errorMsg = DMSConstants.ERROR_SAVING_DATA;
				response.setErrorMessage(errorMsg);
				logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
			}
		} catch (Exception e) {
			String errorMsg = e.getMessage();
			response = new ResponseObject();
			response.setErrorMessage(errorMsg);
			logger.error("User - " + user.getUsername() + ", Role - " + user.getAuthorities() + " - " + errorMsg);
		}
		return response;
	}

	// Method to get work status
	@RequestMapping(value = "/getWorkStatus", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String getAllWorkStatus() {
		logger.info("Getting work status......");
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Getting Work Status", user.getUsername(), user.getAuthorities());
		List<WorkStatusBean> listWorkStatus = commonService.getWorkStatus();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(listWorkStatus);
		return json;
	}

	// Method for controller to get work sub status
	@RequestMapping(value = "/getWorkSubStatus", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String getWorkSubStatus() {
		logger.info("Getting Work Sub Status.....");
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Getting Work Sub Status", user.getUsername(), user.getAuthorities());
		List<WorkSubStatusBean> listWorkSubStatusBean = commonService.getWorkSubStatus();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(listWorkSubStatusBean);
		return json;
	}

	// Method for controller to add tender and work agreement.
	@RequestMapping(value = "/addTenderWorkAgreement", method = RequestMethod.POST, consumes = {
			"multipart/form-data" })
	public ResponseObject addTendorWorkAgreement(WorkTenderBean workTenderbean) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Adding Work documents", user.getUsername(), user.getAuthorities());
		ResponseObject response = new ResponseObject();
		try {
			response = commonService.addWorkTenderAgreementDetls(workTenderbean);
			if (response != null) {
				response.setSuccessMessage("Tender and Agreement successfully saved!");
				logger.info("User - {}, Role - {} - Tender and Agreement successfully saved!", user.getUsername(),
						user.getAuthorities());
			} else {
				String errorMsg = DMSConstants.ERROR_SAVING_DATA;
				// if(response == null) {
				response = new ResponseObject();
				response.setErrorMessage(errorMsg);
				logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
			}
		} catch (Exception ex) {
			String errorMsg = ex.getMessage();

			// ex.printStackTrace();
			logger.error("ERROR IN Adding Work Tender Agreement", ex);
			response = new ResponseObject();
			response.setErrorMessage(errorMsg);
			logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
		}
		return response;
	}

	// Method to add contractor details and save the data in the system.
	@RequestMapping(value = "/addContractorDetails", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject addContractorData(ContractorBean contractorBean, HttpServletRequest request)
			throws Exception {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Adding Contractor details", user.getUsername(), user.getAuthorities());
		ResponseObject response = null;

		try {

			String remoteIpAddr = request.getHeader("X-Forwarded-For");

			logger.info("Header...." + remoteIpAddr);

			response = commonService.addContractorData(contractorBean);
			if (response != null) {

				response.setSuccessMessage("Contractor Data saved successfully!");
				logger.info("User - {}, Role - {} - Contractor  saved successfully!", user.getUsername(),
						user.getAuthorities());

				/*
				 * if (response.getErrorMessage().equals("Work Order not Issued.")) {
				 * 
				 * }else { response.setSuccessMessage("Contractor Data saved successfully!");
				 * logger.info("User - {}, Role - {} - Contractor  saved successfully!",
				 * user.getUsername(), user.getAuthorities()); }
				 * 
				 * System.out.println(" Response....."+response.getErrorMessage());
				 * System.out.println(" Response....."+response.getSuccessMessage());
				 */

			} else {
				response = new ResponseObject();
				String errorMsg = DMSConstants.ERROR_SAVING_DATA;
				response.setErrorMessage(errorMsg);
				logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
			}
		} catch (Exception e) {
			String errorMsg = e.getMessage();
			response = new ResponseObject();
			response.setErrorMessage(errorMsg);
			logger.error("User - " + user.getUsername() + ", Role - " + user.getAuthorities() + " - " + errorMsg);
		}
		return response;

	}

	// Method for controller to get work tender agreement
	@RequestMapping(value = "/fetchWorkTenderAgreement/{Id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public WorkTenderBean getWorkTenderAgreement(@PathVariable("Id") Long workId) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching Work Tender Agreement", user.getUsername(), user.getAuthorities());
		WorkTenderBean bean = new WorkTenderBean();
		try {
			bean = commonService.fetchWorkTenderAgreement(workId);
			if (bean != null) {
				logger.info("User - {}, Role - {} - Fetching Work Tender Agreement", user.getUsername(),
						user.getAuthorities());
			} else {
				String fetchMsg = DMSConstants.ERROR_FETCHING_DATA;
				logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), fetchMsg);
			}
		} catch (Exception ex) {
			String fetchMsg = DMSConstants.ERROR_FETCHING_DATA;
			logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), fetchMsg, ex);
		}
		return bean;
	}

	@RequestMapping(value = "fetchContractorDetails/{id}", method = RequestMethod.GET)
	public ContractorBean fetchContractorWorkDetails(@PathVariable Long id, HttpServletRequest request)
			throws ParseException {

		user = DMSUtil.getUserDetail();

		String role = DMSUtil.getUserRole(user);

		logger.info("User - {}, Role - {} - Fetching Contractor Work data", user.getUsername(), user.getAuthorities());

		ContractorBean contractorBean = commonService.fetchContractorDetails(id);

		return contractorBean;

	}

	@RequestMapping(value = "getCountId/{workId}", method = RequestMethod.GET)
	public Long getContractorCountByWorkId(@PathVariable Long workId, HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching contractor count for work {}", user.getUsername(),
				user.getAuthorities(), workId);
		return commonService.countContractorsByWorkId(workId);
	}

	// Method to display the form for editing ongoing work based on the provided ID.
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM','ROLE_DEPARTMENT') and @workAuthorization.canAccessEncryptedWork(#p0)")
	@RequestMapping(value = "/editWork/{id}", method = RequestMethod.GET)
	public ModelAndView viewEditWorkForm(@PathVariable String id, HttpServletRequest request) {

		try {
			user = DMSUtil.getUserDetail();
			logger.info("User - {}, Role - {} - Displaying Edit OngoingWork Form", user.getUsername(),
					user.getAuthorities());
			
			// Check if this is an AJAX request
			String ajaxHeader = request.getHeader("X-Requested-With");
			ModelAndView modelAndView;
			
			if ("XMLHttpRequest".equals(ajaxHeader)) {
				// For AJAX requests, return a fragment view without full HTML structure
				modelAndView = new ModelAndView("common/editWork-fragment");
			} else {
				// For direct page access, return the full page
				modelAndView = new ModelAndView("common/editWork");
			}
			
			UserBean userBean = fetchLoggedInUserDetails(request);
			modelAndView.addObject("roleName", userBean.getLoggedInUserRole());
			return modelAndView;
		} catch (Exception ex) {
			logger.error("Error while preparing editWork view for id {}: {}", id, ex.getMessage(), ex);
			// Return a minimal fragment to avoid truncated responses for AJAX clients
			ModelAndView fallback = new ModelAndView("common/editWork-fragment");
			fallback.addObject("roleName", "UNKNOWN");
			fallback.addObject("fragmentError", "An error occurred rendering the edit form. Check server logs.");
			return fallback;
		}
	}

	// Method to display the edit form for viewing ongoing work details.
	@RequestMapping(value = "/viewWork/{id}", method = RequestMethod.GET)
	public ModelAndView viewWorkWorkForm(@PathVariable String id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Edit OngoingWork Form", user.getUsername(),
				user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("common/viewWork");
		UserBean userBean = fetchLoggedInUserDetails(request);
		modelAndView.addObject("roleName", userBean.getLoggedInUserRole());
		return modelAndView;
	}

	// Method to display the view work data form for the specified work ID.
	@RequestMapping(value = "/viewWorkData/{id}", method = RequestMethod.GET)
	public ModelAndView viewWorkDataForm(@PathVariable String id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying view Work Data Form", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("common/viewWorkData");
		return modelAndView;
	}

	// Method to fetch work details based on the given work ID and adjust access
	// permissions based on user role.
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM','ROLE_DEPARTMENT') and @workAuthorization.canAccessWork(#p0)")
	@RequestMapping(value = "fetchWorkDetails/{id}", method = RequestMethod.GET)
	public WorkBean fetchWorkDetails(@PathVariable Long id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		String role = DMSUtil.getUserRole(user);
		logger.info("User - {}, Role - {} - Fetching Work data", user.getUsername(), user.getAuthorities());

		WorkBean workBean = commonService.fetchWorkDetails(id);

		try {
			if(null != workBean) {
			if (workBean.getDmStatus() != null) {
				if (fetchLoggedInUser(request).getRole().getRoleCode().equals("ROLE_DM")) {

					if (workBean.getDmStatus() != null) {
						workBean.setIsDisabled(true);
					}

				} else if (fetchLoggedInUser(request).getRole().getRoleCode().equals("ROLE_DEPARTMENT")) {
					if (workBean.getDmStatus() == 2L) {
						workBean.setIsDisabledDep(true);
					}
					if (workBean.getDmStatus() == 1L) {
						workBean.setIsDisabledDep(false);

					}

				}
			} else {

				if (fetchLoggedInUser(request).getRole().getRoleCode().equals("ROLE_DM")) {
					workBean.setIsDisabled(true);
				}
				if (fetchLoggedInUser(request).getRole().getRoleCode().equals("ROLE_DEPARTMENT")) {
					workBean.setIsDisabledDep(true);
				}
			}
			}
		} catch (Exception e) {
			// TODO: handle exception
			// e.printStackTrace();
			logger.error("ERROR IN FETCHING WORK DETAILS", e);
		}

		// workBean.setRole(role);
		return workBean;
	}

	// Method to fetch and return full work details based on the provided work ID.
	@RequestMapping(value = "fetchFullWorkDetails/{id}", method = RequestMethod.GET)
	public String fetchFullWorkDetails(@PathVariable Long id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		String role = DMSUtil.getUserRole(user);

		MergeWorkJson mergeWorkJson = commonService.fetchFullWorkDetails(id);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(mergeWorkJson);
		return json;
	}

	// Method to fetch TSAS work details by ID.
	@RequestMapping(value = "fetchTSASDetails/{id}", method = RequestMethod.GET)
	public TSASWorkBean fetchTSASWorkDetails(@PathVariable Long id, HttpServletRequest request) throws ParseException {
		user = DMSUtil.getUserDetail();
		String role = DMSUtil.getUserRole(user);
		logger.info("User - {}, Role - {} - Fetching TSAS Work data", user.getUsername(), user.getAuthorities());

		TSASWorkBean tsasWorkBean = commonService.fetchTSASWorkDetails(id);

		return tsasWorkBean;

	}

	// Method to fetch the work progress data based on the given work ID.
	@RequestMapping(value = "fetchWorkProgress/{id}", method = RequestMethod.GET)
	public WorkProgressBean fetchWorkProgress(@PathVariable Long id, HttpServletRequest request) throws ParseException {

		user = DMSUtil.getUserDetail();
		String role = DMSUtil.getUserRole(user);
		logger.info("User - {}, Role - {} - Fetching TSAS Work data", user.getUsername(), user.getAuthorities());

		WorkProgressBean workProgressBean = commonService.fetchWorkProgress(id);

		return workProgressBean;

	}

	// Method to edit and update ongoing work data.
	@RequestMapping(value = "/editOngoingWork", method = RequestMethod.POST)
	public ResponseObject editOngoingWork(@RequestBody WorkBean workBean, HttpServletRequest request) throws Exception {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - updating Work data", user.getUsername(), user.getAuthorities());
		ResponseObject response = null;

		try {
			response = commonService.editOngoingWork(workBean);
			if (response != null) {
				response.setSuccessMessage("Work updated successfully!");
				logger.info("User - {}, Role - {} - Work updated successfully!", user.getUsername(),
						user.getAuthorities());
			} else {
				response = new ResponseObject();
				String errorMsg = DMSConstants.ERROR_SAVING_DATA;
				response.setErrorMessage(errorMsg);
				logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
			}
		} catch (Exception e) {
			String errorMsg = e.getMessage();
			response = new ResponseObject();
			response.setErrorMessage(errorMsg);
			logger.error("User - " + user.getUsername() + ", Role - " + user.getAuthorities() + " - " + errorMsg);
		}
		return response;
	}

	// Method to delete a work entry by its ID.
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/deleteWork/{id}", method = RequestMethod.POST)
	public ResponseObject deleteWork(@PathVariable Long id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Deleting Work", user.getUsername(), user.getAuthorities());
		ResponseObject response = new ResponseObject();

		String errorMsg = commonService.deleteWork(id);

		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
		} else {
			response.setSuccessMessage("Work deleted successfully!");
			logger.info("User - {}, Role - {} - Work deleted successfully!", user.getUsername(), user.getAuthorities());
		}
		return response;
	}

	// Method to fetch the list of districts.
	@RequestMapping(value = "fetchDistricts", method = RequestMethod.GET)
	public List<DistrictBean> fetchDistricts(HttpServletRequest request) {
		return commonService.fetchDistricts();
	}

	// Method to fetch the list of divisions.
	@RequestMapping(value = "fetchDivisions", method = RequestMethod.GET)
	public List<DivisionBean> fetchDivisions(HttpServletRequest request) {
		return commonService.fetchDivisions();
	}

	// Method to fetch the list of districts based on the division ID.
	@RequestMapping(value = "fetchDistrictsByDivision/{divisionId}", method = RequestMethod.GET)
	public List<DistrictBean> fetchDistrictsByDivision(@PathVariable Long divisionId, HttpServletRequest request) {
		return commonService.fetchDistrictsByDivision(divisionId);
	}

	// Method to fetch details of the currently logged-in user.
	@RequestMapping(value = "fetchLoggedInUser", method = RequestMethod.GET)
	public UserBean fetchLoggedInUser(HttpServletRequest request) {
		return commonService.fetchLoggedInUser();
	}

	// Method to display the form for editing legacy data based on the provided ID.
	@RequestMapping(value = "/editLegacyData/{id}", method = RequestMethod.GET)
	public ModelAndView viewEditLegacyDataForm(@PathVariable String id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Edit LegacyData Form", user.getUsername(),
				user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("common/editLegacyDataForm");
		return modelAndView;
	}

	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/manageImplAgency", method = RequestMethod.GET)
	public ModelAndView manageImplAgencyView(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Manage ImplAgency page", user.getUsername(),
				user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("superAdmin/manageImplAgency");
		UserBean userBean = fetchLoggedInUserDetails(request);
		modelAndView.addObject("roleName", userBean.getLoggedInUserRole());
		modelAndView.addObject("userName", userBean.getEmailId());
		return modelAndView;
	}

	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/manageHead", method = RequestMethod.GET)
	public ModelAndView manageHeadView(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Manage ImplAgency page", user.getUsername(),
				user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("common/manageHead");
		UserBean userBean = fetchLoggedInUserDetails(request);
		modelAndView.addObject("roleName", userBean.getLoggedInUserRole());
		modelAndView.addObject("userName", userBean.getEmailId());
		return modelAndView;
	}

	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/manageScheme", method = RequestMethod.GET)
	public ModelAndView manageSchemeView(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Manage Scheme page", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("common/manageScheme");
		UserBean userBean = fetchLoggedInUserDetails(request);
		modelAndView.addObject("roleName", userBean.getLoggedInUserRole());
		modelAndView.addObject("userName", userBean.getEmailId());
		return modelAndView;
	}

	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/manageSor", method = RequestMethod.GET)
	public ModelAndView manageSorView(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Manage Scheme page", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("common/manageSor");
		UserBean userBean = fetchLoggedInUserDetails(request);
		modelAndView.addObject("roleName", userBean.getLoggedInUserRole());
		modelAndView.addObject("userName", userBean.getEmailId());
		return modelAndView;
	}

	// Method to fetch the list of implementation agencies with search and
	// pagination.
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/fetchImplAgencyList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String fetchImplAgencyList(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching ImplAgency List", user.getUsername(), user.getAuthorities());
		String searchBoxVal = request.getParameter("searchBoxVal");

		String sSortCol = request.getParameter("iSortCol_0");
		String sSortDir = request.getParameter("sSortDir_0");
		String sColName = request.getParameter("mDataProp_" + sSortCol);

		// Fetch the page number from client
		Integer pageNumber = 0;

		/*
		 * // Fetch search parameter String searchParameter =
		 * request.getParameter("sSearch");
		 */

		// Fetch Page display length
		Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));

		if (null != request.getParameter("iDisplayStart")) {
			pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart")) / pageDisplayLength);
		}

		Sort sort = null;
		if (sColName != null) {
			if (StringUtils.equals("asc", sSortDir)) {
				sort = Sort.by(Direction.ASC, sColName);
			} else {
				sort = Sort.by(Direction.DESC, sColName);
			}
		} else {
			sort = Sort.by(Direction.ASC, "implAgencyname");// default sorting
		}

		Pageable pageable = PageRequest.of(pageNumber, pageDisplayLength, sort);

		ImplAgencyJson implAgencyJson = commonService.getAllImplAgency(pageable, searchBoxVal);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(implAgencyJson);

		return json;
	}

	// Method to fetch the list of heads with search and pagination functionality.
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/fetchHeadList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String fetchHeadList(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - fetchHead List", user.getUsername(), user.getAuthorities());
		String searchBoxVal = request.getParameter("searchBoxVal");

		String sSortCol = request.getParameter("iSortCol_0");
		String sSortDir = request.getParameter("sSortDir_0");
		String sColName = request.getParameter("mDataProp_" + sSortCol);

		// Fetch the page number from client
		Integer pageNumber = 0;

		/*
		 * // Fetch search parameter String searchParameter =
		 * request.getParameter("sSearch");
		 */

		// Fetch Page display length
		Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));

		if (null != request.getParameter("iDisplayStart")) {
			pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart")) / pageDisplayLength);
		}

		Sort sort = null;
		if (sColName != null) {
			if (StringUtils.equals("asc", sSortDir)) {
				sort = Sort.by(Direction.ASC, sColName);
			} else {
				sort = Sort.by(Direction.DESC, sColName);
			}
		} else {
			sort = Sort.by(Direction.ASC, "headName");// default sorting
		}

		Pageable pageable = PageRequest.of(pageNumber, pageDisplayLength, sort);

		HeadJson headJson = commonService.getAllHead(pageable, searchBoxVal);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(headJson);

		return json;
	}

	// Method to fetch the list of schemes with sorting and pagination.
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/fetchSchemeList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String fetchSchemeList(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - fetchScheme List", user.getUsername(), user.getAuthorities());
		String searchBoxVal = request.getParameter("searchBoxVal");

		String sSortCol = request.getParameter("iSortCol_0");
		String sSortDir = request.getParameter("sSortDir_0");
		String sColName = request.getParameter("mDataProp_" + sSortCol);

		// Fetch the page number from client
		Integer pageNumber = 0;

		Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));

		if (null != request.getParameter("iDisplayStart")) {
			pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart")) / pageDisplayLength);
		}

		Sort sort = null;
		if (sColName != null) {
			if (StringUtils.equals("asc", sSortDir)) {
				sort = Sort.by(Direction.ASC, sColName);
			} else {
				sort = Sort.by(Direction.DESC, sColName);
			}
		} else {
			sort = Sort.by(Direction.ASC, "schemeName");// default sorting
		}

		Pageable pageable = PageRequest.of(pageNumber, pageDisplayLength, sort);

		SchemeJson schemeJson = commonService.getAllScheme(pageable, searchBoxVal);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(schemeJson);

		return json;
	}

	// Method to fetch the list of SORs with sorting and pagination.
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/fetchSorList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String fetchSorList(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - fetchSor List", user.getUsername(), user.getAuthorities());
		String searchBoxVal = request.getParameter("searchBoxVal");

		String sSortCol = request.getParameter("iSortCol_0");
		String sSortDir = request.getParameter("sSortDir_0");
		String sColName = request.getParameter("mDataProp_" + sSortCol);

		// Fetch the page number from client
		Integer pageNumber = 0;

		Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));

		if (null != request.getParameter("iDisplayStart")) {
			pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart")) / pageDisplayLength);
		}

		Sort sort = null;
		if (sColName != null) {
			if (StringUtils.equals("asc", sSortDir)) {
				sort = Sort.by(Direction.ASC, sColName);
			} else {
				sort = Sort.by(Direction.DESC, sColName);
			}
		} else {
			sort = Sort.by(Direction.ASC, "sorYear");// default sorting
		}

		Pageable pageable = PageRequest.of(pageNumber, pageDisplayLength, sort);

		SorJson sorJson = commonService.getAllSor(pageable, searchBoxVal);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(sorJson);

		return json;
	}

	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/addImplAgencyForm", method = RequestMethod.GET)
	public ModelAndView viewAddImplAgencyForm(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Add ImplAgency Form", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("superAdmin/addImplAgencyForm");
		UserBean userBean = fetchLoggedInUserDetails(request);
		modelAndView.addObject("roleName", userBean.getLoggedInUserRole());
		modelAndView.addObject("userName", userBean.getEmailId());
		;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = dateFormat.format(new Date());
		modelAndView.addObject("myDate", dateString);

		return modelAndView;
	}

	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/addHeadForm", method = RequestMethod.GET)
	public ModelAndView viewAddHeadForm(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Add ImplAgency Form", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("common/addHeadForm");
		UserBean userBean = fetchLoggedInUserDetails(request);
		modelAndView.addObject("roleName", userBean.getLoggedInUserRole());
		modelAndView.addObject("userName", userBean.getEmailId());
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = dateFormat.format(new Date());
		modelAndView.addObject("myDate", dateString);

		return modelAndView;
	}

	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/addSchemeForm", method = RequestMethod.GET)
	public ModelAndView viewAddSchemeForm(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Add Scheme Form", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("common/addSchemeForm");
		UserBean userBean = fetchLoggedInUserDetails(request);
		modelAndView.addObject("roleName", userBean.getLoggedInUserRole());
		modelAndView.addObject("userName", userBean.getEmailId());
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = dateFormat.format(new Date());
		modelAndView.addObject("myDate", dateString);

		return modelAndView;
	}

	// Method to add a new work head (segment) to the system.
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/addHead", method = RequestMethod.POST)
	public ResponseObject addHead(@RequestBody WorkHeadBean bean, HttpServletRequest request) throws Exception {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Adding Segments data", user.getUsername(), user.getAuthorities());
		ResponseObject response = new ResponseObject();
		UserBean userBean = fetchLoggedInUserDetails(request);
		String userName = userBean.getEmailId();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = dateFormat.format(new Date());
		String errorMsg = commonService.addHead(bean, userName, dateString);
		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
		} else {
			response.setSuccessMessage("Segments added successfully!");
			logger.info("User - {}, Role - {} - Segments added successfully!", user.getUsername(),
					user.getAuthorities());
		}
		return response;
	}

	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/addSorForm", method = RequestMethod.GET)
	public ModelAndView viewAddSorForm(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Add Scheme Form", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("common/addSorForm");
		UserBean userBean = fetchLoggedInUserDetails(request);
		modelAndView.addObject("roleName", userBean.getLoggedInUserRole());
		modelAndView.addObject("userName", userBean.getEmailId());
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = dateFormat.format(new Date());
		modelAndView.addObject("myDate", dateString);

		return modelAndView;
	}

	// Method to add a new scheme with user details and current date.
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/addScheme", method = RequestMethod.POST)
	public ResponseObject addScheme(@RequestBody SchemeBean bean, HttpServletRequest request) throws Exception {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Adding Scheme data", user.getUsername(), user.getAuthorities());
		ResponseObject response = new ResponseObject();
		UserBean userBean = fetchLoggedInUserDetails(request);
		String userName = userBean.getEmailId();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = dateFormat.format(new Date());
		String errorMsg = commonService.addScheme(bean, userName, dateString);
		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
		} else {
			response.setSuccessMessage("Scheme added successfully!");
			logger.info("User - {}, Role - {} -Scheme added successfully!", user.getUsername(), user.getAuthorities());
		}
		return response;
	}

	// Method to add a new Scheme of Rates (SOR) data.
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/addSor", method = RequestMethod.POST)
	public ResponseObject addSor(@RequestBody SorYearBean bean, HttpServletRequest request) throws Exception {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Adding Scheme data", user.getUsername(), user.getAuthorities());
		ResponseObject response = new ResponseObject();
		UserBean userBean = fetchLoggedInUserDetails(request);
		String userName = userBean.getEmailId();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = dateFormat.format(new Date());
		String errorMsg = commonService.addSor(bean, userName, dateString);
		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
		} else {
			response.setSuccessMessage("Scheme added successfully!");
			logger.info("User - {}, Role - {} -Scheme added successfully!", user.getUsername(), user.getAuthorities());
		}
		return response;
	}

	// Method to add implementation agency data.
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/addImplAgency", method = RequestMethod.POST)
	public ResponseObject addImplAgency(@RequestBody ImplAgencyBean bean, HttpServletRequest request) throws Exception {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Adding ImplAgency data", user.getUsername(), user.getAuthorities());
		ResponseObject response = new ResponseObject();
		UserBean userBean = fetchLoggedInUserDetails(request);
		String userName = userBean.getEmailId();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = dateFormat.format(new Date());
		String errorMsg = commonService.addImplAgency(bean, userName, dateString);
		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
		} else {
			response.setSuccessMessage("Implementation Agency added successfully!");
			logger.info("User - {}, Role - {} - Implementation Agency added successfully!", user.getUsername(),
					user.getAuthorities());
		}
		return response;
	}

	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/editImplAgencyForm/{id}", method = RequestMethod.GET)
	public ModelAndView viewEditImplAgencyForm(@PathVariable String id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Edit ImplAgency Form", user.getUsername(),
				user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("superAdmin/editImplAgencyForm");
		modelAndView.addObject("loggedInUserName", user.getUsername());
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = dateFormat.format(new Date());
		modelAndView.addObject("myDate", dateString);
		return modelAndView;
	}

	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/editHeadForm/{id}", method = RequestMethod.GET)
	public ModelAndView viewEditHeadForm(@PathVariable String id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Edit Head Form", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("common/editHeadForm");
		UserBean userBean = fetchLoggedInUserDetails(request);
		modelAndView.addObject("roleName", userBean.getLoggedInUserRole());
		modelAndView.addObject("userName", userBean.getEmailId());
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = dateFormat.format(new Date());
		modelAndView.addObject("myDate", dateString);
		return modelAndView;
	}

	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/editSchemeForm/{id}", method = RequestMethod.GET)
	public ModelAndView viewEditSchemeForm(@PathVariable String id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Edit Scheme Form", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("common/editSchemeForm");
		UserBean userBean = fetchLoggedInUserDetails(request);
		modelAndView.addObject("roleName", userBean.getLoggedInUserRole());
		modelAndView.addObject("userName", userBean.getEmailId());
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = dateFormat.format(new Date());
		modelAndView.addObject("myDate", dateString);
		return modelAndView;
	}

	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/editSorForm/{id}", method = RequestMethod.GET)
	public ModelAndView viewEditSorForm(@PathVariable String id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Edit Scheme Form", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("common/editSorForm");
		UserBean userBean = fetchLoggedInUserDetails(request);
		modelAndView.addObject("roleName", userBean.getLoggedInUserRole());
		modelAndView.addObject("userName", userBean.getEmailId());
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = dateFormat.format(new Date());
		modelAndView.addObject("myDate", dateString);
		return modelAndView;
	}

	// Method to fetch implementation agency details based on the provided ID.
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "fetchImplAgencyDetails/{id}", method = RequestMethod.GET)
	public ImplAgencyBean fetchImplAgencyDetails(@PathVariable String id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching ImplAgency data", user.getUsername(), user.getAuthorities());
		return commonService.fetchImplAgencyDetails(Long.parseLong(DMSUtil.decryptParam(id)));
	}

	// Method to fetch head details based on the provided ID.
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "fetchHeadDetails/{id}", method = RequestMethod.GET)
	public WorkHeadBean fetchHeadDetails(@PathVariable String id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching ImplAgency data", user.getUsername(), user.getAuthorities());
		return commonService.fetchHeadDetails(Long.parseLong(DMSUtil.decryptParam(id)));
	}

	// Method to fetch scheme details based on the provided scheme ID.
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "fetchSchemeDetails/{id}", method = RequestMethod.GET)
	public SchemeBean fetchSchemeDetails(@PathVariable String id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching ImplAgency data", user.getUsername(), user.getAuthorities());
		return commonService.fetchSchemeDetails(Long.parseLong(DMSUtil.decryptParam(id)));
	}

	// Method to fetch SOR details based on the given ID.
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "fetchSorDetails/{id}", method = RequestMethod.GET)
	public SorYearBean fetchSorDetails(@PathVariable String id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching ImplAgency data", user.getUsername(), user.getAuthorities());
		return commonService.fetchSorDetails(Long.parseLong(DMSUtil.decryptParam(id)));
	}

	// Method to update the implementation agency data.
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/editImplAgency", method = RequestMethod.POST)
	public ResponseObject editImplAgency(@RequestBody ImplAgencyBean userBean, HttpServletRequest request)
			throws Exception {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Updating ImplAgency data", user.getUsername(), user.getAuthorities());
		ResponseObject response = new ResponseObject();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = dateFormat.format(new Date());
		String errorMsg = commonService.editImplAgency(userBean, dateString);

		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
		} else {
			response.setSuccessMessage("Implementation Agency updated successfully!");
			logger.info("User - {}, Role - {} - Implementation Agency updated successfully!", user.getUsername(),
					user.getAuthorities());
		}
		return response;
	}

	// Method to update the head data for implementation agency.
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/editHead", method = RequestMethod.POST)
	public ResponseObject editHead(@RequestBody WorkHeadBean workHeadBean, HttpServletRequest request)
			throws Exception {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Updating ImplAgency data", user.getUsername(), user.getAuthorities());
		ResponseObject response = new ResponseObject();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = dateFormat.format(new Date());
		String errorMsg = commonService.editHead(workHeadBean, dateString);

		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
		} else {
			response.setSuccessMessage("Head updated successfully!");
			logger.info("User - {}, Role - {} -Head updated successfully!", user.getUsername(), user.getAuthorities());
		}
		return response;
	}

	// Method to edit and update scheme data.
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/editScheme", method = RequestMethod.POST)
	public ResponseObject editScheme(@RequestBody SchemeBean schemeBean, HttpServletRequest request) throws Exception {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Updating Scheme data", user.getUsername(), user.getAuthorities());
		ResponseObject response = new ResponseObject();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = dateFormat.format(new Date());
		String errorMsg = commonService.editScheme(schemeBean, dateString);

		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
		} else {
			response.setSuccessMessage("Scheme updated successfully!");
			logger.info("User - {}, Role - {} - Scheme updated successfully!", user.getUsername(),
					user.getAuthorities());
		}
		return response;
	}

	// Method to edit and update the Scheme data (SOR).
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/editSor", method = RequestMethod.POST)
	public ResponseObject editSor(@RequestBody SorYearBean schemeBean, HttpServletRequest request) throws Exception {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Updating Scheme data", user.getUsername(), user.getAuthorities());
		ResponseObject response = new ResponseObject();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = dateFormat.format(new Date());
		String errorMsg = commonService.editSor(schemeBean, dateString);

		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
		} else {
			response.setSuccessMessage("Scheme updated successfully!");
			logger.info("User - {}, Role - {} - Scheme updated successfully!", user.getUsername(),
					user.getAuthorities());
		}
		return response;
	}

	// Method to delete an implementation agency by its ID.
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/deleteImplAgency/{id}", method = RequestMethod.POST)
	public ResponseObject deleteImplAgency(@PathVariable Long id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Deleting ImplAgency", user.getUsername(), user.getAuthorities());
		ResponseObject response = new ResponseObject();

		String errorMsg = commonService.deleteImplAgency(id);

		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
		} else {
			response.setSuccessMessage("Implementation Agency deleted successfully!");
			logger.info("User - {}, Role - {} - Implementation Agency deleted successfully!", user.getUsername(),
					user.getAuthorities());
		}
		return response;
	}

	// Method to delete a head based on the provided ID.
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/deleteHead/{id}", method = RequestMethod.POST)
	public ResponseObject deleteHead(@PathVariable Long id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Deleting Head", user.getUsername(), user.getAuthorities());
		ResponseObject response = new ResponseObject();

		String errorMsg = commonService.deleteHead(id);

		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
		} else {
			response.setSuccessMessage("Head deleted successfully!");
			logger.info("User - {}, Role - {} - Head deleted successfully!", user.getUsername(), user.getAuthorities());
		}
		return response;
	}

	// Method to delete a scheme by its ID and return a response message.
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/deleteScheme/{id}", method = RequestMethod.POST)
	public ResponseObject deleteScheme(@PathVariable Long id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Deleting Scheme", user.getUsername(), user.getAuthorities());
		ResponseObject response = new ResponseObject();

		String errorMsg = commonService.deleteScheme(id);

		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
		} else {
			response.setSuccessMessage("Scheme deleted successfully!");
			logger.info("User - {}, Role - {} - Scheme deleted successfully!", user.getUsername(),
					user.getAuthorities());
		}
		return response;
	}

	// Method to delete an SOR by its ID.
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/deleteSor/{id}", method = RequestMethod.POST)
	public ResponseObject deleteSor(@PathVariable Long id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Deleting Sor", user.getUsername(), user.getAuthorities());
		ResponseObject response = new ResponseObject();

		String errorMsg = commonService.deleteSor(id);

		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
		} else {
			response.setSuccessMessage("Sor deleted successfully!");
			logger.info("User - {}, Role - {} -Sordeleted successfully!", user.getUsername(), user.getAuthorities());
		}
		return response;
	}

	// Method to download a work document based on the provided document ID.
	@RequestMapping(value = "/downloadWorkDocument/{documentId}", method = RequestMethod.GET)
	public void downloadEntrepreneurDocument(@PathVariable Long documentId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String fileName = commonService.fetchWorkFileName(documentId);
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

	@RequestMapping(value = "/implAgencyPhotoUploadReport", method = RequestMethod.GET)
	public ModelAndView implAgencyPhotoUploadReport(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();

		ModelAndView modelAndView = null;

		logger.info("User - " + user.getUsername() + ", Role - " + user.getAuthorities()
				+ " - Displaying Impl Agency Photo Upload Report");
		modelAndView = new ModelAndView("common/implAgencyPhotoUploadReport");
		return modelAndView;
	}

	// Method to fetch the default document list.
	@RequestMapping(value = "fetchDefaultDocList", method = RequestMethod.GET)
	public List<OtherDocListBean> fetchDefaultDocList(HttpServletRequest request) {
		return commonService.fetchDefaultDocList();

	}

	// Method to handle uploading of other work-related documents.
	@RequestMapping(value = "/uploadOtherDoc", method = RequestMethod.POST)
	public ResponseObject uploadOtherDoc(OtherDocListBean otherDocListBean, HttpServletRequest request)
			throws Exception {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Adding Work documents", user.getUsername(), user.getAuthorities());
		ResponseObject response = null;

		try {
			response = commonService.uploadOtherDoc(otherDocListBean);

			if (response != null) {
				response.setSuccessMessage("Work saved successfully!");
				logger.info("User - {}, Role - {} - Other documents saved successfully!", user.getUsername(),
						user.getAuthorities());
			} else {
				response = new ResponseObject();
				String errorMsg = DMSConstants.ERROR_SAVING_DATA;
				response.setErrorMessage(errorMsg);
				logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
			}
		} catch (Exception e) {
			String errorMsg = e.getMessage();
			response = new ResponseObject();
			response.setErrorMessage(errorMsg);
			logger.error("User - " + user.getUsername() + ", Role - " + user.getAuthorities() + " - " + errorMsg);
		}
		return response;
	}

	@RequestMapping(value = "fetchWorkProgressImagesDataList", method = RequestMethod.GET)
	public List<WorkProgressImageListBean> fetchWorkProgressImagesDataList(HttpServletRequest request) {
		return commonService.fetchWorkProgressImagesDataList();

	}

	// Method to fetch the list of work progress images data.
	@RequestMapping(value = "fetchInpectionImageDataList", method = RequestMethod.GET)
	public List<OtherDocListBean> fetchInpectionImageDataList(HttpServletRequest request) {
		return commonService.fetchDefaultDocList();

	}

	// delete File
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM','ROLE_DEPARTMENT')")
	@RequestMapping(value = "/deleteFile/{id}", method = RequestMethod.POST)
	public ResponseObject deleteFile(@PathVariable Long id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - " + user.getUsername() + ", Role - " + user.getAuthorities() + " - Deleting Photo");
		ResponseObject response = new ResponseObject();

		String errorMsg = commonService.deleteFile(id);

		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - " + user.getUsername() + ", Role - " + user.getAuthorities() + " - " + errorMsg);
		} else {
			response.setSuccessMessage("file deleted successfully!");
			logger.info("User - " + user.getUsername() + ", Role - " + user.getAuthorities()
					+ " - file deleted successfully!");
		}
		return response;
	}

	// Method to fetch CC work details based on the provided ID.
	@RequestMapping(value = "fetchCCDetails/{id}", method = RequestMethod.GET)
	public CCBean fetchCCWorkDetails(@PathVariable Long id, HttpServletRequest request) throws ParseException {
		user = DMSUtil.getUserDetail();
		String role = DMSUtil.getUserRole(user);
		logger.info("User - {}, Role - {} - Fetching CC Work data", user.getUsername(), user.getAuthorities());

		CCBean ccBean = commonService.fetchCCWorkDetails(id);
		return ccBean;

	}

	@RequestMapping(value = "/downloadDocument/{documentId}", method = RequestMethod.GET)
	public void downloadImageDocument(@PathVariable Long documentId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		/*
		 * String fileName = commonService.fetchImageName(documentId);
		 * 
		 * if (fileName != null) { File file = new File(fileName); InputStream is = new
		 * FileInputStream(file);
		 * 
		 * // MIME type of the file response.setContentType("application/octet-stream");
		 * // Response header response.setHeader("Content-Disposition",
		 * "attachment; filename=\"" + file.getName() + "\""); // Read from the file and
		 * write into the response OutputStream os = response.getOutputStream(); byte[]
		 * buffer = new byte[1024]; int len; while ((len = is.read(buffer)) != -1) {
		 * os.write(buffer, 0, len); } os.flush(); os.close(); is.close(); }
		 */
	}

	// Method to handle document download based on the provided document ID.
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

	// Method to handle downloading of a revised document by document ID.
	@RequestMapping(value = "/downloadDocumentTSRevised/{documentId}", method = RequestMethod.GET)
	public void downloadDocumentTSRevised(@PathVariable Long documentId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" downloadDocument =" + documentId);

		String fileName = commonService.fetchDownloadFileNameRevised(documentId);
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

	// Method to handle document download for work sample based on document ID.
	@RequestMapping(value = "/downloadDocumentWSPro/{documentId}", method = RequestMethod.GET)
	public void downloadDocumentWSPro(@PathVariable Long documentId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.error(" downloadDocument =");

		String fileName = commonService.fetchDownloadDocumentWSPro(documentId);

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

				// Detect MIME type — default to image/jpeg for images
				String contentType = request.getServletContext().getMimeType(file.getName());
				if (contentType == null) {
					String nameLower = file.getName().toLowerCase();
					if (nameLower.endsWith(".jpg") || nameLower.endsWith(".jpeg")) {
						contentType = "image/jpeg";
					} else if (nameLower.endsWith(".png")) {
						contentType = "image/png";
					} else if (nameLower.endsWith(".gif")) {
						contentType = "image/gif";
					} else if (nameLower.endsWith(".pdf")) {
						contentType = "application/pdf";
					} else {
						contentType = "application/octet-stream";
					}
				}
				response.setContentType(contentType);
				// Use "inline" so images display directly in <img> tags and modals
				response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
				response.setContentLengthLong(file.length());

				// Read from the file and write into the response
				byte[] buffer = new byte[4096];
				int len;
				while ((len = is.read(buffer)) != -1) {
					os.write(buffer, 0, len);
				}

				os.flush();
			} catch (IOException e) {
				logger.error("Error while processing file: {}", fileName, e);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while processing file");
			}
		} else {
			logger.error("Invalid file name for documentId: {}", documentId);
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found for documentId: " + documentId);
		}

	}

	// Method to handle downloading of a document based on its document ID.
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

	// Method to download a drawing document based on the provided document ID.
	@RequestMapping(value = "/downloadDocumentDW/{documentId}", method = RequestMethod.GET)
	public void downloadDocumentDW(@PathVariable Long documentId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" downloadDocument =");

		String fileName = commonService.fetchWorkFileNameDW(documentId);
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

	// Method to download a tender document based on the provided document ID.
	@RequestMapping(value = "/downloadDocumentTender/{documentId}", method = RequestMethod.GET)
	public void downloadDocumentTender(@PathVariable Long documentId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" downloadDocument =");

		String fileName = commonService.fetchDownloadFileName(documentId);

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

	// Method to download a progress document based on the provided document ID.
	@RequestMapping(value = "/downloadDocumentProgress/{documentId}", method = RequestMethod.GET)
	public void downloadDocumentProgress(@PathVariable Long documentId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" downloadDocument =");

		String fileName = commonService.fetchDownloadFileName(documentId);

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

	// Method to download a CC document based on the provided document ID.
	@RequestMapping(value = "/downloadDocumentCC/{documentId}", method = RequestMethod.GET)
	public void downloadDocumentCC(@PathVariable Long documentId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" downloadDocument =");

		String fileName = commonService.fetchDownloadFileName(documentId);
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

	// Method to add CC (Construction Certificate) data and save it to the system.
	@RequestMapping(value = "/addCCData", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	@ResponseBody
	public ResponseObject addCCData(CCBean ccBean, HttpServletRequest request) throws Exception {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Adding CC data", user.getUsername(), user.getAuthorities());
		ResponseObject response = null;

		try {

			// String remoteIpAddr = request.getHeader("X-Forwarded-For");

			response = commonService.addCCDetails(ccBean);
			if (response != null) {
				response.setSuccessMessage("Work saved successfully!");
				logger.info("User - {}, Role - {} - CC saved successfully!", user.getUsername(), user.getAuthorities());
			} else {
				response = new ResponseObject();
				String errorMsg = DMSConstants.ERROR_SAVING_DATA;
				response.setErrorMessage(errorMsg);
				logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
			}
		} catch (Exception e) {
			String errorMsg = e.getMessage();
			response = new ResponseObject();
			response.setErrorMessage(errorMsg);
			logger.error("User - " + user.getUsername() + ", Role - " + user.getAuthorities() + " - " + errorMsg);
		}
		return response;
	}

	// Method to fetch a list of LCs by district ID.
	@RequestMapping(value = "fetchLCsByDistrictIdC/{districtId}", method = RequestMethod.GET)
	@ResponseBody
	public List<LCBean> fetchLCsByDistrictNameC(@PathVariable Long districtId, HttpServletRequest request) {
		return commonService.fetchLCsByDistrictId(districtId);
	}
	@RequestMapping(value = "/downloadDocumentRemakrs/{id}", method = RequestMethod.GET)
	public void downloadDocumentRemarks(@PathVariable Long id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" downloadDocument =");

		String fileName = commonService.fetchDownloadFileName(id);
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
			logger.error("Invalid file name for documentId: {}", id);
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid file name");
		}

	}
	
	
	@GetMapping("/previewDocumentRemarks/{id}")
	public ResponseEntity<byte[]> previewDocumentRemarks(@PathVariable Long id) {
	    logger.info("previewDocument =");

	    String fileName = commonService.fetchDownloadFileName(id);

	    if (fileName != null) {
	        File file = new File(fileName);

	        if (!file.exists()) {
	            logger.error("File not found: {}", fileName);
	            return ResponseEntity.notFound().build();
	        }

	        try {
	            byte[] fileBytes = Files.readAllBytes(file.toPath());

	            
	            String mimeType = Files.probeContentType(file.toPath());
	            if (mimeType == null) {
	                mimeType = "application/octet-stream"; 
	            }

	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(MediaType.parseMediaType(mimeType));
	            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"");

	            return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);

	        } catch (IOException e) {
	            logger.error("Error reading file: {}", fileName, e);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }

	    } else {
	        logger.error("Invalid file name for documentId: {}", id);
	        return ResponseEntity.badRequest().build();
	    }
	}

	
	// Method to fetch work statuses based on scheme ID and status ID.
	@RequestMapping(value = "fetchWorkStatusByScheme/{schemeId}/{statusId}", method = RequestMethod.GET)
	public List<WorkStatusBean> fetchWorkStatusByScheme(@PathVariable Long schemeId, @PathVariable Long statusId,
			HttpServletRequest request) {
		return commonService.fetchWorkStatusByScheme(schemeId, statusId);
	}

	@RequestMapping(value = "/printSelectedAS/{parentAsId}", method = RequestMethod.GET)
	public ModelAndView printSelectedAS(@PathVariable String parentAsId, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - " + user.getUsername() + ", Role - " + user.getAuthorities()
				+ " - Printing Admin Sanction For Print  ");

		ModelAndView modelAndView = new ModelAndView("common/printSelectedAS");
		return modelAndView;

	}

	@RequestMapping(value = "/printPreviewSelectedAS/{parentAsId}", method = RequestMethod.GET)
	public ModelAndView printPreviewSelectedAS(@PathVariable String parentAsId, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - " + user.getUsername() + ", Role - " + user.getAuthorities()
				+ " - Print Preview Admin Sanction Before Saving ");

		ModelAndView modelAndView = new ModelAndView("common/printPreviewSelectedAS");
		return modelAndView;

	}

	@RequestMapping(value = "/printPreviewGenerateAS/{workIds}", method = RequestMethod.GET)
	public ModelAndView printPreviewGenerateAS(@PathVariable String workIds, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - " + user.getUsername() + ", Role - " + user.getAuthorities()
				+ " - Print Preview Admin Sanction Before Saving Input Form");

		ModelAndView modelAndView = new ModelAndView("common/printPreviewGenerateAS");
		return modelAndView;

	}
	/*
	 * /// Generate Dynamic PPT BY Yousra Shafiq
	 * 
	 * @RequestMapping(value = "/generatePPT", method = RequestMethod.GET) public
	 * ResponseEntity<ByteArrayResource> generatePowerPoint() throws Exception { try
	 * { List<SlideData> slideDataList = new ArrayList<>();
	 * 
	 * LocalDate currentDate = LocalDate.now(); DateTimeFormatter formatter =
	 * DateTimeFormatter.ofPattern("dd-MM-yyyy"); String currentDateStr =
	 * currentDate.format(formatter);
	 * 
	 * String financialYear = financialYearRepository.findMostRecentFinancialYear();
	 * 
	 * List<Object[]> dataForSlide1 = workRepository.fetchDataReportOne(); if
	 * (dataForSlide1.size() > 0) { List<String> headersForSlide1 =
	 * calculateHeaders(dataForSlide1, 1); List<String> subHeadersForSlide1 =
	 * calculateSubHeaders(dataForSlide1, 1); List<Double> totals =
	 * calculateTotal(dataForSlide1, 1); SlideData slideData1 = new SlideData(
	 * "वित्तीय वर्ष " + financialYear +
	 * " का भौतिक एवं वित्तीय लक्ष्य/उपलब्धि की जानकारी ", dataForSlide1, 1);
	 * slideData1.setHeaders(headersForSlide1); // slideData1.setTotal(totals);
	 * slideData1.setSubHeaders(subHeadersForSlide1);
	 * slideData1.setTableData(dataForSlide1); slideDataList.add(slideData1);
	 * 
	 * }
	 * 
	 * List<Object[]> dataForSlide2 = workRepository.fetchDataReportTwo(); if
	 * (dataForSlide2.size() > 0) { List<String> headersForSlide2 =
	 * calculateHeaders(dataForSlide2, 2); List<Double> totals2 =
	 * calculateTotal(dataForSlide2, 2); SlideData slideData2 = new SlideData(
	 * "वित्तीय वर्ष " + financialYear +
	 * " का भौतिक एवं वित्तीय लक्ष्य/उपलब्धि की जानकारी ", dataForSlide2, 2);
	 * slideData2.setHeaders(headersForSlide2); // slideData1.setTotal(totals2);
	 * slideData2.setTableData(dataForSlide2); slideDataList.add(slideData2);
	 * 
	 * }
	 * 
	 * List<Object[]> dataForSlide3 = workRepository.fetchDataReportThree(); if
	 * (dataForSlide3.size() > 0) { List<String> headersForSlide3 =
	 * calculateHeaders(dataForSlide3, 3); List<Double> totals3 =
	 * calculateTotal(dataForSlide3, 3); SlideData slideData3 = new
	 * SlideData("निर्माण एजेंसी की जानकारी ", dataForSlide3, 3);
	 * slideData3.setHeaders(headersForSlide3); // slideData1.setTotal(totals3);
	 * slideData3.setTableData(dataForSlide3); slideDataList.add(slideData3); }
	 * 
	 * List<Object[]> dataForSlide4 = workRepository.fetchDataReportFour(); if
	 * (dataForSlide4.size() > 0) { List<String> headersForSlide4 =
	 * calculateHeaders(dataForSlide4, 4); List<Double> totals4 =
	 * calculateTotal(dataForSlide4, 4); SlideData slideData4 = new SlideData(
	 * "Contruction Agency Wise Physical in " + financialYear + " Status on " +
	 * currentDateStr, dataForSlide4, 4); slideData4.setHeaders(headersForSlide4);
	 * // slideData1.setTotal(totals4); slideData4.setTableData(dataForSlide4);
	 * slideDataList.add(slideData4);
	 * 
	 * }
	 * 
	 * List<Object[]> dataForSlide5 = workRepository.fetchDataReportFive(); if
	 * (dataForSlide5.size() > 0) { List<String> headersForSlide5 =
	 * calculateHeaders(dataForSlide5, 5); List<Double> totals5 =
	 * calculateTotal(dataForSlide5, 5); SlideData slideData5 = new
	 * SlideData("Contruction Agency Wise Financial Status on " + currentDateStr,
	 * dataForSlide5, 5); slideData5.setHeaders(headersForSlide5); //
	 * slideData5.setTotal(totals5); slideData5.setTableData(dataForSlide5);
	 * slideDataList.add(slideData5);
	 * 
	 * }
	 * 
	 * List<Object[]> dataForSlide6 = new ArrayList<>();
	 * 
	 * List<String> headersForSlide6 = calculateHeaders(dataForSlide6, 6); //
	 * List<Double> totals19=calculateTotal(dataForSlide19, 19); SlideData
	 * slideData6 = new
	 * SlideData("Construction Agency Wise & Year Wise Detail Over all Work",
	 * dataForSlide6, 6); slideData6.setHeaders(headersForSlide6); //
	 * slideData19.setTotal(totals19); slideData6.setTableData(dataForSlide6);
	 * slideDataList.add(slideData6);
	 * 
	 * List<Integer> agencylist = workRepository.fetchWorkAgencyData(); int index =
	 * 8; for (int i = 0; i < agencylist.size(); i++) {
	 * 
	 * List<Object[]> data = workRepository.fetchDataReportNew(agencylist.get(i));
	 * if (data.size() > 0) { List<String> headersForSlide = calculateHeaders(data,
	 * 8); // List<Double> total=calculateTotal(data, index); SlideData slideData =
	 * new SlideData("Status of Construction Work Scheme Wise- " +
	 * agencylist.get(i), data, 8); slideData.setHeaders(headersForSlide); //
	 * slideData.setTotal(total); slideData.setTableData(data);
	 * slideDataList.add(slideData); index++;
	 * 
	 * }
	 * 
	 * List<Object[]> data1 =
	 * workRepository.fetchDataReportFinancialNew(agencylist.get(i)); if
	 * (data1.size() > 0) { List<String> headersForSlide0 = calculateHeaders(data1,
	 * 9); // List<Double> total1=calculateTotal(data1, index); SlideData slideData0
	 * = new SlideData(
	 * "Status of Construction Work Scheme and Financial Year Wise- " +
	 * agencylist.get(i), data1, 9); slideData0.setHeaders(headersForSlide0); //
	 * slideData0.setTotal(total1); slideData0.setTableData(data1);
	 * slideDataList.add(slideData0); index++;
	 * 
	 * }
	 * 
	 * List<Object[]> data2 =
	 * workRepository.fetchDataReportSchemeMultifunded(agencylist.get(i)); if
	 * (data2.size() > 0) { List<String> headersForSlide22 = calculateHeaders(data2,
	 * 10); // List<Double> total22=calculateTotal(data2, index); SlideData
	 * slideData22 = new SlideData(
	 * "Status of Construction Multifunded Work Scheme Wise- " + agencylist.get(i),
	 * data2, 10); slideData22.setHeaders(headersForSlide22); //
	 * slideData22.setTotal(total22); slideData22.setTableData(data2);
	 * slideDataList.add(slideData22); index++;
	 * 
	 * }
	 * 
	 * List<Object[]> data3 =
	 * workRepository.fetchDataReportFinancialMultifunded(agencylist.get(i)); if
	 * (data3.size() > 0) { List<String> headersForSlide23 = calculateHeaders(data3,
	 * 11); // List<Double> total23=calculateTotal(data3, index); SlideData
	 * slideData23 = new SlideData(
	 * "Status of Construction Multifunded Work Scheme & Financial Year Wise- " +
	 * agencylist.get(i), data3, 11); slideData23.setHeaders(headersForSlide23); //
	 * slideData23.setTotal(total23); slideData23.setTableData(data3);
	 * slideDataList.add(slideData23); // index++;
	 * 
	 * }
	 * 
	 * logger.info("List......." + data.size()); }
	 * 
	 * List<Object[]> dataForSlide7 = new ArrayList<>();
	 * 
	 * List<String> headersForSlide7 = calculateHeaders(dataForSlide7, 7); //
	 * List<Double> totals18=calculateTotal(dataForSlide18, 18); SlideData
	 * slideData7 = new SlideData("Thank You ", dataForSlide7, 7);
	 * slideData7.setHeaders(headersForSlide7); // slideData18.setTotal(totals18);
	 * slideData7.setTableData(dataForSlide7); slideDataList.add(slideData7);
	 * 
	 * byte[] presentationBytes = commonService.generatePresentation(slideDataList);
	 * 
	 * ByteArrayResource resource = new ByteArrayResource(presentationBytes);
	 * 
	 * HttpHeaders headers = new HttpHeaders();
	 * headers.add(HttpHeaders.CONTENT_DISPOSITION,
	 * "attachment; filename=generated.pptx");
	 * headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	 * 
	 * return
	 * ResponseEntity.ok().headers(headers).contentLength(presentationBytes.length)
	 * .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource); } catch
	 * (IOException e) { e.printStackTrace(); // Handle the error appropriately
	 * return ResponseEntity.badRequest().body(null); } }
	 */

	/*
	 * private List<String> calculateHeaders(List<Object[]> dataForSlide, int value)
	 * {
	 * 
	 * String financialYear = financialYearRepository.findMostRecentFinancialYear();
	 * 
	 * List<String> headers = new ArrayList<>();
	 * 
	 * // Check the number of columns in the query data if (dataForSlide != null &&
	 * !dataForSlide.isEmpty()) { Object[] firstRow = dataForSlide.get(0); //
	 * Assuming the first row contains the headers int countChang = 0; // Add header
	 * names based on the number of columns for (int col = 0; col < firstRow.length;
	 * col++) { // Generate header names dynamically based on the column index
	 * 
	 * if (value == 1) { if (col == 0) { String headerName = "निर्माण एजेंसी ";
	 * headers.add(headerName); } else if (col == 1) { String headerName =
	 * "कार्यो की संख्या "; headers.add(headerName); } else if (col == 2) { String
	 * headerName = "प्रशासकीय स्वीकृति राशी (रु. लाख में)";
	 * headers.add(headerName); } else if (col == 3) { String headerName =
	 * "कार्यो की संख्या "; headers.add(headerName); } else if (col == 4) { String
	 * headerName = "प्रशासकीय स्वीकृति राशी (रु. लाख में)";
	 * headers.add(headerName); } else if (col == 5) { String headerName =
	 * "कार्यो की संख्या "; headers.add(headerName); } else if (col == 6) { String
	 * headerName = "प्रशासकीय स्वीकृति राशी (रु. लाख में)";
	 * headers.add(headerName); } } else if (value == 2) { if (col == 0) { String
	 * headerName = "निर्माण एजेंसी "; headers.add(headerName); } else if (col == 1)
	 * { String headerName = "कार्यो की संख्या "; headers.add(headerName); } else if
	 * (col == 2) { String headerName = "प्रशासकीय स्वीकृति राशी (रु. लाख में)";
	 * headers.add(headerName); } else if (col == 3) { String headerName =
	 * "भौतिक लक्ष्य "; headers.add(headerName); } else if (col == 4) { String
	 * headerName = "वित्तीय लक्ष्य राशी (रू. लाख में)"; headers.add(headerName); }
	 * else if (col == 5) { String headerName = "भौतिक लक्ष्य ";
	 * headers.add(headerName); } else if (col == 6) { String headerName =
	 * "वित्तीय लक्ष्य राशी (रू. लाख में)"; headers.add(headerName); } else if (col
	 * == 7) { String headerName = "भौतिक उपलब्धि"; headers.add(headerName); } else
	 * if (col == 8) { String headerName = "वित्तीय उपलब्धि राशी (रू. लाख में)";
	 * headers.add(headerName); } } else if (value == 3) { if (col == 0) { String
	 * headerName = "निर्माण एजेंसी "; headers.add(headerName); } else if (col == 1)
	 * { String headerName = "कार्यो की संख्या "; headers.add(headerName); } else if
	 * (col == 2) { String headerName =
	 * "प्रशासकीय स्वीकृति की संख्या राशी लाख में "; headers.add(headerName); } }
	 * else if (value == 4) { if (col == 0) { String headerName =
	 * "Construction Agency"; headers.add(headerName); } else if (col == 1) { String
	 * headerName = "Number of Works"; headers.add(headerName); } else if (col == 2)
	 * { String headerName = "HandOver"; headers.add(headerName); } else if (col ==
	 * 3) { String headerName = "Work Completed in Yr " + financialYear + "";
	 * headers.add(headerName); } else if (col == 4) { String headerName =
	 * "Work Started"; headers.add(headerName); } else if (col == 5) { String
	 * headerName = "Tender Awarded"; headers.add(headerName); } else if (col == 6)
	 * { String headerName = "Tender Issued"; headers.add(headerName); } else if
	 * (col == 7) { String headerName =
	 * "Site Not Selected out of total work alloted"; headers.add(headerName); } }
	 * else if (value == 5) { if (col == 0) { String headerName =
	 * "Construction Agency"; headers.add(headerName); } else if (col == 1) { String
	 * headerName = "Number of Works"; headers.add(headerName); } else if (col == 2)
	 * { String headerName = "Amount of AA(Rs. in Lakh)"; headers.add(headerName); }
	 * else if (col == 3) { String headerName = "Expenditure in Yr " + financialYear
	 * + " (Rs. in Lakh)"; headers.add(headerName); } else if (col == 4) { String
	 * headerName = "Total Expenditure Till Date (Rs.in Lakh)";
	 * headers.add(headerName); } } else if (value == 8) { if (col == 0) { String
	 * headerName = "Scheme"; headers.add(headerName); } else if (col == 1) { String
	 * headerName = "Number of Works"; headers.add(headerName); } else if (col == 2)
	 * { String headerName = "Amount of AA (Rs.In Lakh)"; headers.add(headerName); }
	 * else if (col == 3) { String headerName = "Total Expenditure(Rs.In Lakh)";
	 * headers.add(headerName); } else if (col == 4) { String headerName =
	 * "Expenditure Yr " + financialYear + " (Rs.In Lakh)"; headers.add(headerName);
	 * } else if (col == 5) { String headerName = "Handed Over in Yr " +
	 * financialYear + ""; headers.add(headerName); } else if (col == 6) { String
	 * headerName = "Work Completed in Yr " + financialYear + "";
	 * headers.add(headerName); } else if (col == 7) { String headerName =
	 * "Work Started"; headers.add(headerName); } else if (col == 8) { String
	 * headerName = "Tender Awarded"; headers.add(headerName); } else if (col == 9)
	 * { String headerName = "Tender Issued"; headers.add(headerName); } else if
	 * (col == 10) { String headerName =
	 * "Site Not Selected out of total work alloted"; headers.add(headerName); } }
	 * else if (value == 9) { if (col == 0) { String headerName = "Scheme";
	 * headers.add(headerName); } else if (col == 1) { String headerName =
	 * "Financial Year"; headers.add(headerName); } else if (col == 2) { String
	 * headerName = "Number of Works"; headers.add(headerName); } else if (col == 3)
	 * { String headerName = "Amount of AA (Rs.In Lakh)"; headers.add(headerName); }
	 * else if (col == 4) { String headerName = "Total Expenditure(Rs.In Lakh)";
	 * headers.add(headerName); } else if (col == 5) { String headerName =
	 * "Expenditure Yr " + financialYear + "(Rs.In Lakh)"; headers.add(headerName);
	 * } else if (col == 6) { String headerName = "Handed Over in Yr " +
	 * financialYear + ""; headers.add(headerName); } else if (col == 7) { String
	 * headerName = "Work Completed in Yr " + financialYear + "";
	 * headers.add(headerName); } else if (col == 8) { String headerName =
	 * "Work Started"; headers.add(headerName); } else if (col == 9) { String
	 * headerName = "Tender Awarded"; headers.add(headerName); } else if (col == 10)
	 * { String headerName = "Tender Issued"; headers.add(headerName); } else if
	 * (col == 11) { String headerName =
	 * "Site Not Selected out of total work alloted"; headers.add(headerName); } }
	 * else if (value == 10) { if (col == 0) { String headerName = "Scheme";
	 * headers.add(headerName); } else if (col == 1) { String headerName =
	 * "Number of Works"; headers.add(headerName); } else if (col == 2) { String
	 * headerName = "Funds (Rs.In Lakh)"; headers.add(headerName); } else if (col ==
	 * 3) { String headerName = "Total Expenditure(Rs.In Lakh)";
	 * headers.add(headerName); } else if (col == 4) { String headerName =
	 * "Expenditure Yr " + financialYear + "(Rs.In Lakh)"; headers.add(headerName);
	 * } else if (col == 5) { String headerName = "Handed Over in Yr " +
	 * financialYear + ""; headers.add(headerName); } else if (col == 6) { String
	 * headerName = "Work Completed in Yr " + financialYear + "";
	 * headers.add(headerName); } else if (col == 7) { String headerName =
	 * "Work Started"; headers.add(headerName); } else if (col == 8) { String
	 * headerName = "Tender Awarded"; headers.add(headerName); } else if (col == 9)
	 * { String headerName = "Tender Issued"; headers.add(headerName); } else if
	 * (col == 10) { String headerName =
	 * "Site Not Selected out of total work alloted"; headers.add(headerName); } }
	 * else if (value == 11) { if (col == 0) { String headerName = "Scheme";
	 * headers.add(headerName); } else if (col == 1) { String headerName =
	 * "Financial Year"; headers.add(headerName); } else if (col == 2) { String
	 * headerName = "Number of Works"; headers.add(headerName); } else if (col == 3)
	 * { String headerName = "Funds (Rs.In Lakh)"; headers.add(headerName); } else
	 * if (col == 4) { String headerName = "Total Expenditure(Rs.In Lakh)";
	 * headers.add(headerName); } else if (col == 5) { String headerName =
	 * "Expenditure Yr " + financialYear + "(Rs.In Lakh)"; headers.add(headerName);
	 * } else if (col == 6) { String headerName = "Handed Over in Yr " +
	 * financialYear + ""; headers.add(headerName); } else if (col == 7) { String
	 * headerName = "Work Completed in Yr " + financialYear + "";
	 * headers.add(headerName); } else if (col == 8) { String headerName =
	 * "Work Started"; headers.add(headerName); } else if (col == 9) { String
	 * headerName = "Tender Awarded"; headers.add(headerName); } else if (col == 10)
	 * { String headerName = "Tender Issued"; headers.add(headerName); } else if
	 * (col == 11) { String headerName =
	 * "Site Not Selected out of total work alloted"; headers.add(headerName); } }
	 * 
	 * } }
	 * 
	 * return headers; }
	 */

	/*
	 * private List<Double> calculateTotal(List<Object[]> dataForSlide, int value) {
	 * List<Double> totals = new ArrayList<>();
	 * 
	 * Double amount = 0.0; if (dataForSlide != null && !dataForSlide.isEmpty()) {
	 * Object[] firstRow = dataForSlide.get(0); // Assuming the first row contains
	 * the headers int countChang = 0;
	 * 
	 * for (int col = 0; col < dataForSlide.size(); col++) { Object cellValue =
	 * dataForSlide.get(col); if (cellValue instanceof Double) { // If it's already
	 * a double, just add it to totals double doubleValue = (Double) cellValue;
	 * totals.add(doubleValue); } else if (cellValue instanceof Integer) { // If
	 * it's an integer, convert it to a double and add it to totals int intValue =
	 * (Integer) cellValue; double doubleValue = (double) intValue;
	 * totals.add(doubleValue); } else if (cellValue instanceof String) { // If it's
	 * a string, try to parse it as a double try { double doubleValue =
	 * Double.parseDouble((String) cellValue); totals.add(doubleValue); } catch
	 * (NumberFormatException e) { // Handle the case where parsing as a double
	 * fails } }
	 * 
	 * } }
	 * 
	 * return totals; }
	 */

	/*
	 * private List<String> calculateSubHeaders(List<Object[]> dataForSlide, int
	 * value) { List<String> subHeaders = new ArrayList<>();
	 * 
	 * // Check the number of columns in the query data if (dataForSlide != null &&
	 * !dataForSlide.isEmpty()) { Object[] firstRow = dataForSlide.get(0); //
	 * Assuming the first row contains the headers int countChang = 0; // Add header
	 * names based on the number of columns for (int col = 0; col < firstRow.length;
	 * col++) {
	 * 
	 * if (value == 1) { if (col == 0) { String subheaderName =
	 * "दिनांक 01.04.23 की स्थिति में शेष कार्य"; subHeaders.add(subheaderName); }
	 * else if (col == 1) { String subheaderName =
	 * "वर्ष 2023-24 नविन स्वीकृत कार्य"; subHeaders.add(subheaderName); } else if
	 * (col == 2) { String subheaderName = "वर्ष 2023-24 नविन कुल कार्य";
	 * subHeaders.add(subheaderName); }
	 * 
	 * }
	 * 
	 * }
	 * 
	 * }
	 * 
	 * return subHeaders; }
	 */

	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/manageSdr", method = RequestMethod.GET)
	public ModelAndView manageSdrView(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Manage Scheme page", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("common/manageSdr");
		// ModelAndView modelAndView = new ModelAndView("<h1>Hello dhs</h1>");
		UserBean userBean = fetchLoggedInUserDetails(request);
		modelAndView.addObject("roleName", userBean.getLoggedInUserRole());
		modelAndView.addObject("userName", userBean.getEmailId());
		return modelAndView;
	}

	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/addSdrForm", method = RequestMethod.GET)
	public ModelAndView viewAddSdrForm(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Add Scheme Form", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("common/addSdrForm");
		UserBean userBean = fetchLoggedInUserDetails(request);
		modelAndView.addObject("roleName", userBean.getLoggedInUserRole());
		modelAndView.addObject("userName", userBean.getEmailId());
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = dateFormat.format(new Date());
		modelAndView.addObject("myDate", dateString);

		return modelAndView;
	}

	// Method to add sub delayed reason data for a scheme.
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/addSdr", method = RequestMethod.POST)
	public ResponseObject addSdr(@RequestBody WorkSubDelayResonBean bean, HttpServletRequest request) throws Exception {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Adding dealyed reason data", user.getUsername(), user.getAuthorities());
		ResponseObject response = new ResponseObject();
		UserBean userBean = fetchLoggedInUserDetails(request);
		String userName = userBean.getEmailId();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = dateFormat.format(new Date());
		String errorMsg = commonService.addSdr(bean, userName, dateString);
		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
		} else {
			response.setSuccessMessage("Scheme added successfully!");
			logger.info("User - {}, Role - {} -Scheme added successfully!", user.getUsername(), user.getAuthorities());
		}
		return response;
	}

	// Method to fetch the list of SDRs (Sub Delay Reasons) with filters and
	// pagination.
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/fetchSdrList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String fetchSdrList(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - fetchSdr List", user.getUsername(), user.getAuthorities());
		String searchBoxVal = request.getParameter("searchBoxVal");

		String sSortCol = request.getParameter("iSortCol_0");
		String sSortDir = request.getParameter("sSortDir_0");
		String sColName = request.getParameter("mDataProp_" + sSortCol);

		// Fetch the page number from client
		Integer pageNumber = 0;

		Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));

		if (null != request.getParameter("iDisplayStart")) {
			pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart")) / pageDisplayLength);
		}

		Sort sort = null;
		if (sColName != null) {
			if (StringUtils.equals("asc", sSortDir)) {
				sort = Sort.by(Direction.ASC, sColName);
			} else {
				sort = Sort.by(Direction.DESC, sColName);
			}
		} else {
			sort = Sort.by(Direction.ASC, "subDelayReason");// default sorting
		}

		Pageable pageable = PageRequest.of(pageNumber, pageDisplayLength, sort);

		SdrJson sdrJson = commonService.getAllSdr(pageable, searchBoxVal);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(sdrJson);

		return json;
	}

	// Method to delete an SDR (Sub Delay Reasons) by its ID.
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/deleteSdr/{id}", method = RequestMethod.POST)
	public ResponseObject deleteSdr(@PathVariable Long id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Deleting Sdr", user.getUsername(), user.getAuthorities());
		ResponseObject response = new ResponseObject();

		String errorMsg = commonService.deleteSdr(id);

		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
		} else {
			response.setSuccessMessage("Sor deleted successfully!");
			logger.info("User - {}, Role - {} -Sordeleted successfully!", user.getUsername(), user.getAuthorities());
		}
		return response;
	}

	// Method to display the edit SDR form with user and date details.
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/editSdrForm/{id}", method = RequestMethod.GET)
	public ModelAndView viewEditSdrForm(@PathVariable String id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Edit Scheme Form", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("common/editSdrForm");
		UserBean userBean = fetchLoggedInUserDetails(request);
		modelAndView.addObject("roleName", userBean.getLoggedInUserRole());
		modelAndView.addObject("userName", userBean.getEmailId());
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = dateFormat.format(new Date());
		modelAndView.addObject("myDate", dateString);
		return modelAndView;
	}

	// Method to fetch sub-delay reason details based on the provided ID.
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "fetchSdrDetails/{id}", method = RequestMethod.GET)
	public WorkSubDelayResonBean fetchSdrDetails(@PathVariable String id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching ImplAgency data", user.getUsername(), user.getAuthorities());
		return commonService.fetchSdrDetails(Long.parseLong(DMSUtil.decryptParam(id)));
	}

	// Method to update sub delay reason data for a work scheme.
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/editSdr", method = RequestMethod.POST)
	public ResponseObject editSdr(@RequestBody WorkSubDelayResonBean workSubDelayResonBean, HttpServletRequest request)
			throws Exception {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Updating Delay Reason data", user.getUsername(), user.getAuthorities());
		ResponseObject response = new ResponseObject();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = dateFormat.format(new Date());
		String errorMsg = commonService.editSdr(workSubDelayResonBean, dateString);

		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
		} else {
			response.setSuccessMessage("Scheme updated successfully!");
			logger.info("User - {}, Role - {} - Scheme updated successfully!", user.getUsername(),
					user.getAuthorities());
		}
		return response;
	}

	// Method to fetch sub-delay reasons based on work sub-status ID.
	@RequestMapping(value = "fetchSubDelayReasonByWorkSubStatusId/{workSubStatusId}", method = RequestMethod.GET)
	public List<WorkSubDelayResonBean> fetchSubDelayReasonByWorkSubStatusId(@PathVariable long workSubStatusId,
			HttpServletRequest request) {
		return commonService.fetchSubDelayReasonByWorkSubStatusId(workSubStatusId);
	}

	// Add by Sumit

	// Method to fetch work categories based on work types.
	@RequestMapping(value = "fetchWorkCategoryByWorkType", method = RequestMethod.GET)
	public List<WorkCategoryBean> fetchWorkCategoryByWorkTypes(HttpServletRequest request) {
		return commonService.fetchWorkCategoryByWorkTypes();
	}

	// Method to fetch the list of construction agencies.
	@RequestMapping(value = "fetchConstructionAgencys", method = RequestMethod.GET)
	public List<ImplAgencyBean> fetchConstructionAgency(HttpServletRequest request) {
		return commonService.fetchConstructionAgency();
	}

	// Method to download the document agreement by document ID as a PDF
	@RequestMapping(value = "/downloadDocumentAgreement/{documentId}", method = RequestMethod.GET)
	public void downloadDocumentAgreement(@PathVariable Long documentId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" downloadDocument =");

		String fileName = commonService.fetchWorkFileNameAgreement(documentId);
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

	// Method to download a document (LOI) based on the provided document ID.
	@RequestMapping(value = "/downloadDocumentLOI/{documentId}", method = RequestMethod.GET)
	public void downloadDocumentLOI(@PathVariable Long documentId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" downloadDocument =");

		String fileName = commonService.fetchWorkFileNameLOI(documentId);
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

	/*
	 * @RequestMapping(value = "/contractors", method = RequestMethod.GET) public
	 * Long getContractorCount(@RequestParam Long workId) { return
	 * commonService.countContractorsByWorkId(workId); }
	 */

	// Method to fetch the list of work tender end dates.
	@RequestMapping(value = "/fetchWorkTenderEndDate", method = RequestMethod.GET)
	public List<WorkTenderBean> getWorkTenderEndDate(HttpServletRequest request) {
		logger.info("call==============");
		return commonService.getWorkTenderEndDate();

	}

	// Method to fetch the most recent financial year.
	@RequestMapping(value = "/getFinancialYear", method = RequestMethod.GET)
	public WorkReportBean getFinancialYear() {
		String financialYear = financialYearRepository.findMostRecentFinancialYear();
		WorkReportBean bean = new WorkReportBean();
		bean.setFinancialYear(financialYear);
		return bean;
	}

	// Method to fetch the list of ongoing works with AA issued status, applying
	// filters and pagination.
	@RequestMapping(value = "/fetchWorksAaIssuedList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String fetchWorksAaIssuedList(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching OngoingWorks List", user.getUsername(), user.getAuthorities());

		String scheme = request.getParameter("scheme");
		String workType = request.getParameter("workType");

		String financialYear = request.getParameter("financialYear");
		String implementationAgency = request.getParameter("implementationAgency");
		String blockId = request.getParameter("blockId");
		String divisionId = request.getParameter("divisionId");
		String districtId = request.getParameter("districtId");
		String workStatus = request.getParameter("workStatus");
		String workStatusId = request.getParameter("workStatusId");
		String workSubTypeId = request.getParameter("workSubTypeId");
		String workPriorityId = request.getParameter("workPriorityId");
		String financialHeadId = request.getParameter("financialHeadId");
		String vidhanSabhaId = request.getParameter("vidhanSabhaId");
		String sSortCol = request.getParameter("iSortCol_0");
		String sSortDir = request.getParameter("sSortDir_0");
		String sColName = request.getParameter("mDataProp_" + sSortCol);

		// Fetch the page number from client
		Integer pageNumber = 0;

		// Fetch search parameter
		String searchParameterWorkName = request.getParameter("searchBoxVal");
		// String searchParameterAsNo = request.getParameter("searchBoxValAsNo");
		String searchParameterWorkNo = request.getParameter("searchBoxValWorkNo");

		// String searchByDivision= request.getParameter("searchByDivision");

		String searchByDivision = "0";
		// Fetch Page display length
		Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));

		if (null != request.getParameter("iDisplayStart")) {
			pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart")) / pageDisplayLength);
		}

		Sort sort = null;
		if (sColName != null) {
			if (StringUtils.equals("asc", sSortDir)) {
				sort = Sort.by(Direction.ASC, sColName);
			} else {
				sort = Sort.by(Direction.DESC, sColName);
			}
		} else {
			sort = Sort.by(Direction.DESC, "id");// default sorting
		}

		Pageable pageable = PageRequest.of(pageNumber, pageDisplayLength, sort);

		WorkJson workJson = commonService.fetchWorksAaIssuedList(pageable,
				!StringUtils.isEmpty(searchParameterWorkNo) ? searchParameterWorkNo : null,
				!StringUtils.isEmpty(searchParameterWorkName) ? searchParameterWorkName : null,
				!StringUtils.isEmpty(scheme) ? scheme : null, !StringUtils.isEmpty(workType) ? workType : null,
				!StringUtils.isEmpty(financialYear) ? financialYear : null,
				!StringUtils.isEmpty(implementationAgency) ? implementationAgency : null,
				!StringUtils.isEmpty(blockId) ? blockId : null, !StringUtils.isEmpty(workStatus) ? workStatus : null,
				!StringUtils.isEmpty(districtId) ? districtId : null,
				!StringUtils.isEmpty(divisionId) ? divisionId : null,
				!StringUtils.isEmpty(searchByDivision) ? searchByDivision : null,
				!StringUtils.isEmpty(workSubTypeId) ? workSubTypeId : null,
				!StringUtils.isEmpty(workStatusId) ? workStatusId : null,
				!StringUtils.isEmpty(workPriorityId) ? workPriorityId : null,
				!StringUtils.isEmpty(financialHeadId) ? financialHeadId : null,
				!StringUtils.isEmpty(vidhanSabhaId) ? vidhanSabhaId : null);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(workJson);

		return json;
	}

	// Method to fetch the list of users associated with agencies with filtering and
	// pagination.
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_SU', 'ROLE_AGENCY_ADMIN', 'ROLE_SAU')")
	@RequestMapping(value = "/fetchUserAgencyList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String fetchUserAgencyList(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching User List", user.getUsername(), user.getAuthorities());
		String searchBoxVal = request.getParameter("searchBoxVal");
		/* String roleCode = request.getParameter("roleCode"); */
		String status = request.getParameter("status");
		String username = request.getParameter("username");

		String emailId = request.getParameter("emailId");
		String sSortCol = request.getParameter("iSortCol_0");
		String sSortDir = request.getParameter("sSortDir_0");
		String sColName = request.getParameter("mDataProp_" + sSortCol);

		// Fetch the page number from client
		Integer pageNumber = 0;

		/*
		 * // Fetch search parameter String searchParameter =
		 * request.getParameter("sSearch");
		 */

		// Fetch Page display length
		Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));

		if (null != request.getParameter("iDisplayStart")) {
			pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart")) / pageDisplayLength);
		}

		Sort sort = null;
		if (sColName != null) {
			if (StringUtils.equals("asc", sSortDir)) {
				sort = Sort.by(Direction.ASC, sColName);
			} else {
				sort = Sort.by(Direction.DESC, sColName);
			}
		} else {
			sort = Sort.by(Direction.DESC, "id");// default sorting
		}

		Pageable pageable = PageRequest.of(pageNumber, pageDisplayLength, sort);

		UserJson userJson = superAdminService.getAllAgencyusers(pageable, searchBoxVal, status, username, emailId);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(userJson);

		return json;
	}

	// Method to fetch the state ID of MP based on the user's agency.
	@RequestMapping(value = "fetchStateIdOfMP", method = RequestMethod.GET)
	public Long fetchStateIdOfMP(HttpServletRequest request) {
		logger.info("Call Common Controller ==================== ");
		return commonService.fetchAgencyByuserId();
	}

	// Method to fetch the construction agency details by ID.
	@RequestMapping(value = "fetchConstructionAgencyById", method = RequestMethod.GET)
	public ImplAgencyBean fetchConstructionAgencyById(HttpServletRequest request) {
		return commonService.fetchConstructionAgencyById();

	}

	// Method to download a file based on the provided document ID.
	@RequestMapping(value = "/downloadFile/{documentId}", method = RequestMethod.GET)
	public void downloadFile(@PathVariable Long documentId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String fileName = commonService.fetchDownloadFileName(documentId);
//	    String fileName = commonService.fetchDownloadFileName(documentId);

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

	// Method to fetch work progress details by document ID.
	@RequestMapping(value = "fetchWorkProgressDocumetnId/{documentId}", method = RequestMethod.GET)
	public WorkProgressBean fetchWorkProgressDocumetnId(@PathVariable("documentId") Long id, HttpServletRequest request)
			throws ParseException {

		user = DMSUtil.getUserDetail();
		String role = DMSUtil.getUserRole(user);
		logger.info("User - {}, Role - {} - Fetching TSAS Work data", user.getUsername(), user.getAuthorities());

		WorkProgressBean workProgressBean = commonService.fetchWorkProgressDocumetnId(id);

		return workProgressBean;

	}

	// Method to fetch the list of months
	@RequestMapping(value = "fetchMonths", method = RequestMethod.GET)
	public List<MonthBean> fetchMonths(HttpServletRequest request) {
		return commonService.fetchmonths();
	}

	// Method to add or update TSAS work data status, including cancellation.
	@PreAuthorize("hasRole('ROLE_DM') and @workAuthorization.canAccessWork(#p0.workId)")
	@RequestMapping(value = "/addTSASWorkDataStatus", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	@ResponseBody
	public ResponseObject addTSASWorkDataStatus(TSASWorkBean tsasWorkBean, HttpServletRequest request)
			throws Exception {

		// System.err.println("Work cencel sataus======" +
		// tsasWorkBean.getTsAsSataus());

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Adding Work data", user.getUsername(), user.getAuthorities());
		ResponseObject response = null;

		try {
			String remoteIpAddr = request.getHeader("X-Forwarded-For");

			logger.info("Header...." + remoteIpAddr);

			response = commonService.addTSASWorkDataCancelStatus(tsasWorkBean);
			if (response != null) {
				response.setSuccessMessage("Work saved successfully!");
				logger.info("User - {}, Role - {} - Work saved successfully!", user.getUsername(),
						user.getAuthorities());
			} else {
				response = new ResponseObject();
				String errorMsg = DMSConstants.ERROR_SAVING_DATA;
				response.setErrorMessage(errorMsg);
				logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
			}
		} catch (Exception e) {
			String errorMsg = e.getMessage();
			response = new ResponseObject();
			response.setErrorMessage(errorMsg);
			logger.error("User - " + user.getUsername() + ", Role - " + user.getAuthorities() + " - " + errorMsg);
		}
		return response;
	}

	// Method to add revised work data status for a TSAS work entry.
	@PreAuthorize("hasRole('ROLE_DM') and @workAuthorization.canAccessWork(#p0.workId)")
	@RequestMapping(value = "/addTSReviseWorkDataStatus", method = RequestMethod.POST, consumes = {
			"multipart/form-data" })
	@ResponseBody
	public ResponseObject addTSReviseWorkDataStatus(TSASReviseWorkBean tsasReviseWorkBean, HttpServletRequest request)
			throws Exception {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Adding Work data", user.getUsername(), user.getAuthorities());
		ResponseObject response = null;

		try {

			String remoteIpAddr = request.getHeader("X-Forwarded-For");

			logger.info("Header...." + remoteIpAddr);

			response = commonService.addTSReviseWorkDataStatus(tsasReviseWorkBean);
			if (response != null) {
				response.setSuccessMessage("Revised Data saved successfully!");
				logger.info("User - {}, Role - {} - Work saved successfully!", user.getUsername(),
						user.getAuthorities());
			} else {
				response = new ResponseObject();
				String errorMsg = DMSConstants.ERROR_SAVING_DATA;
				response.setErrorMessage(errorMsg);
				logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
			}
		} catch (Exception e) {
			String errorMsg = e.getMessage();
			response = new ResponseObject();
			response.setErrorMessage(errorMsg);
			logger.error("User - " + user.getUsername() + ", Role - " + user.getAuthorities() + " - " + errorMsg);
		}
		return response;
	}

	// Method to add AS revise work data status with error handling and logging.
	@PreAuthorize("hasRole('ROLE_DM') and @workAuthorization.canAccessWork(#p0.workId)")
	@RequestMapping(value = "/addASReviseWorkDataStatus", method = RequestMethod.POST, consumes = {
			"multipart/form-data" })
	@ResponseBody
	public ResponseObject addASReviseWorkDataStatus(TSASReviseWorkBean tsasReviseWorkBean, HttpServletRequest request)
			throws Exception {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Adding Work data", user.getUsername(), user.getAuthorities());
		ResponseObject response = null;

		try {

			String remoteIpAddr = request.getHeader("X-Forwarded-For");

			logger.info("Header...." + remoteIpAddr);

			response = commonService.addASReviseWorkDataStatus(tsasReviseWorkBean);
			if (response != null) {
				response.setSuccessMessage("Revised Data saved successfully!");
				logger.info("User - {}, Role - {} - Work saved successfully!", user.getUsername(),
						user.getAuthorities());
			} else {
				response = new ResponseObject();
				String errorMsg = DMSConstants.ERROR_SAVING_DATA;
				response.setErrorMessage(errorMsg);
				logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
			}
		} catch (Exception e) {
			String errorMsg = e.getMessage();
			response = new ResponseObject();
			response.setErrorMessage(errorMsg);
			logger.error("User - " + user.getUsername() + ", Role - " + user.getAuthorities() + " - " + errorMsg);
		}
		return response;
	}

	// Method to fetch the list of districts for MP.
	@RequestMapping(value = "fetchDistrictsMP", method = RequestMethod.GET)
	public List<DistrictBean> fetchDistrictsMP(HttpServletRequest request) {
		return commonService.fetchDistrictsMP();
	}

	// Methods to fetch blocks by district using two different services based on
	// district ID.
	@RequestMapping(value = "fetchBlocksByDistirct/{dId}", method = RequestMethod.GET)
	public List<BlockBean> fetchBlocksByDistirct(@PathVariable String dId, HttpServletRequest request) {
		return commonService.fetchBlocksByDistirct(dId);
	}

	// Methods to fetch blocks by district using two different services based on
	// district ID.
	@RequestMapping(value = "fetchBlocksByDistirct2/{dId}", method = RequestMethod.GET)
	public List<BlockBean> fetchBlocksByDistirct2(@PathVariable String dId, HttpServletRequest request) {
		return commonService.fetchBlocksByDistirct2(dId);
	}

	// Method to fetch blocks by district with search and pagination functionality.
	@RequestMapping(value = "/fetchBlocksByDistirctjson", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String fetchBlocksByDistirctjson(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching OngoingWorks List", user.getUsername(), user.getAuthorities());
		String searchBoxVal = request.getParameter("searchBoxVal");
		String districtId = request.getParameter("districtId");
		String sSortCol = request.getParameter("iSortCol_0");
		String sSortDir = request.getParameter("sSortDir_0");
		String sColName = request.getParameter("mDataProp_" + sSortCol);
		logger.info(districtId + "" + searchBoxVal);
		// Fetch the page number from client
		Integer pageNumber = 0;
		logger.info(searchBoxVal + "i amas dnit " + districtId);
		// Fetch search parameter
		String searchParameterWorkName = request.getParameter("searchBoxVal");
		// String searchParameterAsNo = request.getParameter("searchBoxValAsNo");
		String searchParameterWorkNo = request.getParameter("searchBoxValWorkNo");

		// String searchByDivision= request.getParameter("searchByDivision");

		String searchByDivision = "0";
		// Fetch Page display length
		Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));

		if (null != request.getParameter("iDisplayStart")) {
			pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart")) / pageDisplayLength);
		}

		Sort sort = null;
		if (sColName != null) {
			if (StringUtils.equals("asc", sSortDir)) {
				sort = Sort.by(Direction.ASC, sColName);
			} else {
				sort = Sort.by(Direction.DESC, sColName);
			}
		} else {
			sort = Sort.by(Direction.ASC, "blockId");// default sorting
		}

		Pageable pageable = PageRequest.of(pageNumber, pageDisplayLength, sort);

		BlockJson blockJson = commonService.fetchBlockForDistrict(pageable, searchBoxVal, districtId);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(blockJson);

		return json;
	}

	// Method to fetch ongoing works for generating reports with filters and
	// pagination.
	@RequestMapping(value = "/fetchWorkForReport", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String fetchWorkForReport(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching OngoingWorks List", user.getUsername(), user.getAuthorities());

		String scheme = request.getParameter("scheme");
		String workType = request.getParameter("workType1");
		String financialYear = request.getParameter("financialYear1");
		String implementationAgency = request.getParameter("implementationAgency");
		String blockId = request.getParameter("blockId");
		String department = request.getParameter("department");
		String divisionId = request.getParameter("divisionId");
		String districtId = request.getParameter("districtId");
		String workStatus = request.getParameter("workStatus");
		String workStatusId = request.getParameter("workStatusId");
		String workSubTypeId = request.getParameter("workSubTypeId");
		String workPriorityId = request.getParameter("workPriorityId");
		String financialHeadId = request.getParameter("financialHeadId1");
		String vidhanSabhaId = request.getParameter("vidhanSabhaId1");
		String workNameFilter = request.getParameter("workNameFilter");
		// Also check for DataTables global search parameter (keyword)
		if ((workNameFilter == null || workNameFilter.isEmpty()) && request.getParameter("keyword") != null) {
			workNameFilter = request.getParameter("keyword");
		}
		String departmentRemark = request.getParameter("departmentRemark");
		String sSortCol = request.getParameter("iSortCol_0");
		String sSortDir = request.getParameter("sSortDir_0");
		String sColName = request.getParameter("mDataProp_" + sSortCol);
		
		
		List<Long> fyList = convertToList(financialYear);
		List<Long> workTypeList = convertToList(workType);
		List<Long> agencyList = convertToList(implementationAgency);
		List<Long> statusList = convertToList(workStatusId);
		List<Long> priorityList = convertToList(workPriorityId);
		List<Long> headList = convertToList(financialHeadId);
		List<Long> vsList = convertToList(vidhanSabhaId);

		// Fetch the page number from client
		Integer pageNumber = 0;

		// Fetch search parameter
		String searchParameterWorkName = request.getParameter("searchBoxVal");
		// String searchParameterAsNo = request.getParameter("searchBoxValAsNo");
		String searchParameterWorkNo = request.getParameter("searchBoxValWorkNo");

		// String searchByDivision= request.getParameter("searchByDivision");
		String searchByDivision = "0";
		// Fetch Page display length
		Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));

		if (null != request.getParameter("iDisplayStart")) {
			pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart")) / pageDisplayLength);
		}

		Sort sort = null;
		if (sColName != null) {
			if (StringUtils.equals("asc", sSortDir)) {
				sort = Sort.by(Direction.ASC, sColName);
			} else {
				sort = Sort.by(Direction.DESC, sColName);
			}
		} else {
			sort = Sort.by(Direction.DESC, "id");// default sorting
		}

		Pageable pageable = PageRequest.of(pageNumber, pageDisplayLength, sort);

		WorkJson workJson = commonService.fetchWorkForReport(pageable,
				!StringUtils.isEmpty(searchParameterWorkNo) ? searchParameterWorkNo : null,
				!StringUtils.isEmpty(searchParameterWorkName) ? searchParameterWorkName : null,
				!StringUtils.isEmpty(scheme) ? scheme : null, workTypeList,
						fyList,
				!StringUtils.isEmpty(department) ? department : null,
						agencyList,
				!StringUtils.isEmpty(blockId) ? blockId : null, !StringUtils.isEmpty(workStatus) ? workStatus : null,
				!StringUtils.isEmpty(districtId) ? districtId : null,
				!StringUtils.isEmpty(divisionId) ? divisionId : null,
				!StringUtils.isEmpty(searchByDivision) ? searchByDivision : null,
				!StringUtils.isEmpty(workSubTypeId) ? workSubTypeId : null,
						statusList,
						priorityList,
						headList,
						vsList,
						!StringUtils.isEmpty(workNameFilter) ? workNameFilter : null,
						!StringUtils.isEmpty(departmentRemark) ? departmentRemark : null);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(workJson);

		return json;
	}

	// Method to fetch the list of all departments.
	@RequestMapping(value = "fetchAllDepartment", method = RequestMethod.GET)
	public List<departmentbean> fetchAllDepartment(HttpServletRequest request) {
		return commonService.fetchAllDepartment();
	}

	// Method to assign a user to a work based on userId and workId.
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM') and @workAuthorization.canAssignUserToWork(T(Long).valueOf(#p0), T(Long).valueOf(#p1))")
	@RequestMapping(value = "assignUserToWork/{userId}/{workId}", method = RequestMethod.POST)
	public ResponseObject assignUserToWork(@PathVariable("userId") String userId, @PathVariable("workId") String workId,
			HttpServletRequest request) {

		ResponseObject response = new ResponseObject();
		String errorMsg = commonService.assignUserToWork(Long.parseLong(userId), Long.parseLong(workId));

		/*
		 * String fullPath = request.getScheme() + "://"+
		 * getApplicationDeploymentServerName() + ":" + request.getServerPort() +
		 * contextPath;
		 * 
		 * String verifyServiceUrl = fullPath + "/" +
		 * DMSConstants.VERIFY_EMAIL_SERVICE_NAME;
		 */

		if (errorMsg == null) {
			response.setErrorMessage("User Not Assign");

		} else {
			// notificationService.sendUserRegistrationEmail(userBean);

			response.setSuccessMessage(errorMsg);

		}
		return response;
	}

	@RequestMapping(value = "/fetchWorkStatusByWorkID/{workId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String fetchWorkStatusByWorkID(HttpServletRequest request, @PathVariable Long workId) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching Progress Images List", user.getUsername(), user.getAuthorities());

		String totalExpensess = request.getParameter("totalExpensess");

		String expensessUptoMarch = request.getParameter("expensessUptoMarch");
		String expensessCurrentFy = request.getParameter("expensessCurrentFy");

		String year = request.getParameter("year");

		String createdDate = request.getParameter("createdDate");
//		Long yearNew =Long.parseLong(String.valueOf(year));

		String sSortCol = request.getParameter("iSortCol_0");
		String sSortDir = request.getParameter("sSortDir_0");
		String sColName = request.getParameter("mDataProp_" + sSortCol);

		// Fetch the page number from client
		Integer pageNumber = 0;

		// Fetch search parameter
		String searchParameterWorkName = request.getParameter("searchBoxVal");

		// String searchParameterWorkNo = request.getParameter("searchBoxValWorkNo");

		String searchByDivision = "0";
		// Fetch Page display length
		Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));

		if (null != request.getParameter("iDisplayStart")) {
			pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart")) / pageDisplayLength);
		}

		Sort sort = null;
		if (sColName != null) {
			if (StringUtils.equals("asc", sSortDir)) {
				sort = Sort.by(Direction.ASC, sColName);
			} else {
				sort = Sort.by(Direction.DESC, sColName);
			}
		} else {
			sort = Sort.by(Direction.DESC, "year");// default sorting
		}

		Pageable pageable = PageRequest.of(pageNumber, pageDisplayLength, sort);

		WorkStatusJson WorkstatusDataJson = commonService.fetchWorkStatusDataList(workId);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(WorkstatusDataJson);

		return json;
	}

	@RequestMapping(value = "/fetchWorkStatusByWorkIDcc/{workId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String fetchWorkStatusByWorkIDcc(HttpServletRequest request, @PathVariable Long workId) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching Progress Images List", user.getUsername(), user.getAuthorities());

		String totalExpensess = request.getParameter("totalExpensess");

		String expensessUptoMarch = request.getParameter("expensessUptoMarch");
		String expensessCurrentFy = request.getParameter("expensessCurrentFy");

		String year = request.getParameter("year");

		String createdDate = request.getParameter("createdDate");
//		Long yearNew =Long.parseLong(String.valueOf(year));

		String sSortCol = request.getParameter("iSortCol_0");
		String sSortDir = request.getParameter("sSortDir_0");
		String sColName = request.getParameter("mDataProp_" + sSortCol);

		// Fetch the page number from client
		Integer pageNumber = 0;

		// Fetch search parameter
		String searchParameterWorkName = request.getParameter("searchBoxVal");

		// String searchParameterWorkNo = request.getParameter("searchBoxValWorkNo");

		String searchByDivision = "0";
		// Fetch Page display length
		Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));

		if (null != request.getParameter("iDisplayStart")) {
			pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart")) / pageDisplayLength);
		}

		Sort sort = null;
		if (sColName != null) {
			if (StringUtils.equals("asc", sSortDir)) {
				sort = Sort.by(Direction.ASC, sColName);
			} else {
				sort = Sort.by(Direction.DESC, sColName);
			}
		} else {
			sort = Sort.by(Direction.DESC, "year");// default sorting
		}

		Pageable pageable = PageRequest.of(pageNumber, pageDisplayLength, sort);

		WorkStatusJson WorkstatusDataJson = commonService.fetchWorkStatusDataListCC(workId);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(WorkstatusDataJson);

		return json;
	}

	@RequestMapping(value = "getGeoTaggingForWork/{WorkId}", method = RequestMethod.GET)
	public GeoTaggingBean getGeoTaggingForWork(@PathVariable Long WorkId, HttpServletRequest request) {

		logger.info("called");

		GeoTaggingBean geoTaggingForWork = commonService.getGeoTaggingForWork(WorkId);
		return geoTaggingForWork;

	}

	@RequestMapping(value = "/fetchImagesByDateAndWorkId/{workId}/{createdDate}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public List<DocumentUploadWorkProgressBean> fetchImagesByDateAndWorkId(HttpServletRequest request,
			@PathVariable Long workId, @PathVariable Date createdDate) {
		String scheme = request.getScheme();     // http
	    String serverName = request.getServerName(); // localhost
	    String baseUrl = scheme + "://" + serverName;
		List<DocumentUploadWorkProgressBean> workImagelist = commonService.fetchProgressImagesGroupList(workId,
				createdDate, baseUrl);

		return workImagelist;
	}

	@Value("${document.root}")
	private String documentRootPath;
	@Value("${document.workprogress}")
	private String workWorkProgressDocumentPath;

	@GetMapping("/downloadDocumentsZip/{workId}")
	public void downloadDocumentsZip(@PathVariable Long workId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("Downloading documents zip for workId: {}", workId);

		// Fetch the list of documents based on the workId
		List<DocumentUploadWorkProgressBean> documents = commonService.fetchImagesGroupListforzipfile(workId);

		if (documents.isEmpty()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "No documents found for the specified workId.");
			return;
		}

		// Set the response headers for ZIP file download
		response.setContentType("application/zip");
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=documents.zip");

		// Create a ZipOutputStream to stream the ZIP content to the response
		try (ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream())) {
			// Loop through each document and add it to the zip
			for (DocumentUploadWorkProgressBean document : documents) {
				String fileName = document.getDocumentName();
				String fileWithFullPath = documentRootPath + workWorkProgressDocumentPath + fileName; // Full path to
																										// the file

				// Create a File object for the document
				File file = new File(fileWithFullPath);

				if (file.exists()) {
					try (FileInputStream fis = new FileInputStream(file)) {
						// Create a ZipEntry for each document and add it to the zip
						ZipEntry zipEntry = new ZipEntry(fileName); // You can modify the entry name as needed
						zipOut.putNextEntry(zipEntry);

						// Write the file content into the zip output stream
						byte[] buffer = new byte[1024];
						int length;
						while ((length = fis.read(buffer)) >= 0) {
							zipOut.write(buffer, 0, length);
						}

						// Close the current zip entry
						zipOut.closeEntry();
					} catch (IOException e) {
						logger.error("Error while processing file: {}", fileName, e);
						response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
								"Error while processing file.");
						return;
					}
				} else {
					logger.error("File not found: {}", fileWithFullPath);
					response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found: " + fileName);
					return;
				}
			}

			// Flush the output stream to ensure all data is written
			zipOut.flush();
		} catch (IOException e) {
			logger.error("Error while generating zip file", e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while generating zip file.");
		}
	}

	// fetch all data in the Excel Expoert report by Sumit

	@Autowired
	private CommonServiceImpl commonServiceImpl;

//	@RequestMapping(value = "manageOngoingWorks/downloadAllWorksExcel", method = RequestMethod.GET)
//	public void downloadAllWorksExcel(@RequestParam("workId") Long workId,HttpServletResponse response) {
//		System.err.println("workId------------- " + workId);
//		try (Workbook workbook = new XSSFWorkbook()) {
//			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//			response.setHeader("Content-Disposition", "attachment; filename=work_Data.xlsx");
//
//			List<Work> works = workRepository.findById(workId);
//
//			Sheet sheet = workbook.createSheet("Works");
//
//			String[] headers = { "Index", "Work Unique Id", "Work Name", "FY of Sanction", "Executive Agency", "AS Date", "Total AS Amount (In Lakhs)", "Work Type",  "District Name",
//					 "Work Sub Type Name", "Work Status", "Head", "Scheme", "Division Name",
//					"Year Of Administrative Approval", 
//					 "Work Order Date", "Time Line In Months",
//					"Total Expenditure Till Date", "Level Of Completion", "Contractor Name", "SOR", "Tender Percentage",
//					"Above/Below" };
//
//			// Header row
//			Row headerRow = sheet.createRow(0);
//			for (int i = 0; i < headers.length; i++) {
//				headerRow.createCell(i).setCellValue(headers[i]);
//			}
//
//			int rowNum = 1;
//			int index = 1;
//			for (Work work : works) {
//				WorkBean bean = commonServiceImpl.convertWorkEntityToBeans1(work, null); // ✅ Convert entity to bean
//				// System.err.println("bean.getImplementationAgency()===== " +
//				// bean.getImplementationAgency());
//				Row row = sheet.createRow(rowNum++);
//				row.createCell(0).setCellValue(index++);
//				row.createCell(1).setCellValue(nullToDash(bean.getWorkNo()));
//				row.createCell(2).setCellValue(nullToDash(bean.getWorkName()));
//				row.createCell(3).setCellValue(nullToDash(bean.getFinancialYearName()));
//				row.createCell(4).setCellValue(nullToDash(bean.getImplementationAgencyName()));
//				row.createCell(5).setCellValue(nullToDash(bean.getDateOfAdministrativeApproval()));
//				row.createCell(6).setCellValue(nullToDash(bean.getAmountOfAdministrativeApproval()));
////				row.createCell(3).setCellValue(nullToDash(bean.getWorkTypeName()));
////				row.createCell(5).setCellValue(nullToDash(bean.getDistrictName()));
////				row.createCell(7).setCellValue(nullToDash(bean.getWorkSubTypeName()));
////				row.createCell(8).setCellValue(nullToDash(bean.getWorkStatusName()));
////				row.createCell(9).setCellValue(nullToDash(bean.getHead()));
////				row.createCell(10).setCellValue(nullToDash(bean.getScheme()));
////				row.createCell(11).setCellValue(nullToDash(bean.getDivisionName()));
////				row.createCell(12).setCellValue(nullToDash(bean.getYearOfAdministrativeApproval()));
////				
////				row.createCell(15).setCellValue(nullToDash(bean.getWorkOrderDate()));
////				row.createCell(16).setCellValue(nullToDash(bean.getTimeLineInMonths()));
////				row.createCell(17).setCellValue(nullToDash(bean.getTotalExpeditureTillDate()));
////				row.createCell(18).setCellValue(nullToDash(bean.getLevelOfCompletion()));
////				row.createCell(19).setCellValue(nullToDash(bean.getContractorName()));
////				row.createCell(20).setCellValue(nullToDash(bean.getSor()));
////				row.createCell(21).setCellValue(nullToDash(bean.getTenderParcentage()));
////				row.createCell(22).setCellValue(nullToDash(bean.getAboveBelow()));
//			}
//
//			ServletOutputStream outputStream = response.getOutputStream();
//			workbook.write(outputStream);
//			outputStream.flush(); // ✅ Important
//		} catch (IOException e) {
//			e.printStackTrace();
//			throw new RuntimeException("Failed to generate Excel file: " + e.getMessage());
//		}
//	}
	
	private byte[] downloadImageByDocumentId(Long documentId) {
	    try {
	        String filePath =
	            commonService.fetchDownloadDocumentWSPro(documentId);

	        if (filePath == null) return null;

	        File file = new File(filePath);
	        if (!file.exists() || file.length() == 0) return null;

	        return Files.readAllBytes(file.toPath());
	    } catch (Exception e) {
	        return null; // ❗ Excel kabhi fail nahi hoga
	    }
	}



	private void addImageToCell(
	        Workbook workbook,
	        Sheet sheet,
	        Drawing<?> drawing,
	        CreationHelper helper,
	        byte[] imageBytes,
	        int rowNum,
	        int colNum
	) {
	    try {
	        if (imageBytes == null || imageBytes.length == 0) return;

	        int pictureIdx = workbook.addPicture(
	            imageBytes,
	            Workbook.PICTURE_TYPE_JPEG
	        );

	        ClientAnchor anchor = helper.createClientAnchor();
	        anchor.setCol1(colNum);
	        anchor.setRow1(rowNum);
	        anchor.setCol2(colNum + 1);
	        anchor.setRow2(rowNum + 1);

	        drawing.createPicture(anchor, pictureIdx);

	        sheet.getRow(rowNum).setHeightInPoints(120);
	        sheet.setColumnWidth(colNum, 35 * 256);

	    } catch (Exception e) {
	    	 logger.error("Error occurred while processing request" + e.getMessage());
	    }
	}

	
	
	@RequestMapping(
	        value = "manageOngoingWorks/downloadAllWorksExcel",
	        method = RequestMethod.POST
	)
	public void exportWorkExcel(
	        @RequestParam("workIds") List<Long> workIds,
	        HttpServletResponse response) {

		if (workIds == null || workIds.isEmpty()) {
		    throw new RuntimeException("No valid work IDs received");
		}
		for (Long long1 : workIds) {
		}

		
	    response.setContentType(
	        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
	    );
	    response.setHeader(
	        "Content-Disposition",
	        "attachment; filename=work_report.xlsx"
	    );

	    Workbook workbook = new XSSFWorkbook();

	    try {

	    	 List<Work> works = (List<Work>) workRepository.findAllById(workIds);

	    	 works.sort((a, b) -> b.getId().compareTo(a.getId()));
	        Sheet sheet = workbook.createSheet("Works");

	        String[] headers = {
	            "S.No.", "Work Unique Id", "Work Name", "FY of Sanction",
	            "Executive Agency", "AS Date", "Total AS Amount (In Lakhs)",
	            "Fund 1 Cost (Lakh)", "Fund 2 Cost (Lakh)", "Fund 3 Cost (Lakh)",
	            "Agreement Date", "Completion Date as per Agreement",
	            "Fund 1 Exp (Lakh)", "Fund 2 Exp (Lakh)", "Fund 3 Exp (Lakh)",
	            "Total Expenditure Till Date", "Physical Status",
	            "Level Of Completion", "Departmental Remarks", "DM Remarks",
	            "Last Photo", "Second Last Photo"
	        };

	        Row headerRow = sheet.createRow(0);
	        for (int i = 0; i < headers.length; i++) {
	            headerRow.createCell(i).setCellValue(headers[i]);
	        }

	        CreationHelper helper = workbook.getCreationHelper();
	        Drawing<?> drawing = sheet.createDrawingPatriarch();

	        sheet.setColumnWidth(20, 25 * 256);
	        sheet.setColumnWidth(21, 25 * 256);

	        int rowNum = 1;
	        int index = 1;

	        for (Work work : works) {

	            WorkBean bean =
	                commonServiceImpl.convertWorkEntityToBeans1(work, null);

	            Row row = sheet.createRow(rowNum++);

	            row.createCell(0).setCellValue(index++);
	            row.createCell(1).setCellValue(nullToDash(bean.getWorkNo()));
	            row.createCell(2).setCellValue(nullToDash(bean.getWorkName()));
	            row.createCell(3).setCellValue(nullToDash(bean.getFinancialYearName()));
	            row.createCell(4).setCellValue(nullToDash(bean.getImplementationAgencyName()));
	            row.createCell(5).setCellValue(nullToDash(bean.getDateOfAdministrativeApproval()));
	            row.createCell(6).setCellValue(
	                bean.getAmountOfAdministrativeApproval() != null
	                    ? bean.getAmountOfAdministrativeApproval().toString()
	                    : "-"
	            );

	            row.createCell(7).setCellValue(bean.getFund1());
	            row.createCell(8).setCellValue(bean.getFund2());
	            row.createCell(9).setCellValue(bean.getFund3());
	            row.createCell(10).setCellValue(nullToDash(bean.getAgreementDate()));
	            row.createCell(11).setCellValue(nullToDash(bean.getDateOfCompletion()));
	            row.createCell(12).setCellValue(bean.getFund1Exp());
	            row.createCell(13).setCellValue(bean.getFund2Exp());
	            row.createCell(14).setCellValue(bean.getFund3Exp());
	            row.createCell(15).setCellValue(nullToDash(bean.getTotalExpeditureTillDate()));
	            row.createCell(16).setCellValue(nullToDash(bean.getWorkStatusName()));
	            row.createCell(17).setCellValue(nullToDash(bean.getLevelOfCompletion()));
	            row.createCell(18).setCellValue(nullToDash(bean.getDepartmentRemarks()));
	            row.createCell(19).setCellValue(nullToDash(bean.getDmRemakrs()));

	            // ===== LAST PHOTO =====
	            if (bean.getLastPhotoDocumentId() != null) {
	                byte[] imageBytes =
	                    downloadImageByDocumentId(bean.getLastPhotoDocumentId());

	                if (imageBytes != null && imageBytes.length > 0) {
	                    addImageToCell(
	                        workbook, sheet, drawing, helper,
	                        imageBytes, row.getRowNum(), 20
	                    );
	                }
	            }

	            // ===== SECOND LAST PHOTO =====
	            if (bean.getSecondLastPhotoDocumentId() != null) {
	                byte[] imageBytes =
	                    downloadImageByDocumentId(bean.getSecondLastPhotoDocumentId());

	                if (imageBytes != null && imageBytes.length > 0) {
	                    addImageToCell(
	                        workbook, sheet, drawing, helper,
	                        imageBytes, row.getRowNum(), 21
	                    );
	                }
	            }
	        }

	        ServletOutputStream out = response.getOutputStream();
	        workbook.write(out);
	        out.flush();

	    } catch (Exception e) {
	        logger.error("Excel generation error", e);
	        throw new RuntimeException("Failed to generate Excel file", e);
	    } finally {
	        try {
	            workbook.close();
	        } catch (Exception e) {
	            // ignore
	        }
	    }
	}


	
	
	
	
	
	
	

	private String nullToDash(Object value) {
		return value == null ? "-" : value.toString();
	}

	@RequestMapping(value = "manageOngoingWorks/downloadAllWorksPdf", method = RequestMethod.GET)
	public void downloadAllWorksPdf(HttpServletResponse response) {
		logger.info("call controller - PDF");

	    try {
	        response.setContentType("application/pdf");
	        response.setHeader("Content-Disposition", "attachment; filename=work_Data.pdf");

	        List<Work> works = workRepository.findAllActiveWorks();

	        Document document = new Document(PageSize.A4.rotate(), 36, 36, 70, 36); 
	        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
	        writer.setPageEvent(new PdfPageEventHelper() {
	            Font headerFont1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
	            Font headerFont2 = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
	            Font footerFont = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.ITALIC, BaseColor.GRAY);

	            PdfTemplate total;

	            @Override
	            public void onOpenDocument(PdfWriter writer, Document document) {
	                total = writer.getDirectContent().createTemplate(30, 16);
	            }

	            @Override
	            public void onEndPage(PdfWriter writer, Document document) {
	                PdfContentByte cb = writer.getDirectContent();
	                Rectangle pageSize = document.getPageSize();
	                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	                String dateStr = sdf.format(new Date());

	                // ✅ HEADER
	                ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
	                    new Phrase("Collectorate Office, District Anuppur", headerFont1),
	                    (pageSize.getLeft() + pageSize.getRight()) / 2,
	                    pageSize.getTop() - 30, 0);

	                ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
	                    new Phrase("Works Status Report", headerFont2),
	                    (pageSize.getLeft() + pageSize.getRight()) / 2,
	                    pageSize.getTop() - 45, 0);

	                // ✅ FOOTER LEFT
	                ColumnText.showTextAligned(cb, Element.ALIGN_LEFT,
	                    new Phrase("Work Report generated by Work Management System, District Anuppur", footerFont),
	                    document.leftMargin(), document.bottom() - 10, 0);

	                // ✅ FOOTER RIGHT (Date)
	                ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT,
	                    new Phrase("Date: " + dateStr, footerFont),
	                    document.right(), document.bottom() - 10, 0);

	                // ✅ FOOTER CENTER (Page X of Y)
	                String pageText = "Page " + writer.getPageNumber() + " of ";
	                float textBase = document.bottom() - 10;
	                float textSize = footerFont.getBaseFont().getWidthPoint(pageText, 8);
	                float centerPosition = (pageSize.getLeft() + pageSize.getRight()) / 2;

	                cb.beginText();
	                cb.setFontAndSize(footerFont.getBaseFont(), 8);
	                cb.setTextMatrix(centerPosition - textSize / 2, textBase);
	                cb.showText(pageText);
	                cb.endText();

	                // ✅ This will later be replaced with final page count
	                cb.addTemplate(total, centerPosition - textSize / 2 + textSize, textBase);
	            }

	            @Override
	            public void onCloseDocument(PdfWriter writer, Document document) {
	                total.beginText();
	                total.setFontAndSize(footerFont.getBaseFont(), 8);
	                total.setTextMatrix(0, 0);
	                total.showText(String.valueOf(writer.getPageNumber()));
	                total.endText();
	            }
	        });

	        document.open();

	        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
	        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8);
	        Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 7);

	     //   Paragraph title = new Paragraph("Work Report", titleFont);
	      //  title.setAlignment(Element.ALIGN_CENTER);
	       // document.add(title);
	        document.add(Chunk.NEWLINE);

	        String[] headers = {
	                "Index", "Work No", "Work Name", "Work Type", "Financial Year", "District Name",
	                "Implementation Agency", "Work Sub Type Name", "Work Status", "Division Name",
	                "Year Of Administrative Approval", "Date Of Administrative Approval",
	                "Amount Of Administrative Approval", "Work Order Date", "Time Line In Months",
	                "Total Expenditure Till Date", "Level Of Completion", "Contractor Name", "SOR",
	                "Tender Percentage", "Above/Below"
	        };

	        PdfPTable table = new PdfPTable(headers.length);
	        table.setWidthPercentage(100);

	        for (String header : headers) {
	            PdfPCell headerCell = new PdfPCell(new Phrase(header, headFont));
	            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            table.addCell(headerCell);
	        }

	        int index = 1;
	        for (Work work : works) {
	            WorkBean bean = commonServiceImpl.convertWorkEntityToBeans1(work, null);

	            table.addCell(new Phrase(String.valueOf(index++), bodyFont));
	            table.addCell(new Phrase(nullToDash(bean.getWorkNo()), bodyFont));
	            table.addCell(new Phrase(nullToDash(bean.getWorkName()), bodyFont));
	            table.addCell(new Phrase(nullToDash(bean.getWorkTypeName()), bodyFont));
	            table.addCell(new Phrase(nullToDash(bean.getFinancialYearName()), bodyFont));
	            table.addCell(new Phrase(nullToDash(bean.getDistrictName()), bodyFont));
	            table.addCell(new Phrase(nullToDash(bean.getImplementationAgencyName()), bodyFont));
	            table.addCell(new Phrase(nullToDash(bean.getWorkSubTypeName()), bodyFont));
	            table.addCell(new Phrase(nullToDash(bean.getWorkStatusName()), bodyFont));
	            table.addCell(new Phrase(nullToDash(bean.getDivisionName()), bodyFont));
	            table.addCell(new Phrase(nullToDash(bean.getYearOfAdministrativeApproval()), bodyFont));
	            table.addCell(new Phrase(nullToDash(bean.getDateOfAdministrativeApproval()), bodyFont));
	            table.addCell(new Phrase(nullToDash(bean.getAmountOfAdministrativeApproval()), bodyFont));
	            table.addCell(new Phrase(nullToDash(bean.getWorkOrderDate()), bodyFont));
	            table.addCell(new Phrase(nullToDash(bean.getTimeLineInMonths()), bodyFont));
	            table.addCell(new Phrase(nullToDash(bean.getTotalExpeditureTillDate()), bodyFont));
	            table.addCell(new Phrase(nullToDash(bean.getLevelOfCompletion()), bodyFont));
	            table.addCell(new Phrase(nullToDash(bean.getContractorName()), bodyFont));
	            table.addCell(new Phrase(nullToDash(bean.getSor()), bodyFont));
	            table.addCell(new Phrase(nullToDash(bean.getTenderParcentage()), bodyFont));
	            table.addCell(new Phrase(nullToDash(bean.getAboveBelow()), bodyFont));
	        }

	        document.add(table);
	        document.close();

	    } catch (Exception e) {
	    	logger.error("Error occurred while processing request" + e.getMessage());
	        throw new RuntimeException("Failed to generate PDF file: " + e.getMessage());
	    }
	}


	private String nullToDash(String value) {
		return value == null ? "-" : value;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(Long.class, new PropertyEditorSupport() {
	        @Override
	        public void setAsText(String text) {
	            if (text == null || text.trim().isEmpty()
	                    || "null".equalsIgnoreCase(text)
	                    || "undefined".equalsIgnoreCase(text)) {
	                setValue(null);
	            } else {
	                setValue(Long.parseLong(text));
	            }
	        }
	    });
	}

	
	@PreAuthorize("hasRole('ROLE_DM') and @workAuthorization.canAccessWork(#p0.workId)")
	@PostMapping( value="saveOrUpdate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	//@ResponseBody
	public ResponseObject saveOrUpdateDmRemarks( DmRemarksBean bean) {
	    ResponseObject response = new ResponseObject();

	    
	    String result = commonService.addOrUpdateDmRemark(bean);

	    if ("success".equals(result)) {
	        response.setSuccessMessage("DM Remark saved successfully!");
	    } else if (result.startsWith("error:")) {
	        response.setErrorMessage(result.substring(6).trim()); // remove "error:" part
	    } else {
	        response.setErrorMessage("Unexpected response: " + result);
	    }

	    return response;
	}
	
	
	@PreAuthorize("hasRole('ROLE_DEPARTMENT') and @workAuthorization.canAccessWork(#p0.workId)")
	@PostMapping( value="saveOrUpdateDepartment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	//@ResponseBody
	public ResponseObject saveOrUpdateDepartmentRemarks( DepartmentRemarksBean bean) {
	    ResponseObject response = new ResponseObject();

	    
	    String result = commonService.addOrUpdateDepartmentRemark(bean);

	    if ("success".equals(result)) {
	        response.setSuccessMessage("DM Remark saved successfully!");
	    } else if (result.startsWith("error:")) {
	        response.setErrorMessage(result.substring(6).trim()); // remove "error:" part
	    } else {
	        response.setErrorMessage("Unexpected response: " + result);
	    }

	    return response;
	}
	
	
	
	@GetMapping(value="getDMRemarks/{workid}")
	public List<DmRemarksBean> getAllDmRemarks(@PathVariable("workid") String workid,HttpServletRequest request ){
		
		return commonService.getAllRemarksByWorkID(Long.parseLong(workid));
	}

	@GetMapping(value="getDepartmentRemarks/{workid}")
	public List<DepartmentRemarksBean> getAllDepartmentRemarks(@PathVariable("workid") String workid,HttpServletRequest request ){
		
		return commonService.getAllDepartmentRemarksByWorkID(Long.parseLong(workid));
	}
	
	
	@PreAuthorize("hasRole('ROLE_DM') and @workAuthorization.canDeleteDmRemark(T(Long).valueOf(#p0))")
	@PostMapping(value = "deleteRemarks/{id}")
	public Boolean deleteRemarks(@PathVariable("id") String id,HttpServletRequest request) {
		return commonService.deleteRemarks(Long.parseLong(id));
	}
	
	@PreAuthorize("hasRole('ROLE_DEPARTMENT') and @workAuthorization.canDeleteDepartmentRemark(T(Long).valueOf(#p0))")
	@PostMapping(value = "deleteDepartmentRemarks/{id}")
	public Boolean deleteDepartmentRemarks(@PathVariable("id") String id,HttpServletRequest request) {
		return commonService.deleteDepartmentRemarks(Long.parseLong(id));
	}
	
	@GetMapping(value="getRemarksDetailsById/{id}")
	public DmRemarksBean  getRemakrsDetails(@PathVariable("id")String id , HttpServletRequest request) {
		 // ✅ SAFETY CHECK (NO LOGIC CHANGE)
	    if (id == null || id.equalsIgnoreCase("undefined") || id.equalsIgnoreCase("null")) {
	        return null; // or new DepartmentRemarksBean();
	    }
		return commonService.getRemakrsDetails(Long.parseLong(id));

	}
	
	@GetMapping(value="getDepartmentRemarksDetailsById/{id}")
	public DepartmentRemarksBean  getDepartmentRemarksDetailsById(@PathVariable("id") String id , HttpServletRequest request) {
		 // ✅ SAFETY CHECK (NO LOGIC CHANGE)
	    if (id == null || id.equalsIgnoreCase("undefined") || id.equalsIgnoreCase("null")) {
	        return null; // or new DepartmentRemarksBean();
	    }
		Long londId = Long.parseLong(id);
		return commonService.getDepartmentRemarksDetailsById(londId);

	}
	
	
	@RequestMapping(value = "fetchAreaOfficerRecord/{workid}",method = RequestMethod.GET)
	public List<UserBean>  fetchAreaOfficerRecords(@PathVariable("workid")Long workid,HttpServletRequest request)
	{
		return commonService.fetchAreaOfficerListByWorkId(workid);
	}


	@RequestMapping(value = "fetchWorkPriority", method = RequestMethod.GET)
	public List<WorkPriorityBean> fetchWorkPriority(HttpServletRequest request) {
		return commonService.fetchWorkPriority();
	}
	
	@RequestMapping(value = "fetchFinancialHead", method = RequestMethod.GET)
	public List<FinancialHeadBean> fetchFinancialHead(HttpServletRequest request) {
		return commonService.fetchFinancialHead();
	}
	
	
	@RequestMapping(value = "fetchVidhanSabha", method = RequestMethod.GET)
	public List<VidhanSabhaBean> fetchVidhanSabha(HttpServletRequest request) {
		return commonService.fetchVidhanSabha();
	}
	
	@RequestMapping(value = "fetchDmRemarksList", method = RequestMethod.GET)
	public List<DmRemarksBean> fetchDmRemarksList(HttpServletRequest request) {
		return commonService.fetchDmRemarksList();
	}
	
	 
	@RequestMapping(value = "/fetchInspectionReport", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody 
	public Map<String, Object> getInspectionReport(HttpServletRequest request,
	        @RequestParam(required = false) String workStatusId,
	        @RequestParam(required = false) String workSubStatus,
	        @RequestParam(required = false) String implementationAgency,
	        @RequestParam(required = false) String userId,
	        @RequestParam(required = false) String searchBoxVal) {

	    // ✅ Pagination parameters from DataTable
	    int start = Integer.parseInt(request.getParameter("start"));  // offset
	    int length = Integer.parseInt(request.getParameter("length")); // page size
	    int draw = Integer.parseInt(request.getParameter("draw"));     // DataTable draw counter

	    // ✅ Fetch full filtered list from service
	    List<WorkBean> fullList = commonService.getFilteredWorkProgress(workStatusId, userId, implementationAgency, workSubStatus, searchBoxVal);

	    int totalCount = fullList != null ? fullList.size() : 0;

	    // ✅ Paginate manually (subList)
	    int end = Math.min(start + length, totalCount);  // handle last page
	    List<WorkBean> paginatedList = new ArrayList<>();

	    if (totalCount > 0 && start < totalCount) {
	        paginatedList = fullList.subList(start, end);
	    }

	    // ✅ Prepare DataTables compatible response
	    Map<String, Object> result = new HashMap<>();
	    result.put("draw", draw);
	    result.put("recordsTotal", totalCount);
	    result.put("recordsFiltered", totalCount);
	    result.put("data", paginatedList);

	//    System.err.println("Returning " + paginatedList.size() + " of " + totalCount + " records");
	    return result;
	}

	
	@RequestMapping(value = "/fetchWorkWithLatestExpenses", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> fetchWorkWithLatestExpenses(HttpServletRequest request,
	        @RequestParam(required = false) String workStatusId,
	        @RequestParam(required = false) String userId,
	        @RequestParam(required = false) String implementationAgency,
	        @RequestParam(required = false) String searchBoxVal) {

		
	    // ✅ Pagination parameters from DataTable
	    int start = Integer.parseInt(request.getParameter("start"));   // offset
	    int length = Integer.parseInt(request.getParameter("length")); // page size
	    int draw = Integer.parseInt(request.getParameter("draw"));     // DataTable draw counter

	    // ✅ Fetch full filtered list from service (your new query)
	    List<WorkBean> fullList = commonService.getFilteredWorkWithLatestExpenses(
	            workStatusId, userId, implementationAgency, searchBoxVal);

	    int totalCount = (fullList != null) ? fullList.size() : 0;

	    // ✅ Manual pagination (DataTables style)
	    int end = Math.min(start + length, totalCount);
	    List<WorkBean> paginatedList = new ArrayList<>();

	    if (totalCount > 0 && start < totalCount) {
	        paginatedList = fullList.subList(start, end);
	    }

	    // ✅ Prepare DataTables-compatible response
	    Map<String, Object> result = new HashMap<>();
	    result.put("draw", draw);
	    result.put("recordsTotal", totalCount);
	    result.put("recordsFiltered", totalCount);
	    result.put("data", paginatedList);

	    return result;
	}
	
	@RequestMapping(value="fetchAssignedUsers", method = RequestMethod.GET)
	public List<UserBean> fetchAssignedUsers(HttpServletRequest request) {
		return commonService.fetchAssignedUsers();
	}

	@RequestMapping(value = "fetchAssignUser/{implementationAgency}", method = RequestMethod.GET)
	public List<UserBean> fetchAssignUser(HttpServletRequest request, @PathVariable Long implementationAgency) {
		return commonService.fetchAssignUser(implementationAgency);
	}

	
	  @GetMapping("/suggestWorkNos")
	    public ResponseEntity<List<String>> suggestWorkNames(@RequestParam String keyword) {
	        List<String> names = commonService.getWorkNoSuggestions(keyword);
	        return ResponseEntity.ok(names);
	    }

	  @GetMapping("/suggestWorkNames")
	    public ResponseEntity<List<String>> suggestWorkNamesByKeyword(@RequestParam String keyword) {
	        List<String> names = commonService.getWorkNameSuggestions(keyword);
	        return ResponseEntity.ok(names);
	    }

	  
	  
	  
	  @GetMapping("/fetchFinancialAgency/{workId}")
	  public ResponseEntity<Map<String, Object>> fetchFinancialAgency(
	          @PathVariable Long workId,
	          HttpServletRequest request) {


	      int start = !StringUtils.isEmpty(request.getParameter("start"))
	              ? Integer.parseInt(request.getParameter("start"))
	              : 0;
	      int length = !StringUtils.isEmpty(request.getParameter("length"))
	              ? Integer.parseInt(request.getParameter("length"))
	              : 10;
	      int draw = !StringUtils.isEmpty(request.getParameter("draw"))
	              ? Integer.parseInt(request.getParameter("draw"))
	              : 1;

	      List<FinancialAgencyBean> fullList = commonService.fetchFinancialAgencyByWorkId(workId);

	      int total = fullList.size();
	      if (start > total) {
	          start = 0;
	      }
	      int end = Math.min(start + length, total);

	      List<FinancialAgencyBean> paginatedList = fullList.subList(start, end);

	      // --------- Optional: index add ---------
	      int indexCounter = start + 1;
	      for (FinancialAgencyBean bean : paginatedList) {
	          bean.setIndex(indexCounter++);
	      }
	      // ---------------------------------------

	      Map<String, Object> response = new HashMap<>();
	      response.put("draw", draw);
	      response.put("recordsTotal", total);
	      response.put("recordsFiltered", total);
	      response.put("data", paginatedList);  // <--- IMPORTANT (new format)
	      response.put("totalExpenditure", commonService.sumFinancialAgencyExpenditureByWorkId(workId));

	      return ResponseEntity.ok(response);
	  }


	  @PostMapping("/saveFinancialAgencyEnteredCost")
	  public ResponseEntity<String> saveFinancialAgencyEnteredCost(
	          @RequestBody List<FinancialAgencyBean> list) {

	      Long workIdForSync = null;
	      for (FinancialAgencyBean bean : list) {

	          if (bean.getExpenditure() == null || bean.getExpenditure() <= 0) {
	              continue;
	          }

	          workIdForSync = bean.getWorkId();

	          String result = commonService.updateFinancialAgencyCost(
	                  bean.getId(),
	                  bean.getExpenditure(),
	                  bean.getWorkId()
	          );

	          if (!"SUCCESS".equalsIgnoreCase(result)) {
	              return ResponseEntity.ok(result);
	          }
	      }

	      if (workIdForSync != null) {
	          commonService.syncWorkProgressExpenditureFromFinancialAgency(workIdForSync);
	      }

	      return ResponseEntity.ok("SUCCESS");
	  }


	  
	  @PostMapping("/deleteFinancialAgencyRow")
	  public String deleteFinancialAgencyRow(@RequestParam Long id) {
	      return commonService.deleteByFinancailAgencyId(id);
	      
	  }

	  @GetMapping("/getFinancingAgencyList/{workId}")
	  public List<FinancialAgencyBean> getFinancingAgencyList(@PathVariable Long workId) {
	      return commonService.getFinancialAgenciesByWorkId(workId);
	  }
	  
	  @GetMapping("/getFinancingAgencyExpenditureList/{workId}")
	  public List<FinancialAgencyBean> getFinancingAgencyExpenditureList(@PathVariable Long workId) {
	      return commonService.getFinancialAgenciesExpenditureByWorkId(workId);
	  }
	  
	  
	// --------------------added by aman start code
		 // Java Spring Controller for checking password expiry and sending response
		 	@RequestMapping(value = "/verifyUserPasswordExpiry", method = RequestMethod.GET)
		 	@ResponseBody
		 	public ResponseEntity<ResponseObject> verifyUserPasswordExpiry() {
		 	    ResponseObject response = new ResponseObject(); 
		 	   user = DMSUtil.getUserDetail();
		 	  //  User user = getUserDetail();
		 		Users userInfo = userService.findByUserName(user.getUsername());
		 	 //   Users userInfo = commonService.findUserById(user.getUsername());
		 		  String role = SecurityContextHolder.getContext()
		                    .getAuthentication()
		                    .getAuthorities()
		                    .iterator()
		                    .next()
		                    .getAuthority();   // e.g. ROLE_ADMIN, ROLE_USER

		 	    // Check if the user has no password or last password updated date is null
		 	    if (userInfo == null || userInfo.getLastPasswordUpdatedOn() == null) {
		 	        response.setSuccessMessage("Your password has expired. Please Update your Password");
		 	      // response.setRoleCode(role);
		 	        return new ResponseEntity<>(response, HttpStatus.OK);
		 	    }
		 	    
		 	    // Get the last password update date
		 	    Date lastPasswordUpdated = userInfo.getLastPasswordUpdatedOn();
		 	    
		 	    // Set expiration period (e.g., 31 days)
		 	    int expirationPeriod = 31; // Password expires after 31 days
		 	    Calendar calendar = Calendar.getInstance();
		 	    calendar.setTime(lastPasswordUpdated);
		 	    calendar.add(Calendar.DAY_OF_YEAR, expirationPeriod); // Add expiration period to the last updated date
		 	    Date passwordExpiryDate = calendar.getTime();
		 	    
		 	    // Get the current date
		 	    Date currentDate = new Date();
		 	    
		 	    // Calculate the difference in days between the current date and the password expiry date
		 	    long diffInMillis = passwordExpiryDate.getTime() - currentDate.getTime();
		 	    long remainingDays = diffInMillis / (1000 * 60 * 60 * 24); // Convert milliseconds to days
		 	    
		 	    // Determine the response message based on password expiration
		 	    if (remainingDays <= 0) {
		 	    	 response.setRoleCode(role);
		 	        response.setSuccessMessage("Your password has expired. Please Update your Password");
		 	    } else {
		 	        if (remainingDays <= 3) {
		 	        	 response.setRoleCode(role);
		 	            response.setSuccessMessage("Your password will expire in " + remainingDays + " days. Do you want to update the password?");
		 	        } else {
		 	        	 response.setRoleCode(role);
		 	            // Password is still valid and not close to expiration
		 	            response.setSuccessMessage("Your password is still valid.");
		 	            return new ResponseEntity<>(response, HttpStatus.OK); // Return 200 OK for valid password
		 	        }
		 	    }
		 	    
		 	    // Return the response with the appropriate success message
		 	    return new ResponseEntity<>(response, HttpStatus.OK);
		 	}
		
			@RequestMapping(value = "/validateCurrentPassword", method = RequestMethod.POST)
			public ResponseObject validateCurrentPassword(@RequestBody ChangePasswordBean currentpassword) throws Exception {

			    ResponseObject response = new ResponseObject();

			    User user = DMSUtil.getUserDetail();
			    Users userEntity = userService.findByUserName(user.getUsername());

			    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

			    // Wrong current password
			    String currentPasswordHash = !StringUtils.isEmpty(currentpassword.getCurrentPassword())
			            ? SHAHashingUtil.encryptPassword(currentpassword.getCurrentPassword()).toString()
			            : null;

			    if (StringUtils.isEmpty(currentpassword.getCurrentPassword())
			            || !passwordEncoder.matches(currentPasswordHash, userEntity.getPassword())) {
			        response.setErrorMessage("INVALID_CURRENT_PASSWORD");
			        return response;
			    }

			    //  Correct current password
			    response.setSuccessMessage("VALID_CURRENT_PASSWORD");
			    return response;
			}
			
			@RequestMapping(value = "/userchangepassword", method = RequestMethod.GET)
			public ModelAndView viewUserChangePasswordForm(HttpServletRequest request) {

				user = DMSUtil.getUserDetail();
				logger.info("User - " + user.getUsername() + ", Role - " + user.getAuthorities()
						+ " - Displaying Change password page");

				ModelAndView modelAndView = new ModelAndView("common/userchangepassword");
				return modelAndView;

			}
			
			// aman end code
			
			
			@GetMapping(value = "getDepartmentMaster")
			public List<DepartmentMasterBean> fetchDepartmentMasters() {
				return commonService.fetchDepartmentMaster();
				
			}

			@GetMapping(value = "fetchDepartmentRemarksList")
			public List<DepartmentRemarksBean> fetchDepartmentRemarksList() {
				return commonService.fetchDepartmentRemarksList();
			}
}
