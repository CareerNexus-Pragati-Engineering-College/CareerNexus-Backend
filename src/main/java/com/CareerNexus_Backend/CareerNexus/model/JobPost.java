package com.CareerNexus_Backend.CareerNexus.model;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.time.LocalTime;


@Entity
public class JobPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id") // Explicitly define column name if different from field name
    private Long id; // Unique identifier for each job post

    // Many-to-One relationship to the User who posted this job.
    // 'posted_by_user_id' will be the foreign key in the 'job_posts' table.
 @ManyToOne(fetch = FetchType.EAGER) // Lazy fetching for performance
    @JoinColumn(name = "posted_by_user_id", nullable = false) // Foreign key column, cannot be null
    private User postedBy; // The User entity representing the recruiter/TPO who created this post

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "job_title", nullable = false) // Renamed from 'Title' for better clarity
    private String jobTitle;

    @Column(name = "salary_package") // Renamed from 'Package' for better clarity
    private String salaryPackage; // Store as String to allow flexibility (e.g., "5-10 LPA", "Negotiable")

    @Column(name = "application_deadline", nullable = false)
    private LocalDate applicationDeadline;

    @Column(name = "posted_at", nullable = false)
    private LocalDate postedAt; // Date when the job was posted

    // Using @ElementCollection for a list of simple types (like Strings for locations)
    // This will create a separate join table to store the locations for each job post.
    @ElementCollection(fetch = FetchType.LAZY) // Lazy fetch for efficiency
    @CollectionTable(name = "job_post_locations", // Name of the join table
            joinColumns = @JoinColumn(name = "job_post_id")) // Foreign key in join table linking back to job_posts
    @Column(name = "location") // Column name in the 'job_post_locations' table
    private List<String> locations; // List of locations (e.g., "Hyderabad", "Bengaluru", "Remote")

    @Column(name = "job_description", length = 2000, columnDefinition = "TEXT", nullable = false)
    private String jobDescription;

    @Column(name = "recruitment_process", length = 2000, columnDefinition = "TEXT")
    private String recruitmentProcess; // Details about interview rounds, etc.

    public Long getId() {
        return id;
    }

    public JobPost(Long id, User postedBy, String companyName, String jobTitle, String salaryPackage, LocalDate applicationDeadline,  List<String> locations, String jobDescription, String recruitmentProcess) {
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

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
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
