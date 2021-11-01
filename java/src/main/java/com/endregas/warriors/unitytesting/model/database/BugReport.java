package com.endregas.warriors.unitytesting.model.database;

import com.endregas.warriors.unitytesting.model.dto.BugReportDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BugReport {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String game;
    private String build;
    private String runId;
    private LocalDate timestamp;
    private long time;
    private String notes;

    public BugReportDTO convertToDTO() {
        return new BugReportDTO(game, build, runId, time, notes);
    }

}
