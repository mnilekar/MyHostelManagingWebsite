package com.hostel.auth.dto;

import com.hostel.auth.entity.Gender;
import com.hostel.auth.entity.IdProofType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class BaseRegistrationRequest {

    @NotBlank
    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String middleName;

    @Size(max = 50)
    private String surname;

    private Gender gender;

    @NotBlank
    @Email
    @Size(max = 120)
    private String email;

    @NotBlank
    @Size(max = 50)
    private String nationality;

    @NotBlank
    @Size(max = 10)
    private String countryCode;

    @NotBlank
    @Size(max = 20)
    private String mobileNumber;

    @NotBlank
    @Size(max = 50)
    private String stateName;

    @NotBlank
    @Size(max = 50)
    private String cityName;

    @NotNull
    private IdProofType idProofType;

    @NotBlank
    @Size(max = 50)
    private String idProofValue;

    @NotBlank
    @Size(max = 10)
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "username must be alphanumeric")
    private String username;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8}$",
        message = "password must be exactly 8 chars with upper, lower, number and special")
    private String password;

    @NotBlank
    private String confirmPassword;

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
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
}
