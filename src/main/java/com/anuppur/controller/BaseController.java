package com.anuppur.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;

import com.anuppur.bean.UserBean;
import com.anuppur.bean.WorkBean;
import com.anuppur.service.UserService;
import com.anuppur.util.DMSUtil;

public class BaseController {

	@Autowired
	private UserService userService;

	public UserBean fetchLoggedInUserDetails(HttpServletRequest request) {

		User user = DMSUtil.getUserDetail();
		if (null != user) {
			return userService.fetchUserDetailsByUserName(user.getUsername());
		} else {
			return null;
		}
	}

	
}
