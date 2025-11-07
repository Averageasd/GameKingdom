package org.example.storemanagementbestpractice.repository;

import org.example.storemanagementbestpractice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
