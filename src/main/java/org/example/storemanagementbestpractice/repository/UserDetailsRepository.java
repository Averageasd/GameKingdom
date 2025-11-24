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

    Optional<UserEntity> findByEmail(String email);

    @Query(value = "SELECT * FROM studyAppUser sa WHERE sa.username = :username", nativeQuery = true)
    Optional<UserEntity> findByUsername(String username);

    @Query(value = "SELECT Id FROM studyAppUser sa WHERE (sa.username = :username OR sa.email =:username) AND sa.enabled=TRUE", nativeQuery = true)
    Optional<UUID> checkUserEnabled(String username);

    @Query(value = "SELECT Id FROM studyAppUser sa WHERE sa.email = :email AND sa.Id = :id AND sa.enabled=TRUE", nativeQuery = true)
    Optional<UUID> checkUserWithIdExistUsingEmail(String email, UUID id);

    @Modifying
    @Query(value = "UPDATE studyAppUser SET password = :newPassword WHERE Id = :userId", nativeQuery = true)
    void updateUserPassword(String newPassword, UUID userId);
}
