package org.example.storemanagementbestpractice.dtos;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class GameSessionHistoryDTO {

    private UUID id;

    private String sessionName;

    private String gameStatus;

    private String gameType;

    private OffsetDateTime createdAt;

    public GameSessionHistoryDTO(
            UUID id,
            String sessionName,
            String gameStatus,
            String gameType,
            OffsetDateTime createdAt) {
        this.id = id;
        this.sessionName = sessionName;
        ;
        this.gameStatus = gameStatus;
        this.gameType = gameType;
        this.createdAt = createdAt;
    }
}
