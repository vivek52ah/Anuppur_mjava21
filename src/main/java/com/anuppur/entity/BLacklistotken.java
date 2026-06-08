package com.anuppur.entity;

import java.time.LocalDateTime;

// ✅ CHANGED: javax.persistence.* → jakarta.persistence.*
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// ✅ ADDED: Lombok annotations to replace manual getters/setters
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "blacklistertokens")
public class BLacklistotken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private String blacklistedAt;

    // ✅ LocalDateTime is already correct — Hibernate 6 maps it natively to TIMESTAMP
    @Column
    private LocalDateTime expirytime;

}