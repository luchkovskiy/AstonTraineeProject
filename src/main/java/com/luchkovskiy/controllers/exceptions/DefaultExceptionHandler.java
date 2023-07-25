package com.luchkovskiy.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.util.UUID;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleOthersException(Exception e) {
        String exceptionUniqueId = getUUID();
        return new ResponseEntity<>(
                new ErrorMessage(
                        exceptionUniqueId,
                        200,
                        e.getMessage()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> handleRuntimeException(RuntimeException e) {
        String exceptionUniqueId = getUUID();
        return new ResponseEntity<>(
                new ErrorMessage(
                        exceptionUniqueId,
                        300,
                        e.getMessage()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorMessage> handleSqlException(SQLException e) {
        String exceptionUniqueId = getUUID();
        return new ResponseEntity<>(
                new ErrorMessage(
                        exceptionUniqueId,
                        400,
                        e.getMessage()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getUUID() {
        return UUID.randomUUID().toString();
    }

}
