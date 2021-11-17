package com.upliv.user_service.exceptions;

public class ResourceAlreadyExistException extends BaseRuntimeException {

    public ResourceAlreadyExistException(String message, int errorcode) {
        super(message, errorcode);
    }
}
