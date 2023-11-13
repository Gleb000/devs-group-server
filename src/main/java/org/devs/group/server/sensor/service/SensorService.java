package org.devs.group.server.sensor.service;

import org.devs.group.server.measurement.dto.MeasurementRequest;
import org.devs.group.server.measurement.dto.MeasurementResponse;
import org.devs.group.server.sensor.dto.RegisterSensorRequest;
import org.devs.group.server.sensor.dto.RegisterSensorResponse;
import org.devs.group.server.sensor.dto.SensorResponse;
import org.devs.group.server.sensor.dto.SensorWithMeasurementResponse;

import java.util.List;

public interface SensorService {

    RegisterSensorResponse register(RegisterSensorRequest request);

    void addMeasurementToSensor(String key, MeasurementRequest request);

    List<SensorResponse> getAll();

    List<MeasurementResponse> getLastTwentyMeasurements(String key);

    List<SensorWithMeasurementResponse> getActualSensorsMeasurements();
}
