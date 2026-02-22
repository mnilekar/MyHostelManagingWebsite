package com.hostel.auth.hostel.entity;

import com.hostel.auth.entity.AppUser;
import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "HOSTEL")
public class Hostel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hostel_seq")
    @SequenceGenerator(name = "hostel_seq", sequenceName = "HOSTEL_SEQ", allocationSize = 1)
    @Column(name = "HOSTEL_ID")
    private Long hostelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OWNER_USER_ID", nullable = false)
    private AppUser ownerUser;

    @Column(name = "HOSTEL_NAME", nullable = false, length = 120)
    private String hostelName;

    @Column(name = "GST_NUMBER", length = 20)
    private String gstNumber;

    @Column(name = "ADDRESS_LINE1", nullable = false, length = 200)
    private String addressLine1;

    @Column(name = "ADDRESS_LINE2", length = 200)
    private String addressLine2;

    @Column(name = "AREA_LOCALITY", length = 120)
    private String areaLocality;

    @Column(name = "CITY_NAME", nullable = false, length = 60)
    private String cityName;

    @Column(name = "STATE_NAME", nullable = false, length = 60)
    private String stateName;

    @Column(name = "PINCODE", length = 10)
    private String pincode;

    @Column(name = "COUNTRY_NAME", nullable = false, length = 60)
    private String countryName;

    @Column(name = "FLOORS_COUNT", nullable = false)
    private Integer floorsCount;

    @Column(name = "WHY_CHOOSE", length = 2000)
    private String whyChoose;

    @Column(name = "CREATED_AT", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    void onCreate() {
        OffsetDateTime now = OffsetDateTime.now();
        createdAt = now;
        updatedAt = now;
        if (countryName == null || countryName.isBlank()) {
            countryName = "India";
        }
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    public Long getHostelId() { return hostelId; }
    public AppUser getOwnerUser() { return ownerUser; }
    public void setOwnerUser(AppUser ownerUser) { this.ownerUser = ownerUser; }
    public String getHostelName() { return hostelName; }
    public void setHostelName(String hostelName) { this.hostelName = hostelName; }
    public String getGstNumber() { return gstNumber; }
    public void setGstNumber(String gstNumber) { this.gstNumber = gstNumber; }
    public String getAddressLine1() { return addressLine1; }
    public void setAddressLine1(String addressLine1) { this.addressLine1 = addressLine1; }
    public String getAddressLine2() { return addressLine2; }
    public void setAddressLine2(String addressLine2) { this.addressLine2 = addressLine2; }
    public String getAreaLocality() { return areaLocality; }
    public void setAreaLocality(String areaLocality) { this.areaLocality = areaLocality; }
    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }
    public String getStateName() { return stateName; }
    public void setStateName(String stateName) { this.stateName = stateName; }
    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }
    public String getCountryName() { return countryName; }
    public void setCountryName(String countryName) { this.countryName = countryName; }
    public Integer getFloorsCount() { return floorsCount; }
    public void setFloorsCount(Integer floorsCount) { this.floorsCount = floorsCount; }
    public String getWhyChoose() { return whyChoose; }
    public void setWhyChoose(String whyChoose) { this.whyChoose = whyChoose; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
}
