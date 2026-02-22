package com.hostel.auth.hostel.repository;

import com.hostel.auth.hostel.entity.HostelFloorPlan;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostelFloorPlanRepository extends JpaRepository<HostelFloorPlan, Long> {
    List<HostelFloorPlan> findByHostelHostelIdOrderByFloorNumberAsc(Long hostelId);
    void deleteByHostelHostelId(Long hostelId);
}
