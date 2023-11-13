package org.devs.group.server.measurement.dto;

import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
public class MeasurementRequest {

    @NotNull(message = "[value] shouldn't be empty")
    @Min(value = -100, message = "[value] should be more than [-100]")
    @Max(value = 100, message = "[value] should be less than [100]")
    private Double value;

    @NotNull(message = "[raining] flag shouldn't be empty")
    private Boolean raining;
}
