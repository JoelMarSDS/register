package com.cash.register.domain.settings.exceptions;

public class ValidationNotNullException extends BusinessException {

    public ValidationNotNullException(String message) {
        super(message);
    }
}
