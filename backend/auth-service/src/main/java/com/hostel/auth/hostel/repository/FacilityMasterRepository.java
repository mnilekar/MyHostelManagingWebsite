package com.hostel.auth.hostel.repository;

import com.hostel.auth.hostel.entity.FacilityMaster;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityMasterRepository extends JpaRepository<FacilityMaster, Long> {
    List<FacilityMaster> findByIsActiveOrderByFacilityNameAsc(String isActive);
    List<FacilityMaster> findByFacilityIdIn(List<Long> ids);
}
