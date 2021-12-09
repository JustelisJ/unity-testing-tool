package com.endregas.warriors.unitytesting.services.impl;

import com.endregas.warriors.unitytesting.model.database.BugReport;
import com.endregas.warriors.unitytesting.model.dto.BugReportDTO;
import com.endregas.warriors.unitytesting.repositories.BugRepository;
import com.endregas.warriors.unitytesting.services.BugService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BugServiceImpl implements BugService {

    private final BugRepository bugRepository;

    @Override
    public void reportBugs(String game, String build, String playRun, List<BugReportDTO> bugReports) {
        bugReports.stream()
                .map(bugReportDTO -> bugReportDTO.createBugReport(game, build, playRun))
                .forEach(bugRepository::save);
    }

    @Override
    public List<BugReportDTO> getBugsForARun(String game, String build, String runId) {
        List<BugReport> reports = bugRepository.findAllByGameAndBuildAndPlayRun(game, build, runId);
        return reports.stream()
                .map(BugReport::convertToDTO)
                .collect(Collectors.toList());
    }
}
