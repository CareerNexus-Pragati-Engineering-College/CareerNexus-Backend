package com.CareerNexus_Backend.CareerNexus.dto;

import java.time.LocalDateTime;

public class AssessmentRoundStudentStatusDto {

    private Long assessmentRoundId;
    private Long jobPostId;

    private String studentUserId;
    private String studentEmail;

    private Long applicationId;
    private String applicationStatus;
    private LocalDateTime applicationDate;

    private Integer score;
    private Integer totalQuestions;
    private String examResultStatus; // PASSED / FAILED

    public AssessmentRoundStudentStatusDto() {
    }

    public AssessmentRoundStudentStatusDto(
            Long assessmentRoundId,
            Long jobPostId,
            String studentUserId,
            String studentEmail,
            Long applicationId,
            String applicationStatus,
            LocalDateTime applicationDate,
            Integer score,
            Integer totalQuestions,
            String examResultStatus
    ) {
        this.assessmentRoundId = assessmentRoundId;
        this.jobPostId = jobPostId;
        this.studentUserId = studentUserId;
        this.studentEmail = studentEmail;
        this.applicationId = applicationId;
        this.applicationStatus = applicationStatus;
        this.applicationDate = applicationDate;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.examResultStatus = examResultStatus;
    }

    public Long getAssessmentRoundId() {
        return assessmentRoundId;
    }

    public void setAssessmentRoundId(Long assessmentRoundId) {
        this.assessmentRoundId = assessmentRoundId;
    }

    public Long getJobPostId() {
        return jobPostId;
    }

    public void setJobPostId(Long jobPostId) {
        this.jobPostId = jobPostId;
    }

    public String getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(String studentUserId) {
        this.studentUserId = studentUserId;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public LocalDateTime getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDateTime applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public String getExamResultStatus() {
        return examResultStatus;
    }

    public void setExamResultStatus(String examResultStatus) {
        this.examResultStatus = examResultStatus;
    }
}

