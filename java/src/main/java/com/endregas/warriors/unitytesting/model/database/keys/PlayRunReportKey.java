package com.endregas.warriors.unitytesting.model.database.keys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PlayRunReportKey implements Serializable {
    private String game;
    private String build;
    private String playRun;
}