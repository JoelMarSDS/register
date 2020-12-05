package com.cash.register.domain.settings.exceptions;

public class UsersNotFoundException extends BusinessException{

    public UsersNotFoundException(String message) {
        super(message);
    }
}
