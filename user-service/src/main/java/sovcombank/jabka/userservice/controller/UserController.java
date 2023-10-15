package sovcombank.jabka.userservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sovcombank.openapi.api.UserApiDelegate;
import ru.sovcombank.openapi.model.JwtResponseOpenApi;
import ru.sovcombank.openapi.model.UserOpenApi;
import sovcombank.jabka.userservice.mapper.UserMapper;
import sovcombank.jabka.userservice.service.interfaces.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController implements UserApiDelegate {
    private final UserService userService;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<Void> deleteUser(Long id) {
        return UserApiDelegate.super.deleteUser(id);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<UserOpenApi>> getAllUsers() {
        log.error(passwordEncoder.encode("123"));
        return ResponseEntity.ok(userService.getAll()
                .stream()
                .map(userMapper::toUserOpenApi)
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<UserOpenApi> showUserInfo(Long id) {
        return UserApiDelegate.super.showUserInfo(id);
    }

    @Override
    public ResponseEntity<JwtResponseOpenApi> updateUser(UserOpenApi userOpenApi) {
        return UserApiDelegate.super.updateUser(userOpenApi);
    }
}
