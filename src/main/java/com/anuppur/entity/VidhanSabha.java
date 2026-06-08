package com.anuppur.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
