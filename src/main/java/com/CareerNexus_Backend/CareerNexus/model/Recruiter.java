// src/main/java/com/CareerNexus_Backend/CareerNexus/model/RecruiterDetails.java
package com.CareerNexus_Backend.CareerNexus.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "recruiter_details")
public class Recruiter {

    @Id
    @Column(name = "user_id")
    private String userId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // Indicates that the PK (userId) is mapped from the User entity's PK
    @JoinColumn(name = "user_id") // This explicitly defines the foreign key column in recruiter_details table
    private User user; // Reference to the User entity (this is the field mappedBy="user" in User entity)

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "company", nullable = false)
    private String company;

    @Column(name = "designation", nullable = false)
    private String designation;

    @Column(name = "phone")
    private String phone;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Version
    private Long version;


    public Recruiter() {

    }


    public Recruiter(User user, String email, String firstName, String lastName,
                            String company, String designation, String phone) {
        this.user = user;
        this.userId = user.getUserId();
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.designation = designation;
        this.phone = phone;

    }

    // Getters and Setters for all fields
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; } // Be cautious: With @MapsId, this is typically managed by Hibernate/User
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }
    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

}