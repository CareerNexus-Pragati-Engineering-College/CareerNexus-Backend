package com.CareerNexus_Backend.CareerNexus.dto;

import java.util.List;

public class CodingQuestionDto {
    private String title;
    private String description;
    private String constraints;
    private int points;
    private List<TestCaseDto> testCases;

    public CodingQuestionDto() {}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getConstraints() { return constraints; }
    public void setConstraints(String constraints) { this.constraints = constraints; }
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
    public List<TestCaseDto> getTestCases() { return testCases; }
    public void setTestCases(List<TestCaseDto> testCases) { this.testCases = testCases; }
}
