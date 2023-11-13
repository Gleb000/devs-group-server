package org.devs.group.server.config.validation.exception;

public class EntityNotFoundException extends AbstractServerException {

    public EntityNotFoundException(String publicMessage) {
        super(publicMessage);
    }
}
