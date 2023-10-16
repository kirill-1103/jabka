package sovcombank.jabka.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sovcombank.jabka.userservice.model.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findOneByLogin(String login);

    Optional<UserEntity> findOneById(Long id);

    Optional<UserEntity> findOneByEmail(String email);

}
