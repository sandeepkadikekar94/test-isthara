package com.upliv.user_service.exceptions;

public class ResourceNotFoundException extends BaseRuntimeException {

    public ResourceNotFoundException(String message, int errorcode) {
        super(message, errorcode);
    }
}
