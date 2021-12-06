package com.endregas.warriors.unitytesting.services;

import com.endregas.warriors.unitytesting.exceptions.DirectoryDoesNotExistException;
import com.endregas.warriors.unitytesting.exceptions.DuplicatePlayRunReportException;
import com.endregas.warriors.unitytesting.model.dto.PlayRunReportDTO;

public interface PlayRunService {

    void saveReport(PlayRunReportDTO report) throws DirectoryDoesNotExistException, DuplicatePlayRunReportException;

    PlayRunReportDTO getReport(String game, String build, String playRun) throws DirectoryDoesNotExistException;

}
