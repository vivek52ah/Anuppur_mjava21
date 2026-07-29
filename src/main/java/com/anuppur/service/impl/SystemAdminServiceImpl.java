package com.anuppur.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.anuppur.bean.BlockBean;
import com.anuppur.bean.DistrictBean;
import com.anuppur.bean.FinancialYearBean;
import com.anuppur.bean.GramPanchayatBean;
import com.anuppur.bean.ImplAgencyBean;
import com.anuppur.bean.RoleBean;
import com.anuppur.bean.SchemeBean;
import com.anuppur.bean.WorkCategoryBean;
import com.anuppur.bean.WorkSubTypeBean;
import com.anuppur.bean.WorkTypeBean;
import com.anuppur.constants.DMSConstants;
import com.anuppur.entity.Block;
import com.anuppur.entity.District;
import com.anuppur.entity.Division;
import com.anuppur.entity.FinancialYear;
import com.anuppur.entity.GramPanchayat;
import com.anuppur.entity.ImplementationAgency;
import com.anuppur.entity.Role;
import com.anuppur.entity.Schemes;
import com.anuppur.entity.WorkCategory;
import com.anuppur.entity.WorkSubType;
import com.anuppur.entity.WorkType;
import com.anuppur.json.DistrictJson;
import com.anuppur.json.GramPanchayatJson;
import com.anuppur.json.ImplAgencyJson;
import com.anuppur.json.RoleJson;
import com.anuppur.json.SchemeJson;
import com.anuppur.json.WorkCategoryJson;
import com.anuppur.json.WorkSubtypeJson;
import com.anuppur.json.workTypeJson;
import com.anuppur.repository.BlockRepository;
import com.anuppur.repository.DistrictRepository;
import com.anuppur.repository.DivisionRepository;
import com.anuppur.repository.FinancialYearRepository;
import com.anuppur.repository.GramPanchayatRepository;
import com.anuppur.repository.ImplAgencyRepository;
import com.anuppur.repository.LegislativeConsRepository;
import com.anuppur.repository.RoleRepository;
import com.anuppur.repository.SchemeRepository;
import com.anuppur.repository.UserRepository;
import com.anuppur.repository.WorkCategoryRepository;
import com.anuppur.repository.WorkSubTypeRepository;
import com.anuppur.repository.WorkTypeRepository;
import com.anuppur.response.ResponseObject;
import com.anuppur.service.SystemAdminService;
import com.anuppur.util.DMSUtil;

@Service
public class SystemAdminServiceImpl implements SystemAdminService {

	public static final Logger logger = LoggerFactory.getLogger(SystemAdminServiceImpl.class);

	@Autowired
	private DivisionRepository divisionRepository;

	@Autowired
	public UserRepository userRepository;

	@Autowired
	public RoleRepository roleRepository;

	@Autowired
	public SchemeRepository schemeRepository;

	@Autowired
	public LegislativeConsRepository legislativeConsRepository;

	@Autowired
	public GramPanchayatRepository gramPanchayatRepository;

	@Autowired
	private WorkCategoryRepository workCategoryRepository;

	@Autowired
	private DistrictRepository districtRepository;

	@Autowired
	private WorkSubTypeRepository workSubTypeRepository;

	@Autowired
	private ImplAgencyRepository implAgecyRepository;

	@Autowired
	private BlockRepository blockRepository;

	@Autowired
	private WorkTypeRepository workTypeRepository;

	@Autowired
	private FinancialYearRepository financialYearRepository;

	@Override
	public SchemeJson fetchAllSchemes(Pageable pageable, String searchParam) {
		SchemeJson schemeJson = null;
		try {
			Page<Schemes> schemes = null;

			if (searchParam != null && !searchParam.isEmpty()) {
				schemes = schemeRepository.findBySchemeNameContainingAndEnabled(pageable, searchParam, (short) 1);
			} else
				schemes = schemeRepository.findByEnabled(DMSConstants.ENABLED, pageable);

			if (schemes != null) {
				List<Schemes> entityList = schemes.getContent();
				List<SchemeBean> beanList = new ArrayList<>();
				if (entityList != null && !entityList.isEmpty()) {

					int index = pageable.getPageNumber() * pageable.getPageSize();
					for (Schemes scheme : entityList) {

						SchemeBean bean = convertSchemeEntityToBean(scheme);
						bean.setIndex(++index);
						beanList.add(bean);
					}
				}
				schemeJson = new SchemeJson();
				schemeJson.setiTotalDisplayRecords(schemes.getTotalElements());
				schemeJson.setiTotalRecords(schemeRepository.countByEnabled((short) 1));
				schemeJson.setAaData(beanList);
			}
			return schemeJson;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return schemeJson;
		}
	}

	private SchemeBean convertSchemeEntityToBean(Schemes scheme) {

		SchemeBean bean = new SchemeBean();
		if (scheme != null) {
			bean.setSchemeId(scheme.getId());
			bean.setSchemeNameE(scheme.getSchemeName());
			bean.setSchemeCode(scheme.getSchemeCode());
		}
		return bean;
	}

