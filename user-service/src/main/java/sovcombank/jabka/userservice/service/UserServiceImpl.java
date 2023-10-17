package sovcombank.jabka.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sovcombank.openapi.model.ERoleOpenApi;
import ru.sovcombank.openapi.model.SignupRequestOpenApi;
import ru.sovcombank.openapi.model.UpdateUserOpenApi;
import ru.sovcombank.openapi.model.UserOpenApi;
import sovcombank.jabka.userservice.exception.BadRequestException;
import sovcombank.jabka.userservice.exception.NotFoundException;
import sovcombank.jabka.userservice.exception.StateException;
import sovcombank.jabka.userservice.mapper.UserMapper;
import sovcombank.jabka.userservice.model.ActivationToken;
import sovcombank.jabka.userservice.model.RecoveryToken;
import sovcombank.jabka.userservice.model.Role;
import sovcombank.jabka.userservice.model.UserEntity;
import sovcombank.jabka.userservice.model.enums.ActivationStatus;
import sovcombank.jabka.userservice.repositories.ActivationTokenRepository;
import sovcombank.jabka.userservice.repositories.RecoveryTokenRepository;
import sovcombank.jabka.userservice.repositories.RoleRepository;
import sovcombank.jabka.userservice.repositories.UserRepository;
import sovcombank.jabka.userservice.service.interfaces.UserService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MailSenderService mailService;
    private final PasswordEncoder passwordEncoder;
    private final ActivationTokenRepository activationTokenRepository;
    private final RecoveryTokenRepository recoveryTokenRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username)
                .orElseThrow(() -> new BadRequestException("User not found"));
    }

    @Override
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }
    @Override
    @Transactional
    public void sendVerificationEmail(UserEntity user) {
        String userEmail = user.getEmail();
        ActivationToken activationToken = new ActivationToken(user);
        mailService.sendVerificationEmail(userEmail, activationToken);
        activationTokenRepository.save(activationToken);
    }
    @Override
    public ResponseEntity<Void> activateUser(String token) {
        ActivationToken activationToken = activationTokenRepository.findByActivationToken(token);
        if (Objects.nonNull(activationToken)) {
            Date tokenExpirationDate = activationToken.getExpirationDate();
            Date currentDate = new Date();
            if (currentDate.before(tokenExpirationDate) && currentDate.equals(tokenExpirationDate)) {
                UserEntity user = activationToken.getUserEntity();
                Long userId = user.getId();
                Optional<UserEntity> userRepoOpt = userRepository.findById(userId);
                if (userRepoOpt.isPresent()) {
                    UserEntity userRepo = userRepoOpt.get();
                    userRepo.setActivationStatus(ActivationStatus.ACTIVATED);
                    userRepository.save(userRepo);
                    activationTokenRepository.delete(activationToken);
                    return ResponseEntity
                            .ok()
                            .build();
                }
            }
        }
        return ResponseEntity
                .notFound()
                .build();
    }

    @Override
    public ResponseEntity<Void> recoveryPassword(String password, String token) {
        RecoveryToken recoveryToken = recoveryTokenRepository.findByRecoveryToken(token);
        if (Objects.nonNull(recoveryToken)) {
            Date tokenExpirationDate = recoveryToken.getExpirationDate();
            Date currentDate = new Date();
            if (currentDate.before(tokenExpirationDate) && currentDate.equals(tokenExpirationDate)) {
                UserEntity user = recoveryToken.getUserEntity();
                Long userId = user.getId();
                Optional<UserEntity> userRepoOpt = userRepository.findById(userId);
                if (userRepoOpt.isPresent()) {
                    UserEntity userRepo = userRepoOpt.get();
                    userRepo.setPassword(passwordEncoder.encode(password));
                    userRepository.save(userRepo);
                    recoveryTokenRepository.delete(recoveryToken);
                    return ResponseEntity
                            .ok()
                            .build();
                }
            }
        }
        return ResponseEntity
                .notFound()
                .build();
    }

    @Override
    public ResponseEntity<Void> sendRecoveryPasswordMail(String email) {
        Optional<UserEntity> userOpt = userRepository.findByEmail(email);
        if(userOpt.isPresent()) {
            UserEntity user = userOpt.get();
            RecoveryToken recoveryToken = new RecoveryToken(user);
            mailService.sendRecoveryPassword(email, recoveryToken);
            recoveryTokenRepository.save(recoveryToken);
            return ResponseEntity
                    .ok()
                    .build();
        }
        return ResponseEntity
                .notFound()
                .build();
    }

    @Override
    @Transactional
    public UserEntity saveOrUpdate(SignupRequestOpenApi signupRequestOpenApi) {
        UserEntity user = userMapper.toUser(signupRequestOpenApi);
        return saveOrUpdate(user);
    }

    @Override
    @Transactional
    public UserEntity saveOrUpdate(UserOpenApi userOpenApi) {
        UserEntity user = userMapper.toUser(userOpenApi);
        return saveOrUpdate(user);
    }


    private UserEntity saveOrUpdate(UserEntity user) {
        if (Objects.nonNull(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (Objects.isNull(user.getId())) {
            loginOrEmailExistsCheck(user);
        }
        setRole(user);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.findById(id)
                .ifPresentOrElse(
                        userRepository::delete,
                        () -> {
                            throw new NotFoundException(String.format("User with id %d Not Found!", id));
                        });
    }

    @Override
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    @Transactional
    public void update(UpdateUserOpenApi updateUserOpenApi) {
        if (Objects.isNull(updateUserOpenApi.getNewUser()) || Objects.isNull(updateUserOpenApi.getOldUser())) {
            throw new BadRequestException("Old or new user is null");
        }
        if (Objects.isNull(updateUserOpenApi.getOldUser().getId())) {
            throw new BadRequestException("Id is null");
        }
        UserOpenApi oldUser = updateUserOpenApi.getOldUser();
        UserOpenApi newUser = updateUserOpenApi.getNewUser();
        newUser.setId(oldUser.getId());

        userRepository.findById(newUser.getId())
                .orElseThrow(() -> new BadRequestException(String.format("User with such id is not exists. Id: %d", newUser.getId())));

        if (!newUser.getEmail().equals(oldUser.getEmail())) {
            emailExistsCheck(newUser.getEmail());
        }
        if (!newUser.getLogin().equals(oldUser.getLogin())) {
            loginExistsCheck(newUser.getLogin());
        }
        UserEntity userEntity = userMapper.toUser(newUser);
        setRole(userEntity);
        userRepository.save(userEntity);
    }
    private void loginOrEmailExistsCheck(UserEntity user) {
        loginExistsCheck(user.getLogin());
        emailExistsCheck(user.getEmail());
    }

    private void loginExistsCheck(String login) {
        userRepository.findByLogin(login)
                .ifPresent((u) -> {
                    throw new StateException("User with such login is already exists");
                });
    }

    private void emailExistsCheck(String email) {
        userRepository.findByEmail(email)
                .ifPresent((u) -> {
                    throw new StateException("User with such email is already exists");
                });
    }

    private void setRole(UserEntity user) {
        roleRepository.findByName(ERoleOpenApi.ENROLLEE).ifPresentOrElse(
                (role) -> user.setRoles(Set.of(role)),
                () -> user.setRoles(Set.of(new Role(ERoleOpenApi.ENROLLEE)))
        );
    }
}
