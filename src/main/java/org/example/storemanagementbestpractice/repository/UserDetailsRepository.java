package org.example.storemanagementbestpractice.repository;

import org.example.storemanagementbestpractice.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findById(UUID id);

    @Query(value = "SELECT Id FROM studyAppUser sa WHERE sa.username = :username AND sa.enabled=TRUE", nativeQuery = true)
    Optional<UUID> checkUserEnabled(String username, String password);
}
