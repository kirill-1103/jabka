package sovcombank.jabka.userservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.sovcombank.openapi.api.AuthorizationApiDelegate;
import ru.sovcombank.openapi.model.JwtResponseOpenApi;
import ru.sovcombank.openapi.model.LoginRequestOpenApi;
import ru.sovcombank.openapi.model.SignupRequestOpenApi;
import ru.sovcombank.openapi.model.UserOpenApi;
import sovcombank.jabka.userservice.exception.ForbiddenException;
import sovcombank.jabka.userservice.model.UserEntity;
import sovcombank.jabka.userservice.service.interfaces.AuthService;
import sovcombank.jabka.userservice.service.interfaces.UserService;

@RestController
@RequestMapping("/api/user/auth")
@RequiredArgsConstructor
public class AuthController implements AuthorizationApiDelegate {
    private final AuthService authService;

    private final UserService userService;
    private static final String MAPPING_AUTH = "/signin";
    private static final String MAPPING_UPDATE_TOKEN = "/update-token";
    private static final String MAPPING_REGISTRATION = "/signup";
    private static final String MAPPING_ACTIVATION = "/activation";
    private static final String MAPPING_FORGET = "/forget";
    private static final String MAPPING_RECOVERY = "/recovery";

    @Override
    @PostMapping(MAPPING_AUTH)
    public ResponseEntity<JwtResponseOpenApi> authenticateUser(LoginRequestOpenApi loginRequestOpenApi) {
        JwtResponseOpenApi jwtResponseOpenApi = authService.createTokenAndAuthorized(loginRequestOpenApi);
        return ResponseEntity.ok(jwtResponseOpenApi);
    }

    @Override
    @PostMapping(MAPPING_REGISTRATION)
    public ResponseEntity<Void> registerUser(SignupRequestOpenApi signupRequestOpenApi) {
        UserEntity user = userService.saveOrUpdate(signupRequestOpenApi);
        userService.sendVerificationEmail(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @GetMapping(MAPPING_UPDATE_TOKEN)
    public ResponseEntity<JwtResponseOpenApi> updateUserToken(UserOpenApi userOpenApi) {
        String authenticatedName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!authenticatedName.equals(userOpenApi.getLogin())) {
            throw new ForbiddenException("User can't update token for other user!");
        }
        return ResponseEntity.ok(authService.createAuthToken(userOpenApi));
    }

    @Override
    @GetMapping(MAPPING_ACTIVATION)
    public ResponseEntity<Void> activateUser(@RequestParam(name = "token") String token) {
        return userService.activateUser(token);
    }

    @Override
    @Transactional
    @PutMapping(MAPPING_RECOVERY)
    public ResponseEntity<Void> recoveryPassword(@RequestBody String body,
                                                 @RequestParam(name = "token") String token) {
        return userService.recoveryPassword(body, token);
    }

    @Override
    @Transactional
    @PutMapping(MAPPING_FORGET)
    public ResponseEntity<Void> sendRecoveryPasswordMail(String body) {
        return userService.sendRecoveryPasswordMail(body);
    }
}
