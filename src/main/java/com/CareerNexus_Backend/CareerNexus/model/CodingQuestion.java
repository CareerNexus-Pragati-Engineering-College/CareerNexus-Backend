package com.CareerNexus_Backend.CareerNexus.model;

import jakarta.persistence.*;

@Entity
@Table(name = "coding_questions")
public class CodingQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assessment_id", nullable = false)
    private CodingAssessment codingAssessment;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String constraints;

    @Column(nullable = false)
    private int points;

    public CodingQuestion() {}

    public CodingQuestion(CodingAssessment codingAssessment, String title, String description, String constraints, int points) {
        this.codingAssessment = codingAssessment;
        this.title = title;
        this.description = description;
        this.constraints = constraints;
        this.points = points;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public CodingAssessment getCodingAssessment() { return codingAssessment; }
    public void setCodingAssessment(CodingAssessment codingAssessment) { this.codingAssessment = codingAssessment; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getConstraints() { return constraints; }
    public void setConstraints(String constraints) { this.constraints = constraints; }
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
}
