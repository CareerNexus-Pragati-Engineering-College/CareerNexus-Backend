package com.CareerNexus_Backend.CareerNexus.repository;

import com.CareerNexus_Backend.CareerNexus.model.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

    // Get all resources latest first
    List<Resource> findAllByOrderByUploadedAtDesc();

    // Get paginated resources latest first
    Page<Resource> findAllByOrderByUploadedAtDesc(Pageable pageable);

    // Get only a specific student's uploads — String userId
    List<Resource> findByUploadedByUserIdOrderByUploadedAtDesc(String userId);
}