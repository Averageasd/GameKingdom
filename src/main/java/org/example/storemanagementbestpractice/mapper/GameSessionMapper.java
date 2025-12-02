package org.example.storemanagementbestpractice.mapper;

import org.example.storemanagementbestpractice.dtos.GameSessionHistoryDTO;
import org.example.storemanagementbestpractice.models.GameSessionEntity;
import org.springframework.stereotype.Service;

@Service
public class GameSessionMapper {

    public GameSessionHistoryDTO gameSessionEntityToGameSessionHistoryDTOMapper(GameSessionEntity gameSessionEntity) {
        return new GameSessionHistoryDTO(
                gameSessionEntity.getId(),
                gameSessionEntity.getSessionName(),
                gameSessionEntity.getGameStatus(),
                gameSessionEntity.getGameType()
        );
    }
}
