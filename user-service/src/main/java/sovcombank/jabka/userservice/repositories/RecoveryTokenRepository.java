package sovcombank.jabka.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sovcombank.jabka.userservice.model.RecoveryToken;

@Repository
public interface RecoveryTokenRepository extends JpaRepository<RecoveryToken, Long> {
    RecoveryToken findByRecoveryToken(String recoveryToken);
}
