package com.CareerNexus_Backend.CareerNexus.dto;

import com.CareerNexus_Backend.CareerNexus.model.Recruiter;
import java.time.LocalDateTime;


public class RecruiterDetailsDTO {

    private String userId;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String designation;
    private String company;
    private LocalDateTime createdAt;
    private  String img_loc;


    public RecruiterDetailsDTO() {}


    public RecruiterDetailsDTO(Recruiter recruiterDetails) {
        if (recruiterDetails != null) {
            this.userId =recruiterDetails.getUserId();
            this.firstName = recruiterDetails.getFirstName();
            this.lastName = recruiterDetails.getLastName();
            this.phone = recruiterDetails.getPhone();
            this.company=recruiterDetails.getCompany();
            this.designation=recruiterDetails.getDesignation();
            this.email=recruiterDetails.getEmail();
            this.createdAt=recruiterDetails.getCreatedAt();
            this.img_loc=recruiterDetails.getImg_loc();
        }
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getImg_loc() {
        return img_loc;
    }

    public void setImg_loc(String img_loc) {
        this.img_loc = img_loc;
    }
}