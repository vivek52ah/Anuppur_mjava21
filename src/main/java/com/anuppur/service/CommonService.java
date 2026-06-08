package com.anuppur.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.anuppur.bean.BlockBean;
import com.anuppur.bean.CCBean;
import com.anuppur.bean.ContractorBean;
import com.anuppur.bean.DepartmentMasterBean;
import com.anuppur.bean.DepartmentWiseReportRowBean;
import com.anuppur.bean.DmRemarkWiseReportRowBean;
import com.anuppur.bean.PhotoUpdateReportRowBean;
import com.anuppur.bean.DepartmentRemarksBean;
import com.anuppur.bean.DistrictBean;
import com.anuppur.bean.DivisionBean;
import com.anuppur.bean.DmRemarksBean;
import com.anuppur.bean.DocumentUploadDrawingDetailBean;
import com.anuppur.bean.DocumentUploadWorkProgressBean;
import com.anuppur.bean.ExpensesDataBean;
import com.anuppur.bean.FinancialAgencyBean;
import com.anuppur.bean.FinancialHeadBean;
import com.anuppur.bean.FinancialYearBean;
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
import com.anuppur.entity.AreaOfficerRecord;
import com.anuppur.entity.ImplementationAgency;
import com.anuppur.entity.Schemes;
import com.anuppur.entity.SorYear;
import com.anuppur.entity.Work;
import com.anuppur.entity.WorkHead;
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
import com.anuppur.response.ResponseObject;
import com.anuppur.response.UserDetailResponse;

public interface CommonService {

	List<WorkTypeBean> fetchWorkTypes();

	List<WorkSubTypeBean> fetchWorkSubTypes();

	List<ImplAgencyBean> fetchImplAgency();

	List<SchemeBean> fetchSchemes();

	List<HeadBean> fetchHeads();

	List<WorkSubStatusBean> fetchSubWorkStatus();

	List<WorkCategoryBean> fetchCategoryByWorkType(Long workTypeId);

	List<WorkProgressImageListBean> fetchWorkProgressImageList();

	List<WorkHeadBean> fetchWorkHeadByPriorityType(Integer priorityTypeId);

	List<WorkStatusBean> fetchWorkStatusByFlag(Long flag);

	List<WorkSubStatusBean> fetchWorkSubStatusByWorkStatus(Long worksubstatusId,Integer workStatusId);

	PhysicalPercentageBean fetchPercByWorkSubStatus(Long workSubStatusId);

	List<BlockBean> fetchBlocksByDistrict(String districtCode);

	List<BlockBean> fetchBlocksByDistrictName(String districtName);

	List<FinancialYearBean> fetchFinancialYear();

	List<SorYearBean> fetchSorYear();

	List<YearStatusBean> fetchYear();

	List<WorkStatusBean> fetchWorkStatus();

	ResponseObject addWork(WorkBean bean) throws Exception;

	ResponseObject addTSASWorkData(TSASWorkBean tsasWorkBean) throws Exception;

	ResponseObject addTSReviseWorkData(TSASReviseWorkBean tsasReviseWorkBean) throws Exception;

	ResponseObject addASReviseWorkData(TSASReviseWorkBean tsasReviseWorkBean) throws Exception;

	ResponseObject addWorkProgress(WorkProgressBean workProgressBean) throws Exception;

	ResponseObject addWorkProSubStatusUploading(DocumentUploadWorkProgressBean documentUploadWorkProgressBean)
			throws Exception;

	ResponseObject addWorkProExpensesData(ExpensesDataBean expensesDataBean) throws Exception;

	ResponseObject addContractorData(ContractorBean contractorBean) throws Exception;

	WorkTenderBean fetchWorkTenderAgreement(Long workId);

	ContractorBean fetchContractorDetails(Long workId);

	String fetchDownloadFileName(Long documentId);

	String deleteWork(Long id);

	WorkBean fetchWorkDetails(Long id);

	MergeWorkJson fetchFullWorkDetails(Long id);

	TSASWorkBean fetchTSASWorkDetails(Long id);

	WorkProgressBean fetchWorkProgress(Long id);

	List<WorkStatusBean> getWorkStatus();

	List<WorkSubStatusBean> getWorkSubStatus();

	ResponseObject addWorkTenderAgreementDetls(WorkTenderBean workTenderBean) throws Exception;

