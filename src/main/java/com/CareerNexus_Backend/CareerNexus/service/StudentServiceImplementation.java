package com.CareerNexus_Backend.CareerNexus.service;

import com.CareerNexus_Backend.CareerNexus.exceptions.DuplicateUserException;
import com.CareerNexus_Backend.CareerNexus.model.Student;
import com.CareerNexus_Backend.CareerNexus.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder; // Make sure this import exists
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class StudentServiceImplementation implements StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    //  Inject both StudentRepository and PasswordEncoder
    public StudentServiceImplementation(StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // You can remove this line: private PasswordEncoder passwordEncoder; if it was duplicated

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

        //checks whether the student already or not if yes it send a exception to the user signup page
        Optional<Student> existingStudent = studentRepository.findById(student.getS_rollNo());
        if (existingStudent.isPresent()) {
           // Throw the custom exception to signal the conflict
            throw new DuplicateUserException(HttpStatus.CONFLICT.value(), "User with rollno '" + student.getS_rollNo() + "' already exists.");
        }
        try {
            // This line the raw password is encrypted using cryptographic techniques
            student.setS_password(passwordEncoder.encode(student.getS_password()));

            return studentRepository.save(student);

            // signup
        } catch (Exception e) {
            // Log the exception for debugging
            System.err.println("Error inserting student: " + e.getMessage());
            // You might want to throw a more specific custom exception here,
            // or return null/Optional.empty() depending on your error handling strategy.
            throw new RuntimeException("Failed to register student: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public Student updateStudent(Student updatedStudent) {
        try {
            Optional<Student> existingStudent = studentRepository.findById(updatedStudent.getS_rollNo());
            if (existingStudent.isPresent()) {
                Student student = existingStudent.get();
                student.setS_name(updatedStudent.getS_name());
                student.setS_email(updatedStudent.getS_email());

                // Only re-encode password if it has changed and is not null/empty
                if (updatedStudent.getS_password() != null && !updatedStudent.getS_password().isEmpty()) {
                    student.setS_password(passwordEncoder.encode(updatedStudent.getS_password()));
                }

                return studentRepository.save(student);
            } else {
                return null; // or throw an exception indicating student not found
            }
        } catch (Exception e) {
            System.err.println("Error updating student: " + e.getMessage());
            throw new RuntimeException("Failed to update student: " + e.getMessage(), e);
        }
    }
}