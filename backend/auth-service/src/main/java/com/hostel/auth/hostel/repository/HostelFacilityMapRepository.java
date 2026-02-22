package com.hostel.auth.hostel.repository;

import com.hostel.auth.hostel.entity.HostelFacilityMap;
import com.hostel.auth.hostel.entity.HostelFacilityMapId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostelFacilityMapRepository extends JpaRepository<HostelFacilityMap, HostelFacilityMapId> {
    List<HostelFacilityMap> findByHostelHostelId(Long hostelId);
    void deleteByHostelHostelId(Long hostelId);
}
