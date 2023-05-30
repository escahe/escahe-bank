package com.escahe.bank.exception.account;

import org.springframework.http.HttpStatus;

public class AccountDBException extends RuntimeException{
    private final String message;
    private final HttpStatus httpStatus;

    public AccountDBException(AccountDBExceptionCause accountDBExceptionMessage) {
        this.message = accountDBExceptionMessage.getMessage();
        this.httpStatus = accountDBExceptionMessage.getHttpStatus();
        
    }
    public String getMessage() {
        return message;
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }


    public enum AccountDBExceptionCause{
        NOT_FOUND("Cuenta no encontrada", HttpStatus.NOT_FOUND),
        ALREADY_EXISTS("La cuenta ya está registrada", HttpStatus.CONFLICT),
        ACCOUNT_NUMBER_ALREADY_IN_USE("El número ya pertenece a otra cuenta", HttpStatus.CONFLICT),
        FULL_UPDATE_REQUIRED("Solo se acepta actualización total de la cuenta", HttpStatus.BAD_REQUEST);
        
        private String message;
        private HttpStatus httpStatus;

        public HttpStatus getHttpStatus() {
            return httpStatus;
        }

        public String getMessage() {
            return message;
        }

        AccountDBExceptionCause(String message, HttpStatus httpStatus){
            this.message = message;
            this.httpStatus = httpStatus;
        }


    }
}

