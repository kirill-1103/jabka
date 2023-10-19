package sovcombank.jabka.userservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.sovcombank.openapi.api.UserApiDelegate;
import ru.sovcombank.openapi.model.JwtResponseOpenApi;
import ru.sovcombank.openapi.model.UpdateUserOpenApi;
import ru.sovcombank.openapi.model.UserOpenApi;
import sovcombank.jabka.userservice.mapper.UserMapper;
import sovcombank.jabka.userservice.service.interfaces.AuthService;
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

    private final static String MAPPING_GET_ONE = "/{id}";

    private final AuthService authService;

    @Override
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping
    public ResponseEntity<List<UserOpenApi>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll()
                .stream()
                .map(userMapper::toUserOpenApi)
                .peek((userOpenApi -> userOpenApi.setPassword(null)))
                .collect(Collectors.toList()));
    }

    @Override
    @GetMapping(MAPPING_GET_ONE)
    public ResponseEntity<UserOpenApi> showUserInfo(@PathVariable  Long id) {
        UserOpenApi userOpenApi = userMapper.toUserOpenApi(userService.getUserById(id));
        userOpenApi.setPassword(null);
        return ResponseEntity.ok(userOpenApi);
    }

    @Override
    @PutMapping
    public ResponseEntity<JwtResponseOpenApi> updateUser(@RequestBody  UpdateUserOpenApi updateUserOpenApi) {
        userService.update(updateUserOpenApi);
        JwtResponseOpenApi token = authService.createAuthToken(updateUserOpenApi.getNewUser());
        updateUserOpenApi.getNewUser().setPassword(null);
        return ResponseEntity.ok(new JwtResponseOpenApi(token.getAccessToken(),updateUserOpenApi.getNewUser()));
    }
}
