package com.hostel.auth.dto;

public class AuthResponse {
    private final String message;
    private final String token;

    public AuthResponse(String message, String token) {
        this.message = message;
        this.token = token;
    }

    public String getMessage() { return message; }
    public String getToken() { return token; }
}
