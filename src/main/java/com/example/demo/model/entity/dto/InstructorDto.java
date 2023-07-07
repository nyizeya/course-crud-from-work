package com.example.demo.model.entity.dto;

import com.example.demo.model.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstructorDto {
    private Long id;

    @NotEmpty(message = "Please enter instructor name")
    private String name;

    @NotEmpty(message = "Please enter instructor email")
    @Email(message = "Please enter a valid email")
    private String email;

    @NotEmpty(message = "Please enter password")
    @Size(min = 8, message = "At least 8 characters required")
    private String password;

    @NotEmpty(message = "Please enter instructor phone")
    private String phone;

    private List<Course> courses = new ArrayList<>();
}
