package com.example.demo.model.dto.mapper;

import com.example.demo.model.dto.CourseDto;
import com.example.demo.model.entity.Course;
import org.mapstruct.Mapper;

@Mapper
public interface CourseMapper {

    CourseDto courseToCourseDto(Course course);
    Course courseDtoToCourse(CourseDto courseDto);

}
