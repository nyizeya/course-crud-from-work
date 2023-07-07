package com.example.demo.model;

import com.example.demo.model.entity.Instructor;
import com.example.demo.model.entity.dto.InstructorDto;
import com.example.demo.model.entity.dto.mapper.InstructorMapperImpl;
import com.example.demo.model.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IdToInstructorDtoConverter implements Converter<String, InstructorDto> {

    @Autowired
    private CustomInstructorMapperImpl instructorMapper;

    @Autowired
    private InstructorService service;


    @Override
    public InstructorDto convert(String source) {
        if (!source.equals("")) {
            Instructor instructor = service.findInstructorById(Long.valueOf(source));
            return instructorMapper.toDto(instructor);
        }
        return null;
    }


}
