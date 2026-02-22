package com.CareerNexus_Backend.CareerNexus.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CodingAssessmentRequestDto {
    private String assessmentName;
    private Long jobPostId; // Optional -> can be null for standalone tests
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<CodingQuestionDto> questions;

    public CodingAssessmentRequestDto() {}

    public String getAssessmentName() { return assessmentName; }
    public void setAssessmentName(String assessmentName) { this.assessmentName = assessmentName; }
    public Long getJobPostId() { return jobPostId; }
    public void setJobPostId(Long jobPostId) { this.jobPostId = jobPostId; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public List<CodingQuestionDto> getQuestions() { return questions; }
    public void setQuestions(List<CodingQuestionDto> questions) { this.questions = questions; }
}
