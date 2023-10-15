package sovcombank.jabka.userservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sovcombank.openapi.api.AuthorizationApiDelegate;
import ru.sovcombank.openapi.model.JwtResponseOpenApi;
import ru.sovcombank.openapi.model.LoginRequestOpenApi;
import ru.sovcombank.openapi.model.SignupRequestOpenApi;
import ru.sovcombank.openapi.model.UserOpenApi;
import sovcombank.jabka.userservice.service.interfaces.AuthService;

@RestController
@RequestMapping("/api/user/auth")
@RequiredArgsConstructor
public class AuthController implements AuthorizationApiDelegate {
    private final AuthService authService;
    private static final String MAPPING_AUTH = "/signin";
    private static final String MAPPING_UPDATE_TOKEN = "/update-token";
    private static final String MAPPING_REGISTRATION = "/signup";

    @Override
    @PostMapping(MAPPING_AUTH)
    public ResponseEntity<JwtResponseOpenApi> authenticateUser(LoginRequestOpenApi loginRequestOpenApi) {
        JwtResponseOpenApi jwtResponseOpenApi = authService.createTokenAndAuthorized(loginRequestOpenApi);
        return ResponseEntity.ok(jwtResponseOpenApi);
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
