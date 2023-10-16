package sovcombank.jabka.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sovcombank.jabka.userservice.model.Request;

public interface RequestRepository extends JpaRepository<Request,Long> {
}
