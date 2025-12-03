package org.example.storemanagementbestpractice.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.storemanagementbestpractice.dtos.GameSessionHistoryPageable;
import org.example.storemanagementbestpractice.dtos.GameSessionSearchRequestDTO;
import org.example.storemanagementbestpractice.services.GameSessionService;
import org.example.storemanagementbestpractice.services.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("auth/{userId}/gameSessions")
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
}
