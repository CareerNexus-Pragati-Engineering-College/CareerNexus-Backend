package com.CareerNexus_Backend.CareerNexus.dto;

import com.CareerNexus_Backend.CareerNexus.model.JobPost;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;



public class JobPostDTO {
    private Long id;
    private UserDTO postedBy;
    private String companyName;
    private String jobTitle;
    private String salaryPackage;
    private LocalDate applicationDeadline;
    private String locations;
    private String jobDescription;
    private String recruitmentProcess;
    private LocalDate postedAt;
    private String name;
    private String userId;

    private RecruiterDetailsDTO recruiterDetailsDTO;

    // Default constructor
    public JobPostDTO() {}

    // Constructor to convert from JobPost entity to JobPostDTO
    public JobPostDTO(JobPost jobPost) {
        if (jobPost != null) {
            this.id = jobPost.getId();
            this.companyName = jobPost.getCompanyName();
            this.jobTitle = jobPost.getJobTitle();
            this.salaryPackage = jobPost.getSalaryPackage();
            this.applicationDeadline = jobPost.getApplicationDeadline();
            this.locations = jobPost.getLocations();
            this.jobDescription = jobPost.getJobDescription();
            this.recruitmentProcess = jobPost.getRecruitmentProcess();
            this.postedAt = jobPost.getPostedAt();

            if (jobPost.getPostedBy() != null) {
                this.postedBy = new UserDTO(jobPost.getPostedBy());
            }
        }
    }

    public JobPostDTO(Long job_id,LocalDate applicationDeadline,String companyName,String jobTitle,String userId,String firstName,String lastName,LocalDate postedAt){
        this.id=job_id;
        this.applicationDeadline=applicationDeadline;
        this.companyName=companyName;
        this.jobTitle=jobTitle;
        this.userId=userId;
        this.name=firstName+" "+lastName;
        this.postedAt=postedAt;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(UserDTO postedBy) {
        this.postedBy = postedBy;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getSalaryPackage() {
        return salaryPackage;
    }

    public void setSalaryPackage(String salaryPackage) {
        this.salaryPackage = salaryPackage;
    }

    public LocalDate getApplicationDeadline() {
        return applicationDeadline;
    }

    public void setApplicationDeadline(LocalDate applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getRecruitmentProcess() {
        return recruitmentProcess;
    }

    public void setRecruitmentProcess(String recruitmentProcess) {
        this.recruitmentProcess = recruitmentProcess;
    }

    public LocalDate getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(LocalDate postedAt) {
        this.postedAt = postedAt;
    }

    public void setRecruiterDetails(RecruiterDetailsDTO recruiterDetailsDTO) {
        this.recruiterDetailsDTO=recruiterDetailsDTO;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public RecruiterDetailsDTO getRecruiterDetailsDTO() {
        return recruiterDetailsDTO;
    }

    public void setRecruiterDetailsDTO(RecruiterDetailsDTO recruiterDetailsDTO) {
        this.recruiterDetailsDTO = recruiterDetailsDTO;
    }

}