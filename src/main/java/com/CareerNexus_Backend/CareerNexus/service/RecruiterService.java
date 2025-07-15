package com.CareerNexus_Backend.CareerNexus.service;

import com.CareerNexus_Backend.CareerNexus.dto.RecruiterDetailsDTO;

import com.CareerNexus_Backend.CareerNexus.model.Recruiter; // Corrected import
import com.CareerNexus_Backend.CareerNexus.model.Student;
import com.CareerNexus_Backend.CareerNexus.model.User;
import com.CareerNexus_Backend.CareerNexus.repository.RecruiterRepository; // Assuming this is your repo name
import com.CareerNexus_Backend.CareerNexus.repository.UserAuthRepository; // Assuming you use UserRepository for User entity
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import Spring's Transactional

import java.util.Optional;

@Service
public class RecruiterService {

    @Autowired
    private RecruiterRepository recruiterDetailsRepository; // Autowire the correct repository

    @Autowired
    private UserAuthRepository userRepository; // To fetch the User entity to link with

    public boolean isRecruiterAvailable(User user){
        Optional<Recruiter> isData=recruiterDetailsRepository.findByUserId(user.getUserId());
        if(isData.isEmpty()){
            return true;
        }
        return false;
    }

    @Transactional
    public RecruiterDetailsDTO createOrUpdateProfile(String userId, RecruiterDetailsDTO recruiterDetailsDTO) throws Exception {
        // 1. Fetch the User to link the profile to
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found with ID: " + userId));

        // 2. Check if a profile already exists for this user (for update scenario)
        Optional<Recruiter> existingProfile = recruiterDetailsRepository.findById(userId); // PK of RecruiterDetails is userId

        Recruiter recruiterDetails;
        if (existingProfile.isPresent()) {
            recruiterDetails = existingProfile.get();
            recruiterDetails.setFirstName(recruiterDetailsDTO.getFirstName());
            recruiterDetails.setLastName(recruiterDetailsDTO.getLastName());
            recruiterDetails.setCompany(recruiterDetailsDTO.getCompany());
            recruiterDetails.setDesignation(recruiterDetailsDTO.getDesignation());
            recruiterDetails.setPhone(recruiterDetailsDTO.getPhone());

        } else {

            recruiterDetails = new Recruiter(
                   recruiterDetailsDTO.getUserId(),
                    recruiterDetailsDTO.getEmail(),
                    recruiterDetailsDTO.getFirstName(),
                    recruiterDetailsDTO.getLastName(),
                    recruiterDetailsDTO.getCompany(),
                    recruiterDetailsDTO.getDesignation(),
                    recruiterDetailsDTO.getPhone(),
                    user
            );
            user.setRecruiterDetails(recruiterDetails); // Also set the bidirectional relationship from User side
        }

        // 3. Save the profile and convert to DTO for return
        return new RecruiterDetailsDTO(recruiterDetailsRepository.save(recruiterDetails));
    }

    @Transactional(readOnly = true) // Read-only transaction for fetching data
    public RecruiterDetailsDTO getProfileData(String userId) throws Exception { // userId is Long
        Recruiter recruiterDetails = recruiterDetailsRepository.findById(userId)
                .orElseThrow(() -> new Exception("Recruiter Profile not found for User ID: " + userId));
        return new RecruiterDetailsDTO(recruiterDetails);
    }
}