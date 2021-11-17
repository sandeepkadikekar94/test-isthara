package com.upliv.user_service.exceptions;

public class BadRequestException extends BaseRuntimeException {

    public BadRequestException(String message, int errorcode) {
        super(message, errorcode);
    }
}
