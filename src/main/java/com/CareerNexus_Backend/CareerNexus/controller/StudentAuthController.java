package com.CareerNexus_Backend.CareerNexus.controller;


import com.CareerNexus_Backend.CareerNexus.model.Student;
import com.CareerNexus_Backend.CareerNexus.service.StudentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/student")
public class StudentAuthController {

    private final StudentService studentService;

    public StudentAuthController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/register")
    public Student insertStudent(@RequestBody Student student) {
        return studentService.insertStudent(student);
    }

//    @GetMapping
//    public List<Student> getAllStudents() {
//        return studentService.getAllStudents();
//    }

    //   @GetMapping("/{rollNo}")
//    public Student getStudent(@PathVariable String rollNo) {
//        return studentService.getStudentById(rollNo);
//    }



//    @PutMapping("/update")
//    public Student updateStudent(@RequestBody Student student) {
//        return studentService.updateStudent(student);
//    }
}
