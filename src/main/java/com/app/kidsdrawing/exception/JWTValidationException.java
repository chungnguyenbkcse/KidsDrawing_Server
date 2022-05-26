package com.app.kidsdrawing.exception;

import com.app.kidsdrawing.exception.config.GlobalErrorCode;

public class JWTValidationException extends CustomGlobalException {
    public JWTValidationException(String message) {
        super(message, GlobalErrorCode.ERROR_JWT_VALIDATION_REGISTERED);
    }
}
