package com.endregas.warriors.unitytesting.model.database;

import com.endregas.warriors.unitytesting.model.dto.BugReportDTO;
import com.endregas.warriors.unitytesting.model.utils.TimeInterval;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BugReport {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String game;
    private String build;
    private String playRun;
    private LocalDate timestamp;
    private String bugName;
    @Column(columnDefinition="TEXT")
    private String bugDescription;
    private Double fromSec;
    private Double toSec;

    public BugReportDTO convertToDTO() {
        return new BugReportDTO(bugName, bugDescription, new TimeInterval(fromSec, toSec));
    }

}
