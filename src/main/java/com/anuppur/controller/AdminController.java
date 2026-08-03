package com.anuppur.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.util.StringUtils;

import com.anuppur.constants.DMSConstants;
import com.anuppur.entity.Role;
import com.anuppur.entity.Users;
import com.anuppur.service.AdminService;
import com.anuppur.service.CommonService;
import com.anuppur.service.UserService;
import com.anuppur.util.DMSUtil;

@RestController
@RequestMapping("/admin/*")
public class AdminController {
	
	public static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	private User user;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private CommonService commonService;
	
	@Value("${applicationDeploymentServerName}")
	private String applicationDeploymentServerName;
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView viewHome(HttpServletRequest request, Model model, Map<String, Object> model1) {
		
		Locale locale = (Locale) request.getSession().getAttribute(
				SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
		
		String language = request.getParameter("lang");
		
		if (null == locale) {
			request.getSession().setAttribute(
					SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
					Locale.of("en"));
		} else if (!StringUtils.isEmpty(language)
				&& language.equals(DMSConstants.LOCALE_HI)) {

			request.getSession().setAttribute(
					SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
					Locale.of("hi"));
		} else if (!StringUtils.isEmpty(language)
				&& language.equals(DMSConstants.LOCALE_EN)) {
			request.getSession().setAttribute(
					SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
					Locale.of("en"));
		}

		Locale updatedLocale = (Locale) request.getSession().getAttribute(
				SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
		model.addAttribute("language", updatedLocale.toString());
		user = DMSUtil.getUserDetail();
		ModelAndView modelAndView = new ModelAndView("admin/adminHome");
		
		if(user!=null){
			logger.info("User - {}, Role - {} - Displaying home page", user.getUsername(), user.getAuthorities());
			Users userEntity = userService.findByUserName(user.getUsername());
			modelAndView.addObject("loggedInUserName", userEntity.getUsername());
			
			String roleName = "";
			Set<Role> roles = userEntity.getRoles();
			if(roles!= null && !roles.isEmpty()){
				List<Role> roleList = new ArrayList<Role>(roles);
				roleName = roleList.get(0).getRoleName();
			}
			modelAndView.addObject("roleName", roleName);
			model1.put("roleName", roleName);
			
			modelAndView.addObject("district", userEntity.getDistrict()!=null ? userEntity.getDistrict().getDistrictName() : "");
		
		}
		return modelAndView;
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView viewDashboard(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying dashboard", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("admin/dashboard");
		return modelAndView;

	}
	
	@RequestMapping(value = "/workBookAndPhotos", method = RequestMethod.GET)
	public ModelAndView workBookAndPhotos(HttpServletRequest request) {

		user = DMSUtil.getUserDetail();
		logger.info("User - {}, Role - {} - Displaying Work Book And Photos page", user.getUsername(), user.getAuthorities());
		ModelAndView modelAndView = new ModelAndView("admin/workBookAndPhotos");
		return modelAndView;
	}
	
	
	
	
	
	public String getApplicationDeploymentServerName() {
		return applicationDeploymentServerName;
	}

	public void setApplicationDeploymentServerName(String applicationDeploymentServerName) {
		this.applicationDeploymentServerName = applicationDeploymentServerName;
	}
	
}
