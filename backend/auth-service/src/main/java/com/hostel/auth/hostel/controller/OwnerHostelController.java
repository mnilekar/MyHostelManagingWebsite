package com.hostel.auth.hostel.controller;

import com.hostel.auth.hostel.dto.HostelDtos.*;
import com.hostel.auth.hostel.service.OwnerHostelService;
import com.hostel.auth.security.OwnerAuthContext;
import com.hostel.auth.security.OwnerAuthService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owner")
@CrossOrigin(origins = "*")
public class OwnerHostelController {

    private final OwnerHostelService ownerHostelService;
    private final OwnerAuthService ownerAuthService;

    public OwnerHostelController(OwnerHostelService ownerHostelService, OwnerAuthService ownerAuthService) {
        this.ownerHostelService = ownerHostelService;
        this.ownerAuthService = ownerAuthService;
    }

    @GetMapping("/hostels")
    public List<HostelSummaryResponse> listHostels(@RequestHeader("Authorization") String authHeader) {
        OwnerAuthContext owner = ownerAuthService.requireOwner(authHeader);
        return ownerHostelService.listByOwner(owner.userId());
    }

    @GetMapping("/hostels/{id}")
    public HostelDetailResponse getHostel(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        OwnerAuthContext owner = ownerAuthService.requireOwner(authHeader);
        return ownerHostelService.getDetail(owner.userId(), id);
    }

    @PostMapping("/hostels")
    public ResponseEntity<HostelDetailResponse> createHostel(@RequestHeader("Authorization") String authHeader,
                                                             @Valid @RequestBody HostelUpsertRequest request) {
        OwnerAuthContext owner = ownerAuthService.requireOwner(authHeader);
        return ResponseEntity.status(HttpStatus.CREATED).body(ownerHostelService.create(owner.userId(), request));
    }

    @PutMapping("/hostels/{id}")
    public HostelDetailResponse updateHostel(@RequestHeader("Authorization") String authHeader,
                                             @PathVariable Long id,
                                             @Valid @RequestBody HostelUpsertRequest request) {
        OwnerAuthContext owner = ownerAuthService.requireOwner(authHeader);
        return ownerHostelService.update(owner.userId(), id, request);
    }

    @DeleteMapping("/hostels/{id}")
    public ResponseEntity<Void> deleteHostel(@RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
        OwnerAuthContext owner = ownerAuthService.requireOwner(authHeader);
        ownerHostelService.delete(owner.userId(), id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/facilities")
    public List<FacilityResponse> listFacilities(@RequestHeader("Authorization") String authHeader) {
        ownerAuthService.requireOwner(authHeader);
        return ownerHostelService.listActiveFacilities();
    }
}
