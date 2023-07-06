package com.example.demo.model.dto;

import com.example.demo.model.entity.Course;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CourseDto {

    private Long id;

    @NotEmpty(message = "Name must not be empty")
    private String name;

    @NotEmpty(message = "Description must not be empty")
    private String description;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "Please select start date")
    private LocalDate startDate;

    @NotNull(message = "Please select a level")
    private Course.Level level;

    private InstructorDto instructor;

}
