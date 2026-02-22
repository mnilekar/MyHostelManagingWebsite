package com.hostel.auth.hostel.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "FACILITY_MASTER")
public class FacilityMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "facility_master_seq")
    @SequenceGenerator(name = "facility_master_seq", sequenceName = "FACILITY_MASTER_SEQ", allocationSize = 1)
    @Column(name = "FACILITY_ID")
    private Long facilityId;

    @Column(name = "FACILITY_NAME", nullable = false, length = 60)
    private String facilityName;

    @Column(name = "IS_ACTIVE", nullable = false, columnDefinition = "CHAR(1)")
    private String isActive;

    @Column(name = "CREATED_AT", nullable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    void onCreate() {
        if (isActive == null || isActive.isBlank()) {
            isActive = "Y";
        }
        createdAt = OffsetDateTime.now();
    }

    public Long getFacilityId() { return facilityId; }
    public String getFacilityName() { return facilityName; }
    public String getIsActive() { return isActive; }
}
