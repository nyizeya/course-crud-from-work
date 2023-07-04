package com.example.demo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotEmpty(message = "Name must not be empty")
    private String name;

    @Column(nullable = false)
    @NotEmpty(message = "Description must not be empty")
    private String description;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(nullable = false)
    @NotNull(message = "Please select start date")
    private LocalDate startDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Please select a level")
    private Level level;

    @NotNull(message = "Please select a instructor")
    @ManyToOne
    private Instructor instructor;


    public enum Level {
        Basic, Intermediate, Advanced
    }

}