	ResponseObject editOngoingWork(WorkBean workBean) throws Exception;

	UserDetailResponse getLogin(LoginJson loginJson) throws DMSBusinessException;

	/*
	 * WorkJson fetchWorksList(Pageable pageable, String workNo, String asNo, String
	 * workName, String scheme, String workType, String financialYear, String
	 * implementationAgency, String blockId, String workStatus, String lc, String
	 * districtName, Short isLegacy);
	 */

	List<WorkCategoryBean> fetchWorkCategory();

	List<BlockBean> fetchBlock();

	List<LegislativeConstituencyBean> fetchlegislativeCont();

	List<SubCategoryBean> fetchSubCategories();

	List<DistrictBean> fetchDistricts();

	List<DistrictBean> fetchDistrictsByDivision(Long divisionId);

	UserBean fetchLoggedInUser();

	List<LCBean> fetchLCsByDistrictName(String districtName);

	ImplAgencyJson getAllImplAgency(Pageable pageable, String searchParameter);

	HeadJson getAllHead(Pageable pageable, String searchParameter);

	SchemeJson getAllScheme(Pageable pageable, String searchParameter);

	SorJson getAllSor(Pageable pageable, String searchParameter);

	String deleteImplAgency(Long id);

	String deleteHead(Long id);

	String deleteScheme(Long id);

	String deleteSor(Long id);

	String editImplAgency(ImplAgencyBean bean, String date);

	String editHead(WorkHeadBean bean, String date);

	String editScheme(SchemeBean bean, String date);

	String editSor(SorYearBean bean, String date);

	String addImplAgency(ImplAgencyBean bean, String userName, String dateString);

	String addHead(WorkHeadBean bean, String userName, String dateString);

	String addScheme(SchemeBean bean, String userName, String dateString);

	String addSor(SorYearBean bean, String userName, String dateString);

	void convertImplAgencyBeanToEntity(ImplementationAgency entity, ImplAgencyBean bean);

	ImplAgencyBean fetchImplAgencyDetails(long parseLong);

	WorkHeadBean fetchHeadDetails(long parseLong);

	SchemeBean fetchSchemeDetails(long parseLong);

	SorYearBean fetchSorDetails(long parseLong);

	List<WorkCategoryBean> fetchWorkCategories();

	List<BlockBean> fetchBlocksByDistrictCode(String districtCode);

	ResponseObject addCCDetails(CCBean ccBean) throws Exception;

	String fetchWorkFileName(Long documentId);

	String fetchWorkFileNameDW(Long documentId);

	// List<WorkBean> fetchWorksByIA(Long ia);

	List<WorkStatusBean> fetchWorkStatusByWorkType(Long workTypeId);

	List<WorkCategoryBean> fetchWorkCategoryByWorkType(Long workTypeId);

	List<DistrictBean> fetchDistrictByDivision(Long divisionId);

	List<BlockBean> fetchBlockByDistrict(Long districtId);

	List<LegislativeConstituencyBean> fetchLegislativeByDistrict(String districtCode);

	List<SubCategoryBean> fetchSubCategoryByCategory(Long categoryId);

	List<String> fetchDistinctWorkStatus();

	List<String> fetchDistinctLegislativeConstituency();

	List<OtherDocListBean> fetchDefaultDocList();

	List<WorkProgressImageListBean> fetchWorkProgressImagesDataList();

	ResponseObject uploadOtherDoc(OtherDocListBean otherDocListBean) throws Exception;

	String deleteFile(Long id);

	List<LCBean> fetchLCsByDistrictId(Long districtId);

	/* List<WorkStagesBean> fetchWorkStagesList(); */

	public long isUserByEmailIdExists(String emailId);

	void saveOrUpdateOtp(String emailIdOrMobileNo, Integer otp);

	public Integer getOtpByEmailIdMobileNo(String emailIdOrMobileNo);

	List<WorkStatusBean> fetchWorkStatusByScheme(Long schemeId, Long statusId);

	List<ImplAgencyTypeBean> fetchImplAgencyType();

	List<DivisionBean> fetchDivisions();

	WorkJson fetchWorksList(Pageable pageable, String workNo, String workName, String scheme, List<Long> workTypeList,
			List<Long> fyList, List<Long> agencyList, String blockId, String workStatus, String districtName,
			String divisionId, String searchByDivision, String workSubTypeId, List<Long> statusList, List<Long> priorityList, List<Long> headList, List<Long> vsList, String workNameFilter, String departmentRemark, List<Long> departmentList);

