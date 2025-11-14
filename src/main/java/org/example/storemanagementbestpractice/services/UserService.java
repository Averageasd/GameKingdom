package org.example.storemanagementbestpractice.services;

import lombok.extern.slf4j.Slf4j;
import org.example.storemanagementbestpractice.dtos.SignUpDTO;
import org.example.storemanagementbestpractice.models.UserEntity;
import org.example.storemanagementbestpractice.repository.UserDetailsRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsRepository userDetailsRepository;

    public UserService(final PasswordEncoder passwordEncoder, UserDetailsRepository userDetailsRepository) {
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
        userDetailsRepository.save(userEntity);
        log.info("new user created {}", userEntity);
        return userEntity;
    }
}
