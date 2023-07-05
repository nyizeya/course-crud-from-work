package com.example.demo.controller;

import com.example.demo.model.entity.Course;
import com.example.demo.model.entity.Instructor;
import com.example.demo.model.service.CourseService;
import com.example.demo.model.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

        Page<Course> coursePage = courseService.search(name, level, PageRequest.of(currentPage - 1, pageSize));

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

        model.put("course", id.map(courseService::findCourseById).orElse(new Course()));
        return "edit";
    }

    @PostMapping("save")
    public String saveCourse(@Valid @ModelAttribute Course course, BindingResult result) {
        if (result.hasErrors()) {
            return "edit";
        }

        courseService.createCourse(course);

        return "redirect:/courses";
    }

    @GetMapping("/delete/{id}")
    public String deleteCourse(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam Optional<Course.Level> level,
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "5") Integer size
    ) {
        String theLevel = level.map(Enum::name).orElse("");
        courseService.deleteCourse(id);
        return String.format("redirect:/courses?name=%s&level=%s&pageNumber=%d&size=%d", name, theLevel, pageNumber, size);
    }

    @ModelAttribute(name = "levels")
    public Course.Level[] levels() {
        return Course.Level.values();
    }

    @ModelAttribute(name = "instructors")
    public List<Instructor> instructorList() {
        return instructorService.getAllInstructors();
    }

}
