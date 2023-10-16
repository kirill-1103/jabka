package sovcombank.jabka.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sovcombank.openapi.model.ERoleOpenApi;
import ru.sovcombank.openapi.model.SignupRequestOpenApi;
import sovcombank.jabka.userservice.exception.BadRequestException;
import sovcombank.jabka.userservice.exception.StateException;
import sovcombank.jabka.userservice.mapper.UserMapper;
import sovcombank.jabka.userservice.model.Role;
import sovcombank.jabka.userservice.model.UserEntity;
import sovcombank.jabka.userservice.repositories.UserRepository;
import sovcombank.jabka.userservice.service.interfaces.UserService;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findOneByLogin(username)
                .orElseThrow(()->new BadRequestException("User not found"));
    }

    @Override
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public UserEntity saveOrUpdate(SignupRequestOpenApi signupRequestOpenApi){
        signupRequestOpenApi.setPassword(passwordEncoder.encode(signupRequestOpenApi.getPassword()));
        UserEntity user = userMapper.toUser(signupRequestOpenApi);
        if(Objects.nonNull(user.getId())){
            userRepository.findOneByLogin(user.getLogin())
                    .orElseThrow(()->new StateException("User with such login is already exists"));
            userRepository.findOneByLogin(user.getEmail())
                    .orElseThrow(()->new StateException("User with such email is already exists"));
        }
        user.setRoles(Set.of(new Role(ERoleOpenApi.ENROLLEE)));
        return userRepository.save(user);
    }
}
