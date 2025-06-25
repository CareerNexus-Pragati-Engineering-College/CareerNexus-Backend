package com.CareerNexus_Backend.CareerNexus.service;


import com.CareerNexus_Backend.CareerNexus.exceptions.DuplicateUserException;
import com.CareerNexus_Backend.CareerNexus.model.User;
import com.CareerNexus_Backend.CareerNexus.repository.UserAuthRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAuthServiceImplementation  implements  UserAuthService{


    private final UserAuthRepository userAuthRepository;
    private final PasswordEncoder passwordEncoder;

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

    public User login(String userId, String password) {
        return null;
        /*
        return userRepository.findByUserId(userId)
                .filter(user -> user.getPassword().equals(password))
                .orElseThrow(() -> new RuntimeException("Invalid user ID or password"));*/
    }
}
