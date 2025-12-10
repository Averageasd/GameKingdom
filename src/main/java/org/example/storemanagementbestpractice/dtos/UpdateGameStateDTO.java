package org.example.storemanagementbestpractice.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateGameStateDTO {
    @NotNull
    private String gameStatus;

    @NotNull
    private String gameType;

    @NotNull
    private byte[] gameState;
}
