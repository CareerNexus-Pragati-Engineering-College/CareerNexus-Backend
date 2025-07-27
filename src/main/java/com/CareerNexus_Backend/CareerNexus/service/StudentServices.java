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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentServices {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserAuthRepository userAuthRepository;

    public boolean isStudentAvailable(UsersDTO user) {
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


        Optional<Student> studentDetail=this.getProfileForStudent(userId);

        Student studentDetails;
        if (studentDetail.isPresent()) {
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

        } else {
System.out.println("welcome");
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

    @Transactional()
    public Optional<Student> getProfileForStudent(String userId) {

        return studentRepository.findById(userId);

    }

    @Transactional()
    public UserDTO getStudentProfileAsUserDTO(String userId) {
        Student studentDetails = studentRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found for User ID: " + userId));
        return new UserDTO(studentDetails);
    }

}