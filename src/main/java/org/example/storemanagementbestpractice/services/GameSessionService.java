package org.example.storemanagementbestpractice.services;

import org.example.storemanagementbestpractice.dtos.GameSessionHistoryDTO;
import org.example.storemanagementbestpractice.dtos.GameSessionHistoryPageable;
import org.example.storemanagementbestpractice.dtos.GameSessionSearchRequestDTO;
import org.example.storemanagementbestpractice.mapper.GameSessionMapper;
import org.example.storemanagementbestpractice.models.GameSessionEntity;
import org.example.storemanagementbestpractice.repository.GameSessionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GameSessionService {

    private final GameSessionRepository gameSessionRepository;
    private final GameSessionMapper gameSessionMapper;

    public GameSessionService(
            GameSessionRepository gameSessionRepository,
            GameSessionMapper gameSessionMapper
    ) {
        this.gameSessionRepository = gameSessionRepository;
        this.gameSessionMapper = gameSessionMapper;
    }

    public GameSessionHistoryPageable gameSessionHistoryDTOPageable(
            UUID userId,
            GameSessionSearchRequestDTO gameSessionSearchRequestDTO) {
        Page<GameSessionEntity> gameSessionEntities = gameSessionRepository.getGameSessionsForUser(
                userId,
                PageRequest.of(gameSessionSearchRequestDTO.getPage(), 10),
                gameSessionSearchRequestDTO.getSessionName(),
                gameSessionSearchRequestDTO.getGameStatus(),
                gameSessionSearchRequestDTO.getGameType()
        );

        List<GameSessionHistoryDTO> gameSessionHistoryDTOList = new ArrayList<>();
        gameSessionEntities.forEach(gameSessionEntity -> {
            gameSessionHistoryDTOList.add(
                    gameSessionMapper
                            .gameSessionEntityToGameSessionHistoryDTOMapper(gameSessionEntity)
            );
        });

        return new GameSessionHistoryPageable(
                gameSessionHistoryDTOList,
                gameSessionEntities.hasNext(),
                gameSessionEntities.hasPrevious(),
                gameSessionEntities.getTotalPages(),
                gameSessionEntities.getPageable().getPageNumber()
        );
    }
}
