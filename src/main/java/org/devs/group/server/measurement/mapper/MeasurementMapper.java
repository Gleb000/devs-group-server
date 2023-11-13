package org.devs.group.server.measurement.mapper;

import org.devs.group.server.measurement.dto.MeasurementResponse;
import org.devs.group.server.measurement.model.MeasurementModel;

public interface MeasurementMapper {

    MeasurementResponse mapMeasurementModelToMeasurementResponse(MeasurementModel measurementModel);
}
