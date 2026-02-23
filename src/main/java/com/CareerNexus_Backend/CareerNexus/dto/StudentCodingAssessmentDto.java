package com.CareerNexus_Backend.CareerNexus.dto;

import java.util.List;

public class StudentCodingAssessmentDto {
    private Long id;
    private String assessmentName;
    private Long jobPostId;
    private String startTime;
    private String endTime;
    private List<StudentCodingQuestionDto> questions;
    private Boolean solved;
    private Integer totalScore;
    private Integer maxScore;

    public StudentCodingAssessmentDto(Long id, String assessmentName, Long jobPostId, String startTime, String endTime, List<StudentCodingQuestionDto> questions) {
        this.id = id;
        this.assessmentName = assessmentName;
        this.jobPostId = jobPostId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.questions = questions;
    }

    public StudentCodingAssessmentDto(Long id,
                                      String assessmentName,
                                      Long jobPostId,
                                      String startTime,
                                      String endTime,
                                      List<StudentCodingQuestionDto> questions,
                                      Boolean solved,
                                      Integer totalScore,
                                      Integer maxScore) {
        this.id = id;
        this.assessmentName = assessmentName;
        this.jobPostId = jobPostId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.questions = questions;
        this.solved = solved;
        this.totalScore = totalScore;
        this.maxScore = maxScore;
    }

    public StudentCodingAssessmentDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public Long getJobPostId() {
        return jobPostId;
    }

    public void setJobPostId(Long jobPostId) {
        this.jobPostId = jobPostId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<StudentCodingQuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<StudentCodingQuestionDto> questions) {
        this.questions = questions;
    }

    public Boolean getSolved() {
        return solved;
    }

    public void setSolved(Boolean solved) {
        this.solved = solved;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }
}
