package br.com.alura.ProjetoAlura.course;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
// add enumerator validator
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
// add date validator
import java.time.LocalDateTime;
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
    private String code;

    private String description;

    @NotNull
    @NotBlank
    @Email
    private String instructorEmail;

    // status
    @Enumerated(EnumType.STRING)
    private Status status;

    // date
    @FutureOrPresent
    private LocalDateTime inactivationDate;

    // constructor
    public NewCourseDTO(String name, String code, String description, String instructorEmail) {
        this.name = name;
        this.code = code;
        this.description = description;
        this.instructorEmail = instructorEmail;
        this.status = "ACTIVE";
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

    public LocalDateTime getInactivationDate() {
        return inactivationDate;
    }

    public void setInactivationDate(LocalDateTime inactivationDate) {
        this.inactivationDate = inactivationDate;
    }
}
