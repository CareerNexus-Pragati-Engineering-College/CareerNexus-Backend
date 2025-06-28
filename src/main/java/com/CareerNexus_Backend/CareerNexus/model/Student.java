package com.CareerNexus_Backend.CareerNexus.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "StudentDetails")

public class Student {

    @Id
    @Column(name = "userId")
    private String UserId;

    @Column(name = "password")
    private String Password;

    @ElementCollection
    @CollectionTable(name = "student_skills", joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "skills")
    private List<String> skills = new ArrayList<>();

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




    public Student() {
    }

    public Student(float CGPA, String department, String firstName, String password,String lastName, String phone, String email, String UserId, String year, List<String> skills) {
        this.CGPA = CGPA;
        this.Department = department;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Phone = phone;
        this.Email = email;
        this.UserId = UserId;
        this.Year = year;
        this.skills = skills;
        this.Password=password;
    }

    public float getCGPA() {
        return CGPA;
    }

    public void setCGPA(float CGPA) {
        this.CGPA = CGPA;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
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

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        this.UserId = userId;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }



    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}






