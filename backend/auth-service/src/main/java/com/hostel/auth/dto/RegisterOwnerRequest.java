package com.hostel.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterOwnerRequest extends BaseRegistrationRequest {

    @NotBlank
    @Size(max = 60)
    private String permanentAddress;

    @NotBlank
    @Size(max = 60)
    private String currentAddress;

    public String getPermanentAddress() { return permanentAddress; }
    public void setPermanentAddress(String permanentAddress) { this.permanentAddress = permanentAddress; }
    public String getCurrentAddress() { return currentAddress; }
    public void setCurrentAddress(String currentAddress) { this.currentAddress = currentAddress; }
}
