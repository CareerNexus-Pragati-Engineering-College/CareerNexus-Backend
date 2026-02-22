package com.CareerNexus_Backend.CareerNexus.dto;

public class StudentTestCaseDto {
    private Long id;
    private String input;
    private String expectedOutput;
    private int marks;

    // Notice we do not include `isHidden` here, because this DTO will ONLY 
    // ever hold test cases that are public (isHidden = false).

    public StudentTestCaseDto(Long id, String input, String expectedOutput, int marks) {
        this.id = id;
        this.input = input;
        this.expectedOutput = expectedOutput;
        this.marks = marks;
    }

    public StudentTestCaseDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
}