	@Override
	public WorkCategoryJson getWorkCategryByDistrictId(Pageable pageable, String searchParameter) {
		WorkCategoryJson workCategoryJson = null;

		try {
			Page<WorkCategory> workCategory = null;

			if (!StringUtils.isEmpty(searchParameter))
				workCategory = workCategoryRepository.findByWorkCategoryNameEContainingAndEnabled(pageable,
						searchParameter, (short) 1);
			else
				workCategory = workCategoryRepository.findByEnabled(pageable, (short) 1);

			if (workCategory != null) {
				List<WorkCategory> entityList = workCategory.getContent();
				List<WorkCategoryBean> beanList = new ArrayList<>();
				if (entityList != null && !entityList.isEmpty()) {

					int index = pageable.getPageNumber() * pageable.getPageSize();
					for (WorkCategory wrkCategory : entityList) {
						WorkCategoryBean bean = convertWorkCategoryEntityToBean(wrkCategory);
						bean.setIndex(++index);
						beanList.add(bean);
					}
				}
				workCategoryJson = new WorkCategoryJson();
				workCategoryJson.setiTotalDisplayRecords(workCategory.getTotalElements());
				workCategoryJson.setiTotalRecords(workCategoryRepository.countByEnabled((short) 1));
				workCategoryJson.setAaData(beanList);
			}
			return workCategoryJson;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return workCategoryJson;
		}
	}

	private WorkCategoryBean convertWorkCategoryEntityToBean(WorkCategory entity) {
		WorkCategoryBean bean = new WorkCategoryBean();
		if (entity != null) {
			bean.setWorkCategoryId(entity.getWorkCategoryId());
			bean.setWorkCategoryNameE(entity.getWorkCategoryNameE());
			bean.setWorkCategoryNameH(entity.getWorkCategoryNameH());
			// bean.setDistrictBean(convertDistrictEntityToBean(entity.getDistrict()));
			bean.setWorkTypeBean(convertWorkTypeEntityToBean(entity.getWorkType()));
			bean.setEnabled(entity.getEnabled());
		}
		return bean;
	}

	private WorkTypeBean convertWorkTypeEntityToBean(WorkType entity) {

		WorkTypeBean bean = new WorkTypeBean();
		if (entity != null) {
			bean.setWorkTypeId(entity.getWorkTypeId());
			bean.setWorkTypeNameE(entity.getWorkTypeNameE());
			bean.setWorkTypeNameH(entity.getWorkTypeNameH());
			bean.setEnabled(entity.getEnabled());
		}
		return bean;
	}

	@Override
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public String addWorkCategory(WorkCategoryBean workCategoryBean) {

		try {

			WorkCategory entity = workCategoryRepository
					.findByworkCategoryNameEAndEnabled(workCategoryBean.getWorkCategoryNameE(), (short) 1);
			if (entity != null) {
				return "Work Category with given Name Already exist!";
			} else {
				entity = new WorkCategory();
				entity = convertWorkCategoryBeanToEntity(entity, workCategoryBean);

				workCategoryRepository.save(entity);
				return null;
			}
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return DMSConstants.ERROR_SAVING_DATA;
		}
	}

	private WorkCategory convertWorkCategoryBeanToEntity(WorkCategory entity, WorkCategoryBean workCategoryBean) {
		entity.setWorkCategoryNameE(workCategoryBean.getWorkCategoryNameE());
		// entity.setWorkCategoryNameH(workCategoryBean.getWorkCategoryNameH());
		entity.setEnabled(DMSConstants.ENABLED);
		// entity.setDistrict(new
		// District(workCategoryBean.getDistrictBean().getDistrictId()));
		// entity.setWorkType(workTypeRepository.findOne(workCategoryBean.getWorkTypeBean().getWorkTypeId()));
		if (null != workCategoryBean.getWorkCategoryId()) {
			entity.setWorkCategoryId(workCategoryBean.getWorkCategoryId());
		}
		return entity;
	}

