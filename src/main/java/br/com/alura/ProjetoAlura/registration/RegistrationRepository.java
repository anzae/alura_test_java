package br.com.alura.ProjetoAlura.repository;

import br.com.alura.ProjetoAlura.registration.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface RegistrationRepository extends JpaRepository<User, Long> {

}

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    @Query("SELECT c.courseName, c.courseCode, u.userName, u.userEmail, COUNT(r) " +
           "FROM Registration r " +
           "LEFT JOIN Course c ON c.courseCode = r.courseCode " + 
           "LEFT JOIN User u ON u.userEmail = c.instructorEmail " +
           "GROUP BY c.courseName, c.courseCode, u.userName, u.userEmail " +
           "ORDER BY COUNT(r) DESC")
    List<Object[]> countRegistrationsByCourse();
}