package com.anuppur.json;

import java.util.List;

import com.anuppur.bean.CCBean;
import com.anuppur.bean.ContractorBean;
import com.anuppur.bean.TSASWorkBean;
import com.anuppur.bean.WorkBean;
import com.anuppur.bean.WorkTenderBean;

public class MergeWorkJson {
   
	WorkBean workBeans;
	TSASWorkBean tsasWorkBeans;
	WorkTenderBean workTenderBeans;
	ContractorBean woContractorBeans;
	CCBean ccBeans;
	public WorkBean getWorkBeans() {
		return workBeans;
	}
	public void setWorkBeans(WorkBean workBeans) {
		this.workBeans = workBeans;
	}
	public TSASWorkBean getTsasWorkBeans() {
		return tsasWorkBeans;
	}
	public void setTsasWorkBeans(TSASWorkBean tsasWorkBeans) {
		this.tsasWorkBeans = tsasWorkBeans;
	}
	public WorkTenderBean getWorkTenderBeans() {
		return workTenderBeans;
	}
	public void setWorkTenderBeans(WorkTenderBean workTenderBeans) {
		this.workTenderBeans = workTenderBeans;
	}
	public ContractorBean getWoContractorBeans() {
		return woContractorBeans;
	}
	public void setWoContractorBeans(ContractorBean woContractorBeans) {
		this.woContractorBeans = woContractorBeans;
	}
	public CCBean getCcBeans() {
		return ccBeans;
	}
	public void setCcBeans(CCBean ccBeans) {
		this.ccBeans = ccBeans;
	}

}
