package com.CareerNexus_Backend.CareerNexus.dto;

import java.time.LocalDateTime;

public class StudentAssessmentDashboardDto {
    private Long id;
    private String assessmentName;
    private Long jobPostId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isSolved;
    private Integer highestScore;
    private Integer maxScore;

    public StudentAssessmentDashboardDto(Long id, String assessmentName, Long jobPostId, LocalDateTime startTime, LocalDateTime endTime, boolean isSolved, Integer highestScore, Integer maxScore) {
        this.id = id;
        this.assessmentName = assessmentName;
        this.jobPostId = jobPostId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isSolved = isSolved;
        this.highestScore = highestScore;
        this.maxScore = maxScore;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAssessmentName() { return assessmentName; }
    public void setAssessmentName(String assessmentName) { this.assessmentName = assessmentName; }

    public Long getJobPostId() { return jobPostId; }
    public void setJobPostId(Long jobPostId) { this.jobPostId = jobPostId; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public boolean isSolved() { return isSolved; }
    public void setSolved(boolean solved) { isSolved = solved; }

    public Integer getHighestScore() { return highestScore; }
    public void setHighestScore(Integer highestScore) { this.highestScore = highestScore; }

    public Integer getMaxScore() { return maxScore; }
    public void setMaxScore(Integer maxScore) { this.maxScore = maxScore; }
}
