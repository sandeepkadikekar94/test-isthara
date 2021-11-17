package com.upliv.user_service.exceptions;

import java.util.Date;

public class ExceptionResponse {
    private Date timeStamp;
    private String message;
    private int errorCode;
    private String details;

    public ExceptionResponse(Date timeStamp, String message, int errorCode, String details) {
        this.timeStamp = timeStamp;
        this.message = message;
        this.errorCode = errorCode;
        this.details = details;
    }

    public ExceptionResponse(String message, int errorCode, String details) {
        this.timeStamp = new Date();
        this.message = message;
        this.errorCode = errorCode;
        this.details = details;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
