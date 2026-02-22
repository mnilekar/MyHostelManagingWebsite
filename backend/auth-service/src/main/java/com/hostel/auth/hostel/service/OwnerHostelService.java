package com.hostel.auth.hostel.service;

import com.hostel.auth.entity.AppUser;
import com.hostel.auth.exception.BadRequestException;
import com.hostel.auth.exception.ForbiddenException;
import com.hostel.auth.hostel.dto.HostelDtos.*;
import com.hostel.auth.hostel.entity.*;
import com.hostel.auth.hostel.repository.*;
import com.hostel.auth.repository.AppUserRepository;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OwnerHostelService {

    private final HostelRepository hostelRepository;
    private final HostelFloorPlanRepository floorPlanRepository;
    private final HostelFacilityMapRepository facilityMapRepository;
    private final FacilityMasterRepository facilityMasterRepository;
    private final AppUserRepository appUserRepository;

    public OwnerHostelService(HostelRepository hostelRepository, HostelFloorPlanRepository floorPlanRepository,
                              HostelFacilityMapRepository facilityMapRepository, FacilityMasterRepository facilityMasterRepository,
                              AppUserRepository appUserRepository) {
        this.hostelRepository = hostelRepository;
        this.floorPlanRepository = floorPlanRepository;
        this.facilityMapRepository = facilityMapRepository;
        this.facilityMasterRepository = facilityMasterRepository;
        this.appUserRepository = appUserRepository;
    }

    public List<HostelSummaryResponse> listByOwner(Long ownerUserId) {
        return hostelRepository.findByOwnerUserIdOrderByUpdatedAtDesc(ownerUserId).stream()
            .map(h -> new HostelSummaryResponse(h.getHostelId(), h.getHostelName(), h.getCityName(), h.getStateName(),
                h.getFloorsCount(), h.getUpdatedAt()))
            .toList();
    }

    public HostelDetailResponse getDetail(Long ownerUserId, Long hostelId) {
        Hostel hostel = getOwnedHostel(ownerUserId, hostelId);
        return mapDetail(hostel);
    }

    @Transactional
    public HostelDetailResponse create(Long ownerUserId, HostelUpsertRequest request) {
        validateFloorPlan(request);
        AppUser owner = appUserRepository.findById(ownerUserId)
            .orElseThrow(() -> new ForbiddenException("owner not found"));

        Hostel hostel = new Hostel();
        hostel.setOwnerUser(owner);
        applyHostelFields(hostel, request);
        Hostel saved = hostelRepository.save(hostel);
        replaceFloorPlans(saved, request.floorRooms());
        replaceFacilities(saved, request.facilityIds());
        return mapDetail(saved);
    }

    @Transactional
    public HostelDetailResponse update(Long ownerUserId, Long hostelId, HostelUpsertRequest request) {
        validateFloorPlan(request);
        Hostel hostel = getOwnedHostel(ownerUserId, hostelId);
        applyHostelFields(hostel, request);
        Hostel saved = hostelRepository.save(hostel);
        floorPlanRepository.deleteByHostelHostelId(hostelId);
        facilityMapRepository.deleteByHostelHostelId(hostelId);
        replaceFloorPlans(saved, request.floorRooms());
        replaceFacilities(saved, request.facilityIds());
        return mapDetail(saved);
    }

    @Transactional
    public void delete(Long ownerUserId, Long hostelId) {
        Hostel hostel = getOwnedHostel(ownerUserId, hostelId);
        hostelRepository.delete(hostel);
    }

    public List<FacilityResponse> listActiveFacilities() {
        return facilityMasterRepository.findByIsActiveOrderByFacilityNameAsc("Y").stream()
            .map(f -> new FacilityResponse(f.getFacilityId(), f.getFacilityName()))
            .toList();
    }

    private void applyHostelFields(Hostel hostel, HostelUpsertRequest req) {
        hostel.setHostelName(req.hostelName().trim());
        hostel.setGstNumber(normalize(req.gstNumber()));
        hostel.setAddressLine1(req.addressLine1().trim());
        hostel.setAddressLine2(normalize(req.addressLine2()));
        hostel.setAreaLocality(normalize(req.areaLocality()));
        hostel.setCityName(req.cityName().trim());
        hostel.setStateName(req.stateName().trim());
        hostel.setPincode(normalize(req.pincode()));
        hostel.setCountryName(req.countryName() == null || req.countryName().isBlank() ? "India" : req.countryName().trim());
        hostel.setFloorsCount(req.floorsCount());
        hostel.setWhyChoose(normalize(req.whyChoose()));
    }

    private void validateFloorPlan(HostelUpsertRequest request) {
        if (request.floorRooms() == null || request.floorRooms().size() != request.floorsCount()) {
            throw new BadRequestException("floorRooms size must match floorsCount");
        }
        Set<Integer> floors = new HashSet<>();
        for (FloorRoomDto floor : request.floorRooms()) {
            if (floor.floorNumber() == null || floor.floorNumber() < 1 || floor.floorNumber() > request.floorsCount()) {
                throw new BadRequestException("invalid floorNumber in floorRooms");
            }
            if (floor.roomsCount() == null || floor.roomsCount() < 1) {
                throw new BadRequestException("roomsCount must be positive for each floor");
            }
            if (!floors.add(floor.floorNumber())) {
                throw new BadRequestException("duplicate floorNumber entries");
            }
        }
    }

    private void replaceFloorPlans(Hostel hostel, List<FloorRoomDto> floorRooms) {
        for (FloorRoomDto floorRoom : floorRooms) {
            HostelFloorPlan floorPlan = new HostelFloorPlan();
            floorPlan.setHostel(hostel);
            floorPlan.setFloorNumber(floorRoom.floorNumber());
            floorPlan.setRoomsCount(floorRoom.roomsCount());
            floorPlanRepository.save(floorPlan);
        }
    }

    private void replaceFacilities(Hostel hostel, List<Long> facilityIds) {
        if (facilityIds == null || facilityIds.isEmpty()) {
            return;
        }
        List<Long> deduped = facilityIds.stream().filter(Objects::nonNull).distinct().toList();
        List<FacilityMaster> facilities = facilityMasterRepository.findByFacilityIdIn(deduped);
        if (facilities.size() != deduped.size()) {
            throw new BadRequestException("one or more facilityIds are invalid");
        }
        facilities.forEach(f -> facilityMapRepository.save(new HostelFacilityMap(hostel, f)));
    }

    private HostelDetailResponse mapDetail(Hostel hostel) {
        List<FloorRoomDto> floors = floorPlanRepository.findByHostelHostelIdOrderByFloorNumberAsc(hostel.getHostelId())
            .stream().map(fp -> new FloorRoomDto(fp.getFloorNumber(), fp.getRoomsCount())).toList();
        List<Long> facilities = facilityMapRepository.findByHostelHostelId(hostel.getHostelId())
            .stream().map(map -> map.getFacility().getFacilityId()).toList();

        return new HostelDetailResponse(hostel.getHostelId(), hostel.getHostelName(), hostel.getGstNumber(), hostel.getAddressLine1(),
            hostel.getAddressLine2(), hostel.getAreaLocality(), hostel.getCityName(), hostel.getStateName(), hostel.getPincode(),
            hostel.getCountryName(), hostel.getFloorsCount(), hostel.getWhyChoose(), floors, facilities);
    }

    private Hostel getOwnedHostel(Long ownerUserId, Long hostelId) {
        return hostelRepository.findByHostelIdAndOwnerUserId(hostelId, ownerUserId)
            .orElseThrow(() -> new ForbiddenException("hostel not found for this owner"));
    }

    private String normalize(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
