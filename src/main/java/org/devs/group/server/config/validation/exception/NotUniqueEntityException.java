package org.devs.group.server.config.validation.exception;

public class NotUniqueEntityException extends AbstractServerException {

    public NotUniqueEntityException(String publicMessage) {
        super(publicMessage);
    }
}
