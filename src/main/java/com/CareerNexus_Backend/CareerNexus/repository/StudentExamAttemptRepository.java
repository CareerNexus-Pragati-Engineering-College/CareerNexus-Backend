package com.CareerNexus_Backend.CareerNexus.repository;

import com.CareerNexus_Backend.CareerNexus.model.StudentExamAttempt;
import com.CareerNexus_Backend.CareerNexus.model.AssessmentRound;
import com.CareerNexus_Backend.CareerNexus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StudentExamAttemptRepository extends JpaRepository<StudentExamAttempt, Long> {

    /**
     * Finds an existing attempt by student and round.
     * Use this to prevent multiple submissions for the same exam.
     */
    Optional<StudentExamAttempt> findByStudentAndAssessmentRound(User student, AssessmentRound assessmentRound);
}