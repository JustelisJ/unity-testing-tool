package com.endregas.warriors.unitytesting.controllers;

import com.endregas.warriors.unitytesting.model.dto.BugReportDTO;
import com.endregas.warriors.unitytesting.services.BugService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BugController {

    private final BugService bugService;

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/bug", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BugReportDTO> saveVideo(@Valid @RequestBody BugReportDTO report) {
        BugReportDTO savedBug = bugService.reportABug(report);
        log.info("Bug saved: {}", savedBug);
        return ResponseEntity.ok(savedBug);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/bug")
    public ResponseEntity<List<BugReportDTO>> getRunBugs(
            @RequestParam(name = "game") @NotNull @Size(max = 50) String game,
            @RequestParam(name = "build") @NotNull @Size(max = 20) String build,
            @RequestParam(name = "runId") @NotNull @Size(max = 50) String runId) {
        List<BugReportDTO> retrievedBugs = bugService.getBugsForARun(game, build, runId);
        log.info("Fetched bugs: {}", retrievedBugs);
        return ResponseEntity.ok(retrievedBugs);
    }

}
