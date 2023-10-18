package sovcombank.jabka.studyservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sovcombank.jabka.studyservice.models.Homework;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, Long> {
}
