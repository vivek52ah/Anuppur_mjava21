package com.anuppur.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.util.StringUtils;

import com.anuppur.bean.RoleBean;
import com.anuppur.bean.UserBean;
import com.anuppur.constants.DMSConstants;
import com.anuppur.entity.Role;
import com.anuppur.entity.Users;
import com.anuppur.json.UserJson;
import com.anuppur.response.ResponseObject;
import com.anuppur.service.SuperAdminService;
import com.anuppur.service.UserService;
import com.anuppur.util.DMSUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@RequestMapping("/superAdmin")
@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM','ROLE_SU','ROLE_DEPARTMENT')")
public class SuperAdminController extends BaseController {

	public static final Logger logger = LoggerFactory.getLogger(SuperAdminController.class);

	private User user;

	@Autowired
	private UserService userService;

	@Autowired
	private SuperAdminService superAdminService;

	@Value("${applicationDeploymentServerName}")
	private String applicationDeploymentServerName;

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
		ModelAndView modelAndView = new ModelAndView("superAdmin/superAdminHome");

		if (user != null) {
			logger.info("User - {}, Role - {} - Displaying home page", user.getUsername(), user.getAuthorities());
			Users userEntity = userService.findByUserName(user.getUsername());
			modelAndView.addObject("loggedInUserName", userEntity.getUsername());

			String roleName = "";
			Set<Role> roles = userEntity.getRoles();
			if (roles != null && !roles.isEmpty()) {
				List<Role> roleList = new ArrayList<Role>(roles);
				roleName = roleList.get(0).getRoleName();
			}
			//System.out.println("USERLOGIN................" + roleName);
			modelAndView.addObject("roleName", roleName);

			modelAndView.addObject("district",
					userEntity.getDistrict() != null ? userEntity.getDistrict().getDistrictName() : "");
			modelAndView.addObject("name", userEntity.getUsername());
		}
		return modelAndView;
	}

	/*
	 * @RequestMapping(value = "/dashboard", method = RequestMethod.GET) public
	 * ModelAndView viewDashboard(HttpServletRequest request) {
	 * 
	 * user = DMSUtil.getUserDetail();
	 * logger.info("User - {}, Role - {} - Displaying dashboard",
	 * user.getUsername(), user.getAuthorities()); ModelAndView modelAndView = new
	 * ModelAndView("superAdmin/dashboard"); return modelAndView;
	 * 
	 * }
	 */
	
	
	
	
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_SU','ROLE_DEPARMENT')")
	@RequestMapping(value = "/manageusers", method = RequestMethod.GET)
	public ModelAndView manageUsersView(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Manage Users page", user.getUsername(), user.getAuthorities());

		ModelAndView modelAndView = new ModelAndView("superAdmin/manageUsers");
		UserBean userBean = fetchLoggedInUserDetails(request);
		modelAndView.addObject("roleName", userBean.getLoggedInUserRole());
		return modelAndView;
	}
	
	
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DEPARTMENT','ROLE_DEPT_DISTRICT')")
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashBoardView(HttpServletRequest request) {
		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Manage WorkCategory page", user.getUsername(),
				user.getAuthorities());

		return new ModelAndView("superAdmin/dashboard");
	}

	/*
	 * @RequestMapping(value = "/fetchUserList", method = RequestMethod.GET,
	 * produces = "application/json;charset=UTF-8") public String
	 * fetchUserList(HttpServletRequest request) {
	 * 
	 * user = DMSUtil.getUserDetail();
	 * logger.info("User - {}, Role - {} - Fetching User List", user.getUsername(),
	 * user.getAuthorities()); String searchBoxVal =
	 * request.getParameter("searchBoxVal"); String roleCode =
	 * request.getParameter("roleCode"); String status =
	 * request.getParameter("status"); String username =
	 * request.getParameter("username");
	 * 
	 * String emailId = request.getParameter("emailId"); String sSortCol =
	 * request.getParameter("iSortCol_0"); String sSortDir =
	 * request.getParameter("sSortDir_0"); String sColName =
	 * request.getParameter("mDataProp_" + sSortCol);
	 * 
	 * // Fetch the page number from client Integer pageNumber = 0;
	 * 
	 * // Fetch search parameter String searchParameter =
	 * request.getParameter("sSearch");
	 * 
	 * // Fetch Page display length Integer pageDisplayLength =
	 * Integer.valueOf(request .getParameter("iDisplayLength"));
	 * 
	 * if (null != request.getParameter("iDisplayStart")) { pageNumber = (Integer
	 * .valueOf(request.getParameter("iDisplayStart")) / pageDisplayLength); }
	 * 
	 * Sort sort = null; if(sColName!=null){ if(StringUtils.equals("asc",
	 * sSortDir)){ sort = Sort.by(Direction.ASC, sColName); }else{
	 * sort = Sort.by(Direction.DESC, sColName); } }else{ sort =
	 * Sort.by(Direction.DESC, "id");//default sorting }
	 * 
	 * Pageable pageable = PageRequest.of(pageNumber, pageDisplayLength, sort);
	 * 
	 * UserJson userJson = superAdminService.getAllUsers(pageable, searchBoxVal,
	 * status,username,emailId);
	 * 
	 * Gson gson = new GsonBuilder().setPrettyPrinting().create(); String json =
	 * gson.toJson(userJson);
	 * 
	 * return json; }
	 */
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_SU','ROLE_DEPARTMENT')")
	@RequestMapping(value = "/addUserForm", method = RequestMethod.GET)
	public ModelAndView viewAddUserForm(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Add User Form", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("superAdmin/addUserForm");
		return modelAndView;
	}
	


	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_SU','ROLE_DEPARTMENT')")
	@RequestMapping(value = "fetchRoles", method = RequestMethod.GET)
	public List<RoleBean> fetchRoles(HttpServletRequest request) {
		return superAdminService.fetchRoles();
	}
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_SU','ROLE_DEPARTMENT')")
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public ResponseObject addUser(@RequestBody UserBean userBean, HttpServletRequest request) throws Exception {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Adding User data", user.getUsername(), user.getAuthorities());
		ResponseObject response = new ResponseObject();

		String errorMsg = superAdminService.addUser(userBean);
		if (errorMsg != null) {
			response.setErrorMessage(errorMsg);
			logger.error("User - {}, Role - {} - {}", user.getUsername(), user.getAuthorities(), errorMsg);
		} else {
			response.setSuccessMessage("User added successfully!");
			logger.info("User - {}, Role - {} - User added successfully!", user.getUsername(), user.getAuthorities());
		}
		return response;
	}
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_SU','ROLE_DEPARTMENT')")
	@RequestMapping(value = "/editUserForm/{id}", method = RequestMethod.GET)
	public ModelAndView viewEditUserForm(@PathVariable String id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Edit User Form", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("superAdmin/editUserForm");
		modelAndView.addObject("loggedInUserName", user.getUsername());
		return modelAndView;
	}
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_SU','ROLE_DEPARTMENT')")
	@RequestMapping(value = "fetchUserDetails/{id}", method = RequestMethod.GET)
	public UserBean fetchUserDetails(@PathVariable String id, HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Fetching User data", user.getUsername(), user.getAuthorities());
		return superAdminService.fetchUserDetails(Long.parseLong(DMSUtil.decryptParam(id)));
	}
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_SU','ROLE_DEPARTMENT')")
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
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_SU','ROLE_DEPARTMENT')")
	@RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.GET)
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

	public String getApplicationDeploymentServerName() {
		return applicationDeploymentServerName;
	}

	public void setApplicationDeploymentServerName(String applicationDeploymentServerName) {
		this.applicationDeploymentServerName = applicationDeploymentServerName;
	}
	
	
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_SU')")
	@RequestMapping(value = "/manageAgencyUsers", method = RequestMethod.GET)
	public ModelAndView manageAgencyUsersView(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Manage Users page", user.getUsername(), user.getAuthorities());

		ModelAndView modelAndView = new ModelAndView("superAdmin/manageAgencyUsers");
		UserBean userBean = fetchLoggedInUserDetails(request);
		modelAndView.addObject("roleName", userBean.getLoggedInUserRole());
		return modelAndView;
	}
	
}
