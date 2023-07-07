package com.example.demo.model.entity.dto.mapper;

import com.example.demo.model.entity.Instructor;
import com.example.demo.model.entity.dto.InstructorDto;
import com.example.demo.model.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public interface InstructorMapper {

    InstructorMapper INSTANCE = Mappers.getMapper(InstructorMapper.class);

    Instructor toEntity(InstructorDto instructorDto);

    InstructorDto toDto(Instructor instructor);

}
