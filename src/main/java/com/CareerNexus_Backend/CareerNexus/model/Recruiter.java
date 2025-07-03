package com.CareerNexus_Backend.CareerNexus.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime; // For created/updated timestamps (good practice)


@Entity
@Table(name = "RecruiterDetails") // Ensure your DB table is named RecruiterDetails
public class Recruiter {

    @Id
    @Column(name = "user_id") // Best practice: use snake_case for column names
    // If you want the userId to be generated automatically (e.g., UUID):
    // @GeneratedValue(strategy = GenerationType.UUID) // Requires Hibernate 6+ for String UUID
    private String userId;

    // Password field is typically needed for user authentication
    @Column(name = "password")
    private String password; // Corrected casing

    @Column(name = "email")
    private String email; // Corrected casing

    @Column(name = "first_name") // Corrected casing, snake_case recommended
    private String firstName;

    @Column(name = "last_name") // Corrected casing, snake_case recommended
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
    public Recruiter(String userId, String password, String email, String firstName, String lastName, String company, String designation, String phone) {
        this.userId = userId;
        this.password = password; // Added password to constructor
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.designation = designation;
        this.phone = phone;
        this.createdAt = LocalDateTime.now(); // Set creation time

    }

    // --- CRITICAL FIX HERE ---
    // The previous setUserId was `userId = userId;` which assigns the parameter to itself,
    // not to the class field. It should be `this.userId = userId;`.
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId; // FIXED: Use 'this' to refer to the class field
    }

    // Getters and setters for password (add if not present)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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