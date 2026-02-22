package com.CareerNexus_Backend.CareerNexus.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "coding_assessment_results")
public class CodingAssessmentResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assessment_id", nullable = false)
    private CodingAssessment assessment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @Column(nullable = false)
    private int totalScore;

    @Column(nullable = false)
    private int maxScore;

    @Column(nullable = false)
    private LocalDateTime submittedAt = LocalDateTime.now();

    @Column(columnDefinition = "TEXT")
    private String submissionData; // Store JSON string of submissions for reference

    public CodingAssessmentResult() {}

    public CodingAssessmentResult(CodingAssessment assessment, User student, int totalScore, int maxScore, String submissionData) {
        this.assessment = assessment;
        this.student = student;
        this.totalScore = totalScore;
        this.maxScore = maxScore;
        this.submissionData = submissionData;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public CodingAssessment getAssessment() { return assessment; }
    public void setAssessment(CodingAssessment assessment) { this.assessment = assessment; }

    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }

    public int getTotalScore() { return totalScore; }
    public void setTotalScore(int totalScore) { this.totalScore = totalScore; }

    public int getMaxScore() { return maxScore; }
    public void setMaxScore(int maxScore) { this.maxScore = maxScore; }

    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }

    public String getSubmissionData() { return submissionData; }
    public void setSubmissionData(String submissionData) { this.submissionData = submissionData; }
}
