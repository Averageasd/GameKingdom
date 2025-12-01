package org.example.storemanagementbestpractice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

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
    @Length(min = 5, max = 255, message = "session name length must be between 5 and 255 characters")
    private String sessionName;

    @Column(nullable = false)
    private String gameStatus;

    @Column(nullable = false)
    private String gameType;

    @Column(nullable = false)
    private byte[] gameState;

    @Column(nullable = false)
    private UUID userId;

    public GameSessionEntity(String sessionName, String gameStatus, String gameType, byte[] gameState, UUID userId) {
        this.sessionName = sessionName;
        this.gameStatus = gameStatus;
        this.gameType = gameType;
        this.gameState = gameState;
        this.userId = userId;
    }
}
