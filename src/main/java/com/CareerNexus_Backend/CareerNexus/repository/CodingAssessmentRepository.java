package com.CareerNexus_Backend.CareerNexus.repository;

import com.CareerNexus_Backend.CareerNexus.model.CodingAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodingAssessmentRepository extends JpaRepository<CodingAssessment, Long> {
    java.util.List<CodingAssessment> findAllByOrderByCreatedAtDesc();
    java.util.List<CodingAssessment> findByJobPost_Id(Long jobId);
}
