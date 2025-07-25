package com.CareerNexus_Backend.CareerNexus.service;

import com.CareerNexus_Backend.CareerNexus.dto.StudentDetailsDTO;
import com.CareerNexus_Backend.CareerNexus.exceptions.ResourceNotFoundException;
import com.CareerNexus_Backend.CareerNexus.model.Recruiter;
import com.CareerNexus_Backend.CareerNexus.model.Student;
import com.CareerNexus_Backend.CareerNexus.model.User;
import com.CareerNexus_Backend.CareerNexus.repository.StudentRepository;
import com.CareerNexus_Backend.CareerNexus.repository.UserAuthRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServices {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserAuthRepository userAuthRepository;

    public boolean isStudentAvailable(User user) {
        Optional<Student> isData = studentRepository.findByUserId(user.getUserId());
        if (isData.isEmpty()) {
            return true;
        }
        return false;
    }

    @Transactional
    public StudentDetailsDTO createOrUpdateProfile(StudentDetailsDTO studentDetailsDTO, String userId) throws Exception {

        User user = userAuthRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));


        Student studentDetails;
        if (user.getStudentDetails() != null) {
            studentDetails = user.getStudentDetails();

            studentDetails.setFirstName(studentDetailsDTO.getFirstName());
            studentDetails.setLastName(studentDetailsDTO.getLastName());
            studentDetails.setPhone(studentDetailsDTO.getPhone());
            studentDetails.setDepartment(studentDetailsDTO.getDepartment());
            studentDetails.setCGPA(studentDetailsDTO.getCgpa());
            studentDetails.setYear(studentDetailsDTO.getYear());
            studentDetails.setGraduationYear(studentDetailsDTO.getGraduationYear());
            studentDetails.setSkills(studentDetailsDTO.getSkills());
            studentDetails.setEmail(studentDetailsDTO.getEmail());
        } else {

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
                    user
            );


            user.setStudentDetails(studentDetails);
        }


        return new StudentDetailsDTO(studentRepository.save(studentDetails));
    }

    @Transactional
    public StudentDetailsDTO getProfileData(String userId) throws Exception {
        // Ensure studentDetails are eagerly loaded if UserDTO also needs names from here
        Student studentDetails = studentRepository.findById(userId)
                .orElseThrow(() -> new Exception("Student Profile not found for User ID: " + userId));
        return new StudentDetailsDTO(studentDetails);
    }


}