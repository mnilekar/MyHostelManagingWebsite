package com.hostel.auth.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "USER_PROFILE")
public class UserProfile {

    @Id
    @Column(name = "USER_ID")
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "USER_ID")
    private AppUser user;

    @Column(name = "HOME_ADDRESS", length = 60)
    private String homeAddress;

    public void setUser(AppUser user) { this.user = user; }
    public void setHomeAddress(String homeAddress) { this.homeAddress = homeAddress; }
}
