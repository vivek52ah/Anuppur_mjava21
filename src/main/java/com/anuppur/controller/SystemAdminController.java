package com.anuppur.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Supplier;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.util.StringUtils;

import com.anuppur.bean.BlockBean;
import com.anuppur.bean.DashBoardDataBean;
import com.anuppur.bean.DistrictBean;
import com.anuppur.bean.FinancialYearBean;
import com.anuppur.bean.GramPanchayatBean;
import com.anuppur.bean.ImplAgencyBean;
import com.anuppur.bean.OfficeTypeBean;
import com.anuppur.bean.OtherDocListBean;
import com.anuppur.bean.PhotoUpdateReportRowBean;
import com.anuppur.bean.RoleBean;
import com.anuppur.bean.SchemeBean;
import com.anuppur.bean.UserBean;
import com.anuppur.bean.UserTypeBean;
import com.anuppur.bean.WorkCategoryBean;
import com.anuppur.bean.WorkBean;
import com.anuppur.bean.WorkSubTypeBean;
import com.anuppur.bean.WorkTypeBean;
import com.anuppur.bean.WorkTypeFinancialOverviewBean;
import com.anuppur.constants.DMSConstants;
import com.anuppur.entity.Designation;
import com.anuppur.entity.Role;
import com.anuppur.entity.Users;
import com.anuppur.entity.WorkStatus;
import com.anuppur.json.DistrictJson;
import com.anuppur.json.GramPanchayatJson;
import com.anuppur.json.ImplAgencyJson;
import com.anuppur.json.RoleJson;
import com.anuppur.json.SchemeJson;
import com.anuppur.json.UserJson;
import com.anuppur.json.WorkCategoryJson;
import com.anuppur.json.WorkSubtypeJson;
import com.anuppur.json.workTypeJson;
import com.anuppur.repository.DesignationRepository;
import com.anuppur.repository.WorkRepository;
import com.anuppur.repository.WorkStatusRepository;
import com.anuppur.response.ResponseObject;
import com.anuppur.service.CommonService;
import com.anuppur.service.NotificationService;
import com.anuppur.service.SuperAdminService;
import com.anuppur.service.SystemAdminService;
import com.anuppur.service.UserService;
import com.anuppur.util.DMSUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@RequestMapping(value = { "/systemAdmin", "/agencyAdmin" })
@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
public class SystemAdminController extends BaseController {

	public static final Logger logger = LoggerFactory.getLogger(SystemAdminController.class);

	private User user;

	@Autowired
	private CommonService commonService;

	@Autowired
	private UserService userService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private SystemAdminService systemAdminService;

	@Autowired
	private WorkRepository workRepository;

	@Autowired
	private WorkStatusRepository workStatusRepository;

	@Value("${applicationDeploymentServerName}")
	private String applicationDeploymentServerName;

	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DEPARTMENT','ROLE_DM','ROLE_AREA_OFFICER','ROLE_SU','ROLE_DEPT_DISTRICT','ROLE_DISTRICT','ROLE_SAU','ROLE_AGENCY_ADMIN','ROLE_CEO')")
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView viewHome(HttpServletRequest request, Model model) {

		Locale locale = (Locale) request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);

		String language = request.getParameter("lang");

