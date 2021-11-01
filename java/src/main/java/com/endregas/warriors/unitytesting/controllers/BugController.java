package com.endregas.warriors.unitytesting.controllers;

import com.endregas.warriors.unitytesting.model.dto.BugReportDTO;
import com.endregas.warriors.unitytesting.services.BugService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BugController {

    private final BugService bugService;

    @PostMapping(value = "/bug", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BugReportDTO> saveVideo(@Valid @RequestBody BugReportDTO report) {
        return ResponseEntity.ok(bugService.reportABug(report));
    }

    @GetMapping(value = "/bug")
    public ResponseEntity<List<BugReportDTO>> getRunBugs(
            @RequestParam(name = "game") @NotNull @Size(max = 50) String game,
            @RequestParam(name = "build") @NotNull @Size(max = 20) String build,
            @RequestParam(name = "runId") @NotNull @Size(max = 50) String runId) {
        return ResponseEntity.ok(bugService.getBugsForARun(game, build, runId));
    }

}
