package com.hostel.auth.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "EMPLOYEE_PROFILE")
public class EmployeeProfile {

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

    @Column(name = "SKILL", length = 30)
    private String skill;

    public void setUser(AppUser user) { this.user = user; }
    public void setPermanentAddress(String permanentAddress) { this.permanentAddress = permanentAddress; }
    public void setCurrentAddress(String currentAddress) { this.currentAddress = currentAddress; }
    public void setSkill(String skill) { this.skill = skill; }
}
