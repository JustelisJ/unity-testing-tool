package com.endregas.warriors.unitytesting.controllers;

import com.endregas.warriors.unitytesting.exceptions.DirectoryDoesNotExistException;
import com.endregas.warriors.unitytesting.exceptions.DuplicatePlayRunReportException;
import com.endregas.warriors.unitytesting.model.dto.PlayRunReportDTO;
import com.endregas.warriors.unitytesting.services.PlayRunService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PlayRunReportController {

    private final PlayRunService playRunService;

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/playrun")
    public ResponseEntity<String> savePlayRunReport(@RequestBody @NotNull PlayRunReportDTO report) throws DirectoryDoesNotExistException, DuplicatePlayRunReportException {
        playRunService.saveReport(report);
        return ResponseEntity.ok().body("Report uploaded successfully");
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/playrun",
            produces = "application/json")
    public ResponseEntity<PlayRunReportDTO> getVideoReport(String game, String build, String playrun) throws DirectoryDoesNotExistException {
        return ResponseEntity.ok().body(playRunService.getReport(game, build, playrun));
    }

}
