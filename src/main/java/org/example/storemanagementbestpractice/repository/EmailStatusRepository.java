package org.example.storemanagementbestpractice.repository;

import org.example.storemanagementbestpractice.models.EmailStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailStatusRepository extends JpaRepository<EmailStatusEntity, Integer> {
    Optional<EmailStatusEntity> findByEmailToken(String emailToken);
}
