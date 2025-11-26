package org.example.storemanagementbestpractice.controller;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.storemanagementbestpractice.dtos.*;
import org.example.storemanagementbestpractice.models.EmailStatusEntity;
import org.example.storemanagementbestpractice.models.UserEntity;
import org.example.storemanagementbestpractice.services.UserRegistrationEmailService;
import org.example.storemanagementbestpractice.services.UserRegistrationEmailTokenService;
import org.example.storemanagementbestpractice.services.UserService;
import org.example.storemanagementbestpractice.util.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserRegistrationEmailTokenService userRegistrationEmailTokenService;
    private final JWTUtil jwtUtil;
    private final UserRegistrationEmailService userRegistrationEmailService;

    public AuthController(
            AuthenticationManager authenticationManager,
            UserService userService,
            UserRegistrationEmailTokenService userRegistrationEmailTokenService,
            JWTUtil jwtUtil,
            UserRegistrationEmailService userRegistrationEmailService
    ) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userRegistrationEmailTokenService = userRegistrationEmailTokenService;
        this.jwtUtil = jwtUtil;
        this.userRegistrationEmailService = userRegistrationEmailService;
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

        // get id of enabled user using username. if user is not enabled, throw exception and stop
        UUID userId = userService.checkUserEnabledByUsername(loginDTO.getUsername());
        log.info("Authentication Successful");

        // return authenticated token with username and userid
        return ResponseEntity.status(200).body(
                jwtUtil.generateToken(
                        loginDTO.getUsername(),
                        userId
                ));
    }

    @Transactional(rollbackOn = Exception.class)
    @PostMapping("/auth/register")
    public ResponseEntity<String> registerUser(@Validated @RequestBody SignUpDTO signUpDTO) {

        // check user exist using username and email. if user with username or email is already in system, throw exception
        userService.checkUserExists(
                signUpDTO.getUsername(),
                signUpDTO.getEmail()
        );

        // create new user with email
        UserEntity userEntity = userService.createUser(signUpDTO);

        // create token
        EmailStatusEntity emailStatusEntity = userRegistrationEmailTokenService.createOrUpdateToken(userEntity.getId());

        // send token
        userRegistrationEmailService.sendEmail(emailStatusEntity.getEmailToken(), signUpDTO.getEmail());
        return ResponseEntity.status(201).body("Registered successfully. Please check your email for verification token");
    }

    // add email for account verification
    @Transactional(rollbackOn = Exception.class)
    @PostMapping("/auth/askForVerificationCode")
    public ResponseEntity<String> askForNewVerificationCode(
            @Validated @RequestBody CredentialsForEmailVerificationDTO credentialsForEmailVerificationDTO
    ) {
        UserEntity userEntity = userService.getLockedUserWithUsernameAndPassword(
                credentialsForEmailVerificationDTO.getUsername(),
                credentialsForEmailVerificationDTO.getPassword());

        // pass userid to create/update token
        EmailStatusEntity emailStatusEntity = userRegistrationEmailTokenService.createOrUpdateToken(userEntity.getId());

        // email token
        userRegistrationEmailService.sendEmail(emailStatusEntity.getEmailToken(), userEntity.getEmail());
        return ResponseEntity.status(200).body("Please check your email for verification token");
    }

    @PostMapping("/auth/verify")
    public ResponseEntity<String> verifyToken(@RequestParam String token) {
        // verify token to activate account
        userRegistrationEmailService.verifyToken(token);
        return ResponseEntity.status(200).body("Token Verified");
    }

    @Transactional(rollbackOn = Exception.class)
    @PutMapping("/auth/{userId}/resetPassword")
    public ResponseEntity<String> resetPassword(
            @Validated @RequestBody PasswordResetDTO passwordResetDTO,
            @PathVariable UUID userId
    ) {

        // update password given user email.
        // we don't use userid here because we have to make sure user can reset password
        // even before they are authenticated.
        userService.updateUserPassword(passwordResetDTO, userId);
        return ResponseEntity.status(200).body("Password reset successfully");
    }

    @Transactional(rollbackOn = Exception.class)
    @PutMapping("/auth/{userId}/updateEmail")
    public ResponseEntity<String> updateEmail(
            @Validated @RequestBody UpdateEmailDTO updateEmailDTO,
            @PathVariable UUID userId
    ) {
        // check if email already exists
        userService.checkEmailExists(updateEmailDTO.getEmail());

        // update email for user with current id
        userService.updateUserEmail(userId, updateEmailDTO.getEmail());
        return ResponseEntity.status(200).body("Email updated successfully");
    }
}
