package com.endregas.warriors.unitytesting.services.impl;

import com.endregas.warriors.unitytesting.common.Utils;
import com.endregas.warriors.unitytesting.exceptions.DirectoryDoesNotExistException;
import com.endregas.warriors.unitytesting.exceptions.DuplicatePlayRunReportException;
import com.endregas.warriors.unitytesting.model.database.keys.PlayRunReportKey;
import com.endregas.warriors.unitytesting.model.dto.BugReportDTO;
import com.endregas.warriors.unitytesting.model.dto.PlayRunReportDTO;
import com.endregas.warriors.unitytesting.model.utils.TimeInterval;
import com.endregas.warriors.unitytesting.repositories.PlayRunReportRepository;
import com.endregas.warriors.unitytesting.services.BugService;
import com.endregas.warriors.unitytesting.services.BuildService;
import com.endregas.warriors.unitytesting.services.GameService;
import com.endregas.warriors.unitytesting.services.PlayRunService;
import com.endregas.warriors.unitytesting.utils.CommonValidations;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class PlayRunServiceImplTest {

    public static final String GAME = "Game";
    public static final String BUILD = "1.0";
    public static final String PLAYRUN = "run1";
    public static final String VIDEO_DIRECTORY = "src\\main\\resources\\videos\\";

    BugService bugService = mock(BugServiceImpl.class);
    CommonValidations commonValidations = mock(CommonValidations.class);
    PlayRunReportRepository playRunReportRepository = mock(PlayRunReportRepository.class);

    PlayRunService playRunService = new PlayRunServiceImpl(bugService, commonValidations, playRunReportRepository);

    BugReportDTO bugReport1 = new BugReportDTO("bug1", "something happened", new TimeInterval(5.68, 15.68));
    BugReportDTO bugReport2 = new BugReportDTO("bug2", "something else happened", new TimeInterval(20.14, 30.14));

    @BeforeEach
    void setUp() throws DirectoryDoesNotExistException {
        GameService gameService = new GameServiceImpl();
        gameService.createNewGameDirectory(GAME);
        BuildService buildService = new BuildServiceImpl(commonValidations);
        buildService.createNewBuildInGameDirectory(GAME, BUILD);
        when(playRunReportRepository.existsByReportKey(any(PlayRunReportKey.class))).thenReturn(false);
    }

    @AfterEach
    void teardown() {
        Utils.deleteDirectory(VIDEO_DIRECTORY);
    }

    @Test
    void saveReport_invalidGame() throws DirectoryDoesNotExistException {
        when(commonValidations.validateThatGameBuildDirectoryExists(anyString(), anyString())).thenThrow(DirectoryDoesNotExistException.class);
        PlayRunReportDTO playRunReport = PlayRunReportDTO.builder()
                .gameRef("other game")
                .buildRef(BUILD)
                .videoRef(PLAYRUN)
                .bugReport(List.of())
                .build();
        assertThrows(DirectoryDoesNotExistException.class, () -> playRunService.saveReport(playRunReport));
    }

    @Test
    void saveReport_invalidBuild() throws DirectoryDoesNotExistException {
        PlayRunReportDTO playRunReport = PlayRunReportDTO.builder()
                .gameRef(GAME)
                .buildRef("incorrect build")
                .videoRef(PLAYRUN)
                .bugReport(List.of())
                .build();
        when(commonValidations.validateThatGameBuildDirectoryExists(anyString(), anyString())).thenThrow(DirectoryDoesNotExistException.class);
        assertThrows(DirectoryDoesNotExistException.class, () -> playRunService.saveReport(playRunReport));
    }

    @Test
    void saveReport() throws DirectoryDoesNotExistException, DuplicatePlayRunReportException {
        PlayRunReportDTO playRunReport = PlayRunReportDTO.builder()
                .gameRef(GAME)
                .buildRef(BUILD)
                .videoRef(PLAYRUN)
                .bugReport(List.of(bugReport1, bugReport2))
                .build();
        playRunService.saveReport(playRunReport);
        verify(bugService, times(1)).reportBugs(eq(GAME), eq(BUILD), eq(PLAYRUN), anyList());
    }

    @Test
    void saveReport_cantSaveSecondReportForVideo() throws DirectoryDoesNotExistException, DuplicatePlayRunReportException {
        PlayRunReportDTO playRunReport = PlayRunReportDTO.builder()
                .gameRef(GAME)
                .buildRef(BUILD)
                .videoRef(PLAYRUN)
                .bugReport(List.of(bugReport1, bugReport2))
                .build();
        playRunService.saveReport(playRunReport);
        when(playRunReportRepository.existsByReportKey(any(PlayRunReportKey.class))).thenReturn(true);
        assertThrows(Exception.class, () -> playRunService.saveReport(playRunReport));
        verify(bugService, times(1)).reportBugs(eq(GAME), eq(BUILD), eq(PLAYRUN), anyList());
    }

    @Test
    void getReport() throws DirectoryDoesNotExistException {
        PlayRunReportDTO playRunReport = PlayRunReportDTO.builder()
                .gameRef(GAME)
                .buildRef(BUILD)
                .videoRef(PLAYRUN)
                .bugReport(List.of(bugReport1, bugReport2))
                .build();
        when(bugService.getBugsForARun(GAME, BUILD, PLAYRUN)).thenReturn(List.of(bugReport1, bugReport2));
        PlayRunReportDTO result = playRunService.getReport(GAME, BUILD, PLAYRUN);
        assertEquals(playRunReport.getGameRef(), result.getGameRef());
        assertEquals(playRunReport.getBuildRef(), result.getBuildRef());
        assertEquals(playRunReport.getVideoRef(), result.getVideoRef());
        assertEquals(playRunReport.getBugReport(), result.getBugReport());
    }
}