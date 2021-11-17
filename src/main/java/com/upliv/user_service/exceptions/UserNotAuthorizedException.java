package com.upliv.user_service.exceptions;

public class UserNotAuthorizedException extends BaseRuntimeException {

    public UserNotAuthorizedException(String message, int errorcode) {
        super(message, errorcode);
    }
}
