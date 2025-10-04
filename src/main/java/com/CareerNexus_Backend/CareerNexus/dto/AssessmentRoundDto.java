package com.CareerNexus_Backend.CareerNexus.dto;

import java.time.LocalDateTime;

public class AssessmentRoundDto {
    private String createdByUserId;
    private Long jobPostId;
    private String roundName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int min_marks;




    public AssessmentRoundDto(String createdByUserId, Long jobPostId, String roundName, LocalDateTime startTime, LocalDateTime endTime, int min_marks) {
        this.createdByUserId = createdByUserId;
        this.jobPostId = jobPostId;
        this.roundName = roundName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.min_marks = min_marks;
    }

    public AssessmentRoundDto( String roundName,LocalDateTime startTime, LocalDateTime endTime) throws Exception {
        this.roundName = roundName;
        this.startTime = startTime;
        this.endTime = endTime;

    }



    public AssessmentRoundDto() {
    }

    public String getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(String createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public Long getJobPostId() {
        return jobPostId;
    }

    public void setJobPostId(Long jobPostId) {
        this.jobPostId = jobPostId;
    }

    public String getRoundName() {
        return roundName;
    }

    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getMin_marks() {
        return min_marks;
    }

    public void setMin_marks(int min_marks) {
        this.min_marks = min_marks;
    }
}
