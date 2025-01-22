package br.com.alura.ProjetoAlura.user;


import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Course findByCode(String code);
}
