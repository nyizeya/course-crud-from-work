package com.example.demo.model.entity.dto.mapper;

import com.example.demo.model.entity.Course;
import com.example.demo.model.entity.dto.CourseDto;

public interface InstructorMapper {

    Course toEntity(CourseDto courseDto);
    CourseDto toDto(Course course);

}
