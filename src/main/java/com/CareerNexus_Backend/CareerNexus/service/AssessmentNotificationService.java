package com.CareerNexus_Backend.CareerNexus.service;

import com.CareerNexus_Backend.CareerNexus.model.User;
import com.CareerNexus_Backend.CareerNexus.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Service
public class AssessmentNotificationService {

    @Autowired
    private ApplicationRepository jobApplicationRepository;

    @Autowired
    private EmailService emailService;

    private static final Logger logger = LoggerFactory.getLogger(AssessmentNotificationService.class);

    /**
     * This is the "Next Step" logic.
     * Call this method after saving your encrypted questions.
     */
    public void notifyRegisteredStudents(Long jobId,String jobTitle, String roundName, String startTime, String endTime, Long assessmentId) {
        // 1. Find all students who applied for this job
        List<User> students = jobApplicationRepository.findStudentsByJobId(jobId);

        // 2. Loop and dispatch notifications
        for (User student : students) {
            emailService.sendExamInvite(
                    student.getEmail(),
                    student.getUserId(),
                    student.getUserId(), // Assuming student name is not separate, otherwise pass it
                    jobTitle,
                    roundName,
                    startTime,
                    endTime,
                    assessmentId
            );
        }

        logger.info("Dispatched {} notifications for Job ID: {}", students.size(), jobId);
    }

    /**
     * Dispatch an exam invite to a single student (e.g., a late applicant).
     */
    public void notifySingleStudent(User student, String jobTitle, String roundName, String startTime, String endTime, Long assessmentId) {
        emailService.sendExamInvite(
                student.getEmail(),
                student.getUserId(),
                student.getUserId(),
                jobTitle,
                roundName,
                startTime,
                endTime,
                assessmentId
        );
        logger.info("Dispatched late-applicant exam notification to: {}", student.getEmail());
    }
}