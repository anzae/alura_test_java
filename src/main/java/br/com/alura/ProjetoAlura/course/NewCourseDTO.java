package br.com.alura.ProjetoAlura.course;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
// add enumerator validator
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
// add date validator
import jakarta.validation.constraints.FutureOrPresent;
// add entity
import jakarta.persistence.Entity;

import org.hibernate.validator.constraints.Length;

@Entity
public class NewCourseDTO {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    @Length(min = 4, max = 10)
    // regex pattern
    @Pattern(regexp = "^[a-zA-Z]+(-[a-zA-Z]+)*$")
    private String code;

    private String description;

    @NotNull
    @NotBlank
    @Email
    private String instructorEmail;

    /* 
    Note: In a real application, this information should come from User database 
    at the moment of creation, to verify if user with given email is instructor.
    */
    private String userRole;

    // status
    @Enumerated(EnumType.STRING)
    private Status status;

    // date
    @FutureOrPresent
    private LocalDate inactivationDate;

    // constructor
    public NewCourseDTO(String name, String code, String description, String instructorEmail) {
        this.name = name;
        this.code = code;
        this.description = description;
        this.instructorEmail = instructorEmail;
        this.status = "ACTIVE";

        // user role validation
        if (!isInstructorEmailValid()) {
            throw new IllegalArgumentException("User email is not valid.");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    // getters and setters
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getInactivationDate() {
        return inactivationDate;
    }

    public void setInactivationDate(LocalDate inactivationDate) {
        this.inactivationDate = inactivationDate;
    }

    // instructor validation
    public boolean isInstructorEmailValid() {
        return "INSTRUCTOR".equals(userRole);
    }
}
