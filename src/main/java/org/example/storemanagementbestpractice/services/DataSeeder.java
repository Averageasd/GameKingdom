package org.example.storemanagementbestpractice.services;

import org.example.storemanagementbestpractice.models.UserEntity;
import org.example.storemanagementbestpractice.repository.UserDetailsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder {

    @Bean
    public CommandLineRunner createAccounts(
            UserDetailsRepository userDetailsRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            if (userDetailsRepository.findByUsername("admin").isEmpty()) {
                UserEntity admin = new UserEntity();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin1234"));
                admin.setRole("ADMIN");
                userDetailsRepository.save(admin);
                System.out.println("user created");

            }
        };
    }
}
