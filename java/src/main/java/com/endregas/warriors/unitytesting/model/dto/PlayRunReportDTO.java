package com.endregas.warriors.unitytesting.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PlayRunReportDTO {

    @NotBlank(message = "GameRef cannot be blank or null")
    private String gameRef;
    @NotBlank(message = "BuildRef cannot be blank or null")
    private String buildRef;
    @NotBlank(message = "VideoRef cannot be blank or null")
    private String videoRef;
    private List<BugReportDTO> bugReport;

}
