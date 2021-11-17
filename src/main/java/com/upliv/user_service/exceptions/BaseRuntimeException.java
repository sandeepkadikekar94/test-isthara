package com.upliv.user_service.exceptions;

public abstract class BaseRuntimeException extends RuntimeException{
    private int errorCode;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public BaseRuntimeException(String message,int errorcode) {
        super(message);
        this.errorCode = errorcode;
    }


}
