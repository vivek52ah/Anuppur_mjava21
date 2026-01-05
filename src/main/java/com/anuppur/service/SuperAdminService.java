package com.anuppur.service;

import java.util.List;

import org.springframework.data.domain.Pageable;


import com.anuppur.bean.ImplAgencyBean;
import com.anuppur.bean.OfficeTypeBean;
import com.anuppur.bean.RoleBean;
import com.anuppur.bean.UserBean;
import com.anuppur.bean.UserTypeBean;
import com.anuppur.bean.WorkBean;
import com.anuppur.entity.Designation;
import com.anuppur.entity.ImplementationAgency;
import com.anuppur.entity.Users;
import com.anuppur.entity.Work;
import com.anuppur.json.UserJson;

public interface SuperAdminService {


	UserBean fetchUserDetails(Long id);
   
	List<RoleBean> fetchRoles();
	
	List<UserTypeBean> fetchUserType();
	List<OfficeTypeBean> fetchOfficeType();
	List<ImplAgencyBean> fetchConstructionAgency();
	
	List<OfficeTypeBean> fetchOfficeTypesByUserType(Long userTypeId);
	List<RoleBean>fetchRoleTypesByUserOfficeType(Long userTypeId,Long officeTypeId);

	String addUser(UserBean bean);

	String editUser(UserBean bean, String websiteURL);

	String deleteUser(Long id);
	
	UserJson getAllUsers(Pageable pageable,UserBean bean, String searchBoxVal,String mobileNo,  String status, String username,
			String emailId);

	UserBean convertUserEntityToBean(Users user);
	UserJson getAllAgencyusers(Pageable pageable, String searchParameter, String status, String username,
			String emailId);
	
	List<Designation> fetchDesignation(UserBean userBean);
	UserJson fetchUserListForApproval(Pageable pageable, UserBean fetchLoggedInUserDetails, String searchBoxVal,
			String status, String username, String emailId);
	
}
