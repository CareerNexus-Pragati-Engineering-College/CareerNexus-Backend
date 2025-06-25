package com.CareerNexus_Backend.CareerNexus.service;


import com.CareerNexus_Backend.CareerNexus.model.User;
import org.springframework.beans.factory.annotation.Autowired;

public class UserAuthServiceImplementation {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User login(String userId, String password) {
        return userRepository.findByUserId(userId)
                .filter(user -> user.getPassword().equals(password))
                .orElseThrow(() -> new RuntimeException("Invalid user ID or password"));
    }
}
