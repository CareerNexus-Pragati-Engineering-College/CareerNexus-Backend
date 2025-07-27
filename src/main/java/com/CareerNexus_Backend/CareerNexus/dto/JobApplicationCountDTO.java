package com.CareerNexus_Backend.CareerNexus.dto;


public class JobApplicationCountDTO {
    private Long jobId;
    private Long applicationCount;
    private String jobTitle;
    private String jobDescription;


    public JobApplicationCountDTO(Long jobId,String jobTitle,String jobDescription, Long applicationCount) {
        this.jobId = jobId;
        this.applicationCount = applicationCount;
        this.jobTitle=jobTitle;
        this.jobDescription=jobDescription;
    }


    public JobApplicationCountDTO() {
    }


    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }


    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }


    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getApplicationCount() {
        return applicationCount;
    }

    public void setApplicationCount(Long applicationCount) {
        this.applicationCount = applicationCount;
    }

    public String getJobTitle() {
        return jobTitle;
    }
}