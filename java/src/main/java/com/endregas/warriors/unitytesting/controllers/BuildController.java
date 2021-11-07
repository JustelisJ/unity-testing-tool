package com.endregas.warriors.unitytesting.controllers;

import com.endregas.warriors.unitytesting.exceptions.DirectoryDoesNotExistException;
import com.endregas.warriors.unitytesting.services.BuildService;
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
public class BuildController {

    final BuildService buildService;

    @GetMapping(value = "/game/build")
    public ResponseEntity<List<String>> getAllBuilds(
            @RequestParam(name = "game") @NotNull @Size(max = 50) String game) throws DirectoryDoesNotExistException {
        List<String> builds = buildService.getAllBuildForAGame(game);
        log.info("All build fetched for {}: {}", game, builds);
        return ResponseEntity.ok(builds);
    }

    @PostMapping(value = "/game/build")
    public ResponseEntity<String> createGameDirectory(
            @RequestParam(name = "game") @NotNull @Size(max = 50) String game,
            @RequestParam(name = "build") @NotNull @Size(max = 20) String build) throws DirectoryDoesNotExistException {
        String newBuildDirectory = buildService.createNewBuildInGameDirectory(game, build);
        log.info("Created new build directory for {}: {}", game, newBuildDirectory);
        return ResponseEntity.ok(newBuildDirectory);
    }

}
