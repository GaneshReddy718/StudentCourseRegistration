package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

	 private final StudentService service;

	    public StudentController(StudentService service) {
	        this.service = service;
	    }

	    // Register student
	    @PostMapping
	    public Student registerStudent(
	            @Valid @RequestBody Student student) {
	        return service.registerStudent(student);
	    }

	    // View all students
	    @GetMapping
	    public List<Student> getAllStudents() {
	        return service.getAllStudents();
	    }

	    // Find student by ID
	    @GetMapping("/{id}")
	    public Student getStudentById(@PathVariable Integer id) {
	        return service.getStudentById(id);
	    }

	    // Delete registration
	    @DeleteMapping("/{id}")
	    public String deleteStudent(@PathVariable Integer id) {
	        return service.deleteStudent(id);
	    }
	
}
