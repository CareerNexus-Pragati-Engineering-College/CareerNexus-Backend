package com.CareerNexus_Backend.CareerNexus.dto;

import java.util.List;

public class StudentCodingQuestionDto {
    private Long id;
    private String title;
    private String description;
    private String constraints;
    private int points;
    private List<StudentTestCaseDto> publicTestCases;

    public StudentCodingQuestionDto(Long id, String title, String description, String constraints, int points, List<StudentTestCaseDto> publicTestCases) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.constraints = constraints;
        this.points = points;
        this.publicTestCases = publicTestCases;
    }

    public StudentCodingQuestionDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConstraints() {
        return constraints;
    }

    public void setConstraints(String constraints) {
        this.constraints = constraints;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<StudentTestCaseDto> getPublicTestCases() {
        return publicTestCases;
    }

    public void setPublicTestCases(List<StudentTestCaseDto> publicTestCases) {
        this.publicTestCases = publicTestCases;
    }
}
