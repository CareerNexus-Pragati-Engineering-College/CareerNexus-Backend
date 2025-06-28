package com.CareerNexus_Backend.CareerNexus.controller;

// Removed unused import: import com.CareerNexus_Backend.CareerNexus.model.Student;
import com.CareerNexus_Backend.CareerNexus.exceptions.DuplicateUserException;
import com.CareerNexus_Backend.CareerNexus.model.User;
import com.CareerNexus_Backend.CareerNexus.security.JwtUtils;
import com.CareerNexus_Backend.CareerNexus.service.UserAuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin; // Keep if needed
import org.slf4j.Logger; // For logging
import org.slf4j.LoggerFactory; // For logging
import org.springframework.security.authentication.AuthenticationManager;
import javax.naming.AuthenticationException;


@RestController
@RequestMapping("/auth")
public class UserAuthController {

    // Use a logger instead of System.out.println
    private static final Logger logger = LoggerFactory.getLogger(UserAuthController.class);


    @Autowired
    private UserAuthService userAuthService;

    // this method mainly focus on student signup details to store in db
    @PostMapping("/student/register")
    public ResponseEntity<?> studentRegistration(@RequestBody User student) {
        logger.info("Received student registration request for username: {}", student.getUserId());
        try {
            student.setRole("student");
            User registeredStudent = userAuthService.registerUser(student);
            logger.info("Student registered successfully: {}", registeredStudent.getUserId());
            return new ResponseEntity<>(registeredStudent, HttpStatus.CREATED);
        } catch (DuplicateUserException e) { // Catch specific exception for existing user

            return new ResponseEntity<>(new DuplicateUserException(HttpStatus.CONFLICT.value(), "User with this userId already exists."), HttpStatus.CONFLICT);
        } catch (Exception e) { // Catch other unexpected exceptions

            // Return a generic error message to the client, log full details on server
            return new ResponseEntity<>(new DuplicateUserException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred during registration."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // this method mainly focus on tpo signup details to store in db
    @PostMapping("/tpo/register")
    public ResponseEntity<?> tpoRegistration(@RequestBody User tpo) {
        logger.info("Received TPO registration request for username: {}", tpo.getUserId());
        try {
            tpo.setRole("tpo");
            User registeredTpo = userAuthService.registerUser(tpo);
            logger.info("TPO registered successfully: {}", registeredTpo.getUserId());
            return new ResponseEntity<>(registeredTpo, HttpStatus.CREATED);
        } catch (DuplicateUserException e) {

            return new ResponseEntity<>(new DuplicateUserException(HttpStatus.CONFLICT.value(), "User with this userId already exists."), HttpStatus.CONFLICT);
        } catch (Exception e) {

            return new ResponseEntity<>(new DuplicateUserException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred during registration."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // this method mainly focus on Recruiter signup details to store in db
    @PostMapping("/recruiter/register")
    public ResponseEntity<?> recruiterRegistration(@RequestBody User recruiter) {
        logger.info("Received Recruiter registration request for username: {}", recruiter.getUserId());
        try {
            recruiter.setRole("recruiter"); // Fixed typo
            User registeredRecruiter = userAuthService.registerUser(recruiter);
            logger.info("Recruiter registered successfully: {}", registeredRecruiter.getUserId());
            return new ResponseEntity<>(registeredRecruiter, HttpStatus.CREATED);
        } catch (DuplicateUserException e) {

            return new ResponseEntity<>(new DuplicateUserException(HttpStatus.CONFLICT.value(), "User with this userId already exists."), HttpStatus.CONFLICT);
        } catch (Exception e) {

            return new ResponseEntity<>(new DuplicateUserException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred during registration."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/login")

    public ResponseEntity<String> authenticateUser(@RequestBody User user) throws AuthenticationException {
      return userAuthService.login(user);
    }
}