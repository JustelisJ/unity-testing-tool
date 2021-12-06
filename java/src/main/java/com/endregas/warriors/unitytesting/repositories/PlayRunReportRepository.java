package com.endregas.warriors.unitytesting.repositories;

import com.endregas.warriors.unitytesting.model.database.PlayRunReportUploads;
import com.endregas.warriors.unitytesting.model.database.keys.PlayRunReportKey;
import org.springframework.data.repository.CrudRepository;

public interface PlayRunReportRepository extends CrudRepository<PlayRunReportUploads, PlayRunReportKey> {

    boolean existsByReportKey(PlayRunReportKey key);

}