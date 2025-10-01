package com.CareerNexus_Backend.CareerNexus.service;

import com.CareerNexus_Backend.CareerNexus.dto.RecruiterDetailsDTO;

import com.CareerNexus_Backend.CareerNexus.dto.UserDTO;
import com.CareerNexus_Backend.CareerNexus.dto.UsersDTO;
import com.CareerNexus_Backend.CareerNexus.exceptions.ResourceNotFoundException;
import com.CareerNexus_Backend.CareerNexus.model.Recruiter; // Corrected import
import com.CareerNexus_Backend.CareerNexus.model.User;
import com.CareerNexus_Backend.CareerNexus.repository.RecruiterRepository; // Assuming this is your repo name
import com.CareerNexus_Backend.CareerNexus.repository.UserAuthRepository; // Assuming you use UserRepository for User entity
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import Spring's Transactional
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
public class RecruiterService {

    @Autowired
    private RecruiterRepository recruiterDetailsRepository;

    @Autowired
    private UserAuthRepository userRepository;

    public boolean isRecruiterAvailable(UsersDTO user){
        Optional<Recruiter> isData=recruiterDetailsRepository.findByUserId(user.getUserId());
        if(isData.isEmpty()){
            return true;
        }
        return false;
    }
    @Value("${file.img.upload-dir}")
    private String uploadDir;
    @Transactional
    public RecruiterDetailsDTO createOrUpdateProfile(String userId, RecruiterDetailsDTO recruiterDetailsDTO, MultipartFile img) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        Optional<Recruiter> recruiterDetail=this.getProfileForRecruiter(userId);

        Recruiter recruiterDetails;
        if (recruiterDetail.isPresent()) {

            recruiterDetails = recruiterDetail.get();

            recruiterDetails.setFirstName(recruiterDetailsDTO.getFirstName());
            recruiterDetails.setLastName(recruiterDetailsDTO.getLastName());
            recruiterDetails.setCompany(recruiterDetailsDTO.getCompany());
            recruiterDetails.setDesignation(recruiterDetailsDTO.getDesignation());
            recruiterDetails.setPhone(recruiterDetailsDTO.getPhone());
            Files.copy(img.getInputStream(), Paths.get(uploadDir).resolve(recruiterDetails.getImg_loc().substring(1)),REPLACE_EXISTING);

        } else {
            String originalFilename = img.getOriginalFilename();
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String uniqueFileName =  UUID.randomUUID().toString() + "__"+ fileExtension;
            Path filePath = Paths.get(uploadDir).resolve(uniqueFileName);
            Files.copy(img.getInputStream(), filePath);

            recruiterDetails = new Recruiter(
                    user,
                    recruiterDetailsDTO.getEmail(),
                    recruiterDetailsDTO.getFirstName(),
                    recruiterDetailsDTO.getLastName(),
                    recruiterDetailsDTO.getCompany(),
                    recruiterDetailsDTO.getDesignation(),
                    recruiterDetailsDTO.getPhone(),
                    "/"+uniqueFileName
            );


        }

        return new RecruiterDetailsDTO( recruiterDetailsRepository.save(recruiterDetails));
    }

    @Transactional(readOnly = true) // Read-only transaction for fetching data
    public RecruiterDetailsDTO getProfileData(String userId) throws Exception { // userId is Long
        Recruiter recruiterDetails = recruiterDetailsRepository.findById(userId)
                .orElseThrow(() -> new Exception("Recruiter Profile not found for User ID: " + userId));
        return new RecruiterDetailsDTO(recruiterDetails);
    }

    @jakarta.transaction.Transactional()
    public Optional<Recruiter> getProfileForRecruiter(String userId) {

        return recruiterDetailsRepository.findById(userId);

    }

    @jakarta.transaction.Transactional()
    public UserDTO getStudentProfileAsUserDTO(String userId) {
        Recruiter recruiterDetails = recruiterDetailsRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found for User ID: " + userId));
        return new UserDTO(recruiterDetails);
    }

    public List<RecruiterDetailsDTO> getAllCompanies() {
        return recruiterDetailsRepository.findAllCompanies();
    }
}