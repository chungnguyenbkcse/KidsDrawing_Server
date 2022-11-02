package com.app.kidsdrawing.exception;

import com.app.kidsdrawing.exception.config.GlobalErrorCode;

public class CourseAlreadyCreateException extends CustomGlobalException {
    public CourseAlreadyCreateException(String message) {
        super(message, GlobalErrorCode.ERROR_COURSE_ALREADY_CREATE);
    }
}