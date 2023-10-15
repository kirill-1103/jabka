package sovcombank.jabka.userservice.controller;


import org.springframework.http.ResponseEntity;
import ru.sovkombank.openapi.api.AuthorizationApiDelegate;
import ru.sovkombank.openapi.model.JwtResponseOpenApi;
import ru.sovkombank.openapi.model.LoginRequestOpenApi;
import ru.sovkombank.openapi.model.SignupRequestOpenApi;
import ru.sovkombank.openapi.model.UserOpenApi;

public class AuthController implements AuthorizationApiDelegate {
    @Override
    public ResponseEntity<JwtResponseOpenApi> authenticateUser(LoginRequestOpenApi loginRequestOpenApi) {
        return AuthorizationApiDelegate.super.authenticateUser(loginRequestOpenApi);
    }

    @Override
    public ResponseEntity<Void> registerUser(SignupRequestOpenApi signupRequestOpenApi) {
        return AuthorizationApiDelegate.super.registerUser(signupRequestOpenApi);
    }

    @Override
    public ResponseEntity<JwtResponseOpenApi> updateUserToken(UserOpenApi userOpenApi) {
        return AuthorizationApiDelegate.super.updateUserToken(userOpenApi);
    }

}
