package sovcombank.jabka.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import ru.sovcombank.openapi.model.JwtResponseOpenApi;
import ru.sovcombank.openapi.model.LoginRequestOpenApi;
import ru.sovcombank.openapi.model.UserOpenApi;
import sovcombank.jabka.libs.security.utils.JwtUtils;
import sovcombank.jabka.userservice.exception.BadRequestException;
import sovcombank.jabka.userservice.mapper.UserMapper;
import sovcombank.jabka.userservice.model.UserEntity;
import sovcombank.jabka.userservice.service.interfaces.AuthService;
import sovcombank.jabka.userservice.service.interfaces.UserService;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    private final UserMapper userMapper;

    @Override
    public JwtResponseOpenApi createTokenAndAuthorized(LoginRequestOpenApi loginRequestOpenApi) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestOpenApi.getLogin(),
                    loginRequestOpenApi.getPassword()));
            log.debug(String.format("USER %s has logged in", loginRequestOpenApi.getLogin()));
        } catch (BadCredentialsException | InternalAuthenticationServiceException e){
            throw new BadRequestException("Authentication error! Incorrect login/password input.");
        } catch (Exception e){
            e.printStackTrace();
            throw new BadRequestException("Authentication error!");
        }
        return createAuthToken(loginRequestOpenApi);
    }

    @Override
    public JwtResponseOpenApi createAuthToken(LoginRequestOpenApi loginRequestOpenApi){
        return createAuthTokenByLogin(loginRequestOpenApi.getLogin());
    }

    @Override
    public JwtResponseOpenApi createAuthToken(UserOpenApi userOpenApi){
        return createAuthTokenByLogin(userOpenApi.getLogin());
    }

    private JwtResponseOpenApi createAuthTokenByLogin(String login){
        UserEntity user = userService.loadUserByUsername(login);
        String token = jwtUtils.generateToken(user.getUsername(),
                user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return new JwtResponseOpenApi(token, userMapper.toUserOpenApi(user));
    }
}
