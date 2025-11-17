package org.example.storemanagementbestpractice.services;

import lombok.extern.slf4j.Slf4j;
import org.example.storemanagementbestpractice.dtos.LoginDTO;
import org.example.storemanagementbestpractice.dtos.SignUpDTO;
import org.example.storemanagementbestpractice.exceptions.UserAccountLockException;
import org.example.storemanagementbestpractice.exceptions.UserAlreadyExistException;
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

    public UUID checkUserEnabled(LoginDTO loginDTO) {
        UUID userId = userDetailsRepository.checkUserEnabled(loginDTO.getUsername(), loginDTO.getPassword())
                .orElseThrow(() -> new UserAccountLockException(UserAccountLockException.USER_IS_LOCKED));
        log.info("User account is enabled");
        return userId;
    }

    public void checkUserExists(SignUpDTO signUpDTO) {
        if (userDetailsRepository.findByUsername(signUpDTO.getUsername()).isPresent()
                ||
                userDetailsRepository.findByEmail(signUpDTO.getEmail()).isPresent()) {
            log.error("User already exists");
            throw new UserAlreadyExistException(UserAlreadyExistException.USER_ALREADY_EXIST);
        }
    }

    public void checkEmailExists(SignUpDTO signUpDTO) {

    }
}