		if (null == locale) {
			request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.of("en"));
		} else if (!StringUtils.isEmpty(language) && language.equals(DMSConstants.LOCALE_HI)) {

			request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.of("hi"));
		} else if (!StringUtils.isEmpty(language) && language.equals(DMSConstants.LOCALE_EN)) {
			request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.of("en"));
		}

		Locale updatedLocale = (Locale) request.getSession()
				.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
		model.addAttribute("language", updatedLocale.toString());
		model.addAttribute("key", "1234567891234567");

		user = DMSUtil.getUserDetail();
		ModelAndView modelAndView = new ModelAndView("systemAdmin/systemAdminHome");

		if (user != null) {
			logger.info("User - {}, Role - {} - Displaying home page", user.getUsername(), user.getAuthorities());
			Users userEntity = userService.findByUserName(user.getUsername());
			modelAndView.addObject("loggedInUserName", userEntity.getUsername());

			String roleName = "", roleCode = "";
			Set<Role> roles = userEntity.getRoles();
			if (roles != null && !roles.isEmpty()) {
				List<Role> roleList = new ArrayList<Role>(roles);
				roleName = roleList.get(0).getRoleName();
				roleCode = roleList.get(0).getRoleCode();
			}

			if (roleCode.equals("ROLE_DEPARTMENT")) {
				roleCode = "ROLE_DEPT";

			}
			if (roleCode.equals("ROLE_AREA_OFFICER")) {

			}
			
			

			model.addAttribute("Role", roleCode);

			modelAndView.addObject("district",
					userEntity.getDistrict() != null ? userEntity.getDistrict().getDistrictName() : "");
			// modelAndView.addObject("name", userEntity.getName());
			UserBean userBean = fetchLoggedInUserDetails(request);
			modelAndView.addObject("roleName", userBean.getLoggedInUserRole());
		}
		return modelAndView;
	}

	public String getApplicationDeploymentServerName() {
		return applicationDeploymentServerName;
	}

	public void setApplicationDeploymentServerName(String applicationDeploymentServerName) {
		this.applicationDeploymentServerName = applicationDeploymentServerName;
	}

	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "fetchRolesForManage", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String fetchRolesForManage(HttpServletRequest request) {
		// return systemAdminService.fetchRoles();

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching Roles List", user.getUsername(), user.getAuthorities());
		// String searchBoxVal = request.getParameter("searchBoxVal");

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
			sort = Sort.by(Direction.ASC, "roleName");// default sorting
		}

		Pageable pageable = PageRequest.of(pageNumber, pageDisplayLength, sort);
		// systemAdminService.fetchRoles()
		RoleJson implAgencyJson = systemAdminService.fetchRoles(pageable);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(implAgencyJson);

		return json;

	}

	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "fetchSchemesForManage", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String fetchSchemesForManage(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching Scheme List", user.getUsername(), user.getAuthorities());
		String searchBoxVal = request.getParameter("searchBoxVal");

		String sSortCol = request.getParameter("iSortCol_0");
		String sSortDir = request.getParameter("sSortDir_0");
		String sColName = request.getParameter("mDataProp_" + sSortCol);

		// Fetch the page number from client
		Integer pageNumber = 0;

		// Fetch search parameter
		// String searchParameter = request.getParameter("sSearch");

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
			sort = Sort.by(Direction.ASC, "schemeName");// default sorting
		}

		Pageable pageable = PageRequest.of(pageNumber, pageDisplayLength, sort);

		SchemeJson implAgencyJson = systemAdminService.fetchAllSchemes(pageable, searchBoxVal);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(implAgencyJson);

		return json;

	}

	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/manageSchemes", method = RequestMethod.GET)
	public ModelAndView manageSchemesView(HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Manage Schemes page", user.getUsername(), user.getAuthorities());

		return new ModelAndView("common/manageSchemes");
	}

	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/manageWorkCategory", method = RequestMethod.GET)
	public ModelAndView manageWorkCategoryView(HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Manage WorkCategory page", user.getUsername(),
				user.getAuthorities());

		return new ModelAndView("systemAdmin/manageWorkCategory");
	}

	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DEPARTMENT','ROLE_DEPT_DISTRICT','ROLE_DM','ROLE_CEO','ROLE_AREA_OFFICER')")
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashBoardView(HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Dashboard page", user.getUsername(),
				user.getAuthorities());

		return new ModelAndView("systemAdmin/dashboard");
	}

	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DEPARTMENT','ROLE_DEPT_DISTRICT','ROLE_DM','ROLE_CEO','ROLE_AREA_OFFICER')")
	@RequestMapping(value = "/fetchDashboardData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public DashBoardDataBean fetchDashboardData(HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching dashboard data", user.getUsername(), user.getAuthorities());

		DashBoardDataBean dashboardData = new DashBoardDataBean();
		dashboardData.setWorkCount(fetchDashboardCount("Total works", () -> workRepository.countWork()));
		dashboardData.setAsIsuuesCount(fetchDashboardCountByStatusIds("AS issued", 2L));
		dashboardData.setTenderCalledCount(fetchDashboardCountByStatusIds("Tender called", 3L));
		dashboardData.setTenderRcvCount(fetchDashboardCountByStatusIds("Tender received", 4L));
		dashboardData.setTenderApprovalInprocessCount(fetchDashboardCountByStatusIds("Tender approval", 5L));
		dashboardData.setReTenderCount(fetchDashboardCountByStatusIds("Re-tender", 14L));
		dashboardData.setLoaIssuesCount(fetchDashboardCountByStatusIds("LOA issued", 7L));
		dashboardData.setWoIssuedCount(fetchDashboardCountByStatusIds("Work order", 8L));
		dashboardData.setNotStartedCount(fetchDashboardCountByStatusIds("Not started", 9L));
		dashboardData.setInProgressCount(fetchDashboardCountByStatusIds("In progress", 10L));
		dashboardData.setCompletedCount(fetchDashboardCountByStatusIds("Completed", 11L));
		dashboardData.setCcCount(fetchDashboardCountByStatusIds("CC uploaded", 13L));
		dashboardData.setHandOverCount(fetchDashboardCountByStatusIds("Hand over", 12L));
		populatePhotoUploadDashboardCounts(dashboardData);
		return dashboardData;
	}

	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DEPARTMENT','ROLE_DEPT_DISTRICT','ROLE_DM','ROLE_CEO','ROLE_AREA_OFFICER')")
	@RequestMapping(value = "/fetchDashboardWorkTypeFinancialOverview", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<WorkTypeFinancialOverviewBean> fetchDashboardWorkTypeFinancialOverview(HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching dashboard work type financial overview", user.getUsername(),
				user.getAuthorities());
		return fetchWorkTypeFinancialOverview();
	}

	private void populatePhotoUploadDashboardCounts(DashBoardDataBean dashboardData) {
		try {
			List<PhotoUpdateReportRowBean> photoRows = commonService.getPhotoUpdateReport(null);
			if (photoRows == null) {
				photoRows = new ArrayList<PhotoUpdateReportRowBean>();
			}
			BigDecimal uploadedWorks = BigDecimal.ZERO;
			for (PhotoUpdateReportRowBean row : photoRows) {
				if (row != null) {
					uploadedWorks = uploadedWorks.add(BigDecimal.valueOf(row.getTotalWorks()));
				}
			}
			BigDecimal totalWorks = dashboardData.getWorkCount() == null ? BigDecimal.ZERO : dashboardData.getWorkCount();
			BigDecimal pendingWorks = totalWorks.subtract(uploadedWorks);
			if (pendingWorks.compareTo(BigDecimal.ZERO) < 0) {
				pendingWorks = BigDecimal.ZERO;
			}
			dashboardData.setPhotoUploadWorkCount(uploadedWorks);
			dashboardData.setPhotoUploadDepartmentCount(BigDecimal.valueOf(photoRows.size()));
			dashboardData.setPhotoUploadPendingCount(pendingWorks);
		} catch (Exception ex) {
			logger.error("Dashboard photo upload counts failed", ex);
			dashboardData.setPhotoUploadWorkCount(BigDecimal.ZERO);
			dashboardData.setPhotoUploadDepartmentCount(BigDecimal.ZERO);
			dashboardData.setPhotoUploadPendingCount(BigDecimal.ZERO);
		}
	}

	private void populateExpenditureDashboardCounts(DashBoardDataBean dashboardData) {
		try {
			List<WorkBean> expenditureRows = commonService.getFilteredWorkWithLatestExpenses(null, null, null, null);
			if (expenditureRows == null) {
				expenditureRows = new ArrayList<WorkBean>();
			}

			BigDecimal pacTotal = BigDecimal.ZERO;
			BigDecimal totalExpenditure = BigDecimal.ZERO;
			BigDecimal lastExpenditure = BigDecimal.ZERO;
			for (WorkBean row : expenditureRows) {
				if (row != null) {
					pacTotal = pacTotal.add(row.getPac() == null ? BigDecimal.ZERO : row.getPac());
					totalExpenditure = totalExpenditure
							.add(row.getTotalExpensess() == null ? BigDecimal.ZERO : row.getTotalExpensess());
					lastExpenditure = lastExpenditure
							.add(row.getLastExpenditure() == null ? BigDecimal.ZERO : row.getLastExpenditure());
				}
			}

			dashboardData.setExpenditureReportWorkCount(BigDecimal.valueOf(expenditureRows.size()));
			dashboardData.setExpenditurePacTotal(pacTotal);
			dashboardData.setExpenditureTotal(totalExpenditure);
			dashboardData.setExpenditureLastTotal(lastExpenditure);
		} catch (Exception ex) {
			logger.error("Dashboard expenditure counts failed", ex);
			dashboardData.setExpenditureReportWorkCount(BigDecimal.ZERO);
			dashboardData.setExpenditurePacTotal(BigDecimal.ZERO);
			dashboardData.setExpenditureTotal(BigDecimal.ZERO);
			dashboardData.setExpenditureLastTotal(BigDecimal.ZERO);
		}
	}

	private void populateWorkTypeFinancialOverview(DashBoardDataBean dashboardData) {
		dashboardData.setWorkTypeFinancialOverview(fetchWorkTypeFinancialOverview());
	}

	private List<WorkTypeFinancialOverviewBean> fetchWorkTypeFinancialOverview() {
		List<WorkTypeFinancialOverviewBean> overview = new ArrayList<WorkTypeFinancialOverviewBean>();
		try {
			List<Object[]> rows = workRepository.fetchWorkTypeFinancialOverview();
			if (rows != null) {
				for (Object[] row : rows) {
					WorkTypeFinancialOverviewBean bean = new WorkTypeFinancialOverviewBean();
					bean.setWorkTypeName(row[0] != null ? row[0].toString() : "N/A");
					bean.setContractAmount(toBigDecimal(row[1]));
					bean.setExpenditureAmount(toBigDecimal(row[2]));
					overview.add(bean);
				}
			}
		} catch (Exception ex) {
			logger.error("Dashboard work type financial overview failed", ex);
		}
		return overview;
	}

	private BigDecimal toBigDecimal(Object value) {
		if (value == null) {
			return BigDecimal.ZERO;
		}
		if (value instanceof BigDecimal) {
			return (BigDecimal) value;
		}
		if (value instanceof Number) {
			return BigDecimal.valueOf(((Number) value).doubleValue());
		}
		try {
			return new BigDecimal(value.toString());
		} catch (Exception ex) {
			return BigDecimal.ZERO;
		}
	}

	private BigDecimal fetchDashboardCountByStatusIds(String label, Long... statusIds) {
		List<Long> ids = new ArrayList<Long>();
		for (Long statusId : statusIds) {
			if (statusId != null && !ids.contains(statusId)) {
				ids.add(statusId);
			}
		}
		return fetchDashboardCount(label, () -> workRepository.getDashboardCountByStatusIds(ids));
	}

	private BigDecimal fetchDashboardCountByStatusNames(String label, String... statusNames) {
		List<Long> statusIds = new ArrayList<Long>();
		for (String statusName : statusNames) {
			if (StringUtils.isEmpty(statusName)) {
				continue;
			}
			List<WorkStatus> workStatuses = workStatusRepository.findByWorkStatusNameEOrderByIdAsc(statusName);
			for (WorkStatus workStatus : workStatuses) {
				if (workStatus != null && workStatus.getId() != null && !statusIds.contains(workStatus.getId())) {
					statusIds.add(workStatus.getId());
				}
			}
		}
		if (statusIds.isEmpty()) {
			logger.warn("Dashboard status ids not found for {}", label);
			return BigDecimal.ZERO;
		}
		return fetchDashboardCount(label, () -> workRepository.getDashboardCountByStatusIds(statusIds));
	}

	private BigDecimal fetchDashboardCount(String label, Supplier<BigDecimal> countSupplier) {
		try {
			BigDecimal count = countSupplier.get();
			return count == null ? BigDecimal.ZERO : count;
		} catch (Exception ex) {
			logger.error("Dashboard count failed for {}", label, ex);
			return BigDecimal.ZERO;
		}
	}

	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/manageWorkStages", method = RequestMethod.GET)
	public ModelAndView addWorkStages(HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Adding Work Stages page", user.getUsername(), user.getAuthorities());

		return new ModelAndView("systemAdmin/manageWorkStages");
	}

	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/fetchWorkCatListByDst", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String fetchWorkCatListByDst(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching Work Category List", user.getUsername(), user.getAuthorities());
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
			sort = Sort.by(Direction.DESC, "id");// default sorting
		}

		Pageable pageable = PageRequest.of(pageNumber, pageDisplayLength, sort);

		WorkCategoryJson workCategoryJson = systemAdminService.getWorkCategryByDistrictId(pageable, searchBoxVal);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(workCategoryJson);

		return json;
	}

	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/addWorkCatForm", method = RequestMethod.GET)
	public ModelAndView addWorkCatForm(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Add Work C Form", user.getUsername(), user.getAuthorities());
		return new ModelAndView("systemAdmin/addWorkCatForm");

	}

	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/editWorkCatForm/{id}", method = RequestMethod.GET)
	public ModelAndView editWorkCatForm(@PathVariable String id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Edit Work Category Form", user.getUsername(),
				user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("systemAdmin/editWorkCatForm");
		modelAndView.addObject("loggedInUserName", user.getUsername());
		return modelAndView;
	}

	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@ResponseBody
	@RequestMapping(value = "/addWorkCat", method = RequestMethod.POST)
	public ResponseObject addWorkCat(@RequestBody WorkCategoryBean workCategoryBean, HttpServletRequest request)
			throws Exception {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Adding Work Cat data", user.getUsername(), user.getAuthorities());
		ResponseObject response = new ResponseObject();

		String errorMsg = systemAdminService.addWorkCategory(workCategoryBean);
		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
		} else {
			response.setSuccessMessage("Work Category added successfully!");
			logger.info("User - {}, Role - {} - Work Category added successfully!", user.getUsername(),
					user.getAuthorities());
		}
		return response;
	}

	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "fetchWorkCatById/{id}", method = RequestMethod.GET)
	public WorkCategoryBean fetchWorkCatById(@PathVariable String id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching WorkCategory data for Edit", user.getUsername(),
				user.getAuthorities());
		return systemAdminService.fetchWorkCatById(Long.parseLong(DMSUtil.decryptParam(id)));
	}

	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/deleteWorkCatById/{id}", method = RequestMethod.POST)
	public ResponseObject deleteWorkCatById(@PathVariable Long id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Deleting Work Category ", user.getUsername(), user.getAuthorities());
		ResponseObject response = new ResponseObject();

		String errorMsg = systemAdminService.deleteWorkCatById(id);

		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
		} else {
			response.setSuccessMessage("Work Category  deleted successfully!");
			logger.info("User - {}, Role - {} - Work Category deleted successfully!", user.getUsername(),
					user.getAuthorities());
		}
		return response;
	}

	/*
	 * @RequestMapping(value = "fetchWorkTypes", method = RequestMethod.GET) public
	 * List<WorkTypeBean> fetchWorkTypes(HttpServletRequest request) { return
	 * commonService.fetchWorkTypes(); }
	 */
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "fetchSchemesForSyatemAdmin", method = RequestMethod.GET)
	public List<SchemeBean> fetchSchemesForSyatemAdmin(HttpServletRequest request) {
		return commonService.fetchSchemes();
	}

	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "fetchDefaultDocList", method = RequestMethod.GET)
	public List<OtherDocListBean> fetchDefaultDocList(HttpServletRequest request) {
		return commonService.fetchDefaultDocList();

	}

	// Method For Getting Manage User Page
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM','ROLE_DEPARTMENT')")
	@RequestMapping(value = "/manageusers", method = RequestMethod.GET)
	public ModelAndView manageUsersView(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Manage Users page", user.getUsername(), user.getAuthorities());

		ModelAndView modelAndView = new ModelAndView("superAdmin/manageUsers");
		UserBean userBean = fetchLoggedInUserDetails(request);
		modelAndView.addObject("roleName", userBean.getLoggedInUserRole());
		return modelAndView;
	}

	// Method For Getting User List
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM','ROLE_DEPARTMENT')")
	@RequestMapping(value = "/fetchUserList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String fetchUserList(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching User List", user.getUsername(), user.getAuthorities());
		String searchBoxVal = request.getParameter("searchBoxVal");
		/* String roleCode = request.getParameter("roleCode"); */
		String status = request.getParameter("status");
		String username = request.getParameter("username");
		String mobileNo = request.getParameter("mobileNo");
		String emailId = request.getParameter("emailId");
		String sSortCol = request.getParameter("iSortCol_0");
		String sSortDir = request.getParameter("sSortDir_0");
		String sColName = request.getParameter("mDataProp_" + sSortCol);
		logger.info(mobileNo + "  -  " + emailId + " ad " + searchBoxVal);
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

		UserJson userJson = superAdminService.getAllUsers(pageable, fetchLoggedInUserDetails(request), searchBoxVal,
				mobileNo, status, username, emailId);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(userJson);

		return json;
	}

	// Method For Getting add User Form Page
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM','ROLE_DEPARTMENT')")
	@RequestMapping(value = "/addUserForm", method = RequestMethod.GET)
	public ModelAndView viewAddUserForm(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Add User Form", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("superAdmin/addUserForm");
		return modelAndView;
	}

	// Method For Getting Add UserAgency Form
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM')")
	@RequestMapping(value = "/addUserAgencyFrom", method = RequestMethod.GET)
	public ModelAndView viewAddUserAgencyFrom(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Add User Form", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("superAdmin/addUserAgencyFrom");
		return modelAndView;
	}

	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM')")
	@RequestMapping(value = "fetchRoles", method = RequestMethod.GET)
	public List<RoleBean> fetchRoles(HttpServletRequest request) {
		return superAdminService.fetchRoles();
	}

	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM')")
	@RequestMapping(value = "fetchUserType", method = RequestMethod.GET)
	public List<UserTypeBean> fetchUserType(HttpServletRequest request) {
		return superAdminService.fetchUserType();
	}

	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM','ROLE_DEPARTMENT')")
	@RequestMapping(value = "fetchDesignation", method = RequestMethod.GET)
	public List<Designation> fetchDesignation(HttpServletRequest request) {

		return superAdminService.fetchDesignation(fetchLoggedInUserDetails(request));
	}

	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM')")
	@RequestMapping(value = "fetchOfficeType", method = RequestMethod.GET)
	public List<OfficeTypeBean> fetchOfficeType(HttpServletRequest request) {
		return superAdminService.fetchOfficeType();
	}

	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM')")
	@RequestMapping(value = "fetchOfficeTypesByUserType/{userTypeId}", method = RequestMethod.GET)
	@ResponseBody
	public List<OfficeTypeBean> fetchOfficeTypesByUserType(HttpServletRequest request,
			@PathVariable String userTypeId) {
		return superAdminService.fetchOfficeTypesByUserType(Long.parseLong(userTypeId));
	}

	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM')")
	@RequestMapping(value = "fetchRoleTypesByUserOfficeType/{userTypeId}/{officeTypeId}", method = RequestMethod.GET)
	@ResponseBody
	public List<RoleBean> fetchRoleTypesByUserOfficeType(HttpServletRequest request, @PathVariable String userTypeId,
			@PathVariable String officeTypeId) {
		return superAdminService.fetchRoleTypesByUserOfficeType(Long.parseLong(userTypeId),
				Long.parseLong(officeTypeId));
	}

	// Method For Getting Add User
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM') or (hasRole('ROLE_DEPARTMENT') and @userAuthorization.isAreaOfficerRequest(#p0))")
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public ResponseObject addUser(@RequestBody UserBean userBean, HttpServletRequest request) throws Exception {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Adding User data", user.getUsername(), user.getAuthorities());
		ResponseObject response = new ResponseObject();

		/*
		 * String fullPath = request.getScheme() + "://"+
		 * getApplicationDeploymentServerName() + ":" + request.getServerPort() +
		 * contextPath;
		 * 
		 * String verifyServiceUrl = fullPath + "/" +
		 * DMSConstants.VERIFY_EMAIL_SERVICE_NAME;
		 */

		String errorMsg = superAdminService.addUser(userBean);
		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
		} else {
			// notificationService.sendUserRegistrationEmail(userBean);
			notificationService.sendUserRegistrationMessage(userBean);
			notificationService.sendNotificationOnMail(userBean.getEmailId(), userBean);
			response.setSuccessMessage("User added successfully!");
			logger.info("User - {}, Role - {} - User added successfully!", user.getUsername(), user.getAuthorities());

		}
		return response;
	}

	// Method For Getting Edit User Form Page
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM')")
	@RequestMapping(value = "/editUserForm/{id}", method = RequestMethod.GET)
	public ModelAndView viewEditUserForm(@PathVariable String id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Edit User Form", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("superAdmin/editUserForm");
		modelAndView.addObject("loggedInUserName", user.getUsername());
		modelAndView.addObject("roleName", fetchLoggedInUserDetails(request).getRolee());

		return modelAndView;
	}

	// MEthod For Getting USer Details
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM')")
	@RequestMapping(value = "fetchUserDetails/{id}", method = RequestMethod.GET)
	public UserBean fetchUserDetails(@PathVariable String id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching User data", user.getUsername(), user.getAuthorities());
		return superAdminService.fetchUserDetails(Long.parseLong(DMSUtil.decryptParam(id)));
	}

	// Method For Editing User
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM')")
	@RequestMapping(value = "/editUser", method = RequestMethod.POST)
	public ResponseObject editUser(@RequestBody UserBean userBean, HttpServletRequest request) throws Exception {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Updating User data", user.getUsername(), user.getAuthorities());
		ResponseObject response = new ResponseObject();

		String contextPath = "";
		if (!StringUtils.isEmpty(request.getContextPath())) {
			contextPath = request.getContextPath();
		}

		String websiteURL = request.getScheme() + "://" + getApplicationDeploymentServerName() + ":"
				+ request.getServerPort() + contextPath;

		String errorMsg = superAdminService.editUser(userBean, websiteURL);

		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
		} else {
			response.setSuccessMessage("User updated successfully!");
			logger.info("User - {}, Role - {} - User updated successfully!", user.getUsername(), user.getAuthorities());
		}
		return response;
	}

	// Method For Deleteing User
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM')")
	@RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.POST)
	public ResponseObject deleteUser(@PathVariable Long id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Deleting User", user.getUsername(), user.getAuthorities());
		ResponseObject response = new ResponseObject();

		String errorMsg = superAdminService.deleteUser(id);

		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
		} else {
			response.setSuccessMessage("User deleted successfully!");
			logger.info("User - {}, Role - {} - User deleted successfully!", user.getUsername(), user.getAuthorities());
		}
		return response;
	}

	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM')")
	@RequestMapping(value = "fetchConstructionAgency", method = RequestMethod.GET)
	public List<ImplAgencyBean> fetchConstructionAgency(HttpServletRequest request) {
		return superAdminService.fetchConstructionAgency();
	}

	@Autowired
	private SuperAdminService superAdminService;

	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM')")
	@RequestMapping(value = "/manageAgencyUsers", method = RequestMethod.GET)
	public ModelAndView manageAgencyUsersView(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Manage Users page", user.getUsername(), user.getAuthorities());

		ModelAndView modelAndView = new ModelAndView("superAdmin/manageAgencyUsers");
		UserBean userBean = fetchLoggedInUserDetails(request);
		modelAndView.addObject("roleName", userBean.getLoggedInUserRole());
		return modelAndView;
	}

	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM')")
	@RequestMapping(value = "fetchRole", method = RequestMethod.GET)
	public List<Role> fetchRole(HttpServletRequest request) {
		List list = systemAdminService.fetchRole();
		logger.info("list of role =============" + list);
		return list;
	}

	@RequestMapping(value = "/addWorkFacility", method = RequestMethod.GET)
	public ModelAndView addWorkFacility(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Add Work Facility Form", user.getUsername(),
				user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("systemAdmin/addWorkFacility");
		modelAndView.addObject("loggedInUserName", user.getUsername());
		return modelAndView;
	}

	@RequestMapping(value = "/manageWorkFacility", method = RequestMethod.GET)
	public ModelAndView manageWorkFacility(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Manage Work Facility Form", user.getUsername(),
				user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("systemAdmin/manageWorkFacility");
		modelAndView.addObject("loggedInUserName", user.getUsername());
		return modelAndView;
	}

	@GetMapping(value = "/fetchWorkFacility", produces = "application/json;charset=UTF-8")
	public String fetchWorkFacility(HttpServletRequest request) {
		String searchBoxVal = request.getParameter("searchBoxVal");
		String sSortCol = request.getParameter("iSortCol_0");
		String sSortDir = request.getParameter("sSortDir_0");
		String sColName = request.getParameter("mDataProp_" + sSortCol);
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
			sort = Sort.by(Direction.DESC, "workCategoryId");// default sorting
		}
		Pageable pageable = PageRequest.of(pageNumber, pageDisplayLength, sort);
		WorkCategoryJson cJson = systemAdminService.getWorkFacility(pageable, searchBoxVal);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(cJson);
		return json;
	}

	// add
	@RequestMapping(value = "/addWorkFacility", method = RequestMethod.POST)
	public ResponseObject addWorkFacility(@RequestBody WorkCategoryBean bean) {
		ResponseObject response = new ResponseObject();

		String errorMsg = systemAdminService.addWorkFacility(bean);
		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
		} else {
			response.setSuccessMessage("Details added successfully!");
		}

		return response;
	}

	@RequestMapping(value = "/editWorkFacility/{id}", method = RequestMethod.GET)
	public ModelAndView editWorkFacility(@PathVariable String id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Edit Work Facility Form", user.getUsername(),
				user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("systemAdmin/editWorkFacility");
		modelAndView.addObject("loggedInUserName", user.getUsername());
		return modelAndView;
	}

	// edit
	@RequestMapping(value = "fetchWorkFacilityById/{id}", method = RequestMethod.GET)
	public WorkCategoryBean fetchWorkFacilityById(@PathVariable String id, HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching Work Facility for Edit", user.getUsername(),
				user.getAuthorities());
