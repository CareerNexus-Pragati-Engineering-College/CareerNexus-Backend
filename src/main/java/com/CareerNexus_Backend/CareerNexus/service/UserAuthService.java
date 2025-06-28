package com.CareerNexus_Backend.CareerNexus.service;

import com.CareerNexus_Backend.CareerNexus.model.User;
import org.springframework.http.ResponseEntity;


public interface UserAuthService {
    ResponseEntity<String> login(User user);
    User registerUser(User user);
}
