package com.hostel.auth.security;

public record OwnerAuthContext(Long userId, String username, String role) {
}
