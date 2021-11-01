package com.endregas.warriors.unitytesting.repositories;

import com.endregas.warriors.unitytesting.model.database.BugReport;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface BugRepository extends CrudRepository<BugReport, String> {

    List<BugReport> findAllByGameAndBuildAndRunId(String game, String build, String runId);

}
