package com.endregas.warriors.unitytesting.controllers;

import com.endregas.warriors.unitytesting.services.ConnectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ConnectionController {

    private final ConnectionService connectionService;

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/connect")
    public ResponseEntity<Map<String, String>> getGameMapWithBuild() {
        Map<String, String> gameBuildMap = connectionService.getGameWithLatestBuildMap();
        log.info("Game/build mapping: {}", gameBuildMap);
        return ResponseEntity.ok(gameBuildMap);
    }

}
