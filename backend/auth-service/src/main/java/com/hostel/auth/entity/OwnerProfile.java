package com.hostel.auth.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "OWNER_PROFILE")
public class OwnerProfile {

    @Id
    @Column(name = "USER_ID")
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "USER_ID")
    private AppUser user;

    @Column(name = "PERMANENT_ADDRESS", nullable = false, length = 60)
    private String permanentAddress;

    @Column(name = "CURRENT_ADDRESS", nullable = false, length = 60)
    private String currentAddress;

    public void setUser(AppUser user) { this.user = user; }
    public void setPermanentAddress(String permanentAddress) { this.permanentAddress = permanentAddress; }
    public void setCurrentAddress(String currentAddress) { this.currentAddress = currentAddress; }
}
