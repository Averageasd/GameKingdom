package org.example.storemanagementbestpractice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "gameSession")
public class GameSessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "Id", insertable = false, updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String gameStatus;

    @Column(nullable = false)
    private String gameType;

    @Column(nullable = false, columnDefinition = "BYTEA")
    private byte[] gameState;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private UUID userId;

    public GameSessionEntity(String gameStatus, String gameType, byte[] gameState, UUID userId) {
        this.gameStatus = gameStatus;
        this.gameType = gameType;
        this.gameState = gameState;
        this.userId = userId;
    }
}
