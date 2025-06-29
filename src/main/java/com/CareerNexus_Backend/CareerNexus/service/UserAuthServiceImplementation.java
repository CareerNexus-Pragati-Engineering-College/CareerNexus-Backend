package com.CareerNexus_Backend.CareerNexus.service;


import com.CareerNexus_Backend.CareerNexus.exceptions.DuplicateUserException;
import com.CareerNexus_Backend.CareerNexus.model.User;
import com.CareerNexus_Backend.CareerNexus.repository.UserAuthRepository;
import com.CareerNexus_Backend.CareerNexus.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserAuthServiceImplementation  implements  UserAuthService{


    private final UserAuthRepository userAuthRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RecruiterServices recruiterServices;

    @Autowired
    private StudentServices studentServices;

    public UserAuthServiceImplementation(UserAuthRepository userAuthRepository, PasswordEncoder passwordEncoder){
        this.passwordEncoder=passwordEncoder;
        this.userAuthRepository=userAuthRepository;
    }

    public User registerUser(User user){
        //checks whether the student already or not if yes it send a exception to the user signup page
        Optional<User> existingStudent = userAuthRepository.findByUserId(user.getUserId());
        if (existingStudent.isPresent()) {
            // Throw the custom exception to signal the conflict
            throw new DuplicateUserException(HttpStatus.CONFLICT.value(), "User with userId '" + user.getUserId() + "' already exists.");
        }
        try {
            // This line the raw password is encrypted using cryptographic techniques
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // User Signup details stored in db
            return userAuthRepository.save(user);

        } catch (Exception e) {
            // Log the exception for debugging
            System.err.println("Error inserting user: " + e.getMessage());
            // You might want to throw a more specific custom exception here,
            // or return null/Optional.empty() depending on your error handling strategy.
            throw new RuntimeException("Failed to register user: " + e.getMessage(), e);
        }
    }

    public ResponseEntity<Map<String, String>> login(User user) {
        try {
            System.out.println("Authenticating user: " + user.getUserId());

            // Authenticate user credentials
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword())
            );

            // Set the authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate JWT token

            String token = jwtUtils.getToken(user.getUserId());

            String currentLoggedUserRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString().substring(6);
            String currentRole = currentLoggedUserRole.substring(0, currentLoggedUserRole.length() - 1);

            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("token", token);

            // Check if the current user is a 'student' and if they are available via studentServices.
            if (currentRole.equals("student") && studentServices.isStudentAvailable(user)) {
               responseBody.put("router", "/profile?page=data");
                return ResponseEntity.status(200).body(responseBody);
            }
            // Else, check if the current user is a 'recruiter' and if they are available via recruiterProfileService.
            else if (currentRole.equals("recruiter") && recruiterServices.isRecruiterAvailable(user)) {
                responseBody.put("router", "/profile?page=data");
                return ResponseEntity.status(200).body(responseBody);
            }

            responseBody.put("router", "/profile?page=update");
            return ResponseEntity.status(200).body(responseBody);
        }
             catch (Exception e) {
                 Map<String, String> errorBody = new HashMap<>();
                 errorBody.put("error", "Login Failed");
                 errorBody.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorBody);

        }


    }


}