package sovcombank.jabka.studyservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sovcombank.jabka.studyservice.models.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
}