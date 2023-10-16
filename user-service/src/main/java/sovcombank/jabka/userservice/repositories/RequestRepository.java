package sovcombank.jabka.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sovcombank.jabka.userservice.model.Request;

import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request,Long> {
    Optional<Request> findByUserId(Long id);
}
