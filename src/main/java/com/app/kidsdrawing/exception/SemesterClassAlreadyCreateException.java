package com.app.kidsdrawing.exception;

import com.app.kidsdrawing.exception.config.GlobalErrorCode;

public class SemesterClassAlreadyCreateException extends CustomGlobalException {
    public SemesterClassAlreadyCreateException(String message) {
        super(message, GlobalErrorCode.ERROR_SEMESTER_CLASS_ALREADY_CREATE);
    }
}
