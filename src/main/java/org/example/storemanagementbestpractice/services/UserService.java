package org.example.storemanagementbestpractice.services;

import lombok.extern.slf4j.Slf4j;
import org.example.storemanagementbestpractice.dtos.PasswordResetDTO;
import org.example.storemanagementbestpractice.dtos.SignUpDTO;
import org.example.storemanagementbestpractice.exceptions.*;
import org.example.storemanagementbestpractice.models.UserEntity;
import org.example.storemanagementbestpractice.repository.UserDetailsRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsRepository userDetailsRepository;

    public UserService(
            PasswordEncoder passwordEncoder,
            UserDetailsRepository userDetailsRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsRepository = userDetailsRepository;
    }

    public UserEntity createUser(SignUpDTO signUpDTO) {
        UserEntity userEntity = new UserEntity(
                signUpDTO.getUsername(),
                false,
                passwordEncoder.encode(signUpDTO.getPassword()),
                signUpDTO.getEmail()
        );
        log.info("User entity created: {}", userEntity);
        userDetailsRepository.save(userEntity);
        log.info("New user created successfully {}", userEntity);
        return userEntity;
    }

    public UUID checkUserEnabledByUsername(String username) {
        UUID userId = userDetailsRepository.checkUserEnabledByUsername(username)
                .orElseThrow(() -> new UserAccountLockException(UserAccountLockException.USER_IS_LOCKED));
        log.info("User account is enabled");
        return userId;
    }

    public UserEntity getUser(String username, String password) {
        UserEntity userEntity = userDetailsRepository.findByUsername(
                        username
                )
                .orElseThrow(() -> new UserNotFoundException(UserNotFoundException.USER_NOT_FOUND));
        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            throw new UserNotFoundException(UserNotFoundException.USER_NOT_FOUND);
        }
        return userEntity;
    }

    public boolean isAccountEnabled(UserEntity userEntity) {
        return userEntity.isAccountEnabled();
    }

    public UserEntity getLockedUserWithUsernameAndPassword(String username, String password) {
        UserEntity userEntity = getUser(username, password);
        if (isAccountEnabled(userEntity)) {
            throw new AccountAlreadyVerifiedException(AccountAlreadyVerifiedException.ACCOUNT_ALREADY_VERIFIED);
        }
        return userEntity;
    }

    public void updateUserEmail(UUID id, String newEmail) {
        userDetailsRepository.updateEmail(id, newEmail).orElseThrow(
                () -> new UserNotFoundException(UserNotFoundException.USER_NOT_FOUND));
    }

    public void checkUserExists(String username, String email) {
        if (userDetailsRepository.findByUsername(username).isPresent() || userDetailsRepository.findByEmail(email).isPresent()) {
            log.error("User already exists");
            throw new UserAlreadyExistException(UserAlreadyExistException.USER_ALREADY_EXIST);
        }
    }

    public void checkEmailExists(String email) {
        if (userDetailsRepository.findByEmail(email).isPresent()) {
            log.error("email is already associated with an existing account");
            throw new EmailAlreadyExistsException(EmailAlreadyExistsException.EMAIL_ALREADY_EXISTS);
        }
    }

    public void updateUserPassword(PasswordResetDTO passwordResetDTO, UUID userId) {

        // only update account for user if email exists and user account is activated
        userDetailsRepository.updateUserPassword(
                        passwordEncoder.encode(
                                passwordResetDTO.getNewPassword()
                        ),
                        userId
                )
                .orElseThrow(() -> new FailToUpdateUserPasswordException(FailToUpdateUserPasswordException.FAIL_TO_UPDATE_PASSWORD));
        log.info("Password updated successfully for user with id {}", userId);
    }
}
