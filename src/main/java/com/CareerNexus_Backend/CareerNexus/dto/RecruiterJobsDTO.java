package com.CareerNexus_Backend.CareerNexus.dto;

import java.util.List;

public class RecruiterJobsDTO {
    private String userId;
    private String name;
    private List<JobPostDTO> jobPostDTO;
    private String Company;

    public RecruiterJobsDTO(String recruiterUserId, String recruiterName, List<JobPostDTO> jobsForThisRecruiter, String companyName) {
        this.userId=recruiterUserId;
        this.jobPostDTO=jobsForThisRecruiter;
        this.name=recruiterName;
        this.Company=companyName;
    }

    public void RecruiterDetailsDTO( ){}




    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<JobPostDTO> getJobPostDTO() {
        return jobPostDTO;
    }

    public void setJobPostDTO(List<JobPostDTO> jobPostDTO) {
        this.jobPostDTO = jobPostDTO;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }
}
