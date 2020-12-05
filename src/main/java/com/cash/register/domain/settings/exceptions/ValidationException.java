package com.cash.register.domain.settings.exceptions;

public class ValidationException extends BusinessException{

    public ValidationException(String message) {
        super(message);
    }
}
