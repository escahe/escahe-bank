package com.escahe.bank.exception.client;

import org.springframework.http.HttpStatus;

public class ClientDBException extends RuntimeException{
    private final String message;
    private final HttpStatus httpStatus;

    public ClientDBException(ClientDBExceptionCause clientDBExceptionMessage) {
        this.message = clientDBExceptionMessage.getMessage();
        this.httpStatus = clientDBExceptionMessage.getHttpStatus();
        
    }
    public String getMessage() {
        return message;
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }


    public enum ClientDBExceptionCause{
        NOT_FOUND("Cliente no encontrado", HttpStatus.NOT_FOUND),
        ALREADY_EXISTS("El usuario ya está registrado", HttpStatus.CONFLICT),
        FULL_UPDATE_REQUIRED("Solo se acepta modificación total", HttpStatus.BAD_REQUEST),
        PARTIAL_UPDATE_REQUIRED("Solo se acepta modificación parcial", HttpStatus.BAD_REQUEST),
        DNI_ALREADY_IN_USE("El número de identificación no pertenece a este cliente", HttpStatus.CONFLICT);
        
        private String message;
        private HttpStatus httpStatus;

        public HttpStatus getHttpStatus() {
            return httpStatus;
        }

        public String getMessage() {
            return message;
        }

        ClientDBExceptionCause(String message, HttpStatus httpStatus){
            this.message = message;
            this.httpStatus = httpStatus;
        }


    }
}
