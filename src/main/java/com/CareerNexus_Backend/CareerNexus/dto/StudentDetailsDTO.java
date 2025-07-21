package com.CareerNexus_Backend.CareerNexus.dto;

import com.CareerNexus_Backend.CareerNexus.model.Student;
import com.CareerNexus_Backend.CareerNexus.model.User;


public class StudentDetailsDTO {

    private String userId;
    private String firstName;
    private String lastName;
    private String phone;
    private String department;
    private float cgpa;
    private String year;
    private String graduationYear;
    private String skills;
    private String email;
    private String urls;

    public StudentDetailsDTO() {}





    public StudentDetailsDTO(Student studentDetails) {
        if (studentDetails != null) {

            this.userId = studentDetails.getUserId();
            this.firstName = studentDetails.getFirstName();
            this.lastName = studentDetails.getLastName();
            this.phone = studentDetails.getPhone();
            this.department = studentDetails.getDepartment();
            this.cgpa = studentDetails.getCGPA();
            this.year = studentDetails.getYear();
            this.graduationYear = studentDetails.getGraduationYear();
            this.skills = studentDetails.getSkills();
            this.email=studentDetails.getEmail();
            this.urls=studentDetails.getUrls();
        }
    }

    public StudentDetailsDTO(String userId, String firstName, String lastName, String urls){
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.urls = urls;
        // Other fields will remain null/default if not passed to this constructor
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public float getCgpa() {
        return cgpa;
    }

    public void setCgpa(float cgpa) {
        this.cgpa = cgpa;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(String graduationYear) {
        this.graduationYear = graduationYear;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }
}