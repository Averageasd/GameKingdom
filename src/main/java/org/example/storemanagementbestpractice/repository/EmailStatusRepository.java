package org.example.storemanagementbestpractice.repository;

import org.example.storemanagementbestpractice.models.EmailStatusEntity;
import org.example.storemanagementbestpractice.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmailStatusRepository extends JpaRepository<EmailStatusEntity, Integer> {

    @Query(value = "SELECT * FROM studyAppUser sa WHERE sa.Id = :userId", nativeQuery = true)
    Optional<UserEntity> findByUserId(UUID userId);

    Optional<EmailStatusEntity> findByEmailToken(String emailToken);
}
