package com.escahe.bank.exception.report;

import org.springframework.http.HttpStatus;

public class ReportDBException extends RuntimeException{
    String message;
    HttpStatus httpStatus;

    public ReportDBException(ReportDBExceptionCause reportDBExceptionCause){
        this.message = reportDBExceptionCause.message;
        this.httpStatus = reportDBExceptionCause.httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public enum ReportDBExceptionCause{
        INVALID_DATE_FORMAT("Formato de fecha incorrecto, debe ser 'yyyy-MM-dd' por ejemplo 2023-01-01 ", HttpStatus.BAD_REQUEST),
        INVALID_DATE_RANGE("La fecha 'hasta' no puede ser anterior a la fecha 'desde'", HttpStatus.BAD_REQUEST);

        private String message;
        private HttpStatus httpStatus; 

        ReportDBExceptionCause(String message, HttpStatus httpStatus){
            this.message = message;
            this.httpStatus = httpStatus;
        }
    }
}

