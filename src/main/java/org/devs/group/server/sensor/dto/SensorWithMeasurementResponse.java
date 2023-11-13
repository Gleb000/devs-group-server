package org.devs.group.server.sensor.dto;

import lombok.Builder;
import lombok.Getter;
import org.devs.group.server.measurement.dto.MeasurementResponse;

import java.util.List;

@Getter
@Builder
public class SensorWithMeasurementResponse {
    private String name;
    private String key;
    private List<MeasurementResponse> measurements;
}
