package com.endregas.warriors.unitytesting.model.database;

import com.endregas.warriors.unitytesting.model.database.keys.PlayRunReportKey;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PlayRunReportUploads {

    @EmbeddedId
    private PlayRunReportKey reportKey;

}

