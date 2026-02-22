package com.hostel.auth.hostel.repository;

import com.hostel.auth.hostel.entity.Hostel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostelRepository extends JpaRepository<Hostel, Long> {
    List<Hostel> findByOwnerUserIdOrderByUpdatedAtDesc(Long ownerUserId);
    Optional<Hostel> findByHostelIdAndOwnerUserId(Long hostelId, Long ownerUserId);
}
