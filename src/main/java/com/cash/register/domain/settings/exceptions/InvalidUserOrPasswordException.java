package com.cash.register.domain.settings.exceptions;

public class InvalidUserOrPasswordException extends BusinessException{

    public InvalidUserOrPasswordException(String message) {
        super(message);
    }
}
