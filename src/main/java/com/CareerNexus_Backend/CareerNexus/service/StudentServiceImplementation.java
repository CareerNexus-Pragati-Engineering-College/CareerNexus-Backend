package com.CareerNexus_Backend.CareerNexus.service;

import com.CareerNexus_Backend.CareerNexus.model.Student;
import com.CareerNexus_Backend.CareerNexus.repository.StudentRepository;
import com.CareerNexus_Backend.CareerNexus.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class StudentServiceImplementation implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImplementation(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(String s_rollNo) {
        return studentRepository.findById(s_rollNo).orElse(null);
    }

    @Override
    public Student insertStudent(Student student) {
        return studentRepository.save(student); // signup
    }

    @Override
    @Transactional
    public Student updateStudent(Student updatedStudent) {
        Optional<Student> existingStudent = studentRepository.findById(updatedStudent.getS_rollNo());
        if (existingStudent.isPresent()) {
            Student student = existingStudent.get();
            student.setS_name(updatedStudent.getS_name());
            student.setS_email(updatedStudent.getS_email());
            student.setS_password(updatedStudent.getS_password());
            return studentRepository.save(student);
        } else {
            return null; // or throw an exception
        }
    }
}