	WorkJson fetchHandoverWorksList(Pageable pageable, String workNo, String workName, String scheme, String workType,
			String financialYear, String implementationAgency, String blockId, String workStatus, String divisionId,
			String districtId, String searchByDivision, String workSubTypeId, String workStatusId);

	ResponseObject uploadWorkProgressImages(WorkProgressImageListBean workProgressImageListBean) throws Exception;

	CCBean fetchCCWorkDetails(Long id);

	void convertWorkHeadBeanToEntity(WorkHead entity, WorkHeadBean bean);

	void convertSchemeBeanToEntity(Schemes entity, SchemeBean bean);

	TASAReviseJson fetchTSASRevisedList(Pageable pageable, Object typeDoc, Object rvOrderNo, Object rvOrderDate,
			Object rvAmt, Object searchParameterWorkName, Object rvFileId, Object rvRemarks, Long workId);

	String fetchDownloadFileNameRevised(Long documentId);

	String fetchDownloadDocumentWSPro(Long documentId);

	List<String> getWorkNameSuggestions(String keyword);

	List<BlockBean> getBlocksByDistrict(Long districtId);

	WorkProgressImagesJson fetchProgressImagesList(Pageable pageable, Object workStatusName, Object workSubStatusName,
			Object actionTakenDelay, Object reasonDelay, Object createdDate, Object subDelayReason, Object documentId,
			Long workTypeId);

	ExpensesDataJson fetchExpensesDataList(Pageable pageable, Object totalExpensess, Object expensessUptoMarch,
			Object expensessCurrentFy, String year, Object createdDate, Long workTypeId);

	void convertSorBeanToEntity(SorYear entity, SorYearBean bean);

	List<WorkReportBean> fetchWorksListByAgency(Long mode);

	List<WorkReportBean> fetchWorksListByDrawing(Long mode);

	//List<Object[]> fetchexportInpectionReportData(Long id);

	List<Object[]> fetchexportDrawingReportData(Long id);

	List<WorkReportBean> fetchWorksListByDivision(Long mode);

	ResponseObject uploadedDrawingFiles(DocumentUploadDrawingDetailBean uploadDrawingDetailBean) throws Exception;

	PhysicalPercentageUpgradationBean fetchPercByWorkSubStatusUpgrad(Long workSubStatusId);

	byte[] generatePresentation(List<SlideData> slideDataList) throws Exception;

	String addSdr(WorkSubDelayResonBean bean, String userName, String dateString);

	SdrJson getAllSdr(Pageable pageable, String searchParameter);

	String deleteSdr(Long id);

	WorkSubDelayResonBean fetchSdrDetails(long parseLong);

	String editSdr(WorkSubDelayResonBean bean, String date);

	List<WorkSubDelayResonBean> fetchSubDelayReasonByWorkSubStatusId(long workSubStatusId);

	List<WorkCategoryBean> fetchWorkCategoryByWorkTypes();

	List<ImplAgencyBean> fetchConstructionAgency();

	String fetchWorkFileNameAgreement(Long documentId);

	String fetchWorkFileNameLOI(Long documentId);

	Long countContractorsByWorkId(Long workId);

	List<WorkTenderBean> getWorkTenderEndDate();

	WorkJson fetchWorksAaIssuedList(Pageable pageable, String workNo, String workName, String scheme, String workType,
			String financialYear, String implementationAgency, String blockId, String workStatus, String districtName,
			String divisionId, String searchByDivision, String workSubTypeId, String workStatusId, String workPriorityId, String financialHeadId, String vidhanSabhaId);

	Long fetchAgencyByuserId();

	ImplAgencyBean fetchConstructionAgencyById();

	WorkProgressBean fetchWorkProgressDocumetnId(Long id);

	List<MonthBean> fetchmonths();

	ResponseObject addTSASWorkDataCancelStatus(TSASWorkBean tsasWorkbean) throws Exception;

	ResponseObject addASReviseWorkDataStatus(TSASReviseWorkBean tsasReviseWorkBean) throws Exception;

	ResponseObject addTSReviseWorkDataStatus(TSASReviseWorkBean tsasReviseWorkBean) throws Exception;


	List<GramPanchayatBean> fetchGramPanchayatByBlockCode(String districtCode, String blockCode);

