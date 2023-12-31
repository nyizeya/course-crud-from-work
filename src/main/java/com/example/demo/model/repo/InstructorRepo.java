package com.example.demo.model.repo;

import com.example.demo.model.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InstructorRepo extends JpaRepository<Instructor, Long>, JpaSpecificationExecutor<Instructor> {

    Instructor findInstructorByEmail(String email);

}
