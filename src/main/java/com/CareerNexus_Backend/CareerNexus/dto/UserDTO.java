package com.CareerNexus_Backend.CareerNexus.dto;

import com.CareerNexus_Backend.CareerNexus.model.Recruiter;
import com.CareerNexus_Backend.CareerNexus.model.Student;
import com.CareerNexus_Backend.CareerNexus.model.User;

public class UserDTO {
    private String userId;
    private String email;
    private String role;
    private String firstName;
    private String lastName;



    public UserDTO() {}

    // Constructor to convert from User entity to UserDTO
    public UserDTO(User user) {
        if (user != null) {
            this.userId = user.getUserId();
            this.email = user.getEmail();
            this.role = user.getRole();
            if ("Student".equalsIgnoreCase(user.getRole())) {
                Student studentDetails = user.getStudentDetails();
                if (studentDetails != null) {
                    this.firstName = studentDetails.getFirstName();
                    this.lastName = studentDetails.getLastName();
                }
            } else if ("Recruiter".equalsIgnoreCase(user.getRole())) {
                Recruiter recruiterDetails = user.getRecruiterDetails();
                if (recruiterDetails != null) {
                    this.firstName = recruiterDetails.getFirstName();
                    this.lastName = recruiterDetails.getLastName();
                }
            }
        }
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }



}

