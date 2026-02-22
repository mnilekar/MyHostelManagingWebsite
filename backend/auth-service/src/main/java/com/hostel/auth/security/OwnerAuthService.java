package com.hostel.auth.security;

import com.hostel.auth.entity.AppUser;
import com.hostel.auth.entity.UserRole;
import com.hostel.auth.exception.ForbiddenException;
import com.hostel.auth.exception.UnauthorizedException;
import com.hostel.auth.repository.AppUserRepository;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.springframework.stereotype.Service;

@Service
public class OwnerAuthService {

    private final AppUserRepository appUserRepository;

    public OwnerAuthService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public OwnerAuthContext requireOwner(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("missing or invalid authorization header");
        }

        String token = authHeader.substring(7);
        String decoded;
        try {
            decoded = new String(Base64.getDecoder().decode(token), StandardCharsets.UTF_8);
        } catch (IllegalArgumentException ex) {
            throw new UnauthorizedException("invalid token format");
        }

        String[] tokenParts = decoded.split(":");
        if (tokenParts.length < 2) {
            throw new UnauthorizedException("invalid token payload");
        }

        String username = tokenParts[0];
        String role = tokenParts[1];
        AppUser appUser = appUserRepository.findByUsernameIgnoreCase(username)
            .orElseThrow(() -> new UnauthorizedException("user not found"));

        if (appUser.getRole() != UserRole.OWNER || !"OWNER".equalsIgnoreCase(role)) {
            throw new ForbiddenException("only OWNER role can access this endpoint");
        }

        return new OwnerAuthContext(appUser.getId(), appUser.getUsername(), appUser.getRole().name());
    }
}
