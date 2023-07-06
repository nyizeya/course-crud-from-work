package com.example.demo.model.dto.mapper;

import com.example.demo.model.dto.InstructorDto;
import com.example.demo.model.dto.PasswordDto;
import com.example.demo.model.entity.Instructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface InstructorMapper {

    @Mapping(target = "passwordDto", source = "instructor.password", qualifiedByName = "passwordToPasswordDto")
    InstructorDto instrcutroToInstructorDto(Instructor instructor);

    @Mapping(target = "password", source = "passwordDto.password")
    Instructor instructorDtoToInstructor(InstructorDto instructorDto);


    @Named("passwordToPasswordDto")
    default PasswordDto passwordToPasswordDto(String password) {
        return new PasswordDto(password);
    }
}
