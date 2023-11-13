package org.devs.group.server.config.validation.rest.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ServerError {
    private int code;
    private HttpStatus status;
    private String message;
}
