package com.anuppur.service.impl;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import jakarta.persistence.Entity;

//import org.thymeleaf.util.StringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.sl.usermodel.TableCell.BorderEdge;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTable;
import org.apache.poi.xslf.usermodel.XSLFTableCell;
import org.apache.poi.xslf.usermodel.XSLFTextBox;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.anuppur.bean.BlockBean;
import com.anuppur.bean.CCBean;
import com.anuppur.bean.ContractorBean;
import com.anuppur.bean.DepartmentMasterBean;
import com.anuppur.bean.DepartmentRemarksBean;
import com.anuppur.bean.DepartmentWiseReportRowBean;
import com.anuppur.bean.DmRemarkWiseReportRowBean;
import com.anuppur.bean.PhotoUpdateReportRowBean;
import com.anuppur.bean.DistrictBean;
import com.anuppur.bean.DivisionBean;
import com.anuppur.bean.DmRemarksBean;
import com.anuppur.bean.DocumentUploadDrawingDetailBean;
import com.anuppur.bean.DocumentUploadWorkProgressBean;
import com.anuppur.bean.ExpensesDataBean;
import com.anuppur.bean.FinancialAgencyBean;
import com.anuppur.bean.FinancialHeadBean;
import com.anuppur.bean.FinancialYearBean;
import com.anuppur.bean.GTPoint;
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
import com.anuppur.bean.RoleBean;
import com.anuppur.bean.SchemeBean;
import com.anuppur.bean.SlideData;
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
import com.anuppur.bean.WorkProgressDataMoibleBean;
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
import com.anuppur.security.SecureFileUploadPolicy;
import com.anuppur.entity.AreaOfficerRecord;
import com.anuppur.entity.AsGeneratedCount;
import com.anuppur.entity.Block;
import com.anuppur.entity.CC;
import com.anuppur.entity.Contractor;
import com.anuppur.entity.DepartmentMaster;
import com.anuppur.entity.DepartmentRemarks;
import com.anuppur.entity.District;
import com.anuppur.entity.Division;
import com.anuppur.entity.DmRemarks;
import com.anuppur.entity.DocumentUpload;
import com.anuppur.entity.DocumentUploadDrawingDetail;
import com.anuppur.entity.DocumentUploadWorkProgress;
import com.anuppur.entity.ExpensesData;
import com.anuppur.entity.FinancialHead;
import com.anuppur.entity.FinancialYear;
import com.anuppur.entity.GramPanchayat;
import com.anuppur.entity.Head;
import com.anuppur.entity.ImplementationAgency;
import com.anuppur.entity.ImplementationAgencyType;
import com.anuppur.entity.LegislativeConstituency;
import com.anuppur.entity.LocationPoints;
import com.anuppur.entity.Month;
import com.anuppur.entity.OtpGeneration;
import com.anuppur.entity.PhysicalPercentageNew;
import com.anuppur.entity.PhysicalPercentageUpgradation;
import com.anuppur.entity.Role;
import com.anuppur.entity.Schemes;
import com.anuppur.entity.SorYear;
import com.anuppur.entity.SubCategory;
import com.anuppur.entity.TSASReviseWork;
import com.anuppur.entity.TSASWork;
import com.anuppur.entity.Users;
import com.anuppur.entity.VidhanSabha;
import com.anuppur.entity.Work;
import com.anuppur.entity.WorkCategory;
import com.anuppur.entity.WorkCategoryTypeMapping;
import com.anuppur.entity.WorkCount;
import com.anuppur.entity.WorkDocument;
import com.anuppur.entity.WorkFinancialAgency;
import com.anuppur.entity.WorkGeoLocation;
import com.anuppur.entity.WorkHead;
import com.anuppur.entity.WorkPriority;
import com.anuppur.entity.WorkProgress;
import com.anuppur.entity.WorkStatus;
import com.anuppur.entity.WorkSubDelayReson;
import com.anuppur.entity.WorkSubStatus;
import com.anuppur.entity.WorkSubType;
import com.anuppur.entity.WorkTender;
import com.anuppur.entity.WorkTenderCount;
import com.anuppur.entity.WorkType;
import com.anuppur.entity.YearStatus;
import com.anuppur.dto.FinancialExpenditureRequest;
import com.anuppur.exception.FinancialValidationException;
import com.anuppur.exception.DMSBusinessException;
import com.anuppur.json.BlockJson;
import com.anuppur.json.ExpensesDataJson;
import com.anuppur.json.HeadJson;
import com.anuppur.json.ImplAgencyJson;
import com.anuppur.json.LoginJson;
import com.anuppur.json.MergeWorkJson;
import com.anuppur.json.SchemeJson;
import com.anuppur.json.SdrJson;
import com.anuppur.json.SorJson;
import com.anuppur.json.TASAReviseJson;
import com.anuppur.json.WorkJson;
import com.anuppur.json.WorkProgressImagesJson;
import com.anuppur.json.WorkStatusJson;
import com.anuppur.repository.AsGeneratedCountRepository;
import com.anuppur.repository.BlockRepository;
import com.anuppur.repository.CCRepository;
import com.anuppur.repository.CategorySubTypeMappingRepository;
import com.anuppur.repository.ContractorRepository;
import com.anuppur.repository.DepartmentMasterRepository;
import com.anuppur.repository.DepartmentRemarksRepository;
import com.anuppur.repository.DistrictRepository;
import com.anuppur.repository.DivisionRepository;
import com.anuppur.repository.DmRemarksRepository;
import com.anuppur.repository.DocumentRepository;
import com.anuppur.repository.DocumentUploadDrawingDetailRepository;
import com.anuppur.repository.DocumentUploadWorkProgressRepository;
import com.anuppur.repository.ExpensesDataRepository;
import com.anuppur.repository.FinancialAgencyRepository;
import com.anuppur.repository.FinancialHeadRepository;
import com.anuppur.repository.FinancialYearRepository;
import com.anuppur.repository.GramPanchayatRepository;
import com.anuppur.repository.HeadRepository;
import com.anuppur.repository.ImplAgencyRepository;
import com.anuppur.repository.ImplAgencyTypeRepository;
import com.anuppur.repository.LegislativeConsRepository;
import com.anuppur.repository.MonthRepository;
import com.anuppur.repository.OtpGenerationRepository;
import com.anuppur.repository.PhysicalPercentageNewRepository;
import com.anuppur.repository.PhysicalPercentageUpgradationRepository;
import com.anuppur.repository.RoleRepository;
import com.anuppur.repository.SchemeRepository;
import com.anuppur.repository.SorYearRepository;
import com.anuppur.repository.SubCategoryRepository;
import com.anuppur.repository.TSASReviseWorkRepository;
import com.anuppur.repository.TSASWorkRepository;
import com.anuppur.repository.UserRepository;
import com.anuppur.repository.VidhanSabhaRepository;
import com.anuppur.repository.WorkCategoryRepository;
import com.anuppur.repository.WorkCategoryTypeMappingRepository;
import com.anuppur.repository.WorkCountRepository;
import com.anuppur.repository.WorkDocumentRepository;
import com.anuppur.repository.WorkGeoLocationRepository;
import com.anuppur.repository.WorkHeadRepository;
import com.anuppur.repository.WorkPriorityRepository;
import com.anuppur.repository.WorkProgressRepository;
import com.anuppur.repository.WorkRepository;
import com.anuppur.repository.WorkStatusRepository;
import com.anuppur.repository.WorkSubDelayResonRepository;
import com.anuppur.repository.WorkSubStatusRepository;
import com.anuppur.repository.WorkSubTypeRepository;
import com.anuppur.repository.WorkTenderCountRepository;
import com.anuppur.repository.WorkTenderRepository;
import com.anuppur.repository.WorkTypeRepository;
import com.anuppur.repository.YearStatusRepository;
import com.anuppur.repository.areaofficerrecordRepository;
import com.anuppur.repository.pointsrepository;
import com.anuppur.response.ResponseObject;
import com.anuppur.response.UserDetailResponse;
import com.anuppur.service.AdminService;
import com.anuppur.service.CommonService;
import com.anuppur.service.FinancialValidationService;
import com.anuppur.service.NotificationService;
import com.anuppur.service.SuperAdminService;
import com.anuppur.service.WorkDocumentCleanupService;
import com.anuppur.service.WorkSensitiveFieldService;
import com.anuppur.util.DMSUtil;

import java.text.ParseException;

@Service
public class CommonServiceImpl implements CommonService {

	public static final Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);

	@Value("${document.root}")
	private String documentsPath;

	@Value("${document.citizen}")
	private String documentsPathForCitizen;

	@Value("${host.ip.address}")
	private String hostIpAddress;

	@Value("${document.work}")
	private String workDocumentsPath;

	@Value("${document.root}")
	private String documentRootPath;

	@Value("${document.technical}")
	private String workTechSanctionDocumentPath;

	@Value("${document.dmAttachment}")
	private String dmAttachment;
	@Value("${document.tsRevised}")
	private String workRevisedTechSanctionDocumentPath;

	@Value("${document.asRevised}")
	private String workASRevisedSanctionDocumentPath;

	@Value("${document.administrator}")
	private String workASSanctionDocumentPath;

	@Value("${document.tender}")
	private String workTenderSanctionDocumentPath;

	@Value("${document.workprogress}")
	private String workWorkProgressDocumentPath;

	@Value("${document.drawingfile}")
	private String drawigUpoadDocumentPath;

	@Value("${document.cc}")
	private String CCDocumentPath;

	// sumit
	@Value("${stagepassing.imageUrl}")
	private String imageUrl;

	@Value("${file.temp.zip.dir}")
	private String tempZipDir;

	@Autowired
	private WorkGeoLocationRepository geoLocationRepository;

	@Autowired
	private WorkCategoryRepository workCategoryRepository;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WorkRepository workRepository;

	@Autowired
	private TSASWorkRepository tsasWorkRepository;

	@Autowired
	private TSASReviseWorkRepository tsasReviseWorkRepository;

	@Autowired
	private HeadRepository headRepository;

	@Autowired
	private WorkCategoryTypeMappingRepository workCategoryTypeMappingRepository;

	@Autowired
	private CategorySubTypeMappingRepository categorySubTypeMappingRepository;

	@Autowired
	private SubCategoryRepository subCategoryRepository;

	@Autowired
	private OtpGenerationRepository otpGenerationRepository;

	@Autowired
	private WorkSubTypeRepository workSubTypeRepository;

	@Autowired
	private WorkSubStatusRepository workSubStatusRepository;

	@Autowired
	private WorkProgressRepository workProgressRepository;

	@Autowired
	private WorkSubDelayResonRepository workSubDelayResonRepository;

	@Autowired
	private DmRemarksRepository dmRemarksRepository;

	@Autowired
	private WorkPriorityRepository workPriorityRepository;

	@Autowired
	private FinancialHeadRepository financialHeadRepository;

	@Autowired
	private VidhanSabhaRepository vidhanSabhaRepositorys;

	private String[] statusPendingReconciled = new String[] { DMSConstants.STATUS_PENDING,
			DMSConstants.STATUS_RECONCILED };
	private String[] statusPendingReconciledRejected = new String[] { DMSConstants.STATUS_PENDING,
			DMSConstants.STATUS_RECONCILED, DMSConstants.STATUS_REJECTED };

	@Autowired
	private WorkTypeRepository workTypeRepository;

	@Autowired
	private ImplAgencyRepository implAgencyRepository;

	@Autowired
	private ImplAgencyTypeRepository implAgencyTypeRepository;

	@Autowired
	private SchemeRepository schemeRepository;

	@Autowired
	private LegislativeConsRepository legislativeConsRepository;

	@Autowired
	private WorkHeadRepository workHeadRepository;

	@Autowired
	private DistrictRepository districtRepository;

	@Autowired
	private BlockRepository blockRepository;

	@Autowired
	private FinancialYearRepository financialYearRepository;

	@Autowired
	private WorkStatusRepository workStatusRepository;

	@Autowired
	private WorkCountRepository workCountRepository;

	@Autowired
	private AsGeneratedCountRepository asGeneratedCountRepository;

	@Autowired
	private SuperAdminService superAdminService;

	@Autowired
	private WorkDocumentRepository workDocumentRepository;

	@Autowired
	private DivisionRepository divisionRepository;

	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private DocumentUploadWorkProgressRepository documentUploadWorkProgressRepository;

	@Autowired
	private WorkTenderRepository workTenderRepository;

	@Autowired
	private ContractorRepository contractorRepository;

	@Autowired
	private CCRepository ccRepository;

	@Autowired
	private YearStatusRepository yearStatusRepository;

	@Autowired
	private ExpensesDataRepository expensesDataRepository;

	@Autowired
	private SorYearRepository sorYearRepository;

	@Autowired
	private WorkTenderCountRepository workTenderCountRepository;

	@Autowired
	private DocumentUploadDrawingDetailRepository documentUploadDrawingDetailRepository;

	@Autowired
	private PhysicalPercentageNewRepository physicalPercentageNewRepository;

	@Autowired
	private PhysicalPercentageUpgradationRepository physicalPercentageUpgradationRepository;

	@Autowired
	private MonthRepository monthRepository;

	@Autowired
	private GramPanchayatRepository gramPanchayatRepository;

	@Autowired
	private com.anuppur.repository.areaofficerrecordRepository areaOfficerRecordRepository;

	@Autowired
	private WorkRepositoryCustomImpl workRepositoryCustomImpl;

	@Autowired
	private FinancialAgencyRepository financialAgencyRepository;

	@Autowired
	private FinancialValidationService financialValidationService;

	@Autowired
	private WorkSensitiveFieldService workSensitiveFieldService;

	@Autowired
	private DepartmentMasterRepository departmentMasterRepository;

	@Autowired
	private DepartmentRemarksRepository departmentRemarksRepository;

	@Autowired
	private WorkDocumentCleanupService workDocumentCleanupService;

//	@Autowired
//	private CommonService commonService;
//	
	@Override
	public WorkJson fetchWorksList(Pageable pageable, String workNo, String workName, String scheme,
			List<Long> workTypeList, List<Long> fyList, List<Long> agencyList, String blockId, String workStatus,
			String districtId, String divisionId, String searchByDivision, String workSubTypeId, List<Long> statusList,
			List<Long> priorityList, List<Long> headList, List<Long> vsList, String workNameFilter,
			String departmentRemark, List<Long> departmentList) {

		Integer workSubTypeIdInt = null;
		if (workSubTypeId != null) {
			try {
				workSubTypeIdInt = Integer.parseInt(workSubTypeId);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		WorkJson workJson = null;
		User user = DMSUtil.getUserDetail();

		Users userEntity = userRepository.findByUsernameAndStatus(user.getUsername(), DMSConstants.STATUS_ACTIVE);

		// Handle null userEntity
		if (userEntity == null) {
			logger.warn("User entity not found for username: {}", user.getUsername());
			workJson = new WorkJson();
			workJson.setAaData(new ArrayList<>());
			workJson.setiTotalRecords(0L);
			workJson.setiTotalDisplayRecords(0L);
			return workJson;
		}

		Collection<GrantedAuthority> role = user.getAuthorities();
		Long divisionCode = null;
		String districtCode = "";
		ImplementationAgency implAgency = null;
		String r = role.toString();

		// logger.info("ROLEEEE....." + r);
		if (r.contains("ROLE_DEPARTMENT")) {
			Division division = userEntity.getDivision();
			if (division != null) {
				divisionCode = division.getDivisionId();
			} else {
				divisionCode = 3L;
			}
			implAgency = userEntity.getImplementationAgency();
		} else {
			divisionCode = null;

		}

		if (r.contains("ROLE_DISTRICT") || r.contains("ROLE_DEPT_DISTRICT")) {
			District district = userEntity.getDistrict();
			if (district != null) {
				districtCode = district.getDistrictCode();
			} else {
				districtCode = "";
			}
			// logger.info("districtCodeif....." + districtCode);
			implAgency = userEntity.getImplementationAgency();
		} else {
			districtCode = "";
			implAgency = null;
		}

		if (r.contains("ROLE_SAU")) {

			implAgency = userEntity.getImplementationAgency();
		} else {
			implAgency = null;
		}

		try {
			Page<Work> works = null;

			List<String> schemes = new ArrayList<String>();

			// Convert blockId comma-separated strideng to List<Long>
			List<Long> blockIdList = new ArrayList<>();
			if (blockId != null && !blockId.isEmpty()) {
				String[] blockIds = blockId.split(",");
				for (String id : blockIds) {
					try {
						blockIdList.add(Long.valueOf(id.trim()));
					} catch (NumberFormatException e) {
						// Skip invalid IDs
					}
				}
			}

			Long divisionIds = null;
			if (divisionId != null) {
				divisionIds = Long.valueOf(divisionId);
//				divisionName = divisionRepository.findById(Long.valueOf(divisionId)).orElse(null).getDivisionName();
			}

			Long districtIds = null;
			if (districtId != null) {
				districtIds = Long.valueOf(districtId);
//				districtName = districtRepository.findById(Long.valueOf(districtId)).orElse(null).getDistrictName();
			}
			String gpName = null;

			// Sumit
			workTypeList = (workTypeList == null || workTypeList.isEmpty()) ? null : workTypeList;
			fyList = (fyList == null || fyList.isEmpty()) ? null : fyList;
			agencyList = (agencyList == null || agencyList.isEmpty()) ? null : agencyList;
			statusList = (statusList == null || statusList.isEmpty()) ? null : statusList;
			priorityList = (priorityList == null || priorityList.isEmpty()) ? null : priorityList;
			headList = (headList == null || headList.isEmpty()) ? null : headList;
			vsList = (vsList == null || vsList.isEmpty()) ? null : vsList;
			blockIdList = (blockIdList == null || blockIdList.isEmpty()) ? null : blockIdList;

			// Convert workStatus string (COMPLETED/ONGOING/NOT_STARTED) to statusList if
			// not already set
			if (statusList == null && workStatus != null && !workStatus.isEmpty()) {
				Long flag = null;
				if ("COMPLETED".equalsIgnoreCase(workStatus))
					flag = 5L; // workStatusId=11
				else if ("ONGOING".equalsIgnoreCase(workStatus))
					flag = 5L; // workStatusId=10
				else if ("NOT_STARTED".equalsIgnoreCase(workStatus))
					flag = 5L; // workStatusId=9
				// Since all three share flag=5, use direct IDs instead
				statusList = new ArrayList<>();
				if ("COMPLETED".equalsIgnoreCase(workStatus))
					statusList.add(11L);
				else if ("ONGOING".equalsIgnoreCase(workStatus))
					statusList.add(10L);
				else if ("NOT_STARTED".equalsIgnoreCase(workStatus))
					statusList.add(9L);
				if (statusList.isEmpty())
					statusList = null;
			}

			if (r.contains("ROLE_DEPARTMENT")) {
				if (departmentList != null && !departmentList.isEmpty()) {
					// When department filter is explicitly set, use generic query with dept filter
					works = workRepositoryCustomImpl.findAllByStatusNotDeleted(pageable, workNo, workTypeList, fyList,
							null, statusList, agencyList, priorityList, headList, vsList, workNameFilter, blockIdList,
							departmentRemark, departmentList);
				} else if (agencyList != null && !agencyList.isEmpty()) {
					// When agency filter is explicitly set (e.g. from dept-wise report), skip
					// division filter
					works = workRepositoryCustomImpl.findAllByStatusNotDeleted(pageable, workNo, workTypeList, fyList,
							null, statusList, agencyList, priorityList, headList, vsList, workNameFilter, blockIdList,
							departmentRemark, null);
				} else {
					works = workRepositoryCustomImpl.fetchAllWorksByDivision(pageable, workNo, workTypeList, fyList,
							districtIds, statusList, agencyList, null, priorityList, headList, vsList, divisionCode,
							departmentRemark, blockIdList, workNameFilter);
				}
			}

			else if (r.contains("ROLE_DISTRICT")) {
				// Parameter order: pageable, workNo, workTypeList, fyList, districtCode,
				// agencyList, divisionIds, districtIds, workSubTypeIdInt, statusList,
				// priorityList, headList, vsList, departmentRemark, blockIdList, workNameFilter
				works = workRepositoryCustomImpl.fetchAllWorksByDistrict(pageable, workNo, workTypeList, fyList,
						districtCode, agencyList, divisionIds, districtIds, workSubTypeIdInt, statusList, priorityList,
						headList, vsList, departmentRemark, blockIdList, workNameFilter);
			}

//			else if (r.contains("ROLE_SAU")) {
//				works = workRepository.fetchAllWorksByAgency(pageable, workName, workTypeId, financialYear, agency,
//						divisionIds, districtIds, workSubTypeIdInt, workStatusId, workPriority, financialHead, vidhanSabha);
//
//			}

//			else if (r.contains("ROLE_DEPT_DISTRICT")) {
//				works = workRepository.fetchAllDeptDistrict(pageable, workName, workTypeId, financialYear, districtCode,
//						divisionIds, districtIds, workSubTypeIdInt, workStatusId, agency, workPriority, financialHead, vidhanSabha);
//
//			}

			else if (r.contains("ROLE_AGENCY_ADMIN")) { // aman 18-07-2024 new condition add
				// works = workRepository.fetchAllWorksByAgency(pageable, workName, workType,
				// financialYear, agency, divisionName, districtName, workSubTypeIdInt,
				// workStatusName);
				// Parameter order: pageable, workNo, workTypeId, financialYear, districtId,
				// workStatusId, agency, workPriority, financialHead, vidhanSabha,
				// workNameFilter, blockId, departmentRemark
				works = workRepositoryCustomImpl.findAllByStatusNotDeleted(pageable, workNo, workTypeList, fyList, null,
						statusList, agencyList, priorityList, headList, vsList, workNameFilter, blockIdList,
						departmentRemark, departmentList);

			} else {
				// Parameter order: pageable, workNo, workTypeId, financialYear, districtId,
				// workStatusId, agency, workPriority, financialHead, vidhanSabha,
				// workNameFilter, blockId, departmentRemark
				works = workRepositoryCustomImpl.findAllByStatusNotDeleted(pageable, workNo, workTypeList, fyList, null,
						statusList, agencyList, priorityList, headList, vsList, workNameFilter, blockIdList,
						departmentRemark, departmentList);
			}

			// added by sumit
			// works = workRepository.findAll(pageable);

			if (works != null) {
				List<Work> entityList = works.getContent();
				List<WorkBean> beanList = new ArrayList<>();
				if (entityList != null && !entityList.isEmpty()) {

					int index = pageable.getPageNumber() * pageable.getPageSize();
					for (Work work : entityList) {

						// WorkBean bean = convertWorkEntityToBean(work, "All");
						WorkBean bean = convertWorkEntityToBeans1(work, "All");
						if (work.getUserAssignee() != null) {
							bean.setUserAssignee(work.getUserAssignee());

							bean.setUserAssigneeName(
									userRepository.findById(work.getUserAssignee()).orElse(null).getEmailId());
						}
						bean.setIndex(++index);

						for (Role s : userEntity.getRoles()) {
							bean.setRole(s.getRoleCode());

						}
						List<DmRemarks> byworkId = null;
						if (dmRemarksRepository.findByworkIdAndEnabled(bean.getWorkId(), (short) 1) != null) {

							byworkId = dmRemarksRepository.findByworkIdAndEnabled(bean.getWorkId(), (short) 1);

						}
						DmRemarks dmRemarks = null;
						if (byworkId != null && byworkId.size() > 0) {
							dmRemarks = byworkId.get(byworkId.size() - 1);
							// logger.info(dmRemarks.getCreated_time()+"asdasdasdasdasdasdasds");
						}
						StringBuilder deptRemarks = new StringBuilder();
						StringBuilder dmRemark = new StringBuilder();
						StringBuilder dmRemarkMaster = new StringBuilder();
						if (dmRemarks != null) {
							String createdTimeStr = dmRemarks.getCreated_time();
							if (dmRemarks.getRemark() != null && !dmRemarks.getRemark().isEmpty()) {
								dmRemark.append(dmRemarks.getRemark());
							} else {
								bean.setDmRemakrs("-");
							}

							if (createdTimeStr != null && !createdTimeStr.trim().isEmpty()) {
								try {
									SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
									sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
									Date createdTime = sdf.parse(createdTimeStr);
									bean.setDmAproveRejectDate(createdTime);
								} catch (Exception e) {

									bean.setDmAproveRejectDate(null);

								}
							} else {
								bean.setDmAproveRejectDate(null);
							}
						} else {
							bean.setDmAproveRejectDate(null);
							deptRemarks.append("-");
						}

						bean.setDmRemakrs(dmRemark.toString());
						beanList.add(bean);
					}
				}
				workJson = new WorkJson();
				workJson.setiTotalDisplayRecords(works.getTotalElements());
				workJson.setiTotalRecords(works.getTotalElements());
				// workJson.setiTotalRecords(workRepository.countByStatusNotIn(DMSConstants.STATUS_DELETED));

				workJson.setAaData(beanList);
			}
			return workJson;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return workJson;
		}
	}

	private WorkReportBean convertWorkForReport(Work w) {
		WorkReportBean bean = new WorkReportBean();

		bean.setWorkName(w.getWorkName());
		bean.setFinancialYear(financialYearRepository.findById(w.getFinancialYear()).orElse(null).getFinancialYear());

		return null;
	}

	@Override
	public List<WorkReportBean> fetchWorksListByAgency(Long mode) {
		// User user = DMSUtil.getUserDetail();
		// Users userEntity = userRepository.findByUsernameAndStatus(user.getUsername(),
		// DMSConstants.STATUS_ACTIVE);
		User user = DMSUtil.getUserDetail();
		Users userEntity = userRepository.findByUsernameAndStatus(user.getUsername(), DMSConstants.STATUS_ACTIVE);
		Collection<GrantedAuthority> role = user.getAuthorities();
		Long divisionCode = null;
		String districtCode = "";
		String agency = null;
		ImplementationAgency implAgency = null;
		String r = role.toString();
		if (r.contains("ROLE_DEPARTMENT")) {
			Division division = userEntity.getDivision();
			divisionCode = division.getDivisionId();
		} else {
			divisionCode = null;
		}
		if (r.contains("ROLE_DISTRICT") || r.contains("ROLE_DEPT_DISTRICT")) {
			District district = userEntity.getDistrict();
			districtCode = district.getDistrictCode();
			implAgency = userEntity.getImplementationAgency();
		} else {
			districtCode = "";
			implAgency = null;
		}

		if (r.contains("ROLE_SAU")) {

			implAgency = userEntity.getImplementationAgency();
			logger.info("agencyIdif....." + agency);
		} else {
			implAgency = null;
		}

		List<WorkReportBean> beanList = new ArrayList<>();
		try {
			List<Object[]> entityList = null;
			long count = 0;

			int index = 0;

			if (mode == 1) {

				if (r.contains("ROLE_DEPARTMENT")) {
					entityList = workRepository.fetchAgencyWiseWorkByDivision(divisionCode);
				} else if (r.contains("ROLE_DEPT_DISTRICT")) {
					entityList = workRepository.fetchAgencyWiseWorkByDistrict(districtCode, implAgency);
				} else {
					entityList = workRepository.fetchAgencyWiseWork();
				}
			} else if (mode == 2) {
				if (r.contains("ROLE_DEPARTMENT")) {
					entityList = workRepository.fetchSchemeWiseWorkByDivision(divisionCode);
				} else if (r.contains("ROLE_DEPT_DISTRICT")) {
					entityList = workRepository.fetchSchemeWiseWorkByDistrict(districtCode, implAgency);
				} else {
					entityList = workRepository.fetchSchemeWiseWork();
				}
			} else if (mode == 3) {
				if (r.contains("ROLE_DEPARTMENT")) {
					entityList = workRepository.fetchYearWiseWorkByDivision(divisionCode);
				} else if (r.contains("ROLE_DEPT_DISTRICT")) {
					entityList = workRepository.fetchYearWiseWorkByDistrict(districtCode, implAgency);
				} else {
					entityList = workRepository.fetchYearWiseWork();
				}
			} else if (mode == 4) {
				if (r.contains("ROLE_DEPARTMENT")) {
					entityList = workRepository.fetchSegmentWiseWorkByDivision(divisionCode);
				} else if (r.contains("ROLE_DEPT_DISTRICT")) {
					entityList = workRepository.fetchSegmentWiseWorkByDistrict(districtCode, implAgency);
				} else {
					entityList = workRepository.fetchSegmentWiseWork();
				}
			} else if (mode == 5) {
				if (r.contains("ROLE_DEPARTMENT")) {
					entityList = workRepository.fetchSchemeYearWiseWorkByDivision(divisionCode);
				} else if (r.contains("ROLE_DEPT_DISTRICT")) {
					entityList = workRepository.fetchSchemeYearWiseWorkByDistrict(districtCode, implAgency);
				} else {
					entityList = workRepository.fetchSchemeYearWiseWork();
				}
			} else if (mode == 6) {
				entityList = workRepository.fetchDivisionWiseWork();
			}

			if (entityList != null && !entityList.isEmpty()) {

				for (Object[] objects : entityList) {
					WorkReportBean bean = new WorkReportBean();

					if (mode == 1) {
						if (!objects[0].toString().isEmpty()) {
							bean.setNameOfAgency(objects[0].toString());
						} else {
							bean.setNameOfAgency("-");
						}

					} else if (mode == 2) {
						if (objects[0] != null) {
							bean.setScheme(objects[0].toString());
						} else {
							bean.setScheme("-");
						}

					}

					else if (mode == 3) {
						if (objects[0] != null) {
							bean.setYear(objects[0].toString());
						} else {
							bean.setYear("-");
						}

					}

					else if (mode == 4) {
						if (objects[0] != null) {
							bean.setSegment(objects[0].toString());
						} else {
							bean.setSegment("-");
						}

					}

					else if (mode == 5) {
						if (objects[0] != null) {
							bean.setSchemeYear(objects[0].toString());
						} else {
							bean.setSchemeYear("-");
						}

					}

					bean.setWorkCount((BigInteger) objects[1]);
					bean.setWorkContractrAmt((BigDecimal) objects[2]);
					bean.setTotalExp((BigDecimal) objects[3]);
					bean.setTotalExpUptoMarch((BigDecimal) objects[4]);
					bean.setUptoDateAmt((BigDecimal) objects[5]);
					bean.setHandedOverStatus((BigDecimal) objects[6]);
					bean.setCompletedStatus((BigDecimal) objects[7]);
					bean.setWorkStarted((BigDecimal) objects[8]);
					bean.setNotStarted((BigDecimal) objects[9]);
					bean.setFinishLevelCount((BigDecimal) objects[10]);
					bean.setRoofLevel((BigDecimal) objects[11]);
					bean.setLintelLevel((BigDecimal) objects[12]);
					bean.setPlinthLevel((BigDecimal) objects[13]);
					bean.setFoundationLevel((BigDecimal) objects[14]);
					bean.setSiteNotSelected((BigDecimal) objects[15]);
					bean.setTenderIssue((BigDecimal) objects[16]);
					bean.setTenderAwarded((BigDecimal) objects[17]);
					bean.setIndex(++index);
					beanList.add(bean);

				}

			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return beanList;
		}
	}

	@Override
	public List<WorkReportBean> fetchWorksListByDrawing(Long mode) {

		User user = DMSUtil.getUserDetail();

		Users userEntity = userRepository.findByUsernameAndStatus(user.getUsername(), DMSConstants.STATUS_ACTIVE);
		List<WorkReportBean> beanList = new ArrayList<>();
		try {
			List<Object[]> entityList = null;
			long count = 0;

			int index = 0;
			logger.info("Mode......" + mode);
			if (mode == 1) {
				entityList = workRepository.fetchDrawingWork();
			} else if (mode == 2) {
				entityList = workRepository.fetchAsApprovalWork();
			} else if (mode == 3) {
				entityList = workRepository.fetchPhysicalWiseWork();
			}

			if (entityList != null && !entityList.isEmpty()) {

				for (Object[] objects : entityList) {
					WorkReportBean bean = new WorkReportBean();

					bean.setWorkName((String) objects[0]);
					bean.setSubTypeName((String) objects[1]);
					if (mode == 1) {
						bean.setDrawingStatus((String) objects[2]);
						bean.setPendingStatus((String) objects[3]);
					} else if (mode == 2) {
						bean.setAsStatus((String) objects[2]);
						bean.setTenderStatus((String) objects[3]);
					} else if (mode == 3) {
						bean.setPhyPerc((Integer) objects[2]);
						bean.setExpAmt((BigDecimal) objects[3]);
					}

					bean.setIndex(++index);
					beanList.add(bean);

				}

			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return beanList;
		}
	}

	@Override
	public List<WorkReportBean> fetchWorksListByDivision(Long mode) {

		User user = DMSUtil.getUserDetail();

		Users userEntity = userRepository.findByUsernameAndStatus(user.getUsername(), DMSConstants.STATUS_ACTIVE);
		List<WorkReportBean> beanList = new ArrayList<>();
		try {
			List<Object[]> entityList = null;
			long count = 0;

			int index = 0;

			entityList = workRepository.fetchDivisionWiseWork();

			if (entityList != null && !entityList.isEmpty()) {

				for (Object[] objects : entityList) {
					WorkReportBean bean = new WorkReportBean();

					if (!objects[0].toString().isEmpty()) {
						bean.setDivision(objects[0].toString());
					} else {
						bean.setDivision("-");
					}

					if (!objects[1].toString().isEmpty()) {
						bean.setDistrict(objects[1].toString());
					} else {
						bean.setDistrict("-");
					}

					bean.setWorkCount((BigInteger) objects[2]);
					bean.setWorkContractrAmt((BigDecimal) objects[3]);
					bean.setTotalExp((BigDecimal) objects[4]);
					bean.setTotalExpUptoMarch((BigDecimal) objects[5]);
					bean.setUptoDateAmt((BigDecimal) objects[6]);
					bean.setHandedOverStatus((BigDecimal) objects[7]);
					bean.setCompletedStatus((BigDecimal) objects[8]);
					bean.setWorkStarted((BigDecimal) objects[9]);
					bean.setNotStarted((BigDecimal) objects[10]);
					bean.setFinishLevelCount((BigDecimal) objects[11]);
					bean.setRoofLevel((BigDecimal) objects[12]);
					bean.setLintelLevel((BigDecimal) objects[13]);
					bean.setPlinthLevel((BigDecimal) objects[14]);
					bean.setFoundationLevel((BigDecimal) objects[15]);
					bean.setSiteNotSelected((BigDecimal) objects[16]);
					bean.setTenderIssue((BigDecimal) objects[17]);
					bean.setTenderAwarded((BigDecimal) objects[18]);
					bean.setIndex(++index);
					beanList.add(bean);

				}

			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return beanList;
		}
	}

	@Override
	public List<Object[]> fetchexportDrawingReportData(Long id) {
		List<Object[]> object = new ArrayList<>();
		if (id == 1) {
			object = workRepository.fetchDrawingWork();
		} else if (id == 2) {
			object = workRepository.fetchAsApprovalWork();
		} else if (id == 3) {
			object = workRepository.fetchPhysicalWiseWork();
		}

		return object;
	}

	@Override
	public WorkJson fetchHandoverWorksList(Pageable pageable, String workNo, String workName, String scheme,
			String workType, String financialYear, String implementationAgency, String blockId, String workStatus,
			String divisionId, String districtId, String searchByDivision, String workSubTypeId, String workStatusId) {

		Integer workSubTypeIdInt = null;
		if (workSubTypeId != null) {
			try {
				workSubTypeIdInt = Integer.parseInt(workSubTypeId);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		Long financialYearId = null;

		if (financialYear != null) {
			try {
				financialYearId = Long.parseLong(financialYear);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		Long workTypeId = null;
		if (workType != null) {
			workTypeId = Long.valueOf(workType);
//			districtName = districtRepository.findById(Long.valueOf(districtId)).orElse(null).getDistrictName();
		}

		Long agency = null;
		if (implementationAgency != null) {
			agency = Long.valueOf(implementationAgency);
//				districtName = districtRepository.findById(Long.valueOf(districtId)).orElse(null).getDistrictName();
		}

		WorkJson workJson = null;
		User user = DMSUtil.getUserDetail();

		Users userEntity = userRepository.findByUsernameAndStatus(user.getUsername(), DMSConstants.STATUS_ACTIVE);

		Collection<GrantedAuthority> role = user.getAuthorities();
		Long divisionCode = null;
		String districtCode = "";

		String r = role.toString();
		if (r.contains("ROLE_DEPARTMENT")) {
			Division division = userEntity.getDivision();
			divisionCode = division.getDivisionId();
		} else {
			divisionCode = null;
		}

		if (r.contains("ROLE_DISTRICT")) {
			District district = userEntity.getDistrict();
			districtCode = district.getDistrictCode();
		} else {
			districtCode = "";
		}

		ImplementationAgency implAgency = null;
		try {
			Page<Work> works = null;

			List<String> schemes = new ArrayList<String>();

			String blockName = null;
			if (blockId != null) {
				blockName = blockRepository.findById(Long.valueOf(blockId)).orElse(null).getBlockName();
			}
			Long divisionIds = null;
			if (divisionId != null) {
				divisionIds = Long.valueOf(divisionId);
//				divisionName = divisionRepository.findById(Long.valueOf(divisionId)).orElse(null).getDivisionName();
			}
			Long districtIds = null;
			if (districtId != null) {
				districtIds = Long.valueOf(districtId);
//				districtName = districtRepository.findById(Long.valueOf(districtId)).orElse(null).getDistrictName();
			}
			// String gpName = null;

			String workStatusName = null;
			if (workStatusId != null) {
				workStatusName = workStatusRepository.findById(Long.valueOf(workStatusId)).orElse(null)
						.getWorkStatusNameE();
			}
			String gpName = null;

			if (divisionCode != null) {

				/*
				 * else if (!StringUtils.isEmpty(workType)) {
				 * System.out.println("SearchBoxWT....." + workName); works = workRepository.
				 * findByWorkTypeContainingAndDivisionCodeAndStatusNotInAndWorkStatusIn(
				 * pageable, workType, divisionCode, DMSConstants.STATUS_DELETED,
				 * DMSConstants.WORK_STATUS_HANDOVER);
				 * 
				 * }
				 */ /*
					 * else if (null!=financialYearId) { System.out.println("SearchBoxf....." +
					 * workName); works = workRepository.
					 * findByFinancialYearContainingAndDivisionCodeAndStatusNotInAndWorkStatusIn(
					 * pageable, financialYearId, divisionCode, DMSConstants.STATUS_DELETED,
					 * DMSConstants.WORK_STATUS_HANDOVER);
					 * 
					 * }
					 */
//					else if (!StringUtils.isEmpty(divisionName) || workSubTypeIdInt != null
//							|| !StringUtils.isEmpty(workStatusName)) {
				logger.info("SearchBoxf....." + workName);
				works = workRepository.findByDivisionCode(pageable, workName, divisionCode, divisionIds, districtIds,
						workSubTypeIdInt, workStatusId, financialYearId, workTypeId, agency);

				/*
				 * else { System.err.println(".......111111111111.........."); works =
				 * workRepository.findByDivisionCodeAndStatusNotInAndWorkStatusIn(pageable,
				 * divisionCode, DMSConstants.STATUS_DELETED,
				 * DMSConstants.WORK_STATUS_HANDOVERS); }
				 */

			}

			else if (districtCode.equals("")) {

				/*
				 * if (!StringUtils.isEmpty(workName)) { System.out.println("SearchBoxWo....." +
				 * workName+"..."+districtCode);
				 * 
				 * works = workRepository.
				 * findByWorkNameContainingAndDistrictCodeAndStatusNotInAndWorkStatusIn(
				 * pageable, workName, districtCode, DMSConstants.STATUS_DELETED,
				 * DMSConstants.WORK_STATUS_HANDOVERS);
				 * 
				 * }
				 */ /*
					 * else if (!StringUtils.isEmpty(workType)) {
					 * System.out.println("workType....workType....." + workType); works =
					 * workRepository.
					 * findByWorkTypeContainingAndDistrictCodeAndStatusNotInAndWorkStatusIn(
					 * pageable, workType, districtCode, DMSConstants.STATUS_DELETED,
					 * DMSConstants.WORK_STATUS_HANDOVER);
					 * 
					 * }
					 */ /*
						 * else if (null!=financialYearId) { System.out.println("SearchBoxf....." +
						 * workName); works = workRepository.
						 * findByFinancialYearContainingAndDistrictCodeAndStatusNotInAndWorkStatusIn(
						 * pageable, financialYearId, districtCode, DMSConstants.STATUS_DELETED,
						 * DMSConstants.WORK_STATUS_HANDOVER);
						 * 
						 * }
						 */
				works = workRepository.findByDistrictCodeContainingAndDistrictCodeAndStatusNotInAndWorkStatusIn(
						pageable, workName, districtCode, divisionIds, districtIds, workSubTypeIdInt, workStatusId,
						financialYearId, workTypeId, agency);

				/*
				 * else { works =
				 * workRepository.findByDistrictCodeAndStatusNotInAndWorkStatusIn(pageable,
				 * districtCode, DMSConstants.STATUS_DELETED,
				 * DMSConstants.WORK_STATUS_HANDOVERS); }
				 */
			}

			else {

				if (!StringUtils.isEmpty(workName) || null != (divisionIds) || null != workStatusName
						|| workSubTypeIdInt != null || null != financialYear || null != workTypeId || null != agency) {
					logger.info("SearchBoxWo....." + workName);
					/*
					 * works = workRepository.findByWorkNameContainingAndStatusNotInAndWorkStatusIn(
					 * pageable, workName, DMSConstants.STATUS_DELETED,
					 * DMSConstants.WORK_STATUS_HANDOVERS);
					 */

					/*
					 * else if (!StringUtils.isEmpty(workType)) { System.out.println("workType....."
					 * + workType); works =
					 * workRepository.findByWorkTypeContainingAndStatusNotInAndWorkStatusIn(
					 * pageable, workType, DMSConstants.STATUS_DELETED,
					 * DMSConstants.WORK_STATUS_HANDOVER);
					 * 
					 * }
					 */

					/*
					 * else if (null !=financialYear) { System.out.println("financialYear....." +
					 * financialYear); works =
					 * workRepository.findByFinancialYearIdAndStatusNotInAndWorkStatusIn(pageable,
					 * financialYearId); }
					 */

					// else if (!StringUtils.isEmpty(divisionName) ||
					// !StringUtils.isEmpty(workStatusName) || workSubTypeIdInt != null) {

					works = workRepository.findByDivisionNameContainingAndStatusNotInAndWorkStatusIn(pageable, workName,
							divisionIds, districtIds, workSubTypeIdInt, workStatusId, financialYearId, workTypeId,
							agency);
				}

				else {
					works = workRepository.findByStatusNotInAndWorkStatusIn(pageable, DMSConstants.STATUS_DELETED,
							DMSConstants.WORK_STATUS_HANDOVERS);
				}
			}

			if (works != null) {
				List<Work> entityList = works.getContent();
				List<WorkBean> beanList = new ArrayList<>();
				if (entityList != null && !entityList.isEmpty()) {

					int index = pageable.getPageNumber() * pageable.getPageSize();
					for (Work work : entityList) {

						WorkBean bean = convertWorkEntityToBean(work, "All");
						bean.setIndex(++index);
						beanList.add(bean);
					}
				}
				workJson = new WorkJson();
				workJson.setiTotalDisplayRecords(works.getTotalElements());
				workJson.setiTotalRecords(workRepository.countByStatusNotIn(DMSConstants.STATUS_DELETED));

				workJson.setAaData(beanList);
			}
			return workJson;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return workJson;
		}
	}

	@Override
	public TASAReviseJson fetchTSASRevisedList(Pageable pageable, Object typeDoc, Object rvOrderNo, Object rvOrderDate,
			Object rvAmt, Object searchParameterWorkName, Object rvFileId, Object rvRemarks, Long workId) {

		TASAReviseJson tsJson = null;
		User user = DMSUtil.getUserDetail();

		Users userEntity = userRepository.findByUsernameAndStatus(user.getUsername(), DMSConstants.STATUS_ACTIVE);

		Collection<GrantedAuthority> role = user.getAuthorities();

		try {
			List<Object[]> tsPage = null;

			// Long workId=(Long) searchParameterWorkName ;

			tsPage = tsasReviseWorkRepository.findByWorkId(workId);

			if (tsPage != null) {
				// List<TSASReviseWork> entityList = tsPage.getContent();
				List<TSASReviseWorkBean> beanList = new ArrayList<>();
				if (tsPage != null && !tsPage.isEmpty()) {

					int index = pageable.getPageNumber() * pageable.getPageSize();
					for (Object[] tsasReviseWork : tsPage) {

						// TSASReviseWorkBean bean = convertTSASRevisedWorkEntityToBean(tsasReviseWork);
						// bean.setIndex(++index);

						TSASReviseWorkBean tsasReviseWorkBean = null;

						if (tsasReviseWork != null) {

							tsasReviseWorkBean = new TSASReviseWorkBean();
							tsasReviseWorkBean.setIndex(++index);
							if (null != tsasReviseWork[0].toString()) {
								tsasReviseWorkBean.setId(Long.parseLong(tsasReviseWork[0].toString()));
							}
							if (null != tsasReviseWork[1].toString()) {
								tsasReviseWorkBean.setWorkId(Long.parseLong(tsasReviseWork[1].toString()));
							}
							if (null != tsasReviseWork[2].toString()) {
								tsasReviseWorkBean.setWorkStatus(tsasReviseWork[2].toString());
							}
							if (null != tsasReviseWork[3].toString()) {
								tsasReviseWorkBean.setTypeDoc(tsasReviseWork[3].toString());
							}
							// if(null != tsasReviseWork[4].toString()) {
							tsasReviseWorkBean.setRvOrderNo(tsasReviseWork[4].toString());
							// }
							if (null != tsasReviseWork[5].toString()) {
								tsasReviseWorkBean.setRvOrderDate(tsasReviseWork[5].toString());
							}
							if (null != tsasReviseWork[6].toString()) {
								tsasReviseWorkBean.setRvAmt(new BigDecimal(tsasReviseWork[6].toString()));
							}
							if (null != tsasReviseWork[8].toString()) {
								tsasReviseWorkBean.setRvFileId(Long.parseLong(tsasReviseWork[8].toString()));
							}
							if (null != tsasReviseWork[7].toString()) {
								// tsasReviseWorkBean.setFileName(tsasReviseWork.getDocumentUploadRevised().getDocumentName());
								tsasReviseWorkBean.setRvRemarks(tsasReviseWork[7].toString());
							}

							if (null != tsasReviseWork[9].toString()) {
								// tsasReviseWorkBean.setFileName(tsasReviseWork.getDocumentUploadRevised().getDocumentName());
								tsasReviseWorkBean.setTsAsSataus(tsasReviseWork[9].toString());
							}

							if (null != tsasReviseWork[10].toString()) {
								tsasReviseWorkBean.setFlag(Long.parseLong(tsasReviseWork[10].toString()));
							}

						}

						beanList.add(tsasReviseWorkBean);
					}
				}
				tsJson = new TASAReviseJson();
				// tsJson.setiTotalDisplayRecords(tsPage.getTotalElements());
				tsJson.setiTotalRecords(workRepository.countByStatusNotIn(DMSConstants.STATUS_DELETED));

				tsJson.setAaData(beanList);
			}
			return tsJson;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return tsJson;
		}
	}

	public static <T> java.util.function.Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Map<Object, Boolean> seen = new HashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	@Override
	public WorkProgressImagesJson fetchProgressImagesList(Pageable pageable, Object workStatusName,
			Object workSubStatusName, Object actionTakenDelay, Object reasonDelay, Object createdDate,
			Object subDelayReason, Object documentId, Long workId) {

		WorkProgressImagesJson json = null;
		User user = DMSUtil.getUserDetail();

		Users userEntity = userRepository.findByUsernameAndStatus(user.getUsername(), DMSConstants.STATUS_ACTIVE);

		Collection<GrantedAuthority> role = user.getAuthorities();

		try {
			List<DocumentUploadWorkProgress> wpPage = null;

			// Long workId=(Long) searchParameterWorkName ;

			List<DocumentUploadWorkProgress> progressRows = documentUploadWorkProgressRepository.findAllByWorkId(workId);
			if (progressRows != null && !progressRows.isEmpty()) {
				wpPage = progressRows;
			}

			if (wpPage != null) {
				// List<TSASReviseWork> entityList = tsPage.getContent();
				List<DocumentUploadWorkProgressBean> beanList = new ArrayList<>();
				if (wpPage != null && !wpPage.isEmpty()) {

					int index = pageable.getPageNumber() * pageable.getPageSize();
					for (DocumentUploadWorkProgress documentUploadWorkProgress : wpPage) {

						DocumentUploadWorkProgressBean bean = new DocumentUploadWorkProgressBean();
						bean.setIndexWS(++index);
						bean.setDocumentId(documentUploadWorkProgress.getDocumentId());
						bean.setDocumentName(documentUploadWorkProgress.getDocumentName());
						bean.setCreatedDate(documentUploadWorkProgress.getCreatedDate());
						if (documentUploadWorkProgress.getWorkSubStatusId() != null) {
							WorkSubStatus workSubStatus = workSubStatusRepository
									.findByWorkSubStatusId(documentUploadWorkProgress.getWorkSubStatusId());
							if (workSubStatus != null) {
								bean.setWorkSubStatusId(workSubStatus.getWorkSubStatusId());
								if ("In-Progress".equals(documentUploadWorkProgress.getWorkStatusNameE())) {
									bean.setWorkSubStatusNameE(workSubStatus.getWorkSubStatusNameE());
									bean.setReasonDelay("-");
								} else {
									bean.setReasonDelay(workSubStatus.getWorkSubStatusNameE());
									bean.setWorkSubStatusNameE("-");
								}
							} else {
								bean.setWorkSubStatusNameE("-");
								bean.setReasonDelay("-");
							}
						} else {
							bean.setWorkSubStatusNameE("-");
							bean.setReasonDelay("-");

						}
						if (documentUploadWorkProgress.getWorkStatusId() != null) {
							bean.setWorkStatusId(documentUploadWorkProgress.getWorkStatusId());
							bean.setWorkStatusNameE(documentUploadWorkProgress.getWorkStatusNameE());
						}

						if (documentUploadWorkProgress.getActionTakenDelay() != null) {
							bean.setActionTakenDelay(documentUploadWorkProgress.getActionTakenDelay());
						} else {
							bean.setActionTakenDelay("-");
						}

						if (documentUploadWorkProgress.getWorkSubDelayReasonId() != null) {
							WorkSubDelayReson subDelay = workSubDelayResonRepository
									.findById(documentUploadWorkProgress.getWorkSubDelayReasonId()).orElse(null);
							bean.setWorkSubDelayReason(subDelay.getSubDelayReason());
						} else {
							bean.setWorkSubDelayReason("-");
						}

						if (documentUploadWorkProgress.getRemarks() != null) {
							bean.setRemarks(documentUploadWorkProgress.getRemarks());
						} else {
							bean.setRemarks("-");
						}
						bean.setLattitude(documentUploadWorkProgress.getLattitude());
						bean.setLongitude(documentUploadWorkProgress.getLongitude());
						bean.setAddress(documentUploadWorkProgress.getAddress());
						if (bean.getDocumentName() != null) {
							bean.setImagepath(imageUrl.substring(0, 21) + "/anuppur/mobile/downloadDocumentWSPro/"
									+ bean.getDocumentId());
						}
						/*
						 * List<DocumentUploadWorkProgress> byWorkIdAndCreatedDate =
						 * documentUploadWorkProgressRepository .findByWorkIdAndCreatedDate(workId,
						 * documentUploadWorkProgress.getCreatedDate());
						 * 
						 * bean.setMoreImage(byWorkIdAndCreatedDate.size() > 1);
						 */
						beanList.add(bean);
					}

					// beanList =
					// beanList.stream().filter(distinctByKey(DocumentUploadWorkProgressBean::getCreatedDate))
					// // Filter
					// distinct
					// by
					// createdDate
					// .collect(Collectors.toList());
					// AtomicInteger indexa = new AtomicInteger(1);

					// beanList.forEach(bean -> bean.setIndexWS(indexa.getAndIncrement()));

				}
				json = new WorkProgressImagesJson();
				json.setiTotalRecords((long) beanList.size());
				json.setiTotalDisplayRecords((long) beanList.size());

				json.setAaData(beanList);
			}
			return json;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return json;
		}
	}

	@Override
	public ExpensesDataJson fetchExpensesDataList(Pageable pageable, Object totalExpensess, Object expensessUptoMarch,
			Object expensessCurrentFy, String year, Object createdDate, Long workTypeId) {

		ExpensesDataJson json = new ExpensesDataJson();
		json.setAaData(new ArrayList<>());
		json.setiTotalRecords(0);
		json.setiTotalDisplayRecords(0);

		try {
			List<ExpensesData> exPage;
			if (year != null && !year.isEmpty()) {
				Long newYear = Long.parseLong((String) year);
				exPage = expensesDataRepository.findByYearAndWorkIdIn(newYear, Collections.singletonList(workTypeId));
			} else {
				exPage = expensesDataRepository.findByWorkId(workTypeId);
			}
			if (exPage == null) {
				exPage = Collections.emptyList();
			}

			List<ExpensesDataBean> beanList = new ArrayList<>();
			int index = pageable.getPageNumber() * pageable.getPageSize();
			BigDecimal totalprev = BigDecimal.ZERO;

			for (ExpensesData expensesData : exPage) {
				ExpensesDataBean bean = new ExpensesDataBean();
				bean.setIndex(++index);
				bean.setCreatedDate(DMSUtil.convertDateToString(expensesData.getCreatedDate()));

				BigDecimal currentFy = expensesData.getExpensessCurrentFy() != null ? expensesData.getExpensessCurrentFy()
						: BigDecimal.ZERO;
				BigDecimal uptoMarch = expensesData.getExpensessUptoMarch() != null ? expensesData.getExpensessUptoMarch()
						: BigDecimal.ZERO;
				bean.setExpensessCurrentFy(currentFy);
				bean.setExpensessUptoMarch(uptoMarch);

				if (expensesData.getTotalExpensess() == null) {
					BigDecimal baseTotal = BigDecimal.ZERO;
					List<ExpensesData> allForWork = expensesDataRepository.findByWorkId(workTypeId);
					if (allForWork != null && !allForWork.isEmpty() && allForWork.get(0).getTotalExpensess() != null) {
						baseTotal = allForWork.get(0).getTotalExpensess();
					}
					bean.setTotalExpensess(uptoMarch.add(baseTotal));
					totalprev = bean.getTotalExpensess();
				} else {
					bean.setTotalExpensess(expensesData.getTotalExpensess().add(currentFy));
					totalprev = expensesData.getTotalExpensess();
				}

				bean.setYear(expensesData.getYear());
				beanList.add(bean);
			}

			json.setAaData(beanList);
			json.setiTotalRecords(beanList.size());
			json.setiTotalDisplayRecords(beanList.size());
			return json;
		} catch (Exception e) {
			logger.error("fetchExpensesDataList failed for workId {}", workTypeId, e);
			return json;
		}
	}

	private WorkBean convertWorkEntityToBean(Work work, String modeType) {

		WorkBean workBean = null;

		if (work != null) {

			workBean = new WorkBean();
			DocumentUploadDrawingDetail documentUploadDrawingDetail = documentUploadDrawingDetailRepository
					.findByWorkId(work.getId());
			if (documentUploadDrawingDetail != null) {
				workBean.setDrawingId(documentUploadDrawingDetail.getDocumentId());
				workBean.setFileStatus("1");
				workBean.setDrawingStatus(documentUploadDrawingDetail.getStatus());
			} else {
				workBean.setFileStatus("0");
			}

			// DocumentUpload documentUpload = documentRepository.findByDocumentId()

			workBean.setId(work.getId());
			workBean.setWorkId(work.getId());
			workBean.setWorkNo(work.getWorkNo());
			workBean.setWorkName(work.getWorkName());
			workBean.setWorkNo(work.getWorkNo());
			workBean.setMultifundedStatus(work.getMultifundedStatus());
			workBean.setFundByState(work.getFundByState());
			workBean.setFundByEcrp2(work.getFundByEcrp2());
			workBean.setFundByNhm(work.getFundByNhm());
			workBean.setFundByOthers(work.getFundByOthers());
			workBean.setWorkRequestStatusId(work.getWorkRequestStatusId());
			workBean.setDrawCreatedBy(work.getDrawCreatedBy());
			workBean.setAllocatedAmount(work.getAllocatedAmount());
			if (work.getWorkSubtypeId() != null) {
				workBean.setWorkSubTypeId(work.getWorkSubtypeId());
				workBean.setWorkSubTypeName(
						workSubTypeRepository.findById(work.getWorkSubtypeId()).orElse(null).getWorkSubTypeNameE());
			}

			// workBean.setWorkType(work.getWorkType());
			// workBean.setWorkTypeId(workTypeRepository.findByWorkTypeNameE(work.getWorkType()).getWorkTypeId());

			boolean isGeoTagged = false;

			if (work != null && work.getId() != null) {
				isGeoTagged = geoLocationRepository.existsByWorkId(work.getId());
			}

			workBean.setIsGeoTagged(isGeoTagged);

			if (null != work.getFinancialYear()) {
				FinancialYear financialYear = financialYearRepository.findById(work.getFinancialYear()).orElse(null);
				if (null != financialYear) {
					workBean.setFinancialYear(financialYear.getId());
					workBean.setFinancialYearName(financialYear.getFinancialYear());
				}
			}

			if (work.getScheme() != null) {

				Schemes schemes = schemeRepository.findById(work.getScheme()).orElse(null);
				if (schemes != null) {
					workBean.setScheme(schemes.getSchemeName());
					workBean.setSchemeId(schemes.getId());

				}
			}

			if (work.getSchemeState() != null) {

				Schemes schemesSate = schemeRepository.findById(work.getSchemeState()).orElse(null);
				workBean.setSchemeState(schemesSate.getSchemeName());
				workBean.setSchemeStateId(schemesSate.getId());

			}

			if (work.getSchemeNhm() != null) {

				Schemes schemesNhm = schemeRepository.findById(work.getSchemeNhm()).orElse(null);
				workBean.setSchemeNhm(schemesNhm.getSchemeName());
				workBean.setSchemeNhmId(schemesNhm.getId());

			}

			if (work.getSchemeEcrp2() != null) {

				Schemes schemesEcpr = schemeRepository.findById(work.getSchemeEcrp2()).orElse(null);
				workBean.setSchemeEcpr2(schemesEcpr.getSchemeName());
				workBean.setSchemeEcpr2Id(schemesEcpr.getId());

			}

			workBean.setDmStatus(work.getDmStatus());
			workBean.setDmRemakrs(work.getDmRemakrs());

			if (work.getSchemeOthers() != null) {

				Schemes schemesOthers = schemeRepository.findById(work.getSchemeOthers()).orElse(null);
				workBean.setSchemeOthers(schemesOthers.getSchemeName());
				workBean.setSchemeOthersId(schemesOthers.getId());

			}

			/*
			 * if (work.getWorkType() != null) { System.err.println("----- " +
			 * work.getWorkType()); WorkType workType =
			 * workTypeRepository.findByWorkTypeNameE(work.getWorkType()); //
			 * System.err.println(("@@@@@@@@@@@@@@@@" + workType.getWorkTypeId())); //
			 * System.err.println("============@@@@@@@@@@@@============" +
			 * workType.getWorkTypeNameE()); if (null != workType) {
			 * System.err.println("========================" + workType.getWorkTypeNameE());
			 * workBean.setWorkType(workType.getWorkTypeNameE() != null ?
			 * workType.getWorkTypeNameE() : "NA");
			 * workBean.setWorkTypeId(workType.getWorkTypeId()); } }
			 */

			// workBean.setWorkType(work.getWorkType() != null ? work.getWorkType(): "-");
			// WorkType workType =
			// workTypeRepository.findByWorkTypeNameE(work.getWorkType());
			if (null != work.getWorkType()) {
				WorkType workType = workTypeRepository.findById(work.getWorkType()).orElse(null);
				if (null != workType) {
					workBean.setWorkType(workType.getWorkTypeNameE());
					workBean.setWorkTypeId(workType.getWorkTypeId());
				}

			}
			if (work.getWorkSubtypeId() != null)

			{

				WorkSubType workSubType = workSubTypeRepository.findById(work.getWorkSubtypeId()).orElse(null);
				workBean.setWorkSubTypeId(workSubType.getWorkSubtypeId());
				workBean.setWorkSubTypeName(workSubType.getWorkSubTypeNameE());

			}

			if (work.getWorkCategoryId() != null) {
				WorkCategory workCategory = workCategoryRepository.findById(work.getWorkCategoryId()).orElse(null);
				workBean.setWorkCategoryId(workCategory.getWorkCategoryId());
				workBean.setWorkCategoryName(workCategory.getWorkCategoryNameE());

			}

			if (work.getCategorySubtypeId() != null) {

				SubCategory subCategory = subCategoryRepository.findById(work.getCategorySubtypeId()).orElse(null);
				workBean.setCategorySubTypeName(subCategory.getCategorySubTypeNameE());
				workBean.setCategorySubTypeId(subCategory.getCategorySubTypeId());

			}

			if (work.getWorkHead() != null) {
				// WorkHead workHead = workHeadRepository.findByHeadName(work.getWorkHead());
				WorkHead workHead = workHeadRepository.findById(work.getWorkHead()).orElse(null);
				if (workHead != null) {
					workBean.setHeadId(workHead.getId());
					// workBean.setHead(work.getWorkHead() != null ? work.getWorkHead() : "NA");
					workBean.setHead(work.getWorkHead());
				}
			}
			if (work.getHeadState() != null) {
				workBean.setHeadStateId(work.getHeadState());
				WorkHead workHead = workHeadRepository.findById(work.getHeadState()).orElse(null);
				workBean.setHeadState(workHead.getHeadName() != null ? workHead.getHeadName() : "NA");
			}

			if (work.getHeadNhm() != null) {
				workBean.setHeadNhmId(work.getHeadNhm());
				WorkHead workHead = workHeadRepository.findById(work.getHeadState()).orElse(null);
				workBean.setHeadNhm(workHead.getHeadName() != null ? workHead.getHeadName() : "NA");
			}

			if (work.getHeadEcrp2() != null) {
				workBean.setHeadEcpr2Id(work.getHeadEcrp2());
				WorkHead workHead = workHeadRepository.findById(work.getHeadState()).orElse(null);
				workBean.setHeadEcpr2(workHead.getHeadName() != null ? workHead.getHeadName() : "NA");
			}

			if (work.getHeadOthers() != null) {
				workBean.setHeadOthersId(work.getHeadOthers());
				WorkHead workHead = workHeadRepository.findById(work.getHeadState()).orElse(null);
				workBean.setHeadOthers(workHead.getHeadName() != null ? workHead.getHeadName() : "NA");
			}

			workBean.setEstimatedAmt(work.getEstimatedAmt() != null ? work.getEstimatedAmt() : BigDecimal.ZERO);
			// workBean.setAmtReleasedTillDate(work.getAmtReleasedTillDate()!=null?
			// work.getAmtReleasedTillDate() : BigDecimal.ZERO);

			// ImplementationAgencyType implementationAgencyType = null;

			ImplementationAgency implementationAgency = implAgencyRepository
					.findByImplementationAgencyId(work.getImplementationAgency());

			if (null != implementationAgency) {
				workBean.setImplementationAgency(implementationAgency.getImplementationAgencyId());
				workBean.setImplementationAgencyName(implementationAgency.getImplAgencyname());
			}

			/*
			 * if (work.getImplementationAgency() != null) { // ImplementationAgency
			 * implementationAgency = implAgencyRepository //
			 * .findByImplAgencynameAndEnabled(work.getImplementationAgency(), (short) 1);
			 * ImplementationAgency implementationAgency = implAgencyRepository
			 * .findByImplementationAgencyId(work.getImplementationAgency()); if
			 * (implementationAgency != null)
			 * workBean.setImplementationAgencyId(implementationAgency.
			 * getImplementationAgencyId()); else {
			 * workBean.setImplementationAgency(work.getImplementationAgency()); //
			 * workBean.setJpId(work.getJpId()); }
			 * workBean.setImplementationAgency(work.getImplementationAgency());
			 * 
			 * }
			 */

			District district = districtRepository.findByDistrictCodeAndEnabled(work.getDistrictCode(), (short) 1);
			workBean.setDistrictName(district.getDistrictName());
			workBean.setDistrictId(district.getDistrictId());
			workBean.setDistrictCode(district.getDistrictCode());
			if (null != work.getDivisionCode()) {
				Division division = divisionRepository.findById(work.getDivisionCode()).orElse(null);

				workBean.setDivisionId(division.getDivisionId());
				// workBean.setDivisionName(work.getDivisionName());
				workBean.setDivisionName(division.getDivisionName());
			}

			Block block = null;
			if (work.getBlockCode() != null) {
				block = blockRepository.findByBlockCode(work.getBlockCode());
			}
			if (block != null) {
				workBean.setBlockId(block.getBlockId());
				// workBean.setRural("1");
				// workBean.setBlockId(block.getBlockId());
			}
			workBean.setBlockCode(block != null ? work.getBlockCode() : "");
			// workBean.setBlockName(block != null ? work.getBlockName() : "");
			workBean.setBlockName(block != null ? block.getBlockName() : "");

			GramPanchayat gramPanchayat = gramPanchayatRepository.findByGramPanchayat(work.getGramPanchayatCode());
			if (null != gramPanchayat) {
				workBean.setGramPanchayatCode(gramPanchayat.getGramPanchayatCode());
				workBean.setGramPanchayatName(gramPanchayat.getGramPanchayatName());
			}

//			LegislativeConstituency lc = legislativeConsRepository
//					.findByConstituencyNameAndDistrict(work.getLegislativeConstituencyName(), district);
			LegislativeConstituency lc = legislativeConsRepository
					.findByIdAndDistrict(work.getLegislativeConstituencyId(), district);
			if (lc != null) {
				workBean.setConstituencyCode(lc.getConstituencyCode());
				workBean.setConstituencyName(lc.getConstituencyName());

			}
			WorkStatus workStatus = workStatusRepository.findById(work.getWorkStatus()).orElse(null);
			if (null != workStatus) {
				workBean.setWorkStatusName(workStatus.getWorkStatusNameE());
				workBean.setWorkStatusId(workStatus.getId());
			}
			workBean.setStatus(work.getStatus());
			workBean.setSecureAmtStatus(work.getSecureAmtStatus());
			workBean.setStartDate(work.getStartDate());

			workBean.setId(work.getId());
			workBean.setTsAmt(work.getTsAmt());
			workBean.setTsNo(work.getTsNo() != null ? work.getTsNo() : "");
			workBean.setTsDate(work.getTsDate());
			if (null != work.getDocumentUploadTechnical()) {
				workBean.setTsFileId(work.getDocumentUploadTechnical().getDocumentId());
			}
			if (null != work.getDocumentUploadAdministration()) {
				workBean.setAsFileId(work.getDocumentUploadAdministration().getDocumentId());
			}
			if (null != work.getDocumentUploadTechnical()) {
				workBean.setFileName(work.getDocumentUploadTechnical().getDocumentName());
			}
			workBean.setTsRemarks(work.getTsRemarks());

			workBean.setAsAmt(work.getAsAmt());
			workBean.setAsNo(work.getAsNo());
			workBean.setAsDate(work.getAsDate());
			workBean.setAsRemarks(work.getAsRemarks());
			workBean.setIsTenders(work.getIsTenders());
			if (null != work.getWorkPriorityId()) {
				WorkPriority workPriority = workPriorityRepository.findById(work.getWorkPriorityId()).orElse(null);
				workBean.setWorkPriorityId(workPriority.getId());
				workBean.setWorkPriority(workPriority.getWorkPriorityName());
			}

			/*
			 * if (null != work.getFinancialHeadId()) { FinancialHead financialHead =
			 * financialHeadRepository.findById(work.getFinancialHeadId()).orElse(null);
			 * workBean.setFinancialHeadId(financialHead.getId());
			 * workBean.setFinancialHeadName(financialHead.getFinancialHeadName()); }
			 */

			if (null != work.getVidhanSabhaId()) {
				VidhanSabha vidhanSabha = vidhanSabhaRepositorys.findById(work.getVidhanSabhaId()).orElse(null);
				workBean.setVidhanSabhaId(vidhanSabha.getId());
				workBean.setVidhanSabhaName(vidhanSabha.getVidhanSabhaName());
			}

			List<WorkFinancialAgency> workFinancialAgency = financialAgencyRepository.findByWorkId(work.getId());
			if (workFinancialAgency != null) {
				List<FinancialAgencyBean> beanList = new ArrayList<FinancialAgencyBean>();
				for (WorkFinancialAgency entity : workFinancialAgency) {
					FinancialAgencyBean bean = new FinancialAgencyBean();
					bean.setId(entity.getId());
					bean.setFinancialHeadId(entity.getFinancialHeadId());
					bean.setCost(entity.getCost());
					bean.setTotalCost(entity.getTotalCost());

					// Fetch and set the financial head name
					if (entity.getFinancialHeadId() != null) {
						FinancialHead financialHead = financialHeadRepository.findById(entity.getFinancialHeadId())
								.orElse(null);
						if (financialHead != null) {
							bean.setFinancialAgencyName(financialHead.getFinancialHeadName());
						}
					}

					beanList.add(bean);
				}
				workBean.setFinancialHeads(beanList);
			}
		}

		return workBean;
	}

	private OtherDocListBean convertWorkDocumentEntityToOtherDocBean(WorkDocument entity) {

		OtherDocListBean bean = new OtherDocListBean();
		if (entity != null) {
			bean.setOtherDocumentId(entity.getId());
			bean.setNoOfDocs(entity.getNoOfDocs());
			bean.setRemarks(entity.getNameOfDocs());
			bean.setOtherDocDate(DMSUtil.convertDateToString(entity.getDateOfDocs()));
			bean.setOtherDocumentName(entity.getFileName());
			Map<String, String> map = new HashMap<String, String>();
			map.put("formattedDate", DMSUtil.convertDateToString(entity.getDateOfDocs()));
			map.put("name", entity.getFileName());
			bean.setFillArr(map);
		}
		return bean;
	}

	@Override
	public List<WorkTypeBean> fetchWorkTypes() {

		try {
			List<WorkType> list = workTypeRepository.findByEnabled((short) 1);

			List<WorkTypeBean> beanList = new ArrayList<>();
			for (WorkType workType : list) {
				beanList.add(convertWorkTypeEntityToBean(workType));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}

	}

	@Override
	public List<WorkSubTypeBean> fetchWorkSubTypes() {

		try {
			List<WorkSubType> list = workSubTypeRepository.findByEnabled((short) 1);

			List<WorkSubTypeBean> beanList = new ArrayList<>();
			for (WorkSubType workSubType : list) {
				beanList.add(convertWorkSubTypeEntityToBean(workSubType));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}

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

	private WorkSubTypeBean convertWorkSubTypeEntityToBean(WorkSubType entity) {

		WorkSubTypeBean bean = new WorkSubTypeBean();
		if (entity != null) {
			bean.setWorkSubTypeId(entity.getWorkSubtypeId());
			bean.setWorkSubTypeNameE(entity.getWorkSubTypeNameE());
			bean.setWorkSubTypeNameH(entity.getWorkSubTypeNameH());
			bean.setEnabled(entity.getEnabled());
		}
		return bean;
	}

	@Override
	public List<ImplAgencyBean> fetchImplAgency() {

		try {
			List<ImplementationAgency> list = implAgencyRepository.findByEnabled(DMSConstants.ENABLED);

			List<ImplAgencyBean> beanList = new ArrayList<>();
			for (ImplementationAgency entity : list) {
				beanList.add(convertImplAgencyEntityToBean(entity));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred in fetchImplAgency Method.", e);
			return null;
		}

	}

	@Override
	public List<ImplAgencyTypeBean> fetchImplAgencyType() {

		try {
			List<ImplementationAgencyType> list = implAgencyTypeRepository.findAll();

			List<ImplAgencyTypeBean> beanList = new ArrayList<>();
			for (ImplementationAgencyType entity : list) {
				beanList.add(convertImplAgencyTypeEntityToBean(entity));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred in fetchImplAgencyType Method.", e);
			return null;
		}

	}

	private ImplAgencyBean convertImplAgencyEntityToBean(ImplementationAgency entity) {

		ImplAgencyBean bean = new ImplAgencyBean();

		if (entity != null) {

			bean.setImplementationAgencyId(entity.getImplementationAgencyId());
			bean.setImplementationAgencyNameE(entity.getImplAgencyname());
			bean.setImplementationAgencyNameH(entity.getImplAgencyname());
			bean.setCreatedBy(entity.getCreatedBy());
			bean.setCreatedDate(entity.getCreatedDate());
			bean.setEnabled(entity.getEnabled());
			if (entity.getImplAgencyType() != null) {
				ImplementationAgencyType implementationAgencyType = implAgencyTypeRepository
						.findById(entity.getImplAgencyType()).orElse(null);
				bean.setImplAgencyType(
						implementationAgencyType != null ? implementationAgencyType.getImplAgencyType() : "-");
			} else {
				bean.setImplAgencyType("-");
			}
			bean.setImplAgencyTypeId(entity.getImplAgencyType());
		}
		return bean;
	}

	private ImplAgencyTypeBean convertImplAgencyTypeEntityToBean(ImplementationAgencyType entity) {

		ImplAgencyTypeBean bean = new ImplAgencyTypeBean();

		if (entity != null) {
			bean.setId(entity.getId());
			bean.setImplAgencyType(entity.getImplAgencyType());
		}
		return bean;
	}

	@Override
	public List<WorkCategoryBean> fetchWorkCategories() {

		try {
			List<WorkCategory> list = workCategoryRepository
					.findByEnabledOrderByWorkCategoryNameE(DMSConstants.ENABLED);

			List<WorkCategoryBean> beanList = new ArrayList<>();
			for (WorkCategory entity : list) {
				beanList.add(convertWorkCategoryEntityToBean(entity));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred in fetchWorkCategories Method.", e);
			return null;
		}

	}

	@Override
	public List<SchemeBean> fetchSchemes() {

		try {
			List<Schemes> list = schemeRepository.findByEnabled(DMSConstants.ENABLED);

			List<SchemeBean> beanList = new ArrayList<>();
			for (Schemes entity : list) {
				beanList.add(convertSchemeEntityToBean(entity));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred in fetchSchemes Method.", e);
			return null;
		}

	}

	@Override
	public List<HeadBean> fetchHeads() {

		try {
			List<Head> list = headRepository.findByEnabled(DMSConstants.ENABLED);

			List<HeadBean> beanList = new ArrayList<>();
			for (Head entity : list) {
				beanList.add(convertHeadEntityToBean(entity));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred in fetchHead Method.", e);
			return null;
		}

	}

	@Override
	public List<WorkSubStatusBean> fetchSubWorkStatus() {

		try {
			List<WorkSubStatus> list = workSubStatusRepository.findAllDistinctWorkSubStatusNames();

			List<WorkSubStatusBean> beanList = new ArrayList<>();
			for (WorkSubStatus entity : list) {
				beanList.add(convertWorkSubStatusEntityToBean(entity));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred in fetchHead Method.", e);
			return null;
		}

	}

	private SchemeBean convertSchemeEntityToBean(Schemes entity) {

		SchemeBean bean = new SchemeBean();

		if (entity != null) {
			bean.setSchemeId(entity.getId());
			bean.setSchemeNameE(entity.getSchemeName());
			bean.setSchemeNameH(entity.getSchemeName());
			bean.setEnabled(entity.getEnabled());
			bean.setCreatedBy(entity.getCreatedBy());
			bean.setCreatedDate(entity.getCreatedDate());
		}
		return bean;
	}

	private SorYearBean convertSorEntityToBean(SorYear entity) {

		SorYearBean bean = new SorYearBean();

		if (entity != null) {
			bean.setSorYearId(entity.getId());
			bean.setSorYearName(entity.getSorYear());

			bean.setEnabled(entity.getEnabled());
			bean.setCreatedBy(entity.getCreatedBy());
			bean.setCreatedDate(entity.getCreatedDate());
		}
		return bean;
	}

	private HeadBean convertHeadEntityToBean(Head entity) {

		HeadBean bean = new HeadBean();

		if (entity != null) {
			bean.setHeadId(entity.getId());
			bean.setHeadNameE(entity.getHeadName());
			bean.setHeadNameH(entity.getHeadName());
			bean.setEnabled(entity.getEnabled());
			bean.setCreatedBy(entity.getCreatedBy());
			bean.setCreatedDate(bean.getCreatedDate());
		}
		return bean;
	}

	@Override
	public List<String> fetchDistinctLegislativeConstituency() {

		try {
			List<String> list = legislativeConsRepository
					.findDistinctLegislativeConstituencyByEnabled(DMSConstants.ENABLED);

			List<String> beanList = new ArrayList<>();
			for (String workStatus : list) {
				beanList.add(workStatus);
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred in fetchLegislativeConstituency Method.", e);
			return null;
		}

	}

	private LCBean convertLCEntityToBean(LegislativeConstituency entity) {
		LCBean bean = new LCBean();

		if (entity != null) {
			bean.setLcId(entity.getId());
			bean.setLcName(entity.getConstituencyName());
			bean.setLcNameH(entity.getConstituencyName());
			// bean.setLcCode(entity.getLcCode());
			bean.setEnabled(entity.getEnabled());
		}
		return bean;
	}

	@Override
	public List<WorkHeadBean> fetchWorkHeadByPriorityType(Integer priorityTypeId) {

		try {

			List<WorkHead> list = new ArrayList<WorkHead>();

			list = workHeadRepository.findByEnabledAndPriorityType(DMSConstants.ENABLED, priorityTypeId);

			List<WorkHeadBean> beanList = new ArrayList<>();
			for (WorkHead entity : list) {
				beanList.add(convertWorkHeadEntityToBean(entity));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred in fetchWorkHeadByPriorityType Method.", e);
			return null;
		}

	}

	@Override
	public List<WorkStatusBean> fetchWorkStatusByFlag(Long flag) {

		try {

			List<WorkStatus> list = new ArrayList<>();

			list = workStatusRepository.findByEnabledAndFlag(DMSConstants.ENABLED, flag);

			List<WorkStatusBean> beanList = new ArrayList<>();
			for (WorkStatus entity : list) {
				beanList.add(convertWorkStatusEntityToBean(entity));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred in fetchWorkHeadByPriorityType Method.", e);
			return null;
		}

	}

	@Override
	public List<WorkSubStatusBean> fetchWorkSubStatusByWorkStatus(Long workSubTypeId, Integer workStatusId) {
		try {

			List<WorkSubStatus> list = new ArrayList<>();

			list = workSubStatusRepository.findByEnabledAndWorkSubTypeIdAndWorkStatusId((short) 1, workSubTypeId,
					workStatusId);

			List<WorkSubStatusBean> beanList = new ArrayList<>();
			for (WorkSubStatus entity : list) {
				beanList.add(convertWorkSubStatusEntityToBean(entity));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred in fetchWorkHeadByPriorityType Method.", e);
			return null;
		}

	}

	@Override
	public PhysicalPercentageBean fetchPercByWorkSubStatus(Long workSubStatusId) {

		try {

			PhysicalPercentageBean physicalPercentageBean = new PhysicalPercentageBean();

			WorkSubStatus workSubStatus = workSubStatusRepository.findById(workSubStatusId).orElse(null);
			PhysicalPercentageNew physicalPercentageNew = physicalPercentageNewRepository
					.findByWorkSubStatus(workSubStatus);
			physicalPercentageBean = convertPhysicalPercentNewEntityToBean(physicalPercentageNew);

			return physicalPercentageBean;
		} catch (Exception e) {
			logger.error("An exception occurred in fetchWorkHeadByPriorityType Method.", e);
			return null;
		}

	}

	private PhysicalPercentageBean convertPhysicalPercentNewEntityToBean(PhysicalPercentageNew entity) {

		PhysicalPercentageBean bean = new PhysicalPercentageBean();

		if (entity != null) {
			bean.setId(entity.getId());
			bean.setPercentage(entity.getPercentage());

		}
		return bean;
	}

	@Override
	public PhysicalPercentageUpgradationBean fetchPercByWorkSubStatusUpgrad(Long workSubStatusId) {

		try {

			PhysicalPercentageUpgradationBean phyPercentageUpgradationBean = new PhysicalPercentageUpgradationBean();

			WorkSubStatus workSubStatus = workSubStatusRepository.findById(workSubStatusId).orElse(null);
			PhysicalPercentageUpgradation physicalPercentageUpgradation = physicalPercentageUpgradationRepository
					.findByWorkSubStatus(workSubStatus);
			phyPercentageUpgradationBean = convertPhysicalPercentUpgradEntityToBean(physicalPercentageUpgradation);

			return phyPercentageUpgradationBean;
		} catch (Exception e) {
			logger.error("An exception occurred in fetchWorkHeadByPriorityType Method.", e);
			return null;
		}

	}

	private PhysicalPercentageUpgradationBean convertPhysicalPercentUpgradEntityToBean(
			PhysicalPercentageUpgradation entity) {

		PhysicalPercentageUpgradationBean bean = new PhysicalPercentageUpgradationBean();

		if (entity != null) {
			bean.setId(entity.getId());
			bean.setPercentage(entity.getPercentage());

		}
		return bean;
	}

	private WorkHeadBean convertWorkHeadEntityToBean(WorkHead entity) {

		WorkHeadBean bean = new WorkHeadBean();

		if (entity != null) {
			bean.setWorkHeadId(entity.getId());
			bean.setWorkHeadName(entity.getHeadName());
			bean.setWorkHeadNameH(entity.getHeadName());
			bean.setPriorityType(entity.getPriorityType());
			// bean.setLcCode(entity.getLcCode());
			bean.setEnabled(entity.getEnabled());
			bean.setCreatedBy(entity.getCreatedBy());
			bean.setCreatedDate(entity.getCreatedDate());
		}
		return bean;
	}

	@Override
	public List<BlockBean> fetchBlocksByDistrict(String districtCode) {
		try {
			District district = districtRepository.findByDistrictCode(districtCode);
			List<Block> list = blockRepository.findByDistrictAndEnabledOrderByBlockName(district, (short) 1);
			List<BlockBean> beanList = new ArrayList<>();

			for (Block block : list) {
				beanList.add(convertBlockEntityToBean(block));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred in fetchBlocksByDistrict Method", e);
			return null;
		}
	}

	@Override
	public List<BlockBean> fetchBlocksByDistrictName(String districtName) {
		try {

			District district = districtRepository.findByDistrictNameAndEnabled(districtName, DMSConstants.ENABLED);

			List<Block> list = blockRepository.findByDistrictAndEnabledOrderByBlockName(
					districtRepository.findByDistrictNameAndEnabled(districtName, (short) 1), (short) 1);

			List<BlockBean> beanList = new ArrayList<>();

			for (Block block : list) {
				beanList.add(convertBlockEntityToBean(block));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred in fetchBlocksByDistrict Method", e);
			return null;
		}
	}

	@Override
	public List<WorkCategoryBean> fetchCategoryByWorkType(Long workTypeId) {
		try {
			List<WorkCategoryTypeMapping> workcategoryTypeMappingList = workCategoryTypeMappingRepository
					.findByWorkTypeAndEnabled(new WorkType(workTypeId), (short) 1);
			List<Long> CategoryTypeIds = null;
			if (workcategoryTypeMappingList != null && !workcategoryTypeMappingList.isEmpty()) {
				CategoryTypeIds = new ArrayList<Long>();
				for (WorkCategoryTypeMapping d : workcategoryTypeMappingList) {
					if (d.getWorkType().getWorkTypeId() != null) {
						CategoryTypeIds.add(d.getWorkCategory().getWorkCategoryId());
					}
				}
			}
			List<WorkCategoryBean> beanList = new LinkedList<>();

			if (null != CategoryTypeIds) {

				List<WorkCategory> list = workCategoryRepository.findByWorkCategoryIdIn(CategoryTypeIds);
				if (null != list && !list.isEmpty()) {
					for (WorkCategory workCategory : list) {
						beanList.add(convertworkCategoryentityToBean(workCategory));
					}
				}
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	@Override
	public List<WorkProgressImageListBean> fetchWorkProgressImageList() {
		WorkProgressImageListBean workProgressImageListBean = new WorkProgressImageListBean();
		List<WorkProgressImageListBean> workProgressImageListBeans = new ArrayList<WorkProgressImageListBean>();
		/*
		 * otherDocListBean.setNoOfDocs(Long.valueOf("0"));
		 * otherDocListBean.setRemarks("");
		 */
		workProgressImageListBeans.add(workProgressImageListBean);
		return workProgressImageListBeans;
	}

	private WorkCategoryBean convertworkCategoryentityToBean(WorkCategory workCategory) {

		WorkCategoryBean bean = new WorkCategoryBean();

		if (workCategory != null) {
			bean.setWorkCategoryId(workCategory.getWorkCategoryId());
			bean.setWorkCategoryNameE(workCategory.getWorkCategoryNameE());
		}
		return bean;
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

		}
		return bean;

	}

	@Override
	public List<FinancialYearBean> fetchFinancialYear() {

		try {
			List<FinancialYear> list = financialYearRepository.findByEnabledOrderByIdDesc(DMSConstants.ENABLED);

			List<FinancialYearBean> beanList = new ArrayList<>();
			for (FinancialYear financialYear : list) {
				beanList.add(convertFinancialYearEntityToBean(financialYear));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}

	}

	@Override
	public List<SorYearBean> fetchSorYear() {

		try {
			List<SorYear> list = sorYearRepository.findByEnabledOrderBySorYearDesc(DMSConstants.ENABLED);

			List<SorYearBean> beanList = new ArrayList<>();
			for (SorYear sorYear : list) {
				beanList.add(convertSorYearEntityToBean(sorYear));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}

	}

	@Override
	public List<YearStatusBean> fetchYear() {

		try {
			List<YearStatus> list = yearStatusRepository.findByEnabledOrderByYearIdAsc(DMSConstants.ENABLED);

			List<YearStatusBean> beanList = new ArrayList<>();
			for (YearStatus yearStatus : list) {
				beanList.add(convertYearEntityToBean(yearStatus));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}

	}

	private FinancialYearBean convertFinancialYearEntityToBean(FinancialYear entity) {

		FinancialYearBean bean = new FinancialYearBean();

		if (entity != null) {
			bean.setFinancialYearId(entity.getId());
			bean.setFinancialYearName(entity.getFinancialYear());
			bean.setEnabled(entity.getEnabled());
		}
		return bean;
	}

	private SorYearBean convertSorYearEntityToBean(SorYear entity) {

		SorYearBean bean = new SorYearBean();

		if (entity != null) {
			bean.setSorYearId(entity.getId());
			bean.setSorYearName(entity.getSorYear());
			bean.setEnabled(entity.getEnabled());
		}
		return bean;
	}

	private YearStatusBean convertYearEntityToBean(YearStatus entity) {

		YearStatusBean bean = new YearStatusBean();

		if (entity != null) {
			bean.setYearId(entity.getYearId());
			bean.setYear(entity.getYear());
			bean.setEnabled(entity.getEnabled());
			bean.setStatus(entity.getStatus());
		}
		return bean;
	}

	@Override
	public List<String> fetchDistinctWorkStatus() {
		try {
			List<String> list = workStatusRepository.findDistinctWorkStatusByEnabled(DMSConstants.ENABLED);

			List<String> beanList = new ArrayList<>();
			for (String workStatus : list) {
				beanList.add(workStatus);
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	@Override
	public List<WorkStatusBean> fetchWorkStatus() {
		try {
			List<WorkStatus> list = workStatusRepository.findByEnabled(DMSConstants.ENABLED);

			List<WorkStatusBean> beanList = new ArrayList<>();
			for (WorkStatus workStatus : list) {
				beanList.add(convertWorkStatusEntityToBean(workStatus));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	@Override
	public List<WorkStatusBean> fetchWorkStatusByWorkType(Long workTypeId) {
		try {
			List<WorkStatus> list = workStatusRepository.findByWorkTypeAndEnabled(
					workTypeRepository.findById(workTypeId).orElse(null), DMSConstants.ENABLED);

			List<WorkStatusBean> beanList = new ArrayList<>();
			for (WorkStatus workStatus : list) {
				beanList.add(convertWorkStatusEntityToBean(workStatus));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	private WorkStatusBean convertWorkStatusEntityToBean(WorkStatus entity) {

		WorkStatusBean bean = new WorkStatusBean();
		if (entity != null) {
			bean.setWorkStatusId(entity.getId());
			bean.setWorkStatusNameE(entity.getWorkStatusNameE());
			bean.setWorkStatusNameH(entity.getWorkStatusNameH());
			bean.setEnabled(entity.getEnabled());
			bean.setFlag(entity.getFlag());
			bean.setColor(entity.getColor());
		}
		return bean;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM','ROLE_DEPARTMENT') and @workAuthorization.canEditWork(#p0)")
	synchronized public ResponseObject addWork(WorkBean bean) throws Exception {
		String ni = "";
		ResponseObject responseObject = null;
		User user = DMSUtil.getUserDetail();

		Users userEntity = userRepository.findByUsernameAndStatus(user.getUsername(), DMSConstants.STATUS_ACTIVE);

		Collection<GrantedAuthority> role = user.getAuthorities();
		String r = role.toString();
		try {
			if (bean != null) {
				Work entity = null;
				if (null != bean.getId()) {
					entity = workRepository.findByIdForFinancialUpdate(bean.getId())
							.orElseThrow(() -> new FinancialValidationException(
									"Work not found for ID " + bean.getId() + "."));

				} else {
					entity = new Work();
					// entity.setWorkStatus("Work Created");
					entity.setWorkStatus(1L);
				}
				financialValidationService.validateWorkFinancials(
						bean, bean.getId() == null ? null : entity);
				responseObject = new ResponseObject();

				District district = districtRepository.findByDistrictNameAndEnabled(bean.getDistrictName(),
						DMSConstants.ENABLED);
				if (bean.getSchemeId() != null) {
					Schemes scheme = schemeRepository.findById(bean.getSchemeId()).orElse(null);
				}

				if (bean.getTsDocumentUpload() != null) {
					DocumentUpload documentUpload = DMSUtil.uploadTsWorkDocument(
							documentRootPath + workTechSanctionDocumentPath, "blank", bean.getTsDocumentUpload(), null,
							"blank");

					documentRepository.save(documentUpload);
					ni += "called ts  -";

					entity.setDocumentUploadTechnical(documentUpload);

				}

				if (bean.getAsDocumentUpload() != null) {
					DocumentUpload documentUpload2 = DMSUtil.uploadAsWorkDocument(
							documentRootPath + workASSanctionDocumentPath, "blank", bean.getAsDocumentUpload(), null,
							"blank");

					documentRepository.save(documentUpload2);
					ni += "-called as  -";
					entity.setDocumentUploadAdministration(documentUpload2);
				}

				String workNo = null;

				if (entity != null) {
					convertWorkBeanToEntity(entity, bean);

				}
				if (DMSUtil.getUserDetail() != null && r.contains("ROLE_DEPARTMENT")) {

					Division division = divisionRepository.findById(userRepository
							.findByUsername(DMSUtil.getUserDetail().getUsername()).getDivision().getDivisionId())
							.orElse(null);
					// entity.setDivisionName(division.getDivisionName());
					if (bean.getDivisionId() != null) {
						entity.setDivisionId(bean.getDivisionId());
					}
					if (division.getDivisionId() != null) {
						entity.setDivisionCode(division.getDivisionId());
						entity.setDivisionId(division.getDivisionId());
					}
				}
				if (bean.getDmRemakrs() != null && entity != null) {
					entity.setDmRemakrs(bean.getDmRemakrs());
					entity.setDmApproveRejctDate(new Date());
				}
				if (bean.getDmStatus() != null && entity != null) {
					entity.setDmStatus(bean.getDmStatus());

				}

				Work work = workRepository.save(entity);
				ni += "-called work  -";
				if (work != null) {
					// updateWorkCount();
					if (bean.getFinancialYear() != null) {
						FinancialYear financialYear = financialYearRepository.findById(bean.getFinancialYear())
								.orElse(null);
						work.setWorkNo(DMSConstants.DHS + "_" + financialYear.getFinancialYear() + "_" + work.getId());
					}
					responseObject.setId(work.getId());
					responseObject.setNumber(work.getWorkNo());

					// ? Save Financial Head Rows (INSERT HERE)
//				    if (bean.getFinancialHeads() != null) {
//
//				        for (FinancialAgencyBean fhBean : bean.getFinancialHeads()) {
//
//				            WorkFinancialAgency wf = new WorkFinancialAgency();
//				            wf.setWorkId(work.getId());
//				            wf.setFinancialHeadId(fhBean.getFinancialHeadId());
//				            wf.setCost(fhBean.getCost());
//				            wf.setTotalCost(bean.getTotalCost());
//				            financialAgencyRepository.save(wf);
//				        }
//				    }

					if (bean.getFinancialHeads() != null) {

						for (FinancialAgencyBean fhBean : bean.getFinancialHeads()) {

							WorkFinancialAgency wf = null;

							// ?? If ID exists ? fetch and update
							Long financialAgencyRowId = fhBean.getFinancialAgencyId() != null
									? fhBean.getFinancialAgencyId()
									: fhBean.getId();
							if (financialAgencyRowId != null) {
								wf = financialAgencyRepository.findById(financialAgencyRowId).orElse(null);
								if (wf == null || !Objects.equals(wf.getWorkId(), work.getId())) {
									throw new FinancialValidationException(
											"Financial head row does not belong to the supplied work.");
								}
							}

							// ?? If no existing row ? create new
							if (wf == null) {
								wf = new WorkFinancialAgency();
								wf.setWorkId(work.getId()); // always required for new rows
								wf.setFinancialHeadId(fhBean.getFinancialHeadId());
								wf.setCost(fhBean.getCost());
								wf.setTotalCost(fhBean.getTotalCost()); // if bean me ho
								wf.setExpenditure(0.0);
							}

							// ?? COMMON: UPDATE/INSERT ALL FIELDS
							wf.setFinancialHeadId(fhBean.getFinancialHeadId());
							wf.setCost(fhBean.getCost());
							wf.setTotalCost(fhBean.getTotalCost()); // if bean me ho
							financialAgencyRepository.save(wf);
						}
					}
					financialValidationService.validatePersistedFinancialHeadTotal(work.getId());

				}

			}
			return responseObject;
		} catch (FinancialValidationException e) {
			throw e;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			throw new Exception(DMSConstants.ERROR_SAVING_DATA);
		}
	}

	/*
	 * @Override
	 * 
	 * @Transactional(rollbackFor = Exception.class) // @Transactional synchronized
	 * public ResponseObject addTSASWorkData(TSASWorkBean tsasWorkbean) throws
	 * Exception {
	 * 
	 * ResponseObject responseObject = null;
	 * 
	 * System.out.println("TSASDEtails...." + tsasWorkbean.getWorkId());
	 * 
	 * Work works = workRepository.findById(tsasWorkbean.getWorkId()).orElse(null);
	 * 
	 * try { if (tsasWorkbean != null) { TSASWork entity = null;
	 * 
	 * if (null != tsasWorkbean.getWorkId()) { // entity =
	 * tsasWorkRepository.findByWorkId(tsasWorkbean.getWorkId().longValue());
	 * 
	 * 
	 * entity = new TSASWork();
	 * entity.setWork(workRepository.findById(tsasWorkbean.getWorkId()).orElse(null)
	 * ); entity.setAsAmt(works.getAsAmt()); entity.setAsDate(works.getAsDate());
	 * entity.setAsNo(works.getAsNo()); entity.setAsRemarks(works.getAsRemarks());
	 * entity.setTsAmt(works.getTsAmt()); entity.setTsDate(works.getTsDate());
	 * entity.setTsNo(works.getTsNo()); entity.setTsRemarks(works.getTsRemarks());
	 * entity.setTsAsSataus(DMSConstants.STATUS_ACTIVE); entity.setWorkStatus(2L);
	 * entity = tsasWorkRepository.save(entity);
	 * 
	 * 
	 * else { entity = new TSASWork(); entity.setWorkStatus(2L);
	 * entity.setTsAsSataus(DMSConstants.STATUS_ACTIVE); }
	 * 
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 * else { entity = new TSASWork(); entity.setWorkStatus(2L);
	 * entity.setTsAsSataus(DMSConstants.STATUS_ACTIVE); }
	 * 
	 * 
	 * responseObject = new ResponseObject();
	 * System.err.println("tsasWorkbean.getWorkId()======== " +
	 * tsasWorkbean.getWorkId()); Work work =
	 * workRepository.findById(tsasWorkbean.getWorkId()).orElse(null); String workNo
	 * = null;
	 * 
	 * 
	 * if (!StringUtils.isEmpty(tsasWorkbean.getStatus())) {
	 * entity.setStatus(tsasWorkbean.getStatus()); } else {
	 * entity.setStatus(DMSConstants.STATUS_ACTIVE); }
	 * 
	 * 
	 * 
	 * if (tsasWorkbean.getTsDocumentUpload() != null) { DocumentUpload
	 * documentUpload = DMSUtil.uploadTsWorkDocument( documentRootPath +
	 * workTechSanctionDocumentPath, "blank", tsasWorkbean.getTsDocumentUpload(),
	 * null, "blank");
	 * 
	 * documentRepository.save(documentUpload);
	 * entity.setDocumentUploadTechnical(documentUpload);
	 * 
	 * }
	 * 
	 * if (tsasWorkbean.getAsDocumentUpload() != null) { DocumentUpload
	 * documentUpload2 = DMSUtil.uploadAsWorkDocument( documentRootPath +
	 * workASSanctionDocumentPath, "blank", tsasWorkbean.getAsDocumentUpload(),
	 * null, "blank");
	 * 
	 * documentRepository.save(documentUpload2);
	 * entity.setDocumentUploadAdministration(documentUpload2); }
	 * 
	 * 
	 * //convertTSASWorkBeanToEntity(entity, tsasWorkbean);
	 * 
	 * 
	 * 
	 * if (entity != null) { // updateWorkCount(); //
	 * work.setWorkNo(DMSConstants.DHS + work.getId()); if (entity.getWorkStatus()
	 * == 2) { // work.setWorkStatus("AA Issued"); work.setWorkStatus(2L); }
	 * 
	 * responseObject.setId(work.getId()); //
	 * responseObject.setNumber(tsasWork.getWorkId());
	 * 
	 * }
	 * 
	 * } return responseObject; } catch (Exception e) {
	 * logger.error("An exception occurred.", e); throw new
	 * Exception(DMSConstants.ERROR_SAVING_DATA); }
	 * 
	 * }
	 */

	@Override
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM','ROLE_DEPARTMENT') and @workAuthorization.canAccessWork(#p0.workId)")
	synchronized public ResponseObject addTSASWorkData(TSASWorkBean tsasWorkbean) throws Exception {

		ResponseObject responseObject = new ResponseObject();

		try {
			if (tsasWorkbean == null || tsasWorkbean.getWorkId() == null) {
				throw new IllegalArgumentException("Invalid input: WorkId is required.");
			}

			Long workId = tsasWorkbean.getWorkId();
			logger.info("Processing TSASDetails for Work ID: " + workId);

			// Fetch the related Work entity once
			Work work = workRepository.findById(workId).orElse(null);
			if (work == null) {
				throw new Exception("Work not found for WorkId: " + workId);
			}
			financialValidationService.validateTsDoesNotExceedAs(work.getTsAmt(), work.getAsAmt());

			// Check if TSASWork already exists for the given workId
			TSASWork entity = tsasWorkRepository.findByWorkId(workId);
			if (entity == null) {
				// Create new TSASWork
				entity = new TSASWork();
				entity.setWork(work);
				entity.setTsAsSataus(DMSConstants.STATUS_ACTIVE);
				entity.setWorkStatus(2L); // Default status for a new record
			}

			// Update TSASWork details
			entity.setAsAmt(work.getAsAmt());
			entity.setAsDate(work.getAsDate());
			entity.setAsNo(work.getAsNo());
			entity.setAsRemarks(work.getAsRemarks());
			entity.setTsAmt(work.getTsAmt());
			entity.setTsDate(work.getTsDate());
			entity.setTsNo(work.getTsNo());
			entity.setTsRemarks(work.getTsRemarks());

			// Save the entity
			entity = tsasWorkRepository.save(entity);

			// Update Work status if required
			if (entity.getWorkStatus() == 2L) {
				work.setWorkStatus(2L);
			}

			// Prepare the response
			responseObject.setId(work.getId());
			logger.info("TSASWork updated successfully for Work ID: " + workId);

			return responseObject;

		} catch (FinancialValidationException e) {
			throw e;
		} catch (IllegalArgumentException e) {
			logger.error("Invalid input provided.", e);
			throw e;
		} catch (Exception e) {
			logger.error("An exception occurred while saving TSASWork data.", e);
			throw new Exception(DMSConstants.ERROR_SAVING_DATA, e);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM','ROLE_DEPARTMENT') and @workAuthorization.canAccessWork(#p0.workId)")
	// @Transactional
	synchronized public ResponseObject addTSReviseWorkData(TSASReviseWorkBean tsasReviseWorkBean) throws Exception {

		ResponseObject responseObject = null;

		try {
			if (tsasReviseWorkBean != null) {
				financialValidationService.validateRevisedTsAs(
						tsasReviseWorkBean.getWorkId(), tsasReviseWorkBean.getRvAmt(), "TS");
				TSASReviseWork entity = null;
				entity = new TSASReviseWork();

				responseObject = new ResponseObject();
				Work work = workRepository.findById(tsasReviseWorkBean.getWorkId()).orElse(null);
				String workNo = null;

				if (!StringUtils.isEmpty(tsasReviseWorkBean.getStatus())) {
					entity.setStatus(tsasReviseWorkBean.getStatus());
				} else {
					entity.setStatus(DMSConstants.STATUS_ACTIVE);
				}

				if (tsasReviseWorkBean.getRvDocumentUpload() != null) {
					DocumentUpload documentUpload = DMSUtil.uploadTsRevisedWorkDocument(
							documentRootPath + workRevisedTechSanctionDocumentPath, "blank",
							tsasReviseWorkBean.getRvDocumentUpload(), null, "blank");

					documentRepository.save(documentUpload);
					entity.setDocumentUploadRevised(documentUpload);

				}

				convertTSRevisedWorkBeanToEntity(entity, tsasReviseWorkBean, "TS");

				entity = tsasReviseWorkRepository.save(entity);

				if (entity != null) {

					responseObject.setId(entity.getWork().getId());

				}

			}
			return responseObject;
		} catch (FinancialValidationException e) {
			throw e;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			throw new Exception(DMSConstants.ERROR_SAVING_DATA);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM','ROLE_DEPARTMENT') and @workAuthorization.canAccessWork(#p0.workId)")
	// @Transactional
	synchronized public ResponseObject addASReviseWorkData(TSASReviseWorkBean tsasReviseWorkBean) throws Exception {

		ResponseObject responseObject = null;

		try {
			if (tsasReviseWorkBean != null) {
				financialValidationService.validateRevisedTsAs(
						tsasReviseWorkBean.getWorkId(), tsasReviseWorkBean.getRvAmt(), "AS");
				TSASReviseWork entity = null;
				entity = new TSASReviseWork();

				responseObject = new ResponseObject();
				Work work = workRepository.findById(tsasReviseWorkBean.getWorkId()).orElse(null);
				String workNo = null;

				if (!StringUtils.isEmpty(tsasReviseWorkBean.getStatus())) {
					entity.setStatus(tsasReviseWorkBean.getStatus());
				} else {
					entity.setStatus(DMSConstants.STATUS_ACTIVE);
				}

				if (tsasReviseWorkBean.getRvDocumentUpload() != null) {

					DocumentUpload documentUpload = DMSUtil.uploadAsRevisedWorkDocument(
							documentRootPath + workASRevisedSanctionDocumentPath, "blank",
							tsasReviseWorkBean.getRvDocumentUpload(), null, "blank");

					documentRepository.save(documentUpload);
					entity.setDocumentUploadRevised(documentUpload);

				}

				convertTSRevisedWorkBeanToEntity(entity, tsasReviseWorkBean, "AS");

				entity = tsasReviseWorkRepository.save(entity);

				if (entity != null) {

					responseObject.setId(entity.getWork().getId());

				}

			}
			return responseObject;
		} catch (FinancialValidationException e) {
			throw e;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			throw new Exception(DMSConstants.ERROR_SAVING_DATA);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	// @Transactional
	synchronized public ResponseObject addWorkProgress(WorkProgressBean workProgressBean) throws Exception {

		ResponseObject responseObject = null;

		try {
			if (workProgressBean != null) {
				WorkProgress entity = null;

				if (null != workProgressBean.getWorkId()) {
					entity = workProgressRepository.findByWorkId(workProgressBean.getWorkId());
					if (entity != null) {

					} else {
						entity = new WorkProgress();
					}

				} else {
					entity = new WorkProgress();
				}

				responseObject = new ResponseObject();
				Work work = workRepository.findById(workProgressBean.getWorkId()).orElse(null);
				TSASWork tsasWork = tsasWorkRepository.findByWorkId(workProgressBean.getWorkId());
				WorkTender tender = workTenderRepository.findByWorkId(workProgressBean.getWorkId());

				String workNo = null;

				if (!StringUtils.isEmpty(workProgressBean.getStatus())) {
					entity.setStatus(workProgressBean.getStatus());
				} else {
					entity.setStatus(DMSConstants.STATUS_ACTIVE);
				}

				/*
				 * if (workProgressBean.getProgressDocumentUpload() != null) { DocumentUpload
				 * documentUpload = DMSUtil.uploadWorkProgressDocument( documentRootPath +
				 * workWorkProgressDocumentPath, "blank",
				 * workProgressBean.getProgressDocumentUpload(), null, "blank");
				 * 
				 * documentRepository.save(documentUpload);
				 * entity.setProgressDocumentUpload(documentUpload);
				 * 
				 * }
				 */

				convertWorkProgressBeanToEntity(entity, workProgressBean);

				entity = workProgressRepository.save(entity);

				if (entity != null) {
					WorkStatus workStatus = workStatusRepository.findById(workProgressBean.getWorkStatusId())
							.orElse(null);
					// entity.setWorkStatus(workStatus.getWorkStatusNameE());
					entity.setWorkStatusId(workStatus.getId());
					// work.setWorkStatus(workStatus.getWorkStatusNameE());
					work.setWorkStatus(workProgressBean.getWorkStatusId());
					// tsasWork.setWorkStatus(workStatus.getWorkStatusNameE());
					tsasWork.setWorkStatus(workStatus.getId());
					// tender.setWorkStatus(workStatus.getWorkStatusNameE());
					tender.setWorkStatus(workStatus.getId());
					if (workProgressBean.getWorkStatusId() == 12) {
						CC cc = ccRepository.findByWorkId(workProgressBean.getWorkId());
						cc.setWorkStatusId(workStatus.getId());
						// cc.setWorkStatus(workStatus.getWorkStatusNameE());
					}
					if (workProgressBean.getWorkStatusId() == 11) {
						saveCompletedWorkProgressListRow(workProgressBean, workStatus);
					}
					responseObject.setId(entity.getWork().getId());

				}

			}
			return responseObject;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			throw new Exception(DMSConstants.ERROR_SAVING_DATA);
		}
	}

	private void saveCompletedWorkProgressListRow(WorkProgressBean workProgressBean, WorkStatus workStatus) {
		DocumentUploadWorkProgress completedRow = new DocumentUploadWorkProgress();
		completedRow.setWorkId(workProgressBean.getWorkId());
		completedRow.setWorkStatusId(workStatus.getId());
		completedRow.setWorkStatusNameE(workStatus.getWorkStatusNameE());
		completedRow.setRemarks(workProgressBean.getRemarks());
		completedRow.setPerc(workProgressBean.getPerc() != null ? workProgressBean.getPerc() : 100);
		completedRow.setEnabled((short) 1);
		completedRow.setCreatedDate(new Date());
		documentUploadWorkProgressRepository.save(completedRow);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	// @Transactional
	synchronized public ResponseObject addWorkProSubStatusUploading(
			DocumentUploadWorkProgressBean uplDocumentUploadWorkProgressBean) throws Exception {

		ResponseObject responseObject = null;

		try {
			if (uplDocumentUploadWorkProgressBean != null) {
				DocumentUploadWorkProgress entity = null;
				entity = new DocumentUploadWorkProgress();

				responseObject = new ResponseObject();
				Work work = workRepository.findById(uplDocumentUploadWorkProgressBean.getWorkId()).orElse(null);
				String workNo = null;
				DocumentUploadWorkProgress documentUpload = null;
				if (uplDocumentUploadWorkProgressBean.getFile() != null) {
					logger.info("WorkStatusnkdncknd.." + uplDocumentUploadWorkProgressBean.getWorkSubStatusId());
					documentUpload = DMSUtil.uploadWorkProgressDocument(documentRootPath + workWorkProgressDocumentPath,
							uplDocumentUploadWorkProgressBean.getWorkId(), uplDocumentUploadWorkProgressBean.getFile(),
							null, "blank", uplDocumentUploadWorkProgressBean.getWorkSubStatusId(),
							uplDocumentUploadWorkProgressBean.getWorkStatusId());
					WorkStatus workStatusId = workStatusRepository
							.findById(uplDocumentUploadWorkProgressBean.getWorkStatusId()).orElse(null);
					documentUpload.setWorkStatusId(workStatusId.getId());
					documentUpload.setWorkStatusNameE(workStatusId.getWorkStatusNameE());
					documentUpload.setPerc(uplDocumentUploadWorkProgressBean.getPerc());
					documentUploadWorkProgressRepository.save(documentUpload);
				} else if (uplDocumentUploadWorkProgressBean.getFile() == null
						&& uplDocumentUploadWorkProgressBean.getWorkStatusId() == 10) {
					documentUpload = new DocumentUploadWorkProgress();
					WorkStatus workStatusId = workStatusRepository
							.findById(uplDocumentUploadWorkProgressBean.getWorkStatusId()).orElse(null);
					documentUpload.setWorkStatusId(workStatusId.getId());
					documentUpload.setWorkStatusNameE(workStatusId.getWorkStatusNameE());
					documentUpload.setWorkSubStatusId(uplDocumentUploadWorkProgressBean.getWorkSubStatusId());
					documentUpload.setRemarks(uplDocumentUploadWorkProgressBean.getRemarks());
					documentUpload.setPerc(uplDocumentUploadWorkProgressBean.getPerc());
					documentUpload.setEnabled((short) 1);
					documentUpload.setCreatedDate(new Date());
					documentUpload.setWorkId(uplDocumentUploadWorkProgressBean.getWorkId());
					documentUploadWorkProgressRepository.save(documentUpload);

					/*
					 * WorkSubStatus workSubStatus= workSubStatusRepository.findByWorkSubStatusId(
					 * uplDocumentUploadWorkProgressBean.getWorkSubStatusId());
					 * entity.setWorkSubStatusId(uplDocumentUploadWorkProgressBean.
					 * getWorkSubStatusId());
					 * entity.setWorkSubStatusNameE(workSubStatus.getWorkSubStatusNameE());
					 */

					responseObject.setId(uplDocumentUploadWorkProgressBean.getWorkId());

				} else if (uplDocumentUploadWorkProgressBean.getWorkStatusId() == 9) {
//					System.out
//							.println("WorkStatusnkdncknd11.." + uplDocumentUploadWorkProgressBean.getWorkSubStatusId());
					DocumentUploadWorkProgress documentUpload1 = new DocumentUploadWorkProgress();

					documentUpload1.setWorkId(uplDocumentUploadWorkProgressBean.getWorkId());
					documentUpload1.setEnabled((short) 1);
					documentUpload1.setCreatedDate(new Date());
					documentUpload1.setWorkSubStatusId(uplDocumentUploadWorkProgressBean.getWorkSubStatusId());
					documentUpload1.setActionTakenDelay(uplDocumentUploadWorkProgressBean.getActionTakenDelay());
					WorkStatus workStatusId = workStatusRepository
							.findById(uplDocumentUploadWorkProgressBean.getWorkStatusId()).orElse(null);
					documentUpload1.setWorkStatusId(workStatusId.getId());
					documentUpload1.setWorkStatusNameE(workStatusId.getWorkStatusNameE());
					documentUpload1.setRemarks(uplDocumentUploadWorkProgressBean.getRemarks());
					documentUpload1
							.setWorkSubDelayReasonId(uplDocumentUploadWorkProgressBean.getWorkSubDelayReasonId());
					documentUploadWorkProgressRepository.save(documentUpload1);
					responseObject.setId(uplDocumentUploadWorkProgressBean.getWorkId());

				}

			}
			return responseObject;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			throw new Exception(DMSConstants.ERROR_SAVING_DATA);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM','ROLE_DEPARTMENT') and @workAuthorization.canAccessWork(#p0.workId)")
	// @Transactional
	synchronized public ResponseObject addWorkProExpensesData(ExpensesDataBean expensesDataBean) throws Exception {

		ResponseObject responseObject = null;

		try {
			if (expensesDataBean != null) {
				ExpensesData entity = null;
				entity = new ExpensesData();

				responseObject = new ResponseObject();
				YearStatus yearStatus = yearStatusRepository.findByYear(expensesDataBean.getYear());

				Long status = 1L;
				if (yearStatus != null) {
					status = yearStatus.getStatus();
				} else {
					logger.warn("YearStatus not found for year {}, using default status for workId {}",
							expensesDataBean.getYear(), expensesDataBean.getWorkId());
				}

				if (expensesDataBean.getWorkId() != null) {
					BigDecimal validatedCurrentExpense =
							financialValidationService.validateExpenseRequest(expensesDataBean);
					List<Long> statusList = new ArrayList<>();
					statusList.add(status);
					List<ExpensesData> expensesData = expensesDataRepository
							.findByWorkIdAndStatusIn(expensesDataBean.getWorkId(), statusList);

					List<Long> statusList2 = new ArrayList<>();
					statusList2.add((long) 1);
					List<ExpensesData> expensesData2 = expensesDataRepository
							.findByWorkIdAndStatusIn(expensesDataBean.getWorkId(), statusList2);

					BigDecimal expenseCurrentFy = validatedCurrentExpense;
					BigDecimal expenseUptoMarch = expensesDataBean.getExpensessUptoMarch() != null
							? expensesDataBean.getExpensessUptoMarch()
							: BigDecimal.ZERO;

					// Only save if user actually provided a positive value — skip 0/null rows
					if (expenseCurrentFy != null && expenseCurrentFy.compareTo(BigDecimal.ZERO) > 0) {						if (expensesData.size() != 0) {
							entity.setCreatedDate(new Date());
							entity.setWorkId(expensesDataBean.getWorkId());
							entity.setExpensessCurrentFy(expenseCurrentFy);
							BigDecimal expensesfinalMonth = expenseUptoMarch.add(expenseCurrentFy);
							entity.setExpensessUptoMarch(expensesfinalMonth);
							entity.setMonth(expensesDataBean.getMonth());
							entity.setYear(expensesDataBean.getYear());
							entity.setStatus(status);
							logger.info("MonthAmount......" + expensesfinalMonth);
							logger.info("MonthAmount......" + expensesData.size());

							/*
							 * New Code Added here List<ExpensesData>
							 * expensesDataEntity=expensesDataRepository.findByWorkIdAndYearIn(
							 * expensesDataBean.getWorkId() ,expensesDataBean.getYear());
							 * 
							 * if (expensesDataEntity !=null) {
							 * 
							 * }else { BigDecimal
							 * expYearlyAmount=expensesDataRepository.sumExpensesAmount(); //BigDecimal
							 * newExpensesTotal=expYearlyAmount.add(expensesDataBean.getExpensessCurrentFy()
							 * ); entity.setTotalExpensess(expYearlyAmount); }
							 */

						} else {
							entity.setCreatedDate(new Date());
							entity.setWorkId(expensesDataBean.getWorkId());
							entity.setExpensessCurrentFy(expenseCurrentFy);
							BigDecimal expensesfinalMonth = expenseUptoMarch.add(expenseCurrentFy);
							entity.setExpensessUptoMarch(expensesfinalMonth);

							logger.info("MonthAmount......" + expensesDataBean.getTotalExpensess());
							logger.info("MonthAmount......" + expensesDataBean.getExpensessUptoMarch());
							logger.info("MonthAmount......" + expensesDataBean.getExpensessCurrentFy());

							// BigDecimal
							// expYearlyAmount=expensesDataBean.getExpensessUptoMarch().add(expensesDataBean.getTotalExpensess());
							if (expenseUptoMarch.equals(BigDecimal.ZERO)) {
								entity.setTotalExpensess(expensesDataBean.getTotalExpensess());
								// entity.setTotalExpensess(expensesDataBean.getExpensessCurrentFy());
							} else {
								BigDecimal firstTotalGet = BigDecimal.ZERO;
								if (expensesData2.size() != 0) {

									for (int i = 0; i < expensesData2.size(); i++) {
										if (i == 0) {
											firstTotalGet = expensesData2.get(i).getTotalExpensess();
											break;
										}

									}

								}
								if (status == 2) {
									List<Long> workIdList = new ArrayList<>();
									workIdList.add(expensesDataBean.getWorkId());
									BigDecimal expYearlyAmount = expensesDataRepository
											.sumExpensesAmount(workIdList);
									BigDecimal newExpensesTotal = expYearlyAmount.add(expenseCurrentFy);
									BigDecimal finalAmtTotal = newExpensesTotal
											.add(expensesDataBean.getTotalExpensess() != null
													? expensesDataBean.getTotalExpensess()
													: BigDecimal.ZERO);
									entity.setTotalExpensess(finalAmtTotal);
								} else {

									List<Long> workIdList = new ArrayList<>();
									workIdList.add(expensesDataBean.getWorkId());
									BigDecimal expYearlyAmount = expensesDataRepository
											.sumExpensesAmount(workIdList);
									BigDecimal newExpensesTotal = expYearlyAmount.add(expenseCurrentFy);
									if (newExpensesTotal != null) {
										BigDecimal finalAmtTotal = newExpensesTotal.add(firstTotalGet);
										entity.setTotalExpensess(finalAmtTotal);
									}
								}

							}

							entity.setMonth(expensesDataBean.getMonth());
							entity.setYear(expensesDataBean.getYear());
							entity.setStatus(status);
							logger.info("TargetAmount...." + expensesDataBean.getExpensessUptoMarch() + " "
									+ expensesDataBean.getExpensessCurrentFy());
							logger.info("Amount...." + expensesfinalMonth);
							logger.info("TargetAmount...." + expensesDataBean.getExpensessUptoMarch() + " "
									+ expensesDataBean.getTotalExpensess());

						}

					}

					if (entity.getWorkId() != null) {
						financialValidationService.validateCalculatedExpenseFields(
								entity.getWorkId(), entity.getExpensessUptoMarch(), entity.getTotalExpensess());
						expensesDataRepository.save(entity);
						responseObject.setId(expensesDataBean.getWorkId());
						responseObject.setSuccessMessage("Expenses saved successfully!");
					}
				}

			}
			return responseObject;
		} catch (FinancialValidationException e) {
			throw e;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			throw new Exception(DMSConstants.ERROR_SAVING_DATA);
		}
	}

	public static String saveWorkProgressFileFile(String documentsPath, String requestId, MultipartFile mpresFile)
			throws DMSBusinessException {

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
		String strDate = formatter.format(date);

		File serverFile = null;
		String createdFileName = null;

		File dir = new File(documentsPath);

		String fileExtension = "pdf";
		if (null != mpresFile.getOriginalFilename()) {
			String[] fileArr = mpresFile.getOriginalFilename().split("\\.");
			int length = fileArr.length;
			fileExtension = fileArr[length - 1];
		}

		if (!dir.exists()) {
			dir.mkdirs();

			createdFileName = SecureFileUploadPolicy.createDocumentStorageName(mpresFile);
			serverFile = new File(dir.getAbsolutePath() + File.separator + createdFileName);
		} else {

			createdFileName = SecureFileUploadPolicy.createDocumentStorageName(mpresFile);
			serverFile = new File(dir.getAbsolutePath() + File.separator + createdFileName);
		}

		// BufferedOutputStream stream = null;
		try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
			// Write the file contents to the server file
			stream.write(mpresFile.getBytes());
		} catch (IOException e) {
			// Throw a custom exception with the cause for debugging
			throw new DMSBusinessException("System is unable to process.", e);
		}

		// Return the created file name
		return createdFileName;
	}

	// Method to convert Work Progress Bean to Work Progress Entity
	private void convertWorkProgressBeanToEntity(WorkProgress entity, WorkProgressBean bean) {
		logger.info("StipulatedDateCompleted..." + bean.getStipulatedDateCompleted());
		logger.info("LikelyDateCompleted..." + bean.getWorkSubStatusId());
		// System.err.println("bean.getWorkId==========" + bean.getWorkId());
		entity.setWork(workRepository.findById(bean.getWorkId()).orElse(null));
		// entity.setWorkStatusId(bean.getWorkStatusId());
		if (bean.getWorkSubStatusId() != null) {
			entity.setWorkSubStatusId(bean.getWorkSubStatusId());
		}
		if (bean.getWorkSubDelayReasonId() != null) {
			entity.setWorkSubDelayReasonId(bean.getWorkSubDelayReasonId());
		}
		if (bean.getOtherReasonDelay() != null) {
			entity.setOtherReasonDelay(bean.getOtherReasonDelay());
		}
		if (bean.getActionTakenDelay() != null) {
			entity.setActionTakenDelay(bean.getActionTakenDelay());
		}
		if (bean.getStipulatedDateCompleted() != null) {
			entity.setStipulatedDateCompleted(bean.getStipulatedDateCompleted());
		}
		if (bean.getLikelyDateCompleted() != null) {
			entity.setLikelyDateCompleted(bean.getLikelyDateCompleted());
		}
		if (bean.getDateCompletion() != null) {
			entity.setDateCompletion(bean.getDateCompletion());
		}
		if (bean.getDateHandOver() != null) {
			entity.setDateHandOver(bean.getDateHandOver());
		}
		if (bean.getRemarks() != null) {
			entity.setRemarks(bean.getRemarks());
		}
		if (bean.getPerc() != null) {
			entity.setPerc(bean.getPerc());
		}
		// entity.setExpensessUptoMarch(bean.getExpensessUptoMarch());
		if (bean.getExpensessCurrentFy() != null
				&& bean.getExpensessCurrentFy().compareTo(BigDecimal.ZERO) > 0) {
			entity.setExpensessCurrentFy(bean.getExpensessCurrentFy());
			BigDecimal uptoMarch = bean.getExpensessUptoMarch() != null ? bean.getExpensessUptoMarch()
					: BigDecimal.ZERO;
			entity.setExpensessUptoMarch(uptoMarch.add(bean.getExpensessCurrentFy()));
			if (bean.getTotalExpensess() != null) {
				entity.setTotalExpensess(bean.getTotalExpensess());
			} else if (bean.getWorkId() != null) {
				Double agencyTotal = sumFinancialAgencyExpenditureByWorkId(bean.getWorkId());
				if (agencyTotal != null) {
					entity.setTotalExpensess(BigDecimal.valueOf(agencyTotal));
				}
			}
		} else if (bean.getWorkId() != null) {
			Double agencyTotal = sumFinancialAgencyExpenditureByWorkId(bean.getWorkId());
			if (agencyTotal != null && agencyTotal > 0) {
				entity.setTotalExpensess(BigDecimal.valueOf(agencyTotal));
			} else if (bean.getTotalExpensess() != null) {
				entity.setTotalExpensess(bean.getTotalExpensess());
			}
		}

		// entity.setStatus(bean.getStatus());
		entity.setCreatedDate(java.time.LocalDateTime.now());
		entity.setWorkRequestStatusId(bean.getWorkRequestStatusId());
		entity.setOtherReasonDelay(bean.getOtherReasonDelay());

		entity.setMonth(bean.getMonth());
		entity.setFinancialYear(bean.getFinancialYear());
		entity.setMonthlyProgress(bean.getMonthlyProgress());
	}

	// Method to add Tender & Work Agreement details.
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseObject addWorkTenderAgreementDetls(WorkTenderBean workTenderBean) throws Exception {
		ResponseObject response = null;
		try {
			logger.info("Adding Tender and Work Agreement...");

			if (workTenderBean != null) {

				WorkTender workTender = null;

				if (null != workTenderBean.getWorkId()) {
					workTender = workTenderRepository.findByWorkId(workTenderBean.getWorkId());
					if (workTender != null) {

					} else {
						workTender = new WorkTender();
					}
				} else {
					workTender = new WorkTender();
				}

				response = new ResponseObject();
				Work work = workRepository.findById(workTenderBean.getWorkId()).orElse(null);
				TSASWork tsasWork = tsasWorkRepository.findByWorkId(workTenderBean.getWorkId());

				if (!StringUtils.isEmpty(workTenderBean.getStatus())) {
					workTender.setStatus(workTenderBean.getStatus());
				} else {
					workTender.setStatus(DMSConstants.STATUS_ACTIVE);
				}

				// Save Work Order file (pac)
				if (workTenderBean.getPac() != null && !workTenderBean.getPac().isEmpty()) {
					DocumentUpload docWorkOrder = DMSUtil.uploadTenderWorkDocument(
							documentRootPath + workTenderSanctionDocumentPath, "blank", workTenderBean.getPac(), null,
							"Tender");
					documentRepository.save(docWorkOrder);
					workTender.setDocumentUpload(docWorkOrder);
				}

				// Save LOA file (ldtul)
				if (workTenderBean.getLdtul() != null && !workTenderBean.getLdtul().isEmpty()) {
					DocumentUpload docLoi = DMSUtil.uploadTenderWorkDocumentUloi(
							documentRootPath + workTenderSanctionDocumentPath, "blank", workTenderBean.getLdtul(), null,
							"Tender");
					documentRepository.save(docLoi);
					workTender.setDocumentUploadLoi(docLoi);
				}

				// Save Agreement file (uploadAgreementforWork)
				if (workTenderBean.getUploadAgreementforWork() != null
						&& !workTenderBean.getUploadAgreementforWork().isEmpty()) {
					DocumentUpload docAgreement = DMSUtil.uploadTenderWorkDocumentUploadAgreement(
							documentRootPath + workTenderSanctionDocumentPath, "blank",
							workTenderBean.getUploadAgreementforWork(), null, "Tender");
					documentRepository.save(docAgreement);
					workTender.setDocumentUploadUa(docAgreement);
				}

				// Store document uploaded in the DB
				convertWorkTenderBeanToEntity(workTender, workTenderBean);
				workTenderRepository.save(workTender);

				logger.info("Data saved successfully...." + workTenderBean.getWorkStatusId());

				if (workTender != null) {
					int count = 1;

					List<WorkTenderCount> entity = workTenderCountRepository
							.findByWorkIdAndStatusId(workTenderBean.getWorkId(), workTenderBean.getWorkStatusId());
					if (entity.size() >= 1) { // replace 0 to 1
						for (int i = 0; i < entity.size(); i++) {
							count = Integer.parseInt(String.valueOf(entity.get(i).getCount()));
							count++;
						}
					} else {
						count = 1;
					}

					String alphaNumId = generateAlphanumericId();
					WorkTenderCount workTenderCount = new WorkTenderCount();
					workTenderCount.setTenderUniqId(alphaNumId);
					workTenderCount.setWorkId(workTender.getWork().getId());
					workTenderCount.setCount(count);
					WorkStatus workStatus = workStatusRepository.findById(workTenderBean.getWorkStatusId())
							.orElse(null);
					workTenderCount.setStatus(workStatus.getWorkStatusNameE());
					workTenderCount.setStatusId(workStatus.getId());
					workTenderCountRepository.save(workTenderCount);
					// workTender.setWorkStatus(workStatus.getWorkStatusNameE());
					workTender.setWorkStatus(workTenderBean.getWorkStatusId());
					// work.setWorkStatus(workStatus.getWorkStatusNameE());
					work.setWorkStatus(workTenderBean.getWorkStatusId());
					// tsasWork.setWorkStatus(workStatus.getWorkStatusNameE());
					tsasWork.setWorkStatus(workTenderBean.getWorkStatusId());
					response.setId(workTender.getWork().getId());
				}

			}
			return response;
		} catch (Exception ex) {
			logger.error("An exception occurred.", ex);
			throw new Exception(DMSConstants.ERROR_SAVING_DATA);
		}

	}

	public static String generateAlphanumericId() {
		// Generate a random UUID
		UUID uuid = UUID.randomUUID();

		// Convert UUID to a string and remove any hyphens
		String alphanumericId = uuid.toString().replace("-", "");

		// Return the alphanumeric ID
		return alphanumericId;
	}

	// Method to convert WorkTenderBean to Entity
	private void convertWorkTenderBeanToEntity(WorkTender workTender, WorkTenderBean workTenderBean) {

		// workTender.setWorkStatus(workTenderBean.getWorkStatusId());

		workTender.setWorkOrderDate(workTenderBean.getWorkOrderDate());
		workTender.setTenderPercentage(workTenderBean.getTenderPercentage());
		workTender.setContractAmount(workTenderBean.getContractAmount());
		workTender.setPacAmount(workTenderBean.getPacAmount());
		workTender.setContractTenure(workTenderBean.getContractTenure());

		if (workTenderBean.getSecureAmtStatus() != null) {
			workTender.setSecureAmtStatus(workTenderBean.getSecureAmtStatus());
		}

		/*
		 * String formattedDate = ""; Date date;
		 * 
		 * try { String inputDate = bean.getStartDate(); DateFormat inputFormat = new
		 * SimpleDateFormat("dd/MM/yyyy"); DateFormat outputFormat = new
		 * SimpleDateFormat("yyyy-MM-dd"); date = inputFormat.parse(inputDate);
		 * formattedDate = outputFormat.format(date); } catch (java.text.ParseException
		 * e) { // TODO Auto-generated catch block e.printStackTrace();
		 * 
		 * }
		 */

		if (workTenderBean.getStartDate() != null) {
			workTender.setStartDate(workTenderBean.getStartDate());
		}

		if (workTenderBean.getEndDate() != null) {
			workTender.setEndDate(workTenderBean.getEndDate());
		}

		if (workTenderBean.getTenderCalledDate() != null) {
			workTender.setTenderCalledDate(workTenderBean.getTenderCalledDate());
		}

		if (workTenderBean.geteTenderNo() != null) {
			workTender.seteTenderNo(workTenderBean.geteTenderNo());
		}

		if (workTenderBean.getTenderReceivedDate() != null) {
			workTender.setTenderReceivedDate(workTenderBean.getTenderReceivedDate());
		}

		if (workTenderBean.getReTenderDate() != null) {
			workTender.setReTenderDate(workTenderBean.getReTenderDate());
		}

		if (workTenderBean.getLoaIssuedDate() != null) {
			workTender.setLoaIssuedDate(workTenderBean.getLoaIssuedDate());
		}

		if (workTenderBean.getWorkCompletionDate() != null
				&& !workTenderBean.getWorkCompletionDate().trim().isEmpty()) {

			try {
				// Frontend date format
				SimpleDateFormat inputFormat = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss 'GMT'Z (zzzz)",
						Locale.ENGLISH);

				Date inputDate = inputFormat.parse(workTenderBean.getWorkCompletionDate());

				// Required DB format
				SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

				String formattedDate = outputFormat.format(inputDate);

				workTender.setWorkCompletionDate(formattedDate);

			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				logger.error("Cannot parse bean to entity", e);
			}
		}
		workTender.setRemarks(workTenderBean.getRemarks());
		workTender.setWork(workRepository.findById(workTenderBean.getWorkId()).orElse(null));
		workTender.setWorkRequestStatusId(workTenderBean.getWorkRequestStatusId());
		if (workTenderBean.getSorYear() != null) {
			workTender.setSorYear(workTenderBean.getSorYear());
		}
		workTender.setAgreementDate(workTenderBean.getAgreementDate());
		workTender.setAgreementNo(workTenderBean.getAgreementNo());

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	synchronized public ResponseObject addContractorData(ContractorBean contractorBean) throws Exception {

		ResponseObject responseObject = null;
		try {
			if (contractorBean != null) {
				Contractor entity = null;
				WorkTender workTender = null;
				responseObject = new ResponseObject();
				/*
				 * workTender = workTenderRepository.findByWorkId(contractorBean.getWorkId());
				 * if (workTender !=null) { if (workTender.getWorkStatus().equals("WO Issued"))
				 * { if (contractorBean.getWorkId() != null) { entity =
				 * contractorRepository.findByWork_Id(contractorBean.getWorkId()); if (entity !=
				 * null) {
				 * 
				 * } else { entity = new Contractor(); } } else { entity = new Contractor(); }
				 * 
				 * // Work work =
				 * workRepository.findById(contractorBean.getWorkId()).orElse(null);
				 * 
				 * if (!StringUtils.isEmpty(contractorBean.getStatus())) {
				 * entity.setStatus(contractorBean.getStatus()); } else {
				 * entity.setStatus(DMSConstants.STATUS_ACTIVE); }
				 * 
				 * convertContractorBeanToEntity(entity, contractorBean);
				 * 
				 * entity = contractorRepository.save(entity);
				 * 
				 * responseObject.setId(entity.getWorkId());
				 * 
				 * }else { responseObject= new ResponseObject();
				 * responseObject.setErrorMessage("Work Order not Issued."); return
				 * responseObject; } }else { responseObject= new ResponseObject();
				 * responseObject.setErrorMessage("Work Order not Issued."); return
				 * responseObject; }
				 */

				if (contractorBean.getId() != null) {
					entity = contractorRepository.findById(contractorBean.getId()).orElse(null);
				}
				if (entity == null && contractorBean.getWorkId() != null) {
					entity = contractorRepository.findByWork_Id(contractorBean.getWorkId());
				}
				if (entity == null) {
					entity = new Contractor();
					Long maxId = contractorRepository.findMaxId();
					entity.setId((maxId == null ? 0L : maxId) + 1L);
				}

				// Work work = workRepository.findById(contractorBean.getWorkId()).orElse(null);

				if (!StringUtils.isEmpty(contractorBean.getStatus())) {
					entity.setStatus(contractorBean.getStatus());
				} else {
					entity.setStatus(DMSConstants.STATUS_ACTIVE);
				}

				convertContractorBeanToEntity(entity, contractorBean);

				entity = contractorRepository.save(entity);

				responseObject.setId(entity.getWork().getId());

			}
			return responseObject;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			throw new Exception(DMSConstants.ERROR_SAVING_DATA);
		}

	}

	private void convertContractorBeanToEntity(Contractor contractor, ContractorBean contractorBean)
			throws ParseException {

		contractor.setName(contractorBean.getName());
		contractor.setWork(workRepository.findById(contractorBean.getWorkId()).orElse(null));
		contractor.setContactNo(contractorBean.getContactNo());
		contractor.setEmailId(contractorBean.getEmailId());
		contractor.setFirmNameAddress(contractorBean.getFirmNameAddress());
		contractor.setRemarks(contractorBean.getRemarks());
		contractor.setPan(contractorBean.getPan());
		contractor.setGstin(contractorBean.getGstin());
		contractor.setWorkRequestStatusId(contractorBean.getWorkRequestStatusId());

	}

	// Method to get work tender agreement on the basis of work id.
	@Override
	public WorkTenderBean fetchWorkTenderAgreement(Long workId) {
		WorkTenderBean workTenderBean = new WorkTenderBean();
		try {
			logger.info("Fetching Work Tender Agreement..." + workId);
			WorkTender optWorkTender = workTenderRepository.findByWorkId(workId);
			if (optWorkTender != null) {
				workTenderBean = convertWorkTenderEntityToWorkTenderBean(optWorkTender);
			}
			/*
			 * if(optWorkTender.isPresent()) { WorkTender workTender = optWorkTender.get();
			 * workTenderBean = convertWorkTenderEntityToWorkTenderBean(workTender); } else
			 * { workTenderBean.setErrorMessage("No Work ID exists..."); }
			 */
		} catch (Exception ex) {
			logger.error("An exception occurred.", ex);
		}
		return workTenderBean;
	}

	@Override
	public ContractorBean fetchContractorDetails(Long id) {
		try {
			Contractor entity = contractorRepository.findByWork_Id(id);
			ContractorBean bean = convertContractorEntityToBean(entity);

			return bean;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	private ContractorBean convertContractorEntityToBean(Contractor contractor) throws ParseException {

		ContractorBean contractorBean = null;

		if (contractor != null) {

			contractorBean = new ContractorBean();
			contractorBean.setId(contractor.getId());
			contractorBean.setWorkId(contractor.getWork().getId());
			contractorBean.setName(contractor.getName());
			contractorBean.setContactNo(contractor.getContactNo());
			contractorBean.setEmailId(contractor.getEmailId());
			contractorBean.setFirmNameAddress(contractor.getFirmNameAddress());
			contractorBean.setRemarks(contractor.getRemarks());
			contractorBean.setPan(contractor.getPan());
			contractorBean.setGstin(contractor.getGstin());
			Long workId = contractorRepository.countByWorkId(contractor.getWork().getId());
			contractorBean.setWorkIdCount(workId);
		}
		return contractorBean;

	}

	/**
	 * Strips the time portion from a date string.
	 * Handles formats like "DD/MM/YYYY h:mm A", "MM/DD/YYYY h:mm A", returning only "DD/MM/YYYY".
	 */
	private String stripTime(String dateStr) {
		if (dateStr == null || dateStr.trim().isEmpty()) {
			return dateStr;
		}
		// Split on space — date is always the first token
		String[] parts = dateStr.trim().split("\\s+");
		return parts[0];
	}

	// Method to convert WorkTender Entity to WorkTenderBean
	private WorkTenderBean convertWorkTenderEntityToWorkTenderBean(WorkTender entity) {
		WorkTenderBean workTenderBean = new WorkTenderBean();

		DocumentUploadDrawingDetail documentUploadDrawingDetail = documentUploadDrawingDetailRepository
				.findByWorkId(entity.getId());
		if (documentUploadDrawingDetail != null) {
			workTenderBean.setDrawingId(documentUploadDrawingDetail.getDocumentId());
			workTenderBean.setFileStatus("1");
			workTenderBean.setDrawingStatus(documentUploadDrawingDetail.getStatus());
		} else {
			workTenderBean.setFileStatus("0");
		}

		workTenderBean.setWorkId(entity.getWork().getId());
		workTenderBean.setWorkName(entity.getWork().getWorkName());
		workTenderBean.setWorkNo(entity.getWork().getWorkNo());
		workTenderBean.setImplementationAgency(entity.getWork().getImplementationAgency());
		workTenderBean.setFinancialYear(entity.getWork().getFinancialYear());
		workTenderBean.setId(entity.getId());
		// WorkStatus workStatus =
		// workStatusRepository.findByWorkStatusNameE(entity.getWorkStatus());
		WorkStatus workStatus = workStatusRepository.findById(entity.getWorkStatus()).orElse(null);
		List<WorkTenderCount> countList = workTenderCountRepository.findByWorkIdAndStatus(entity.getWork().getId(),
				"Re-Tender");
		int maxCount = Integer.MIN_VALUE;
		int count = 0;
		for (int i = 0; i < countList.size(); i++) {
			if (countList.get(i).getCount() > maxCount) {
				maxCount = countList.get(i).getCount();
			}
			workTenderBean.setCount(maxCount);
		}

		workTenderBean.setWorkStatusId(workStatus.getId());
		workTenderBean.setWorkStatusNameE(workStatus.getWorkStatusNameE());
		workTenderBean.setFlag(workStatus.getFlag());
		workTenderBean.setWorkOrderDate(entity.getWorkOrderDate());
		workTenderBean.setTenderPercentage(entity.getTenderPercentage());
		workTenderBean.setContractAmount(entity.getContractAmount());
		workTenderBean.setContractTenure(entity.getContractTenure());
		workTenderBean.setWorkCompletionDate(entity.getWorkCompletionDate());
		workTenderBean.setRemarks(entity.getRemarks());
		workTenderBean.setStatus(entity.getStatus());
		workTenderBean.setPacAmount(entity.getPacAmount());
		workTenderBean.setSorYear(entity.getSorYear());
		workTenderBean.setSecureAmtStatus(entity.getSecureAmtStatus());
		workTenderBean.setStartDate(entity.getStartDate());
		workTenderBean.setEndDate(entity.getEndDate());
		workTenderBean.seteTenderNo(entity.geteTenderNo());
		workTenderBean.setTenderCalledDate(stripTime(entity.getTenderCalledDate()));
		workTenderBean.setTenderReceivedDate(stripTime(entity.getTenderReceivedDate()));
		workTenderBean.setReTenderDate(stripTime(entity.getReTenderDate()));
		workTenderBean.setLoaIssuedDate(stripTime(entity.getLoaIssuedDate()));

		if (entity.getRateStatus() != null) {
			workTenderBean.setRateStatus(entity.getRateStatus());
			/*
			 * if (entity.getRateStatus().equals("Plus")) {
			 * workTenderBean.setRateStatus("+");
			 * 
			 * } if (entity.getRateStatus().equals("Minus")) {
			 * workTenderBean.setRateStatus("-");
			 * 
			 * } if (entity.getRateStatus().equals("Equal")) {
			 * workTenderBean.setRateStatus("=");
			 * 
			 * }
			 */
		}

		if (entity.getDocumentUpload() != null) {
			workTenderBean.setTenderFileId(entity.getDocumentUpload().getDocumentId());
		}

		if (entity.getDocumentUploadLoi() != null) {
			workTenderBean.setuLoiId(entity.getDocumentUploadLoi().getDocumentId());
		}

		if (entity.getDocumentUploadUa() != null) {
			workTenderBean.setuAId(entity.getDocumentUploadUa().getDocumentId());
		}

		workTenderBean.setWorkRequestStatusId(entity.getWorkRequestStatusId());
		// DocumentUpload
		// documentUpload=documentRepository.findById(entity.getDocumentUpload().orElse(null).getDocumentId());
		if (entity.getAgreementDate() != null) {
			workTenderBean.setAgreementDate(entity.getAgreementDate());
		}
		if (entity.getAgreementNo() != null) {
			workTenderBean.setAgreementNo(entity.getAgreementNo());
		}

		return workTenderBean;
	}

	// Method to get all the work status
	@Override
	public List<WorkStatusBean> getWorkStatus() {
		List<WorkStatusBean> listWorkBean = new ArrayList<WorkStatusBean>();
		try {
			logger.info("Getting Work Status");
			List<WorkStatus> listWorkStatus = workStatusRepository.findByEnabled((short) 1);
			for (WorkStatus status : listWorkStatus) {
				listWorkBean.add(convertWorkStatusEntityToBean(status));
			}
		} catch (Exception ex) {
			logger.error("An exception occurred.", ex);
		}
		return listWorkBean;
	}

	// Method to get all the work sub status
	@Override
	public List<WorkSubStatusBean> getWorkSubStatus() {

		try {
			logger.info("Getting Work Sub Status...");

			List<WorkSubStatus> list = workSubStatusRepository.findByEnabled(DMSConstants.ENABLED);

			List<WorkSubStatusBean> listWorkSubStatusBean = new ArrayList<>();
			for (WorkSubStatus workSubStatus : list) {
				listWorkSubStatusBean.add(convertWorkSubStatusEntityToBean(workSubStatus));
			}
			return listWorkSubStatusBean;
		} catch (Exception ex) {
			logger.error("Error occurred while fetching work sub status.", ex);

			return null;
		}

	}

	// Method to convert WorkSubStatus Entity to WorkSubStatus Bean
	private WorkSubStatusBean convertWorkSubStatusEntityToBean(WorkSubStatus entity) {

		WorkSubStatusBean bean = new WorkSubStatusBean();
		bean.setWorkSubStatusId(entity.getWorkSubStatusId());
		bean.setWorkSubStatusNameE(entity.getWorkSubStatusNameE());
		bean.setWorkSubStatusNameH(bean.getWorkSubStatusNameH());
		bean.setEnabled(entity.getEnabled());
		bean.setWorkStatusId(entity.getWorkStatusId());
		System.err.println("bean.setWorkStatusId" + bean.getWorkStatusId());
		return bean;
	}

	private void updateWorkCount() {

		WorkCount workCount = workCountRepository.findById(1L).orElse(null);
		if (workCount != null) {
			workCount.setLastCount(workCount.getLastCount() + 1);
			workCountRepository.save(workCount);
		}
	}

	private String fetchWorkNo() {

		WorkCount workCount = workCountRepository.findById(1L).orElse(null);

		if (workCount == null) {
			workCount = new WorkCount(1L, 0);
			workCountRepository.save(workCount);
		}
		long count = workCount.getLastCount() + 1L;

		String workNo = count + "";

		return workNo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	synchronized public ResponseObject editOngoingWork(WorkBean bean) throws Exception {

		ResponseObject responseObject = null;
		try {
			if (bean != null) {
				Work entity = workRepository.findById(bean.getId()).orElse(null);
				responseObject = new ResponseObject();

				convertWorkBeanToEntity(entity, bean);

				workRepository.save(entity);
				responseObject.setId(entity.getId());

			}
			return responseObject;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			throw new Exception(DMSConstants.ERROR_SAVING_DATA);
		}
	}

	private void convertWorkBeanToEntity(Work entity, WorkBean bean) throws java.text.ParseException, ParseException {

		entity.setFinancialYear(bean.getFinancialYear());

		if (bean.getHeadId() != null) {
			// Head head = headRepository.findById(bean.getHeadId()).orElse(null);
			// entity.setWorkHead(head.getHeadName());
			entity.setWorkHead(bean.getHeadId());
		}
		entity.setWorkSubtypeId(bean.getWorkSubTypeId());
		if (bean.getHeadStateId() != null) {
			Head headState = headRepository.findById(bean.getHeadStateId()).orElse(null);
			entity.setHeadState(headState.getId());
		}

		if (bean.getHeadNhmId() != null) {
			Head headNhm = headRepository.findById(bean.getHeadNhmId()).orElse(null);
			entity.setHeadNhm(headNhm.getId());
		}

		if (bean.getHeadEcpr2Id() != null) {
			Head headEcpr = headRepository.findById(bean.getHeadEcpr2Id()).orElse(null);
			entity.setHeadEcrp2(headEcpr.getId());
		}

		if (bean.getHeadOthersId() != null) {
			Head headOthers = headRepository.findById(bean.getHeadOthersId()).orElse(null);
			entity.setHeadOthers(headOthers.getId());
		}

		if (bean.getSchemeId() != null) {
			Schemes scheme = schemeRepository.findById(bean.getSchemeId()).orElse(null);
			// entity.setScheme(scheme.getSchemeName());
			entity.setScheme(scheme.getId());
		}

		if (bean.getSchemeStateId() != null) {
			Schemes schemeState = schemeRepository.findById(bean.getSchemeStateId()).orElse(null);
			entity.setSchemeState(schemeState.getId());
		}

		if (bean.getSchemeNhmId() != null) {
			Schemes schemeNhm = schemeRepository.findById(bean.getSchemeNhmId()).orElse(null);
			entity.setSchemeNhm(schemeNhm.getId());
		}

		if (bean.getSchemeEcpr2Id() != null) {
			Schemes schemeEcpr = schemeRepository.findById(bean.getSchemeEcpr2Id()).orElse(null);
			entity.setSchemeEcrp2(schemeEcpr.getId());
		}

		if (bean.getSchemeOthersId() != null) {
			Schemes schemeOther = schemeRepository.findById(bean.getSchemeOthersId()).orElse(null);
			entity.setSchemeOthers(schemeOther.getId());
		}

		if (bean.getWorkTypeId() != null) {
			WorkType workType = workTypeRepository.findById(bean.getWorkTypeId()).orElse(null);
			// entity.setWorkType(workType.getWorkTypeNameE());
			entity.setWorkType(workType.getWorkTypeId());
			entity.setWorTypeId(workType.getWorkTypeId());
		}
		if (bean.getWorkCategoryId() != null) {
			WorkCategory workCategory = workCategoryRepository.findById(bean.getWorkCategoryId()).orElse(null);
			entity.setWorkCategoryId(workCategory.getWorkCategoryId());
		}
		if (bean.getWorkSubTypeId() != null) {

			WorkSubType workSubType = workSubTypeRepository.findById(bean.getWorkSubTypeId()).orElse(null);
			entity.setWorkSubtypeId(workSubType.getWorkSubtypeId());

		}

		if (!StringUtils.isEmpty(bean.getStatus())) {
			entity.setStatus(bean.getStatus());
		} else {
			entity.setStatus(DMSConstants.STATUS_ACTIVE);
		}

		if (bean.getCategorySubTypeId() != null) {
			SubCategory subCategory = subCategoryRepository.findById(bean.getCategorySubTypeId()).orElse(null);
			entity.setCategorySubtypeId(subCategory.getCategorySubTypeId());
		}

		entity.setWorkName(bean.getWorkName());

		if (null != bean.getImplementationAgency()) {
			workSensitiveFieldService.validateNormalEditImplementationAgency(
					entity, bean.getImplementationAgency());
			ImplementationAgency implAgency = implAgencyRepository.findById(bean.getImplementationAgency())
					.orElse(null);

			// ImplementationAgency implAgency =
			// implAgencyRepository.findById(Long.valueOf(bean.getImplementationAgencyId().orElse(null)));
			// entity.setImplementationAgency(implAgency.getImplAgencyname());
			if (null != implAgency) {
				entity.setImplementationAgency(implAgency.getImplementationAgencyId());
			}

		}
		if (bean.getMultifundedStatus() != null) {
			entity.setMultifundedStatus(bean.getMultifundedStatus());
		}
		// entity.setWorkStatus(bean.getWorkStatus());

		if (bean.getFundByState() != null) {
			entity.setFundByState(bean.getFundByState());
		}

		if (bean.getFundByNhm() != null) {
			entity.setFundByNhm(bean.getFundByNhm());
		}

		if (bean.getFundByEcrp2() != null) {
			entity.setFundByEcrp2(bean.getFundByEcrp2());
		}

		if (bean.getFundByOthers() != null) {
			entity.setFundByOthers(bean.getFundByOthers());
		}

		if (bean.getEstimatedAmt() != null) {
			entity.setEstimatedAmt(bean.getEstimatedAmt());
		}

		if (bean.getDistrictCode() != null) {
			District district = districtRepository.findByDistrictCode(bean.getDistrictCode());
			// entity.setDistrictName(district.getDistrictName());
			entity.setDistrictId(district.getDistrictId());
			entity.setDistrictCode(district.getDistrictCode());
		}

		if (bean.getDivisionId() != null) {
			Division division = divisionRepository.findById(bean.getDivisionId()).orElse(null);
			// entity.setDivisionName(division.getDivisionName());
			entity.setDivisionId(bean.getDivisionId());
			entity.setDivisionCode(division.getDivisionId());
		}

		if (bean.getConstituencyCode() == null || bean.getConstituencyCode().equals("null")
				|| bean.getConstituencyCode().equals("")) {

		} else {
			LegislativeConstituency LC = legislativeConsRepository.findByConstituencyCode(bean.getConstituencyCode());
			// entity.setLegislativeConstituencyName(LC.getConstituencyName());
			entity.setLegislativeConstituencyId(LC.getId());
			entity.setLegislativeConstituencyCode(LC.getConstituencyCode());
		}

		if (bean.getWorkRequestStatusId() != null) {
			entity.setWorkRequestStatusId(bean.getWorkRequestStatusId());
		}

		if (bean.getBlockCode() != null) {
			Block block = blockRepository.findByBlockCode(bean.getBlockCode());
			entity.setBlockCode(block.getBlockCode());
			// entity.setBlockName(block.getBlockName());
			entity.setBlockId(block.getBlockId());
		}

		if (bean.getGramPanchayatCode() != null) {
			GramPanchayat gramPanchayat = gramPanchayatRepository.findByGramPanchayat(bean.getGramPanchayatCode()); // districtRepository.findByDistrictCode(bean.getDistrictCode());
			entity.setGramPanchayatCode(gramPanchayat.getGramPanchayatCode());
			entity.setGramPanchayatId(gramPanchayat.getGramPanchayatId());
		}

		if (bean.getFileStatus() != null) {
			if (bean.getFileStatus().equals("1")) {
				entity.setDrawCreatedBy(bean.getDrawCreatedBy());
			}
		}

		if (bean.getSecureAmtStatus() != null) {
			entity.setSecureAmtStatus(bean.getSecureAmtStatus());
		}

		/*
		 * String formattedDate = ""; Date date;
		 * 
		 * try { String inputDate = bean.getStartDate(); DateFormat inputFormat = new
		 * SimpleDateFormat("dd/MM/yyyy"); DateFormat outputFormat = new
		 * SimpleDateFormat("yyyy-MM-dd"); date = inputFormat.parse(inputDate);
		 * formattedDate = outputFormat.format(date); } catch (java.text.ParseException
		 * e) { // TODO Auto-generated catch block e.printStackTrace();
		 * 
		 * }
		 */

		if (bean.getStartDate() != null) {
			entity.setStartDate(bean.getStartDate());
		}

		if (bean.getAllocatedAmount() != null) {
			entity.setAllocatedAmount(bean.getAllocatedAmount());
		}

		// entity.setRemarks(bean.getRemarks());

		/*
		 * WorkStatus workStatus =
		 * workStatusRepository.findById(bean.getWorkStatusId()).orElse(null);
		 * entity.setWorkStatus(workStatus.getWorkStatusNameE());
		 */

		// entity.setStatus(DMSConstants.STATUS_ACTIVE);

		// entity.setAsAmt(bean.getAsAmt());
		// entity.setWorkCategoryId(bean.getWorkCategoryId());

		// entity.setTsAmt(bean.getTsAmt());

		entity.setTsNo(bean.getTsNo());
		entity.setTsAmt(bean.getTsAmt());
		entity.setTsDate(bean.getTsDate());
		entity.setTsRemarks(bean.getTsRemarks());
		// tsasWork.setDocumentUploadTechnical(tsasWorkBean.getTsDocumentUpload());

		entity.setAsNo(bean.getAsNo());
		entity.setAsAmt(bean.getAsAmt());
		entity.setAsDate(bean.getAsDate());
		entity.setAsRemarks(bean.getAsRemarks());
		entity.setIsTenders(bean.getIsTenders());

		entity.setDmRemakrs(bean.getDmRemakrs());
		entity.setDmStatus(bean.getDmStatus());
		if (null != bean.getWorkPriorityId()) {
			WorkPriority workPriority = workPriorityRepository.findById(bean.getWorkPriorityId()).orElse(null);
			entity.setWorkPriorityId(workPriority.getId());
		}

		if (null != bean.getFinancialHeadId()) {
			FinancialHead financialHead = financialHeadRepository.findById(bean.getFinancialHeadId()).orElse(null);
			entity.setFinancialHeadId(financialHead.getId());
		}

		if (null != bean.getVidhanSabhaId()) {
			VidhanSabha vidhanSabha = vidhanSabhaRepositorys.findById(bean.getVidhanSabhaId()).orElse(null);
			entity.setVidhanSabhaId(vidhanSabha.getId());
		}
	}

	/*
	 * private void convertTSASWorkBeanToEntity(TSASWork tsasWork, TSASWorkBean
	 * tsasWorkBean) {
	 * 
	 * // System.out.println("WorkRequestStatusId....."+tsasWorkBean.
	 * getWorkRequestStatusId());
	 * 
	 * tsasWork.setWork(workRepository.findById(tsasWorkBean.getWorkId()).orElse(
	 * null)); tsasWork.setTsNo(tsasWorkBean.getTsNo());
	 * tsasWork.setTsAmt(tsasWorkBean.getTsAmt());
	 * tsasWork.setTsDate(tsasWorkBean.getTsDate());
	 * tsasWork.setTsRemarks(tsasWorkBean.getTsRemarks()); //
	 * tsasWork.setDocumentUploadTechnical(tsasWorkBean.getTsDocumentUpload());
	 * 
	 * tsasWork.setAsNo(tsasWorkBean.getAsNo());
	 * tsasWork.setAsAmt(tsasWorkBean.getAsAmt());
	 * tsasWork.setAsDate(tsasWorkBean.getAsDate());
	 * tsasWork.setAsRemarks(tsasWorkBean.getAsRemarks()); //
	 * tsasWork.setDocumentUploadAdministration(tsasWorkBean.getAsDocumentUpload())
	 * // tsasWork.setWorkStatus(tsasWorkBean.getWorkStatus()); //
	 * tsasWork.setWorkRequestStatusId(tsasWorkBean.getWorkRequestStatusId());
	 * 
	 * }
	 */
	private void convertTSRevisedWorkBeanToEntity(TSASReviseWork tsasReviseWork, TSASReviseWorkBean tsasReviseWorkBean,
			String type) {

		tsasReviseWork.setTsAsId(tsasReviseWorkBean.getTsAsId());
		tsasReviseWork.setRevisedStatus(tsasReviseWorkBean.getRevisedStatus());
		tsasReviseWork.setWork(workRepository.findById(tsasReviseWorkBean.getWorkId()).orElse(null));
		tsasReviseWork.setRvNo(tsasReviseWorkBean.getRvOrderNo());
		tsasReviseWork.setRvAmt(tsasReviseWorkBean.getRvAmt());
		tsasReviseWork.setRvDate(tsasReviseWorkBean.getRvOrderDate());
		tsasReviseWork.setRvRemarks(tsasReviseWorkBean.getRvRemarks());
		tsasReviseWork.setTypeDoc(type);
		tsasReviseWork.setTsAsSataus(DMSConstants.STATUS_ACTIVE);

	}

	@Override
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	@Transactional
	public String deleteWork(Long id) {

		try {

			Work entity = workRepository.findById(id).orElse(null);
			TSASWork entity2 = tsasWorkRepository.findByWorkId(id);
			WorkTender entity3 = workTenderRepository.findByWorkId(id);
			WorkProgress entity4 = workProgressRepository.findByWorkId(id);
			CC entity5 = ccRepository.findByWorkId(id);

			if (entity != null) {
				workDocumentCleanupService.deleteForWork(id);
				entity.setStatus(DMSConstants.STATUS_DELETED);
				workRepository.save(entity);
				/*
				 * if (entity2 != null) { entity2.setStatus(DMSConstants.STATUS_DELETED);
				 * tsasWorkRepository.save(entity2); }
				 * 
				 * if (entity3 != null) { entity3.setStatus(DMSConstants.STATUS_DELETED);
				 * workTenderRepository.save(entity3); }
				 * 
				 * if (entity4 != null) { entity4.setStatus(DMSConstants.STATUS_DELETED);
				 * workProgressRepository.save(entity4); }
				 * 
				 * if (entity5 != null) { entity5.setStatus(DMSConstants.STATUS_DELETED);
				 * ccRepository.save(entity5); }
				 */
			}
			return null;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return DMSConstants.ERROR_DELETING_DATA;
		}
	}

	@Override
	public WorkBean fetchWorkDetails(Long id) {

		try {
			Work entity = workRepository.findActiveStatusById(id);
			WorkBean bean = null;
			if (entity != null) {
				bean = convertWorkEntityToBean(entity, "IdWise");
				if (entity.getUserAssignee() != null) {
					bean.setUserAssignee(entity.getUserAssignee());

					bean.setUserAssigneeName(
							userRepository.findById(entity.getUserAssignee()).orElse(null).getFirstname() + " "
									+ userRepository.findById(entity.getUserAssignee()).orElse(null).getLastname());
				}

			}

			return bean;
		} catch (Exception e) {
			logger.error("An exception occurred fetching work... .", e);
			return null;
		}
	}

	@Override
	public MergeWorkJson fetchFullWorkDetails(Long id) {

		try {
			MergeWorkJson json = new MergeWorkJson();
			WorkBean bean = new WorkBean();
			TSASWorkBean tsasWorkBean = new TSASWorkBean();
			WorkTenderBean workTenderBean = new WorkTenderBean();
			ContractorBean contractorBean = new ContractorBean();
			CCBean ccBean = new CCBean();
			Work work = workRepository.findById(id).orElse(null);
			TSASWork tsasWork = tsasWorkRepository.findByWorkId(id);
			WorkTender workTender = workTenderRepository.findByWorkId(id);
			Contractor contractor = contractorRepository.findByWork_Id(id);
			CC cc = ccRepository.findByWorkId(id);
			if (work != null) {
				bean = convertWorkEntityToBean(work, "All");
			}
			if (tsasWork != null) {
				tsasWorkBean = convertTSASWorkEntityToBean(tsasWork);
			}
			if (workTender != null) {
				workTenderBean = convertWorkTenderEntityToWorkTenderBean(workTender);
			}
			if (contractor != null) {
				contractorBean = convertContractorEntityToBean(contractor);
			}
			if (cc != null) {
				ccBean = convertCCWorkEntityToBean(cc);
			}
			json.setWorkBeans(bean);
			json.setTsasWorkBeans(tsasWorkBean);
			json.setWorkTenderBeans(workTenderBean);
			json.setWoContractorBeans(contractorBean);
			json.setCcBeans(ccBean);

			return json;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	@Override
	public TSASWorkBean fetchTSASWorkDetails(Long id) {

		try {
			TSASWork entity = tsasWorkRepository.findByWorkId(id);
			TSASWorkBean bean = null;
			if (entity != null) {
				bean = convertTSASWorkEntityToBean(entity);
			}

			return bean;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}

	}

	@Override
	public WorkProgressBean fetchWorkProgress(Long id) {
		try {
			WorkProgress entity = workProgressRepository.findByWorkId(id);
			// entity.getProgressDocumentUpload().getDocumentId();
			WorkProgressBean bean = null;
			if (entity != null) {
				bean = convertWorkProgressEntityToBean(entity);
			}

			return bean;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}

	}

	private TSASWorkBean convertTSASWorkEntityToBean(TSASWork tsasWork) throws ParseException {

		TSASWorkBean tsasWorkBean = null;

		if (tsasWork != null) {

			tsasWorkBean = new TSASWorkBean();
			tsasWorkBean.setWorkId(tsasWork.getWork().getId());

			tsasWorkBean.setWorkStatus(tsasWork.getWorkStatus());

			tsasWorkBean.setId(tsasWork.getId());
			tsasWorkBean.setTsAmt(tsasWork.getTsAmt());
			tsasWorkBean.setTsNo(tsasWork.getTsNo() != null ? tsasWork.getTsNo() : "");
			tsasWorkBean.setTsDate(tsasWork.getTsDate());
			if (null != tsasWork.getDocumentUploadTechnical()) {
				tsasWorkBean.setTsFileId(tsasWork.getDocumentUploadTechnical().getDocumentId());
			}
			if (null != tsasWork.getDocumentUploadAdministration()) {
				tsasWorkBean.setAsFileId(tsasWork.getDocumentUploadAdministration().getDocumentId());
			}
			if (null != tsasWork.getDocumentUploadTechnical()) {
				tsasWorkBean.setFileName(tsasWork.getDocumentUploadTechnical().getDocumentName());
			}
			tsasWorkBean.setTsRemarks(tsasWork.getTsRemarks());
			tsasWorkBean.setAsAmt(tsasWork.getAsAmt());
			tsasWorkBean.setAsNo(tsasWork.getAsNo());
			tsasWorkBean.setAsDate(tsasWork.getAsDate());
			tsasWorkBean.setAsRemarks(tsasWork.getAsRemarks());

		}
		return tsasWorkBean;

	}

	private TSASReviseWorkBean convertTSASRevisedWorkEntityToBean(TSASReviseWork tsasReviseWork) throws ParseException {

		TSASReviseWorkBean tsasReviseWorkBean = null;

		if (tsasReviseWork != null) {

			tsasReviseWorkBean = new TSASReviseWorkBean();
			tsasReviseWorkBean.setWorkId(tsasReviseWork.getWork().getId());

			// tsasReviseWorkBean.setWorkStatus(tsasReviseWork.getWorkStatus());

			tsasReviseWorkBean.setId(tsasReviseWork.getId());
			tsasReviseWorkBean.setRvAmt(tsasReviseWork.getRvAmt());
			tsasReviseWorkBean.setRvOrderNo(tsasReviseWork.getRvNo());
			tsasReviseWorkBean.setRvOrderDate(tsasReviseWork.getRvDate());
			tsasReviseWorkBean.setRvFileId(tsasReviseWork.getDocumentUploadRevised().getDocumentId());

			tsasReviseWorkBean.setFileName(tsasReviseWork.getDocumentUploadRevised().getDocumentName());
			tsasReviseWorkBean.setRvRemarks(tsasReviseWork.getRvRemarks());

			tsasReviseWorkBean.setTypeDoc(tsasReviseWork.getTypeDoc());

		}
		return tsasReviseWorkBean;

	}

	private WorkProgressBean convertWorkProgressEntityToBean(WorkProgress workProgress) throws ParseException {

		WorkProgressBean workProgressBean = null;

		if (workProgress != null) {

			workProgressBean = new WorkProgressBean();
			Long workProgressCount = workProgressRepository
					.countByWorkIdAndTotalExpensessIsNotNull(workProgress.getWork().getId());
			workProgressBean.setWorkProgressCount(workProgressCount);
			workProgressBean.setId(workProgress.getId());
			workProgressBean.setWorkId(workProgress.getWork().getId());
			workProgressBean.setPerc(workProgress.getPerc());
			WorkStatus workStatus = workStatusRepository.findById(workProgress.getWorkStatusId()).orElse(null);
			workProgressBean.setWorkStatusId(workStatus.getId());
			workProgressBean.setWorkStatus(workStatus.getWorkStatusNameE());
			workProgressBean.setWorkSubStatusId(workProgress.getWorkSubStatusId());
			workProgressBean.setActionTakenDelay(workProgress.getActionTakenDelay());
			workProgressBean.setStipulatedDateCompleted(workProgress.getStipulatedDateCompleted());
			workProgressBean.setLikelyDateCompleted(workProgress.getLikelyDateCompleted());
			workProgressBean.setDateCompletion(workProgress.getDateCompletion());
			workProgressBean.setDateHandOver(workProgress.getDateHandOver());
			workProgressBean.setRemarks(workProgress.getRemarks());
			workProgressBean.setPerc(workProgress.getPerc());
			workProgressBean.setExpensessCurrentFy(workProgress.getExpensessCurrentFy());
			workProgressBean.setExpensessUptoMarch(workProgress.getExpensessUptoMarch());
			Double agencyExpenditureTotal = sumFinancialAgencyExpenditureByWorkId(workProgress.getWork().getId());
			if (agencyExpenditureTotal != null && agencyExpenditureTotal > 0) {
				workProgressBean.setTotalExpensess(BigDecimal.valueOf(agencyExpenditureTotal));
			} else {
				workProgressBean.setTotalExpensess(workProgress.getTotalExpensess());
			}
			if (expensesDataRepository.findByWorkId(workProgress.getWork().getId()).size() > 0) {
				// workProgressBean.setTotalExpensess(workProgress.getTotalExpensess().add(expensesDataRepository.findByWorkId(workProgress.getWork().getId()).get(0).getExpensessCurrentFy()));
			}
			workProgressBean.setOtherReasonDelay(workProgress.getOtherReasonDelay());
			workProgressBean.setWorkSubDelayReasonId(workProgress.getWorkSubDelayReasonId());
//			workProgressBean.setWorkSubDelayReasonId(workProgress.getWorkSubDelayReasonId().getWorkSubDelayReasonId());
			List<DocumentUploadWorkProgress> wpPage = null;

			// Long workId=(Long) searchParameterWorkName ;

			DocumentUploadWorkProgress singleResult = documentUploadWorkProgressRepository.findByWorkId(workProgress.getWork().getId());
			if (singleResult != null) {
				wpPage = new ArrayList<>();
				wpPage.add(singleResult);
				workProgressBean.setSize(wpPage.size());
			} else {
				workProgressBean.setSize(0);
			}

			/*
			 * List<DocumentUploadWorkProgress> list = new
			 * ArrayList<DocumentUploadWorkProgress>(); System.err.println("============ " +
			 * list.size());
			 */

			/*
			 * for(DocumentUploadWorkProgress documentUploadWorkProgress:wpPage) {
			 * 
			 * if(null != documentUploadWorkProgress.getDocumentId()) {
			 * System.err.println("workProgress.getProgressDocumentUpload()========="
			 * +documentUploadWorkProgress.getDocumentId());
			 * workProgressBean.setDocumentUploadWorkImage(documentUploadWorkProgress.
			 * getDocumentId()); System.err.println(
			 * "workProgress.getProgressDocumentUpload().getDocumentId()========="
			 * +documentUploadWorkProgress.getDocumentId());
			 * workProgressBean.setWorkUploadImageUrl(imageUrl +
			 * "admin/downloadDocumentWSPro/" + documentUploadWorkProgress.getDocumentId());
			 * list.add(documentUploadWorkProgress); workProgressBean.setListDocument(list);
			 * } }
			 */

			/*
			 * if (workProgress.getWorkStatusId() == 11) { if
			 * (workProgress.getProgressDocumentUpload().getDocumentId() != null) {
			 * workProgressBean.setProFileId(workProgress.getProgressDocumentUpload().
			 * getDocumentId()); } }
			 */
			if (null != workProgress.getMonth()) {
				Month month = monthRepository.findById(workProgress.getMonth()).orElse(null);
				if (null != month) {
					workProgressBean.setMonth(month.getId());
					workProgressBean.setMonthName(month.getMonthName());
				}
			}
			if (null != workProgress.getFinancialYear()) {
				FinancialYear financialYear = financialYearRepository.findById(workProgress.getFinancialYear())
						.orElse(null);
				if (null != financialYear) {
					workProgressBean.setFinancialYear(financialYear.getId());
					workProgressBean.setFinancialYearName(financialYear.getFinancialYear());
				}
			}
			workProgressBean.setMonthlyProgress(workProgress.getMonthlyProgress());
		}

		return workProgressBean;

	}

	@Override
	public CCBean fetchCCWorkDetails(Long id) {

		try {
			CC entity = ccRepository.findByWorkId(id);
			CCBean bean = null;
			if (entity != null) {
				bean = convertCCWorkEntityToBean(entity);
			}

			return bean;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	private CCBean convertCCWorkEntityToBean(CC entity) throws ParseException {

		CCBean ccBean = null;

		if (entity != null) {

			ccBean = new CCBean();
			ccBean.setWorkId(entity.getWork().getId());
			ccBean.setCcNo(entity.getCcNo());
			ccBean.setCcDate(entity.getCcDate());
			WorkStatus workStatus = workStatusRepository.findById(entity.getWorkStatusId()).orElse(null);
			if (null != workStatus) {
				ccBean.setWorkStatus(workStatus.getWorkStatusNameE());
			}
			ccBean.setWorkStatusId(entity.getWorkStatusId());
			ccBean.setCcFileId(entity.getDocumentUploadCC().getDocumentId());
			ccBean.setRemarks(entity.getRemarks());
			ccBean.setHandoverRemarks(entity.getHandoverRemarks());
			ccBean.setDateHandOver(entity.getDateHandOver());
			ccBean.setPaymentStatus(entity.getPaymentStatus());

		}
		return ccBean;

	}

	@Override
	public List<DistrictBean> fetchDistricts() {

		try {
			List<District> list = districtRepository.findByEnabled((short) 1);

			List<DistrictBean> beanList = new ArrayList<>();
			for (District district : list) {
				beanList.add(convertDistrictEntityToBean(district));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	@Override
	public List<DivisionBean> fetchDivisions() {

		try {

			User user = DMSUtil.getUserDetail();

			Users userEntity = userRepository.findByUsernameAndStatus(user.getUsername(), DMSConstants.STATUS_ACTIVE);

			Collection<GrantedAuthority> role = user.getAuthorities();
			Long divisionCode = null;
			String districtCode = "";

			String r = role.toString();
			if (r.contains("ROLE_DEPARTMENT")) {
				Division division = userEntity.getDivision();
				divisionCode = 3L;
			} else {
				divisionCode = null;
			}
			List<Division> list = null;
			if (divisionCode != null) {
				list = divisionRepository.findByDivisionIdAndEnabled(divisionCode, (short) 1);
			} else {
				list = divisionRepository.findByEnabled((short) 1);
			}

			List<DivisionBean> beanList = new ArrayList<>();
			for (Division division : list) {
				beanList.add(convertDivisionEntityToBean(division));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	private DistrictBean convertDistrictEntityToBean(District entity) {

		DistrictBean bean = new DistrictBean();
		if (entity != null) {
			bean.setDistrictId(entity.getDistrictId());
			bean.setDistrictCode(entity.getDistrictCode());
			bean.setDistrictName(entity.getDistrictName());
			bean.setDistrictNameH(entity.getDistrictNameH());
			bean.setEnabled(entity.getEnabled());
		}
		return bean;
	}

	private DivisionBean convertDivisionEntityToBean(Division entity) {

		DivisionBean bean = new DivisionBean();
		if (entity != null) {
			bean.setDivisionId(entity.getDivisionId());
			bean.setDivisionName(entity.getDivisionName());
			bean.setEnabled(entity.getEnabled());
		}
		return bean;
	}

	@Override
	public UserDetailResponse getLogin(LoginJson loginJson) throws DMSBusinessException {

		String userName = loginJson.getUserName();

		String pwd = loginJson.getPwd();

		UserDetailResponse userDetailResponse = new UserDetailResponse();

		if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(pwd)) {

			Users userEntity = userRepository.findByUsernameAndStatus(userName, DMSConstants.STATUS_ACTIVE);

			if (userEntity != null) {

				PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

				// String encodedPassword =
				// passwordEncoder.encode(SHAHashingUtil.encryptPassword(pwd));

				if (passwordEncoder.matches(pwd, userEntity.getPassword())) {

					userDetailResponse.setUserId(userEntity.getId().toString());
					userDetailResponse.setEmail(userEntity.getEmailId());

					userDetailResponse.setMobile(userEntity.getMobileNo());
					userDetailResponse.setName(userEntity.getFirstname());
					userDetailResponse.setDistrictCode(
							userEntity.getDistrict() != null ? userEntity.getDistrict().getDistrictCode() : "");
					userDetailResponse.setRoleId(userEntity.getId().toString());

				} else {

					throw new DMSBusinessException("Password is incorrect!!!");
				}

			} else {
				throw new DMSBusinessException("UserName is invalid!!!");
			}
		} else {

			throw new DMSBusinessException("UserName or password cannot be empty!!!");
		}
		return userDetailResponse;
	}

	@Override
	public List<BlockBean> fetchBlocksByDistrictCode(String districtCode) {
		try {

			District district = districtRepository.findByDistrictCodeAndEnabled(districtCode, DMSConstants.ENABLED);

			List<Block> list = blockRepository.findByDistrictAndEnabledOrderByBlockName(district, (short) 1);

			List<BlockBean> beanList = new ArrayList<>();

			for (Block block : list) {
				beanList.add(convertBlockEntityToBean(block));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred in fetchBlocksByDistrictCode Method", e);
			return null;
		}
	}

	@Override
	public List<GramPanchayatBean> fetchGramPanchayatByBlockCode(String districtCode, String blockCode) {
		try {

			// District district =
			// districtRepository.findByDistrictCodeAndEnabled(districtCode,
			// DMSConstants.ENABLED);

			// Block block = blockRepository.findByBlockCodeAndEnabled(blockCode,
			// DMSConstants.ENABLED);

			List<GramPanchayat> list = gramPanchayatRepository.findByOptionalDistrictAndBlockCode(districtCode,
					blockCode, (short) 1);

			List<GramPanchayatBean> beanList = new ArrayList<>();

			for (GramPanchayat gramPanchayat : list) {
				beanList.add(convertGramPanchayatEntityToBean(gramPanchayat));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred in fetchBlocksByDistrictCode Method", e);
			return null;
		}
	}

	@Override
	public List<WorkCategoryBean> fetchWorkCategory() {
		try {
			List<WorkCategory> list = workCategoryRepository.findByEnabledOrderByOrdering(DMSConstants.ENABLED);

			List<WorkCategoryBean> beanList = new ArrayList<>();
			for (WorkCategory workCategory : list) {
				beanList.add(convertWorkCategoryEntityToBean(workCategory));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	@Override
	public List<BlockBean> fetchBlock() {
		try {
			List<Block> list = blockRepository.findByEnabled(DMSConstants.ENABLED);

			List<BlockBean> beanList = new ArrayList<>();
			for (Block block : list) {
				beanList.add(convertBlockEntityToBean(block));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	@Override
	public List<LegislativeConstituencyBean> fetchlegislativeCont() {
		try {
			List<LegislativeConstituency> list = legislativeConsRepository.findByEnabled(DMSConstants.ENABLED);

			List<LegislativeConstituencyBean> beanList = new ArrayList<>();
			for (LegislativeConstituency legislativeConstituency : list) {
				beanList.add(convertLegislativeConstituencyEntityToBean(legislativeConstituency));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	@Override
	public List<SubCategoryBean> fetchSubCategories() {
		try {
			List<SubCategory> list = subCategoryRepository.findByEnabled(DMSConstants.ENABLED);

			List<SubCategoryBean> beanList = new ArrayList<>();
			for (SubCategory subCategory : list) {
				beanList.add(convertSubCategoryEntityToBean(subCategory));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	@Override
	public List<WorkCategoryBean> fetchWorkCategoryByWorkType(Long workTypeId) {
		try {
			// List<WorkCategory> list =
			// workCategoryRepository.findByWorkTypeAndEnabledOrderByWorkCategoryNameE(
			// workTypeRepository.findById(workTypeId).orElse(null), DMSConstants.ENABLED);

			List<WorkCategory> list = workCategoryRepository.findByEnabledOrderByOrdering(DMSConstants.ENABLED);

			List<WorkCategoryBean> beanList = new ArrayList<>();
			for (WorkCategory workCategory : list) {
				beanList.add(convertWorkCategoryEntityToBean(workCategory));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	@Override
	public List<DistrictBean> fetchDistrictByDivision(Long divisionId) {
		try {
			List<District> list = districtRepository.findByDivisionAndEnabled(
					divisionRepository.findById(divisionId).orElse(null), DMSConstants.ENABLED);

			List<DistrictBean> beanList = new ArrayList<>();
			for (District district : list) {
				beanList.add(convertDistrictEntityToBean(district));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	@Override
	public List<BlockBean> fetchBlockByDistrict(Long districtId) {
		try {
			List<Block> list = blockRepository.findByDistrictAndEnabledOrderByBlockName(
					districtRepository.findById(districtId).orElse(null), DMSConstants.ENABLED);
			List<BlockBean> beanList = new ArrayList<>();
			for (Block block : list) {
				beanList.add(convertBlockEntityToBean(block));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}

	}

	@Override
	public List<LegislativeConstituencyBean> fetchLegislativeByDistrict(String districtCode) {
		try {
			List<LegislativeConstituency> list = legislativeConsRepository.findByDistrictAndEnabled(
					districtRepository.findByDistrictCode(districtCode), DMSConstants.ENABLED);

			List<LegislativeConstituencyBean> beanList = new ArrayList<>();
			for (LegislativeConstituency legislativeConstituency : list) {
				beanList.add(convertLegislativeConstituencyEntityToBean(legislativeConstituency));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	@Override
	public List<SubCategoryBean> fetchSubCategoryByCategory(Long categoryId) {
		try {
			List<SubCategory> list = subCategoryRepository.findByWorkCategoryAndEnabledOrderByCategorySubTypeNameE(
					workCategoryRepository.findById(categoryId).orElse(null), DMSConstants.ENABLED);

			List<SubCategoryBean> beanList = new ArrayList<>();
			for (SubCategory subCategory : list) {
				beanList.add(convertSubCategoryEntityToBean(subCategory));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	private SubCategoryBean convertSubCategoryEntityToBean(SubCategory entity) {

		SubCategoryBean bean = new SubCategoryBean();
		if (entity != null) {
			bean.setCategorySubTypeId(entity.getCategorySubTypeId());
			bean.setCategorySubTypeNameE(entity.getCategorySubTypeNameE());
			bean.setCategorySubTypeNameH(entity.getCategorySubTypeNameH());
			bean.setEnabled(entity.getEnabled());
		}
		return bean;
	}

	private WorkCategoryBean convertWorkCategoryEntityToBean(WorkCategory entity) {

		WorkCategoryBean bean = new WorkCategoryBean();
		if (entity != null) {
			bean.setWorkCategoryId(entity.getWorkCategoryId());
			bean.setWorkCategoryNameE(entity.getWorkCategoryNameE());
			bean.setWorkCategoryNameH(entity.getWorkCategoryNameH());
			bean.setEnabled(entity.getEnabled());
		}
		return bean;
	}

	@Override
	public UserBean fetchLoggedInUser() {

		User user = DMSUtil.getUserDetail();

		Users userEntity = userRepository.findByUsernameAndStatus(user.getUsername(), DMSConstants.STATUS_ACTIVE);

		if (userEntity != null) {
			return superAdminService.convertUserEntityToBean(userEntity);

		} else {
			return null;
		}
	}

	@Override
	public List<LCBean> fetchLCsByDistrictName(String districtName) {
		try {

			District district = districtRepository.findByDistrictNameAndEnabled(districtName, DMSConstants.ENABLED);

			List<LegislativeConstituency> list = legislativeConsRepository.findByDistrictAndEnabled(district,
					(short) 1);

			List<LCBean> beanList = new ArrayList<>();

			for (LegislativeConstituency lc : list) {
				beanList.add(convertLCEntityToBean(lc));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred in fetchLCsByDistrictName Method", e);
			return null;
		}
	}

	@Override
	public ImplAgencyJson getAllImplAgency(Pageable pageable, String searchParameter) {

		ImplAgencyJson implAgencyJson = null;

		try {
			Page<ImplementationAgency> implAgency = null;

			if (!StringUtils.isEmpty(searchParameter))
				implAgency = implAgencyRepository.findByImplAgencynameContainingAndEnabled(pageable, searchParameter,
						(short) 1);
			else
				implAgency = implAgencyRepository.findByEnabled(pageable, (short) 1);

			if (implAgency != null) {
				List<ImplementationAgency> entityList = implAgency.getContent();
				List<ImplAgencyBean> beanList = new ArrayList<>();
				if (entityList != null && !entityList.isEmpty()) {

					int index = pageable.getPageNumber() * pageable.getPageSize();
					for (ImplementationAgency agency : entityList) {

						ImplAgencyBean bean = convertImplAgencyEntityToBean(agency);
						bean.setIndex(++index);
						beanList.add(bean);
					}
				}
				implAgencyJson = new ImplAgencyJson();
				implAgencyJson.setiTotalDisplayRecords(implAgency.getTotalElements());
				implAgencyJson.setiTotalRecords(implAgencyRepository.countByEnabled((short) 1));
				implAgencyJson.setAaData(beanList);
			}
			return implAgencyJson;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return implAgencyJson;
		}
	}

	@Override
	public HeadJson getAllHead(Pageable pageable, String searchParameter) {

		HeadJson headJson = null;

		try {
			Page<WorkHead> head = null;

			if (!StringUtils.isEmpty(searchParameter))
				head = workHeadRepository.findByHeadNameContainingAndEnabled(pageable, searchParameter, (short) 1);
			else
				head = workHeadRepository.findByEnabled(pageable, (short) 1);

			if (head != null) {
				List<WorkHead> entityList = head.getContent();
				List<WorkHeadBean> beanList = new ArrayList<>();
				if (entityList != null && !entityList.isEmpty()) {

					int index = pageable.getPageNumber() * pageable.getPageSize();
					for (WorkHead heads : entityList) {

						WorkHeadBean bean = convertWorkHeadEntityToBean(heads);
						bean.setIndex(++index);
						beanList.add(bean);
					}
				}
				headJson = new HeadJson();
				headJson.setiTotalDisplayRecords(head.getTotalElements());
				headJson.setiTotalRecords(headRepository.countByEnabled((short) 1));
				headJson.setAaData(beanList);
			}
			return headJson;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return headJson;
		}
	}

	@Override
	public SchemeJson getAllScheme(Pageable pageable, String searchParameter) {

		SchemeJson schemeJson = null;

		try {
			Page<Schemes> scheme = null;

			if (!StringUtils.isEmpty(searchParameter))
				scheme = schemeRepository.findBySchemeNameContainingAndEnabled(pageable, searchParameter, (short) 1);
			else
				scheme = schemeRepository.findByEnabled((short) 1, pageable);

			if (scheme != null) {
				List<Schemes> entityList = scheme.getContent();
				List<SchemeBean> beanList = new ArrayList<>();
				if (entityList != null && !entityList.isEmpty()) {

					int index = pageable.getPageNumber() * pageable.getPageSize();
					for (Schemes schemes : entityList) {

						SchemeBean bean = convertSchemeEntityToBean(schemes);
						bean.setIndex(++index);
						beanList.add(bean);
					}
				}
				schemeJson = new SchemeJson();
				schemeJson.setiTotalDisplayRecords(scheme.getTotalElements());
				schemeJson.setiTotalRecords(schemeRepository.countByEnabled((short) 1));
				schemeJson.setAaData(beanList);
			}
			return schemeJson;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return schemeJson;
		}
	}

	@Override
	public SorJson getAllSor(Pageable pageable, String searchParameter) {

		SorJson sorjson = null;

		try {
			Page<SorYear> sor = null;

			if (!StringUtils.isEmpty(searchParameter))
				sor = sorYearRepository.findBySorYearContainingAndEnabled(pageable, searchParameter, (short) 1);
			else
				sor = sorYearRepository.findByEnabled((short) 1, pageable);

			if (sor != null) {
				List<SorYear> entityList = sor.getContent();
				List<SorYearBean> beanList = new ArrayList<>();
				if (entityList != null && !entityList.isEmpty()) {

					int index = pageable.getPageNumber() * pageable.getPageSize();
					for (SorYear sors : entityList) {

						SorYearBean bean = convertSorEntityToBean(sors);
						bean.setIndex(++index);
						beanList.add(bean);
					}
				}
				sorjson = new SorJson();
				sorjson.setiTotalDisplayRecords(sor.getTotalElements());
				sorjson.setiTotalRecords(schemeRepository.countByEnabled((short) 1));
				sorjson.setAaData(beanList);
			}
			return sorjson;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return sorjson;
		}
	}

	@Override
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public String addImplAgency(ImplAgencyBean bean, String userName, String dateString) {

		try {

			ImplementationAgency entity = implAgencyRepository
					.findByImplAgencynameAndEnabled(bean.getImplementationAgencyNameE(), (short) 1);
			if (entity != null) {
				return "Implementation Agency with given Name Already exist!";
			} else {
				entity = new ImplementationAgency();
				convertImplAgencyBeanToEntity(entity, bean);
				entity.setCreatedBy(userName);
				entity.setCreatedDate(dateString);

				implAgencyRepository.save(entity);

				return null;
			}
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return DMSConstants.ERROR_SAVING_DATA;
		}
	}

	@Override
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public String addHead(WorkHeadBean bean, String userName, String dateString) {

		try {

			WorkHead entity = workHeadRepository.findByHeadNameAndEnabled(bean.getWorkHeadName(), (short) 1);
			if (entity != null) {
				return "Head with given Name Already exist!";
			} else {
				entity = new WorkHead();
				convertWorkHeadBeanToEntity(entity, bean);
				entity.setCreatedBy(userName);
				entity.setCreatedDate(dateString);
				workHeadRepository.save(entity);

				return null;
			}
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return DMSConstants.ERROR_SAVING_DATA;
		}
	}

	@Override
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public String addScheme(SchemeBean bean, String userName, String dateString) {

		try {

			Schemes entity = schemeRepository.findBySchemeNameAndEnabled(bean.getSchemeNameE(), (short) 1);
			if (entity != null) {
				return "Scheme with given Name Already exist!";
			} else {
				entity = new Schemes();
				convertSchemeBeanToEntity(entity, bean);
				entity.setCreatedBy(userName);
				entity.setCreatedDate(dateString);

				schemeRepository.save(entity);

				return null;
			}
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return DMSConstants.ERROR_SAVING_DATA;
		}
	}

	@Override
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public String addSor(SorYearBean bean, String userName, String dateString) {

		try {

			SorYear entity = sorYearRepository.findBySorYearAndEnabled(bean.getSorYearName(), (short) 1);
			if (entity != null) {
				return "Scheme with given Name Already exist!";
			} else {
				entity = new SorYear();
				convertSorBeanToEntity(entity, bean);
				entity.setCreatedBy(userName);
				entity.setCreatedDate(dateString);

				sorYearRepository.save(entity);

				return null;
			}
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return DMSConstants.ERROR_SAVING_DATA;
		}
	}

	@Override
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public String editImplAgency(ImplAgencyBean bean, String date) {

		try {
			ImplementationAgency entity = implAgencyRepository.findById(bean.getImplementationAgencyId()).orElse(null);
			if (!entity.getImplAgencyname().equals(bean.getImplementationAgencyNameE())) {// if Name has changed
				// check whether already exist
				ImplementationAgency implAgency = implAgencyRepository
						.findByImplAgencynameAndEnabled(bean.getImplementationAgencyNameE(), (short) 1);
				if (implAgency != null) {
					// already present
					return "Implementation Agency with given Name Already exist!";
				}
			}
			convertImplAgencyBeanToEntity(entity, bean);
			entity.setCreatedBy(bean.getCreatedBy());
			entity.setCreatedDate(date);
			implAgencyRepository.save(entity);

			return null;

		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return DMSConstants.ERROR_SAVING_DATA;
		}
	}

	@Override
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public String editHead(WorkHeadBean bean, String date) {

		try {
			WorkHead entity = workHeadRepository.findById(bean.getWorkHeadId()).orElse(null);
			if (!entity.getHeadName().equals(bean.getWorkHeadName())) {// if Name has changed
				// check whether already exist
				WorkHead workHead = workHeadRepository.findByHeadNameAndEnabled(bean.getWorkHeadName(), (short) 1);
				if (workHead != null) {
					// already present
					return "Head with given Name Already exist!";
				}
			}
			convertWorkHeadBeanToEntity(entity, bean);
			entity.setCreatedBy(bean.getCreatedBy());
			entity.setCreatedDate(date);
			workHeadRepository.save(entity);

			return null;

		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return DMSConstants.ERROR_SAVING_DATA;
		}
	}

	@Override
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public String editScheme(SchemeBean bean, String date) {

		try {
			Schemes entity = schemeRepository.findById(bean.getSchemeId()).orElse(null);
			if (!entity.getSchemeName().equals(bean.getSchemeNameE())) {// if Name has changed
				// check whether already exist
				Schemes schemes = schemeRepository.findBySchemeNameAndEnabled(bean.getSchemeNameE(), (short) 1);
				if (schemes != null) {
					// already present
					return "Scheme with given Name Already exist!";
				}
			}
			convertSchemeBeanToEntity(entity, bean);
			entity.setCreatedBy(bean.getCreatedBy());
			entity.setCreatedDate(date);
			schemeRepository.save(entity);

			return null;

		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return DMSConstants.ERROR_SAVING_DATA;
		}
	}

	@Override
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public String editSor(SorYearBean bean, String date) {

		try {
			SorYear entity = sorYearRepository.findById(bean.getSorYearId()).orElse(null);
			if (!entity.getSorYear().equals(bean.getSorYearName())) {// if Name has changed
				// check whether already exist
				SorYear sorYear = sorYearRepository.findBySorYearAndEnabled(bean.getSorYearName(), (short) 1);
				if (sorYear != null) {
					// already present
					return "Sor with given Name Already exist!";
				}
			}
			convertSorBeanToEntity(entity, bean);
			entity.setCreatedBy(bean.getCreatedBy());
			entity.setCreatedDate(date);
			sorYearRepository.save(entity);

			return null;

		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return DMSConstants.ERROR_SAVING_DATA;
		}
	}

	@Override
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public String deleteImplAgency(Long id) {

		try {
			ImplementationAgency entity = implAgencyRepository.findById(id).orElse(null);
			if (entity != null) {
				entity.setEnabled((short) 0);
				implAgencyRepository.save(entity);
			}
			return null;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return DMSConstants.ERROR_DELETING_DATA;
		}
	}

	@Override
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public String deleteHead(Long id) {

		try {

			WorkHead entity = workHeadRepository.findById(id).orElse(null);
			if (entity != null) {
				Work work = workRepository.findByWorkHeadContainingAndStatusNotIn(entity.getHeadName(),
						DMSConstants.STATUS_DELETED);
				if (work != null) {
					return "With this head already created work, So cant be able to delete it!";
				} else {
					entity.setEnabled((short) 0);
					workHeadRepository.save(entity);
				}

			}
			return null;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return DMSConstants.ERROR_DELETING_DATA;
		}
	}

	@Override
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public String deleteScheme(Long id) {

		try {

			Schemes entity = schemeRepository.findById(id).orElse(null);
			if (entity != null) {
				Work work = workRepository.findBySchemeContainingAndStatusNotIn(entity.getSchemeName(),
						DMSConstants.STATUS_DELETED);
				if (work != null) {
					return "With this scheme already created work, So cant be able to delete it!";
				} else {
					entity.setEnabled((short) 0);
					schemeRepository.save(entity);
				}

			}
			return null;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return DMSConstants.ERROR_DELETING_DATA;
		}
	}

//working
	@Override
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public String deleteSor(Long id) {

		try {

			SorYear entity = sorYearRepository.findById(id).orElse(null);
			if (entity != null) {
				WorkTender work = workTenderRepository.findBySorYearContainingAndStatusNotIn(entity.getSorYear(),
						DMSConstants.STATUS_DELETED);
				if (work != null) {
					return "With this sor already created work, So cant be able to delete it!";
				} else {
					entity.setEnabled((short) 0);
					sorYearRepository.save(entity);
				}

			}
			return null;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return DMSConstants.ERROR_DELETING_DATA;
		}
	}

	@Override
	public void convertImplAgencyBeanToEntity(ImplementationAgency entity, ImplAgencyBean bean) {

		if (entity != null && bean != null) {

			entity.setImplementationAgencyId(bean.getImplementationAgencyId());
			entity.setImplAgencyname(bean.getImplementationAgencyNameE());

			entity.setEnabled((short) 1);
			entity.setImplAgencyType(bean.getImplAgencyTypeId());
			/*
			 * 
			 * entity.setCreatedDate(bean.getCreatedDate());
			 */
		}
	}

	@Override
	public void convertWorkHeadBeanToEntity(WorkHead entity, WorkHeadBean bean) {

		if (entity != null && bean != null) {

			// entity.setId(null);
			entity.setHeadName(bean.getWorkHeadName());
			/*
			 * entity.setCreatedBy(bean.getCreatedBy());
			 * entity.setCreatedDate(bean.getCreatedDate());
			 */
			entity.setEnabled((short) 1);

		}
	}

	@Override
	public void convertSchemeBeanToEntity(Schemes entity, SchemeBean bean) {

		if (entity != null && bean != null) {

			// entity.setId(null);
			entity.setSchemeName(bean.getSchemeNameE());
			/*
			 * entity.setCreatedBy(bean.getCreatedBy());
			 * entity.setCreatedDate(bean.getCreatedDate());
			 */
			entity.setEnabled((short) 1);

		}
	}

	@Override
	public void convertSorBeanToEntity(SorYear entity, SorYearBean bean) {

		if (entity != null && bean != null) {

			// entity.setId(null);
			entity.setSorYear(bean.getSorYearName());
			/*
			 * entity.setCreatedBy(bean.getCreatedBy());
			 * entity.setCreatedDate(bean.getCreatedDate());
			 */
			entity.setEnabled((short) 1);

		}
	}

	@Override
	public ImplAgencyBean fetchImplAgencyDetails(long id) {
		try {
			ImplementationAgency entity = implAgencyRepository.findById(id).orElse(null);

			return convertImplAgencyEntityToBean(entity);
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	@Override
	public WorkHeadBean fetchHeadDetails(long id) {
		try {
			WorkHead entity = workHeadRepository.findById(id).orElse(null);

			return convertWorkHeadEntityToBean(entity);
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	@Override
	public SchemeBean fetchSchemeDetails(long id) {
		try {
			Schemes entity = schemeRepository.findById(id).orElse(null);

			return convertSchemeEntityToBean(entity);
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	@Override
	public SorYearBean fetchSorDetails(long id) {
		try {
			SorYear entity = sorYearRepository.findById(id).orElse(null);

			return convertSorEntityToBean(entity);
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	public LegislativeConstituencyBean convertLegislativeConstituencyEntityToBean(LegislativeConstituency entity) {
		LegislativeConstituencyBean bean = new LegislativeConstituencyBean();
		if (entity != null) {
			bean.setId(entity.getId());
			bean.setConstituencyCode(entity.getConstituencyCode());
			bean.setConstituencyName(entity.getConstituencyName());
			bean.setDistrictCode(entity.getDistrict().getDistrictCode());
			bean.setEnabled(entity.getEnabled());
		}
		return bean;
	}

	@Override
	public ResponseObject uploadOtherDoc(OtherDocListBean workBean) throws Exception {

		ResponseObject responseObject = null;
		try {
			if (workBean != null) {

				Work work = workRepository.findById(workBean.getId()).orElse(null);
				if (work != null) {
					List<WorkDocument> workDocuments = workDocumentRepository.findByWorkIdAndIsDeleted(work.getId(),
							(short) 0);
					/*
					 * for (WorkDocument workDocument : workDocuments) {// Logic for existing Entry,
					 * edit docs if(workDocument.getFileType().equalsIgnoreCase("AS"))
					 * asDocument(workBean, work, workDocument);
					 * if(workDocument.getFileType().equalsIgnoreCase("TS")) tsDocument(workBean,
					 * work, workDocument);
					 * if(workDocument.getFileType().equalsIgnoreCase("WorkOrder"))
					 * workOrderDocument(workBean, work, workDocument); }
					 */
					/* if(workDocuments.isEmpty()||workDocuments.size()<2) { */// its commented for uploadOtherDoc
					if (workBean != null) {
						saveOtherDocument(work, workBean, new WorkDocument(), "OTH");// AS Document
					}
					/*
					 * if(workBean.getFileArr1()!=null) { saveWorkDocument(work,
					 * workBean.getFileArr1(), new WorkDocument(), "OTH");//AS Document }
					 * if(workBean.getFileArr2()!=null) { saveWorkDocument(work,
					 * workBean.getFileArr2(), new WorkDocument(), "OTH");//AS Document }
					 * if(workBean.getFileArr3()!=null) { saveWorkDocument(work,
					 * workBean.getFileArr3(), new WorkDocument(), "OTH");//AS Document }
					 * if(workBean.getFileArr4()!=null) { saveWorkDocument(work,
					 * workBean.getFileArr4(), new WorkDocument(), "OTH");//AS Document }
					 * if(workBean.getFileArr5()!=null) { saveWorkDocument(work,
					 * workBean.getFileArr5(), new WorkDocument(), "OTH");//AS Document }
					 * if(workBean.getFileArr6()!=null) { saveWorkDocument(work,
					 * workBean.getFileArr6(), new WorkDocument(), "OTH");//AS Document }
					 */

					responseObject = new ResponseObject();
					responseObject.setId(work.getId());
				}
			}
			return responseObject;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			throw new Exception(DMSConstants.ERROR_SAVING_DATA);
		}
	}

	private void saveWorkDocument(Work work, MultipartFile document, WorkDocument workDocumentExisting, String fileType)
			throws DMSBusinessException {

		String fileName = DMSUtil.saveFile(documentsPath + workDocumentsPath, String.valueOf(work.getId()), document);
		WorkDocument workDocument = null;
		if (workDocumentExisting != null) {
			workDocument = workDocumentExisting;
		} else {
			workDocument = new WorkDocument();
		}
		workDocument.setFileType(fileType);
		workDocument.setFileName(fileName);
		workDocument.setWork(work);
		workDocument.setIsDeleted((short) 0);
		workDocumentRepository.save(workDocument);
	}

	private void saveOtherDocument(Work work, OtherDocListBean bean, WorkDocument workDocumentExisting, String fileType)
			throws DMSBusinessException {
		if (bean.getFileArr0() != null) {
			String fileName = DMSUtil.saveFile(documentsPath + workDocumentsPath, String.valueOf(work.getId()),
					bean.getFileArr0());
			WorkDocument workDocument = null;
			/*
			 * if(workDocumentExisting!=null) { workDocument = workDocumentExisting; }else {
			 */
			workDocument = new WorkDocument();
			// }
			workDocument.setNameOfDocs(bean.getRemarks0());
			workDocument.setNoOfDocs(bean.getNoOfDocs0());
			workDocument.setDateOfDocs(DMSUtil.convertStringToDate(bean.getOtherDocDate0()));
			workDocument.setFileType(fileType);
			workDocument.setFileName(fileName);
			workDocument.setWork(work);
			workDocument.setIsDeleted((short) 0);
			workDocumentRepository.save(workDocument);
		}

		if (bean.getFileArr1() != null) {
			String fileName = DMSUtil.saveFile(documentsPath + workDocumentsPath, String.valueOf(work.getId()),
					bean.getFileArr1());
			WorkDocument workDocument = null;

			workDocument = new WorkDocument();

			workDocument.setNameOfDocs(bean.getRemarks1());
			workDocument.setNoOfDocs(bean.getNoOfDocs1());
			workDocument.setDateOfDocs(DMSUtil.convertStringToDate(bean.getOtherDocDate1()));
			workDocument.setFileType(fileType);
			workDocument.setFileName(fileName);
			workDocument.setWork(work);
			workDocument.setIsDeleted((short) 0);
			workDocumentRepository.save(workDocument);
		}

		if (bean.getFileArr2() != null) {
			String fileName = DMSUtil.saveFile(documentsPath + workDocumentsPath, String.valueOf(work.getId()),
					bean.getFileArr2());
			WorkDocument workDocument = null;

			workDocument = new WorkDocument();

			workDocument.setNameOfDocs(bean.getRemarks2());
			workDocument.setNoOfDocs(bean.getNoOfDocs2());
			workDocument.setDateOfDocs(DMSUtil.convertStringToDate(bean.getOtherDocDate2()));
			workDocument.setFileType(fileType);
			workDocument.setFileName(fileName);
			workDocument.setWork(work);
			workDocument.setIsDeleted((short) 0);
			workDocumentRepository.save(workDocument);
		}

		if (bean.getFileArr3() != null) {
			String fileName = DMSUtil.saveFile(documentsPath + workDocumentsPath, String.valueOf(work.getId()),
					bean.getFileArr3());
			WorkDocument workDocument = null;

			workDocument = new WorkDocument();

			workDocument.setNameOfDocs(bean.getRemarks3());
			workDocument.setNoOfDocs(bean.getNoOfDocs3());
			workDocument.setDateOfDocs(DMSUtil.convertStringToDate(bean.getOtherDocDate3()));
			workDocument.setFileType(fileType);
			workDocument.setFileName(fileName);
			workDocument.setWork(work);
			workDocument.setIsDeleted((short) 0);
			workDocumentRepository.save(workDocument);
		}

		if (bean.getFileArr4() != null) {
			String fileName = DMSUtil.saveFile(documentsPath + workDocumentsPath, String.valueOf(work.getId()),
					bean.getFileArr4());
			WorkDocument workDocument = null;

			workDocument = new WorkDocument();

			workDocument.setNameOfDocs(bean.getRemarks4());
			workDocument.setNoOfDocs(bean.getNoOfDocs4());
			workDocument.setDateOfDocs(DMSUtil.convertStringToDate(bean.getOtherDocDate4()));
			workDocument.setFileType(fileType);
			workDocument.setFileName(fileName);
			workDocument.setWork(work);
			workDocument.setIsDeleted((short) 0);
			workDocumentRepository.save(workDocument);
		}

		if (bean.getFileArr5() != null) {
			String fileName = DMSUtil.saveFile(documentsPath + workDocumentsPath, String.valueOf(work.getId()),
					bean.getFileArr5());
			WorkDocument workDocument = null;

			workDocument = new WorkDocument();

			workDocument.setNameOfDocs(bean.getRemarks5());
			workDocument.setNoOfDocs(bean.getNoOfDocs5());
			workDocument.setDateOfDocs(DMSUtil.convertStringToDate(bean.getOtherDocDate5()));
			workDocument.setFileType(fileType);
			workDocument.setFileName(fileName);
			workDocument.setWork(work);
			workDocument.setIsDeleted((short) 0);
			workDocumentRepository.save(workDocument);
		}

		if (bean.getFileArr6() != null) {
			String fileName = DMSUtil.saveFile(documentsPath + workDocumentsPath, String.valueOf(work.getId()),
					bean.getFileArr6());
			WorkDocument workDocument = null;

			workDocument = new WorkDocument();

			workDocument.setNameOfDocs(bean.getRemarks6());
			workDocument.setNoOfDocs(bean.getNoOfDocs6());
			workDocument.setDateOfDocs(DMSUtil.convertStringToDate(bean.getOtherDocDate6()));
			workDocument.setFileType(fileType);
			workDocument.setFileName(fileName);
			workDocument.setWork(work);
			workDocument.setIsDeleted((short) 0);
			workDocumentRepository.save(workDocument);
		}
	}

	@Override
	public String fetchWorkFileName(Long documentId) {
		WorkDocument document = workDocumentRepository.findById(documentId).orElse(null);
		String fileName = document.getFileName();
		String fileWithFullPath = documentsPath + workDocumentsPath + document.getWork().getId() + "/" + fileName;
		return fileWithFullPath;
	}

	@Override
	public String fetchWorkFileNameDW(Long documentId) {
		DocumentUploadDrawingDetail document = documentUploadDrawingDetailRepository.findById(documentId).orElse(null);
		String fileName = document.getDocumentName();
		String fileWithFullPath = documentsPath + drawigUpoadDocumentPath + "/" + fileName;
		return fileWithFullPath;
	}

	@Override
	public String fetchDownloadFileName(Long documentId) {
		logger.info("fetchDownloadFileName ");
		DocumentUpload document = documentRepository.findById(documentId).orElse(null);
		if (document == null || document.getDocumentName() == null) {
			return null;
		}
		String fileName = document.getDocumentName();
		String secureStoredPath = locateStoredDocument(fileName, document.getWorkId(), List.of(
				workTechSanctionDocumentPath, workASSanctionDocumentPath, workTenderSanctionDocumentPath,
				workWorkProgressDocumentPath, CCDocumentPath, dmAttachment,
				workRevisedTechSanctionDocumentPath, workASRevisedSanctionDocumentPath));
		if (secureStoredPath != null) {
			return secureStoredPath;
		}

		String compareString = fileName.split("_")[0];
		String fileWithFullPath = null;
		logger.info(" fileName= " + compareString);

		switch (compareString) {

		case "technical":
			fileWithFullPath = documentRootPath + workTechSanctionDocumentPath + fileName;
			break;

		case "Administrator":

			fileWithFullPath = documentRootPath + workASSanctionDocumentPath + fileName;
			break;

		case "Tender":

			fileWithFullPath = documentRootPath + workTenderSanctionDocumentPath + fileName;
			break;

		case "Work":

			fileWithFullPath = documentRootPath + workWorkProgressDocumentPath + fileName;
			break;

		case "CC":

			fileWithFullPath = documentRootPath + CCDocumentPath + fileName;
			break;
		case "DM":

			fileWithFullPath = documentRootPath + dmAttachment + fileName;
			break;
		default:
			break;
		}

		logger.info(" fileWithFullPath = " + fileWithFullPath);
		return fileWithFullPath;
	}

	@Override
	public String fetchDownloadFileNameRevised(Long documentId) {
		logger.info("fetchDownloadFileName ");
		DocumentUpload document = documentRepository.findById(documentId).orElse(null);
		if (document == null || document.getDocumentName() == null) {
			return null;
		}
		String fileName = document.getDocumentName();
		String secureStoredPath = locateStoredDocument(fileName, document.getWorkId(),
				List.of(workRevisedTechSanctionDocumentPath, workASRevisedSanctionDocumentPath));
		if (secureStoredPath != null) {
			return secureStoredPath;
		}
		String compareString = fileName.split("_")[0];
		String fileWithFullPath = null;
		logger.info(" fileName= " + compareString);

		switch (compareString) {

		case "technical":
			fileWithFullPath = documentRootPath + workRevisedTechSanctionDocumentPath + fileName;
			break;

		case "Administrator":

			fileWithFullPath = documentRootPath + workASRevisedSanctionDocumentPath + fileName;
			break;

		default:
			break;
		}

		logger.info(" fileWithFullPath = " + fileWithFullPath);
		return fileWithFullPath;
	}

	private String locateStoredDocument(String storedName, Long workId, List<String> relativeDirectories) {
		if (storedName == null || storedName.isBlank() || storedName.contains("/") || storedName.contains("\\")) {
			logger.warn("Rejected unsafe stored document name: {}", storedName);
			return null;
		}
		Path root = Paths.get(documentRootPath).toAbsolutePath().normalize();
		for (String relativeDirectory : relativeDirectories) {
			Path directory = root.resolve(relativeDirectory).normalize();
			if (!directory.startsWith(root)) {
				continue;
			}
			Path flatCandidate = directory.resolve(storedName).normalize();
			if (flatCandidate.getParent().equals(directory) && Files.isRegularFile(flatCandidate)) {
				return flatCandidate.toString();
			}
			if (workId != null) {
				Path workDirectory = directory.resolve(String.valueOf(workId)).normalize();
				Path workCandidate = workDirectory.resolve(storedName).normalize();
				if (workDirectory.startsWith(root) && workCandidate.getParent().equals(workDirectory)
						&& Files.isRegularFile(workCandidate)) {
					return workCandidate.toString();
				}
			}
		}
		return null;
	}

	@Override
	public String fetchDownloadDocumentWSPro(Long documentId) {
		logger.info("fetchDownloadFileName ");
		DocumentUploadWorkProgress document = documentUploadWorkProgressRepository.findById(documentId).orElse(null);
		if (document == null) {
			logger.error("No document found for documentId: " + documentId);
			return null;
		}
		String fileName = document.getDocumentName();
		if (fileName == null || fileName.trim().isEmpty()) {
			logger.error("Document name is null or empty for documentId: " + documentId);
			return null;
		}

		String compareString = fileName.split("_")[0];
		String fileWithFullPath = null;
		logger.info(" fileName= " + compareString);

		switch (compareString) {

		case "Work":

			fileWithFullPath = documentRootPath + workWorkProgressDocumentPath + fileName;
			break;

		default:
			// Fallback: try workWorkProgressDocumentPath for any unrecognised prefix
			logger.warn("Unrecognised file prefix '" + compareString + "' for documentId: " + documentId + ", using workprogress path as fallback");
			fileWithFullPath = documentRootPath + workWorkProgressDocumentPath + fileName;
			break;
		}

		logger.info(" fileWithFullPath = " + fileWithFullPath);
		return fileWithFullPath;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	synchronized public ResponseObject addCCDetails(CCBean ccBean) throws Exception {
		ResponseObject responseObject = null;

		try {
			if (ccBean != null) {
				CC entity = null;

				if (null != ccBean.getWorkId()) {
					entity = ccRepository.findByWorkId(ccBean.getWorkId());
					if (entity != null) {

					} else {
						entity = new CC();
					}

				} else {
					entity = new CC();
				}

				responseObject = new ResponseObject();

				Work work = workRepository.findById(ccBean.getWorkId()).orElse(null);

				TSASWork tsasWork = tsasWorkRepository.findByWorkId(ccBean.getWorkId());
				WorkTender tender = workTenderRepository.findByWorkId(ccBean.getWorkId());
				WorkProgress workProgress = workProgressRepository.findByWorkId(ccBean.getWorkId());
				String workNo = null;

				if (!StringUtils.isEmpty(ccBean.getStatus())) {
					entity.setStatus(ccBean.getStatus());
				} else {
					entity.setStatus(DMSConstants.STATUS_ACTIVE);
				}
				if (ccBean.getUploadCC() != null) {
					DocumentUpload documentUpload = DMSUtil.uploadCCDocument(documentRootPath + CCDocumentPath, "blank",
							ccBean.getUploadCC(), null, "blank");

					documentRepository.save(documentUpload);
					entity.setDocumentUploadCC(documentUpload);

				}

				convertCCBeanToEntity(entity, ccBean);

				entity = ccRepository.save(entity);

				if (entity != null) {
					WorkStatus workStatus = workStatusRepository.findById(ccBean.getWorkStatusId()).orElse(null);
					if (workStatus != null) {
						entity.setWorkStatusId(workStatus.getId());
						work.setWorkStatus(workStatus.getId());
						if (tsasWork != null) {
							tsasWork.setWorkStatus(workStatus.getId());
						}
						if (tender != null) {
							tender.setWorkStatus(workStatus.getId());
						}
						if (workProgress != null) {
							workProgress.setWorkStatusId(workStatus.getId());
						}
					}
					responseObject.setId(entity.getWork().getId());
				}

			}
			return responseObject;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			throw new Exception(DMSConstants.ERROR_SAVING_DATA);
		}
	}

	private void convertCCBeanToEntity(CC cc, CCBean ccBean) throws ParseException {

		cc.setWork(workRepository.findById(ccBean.getWorkId()).orElse(null));
		if (ccBean.getCcNo() == null || ccBean.getCcNo().isEmpty()) {

		} else {
			cc.setCcNo(ccBean.getCcNo());
		}

		if (ccBean.getPaymentStatus() != null) {
			cc.setPaymentStatus(ccBean.getPaymentStatus());
		}

		cc.setCcDate(ccBean.getCcDate());
		cc.setRemarks(ccBean.getRemarks());
		cc.setWorkRequestStatusId(ccBean.getWorkRequestStatusId());
		cc.setDateHandOver(ccBean.getDateHandOver());
		cc.setHandoverRemarks(ccBean.getHandoverRemarks());

	}

	@Override
	public List<OtherDocListBean> fetchDefaultDocList() {
		OtherDocListBean otherDocListBean = new OtherDocListBean();
		List<OtherDocListBean> othDocList = new ArrayList<OtherDocListBean>();
		/*
		 * otherDocListBean.setNoOfDocs(Long.valueOf("0"));
		 * otherDocListBean.setRemarks("");
		 */
		othDocList.add(otherDocListBean);
		return othDocList;
	}

	@Override
	public List<WorkProgressImageListBean> fetchWorkProgressImagesDataList() {
		WorkProgressImageListBean workProgressImageListBean = new WorkProgressImageListBean();
		List<WorkProgressImageListBean> workProgressImageListBeans = new ArrayList<WorkProgressImageListBean>();

		workProgressImageListBeans.add(workProgressImageListBean);
		return workProgressImageListBeans;
	}

	@Override
	public String deleteFile(Long id) {

		try {
			WorkDocument entity = workDocumentRepository.findById(id).orElse(null);
			if (entity != null) {
				entity.setIsDeleted(DMSConstants.ENABLED);
				workDocumentRepository.save(entity);
			}
			return null;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return DMSConstants.ERROR_DELETING_DATA;
		}
	}

	@Override
	public List<LCBean> fetchLCsByDistrictId(Long districtId) {
		try {

			District district = districtRepository.findByDistrictIdAndEnabled(districtId, DMSConstants.ENABLED);

			List<LegislativeConstituency> list = legislativeConsRepository.findByDistrictAndEnabled(district,
					(short) 1);

			List<LCBean> beanList = new ArrayList<>();

			for (LegislativeConstituency lc : list) {
				beanList.add(convertLCEntityToBean(lc));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred in fetchLCsByDistrictName Method", e);
			return null;
		}
	}

	@Override
	public long isUserByEmailIdExists(String emailId) {
		long id = 0L;

		Users users = userRepository.findByEmailId(emailId);
		if (null != users) {
			id = users.getId();
		}

		return id;
	}

	@Override
	public void saveOrUpdateOtp(String emailIdOrMobileNo, Integer otp) {

		OtpGeneration otpGeneration = otpGenerationRepository.findById(emailIdOrMobileNo).orElse(null);
		if (null != otpGeneration) {
			otpGeneration.setCreatedDate(new Date());
			otpGeneration.setOtp(otp);
		} else {
			otpGeneration = new OtpGeneration();
			otpGeneration.setCreatedDate(new Date());
			otpGeneration.setOtp(otp);
			otpGeneration.setEmailIdMobileNo(emailIdOrMobileNo);
		}

		otpGenerationRepository.saveAndFlush(otpGeneration);
	}

	@Override
	public Integer getOtpByEmailIdMobileNo(String emailIdOrMobileNo) {

		Integer otp = null;

		OtpGeneration otpGeneration = otpGenerationRepository.findById(emailIdOrMobileNo).orElse(null);
		if (null != otpGeneration) {
			otp = otpGeneration.getOtp();
		}

		return otp;
	}

	@Override
	public List<WorkStatusBean> fetchWorkStatusByScheme(Long schemeId, Long statusId) {
		try {
			List<WorkStatus> list = workStatusRepository
					.findBySchemesAndEnabled(schemeRepository.findById(schemeId).orElse(null), DMSConstants.ENABLED);

			List<WorkStatusBean> beanList = new ArrayList<>();
			for (WorkStatus workStatus : list) {
				if (workStatus.getId() >= statusId) {
					beanList.add(convertWorkStatusEntityToBean(workStatus));
				}
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	@Override
	public List<DistrictBean> fetchDistrictsByDivision(Long divisionId) {

		try {
			List<District> list = districtRepository.findByEnabledAndDivisionDivisionId((short) 1, divisionId);

			List<DistrictBean> beanList = new ArrayList<>();
			for (District district : list) {
				beanList.add(convertDistrictEntityToBean(district));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	private Integer updateAsGeneratedCount2(String financialYear) {
		Integer lastCount = 0;

		AsGeneratedCount asGeneratedCount = asGeneratedCountRepository.findByFinancialYear(financialYear);
		if (asGeneratedCount != null) {
			lastCount = asGeneratedCount.getLastCount() - 1;
			asGeneratedCount.setLastCount(lastCount);
			asGeneratedCount.setFinancialYear(financialYear);
			asGeneratedCountRepository.save(asGeneratedCount);
		} else {
			lastCount = 1;
			asGeneratedCount = new AsGeneratedCount();
			asGeneratedCount.setFinancialYear(financialYear);
			asGeneratedCount.setLastCount(lastCount);
			asGeneratedCountRepository.save(asGeneratedCount);
		}
		return lastCount;
	}

	private Integer updateAsGeneratedCount(String financialYear) {
		Integer lastCount = 0;

		AsGeneratedCount asGeneratedCount = asGeneratedCountRepository.findByFinancialYear(financialYear);
		if (asGeneratedCount != null) {
			lastCount = asGeneratedCount.getLastCount() + 1;
			asGeneratedCount.setLastCount(lastCount);
			asGeneratedCount.setFinancialYear(financialYear);
			asGeneratedCountRepository.save(asGeneratedCount);
		} else {
			lastCount = 1;
			asGeneratedCount = new AsGeneratedCount();
			asGeneratedCount.setFinancialYear(financialYear);
			asGeneratedCount.setLastCount(lastCount);
			asGeneratedCountRepository.save(asGeneratedCount);
		}
		return lastCount;
	}

	private Long fetchAsGeneratedCount(String financialYear) {

		AsGeneratedCount asGeneratedCount = asGeneratedCountRepository.findByFinancialYear(financialYear);

		if (asGeneratedCount == null) {
			asGeneratedCount = new AsGeneratedCount();
			asGeneratedCount.setFinancialYear(financialYear);
			asGeneratedCount.setLastCount(1);
			;
			asGeneratedCountRepository.save(asGeneratedCount);
		}
		long count = asGeneratedCount.getLastCount() + 1L;

		Long asUniqueDispatchNo = count;

		return asUniqueDispatchNo;
	}

	@Override
	public ResponseObject uploadWorkProgressImages(WorkProgressImageListBean workProgressImageListBean)
			throws Exception {

		ResponseObject responseObject = null;
		try {
			if (workProgressImageListBean != null) {

				Work work = workRepository.findById(workProgressImageListBean.getId()).orElse(null);
				if (work != null) {
					// List<DocumentUpload> documentUploads =
					// documentRepository.findByWorkIdAndIsDeleted(work.getId(),(short) 0);

					DocumentUpload documentUploads = null;

					if (workProgressImageListBean != null) {

						documentUploads = documentRepository.findByWorkId(work.getId());

						saveWorkProgressImages(work, workProgressImageListBean, documentUploads, "WP");// AS
																										// Document
																										// }

					}

					responseObject = new ResponseObject();
					responseObject.setId(work.getId());
				}
			}
			return responseObject;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			throw new Exception(DMSConstants.ERROR_SAVING_DATA);
		}
	}

	@Override
	public ResponseObject uploadedDrawingFiles(DocumentUploadDrawingDetailBean uploadDrawingDetailBean)
			throws Exception {

		ResponseObject responseObject = null;
		try {
			if (uploadDrawingDetailBean != null) {

				Work work = workRepository.findById(uploadDrawingDetailBean.getWorkId()).orElse(null);
				if (work != null) {
					// List<DocumentUpload> documentUploads =
					// documentRepository.findByWorkIdAndIsDeleted(work.getId(),(short) 0);

					DocumentUploadDrawingDetail documentUploadDrawingDetail = null;

					if (uploadDrawingDetailBean != null) {
						// documentUploadDrawingDetail=new DocumentUploadDrawingDetail();
						// documentUploadDrawingDetail =
						// documentUploadDrawingDetailRepository.findByWorkId(work.getId(), (short) 0);

						saveDocumentUploadDrawing(work, uploadDrawingDetailBean, documentUploadDrawingDetail);

					}

					responseObject = new ResponseObject();
					responseObject.setId(work.getId());
				}
			}
			return responseObject;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			throw new Exception(DMSConstants.ERROR_SAVING_DATA);
		}
	}

	private void saveWorkProgressImages(Work work, WorkProgressImageListBean bean, DocumentUpload documentExistUploads,
			String fileType) throws DMSBusinessException {

		if (bean.getFileArr0() != null) {
			String fileName = DMSUtil.saveFile(documentRootPath + workWorkProgressDocumentPath,
					String.valueOf(work.getId()), bean.getFileArr0());
			DocumentUpload documentUpload = null;
			if (documentExistUploads != null) {
				documentUpload = documentExistUploads;
			} else {
				documentUpload = new DocumentUpload(); // }
			}
			documentUpload.setTypeWork(fileType);
			documentUpload.setDocumentName(fileName);
			documentUpload.setWorkId(bean.getId());
			// documentUpload.setIsDeleted((short) 0);
			documentRepository.save(documentUpload);
		}

		if (bean.getFileArr1() != null) {
			String fileName = DMSUtil.saveFile(documentRootPath + workWorkProgressDocumentPath,
					String.valueOf(work.getId()), bean.getFileArr1());

			DocumentUpload documentUpload = null;
			if (documentExistUploads != null) {
				documentUpload = documentExistUploads;
			} else {
				documentUpload = new DocumentUpload(); // }
			}
			documentUpload.setTypeWork(fileType);
			documentUpload.setDocumentName(fileName);
			documentUpload.setWorkId(bean.getId());
			// documentUpload.setIsDeleted((short) 0);
			documentRepository.save(documentUpload);
		}

		if (bean.getFileArr2() != null) {
			String fileName = DMSUtil.saveFile(documentRootPath + workWorkProgressDocumentPath,
					String.valueOf(work.getId()), bean.getFileArr2());
			DocumentUpload documentUpload = null;
			if (documentExistUploads != null) {
				documentUpload = documentExistUploads;
			} else {
				documentUpload = new DocumentUpload(); // }
			}
			documentUpload.setTypeWork(fileType);
			documentUpload.setDocumentName(fileName);
			documentUpload.setWorkId(bean.getId());
			// documentUpload.setIsDeleted((short) 0);
			documentRepository.save(documentUpload);
		}

		if (bean.getFileArr3() != null) {
			String fileName = DMSUtil.saveFile(documentRootPath + workWorkProgressDocumentPath,
					String.valueOf(work.getId()), bean.getFileArr3());
			DocumentUpload documentUpload = null;
			if (documentExistUploads != null) {
				documentUpload = documentExistUploads;
			} else {
				documentUpload = new DocumentUpload(); // }
			}
			documentUpload.setTypeWork(fileType);
			documentUpload.setDocumentName(fileName);
			documentUpload.setWorkId(bean.getId());
			// documentUpload.setIsDeleted((short) 0);
			documentRepository.save(documentUpload);
		}

		if (bean.getFileArr4() != null) {
			String fileName = DMSUtil.saveFile(documentRootPath + workWorkProgressDocumentPath,
					String.valueOf(work.getId()), bean.getFileArr4());
			DocumentUpload documentUpload = null;
			if (documentExistUploads != null) {
				documentUpload = documentExistUploads;
			} else {
				documentUpload = new DocumentUpload(); // }
			}
			documentUpload.setTypeWork(fileType);
			documentUpload.setDocumentName(fileName);
			documentUpload.setWorkId(bean.getId());
			// documentUpload.setIsDeleted((short) 0);
			documentRepository.save(documentUpload);
		}

	}

	private void saveDocumentUploadDrawing(Work work, DocumentUploadDrawingDetailBean bean,
			DocumentUploadDrawingDetail documentExistUploads) throws DMSBusinessException {
		String fileName = DMSUtil.saveDrawingFile(documentRootPath + drawigUpoadDocumentPath,
				String.valueOf(work.getId()), bean.getDrawingFile());
		DocumentUploadDrawingDetail documentUpload = null;

		DocumentUploadDrawingDetail byWorkId = documentUploadDrawingDetailRepository.findByWorkId(bean.getWorkId());
		if (byWorkId != null) {
			documentUpload = byWorkId;
		} else {
			documentUpload = new DocumentUploadDrawingDetail(); // }
		}

		documentUpload.setDocumentName(fileName);
		documentUpload.setWorkId(work.getId());
		documentUpload.setStatus("Approved");
		documentUploadDrawingDetailRepository.save(documentUpload);

	}

	/// Generate Dynamic PPT BY Yousra Shafiq
	public byte[] generatePresentation(List<SlideData> slideDataList) throws IOException {

		try (XMLSlideShow presentation = new XMLSlideShow();
				ByteArrayOutputStream outStream = new ByteArrayOutputStream()) {

			int slideWidth = 3200;
			int slideHeight = 2800;

			LocalDate currentDate = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			String currentDateStr = currentDate.format(formatter);

			XSLFSlide firstSlide = presentation.createSlide();

			// Slide content
			String text1Part1 = "??? ????? ????, ??? ????????? ??? ?????? ?????? ????? ??????";
			String text1Part2 = "         ??????? ?????? ?? ??????? ???? ?????? " + currentDateStr;

			XSLFTextBox titleShape = firstSlide.createTextBox();
			titleShape.setText(text1Part1 + "\n" + text1Part2);

			double fontSize = 16d;
			double text1Width = (text1Part1.length() + text1Part2.length()) * fontSize * 1.6; // Adjust the factor as
																								// needed
			double x1 = (slideWidth - text1Width) / 2;
			double y1 = 200.0;

			titleShape.setAnchor(new Rectangle2D.Double(x1, y1, text1Width, 4d));

			XSLFTextParagraph titleParagraph = titleShape.addNewTextParagraph();
			XSLFTextRun titleRun = titleParagraph.addNewTextRun();
			titleRun.setFontSize(20.0);
			titleRun.setBold(true);
			titleRun.setFontFamily("Mangal");
			titleRun.setUnderlined(true);
			titleRun.setFontColor(new Color(0, 0, 255));
			titleParagraph.setLineSpacing(2.0);

			String text2 = "??? ????????? ??? ?????? ?????? ?????, ???? ?????? ";
			titleShape = firstSlide.createTextBox();
			titleShape.setText(text2);
			double text2Width = (text2.length()) * fontSize * 3.5;
			double x2 = (slideWidth - text2Width) / 2;
			double y2 = y1 + 100d;
			titleShape.setAnchor(new Rectangle2D.Double(x2, y2, text2Width, 30d));
			titleParagraph = titleShape.addNewTextParagraph();
			titleRun = titleParagraph.addNewTextRun();
			titleRun.setFontSize(20.0);
			titleRun.setUnderlined(true);
			titleRun.setFontColor(new Color(0, 0, 255));
			titleParagraph.setLineSpacing(2.0);

			// Process slide data
			for (SlideData slideData : slideDataList) {
				createTable(slideData.getTableData(), slideData.getHeaders(), slideData.getTitles(), slideData,
						presentation, 12, slideData.getSlideCount(), slideData.getSubHeaders(), slideData.getTotal());
			}

			// Write the presentation to the ByteArrayOutputStream
			presentation.write(outStream);

			// Return the byte array
			return outStream.toByteArray();

		} catch (IOException e) {
			// Log the exception
			logger.error("Error generating presentation", e);
			return new byte[0]; // Return an empty byte array in case of an error
		}
	}

	private void createTable(List<Object[]> tableData, List<String> headers, List<String> titles, SlideData slideData,
			XMLSlideShow presentation, int maxRowsPerPage, int slideCount, List<String> parentHeaders,
			List<Double> totals) {

		String financialYear = financialYearRepository.findMostRecentFinancialYear();

		int numRows = tableData.size();
		int currentPage = 0;
		int remainingRows = numRows;
		int slideWidth = 4000;
		int slideHeight = 3600;
		int tableX = 10;
		int tableY = 50;

		if (tableData.size() > 0) {
			int numCols = tableData.get(0).length;

			XSLFSlide newSlide = null;
			while (remainingRows > 0) {
				newSlide = presentation.createSlide();

				XSLFTable table = newSlide.createTable(maxRowsPerPage + 1, numCols);
				table.setAnchor(new Rectangle(tableX, tableY, slideWidth - 1 * tableX, slideHeight - tableY - 20));

				XSLFTextShape titleShape = newSlide.createTextBox();
				titleShape.setText(slideData.getTitle());

				titleShape.setAnchor(new Rectangle(tableX, tableY - 40, slideWidth - 1 * tableX, 20));
				XSLFTextParagraph titleParagraph = titleShape.addNewTextParagraph();
				XSLFTextRun titleRun = titleParagraph.addNewTextRun();
				titleRun.setBold(true);
				titleRun.setFontSize(16d);
				titleRun.setFontColor(Color.BLACK);
				XSLFTableCell cell1 = null;
				XSLFTextParagraph paragraph1;
				XSLFTextRun run1;
				if (slideCount == 1) {

					table.mergeCells(0, 0, 0, 0);
					cell1 = table.getCell(0, 0);
					paragraph1 = cell1.addNewTextParagraph();
					run1 = paragraph1.addNewTextRun();
					setAllCellBorders(cell1, Color.BLACK);

					run1.setText("");

					table.mergeCells(0, 0, 1, 2);
					cell1 = table.getCell(0, 1);
					paragraph1 = cell1.addNewTextParagraph();
					run1 = paragraph1.addNewTextRun();
					setAllCellBorders(cell1, Color.BLACK);
					run1.setFontSize(14d);
					run1.setText("?????? 01.04.23 ?? ?????? ??? ??? ?????");
					run1.setBold(true);

					table.mergeCells(0, 0, 3, 4);
					cell1 = table.getCell(0, 3);
					paragraph1 = cell1.addNewTextParagraph();
					run1 = paragraph1.addNewTextRun();
					setAllCellBorders(cell1, Color.BLACK);
					run1.setFontSize(14d);
					run1.setText("???? " + financialYear + " ???? ??????? ?????");
					run1.setBold(true);

					table.mergeCells(0, 0, 5, 6);
					cell1 = table.getCell(0, 5);
					paragraph1 = cell1.addNewTextParagraph();
					run1 = paragraph1.addNewTextRun();
					setAllCellBorders(cell1, Color.BLACK);
					run1.setFontSize(14d);
					run1.setText("???? " + financialYear + " ???? ??? ?????");
					run1.setBold(true);
				} else if (slideCount == 2) {

					table.mergeCells(0, 0, 0, 0);
					cell1 = table.getCell(0, 0);
					paragraph1 = cell1.addNewTextParagraph();
					run1 = paragraph1.addNewTextRun();
					setAllCellBorders(cell1, Color.BLACK);
					run1.setFontSize(14d);
					run1.setText("");

					table.mergeCells(0, 0, 1, 2);
					cell1 = table.getCell(0, 1);
					paragraph1 = cell1.addNewTextParagraph();
					run1 = paragraph1.addNewTextRun();
					setAllCellBorders(cell1, Color.BLACK);
					run1.setFontSize(14d);
					run1.setText("???? " + financialYear + " ??? ?????");
					run1.setBold(true);

					table.mergeCells(0, 0, 3, 4);
					cell1 = table.getCell(0, 3);
					paragraph1 = cell1.addNewTextParagraph();
					run1 = paragraph1.addNewTextRun();
					setAllCellBorders(cell1, Color.BLACK);
					run1.setFontSize(14d);
					run1.setText("???? " + financialYear + " ???? ??? ??????");
					run1.setBold(true);

					table.mergeCells(0, 0, 5, 6);
					cell1 = table.getCell(0, 5);
					paragraph1 = cell1.addNewTextParagraph();
					run1 = paragraph1.addNewTextRun();
					setAllCellBorders(cell1, Color.BLACK);
					run1.setFontSize(14d);
					run1.setText("??????? ??? ?? ?? ?????? ");
					run1.setBold(true);

					table.mergeCells(0, 0, 7, 8);
					cell1 = table.getCell(0, 7);
					paragraph1 = cell1.addNewTextParagraph();
					run1 = paragraph1.addNewTextRun();
					setAllCellBorders(cell1, Color.BLACK);
					run1.setFontSize(14d);
					run1.setText("??????? ??? ?? ?? ??????? ");
					run1.setBold(true);

				}

				XSLFTableCell cell = null;
				XSLFTextParagraph paragraph;
				XSLFTextRun run;
				BigDecimal[] columnTotals = new BigDecimal[numCols];
				for (int i = 0; i < numCols; i++) {
					columnTotals[i] = BigDecimal.ZERO; // Initialize all elements to zero
				}
				for (int col = 0; col < numCols; col++) {

					int index = 0;
					if (slideCount == 1 || slideCount == 2) {
						cell = table.getCell(1, col);
						index = 1;
					} else {
						cell = table.getCell(0, col);
						index = 0;
					}

					XSLFTextParagraph headerParagraph = cell.addNewTextParagraph();
					XSLFTextRun headerRun = headerParagraph.addNewTextRun();
					setAllCellBorders(cell, Color.BLACK);
					table.setColumnWidth(col, 70d);
					headerRun.setText(headers.get(col));
					headerRun.setFontSize(14d);
					headerRun.setBold(true);
					int dataIndex = 0;
					int insert = 2;
					for (int row = 0; row < tableData.size(); row++) {

						if (slideCount == 1 || slideCount == 2) {

							cell = table.getCell(insert, col);
							insert++;
						} else {
							cell = table.getCell(row + 1, col);
						}

						// index++;
						if (cell != null) {
							if (slideCount == 1) {
								table.setColumnWidth(col, 100d);

								// double cellValue = Double.parseDouble(String.valueOf(tableData.get(row)));
								// columnTotals[col] += cellValue;
							} else if (slideCount == 2) {
								table.setColumnWidth(col, 78d);
							} else if (slideCount == 3) {
								table.setColumnWidth(col, 230d);
							} else if (slideCount == 4) {
								table.setColumnWidth(col, 87d);
							} else if (slideCount == 5) {
								table.setColumnWidth(col, 140d);
							} else if (slideCount == 8) {
								table.setColumnWidth(col, 63d);
							} else if (slideCount == 9) {
								table.setColumnWidth(col, 58d);
							} else if (slideCount == 10) {
								table.setColumnWidth(col, 63d);
							} else if (slideCount == 11) {
								table.setColumnWidth(col, 58d);
							}
							paragraph = cell.addNewTextParagraph();
							run = paragraph.addNewTextRun();
							setAllCellBorders(cell, Color.BLACK);

							dataIndex = currentPage * maxRowsPerPage + row;

							if (dataIndex < tableData.size()) {
								String cellValueStr = String.valueOf(tableData.get(dataIndex)[col]);
								run.setText(String.valueOf(tableData.get(dataIndex)[col]));
								run.setFontSize(10d);

								try {
									BigDecimal cellValue = new BigDecimal(cellValueStr);
									BigDecimal formattedValue = cellValue.setScale(2, BigDecimal.ROUND_HALF_UP);
									columnTotals[col] = columnTotals[col].add(formattedValue);
								} catch (NumberFormatException e) {

								}
							}

						}

					}

					int size = 0;

					/*
					 * for (int col1 = 0; col1 < numCols; col1++) { columnTotals[col1] =
					 * columnTotals[col1].divide(new BigDecimal("100000"), 2,
					 * BigDecimal.ROUND_HALF_UP); }
					 */
					if (slideCount == 1 || slideCount == 2) {
						size = tableData.size() + 2;

					} else if (slideCount == 3 || slideCount == 4 || slideCount == 5) {
						size = tableData.size() + 1;
					} else if (slideCount == 9 || slideCount == 11) {

						if (remainingRows > 12) {

							size = dataIndex + 1;
						} else {
							size = remainingRows + 1;
						}

					} else {

						size = tableData.size() + 1;
					}

					logger.info("Number......" + size);
					cell = table.getCell(size, col);
					if (cell != null) {
						XSLFTextParagraph totalParagraph = cell.addNewTextParagraph();
						XSLFTextRun totalRun = totalParagraph.addNewTextRun();
						setAllCellBorders(cell, Color.BLACK);
						if (col == 0) {
							totalRun.setText("Grand Total");
						} else {
							totalRun.setText(String.valueOf(columnTotals[col]));

						}

						totalRun.setFontSize(12d);
						totalRun.setBold(true);
					}

				}

				currentPage++;
				remainingRows -= maxRowsPerPage;

			}

		} else if (slideCount == 6) {
			XSLFSlide newSlide = null;

			newSlide = presentation.createSlide();
			XSLFTextBox titleShape = newSlide.createTextBox();

			double fontSize = 10d;
			double text1Width = (slideData.getTitle().length()) * fontSize * 6.5;// Adjust the factor as
			double x1 = (slideWidth - text1Width) / 2;
			double y1 = 250.0;
			titleShape.setAnchor(new Rectangle2D.Double(x1, y1, text1Width, 4d));
			titleShape.setText(slideData.getTitle());
			XSLFTextParagraph titleParagraph = titleShape.addNewTextParagraph();
			XSLFTextRun titleRun = titleParagraph.addNewTextRun();

			titleRun.setBold(true);
			titleRun.setFontColor(new Color(0, 0, 255));
			titleRun.setUnderlined(true);

		} else if (slideCount == 7) {
			XSLFSlide newSlide = null;
			newSlide = presentation.createSlide();

			XSLFTextBox titleShape = newSlide.createTextBox();

			double fontSize = 20d;

			double text1Width = (slideData.getTitle().length()) * fontSize * 17.0;// Adjust the factor as
			double x1 = (slideWidth - text1Width) / 2;
			double y1 = 250.0;

			titleShape.setAnchor(new Rectangle2D.Double(x1, y1, text1Width, 4d));
			titleShape.setText(slideData.getTitle());
			XSLFTextParagraph titleParagraph = titleShape.addNewTextParagraph();
			XSLFTextRun titleRun = titleParagraph.addNewTextRun();

			titleRun.setBold(true);
			titleRun.setFontColor(Color.BLACK);
			titleRun.setUnderlined(true);

		}

	}

	static void setAllCellBorders(XSLFTableCell cell, Color color) {
		cell.setBorderColor(BorderEdge.top, color);
		cell.setBorderColor(BorderEdge.right, color);
		cell.setBorderColor(BorderEdge.bottom, color);
		cell.setBorderColor(BorderEdge.left, color);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public String addSdr(WorkSubDelayResonBean bean, String userName, String dateString) {

		try {

			WorkSubDelayReson entity = workSubDelayResonRepository
					.findBySubDelayReasonAndEnabled(bean.getSubDelayReason(), (short) 1);
			if (null != entity) {
				return "Scheme with given Name Already exist!";
			} else {
				entity = new WorkSubDelayReson();
				convertWorkSubDelayResonBeanToEntity(entity, bean);
				entity.setCreatedBy(userName);
				entity.setCreatedDate(dateString);

				workSubDelayResonRepository.save(entity);

				return null;
			}
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return DMSConstants.ERROR_SAVING_DATA;
		}
	}

	private void convertWorkSubDelayResonBeanToEntity(WorkSubDelayReson entity, WorkSubDelayResonBean bean) {

		if (entity != null && bean != null) {
			entity.setWorkSubStatusId(new WorkSubStatus(bean.getWorkSubStatusId()));
			entity.setSubDelayReason(bean.getSubDelayReason());
			entity.setEnabled((short) 1);
			// entity.setCreatedBy(bean.getCreatedBy());
			// entity.setCreatedDate(bean.getCreatedDate());
		}

	}

	@Override
	public SdrJson getAllSdr(Pageable pageable, String searchParameter) {

		SdrJson sdrjson = null;

		try {
			Page<WorkSubDelayReson> sdr = null;

			if (!StringUtils.isEmpty(searchParameter))
				sdr = workSubDelayResonRepository.findBySubDelayReasonContainingAndEnabled(pageable, searchParameter,
						(short) 1);
			else
				sdr = workSubDelayResonRepository.findByEnabled((short) 1, pageable);

			if (sdr != null) {
				List<WorkSubDelayReson> entityList = sdr.getContent();
				List<WorkSubDelayResonBean> beanList = new ArrayList<>();
				if (entityList != null && !entityList.isEmpty()) {

					int index = pageable.getPageNumber() * pageable.getPageSize();
					for (WorkSubDelayReson sdrs : entityList) {

						WorkSubDelayResonBean bean = convertWorkSubDelayResonEntityToBean(sdrs);
						bean.setIndex(++index);
						beanList.add(bean);
					}
				}
				sdrjson = new SdrJson();
				sdrjson.setiTotalDisplayRecords(sdr.getTotalElements());
				sdrjson.setiTotalRecords(schemeRepository.countByEnabled((short) 1));
				sdrjson.setAaData(beanList);
			}
			return sdrjson;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return sdrjson;
		}
	}

	private WorkSubDelayResonBean convertWorkSubDelayResonEntityToBean(WorkSubDelayReson entity) {

		WorkSubDelayResonBean bean = new WorkSubDelayResonBean();

		if (entity != null) {
			bean.setWorkSubStatusId(entity.getWorkSubDelayReasonId());
			bean.setWorkSubDelayReasonId(entity.getWorkSubDelayReasonId());
			bean.setEnabled(entity.getEnabled());
			bean.setCreatedBy(entity.getCreatedBy());
			bean.setCreatedDate(entity.getCreatedDate());
			bean.setSubDelayReason(entity.getSubDelayReason());
		}
		return bean;
	}

	@Override
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public String deleteSdr(Long id) {

		try {

			WorkSubDelayReson entity = workSubDelayResonRepository.findById(id).orElse(null);
			if (entity != null) {
				WorkProgress progress = workProgressRepository
						.findByWorkSubDelayReasonId(entity.getWorkSubDelayReasonId());
//				WorkTender work = workTenderRepository.findBySorYearContainingAndStatusNotIn(entity.getSubDelayReason(),
//						DMSConstants.STATUS_DELETED);
				if (progress != null) {
					Work wentityWork = workRepository.findById(progress.getWork().getId()).orElse(null);
					wentityWork = workRepository.findByIdAndStatusNotIn(progress.getWork().getId(),
							DMSConstants.STATUS_DELETED);

					if (wentityWork != null) {
						return "With this sub delay reason already created work, So cant be able to delete it!";
					}
				} else {
					entity.setEnabled((short) 0);
					workSubDelayResonRepository.save(entity);
					// workSubDelayResonRepository.save(entity);
				}

			}
			return null;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return DMSConstants.ERROR_DELETING_DATA;
		}
	}

	@Override
	public WorkSubDelayResonBean fetchSdrDetails(long id) {
		try {
			WorkSubDelayReson entity = workSubDelayResonRepository.findById(id).orElse(null);

			return convertWorkSubDelayResonToBean(entity);
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	private WorkSubDelayResonBean convertWorkSubDelayResonToBean(WorkSubDelayReson entity) {

		WorkSubDelayResonBean bean = new WorkSubDelayResonBean();

		if (entity != null) {
			bean.setWorkSubDelayReasonId(entity.getWorkSubDelayReasonId());
			bean.setWorkSubStatusId(entity.getWorkSubStatusId().getWorkSubStatusId());
			bean.setSubDelayReason(entity.getSubDelayReason());
			bean.setEnabled(entity.getEnabled());
			bean.setCreatedBy(entity.getCreatedBy());
			bean.setCreatedDate(entity.getCreatedDate());

		}
		return bean;
	}

	@Override
	@PreAuthorize("hasRole('ROLE_SYSTEM_ADMIN')")
	public String editSdr(WorkSubDelayResonBean bean, String date) {

		try {
			WorkSubDelayReson entity = workSubDelayResonRepository.findById(bean.getWorkSubDelayReasonId())
					.orElse(null);
			if (!entity.getSubDelayReason().equals(bean.getSubDelayReason())) {// if Name has changed
				// check whether already exist
				WorkSubDelayReson workSubDelayReson = workSubDelayResonRepository
						.findBySubDelayReasonAndEnabled(bean.getSubDelayReason(), (short) 1);
				if (workSubDelayReson != null) {
					// already present
					return "Sub Delay Reason with given Name Already exist!";
				}
			}
			convertSdrBeanToEntity(entity, bean);
			entity.setCreatedBy(bean.getCreatedBy());
			entity.setCreatedDate(date);
			workSubDelayResonRepository.save(entity);

			return null;

		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return DMSConstants.ERROR_SAVING_DATA;
		}
	}

	public void convertSdrBeanToEntity(WorkSubDelayReson entity, WorkSubDelayResonBean bean) {

		if (entity != null && bean != null) {

			// entity.setId(null);
			entity.setSubDelayReason(bean.getSubDelayReason());
			/*
			 * entity.setCreatedBy(bean.getCreatedBy());
			 * entity.setCreatedDate(bean.getCreatedDate());
			 */
			entity.setEnabled((short) 1);

		}
	}

	@Override
	public List<WorkSubDelayResonBean> fetchSubDelayReasonByWorkSubStatusId(long workSubStatusId) {

		try {

			List<WorkSubDelayReson> list = new ArrayList<>();
			WorkSubStatus ws = new WorkSubStatus();
			ws.setWorkSubStatusId(workSubStatusId);
			// list =
			// workSubStatusRepository.findByEnabledAndWorkStatusId(DMSConstants.ENABLED,
			// workStatusId);
			list = workSubDelayResonRepository.findByEnabledAndWorkSubStatusId(DMSConstants.ENABLED, ws);

			List<WorkSubDelayResonBean> beanList = new ArrayList<>();
			for (WorkSubDelayReson entity : list) {
				beanList.add(convertWorkSubDelayResonEntityToBean(entity));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred in fetchWorkSubReasonOfDelay.", e);
			return null;
		}

	}

//Add by Sumit
	@Override
	public List<WorkCategoryBean> fetchWorkCategoryByWorkTypes() {
		try {
			// List<WorkCategory> list =
			// workCategoryRepository.findByWorkTypeAndEnabledOrderByWorkCategoryNameE(
			// workTypeRepository.findById(workTypeId).orElse(null), DMSConstants.ENABLED);

			List<WorkCategory> list = workCategoryRepository.findByEnabledOrderByOrdering(DMSConstants.ENABLED);

			List<WorkCategoryBean> beanList = new ArrayList<>();
			for (WorkCategory workCategory : list) {
				beanList.add(convertWorkCategoryEntityToBean(workCategory));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	@Override
	public List<ImplAgencyBean> fetchConstructionAgency() {

		try {
			List<ImplementationAgency> agencies = implAgencyRepository.findByEnabled((short) 1);

			List<ImplAgencyBean> beanList = new ArrayList<>();
			for (ImplementationAgency agency : agencies) {
				beanList.add(convertAgencyEntityToBean(agency));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}

	}

	private ImplAgencyBean convertAgencyEntityToBean(ImplementationAgency implementationAgency) {

		ImplAgencyBean bean = new ImplAgencyBean();

		if (implementationAgency != null) {

			bean.setImplementationAgencyId(implementationAgency.getImplementationAgencyId());
			bean.setImplementationAgencyNameE(implementationAgency.getImplAgencyname());
		}
		return bean;
	}

	@Override
	public String fetchWorkFileNameAgreement(Long documentId) {
		DocumentUpload document = documentRepository.findById(documentId).orElse(null);
		String fileName = document.getDocumentName();
		String fileWithFullPath = documentRootPath + workTenderSanctionDocumentPath + fileName;
		return fileWithFullPath;
	}

	@Override
	public String fetchWorkFileNameLOI(Long documentId) {
		DocumentUpload document = documentRepository.findById(documentId).orElse(null);
		String fileName = document.getDocumentName();
		String fileWithFullPath = documentRootPath + workTenderSanctionDocumentPath + fileName;
		return fileWithFullPath;
	}

	@Override
	public Long countContractorsByWorkId(Long workId) {
		return contractorRepository.countByWorkId(workId);
	}

	@Override
	public List<WorkTenderBean> getWorkTenderEndDate() {
		try {
			int index = 1;
			List<WorkTender> list = workTenderRepository.findUpcomingWorkTenders();
			List<WorkTenderBean> beanList = new ArrayList<>();

			for (WorkTender workTender : list) {
				WorkTenderBean bean = (convertWorkTenderEntityToWorkTenderBean(workTender));
				bean.setIndex(index++);
				beanList.add(bean);
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}

	}

	@Override
	public WorkJson fetchWorksAaIssuedList(Pageable pageable, String workNo, String workName, String scheme,
			String workType, String financialYear, String implementationAgency, String blockId, String workStatus,
			String districtId, String divisionId, String searchByDivision, String workSubTypeId, String workStatusId,
			String workPriorityId, String financialHeadId, String vidhanSabhaId) {

		Integer workSubTypeIdInt = null;
		if (workSubTypeId != null) {
			try {
				workSubTypeIdInt = Integer.parseInt(workSubTypeId);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		WorkJson workJson = null;
		User user = DMSUtil.getUserDetail();

		Users userEntity = userRepository.findByUsernameAndStatus(user.getUsername(), DMSConstants.STATUS_ACTIVE);

		Collection<GrantedAuthority> role = user.getAuthorities();
		Long divisionCode = null;
		String districtCode = "";
		Long agency = null;
		ImplementationAgency implAgency = null;
		String r = role.toString();

		if (r.contains("ROLE_DEPARTMENT")) {
			Division division = userEntity.getDivision();
			divisionCode = division.getDivisionId();

		} else {
			divisionCode = null;

		}

		if (r.contains("ROLE_DISTRICT") || r.contains("ROLE_DEPT_DISTRICT")) {
			District district = userEntity.getDistrict();
			districtCode = district.getDistrictCode();
			implAgency = userEntity.getImplementationAgency();
		} else {
			districtCode = "";
			implAgency = null;
		}

		if (r.contains("ROLE_SAU")) {
			implAgency = userEntity.getImplementationAgency();
		} else {
			implAgency = null;
		}

		try {
			Page<Work> works = null;

			List<String> schemes = new ArrayList<String>();

			String blockName = null;
			if (blockId != null) {
				blockName = blockRepository.findById(Long.valueOf(blockId)).orElse(null).getBlockName();
			}
			Long divisionIds = null;
			if (divisionId != null) {
				divisionIds = Long.valueOf(divisionId);
//				divisionName = divisionRepository.findById(Long.valueOf(divisionId)).orElse(null).getDivisionName();
			}
			Long districtIds = null;
			if (districtId != null) {
				districtIds = Long.valueOf(districtId);
//				districtName = districtRepository.findById(Long.valueOf(districtId)).orElse(null).getDistrictName();
			}
			String gpName = null;

			String workStatusName = null;
			if (workStatusId != null) {
				workStatusName = workStatusRepository.findById(Long.valueOf(workStatusId)).orElse(null)
						.getWorkStatusNameE();
			}

			Long workTypeId = null;
			if (workType != null) {
				workTypeId = Long.valueOf(workType);
//				districtName = districtRepository.findById(Long.valueOf(districtId)).orElse(null).getDistrictName();
			}

			agency = null;
			if (implementationAgency != null) {
				agency = Long.valueOf(implementationAgency);
//					districtName = districtRepository.findById(Long.valueOf(districtId)).orElse(null).getDistrictName();
			}

			Long workPriority = null;
			if (workPriorityId != null) {
				workPriority = Long.valueOf(workPriorityId);
			}

			Long financialHead = null;
			if (financialHeadId != null) {
				financialHead = Long.valueOf(financialHeadId);
			}

			Long vidhanSabha = null;
			if (vidhanSabhaId != null) {
				vidhanSabha = Long.valueOf(vidhanSabhaId);
			}

			works = workRepository.findAllByStatusNotDeleted(pageable, workName, workTypeId, financialYear, districtIds,
					workStatusName, agency, workPriority, financialHead, vidhanSabha);

			// added by sumit
			// works = workRepository.findAll(pageable);

			if (works != null) {
				List<Work> entityList = works.getContent();
				List<WorkBean> beanList = new ArrayList<>();
				if (entityList != null && !entityList.isEmpty()) {

					int index = pageable.getPageNumber() * pageable.getPageSize();
					for (Work work : entityList) {

						WorkBean bean = convertWorkEntityToBean(work, "All");
						bean.setIndex(++index);
						beanList.add(bean);
					}
				}
				workJson = new WorkJson();
				workJson.setiTotalDisplayRecords(works.getTotalElements());
				workJson.setiTotalRecords(workRepository.countByStatusNotIn(DMSConstants.STATUS_DELETED));

				workJson.setAaData(beanList);
			}
			return workJson;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return workJson;
		}
	}

	@Override
	public Long fetchAgencyByuserId() {
		User user = DMSUtil.getUserDetail();
		Users users = userRepository.findByUsername(user.getUsername());
		Long agencyId = users.getImplementationAgency().getImplementationAgencyId();
		// Users userAgency = userRepository.findByImplementationAgency(userId);
		return agencyId;
	}

	@Override
	public ImplAgencyBean fetchConstructionAgencyById() {

		User user = DMSUtil.getUserDetail();
		Users users = userRepository.findByUsername(user.getUsername());
		Long agencyId = users.getImplementationAgency().getImplementationAgencyId();
		try {
			ImplementationAgency agencies = implAgencyRepository.findById(agencyId).orElse(null);
			ImplAgencyBean bean = new ImplAgencyBean();
			bean = convertAgencyEntityToBean(agencies);
			return bean;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}

	}

	public WorkBean convertWorkEntityToBeans1(Work work, String modeType) {

		WorkBean workBean = null;

		if (work != null) {

			workBean = new WorkBean();
			DocumentUploadDrawingDetail documentUploadDrawingDetail = documentUploadDrawingDetailRepository
					.findByWorkId(work.getId());
			if (documentUploadDrawingDetail != null) {
				workBean.setDrawingId(documentUploadDrawingDetail.getDocumentId());
				workBean.setFileStatus("1");
				workBean.setDrawingStatus(documentUploadDrawingDetail.getStatus());
			} else {
				workBean.setFileStatus("0");
			}

			// DocumentUpload documentUpload = documentRepository.findByDocumentId()
			if (work.getId() != null) {
				workBean.setId(work.getId());
			}
			if (work.getId() != null) {
				workBean.setWorkId(work.getId());
			}
			if (work.getWorkNo() != null) {
				workBean.setWorkNo(work.getWorkNo() != null ? work.getWorkNo() : "NA");
			}
			if (work.getWorkName() != null) {
				workBean.setWorkName(work.getWorkName() != null ? work.getWorkName() : "NA");
			}

			// workBean.setWorkNo(work.getWorkNo());
			if (work.getMultifundedStatus() != null) {
				workBean.setMultifundedStatus(work.getMultifundedStatus());
			}
			if (work.getFundByState() != null) {
				workBean.setFundByState(work.getFundByState());
			}
			if (work.getFundByEcrp2() != null) {
				workBean.setFundByEcrp2(work.getFundByEcrp2());
			}
			if (work.getFundByNhm() != null) {
				workBean.setFundByNhm(work.getFundByNhm());
			}
			if (work.getFundByOthers() != null) {
				workBean.setFundByOthers(work.getFundByOthers());
			}
			if (work.getWorkRequestStatusId() != null) {
				workBean.setWorkRequestStatusId(work.getWorkRequestStatusId());
			}
			if (work.getDrawCreatedBy() != null) {
				workBean.setDrawCreatedBy(work.getDrawCreatedBy());
			}
			if (work.getAllocatedAmount() != null) {
				workBean.setAllocatedAmount(work.getAllocatedAmount());
			}
			if (work.getWorkSubtypeId() != null && work.getWorkSubtypeId() <= 6) {
				workBean.setWorkSubTypeId(work.getWorkSubtypeId());
				workBean.setWorkSubTypeName(workSubTypeRepository.findById(work.getWorkSubtypeId()).orElse(null)
						.getWorkSubTypeNameE() != null
								? workSubTypeRepository.findById(work.getWorkSubtypeId()).orElse(null)
										.getWorkSubTypeNameE()
								: "NA");
			}

			// workBean.setWorkType(work.getWorkType());
			// workBean.setWorkTypeId(workTypeRepository.findByWorkTypeNameE(work.getWorkType()).getWorkTypeId());
			if (work.getFinancialYear() != null) {
				workBean.setFinancialYear(work.getFinancialYear());
				FinancialYear financialYear = financialYearRepository.findById(work.getFinancialYear()).orElse(null);
				if (financialYear != null) {
					workBean.setFinancialYearName(financialYear.getFinancialYear());
				}
			}
			if (work.getScheme() != null) {

				// Schemes schemes = schemeRepository.findBySchemeName(work.getScheme());
				Schemes schemes = schemeRepository.findById(work.getScheme()).orElse(null);
				if (schemes != null) {
					workBean.setScheme(schemes.getSchemeName() != null ? schemes.getSchemeName() : "NA");
					workBean.setSchemeId(schemes.getId());

				}
			}
			workBean.setProgressDocuments(buildProgressDocumentBeans(work.getId()));
			if (work.getSchemeState() != null) {

				Schemes schemesSate = schemeRepository.findById(work.getSchemeState()).orElse(null);
				if (schemesSate != null) {
					workBean.setSchemeState(schemesSate.getSchemeName() != null ? schemesSate.getSchemeName() : "NA");
					workBean.setSchemeStateId(schemesSate.getId());
				}

			}

			if (work.getSchemeNhm() != null) {

				Schemes schemesNhm = schemeRepository.findById(work.getSchemeNhm()).orElse(null);
				if (schemesNhm != null) {
					workBean.setSchemeNhm(schemesNhm.getSchemeName() != null ? schemesNhm.getSchemeName() : "NA");
					workBean.setSchemeNhmId(schemesNhm.getId());
				}

			}

			if (work.getSchemeEcrp2() != null) {

				Schemes schemesEcpr = schemeRepository.findById(work.getSchemeEcrp2()).orElse(null);
				if (schemesEcpr != null) {
					workBean.setSchemeEcpr2(schemesEcpr.getSchemeName() != null ? schemesEcpr.getSchemeName() : "NA");
					workBean.setSchemeEcpr2Id(schemesEcpr.getId());
				}

			}

			if (work.getSchemeOthers() != null) {

				Schemes schemesOthers = schemeRepository.findById(work.getSchemeOthers()).orElse(null);
				if (schemesOthers != null) {
					workBean.setSchemeOthers(
							schemesOthers.getSchemeName() != null ? schemesOthers.getSchemeName() : "NA");
					workBean.setSchemeOthersId(schemesOthers.getId());
				}
			}

			/*
			 * if (work.getWorkType() != null) { System.err.println("----- " +
			 * work.getWorkType()); WorkType workType =
			 * workTypeRepository.findByWorkTypeNameE(work.getWorkType()); //
			 * System.err.println(("@@@@@@@@@@@@@@@@" + workType.getWorkTypeId())); //
			 * System.err.println("============@@@@@@@@@@@@============" +
			 * workType.getWorkTypeNameE()); if (null != workType) {
			 * System.err.println("========================" + workType.getWorkTypeNameE());
			 * workBean.setWorkType(workType.getWorkTypeNameE() != null ?
			 * workType.getWorkTypeNameE() : "NA");
			 * workBean.setWorkTypeId(workType.getWorkTypeId()); } }
			 */

			// workBean.setWorkType(work.getWorkType() != null ? work.getWorkType(): "-");
			// WorkType workType =
			// workTypeRepository.findByWorkTypeNameE(work.getWorkType());
			if (work.getWorkType() != null) {
				WorkType workType = workTypeRepository.findById(work.getWorkType()).orElse(null);
				if (null != workType) {
					workBean.setWorkType(workType.getWorkTypeNameE() != null ? workType.getWorkTypeNameE() : "NA");
					workBean.setWorkTypeName(workType.getWorkTypeNameE() != null ? workType.getWorkTypeNameE() : "NA");
					workBean.setWorkTypeId(workType.getWorkTypeId());
				}
			}
			if (work.getWorkSubtypeId() != null)

			{

				WorkSubType workSubType = workSubTypeRepository.findById(work.getWorkSubtypeId()).orElse(null);
				if (null != workSubType) {
					workBean.setWorkSubTypeId(workSubType.getWorkSubtypeId());
					workBean.setWorkSubTypeName(
							workSubType.getWorkSubTypeNameE() != null ? workSubType.getWorkSubTypeNameE() : "NA");
				}
			}

			if (work.getWorkCategoryId() != null) {
				WorkCategory workCategory = workCategoryRepository.findById(work.getWorkCategoryId()).orElse(null);
				if (null != workCategory) {
					workBean.setWorkCategoryId(workCategory.getWorkCategoryId());
					workBean.setWorkCategoryName(
							workCategory.getWorkCategoryNameE() != null ? workCategory.getWorkCategoryNameE() : "NA");
				}
			}

			if (work.getCategorySubtypeId() != null) {

				SubCategory subCategory = subCategoryRepository.findById(work.getCategorySubtypeId()).orElse(null);
				if (null != subCategory) {
					workBean.setCategorySubTypeName(
							subCategory.getCategorySubTypeNameE() != null ? subCategory.getCategorySubTypeNameE()
									: "NA");
					workBean.setCategorySubTypeId(subCategory.getCategorySubTypeId());
				}
			}

			if (work.getWorkHead() != null) {
				// WorkHead workHead = workHeadRepository.findByHeadName(work.getWorkHead());
				WorkHead workHead = workHeadRepository.findById(work.getWorkHead()).orElse(null);
				if (workHead != null) {
					workBean.setHeadId(workHead.getId());
					// workBean.setHead(work.getWorkHead() != null ? work.getWorkHead() : "NA");
					workBean.setHead(work.getWorkHead());
					workBean.setHeadName(workHead.getHeadName());
				}
			}
			if (work.getHeadState() != null) {
				workBean.setHeadStateId(work.getHeadState());
				WorkHead workHaed = workHeadRepository.findById(work.getHeadState()).orElse(null);
				if (null != workHaed) {
					workBean.setHeadState(workHaed.getHeadName() != null ? workHaed.getHeadName() : "NA");
				}
			}

			if (work.getHeadNhm() != null) {
				workBean.setHeadNhmId(work.getHeadNhm());
				WorkHead workHaed = workHeadRepository.findById(work.getHeadNhm()).orElse(null);
				if (null != workHaed) {
					workBean.setHeadNhm(workHaed.getHeadName() != null ? workHaed.getHeadName() : "NA");
				}
			}

			if (work.getHeadEcrp2() != null) {
				workBean.setHeadEcpr2Id(work.getHeadEcrp2());
				WorkHead workHaed = workHeadRepository.findById(work.getHeadEcrp2()).orElse(null);
				if (null != workHaed) {
					workBean.setHeadEcpr2(workHaed.getHeadName() != null ? workHaed.getHeadName() : "NA");
				}
			}

			if (work.getHeadOthers() != null) {
				workBean.setHeadOthersId(work.getHeadOthers());
				WorkHead workHaed = workHeadRepository.findById(work.getHeadOthers()).orElse(null);
				if (null != workHaed) {
					workBean.setHeadOthers(workHaed.getHeadName() != null ? workHaed.getHeadName() : "NA");
				}
			}
			workBean.setEstimatedAmt(work.getEstimatedAmt() != null ? work.getEstimatedAmt() : BigDecimal.ZERO);
			// workBean.setAmtReleasedTillDate(work.getAmtReleasedTillDate()!=null?
			// work.getAmtReleasedTillDate() : BigDecimal.ZERO);

			ImplementationAgencyType implementationAgencyType = null;

			if (work.getImplementationAgency() != null) {
//				ImplementationAgency implementationAgency = implAgencyRepository
//						.findByImplAgencynameAndEnabled(work.getImplementationAgency(), (short) 1);
				ImplementationAgency implementationAgency = implAgencyRepository
						.findByImplementationAgencyId(work.getImplementationAgency());
				if (implementationAgency != null)
					workBean.setImplementationAgencyId(implementationAgency.getImplementationAgencyId());
				workBean.setImplementationAgencyName(implementationAgency.getImplAgencyname());

			} else {
				workBean.setImplementationAgency(work.getImplementationAgency());
				// workBean.setJpId(work.getJpId());
			}
			workBean.setImplementationAgency(work.getImplementationAgency());
			if (work.getDivisionId() != null) {
				workBean.setDivisionName(
						divisionRepository.findById(work.getDivisionId()).orElse(null).getDivisionName());
			}

		}

		District district = districtRepository.findByDistrictCodeAndEnabled(work.getDistrictCode(), (short) 1);
		if (null != district) {
			workBean.setDistrictName(district.getDistrictName());
			workBean.setDistrictId(district.getDistrictId());
			workBean.setDistrictCode(district.getDistrictCode());
		}
		// if (null != work.getDivisionCode()) {
		Division division = divisionRepository.findById(3L).orElse(null);
		if (null != division) {
			workBean.setDivisionId(division.getDivisionId());
			workBean.setDivisionName(division.getDivisionName());

		}
		// }
		Block block = blockRepository.findByBlockCode(work.getBlockCode());
		if (block != null) {
			workBean.setBlockId(block.getBlockId());
			// workBean.setRural("1");

			workBean.setBlockCode(block != null ? work.getBlockCode() : "");
			workBean.setBlockName(block != null ? block.getBlockName() : "");
		}
//			LegislativeConstituency lc = legislativeConsRepository
//					.findByConstituencyNameAndDistrict(work.getLegislativeConstituencyName(), district);

		LegislativeConstituency lc = legislativeConsRepository.findByIdAndDistrict(work.getLegislativeConstituencyId(),
				district);
		if (lc != null) {
			workBean.setConstituencyCode(lc.getConstituencyCode());
			workBean.setConstituencyName(lc.getConstituencyName());

		}
		if (null != work.getWorkStatus()) {
			workBean.setWorkStatus(work.getWorkStatus());
			WorkStatus workStatus = workStatusRepository.findById(work.getWorkStatus()).orElse(null);
			workBean.setWorkStatusName(workStatus.getWorkStatusNameE());
		}
		if (null != work.getStatus()) {
			workBean.setStatus(work.getStatus());
		}
		if (null != work.getSecureAmtStatus()) {
			workBean.setSecureAmtStatus(work.getSecureAmtStatus());
		}
		if (null != work.getStartDate()) {
			workBean.setStartDate(work.getStartDate());
		}
		// Sumit
		if (null != tsasWorkRepository.getDateOfAdministrativeApproval(work.getId())) {
			workBean.setDateOfAdministrativeApproval(tsasWorkRepository.getDateOfAdministrativeApproval(work.getId()));
		}
		if (null != tsasWorkRepository.getYearOfAdministrativeApproval(work.getId())) {
			workBean.setYearOfAdministrativeApproval(tsasWorkRepository.getYearOfAdministrativeApproval(work.getId()));
		}
		if (null != tsasWorkRepository.getAmountOfAdministrativeApproval(work.getId())) {
			workBean.setAmountOfAdministrativeApproval(
					tsasWorkRepository.getAmountOfAdministrativeApproval(work.getId()));
		}
		if (null != workTenderRepository.getWorkOrderDate(work.getId())) {
			WorkTender tender = workTenderRepository.findByWorkId(work.getId());
			if (null != tender) {
				workBean.setWorkOrderDate(tender.getWorkOrderDate());
				workBean.setAboveBelow(tender.getRateStatus());
				workBean.setAgreementDate(tender.getAgreementDate());
				workBean.setAgreementNo(tender.getAgreementNo());
				if (null != tender.getSorYear()) {
					SorYear sorYear = sorYearRepository.findById(tender.getSorYear()).orElse(null);
					if (null != sorYear) {
						workBean.setSor(sorYear.getSorYear());
					}
				}
				workBean.setTenderParcentage(tender.getTenderPercentage());
			}
		}

		if (null != workTenderRepository.getTimeLineInMonths(work.getId())) {
			workBean.setTimeLineInMonths(workTenderRepository.getTimeLineInMonths(work.getId()));
		}
		if (null != workProgressRepository.getTotalExpeditureTillDate(work.getId())) {
			workBean.setTotalExpeditureTillDate(workProgressRepository.getTotalExpeditureTillDate(work.getId()));
		}
		if (null != workProgressRepository.getLevelOfCompletion(work.getId())) {
			workBean.setLevelOfCompletion(workProgressRepository.getLevelOfCompletion(work.getId()));
		}

		if (null != workProgressRepository.getDateOfCompletion(work.getId())) {
			workBean.setDateOfCompletion(workProgressRepository.getDateOfCompletion(work.getId()));
		}

		Contractor contractor = contractorRepository.findByWork_Id(work.getId());
		if (null != contractor) {
			workBean.setContractorName(contractor.getName());
		}

		workBean.setAsAmt(work.getAsAmt());
		workBean.setAsDate(work.getAsDate());
		workBean.setDmAproveRejectDate(work.getDmApproveRejctDate());
		workBean.setDmStatus(work.getDmStatus());
		// workBean.setDmRemakrs(work.getDmRemakrs());

		if (null != work.getWorkPriorityId()) {
			WorkPriority workPriority = workPriorityRepository.findById(work.getWorkPriorityId()).orElse(null);
			if (null != workPriority) {
				workBean.setWorkPriorityId(workPriority.getId());
				workBean.setWorkPriority(workPriority.getWorkPriorityName());
			}
		}

		if (null != work.getFinancialHeadId()) {
			FinancialHead financialHead = financialHeadRepository.findById(work.getFinancialHeadId()).orElse(null);
			if (null != financialHead) {
				workBean.setFinancialHeadId(financialHead.getId());
				workBean.setFinancialHeadName(financialHead.getFinancialHeadName());
			}
		}

		if (null != work.getVidhanSabhaId()) {
			VidhanSabha vidhanSabha = vidhanSabhaRepositorys.findById(work.getVidhanSabhaId()).orElse(null);
			if (null != vidhanSabha) {
				workBean.setVidhanSabhaId(vidhanSabha.getId());
				workBean.setVidhanSabhaName(vidhanSabha.getVidhanSabhaName());
			}
		}

		Object[] result = (Object[]) workRepository.findWorkWithFundsAndPhotos(work.getId());

		if (result != null) {
			workBean.setFund1(result[3] != null ? Double.valueOf(result[3].toString()) : 0.0);
			workBean.setFund1Exp(result[4] != null ? Double.valueOf(result[4].toString()) : 0.0);

			workBean.setFund2(result[5] != null ? Double.valueOf(result[5].toString()) : 0.0);
			workBean.setFund2Exp(result[6] != null ? Double.valueOf(result[6].toString()) : 0.0);

			workBean.setFund3(result[7] != null ? Double.valueOf(result[7].toString()) : 0.0);
			workBean.setFund3Exp(result[8] != null ? Double.valueOf(result[8].toString()) : 0.0);

			Long lastPhotoId = result[9] != null ? Long.valueOf(result[9].toString()) : null;
			Long secondLastPhotoId = result[10] != null ? Long.valueOf(result[10].toString()) : null;

			if (lastPhotoId != null) {
				workBean.setLastPhoto(
						imageUrl.substring(0, 21) + "/anuppur/mobile/downloadDocumentWSPro/" + lastPhotoId);
			}

			if (secondLastPhotoId != null) {
				workBean.setSecondLastPhoto(
						imageUrl.substring(0, 21) + "/anuppur/mobile/downloadDocumentWSPro/" + secondLastPhotoId);
			}

			if (lastPhotoId != null) {
				workBean.setLastPhotoDocumentId(lastPhotoId);
			}

			if (secondLastPhotoId != null) {
				workBean.setSecondLastPhotoDocumentId(secondLastPhotoId);
			}

		}

		List<DmRemarks> dmRemarks = dmRemarksRepository.findByworkId(work.getId());
		if (dmRemarks != null && !dmRemarks.isEmpty()) {

			DmRemarks lastRemark = dmRemarks.get(dmRemarks.size() - 1);

			if (lastRemark.getRemark() != null) {
				workBean.setDmRemakrs(lastRemark.getRemark());
			}
		}

		List<DepartmentRemarks> departmentRemarks = departmentRemarksRepository.findByWorkId(work.getId());

		if (departmentRemarks != null && !departmentRemarks.isEmpty()) {

			// Use LAST record � shows most recent department remark
			DepartmentRemarks dr = departmentRemarks.get(departmentRemarks.size() - 1);

			DepartmentMaster departmentMaster = null;
			if (dr.getDepertmentMasterId() != null) {
				departmentMaster = departmentMasterRepository.findById(dr.getDepertmentMasterId()).orElse(null);
			}

			String results = "";

			// ?? masterId == 5 ? name + remark
			if (dr.getDepertmentMasterId() != null && dr.getDepertmentMasterId() == 5L) {

				if (departmentMaster != null) {
					results = departmentMaster.getName();
				}

				if (dr.getDepartmentRemarkName() != null) {
					results = results + " : " + dr.getDepartmentRemarkName();
				}
			}
			// ?? masterId != 5 ? only name
			else {
				if (departmentMaster != null) {
					results = departmentMaster.getName();
				}
			}

			workBean.setDepartmentRemarks(results);
		}

		// Set Financial Heads
		List<WorkFinancialAgency> workFinancialAgency = financialAgencyRepository.findByWorkId(work.getId());
		if (workFinancialAgency != null && !workFinancialAgency.isEmpty()) {
			List<FinancialAgencyBean> beanList = new ArrayList<FinancialAgencyBean>();
			for (WorkFinancialAgency entity : workFinancialAgency) {
				FinancialAgencyBean bean = new FinancialAgencyBean();
				bean.setId(entity.getId());
				bean.setFinancialHeadId(entity.getFinancialHeadId());
				bean.setCost(entity.getCost());
				bean.setTotalCost(entity.getTotalCost());

				// Fetch and set the financial head name
				if (entity.getFinancialHeadId() != null) {
					FinancialHead financialHead = financialHeadRepository.findById(entity.getFinancialHeadId())
							.orElse(null);
					if (financialHead != null) {
						bean.setFinancialAgencyName(financialHead.getFinancialHeadName());
					}
				}

				beanList.add(bean);
			}
			workBean.setFinancialHeads(beanList);
		}

		if (work.getId() != null) {
			// Disabled due to compilation issues
			// List<GeoTaggingBean> geoTaggingList = commonService.getGeoTaggingForWorkList(work.getId());
			// if (geoTaggingList != null) {
			//	workBean.setGeoTaggingBeans(geoTaggingList);
			// }
		}
		return workBean;

	}

	@Override
	public WorkProgressBean fetchWorkProgressDocumetnId(Long id) {
		try {

			WorkProgressBean bean = new WorkProgressBean();
			WorkProgress workProgress = new WorkProgress();// workProgressRepository.findByWorkId(documentUploadWorkProgress.getWorkId());
			DocumentUploadWorkProgress documentUploadWorkProgress = documentUploadWorkProgressRepository.findById(id)
					.orElse(null);
			if (null != documentUploadWorkProgress) {

				bean.setDocumentUploadWorkImage(documentUploadWorkProgress.getDocumentId());
				bean.setWorkUploadImageUrl(
						imageUrl + "admin/downloadDocumentWSPro/" + documentUploadWorkProgress.getDocumentId());
				workProgress = workProgressRepository.findByWorkId(documentUploadWorkProgress.getWorkId());
				bean.setWorkSubStatusId(workProgress.getWorkSubStatusId());
				WorkSubStatus workSubStatus = workSubStatusRepository
						.findByWorkSubStatusId(documentUploadWorkProgress.getWorkSubStatusId());
				bean.setWorkSubStatusNameE(workSubStatus.getWorkSubStatusNameE());

			}
			// WorkProgress workProgress =
			// workProgressRepository.findByWorkId(documentUploadWorkProgress.getWorkId());

			return bean;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}

	}

	/*
	 * private WorkProgressBean convertWorkProgressEntityToBean1(WorkProgress
	 * workProgress) throws ParseException {
	 * 
	 * WorkProgressBean workProgressBean = null;
	 * 
	 * if (workProgress != null) {
	 * 
	 * workProgressBean = new WorkProgressBean(); DocumentUploadWorkProgress
	 * documentUploadWorkProgress = documentUploadWorkProgressRepository
	 * .findOne(documentId); if (null != documentUploadWorkProgress.getDocumentId())
	 * {
	 * 
	 * System.err.println( "workProgress.getProgressDocumentUpload()=========" +
	 * documentUploadWorkProgress.getDocumentId());
	 * workProgressBean.setDocumentUploadWorkImage(documentUploadWorkProgress.
	 * getDocumentId()); System.err.println(
	 * "workProgress.getProgressDocumentUpload().getDocumentId()=========" +
	 * documentUploadWorkProgress.getDocumentId());
	 * workProgressBean.setWorkUploadImageUrl( imageUrl +
	 * "admin/downloadDocumentWSPro/" + documentUploadWorkProgress.getDocumentId());
	 * 
	 * } return workProgressBean;
	 * 
	 * } return workProgressBean; }
	 */

	@Override
	public List<MonthBean> fetchmonths() {

		try {
			List<Month> list = monthRepository.findAll();

			List<MonthBean> beanList = new ArrayList<>();
			for (Month month : list) {
				beanList.add(convertMonthEntityToBean(month));
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}

	}

	private MonthBean convertMonthEntityToBean(Month entity) {

		MonthBean bean = new MonthBean();
		if (entity != null) {
			bean.setId(entity.getId());
			bean.setMonthName(entity.getMonthName());
		}
		return bean;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
// @Transactional
	@PreAuthorize("hasRole('ROLE_DM') and @workAuthorization.canAccessWork(#p0.workId)")
	synchronized public ResponseObject addTSASWorkDataCancelStatus(TSASWorkBean tsasWorkbean) throws Exception {

		ResponseObject responseObject = null;

		try {
			if (tsasWorkbean != null) {
				TSASWork entity = null;

				if (null != tsasWorkbean.getWorkId()) {
					entity = tsasWorkRepository.findByWorkId(tsasWorkbean.getWorkId());
					if (entity != null) {

					} else {
						entity = new TSASWork();
						entity.setWorkStatus(2L);

					}

				} else {
					entity = new TSASWork();
					entity.setWorkStatus(2L);

				}

				responseObject = new ResponseObject();
				Work work = workRepository.findById(tsasWorkbean.getWorkId()).orElse(null);
				String workNo = null;

				if (!StringUtils.isEmpty(tsasWorkbean.getStatus())) {
					entity.setStatus(tsasWorkbean.getStatus());
				} else {
					entity.setStatus(DMSConstants.STATUS_ACTIVE);
				}

				// convertTSASWorkBeanToEntity(entity, tsasWorkbean);
				entity.setTsAsSataus(DMSConstants.STATUS_CANCEL);
				entity = tsasWorkRepository.save(entity);

				if (entity != null) {
					// updateWorkCount();
					// work.setWorkNo(DMSConstants.DHS + work.getId());
					if (entity.getWorkStatus() == 2) {
						work.setWorkStatus(2L);
					}

					responseObject.setId(entity.getWork().getId());
					// responseObject.setNumber(tsasWork.getWorkId());

				}

			}
			return responseObject;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			throw new Exception(DMSConstants.ERROR_SAVING_DATA);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
// @Transactional
	@PreAuthorize("hasRole('ROLE_DM') and @workAuthorization.canAccessWork(#p0.workId)")
	synchronized public ResponseObject addTSReviseWorkDataStatus(TSASReviseWorkBean tsasReviseWorkBean)
			throws Exception {

		ResponseObject responseObject = null;

		try {
			if (tsasReviseWorkBean != null) {
				TSASReviseWork entity = null;

				entity = tsasReviseWorkRepository.findById(tsasReviseWorkBean.getId()).orElse(null);
				responseObject = new ResponseObject();
				Work work = workRepository.findById(tsasReviseWorkBean.getWorkId()).orElse(null);
				String workNo = null;

				// convertTSRevisedWorkBeanToEntity(entity, tsasReviseWorkBean, "TS");
				if (null != entity) {
					entity.setTsAsSataus(DMSConstants.STATUS_CANCEL);

					entity = tsasReviseWorkRepository.save(entity);
				}

				if (entity != null) {

					responseObject.setId(entity.getWork().getId());

				}

			}
			return responseObject;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			throw new Exception(DMSConstants.ERROR_SAVING_DATA);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
// @Transactional
	@PreAuthorize("hasRole('ROLE_DM') and @workAuthorization.canAccessWork(#p0.workId)")
	synchronized public ResponseObject addASReviseWorkDataStatus(TSASReviseWorkBean tsasReviseWorkBean)
			throws Exception {

		ResponseObject responseObject = null;

		try {
			if (tsasReviseWorkBean != null) {
				TSASReviseWork entity = null;

				entity = tsasReviseWorkRepository.findById(tsasReviseWorkBean.getId()).orElse(null);

				responseObject = new ResponseObject();
				Work work = workRepository.findById(tsasReviseWorkBean.getWorkId()).orElse(null);
				String workNo = null;

				// convertTSRevisedWorkBeanToEntity(entity, tsasReviseWorkBean, "AS");
				entity.setTsAsSataus(DMSConstants.STATUS_CANCEL);
				entity = tsasReviseWorkRepository.save(entity);

				if (entity != null) {

					responseObject.setId(entity.getWork().getId());

				}
			}
			return responseObject;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			throw new Exception(DMSConstants.ERROR_SAVING_DATA);
		}
	}

	@Override
	public List<DistrictBean> fetchDistrictsMP() {

		List<DistrictBean> beanlist = new ArrayList<>();

		List<District> list = districtRepository.getAllByDivisionBetween(new Division(1L), new Division(9L));

		for (District s : list) {

			beanlist.add(convertDistrictEntityToBean(s));

		}
		return beanlist;
	}

	@Override
	public List<BlockBean> fetchBlocksByDistirct(String dId) {

		List<BlockBean> beanlist = new ArrayList<>();

		try {
			List<Block> entity = blockRepository.findByDistrictAndEnabledOrderByBlockName(
					districtRepository.findById(Long.parseLong(dId)).orElse(null), DMSConstants.ENABLED);

			for (Block e : entity) {

				beanlist.add(convertBlockEntityToBean(e));

			}
		} catch (Exception e) {
			// TODO: handle exception

		}

		return beanlist;
	}

	@Override
	public List<BlockBean> fetchBlocksByDistirct2(String dId) {

		List<BlockBean> beanlist = new ArrayList<>();

		try {
			List<Block> entity = blockRepository.findByDistrictAndEnabledOrderByBlockName(
					districtRepository.findByDistrictCode(dId), DMSConstants.ENABLED);

			for (Block e : entity) {

				beanlist.add(convertBlockEntityToBean(e));

			}
		} catch (Exception e) {
			// TODO: handle exception

		}

		return beanlist;
	}

	@Override
	public BlockJson fetchBlockForDistrict(Pageable pageable, String searchboxval, String districtCode) {

		BlockJson blockjson = null;

		String searchboxval1 = searchboxval;

		try {
			Page<Block> block = null;
			if (districtCode != null && !districtCode.isEmpty()) {

				if (searchboxval != null && !searchboxval.isEmpty()) {

					block = blockRepository.findByBlockNameContainingAndDistrictAndEnabled(pageable, searchboxval1,
							districtRepository.findById(Long.parseLong(districtCode)).orElse(null), (short) 1);
				} else {

					block = blockRepository.findByDistrictAndEnabled(pageable,
							districtRepository.findById(Long.parseLong(districtCode)).orElse(null), (short) 1);
				}

			} else if ((districtCode != null && districtCode.isEmpty()) && !searchboxval.isEmpty()) {
				block = blockRepository.findByBlockNameContainingAndEnabled(pageable, searchboxval1, (short) 1);

			} else {
				block = blockRepository.findAllByEnabled(pageable, (short) 1);
			}

			if (block != null) {
				List<Block> entityList = block.getContent();
				List<BlockBean> beanList = new ArrayList<>();
				if (entityList != null && !entityList.isEmpty()) {

					int index = pageable.getPageNumber() * pageable.getPageSize();
					for (Block entity : entityList) {

						BlockBean bean = convertBlockEntityToBean(entity);
						bean.setIndex(++index);
						beanList.add(bean);
					}
				}
				blockjson = new BlockJson();
				blockjson.setiTotalDisplayRecords(block.getTotalElements());
				blockjson.setiTotalRecords(blockRepository.count());
				blockjson.setAaData(beanList);
			}
			return blockjson;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return blockjson;
		}
	}

	@Override
	public WorkJson fetchWorkForReport(Pageable pageable, String workNo, String workName, String scheme,
			List<Long> workTypeList, List<Long> fyList, String Department, List<Long> agencyList, String blockId,
			String workStatus, String districtName, String divisionId, String searchByDivision, String workSubTypeId,
			List<Long> statusList, List<Long> priorityList, List<Long> headList, List<Long> vsList,
			String workNameFilter, String departmentRemark) {

		Integer workSubTypeIdInt = null;
		if (workSubTypeId != null) {
			try {
				workSubTypeIdInt = Integer.parseInt(workSubTypeId);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		WorkJson workJson = null;
		User user = DMSUtil.getUserDetail();

		Users userEntity = userRepository.findByUsernameAndStatus(user.getUsername(), DMSConstants.STATUS_ACTIVE);

		Collection<GrantedAuthority> role = user.getAuthorities();
		String department = "";
		if (Department != null) {
			department = Department;
		}
		String WorkName = "";
		if (workName != null) {
			WorkName = workName;

		}

		Long districtIds = null;
		if (districtName != null) {
			districtIds = Long.valueOf(districtName);
//			districtName = districtRepository.findById(Long.valueOf(districtId)).orElse(null).getDistrictName();
		}

		Long divisionIds = null;
		if (divisionId != null) {
			divisionIds = Long.valueOf(divisionId);
//			divisionName = divisionRepository.findById(Long.valueOf(divisionId)).orElse(null).getDivisionName();
		}

		// Convert blockIdFilter comma-separated string to List<Long>
		List<Long> blockIdList = new ArrayList<>();
		if (blockId != null && !blockId.isEmpty()) {
			String[] blockIds = blockId.split(",");
			for (String id : blockIds) {
				try {
					blockIdList.add(Long.valueOf(id.trim()));
				} catch (NumberFormatException e) {
					// Skip invalid IDs
				}
			}
		}

		// Sumit
		workTypeList = (workTypeList == null || workTypeList.isEmpty()) ? null : workTypeList;
		fyList = (fyList == null || fyList.isEmpty()) ? null : fyList;
		agencyList = (agencyList == null || agencyList.isEmpty()) ? null : agencyList;
		statusList = (statusList == null || statusList.isEmpty()) ? null : statusList;
		priorityList = (priorityList == null || priorityList.isEmpty()) ? null : priorityList;
		headList = (headList == null || headList.isEmpty()) ? null : headList;
		vsList = (vsList == null || vsList.isEmpty()) ? null : vsList;
		blockIdList = (blockIdList == null || blockIdList.isEmpty()) ? null : blockIdList;

		try {
			Page<Work> works = null;

//			System.err.println("th si sads" + Department + financialYear + workName);
//
//			if (!WorkName.isEmpty() && department.isEmpty() && Year.isEmpty()) {
//
//				works = workRepository.findByWorkNameContaining(pageable, WorkName);
//
//			} else if (!Year.isEmpty() && WorkName.isEmpty() && department.isEmpty() && status.isEmpty()) {
//
//				works = workRepository.findByFinancialYear(pageable, Long.parseLong(Year));
//			} else if (!Year.isEmpty() && !WorkName.isEmpty() && department.isEmpty() && status.isEmpty()) {
//
//				works = workRepository.findByFinancialYearAndWorkNameContaining(pageable, Long.parseLong(Year),
//						WorkName);
//			} else if (Year.isEmpty() && WorkName.isEmpty() && !department.isEmpty() && status.isEmpty()) {
//
//				works = workRepository.findByCreatedBy(pageable,
//						userRepository.findById(Long.parseLong(department)).orElse(null).getUsername());
//			} else if (!Year.isEmpty() && !WorkName.isEmpty() && !department.isEmpty() && status.isEmpty()) {
//
//				works = workRepository.findByCreatedByAndFinancialYearAndWorkNameContaining(pageable,
//						userRepository.findById(Long.parseLong(department)).orElse(null).getUsername(), Long.parseLong(Year),
//						WorkName);
//			} else if (!Year.isEmpty() && WorkName.isEmpty() && !department.isEmpty() && status.isEmpty()) {
//
//				works = workRepository.findByCreatedByAndFinancialYear(pageable,
//						userRepository.findById(Long.parseLong(department)).orElse(null).getUsername(), Long.parseLong(Year));
//			} else if (Year.isEmpty() && !WorkName.isEmpty() && !department.isEmpty() && status.isEmpty()) {
//
//				works = workRepository.findByCreatedByAndWorkNameContaining(pageable,
//						userRepository.findById(Long.parseLong(department)).orElse(null).getUsername(), WorkName);
//			} else if (!status.isEmpty() && Year.isEmpty() && WorkName.isEmpty() && department.isEmpty()) {
//
//				works = workRepository.findByWorkStatus(pageable, Long.parseLong(status));
//
//			} else if (!status.isEmpty() && Year.isEmpty() && !WorkName.isEmpty() && department.isEmpty()) {
//
//				works = workRepository.findByWorkStatusAndWorkNameContaining(pageable, Long.parseLong(status),
//						WorkName);
//
//			} else if (!status.isEmpty() && Year.isEmpty() && !WorkName.isEmpty() && !department.isEmpty()) {
//
//				works = workRepository.findByWorkStatusAndWorkNameContainingAndCreatedBy(pageable,
//						Long.parseLong(status), WorkName,
//						userRepository.findById(Long.parseLong(department)).orElse(null).getUsername());
//
//			} else if (!status.isEmpty() && !Year.isEmpty() && !WorkName.isEmpty() && department.isEmpty()) {
//
//				works = workRepository.findByWorkStatusAndWorkNameContainingAndFinancialYear(pageable,
//						Long.parseLong(status), WorkName, Long.parseLong(Year));
//
//			} else if (!status.isEmpty() && !Year.isEmpty() && !WorkName.isEmpty() && !department.isEmpty()) {
//
//				works = workRepository.findByWorkStatusAndWorkNameContainingAndFinancialYearAndCreatedBy(pageable,
//						Long.parseLong(status), WorkName, Long.parseLong(Year),
//						userRepository.findById(Long.parseLong(department)).orElse(null).getUsername());
//
//			}

//			else {

			works = workRepositoryCustomImpl.findAllByStatusNotDeleted(pageable, workName, workTypeList, fyList,
					districtIds, statusList, agencyList, priorityList, headList, vsList, workNameFilter, blockIdList,
					departmentRemark);

//			}

			if (works != null) {
				List<Work> entityList = works.getContent();
				List<WorkBean> beanList = new ArrayList<>();

				if (entityList != null && !entityList.isEmpty()) {

					int index = pageable.getPageNumber() * pageable.getPageSize();
					for (Work work : entityList) {
						// System.err.println("workid====++++++ " + work.getId());
						// WorkBean bean = convertWorkEntityToBean(work, "All");
						WorkBean bean = convertWorkEntityToBeansreport(work);
						bean.setIndex(++index);

						for (Role s : userEntity.getRoles()) {
							bean.setRole(s.getRoleCode());

						}

						bean.setProgressDocuments(buildProgressDocumentBeans(work.getId()));

						// ? finally add to list
						beanList.add(bean);

						for (WorkBean workBean : beanList) {
							List<DmRemarks> byworkId = null;
							// System.err.println(workBean.getWorkId()+"adasdasdasd");
							if (dmRemarksRepository.findByworkIdAndEnabled(workBean.getWorkId(), (short) 1) != null) {

								byworkId = dmRemarksRepository.findByworkIdAndEnabled(workBean.getWorkId(), (short) 1);

							}
							DmRemarks dmRemarks = null;
							if (byworkId != null && byworkId.size() > 0) {
								dmRemarks = byworkId.get(byworkId.size() - 1);
								// System.err.println(dmRemarks.getCreated_time()+"asdasdasdasdasdasdasds");
							}
							if (dmRemarks != null) {
								String createdTimeStr = dmRemarks.getCreated_time();
								if (dmRemarks.getRemark() != null && !dmRemarks.getRemark().isEmpty()) {
									workBean.setDmRemakrs(dmRemarks.getRemark());
									// System.err.println(dmRemarks.getRemark() + "this is the remarkss");
								} else {
									workBean.setDmRemakrs(null);
								}
								if (createdTimeStr != null && !createdTimeStr.trim().isEmpty()) {
									try {

										SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
										sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
										Date createdTime = sdf.parse(createdTimeStr);
										workBean.setDmAproveRejectDate(createdTime);
									} catch (Exception e) {

										workBean.setDmAproveRejectDate(null);

									}
								} else {
									workBean.setDmAproveRejectDate(null);
								}
							} else {
								workBean.setDmAproveRejectDate(null);
							}
							// System.out.println("Work ID: " + workBean.getWorkId());
							// System.out.println("Documents Count: " + (workBean.getProgressDocuments() !=
							// null ? workBean.getProgressDocuments().size() : "NULL"));
						}

					}
				}

				workJson = new WorkJson();
				workJson.setiTotalDisplayRecords(works.getTotalElements());
				workJson.setiTotalRecords(workRepository.countByStatusNotIn(DMSConstants.STATUS_DELETED));

				workJson.setAaData(beanList);
			}
			return workJson;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return workJson;
		}
	}

	private List<DocumentUploadWorkProgressBean> buildProgressDocumentBeans(Long workId) {
		List<DocumentUploadWorkProgress> wpList = documentUploadWorkProgressRepository.findAllByWorkId(workId);
		List<DocumentUploadWorkProgressBean> documentBeans = new ArrayList<>();
		if (wpList == null || wpList.isEmpty()) {
			return documentBeans;
		}
		int index = 0;
		for (DocumentUploadWorkProgress documentUploadWorkProgress : wpList) {
			if (documentUploadWorkProgress.getDocumentId() == null) {
				continue;
			}
			DocumentUploadWorkProgressBean bean2 = new DocumentUploadWorkProgressBean();
			bean2.setIndexWS(++index);
			bean2.setDocumentId(documentUploadWorkProgress.getDocumentId());
			bean2.setDocumentName(documentUploadWorkProgress.getDocumentName());
			bean2.setCreatedDate(documentUploadWorkProgress.getCreatedDate());
			if (documentUploadWorkProgress.getWorkStatusId() != null) {
				bean2.setWorkStatusId(documentUploadWorkProgress.getWorkStatusId());
				bean2.setWorkStatusNameE(documentUploadWorkProgress.getWorkStatusNameE());
			}
			if (documentUploadWorkProgress.getWorkSubStatusId() != null) {
				bean2.setWorkSubStatusId(documentUploadWorkProgress.getWorkSubStatusId());
				WorkSubStatus workSubStatus = workSubStatusRepository
						.findByWorkSubStatusId(documentUploadWorkProgress.getWorkSubStatusId());
				if (workSubStatus != null) {
					bean2.setWorkSubStatusNameE(workSubStatus.getWorkSubStatusNameE());
				}
			}
			if (imageUrl != null && imageUrl.length() >= 21) {
				bean2.setImagepath(imageUrl.substring(0, 21) + "/anuppur/mobile/downloadDocumentWSPro/"
						+ bean2.getDocumentId());
			}
			documentBeans.add(bean2);
		}
		return documentBeans;
	}

	private WorkBean convertWorkEntityToBeansreport(Work work) {

		WorkBean workBean = new WorkBean();

		workBean.setWorkId(work.getId());

		workBean.setWorkName(work.getWorkName());
		workBean.setFinancialYear(work.getFinancialYear());
		if (work.getFinancialYear() != null) {
			FinancialYear financialYear = financialYearRepository.findById(work.getFinancialYear()).orElse(null);
			if (null != financialYear) {
				workBean.setFinancialYearName(financialYear.getFinancialYear());
			}
		}
		// bean.setImplementationAgency(implAgencyRepository.findById(work.getImplementationAgency()).orElse(null).get);
		if (work.getImplementationAgency() != null) {
			workBean.setImplementationAgencyName(
					implAgencyRepository.findById(work.getImplementationAgency()).orElse(null).getImplAgencyname());
		}

		if (work.getWorkStatus() != null) {
			workBean.setWorkStatusName(
					workStatusRepository.findById(work.getWorkStatus()).orElse(null).getWorkStatusNameE());
		}

		if (work.getId() != null) {
			workBean.setId(work.getId());
		}
		if (work.getId() != null) {
			workBean.setWorkId(work.getId());
		}
		if (work.getWorkNo() != null) {
			workBean.setWorkNo(work.getWorkNo() != null ? work.getWorkNo() : "NA");
		}
		if (work.getWorkName() != null) {
			workBean.setWorkName(work.getWorkName() != null ? work.getWorkName() : "NA");
		}

		// workBean.setWorkNo(work.getWorkNo());
		if (work.getMultifundedStatus() != null) {
			workBean.setMultifundedStatus(work.getMultifundedStatus());
		}
		if (work.getFundByState() != null) {
			workBean.setFundByState(work.getFundByState());
		}
		if (work.getFundByEcrp2() != null) {
			workBean.setFundByEcrp2(work.getFundByEcrp2());
		}
		if (work.getFundByNhm() != null) {
			workBean.setFundByNhm(work.getFundByNhm());
		}
		if (work.getFundByOthers() != null) {
			workBean.setFundByOthers(work.getFundByOthers());
		}
		if (work.getWorkRequestStatusId() != null) {
			workBean.setWorkRequestStatusId(work.getWorkRequestStatusId());
		}
		if (work.getDrawCreatedBy() != null) {
			workBean.setDrawCreatedBy(work.getDrawCreatedBy());
		}
		if (work.getAllocatedAmount() != null) {
			workBean.setAllocatedAmount(work.getAllocatedAmount());
		}
		if (work.getWorkSubtypeId() != null && work.getWorkSubtypeId() <= 6) {
			workBean.setWorkSubTypeId(work.getWorkSubtypeId());
			workBean.setWorkSubTypeName(
					workSubTypeRepository.findById(work.getWorkSubtypeId()).orElse(null).getWorkSubTypeNameE() != null
							? workSubTypeRepository.findById(work.getWorkSubtypeId()).orElse(null).getWorkSubTypeNameE()
							: "NA");
		}

		// workBean.setWorkType(work.getWorkType());
		// workBean.setWorkTypeId(workTypeRepository.findByWorkTypeNameE(work.getWorkType()).getWorkTypeId());
		if (work.getFinancialYear() != null) {
			workBean.setFinancialYear(work.getFinancialYear());
			FinancialYear financialYear = financialYearRepository.findById(work.getFinancialYear()).orElse(null);
			if (financialYear != null) {
				workBean.setFinancialYearName(financialYear.getFinancialYear());
			}
		}
		if (work.getScheme() != null) {

			// Schemes schemes = schemeRepository.findBySchemeName(work.getScheme());
			Schemes schemes = schemeRepository.findById(work.getScheme()).orElse(null);
			if (schemes != null) {
				workBean.setScheme(schemes.getSchemeName() != null ? schemes.getSchemeName() : "NA");
				workBean.setSchemeId(schemes.getId());

			}
		}
		if (work.getSchemeState() != null) {

			Schemes schemesSate = schemeRepository.findById(work.getSchemeState()).orElse(null);
			if (schemesSate != null) {
				workBean.setSchemeState(schemesSate.getSchemeName() != null ? schemesSate.getSchemeName() : "NA");
				workBean.setSchemeStateId(schemesSate.getId());
			}

		}

		if (work.getSchemeNhm() != null) {

			Schemes schemesNhm = schemeRepository.findById(work.getSchemeNhm()).orElse(null);
			if (schemesNhm != null) {
				workBean.setSchemeNhm(schemesNhm.getSchemeName() != null ? schemesNhm.getSchemeName() : "NA");
				workBean.setSchemeNhmId(schemesNhm.getId());
			}

		}

		if (work.getSchemeEcrp2() != null) {

			Schemes schemesEcpr = schemeRepository.findById(work.getSchemeEcrp2()).orElse(null);
			if (schemesEcpr != null) {
				workBean.setSchemeEcpr2(schemesEcpr.getSchemeName() != null ? schemesEcpr.getSchemeName() : "NA");
				workBean.setSchemeEcpr2Id(schemesEcpr.getId());
			}

		}

		if (work.getSchemeOthers() != null) {

			Schemes schemesOthers = schemeRepository.findById(work.getSchemeOthers()).orElse(null);
			if (schemesOthers != null) {
				workBean.setSchemeOthers(schemesOthers.getSchemeName() != null ? schemesOthers.getSchemeName() : "NA");
				workBean.setSchemeOthersId(schemesOthers.getId());
			}
		}

		/*
		 * if (work.getWorkType() != null) { System.err.println("----- " +
		 * work.getWorkType()); WorkType workType =
		 * workTypeRepository.findByWorkTypeNameE(work.getWorkType()); //
		 * System.err.println(("@@@@@@@@@@@@@@@@" + workType.getWorkTypeId())); //
		 * System.err.println("============@@@@@@@@@@@@============" +
		 * workType.getWorkTypeNameE()); if (null != workType) {
		 * System.err.println("========================" + workType.getWorkTypeNameE());
		 * workBean.setWorkType(workType.getWorkTypeNameE() != null ?
		 * workType.getWorkTypeNameE() : "NA");
		 * workBean.setWorkTypeId(workType.getWorkTypeId()); } }
		 */

		// workBean.setWorkType(work.getWorkType() != null ? work.getWorkType(): "-");
		// WorkType workType =
		// workTypeRepository.findByWorkTypeNameE(work.getWorkType());
		WorkType workType = work.getWorkType() != null ? workTypeRepository.findById(work.getWorkType()).orElse(null)
				: null;
		if (null != workType) {
			workBean.setWorkType(workType.getWorkTypeNameE() != null ? workType.getWorkTypeNameE() : "NA");
			workBean.setWorkTypeId(workType.getWorkTypeId());
		}

		if (work.getWorkSubtypeId() != null)

		{

			WorkSubType workSubType = workSubTypeRepository.findById(work.getWorkSubtypeId()).orElse(null);
			if (null != workSubType) {
				workBean.setWorkSubTypeId(workSubType.getWorkSubtypeId());
				workBean.setWorkSubTypeName(
						workSubType.getWorkSubTypeNameE() != null ? workSubType.getWorkSubTypeNameE() : "NA");

				// System.err.println("workSubType.getWorkSubTypeNameE()" +
				// workBean.getWorkSubTypeName());
				// System.err.println("workSubType.getWorkSubtypeId()" +
				// workBean.getWorkSubTypeId());
			}
		}

		if (work.getWorkCategoryId() != null) {
			WorkCategory workCategory = workCategoryRepository.findById(work.getWorkCategoryId()).orElse(null);
			if (null != workCategory) {
				workBean.setWorkCategoryId(workCategory.getWorkCategoryId());
				workBean.setWorkCategoryName(
						workCategory.getWorkCategoryNameE() != null ? workCategory.getWorkCategoryNameE() : "NA");
			}
		}

		if (work.getCategorySubtypeId() != null) {

			SubCategory subCategory = subCategoryRepository.findById(work.getCategorySubtypeId()).orElse(null);
			if (null != subCategory) {
				workBean.setCategorySubTypeName(
						subCategory.getCategorySubTypeNameE() != null ? subCategory.getCategorySubTypeNameE() : "NA");
				workBean.setCategorySubTypeId(subCategory.getCategorySubTypeId());
			}
		}

		if (work.getWorkHead() != null) {
			// WorkHead workHead = workHeadRepository.findByHeadName(work.getWorkHead());
			WorkHead workHead = workHeadRepository.findById(work.getWorkHead()).orElse(null);
			if (workHead != null) {
				workBean.setHeadId(workHead.getId());
				// workBean.setHead(work.getWorkHead() != null ? work.getWorkHead() : "NA");
				workBean.setHead(work.getWorkHead());
				workBean.setHeadName(workHead.getHeadName());
			}
		}
		if (work.getHeadState() != null) {
			workBean.setHeadStateId(work.getHeadState());
			WorkHead workHaed = workHeadRepository.findById(work.getHeadState()).orElse(null);
			if (null != workHaed) {
				workBean.setHeadState(workHaed.getHeadName() != null ? workHaed.getHeadName() : "NA");
			}
		}

		if (work.getHeadNhm() != null) {
			workBean.setHeadNhmId(work.getHeadNhm());
			WorkHead workHaed = workHeadRepository.findById(work.getHeadNhm()).orElse(null);
			if (null != workHaed) {
				workBean.setHeadNhm(workHaed.getHeadName() != null ? workHaed.getHeadName() : "NA");
			}
		}

		if (work.getHeadEcrp2() != null) {
			workBean.setHeadEcpr2Id(work.getHeadEcrp2());
			WorkHead workHaed = workHeadRepository.findById(work.getHeadEcrp2()).orElse(null);
			if (null != workHaed) {
				workBean.setHeadEcpr2(workHaed.getHeadName() != null ? workHaed.getHeadName() : "NA");
			}
		}

		if (work.getHeadOthers() != null) {
			workBean.setHeadOthersId(work.getHeadOthers());
			WorkHead workHaed = workHeadRepository.findById(work.getHeadOthers()).orElse(null);
			if (null != workHaed) {
				workBean.setHeadOthers(workHaed.getHeadName() != null ? workHaed.getHeadName() : "NA");
			}
		}
		workBean.setEstimatedAmt(work.getEstimatedAmt() != null ? work.getEstimatedAmt() : BigDecimal.ZERO);
		// workBean.setAmtReleasedTillDate(work.getAmtReleasedTillDate()!=null?
		// work.getAmtReleasedTillDate() : BigDecimal.ZERO);

		ImplementationAgencyType implementationAgencyType = null;

		if (work.getImplementationAgency() != null) {
//			ImplementationAgency implementationAgency = implAgencyRepository
//					.findByImplAgencynameAndEnabled(work.getImplementationAgency(), (short) 1);
			ImplementationAgency implementationAgency = implAgencyRepository
					.findByImplementationAgencyId(work.getImplementationAgency());
			if (implementationAgency != null)
				workBean.setImplementationAgencyId(implementationAgency.getImplementationAgencyId());
			workBean.setImplementationAgencyName(implementationAgency.getImplAgencyname());

		} else {
			workBean.setImplementationAgency(work.getImplementationAgency());
			// workBean.setJpId(work.getJpId());
		}
		workBean.setImplementationAgency(work.getImplementationAgency());
		if (work.getDivisionId() != null) {
			workBean.setDivisionName(divisionRepository.findById(work.getDivisionId()).orElse(null).getDivisionName());
		}

		District district = districtRepository.findByDistrictCodeAndEnabled(work.getDistrictCode(), (short) 1);
		if (null != district) {
			workBean.setDistrictName(district.getDistrictName());
			workBean.setDistrictId(district.getDistrictId());
			workBean.setDistrictCode(district.getDistrictCode());
		}
		// if (null != work.getDivisionCode()) {
		Division division = divisionRepository.findById(3L).orElse(null);
		if (null != division) {
			workBean.setDivisionId(division.getDivisionId());
			workBean.setDivisionName(division.getDivisionName());

		}
		// }
		Block block = blockRepository.findByBlockCode(work.getBlockCode());
		if (block != null) {
			workBean.setBlockId(block.getBlockId());
			// workBean.setRural("1");

			workBean.setBlockCode(block != null ? work.getBlockCode() : "");
			workBean.setBlockName(block != null ? block.getBlockName() : "");
		}
//		LegislativeConstituency lc = legislativeConsRepository
//				.findByConstituencyNameAndDistrict(work.getLegislativeConstituencyName(), district);

		LegislativeConstituency lc = legislativeConsRepository.findByIdAndDistrict(work.getLegislativeConstituencyId(),
				district);
		if (lc != null) {
			workBean.setConstituencyCode(lc.getConstituencyCode());
			workBean.setConstituencyName(lc.getConstituencyName());

		}
		if (null != work.getWorkStatus()) {
			workBean.setWorkStatus(work.getWorkStatus());
			WorkStatus workStatus = workStatusRepository.findById(work.getWorkStatus()).orElse(null);
			workBean.setWorkStatusName(workStatus.getWorkStatusNameE());
		}
		if (null != work.getStatus()) {
			workBean.setStatus(work.getStatus());
		}
		if (null != work.getSecureAmtStatus()) {
			workBean.setSecureAmtStatus(work.getSecureAmtStatus());
		}
		if (null != work.getStartDate()) {
			workBean.setStartDate(work.getStartDate());
		}
		// aman 17-07-2024
		if (null != tsasWorkRepository.getDateOfAdministrativeApproval(work.getId())) {
			workBean.setDateOfAdministrativeApproval(tsasWorkRepository.getDateOfAdministrativeApproval(work.getId()));
		}
		if (null != tsasWorkRepository.getYearOfAdministrativeApproval(work.getId())) {
			workBean.setYearOfAdministrativeApproval(tsasWorkRepository.getYearOfAdministrativeApproval(work.getId()));
		}
		if (null != tsasWorkRepository.getAmountOfAdministrativeApproval(work.getId())) {
			workBean.setAmountOfAdministrativeApproval(
					tsasWorkRepository.getAmountOfAdministrativeApproval(work.getId()));
		}
		if (null != workTenderRepository.getWorkOrderDate(work.getId())) {
			WorkTender tender = workTenderRepository.findByWorkId(work.getId());
			if (null != tender) {
				workBean.setWorkOrderDate(tender.getWorkOrderDate());
				workBean.setAboveBelow(tender.getRateStatus());
				workBean.setAgreementDate(tender.getAgreementDate());
				workBean.setAgreementNo(tender.getAgreementNo());
				if (null != tender.getSorYear()) {
					SorYear sorYear = sorYearRepository.findById(tender.getSorYear()).orElse(null);
					if (null != sorYear) {
						workBean.setSor(sorYear.getSorYear());
					}
				}
				workBean.setTenderParcentage(tender.getTenderPercentage());
			}
		}

		if (null != workTenderRepository.getTimeLineInMonths(work.getId())) {
			workBean.setTimeLineInMonths(workTenderRepository.getTimeLineInMonths(work.getId()));
		}
		if (null != workProgressRepository.getTotalExpeditureTillDate(work.getId())) {
			workBean.setTotalExpeditureTillDate(workProgressRepository.getTotalExpeditureTillDate(work.getId()));
		}
		if (null != workProgressRepository.getLevelOfCompletion(work.getId())) {
			workBean.setLevelOfCompletion(workProgressRepository.getLevelOfCompletion(work.getId()));
		}

		Contractor contractor = contractorRepository.findByWork_Id(work.getId());
		if (null != contractor) {
			workBean.setContractorName(contractor.getName());
		}

		workBean.setAsAmt(work.getAsAmt());
		workBean.setAsDate(work.getAsDate());
		workBean.setDmAproveRejectDate(work.getDmApproveRejctDate());
		workBean.setDmStatus(work.getDmStatus());
		workBean.setDmRemakrs(work.getDmRemakrs());

		if (null != work.getWorkPriorityId()) {
			WorkPriority workPriority = workPriorityRepository.findById(work.getWorkPriorityId()).orElse(null);
			if (null != workPriority) {
				workBean.setWorkPriorityId(workPriority.getId());
				workBean.setWorkPriority(workPriority.getWorkPriorityName());
			}
		}

		if (null != work.getFinancialHeadId()) {
			FinancialHead financialHead = financialHeadRepository.findById(work.getFinancialHeadId()).orElse(null);
			if (null != financialHead) {
				workBean.setFinancialHeadId(financialHead.getId());
				workBean.setFinancialHeadName(financialHead.getFinancialHeadName());
			}
		}

		if (null != work.getVidhanSabhaId()) {
			VidhanSabha vidhanSabha = vidhanSabhaRepositorys.findById(work.getVidhanSabhaId()).orElse(null);
			if (null != vidhanSabha) {
				workBean.setVidhanSabhaId(vidhanSabha.getId());
				workBean.setVidhanSabhaName(vidhanSabha.getVidhanSabhaName());
			}
		}

		return workBean;

	}

	@Override
	public List<departmentbean> fetchAllDepartment() {

		List<departmentbean> map = new ArrayList<>();

		try {

			List<Users> user = userRepository.findByDesignationIDAndStatus(2L, "Active");

			for (Users u : user) {

				map.add(new departmentbean(u.getId(), u.getDepartmentName()));
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return map;
	}

	@Override
	public List<Work> getWorksByAgencyId(Long agencyId) {
		return workRepository.findByImplementationAgency(agencyId);
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM') and @workAuthorization.canAssignUserToWork(#p0, #p1)")
	public String assignUserToWork(Long userid, Long workid) {

		try {

			Work one = workRepository.findById(workid).orElse(null);

			one.setUserAssignee(userid);

			one.setUserAssigneeDate(new Date());
			workRepository.save(one);

			AreaOfficerRecord areaofficerrecord;
			AreaOfficerRecord byUserid = areaOfficerRecordRepository.findByUserid(userid);
			if (byUserid != null) {
				areaofficerrecord = byUserid;
			} else {
				areaofficerrecord = new AreaOfficerRecord();
			}
			areaofficerrecord.setUserid(userid);
			areaofficerrecord.setWorkId(workid);

			areaOfficerRecordRepository.save(areaofficerrecord);

			return "User Has Assign To Work";

		} catch (Exception e) {
			// TODO: handle exception
			// e.printStackTrace();
			logger.error("Can Not Assign Work To User", e);
			return null;

		}

	}

	@Override
	public WorkStatusJson fetchWorkStatusDataList(Long workId) {

		WorkStatusJson json = null;

		try {

			WorkTender tender = workTenderRepository.findByWorkId(workId);

			List<WorkStatusBean> beanList = new ArrayList<>();
			// List<Integer> workstatusLIst = Arrays.asList(3,4,7,8,14);

			int index = 0;
			if (tender != null) {
				WorkStatusBean bean = convertWorkStatusEntityToBean(workStatusRepository.findById(3L).orElse(null));
				bean.setIndex(++index);
				bean.setStatusDate(tender.getTenderCalledDate());
				beanList.add(bean);
			}
			if (tender != null) {
				WorkStatusBean bean = convertWorkStatusEntityToBean(workStatusRepository.findById(4L).orElse(null));
				bean.setIndex(++index);
				bean.setStatusDate(tender.getTenderReceivedDate());
				beanList.add(bean);
			}
			if (tender != null) {
				if (tender.getLoaIssuedDate() != null) {
					WorkStatusBean bean = convertWorkStatusEntityToBean(workStatusRepository.findById(7L).orElse(null));
					bean.setIndex(++index);
					bean.setStatusDate(tender.getLoaIssuedDate());
					beanList.add(bean);

				}
			}
			if (tender != null) {
				WorkStatusBean bean = convertWorkStatusEntityToBean(workStatusRepository.findById(8L).orElse(null));
				bean.setIndex(++index);
				bean.setStatusDate(tender.getWorkOrderDate());
				beanList.add(bean);
			}
			if (tender != null) {
				WorkStatusBean bean = convertWorkStatusEntityToBean(workStatusRepository.findById(14L).orElse(null));
				bean.setIndex(++index);
				bean.setStatusDate(tender.getReTenderDate());
				beanList.add(bean);
			}

			json = new WorkStatusJson();
			json.setiTotalRecords(10);

			json.setAaData(beanList);
			return json;
		}

		catch (Exception e) {
			logger.error("An exception occurred.", e);
			return json;
		}
	}

	@Override
	public WorkStatusJson fetchWorkStatusDataListCC(Long workId) {
		WorkStatusJson json = null;

		try {

			CC cc = ccRepository.findByWorkId(workId);

			List<WorkStatusBean> beanList = new ArrayList<>();
			// List<Integer> workstatusLIst = Arrays.asList(3,4,7,8,14);

			int index = 0;
			if (cc != null) {
				WorkStatusBean bean = convertWorkStatusEntityToBean(workStatusRepository.findById(13L).orElse(null));
				bean.setIndex(++index);
				bean.setStatusDate(cc.getCcDate());

				beanList.add(bean);
			}
			if (cc != null) {
				WorkStatusBean bean = convertWorkStatusEntityToBean(workStatusRepository.findById(12L).orElse(null));
				bean.setIndex(++index);
				bean.setStatusDate(cc.getDateHandOver());

				beanList.add(bean);
			}

			json = new WorkStatusJson();
			json.setiTotalRecords(10);

			json.setAaData(beanList);
			return json;
		}

		catch (Exception e) {
			logger.error("An exception occurred.", e);
			return json;
		}
	}

	@Override
	public List<WorkBean> getWorksByAreaOfficer(Long userId) {
		List<Work> entityList = new ArrayList<>();
		List<WorkBean> beanlist = new ArrayList<>();
		try {

			if (userRepository.findById(userId).orElse(null).getDesignationID() == 1L) {

				entityList = workRepository.findActiveWorksByUser(userId);

			}
			for (Work w : entityList) {
				WorkBean convertWorkEntityToBean = convertWorkEntityToBean(w, "IdWise");

				if (w.getUserAssigneeDate() != null) {
					convertWorkEntityToBean.setAssignDate(w.getUserAssigneeDate());
				}

				if (w.getUserAssignee() != null) {
					String assignee = userRepository.findByUserAssinee(w.getUserAssignee());

					Users byUsername2 = userRepository.findByUsername(assignee);
					if (byUsername2 != null) {
						convertWorkEntityToBean
								.setUserAssigneeName(byUsername2.getFirstname() + byUsername2.getLastname());
					}

				}

				beanlist.add(convertWorkEntityToBean);

			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error getting  areaofficer", e);
		}
		return beanlist;
	}

	@Autowired
	private pointsrepository pointsrepo;

	@Override
	@Transactional
	public ResponseObject addGeoTaggingData(GeoTaggingBean bean) {

		ResponseObject responseObject = null;

		try {

			if (bean != null) {
				responseObject = new ResponseObject();
				WorkGeoLocation geoLocation = new WorkGeoLocation();

				geoLocation.setAddress(bean.getAddress());
				geoLocation.setAtProjectionLocation(bean.getAtProjectionLocation());
				// geoLocation.setLocationpoints(bean.getPoints());
				geoLocation.setWorkId(bean.getWorkId());
				geoLocation.setCurrentPoint(bean.getCurrentLocation());
				geoLocation.setWorkId(bean.getWorkId());
				WorkGeoLocation entity = geoLocationRepository.save(geoLocation);
				List<GTPoint> io = bean.getPoints();
				List<LocationPoints> iop = new ArrayList<>();
				for (GTPoint i : io) {
					LocationPoints po = new LocationPoints();
					po.setLattitude(i.getLattitude() + "");
					po.setLongitude(i.getLongitude() + "");
					po.setWorkID(bean.getWorkId());
					// pointsrepo.save(po);
					iop.add(po);

				}
				List<LocationPoints> save = pointsrepo.saveAll(iop);
				if (entity != null && save != null) {
					responseObject.setSuccessMessage("Geo Location Of WorkDone");
				} else {
					responseObject = null;
				}

				// System.err.println("adadasadsadasdasdasdasdas");

			}
			return responseObject;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			// throw new Exception(DMSConstants.ERROR_SAVING_DATA);
			logger.error("Error occurred while processing request" + e.getMessage());
			return null;
		}

	}

//        @Override
//        public GeoTaggingBean getGeoTaggingForWork(Long workId) {
//        GeoTaggingBean bean =  new GeoTaggingBean();
//        try {
//        	WorkGeoLocation geo = geoLocationRepository.findByworkId(workId).get(0);
//        	
//        	List<LocationPoints> lp =  pointsrepo.findAllByworkID(workId);
//        	
//        	if(lp !=null && geo !=null ) {
//        		
//        		bean.setAddress(geo.getAddress());
//        		bean.setAtProjectionLocation(geo.getAtProjectionLocation());
//        		bean.setCurrentLocation(geo.getCurrentPoint());
//        		bean.setWorkId(geo.getWorkId());
//        		
//        		List<LocationPoints> listpoint = lp;
//        		List<GTPoint> points2 =  new  ArrayList<>();
//        		for(LocationPoints  points : lp) {
//        			GTPoint  bean2 = new GTPoint();
//        			bean2.setLattitude(points.getLattitude());
//        			bean2.setLongitude(points.getLongitude());
//        			points2.add(bean2);
//        			
//        		}
//        		bean.setPoints(points2);
//        		
//        	}
//        	
//        	
//        	
//		} catch (Exception e) {
//			// TODO: handle exception
//			logger.error("error getting Data",e );
//			
//		}
//        
//        
//        
//        
//        return bean;
//}

	@Override
	public GeoTaggingBean getGeoTaggingForWork(Long workId) {
		GeoTaggingBean bean = new GeoTaggingBean();
		try {
			List<WorkGeoLocation> geoList = geoLocationRepository.findByworkId(workId);
			List<LocationPoints> lp = pointsrepo.findAllByworkID(workId);

			if (geoList != null && !geoList.isEmpty()) {
				WorkGeoLocation geo = geoList.get(0);

				bean.setAddress(geo.getAddress());
				bean.setAtProjectionLocation(geo.getAtProjectionLocation());
				bean.setCurrentLocation(geo.getCurrentPoint());
				bean.setWorkId(geo.getWorkId());

				if (lp != null && !lp.isEmpty()) {
					List<GTPoint> points2 = new ArrayList<>();
					for (LocationPoints points : lp) {
						GTPoint bean2 = new GTPoint();
						bean2.setLattitude(points.getLattitude());
						bean2.setLongitude(points.getLongitude());
						points2.add(bean2);
					}
					bean.setPoints(points2);
				}
			}

		} catch (Exception e) {
			logger.error("error getting Data", e);
		}

		return bean;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	synchronized public ResponseObject addWorkProgressDataMobile(
			WorkProgressDataMoibleBean uplDocumentUploadWorkProgressBean) throws Exception {

		ResponseObject responseObject = null;

		try {
			if (uplDocumentUploadWorkProgressBean != null) {
				if (uplDocumentUploadWorkProgressBean.getWorkId() == null) {
					throw new Exception("Work id is required");
				}
				if (uplDocumentUploadWorkProgressBean.getWorkStatusId() == null) {
					throw new Exception("Work status is required");
				}
				Work work = workRepository.findById(uplDocumentUploadWorkProgressBean.getWorkId()).orElse(null);
				if (work == null) {
					throw new Exception("Work not found");
				}
				WorkStatus workStatus = workStatusRepository
						.findById(uplDocumentUploadWorkProgressBean.getWorkStatusId()).orElse(null);
				if (workStatus == null) {
					throw new Exception("Work status not found");
				}

				WorkProgress progressentity = workProgressRepository
						.findByWorkId(uplDocumentUploadWorkProgressBean.getWorkId());

				if (progressentity == null) {
					progressentity = new WorkProgress();
				}

				progressentity.setWork(work);
				progressentity.setWorkStatusId(workStatus.getId());
				if (uplDocumentUploadWorkProgressBean.getWorkSubStatusId() != null) {
					progressentity.setWorkSubStatusId(uplDocumentUploadWorkProgressBean.getWorkSubStatusId().intValue());
				}
				if (uplDocumentUploadWorkProgressBean.getPerc() != null) {
					progressentity.setPerc(uplDocumentUploadWorkProgressBean.getPerc());
				}
				if (uplDocumentUploadWorkProgressBean.getWorkRemakrsM() != null) {
					progressentity.setWorkremarksM(uplDocumentUploadWorkProgressBean.getWorkRemakrsM());
				}

				workProgressRepository.save(progressentity);

				if (uplDocumentUploadWorkProgressBean != null) {
					responseObject = new ResponseObject();
					work.setWorkStatus(uplDocumentUploadWorkProgressBean.getWorkStatusId());
					workRepository.save(work);
					WorkProgress workprogress = workProgressRepository
							.findByWorkId(uplDocumentUploadWorkProgressBean.getWorkId());
					if (workprogress == null) {
						workprogress = new WorkProgress();
						workprogress.setWork(work);
					}
					if (uplDocumentUploadWorkProgressBean.getWorkSubStatusId() != null) {
						workprogress.setWorkSubStatusId(uplDocumentUploadWorkProgressBean.getWorkSubStatusId().intValue());
					}
					workprogress.setWorkStatusId(uplDocumentUploadWorkProgressBean.getWorkStatusId());
					workProgressRepository.save(workprogress);
					Integer remarkscount = -1;
					boolean hasSavedDocument = false;
					// Iterate over the list of files (List<MultipartFile>)
					if (uplDocumentUploadWorkProgressBean.getUploadFile() != null
							&& !uplDocumentUploadWorkProgressBean.getUploadFile().isEmpty()) {
						// Iterate through each file in the list
						for (MultipartFile file : uplDocumentUploadWorkProgressBean.getUploadFile()) {
							if (file == null || file.isEmpty()) {
								continue;
							}
							logger.info("Processing file: " + file.getOriginalFilename());
							remarkscount += 1;
							// Upload document (Assuming DMSUtil.uploadWorkProgressDocument method)
							DocumentUploadWorkProgress documentUpload = DMSUtil.uploadWorkProgressDocument(
									documentRootPath + workWorkProgressDocumentPath,
									uplDocumentUploadWorkProgressBean.getWorkId(), file, null, "blank",
									uplDocumentUploadWorkProgressBean.getWorkSubStatusId(),
									uplDocumentUploadWorkProgressBean.getWorkStatusId());
							if (documentUpload == null) {
								throw new Exception("Unable to upload progress document");
							}

							// Set work status
							documentUpload.setWorkStatusId(workStatus.getId());
							documentUpload.setWorkStatusNameE(workStatus.getWorkStatusNameE());
							if (uplDocumentUploadWorkProgressBean.getRemarks() != null
									&& uplDocumentUploadWorkProgressBean.getRemarks().size() > remarkscount
									&& uplDocumentUploadWorkProgressBean.getRemarks().get(remarkscount) != null) {
								documentUpload
										.setRemarks(uplDocumentUploadWorkProgressBean.getRemarks().get(remarkscount));
							}

							documentUpload.setWorkSubStatusId(uplDocumentUploadWorkProgressBean.getWorkSubStatusId());
							documentUpload.setPerc(uplDocumentUploadWorkProgressBean.getPerc());
							documentUpload.setAddress(uplDocumentUploadWorkProgressBean.getAddress());
							documentUpload.setLattitude(uplDocumentUploadWorkProgressBean.getLattitude());
							documentUpload.setLongitude(uplDocumentUploadWorkProgressBean.getLongitude());
							documentUpload.setEnabled((short) 1);
							if (documentUpload.getCreatedDate() == null) {
								documentUpload.setCreatedDate(new Date());
							}
							// Save the document

							documentUploadWorkProgressRepository.save(documentUpload);
							hasSavedDocument = true;
							// workRepository.findById(uplDocumentUploadWorkProgressBean.getWorkId()).orElse(null);
						}

						// Set the response ID
						responseObject.setId(uplDocumentUploadWorkProgressBean.getWorkId());
					}
					if (!hasSavedDocument) {
						// If no valid file is uploaded, keep a progress history row for the saved mobile form.
						DocumentUploadWorkProgress documentUpload1 = new DocumentUploadWorkProgress();
						documentUpload1.setWorkId(uplDocumentUploadWorkProgressBean.getWorkId());
						documentUpload1.setEnabled((short) 1);
						documentUpload1.setCreatedDate(new Date());
						documentUpload1.setWorkSubStatusId(uplDocumentUploadWorkProgressBean.getWorkSubStatusId());
						documentUpload1.setActionTakenDelay(uplDocumentUploadWorkProgressBean.getActionTakenDelay());
						documentUpload1.setWorkStatusId(workStatus.getId());
						documentUpload1.setAddress(uplDocumentUploadWorkProgressBean.getAddress());
						documentUpload1.setLattitude(uplDocumentUploadWorkProgressBean.getLattitude());
						documentUpload1.setLongitude(uplDocumentUploadWorkProgressBean.getLongitude());
						documentUpload1.setWorkStatusNameE(workStatus.getWorkStatusNameE());
						if (uplDocumentUploadWorkProgressBean.getRemarks() != null
								&& !uplDocumentUploadWorkProgressBean.getRemarks().isEmpty()) {
							documentUpload1.setRemarks(uplDocumentUploadWorkProgressBean.getRemarks().get(0));
						} else if (uplDocumentUploadWorkProgressBean.getWorkremarks() != null) {
							documentUpload1.setRemarks(uplDocumentUploadWorkProgressBean.getWorkremarks());
						}
						documentUpload1.setPerc(uplDocumentUploadWorkProgressBean.getPerc());
						documentUpload1
								.setWorkSubDelayReasonId(uplDocumentUploadWorkProgressBean.getWorkSubDelayReasonId());
						documentUploadWorkProgressRepository.save(documentUpload1);
						responseObject.setId(uplDocumentUploadWorkProgressBean.getWorkId());
					}

				}
			}
			return responseObject;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			throw new Exception(DMSConstants.ERROR_SAVING_DATA);
		}
	}

	@Override
	public List<DocumentUploadWorkProgressBean> fetchProgressImagesGroupList(Long workId, Date createdDate,
			String fullUrl) {
		List<DocumentUploadWorkProgressBean> beanlist = new ArrayList<>();
		try {
			List<DocumentUploadWorkProgress> wpPage = documentUploadWorkProgressRepository
					.findByWorkIdAndCreatedDate(workId, createdDate);
			int index = 0;
			for (DocumentUploadWorkProgress documentUploadWorkProgress : wpPage) {
				DocumentUploadWorkProgressBean bean = new DocumentUploadWorkProgressBean();
				bean.setIndexWS(++index);
				bean.setDocumentId(documentUploadWorkProgress.getDocumentId());
				bean.setDocumentName(documentUploadWorkProgress.getDocumentName());
				bean.setCreatedDate(documentUploadWorkProgress.getCreatedDate());
				if (documentUploadWorkProgress.getWorkSubStatusId() != null) {
					WorkSubStatus workSubStatus = workSubStatusRepository
							.findByWorkSubStatusId(documentUploadWorkProgress.getWorkSubStatusId());
					bean.setWorkSubStatusId(workSubStatus.getWorkSubStatusId());
					if (documentUploadWorkProgress.getWorkStatusNameE().equals("In-Progress")) {
						bean.setWorkSubStatusNameE(workSubStatus.getWorkSubStatusNameE());
						bean.setReasonDelay("-");
					} else {
						bean.setReasonDelay(workSubStatus.getWorkSubStatusNameE());
						bean.setWorkSubStatusNameE("-");
					}

				}
				if (documentUploadWorkProgress.getWorkStatusId() != null) {
					bean.setWorkStatusId(documentUploadWorkProgress.getWorkStatusId());
					bean.setWorkStatusNameE(documentUploadWorkProgress.getWorkStatusNameE());
				}

				if (documentUploadWorkProgress.getActionTakenDelay() != null) {
					bean.setActionTakenDelay(documentUploadWorkProgress.getActionTakenDelay());
				} else {
					bean.setActionTakenDelay("-");
				}

				if (documentUploadWorkProgress.getWorkSubDelayReasonId() != null) {
					WorkSubDelayReson subDelay = workSubDelayResonRepository
							.findById(documentUploadWorkProgress.getWorkSubDelayReasonId()).orElse(null);
					bean.setWorkSubDelayReason(subDelay.getSubDelayReason());
				} else {
					bean.setWorkSubDelayReason("-");
				}

				if (documentUploadWorkProgress.getRemarks() != null) {
					bean.setRemarks(documentUploadWorkProgress.getRemarks());
				} else {
					bean.setRemarks("-");
				}
				bean.setLattitude(documentUploadWorkProgress.getLattitude());
				bean.setLongitude(documentUploadWorkProgress.getLongitude());
				bean.setAddress(documentUploadWorkProgress.getAddress());
				bean.setImagepath(imageUrl + "anuppur/mobile/downloadDocumentWSPro/" + bean.getDocumentId());

				/*
				 * List<DocumentUploadWorkProgress> byWorkIdAndCreatedDate =
				 * documentUploadWorkProgressRepository .findByWorkIdAndCreatedDate(workId,
				 * documentUploadWorkProgress.getCreatedDate());
				 * 
				 * bean.setMoreImage(byWorkIdAndCreatedDate.size() > 1);
				 */
				beanlist.add(bean);
			}

		} catch (Exception e) {
			// TODO: handle exception
			beanlist = null;
		}
		return beanlist;
	}

	public ResponseEntity<InputStreamResource> downloadImagesAsZip(Long workId, Date createdDate) {
		try {
			// Fetch all documents for the given workId and createdDate
			List<DocumentUploadWorkProgress> wpPage = documentUploadWorkProgressRepository
					.findByWorkIdAndCreatedDate(workId, createdDate);

			// Create a temporary file to store the zip
			Path zipDir = Paths.get(tempZipDir);

			// Ensure directory exists with safe permissions
			if (!Files.exists(zipDir)) {
				Files.createDirectories(zipDir);
			}

			Path tempZipFile = Files.createTempFile(zipDir, "images-", ".zip");

			// Create a ZipOutputStream to write files into the zip
			try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(tempZipFile.toFile()))) {

				// Loop through each document and add its corresponding image to the zip
				for (DocumentUploadWorkProgress document : wpPage) {
					String fileName = document.getDocumentName();
					String imagePath = documentRootPath + workWorkProgressDocumentPath + fileName;

					// Create a file object for the image
					File imageFile = new File(imagePath);

					if (imageFile.exists()) {
						try (FileInputStream fis = new FileInputStream(imageFile)) {
							// Create a ZipEntry with the image file name (you can use the original name or
							// modify it)
							ZipEntry zipEntry = new ZipEntry(fileName);
							zipOut.putNextEntry(zipEntry);

							// Write the content of the image file into the zip
							FileCopyUtils.copy(fis, zipOut);

							// Close the current entry in the zip
							zipOut.closeEntry();
						}
					}
				}
			}

			// Return the zip file as a response for download
			InputStreamResource resource = new InputStreamResource(new FileInputStream(tempZipFile.toFile()));
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=images.zip")
					.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);

		} catch (IOException e) {
			// Handle the exception (e.g., file not found, IO issues)
			logger.error("Error occurred while processing request" + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@Override
	public List<DocumentUploadWorkProgressBean> fetchImagesGroupListforzipfile(Long documentId) {
		List<DocumentUploadWorkProgressBean> beanlist = new ArrayList<>();

		try {
			DocumentUploadWorkProgress doc = documentUploadWorkProgressRepository.findById(documentId).orElse(null);
			List<DocumentUploadWorkProgress> wpPage = documentUploadWorkProgressRepository
					.findByWorkIdAndCreatedDate(doc.getWorkId(), doc.getCreatedDate());
			int index = 0;
			for (DocumentUploadWorkProgress documentUploadWorkProgress : wpPage) {
				DocumentUploadWorkProgressBean bean = new DocumentUploadWorkProgressBean();
				bean.setIndexWS(++index);
				bean.setDocumentId(documentUploadWorkProgress.getDocumentId());
				bean.setDocumentName(documentUploadWorkProgress.getDocumentName());
				bean.setCreatedDate(documentUploadWorkProgress.getCreatedDate());
				if (documentUploadWorkProgress.getWorkSubStatusId() != null) {
					WorkSubStatus workSubStatus = workSubStatusRepository
							.findByWorkSubStatusId(documentUploadWorkProgress.getWorkSubStatusId());
					bean.setWorkSubStatusId(workSubStatus.getWorkSubStatusId());
					if (documentUploadWorkProgress.getWorkStatusNameE().equals("In-Progress")) {
						bean.setWorkSubStatusNameE(workSubStatus.getWorkSubStatusNameE());
						bean.setReasonDelay("-");
					} else {
						bean.setReasonDelay(workSubStatus.getWorkSubStatusNameE());
						bean.setWorkSubStatusNameE("-");
					}

				}
				if (documentUploadWorkProgress.getWorkStatusId() != null) {
					bean.setWorkStatusId(documentUploadWorkProgress.getWorkStatusId());
					bean.setWorkStatusNameE(documentUploadWorkProgress.getWorkStatusNameE());
				}

				if (documentUploadWorkProgress.getActionTakenDelay() != null) {
					bean.setActionTakenDelay(documentUploadWorkProgress.getActionTakenDelay());
				} else {
					bean.setActionTakenDelay("-");
				}

				if (documentUploadWorkProgress.getWorkSubDelayReasonId() != null) {
					WorkSubDelayReson subDelay = workSubDelayResonRepository
							.findById(documentUploadWorkProgress.getWorkSubDelayReasonId()).orElse(null);
					bean.setWorkSubDelayReason(subDelay.getSubDelayReason());
				} else {
					bean.setWorkSubDelayReason("-");
				}

				if (documentUploadWorkProgress.getRemarks() != null) {
					bean.setRemarks(documentUploadWorkProgress.getRemarks());
				} else {
					bean.setRemarks("-");
				}
				bean.setLattitude(documentUploadWorkProgress.getLattitude());
				bean.setLongitude(documentUploadWorkProgress.getLongitude());
				bean.setAddress(documentUploadWorkProgress.getAddress());
				bean.setImagepath(
						imageUrl.substring(0, 21) + "/anuppur/mobile/downloadDocumentWSPro/" + bean.getDocumentId());

				// List<DocumentUploadWorkProgress> byWorkIdAndCreatedDate =
				// documentUploadWorkProgressRepository.findByWorkIdAndCreatedDate(workId,
				// documentUploadWorkProgress.getCreatedDate());

				// bean.setMoreImage(byWorkIdAndCreatedDate.size()>1);
				beanlist.add(bean);
			}

		} catch (Exception e) {
			// TODO: handle exception
			beanlist = null;
		}
		return beanlist;
	}

	@Override
	@PreAuthorize("hasRole('ROLE_DM') and @workAuthorization.canAccessWork(#p0.workId)")
	public String addOrUpdateDmRemark(DmRemarksBean bean) {
		try {

			if (bean == null) {
				return "error: Invalid input";
			}
			DmRemarks entity;

			if (bean.getId() != null) {
				// ?? Update case
				entity = dmRemarksRepository.findById(bean.getId()).orElse(null);
			} else {
				// ?? New record
				entity = new DmRemarks();
			}
			if (bean.getDmattachment() != null) {
				DocumentUpload documentUpload = DMSUtil.uploadDMAttachment(documentRootPath + dmAttachment, "blank",
						bean.getDmattachment(), null, "blank");

				// documentUpload.setDocumentUploadPath(documentRootPath+dmAttachment+"");
				documentRepository.save(documentUpload);

				entity.setDocumentUpload(documentUpload);

			}
			entity.setRemark(bean.getRemark() != null && !bean.getRemark().trim().isEmpty() ? bean.getRemark() : null);

			entity.setDepartmentRemarks(
					bean.getDepartmentRemarks() != null && !bean.getDepartmentRemarks().trim().isEmpty()
							? bean.getDepartmentRemarks()
							: null);

			if (bean.getWorkId() != null) {
				entity.setWorkId(Long.valueOf(bean.getWorkId()));
			}

			if (bean.getDepertmentMasterId() != null && bean.getDepertmentMasterId() == 0L) {
				bean.setDepertmentMasterId(null);
			}
			entity.setDepertmentMasterId(bean.getDepertmentMasterId());
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
			entity.setCreated_time(sdf.format(new Date()));
			entity.setEnabled((short) 1);
			dmRemarksRepository.save(entity);

			return "success";

		} catch (Exception e) {
			logger.error("Error while saving DM Remark: {}", e.getMessage(), e);
			return "error: " + e.getMessage();
		}

	}

	@Override
	@PreAuthorize("hasRole('ROLE_DEPARTMENT') and @workAuthorization.canAccessWork(#p0.workId)")
	public String addOrUpdateDepartmentRemark(DepartmentRemarksBean bean) {
		try {

			if (bean == null) {
				return "error: Invalid input";
			}

			if (bean.getDepertmentMasterId() == null || bean.getDepertmentMasterId() <= 0) {
				return "error: Please select Department Remarks";
			}

			if (bean.getDepertmentMasterId() == 5L) {
				if (bean.getDepartmentRemarkName() == null || bean.getDepartmentRemarkName().trim().isEmpty()) {
					return "error: Please enter remarks";
				}
			}

			DepartmentRemarks entity;

			if (bean.getId() != null) {
				// ?? Update case
				entity = departmentRemarksRepository.findById(bean.getId()).orElse(null);
			} else {
				// ?? New record
				entity = new DepartmentRemarks();
			}
			if (bean.getDmattachment() != null) {
				DocumentUpload documentUpload = DMSUtil.uploadDMAttachment(documentRootPath + dmAttachment, "blank",
						bean.getDmattachment(), null, "blank");

				// documentUpload.setDocumentUploadPath(documentRootPath+dmAttachment+"");
				documentRepository.save(documentUpload);

				entity.setDocumentUpload(documentUpload);

			}

			entity.setDepartmentRemarkName(
					bean.getDepartmentRemarkName() != null && !bean.getDepartmentRemarkName().trim().isEmpty()
							? bean.getDepartmentRemarkName()
							: null);

			if (bean.getWorkId() != null) {
				entity.setWorkId(Long.valueOf(bean.getWorkId()));
			}

			if (bean.getDepertmentMasterId() != null && bean.getDepertmentMasterId() == 0L) {
				bean.setDepertmentMasterId(null);
			}
			entity.setDepertmentMasterId(bean.getDepertmentMasterId());
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
			entity.setCreated_time(sdf.format(new Date()));
			entity.setEnabled((short) 1);
			departmentRemarksRepository.save(entity);

			return "success";

		} catch (Exception e) {
			logger.error("Error while saving DM Remark: {}", e.getMessage(), e);
			return "error: " + e.getMessage();
		}

	}

	@Override
	public List<DmRemarksBean> getAllRemarksByWorkID(Long long1) {

		List<DmRemarksBean> beanlist = new ArrayList<>();

		List<DmRemarks> remakrs = dmRemarksRepository.findByworkIdAndEnabled(long1, (short) 1);
		int index = 1;

		for (DmRemarks s : remakrs) {
			DmRemarksBean bean = new DmRemarksBean();
			bean.setIndex(index);
			index++;
			bean.setRemark(s.getRemark() != null ? s.getRemark() : "-");
			bean.setDepartmentRemarks(s.getDepartmentRemarks() != null ? s.getDepartmentRemarks() : "-");
			if (s.getDepertmentMasterId() != null) {
				DepartmentMaster departmentMaster = departmentMasterRepository.findById(s.getDepertmentMasterId())
						.orElse(null);
				if (departmentMaster != null) {
					bean.setDepertmentMasterId(departmentMaster.getId());
					bean.setDepartmentName(departmentMaster.getName() != null ? departmentMaster.getName() : "-");
				}
			}
			bean.setId(s.getId());
			bean.setCreatedDate(dmRemarksRepository.findCreatedDateByWorkId(Long.parseLong(s.getId() + "")));
			if (s.getDocumentUpload() != null) {
				bean.setDocumentId(s.getDocumentUpload().getDocumentId());
			}
			bean.setDocumentPath(CCDocumentPath);
			if (s.getCreatedBy() != null) {
				Users users = userRepository.findByUsername(s.getCreatedBy());
				if (users != null) {
					bean.setCreateBy(users.getFirstname() + " " + users.getLastname());
					Set<Role> role = users.getRoles();
					for (Role r : role) {
						bean.setRole(r.getRoleName());
						bean.setRoleCode(r.getRoleCode());
					}
				}
			}
			beanlist.add(bean);
		}

		return beanlist;
	}

	@Override
	public List<DepartmentRemarksBean> getAllDepartmentRemarksByWorkID(Long long1) {

		List<DepartmentRemarksBean> beanlist = new ArrayList<>();

		List<DepartmentRemarks> remakrs = departmentRemarksRepository.findByworkIdAndEnabled(long1, (short) 1);
		int index = 1;

		for (DepartmentRemarks s : remakrs) {
			DepartmentRemarksBean bean = new DepartmentRemarksBean();
			bean.setIndex(index);
			index++;
//			if(bean.getWorkId() != null) {
//			DmRemarks dmRemarks = dmRemarksRepository.findByWorkId(bean.getWorkId());
//			if(dmRemarks != null) {
//				bean.setRemark(dmRemarks.getRemark() != null ? dmRemarks.getRemark() : "-" );
			bean.setWorkId(s.getWorkId());

			if (s.getWorkId() != null) {
				List<DmRemarks> dmRemarks = dmRemarksRepository.findByWorkId(s.getWorkId());

				if (dmRemarks != null && !dmRemarks.isEmpty()) {

					StringBuilder remarkBuilder = new StringBuilder();

					for (DmRemarks dmRemarks2 : dmRemarks) {
						if (dmRemarks2.getRemark() != null) {
							remarkBuilder.append(dmRemarks2.getRemark());

						}
					}

					bean.setRemark(remarkBuilder.toString());
				}
			}

			// bean.setRemark(s.getRemark() != null ? s.getRemark() : "-" );
			// bean.setDepartmentRemarks(s.getDepartmentRemarks() != null ?
			// s.getDepartmentRemarks() : "-");

			bean.setDepartmentRemarkName(s.getDepartmentRemarkName());

			if (s.getDepertmentMasterId() != null) {
				DepartmentMaster departmentMaster = departmentMasterRepository.findById(s.getDepertmentMasterId())
						.orElse(null);
				if (departmentMaster != null) {
					bean.setDepertmentMasterId(departmentMaster.getId());
					bean.setDepartmentName(departmentMaster.getName() != null ? departmentMaster.getName() : "-");
				}
			}
			bean.setId(s.getId());
			bean.setCreatedDate(departmentRemarksRepository.findCreatedDateByWorkId(Long.parseLong(s.getId() + "")));
			if (s.getDocumentUpload() != null) {
				bean.setDocumentId(s.getDocumentUpload().getDocumentId());
			}
			bean.setDocumentPath(CCDocumentPath);
			if (s.getCreatedBy() != null) {
				Users users = userRepository.findByUsername(s.getCreatedBy());
				if (users != null) {
					bean.setCreateBy(users.getFirstname() + " " + users.getLastname());
					Set<Role> role = users.getRoles();
					for (Role r : role) {
						bean.setRole(r.getRoleName());
						bean.setRoleCode(r.getRoleCode());
					}
				}
			}

			beanlist.add(bean);
		}

		return beanlist;

	}

	@Override
	@PreAuthorize("hasRole('ROLE_DM') and @workAuthorization.canDeleteDmRemark(#p0)")
	public Boolean deleteRemarks(Long long1) {
		DmRemarks one = dmRemarksRepository.findById(long1).orElse(null);

		if (one != null) {
			one.setEnabled((short) 0);
			DmRemarks save = dmRemarksRepository.save(one);
			if (save.getId() != null) {
				return true;
			}
		}
		return false;
	}
   @Transactional
	@Override
	@PreAuthorize("hasRole('ROLE_DEPARTMENT') and @workAuthorization.canDeleteDepartmentRemark(#p0)")
	public Boolean deleteDepartmentRemarks(Long long1) {
		DepartmentRemarks one = departmentRemarksRepository.findById(long1).orElseThrow(() -> new RuntimeException("Department Remarks not found"));

		// if (one != null) {
			one.setEnabled((short) 0);
			DepartmentRemarks save = departmentRemarksRepository.save(one);
			if (save.getId() != null) {
				return true;
			}
		// }
		return false;
	}

	@Override
	public DmRemarksBean getRemakrsDetails(Long long1) {
		DmRemarksBean bean = new DmRemarksBean();
		DmRemarks one = dmRemarksRepository.findById(long1).orElse(null);
		bean.setRemark(one.getRemark());
		bean.setDepartmentRemarks(one.getDepartmentRemarks());
		if (one.getDocumentUpload() != null) {
			bean.setDocumentId(one.getDocumentUpload().getDocumentId());
		}
		bean.setId(one.getId());

		if (one.getCreatedBy() != null) {
			Users users = userRepository.findByUsername(one.getCreatedBy());
			if (users != null) {
				bean.setCreateBy(users.getFirstname() + " " + users.getLastname());
				Set<Role> role = users.getRoles();
				for (Role r : role) {
					bean.setRole(r.getRoleName());
					bean.setRoleCode(r.getRoleCode());
				}
			}
		}
		if (null != one.getDepertmentMasterId()) {
			DepartmentMaster departmentMaster = departmentMasterRepository.findById(one.getDepertmentMasterId())
					.orElse(null);
			if (null != departmentMaster) {
				bean.setDepertmentMasterId(departmentMaster.getId());
				bean.setDepartmentName(departmentMaster.getName());
			}

		}
		return bean;
	}

	@Override
	public DepartmentRemarksBean getDepartmentRemarksDetailsById(Long long1) {
		DepartmentRemarksBean bean = new DepartmentRemarksBean();
		DepartmentRemarks one = departmentRemarksRepository.findById(long1).orElse(null);
		System.err.println(one.getWorkId() + "<------------- work id null");
		if (one.getWorkId() != null) {
			List<DmRemarks> dmRemarks = (List<DmRemarks>) dmRemarksRepository.findByWorkId(one.getWorkId());

			if (dmRemarks != null && !dmRemarks.isEmpty()) {

				StringBuilder remarkBuilder = new StringBuilder();

				for (DmRemarks dmRemarks2 : dmRemarks) {
					if (dmRemarks2.getRemark() != null) {
						remarkBuilder.append(dmRemarks2.getRemark()).append("");
					}
				}

				bean.setRemark(remarkBuilder.toString());
			}
		}

		// bean.setRemark(one.getRemark());
		bean.setDepartmentRemarkName(one.getDepartmentRemarkName());
		if (one.getDocumentUpload() != null) {
			bean.setDocumentId(one.getDocumentUpload().getDocumentId());
		}
		bean.setId(one.getId());

		if (one.getCreatedBy() != null) {
			Users users = userRepository.findByUsername(one.getCreatedBy());
			if (users != null) {
				bean.setCreateBy(users.getFirstname() + " " + users.getLastname());
				Set<Role> role = users.getRoles();
				for (Role r : role) {
					bean.setRole(r.getRoleName());
					bean.setRoleCode(r.getRoleCode());
				}
			}
		}
		if (null != one.getDepertmentMasterId()) {
			DepartmentMaster departmentMaster = departmentMasterRepository.findById(one.getDepertmentMasterId())
					.orElse(null);
			if (null != departmentMaster) {
				bean.setDepertmentMasterId(departmentMaster.getId());
				bean.setDepartmentName(departmentMaster.getName());
			}

		}
		return bean;
	}

	@Override
	public List<UserBean> fetchAreaOfficerListByWorkId(Long workid) {
		List<AreaOfficerRecord> findbyWorkid = areaOfficerRecordRepository.findAllByWorkId(workid);
		List<UserBean> userlist = new ArrayList<>();
		AreaOfficerRecord aa = areaOfficerRecordRepository
				.findByUserid(workRepository.findById(workid).orElse(null).getUserAssignee());
		if (aa != null) {
			UserBean convertUserEntityToBean2 = convertUserEntityToBean(
					userRepository.findById(aa.getUserid()).orElse(null));
			convertUserEntityToBean2.setAssignDate(aa.getModifiedDate().toString());
			userlist.add(convertUserEntityToBean2);
		}
		for (AreaOfficerRecord a : findbyWorkid) {
			UserBean convertUserEntityToBean = convertUserEntityToBean(
					userRepository.findById(a.getUserid()).orElse(null));
			convertUserEntityToBean.setAssignDate(a.getModifiedDate().toString());

			if (!a.getUserid().equals(workRepository.findById(workid).orElse(null).getUserAssignee())) {
				{
					userlist.add(convertUserEntityToBean);
				}

			}
			// Collections.reverse(userlist);
		}
		return userlist;

	}

	public UserBean convertUserEntityToBean(Users user) {

		UserBean bean = new UserBean();

		if (user != null) {
			bean.setId(user.getId());
			bean.setDesignationId(user.getDesignationID());
			// bean.setName(user.getName());
			bean.setUsername(user.getUsername());
			bean.setEmailId(user.getEmailId());
			bean.setMobileNo(user.getMobileNo());
			bean.setFirstName(user.getFirstname());
			bean.setLastName(user.getLastname());
			// bean.setOfficialEmailId(user.getOfficialEmailId());
			// bean.setOfficialPhone(user.getOfficialPhone());

			bean.setDepartmentName(user.getDepartmentName());

			bean.setStatus(user.getStatus());
			bean.setOldStatus(user.getStatus());

			Set<Role> roles = user.getRoles();
			if (roles != null && !roles.isEmpty()) {
				for (Role role : roles) {
					bean.setRole(convertRoleEntityToBean(role));
				}
			} else {
				bean.setRole(new RoleBean("", ""));
			}
			bean.setRolee(bean.getRole().getRoleCode());
			bean.setDistrictId(user.getDistrict() != null ? user.getDistrict().getDistrictId() : 0);
			bean.setDistrictName(user.getDistrict() != null ? user.getDistrict().getDistrictName() : "-");
			bean.setDivisionId(user.getDivision() != null ? user.getDivision().getDivisionId() : 0);
			bean.setDivisionName(user.getDivision() != null ? user.getDivision().getDivisionName() : "-");

			bean.setUserTypeId(user.getUserType() != null ? user.getUserType().getId() : 0);
			bean.setUserTypeName(user.getUserType() != null ? user.getUserType().getUserType() : "-");

			bean.setOfficeTypeId(user.getOfficeType() != null ? user.getOfficeType().getId() : 0);
			bean.setOfficeTypeName(user.getOfficeType() != null ? user.getOfficeType().getOfficeTypeName() : "-");

			bean.setImplementationAgencyId(
					user.getImplementationAgency() != null ? user.getImplementationAgency().getImplementationAgencyId()
							: 0);
			bean.setImplementationAgencyName(
					user.getImplementationAgency() != null ? user.getImplementationAgency().getImplAgencyname() : "-");
			// System.err.println(user.getCreatedBy() + "asdasdasdasdas");
			/*
			 * if(userRepository.findByUsername(user.getCreatedBy())!=null) {
			 * 
			 * bean.setDepartmentName(userRepository.findByUsername(user.getCreatedBy()).
			 * getDepartmentName()); }
			 */
			if (user != null) {
				if (user.getDesignationID() == 3L) {
					bean.setDepartmentName("Collectorate");

				}
			}
		}
		return bean;
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
	public List<WorkPriorityBean> fetchWorkPriority() {
		try {
			List<WorkPriority> list = workPriorityRepository.findAll();

			List<WorkPriorityBean> beanList = new ArrayList<>();
			for (WorkPriority workPriority : list) {
				WorkPriorityBean workPriorityBean = new WorkPriorityBean();
				workPriorityBean.setId(workPriority.getId());
				workPriorityBean.setWorkPriorityName(workPriority.getWorkPriorityName());
				beanList.add(workPriorityBean);
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	@Override
	public List<FinancialHeadBean> fetchFinancialHead() {
		try {
			List<FinancialHead> list = financialHeadRepository.findAll();

			List<FinancialHeadBean> beanList = new ArrayList<>();
			for (FinancialHead financialHead : list) {
				FinancialHeadBean financialHeadBean = new FinancialHeadBean();
				financialHeadBean.setId(financialHead.getId());
				financialHeadBean.setFinancialHeadName(financialHead.getFinancialHeadName());
				beanList.add(financialHeadBean);
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	@Override
	public List<VidhanSabhaBean> fetchVidhanSabha() {
		try {
			List<VidhanSabha> list = vidhanSabhaRepositorys.findAll();

			List<VidhanSabhaBean> beanList = new ArrayList<>();
			for (VidhanSabha vidhanSabha : list) {
				VidhanSabhaBean vidhanSabhaBean = new VidhanSabhaBean();
				vidhanSabhaBean.setId(vidhanSabha.getId());
				vidhanSabhaBean.setVidhanSabhaName(vidhanSabha.getVidhanSabhaName());
				beanList.add(vidhanSabhaBean);
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred.", e);
			return null;
		}
	}

	@Override
	public List<DmRemarksBean> fetchDmRemarksList() {
		try {
			List<DmRemarks> list = dmRemarksRepository.findByEnabled((short) 1);

			List<DmRemarksBean> beanList = new ArrayList<>();
			Set<String> uniqueRemarks = new LinkedHashSet<>();

			for (DmRemarks dmRemark : list) {
				if (dmRemark.getDepartmentRemarks() != null && !dmRemark.getDepartmentRemarks().isEmpty()) {
					uniqueRemarks.add(dmRemark.getDepartmentRemarks());
				}
			}

			int id = 1;
			for (String remark : uniqueRemarks) {
				DmRemarksBean bean = new DmRemarksBean();
				bean.setId((long) id);
				bean.setDepartmentRemarks(remark);
				beanList.add(bean);
				id++;
			}
			return beanList;
		} catch (Exception e) {
			logger.error("An exception occurred while fetching DM remarks.", e);
			return new ArrayList<>();
		}
	}

	@Override
	public List<WorkBean> getFilteredWorkProgress(String workStatusStr, String userIdStr, String agencyIdStr,
			String workSubStatusStr, String workName) {

		// Convert string params to Long safely
		Long workStatus = parseLongOrNull(workStatusStr);
		Long userId = parseLongOrNull(userIdStr);
		Long agencyId = parseLongOrNull(agencyIdStr);
		Long workSubStatus = parseLongOrNull(workSubStatusStr);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		// Fetch data from repository
		List<Object[]> resultList = workRepository.fetchWorkWithProgressFilters(workStatus, userId, agencyId,
				workSubStatus, workName);
//	    System.err.println("List--- " + resultList.size());
		List<WorkBean> workProgressList = new ArrayList<>();

		int serialNo = 1; // ? for serial number

		for (Object[] row : resultList) {
			WorkBean dto = new WorkBean();
//	        System.err.println("row[2] ------------" + row[2]);
			// dto.setSerialNo(serialNo++); // Add serial number
			dto.setWorkNo((String) row[0]);
			dto.setWorkName((String) row[1]);
			// dto.setImplementationAgencyName(row[2] != null ? row[2].toString() : "N/A");
			if (row[2] != null) {
				Long agencyId1 = Long.valueOf(row[2].toString()); // Integer ? Long
				ImplementationAgency imp = implAgencyRepository.findById(agencyId1).orElse(null);
				dto.setImplementationAgencyName(imp != null ? imp.getImplAgencyname() : "N/A");
			} else {
				dto.setImplementationAgencyName("N/A");
			}

			if (row[3] != null) {
				Long userId1 = Long.valueOf(row[3].toString()); // Integer ? Long
				Users user = userRepository.findById(userId1).orElse(null);
				dto.setUserAssigneeName(user != null ? user.getFirstname() + " " + user.getLastname() : "N/A");
			} else {
				dto.setUserAssigneeName("N/A");
			}

			// dto.setUserAssigneeName(row[3] != null ? row[3].toString() : "N/A");
			if (row[4] != null) {
				Long statusId = Long.valueOf(row[4].toString()); // Integer ? Long
				WorkStatus workStatus1 = workStatusRepository.findById(statusId).orElse(null);
				dto.setWorkStatusName(workStatus1 != null ? workStatus1.getWorkStatusNameE() : "N/A");
			} else {
				dto.setWorkStatusName("N/A");
			}

			if (row[5] != null) {
				Long subStatusId = Long.valueOf(row[5].toString()); // Integer ? Long
				WorkSubStatus workSubStatus1 = workSubStatusRepository.findById(subStatusId).orElse(null);
				dto.setWorkSubTypeName(workSubStatus1 != null ? workSubStatus1.getWorkSubStatusNameE() : "N/A");
			} else {
				dto.setWorkSubTypeName("N/A");
			}
			// dto.setWorkStatusName(row[4] != null ? row[4].toString() : "N/A");
			if (row[6] != null) {
				Date modifiedDate = (Date) row[6];
				dto.setModifiedDate(sdf.format(modifiedDate)); // String field for formatted date
			} else {
				dto.setModifiedDate("N/A");
			}

			workProgressList.add(dto);
//	        System.err.println("dto------- " + dto);
		}

		return workProgressList;
	}

	@Override
	public List<WorkBean> getFilteredWorkWithLatestExpenses(String workStatusStr, String userIdStr, String agencyIdStr,
			String workName) {

		// Convert String parameters to Long safely
		Long workStatus = parseLongOrNull(workStatusStr);
		Long userId = parseLongOrNull(userIdStr);
		Long agencyId = parseLongOrNull(agencyIdStr);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		// Fetch data using the repository query with filters and latest (year, month)
		List<Object[]> resultList = workRepository.fetchWorkWithLatestExpensesFilters(workStatus, userId, agencyId,
				workName);

		List<WorkBean> workList = new ArrayList<>();

		for (Object[] row : resultList) {
			WorkBean bean = new WorkBean();

			// Work No
			bean.setWorkNo(row[0] != null ? row[0].toString() : "N/A");

			// Work Name
			bean.setWorkName(row[1] != null ? row[1].toString() : "N/A");

			// Implementation Agency (id stored in DB)
			if (row[2] != null) {
				try {
					Long implId = Long.valueOf(row[2].toString());
					ImplementationAgency agency = implAgencyRepository.findById(implId).orElse(null);
					bean.setImplementationAgencyName(agency != null ? agency.getImplAgencyname() : "N/A");
				} catch (Exception e) {
					bean.setImplementationAgencyName("N/A");
				}
			} else {
				bean.setImplementationAgencyName("N/A");
			}

			// User / Assignee
			if (row[3] != null) {
				try {
					Long userIdVal = Long.valueOf(row[3].toString());
					Users user = userRepository.findById(userIdVal).orElse(null);
					bean.setUserAssigneeName(user != null ? user.getFirstname() + " " + user.getLastname() : "N/A");
				} catch (Exception e) {
					bean.setUserAssigneeName("N/A");
				}
			} else {
				bean.setUserAssigneeName("N/A");
			}

			// PAC (Tender amount)
			bean.setPac(row[4] != null ? new BigDecimal(row[4].toString()) : BigDecimal.ZERO);

			// Total Expenses (from Work Progress)
			bean.setTotalExpensess(row[5] != null ? new BigDecimal(row[5].toString()) : BigDecimal.ZERO);

			// Total Expenses Upto March (latest year-month)
			bean.setLastExpenditure(row[6] != null ? new BigDecimal(row[6].toString()) : BigDecimal.ZERO);

			// Work Status
			if (row[7] != null) {
				Long statusId = Long.valueOf(row[7].toString()); // Integer ? Long
				WorkStatus workStatus1 = workStatusRepository.findById(statusId).orElse(null);
				bean.setWorkStatusName(workStatus1 != null ? workStatus1.getWorkStatusNameE() : "N/A");
			} else {
				bean.setWorkStatusName("N/A");
			}
			// bean.setWorkStatus(row[7] != null ? row[7].toString() : "N/A");

			// Created Date (if needed)
			if (row.length > 8 && row[8] != null) {
				if (row[8] != null) {
					Date modifiedDate = (Date) row[8];
					bean.setModifiedDate(sdf.format(modifiedDate)); // String field for formatted date
				} else {
					bean.setModifiedDate("N/A");
				}
				// dto.setModifiedDate(row[5] != null ? (Date) row[5] : null);
			}

			workList.add(bean);
		}

		return workList;
	}

	// Utility method to safely parse String ? Long
	private Long parseLongOrNull(String value) {
		try {
			return (value != null && !value.trim().isEmpty()) ? Long.parseLong(value.trim()) : null;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	@Override
	public List<UserBean> fetchAssignedUsers() {

		List<Users> users = userRepository.findByDesignationIDAndStatus(1L, DMSConstants.STATUS_ACTIVE);

		List<UserBean> userBeans = new ArrayList<>();

		if (users != null && !users.isEmpty()) {
			for (Users user : users) {
				UserBean bean = new UserBean();
				bean.setId(user.getId());
				bean.setFirstName(user.getFirstname() + " " + user.getLastname());
				userBeans.add(bean);
			}
		}

		return userBeans;
	}

	@Override
	public List<UserBean> fetchAssignUser(Long implementationAgency) {

		List<Work> works = workRepository.findByImplementationAgency(implementationAgency);
		Set<Long> userIds = new HashSet<>();

		// Step 1: Collect unique non-null user IDs
		for (Work work : works) {
			if (work.getUserAssignee() != null) {
				userIds.add(work.getUserAssignee());
			}
		}

		List<UserBean> userBeans = new ArrayList<>();

		// Step 2: Fetch all unique users only once
		for (Long userId : userIds) {
			Users user = userRepository.findById(userId).orElse(null);
			if (user != null) {
				UserBean bean = new UserBean();
				bean.setId(user.getId());
				bean.setFirstName(user.getFirstname() + " " + user.getLastname());
				userBeans.add(bean);
			}
		}

		return userBeans;
	}

	@Override
	public List<String> getWorkNoSuggestions(String keyword) {
		if (keyword == null || keyword.trim().isEmpty()) {
			return new ArrayList<>();
		}
		return workRepository.searchWorkNoSuggestions(keyword.trim());
	}

	@Override
	public List<FinancialAgencyBean> fetchFinancialAgencyByWorkId(Long workId) {
		List<WorkFinancialAgency> financialAgencyList = financialAgencyRepository.findByWorkId(workId);

		List<FinancialAgencyBean> bean = new ArrayList<FinancialAgencyBean>();

		for (WorkFinancialAgency financialAgency : financialAgencyList) {
			FinancialAgencyBean financialAgencyBean = new FinancialAgencyBean();
			financialAgencyBean.setId(financialAgency.getId());
			financialAgencyBean.setCost(financialAgency.getCost());
			financialAgencyBean.setExpenditure(financialAgency.getExpenditure());
			if (financialAgency.getCost() != null && financialAgency.getExpenditure() != null) {
				financialAgencyBean.setBalance(financialAgency.getCost() - financialAgency.getExpenditure());
			}
			// System.err.println("financialAgency.getId()==== " + financialAgency.getId());
			if (financialAgency.getFinancialHeadId() != null) {
				FinancialHead financialAgencyName = financialHeadRepository
						.findById(financialAgency.getFinancialHeadId()).orElse(null);
				if (financialAgencyName != null) {

					financialAgencyBean.setFinancialAgencyName(financialAgencyName.getFinancialHeadName());
					financialAgencyBean.setWorkId(financialAgency.getWorkId());
					financialAgencyBean.setFinancialHeadId(financialAgencyName.getId());
				}
			}
			bean.add(financialAgencyBean);
		}
		return bean;
	}

	@Override
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM','ROLE_DEPARTMENT') and @workAuthorization.canAccessWork(#p2)")
	public String updateFinancialAgencyCost(Long id, Double expenditure, Long workId) {
		FinancialExpenditureRequest request = new FinancialExpenditureRequest();
		request.setId(id);
		request.setWorkId(workId);
		request.setExpenditure(expenditure == null ? null : BigDecimal.valueOf(expenditure));
		saveFinancialAgencyExpenditures(List.of(request));
		return "SUCCESS";
	}

	@Override
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMIN','ROLE_DM','ROLE_DEPARTMENT') "
			+ "and @workAuthorization.canEditFinancialRequests(#p0)")
	public void saveFinancialAgencyExpenditures(List<FinancialExpenditureRequest> requests) {
		Set<Long> affectedWorkIds = financialValidationService.applyFinancialAgencyExpenditures(requests);
		for (Long workId : affectedWorkIds) {
			syncWorkProgressExpenditureFromFinancialAgency(workId);
		}
	}

	@Override
	public Double sumFinancialAgencyExpenditureByWorkId(Long workId) {
		if (workId == null) {
			return 0.0;
		}
		Double total = financialAgencyRepository.sumExpenditureByWorkId(workId);
		return total != null ? total : 0.0;
	}

	@Override
	@Transactional
	public void syncWorkProgressExpenditureFromFinancialAgency(Long workId) {
		if (workId == null) {
			return;
		}
		Double agencyTotal = sumFinancialAgencyExpenditureByWorkId(workId);
		if (agencyTotal == null || agencyTotal <= 0) {
			return;
		}
		WorkProgress workProgress = workProgressRepository.findByWorkId(workId);
		if (workProgress != null) {
			workProgress.setTotalExpensess(BigDecimal.valueOf(agencyTotal));
			workProgressRepository.save(workProgress);
		}
	}

	@Override
	@Transactional
	public String deleteByFinancailAgencyId(Long id) {
		if (id == null) {
			return "Given id not found";
		}

		// Check if record exists
		boolean exists = financialAgencyRepository.existsById(id);
		if (!exists) {
			return "Record not found for id: " + id;
		}

		// Delete from DB
		financialAgencyRepository.deleteById(id);

		return "Financial Agency Deleted Successfully...";
	}

	@Override
	public List<FinancialAgencyBean> getFinancialAgenciesByWorkId(Long workId) {
		List<WorkFinancialAgency> agencies = financialAgencyRepository.findByWorkId(workId);
		List<FinancialAgencyBean> beans = new ArrayList<FinancialAgencyBean>();
		for (WorkFinancialAgency financialAgency : agencies) {
			FinancialAgencyBean agencyBean = new FinancialAgencyBean();
			if (financialAgency.getFinancialHeadId() != null) {
				FinancialHead financialHead = financialHeadRepository.findById(financialAgency.getFinancialHeadId())
						.orElse(null);
				if (financialHead != null) {
					agencyBean.setFinancialAgencyName(financialHead.getFinancialHeadName());
					agencyBean.setFinancialHeadId(financialHead.getId());
				}
			}

			agencyBean.setCost(financialAgency.getCost());
			beans.add(agencyBean);
		}
		return beans;
	}

	@Override
	public List<FinancialAgencyBean> getFinancialAgenciesExpenditureByWorkId(Long workId) {
		List<WorkFinancialAgency> agencies = financialAgencyRepository.findByWorkId(workId);
		List<FinancialAgencyBean> beans = new ArrayList<FinancialAgencyBean>();
		for (WorkFinancialAgency financialAgency : agencies) {
			FinancialAgencyBean agencyBean = new FinancialAgencyBean();
			if (financialAgency.getFinancialHeadId() != null) {
				FinancialHead financialHead = financialHeadRepository.findById(financialAgency.getFinancialHeadId())
						.orElse(null);
				if (financialHead != null) {
					agencyBean.setFinancialAgencyName(financialHead.getFinancialHeadName());
					agencyBean.setFinancialHeadId(financialHead.getId());
				}
			}

			agencyBean.setCost(financialAgency.getCost());
			if (financialAgency.getExpenditure() != null) {
				agencyBean.setExpenditure(financialAgency.getExpenditure());
			} else {
				agencyBean.setExpenditure(0.0);
			}
			beans.add(agencyBean);
		}
		return beans;
	}

	@Override
	public List<DepartmentMasterBean> fetchDepartmentMaster() {
		List<DepartmentMaster> departmentMastersList = departmentMasterRepository.findAll();
		List<DepartmentMasterBean> departmentMasterBeans = new ArrayList<DepartmentMasterBean>();

		for (DepartmentMaster departmentMasters : departmentMastersList) {
			DepartmentMasterBean bean = new DepartmentMasterBean();
			bean.setId(departmentMasters.getId());
			bean.setName(departmentMasters.getName());
			bean.setEnabled(departmentMasters.getEnabled());
			departmentMasterBeans.add(bean);
		}
		// System.err.println("Service------- " + departmentMasterBeans.size());
		return departmentMasterBeans;
	}

	@Override
	public List<DepartmentRemarksBean> fetchDepartmentRemarksList() {
		List<DepartmentRemarks> departmentRemarksList = departmentRemarksRepository.findAll();
		List<DepartmentRemarksBean> departmentRemarksBeans = new ArrayList<DepartmentRemarksBean>();

		for (DepartmentRemarks remark : departmentRemarksList) {
			DepartmentRemarksBean bean = new DepartmentRemarksBean();
			bean.setId(remark.getId());
			bean.setDepartmentRemarkName(remark.getDepartmentRemarkName());
			bean.setDepertmentMasterId(remark.getDepertmentMasterId());
			bean.setWorkId(remark.getWorkId());
			bean.setEnabled(remark.getEnabled());
			departmentRemarksBeans.add(bean);
		}
		return departmentRemarksBeans;
	}

	@Override
	public List<String> getWorkNameSuggestions(String keyword) {
		try {
			if (keyword == null || keyword.trim().isEmpty()) {
				return new ArrayList<>();
			}
			Pageable pageable = PageRequest.of(0, 100);
			Page<Work> works = workRepository.findByWorkNameContainingIgnoreCase(pageable, keyword);
			return works.getContent().stream().map(Work::getWorkName).distinct().collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Error fetching work name suggestions", e);
			return new ArrayList<>();
		}
	}

	@Override
	public List<BlockBean> getBlocksByDistrict(Long districtId) {
		try {
			if (districtId == null) {
				return new ArrayList<>();
			}
			District district = districtRepository.findById(districtId).orElse(null);
			if (district == null) {
				return new ArrayList<>();
			}
			List<Block> blocks = blockRepository.findByDistrictAndEnabledOrderByBlockName(district, (short) 1);
			List<BlockBean> blockBeans = new ArrayList<>();
			for (Block block : blocks) {
				BlockBean bean = new BlockBean();
				bean.setBlockId(block.getBlockId());
				bean.setBlockName(block.getBlockName());
				bean.setBlockCode(block.getBlockCode());
				blockBeans.add(bean);
			}
			return blockBeans;
		} catch (Exception e) {
			logger.error("Error fetching blocks by district", e);
			return new ArrayList<>();
		}
	}

	@Override
	public List<GeoTaggingBean> getGeoTaggingForWorkList(Long workId) {

		List<GeoTaggingBean> beanList = new ArrayList<>();

		try {
			List<WorkGeoLocation> geoList = geoLocationRepository.findByworkId(workId);
			List<LocationPoints> lp = pointsrepo.findAllByworkID(workId);

			if (geoList != null && !geoList.isEmpty()) {

				for (WorkGeoLocation geo : geoList) {

					GeoTaggingBean bean = new GeoTaggingBean();

					bean.setAddress(geo.getAddress());
					bean.setAtProjectionLocation(geo.getAtProjectionLocation());
					bean.setCurrentLocation(geo.getCurrentPoint());
					bean.setWorkId(geo.getWorkId());

					// Points mapping
					if (lp != null && !lp.isEmpty()) {
						List<GTPoint> points2 = new ArrayList<>();

						for (LocationPoints points : lp) {
							GTPoint bean2 = new GTPoint();
							bean2.setLattitude(points.getLattitude());
							bean2.setLongitude(points.getLongitude());
							points2.add(bean2);
						}

						bean.setPoints(points2);
					}

					beanList.add(bean);
				}
			}

		} catch (Exception e) {
			logger.error("error getting Data", e);
		}

		return beanList;
	}

	@Override
	public List<WorkBean> getWorkDetailsWithGeo() {
		List<Work> workList = workRepository.findByStatus(DMSConstants.STATUS_ACTIVE);
		List<WorkBean> beanList = new ArrayList<WorkBean>();
		for (Work entity : workList) {
			beanList.add(convertWorkEntityToBeans1(entity, CCDocumentPath));
		}
		return beanList;
	}

	@Override
	public List<DepartmentWiseReportRowBean> getDepartmentWiseReport(List<Long> agencyIds,
			List<Long> financialYearIds) {
		List<DepartmentWiseReportRowBean> result = new ArrayList<>();
		try {
			List<Object[]> rows;
			boolean hasAgencyFilter = agencyIds != null && !agencyIds.isEmpty();
			boolean hasFyFilter = financialYearIds != null && !financialYearIds.isEmpty();

			if (hasAgencyFilter && hasFyFilter) {
				rows = departmentRemarksRepository.fetchDepartmentWiseReportByDeptAndFy(agencyIds, financialYearIds);
			} else if (hasFyFilter) {
				rows = departmentRemarksRepository.fetchDepartmentWiseReportByFy(financialYearIds);
			} else if (hasAgencyFilter) {
				rows = departmentRemarksRepository.fetchDepartmentWiseReportByDept(agencyIds);
			} else {
				rows = departmentRemarksRepository.fetchDepartmentWiseReportAll();
			}

			if (rows != null) {
				for (Object[] row : rows) {
					DepartmentWiseReportRowBean bean = new DepartmentWiseReportRowBean();
					bean.setImplementationAgencyId(row[0] != null ? ((Number) row[0]).longValue() : null);
					bean.setImplementationAgencyName(row[1] != null ? row[1].toString() : null);
					bean.setFinancialYearId(row[2] != null ? ((Number) row[2]).longValue() : null);
					bean.setFinancialYearName(row[3] != null ? row[3].toString() : null);
					bean.setTotalWorks(row[4] != null ? ((Number) row[4]).longValue() : 0L);
					bean.setCompletedWorks(row[5] != null ? ((Number) row[5]).longValue() : 0L);
					bean.setOngoingWorks(row[6] != null ? ((Number) row[6]).longValue() : 0L);
					bean.setNotStartedWorks(row[7] != null ? ((Number) row[7]).longValue() : 0L);
					result.add(bean);
				}
			}
		} catch (Exception e) {
			logger.error("Error fetching department-wise report", e);
			throw new RuntimeException("Error fetching department-wise report", e);
		}
		return result;
	}

	@Override
	public List<PhotoUpdateReportRowBean> getPhotoUpdateReport(List<Long> departmentIds) {
		List<PhotoUpdateReportRowBean> result = new ArrayList<>();
		try {
			List<Object[]> rows;
			if (departmentIds == null || departmentIds.isEmpty()) {
				rows = documentUploadWorkProgressRepository.fetchPhotoUpdateReportAll();
			} else {
				rows = documentUploadWorkProgressRepository.fetchPhotoUpdateReportByDept(departmentIds);
			}
			if (rows != null) {
				for (Object[] row : rows) {
					PhotoUpdateReportRowBean bean = new PhotoUpdateReportRowBean();
					bean.setDepartmentId(row[0] != null ? ((Number) row[0]).longValue() : null);
					bean.setDepartmentName(row[1] != null ? row[1].toString() : null);
					bean.setWorkName(row[2] != null ? row[2].toString() : null);
					bean.setTotalWorks(row[3] != null ? ((Number) row[3]).longValue() : 0L);
					bean.setAreaOfficerName(row[4] != null ? row[4].toString() : null);
					bean.setDays(row[5] != null ? ((Number) row[5]).intValue() : 0);
					result.add(bean);
				}
			}
		} catch (Exception e) {
			logger.error("Error fetching photo update report", e);
			throw new RuntimeException("Error fetching photo update report", e);
		}
		return result;
	}

	@Override
	public List<DmRemarkWiseReportRowBean> getDmRemarkWiseReport(List<Long> deptMasterIds, List<Long> implAgencyIds) {
		List<DmRemarkWiseReportRowBean> result = new ArrayList<>();
		try {
			List<Object[]> rows;
			boolean hasDeptFilter = deptMasterIds != null && !deptMasterIds.isEmpty();
			boolean hasAgencyFilter = implAgencyIds != null && !implAgencyIds.isEmpty();

			if (hasDeptFilter && hasAgencyFilter) {
				rows = departmentRemarksRepository.fetchDmRemarkWiseReportByDeptAndAgency(deptMasterIds, implAgencyIds);
			} else if (hasDeptFilter) {
				rows = departmentRemarksRepository.fetchDmRemarkWiseReportByDept(deptMasterIds);
			} else if (hasAgencyFilter) {
				rows = departmentRemarksRepository.fetchDmRemarkWiseReportByAgency(implAgencyIds);
			} else {
				rows = departmentRemarksRepository.fetchDmRemarkWiseReportAll();
			}

			if (rows != null) {
				for (Object[] row : rows) {
					DmRemarkWiseReportRowBean bean = new DmRemarkWiseReportRowBean();
					bean.setDepartmentMasterId(row[0] != null ? ((Number) row[0]).longValue() : null);
					bean.setIssueType(row[1] != null ? row[1].toString() : null);
					bean.setDepartmentNames(row[2] != null ? row[2].toString() : null);
					bean.setTotalWorks(row[3] != null ? ((Number) row[3]).longValue() : 0L);
					result.add(bean);
				}
			}
		} catch (Exception e) {
			logger.error("Error fetching DM remark-wise report", e);
			throw new RuntimeException("Error fetching DM remark-wise report", e);
		}
		return result;
	}

}
