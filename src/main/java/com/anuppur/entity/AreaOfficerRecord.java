package com.anuppur.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "area_officer_record")
public class AreaOfficerRecord extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	
	
	@Column(name = "userid")
	private Long userid;
	
	@Column(name = "workid")
	private Long workId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getWorkid() {
		return workId;
	}

	public void setWorkid(Long workid) {
		this.workId = workid;
	}
	
	
	
}
