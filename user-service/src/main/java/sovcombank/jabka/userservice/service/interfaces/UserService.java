package sovcombank.jabka.userservice.service.interfaces;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;
import ru.sovcombank.openapi.model.SignupRequestOpenApi;
import sovcombank.jabka.userservice.model.UserEntity;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserEntity> getAll();
    UserEntity loadUserByUsername(String login);

    @Transactional
    UserEntity saveOrUpdate(SignupRequestOpenApi signupRequestOpenApi);
}
