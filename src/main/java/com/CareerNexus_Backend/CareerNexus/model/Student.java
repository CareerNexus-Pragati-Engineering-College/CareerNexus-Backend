package com.CareerNexus_Backend.CareerNexus.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


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


    @Column(name = "skills", nullable = false)
    private String skills ;

    @Column(name = "email", nullable = false)
    private String Email;

    @Column(name = "firstName", nullable = false)
    private String FirstName;

    @Column(name = "lastName", nullable = false)
    private String LastName;

    @Column(name = "department", nullable = false)
    private String Department;

    @Column(name = "cgpa", nullable = false)
    private float CGPA;

    @Column(name = "phone", nullable = false)
    private String Phone;

    @Column(name = "year", nullable = false)
    private String Year;

    @Column(name = "graduation_year", nullable = false)
    private String GraduationYear;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name="urls")
    private String urls;

    @Column(name = "img_loc")
    private String img_loc;

    @Version
    private Long version;

    public Student() {
    }

    public Student(String skills, String email, String firstName, String lastName, String department, float CGPA, String phone, String year, String graduationYear, String urls,User user, String img_loc) {
        this.userId = user.getUserId();
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
        this.img_loc = img_loc;
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

    public String getImg_loc() {
        return img_loc;
    }

    public void setImg_loc(String img_loc) {
        this.img_loc = img_loc;
    }
}