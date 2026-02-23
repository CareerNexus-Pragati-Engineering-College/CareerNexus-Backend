package com.CareerNexus_Backend.CareerNexus.service;

import com.CareerNexus_Backend.CareerNexus.dto.UsersDTO;
import com.CareerNexus_Backend.CareerNexus.exceptions.DuplicateUserException;
import com.CareerNexus_Backend.CareerNexus.model.User;
import com.CareerNexus_Backend.CareerNexus.repository.UserAuthRepository;
import com.CareerNexus_Backend.CareerNexus.security.JwtUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserAuthServiceImplementation implements UserAuthService {

    private final UserAuthRepository userAuthRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RecruiterService recruiterService;

    @Autowired
    private StudentServices studentServices;

    @Autowired
    private TpoService tpoService;

    private static final Logger logger = LoggerFactory.getLogger(UserAuthServiceImplementation.class);

    public UserAuthServiceImplementation(UserAuthRepository userAuthRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userAuthRepository = userAuthRepository;
    }

    public User registerUser(User user) {
        // checks whether the student already or not if yes it send a exception to the
        // user signup page
        Optional<User> existingStudent = userAuthRepository.findByUserId(user.getUserId());
        if (existingStudent.isPresent()) {
            // Throw the custom exception to signal the conflict
            throw new DuplicateUserException(HttpStatus.CONFLICT.value(),
                    "User with userId '" + user.getUserId() + "' already exists.");
        }
        try {
            // This line the raw password is encrypted using cryptographic techniques
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // User Signup details stored in db
            return userAuthRepository.save(user);

        } catch (Exception e) {
            // Log the exception for debugging
            logger.error("Error inserting user: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to register user: " + e.getMessage(), e);
        }
    }

    @Transactional()
    public ResponseEntity<Map<String, String>> login(UsersDTO user) {
        try {
            logger.info("Authenticating user: {}", user.getUserId());

            // Authenticate user credentials
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword()));

            // Extract role safely from authorities
            String authenticatedRole = authentication.getAuthorities().stream()
                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                    .map(auth -> auth.startsWith("ROLE_") ? auth.substring(5) : auth)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No roles assigned to user"));

            Map<String, String> responseBody = new HashMap<>();

            // Security check: ensure the requested role matches the authenticated role
            if (!authenticatedRole.equalsIgnoreCase(user.getRole())) {
                // Special case: Admin can log into TPO flow
                if (authenticatedRole.equalsIgnoreCase("admin") && user.getRole().equalsIgnoreCase("tpo")) {
                    logger.info("Admin authenticated successfully for TPO flow.");
                } else {
                    logger.warn("Unauthorized access attempt: User {} (Role: {}) tried to log in as {}", 
                            user.getUserId(), authenticatedRole, user.getRole());
                    Map<String, String> errorBody = new HashMap<>();
                    errorBody.put("error", "Login Failed");
                    errorBody.put("message", "Unauthorized Access");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorBody);
                }
            }

            // Generate JWT token
            String token = jwtUtils.getToken(user.getUserId(), authenticatedRole);
            
            // Set the authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String currentRole = authenticatedRole.toLowerCase();
            responseBody.put("token", token);
            responseBody.put("role", currentRole);

            // Fetch user email safely
            String userEmail = userAuthRepository.findByUserId(user.getUserId())
                    .map(User::getEmail)
                    .orElse("");

            // Redirection logic
            if (currentRole.equals("admin")) {
                responseBody.put("router", "/admin");
                responseBody.put("msg", "redirecting to admin portal");
                return ResponseEntity.ok(responseBody);
            }

            // Check if the current user is a 'student' and if they are missing a profile.
            if (currentRole.equals("student") && studentServices.isProfileMissing(user)) {
                responseBody.put("router", "/profile?page=data&userId=" + user.getUserId() + "&email=" + userEmail);
                return ResponseEntity.ok(responseBody);
            }
            // Else, check if the current user is a 'recruiter' and if they are missing a profile.
            else if (currentRole.equals("recruiter") && recruiterService.isProfileMissing(user)) {
                responseBody.put("msg", "redirecting to profile");
                responseBody.put("router", "/profile?page=data&userId=" + user.getUserId() + "&email=" + userEmail);
                return ResponseEntity.ok(responseBody);
            }
            else if (currentRole.equals("tpo") && tpoService.isProfileMissing(user)) {
                responseBody.put("msg", "redirecting to profile");
                responseBody.put("router", "/profile?page=data&userId=" + user.getUserId() + "&email=" + userEmail);
                return ResponseEntity.ok(responseBody);
            }

            responseBody.put("msg", "redirecting to Home...");
            responseBody.put("router", "/home");
            return ResponseEntity.status(200).body(responseBody);
        } catch (Exception e) {
            logger.error("Authentication failed for user: {}. Error: {}", user.getUserId(), e.getMessage());
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Login Failed");
            errorBody.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(errorBody);

        }

    }

}