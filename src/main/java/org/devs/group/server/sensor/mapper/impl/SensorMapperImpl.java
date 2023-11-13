package org.devs.group.server.sensor.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.devs.group.server.measurement.dto.MeasurementResponse;
import org.devs.group.server.measurement.mapper.MeasurementMapper;
import org.devs.group.server.measurement.model.MeasurementModel;
import org.devs.group.server.sensor.dto.SensorResponse;
import org.devs.group.server.sensor.dto.SensorWithMeasurementResponse;
import org.devs.group.server.sensor.mapper.SensorMapper;
import org.devs.group.server.sensor.model.SensorModel;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SensorMapperImpl implements SensorMapper {

    private final MeasurementMapper measurementMapper;

    @Override
    public SensorResponse mapSensorModelToSensorResponse(SensorModel sensorModel) {
        return SensorResponse.builder()
                .name(sensorModel.getName())
                .key(sensorModel.getKey())
                .build();
    }

    @Override
    public SensorWithMeasurementResponse mapSensorModelToSensorWithMeasurementResponse(
            SensorModel sensorModel,
            Instant validityDate
    ) {
        List<MeasurementResponse> measurements = retrieveAndMapActualMeasurements(
                sensorModel.getMeasurements(),
                validityDate
        );

        return SensorWithMeasurementResponse.builder()
                .name(sensorModel.getName())
                .key(sensorModel.getKey())
                .measurements(measurements)
                .build();
    }

    private List<MeasurementResponse> retrieveAndMapActualMeasurements(
            List<MeasurementModel> measurementModels,
            Instant validityDate
    ) {
        return measurementModels.stream()
                .filter(model -> !model.getMeasurementDate().isBefore(validityDate))
                .map(measurementMapper::mapMeasurementModelToMeasurementResponse)
                .collect(Collectors.toList());
    }
}
