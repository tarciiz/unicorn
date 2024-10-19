package com.unicorn.api.infra.exceptions;

public class UserCreatorException extends RuntimeException {
    public UserCreatorException(String message) {
        super(message);
    }
}
