package com.CareerNexus_Backend.CareerNexus.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "applications_table",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"student_user_id", "job_id"})
        })
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long id;

    // Many-to-One relationship to the User who is applying (a Student)
    // The 'student_user_id' will be the foreign key in the 'applications' table.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_user_id", nullable = false)
    private User student;

    // Many-to-One relationship to the JobPost being applied for
    // The 'job_id' will be the foreign key in the 'applications' table.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private JobPost jobPost;

    @Column(name = "application_date", nullable = false)
    private LocalDateTime applicationDate;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "feedback", columnDefinition = "TEXT")
    private String feedback;

    @Column(name = "applied_resume_url", columnDefinition = "TEXT")
    private String appliedResumeUrl;


    public Application() {
        this.applicationDate = LocalDateTime.now();
        this.status = "PENDING";
    }

    public Application(User student, JobPost jobPost) {
        this();
        this.student = student;
        this.jobPost = jobPost;
    }

    public Application(JobPost jobPost, User student, String resumeUrl) {
        this.jobPost=jobPost;
        this.student=student;
        this.appliedResumeUrl=resumeUrl;
        this.applicationDate = LocalDateTime.now();
        this.status = "PENDING";
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public JobPost getJobPost() {
        return jobPost;
    }

    public void setJobPost(JobPost jobPost) {
        this.jobPost = jobPost;
    }

    public LocalDateTime getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDateTime applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getAppliedResumeUrl() {
        return appliedResumeUrl;
    }

    public void setAppliedResumeUrl(String appliedResumeUrl) {
        this.appliedResumeUrl = appliedResumeUrl;
    }
}