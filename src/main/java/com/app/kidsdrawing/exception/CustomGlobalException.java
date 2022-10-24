package com.app.kidsdrawing.exception;

import lombok.Getter;

@Getter
public class CustomGlobalException extends RuntimeException {
    private Long code;

    CustomGlobalException(String message, Long code) {
        super(message);
        this.code = code;
    }
}