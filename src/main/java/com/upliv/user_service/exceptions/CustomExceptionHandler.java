package com.upliv.user_service.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {



    @ExceptionHandler(ResourceAlreadyExistException.class)
    public final ResponseEntity<Object> handleResourceAlreadyExistExpetion (ResourceAlreadyExistException ex, WebRequest request) throws Exception {
        ExceptionResponse response = new ExceptionResponse(new Date(),ex.getMessage(),ex.getErrorCode(),ex.getLocalizedMessage());
        return new ResponseEntity(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<Object> handleResourceNotFoundException (ResourceNotFoundException ex, WebRequest request) throws Exception {
        ExceptionResponse response = new ExceptionResponse(new Date(),ex.getMessage(),ex.getErrorCode(),ex.getLocalizedMessage());
        return new ResponseEntity(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotAuthorizedException.class)
    public final ResponseEntity<Object> handleUserNotAuthorizedException (UserNotAuthorizedException ex, WebRequest request) throws Exception {
        ExceptionResponse response = new ExceptionResponse(new Date(),ex.getMessage(),ex.getErrorCode(),ex.getLocalizedMessage());
        return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        ExceptionResponse response = new ExceptionResponse(error, ErrorCodes.MISSING_PARAM,ex.getLocalizedMessage());
        return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(),0,request.getDescription(false));
        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse("Bad Request. Please check the request",ErrorCodes.BAD_REQUEST,ex.getBindingResult().toString());
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }
}
