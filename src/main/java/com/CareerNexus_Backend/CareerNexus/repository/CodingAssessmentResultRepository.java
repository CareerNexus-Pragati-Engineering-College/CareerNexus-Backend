package com.CareerNexus_Backend.CareerNexus.repository;

import com.CareerNexus_Backend.CareerNexus.model.CodingAssessmentResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CodingAssessmentResultRepository extends JpaRepository<CodingAssessmentResult, Long> {
    List<CodingAssessmentResult> findByStudent_UserId(String studentId);
    List<CodingAssessmentResult> findByAssessment_Id(Long assessmentId);
    Optional<CodingAssessmentResult> findByStudent_UserIdAndAssessment_Id(String studentId, Long assessmentId);
}
