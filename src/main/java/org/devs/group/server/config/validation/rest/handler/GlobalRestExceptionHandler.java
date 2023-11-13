package org.devs.group.server.config.validation.rest.handler;

import lombok.extern.slf4j.Slf4j;
import org.devs.group.server.config.validation.exception.AbstractServerException;
import org.devs.group.server.config.validation.exception.EntityNotFoundException;
import org.devs.group.server.config.validation.exception.NotUniqueEntityException;
import org.devs.group.server.config.validation.rest.dto.ServerError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.Optional;

@Slf4j
@ControllerAdvice
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String DEFAULT_VALIDATION_ERROR_MESSAGE = "Validation error";

    private final Map<Class<?>, HttpStatus> statusMap;

    public GlobalRestExceptionHandler() {
        statusMap = getStatusMapping();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        Optional<String> optionalMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .findFirst();
        String message = DEFAULT_VALIDATION_ERROR_MESSAGE;

        if (optionalMessage.isPresent()) {
            message = optionalMessage.get();
        }

        ServerError error = ServerError.builder()
                .code(status.value())
                .status(status)
                .message(message)
                .build();

        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ServerError> handle(Exception exception) {
        log.error("Exception [{}] during handling request", exception.getMessage(), exception);
        ServerError error = getErrorBy(exception);

        return new ResponseEntity<>(error, error.getStatus());
    }

    private ServerError getErrorBy(Throwable exception) {
        String message = String.format("Error [%s] during http request", exception.getMessage());
        log.error(message, exception);
        HttpStatus status =
                Optional.ofNullable(statusMap.get(exception.getClass()))
                        .orElse(HttpStatus.INTERNAL_SERVER_ERROR);

        log.info("HttpStatus for exception=[{}] is [{}]", exception.getClass(), status);
        String serverErrorMessage;

        if (exception instanceof AbstractServerException) {
            serverErrorMessage = ((AbstractServerException) exception).getPublicMessage();
        } else {
            serverErrorMessage = exception.getMessage();
        }

        return ServerError.builder()
                .code(status.value())
                .status(status)
                .message(serverErrorMessage)
                .build();
    }

    private Map<Class<?>, HttpStatus> getStatusMapping() {
        return Map.of(
                NotUniqueEntityException.class, HttpStatus.CONFLICT,
                EntityNotFoundException.class, HttpStatus.NOT_FOUND
        );
    }
}
