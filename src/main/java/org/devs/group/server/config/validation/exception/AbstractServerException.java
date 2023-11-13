package org.devs.group.server.config.validation.exception;

import lombok.Getter;

@Getter
public abstract class AbstractServerException extends RuntimeException {

    protected String publicMessage;

    public AbstractServerException(String publicMessage) {
        super(publicMessage);
        this.publicMessage = publicMessage;
    }
}
