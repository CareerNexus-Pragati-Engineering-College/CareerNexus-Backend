package com.CareerNexus_Backend.CareerNexus.model;

import jakarta.persistence.*;


@Entity
@Table(name = "StudentDetails")

public class Student {

    @Id
    @Column(name = "userId")
    private String userId;

    @OneToOne(fetch = FetchType.LAZY) // One-to-one relationship with User
    @MapsId // Indicates that the PK is mapped from the User entity's PK
    @JoinColumn(name = "user_id") // This creates the foreign key column in recruiter_details table
    private User user; // Reference to the User entity (this is the field mappedBy="user" in User entity)


    @Column(name = "skills")
    private String skills ;

    @Column(name = "email")
    private String Email;

    @Column(name = "firstName")
    private String FirstName;

    @Column(name = "lastName")
    private String LastName;

    @Column(name = "department")
    private String Department;

    @Column(name = "cgpa")
    private float CGPA;

    @Column(name = "phone")
    private String Phone;

    @Column(name = "year")
    private String Year;

    @Column(name = "graduation_year")
    private String GraduationYear;



    @Column(name="urls")
    private String urls;

    public Student() {
    }

    public Student(String userId, String skills, String email, String firstName, String lastName, String department, float CGPA, String phone, String year, String graduationYear, String urls,User user) {
        this.userId = userId;
        this.skills = skills;
        Email = email;
        FirstName = firstName;
        LastName = lastName;
        Department = department;
        this.CGPA = CGPA;
        Phone = phone;
        Year = year;
        GraduationYear = graduationYear;
        this.urls = urls;
        this.user=user;
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
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

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public float getCGPA() {
        return CGPA;
    }

    public void setCGPA(float CGPA) {
        this.CGPA = CGPA;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getGraduationYear() {
        return GraduationYear;
    }

    public void setGraduationYear(String graduationYear) {
        GraduationYear = graduationYear;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }
}