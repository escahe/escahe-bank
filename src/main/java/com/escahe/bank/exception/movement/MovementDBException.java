package com.escahe.bank.exception.movement;

import org.springframework.http.HttpStatus;

public class MovementDBException extends RuntimeException{
    private final String message;
    private final HttpStatus httpStatus;

    public MovementDBException(MovementDBExceptionCause movementDBExceptionMessage) {
        this.message = movementDBExceptionMessage.getMessage();
        this.httpStatus = movementDBExceptionMessage.getHttpStatus();
        
    }
    public String getMessage() {
        return message;
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }


    public enum MovementDBExceptionCause{
        NOT_FOUND("Movimiento no encontrado", HttpStatus.NOT_FOUND),
        INSUFFICIENT_FUNDS("Fondos insuficientes", HttpStatus.BAD_REQUEST),
        DAILY_LIMIT_REACHED("El límite diario de retiros (1000 USD) ha sido alcanzado", HttpStatus.FORBIDDEN),
        INVALID_DATE_RANGE("La fecha final no puede ser menor que la fecha inicial", HttpStatus.BAD_REQUEST),
        EXCEEDS_WITHDRAWAL_LIMIT("No se permite retirar más de 1000 USD por día o transacción", HttpStatus.FORBIDDEN);
        
        private String message;
        private HttpStatus httpStatus;

        public HttpStatus getHttpStatus() {
            return httpStatus;
        }

        public String getMessage() {
            return message;
        }

        MovementDBExceptionCause(String message, HttpStatus httpStatus){
            this.message = message;
            this.httpStatus = httpStatus;
        }


    }
}
