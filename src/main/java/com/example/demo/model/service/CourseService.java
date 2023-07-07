package com.example.demo.model.service;

import com.example.demo.model.entity.Course;
import com.example.demo.model.entity.Instructor;
import com.example.demo.model.entity.dto.CourseDto;
import com.example.demo.model.entity.dto.mapper.CourseMapper;
import com.example.demo.model.repo.CourseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepo courseRepo;
    private final InstructorService instructorService;
    private final CourseMapper courseMapper;

    public Page<CourseDto> search(
            Optional<String> name,
            Optional<Course.Level> level,
            Pageable pageable
    ) {

        int currentPage = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int startItem = currentPage * pageSize;

        Specification<Course> whichLevel = level.isPresent() ? ((root, query, cb)
                -> cb.equal(root.get("level"), level.get())) : Specification.where(null);

        Specification<Course> whichName = name.isPresent() ? (root, query, cb)
                -> cb.like(cb.lower(root.get("name")), name.get().toLowerCase().concat("%")) : Specification.where(null);

        List<Course> courses = courseRepo.findAll(whichName.and(whichLevel));
        List<CourseDto> courseDtoList = courses.stream().map(courseMapper::toDto).collect(Collectors.toList());
        List<CourseDto> list;

        if (courseDtoList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, courses.size());
            list = courseDtoList.subList(startItem, toIndex);
        }


        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), courseDtoList.size());
    }

    public Course createCourse(Course course) {
        return courseRepo.save(course);
    }

    public Course findCourseById(Long id) {
        return courseRepo.findCourseById(id).orElseThrow(()
                -> new EntityNotFoundException(String.format("course with id [%d] not found", id)));
    }

    public Course updateCourse(Course course) {
        return courseRepo.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepo.deleteById(id);
    }

}
