package com.anuppur.bean;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public class WorkProgressImageListBean {
	
    private Integer index;
	
    private Long id;
    private Long otherDocumentId;
    private MultipartFile fileArr;
    private Map<String,String> fillArr;
    
    private MultipartFile fileArr0;
    private MultipartFile fileArr1;
    private MultipartFile fileArr2;
    private MultipartFile fileArr3;
    private MultipartFile fileArr4;
    
    
	public MultipartFile getFileArr0() {
		return fileArr0;
	}
	public void setFileArr0(MultipartFile fileArr0) {
		this.fileArr0 = fileArr0;
	}
	public MultipartFile getFileArr1() {
		return fileArr1;
	}
	public void setFileArr1(MultipartFile fileArr1) {
		this.fileArr1 = fileArr1;
	}
	public MultipartFile getFileArr2() {
		return fileArr2;
	}
	public void setFileArr2(MultipartFile fileArr2) {
		this.fileArr2 = fileArr2;
	}
	public MultipartFile getFileArr3() {
		return fileArr3;
	}
	public void setFileArr3(MultipartFile fileArr3) {
		this.fileArr3 = fileArr3;
	}
	public MultipartFile getFileArr4() {
		return fileArr4;
	}
	public void setFileArr4(MultipartFile fileArr4) {
		this.fileArr4 = fileArr4;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOtherDocumentId() {
		return otherDocumentId;
	}
	public void setOtherDocumentId(Long otherDocumentId) {
		this.otherDocumentId = otherDocumentId;
	}
	public MultipartFile getFileArr() {
		return fileArr;
	}
	public void setFileArr(MultipartFile fileArr) {
		this.fileArr = fileArr;
	}
	public Map<String, String> getFillArr() {
		return fillArr;
	}
	public void setFillArr(Map<String, String> fillArr) {
		this.fillArr = fillArr;
	}

}
