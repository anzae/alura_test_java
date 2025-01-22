package br.com.alura.ProjetoAlura.user;


import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<NewCourseDTO, Long> {

    NewCourseDTO findByCode(String code);
}
