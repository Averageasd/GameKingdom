package org.example.storemanagementbestpractice.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class GameplayDTO {

    private UUID id;

    private String gameStatus;

    private String gameType;


    public GameplayDTO(
            UUID id,
            String gameStatus,
            String gameType) {
        this.id = id;
        this.gameStatus = gameStatus;
        this.gameType = gameType;
    }
}
