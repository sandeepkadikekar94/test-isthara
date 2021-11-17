package com.upliv.user_service.exceptions;

public interface ErrorCodes {

    /*
    *   General Error 1001 - 1030
    *
    * */
    int MISSING_PARAM = 1001;
    int BAD_REQUEST = 1002;
    int DETAILS_NOT_VALID = 1003;
    int RECORD_NOT_FOUND = 1004;
    int NOT_AUTHORIZED = 1005;

    /* User Related Error 1030 - 1050 */
    int USER_ALREADY_EXIST = 1030;
    int INVALID_CREDENTIALS = 1031;
    int OTP_EXPIRED = 1032;
    int USER_NOT_FOUND = 1033;


}
