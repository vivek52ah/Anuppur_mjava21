package com.anuppur.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="mst_dist_block_legislative_mapping")
public class DistBlockLegislativeMapping {
	
	private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long id;
    
    
    public DistBlockLegislativeMapping() {
		super();
		// TODO Auto-generated constructor stub
	}

	@JoinColumn(name = "division_id", referencedColumnName = "id")
  	@ManyToOne
  	private Division division;
    
    @JoinColumn(name = "district_code", referencedColumnName = "district_code")
  	@ManyToOne
  	private District district;
    
    @JoinColumn(name = "block_code", referencedColumnName = "block_code")
  	@ManyToOne
  	private Block block;
    
    @JoinColumn(name = "constituency_code", referencedColumnName = "constituency_code")
  	@ManyToOne
  	private LegislativeConstituency legislativeConstituency;
    
    private Short enabled;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public LegislativeConstituency getLegislativeConstituency() {
		return legislativeConstituency;
	}

	public void setLegislativeConstituency(LegislativeConstituency legislativeConstituency) {
		this.legislativeConstituency = legislativeConstituency;
	}

	public Short getEnabled() {
		return enabled;
	}

	public void setEnabled(Short enabled) {
		this.enabled = enabled;
	}

}