	List<DistrictBean> fetchDistrictsMP();

	List<BlockBean> fetchBlocksByDistirct(String dId);

	BlockJson fetchBlockForDistrict(Pageable pageable, String searchbolval, String object);

	List<BlockBean> fetchBlocksByDistirct2(String dId);

	WorkJson fetchWorkForReport(Pageable pageable, String workNo, String workName, String scheme, List<Long> workTypeList,
			List<Long> fyList, String Department, List<Long> agencyList, String blockId, String workStatus,
			String districtName, String divisionId, String searchByDivision, String workSubTypeId, List<Long> statusList, List<Long> priorityList, List<Long> headList, List<Long> vsList, String workNameFilter, String departmentRemark);

	List<departmentbean> fetchAllDepartment();

	List<Work> getWorksByAgencyId(Long agencyId);

	String assignUserToWork(Long long1, Long long2);

	WorkStatusJson fetchWorkStatusDataList(Long workId);

	WorkStatusJson fetchWorkStatusDataListCC(Long workId);

	List<WorkBean> getWorksByAreaOfficer(Long userId);

	ResponseObject addGeoTaggingData(GeoTaggingBean bean);

	GeoTaggingBean getGeoTaggingForWork(Long workId);
	ResponseObject addWorkProgressDataMobile(WorkProgressDataMoibleBean documentUploadWorkProgressBean)
			throws Exception;

	List<DocumentUploadWorkProgressBean> fetchProgressImagesGroupList(Long workId, Date createdDate, String fullUrl);

	List<DocumentUploadWorkProgressBean> fetchImagesGroupListforzipfile(Long documentId);

	String addOrUpdateDmRemark(DmRemarksBean bean);

	List<DmRemarksBean> getAllRemarksByWorkID(Long long1);

	Boolean deleteRemarks(Long long1);

	DmRemarksBean getRemakrsDetails(Long long1);

	List<UserBean> fetchAreaOfficerListByWorkId(Long workid);

	List<WorkPriorityBean> fetchWorkPriority();

	List<FinancialHeadBean> fetchFinancialHead();

	List<VidhanSabhaBean> fetchVidhanSabha();

	List<DmRemarksBean> fetchDmRemarksList();

	List<WorkBean> getFilteredWorkProgress(String workStatusStr, String userIdStr, String agencyIdStr, String workSubStatusStr, String searchBoxVal);

	List<WorkBean> getFilteredWorkWithLatestExpenses(String workStatusStr, String userIdStr, String agencyIdStr,
			String workName);
	
	List<UserBean> fetchAssignedUsers();

	List<UserBean> fetchAssignUser(Long implementationAgency);

//	List<String> getWorkNameSuggestions(String keyword);

	List<FinancialAgencyBean> fetchFinancialAgencyByWorkId(Long workId);

	String updateFinancialAgencyCost(Long id, Double expenditure, Long workId);

	Double sumFinancialAgencyExpenditureByWorkId(Long workId);

	void syncWorkProgressExpenditureFromFinancialAgency(Long workId);

	String deleteByFinancailAgencyId(Long id);

	List<String> getWorkNoSuggestions(String keyword);

	List<FinancialAgencyBean> getFinancialAgenciesByWorkId(Long workId);

	List<FinancialAgencyBean> getFinancialAgenciesExpenditureByWorkId(Long workId);

	List<DepartmentMasterBean> fetchDepartmentMaster();

	String addOrUpdateDepartmentRemark(DepartmentRemarksBean bean);

	List<DepartmentRemarksBean> getAllDepartmentRemarksByWorkID(Long long1);

	List<DepartmentRemarksBean> fetchDepartmentRemarksList();

	DepartmentRemarksBean getDepartmentRemarksDetailsById(Long long1);

	Boolean deleteDepartmentRemarks(Long long1);

	List<GeoTaggingBean> getGeoTaggingForWorkList(Long workId);

	List<WorkBean> getWorkDetailsWithGeo();

	List<DepartmentWiseReportRowBean> getDepartmentWiseReport(List<Long> agencyIds, List<Long> financialYearIds);

	List<PhotoUpdateReportRowBean> getPhotoUpdateReport(List<Long> departmentIds);

	List<DmRemarkWiseReportRowBean> getDmRemarkWiseReport(List<Long> deptMasterIds, List<Long> implAgencyIds);
}