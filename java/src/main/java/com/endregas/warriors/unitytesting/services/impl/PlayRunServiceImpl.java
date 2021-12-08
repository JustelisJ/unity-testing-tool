package com.endregas.warriors.unitytesting.services.impl;

import com.endregas.warriors.unitytesting.exceptions.DirectoryDoesNotExistException;
import com.endregas.warriors.unitytesting.exceptions.DuplicatePlayRunReportException;
import com.endregas.warriors.unitytesting.model.database.PlayRunReportUploads;
import com.endregas.warriors.unitytesting.model.database.keys.PlayRunReportKey;
import com.endregas.warriors.unitytesting.model.dto.PlayRunReportDTO;
import com.endregas.warriors.unitytesting.repositories.PlayRunReportRepository;
import com.endregas.warriors.unitytesting.services.BugService;
import com.endregas.warriors.unitytesting.services.PlayRunService;
import com.endregas.warriors.unitytesting.utils.CommonValidations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayRunServiceImpl implements PlayRunService {

    private final BugService bugService;
    private final CommonValidations commonValidations;
    private final PlayRunReportRepository playRunReportRepository;

    @Override
    public void saveReport(PlayRunReportDTO report) throws DirectoryDoesNotExistException, DuplicatePlayRunReportException {
        if (playRunReportRepository.existsByReportKey(new PlayRunReportKey(report.getGameRef(), report.getBuildRef(), report.getVideoRef()))) {
            throw new DuplicatePlayRunReportException();
        }
        validateGameAndBuild(report.getGameRef(), report.getBuildRef());
        bugService.reportBugs(report.getGameRef(), report.getBuildRef(), report.getVideoRef(), report.getBugReports());
        playRunReportRepository.save(new PlayRunReportUploads(new PlayRunReportKey(report.getGameRef(), report.getBuildRef(), report.getVideoRef())));
    }

    @Override
    public PlayRunReportDTO getReport(String game, String build, String playRun) throws DirectoryDoesNotExistException {
        validateGameAndBuild(game, build);
        return PlayRunReportDTO.builder()
                .gameRef(game)
                .buildRef(build)
                .videoRef(playRun)
                .bugReports(bugService.getBugsForARun(game, build, playRun))
                .build();
    }

    private void validateGameAndBuild(String game, String build) throws DirectoryDoesNotExistException {
        try {
            commonValidations.validateThatGameBuildDirectoryExists(game, build);
        } catch (DirectoryDoesNotExistException e) {
            throw new DirectoryDoesNotExistException("Game or build provided are incorrect");
        }
    }
}