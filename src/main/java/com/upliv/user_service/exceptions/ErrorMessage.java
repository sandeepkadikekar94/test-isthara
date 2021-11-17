package com.upliv.user_service.exceptions;

public interface ErrorMessage {

    String USER_ALREADY_EXIST = "User Already exists";
    String USER_NOT_FOUND = "No User Found";
    String NOT_AUTHORIZED = "User is not authorized for this this action";
    String RECORD_NOT_FOUND = "Could Not Find %s ";
    String INVALID_CREDENTIALS = "Invalid credentials %s";
    String OTP_EXPIRED = "OTP Expired";

    //General
    String DETAILS_NOT_VALID = "Details provided are not valid";
}
