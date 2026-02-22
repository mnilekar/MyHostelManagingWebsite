package com.hostel.auth.hostel.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.hostel.auth.entity.AppUser;
import com.hostel.auth.entity.UserRole;
import com.hostel.auth.exception.BadRequestException;
import com.hostel.auth.hostel.dto.HostelDtos.FloorRoomDto;
import com.hostel.auth.hostel.dto.HostelDtos.HostelUpsertRequest;
import com.hostel.auth.hostel.entity.*;
import com.hostel.auth.hostel.repository.*;
import com.hostel.auth.repository.AppUserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OwnerHostelServiceTest {

    @Mock private HostelRepository hostelRepository;
    @Mock private HostelFloorPlanRepository floorPlanRepository;
    @Mock private HostelFacilityMapRepository facilityMapRepository;
    @Mock private FacilityMasterRepository facilityMasterRepository;
    @Mock private AppUserRepository appUserRepository;

    private OwnerHostelService service;

    @BeforeEach
    void setUp() {
        service = new OwnerHostelService(hostelRepository, floorPlanRepository, facilityMapRepository, facilityMasterRepository, appUserRepository);
    }

    @Test
    void createHostelHappyPath() {
        AppUser owner = new AppUser();
        owner.setRole(UserRole.OWNER);
        when(appUserRepository.findById(1L)).thenReturn(Optional.of(owner));
        when(hostelRepository.save(any(Hostel.class))).thenAnswer(inv -> inv.getArgument(0));
        when(floorPlanRepository.findByHostelHostelIdOrderByFloorNumberAsc(any())).thenReturn(List.of());
        when(facilityMapRepository.findByHostelHostelId(any())).thenReturn(List.of());

        HostelUpsertRequest request = new HostelUpsertRequest("A", null, "Addr", null, null, "City", "State", null,
            "India", 2, null, List.of(new FloorRoomDto(1, 10), new FloorRoomDto(2, 9)), List.of());

        assertDoesNotThrow(() -> service.create(1L, request));
        verify(hostelRepository).save(any(Hostel.class));
        verify(floorPlanRepository, times(2)).save(any(HostelFloorPlan.class));
    }

    @Test
    void updateHostelRejectsInvalidFloorPlan() {
        HostelUpsertRequest request = new HostelUpsertRequest("A", null, "Addr", null, null, "City", "State", null,
            "India", 2, null, List.of(new FloorRoomDto(1, 10)), List.of());

        assertThrows(BadRequestException.class, () -> service.update(1L, 11L, request));
    }
}
