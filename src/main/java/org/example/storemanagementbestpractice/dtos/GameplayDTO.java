package org.example.storemanagementbestpractice.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class GameplayDTO {

    private UUID id;

    private String gameStatus;

    private String gameType;

    private byte[] gameState;

    public GameplayDTO(
            UUID id,
            String gameStatus,
            String gameType,
            byte[] gameState) {
        this.id = id;
        this.gameStatus = gameStatus;
        this.gameType = gameType;
        this.gameState = gameState;
    }
}
