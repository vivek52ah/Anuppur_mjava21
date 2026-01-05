package com.anuppur.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.anuppur.bean.BlockBean;
import com.anuppur.bean.DistrictBean;
import com.anuppur.bean.FinancialYearBean;
import com.anuppur.bean.GramPanchayatBean;
import com.anuppur.bean.ImplAgencyBean;
import com.anuppur.bean.SchemeBean;
import com.anuppur.bean.WorkCategoryBean;
import com.anuppur.bean.WorkSubTypeBean;
import com.anuppur.bean.WorkTypeBean;
import com.anuppur.entity.Role;
import com.anuppur.json.DistrictJson;
import com.anuppur.json.GramPanchayatJson;
import com.anuppur.json.ImplAgencyJson;
import com.anuppur.json.RoleJson;
import com.anuppur.json.SchemeJson;
import com.anuppur.json.WorkCategoryJson;
import com.anuppur.json.WorkSubtypeJson;
import com.anuppur.json.workTypeJson;


public interface SystemAdminService {


	WorkCategoryJson getWorkCategryByDistrictId(Pageable pageable, String searchBoxVal);
	

	String addWorkCategory(WorkCategoryBean userBean);
	

	WorkCategoryBean fetchWorkCatById(long parseLong);


	String deleteWorkCatById(Long id);

	RoleJson fetchRoles(Pageable pageable);
	


	SchemeJson fetchAllSchemes(Pageable pageable, String searchParam);


	List<Role> fetchRole();


	WorkCategoryJson getWorkFacility(Pageable pageable, String searchBoxVal);


	String addWorkFacility(WorkCategoryBean bean);


	WorkCategoryBean fetchWorkFacilityById(Long id);


	String deleteWorkFacility(Long id);


	WorkTypeBean fetchWorkTypeById(Long id);


	String deleteWorkSubType(Long id);


	String addWorkSubType(WorkTypeBean bean);


	workTypeJson getWorkSubType(Pageable pageable, String searchBoxVal);


	String addImplAgencyy(ImplAgencyBean bean);


	ImplAgencyJson getImplAgencyy(Pageable pageable, String searchBoxVal);


	String deleteImplAgencyy(Long id);


	ImplAgencyBean fetchImplAgencyy(Long id);


	DistrictJson getAllDistrict(Pageable pageable, String searchBoxVal);


	DistrictBean fetchDistrictDetails(Long long1);


	String addDistrict(DistrictBean bean);


	String deleteDistrict(Long id);


	GramPanchayatJson getallGrampanchayat(Pageable pageable, String searchBoxVal, String districtId, String blockId);


	BlockBean fetchBlockDetails(Long long1);


	String deleteBlock(Long id);


	GramPanchayatBean fetchGPDetails(Long long1);


	String deleteGP(Long id);


	String addblock(BlockBean bean);


	String addGP(GramPanchayatBean bean);


	String addFinanicalYear(FinancialYearBean bean);


	FinancialYearBean fetchFinancialYearData(Long id);


	WorkSubtypeJson getWorkSubTypes(Pageable pageable, String searchBoxVal);


	String addWorkSubTypes(WorkSubTypeBean bean);


	WorkSubTypeBean fetchWorkSubTypeById(long id);


	String deleteWorkSubTypes(Long id);

	
	

}
