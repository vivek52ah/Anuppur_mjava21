package com.anuppur.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

// ✅ CHANGED: javax.persistence.* → jakarta.persistence.*
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

// ✅ REMOVED: @Temporal and TemporalType — not needed with LocalDateTime in Hibernate 6
// Hibernate 6 maps LocalDateTime to TIMESTAMP natively without @Temporal

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

// ✅ ADDED: Lombok
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable implements Serializable {

    private static final long serialVersionUID = 1L;

    // ✅ CHANGED: Date → LocalDateTime
    // java.util.Date is legacy. LocalDateTime is the modern Java 8+ standard.
    // Hibernate 6 maps it to TIMESTAMP automatically — no @Temporal needed.
    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;

}