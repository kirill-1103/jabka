package sovcombank.jabka.userservice.service.interfaces;

import org.springframework.stereotype.Service;
import ru.sovcombank.openapi.model.JwtResponseOpenApi;
import ru.sovcombank.openapi.model.LoginRequestOpenApi;
import ru.sovcombank.openapi.model.UserOpenApi;

@Service
public interface AuthService {
    JwtResponseOpenApi createTokenAndAuthorized(LoginRequestOpenApi loginRequestOpenApi);

    JwtResponseOpenApi createAuthToken(LoginRequestOpenApi loginRequestOpenApi);

    JwtResponseOpenApi createAuthToken(UserOpenApi userOpenApi);
}
