package com.example.demo.model;

import com.example.demo.model.entity.dto.InstructorDto;
import com.example.demo.model.entity.dto.mapper.InstructorMapperImpl;
import org.springframework.stereotype.Component;

@Component
public class CustomInstructorMapperImpl extends InstructorMapperImpl {

    private final IdToInstructorDtoConverter converter = new IdToInstructorDtoConverter();

    public InstructorDto stringToDto(String id) {
        return converter.convert(id);
    }

}
