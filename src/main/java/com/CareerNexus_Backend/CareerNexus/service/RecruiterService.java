package com.CareerNexus_Backend.CareerNexus.service;



import com.CareerNexus_Backend.CareerNexus.model.Recruiter;
import com.CareerNexus_Backend.CareerNexus.model.Student;
import com.CareerNexus_Backend.CareerNexus.model.User;
import com.CareerNexus_Backend.CareerNexus.repository.RecruiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
    public class RecruiterService {

        @Autowired
        private RecruiterRepository recruiterRepository;

        public boolean isRecruiterAvailable (User user){
            Optional<Recruiter> isData=recruiterRepository.findByUserId(user.getUserId());
            if(isData.isEmpty()){
                return true;
            }
            return false;
}

        public Recruiter createOrUpdateProfile(Recruiter recruiter, String userId) {
            recruiter.setUserId(userId); // Directly set the userId

            return recruiterRepository.findByUserId(userId)
                    .map(existing -> {
                        existing.setFirstName(recruiter.getFirstName());
                        existing.setLastName(recruiter.getLastName());
                        existing.setCompany(recruiter.getCompany());
                        existing.setDesignation(recruiter.getDesignation());
                        return recruiterRepository.save(existing);
                    })
                    .orElseGet(() -> recruiterRepository.save(recruiter));
        }

    public Optional<Recruiter> getProfileData(String userId) {

        return recruiterRepository.findByUserId(userId);
    }
}

