package org.example.storemanagementbestpractice.dtos;

import lombok.Data;

import java.util.List;

@Data
public class GameSessionHistoryPageable {
    private List<GameSessionHistoryDTO> gameSessionHistoryDTOS;
    private boolean hasNext;
    private boolean hasPrevious;
    private int allPages;
    private int currentPage;

    public GameSessionHistoryPageable(
            List<GameSessionHistoryDTO> gameSessionHistoryDTOS,
            boolean hasNext,
            boolean hasPrevious,
            int allPages,
            int currentPage
    ) {
        this.gameSessionHistoryDTOS = gameSessionHistoryDTOS;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
        this.allPages = allPages;
        this.currentPage = currentPage;
    }
}
