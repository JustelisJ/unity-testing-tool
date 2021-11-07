package com.endregas.warriors.unitytesting.controllers;

import com.endregas.warriors.unitytesting.exceptions.DirectoryDoesNotExistException;
import com.endregas.warriors.unitytesting.services.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping(value = "/game")
    public ResponseEntity<List<String>> getAllGames() {
        List<String> games = gameService.getAllGames();
        log.info("All games fetched: {}", games);
        return ResponseEntity.ok(games);
    }

    @PostMapping(value = "/game")
    public ResponseEntity<String> createGameDirectory(
            @RequestParam(name = "game") @NotNull @Size(max = 50) String game) throws DirectoryDoesNotExistException {
        String newGameDirectory = gameService.createNewGameDirectory(game);
        log.info("Created new game directory: {}", newGameDirectory);
        return ResponseEntity.ok(newGameDirectory);
    }

}
