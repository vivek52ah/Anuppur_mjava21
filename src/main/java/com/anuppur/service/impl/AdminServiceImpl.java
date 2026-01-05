package com.anuppur.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.anuppur.bean.RoleBean;
import com.anuppur.bean.UserBean;
import com.anuppur.constants.DMSConstants;
import com.anuppur.entity.Role;
import com.anuppur.entity.Users;
import com.anuppur.exception.DMSBusinessException;
import com.anuppur.json.UserJson;
import com.anuppur.repository.RoleRepository;
import com.anuppur.repository.UserRepository;
import com.anuppur.service.AdminService;
import com.anuppur.service.NotificationService;
import com.anuppur.util.SHAHashingUtil;

@Service
public class AdminServiceImpl implements AdminService {
	
	public static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
	
	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	public RoleRepository roleRepository; 
	
	@Autowired
	private NotificationService notificationService;
	
}
