package com.example.demo.controller;

import com.example.demo.model.CustomInstructorMapperImpl;
import com.example.demo.model.HttpResponse;
import com.example.demo.model.entity.Course;
import com.example.demo.model.entity.Instructor;
import com.example.demo.model.entity.dto.CourseDto;
import com.example.demo.model.entity.dto.InstructorDto;
import com.example.demo.model.entity.dto.mapper.CourseMapper;
import com.example.demo.model.entity.dto.mapper.InstructorMapperImpl;
import com.example.demo.model.service.CourseService;
import com.example.demo.model.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping({"/", "/courses"})
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final InstructorService instructorService;
    private final CourseMapper courseMapper;
    private final CustomInstructorMapperImpl instructorMapper;

    @GetMapping
    public String search(
                @RequestParam Optional<String> name,
                @RequestParam Optional<Course.Level> level,
                @RequestParam Optional<Integer> pageNumber,
                @RequestParam Optional<Integer> size,
                Principal principal,
                ModelMap model
            ) {

        int currentPage = pageNumber.orElse(1);
        int pageSize = size.orElse(5);

        Page<CourseDto> coursePage = courseService.search(name, level, PageRequest.of(currentPage - 1, pageSize));

        model.put("courses", coursePage);
        model.put("username", principal.getName());

        int totalPages = coursePage.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());

            model.put("pageNumbers", pageNumbers);
        }

        return "index";
    }

    @GetMapping("edit")
    public String addCourse(
            @RequestParam Optional<Long> id,
            ModelMap model
    ) {

        Course course = id.map(courseService::findCourseById).orElse(new Course());
        CourseDto courseDto = courseMapper.toDto(course);

        model.put("course", courseDto);
        return "edit";
    }

    @PostMapping("save")
    public String saveCourse(@Valid @ModelAttribute(name = "course") CourseDto courseDto, BindingResult result) {

        if (result.hasErrors()) {
            return "edit";
        }

        courseService.createCourse(courseMapper.toEntity(courseDto));

        return "redirect:/courses";
    }

    @GetMapping("/delete/{id}")
    public String deleteCourse(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam Optional<Course.Level> level,
            @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "5") Integer size
    ) {
        String theLevel = level.map(Enum::name).orElse("");
        courseService.deleteCourse(id);
        return String.format("redirect:/courses?name=%s&level=%s&pageNumber=%d&size=%d", name, theLevel, pageNumber, size);
    }

    @GetMapping("/error-page")
    public String error(String viewName, Model model) {
        HttpResponse response = (HttpResponse) model.asMap().get("errorResponse");
        model.addAttribute("errorResponse", response);
        return "errors/404";
    }

    @ModelAttribute(name = "levels")
    public Course.Level[] levels() {
        return Course.Level.values();
    }

    @ModelAttribute(name = "instructors")
    public List<InstructorDto> instructorList() {
        List<Instructor> instructors = instructorService.getAllInstructors();
        List<InstructorDto> instructorDtoList = instructors.stream().map(instructorMapper::toDto).collect(Collectors.toList());
        return instructorDtoList;
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public String entityNotFound(EntityNotFoundException exception, RedirectAttributes redirectAttributes) {
        HttpResponse response = new HttpResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );

        redirectAttributes.addFlashAttribute("errorResponse", response);

        return "redirect:/error-page";
    }

}
