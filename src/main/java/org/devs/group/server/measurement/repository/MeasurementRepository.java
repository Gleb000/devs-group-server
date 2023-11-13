package org.devs.group.server.measurement.repository;

import org.devs.group.server.measurement.model.MeasurementModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepository extends JpaRepository<MeasurementModel, Long> {}
