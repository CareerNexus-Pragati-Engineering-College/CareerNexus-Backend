package com.CareerNexus_Backend.CareerNexus.dto;

import com.CareerNexus_Backend.CareerNexus.model.Application;
import java.time.LocalDateTime;

public class ApplicationDTO {

    private Long id;
    private UserDTO student;
    private JobPostDTO jobPost;
    private LocalDateTime applicationDate;
    private String status;
    private String feedback;
    private String appliedResumeUrl;


    public ApplicationDTO() {}


    public ApplicationDTO(Application application) {
        this.id = application.getId();
        this.applicationDate = application.getApplicationDate();
        this.status = application.getStatus();
        this.feedback = application.getFeedback();
        this.appliedResumeUrl = application.getAppliedResumeUrl();
        if (application.getStudent() != null) {
            this.student = new UserDTO(application.getStudent());
        }
        if (application.getJobPost() != null) {
            this.jobPost = new JobPostDTO(application.getJobPost());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getStudent() {
        return student;
    }

    public void setStudent(UserDTO student) {
        this.student = student;
    }

    public JobPostDTO getJobPost() {
        return jobPost;
    }

    public void setJobPost(JobPostDTO jobPost) {
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