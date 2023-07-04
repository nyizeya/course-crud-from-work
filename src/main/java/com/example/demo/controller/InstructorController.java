package com.example.demo.controller;

import com.example.demo.model.entity.Instructor;
import com.example.demo.model.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("instructors")
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorService instructorService;

    @GetMapping
    public String search(@RequestParam Optional<String> name, Principal principal, ModelMap model) {
        model.put("username", principal.getName());
        model.put("instructors", instructorService.search(name));
        return "instructor/index";
    }

    @GetMapping("edit")
    public String edit(@RequestParam Optional<Long> id, ModelMap model) {
        model.put("instructor", id.map(instructorService::findInstructorById).orElse(new Instructor()));
        return "instructor/edit";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute Instructor instructor, BindingResult result) {
        if (result.hasErrors()) {
            return "instructor/edit";
        }

        instructorService.saveInstructor(instructor);

        return "redirect:/instructors";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable Long id) {
        instructorService.removeInstructorById(id);
        return "redirect:/instructors";
    }

}
