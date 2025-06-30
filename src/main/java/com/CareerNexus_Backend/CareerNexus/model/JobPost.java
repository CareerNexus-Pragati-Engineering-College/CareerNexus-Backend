package com.CareerNexus_Backend.CareerNexus.model;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String Title;
    private String Package;

    private LocalDate Deadline;
    private LocalDate postedAt;

    @ElementCollection
    private List<String> Location; // ArrayList of Indian states

    @Column(length = 2000)
    private String jobDescription;

    @Column(length = 2000)
    private String recruitmentProcess;

   private String recruiterUserId; // To link post to recruiter

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public LocalDate getDeadline() {
        return Deadline;
    }

    public void setDeadline(LocalDate deadline) {
        Deadline = deadline;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public List<String> getLocation() {
        return Location;
    }

    public void setLocation(List<String> location) {
        Location = location;
    }

    public String getPackage() {
        return Package;
    }

    public void setPackage(String aPackage) {
        Package = aPackage;
    }

    public LocalDate getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(LocalDate postedAt) {
        this.postedAt = postedAt;
    }



    public String getRecruitmentProcess() {
        return recruitmentProcess;
    }

    public void setRecruitmentProcess(String recruitmentProcess) {
        this.recruitmentProcess = recruitmentProcess;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }


    public String getRecruiterUserId() {
        return recruiterUserId;
    }

    public void setRecruiterUserId(String recruiterUserId) {
        this.recruiterUserId = recruiterUserId;
    }
}
