package com.example.demo.model.service;

import com.example.demo.model.entity.Instructor;
import com.example.demo.model.repo.InstructorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class InstructorService {

    private final InstructorRepo instructorRepo;
    private final PasswordEncoder passwordEncoder;

    public List<Instructor> getAllInstructors() {
        return instructorRepo.findAll();
    }

    public List<Instructor> search(Optional<String> name) {
        Specification<Instructor> whichName = name.isPresent() ?
                (root, query, cb) -> cb.like(cb.lower(root.get("name")), name.get().toLowerCase().concat("%"))
                : Specification.where(null);

        return instructorRepo.findAll(whichName);
    }

    public List<Instructor> findAllInstructors() {
        return instructorRepo.findAll();
    }

    public Instructor findInstructorById(Long id) {
        return instructorRepo.findById(id).orElseThrow(()
                -> new EntityNotFoundException(String.format("instructor with id [%d] not found", id)));
    }

    public void removeInstructorById(Long id) {
        instructorRepo.deleteById(id);
    }

    public Instructor saveInstructor(Instructor instructor) {

        if (null == instructor.getId()) {
            instructor.setPassword(passwordEncoder.encode(instructor.getPassword()));
        }

//        if (null != instructor.getId()) {
//            Instructor savedInstructor = findInstructorById(instructor.getId());
//
//            if (passwordEncoder.matches(instructor.getPassword(), savedInstructor.getPassword())) {
//                instructor.setPassword(savedInstructor.getPassword());
//            } else {
//                instructor.setPassword(passwordEncoder.encode(instructor.getPassword()));
//            }
//        }

        return instructorRepo.save(instructor);
    }

    public Instructor loadInstructorByUsername(String username) {
        return instructorRepo.findInstructorByEmail(username);
    }

    public void changePassword(Long id, String currentPassword, String newPassword, String confirmPassword) {

        Instructor instructor = findInstructorById(id);

        if (passwordEncoder.matches(currentPassword, instructor.getPassword())) {

            if (newPassword.equals(confirmPassword)) {
                if (currentPassword.equals(newPassword)) {
                    return;
                }

                instructor.setPassword(passwordEncoder.encode(newPassword));

                instructorRepo.save(instructor);
            }

        }

    }

    public boolean checkPassword(Long id, String password) {
        Instructor instructor = findInstructorById(id);

        if (passwordEncoder.matches(password, instructor.getPassword())) {
            return true;
        }

        return false;
    }

}
