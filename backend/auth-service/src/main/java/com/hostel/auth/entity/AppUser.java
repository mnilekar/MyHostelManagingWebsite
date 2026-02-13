package com.hostel.auth.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "APP_USER")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_user_seq")
    @SequenceGenerator(name = "app_user_seq", sequenceName = "APP_USER_SEQ", allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false, length = 20)
    private UserRole role;

    @Column(name = "FIRST_NAME", nullable = false, length = 50)
    private String firstName;

    @Column(name = "MIDDLE_NAME", length = 50)
    private String middleName;

    @Column(name = "SURNAME", length = 50)
    private String surname;

    @Enumerated(EnumType.STRING)
    @Column(name = "GENDER", length = 20)
    private Gender gender;

    @Column(name = "EMAIL", nullable = false, unique = true, length = 120)
    private String email;

    @Column(name = "NATIONALITY", nullable = false, length = 50)
    private String nationality;

    @Column(name = "COUNTRY_CODE", nullable = false, length = 10)
    private String countryCode;

    @Column(name = "MOBILE_NUMBER", nullable = false, length = 20)
    private String mobileNumber;

    @Column(name = "STATE_NAME", nullable = false, length = 50)
    private String stateName;

    @Column(name = "CITY_NAME", nullable = false, length = 50)
    private String cityName;

    @Enumerated(EnumType.STRING)
    @Column(name = "ID_PROOF_TYPE", nullable = false, length = 20)
    private IdProofType idProofType;

    @Column(name = "ID_PROOF_VALUE", nullable = false, length = 50)
    private String idProofValue;

    @Column(name = "USERNAME", nullable = false, unique = true, length = 30)
    private String username;

    @Column(name = "PASSWORD_HASH", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "CREATED_AT", nullable = false)
    private OffsetDateTime createdAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserProfile userProfile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private EmployeeProfile employeeProfile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private OwnerProfile ownerProfile;

    @PrePersist
    public void onCreate() {
        this.createdAt = OffsetDateTime.now();
    }

    public Long getId() { return id; }
    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }
    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }
    public String getStateName() { return stateName; }
    public void setStateName(String stateName) { this.stateName = stateName; }
    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }
    public IdProofType getIdProofType() { return idProofType; }
    public void setIdProofType(IdProofType idProofType) { this.idProofType = idProofType; }
    public String getIdProofValue() { return idProofValue; }
    public void setIdProofValue(String idProofValue) { this.idProofValue = idProofValue; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
}
