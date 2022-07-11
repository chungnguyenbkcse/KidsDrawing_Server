package com.app.kidsdrawing.exception;

import com.app.kidsdrawing.exception.config.GlobalErrorCode;

public class ArtAgeAlreadyCreateException extends CustomGlobalException {
    public ArtAgeAlreadyCreateException(String message) {
        super(message, GlobalErrorCode.ERROR_ART_AGE_ALREADY_CREATE);
    }
}
