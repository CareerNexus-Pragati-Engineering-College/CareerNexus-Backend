package com.CareerNexus_Backend.CareerNexus.controller;

import com.CareerNexus_Backend.CareerNexus.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
// @CrossOrigin(origins = "*") // allow frontend access (optional)
public class UserAuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public User login(@RequestBody User loginRequest) {
        return authService.login(loginRequest.getUserId(), loginRequest.getPassword());
    }

}
