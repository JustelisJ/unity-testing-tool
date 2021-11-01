package com.endregas.warriors.unitytesting.services.impl;

import com.endregas.warriors.unitytesting.model.database.BugReport;
import com.endregas.warriors.unitytesting.model.dto.BugReportDTO;
import com.endregas.warriors.unitytesting.repositories.BugRepository;
import com.endregas.warriors.unitytesting.services.BugService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BugServiceImplTest {

    BugRepository bugRepository = mock(BugRepository.class);

    BugService bugService = new BugServiceImpl(bugRepository);

    @Test
    void reportABug() {
        BugReportDTO bugReport = new BugReportDTO("Game name", "1", "1", 1L, "Notes");
        assertEquals(bugReport, bugService.reportABug(bugReport));
        verify(bugRepository, times(1)).save(any());
    }

    @Test
    void getBugsForARun() {
        BugReport bugReport = BugReport.builder()
                .timestamp(LocalDate.now())
                .game("Game name")
                .build("1")
                .runId("1")
                .time(1L)
                .notes("Notes")
                .build();
        BugReportDTO bugReportDTO = bugReport.convertToDTO();
        when(bugRepository.findAllByGameAndBuildAndRunId(anyString(), anyString(), anyString())).thenReturn(List.of(bugReport));
        List<BugReportDTO> result = bugService.getBugsForARun("Game name", "1", "1");
        assertEquals(List.of(bugReportDTO).size(), result.size());
        assertEquals(bugReportDTO.getGame(), result.get(0).getGame());
        assertEquals(bugReportDTO.getBuild(), result.get(0).getBuild());
        assertEquals(bugReportDTO.getRunId(), result.get(0).getRunId());
        assertEquals(bugReportDTO.getNotes(), result.get(0).getNotes());
        assertEquals(bugReportDTO.getTime(), result.get(0).getTime());
    }
}