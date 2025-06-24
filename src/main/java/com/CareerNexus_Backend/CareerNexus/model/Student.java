package com.CareerNexus_Backend.CareerNexus.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "StudentDetails")

public class Student {

    @Id
    @Column(name = "s_rollNo")
    private String s_rollNo;

    @Column(name = "s_name")
    private String s_name;

    @Column(name = "s_email")
    private String s_email;

    @Column(name = "s_password")
    private String s_password;

    public Student() {
    }

    public Student(String s_email, String s_name, String s_password, String s_rollNo) {
        this.s_email = s_email;
        this.s_name = s_name;
        this.s_password = s_password;
        this.s_rollNo = s_rollNo;
    }

    public String getS_email() {
        return s_email;
    }

    public void setS_email(String s_email) {
        this.s_email = s_email;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getS_password() {
        return s_password;
    }

    public void setS_password(String s_password) {
        this.s_password = s_password;
    }

    public String getS_rollNo() {
        return s_rollNo;
    }

    public void setS_rollNo(String s_rollNo) {
        this.s_rollNo = s_rollNo;
    }
}
