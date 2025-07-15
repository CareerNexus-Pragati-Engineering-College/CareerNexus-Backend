package com.CareerNexus_Backend.CareerNexus.model;

import jakarta.persistence.*;

import java.time.LocalDateTime; // For created/updated timestamps (good practice)


@Entity
@Table(name = "RecruiterDetails") // Ensure your DB table is named RecruiterDetails
public class Recruiter {

    @Id
    @Column(name = "user_id")
    private String userId;


    @OneToOne(fetch = FetchType.LAZY) // One-to-one relationship with User
    @MapsId // Indicates that the PK is mapped from the User entity's PK
    @JoinColumn(name = "user_id") // This creates the foreign key column in recruiter_details table
    private User user; // Reference to the User entity (this is the field mappedBy="user" in User entity)




    @Column(name = "email")
    private String email; // Corrected casing

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "company")
    private String company; // Corrected casing

    @Column(name = "designation")
    private String designation; // Already correct casing, good!

    @Column(name = "phone")
    private String phone; // Corrected casing

    // Optional: Timestamps for auditing
    @Column(name = "created_at")
    private LocalDateTime createdAt;




    public Recruiter(){}

    // Constructor with all fields
    public Recruiter(String userId,  String email, String firstName, String lastName, String company, String designation, String phone,User user) {
        this.userId = userId;

        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.designation = designation;
        this.phone = phone;
        this.createdAt = LocalDateTime.now(); // Set creation time
        this.user=user;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId; // FIXED: Use 'this' to refer to the class field
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEmail() {
        return email; // Corrected casing
    }

    public void setEmail(String email) {
        this.email = email; // Corrected casing
    }

    public String getFirstName() {
        return firstName; // Corrected casing
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName; // Corrected casing
    }

    public String getLastName() {
        return lastName; // Corrected casing
    }

    public void setLastName(String lastName) {
        this.lastName = lastName; // Corrected casing
    }

    public String getCompany() {
        return company; // Corrected casing
    }

    public void setCompany(String company) {
        this.company = company; // Corrected casing
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPhone() {
        return phone; // Corrected casing
    }

    public void setPhone(String phone) {
        this.phone = phone; // Corrected casing
    }

    // Getters and setters for timestamps
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }



    @Override
    public String toString() {
        return "Recruiter{" +
                "userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", company='" + company + '\'' +
                ", designation='" + designation + '\'' +
                ", phone='" + phone + '\'' +
                ", createdAt=" + createdAt +

                '}';
    }
}