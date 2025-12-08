package org.example.storemanagementbestpractice.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.example.storemanagementbestpractice.dtos.GameSessionHistoryPageable;
import org.example.storemanagementbestpractice.dtos.GameSessionSearchRequestDTO;
import org.example.storemanagementbestpractice.dtos.GameplayDTO;
import org.example.storemanagementbestpractice.dtos.NewGameRequestDTO;
import org.example.storemanagementbestpractice.services.GameSessionService;
import org.example.storemanagementbestpractice.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
public class GameController {

    private final GameSessionService gameSessionService;
    private final UserService userService;

    public GameController(
            GameSessionService gameSessionService,
            UserService userService
    ) {
        this.gameSessionService = gameSessionService;
        this.userService = userService;
    }

    @GetMapping("auth/{userId}/game")
    public GameSessionHistoryPageable gameSessionHistoryPageable(
            @PathVariable UUID userId,
            GameSessionSearchRequestDTO gameSessionSearchRequestDTO
    ) {
        userService.userExistById(userId);
        return gameSessionService.gameSessionHistoryDTOPageable(
                userId,
                gameSessionSearchRequestDTO
        );
    }

    @PostMapping(value = "auth/{userId}/game", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UUID addNewGame(
            @PathVariable UUID userId,
            @RequestParam String gameType,
            @RequestParam String gameStatus,
            @RequestPart("gameState") MultipartFile stateBlob
    ) throws IOException {
        userService.userExistById(userId);
        byte[] originalGameState = stateBlob.getBytes();
        val newGameRequestDTO = new NewGameRequestDTO();
        newGameRequestDTO.setGameType(gameType);
        newGameRequestDTO.setGameStatus(gameStatus);
        newGameRequestDTO.setGameState(originalGameState);
        return gameSessionService.createNewGameSession(userId, newGameRequestDTO);
    }

    @GetMapping(value = "auth/{userId}/game/{gameId}")
    public GameplayDTO getGame(
            @PathVariable UUID userId,
            @PathVariable UUID gameId
    ) {
        userService.userExistById(userId);
        return gameSessionService.getGame(userId, gameId);
    }

    @GetMapping(value = "auth/{userId}/game/gameState/{gameId}")
    public ResponseEntity<byte[]> getGameStateAsBinary(
            @PathVariable UUID userId,
            @PathVariable UUID gameId) {
        userService.userExistById(userId);

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(gameSessionService.getGameStateAsBinaryData(userId, gameId));
    }
}
