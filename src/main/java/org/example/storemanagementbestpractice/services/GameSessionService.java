package org.example.storemanagementbestpractice.services;

import jakarta.transaction.Transactional;
import org.example.storemanagementbestpractice.dtos.*;
import org.example.storemanagementbestpractice.exceptions.GameNotExistException;
import org.example.storemanagementbestpractice.mapper.GameSessionMapper;
import org.example.storemanagementbestpractice.models.GameSessionEntity;
import org.example.storemanagementbestpractice.repository.GameSessionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
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
        Page<GameSessionEntity> gameSessionEntities =
                gameSessionRepository.getGameSessionsForUser(
                        userId,
                        PageRequest.of(gameSessionSearchRequestDTO.getPage(), 10),
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
                gameSessionEntities.getTotalElements(),
                gameSessionEntities.getNumber()
        );
    }

    public UUID createNewGameSession(
            UUID userId,
            NewGameRequestDTO newGameRequestDTO) {
        GameSessionEntity gameSessionEntity = new GameSessionEntity(
                newGameRequestDTO.getGameStatus(),
                newGameRequestDTO.getGameType(),
                newGameRequestDTO.getGameState(),
                userId
        );
        gameSessionRepository.save(gameSessionEntity);
        return gameSessionEntity.getId();
    }

    public GameplayDTO getGame(UUID userId, UUID gameId) {
        GameSessionEntity gameSessionEntity
                = gameSessionRepository.getGame(userId, gameId)
                .orElseThrow(
                        () -> new GameNotExistException(GameNotExistException.GAME_NOT_EXIST)
                );
        return new GameplayDTO(
                gameSessionEntity.getId(),
                gameSessionEntity.getGameStatus(),
                gameSessionEntity.getGameType()
        );
    }

    public byte[] getGameStateAsBinaryData(UUID userId, UUID gameId) {
        return gameSessionRepository.getGameStateAsBinaryData(userId, gameId)
        .orElseThrow(
                () -> new GameNotExistException(GameNotExistException.GAME_NOT_EXIST)
        );
    }

    @Transactional
    public void updateGameState(UUID userId, UUID gameId, String gameType, String gameStatus, byte[] gameState) {
       gameSessionRepository.getGame(userId, gameId)
                .orElseThrow(
                        () -> new GameNotExistException(GameNotExistException.GAME_NOT_EXIST)
                );
        gameSessionRepository.updateGameState(userId, gameId, gameType, gameStatus, gameState);
    }
}
