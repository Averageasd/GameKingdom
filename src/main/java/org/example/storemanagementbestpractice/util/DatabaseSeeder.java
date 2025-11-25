package org.example.storemanagementbestpractice.util;

import org.example.storemanagementbestpractice.models.GameSessionEntity;
import org.example.storemanagementbestpractice.models.UserEntity;
import org.example.storemanagementbestpractice.repository.GameSessionRepository;
import org.example.storemanagementbestpractice.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final UserDetailsRepository userRepository;
    private final GameSessionRepository gameSessionRepository;
    @Autowired
    public DatabaseSeeder(UserDetailsRepository userRepository, GameSessionRepository gameSessionRepository) {
        this.userRepository = userRepository;
        this.gameSessionRepository = gameSessionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists to prevent duplicates on every run
        this.gameSessionRepository.deleteAll();
        seedDatabase();
    }

    private void seedDatabase() {
        // Create and save user entities
        Optional<UserEntity> userEntityOptional = userRepository.getUserWithUsername("testuser");
        UserEntity userEntity1 = userEntityOptional.get();
        List<GameSessionEntity> gameSessionEntityList = new ArrayList<>();
        for (int i = 0; i<10;i++){
            GameSessionEntity gameSessionEntity = new GameSessionEntity(
                    "session" + i,
                    "finish",
                    "tic-tac-toe",
                    userEntity1.getId()
            );
            gameSessionEntityList.add(gameSessionEntity);
        }

        gameSessionRepository.saveAll(gameSessionEntityList);
        System.out.println("Seeded initial user data.");
    }
}
