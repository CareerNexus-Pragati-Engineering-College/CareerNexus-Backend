package com.CareerNexus_Backend.CareerNexus.dto;


import java.time.LocalDateTime;


public class StudentsApplicationsDTO {

    private Long jobId;
    private String studentUserId;
    private String studentFirstName;
    private String studentLastName;
    private String studentEmail;
    private String studentDepartment;
    private Long applicationId;
    private LocalDateTime applicationDate;
    private String applicationStatus;
    private String resume_url;


    public String getResume_url() {
        return resume_url;
    }

    public void setResume_url(String resume_url) {
        this.resume_url = resume_url;
    }

    private String urls;
    private String year;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public StudentsApplicationsDTO(Long jobId, String studentUserId, String studentFirstName, String studentLastName, String studentEmail, String studentDepartment,String year, Long applicationId, LocalDateTime applicationDate, String applicationStatus, String urls, String resume_url) {
        this.jobId = jobId;
        this.studentUserId = studentUserId;
        this.studentFirstName = studentFirstName;
        this.year=year;
        this.studentLastName = studentLastName;
        this.studentEmail = studentEmail;
        this.studentDepartment = studentDepartment;
        this.applicationId = applicationId;
        this.applicationDate = applicationDate;
        this.applicationStatus = applicationStatus;
        this.urls = urls;
        this.resume_url=resume_url;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(String studentUserId) {
        this.studentUserId = studentUserId;
    }

    public String getStudentFirstName() {
        return studentFirstName;
    }

    public void setStudentFirstName(String studentFirstName) {
        this.studentFirstName = studentFirstName;
    }

    public String getStudentLastName() {
        return studentLastName;
    }

    public void setStudentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentDepartment() {
        return studentDepartment;
    }

    public void setStudentDepartment(String studentDepartment) {
        this.studentDepartment = studentDepartment;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public LocalDateTime getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDateTime applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }
}
