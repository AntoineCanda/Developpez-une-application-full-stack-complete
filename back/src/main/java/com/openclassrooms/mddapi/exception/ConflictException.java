package com.openclassrooms.mddapi.exception;

import lombok.Getter;

@Getter
public class ConflictException extends RuntimeException {

    private final String field;

    public ConflictException(String message, String field) {
        super(message);
        this.field = field;
    }
}
