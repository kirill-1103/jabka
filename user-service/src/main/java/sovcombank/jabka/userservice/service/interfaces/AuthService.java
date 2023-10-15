package sovcombank.jabka.userservice.service.interfaces;

import org.springframework.stereotype.Service;
import ru.sovcombank.openapi.model.JwtResponseOpenApi;
import ru.sovcombank.openapi.model.LoginRequestOpenApi;

@Service
public interface AuthService {
    JwtResponseOpenApi createTokenAndAuthorized(LoginRequestOpenApi loginRequestOpenApi);

    JwtResponseOpenApi createAuthToken(LoginRequestOpenApi loginRequestOpenApi);
}
