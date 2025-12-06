package org.example.storemanagementbestpractice.util;

import org.example.storemanagementbestpractice.models.GameSessionEntity;
import org.example.storemanagementbestpractice.models.GameStatusConstants;
import org.example.storemanagementbestpractice.models.GameTypeConstants;
import org.example.storemanagementbestpractice.models.UserEntity;
import org.example.storemanagementbestpractice.repository.GameSessionRepository;
import org.example.storemanagementbestpractice.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final UserDetailsRepository userRepository;
    private final GameSessionRepository gameSessionRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DatabaseSeeder(UserDetailsRepository userRepository, GameSessionRepository gameSessionRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.gameSessionRepository = gameSessionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists to prevent duplicates on every run
        seedDatabase();
    }

    private void seedDatabase() {
        // Create and save user entities
        Optional<UserEntity> userEntityOptional = userRepository.findByUsername("testuser");
        if (userEntityOptional.isEmpty()) {
            UserEntity userEntity = new UserEntity(
                    "testuser",
                    true,
                    passwordEncoder.encode("123456"),
                    "testemail@gmail.com"
            );
            userRepository.save(userEntity);
            List<GameSessionEntity> gameSessionEntityList = new ArrayList<>();
            gameSessionRepository.saveAll(gameSessionEntityList);
        }


        System.out.println("Seeded initial user data.");
    }
}
