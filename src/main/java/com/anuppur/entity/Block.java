package com.anuppur.entity;

import java.io.Serializable;

// ✅ CHANGED: javax.persistence.* → jakarta.persistence.*
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// ✅ CHANGED: javax.validation.constraints.* → jakarta.validation.constraints.*
// (Note: @NotNull was imported but not used in original — kept import in case needed)
import jakarta.validation.constraints.NotNull;

// ✅ ADDED: Lombok annotations to replace manual getters/setters/constructors
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mst_block")
public class Block implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long blockId;

    @Column(name = "block_code")
    private String blockCode;

    @Column(name = "block_name")
    private String blockName;

    @Column(name = "block_name_h")
    private String blockNameH;

    @Column(name = "enabled")
    private Short enabled;

    @ManyToOne
    @JoinColumn(name = "district_id", referencedColumnName = "id")
    private District district;

    // ✅ Kept custom constructor with blockId (used for lookups/references)
    public Block(Long blockId) {
        this.blockId = blockId;
    }

}