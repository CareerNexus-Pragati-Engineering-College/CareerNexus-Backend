package com.CareerNexus_Backend.CareerNexus.dto;

public class Temp {
    private String userId;
    private String firstName;
    private String lastName;
    private String urls;

    public Temp(String userId, String firstName, String lastName, String urls) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.urls = urls;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }
}
