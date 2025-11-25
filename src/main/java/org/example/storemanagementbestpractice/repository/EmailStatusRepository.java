package org.example.storemanagementbestpractice.repository;

import org.example.storemanagementbestpractice.models.EmailStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmailStatusRepository extends JpaRepository<EmailStatusEntity, Integer> {

    @Query(value = "SELECT * FROM studyAppEmailStatus studyAppEmailStatus WHERE studyAppEmailStatus.userId = :userId", nativeQuery = true)
    Optional<EmailStatusEntity> findEmailTokenByUserId(UUID userId);

    Optional<EmailStatusEntity> findByEmailToken(String emailToken);
}
