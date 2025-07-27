package com.CareerNexus_Backend.CareerNexus.service;

import com.CareerNexus_Backend.CareerNexus.dto.UsersDTO;
import com.CareerNexus_Backend.CareerNexus.model.User;
import org.springframework.http.ResponseEntity;

import java.util.Map;


public interface UserAuthService {
    ResponseEntity<Map<String,String>> login(UsersDTO user);
    User registerUser(User user);
}
