package com.anuppur.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;



@Entity
@Table(name="t_work_tender_counts")
public class WorkTenderCount extends Auditable implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="work_id")
	private Long workId;
	
	
	@Column(name="tender_uniq_id")
	private String tenderUniqId;
	
	@Column(name="status_id")
	private Long statusId;
	
	@Column(name="status")
	private String status;

	@Column(name="count")
	private Integer count;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public String getTenderUniqId() {
		return tenderUniqId;
	}

	public void setTenderUniqId(String tenderUniqId) {
		this.tenderUniqId = tenderUniqId;
	}

	

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	

	
	
}
