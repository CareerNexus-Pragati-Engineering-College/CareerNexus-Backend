package com.CareerNexus_Backend.CareerNexus.dto;

import java.time.LocalDateTime;
import java.util.List;

public class StudentExamDTO {
    private Long assessmentId;
    private String roundName;
    private LocalDateTime endTime;
    private List<QuestionDto> questions;

    public StudentExamDTO() {}

    public Long getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(Long assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getRoundName() {
        return roundName;
    }

    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public List<QuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDto> questions) {
        this.questions = questions;
    }
}
