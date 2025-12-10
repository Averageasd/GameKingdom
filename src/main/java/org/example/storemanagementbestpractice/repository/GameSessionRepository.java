package org.example.storemanagementbestpractice.repository;

import org.example.storemanagementbestpractice.models.GameSessionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSessionEntity, UUID> {

    @Query(value = """
                 SELECT * FROM gameSession gs
                              WHERE gs.userId = :userId
                                AND (:gameStatus IS NULL OR :gameStatus = '' OR gs.gameStatus = :gameStatus)
                                AND (:gameType IS NULL OR :gameType = '' OR gs.gameType = :gameType)
                             ORDER BY gs.createdAt DESC
            """, nativeQuery = true)
    Page<GameSessionEntity> getGameSessionsForUser(UUID userId, Pageable pageable, String gameStatus, String gameType);

    @Query(value = """
                 SELECT * FROM gameSession gs
                              WHERE gs.userId = :userId
                              AND gs.id = :gameId
            """, nativeQuery = true)
    Optional<GameSessionEntity> getGame(UUID userId, UUID gameId);

    @Query(value = """
                 SELECT gs.gameState FROM gameSession gs
                              WHERE gs.userId = :userId
                              AND gs.id = :gameId
            """, nativeQuery = true)
    Optional<byte[]> getGameStateAsBinaryData(UUID userId, UUID gameId);

    @Modifying
    @Query(value = """
                 UPDATE gameSession
                             SET gameType = :gameType,
                                 gameStatus =:gameStatus,
                                 gameState =:gameState
                              WHERE userId = :userId AND id = :gameId
           """, nativeQuery = true)
    void updateGameState(UUID userId, UUID gameId, String gameType, String gameStatus, byte[] gameState);
}
