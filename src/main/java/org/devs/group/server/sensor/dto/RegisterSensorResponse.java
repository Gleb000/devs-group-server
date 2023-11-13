package org.devs.group.server.sensor.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterSensorResponse {
    private String key;
}
