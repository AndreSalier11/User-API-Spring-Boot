package com.cmas.systems.user.exceptions;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Problem> handleException(Exception ex, WebRequest request) {

        if (ex instanceof UserNotFoundException) {
            StatusType status = Status.NOT_FOUND;

            return new ResponseEntity<Problem>(
                Problem.builder()
                    .withTitle("User not found")
                    .withStatus(status)
                    .withDetail("User with id '" + ex.getMessage() + "' was not found in our systems")
                    .build(), HttpStatus.NOT_FOUND); 

        } else if (ex instanceof SomeFieldNotFilledException) {
            StatusType status = Status.BAD_REQUEST;

            return new ResponseEntity<Problem>(
                Problem.builder()
                    .withTitle("Some required field has not been filled in")
                    .withStatus(status)
                    .withDetail("At least one of the filds { firstName ; lastName ; email ; birthDate ; active }  has not been filled in")
                    .build(), HttpStatus.BAD_REQUEST);

        } else if (ex instanceof TypeMismatchException) {
            StatusType status = Status.BAD_REQUEST;

            return new ResponseEntity<Problem>(
                Problem.builder()
                    .withTitle("Paramer 'id' not filled properly")
                    .withStatus(status)
                    .withDetail(ex.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST);
                    
        } else {
            StatusType status = Status.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<Problem>(
                Problem.builder()
                    .withTitle(status.getReasonPhrase())
                    .withStatus(status)
                    .withDetail(ex.getMessage())
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }
}
