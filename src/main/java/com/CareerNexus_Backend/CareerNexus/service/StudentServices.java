package com.CareerNexus_Backend.CareerNexus.service;

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

    public Student createOrUpdate(Student StudentDetails, String userId) {

        Optional<Student> existingStudentOpt = studentRepository.findByUserId(StudentDetails.getUserId());
        if (existingStudentOpt.isPresent()) {
            // Update profile fields
            Student existingStudent = existingStudentOpt.get();
            return studentRepository.save(existingStudent);
        } else {
            // New student profile creation
            return studentRepository.save(StudentDetails);
        }
    }
}