//	System.out.println("this is the id"+id);
//	String decryptParam = DMSUtil.decryptParam(id);
//	long long1 = Long.parseLong(DMSUtil.decryptParam(id));
		return systemAdminService.fetchWorkFacilityById(Long.parseLong(DMSUtil.decryptParam(id)));
	}

	// delete
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/deleteWorkFacility/{id}", method = RequestMethod.POST)
	public ResponseObject deleteWorkFacility(@PathVariable Long id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Deleting data ", user.getUsername(), user.getAuthorities());
		ResponseObject response = new ResponseObject();
		String errorMsg = systemAdminService.deleteWorkFacility(id);

		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
		} else {
			response.setSuccessMessage(" deleted successfully!");
			logger.info("User - {}, Role - {} -deleted successfully!", user.getUsername(), user.getAuthorities());
		}
		return response;
	}

	// subtype

	@RequestMapping(value = "/addWorkSubType", method = RequestMethod.GET)
	public ModelAndView addWorkSubType(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Add Work SubType Form", user.getUsername(),
				user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("systemAdmin/addWorkSubType");
		modelAndView.addObject("loggedInUserName", user.getUsername());
		return modelAndView;
	}

	@RequestMapping(value = "/editWorkSubType/{id}", method = RequestMethod.GET)
	public ModelAndView editWorkSubType(@PathVariable String id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Edit Work Subtype Form", user.getUsername(),
				user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("systemAdmin/editWorkSubType");
		modelAndView.addObject("loggedInUserName", user.getUsername());
		return modelAndView;
	}

	// edit
	@RequestMapping(value = "fetchWorkTypeById/{id}", method = RequestMethod.GET)
	public WorkTypeBean fetchWorkSubTypeById(@PathVariable String id, HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching Work TYPE for Edit", user.getUsername(), user.getAuthorities());
		return systemAdminService.fetchWorkTypeById(Long.parseLong(DMSUtil.decryptParam(id)));
	}

	// addWorkSubType

	@RequestMapping(value = "/addWorkSubType", method = RequestMethod.POST)
	public ResponseObject addWorkSubType(@RequestBody WorkTypeBean bean) {
		ResponseObject response = new ResponseObject();

		String errorMsg = systemAdminService.addWorkSubType(bean);
		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
		} else {
			response.setSuccessMessage("Details added successfully!");
		}

		return response;
	}

	//
	@RequestMapping(value = "/manageWorkSubtype", method = RequestMethod.GET)
	public ModelAndView manageWorkSubtype(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Manage Work Facility Form", user.getUsername(),
				user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("systemAdmin/manageWorkSubtype");
		modelAndView.addObject("loggedInUserName", user.getUsername());
		return modelAndView;
	}

	@GetMapping(value = "/fetchWorkType", produces = "application/json;charset=UTF-8")
	public String fetchWorkSubType(HttpServletRequest request) {
		String searchBoxVal = request.getParameter("searchBoxVal");
		String sSortCol = request.getParameter("iSortCol_0");
		String sSortDir = request.getParameter("sSortDir_0");
		String sColName = request.getParameter("mDataProp_" + sSortCol);
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
			sort = Sort.by(Direction.DESC, "workTypeId");// default sorting
		}
		Pageable pageable = PageRequest.of(pageNumber, pageDisplayLength, sort);
		workTypeJson cJson = systemAdminService.getWorkSubType(pageable, searchBoxVal);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(cJson);
		return json;
	}

	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/deleteWorkSubType/{id}", method = RequestMethod.POST)
	public ResponseObject deleteWorkSubType(@PathVariable Long id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Deleting data ", user.getUsername(), user.getAuthorities());
		ResponseObject response = new ResponseObject();
		String errorMsg = systemAdminService.deleteWorkSubType(id);

		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
		} else {
			response.setSuccessMessage(" deleted successfully!");
			logger.info("User - {}, Role - {} -deleted successfully!", user.getUsername(), user.getAuthorities());
		}
		return response;
	}

	// addImplAgency
	// Method for Getting AddImplementaion Agency PAge
	@RequestMapping(value = "/addImplAgencyy", method = RequestMethod.GET)
	public ModelAndView addImplAgencyy(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Add Impl Agency Form", user.getUsername(),
				user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("systemAdmin/addImplAgencyy");
		modelAndView.addObject("loggedInUserName", user.getUsername());
		return modelAndView;
	}

	// Method For Adding Implementation Agency
	@RequestMapping(value = "/addImplAgencyy", method = RequestMethod.POST)
	public ResponseObject addImplAgency(@RequestBody ImplAgencyBean bean) {
		ResponseObject response = new ResponseObject();

		String errorMsg = systemAdminService.addImplAgencyy(bean);
		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
		} else {
			response.setSuccessMessage("Details added successfully!");
		}

		return response;
	}

	// manageImplAgency
	// Mehtod for Getting Manage Implementaion Agency Page
	@RequestMapping(value = "/manageImplAgencyy", method = RequestMethod.GET)
	public ModelAndView manageImplAgency(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Manage Impl Agnecy Form", user.getUsername(),
				user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("systemAdmin/manageImplAgencyy");
		modelAndView.addObject("loggedInUserName", user.getUsername());
		return modelAndView;
	}

	//
	// MEthod For Getting fethcing Implementaion Agency By Id
	@GetMapping(value = "/fetchImplAgencyyById", produces = "application/json;charset=UTF-8")
	public String fetchImplAgencyyById(HttpServletRequest request) {
		String searchBoxVal = request.getParameter("searchBoxVal");
		String sSortCol = request.getParameter("iSortCol_0");
		String sSortDir = request.getParameter("sSortDir_0");
		String sColName = request.getParameter("mDataProp_" + sSortCol);
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
			sort = Sort.by(Direction.DESC, "implementationAgencyId");// default sorting
		}
		Pageable pageable = PageRequest.of(pageNumber, pageDisplayLength, sort);
		ImplAgencyJson cJson = systemAdminService.getImplAgencyy(pageable, searchBoxVal);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(cJson);
		return json;
	}

	// deleteImplAgency

	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/deleteImplAgencyy/{id}", method = RequestMethod.POST)
	public ResponseObject deleteImplAgencyy(@PathVariable Long id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Deleting data ", user.getUsername(), user.getAuthorities());
		ResponseObject response = new ResponseObject();
		String errorMsg = systemAdminService.deleteImplAgencyy(id);

		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
		} else {
			response.setSuccessMessage(" deleted successfully!");
			logger.info("User - {}, Role - {} -deleted successfully!", user.getUsername(), user.getAuthorities());
		}
		return response;
	}

	// Method For Edit Implementation Agency
	@RequestMapping(value = "/editImplAgencyy/{id}", method = RequestMethod.GET)
	public ModelAndView editImplAgencyy(@PathVariable String id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Edit Impl Agency Form", user.getUsername(),
				user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("systemAdmin/editImplAgencyy");
		modelAndView.addObject("loggedInUserName", user.getUsername());
		return modelAndView;
	}
	// fetchImplAgencyy

	@RequestMapping(value = "fetchImplAgencyy/{id}", method = RequestMethod.GET)
	public ImplAgencyBean fetchImplAgencyy(@PathVariable String id, HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching Impl agency for Edit", user.getUsername(), user.getAuthorities());
		return systemAdminService.fetchImplAgencyy(Long.parseLong(DMSUtil.decryptParam(id)));
	}

	// Method For Getting List of All Department User
	@RequestMapping(value = "/manageDepartmentUser", method = RequestMethod.GET)
	public ModelAndView manageDepartmentUser(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying manageDepartmentUser", user.getUsername(),
				user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("systemAdmin/manageDepartmentUser");
		modelAndView.addObject("loggedInUserName", user.getUsername());
		return modelAndView;

	}

	// Method For add Department User Page
	@RequestMapping(value = "/addDepartmentUser", method = RequestMethod.GET)
	public ModelAndView addDepartmentUser(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying addDepartmentUser", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("systemAdmin/addDepartmentUser");
		modelAndView.addObject("loggedInUserName", user.getUsername());
		return modelAndView;
	}

	// Method for Getting Edit Department User Page
	@RequestMapping(value = "/editDepartmentUser/{id}", method = RequestMethod.GET)
	public ModelAndView editDepartmentUser(@PathVariable String id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying editDeparmentUser", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("systemAdmin/addDepartmentUser");
		modelAndView.addObject("loggedInUserName", user.getUsername());
		return modelAndView;
	}

	// Method For Getting Manage District Page
	@RequestMapping(value = "/manageDistricts", method = RequestMethod.GET)
	public ModelAndView viewManageDistricts(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying manageDistricts", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("systemAdmin/manageDistricts");
		modelAndView.addObject("loggedInUserName", user.getUsername());
		return modelAndView;
	}

	// Method For Getting addDistrict Page
	@RequestMapping(value = "/addDistrict", method = RequestMethod.GET)
	public ModelAndView addDistrict(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying addDistrict", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("systemAdmin/addDistrict");
		modelAndView.addObject("loggedInUserName", user.getUsername());
		return modelAndView;
	}

	// Method For Getting editDistrict Page
	@RequestMapping(value = "/editDistrict/{id}", method = RequestMethod.GET)
	public ModelAndView editDistrict(@PathVariable String id, HttpServletRequest request) {
		// System.out.println("i am inside gta 6");
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying addDistrict", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("systemAdmin/addDistrict");
		modelAndView.addObject("loggedInUserName", user.getUsername());
		return modelAndView;
	}

	// Method For Getting ManageBlock Page
	@RequestMapping(value = "/manageBlock", method = RequestMethod.GET)
	public ModelAndView viewManageBlock(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying manageBlock", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("systemAdmin/manageBlock");
		modelAndView.addObject("loggedInUserName", user.getUsername());
		return modelAndView;
	}

	// Method For Getting Manage Grampanchayat Page
	@RequestMapping(value = "/manageGrampanchayat", method = RequestMethod.GET)
	public ModelAndView viewManagegrampanchayat(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying manageGrampanchayat", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("systemAdmin/manageGrampanchayat");
		modelAndView.addObject("loggedInUserName", user.getUsername());
		return modelAndView;
	}

	// Mehtod For Getting All Districts
	@GetMapping(value = "/fetchAllDistrict", produces = "application/json;charset=UTF-8")
	public String fetchAllDistricts(HttpServletRequest request) {
		String searchBoxVal = request.getParameter("searchBoxVal");
		String sSortCol = request.getParameter("iSortCol_0");
		String sSortDir = request.getParameter("sSortDir_0");
		String sColName = request.getParameter("mDataProp_" + sSortCol);
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
			sort = Sort.by(Direction.ASC, "districtId");// default sorting
		}
		Pageable pageable = PageRequest.of(pageNumber, pageDisplayLength, sort);
		DistrictJson cJson = systemAdminService.getAllDistrict(pageable, searchBoxVal);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(cJson);
		return json;
	}

	// Method for Getting District Details
	@RequestMapping(value = "fetchDistrictDetails/{id}", method = RequestMethod.GET)
	public DistrictBean fetchDistrictDetails(@PathVariable String id, HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching District for Edit", user.getUsername(), user.getAuthorities());
		return systemAdminService.fetchDistrictDetails(Long.parseLong(id));
	}

	// Method For Getting Grampanchayat Details
	@RequestMapping(value = "fetchGPDetails/{id}", method = RequestMethod.GET)
	public GramPanchayatBean fetchGPDetails(@PathVariable String id, HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching GRAMPANCHAYAT for Edit", user.getUsername(),
				user.getAuthorities());
		return systemAdminService.fetchGPDetails(Long.parseLong(id));
	}

	// Method For Getting Block Details
	@RequestMapping(value = "fetchBlockDetails/{id}", method = RequestMethod.GET)
	public BlockBean fetchBlockDetails(@PathVariable String id, HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching BLOCK for Edit", user.getUsername(), user.getAuthorities());
		return systemAdminService.fetchBlockDetails(Long.parseLong(id));
	}

	// Method For Adding District
	@RequestMapping(value = "/addDistrict", method = RequestMethod.POST)
	public ResponseObject addDistrict(@RequestBody DistrictBean bean) {
		ResponseObject response = new ResponseObject();

		String errorMsg = systemAdminService.addDistrict(bean);
		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
		} else {
			response.setSuccessMessage("Details added successfully!");
		}
		return response;
	}

	// Mehtod For Adding Block
	@RequestMapping(value = "/addBlock", method = RequestMethod.POST)
	public ResponseObject addBlock(@RequestBody BlockBean bean) {
		ResponseObject response = new ResponseObject();

		String errorMsg = systemAdminService.addblock(bean);
		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
		} else {
			response.setSuccessMessage("Details added successfully!");
		}
		return response;
	}

	@RequestMapping(value = "/addGP", method = RequestMethod.POST)
	public ResponseObject addGP(@RequestBody GramPanchayatBean bean) {
		ResponseObject response = new ResponseObject();

		String errorMsg = systemAdminService.addGP(bean);
		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
		} else {
			response.setSuccessMessage("Details added successfully!");
		}

		return response;
	}

	// Method For Deleteing District
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/deleteDistrict/{id}", method = RequestMethod.POST)
	public ResponseObject deleteDistrict(@PathVariable Long id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Deleting data ", user.getUsername(), user.getAuthorities());
		ResponseObject response = new ResponseObject();
		String errorMsg = systemAdminService.deleteDistrict(id);

		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
		} else {
			response.setSuccessMessage(" deleted successfully!");
			logger.info("User - {}, Role - {} -deleted successfully!", user.getUsername(), user.getAuthorities());
		}
		return response;
	}

	// Method For Deleteing Grampanchayat
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/deleteGP/{id}", method = RequestMethod.POST)
	public ResponseObject deleteGP(@PathVariable Long id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Deleting data ", user.getUsername(), user.getAuthorities());
		ResponseObject response = new ResponseObject();
		String errorMsg = systemAdminService.deleteGP(id);

		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
		} else {
			response.setSuccessMessage(" deleted successfully!");
			logger.info("User - {}, Role - {} -deleted successfully!", user.getUsername(), user.getAuthorities());
		}
		return response;
	}

	// Mehtod for Deleteing Block
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@RequestMapping(value = "/deleteBlock/{id}", method = RequestMethod.POST)
	public ResponseObject deleteBlock(@PathVariable Long id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Deleting data ", user.getUsername(), user.getAuthorities());
		ResponseObject response = new ResponseObject();
		String errorMsg = systemAdminService.deleteBlock(id);

		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
		} else {
			response.setSuccessMessage(" deleted successfully!");
			logger.info("User - {}, Role - {} -deleted successfully!", user.getUsername(), user.getAuthorities());
		}
		return response;
	}

	// Method For Getting AddBlock Page
	@RequestMapping(value = "/addBlock", method = RequestMethod.GET)
	public ModelAndView addBlock(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying addBlock", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("systemAdmin/addBlock");
		modelAndView.addObject("loggedInUserName", user.getUsername());
		return modelAndView;
	}

	// Mehtod for Getting EditBlock Page
	@RequestMapping(value = "/editBlock/{id}", method = RequestMethod.GET)
	public ModelAndView editBlock(@PathVariable String id, HttpServletRequest request) {
		// System.out.println("i am inside gta 6");
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying editBlock", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("systemAdmin/addBlock");
		modelAndView.addObject("loggedInUserName", user.getUsername());
		return modelAndView;
	}

	// Method For Getting addGramPanchayat Page
	@RequestMapping(value = "/addGrampanchayat", method = RequestMethod.GET)
	public ModelAndView addGrampanchayat(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying addBlock", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("systemAdmin/addGrampanchayat");
		modelAndView.addObject("loggedInUserName", user.getUsername());
		return modelAndView;
	}

	// Method for Getting Edit Gram Panchayat Page
	@RequestMapping(value = "/editGrampanchayat/{id}", method = RequestMethod.GET)
	public ModelAndView editGrampanchayat(@PathVariable String id, HttpServletRequest request) {
		// System.out.println("i am inside gta 6");
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying editBlock", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("systemAdmin/addGrampanchayat");
		modelAndView.addObject("loggedInUserName", user.getUsername());
		return modelAndView;
	}

	// Method for Getting GramPanchayat List
	@GetMapping(value = "/fetchGrampanchyatJson", produces = "application/json;charset=UTF-8")
	public String fetchGrampanchyatJson(HttpServletRequest request) {
		String searchBoxVal = request.getParameter("searchBoxVal");
		String districtId = request.getParameter("districtId");
		String blockId = request.getParameter("blockId");
		String sSortCol = request.getParameter("iSortCol_0");
		String sSortDir = request.getParameter("sSortDir_0");
		String sColName = request.getParameter("mDataProp_" + sSortCol);
		Integer pageNumber = 0;

		// System.out.println(districtId.isEmpty()?"empty":"aya hai");

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
			sort = Sort.by(Direction.ASC, "gramPanchayatId");// default sorting
		}
		Pageable pageable = PageRequest.of(pageNumber, pageDisplayLength, sort);
		GramPanchayatJson cJson = systemAdminService.getallGrampanchayat(pageable, searchBoxVal, districtId, blockId);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(cJson);
		return json;
	}

	// Mehtod for Getting Page of Pending User Page
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM')")
	@RequestMapping(value = "/managePendingUsers", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView viewmanagePendingUsers(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Manage Users page", user.getUsername(), user.getAuthorities());

		ModelAndView modelAndView = new ModelAndView("systemAdmin/managePendingUsers");
		UserBean userBean = fetchLoggedInUserDetails(request);
		modelAndView.addObject("roleName", userBean.getLoggedInUserRole());
		return modelAndView;
	}

	// Method for Getting List of User For Approval
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM')")
	@RequestMapping(value = "/fetchUserListForApproval", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public String fetchUserListForApproval(HttpServletRequest request) {

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

		UserJson userJson = superAdminService.fetchUserListForApproval(pageable, fetchLoggedInUserDetails(request),
				searchBoxVal, status, username, emailId);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(userJson);

		return json;
	}

	// Method for Rendering Approve User
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM')")
	@RequestMapping(value = "/ApproveUser/{id}", method = RequestMethod.GET)
	public ModelAndView ApproveUser(@PathVariable String id, HttpServletRequest request) {
		// System.out.println("i am inside gta 6");
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying ApproveUser", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("systemAdmin/ApproveUser");
		modelAndView.addObject("loggedInUserName", user.getUsername());
		return modelAndView;
	}

	// Method For Rendering Financial Year Page
	@RequestMapping(value = "addFinancialYear", method = RequestMethod.GET)
	private ModelAndView viewAddFinancialYear(HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying ApproveUser", user.getUsername(), user.getAuthorities());

		ModelAndView modelAndView = new ModelAndView("systemAdmin/addFinancialYear");
		modelAndView.addObject(modelAndView);
		return modelAndView;

	}

	// Method for Fetching Financial Year
	@RequestMapping(value = "fetchFinancialYearData/{id}", method = RequestMethod.GET)
	public FinancialYearBean fetchFinancialYearData(@PathVariable Long id, HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching District for Edit", user.getUsername(), user.getAuthorities());
		return systemAdminService.fetchFinancialYearData(id);
	}

	// Post Method for Adding Financial Year
	@RequestMapping(value = "/addfinancialYear", method = RequestMethod.POST)
	public ResponseObject addfinanicalYEar(@RequestBody FinancialYearBean bean) {
		ResponseObject response = new ResponseObject();

		String errorMsg = systemAdminService.addFinanicalYear(bean);
		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
		} else {
			response.setSuccessMessage("Details added successfully!");
		}

		return response;
	}

	@RequestMapping(value = "/manageWorkSubTypes", method = RequestMethod.GET)
	public ModelAndView manageWorkSubType(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Manage Work Facility Form", user.getUsername(),
				user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("systemAdmin/manageWorkSubTypes");
		modelAndView.addObject("loggedInUserName", user.getUsername());
		return modelAndView;
	}

	@GetMapping(value = "/fetchWorkSubTypes", produces = "application/json;charset=UTF-8")
	public String fetchWorkSubTypes(HttpServletRequest request) {
		String searchBoxVal = request.getParameter("searchBoxVal");
		String sSortCol = request.getParameter("iSortCol_0");
		String sSortDir = request.getParameter("sSortDir_0");
		String sColName = request.getParameter("mDataProp_" + sSortCol);
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
			sort = Sort.by(Direction.DESC, "workSubtypeId");// default sorting
		}
		Pageable pageable = PageRequest.of(pageNumber, pageDisplayLength, sort);
		WorkSubtypeJson cJson = systemAdminService.getWorkSubTypes(pageable, searchBoxVal);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(cJson);
		return json;
	}

	// addWorkSubTypes

	@RequestMapping(value = "/addWorkSubTypes", method = RequestMethod.POST)
	public ResponseObject addWorkSubTypes(@RequestBody WorkSubTypeBean bean) {
		ResponseObject response = new ResponseObject();

		String errorMsg = systemAdminService.addWorkSubTypes(bean);
		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
		} else {
			response.setSuccessMessage("Details added successfully!");
		}

		return response;
	}
	
	//addWorkSubTypes
	@RequestMapping(value = "/addWorkSubTypes", method = RequestMethod.GET)
	public ModelAndView addWorkSubTypes(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Add Work SubType Form", user.getUsername(),
				user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("systemAdmin/addWorkSubTypes");
		modelAndView.addObject("loggedInUserName", user.getUsername());
		return modelAndView;
	}
	
	
	//editWorkSubTypes
	@RequestMapping(value = "/editWorkSubTypes/{id}", method = RequestMethod.GET)
	public ModelAndView editWorkSubTypes(@PathVariable String id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Edit Work Subtype Form", user.getUsername(),
				user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("systemAdmin/editWorkSubTypes");
		modelAndView.addObject("loggedInUserName", user.getUsername());
		return modelAndView;
	}

	 // edit fetchWorkSubTypeById
		@RequestMapping(value = "fetchWorkSubTypeById/{id}", method = RequestMethod.GET)
		public WorkSubTypeBean fetchWorkSubTypeByIds(@PathVariable String id, HttpServletRequest request) {
			user = DMSUtil.getUserDetail();
			logger.info("User - {}, Role - {} - Fetching Work Sub TYPE for Edit", user.getUsername(), user.getAuthorities());
			return systemAdminService.fetchWorkSubTypeById(Long.parseLong(DMSUtil.decryptParam(id)));
		}
		
		// Delete WorkSubTypes
		@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
		@RequestMapping(value = "/deleteWorkSubTypes/{id}", method = RequestMethod.POST)
		public ResponseObject deleteWorkSubTypes(@PathVariable Long id, HttpServletRequest request) {

			user = DMSUtil.getUserDetail();
			logger.info("User - {}, Role - {} - Deleting data ", user.getUsername(), user.getAuthorities());
			ResponseObject response = new ResponseObject();
			String errorMsg = systemAdminService.deleteWorkSubTypes(id);

			if (errorMsg != null) {
				response.setErrorMessage(errorMsg);
				logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
			} else {
				response.setSuccessMessage(" deleted successfully!");
				logger.info("User - {}, Role - {} -deleted successfully!", user.getUsername(), user.getAuthorities());
			}
			return response;
		}
}
