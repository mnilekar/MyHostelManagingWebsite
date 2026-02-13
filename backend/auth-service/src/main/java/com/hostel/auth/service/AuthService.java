package com.hostel.auth.service;

import com.hostel.auth.dto.*;
import com.hostel.auth.entity.*;
import com.hostel.auth.exception.BadRequestException;
import com.hostel.auth.exception.ResourceConflictException;
import com.hostel.auth.exception.UnauthorizedException;
import com.hostel.auth.repository.AppUserRepository;
import com.hostel.auth.repository.EmployeeProfileRepository;
import com.hostel.auth.repository.OwnerProfileRepository;
import com.hostel.auth.repository.UserProfileRepository;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserProfileRepository userProfileRepository;
    private final EmployeeProfileRepository employeeProfileRepository;
    private final OwnerProfileRepository ownerProfileRepository;

    public AuthService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder,
                       UserProfileRepository userProfileRepository,
                       EmployeeProfileRepository employeeProfileRepository,
                       OwnerProfileRepository ownerProfileRepository) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.userProfileRepository = userProfileRepository;
        this.employeeProfileRepository = employeeProfileRepository;
        this.ownerProfileRepository = ownerProfileRepository;
    }

    @Transactional
    public RegisterResponse registerUser(RegisterUserRequest request) {
        validatePasswordMatch(request.getPassword(), request.getConfirmPassword());
        validateUsernameAndEmailUniqueness(request.getUsername(), request.getEmail());
        validateIndianIdRule(request);

        AppUser appUser = appUserRepository.save(baseUser(request, UserRole.USER));
        UserProfile profile = new UserProfile();
        profile.setUser(appUser);
        profile.setHomeAddress(request.getHomeAddress());
        userProfileRepository.save(profile);
        return new RegisterResponse("Successfully Registered", appUser.getUsername());
    }

    @Transactional
    public RegisterResponse registerEmployee(RegisterEmployeeRequest request) {
        validatePasswordMatch(request.getPassword(), request.getConfirmPassword());
        validateUsernameAndEmailUniqueness(request.getUsername(), request.getEmail());
        validateIndianIdRule(request);
        if (request.getGender() == null) {
            throw new BadRequestException("gender is required");
        }

        AppUser appUser = appUserRepository.save(baseUser(request, UserRole.EMPLOYEE));
        EmployeeProfile profile = new EmployeeProfile();
        profile.setUser(appUser);
        profile.setPermanentAddress(request.getPermanentAddress());
        profile.setCurrentAddress(request.getCurrentAddress());
        profile.setSkill(request.getSkill());
        employeeProfileRepository.save(profile);
        return new RegisterResponse("Successfully Registered", appUser.getUsername());
    }

    @Transactional
    public RegisterResponse registerOwner(RegisterOwnerRequest request) {
        validatePasswordMatch(request.getPassword(), request.getConfirmPassword());
        validateUsernameAndEmailUniqueness(request.getUsername(), request.getEmail());
        validateIndianIdRule(request);
        if (request.getGender() == null) {
            throw new BadRequestException("gender is required");
        }

        AppUser appUser = appUserRepository.save(baseUser(request, UserRole.OWNER));
        OwnerProfile profile = new OwnerProfile();
        profile.setUser(appUser);
        profile.setPermanentAddress(request.getPermanentAddress());
        profile.setCurrentAddress(request.getCurrentAddress());
        ownerProfileRepository.save(profile);
        return new RegisterResponse("Successfully Registered", appUser.getUsername());
    }

    public AuthResponse login(LoginRequest request) {
        AppUser user = appUserRepository.findByUsernameIgnoreCase(request.getUsername())
            .orElseThrow(() -> new UnauthorizedException("invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new UnauthorizedException("invalid username or password");
        }

        String rawToken = user.getUsername() + ":" + user.getRole().name() + ":" + UUID.randomUUID();
        String token = Base64.getEncoder().encodeToString(rawToken.getBytes(StandardCharsets.UTF_8));
        return new AuthResponse("Login successful", token);
    }

    private AppUser baseUser(BaseRegistrationRequest request, UserRole role) {
        AppUser appUser = new AppUser();
        appUser.setRole(role);
        appUser.setFirstName(request.getFirstName().trim());
        appUser.setMiddleName(request.getMiddleName());
        appUser.setSurname(request.getSurname());
        appUser.setGender(request.getGender());
        appUser.setEmail(request.getEmail().trim());
        appUser.setNationality(request.getNationality().trim());
        appUser.setCountryCode(request.getCountryCode().trim());
        appUser.setMobileNumber(request.getMobileNumber().trim());
        appUser.setStateName(request.getStateName().trim());
        appUser.setCityName(request.getCityName().trim());
        appUser.setIdProofType(request.getIdProofType());
        appUser.setIdProofValue(request.getIdProofValue().trim());
        appUser.setUsername(request.getUsername().trim());
        appUser.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        return appUser;
    }

    private void validatePasswordMatch(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new BadRequestException("password and confirmPassword must match");
        }
    }

    private void validateUsernameAndEmailUniqueness(String username, String email) {
        if (appUserRepository.existsByUsernameIgnoreCase(username)) {
            throw new ResourceConflictException("username already exists");
        }
        if (appUserRepository.existsByEmailIgnoreCase(email)) {
            throw new ResourceConflictException("email already exists");
        }
    }

    private void validateIndianIdRule(BaseRegistrationRequest request) {
        boolean isIndia = "India".equalsIgnoreCase(request.getNationality());
        if (!isIndia && request.getIdProofType() != IdProofType.PASSPORT) {
            throw new BadRequestException("idProofType must be PASSPORT for non-Indian nationality");
        }
        if (isIndia && !request.getIdProofValue().matches("^[0-9]{1,10}$")) {
            throw new BadRequestException("For India, idProofValue must contain up to 10 digits");
        }
    }
}
