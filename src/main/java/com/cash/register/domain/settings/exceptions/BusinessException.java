package com.cash.register.domain.settings.exceptions;

public class BusinessException extends RuntimeException {

    public BusinessException(String message){
        super(message);
    }

    public BusinessException(String message, Throwable messageCase){
        super(message, messageCase);
    }
}
