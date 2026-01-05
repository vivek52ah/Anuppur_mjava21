package com.anuppur.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name = "t_work_count")
public class WorkCount implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	/*@GeneratedValue(strategy=GenerationType.AUTO)*/
	private Long id;
	
	@Column(name="last_count")
	private Integer lastCount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getLastCount() {
		return lastCount;
	}

	public void setLastCount(Integer lastCount) {
		this.lastCount = lastCount;
	}

	public WorkCount(Long id, Integer lastCount) {
		super();
		this.id = id;
		this.lastCount = lastCount;
	}

	public WorkCount() {
		super();
	}
}
