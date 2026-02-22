package com.CareerNexus_Backend.CareerNexus.dto;

public class TestCaseDto {
    private String input;
    private String expectedOutput;
    private boolean isHidden;
    private int marks;

    public TestCaseDto() {}

    public TestCaseDto(String input, String expectedOutput, boolean isHidden, int marks) {
        this.input = input;
        this.expectedOutput = expectedOutput;
        this.isHidden = isHidden;
        this.marks = marks;
    }

    public String getInput() { return input; }
    public void setInput(String input) { this.input = input; }
    public String getExpectedOutput() { return expectedOutput; }
    public void setExpectedOutput(String expectedOutput) { this.expectedOutput = expectedOutput; }
    public boolean getIsHidden() { return isHidden; }
    public void setIsHidden(boolean isHidden) { this.isHidden = isHidden; }
    public int getMarks() { return marks; }
    public void setMarks(int marks) { this.marks = marks; }
}
