package sovcombank.jabka.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sovcombank.jabka.userservice.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
}
