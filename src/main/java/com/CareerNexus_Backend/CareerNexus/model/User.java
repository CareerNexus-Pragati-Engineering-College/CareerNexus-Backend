// src/main/java/com/CareerNexus_Backend/CareerNexus/model/User.java
package com.CareerNexus_Backend.CareerNexus.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    private String userId;

    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Student studentDetails;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Recruiter recruiterDetails;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private TPO tpoDetails;

    // Constructors, Getters, and Setters
    public User() {}

    public User(String userId, String email, String password, String role) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters for all fields
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public Student getStudentDetails() { return studentDetails; }
    public void setStudentDetails(Student studentDetails) { this.studentDetails = studentDetails; }
    public Recruiter getRecruiterDetails() { return recruiterDetails; }
    public void setRecruiterDetails(Recruiter recruiterDetails) { this.recruiterDetails = recruiterDetails; }
    public TPO getTpoDetails(){
        return tpoDetails;
    }
    public void setTpoDetails(TPO tpoDetails){
        this.tpoDetails=tpoDetails;
    }
}