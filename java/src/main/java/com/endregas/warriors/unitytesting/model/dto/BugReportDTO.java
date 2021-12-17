package com.endregas.warriors.unitytesting.model.dto;

import com.endregas.warriors.unitytesting.model.database.BugReport;
import com.endregas.warriors.unitytesting.model.utils.TimeInterval;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class BugReportDTO {

    @Size(max = 100, message = "Bug name cannot be longer than 100 characters")
    @NotBlank
    private String bugName;
    @Size(max = 1500, message = "Bug description cannot contain more than 1500 characters")
    private String bugDescription;
    @NotNull(message = "Time cannot be null")
    private TimeInterval timeVideoReference;

    public BugReport createBugReport(String game, String build, String playRun) {
        return BugReport.builder()
                .game(game)
                .build(build)
                .playRun(playRun)
                .timestamp(LocalDate.now())
                .bugName(bugName)
                .bugDescription(bugDescription)
                .fromSec(timeVideoReference.getStart())
                .toSec(timeVideoReference.getEnd())
                .build();
    }

}
