package com.example.demo;

import com.example.demo.model.entity.Course;
import com.example.demo.model.entity.Instructor;
import com.example.demo.model.service.CourseService;
import com.example.demo.model.service.InstructorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Bean
    CommandLineRunner runner(CourseService courseRepo, InstructorService instructorRepo) {
        return args -> {

            Instructor i1 = new Instructor(null, "Zaw Min Lwin",
                    "minlwin@gmail.com", "1234", "09457333234", null);

            Instructor i2 = new Instructor(null, "Kyaw Kyaw",
                    "kyaw@gmail.com", "1234", "09738457239", null);


            Instructor i3 = new Instructor(null, "Waifer Kolar",
                    "waifer@kolar.com", "1234", "0987463211", null);

            Course c1 = new Course(null, "Java SE", "Java For Beginners",
                    LocalDate.now(), Course.Level.Basic, i1);

            Course c2 = new Course(null, "Spring Context", "Spring For Beginners",
                    LocalDate.now(), Course.Level.Intermediate, i1);

            Course c3 = new Course(null, "Spring Data JPA", "Hibernate For Persisting Objects",
                    LocalDate.now(), Course.Level.Advanced, i2);

            i1.setCourses(Arrays.asList(c1, c2));
            i2.setCourses(Arrays.asList(c3));

//            Arrays.asList(i1, i2, i3).forEach(instructorRepo::saveInstructor);
//            Arrays.asList(c1, c2 ,c3).forEach(courseRepo::createCourse);

//            instructorRepo.saveAll(Arrays.asList(i1, i2, i3));
//            courseRepo.saveAll(Arrays.asList(c1, c2, c3));

        };
    }

}
