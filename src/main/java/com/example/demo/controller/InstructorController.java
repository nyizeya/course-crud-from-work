package com.example.demo.controller;

import com.example.demo.model.HttpResponse;
import com.example.demo.model.entity.Instructor;
import com.example.demo.model.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("instructors")
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorService instructorService;

    @GetMapping
    public String search(@RequestParam Optional<String> name, Principal principal, ModelMap model) {
        model.put("username", principal.getName());
        List<Instructor> instructors = instructorService.search(name);

        model.put("instructors", instructors);


        return "instructor/index";
    }

    @GetMapping("edit")
    public String edit(@RequestParam Optional<Long> id, ModelMap model) {
        Instructor instructor = id.map(instructorService::findInstructorById).orElse(new Instructor());
        model.put("instructor", instructor);

        boolean isEdit = id.isPresent();
        model.put("edit", isEdit);

        if (isEdit) {
            model.put("instructorId", instructor.getId());
        }

        return "instructor/edit";
    }

    @PostMapping
    public String save(
            @Valid @ModelAttribute Instructor instructor,
            BindingResult result) {
        if (result.hasErrors()) {
            return "instructor/edit";
        }

        instructorService.saveInstructor(instructor);

        return "redirect:/instructors";
    }

    @PostMapping("change-password")
    public String changePassword(
            @RequestParam Long id,
            @RequestParam String currentPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword
    ) {

        instructorService.changePassword(id, currentPassword, newPassword, confirmPassword);

        return "redirect:/instructors/edit?id=" + id;
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable Long id) {
        instructorService.removeInstructorById(id);
        return "redirect:/instructors";
    }

    @GetMapping("check-password")
    public ResponseEntity<Map<String, Boolean>> checkPassword(
            @RequestParam Long id,
            @RequestParam String password
    ) {

        boolean result = instructorService.checkPassword(id, password);

        Map<String, Boolean> response = new HashMap<>();
        response.put("theResult", result);

        return ResponseEntity.ok(response);
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
