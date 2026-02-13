package com.hostel.auth.dto;

import jakarta.validation.constraints.Size;

public class RegisterUserRequest extends BaseRegistrationRequest {

    @Size(max = 60)
    private String homeAddress;

    public String getHomeAddress() { return homeAddress; }
    public void setHomeAddress(String homeAddress) { this.homeAddress = homeAddress; }
}
