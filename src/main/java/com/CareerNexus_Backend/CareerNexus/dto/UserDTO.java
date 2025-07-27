package com.CareerNexus_Backend.CareerNexus.dto;

import com.CareerNexus_Backend.CareerNexus.model.Recruiter;
import com.CareerNexus_Backend.CareerNexus.model.Student;
import com.CareerNexus_Backend.CareerNexus.model.TPO;
import com.CareerNexus_Backend.CareerNexus.model.User;
import com.CareerNexus_Backend.CareerNexus.service.StudentServices;

public class UserDTO {
    private String userId;
    private String email;
    private String role;
    private String firstName;
    private String lastName;



    public UserDTO() {}

    public UserDTO(User user) {
        if (user != null) {
            this.userId = user.getUserId();
            this.email = user.getEmail();
            this.role = user.getRole();
        }
    }

    // Constructor 2: For Student Profile (populates from StudentDetails)
    public UserDTO(Student studentDetails) {
        if (studentDetails != null) {
            this.userId = studentDetails.getUserId();
            this.firstName = studentDetails.getFirstName();
            this.lastName = studentDetails.getLastName();

            if (studentDetails.getUser() != null) {
                this.email = studentDetails.getUser().getEmail();
                this.role = studentDetails.getUser().getRole();
            }
        }
    }
    public UserDTO(Recruiter recruiterDetails) {
        if (recruiterDetails != null) {
            this.userId = recruiterDetails.getUserId();
            this.firstName = recruiterDetails.getFirstName();
            this.lastName = recruiterDetails.getLastName();

            if (recruiterDetails.getUser() != null) {
                this.email = recruiterDetails.getUser().getEmail();
                this.role = recruiterDetails.getUser().getRole();
            }
        }
    }

    public UserDTO(TPO tpoDetails) {
        if (tpoDetails != null) {
            this.userId = tpoDetails.getUserId();
            this.firstName = tpoDetails.getFirstName();
            this.lastName = tpoDetails.getLastName();

            if (tpoDetails.getUser() != null) {
                this.email = tpoDetails.getUser().getEmail();
                this.role = tpoDetails.getUser().getRole();
            }
        }
    }

    // Constructor to convert from User entity to UserDTO
    /*
    public UserDTO(User user) {
        if (user != null) {
            this.userId = user.getUserId();
            this.email = user.getEmail();
            this.role = user.getRole();
            if ("student".equalsIgnoreCase(user.getRole())) {
                Student student= new StudentServices().getProfileForStudent(user.getUserId()).get();
                this.firstName=student.getFirstName();
                this.lastName=student.getLastName();
            } else if ("recruiter".equalsIgnoreCase(user.getRole())) {
                Recruiter recruiterDetails = user.getRecruiterDetails();
                if (recruiterDetails != null) {
                    this.firstName = recruiterDetails.getFirstName();
                    this.lastName = recruiterDetails.getLastName();
                }
            }
        }
    }*/

//    public UserDTO(Student studentDetails) {
//        if (studentDetails != null) {
//            this.userId = studentDetails.getUserId();
//            this.firstName = studentDetails.getFirstName();
//            this.lastName = studentDetails.getLastName();
//            if (studentDetails.getUser() != null) {
//                this.email = studentDetails.getUser().getEmail();
//                this.role = studentDetails.getUser().getRole();
//            }
//        }
//    }
//

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

