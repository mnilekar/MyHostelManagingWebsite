package com.hostel.auth.hostel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class HostelFacilityMapId implements Serializable {
    @Column(name = "HOSTEL_ID")
    private Long hostelId;

    @Column(name = "FACILITY_ID")
    private Long facilityId;

    public HostelFacilityMapId() {
    }

    public HostelFacilityMapId(Long hostelId, Long facilityId) {
        this.hostelId = hostelId;
        this.facilityId = facilityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HostelFacilityMapId that)) return false;
        return Objects.equals(hostelId, that.hostelId) && Objects.equals(facilityId, that.facilityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hostelId, facilityId);
    }
}
