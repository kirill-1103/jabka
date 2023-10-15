package sovcombank.jabka.userservice.service.interfaces;

import org.springframework.security.core.userdetails.UserDetailsService;
import sovcombank.jabka.userservice.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAll();
    User loadUserByUsername(String login);
}
