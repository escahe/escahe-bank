package com.escahe.bank.exception.client;

import java.util.List;

public class ClientFieldsValidationException extends RuntimeException{
    private final List<String> messages;

    public ClientFieldsValidationException(List<ClientFieldExceptionMessage> errors) {
        this.messages = errors.stream().map(ClientFieldExceptionMessage::getMessage).toList();
    }

    public List<String> getMessages() {
        return messages;
    }

    public enum ClientFieldExceptionMessage{
        NAME_BLANK_FIELD("El nombre está vacio"),
        NAME_WRONG_CHARACTER("El nombre solo acepta caracteres alfabeticos, apóstrofe y espacio"),
        AGE_MINIMUN_REQUIRED("La edad mínima permitida es de 14 años");
        
        private String message;

        public String getMessage() {
            return message;
        }
        ClientFieldExceptionMessage(String message){
            this.message = message;
        }
    }
}

