package org.example.storemanagementbestpractice.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.storemanagementbestpractice.models.LoginModel;
import org.example.storemanagementbestpractice.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JWTUtil jwtUtil;

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody LoginModel loginModel) {
        try {
            log.info("called this method");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginModel.getUsername(), loginModel.getPassword()));
            //TODO: Create jwt token
            return jwtUtil.generateToken(loginModel.getUsername());
        } catch (Exception e) {
            throw e;
        }
    }
}
