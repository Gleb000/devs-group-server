package org.devs.group.server.sensor.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class RegisterSensorRequest {

    @NotNull(message = "[name] field shouldn't be empty")
    @Size(min = 3, max = 30, message = "[name] field length should be between 3 and 30 symbols")
    private String name;
}
