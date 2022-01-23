package com.endregas.warriors.unitytesting.services.impl;

import com.endregas.warriors.unitytesting.model.database.BugReport;
import com.endregas.warriors.unitytesting.model.dto.BugReportDTO;
import com.endregas.warriors.unitytesting.model.utils.TimeInterval;
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
        BugReportDTO bugReport = new BugReportDTO("asd", "asd", new TimeInterval(1.4, 4.4));
        bugService.reportBugs("game", "build", "run", List.of(bugReport));
        verify(bugRepository, times(1)).save(any());
    }

    @Test
    void getBugsForARun() {
        BugReport bugReport = BugReport.builder()
                .timestamp(LocalDate.now())
                .game("Awesome game")
                .build("1.0")
                .playRun("asd")
                .fromSec(10.4)
                .toSec(15.4)
                .bugName("Player twitches")
                .bugDescription("idk")
                .build();
        BugReportDTO bugReportDTO = bugReport.convertToDTO();
        when(bugRepository.findAllByGameAndBuildAndPlayRun(anyString(), anyString(), anyString())).thenReturn(List.of(bugReport));
        List<BugReportDTO> result = bugService.getBugsForARun("Game name", "1", "1");
        assertEquals(List.of(bugReportDTO).size(), result.size());
        assertEquals(bugReportDTO.getBugName(), result.get(0).getBugName());
        assertEquals(bugReportDTO.getBugDescription(), result.get(0).getBugDescription());
        assertEquals(bugReportDTO.getTimeVideoReference().getStart(), result.get(0).getTimeVideoReference().getStart());
        assertEquals(bugReportDTO.getTimeVideoReference().getEnd(), result.get(0).getTimeVideoReference().getEnd());
    }
}