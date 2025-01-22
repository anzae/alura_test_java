package br.com.alura.ProjetoAlura.registration;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RegistrationController {

    @PostMapping("/registration/new")
    public ResponseEntity createCourse(@Valid @RequestBody NewRegistrationDTO newRegistration) {

        // question 3
        Course course = courseRepository.findByCode(newRegistration.getCourseCode())
            .orElseThrow(() -> new IllegalArgumentException("Course not found."));
    
        if (course.getStatus() != Status.ACTIVE) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorItemDTO("course", "Course not active."));
        }

        Registration registration = new Registration(newRegistration.getCourseCode(), newRegistration.getStudentEmail());
            registrationRepository.save(registration);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/registration/report")
    public ResponseEntity<List<RegistrationReportItem>> report() {
        List<RegistrationReportItem> items = new ArrayList<>();

        // question 4
        List<Object[]> result = registrationRepository.countRegistrationsByCourse();
        for (Object[] obj : result) {
            String courseName = (String) obj[0];
            String courseCode = (String) obj[1];
            String instructorName = (String) obj[2];
            String instructorEmail = (String) obj[3];
            Long totalRegistrations = (Long) obj[4];

            RegistrationReportItem reportItem = new RegistrationReportItem(
                courseName,
                courseCode,
                instructorName,
                instructorEmail,
                totalRegistrations
            );

            items.add(reportItem);
        }

        return ResponseEntity.ok(items);
    }

}
