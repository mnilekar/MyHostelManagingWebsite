package com.hostel.auth.hostel.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "HOSTEL_FLOOR_PLAN", uniqueConstraints = @UniqueConstraint(name = "UK_FLOOR_UNIQUE", columnNames = {"HOSTEL_ID", "FLOOR_NUMBER"}))
public class HostelFloorPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hostel_floor_plan_seq")
    @SequenceGenerator(name = "hostel_floor_plan_seq", sequenceName = "HOSTEL_FLOOR_PLAN_SEQ", allocationSize = 1)
    @Column(name = "FLOOR_PLAN_ID")
    private Long floorPlanId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOSTEL_ID", nullable = false)
    private Hostel hostel;

    @Column(name = "FLOOR_NUMBER", nullable = false)
    private Integer floorNumber;

    @Column(name = "ROOMS_COUNT", nullable = false)
    private Integer roomsCount;

    public void setHostel(Hostel hostel) { this.hostel = hostel; }
    public Integer getFloorNumber() { return floorNumber; }
    public void setFloorNumber(Integer floorNumber) { this.floorNumber = floorNumber; }
    public Integer getRoomsCount() { return roomsCount; }
    public void setRoomsCount(Integer roomsCount) { this.roomsCount = roomsCount; }
}
