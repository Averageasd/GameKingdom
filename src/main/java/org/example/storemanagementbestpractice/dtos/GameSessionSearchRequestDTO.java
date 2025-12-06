package org.example.storemanagementbestpractice.dtos;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class GameSessionSearchRequestDTO {

    private String gameStatus;
    private String gameType;
    @Min(0)
    private int page;
}
