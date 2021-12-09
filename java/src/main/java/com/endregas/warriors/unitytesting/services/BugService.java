package com.endregas.warriors.unitytesting.services;

import com.endregas.warriors.unitytesting.model.dto.BugReportDTO;

import java.util.List;

public interface BugService {

    void reportBugs(String game, String build, String playRun, List<BugReportDTO> bugReport);

    List<BugReportDTO> getBugsForARun(String game, String build, String runId);

}
