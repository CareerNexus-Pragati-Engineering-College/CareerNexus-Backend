package com.CareerNexus_Backend.CareerNexus.service;

import com.CareerNexus_Backend.CareerNexus.dto.StudentDetailsDTO;
import com.CareerNexus_Backend.CareerNexus.dto.UserDTO;
import com.CareerNexus_Backend.CareerNexus.dto.UsersDTO;
import com.CareerNexus_Backend.CareerNexus.exceptions.ResourceNotFoundException;
import com.CareerNexus_Backend.CareerNexus.model.Student;
import com.CareerNexus_Backend.CareerNexus.model.User;
import com.CareerNexus_Backend.CareerNexus.repository.StudentRepository;
import com.CareerNexus_Backend.CareerNexus.repository.UserAuthRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class StudentServices {

    private static final Logger logger = LoggerFactory.getLogger(StudentServices.class);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Autowired
    private SupabaseStorageService supabaseStorageService;

    /**
     * Checks if a student profile exists for the given user.
     * Note: Returns false if profile DOES exist (matching previous isStudentAvailable logic).
     * Refactored for better naming.
     */
    public boolean isProfileMissing(UsersDTO user) {
        return studentRepository.findByUserId(user.getUserId()).isEmpty();
    }

    @Transactional
    public StudentDetailsDTO createOrUpdateProfile(StudentDetailsDTO studentDetailsDTO,
                                                   String userId,
                                                   MultipartFile img) throws Exception {

        User user = userAuthRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.warn("User not found with ID: {}", userId);
                    return new ResourceNotFoundException("User not found with ID: " + userId);
                });

        Optional<Student> studentDetail = this.getProfileForStudent(userId);

        Student studentDetails;

        if (studentDetail.isPresent()) {

            // 🔁 UPDATE EXISTING PROFILE
            studentDetails = studentDetail.get();

            studentDetails.setUser(user);
            studentDetails.setFirstName(studentDetailsDTO.getFirstName());
            studentDetails.setLastName(studentDetailsDTO.getLastName());
            studentDetails.setPhone(studentDetailsDTO.getPhone());
            studentDetails.setDepartment(studentDetailsDTO.getDepartment());
            studentDetails.setCGPA(studentDetailsDTO.getCgpa());
            studentDetails.setYear(studentDetailsDTO.getYear());
            studentDetails.setGraduationYear(studentDetailsDTO.getGraduationYear());
            studentDetails.setSkills(studentDetailsDTO.getSkills());
            studentDetails.setEmail(studentDetailsDTO.getEmail());

            // ✅ FIX: UPDATE URLS ALSO
            studentDetails.setUrls(studentDetailsDTO.getUrls());

            if (img != null && !img.isEmpty()) {
                try {
                    String imageUrl = supabaseStorageService.uploadImage(img);

                    if (studentDetails.getImg_loc() != null) {
                        supabaseStorageService.deleteImage(studentDetails.getImg_loc());
                    }

                    studentDetails.setImg_loc(imageUrl);

                    logger.info("Updated profile image for student: {} in Supabase", userId);

                } catch (IOException e) {
                    logger.error("Failed to update profile image for student: {} in Supabase. Error: {}", userId, e.getMessage());
                    throw e;
                }
            }

        } else {

            // 🆕 CREATE NEW PROFILE
            String imageUrl = null;

            if (img != null && !img.isEmpty()) {
                try {
                    imageUrl = supabaseStorageService.uploadImage(img);
                    logger.info("Saved new profile image for student: {} in Supabase", userId);
                } catch (IOException e) {
                    logger.error("Failed to save new profile image for student: {} in Supabase. Error: {}", userId, e.getMessage());
                    throw e;
                }
            }

            studentDetails = new Student(
                    studentDetailsDTO.getSkills(),
                    studentDetailsDTO.getEmail(),
                    studentDetailsDTO.getFirstName(),
                    studentDetailsDTO.getLastName(),
                    studentDetailsDTO.getDepartment(),
                    studentDetailsDTO.getCgpa(),
                    studentDetailsDTO.getPhone(),
                    studentDetailsDTO.getYear(),
                    studentDetailsDTO.getGraduationYear(),
                    studentDetailsDTO.getUrls(),
                    user,
                    imageUrl
            );
        }

        return new StudentDetailsDTO(studentRepository.save(studentDetails));
    }

    @Transactional
    public StudentDetailsDTO getProfileData(String userId) throws Exception {
        Student studentDetails = studentRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.warn("Student profile not found for user ID: {}", userId);
                    return new Exception("Student Profile not found for User ID: " + userId);
                });

        return new StudentDetailsDTO(studentDetails);
    }

    @Transactional
    public Optional<Student> getProfileForStudent(String userId) {
        return studentRepository.findById(userId);
    }

    @Transactional
    public UserDTO getStudentProfileAsUserDTO(String userId) {
        Student studentDetails = studentRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found for User ID: " + userId));

        return new UserDTO(studentDetails);
    }
}