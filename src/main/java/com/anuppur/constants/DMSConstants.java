package com.anuppur.constants;

public interface DMSConstants {
	
	public static final String TECHNICAL_SANCTION_FILE = "technical_sanction_";
	public static final String DM_ATTACHMENT_FILE = "DM_ATTACHMENT_";
	
	public static final String TECHNICAL_SANCTION_REVISED_FILE = "technical_sanction_revised_";
	public static final String AS_SANCTION_FILE = "Administrator_sanction_";
	public static final String AS_SANCTION_REVISED_FILE = "Administrator_sanction_revised_";
	
	public static final String WORK_PROGRESS_FILE = "Work_Progress_";
	public static final String WORK_DRAWING_FILE = "Work_Drawing_";
	public static final String Tender_FILE = "Tender_";
	public static final String Tender_FILE_UPLOAD_LOI = "LOI_";
	public static final String Tender_FILE_UPLOAD_AGREEMENT = "AGREEMENT_";
	public static final String CC_FLIE = "CC_WORK_";
	public String LOCALE_EN = "en";

	public String LOCALE_HI = "hi";
	
	public static final String MAIL_SMTP_HOST = "mail.smtp.host";

	public static final String MAIL_SMTP_PORT = "mail.smtp.port";
	
	public static final String NOTIFICATION_FILE = "notification";

	public static final String CAPTCHA_LOGIN = "CAPTCHA_LOGIN";
	
	public static final String CAPTCHA_RESET = "CAPTCHA_RESET";
	
	public static final String CAPTCHA_SIGNUP = "CAPTCHA_SIGNUP";
	
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	
	public static final String ROLE_DPO = "ROLE_DPO";
	
	public static final String ROLE_SAU = "ROLE_SAU";
	public static final String ROLE_SU = "ROLE_SU";
	
	//public static final String ROLE_DEPARTMENT = "ROLE_DEPARTMENT";
	
	public static final String ROLE_AGENCY_ADMIN = "ROLE_AGENCY_ADMIN";
	
	public static final String ROLE_DIV = "ROLE_DIV";
	
	public static final String ROLE_DISTRICT = "ROLE_DISTRICT";
	
	public static final String ROLE_DEPT_DISTRICT="ROLE_DEPT_DISTRICT";
	
	public static final String ERROR_FETCHING_DATA = "Some error occured while fetching the data";
	
	public static final String ERROR_SAVING_DATA = "Some error occured while saving the data";
	
	public static final String ERROR_DELETING_DATA = "Some error occured while deleting the data";
	
	public static final String DUPLICATE_ENTRY = "Duplicate entry - ";
	
	public static final String STATUS_ACTIVE = "Active";
	
	public static final String STATUS_INACTIVE = "InActive";
	
	public static final String STATUS_DELETED = "Deleted";
	
	public static final String STATUS_CANCEL = "Cancel";
	
	public static final String WORK_STATUS_HANDOVER = "Handed Over";
	
	public static final String STATUS_PENDING = "Pending";
	
	public static final String STATUS_RECONCILED = "Reconciled";
	
	public static final String STATUS_REJECTED = "Rejected";
	
	public static final String STATUS_TRANSFERRED = "Transferred";
	
	public static final String STATUS_PENDING_VERIFICATION = "Pending Verification";
	
	public static final String STATUS_PENDING_ACTIVATION = "Pending Activation";
	
	public static final String STATUS_COMPLETED = "Completed";
	
	public static final String PRESENT = "Present";
	
	String DATE_FORMAT = "dd/MM/yyyy";
	
	String DATE_FORMAT_dd_MMM_yyyy = "dd-MMM-yyyy";
	
	String DATE_FORMAT_MMM_yyyy = "MMM-yyyy";
	
	String PENDING = "Pending";
	
	String COMPLETED = "Completed";
	
	String MONTH[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
	
	String VERIFY_EMAIL_SERVICE_NAME = "verifyEmail";

	public static final String WORK_IMAGE = "WORK_IMAGE";
	
	public static final Short ENABLED = (short) 1;
	
	public static final Short DISABLED = (short) 0;
	
	public static final String WORK_PRIORITY_HIGH = "High Priority";
	
	public static final String WORK_PRIORITY_OTHER = "Other Priority";
	
	String DESIGNATION_ENGINEER = "Sub Engineer";

	public String ROLE_SYSTEM_ADMIN = "ROLE_SYSTEM_ADMIN";
	
	public String ROLE_DM = "ROLE_DM";
	
	
	
	public static final String STAGE_ONE = "1";
	public static final String STAGE_TWO = "2";
	public static final String STAGE_THREE = "3";
	public static final String STAGE_FOUR = "4";
	public static final String STAGE_FIVE = "5";
	public static final String STAGE_SIX = "6";
	
	String DATE_FORMAT_HH_MM = "dd/MM/yyyy hh:mm:ss a";
	
	
	
	public static final String WORK_CREATED = "work created";
	
	public String ROLE_HQ = "ROLE_HQ";
	
	
	
	public static final String STATUS_AS_IN_PROCESS  = "Administrative Sanction In Process";
	public static final String STATUS_AS_GENERATED_BUT_NOT_DISPATCHED  = "Administrative Sanction Generated But Not Dispatched";
	public static final String STATUS_AS_GENERATED_IN_DRAFT  = "Administrative Sanction In Process (In Draft)";
	public static final int STATUS_AS_GENERATED_BUT_NOT_DISPATCHED_ID = 25;
	public static final int STATUS_AS_GENERATED_IN_DRAFT_ID  = 26;
	
	public static final String ADMIN_SANCT_STATUS = "adminSanctStatus";
	
	public static final String DHS = "ANUPPUR/" ;
	public static final String AS_NO_FORMAT = "/जि.यो.सां/वि.नि./" ;
	public static final String DTF_DDMMYYYY_S = "DTF_DDMMYYYY_S";
	
	public static final String WORK_STATUS_Work_Created = "Work Created";
	public static final String WORK_STATUS_AA_Issued = "AA Issued";
	public static final String WORK_STATUS_Tender_Called_date = "Tender Called date";
	public static final String WORK_STATUS_Tender_Received = "Tender Received";
	public static final String WORK_STATUS_LoA_Issued = "LoA Issued";
	public static final String WORK_STATUS_Work_Order_Issued = "Work Order Issued";
	public static final String WORK_STATUS_Not_Started = "Not Started";
	public static final String WORK_STATUS_In_Progress = "In-Progress";
	public static final String WORK_STATUS_Completed = "Completed";
	public static final String WORK_STATUS_Handed_Over = "Handed Over";
	public static final String WORK_STATUS_CC_Uploaded = "CC Uploaded";
	public static final String WORK_STATUS_Re_Tender = "Re-Tender";
//	public static final String WORK_STATUS_ACTIVE = "Active";
	
	public static final Long WORK_STATUS_HANDOVERS = 12L;
	public static final String ROLE_AREA_OFFICER = "ROLE_AREA_OFFICER";
	public static final String ROLE_DEPARTMENT = "ROLE_DEPARTMENT";
	public static final String ROLE_CEO = "ROLE_CEO";
}
