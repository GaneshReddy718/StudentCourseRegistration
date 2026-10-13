package com.example.demo.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Student {

	 private Integer studentId;

	    @NotBlank(message = "Student name cannot be empty")
	    @Size(min = 3, max = 50, message = "Student name must contain 3 to 50 characters")
	    private String studentName;

	    @NotBlank(message = "Course name cannot be empty")
	    private String courseName;

	    @NotBlank(message = "Email cannot be empty")
	    @Email(message = "Enter valid email")
	    private String email;

	    @NotNull(message = "Age cannot be empty")
	    @Min(value = 18, message = "Age must be at least 18")
	    private Integer age;
	
}
