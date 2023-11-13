package org.devs.group.server.measurement.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class MeasurementResponse {
    private double value;
    private boolean raining;
    private Instant measurementDate;
}
