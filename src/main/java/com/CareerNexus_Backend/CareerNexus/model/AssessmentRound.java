package com.CareerNexus_Backend.CareerNexus.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "assessment_rounds")
public class AssessmentRound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "round_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id", nullable = false)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_post_id")
    private JobPost jobPost;

    @Column(name = "round_name", nullable = false)
    private String roundName;

    @Column(name="min_marks",nullable = false)
    private  int min_marks;


    @Column(columnDefinition = "TEXT")
    private String encryptedQuestions;

    @Column(columnDefinition = "TEXT")
    private String encryptedAnswers;



    @Column(length = 1024) // A long column to store the Base64 encoded key
    private String encryptionKey;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    public AssessmentRound() {
        this.createdAt = LocalDateTime.now();
    }

    public AssessmentRound( User createdBy, JobPost jobPost, String roundName, int min_marks, String encryptedQuestions,String encryptedAnswers, String encryptionKey, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime createdAt) {

        this.createdBy = createdBy;
        this.jobPost = jobPost;
        this.roundName = roundName;
        this.min_marks = min_marks;
        this.encryptedQuestions = encryptedQuestions;
        this.encryptedAnswers=encryptedAnswers;
        this.encryptionKey = encryptionKey;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    public String getEncryptedQuestions() {
        return encryptedQuestions;
    }

    public void setEncryptedQuestions(String encryptedQuestions) {
        this.encryptedQuestions = encryptedQuestions;
    }

    public String getEncryptedAnswers() {
        return encryptedAnswers;
    }

    public void setEncryptedAnswers(String encryptedAnswers) {
        this.encryptedAnswers = encryptedAnswers;
    }

    public int getMin_marks() {
        return min_marks;
    }

    public void setMin_marks(int min_marks) {
        this.min_marks = min_marks;
    }

    public String getRoundName() {
        return roundName;
    }

    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }

    public JobPost getJobPost() {
        return jobPost;
    }

    public void setJobPost(JobPost jobPost) {
        this.jobPost = jobPost;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}