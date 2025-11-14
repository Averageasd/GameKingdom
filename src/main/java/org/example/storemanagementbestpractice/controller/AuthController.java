package org.example.storemanagementbestpractice.controller;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.storemanagementbestpractice.dtos.LoginDTO;
import org.example.storemanagementbestpractice.dtos.SignUpDTO;
import org.example.storemanagementbestpractice.models.EmailStatusEntity;
import org.example.storemanagementbestpractice.models.UserEntity;
import org.example.storemanagementbestpractice.repository.UserDetailsRepository;
import org.example.storemanagementbestpractice.services.CustomUserDetailsService;
import org.example.storemanagementbestpractice.services.EmailService;
import org.example.storemanagementbestpractice.services.EmailTokenService;
import org.example.storemanagementbestpractice.services.UserService;
import org.example.storemanagementbestpractice.util.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsRepository userDetailsRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserService userService;
    private final EmailTokenService emailTokenService;
    private final JWTUtil jwtUtil;
    private final EmailService emailService;

    public AuthController(
            AuthenticationManager authenticationManager,
            UserDetailsRepository userDetailsRepository,
            CustomUserDetailsService customUserDetailsService,
            UserService userService,
            EmailTokenService emailTokenService,
            JWTUtil jwtUtil,
            EmailService emailService
    ) {
        this.authenticationManager = authenticationManager;
        this.userDetailsRepository = userDetailsRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.userService = userService;
        this.emailTokenService = emailTokenService;
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
    }

    @Transactional(rollbackOn = Exception.class)
    @PostMapping("/auth/authenticate")
    public ResponseEntity<String> generateToken(
            @Validated @RequestBody LoginDTO loginDTO
    ) {
        try {
            authenticationManager.authenticate(new
                    UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(),
                    loginDTO.getPassword())
            );
            UUID id = userDetailsRepository.checkUserEnabled(loginDTO.getUsername(), loginDTO.getPassword())
                    .orElseThrow(() -> new AuthorizationDeniedException("user account is still locked. verify account to activate it"));
            log.info("Authentication Successful");
            return ResponseEntity.status(200).body(
                    jwtUtil
                            .generateToken(loginDTO.getUsername())
            );
        } catch (Exception e) {
            log.info("Authentication Failed. Exception: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @PostMapping("/auth/register")
    public ResponseEntity<?> registerUser(@Validated @RequestBody SignUpDTO signUpDTO) {
        try {
            if (
                    userDetailsRepository.findByUsername(signUpDTO.getUsername()).isPresent() ||
                            userDetailsRepository.findByEmail(signUpDTO.getEmail()).isPresent()
            ) {
                return ResponseEntity.badRequest().body("Username is already in use");
            }
            UserEntity userEntity = userService.createUser(signUpDTO);
            EmailStatusEntity emailStatusEntity = emailTokenService.createToken(userEntity);
            emailService.sendEmail(emailStatusEntity.getEmailToken(), userEntity.getEmail());
            return ResponseEntity.status(200).body("Registered successfully");
        } catch (Exception e) {
            log.error("Registration failed. Exception {}", e.getMessage());
            throw e;
        }
    }

    @PostMapping("/auth/verify")
    public ResponseEntity<String> verifyToken(@RequestParam String token) {
        try {
            emailService.verifyToken(token);
            return ResponseEntity.status(200).body("Token Verified");
        } catch (Exception e) {
            log.error("Verification failed. Exception: {}", e.getMessage());
            throw e;
        }
    }
}
