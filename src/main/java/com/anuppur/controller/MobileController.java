package com.anuppur.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.anuppur.bean.AuthResponseBean;
import com.anuppur.bean.ReqMobileLoginBean;
import com.anuppur.bean.TSASWorkBean;
import com.anuppur.bean.UserBean;
import com.anuppur.bean.WorkBean;
import com.anuppur.bean.WorkProgressBean;
import com.anuppur.bean.WorkStatusBean;
import com.anuppur.bean.WorkSubStatusBean;
import com.anuppur.bean.WorkTenderBean;
import com.anuppur.constants.DMSConstants;
import com.anuppur.entity.Users;
import com.anuppur.entity.Work;
import com.anuppur.exception.DMSBusinessException;
import com.anuppur.repository.DesignationRepository;
import com.anuppur.response.ResponseObject;
import com.anuppur.service.CommonService;
import com.anuppur.service.UserService;
import com.anuppur.service.impl.Blacklisttoken;
import com.anuppur.util.JwtUtil;
import com.anuppur.util.SHAHashingUtil;

import java.text.ParseException;

/**
 * @author sumit
 *
 */
@RestController
@RequestMapping(value = "/mobilelogin")
public class MobileController extends BaseController {
	
	public static final Logger logger = LoggerFactory.getLogger(MobileController.class);
	private User user;
		
	
	@Autowired
	private UserService userService;
	
	
	@Autowired
	private Blacklisttoken   blacklisttoken;
	@Autowired
	private CommonService commonService;
	
	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private UserDetailsService userDetailsService;
	
    @PostMapping
    public ResponseEntity<AuthResponseBean> authenticateUser(@RequestBody ReqMobileLoginBean loginRequest) throws DMSBusinessException {
        try {
            // Authenticate the user credentials
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), SHAHashingUtil.encryptPassword(loginRequest.getPassword()))
            );
            
            // Load user details and generate JWT token
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUserName());
            String token = jwtUtil.generateToken(userDetails.getUsername());
            
            AuthResponseBean authresponse  =  new AuthResponseBean();
            authresponse.setJwttoken("Bearer "+token);
            UserBean user = userService.fetchUserDetailsByUserName(loginRequest.getUserName());
            if (null != user && null != user.getId()) {
            	 PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
 		        StringBuilder encodedPassword = com.anuppur.util.SHAHashingUtil.encryptPassword(loginRequest.getPassword());
 		        if (passwordEncoder.matches(encodedPassword, user.getPassword())) {
 		        	authresponse.setStatusCode(200L);
 		        	authresponse.setStatusDesc("Success");
 		        	//authresponse.setId(user.getId());
 		        	authresponse.setId(user.getId());
 		        	authresponse.setUserId(user.getId().toString());
 		        	authresponse.setUserIdInt(user.getId().intValue());
 		        	authresponse.setLoggedInUserRole(user.getRolee());
 		        	authresponse.setName(user.getFirstName()+" "+user.getLastName());
 		        	authresponse.setEmailAddress(user.getEmailId());
 		        	if(user.getMobileNo()!=null) {
 		        	authresponse.setMobileNo(user.getMobileNo());}
 		        	authresponse.setDepartmentNAme(user.getDepartmentName());
 		        	if(user.getDesignationId()!= null) {
 		        		var designation = designationRepository.findById(user.getDesignationId()).orElse(null);
 		        		if (designation != null) {
 		        			authresponse.setDesignation(designation.getDesignationNameEnglish());
 		        		}
 		        	}
 		        	
 		        } else {
 		        	authresponse.setStatusCode(302L);
 		        	authresponse.setStatusDesc("Invalid Password");
 		        }
            	
            } else {
            	authresponse.setStatusCode(301L);
            	authresponse.setStatusDesc("Invalid User Id");
            }
            return ResponseEntity.ok(authresponse );
        } catch (AuthenticationException e) {
            // Handle invalid credentials
            AuthResponseBean authresponse = new AuthResponseBean();
            authresponse.setUserId(loginRequest.getUserName());
            authresponse.setStatusCode(302L);
            authresponse.setStatusDesc("Invalid User Id or Password");
            return ResponseEntity.ok(authresponse);
        }
    }
    
  
    
	@RequestMapping(value = "mLogin", method = RequestMethod.POST)
	// @ApiOperation(value = "Get all users", notes = "Returns a list of users")
	public AuthResponseBean getAllWorkAssigned(HttpServletRequest request, HttpServletResponse response, @RequestBody ReqMobileLoginBean bean)
	        throws DMSBusinessException {
		logger.info("call");
		    AuthResponseBean responseBean = new AuthResponseBean();
		try {
			responseBean.setUserId(bean.getUserName());

		    UserBean user = userService.fetchUserDetailsByUserName(bean.getUserName());

		    if (null != user && null != user.getId()) {

		        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		        StringBuilder encodedPassword = com.anuppur.util.SHAHashingUtil.encryptPassword(bean.getPassword());
		        if (passwordEncoder.matches(encodedPassword, user.getPassword())) {

		        	responseBean.setStatusCode(200L);
					responseBean.setStatusDesc("Success");
					responseBean.setLoggedInUserRole(user.getRolee());
					if (null != user.getId()) {
						responseBean.setUserIdInt(user.getId().intValue());
					}
					String tokenId = UUID.randomUUID().toString();
				//	responseBean.setTokenId(tokenId);
					responseBean.setEmailAddress(user.getEmailId());
					responseBean.setName(user.getUsername());
					responseBean.setMobileNo(user.getMobileNo());
					responseBean.setId(user.getId());
					responseBean.setJsessionid(request.getSession().getId());
					//userRepository.save(userEntity);
				} else {
					responseBean.setStatusCode(302L);
					responseBean.setStatusDesc("Invalid Password");
				}
			} else {
				responseBean.setStatusCode(301L);
				responseBean.setStatusDesc("Invalid User Id");
			}	
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception in valdiating user",e);
		}
	   
	        return responseBean;
	}

	
	@PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        // Extract token from Authorization header
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Remove "Bearer " prefix

            // Blacklist the token
            blacklisttoken.blacklistToken(token);
            return ResponseEntity.ok("Logged out successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid Authorization header");
        }
    }
    
	
	
	

	
	
	
	
	
}
