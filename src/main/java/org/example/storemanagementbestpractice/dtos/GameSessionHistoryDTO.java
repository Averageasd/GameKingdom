package org.example.storemanagementbestpractice.dtos;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class GameSessionHistoryDTO {

    private UUID id;

    private String gameStatus;

    private String gameType;

    private OffsetDateTime createdAt;

    public GameSessionHistoryDTO(
            UUID id,
            String gameStatus,
            String gameType,
            OffsetDateTime createdAt) {
        this.id = id;
        this.gameStatus = gameStatus;
        this.gameType = gameType;
        this.createdAt = createdAt;
    }
}
