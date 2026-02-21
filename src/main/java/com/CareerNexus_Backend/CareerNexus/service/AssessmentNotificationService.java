package com.CareerNexus_Backend.CareerNexus.service;

import com.CareerNexus_Backend.CareerNexus.model.User;
import com.CareerNexus_Backend.CareerNexus.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AssessmentNotificationService {

    @Autowired
    private ApplicationRepository jobApplicationRepository;

    @Autowired
    private EmailService emailService;

    /**
     * This is the "Next Step" logic.
     * Call this method after saving your encrypted questions.
     */
    public void notifyRegisteredStudents(Long jobId,String jobTitle, String roundName, String startTime, String endTime) {
        // 1. Find all students who applied for this job
        List<User> students = jobApplicationRepository.findStudentsByJobId(jobId);

        // 2. Loop and dispatch notifications
        for (User student : students) {
            emailService.sendExamInvite(
                    student.getEmail(),
                    student.getUserId(),
                    jobTitle,
                    roundName,
                    startTime,
                    endTime
            );
        }

        System.out.println("Dispatched " + students.size() + " notifications for Job ID: " + jobId);
    }
}