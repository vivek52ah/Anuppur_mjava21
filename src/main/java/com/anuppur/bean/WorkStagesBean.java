package com.anuppur.bean;

import java.util.List;

public class WorkStagesBean {

	private Long stageId;
	private Long schemeId;
	private Long workTypeId;
	private Long workCategoryId;
	private Integer index;
	private List<String> stageNameList;
	private List<StageBean> stageList;
	private String stageName;

	public Long getStageId() {
		return stageId;
	}

	public void setStageId(Long stageId) {
		this.stageId = stageId;
	}

	public Long getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}

	public Long getWorkTypeId() {
		return workTypeId;
	}

	public void setWorkTypeId(Long workTypeId) {
		this.workTypeId = workTypeId;
	}

	public Long getWorkCategoryId() {
		return workCategoryId;
	}

	public void setWorkCategoryId(Long workCategoryId) {
		this.workCategoryId = workCategoryId;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public List<String> getStageNameList() {
		return stageNameList;
	}

	public void setStageNameList(List<String> stageNameList) {
		this.stageNameList = stageNameList;
	}

	public List<StageBean> getStageList() {
		return stageList;
	}

	public void setStageList(List<StageBean> stageList) {
		this.stageList = stageList;
	}

}
