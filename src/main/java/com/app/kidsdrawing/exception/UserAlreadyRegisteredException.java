package com.app.kidsdrawing.exception;

import com.app.kidsdrawing.exception.config.GlobalErrorCode;

public class UserAlreadyRegisteredException extends CustomGlobalException {
    public UserAlreadyRegisteredException(String message) {
        super(message, GlobalErrorCode.ERROR_USER_ALREADY_REGISTERED);
    }
}
