package com.CareerNexus_Backend.CareerNexus.service;

import com.CareerNexus_Backend.CareerNexus.model.Recruiter;
import com.CareerNexus_Backend.CareerNexus.model.Student;
import com.CareerNexus_Backend.CareerNexus.model.User;
import com.CareerNexus_Backend.CareerNexus.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentServices {

    @Autowired
    private StudentRepository studentRepository;

    public boolean isStudentAvailable(User user){
        Optional<Student> isData=studentRepository.findByUserId(user.getUserId());
        if(isData.isEmpty()){
            return true;
        }
        return false;
    }

    public Student createOrUpdateProfile(Student student , String userId) {
        student.setUserId(userId);
        return studentRepository.findByUserId(student.getUserId())
                .map(existing -> {
                    // Update only allowed fields
                    existing.setFirstName(student.getFirstName());
                    existing.setLastName(student.getLastName());
                    existing.setDepartment(student.getDepartment());
                    existing.setYear(student.getYear());
                    existing.setCGPA(student.getCGPA());
                    existing.setSkills(student.getSkills());
                    existing.setUrls(student.getUrls());
                    existing.setGraduationYear(student.getGraduationYear());
                    // Phone/email remain unchanged from registration
                    return studentRepository.save(existing);
                })
                .orElseGet(() -> {
                    // New profile (shouldn't happen for registered users)
                    return studentRepository.save(student);
                });
    }

    public Optional<Student> getProfileData(String userId) {

        return studentRepository.findByUserId(userId);
    }
}
