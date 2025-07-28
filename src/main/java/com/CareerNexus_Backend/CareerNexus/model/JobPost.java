package com.CareerNexus_Backend.CareerNexus.model;



import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
public class JobPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Long id;

 @ManyToOne(fetch = FetchType.EAGER) // Lazy fetching for performance
    @JoinColumn(name = "posted_by_user_id", nullable = false) // Foreign key column, cannot be null
    private User postedBy; // The User entity representing the recruiter/TPO who created this post

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "job_title", nullable = false)
    private String jobTitle;

    @Column(name = "salary_package")
    private String salaryPackage;

    @Column(name = "application_deadline", nullable = false)
    private LocalDate applicationDeadline;

    @Column(name = "posted_at", nullable = false)
    private LocalDate postedAt; // Date when the job was posted

    @Column(name = "location")
    private String locations;

    @Column(name = "job_description", length = 2000, columnDefinition = "TEXT", nullable = false)
    private String jobDescription;

    @Column(name = "recruitment_process", length = 2000, columnDefinition = "TEXT")
    private String recruitmentProcess; // Details about interview rounds, etc.

    public Long getId() {
        return id;
    }





    public JobPost(Long id, User postedBy, String companyName, String jobTitle, String salaryPackage, LocalDate applicationDeadline, String locations, String jobDescription, String recruitmentProcess) {
        this.id = id;
        this.postedBy = postedBy;
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.salaryPackage = salaryPackage;
        this.applicationDeadline = applicationDeadline;
        this.locations = locations;
        this.jobDescription = jobDescription;
        this.recruitmentProcess = recruitmentProcess;
    }
    public JobPost(){}
    public void setId(Long id) {
        this.id = id;
    }

    public User getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(User postedBy) {
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

    public LocalDate getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(LocalDate postedAt) {
        this.postedAt = postedAt;
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


}
