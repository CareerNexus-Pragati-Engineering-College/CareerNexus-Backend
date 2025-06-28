package com.CareerNexus_Backend.CareerNexus.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class Recruiter {

    @Id
    @Column(name = "userId")
    private String UserId;


    @Column(name = "email")
    private String Email;

    @Column(name = "firstName")
    private String FirstName;

    @Column(name = "lastName")
    private String LastName;

    @Column(name = "company")
    private String Company;

    @Column(name = "designation")
    private String designation;


    @Column(name = "phone")
    private String Phone;


    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }



}
