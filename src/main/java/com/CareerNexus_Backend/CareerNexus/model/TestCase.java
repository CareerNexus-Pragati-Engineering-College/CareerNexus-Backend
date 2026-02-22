package com.CareerNexus_Backend.CareerNexus.model;

import jakarta.persistence.*;

@Entity
@Table(name = "test_cases")
public class TestCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private CodingQuestion codingQuestion;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String input;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String expectedOutput;

    @Column(nullable = false)
    private boolean isHidden; // True = private grading case, False = public example

    @Column(nullable = false)
    private int marks;

    public TestCase() {}

    public TestCase(CodingQuestion codingQuestion, String input, String expectedOutput, boolean isHidden, int marks) {
        this.codingQuestion = codingQuestion;
        this.input = input;
        this.expectedOutput = expectedOutput;
        this.isHidden = isHidden;
        this.marks = marks;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public CodingQuestion getCodingQuestion() { return codingQuestion; }
    public void setCodingQuestion(CodingQuestion codingQuestion) { this.codingQuestion = codingQuestion; }
    public String getInput() { return input; }
    public void setInput(String input) { this.input = input; }
    public String getExpectedOutput() { return expectedOutput; }
    public void setExpectedOutput(String expectedOutput) { this.expectedOutput = expectedOutput; }
    public boolean isHidden() { return isHidden; }
    public void setHidden(boolean hidden) { isHidden = hidden; }
    public int getMarks() { return marks; }
    public void setMarks(int marks) { this.marks = marks; }
}
