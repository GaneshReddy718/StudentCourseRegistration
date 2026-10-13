package com.example.demo.service;

import com.example.demo.model.Student;
import com.example.demo.exception.StudentNotFoundException;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StudentService {

	private final List<Student> students = new ArrayList<>();

    private final AtomicInteger idGenerator = new AtomicInteger(1000);

    private final Set<String> availableCourses = new HashSet<>(
        Arrays.asList(
            "Java",
            "Spring Boot",
            "Python",
            "SQL",
            "React"
        )
    );

    private static final int MAX_STUDENTS_PER_COURSE = 30;

    // 1. Register student
    public synchronized Student registerStudent(Student student) {

        String name = student.getStudentName().trim();
        String email = student.getEmail().trim().toLowerCase();
        String course = student.getCourseName().trim();

        if (student.getAge() < 18) {
            throw new IllegalArgumentException("Student must be at least 18 years old");
        }

        String selectedCourse = findAvailableCourse(course);

        if (selectedCourse == null) {
            throw new IllegalArgumentException("Course unavailable. Available courses: " + availableCourses);
        }

        boolean emailExists = students.stream()
            .anyMatch(s -> s.getEmail().equalsIgnoreCase(email));

        if (emailExists) {
            throw new IllegalArgumentException("A student with this email is already registered");
        }

        long enrolledCount = students.stream()
            .filter(s -> s.getCourseName()
                          .equalsIgnoreCase(selectedCourse))
            .count();

        if (enrolledCount >= MAX_STUDENTS_PER_COURSE) {
            throw new IllegalArgumentException("Course is full: " + selectedCourse);
        }

        student.setStudentId(idGenerator.incrementAndGet());
        student.setStudentName(name);
        student.setEmail(email);
        student.setCourseName(selectedCourse);

        students.add(student);

        return student;
    }

    private String findAvailableCourse(String course) {
        return availableCourses.stream()
            .filter(c -> c.equalsIgnoreCase(course))
            .findFirst()
            .orElse(null);
    }

    // 2. View all students
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    // 3. Find student by ID
    public Student getStudentById(Integer studentId) {

        return students.stream()
            .filter(s -> s.getStudentId().equals(studentId))
            .findFirst()
            .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + studentId));
    }

    // 4. Delete student registration
    public synchronized String deleteStudent(Integer studentId) {

        Student student = getStudentById(studentId);

        students.remove(student);

        return "Registration deleted successfully for student ID: " + studentId;
    }
	
}
