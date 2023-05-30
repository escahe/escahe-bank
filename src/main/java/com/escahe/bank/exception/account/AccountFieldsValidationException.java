package com.escahe.bank.exception.account;

import java.util.List;

public class AccountFieldsValidationException extends RuntimeException{
    private final List<String> messages;

    public AccountFieldsValidationException(List<AccountFieldExceptionMessage> errors) {
        this.messages = errors.stream().map(AccountFieldExceptionMessage::getMessage).toList();
    }

    public List<String> getMessages() {
        return messages;
    }

    public enum AccountFieldExceptionMessage{
        INVALID_NUMBER_FORMAT("El número de cuenta solo acepta números"),
        INVALID_NUMBER_LENGTH("El número de cuenta debe ser de 10 dígitos"),
        INVALID_TYPE("El tipo de cuenta no es válido, solo Ahorro o Corriente"),
        ;
        
        private String message;

        public String getMessage() {
            return message;
        }
        AccountFieldExceptionMessage(String message){
            this.message = message;
        }
    }
}

