package org.example.storemanagementbestpractice.repository;

import org.example.storemanagementbestpractice.models.GameSessionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSessionEntity, UUID> {

    @Query(value = """
                 SELECT * FROM gameSession gs
                              WHERE gs.userId = :userId
                                AND (:sessionName IS NULL OR :sessionName = '' OR gs.sessionName ILIKE CONCAT(:sessionName, '%'))
                                AND (:gameStatus IS NULL OR :gameStatus = '' OR gs.gameStatus = :gameStatus)
                                AND (:gameType IS NULL OR :gameType = '' OR gs.gameType = :gameType)
            """, nativeQuery = true)
    Page<GameSessionEntity> getGameSessionsForUser(UUID userId, Pageable pageable, String sessionName, String gameStatus, String gameType);
}
