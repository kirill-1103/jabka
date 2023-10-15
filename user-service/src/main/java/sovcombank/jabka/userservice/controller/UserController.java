package sovcombank.jabka.userservice.controller;

import org.springframework.http.ResponseEntity;
import ru.sovkombank.openapi.api.UserApiDelegate;
import ru.sovkombank.openapi.model.JwtResponseOpenApi;
import ru.sovkombank.openapi.model.UserOpenApi;

import java.util.List;

public class UserController implements UserApiDelegate {
    @Override
    public ResponseEntity<Void> deleteUser(Long id) {
        return UserApiDelegate.super.deleteUser(id);
    }

    @Override
    public ResponseEntity<List<UserOpenApi>> getAllUsers() {
        return UserApiDelegate.super.getAllUsers();
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
