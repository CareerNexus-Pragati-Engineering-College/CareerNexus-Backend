package com.CareerNexus_Backend.CareerNexus.service;

import com.CareerNexus_Backend.CareerNexus.model.User;


public interface UserAuthService {
    User login(String userId, String password);
    User registerUser(User user);
}
