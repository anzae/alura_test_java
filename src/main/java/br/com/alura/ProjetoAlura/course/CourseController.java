package br.com.alura.ProjetoAlura.course;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// date import
import java.time.LocalDateTime;

@RestController
public class CourseController {

    // db for course
    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @PostMapping("/course/new")
    public ResponseEntity createCourse(@Valid @RequestBody NewCourseDTO newCourse) {
        
        // question 1
        if (courseRepository.existsByCode(newCourse.getCode())) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorItemDTO("code", "Course code already exists."));
        }

        Course course = new NewCourseDTO(
            newCourse.getName(),
            newCourse.getCode(),
            newCourse.getDescription(),
            newCourse.getInstructorEmail(),
        );
        courseRepository.save(course);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/course/{code}/inactive")
    public ResponseEntity createCourse(@PathVariable("code") String courseCode) {

        // question 2
        Course course = courseRepository.findByCode(courseCode)
                .orElseThrow(() -> new IllegalArgumentException("Course not found."));

        course.setStatus(Status.INACTIVE);
        course.setInactivationDate(LocalDateTime.now());
        courseRepository.save(course);

        return ResponseEntity.ok().build();
    }

}
