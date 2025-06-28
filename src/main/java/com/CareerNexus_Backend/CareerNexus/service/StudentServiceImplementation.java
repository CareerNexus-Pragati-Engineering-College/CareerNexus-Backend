package com.CareerNexus_Backend.CareerNexus.service;

import com.CareerNexus_Backend.CareerNexus.model.Student;
import com.CareerNexus_Backend.CareerNexus.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImplementation implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public boolean hasProfile(String userId) {
        Student student = studentRepository.findById(userId).orElse(null);
        if (student == null) {
            return false;
        }
        // Check if required profile fields are filled
        return student.getFirstName() != null &&
                student.getLastName() != null &&
                student.getDepartment() != null &&
                student.getYear() != null &&
                student.getCGPA() != 0.0f;
    }

    @Override
    public Student getStudentProfile(String userId) {
        return studentRepository.findById(userId).orElse(null);
    }

    @Override
    public Student createOrUpdateProfile(Student student) {
        // Check if student exists
        Student existingStudent = studentRepository.findById(student.getUserId()).orElse(null);

        if (existingStudent != null) {
            // Update only the profile fields, don't overwrite password/email
            existingStudent.setFirstName(student.getFirstName());
            existingStudent.setLastName(student.getLastName());
            existingStudent.setDepartment(student.getDepartment());
            existingStudent.setYear(student.getYear());
            existingStudent.setCGPA(student.getCGPA());
            existingStudent.setSkills(student.getSkills());
            existingStudent.setPhone(student.getPhone());
            return studentRepository.save(existingStudent);
        } else {
            // Create new profile
            return studentRepository.save(student);
        }
    }
}