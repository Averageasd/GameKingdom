package org.example.storemanagementbestpractice.dtos;

import lombok.Data;

import java.util.List;

@Data
public class GameSessionHistoryPageable {
    private List<GameSessionHistoryDTO> gameSessionHistoryDTOS;
    private boolean hasNext;
    private boolean hasPrevious;
    private long totalElements;
    private int currentPage;

    public GameSessionHistoryPageable(
            List<GameSessionHistoryDTO> gameSessionHistoryDTOS,
            boolean hasNext,
            boolean hasPrevious,
            long allElements,
            int currentPage
    ) {
        this.gameSessionHistoryDTOS = gameSessionHistoryDTOS;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
        this.totalElements = allElements;
        this.currentPage = currentPage;
    }
}
