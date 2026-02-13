package com.hostel.auth.dto;

public class RegisterResponse {

    private final String message;
    private final String username;

    public RegisterResponse(String message, String username) {
        this.message = message;
        this.username = username;
    }

    public String getMessage() { return message; }
    public String getUsername() { return username; }
}
