package org.devs.group.server.sensor.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SensorResponse {
    private String name;
    private String key;
}
