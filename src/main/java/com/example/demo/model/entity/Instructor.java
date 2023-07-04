package com.example.demo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Please enter instructor name")
    @Column(nullable = false)
    private String name;

    @NotEmpty(message = "Please enter instructor email")
    @Email(message = "Please enter a valid email")
    @Column(nullable = false, unique = true)
    private String email;

    @NotEmpty(message = "Please enter password")
    @Size(min = 8, message = "At least 8 characters required")
    @Column(nullable = false)
    private String password;

    @NotEmpty(message = "Please enter instructor phone")
    @Column(nullable = false, unique = true)
    private String phone;

    @OneToMany(orphanRemoval = true, cascade = {CascadeType.MERGE}, mappedBy = "instructor")
    private List<Course> courses = new ArrayList<>();

    @Override
    public String toString() {
        return "Instructor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
