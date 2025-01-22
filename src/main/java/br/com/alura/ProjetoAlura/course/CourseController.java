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

// regex import
import java.util.regex.Pattern;

@RestController
public class CourseController {

    // db for course
    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // db for user
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/course/new")
    public ResponseEntity createCourse(@Valid @RequestBody Course newCourse) {
        
        // question 1

        // code validator
        String regex = "^[a-zA-Z]+(-[a-zA-Z]+)*$";

        if (!Pattern.matches(regex, newCourse.getCode())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorItemDTO("code", "Invalid course code format."));
        }

        // instructor email validation
        User user = userRepository.findByEmail(newCourse.getInstructorEmail())
                .orElseThrow(() -> new IllegalArgumentException("Instructor email not found."));

        if (!user.getRole().equals("INSTRUCTOR")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorItemDTO("instructorEmail", "Email not valid."));
        }

        Course course = new Course(
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
