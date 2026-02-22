package com.hostel.auth.hostel.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.OffsetDateTime;
import java.util.List;

public class HostelDtos {
    public record FloorRoomDto(
        @NotNull @Min(1) Integer floorNumber,
        @NotNull @Min(1) Integer roomsCount
    ) {}

    public record HostelUpsertRequest(
        @NotBlank String hostelName,
        String gstNumber,
        @NotBlank String addressLine1,
        String addressLine2,
        String areaLocality,
        @NotBlank String cityName,
        @NotBlank String stateName,
        String pincode,
        String countryName,
        @NotNull @Min(1) @Max(50) Integer floorsCount,
        @Size(max = 2000) String whyChoose,
        @Valid @NotNull List<FloorRoomDto> floorRooms,
        List<Long> facilityIds
    ) {}

    public record HostelSummaryResponse(Long hostelId, String hostelName, String cityName, String stateName,
                                        Integer floorsCount, OffsetDateTime updatedAt) {}

    public record FacilityResponse(Long facilityId, String facilityName) {}

    public record HostelDetailResponse(
        Long hostelId,
        String hostelName,
        String gstNumber,
        String addressLine1,
        String addressLine2,
        String areaLocality,
        String cityName,
        String stateName,
        String pincode,
        String countryName,
        Integer floorsCount,
        String whyChoose,
        List<FloorRoomDto> floorRooms,
        List<Long> facilityIds
    ) {}
}
