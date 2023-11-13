package org.devs.group.server.sensor.mapper;

import org.devs.group.server.sensor.dto.SensorResponse;
import org.devs.group.server.sensor.dto.SensorWithMeasurementResponse;
import org.devs.group.server.sensor.model.SensorModel;

import java.time.Instant;

public interface SensorMapper {

    SensorResponse mapSensorModelToSensorResponse(SensorModel sensorModel);

    SensorWithMeasurementResponse mapSensorModelToSensorWithMeasurementResponse(
            SensorModel sensorModel,
            Instant validityDate
    );
}
