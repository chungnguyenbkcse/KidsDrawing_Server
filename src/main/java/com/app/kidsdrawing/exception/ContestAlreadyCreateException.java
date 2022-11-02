package com.app.kidsdrawing.exception;

import com.app.kidsdrawing.exception.config.GlobalErrorCode;

public class ContestAlreadyCreateException extends CustomGlobalException {
    public ContestAlreadyCreateException(String message) {
        super(message, GlobalErrorCode.ERROR_CONTEST_ALREADY_CREATE);
    }
}
