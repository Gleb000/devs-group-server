package org.devs.group.server.measurement.service;

import org.devs.group.server.measurement.dto.MeasurementRequest;
import org.devs.group.server.measurement.model.MeasurementModel;

import java.time.Instant;

public interface MeasurementService {

    MeasurementModel add(MeasurementRequest request, Instant measurementDate);
}
