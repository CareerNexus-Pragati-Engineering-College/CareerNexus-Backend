package com.CareerNexus_Backend.CareerNexus.model;



import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    private String userId;

    private String email;
    private String password;
    private String role;
    // One-to-one relationship to StudentDetails (for ALL student-specific info, including names/phone)

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Student studentDetails;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Recruiter recruiterDetails;





    // Constructors
    public User() {}

    public User(String userId, String password){
        this.userId = userId;

        this.password = password;
    }


    public User(String userId, String email, String password){
        this.userId = userId;
        this.email = email;
        this.password = password;
    }
    public User(String userId, String email, String password, String role) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.role = role;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Student getStudentDetails() {
        return studentDetails;
    }



    public Recruiter getRecruiterDetails() {
        return recruiterDetails;
    }



    public void setStudentDetails(Student studentDetails) {
        this.studentDetails = studentDetails;
        if (studentDetails != null) {
            studentDetails.setUser(this);
        }
    }



    public void setRecruiterDetails(Recruiter recruiterDetails) {
        this.recruiterDetails = recruiterDetails;
        if (recruiterDetails != null) {
            recruiterDetails.setUser(this);
        }
    }


}
