package com.example.demo.model.entity.dto.mapper;

import com.example.demo.model.entity.Course;
import com.example.demo.model.entity.dto.CourseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    @Mapping(source = "instructor", target = "instructor")
    Course toEntity(CourseDto courseDto);

    @Mapping(source = "instructor", target = "instructor")
    CourseDto toDto(Course course);

}
