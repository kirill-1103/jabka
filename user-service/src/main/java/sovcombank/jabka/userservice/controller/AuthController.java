package sovcombank.jabka.userservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sovcombank.openapi.api.AuthorizationApiDelegate;
import ru.sovcombank.openapi.model.JwtResponseOpenApi;
import ru.sovcombank.openapi.model.LoginRequestOpenApi;
import ru.sovcombank.openapi.model.SignupRequestOpenApi;
import ru.sovcombank.openapi.model.UserOpenApi;
import sovcombank.jabka.userservice.exception.ForbiddenException;
import sovcombank.jabka.userservice.service.interfaces.AuthService;
import sovcombank.jabka.userservice.service.interfaces.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/api/user/auth")
@RequiredArgsConstructor
public class AuthController implements AuthorizationApiDelegate {
    private final AuthService authService;

    private final UserService userService;
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
    @PostMapping(MAPPING_REGISTRATION)
    public ResponseEntity<Void> registerUser(SignupRequestOpenApi signupRequestOpenApi) {
        userService.saveOrUpdate(signupRequestOpenApi);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @GetMapping(MAPPING_UPDATE_TOKEN)
    public ResponseEntity<JwtResponseOpenApi> updateUserToken(UserOpenApi userOpenApi) {
        String authenticatedName = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!authenticatedName.equals(userOpenApi.getLogin())){
            throw new ForbiddenException("User can't update token for other user!");
        }
        return ResponseEntity.ok(authService.createAuthToken(userOpenApi));
    }

}
