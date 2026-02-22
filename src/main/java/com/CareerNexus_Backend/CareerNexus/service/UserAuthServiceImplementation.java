package com.CareerNexus_Backend.CareerNexus.service;

// import com.CareerNexus_Backend.CareerNexus.dto.ChangePasswordDTO;
import com.CareerNexus_Backend.CareerNexus.dto.UsersDTO;
import com.CareerNexus_Backend.CareerNexus.exceptions.DuplicateUserException;
// import com.CareerNexus_Backend.CareerNexus.exceptions.ResourceNotFoundException;
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

    public UserAuthServiceImplementation(UserAuthRepository userAuthRepository,
                                         PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userAuthRepository = userAuthRepository;
    }

    // ── Register ──────────────────────────────────────────────────────────────

    public User registerUser(User user) {
        Optional<User> existingStudent = userAuthRepository.findByUserId(user.getUserId());
        if (existingStudent.isPresent()) {
            throw new DuplicateUserException(HttpStatus.CONFLICT.value(),
                    "User with userId '" + user.getUserId() + "' already exists.");
        }
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userAuthRepository.save(user);
        } catch (Exception e) {
            System.err.println("Error inserting user: " + e.getMessage());
            throw new RuntimeException("Failed to register user: " + e.getMessage(), e);
        }
    }

    // ── Login ─────────────────────────────────────────────────────────────────

    @Transactional
    public ResponseEntity<Map<String, String>> login(UsersDTO user) {
        Authentication authentication;
        try {
            System.out.println("Authenticating user: " + user.getUserId());

            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUserId(), user.getPassword()));

            String currentAuthenticateUserRole = authentication.getAuthorities()
                    .toString().substring(6);
            Map<String, String> responseBody = new HashMap<>();

            String role = currentAuthenticateUserRole.substring(0,
                    currentAuthenticateUserRole.length() - 1);
            String token = jwtUtils.getToken(user.getUserId(), role);

            if (!currentAuthenticateUserRole.equals(user.getRole() + "]")) {
                if (currentAuthenticateUserRole.equals("admin]")
                        && user.getRole().equals("tpo")) {
                    System.out.println("admin authenticated successfully..");
                    responseBody.put("token", token);
                    responseBody.put("router", "/admin");
                    responseBody.put("msg", "redirecting to admin portal");
                    responseBody.put("role", "admin");
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    return ResponseEntity.status(200).body(responseBody);
                } else {
                    Map<String, String> errorBody = new HashMap<>();
                    errorBody.put("error", "Login Failed");
                    errorBody.put("message", "Unauthorized Access");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(errorBody);
                }
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String currentLoggedUserRole = SecurityContextHolder.getContext()
                    .getAuthentication().getAuthorities().toString().substring(6);
            String currentRole = currentLoggedUserRole.substring(0,
                    currentLoggedUserRole.length() - 1);

            responseBody.put("token", token);

            if (currentRole.equals("student")
                    && studentServices.isStudentAvailable(user)) {
                responseBody.put("router", "/profile?page=data&userId="
                        + user.getUserId() + "&email="
                        + userAuthRepository.findByUserId(user.getUserId())
                        .get().getEmail());
                return ResponseEntity.status(200).body(responseBody);
            } else if (currentRole.equals("recruiter")
                    && recruiterService.isRecruiterAvailable(user)) {
                responseBody.put("msg", "redirecting to profile");
                responseBody.put("router", "/profile?page=data&userId="
                        + user.getUserId() + "&email="
                        + userAuthRepository.findByUserId(user.getUserId())
                        .get().getEmail());
                return ResponseEntity.status(200).body(responseBody);
            } else if (currentRole.equals("tpo")
                    && tpoService.isTpoAvailable(user)) {
                responseBody.put("msg", "redirecting to profile");
                responseBody.put("router", "/profile?page=data&userId="
                        + user.getUserId() + "&email="
                        + userAuthRepository.findByUserId(user.getUserId())
                        .get().getEmail());
                return ResponseEntity.status(200).body(responseBody);
            }

            responseBody.put("msg", "redirecting to Home...");
            responseBody.put("router", "/home");
            return ResponseEntity.status(200).body(responseBody);

        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Login Failed");
            errorBody.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorBody);
        }
    }

//    // ── Change Password ───────────────────────────────────────────────────────
//
//    @Transactional
//    public ResponseEntity<Map<String, String>> changePassword(
//            String userId, ChangePasswordDTO request) {
//
//        // Step 1 — Find the user
//        User user = userAuthRepository.findByUserId(userId)
//                .orElseThrow(() -> new ResourceNotFoundException(
//                        "User not found with ID: " + userId));
//
//        // Step 2 — Verify current password is correct
//        if (!passwordEncoder.matches(
//                request.getCurrentPassword(), user.getPassword())) {
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body(Map.of("error", "Current password is incorrect"));
//        }
//
//        // Step 3 — New password and confirm password must match
//        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body(Map.of("error",
//                            "New password and confirm password do not match"));
//        }
//
//        // Step 4 — New password must not be same as current password
//        if (passwordEncoder.matches(
//                request.getNewPassword(), user.getPassword())) {
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body(Map.of("error",
//                            "New password cannot be the same as current password"));
//        }
//
//        // Step 5 — Minimum length check
//        if (request.getNewPassword().length() < 6) {
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body(Map.of("error",
//                            "New password must be at least 6 characters"));
//        }
//
//        // Step 6 — Encode and save
//        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
//        userAuthRepository.save(user);
//
//        return ResponseEntity.ok(Map.of(
//                "message", "Password changed successfully"));
//    }
}