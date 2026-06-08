package com.anuppur.entity;

// ✅ CHANGED: javax.persistence.* → jakarta.persistence.*
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// ✅ ADDED: Lombok annotations to replace manual getters/setters (optional but recommended)
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "area_officer_record")
public class AreaOfficerRecord extends Auditable {

    @Id
    // ✅ CHANGED: GenerationType.AUTO → GenerationType.IDENTITY
    // In Hibernate 6 (Spring Boot 3), AUTO uses a sequence table by default
    // which can cause issues with MySQL. IDENTITY uses AUTO_INCREMENT instead.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userid")
    private Long userid;

    @Column(name = "workid")
    private Long workId;

}