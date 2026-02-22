package com.hostel.auth.hostel.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "HOSTEL_FACILITY_MAP")
public class HostelFacilityMap {
    @EmbeddedId
    private HostelFacilityMapId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("hostelId")
    @JoinColumn(name = "HOSTEL_ID")
    private Hostel hostel;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("facilityId")
    @JoinColumn(name = "FACILITY_ID")
    private FacilityMaster facility;

    public HostelFacilityMap() {}

    public HostelFacilityMap(Hostel hostel, FacilityMaster facility) {
        this.hostel = hostel;
        this.facility = facility;
        this.id = new HostelFacilityMapId(hostel.getHostelId(), facility.getFacilityId());
    }
}
