package com.anuppur.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mst_vidhan_sabha")
public class VidhanSabha {

	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "vidhan_sabha_name")
	private String vidhanSabhaName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVidhanSabhaName() {
		return vidhanSabhaName;
	}

	public void setVidhanSabhaName(String vidhanSabhaName) {
		this.vidhanSabhaName = vidhanSabhaName;
	}
	
	
}
