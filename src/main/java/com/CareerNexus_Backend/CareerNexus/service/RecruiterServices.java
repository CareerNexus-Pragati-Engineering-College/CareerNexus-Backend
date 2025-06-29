package com.CareerNexus_Backend.CareerNexus.service;

import com.CareerNexus_Backend.CareerNexus.model.Recruiter;
import com.CareerNexus_Backend.CareerNexus.model.User;
import com.CareerNexus_Backend.CareerNexus.repository.RecruiterRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class RecruiterServices {

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

}
