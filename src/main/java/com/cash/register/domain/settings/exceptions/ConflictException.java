package com.cash.register.domain.settings.exceptions;

public class ConflictException extends BusinessException{

    public ConflictException(String message) {
        super(message);
    }
}
