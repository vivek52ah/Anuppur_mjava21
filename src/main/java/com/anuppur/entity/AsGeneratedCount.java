package com.anuppur.entity;

import java.io.Serializable;

// ✅ CHANGED: javax.persistence.* → jakarta.persistence.*
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// ✅ ADDED: Lombok annotations to replace manual getters/setters/constructors
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "as_generated_count")
public class AsGeneratedCount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    // ✅ CHANGED: GenerationType.AUTO → GenerationType.IDENTITY
    // Hibernate 6 (Spring Boot 3) changed AUTO behavior — causes issues with MySQL.
    // IDENTITY correctly uses MySQL's AUTO_INCREMENT column.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_count")
    private Integer lastCount;

    @Column(name = "financial_year")
    private String financialYear;

}