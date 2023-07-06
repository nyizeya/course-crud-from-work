package com.example.demo.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
public class InstructorDto {

    private Long id;

    @NotEmpty(message = "Please enter instructor name")
    private String name;

    @NotEmpty(message = "Please enter instructor email")
    @Email(message = "Please enter a valid email")
    private String email;

    private PasswordDto passwordDto;

    @NotEmpty(message = "Please enter instructor phone")
    private String phone;

    private List<CourseDto> courses;

}
