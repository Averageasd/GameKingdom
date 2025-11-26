package org.example.storemanagementbestpractice.repository;

import org.example.storemanagementbestpractice.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserEntity, UUID> {

    @Query(value = "SELECT Id FROM studyAppUser sa WHERE sa.email = :email", nativeQuery = true)
    Optional<UUID> findByEmail(String email);

    @Query(value = "SELECT * FROM studyAppUser sa WHERE sa.username = :username", nativeQuery = true)
    Optional<UserEntity> findByUsername(String username);

    @Query(value = "SELECT Id FROM studyAppUser sa WHERE sa.username = :username AND sa.accountEnabled=TRUE", nativeQuery = true)
    Optional<UUID> checkUserEnabledByUsername(String username);

    @Modifying
    @Query(value = "UPDATE studyAppUser SET password = :newPassword WHERE Id = :userId AND accountEnabled=TRUE", nativeQuery = true)
    Optional<Void> updateUserPassword(String newPassword, UUID userId);

    @Modifying
    @Query(value = "UPDATE studyAppUser SET email = :newEmail WHERE Id = :userId", nativeQuery = true)
    Optional<Void> updateEmail(UUID userId, String newEmail);

    @Query(value = "SELECT * FROM studyAppUser sa WHERE sa.Id = :userId", nativeQuery = true)
    Optional<UserEntity> findByUserId(UUID userId);
}
