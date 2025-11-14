package org.example.storemanagementbestpractice.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.storemanagementbestpractice.dtos.LoginDTO;
import org.example.storemanagementbestpractice.dtos.SignUpDTO;
import org.example.storemanagementbestpractice.models.UserEntity;
import org.example.storemanagementbestpractice.repository.UserDetailsRepository;
import org.example.storemanagementbestpractice.util.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsRepository userDetailsRepository;
    private final JWTUtil jwtUtil;

    public AuthController(
            AuthenticationManager authenticationManager,
            UserDetailsRepository userDetailsRepository,
            JWTUtil jwtUtil
    ) {
        this.authenticationManager = authenticationManager;
        this.userDetailsRepository = userDetailsRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/auth/authenticate")
    public ResponseEntity<String> generateToken(@RequestBody LoginDTO loginDTO) {
        try {
            authenticationManager.authenticate(new
                    UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(),
                    loginDTO.getPassword())
            );
            return ResponseEntity.status(200).body(
                    jwtUtil
                            .generateToken(loginDTO.getUsername())
            );
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/auth/register")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDTO) {
        try {
            if (
                    userDetailsRepository.findByUsername(signUpDTO.getUsername()).isPresent() ||
                            userDetailsRepository.findByEmail(signUpDTO.getEmail()).isPresent()
            ) {
                return ResponseEntity.badRequest().body("Username is already in use");
            }
            UserEntity userEntity = new UserEntity(
                    signUpDTO.getUsername(),
                    false,
                    signUpDTO.getEmail(),
                    signUpDTO.getPassword()
            );
            userDetailsRepository.save(userEntity);
            return ResponseEntity.status(200).body("Registered successfully");
        } catch (Exception e) {
            throw e;
        }
    }
}
