package org.example.storemanagementbestpractice.controller;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.storemanagementbestpractice.dtos.EmailForPasswordResetDTO;
import org.example.storemanagementbestpractice.dtos.LoginDTO;
import org.example.storemanagementbestpractice.dtos.SignUpDTO;
import org.example.storemanagementbestpractice.models.EmailStatusEntity;
import org.example.storemanagementbestpractice.models.UserEntity;
import org.example.storemanagementbestpractice.services.EmailService;
import org.example.storemanagementbestpractice.services.EmailTokenService;
import org.example.storemanagementbestpractice.services.UserService;
import org.example.storemanagementbestpractice.util.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.el.MethodNotFoundException;
import java.util.UUID;

@Slf4j
@RestController
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final EmailTokenService emailTokenService;
    private final JWTUtil jwtUtil;
    private final EmailService emailService;

    public AuthController(
            AuthenticationManager authenticationManager,
            UserService userService,
            EmailTokenService emailTokenService,
            JWTUtil jwtUtil,
            EmailService emailService
    ) {
        this.authenticationManager = authenticationManager;
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
        authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(),
                loginDTO.getPassword())
        );
        UUID userId = userService.checkUserEnabled(loginDTO);
        log.info("Authentication Successful");
        return ResponseEntity.status(200).body(
                jwtUtil.generateToken(
                        loginDTO.getUsername(),
                        userId
                ));
    }

    @Transactional(rollbackOn = Exception.class)
    @PostMapping("/auth/register")
    public ResponseEntity<?> registerUser(@Validated @RequestBody SignUpDTO signUpDTO) {
        userService.checkUserExists(signUpDTO);
        UserEntity userEntity = userService.createUser(signUpDTO);
        EmailStatusEntity emailStatusEntity = emailTokenService.createToken(userEntity);
        emailService.sendEmail(emailStatusEntity.getEmailToken(), userEntity.getEmail());
        return ResponseEntity.status(201).body("Registered successfully");
    }

    @PostMapping("/auth/verify")
    public ResponseEntity<String> verifyToken(@RequestParam String token) {
        emailService.verifyToken(token);
        return ResponseEntity.status(200).body("Token Verified");
    }

    @Transactional(rollbackOn = Exception.class)
    @PostMapping("/auth/{userId}/resetPassword")
    public ResponseEntity<String> resetPassword(
            @Validated @RequestBody EmailForPasswordResetDTO emailForPasswordResetDTO,
            @PathVariable UUID userId
    ) {
        userService.checkUserWithIdExistUsingEmail(emailForPasswordResetDTO, userId);
        userService.updateUserPassword(emailForPasswordResetDTO,userId);
        return ResponseEntity.status(200).body("Password reset successfully");
    }
}
