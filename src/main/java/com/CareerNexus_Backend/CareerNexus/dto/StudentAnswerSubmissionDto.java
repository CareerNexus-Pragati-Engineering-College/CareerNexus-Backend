package com.CareerNexus_Backend.CareerNexus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object representing a single answer submitted by a student.
 * This matches the JSON structure sent from the frontend: { "que_no": 1, "option": "A) Paris" }
 */
public class StudentAnswerSubmissionDto {

    @JsonProperty("que_no")
    private int queNo;

    private String option;

    // Default constructor for Jackson deserialization
    public StudentAnswerSubmissionDto() {}

    public StudentAnswerSubmissionDto(int queNo, String option) {
        this.queNo = queNo;
        this.option = option;
    }

    public int getQueNo() {
        return queNo;
    }

    public void setQueNo(int queNo) {
        this.queNo = queNo;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    @Override
    public String toString() {
        return "StudentAnswerSubmissionDto{" +
                "queNo=" + queNo +
                ", option='" + option + '\'' +
                '}';
    }
}