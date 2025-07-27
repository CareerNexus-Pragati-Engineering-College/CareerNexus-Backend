package com.CareerNexus_Backend.CareerNexus.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "question_pdf_url", columnDefinition = "TEXT", nullable = false)
    private String questionPdfUrl;

    @Column(name = "answer_pdf_url", columnDefinition = "TEXT")
    private String answerPdfUrl;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;



    public AssessmentRound() {
        this.createdAt = LocalDateTime.now();
    }


    public AssessmentRound(User createdBy, JobPost jobPost, String roundName, LocalDateTime startTime, LocalDateTime endTime,
                           String questionPdfUrl, String answerPdfUrl) {
        this();
        this.createdBy = createdBy;
        this.jobPost = jobPost;
        this.roundName = roundName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.questionPdfUrl = questionPdfUrl;
        this.answerPdfUrl = answerPdfUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public JobPost getJobPost() {
        return jobPost;
    }

    public void setJobPost(JobPost jobPost) {
        this.jobPost = jobPost;
    }

    public String getRoundName() {
        return roundName;
    }

    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getQuestionPdfUrl() {
        return questionPdfUrl;
    }

    public void setQuestionPdfUrl(String questionPdfUrl) {
        this.questionPdfUrl = questionPdfUrl;
    }

    public String getAnswerPdfUrl() {
        return answerPdfUrl;
    }

    public void setAnswerPdfUrl(String answerPdfUrl) {
        this.answerPdfUrl = answerPdfUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}