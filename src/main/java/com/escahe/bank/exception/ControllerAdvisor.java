package com.escahe.bank.exception;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.escahe.bank.exception.account.AccountDBException;
import com.escahe.bank.exception.account.AccountFieldsValidationException;
import com.escahe.bank.exception.client.ClientDBException;
import com.escahe.bank.exception.client.ClientFieldsValidationException;
import com.escahe.bank.exception.movement.MovementDBException;
import com.escahe.bank.exception.report.ReportDBException;

@ControllerAdvice
public class ControllerAdvisor {
    private static final String MESSAGE = "Message";

    //General InvalidDataAccessApiUsageException Handler
    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<Map<String, String>> handleInvalidDataAccessApiUsageException(
        InvalidDataAccessApiUsageException invalidDataAccessApiUsageException) {
        String message = invalidDataAccessApiUsageException.getMessage();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        
        return ResponseEntity.status(status).body(Collections.singletonMap(MESSAGE, message));
    }

    //General HttpMessageNotReadableException Handler
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(
        HttpMessageNotReadableException httpMessageNotReadableException) {
        String message = httpMessageNotReadableException.getMessage();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        
        return ResponseEntity.status(status).body(Collections.singletonMap(MESSAGE, message));
    }

    //Client Exceptions Handlers
    @ExceptionHandler(ClientFieldsValidationException.class)
    public ResponseEntity<Map<String, List<String>>> handleClientFieldsValidationException(
        ClientFieldsValidationException clientFieldsValidationException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, clientFieldsValidationException.getMessages()));
    }

    @ExceptionHandler(ClientDBException.class)
    public ResponseEntity<Map<String, String>> handleClientDBException(ClientDBException clientDBException) {
        String message = clientDBException.getMessage();
        HttpStatus status = clientDBException.getHttpStatus();

        return ResponseEntity.status(status).body(Collections.singletonMap(MESSAGE, message));
    }

    //Account Exceptions Handlers
    @ExceptionHandler(AccountFieldsValidationException.class)
    public ResponseEntity<Map<String, List<String>>> handleAccountFieldsValidationException(
        AccountFieldsValidationException accountFieldsValidationException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, accountFieldsValidationException.getMessages()));
    }

    @ExceptionHandler(AccountDBException.class)
    public ResponseEntity<Map<String, String>> handleAccountDBException(AccountDBException accountDBException) {
        String message = accountDBException.getMessage();
        HttpStatus status = accountDBException.getHttpStatus();

        return ResponseEntity.status(status).body(Collections.singletonMap(MESSAGE, message));
    }
    
    //Movement Exceptions Handlers
    @ExceptionHandler(MovementDBException.class)
    public ResponseEntity<Map<String, String>> handlerMovementDBException(MovementDBException movementDBException) {
        String message = movementDBException.getMessage();
        HttpStatus status = movementDBException.getHttpStatus();
        return ResponseEntity.status(status).body(Collections.singletonMap(MESSAGE, message));
    }

        //Reports Exceptions handlers
        @ExceptionHandler(ReportDBException.class)
        public ResponseEntity<Map<String, String>> handleReportDBException(ReportDBException reportDBException) {
            String message = reportDBException.getMessage();
            HttpStatus status = reportDBException.getHttpStatus();
            return ResponseEntity.status(status).body(Collections.singletonMap(MESSAGE, message));
        }
    
}