	@Override
	public WorkCategoryBean fetchWorkCatById(long id) {
		try {
			WorkCategory entity = workCategoryRepository.findById(id).orElse(null);

			return convertWorkCategoryEntityToBean(entity);
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	@Override
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public String deleteWorkCatById(Long id) {
		try {
			WorkCategory entity = workCategoryRepository.findById(id).orElse(null);
			if (entity != null) {
				entity.setEnabled((short) 0);
				workCategoryRepository.save(entity);
			}
			return null;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return DMSConstants.ERROR_DELETING_DATA;
		}
	}

	private RoleBean convertRoleEntityToBean(Role role) {

		RoleBean bean = new RoleBean();

		if (role != null) {
			bean.setRoleCode(role.getRoleCode());
			bean.setRoleName(role.getRoleName());
		}
		return bean;
	}

	@Override
	public RoleJson fetchRoles(Pageable pageable) {

		RoleJson roleJson = null;
		try {
			Page<Role> roles = null;

			roles = roleRepository.findAll(pageable);

			if (roles != null) {
				List<Role> entityList = roles.getContent();
				List<RoleBean> beanList = new ArrayList<>();
				if (entityList != null && !entityList.isEmpty()) {

					int index = pageable.getPageNumber() * pageable.getPageSize();
					for (Role role : entityList) {

						if (role.getRoleCode().equals(DMSConstants.ROLE_SYSTEM_ADMIN)
						// || role.getRoleCode().equals(DMSConstants.ROLE_SUPER_ADMIN)
						// || role.getRoleCode().equals(DMSConstants.ROLE_HQ)
						// || role.getRoleCode().equals(DMSConstants.ROLE_DEPARTMENT)
						) {
							continue;
						}
						RoleBean bean = convertRoleEntityToBean(role);
						bean.setIndex(++index);
						beanList.add(bean);
					}
				}
				roleJson = new RoleJson();
				roleJson.setiTotalDisplayRecords(roles.getTotalElements() - 2);
				roleJson.setiTotalRecords(roleRepository.count() - 2);
				roleJson.setAaData(beanList);
			}
			return roleJson;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return roleJson;
		}
	}

	private Schemes convertSchemeBeanToEntity(Schemes entity, SchemeBean schemeBean) {
		entity.setSchemeName(schemeBean.getSchemeNameE());
		entity.setSchemeCode(schemeBean.getSchemeCode());
		entity.setEnabled(DMSConstants.ENABLED);
//		if(null!=schemeBean.getId()) {
//			entity.setId(schemeBean.getId());
//		}
		return entity;
	}

	@Override
	public List<Role> fetchRole() {
		return roleRepository.findAll();
	}

	@Override
	public WorkCategoryJson getWorkFacility(Pageable pageable, String searchParameter) {
		WorkCategoryJson categoryJson = null;
		try {
			Page<WorkCategory> category = null;

			if (!StringUtils.isEmpty(searchParameter)) {
				category = workCategoryRepository.findByworkCategoryNameEContainingAndEnabled(pageable, searchParameter,
						DMSConstants.ENABLED);
			} else {
				category = workCategoryRepository.findByEnabled(pageable, DMSConstants.ENABLED);
			}

			if (category != null) {
				List<WorkCategory> entityList = category.getContent();
				List<WorkCategoryBean> beanList = new ArrayList<>();
				if (entityList != null && !entityList.isEmpty()) {
					int index = pageable.getPageNumber() * pageable.getPageSize();
					for (WorkCategory wEnt : entityList) {
						WorkCategoryBean bean = convertWorkCategoryEntityToBean(wEnt);
						bean.setIndex(++index);
						beanList.add(bean);
					}
				}
				categoryJson = new WorkCategoryJson();
				categoryJson.setiTotalDisplayRecords(category.getTotalElements());
				categoryJson.setiTotalRecords(workCategoryRepository.count());
				categoryJson.setAaData(beanList);
			}
			return categoryJson;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return categoryJson;
		}
	}

	// add

	@Override
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public String addWorkFacility(WorkCategoryBean bean) {
		if (bean == null) {
			return "Invalid data provided";
		}
		try {

			WorkCategory entity = convertWorkCategoryBeanToEntity(new WorkCategory(), bean);
			boolean exists = workCategoryRepository.existsByWorkCategoryNameE(bean.getWorkCategoryNameE());
			if (exists) {
				return "A work category with this name already exists";
			}
			WorkCategory savedEntity = workCategoryRepository.save(entity);

			if (savedEntity != null) {
				logger.info("Entry saved successfully");
				return null;
			} else {
				return "Error occurred while saving data";
			}
		} catch (Exception e) {
			return "Error occurred while saving data: " + e.getMessage();
		}
	}

//edit

	@Override
	public WorkCategoryBean fetchWorkFacilityById(Long id) {

		try {
			WorkCategory entity = workCategoryRepository.findById(id).orElse(null);
			return convertWorkCategoryEntityToBean(entity);
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}

	}

//delete
	@Override
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public String deleteWorkFacility(Long id) {

		try {
			WorkCategory entity = workCategoryRepository.findById(id).orElse(null);
			if (entity != null) {
				entity.setEnabled((short) 0);
				workCategoryRepository.save(entity);
			}
			return null;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return DMSConstants.ERROR_DELETING_DATA;
		}

	}

	@Override
	public WorkTypeBean fetchWorkTypeById(Long id) {
		try {
			WorkType entity = workTypeRepository.findById(id).orElse(null);
			return convertWorkTypeEntityToBean(entity);
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	private WorkSubTypeBean convertWorkSubTypeEntityToBean(WorkSubType entity) {
		WorkSubTypeBean bean = new WorkSubTypeBean();
		bean.setWorkSubTypeId(entity.getWorkSubtypeId());//workSubTypeId
		bean.setWorkSubTypeNameE(entity.getWorkSubTypeNameE());
		bean.setWorkSubTypeNameH(entity.getWorkSubTypeNameH());
		bean.setEnabled(DMSConstants.ENABLED);
		return bean;
	}

	// delete
	@Override
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public String deleteWorkSubType(Long id) {

		try {
			WorkType entity = workTypeRepository.findById(id).orElse(null);
			if (entity != null) {
				entity.setEnabled((short) 0);
				workTypeRepository.save(entity);
			}
			return null;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return DMSConstants.ERROR_DELETING_DATA;
		}

	}

	@Override
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public String addWorkSubType(WorkTypeBean bean) {
		if (bean == null) {
			return "Invalid data provided";
		}
		try {
			WorkType entity = new WorkType();

			if (bean.getWorkTypeId() != null) {

				entity = workTypeRepository.findById(bean.getWorkTypeId()).orElse(null);

			}

			entity = convertWorkTypeBeanToEntity(entity, bean);
			boolean exists = workTypeRepository.existsByworkTypeNameEAndEnabled(bean.getWorkTypeNameE(),
					DMSConstants.ENABLED);
			if (exists) {
				return "A work type with this name already exists";
			}

			WorkType savedEntity = workTypeRepository.save(entity);

			if (savedEntity != null) {
				logger.info("Entry saved successfully");
				return null;
			} else {
				return "Error occurred while saving data";
			}
		} catch (Exception e) {

			logger.error("Error Occurred during saving Data", e);
			return "Error occurred while saving data: " + e.getMessage();
		}
	}

	private WorkType convertWorkTypeBeanToEntity(WorkType workType, WorkTypeBean bean) {

		workType.setEnabled(DMSConstants.ENABLED);
		workType.setWorkTypeNameE(bean.getWorkTypeNameE());
		workType.setWorkTypeNameH(bean.getWorkTypeNameH());

		return workType;
	}

	private WorkSubType convertWorkSubTypeBeanToEntity(WorkSubType entity, WorkSubTypeBean bean) {
		entity.setWorkSubtypeId(bean.getWorkSubTypeId());
		entity.setWorkSubTypeNameE(bean.getWorkSubTypeNameE());
		entity.setEnabled(DMSConstants.ENABLED);
		return entity;
	}

	@Override
	public workTypeJson getWorkSubType(Pageable pageable, String searchParameter) {
		workTypeJson worktypejson = null;
		try {
			Page<WorkType> workType = null;

			if (!StringUtils.isEmpty(searchParameter)) {
				workType = workTypeRepository.findByWorkTypeNameEContainingAndEnabled(pageable, searchParameter,
						DMSConstants.ENABLED);
			} else {
				workType = workTypeRepository.findByEnabled(pageable, DMSConstants.ENABLED);
			}

			if (workType != null) {
				List<WorkType> entityList = workType.getContent();
				List<WorkTypeBean> beanList = new ArrayList<>();
				if (entityList != null && !entityList.isEmpty()) {
					int index = pageable.getPageNumber() * pageable.getPageSize();
					for (WorkType wEnt : entityList) {
						WorkTypeBean bean = convertWorkTypeEntityToBean(wEnt);
						bean.setIndex(++index);
						beanList.add(bean);
					}
				}
				worktypejson = new workTypeJson();
				worktypejson.setiTotalDisplayRecords(workType.getTotalElements());
				worktypejson.setiTotalRecords(workTypeRepository.count());
				worktypejson.setAaData(beanList);
			}
			return worktypejson;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return worktypejson;
		}
	}

	@Override
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public String addImplAgencyy(ImplAgencyBean bean) {
		if (bean == null) {
			return "Invalid data provided";
		}
		try {

			ImplementationAgency entity = convertImplAgencyBeanToEntity(new ImplementationAgency(), bean);
			boolean exists = implAgecyRepository.existsByimplAgencynameAndEnabled(bean.getImplementationAgencyNameE(),
					(short) 1);
			if (exists) {
				return "A Impl Agency with this name already exists";
			}
			ImplementationAgency savedEntity = implAgecyRepository.save(entity);

			if (savedEntity != null) {
				logger.info("Entry saved successfully");
				return null;
			} else {
				return "Error occurred while saving data";
			}
		} catch (Exception e) {
			return "Error occurred while saving data: " + e.getMessage();
		}
	}

	private ImplementationAgency convertImplAgencyBeanToEntity(ImplementationAgency entity, ImplAgencyBean bean) {
		entity.setImplementationAgencyId(bean.getImplementationAgencyId());
		entity.setImplAgencyname(bean.getImplementationAgencyNameE());
		entity.setEnabled(DMSConstants.ENABLED);
		return entity;
	}

	@Override
	public ImplAgencyJson getImplAgencyy(Pageable pageable, String searchBoxVal) {
		ImplAgencyJson agencyJson = null;
		try {
			Page<ImplementationAgency> agencytype = null;

			if (!StringUtils.isEmpty(searchBoxVal)) {
				agencytype = implAgecyRepository.findByimplAgencynameContainingAndEnabled(pageable, searchBoxVal,
						DMSConstants.ENABLED);
			} else {
				agencytype = implAgecyRepository.findByEnabled(pageable, DMSConstants.ENABLED);
			}

			if (agencytype != null) {
				List<ImplementationAgency> entityList = agencytype.getContent();
				List<ImplAgencyBean> beanList = new ArrayList<>();
				if (entityList != null && !entityList.isEmpty()) {
					int index = pageable.getPageNumber() * pageable.getPageSize();
					for (ImplementationAgency wEnt : entityList) {
						ImplAgencyBean bean = convertImplAgencyEntityToBean(wEnt);
						bean.setIndex(++index);
						beanList.add(bean);
					}
				}
				agencyJson = new ImplAgencyJson();
				agencyJson.setiTotalDisplayRecords(agencytype.getTotalElements());
				agencyJson.setiTotalRecords(implAgecyRepository.count());
				agencyJson.setAaData(beanList);
			}
			return agencyJson;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return agencyJson;
		}
	}

	private ImplAgencyBean convertImplAgencyEntityToBean(ImplementationAgency wEnt) {
		ImplAgencyBean bean = new ImplAgencyBean();
		bean.setImplementationAgencyId(wEnt.getImplementationAgencyId());
		bean.setImplementationAgencyNameE(wEnt.getImplAgencyname());
		bean.setEnabled(DMSConstants.ENABLED);
		return bean;
	}

	@Override
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public String deleteImplAgencyy(Long id) {
		try {
			ImplementationAgency entity = implAgecyRepository.findById(id).orElse(null);
			if (entity != null) {
				entity.setEnabled((short) 0);
				implAgecyRepository.save(entity);
			}
			return null;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return DMSConstants.ERROR_DELETING_DATA;
		}

	}

	@Override
	public ImplAgencyBean fetchImplAgencyy(Long id) {
		try {
			ImplementationAgency entity = implAgecyRepository.findById(id).orElse(null);
			return convertImplAgencyEntityToBean(entity);
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	@Override
	public DistrictJson getAllDistrict(Pageable pageable, String searchParameter) {
		DistrictJson districtJson = null;
		try {
			Page<District> district = null;

			if (!StringUtils.isEmpty(searchParameter)) {
				district = districtRepository.getByDistrictNameContainingAndEnabledAndDivisionBetween(pageable,
						searchParameter, DMSConstants.ENABLED, new Division(1L), new Division(9L));
			} else {
				Division division = new Division();
				district = districtRepository.getAllByEnabledAndDivisionBetween(pageable, DMSConstants.ENABLED,
						new Division(1L), new Division(9L));
			}

			if (district != null) {
				List<District> entityList = district.getContent();
				List<DistrictBean> beanList = new ArrayList<>();
				if (entityList != null && !entityList.isEmpty()) {
					int index = pageable.getPageNumber() * pageable.getPageSize();
					for (District wEnt : entityList) {
						DistrictBean bean = convertDistrictEntityToBean(wEnt);
						bean.setIndex(++index);
						beanList.add(bean);
					}
				}
				districtJson = new DistrictJson();
				districtJson.setiTotalDisplayRecords(district.getTotalElements());
				districtJson.setiTotalRecords(workSubTypeRepository.count());
				districtJson.setAaData(beanList);
			}
			return districtJson;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return districtJson;
		}
	}

	private DistrictBean convertDistrictEntityToBean(District wEnt) {
		DistrictBean bean = new DistrictBean();

		bean.setDistrictCode(wEnt.getDistrictCode());
		bean.setDistrictId(wEnt.getDistrictId());
		bean.setDistrictName(wEnt.getDistrictName());
		bean.setDistrictNameH(wEnt.getDistrictNameH());
		bean.setEnabled(wEnt.getEnabled());
		if (wEnt.getDivision() != null) {
			bean.setDivisionId(wEnt.getDivision().getDivisionId());

		}

		return bean;
	}

	@Override
	public DistrictBean fetchDistrictDetails(Long long1) {
		try {
			return convertDistrictEntityToBean(districtRepository.findById(long1).orElse(null));
		} catch (Exception e) {
			// TODO: handle exception

			return null;
		}

	}

	private BlockBean convertBlockEntityToBean(Block entity) {

		BlockBean bean = new BlockBean();

		if (entity != null) {
			bean.setBlockId(entity.getBlockId());
			bean.setBlockName(entity.getBlockName());
			bean.setBlockNameH(entity.getBlockNameH());
			bean.setEnabled(entity.getEnabled());
			bean.setDistrictCode(entity.getDistrict().getDistrictCode());
			bean.setBlockCode(entity.getBlockCode());
			if (!entity.getDistrict().getDistrictCode().isEmpty()) {
				bean.setDistrict(convertDistrictEntityToBean(
						districtRepository.findByDistrictCode(entity.getDistrict().getDistrictCode())));
			}
		}
		return bean;

	}

	@Override
	public BlockBean fetchBlockDetails(Long long1) {
		try {
			return convertBlockEntityToBean(blockRepository.findById(long1).orElse(null));
		} catch (Exception e) {
			// TODO: handle exception

			return null;
		}
	}

	@Override
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public String addDistrict(DistrictBean bean) {
		try {

			if (bean.getDistrictId() != null) {

				District entity = convertDistrictBEanToEntity(districtRepository.findById(bean.getDistrictId()).orElse(null), bean);
				System.out.print(bean.getDistrictCode() + "   adhued   " + bean.getDistrictName());
				District savedEntity = districtRepository.save(entity);

				if (savedEntity != null) {
					logger.info("Entry saved successfully");
					return null;
				} else {
					return "Error occurred while saving data";
				}

			} else {
				if (districtRepository.findByDistrictCodeAndEnabled(bean.getDistrictCode(),
						DMSConstants.ENABLED) != null) {

					return "District  Code Already Available";
				}
				if (districtRepository.findByDistrictNameAndEnabled(bean.getDistrictName(),
						DMSConstants.ENABLED) != null) {

					return "District  Name Already Available";
				}

				District entity = convertDistrictBEanToEntity(new District(), bean);

				District savedEntity = districtRepository.save(entity);
				if (savedEntity != null) {
					logger.info("Entry saved successfully");
					return null;
				} else {
					return "Error occurred while saving data";
				}
			}

		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return DMSConstants.ERROR_SAVING_DATA;
		}
	}

	private District convertDistrictBEanToEntity(District district, DistrictBean bean) {

		try {

			byte[] bytes = bean.getDistrictNameH().getBytes("ISO-8859-1");

			district.setDistrictNameH(new String(bytes, StandardCharsets.UTF_8));

			district.setDistrictCode(bean.getDistrictCode());
			district.setDistrictName(bean.getDistrictName());
			district.setDistrictNameH(bean.getDistrictNameH());
			district.setEnabled(DMSConstants.ENABLED);
			logger.info(bean.getDivisionId() + "this is not for the f");
			district.setDivision(divisionRepository.findById(bean.getDivisionId()).orElse(null));

		} catch (Exception e) {

			logger.error("Error COnverting BEan to Entity", e);
		}

		return district;
	}

	@Override
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public String deleteDistrict(Long id) {
		try {
			District entity = districtRepository.findById(id).orElse(null);
			if (entity != null) {
				entity.setEnabled((short) 0);
				districtRepository.save(entity);
			}
			return null;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return DMSConstants.ERROR_DELETING_DATA;
		}

	}

	@Override
	public GramPanchayatJson getallGrampanchayat(Pageable pageable, String searchParameter, String districtid,
			String blockId) {
		GramPanchayatJson gpjson = null;
		try {
			Page<GramPanchayat> gp = null;

			if (districtid == null) {
				districtid = "";
			}

			if (blockId == null) {
				blockId = "";
			}
			if (searchParameter == null) {
				searchParameter = "";
			}

			logger.info(searchParameter + "ad" + districtid + "adss" + blockId);

			if ((districtid != null && !districtid.isEmpty()) && (blockId != null && !blockId.isEmpty())) {

				if (searchParameter != null && !searchParameter.isEmpty()) {

					gp = gramPanchayatRepository.findByGramPanchayatNameAndDistrictCodeAndBlockCodeAndEnabled(pageable,
							searchParameter, districtRepository.findById(Long.parseLong(districtid)).orElse(null).getDistrictCode(),
							blockId, (short) 1);

				}
				/*
				 * else if((searchParameter!=null && !searchParameter.isEmpty()) &&
				 * !((districtid != null && !districtid.isEmpty()) && (blockId != null &&
				 * !blockId.isEmpty()))) {
				 * 
				 * gp =
				 * gramPanchayatRepository.findByGramPanchayatNameContainingAndEnabled(pageable,
				 * searchParameter,DMSConstants.ENABLED);
				 * 
				 * }
				 */

				else {
					gp = gramPanchayatRepository.findByDistrictCodeAndBlockCodeAndEnabled(pageable,
							districtRepository.findById(Long.parseLong(districtid)).orElse(null).getDistrictCode(), blockId,
							(short) 1);
				}

			} else if (!searchParameter.isEmpty() && (districtid.isEmpty() && blockId.isEmpty())) {
				gp = gramPanchayatRepository.findByGramPanchayatNameContainingAndEnabled(pageable, searchParameter,
						DMSConstants.ENABLED);
			} else if ((districtid != null && !districtid.isEmpty()) && !(blockId != null && !blockId.isEmpty())) {
				if (searchParameter != null && !searchParameter.isEmpty()) {

					gp = gramPanchayatRepository.findByGramPanchayatNameContainingAndDistrictCodeAndEnabled(pageable,
							districtRepository.findById(Long.parseLong(districtid)).orElse(null).getDistrictCode(), searchParameter,
							DMSConstants.ENABLED);
				} else {
					logger.info("i am inside new block 55");
					gp = gramPanchayatRepository.findByDistrictCodeAndEnabled(pageable,
							districtRepository.findById(Long.parseLong(districtid)).orElse(null).getDistrictCode(), (short) 1);
				}
			} else {

				gp = gramPanchayatRepository.findAllByEnabled(pageable, (short) 1);
			}

			if (gp != null) {
				List<GramPanchayat> entityList = gp.getContent();
				List<GramPanchayatBean> beanList = new ArrayList<>();
				if (entityList != null && !entityList.isEmpty()) {
					int index = pageable.getPageNumber() * pageable.getPageSize();
					for (GramPanchayat wEnt : entityList) {
						GramPanchayatBean bean = convertGramPanchayatEntityToBean(wEnt);
						bean.setIndex(++index);
						beanList.add(bean);
					}
				}
				gpjson = new GramPanchayatJson();
				gpjson.setiTotalDisplayRecords(gp.getTotalElements());
				gpjson.setiTotalRecords(workSubTypeRepository.count());
				gpjson.setAaData(beanList);
			}
			return gpjson;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return gpjson;
		}
	}

	private GramPanchayatBean convertGramPanchayatEntityToBean(GramPanchayat entity) {

		GramPanchayatBean bean = new GramPanchayatBean();

		if (entity != null) {
			bean.setBlockCode(entity.getBlockCode());
			bean.setDistrictCode(entity.getDistrictCode());
			bean.setEnabled(DMSConstants.ENABLED);
			bean.setGramPanchayatCode(entity.getGramPanchayatCode());
			bean.setGramPanchayatId(entity.getGramPanchayatId());
			bean.setGramPanchayatName(entity.getGramPanchayatName());
			bean.setTehsilCode(entity.getTehsil_code());
			bean.setBlockName(blockRepository.findByBlockCode(entity.getBlockCode()).getBlockName());
			bean.setDistrictName(districtRepository.findByDistrictCode(entity.getDistrictCode()).getDistrictName());
		}
		return bean;

	}

	@Override
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public String deleteBlock(Long id) {
		try {
			Block entity = blockRepository.findById(id).orElse(null);
			if (entity != null) {
				entity.setEnabled((short) 0);
				blockRepository.save(entity);
			}
			return null;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return DMSConstants.ERROR_DELETING_DATA;
		}
	}

	@Override
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public String deleteGP(Long id) {
		try {
			GramPanchayat entity = gramPanchayatRepository.findById(id).orElse(null);
			if (entity != null) {
				entity.setEnabled((short) 0);
				gramPanchayatRepository.save(entity);
			}
			return null;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return DMSConstants.ERROR_DELETING_DATA;
		}
	}

	@Override
	public GramPanchayatBean fetchGPDetails(Long long1) {
		try {
			return convertGramPanchayatEntityToBean(gramPanchayatRepository.findById(long1).orElse(null));
		} catch (Exception e) {
			// TODO: handle exception

			return null;
		}
	}

	@Override
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public String addblock(BlockBean bean) {
		try {

			if (bean.getBlockId() != null) {

				Block entity = convertBlockBEanToEntity(blockRepository.findById(bean.getBlockId()).orElse(null), bean);

				Block savedEntity = blockRepository.save(entity);

				if (savedEntity != null) {
					logger.info("Entry saved successfully");
					return null;
				} else {
					return "Error occurred while saving data";
				}

			} else {
				if (blockRepository.findByBlockCode(bean.getBlockCode()) != null) {

					return "Block Code Already Available";

				}

				Block entity = convertBlockBEanToEntity(new Block(), bean);

				Block savedEntity = blockRepository.save(entity);
				if (savedEntity != null) {
					logger.info("Entry saved successfully");
					return null;
				} else {
					return "Error occurred while saving data";
				}
			}

		} catch (Exception e) {

			logger.error("ERROR SAVING DATA", e);
			return DMSConstants.ERROR_SAVING_DATA;
		}
	}

	private Block convertBlockBEanToEntity(Block one, BlockBean bean) {

		one.setBlockCode(bean.getBlockCode());

		one.setBlockNameH(bean.getBlockNameH());
		one.setBlockName(bean.getBlockName());

		one.setDistrict(districtRepository.findByDistrictCode(bean.getDistrictCode()));

		one.setEnabled(DMSConstants.ENABLED);

		return one;
	}

	@Override
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public String addGP(GramPanchayatBean bean) {
		try {

			// System.err.println(bean.getBlockCode() + bean.getBlockName() +
			// bean.getDistrictCode() + bean.getDistrictName() +
			// bean.getGramPanchayatCode());

			if (bean.getGramPanchayatId() != null) {

				GramPanchayat entity = convertBlockBEanToEntity(
						gramPanchayatRepository.findById(bean.getGramPanchayatId()).orElse(null), bean);

				GramPanchayat savedEntity = gramPanchayatRepository.save(entity);

				if (savedEntity != null) {
					logger.info("Entry saved successfully");
					return null;
				} else {
					return "Error occurred while saving data";
				}

			} else {
				if (!gramPanchayatRepository.findByGramPanchayatCode(bean.getGramPanchayatCode()).isEmpty()) {

					return "GramPanchahyat  already available with this code";
				}

				GramPanchayat entity = convertBlockBEanToEntity(new GramPanchayat(), bean);

				GramPanchayat savedEntity = gramPanchayatRepository.save(entity);
				if (savedEntity != null) {
					logger.info("Entry saved successfully");
					return null;
				} else {
					return "Error occurred while saving data";
				}
			}

		} catch (Exception e) {

			logger.error("ERROR SAVING DATA", e);

			return DMSConstants.ERROR_SAVING_DATA;
		}
	}

	private GramPanchayat convertBlockBEanToEntity(GramPanchayat one, GramPanchayatBean bean) {

		one.setEnabled(DMSConstants.ENABLED);
		one.setBlockCode(bean.getBlockCode());
		one.setDistrictCode(bean.getDistrictCode());
		one.setGramPanchayatCode(bean.getGramPanchayatCode());
		one.setGramPanchayatName(bean.getGramPanchayatName());
		one.setGramPanchayatNameH(bean.getGramPanchayatkNameH());
		one.setTehsil_code(bean.getTehsilCode());

		return one;
	}

	@Override
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public String addFinanicalYear(FinancialYearBean bean) {

		try {
			if (bean == null || StringUtils.isEmpty(bean.getFinancialYearName())) {
				return "Please select financial year.";
			}
			bean.setFinancialYearName(bean.getFinancialYearName().trim());

			FinancialYear entity = new FinancialYear();
			FinancialYear e = financialYearRepository.findByFinancialYearAndEnabled(bean.getFinancialYearName(),
					(short) 1);
			if (bean.getFinancialYearId() != null) {

				entity = financialYearRepository.findById(bean.getFinancialYearId()).orElse(null);

				entity.setEnabled(bean.getEnabled());
				entity.setFinancialYear(bean.getFinancialYearName());
				if (e != null && !(e.getEnabled().toString().equals(bean.getEnabled().toString()))) {

					return "Year Already Available";
				}

			} else {

				if (e != null) {

					return "Year Already Available";
				}

				entity.setEnabled(DMSConstants.ENABLED);
				entity.setFinancialYear(bean.getFinancialYearName());

			}

			financialYearRepository.save(entity);

			return null;

		} catch (Exception e) {
			// TODO: handle exception

			logger.error("Error Saving Data", e);
			return DMSConstants.ERROR_SAVING_DATA;
		}

	}

	@Override
	public FinancialYearBean fetchFinancialYearData(Long id) {
		try {

			FinancialYear entity = financialYearRepository.findById(id).orElse(null);
			FinancialYearBean bean = new FinancialYearBean();
			bean.setEnabled(entity.getEnabled());
			bean.setFinancialYearId(entity.getId());
			bean.setFinancialYearName(entity.getFinancialYear());
			return bean;

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error Fetching Data", e);

		}
		return null;
	}

	@Override
	public WorkSubtypeJson getWorkSubTypes(Pageable pageable, String searchParameter) {
		WorkSubtypeJson worksubtypejson = null;
		try {
			Page<WorkSubType> worksubType = null;

			if (!StringUtils.isEmpty(searchParameter)) {
				worksubType = workSubTypeRepository.findByWorkSubTypeNameEContainingAndEnabled(pageable,
						searchParameter, DMSConstants.ENABLED);
			} else {
				worksubType = workSubTypeRepository.findByEnabled(pageable, DMSConstants.ENABLED);
			}

			if (worksubType != null) {
				List<WorkSubType> entityList = worksubType.getContent();
				List<WorkSubTypeBean> beanList = new ArrayList<>();
				if (entityList != null && !entityList.isEmpty()) {
					int index = pageable.getPageNumber() * pageable.getPageSize();
					for (WorkSubType wEnt : entityList) {
						WorkSubTypeBean bean = convertWorkSubTypeEntityToBean(wEnt);
						bean.setIndex(++index);
						beanList.add(bean);
					}
				}
				worksubtypejson = new WorkSubtypeJson();
				worksubtypejson.setiTotalDisplayRecords(worksubType.getTotalElements());
				worksubtypejson.setiTotalRecords(workSubTypeRepository.count());
				worksubtypejson.setAaData(beanList);
			}
			return worksubtypejson;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return worksubtypejson;
		}
	}
	
	
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public String addWorkSubTypes(WorkSubTypeBean bean) {
		if (bean == null) {
			return "Invalid data provided";
		}
		try {
			WorkSubType entity = new WorkSubType();

			if (bean.getWorkSubTypeId() != null) {

				entity = workSubTypeRepository.findById(bean.getWorkSubTypeId()).orElse(null);

			}

			entity = convertWorkSubTypeBeanToEntity(entity, bean);
			boolean exists = workSubTypeRepository.existsByworkSubTypeNameEAndEnabled(bean.getWorkSubTypeNameE(),
					DMSConstants.ENABLED);
			if (exists) {
				return "A work type with this name already exists";
			}

			WorkSubType savedEntity = workSubTypeRepository.save(entity);

			if (savedEntity != null) {
				logger.info("Entry saved successfully");
				return null;
			} else {
				return "Error occurred while saving data";
			}
		} catch (Exception e) {

			logger.error("Error Occurred during saving Data", e);
			return "Error occurred while saving data: " + e.getMessage();
		}
	}

	@Override
	public WorkSubTypeBean fetchWorkSubTypeById(long id) {
		try {
			WorkSubType entity = workSubTypeRepository.findById(id).orElse(null);
			return convertWorkSubTypeEntityToBean(entity);
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}
	
	// delete workSubTypes
		@Override
		@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
		public String deleteWorkSubTypes(Long id) {

			try {
				WorkSubType entity = workSubTypeRepository.findById(id).orElse(null);
				if (entity != null) {
					entity.setEnabled((short) 0);
					workSubTypeRepository.save(entity);
				}
				return null;
			} catch (Exception e) {
				logger.error("An exception occurred.", e);
				return DMSConstants.ERROR_DELETING_DATA;
			}

		}

}
