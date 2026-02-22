package com.hostel.auth.dto;

public class AuthResponse {
    private final String message;
    private final String token;
    private final AuthUserDto user;

    public AuthResponse(String message, String token, AuthUserDto user) {
        this.message = message;
        this.token = token;
        this.user = user;
    }

    public String getMessage() { return message; }
    public String getToken() { return token; }
    public AuthUserDto getUser() { return user; }

    public static class AuthUserDto {
        private final String name;
        private final String username;
        private final String email;
        private final String role;

        public AuthUserDto(String name, String username, String email, String role) {
            this.name = name;
            this.username = username;
            this.email = email;
            this.role = role;
        }

        public String getName() { return name; }
        public String getUsername() { return username; }
        public String getEmail() { return email; }
        public String getRole() { return role; }
    }
}
