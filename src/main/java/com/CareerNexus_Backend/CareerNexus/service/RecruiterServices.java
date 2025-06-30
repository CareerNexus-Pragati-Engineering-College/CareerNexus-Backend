package com.CareerNexus_Backend.CareerNexus.service;

import com.CareerNexus_Backend.CareerNexus.model.Recruiter;
import com.CareerNexus_Backend.CareerNexus.model.User;
import com.CareerNexus_Backend.CareerNexus.repository.RecruiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class RecruiterServices {

    @Autowired
    private final RecruiterRepository recruiterRepository;

    public RecruiterServices(RecruiterRepository recruiterRepository){
        this.recruiterRepository=recruiterRepository;
    }

    public boolean isRecruiterAvailable (User user){
        Optional<Recruiter> isData=recruiterRepository.findByUserId(user.getUserId());
        if(isData.isEmpty()){
            return true;
        }
       return false;
    }

    public boolean isProfileComplete(String userId) {
        return recruiterRepository.findByUserId(userId).map(recruiter ->
                recruiter.getCompany() != null &&
                        recruiter.getFirstName() != null &&
                        recruiter.getLastName() != null &&
                        recruiter.getEmail() != null &&
                        recruiter.getPhone() != null &&
                        recruiter.getUserId() != null &&
                        recruiter.getDesignation()!=null
        ).orElse(false);
    }}


